#!/usr/bin/env python3
"""Builds the three Martian structures as vanilla structure NBT files.

Every structure is made of Martian alloy blocks, with a cylinder block for the hatch or the power
core, hollow interiors, a scatter of red roots and the Martians that live inside. The files are
written to `src/main/resources/data/wotw/structures/` together with the worldgen JSON that places
them in the world (structure, template pool and structure set are generated here as well).

Usage: python3 tools/generate_structures.py
"""

import gzip
import json
import os
import struct

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
DATA = os.path.join(ROOT, "src/main/resources/data/wotw")
STRUCTURES = os.path.join(DATA, "structures")
WORLDGEN = os.path.join(DATA, "worldgen")
DATA_VERSION = 3465  # Minecraft 1.20.1

ALLOY = "wotw:martian_alloy_block"
CYLINDER = "wotw:martian_cylinder"
ROOT_BLOCK = "wotw:red_root"
BLOOD_ROOTS = "wotw:blood_roots"
WHITE_ALLOY = "wotw:white_martian_alloy"
GLASS = "wotw:martian_glass"
CHEST = "minecraft:chest"
AIR = "minecraft:air"


# --------------------------------------------------------------------------- #
#  Minimal NBT writer
# --------------------------------------------------------------------------- #

def tag_string(value):
    data = value.encode("utf-8")
    return struct.pack(">H", len(data)) + data


def tag_payload(payload):
    kind, value = payload
    if kind == "byte":
        return struct.pack(">b", value)
    if kind == "int":
        return struct.pack(">i", value)
    if kind == "long":
        return struct.pack(">q", value)
    if kind == "float":
        return struct.pack(">f", value)
    if kind == "double":
        return struct.pack(">d", value)
    if kind == "string":
        return tag_string(value)
    if kind == "list":
        item_kind, items = value
        if item_kind == "compound":
            body = b"".join(compound_body(item) for item in items)
        else:
            body = b"".join(tag_payload((item_kind, item)) for item in items)
        return bytes([KINDS[item_kind]]) + struct.pack(">i", len(items)) + body
    if kind == "compound":
        return compound_body(value)
    raise ValueError(f"unsupported tag {kind}")


KINDS = {"byte": 1, "int": 3, "long": 4, "float": 5, "double": 6, "string": 8, "list": 9, "compound": 10}


def compound_body(mapping):
    out = b""
    for name, payload in mapping.items():
        kind = payload[0]
        out += bytes([KINDS[kind]]) + tag_string(name) + tag_payload(payload)
    return out + b"\x00"


def write_nbt(path, mapping):
    body = b"\x0a" + tag_string("") + compound_body(mapping)
    with gzip.GzipFile(path, "wb", mtime=0) as handle:
        handle.write(body)


def int_list(values):
    return ("list", ("int", list(values)))


# --------------------------------------------------------------------------- #
#  Structure designs
# --------------------------------------------------------------------------- #

