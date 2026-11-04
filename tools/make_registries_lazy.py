#!/usr/bin/env python3
"""Rewrite the registry holders so every instance is built *inside* its supplier.

Forge freezes ``BuiltInRegistries.BLOCK``, ``.ITEM`` and ``.ENTITY_TYPE`` before a mod is constructed,
and the constructors of ``Block``, ``Item`` and ``EntityType`` all call ``createIntrusiveHolder`` on
those very registries. A block, an item or an entity type therefore has to be created during
``RegisterEvent``; building one while the mod class loads dies with "Registry is already frozen".

The rewrite keeps the familiar ``ModBlocks.MARTIAN_CYLINDER`` style constants: the field stays, it just
is no longer final and is assigned by the supplier the moment Forge fires the register event. The
helper calls (``register``, ``simple``, ``spawnEgg``, ``tripod``) move into one static block placed
right before the private constructor, in declaration order.

Usage: python3 tools/make_registries_lazy.py
"""

from __future__ import annotations

import pathlib
import re
import sys

ROOT = pathlib.Path(__file__).resolve().parent.parent
REGISTRY = ROOT / "src/main/java/dev/arena/wotw/registry"

DECL = re.compile(
    r"^(?P<indent> *)public static final (?P<type>[A-Za-z0-9_.]+(?:<[^;=]*?>)?) "
    r"(?P<name>[A-Z][A-Z0-9_]*) = (?P<helper>[a-z][A-Za-z0-9_]*)\(",
    re.M,
)

KNOWN_HELPERS = ("register", "tripod", "simple", "spawnEgg")

STATIC_BLOCK_HEADER = """\
    /*
     * The instances are built here, not next to the fields: Forge freezes the vanilla registries before
     * a mod is constructed, and [Block], [Item] and [EntityType] all register themselves in their own
     * constructor. Running the construction inside the supplier keeps it in the register event, where
     * Forge expects it, and every field above is ready by the time the game uses it.
     */
    static {"""


def split_top_level(text: str) -> list[str]:
    """Split a Java argument list on the commas that are not inside a nested call."""
    out: list[str] = []
    depth = 0
    quote = ""
    current = ""
    i = 0
    while i < len(text):
        char = text[i]
        if quote:
            current += char
            if char == "\\":
                current += text[i + 1]
                i += 2
                continue
            if char == quote:
                quote = ""
            i += 1
            continue
        if char in "\"'":
            quote = char
            current += char
            i += 1
            continue
        if char in "([{":
            depth += 1
        elif char in ")]}":
            depth -= 1
        if char == "," and depth == 0:
            out.append(current)
            current = ""
        else:
            current += char
        i += 1
    if current.strip():
        out.append(current)
    return [chunk.strip() for chunk in out]


def render_call(indent: str, register_name: str, literal: str, name: str, expression: str) -> str:
    head = f"{indent}{register_name}.register({literal},"
    arrow = f"() -> {name} = "
    lines = expression.split("\n")
    if len(lines) == 1:
        return f"{head} {arrow}{expression});"
    body = lines[0] + "\n" + "\n".join(lines[1:])
    return f"{head}\n{indent}        {arrow}{body});"


def transform(path: pathlib.Path, register_name: str, build_entity: bool = False) -> bool:
    text = path.read_text()
    if "public static final" not in text:
        return False

    kept: list[str] = []
    calls: list[str] = []
    cursor = 0
    changed = False

    while True:
        match = DECL.search(text, cursor)
        if not match:
            kept.append(text[cursor:])
            break

        if match.group("helper") not in KNOWN_HELPERS:
            kept.append(text[cursor:match.end()])
            cursor = match.end()
            continue

        depth = 1
        quote = ""
        i = match.end()
        while i < len(text):
            char = text[i]
            if quote:
                if char == "\\":
                    i += 2
                    continue
                if char == quote:
                    quote = ""
            elif char in "\"'":
                quote = char
            elif char in "([{":
                depth += 1
            elif char in ")]}":
                depth -= 1
                if depth == 0:
                    break
            i += 1

        args = split_top_level(text[match.end():i])
        tail = text[i + 1:]
        if not tail.lstrip().startswith(";"):
            kept.append(text[cursor:match.end()])
            cursor = match.end()
            continue

        helper = match.group("helper")
        name = match.group("name")
        field_type = match.group("type")
        literal = args[0]

        if helper == "register":
            expression = args[1] if not build_entity else f"{args[1]}.build({literal})"
        elif helper == "tripod":
            expression = f"track({args[1]}.clientTrackingRange(10).updateInterval(2).build({literal}))"
        elif helper == "simple":
            properties = args[1]
            inner = "InfestedBlock" if len(args) > 2 and args[2] == "true" else "Block"
            expression = f"new {inner}({properties})"
        else:  # spawnEgg
            expression = (f"new net.minecraftforge.common.ForgeSpawnEggItem(() -> {args[1]}, {args[2]}, "
                          f"{args[3]}, new net.minecraft.world.item.Item.Properties())")

        kept.append(text[cursor:match.start()])
        kept.append(f"{match.group('indent')}public static {field_type} {name};\n")
        calls.append(render_call(match.group("indent") + "    ", register_name, literal, name, expression))

        cursor = i + 1
        while cursor < len(text) and text[cursor] in " \t":
            cursor += 1
        if cursor < len(text) and text[cursor] == ";":
            cursor += 1
        if cursor < len(text) and text[cursor] == "\n":
            cursor += 1
        changed = True

    if not changed:
        return False

    body = "".join(kept)
    block = STATIC_BLOCK_HEADER + "\n" + "\n".join(calls) + "\n    }\n"

    # put the static block right before the private constructor (or before the closing brace)
    anchor = re.search(r"\n    private [A-Z]\w*\(\) \{", body)
    if anchor:
        body = body[:anchor.start() + 1] + block + "\n" + body[anchor.start() + 1:]
    else:
        last = body.rstrip().rfind("\n}")
        body = body[:last + 1] + block + body[last + 1:]

    body = re.sub(r"\n{3,}", "\n\n", body)
    path.write_text(body.rstrip() + "\n")
    return True


def main() -> int:
    targets = {
        "ModBlocks.java": ("BLOCKS", False),
        "ModItems.java": ("ITEMS", False),
        "ModEntities.java": ("ENTITY_TYPES", True),
    }
    for name, (register, build_entity) in targets.items():
        path = REGISTRY / name
        if not path.exists():
            print(f"missing {name}")
            continue
        print(f"{name}: {'rewritten' if transform(path, register, build_entity) else 'unchanged'}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