class Structure:
    """A sparse block grid plus a list of entity spawns."""

    def __init__(self):
        self.blocks = {}
        self.entities = []

    def set(self, x, y, z, block, nbt=None):
        self.blocks[(x, y, z)] = (block, nbt)

    def chest(self, x, y, z, loot_table):
        """A chest whose contents come from one of the mod's loot tables."""
        self.set(x, y, z, CHEST, {"LootTable": loot_table})

    def fill(self, x0, y0, z0, x1, y1, z1, block):
        for x in range(min(x0, x1), max(x0, x1) + 1):
            for y in range(min(y0, y1), max(y0, y1) + 1):
                for z in range(min(z0, z1), max(z0, z1) + 1):
                    self.set(x, y, z, block)

    def shell(self, x0, y0, z0, x1, y1, z1, block, hollow=True):
        for x in range(x0, x1 + 1):
            for y in range(y0, y1 + 1):
                for z in range(z0, z1 + 1):
                    edge = x in (x0, x1) or y in (y0, y1) or z in (z0, z1)
                    if edge or not hollow:
                        self.set(x, y, z, block)

    def spawn(self, x, y, z, entity_id):
        self.entities.append({"pos": (x + 0.5, float(y), z + 0.5), "id": entity_id})

    def to_nbt(self):
        palette = [AIR, ALLOY, CYLINDER, ROOT_BLOCK]
        for entity in self.entities:
            if entity["id"] not in palette:
                palette.append(entity["id"])
        for name, _ in self.blocks.values():
            if name not in palette:
                palette.append(name)
        index = {name: i for i, name in enumerate(palette)}

        xs = [x for x, _, _ in self.blocks] or [0]
        ys = [y for _, y, _ in self.blocks] or [0]
        zs = [z for _, _, z in self.blocks] or [0]
        # NBT cannot store negative positions, so the whole design is shifted so its corner is 0,0,0
        min_x, min_y, min_z = min(xs), min(ys), min(zs)
        size = (max(xs) - min_x + 1, max(ys) - min_y + 1, max(zs) - min_z + 1)

        blocks = []
        for (x, y, z), (block, nbt) in sorted(self.blocks.items()):
            entry = {
                "pos": int_list((x - min_x, y - min_y, z - min_z)),
                "state": ("int", index[block]),
            }
            if nbt:
                entry["nbt"] = ("compound", {key: ("string", value) for key, value in nbt.items()})
            blocks.append(entry)

        entities = []
        for entity in self.entities:
            x, y, z = entity["pos"]
            x -= min_x
            y -= min_y
            z -= min_z
            nbt = {"id": ("string", entity["id"]), "PersistenceRequired": ("byte", 1)}
            if entity["id"] == "wotw:martian":
                nbt["CustomName"] = ("string", '{"text":"Martian"}')
            entities.append({
                "pos": ("list", ("double", [x, y, z])),
                "blockPos": int_list((int(x), int(y), int(z))),
                "nbt": ("compound", nbt),
            })

        return {
            "DataVersion": ("int", DATA_VERSION),
            "size": int_list(size),
            "palette": ("list", ("compound", [{"Name": ("string", name)} for name in palette])),
            "blocks": ("list", ("compound", blocks)),
            "entities": ("list", ("compound", entities)),
        }


def landing_site():
    """A capsule that came down and stood up: hollow cylinder, hatch, crater roots."""
    s = Structure()
    # scorched crater lip of alloy plates
    for x in range(-5, 6):
        for z in range(-5, 6):
            distance = max(abs(x), abs(z))
            if distance in (4, 5) and (x + z) % 2 == 0:
                s.set(x, 0, z, ALLOY)
    # the capsule body: a hollow square cylinder, 5 wide, 9 tall
    s.shell(-2, 1, -2, 2, 8, 2, ALLOY)
    s.fill(-1, 1, -1, 1, 4, 1, AIR)
    s.fill(-1, 5, -1, 1, 7, 1, AIR)
    # hatch and portholes
    s.set(0, 1, -2, CYLINDER)
    s.set(0, 6, -2, AIR)
    s.set(0, 5, -2, AIR)
    # domed nose and fins
    for x in range(-2, 3):
        for z in range(-2, 3):
            if max(abs(x), abs(z)) <= 2:
                s.set(x, 9, z, ALLOY)
    for x, z in ((0, 0),):
        s.set(x, 10, z, ALLOY)
    for dx, dz in ((-2, -2), (2, -2), (-2, 2), (2, 2)):
        s.set(dx, 1, dz, ALLOY)
        s.set(dx, 2, dz, ALLOY)
    # the weed already creeping out of the hatch
    for x, z in ((0, -3), (1, -3), (-1, -3), (2, -2), (-2, -1), (3, 1), (-3, 2),
                 (2, 3), (-1, 3), (0, 4)):
        s.set(x, 0, z, ROOT_BLOCK)
    s.spawn(0, 1, 0, "wotw:martian")
    s.spawn(0, 5, 0, "wotw:martian")
    s.spawn(2, 0, -3, "wotw:martian_louse")
    s.spawn(-3, 0, 2, "wotw:martian")
    return s


def hatchery():
    """A capsule on its side, half sunk: a long tube with two hatches and a nest inside."""
    s = Structure()
    length = 13
    # wreck plates scattered around
    for x in range(-1, length + 1, 3):
        s.set(x, 0, -4, ALLOY)
        s.set(x + 1, 0, 4, ALLOY)
    # tube body
    s.shell(0, 1, -2, length - 1, 5, 2, ALLOY)
    s.fill(1, 2, -1, length - 2, 4, 1, AIR)
    # caps
    for y in range(1, 6):
        for z in range(-2, 3):
            s.set(0, y, z, ALLOY)
            s.set(length - 1, y, z, ALLOY)
    # ends open: hatches cut into the caps
    s.fill(0, 2, -1, 0, 4, 1, AIR)
    s.fill(length - 1, 2, -1, length - 1, 4, 1, AIR)
    s.set(0, 2, 0, CYLINDER)
    # ribs
    for x in range(2, length - 1, 3):
        s.fill(x, 1, -2, x, 5, -2, CYLINDER)
        s.fill(x, 1, 2, x, 5, 2, CYLINDER)
    # a nest of roots inside and out
    for x in range(2, length - 2, 2):
        s.set(x, 1, 0, ROOT_BLOCK)
    for x, z in ((-2, -1), (-2, 1), (length + 1, -1), (length + 1, 1), (5, -4), (8, 4), (-1, 3)):
        s.set(x, 0, z, ROOT_BLOCK)
    # an alloy roof over the middle so it reads as a lair
    s.shell(4, 6, -3, 9, 7, 3, ALLOY)
    s.spawn(3, 1, 0, "wotw:martian_louse")
    s.spawn(6, 1, 0, "wotw:martian")
    s.spawn(9, 1, 0, "wotw:martian_louse")
    s.spawn(11, 1, 0, "wotw:martian")
    s.spawn(-2, 0, 0, "wotw:martian")
    return s


def spire():
    """A three legged alloy tower with a glowing core — a Martian outpost."""
    s = Structure()
    # three legs
    for dx, dz in ((-3, -3), (3, -3), (0, 3)):
        for step in range(4):
            s.set(dx - step // 2 if dx > 0 else dx + step // 2, step, dz, ALLOY)
            s.set(dx, step, dz - step // 2 if dz > 0 else dz + step // 2, ALLOY)
    # body
    s.shell(-3, 4, -3, 3, 12, 3, ALLOY)
    s.fill(-2, 5, -2, 2, 11, 2, AIR)
    # doorway on two sides
    s.fill(0, 4, -3, 0, 6, -3, AIR)
    s.fill(3, 9, 3, 3, 10, 3, AIR)
    # core
    s.fill(-1, 8, -1, 1, 9, 1, CYLINDER)
    # crown
    for x in range(-3, 4):
        for z in range(-3, 4):
            if max(abs(x), abs(z)) <= 2:
                s.set(x, 13, z, ALLOY)
    s.set(0, 14, 0, CYLINDER)
    # root garden at the foot of the tower
    for x, z in ((4, 0), (-4, 0), (0, -4), (0, 4), (4, 4), (-4, -4), (2, 5), (-2, 5), (5, 2), (-5, -2)):
        s.set(x, 0, z, ROOT_BLOCK)
    s.spawn(0, 5, 0, "wotw:martian")
    s.spawn(1, 5, 0, "wotw:martian")
    s.spawn(0, 10, 0, "wotw:martian_master")
    s.spawn(0, 11, 0, "wotw:martian_louse")
    s.spawn(4, 0, 0, "wotw:martian")
    return s


def complex_site():
    """The complex: a white alloy blockhouse with glass windows, Martians and a blueprint chest."""
    s = Structure()
    radius = 10
    # foundation and floor
    s.fill(-radius, 0, -radius, radius, 0, radius, WHITE_ALLOY)
    # walls with a glass band
    for y in range(1, 8):
        for x in range(-radius, radius + 1):
            for z in range(-radius, radius + 1):
                on_wall = abs(x) == radius or abs(z) == radius
                if not on_wall:
                    continue
                block = GLASS if 3 <= y <= 5 and (x + z) % 2 == 0 else WHITE_ALLOY
                s.set(x, y, z, block)
    # roof, with a hole for the antenna
    s.fill(-radius, 8, -radius, radius, 8, radius, WHITE_ALLOY)
    s.fill(-1, 8, -1, 1, 8, 1, AIR)
    s.set(0, 9, 0, CYLINDER)
    s.set(0, 10, 0, CYLINDER)
    # door
    s.fill(0, 1, -radius, 1, 4, -radius, AIR)
    # inner sanctum: alloy frame, blueprint chest, pillars
    for x in (-4, 4):
        for z in (-4, 4):
            s.fill(x, 1, z, x, 6, z, WHITE_ALLOY)
    s.fill(-3, 1, -3, 3, 6, 3, AIR)
    s.chest(0, 1, -2, "wotw:chests/complex")
    s.set(0, 1, 2, CYLINDER)
    # machinery along the walls
    for x in (-7, 7):
        s.set(x, 1, 0, CYLINDER)
        s.set(x, 2, 0, WHITE_ALLOY)
    # blood roots outside the door
    for x, z in ((0, -radius - 1), (1, -radius - 1), (-1, -radius - 1), (2, -radius - 2), (-2, -radius - 2),
                 (3, -radius - 1), (-3, -radius - 1)):
        s.set(x, 0, z, BLOOD_ROOTS)
    s.spawn(0, 1, -5, "wotw:martian")
    s.spawn(3, 1, 3, "wotw:martian")
    s.spawn(-4, 1, 2, "wotw:martian")
    s.spawn(0, 1, 4, "wotw:martian_master")
    s.spawn(-6, 1, -6, "wotw:martian_louse")
    s.spawn(2, 1, 6, "wotw:martian_louse")
    s.spawn(5, 1, -5, "wotw:drone")
    s.spawn(-5, 1, 5, "wotw:drone")
    return s


def dig_site():
    """The digging machine's quarry: a terraced pit with alloy props and a borer inside it."""
    s = Structure()
    # concentric terraces, deepest in the middle
    for level in range(4):
        half = 11 - level * 3
        s.fill(-half, level, -half, half, level, half, WHITE_ALLOY)
        s.fill(-half + 1, level, -half + 1, half - 1, level, half - 1, AIR)
    s.fill(-2, 0, -2, 2, 0, 2, ALLOY)                     # pit floor
    s.set(0, 1, 0, CYLINDER)                              # the borer's power core
    # a roofed tunnel leaving the pit towards -z
    for z in range(-14, -2):
        s.fill(-2, 1, z, 2, 3, z, AIR)
        s.fill(-3, 1, z, -3, 3, z, ALLOY)
        s.fill(3, 1, z, 3, 3, z, ALLOY)
        s.fill(-3, 4, z, 3, 4, z, ALLOY)
    # spore fields and crew
    for x, z in ((6, 2), (-6, 3), (2, 8), (-3, 8), (8, -4), (-8, -5), (0, 9)):
        s.set(x, 0, z, BLOOD_ROOTS)
    s.spawn(0, 1, 0, "wotw:digging_machine")
    s.spawn(4, 1, -4, "wotw:martian")
    s.spawn(-4, 1, 4, "wotw:martian")
    s.spawn(5, 1, 5, "wotw:drone")
    return s


def beacon():
    """A landing beacon: a white alloy tower with a glass band and a glowing Martian core."""
    s = Structure()
    s.fill(-4, 0, -4, 4, 0, 4, WHITE_ALLOY)
    for y in range(1, 4):
        s.fill(-4, y, -4, 4, y, 4, WHITE_ALLOY)
        s.fill(-3, y, -3, 3, y, 3, AIR)
    # glass band
    for y in (4, 5):
        for x in range(-4, 5):
            for z in range(-4, 5):
                if abs(x) == 4 or abs(z) == 4:
                    s.set(x, y, z, GLASS)
    # tower shaft
    for y in range(6, 14):
        s.fill(-2, y, -2, 2, y, 2, WHITE_ALLOY)
        s.fill(-1, y, -1, 1, y, 1, AIR)
    s.set(0, 7, 0, CYLINDER)
    s.set(0, 11, 0, CYLINDER)
    # crown and beacon light
    s.fill(-3, 14, -3, 3, 14, 3, WHITE_ALLOY)
    s.set(0, 15, 0, CYLINDER)
    s.set(0, 16, 0, CYLINDER)
    # door and roots
    s.fill(0, 1, -4, 1, 3, -4, AIR)
    for x, z in ((0, -5), (1, -6), (-1, -5), (2, -6), (-2, -6), (5, 2), (-5, -3)):
        s.set(x, 0, z, BLOOD_ROOTS)
    s.spawn(0, 1, 0, "wotw:martian_master")
    s.spawn(0, 7, 0, "wotw:martian")
    s.spawn(3, 1, 3, "wotw:martian")
    s.spawn(-3, 1, -3, "wotw:drone")
    s.spawn(6, 0, 0, "wotw:tempest")
    return s


STRUCTURES_SPEC = {
    "landing_site": (landing_site(), 40, 14, 3417001, 2),
    "hatchery": (hatchery(), 52, 18, 3417002, 2),
    "spire": (spire(), 64, 22, 3417003, 1),
    "complex": (complex_site(), 90, 32, 3417004, 2),
    "dig_site": (dig_site(), 72, 26, 3417005, 1),
    "beacon": (beacon(), 84, 30, 3417006, 1),
}


def build_worldgen(name, spacing, separation, salt, size):
    structure = {
        "type": "minecraft:jigsaw",
        "biomes": f"#wotw:has_structure/{name}",
        "step": "surface_structures",
        "spawn_overrides": {},
        "terrain_adaptation": "beard_thin",
        "start_pool": f"wotw:{name}/start_pool",
        "size": size,
        "start_height": {"absolute": 0},
        "project_start_to_heightmap": "WORLD_SURFACE_WG",
        "max_distance_from_center": 24,
        "use_expansion_hack": False,
    }
    pool = {
        "name": f"wotw:{name}/start_pool",
        "fallback": "minecraft:empty",
        "elements": [{
            "weight": 1,
            "element": {
                "element_type": "minecraft:single_pool_element",
                "location": f"wotw:{name}",
                "processors": "minecraft:empty",
                "projection": "rigid",
            },
        }],
    }
    structure_set = {
        "structures": [{"structure": f"wotw:{name}", "weight": 1}],
        "placement": {
            "type": "minecraft:random_spread",
            "spacing": spacing,
            "separation": separation,
            "salt": salt,
            "spread_type": "linear",
        },
    }
    biome_tag = {"replace": False, "values": [
        "minecraft:plains", "minecraft:sunflower_plains", "minecraft:desert", "minecraft:savanna",
        "minecraft:snowy_plains", "minecraft:taiga", "minecraft:badlands", "minecraft:swamp",
        "minecraft:forest", "minecraft:birch_forest", "minecraft:dark_forest",
        "minecraft:old_growth_birch_forest", "minecraft:jungle", "minecraft:wooded_badlands",
        "minecraft:stony_shore", "minecraft:windswept_hills",
    ]}
    return structure, pool, structure_set, biome_tag


def main():
    os.makedirs(STRUCTURES, exist_ok=True)
    for name, (structure, spacing, separation, salt, size) in STRUCTURES_SPEC.items():
        write_nbt(os.path.join(STRUCTURES, f"{name}.nbt"), structure.to_nbt())
        print("structure", name, len(structure.blocks), "blocks,", len(structure.entities), "entities")

        json_spec, pool, structure_set, biome_tag = build_worldgen(name, spacing, separation, salt, size)
        outputs = {
            os.path.join(WORLDGEN, "structure", f"{name}.json"): json_spec,
            os.path.join(WORLDGEN, "template_pool", name, "start_pool.json"): pool,
            os.path.join(WORLDGEN, "structure_set", f"{name}.json"): structure_set,
            os.path.join(DATA, "tags/worldgen/biome/has_structure", f"{name}.json"): biome_tag,
        }
        for path, payload in outputs.items():
            os.makedirs(os.path.dirname(path), exist_ok=True)
            with open(path, "w", encoding="utf-8") as handle:
                json.dump(payload, handle, indent=2)
                handle.write("\n")
    print("worldgen json done")


if __name__ == "__main__":
    main()
