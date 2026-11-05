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
HULL = "wotw:martian_hull"
CONSOLE = "wotw:martian_console"
LAMP = "wotw:martian_lamp"
CRATE_M = "wotw:martian_crate"
CRATE_H = "wotw:human_crate"
CRATE_MIL = "wotw:military_crate"
TURRET = "wotw:martian_turret"
VINES = "wotw:blood_vines"
INF_COBBLE = "wotw:infested_cobblestone"
INF_BRICKS = "wotw:infested_bricks"
# vanilla palette used by the human settlements
STONE_BRICK = "minecraft:stone_bricks"
COBBLE = "minecraft:cobblestone"
PLANKS = "minecraft:oak_planks"
SPRUCE = "minecraft:spruce_planks"
LOG = "minecraft:oak_log"
GLASS_PANE = "minecraft:glass_pane"
WINDOW = "minecraft:glass"
GRAVEL = "minecraft:gravel"
DIRT_PATH = "minecraft:dirt_path"
CONCRETE = "minecraft:gray_concrete"
WHITE_CONCRETE = "minecraft:white_concrete"
WATER = "minecraft:water"
SAND = "minecraft:sand"
LANTERN = "minecraft:lantern"
IRON = "minecraft:iron_block"
SMOOTH = "minecraft:smooth_stone"
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




# --------------------------------------------------------------------------- #
#  Human settlements
# --------------------------------------------------------------------------- #

def house(s, x0, z0, width, depth, height, wall, floor, roof_tile, ruined=False, rnd_seed=0):
    """A small house: floor, walls with windows, a door and a gabled roof. Ruined ones have holes.

    The roof is walked row by row with a random skip so a ruined house reads as collapsed rather
    than as a clean shell.
    """
    import random as _random
    rnd = _random.Random(rnd_seed)
    s.fill(x0, 1, z0, x0 + width - 1, 1, z0 + depth - 1, floor)
    s.shell(x0, 2, z0, x0 + width - 1, height, z0 + depth - 1, wall)
    # windows on the long walls
    for x in range(x0 + 2, x0 + width - 2, 3):
        s.set(x, 3, z0, WINDOW)
        s.set(x, 3, z0 + depth - 1, WINDOW)
    # door
    s.fill(x0 + width // 2, 2, z0, x0 + width // 2, 3, z0, AIR)
    # interior light and furniture
    s.set(x0 + 1, height - 1, z0 + 1, LANTERN)
    s.set(x0 + width - 2, 2, z0 + depth - 2, CRATE_H)
    # roof
    for step in range(width // 2 + 1):
        y = height + 1 + step
        if ruined and rnd.random() < 0.55:
            continue
        s.fill(x0 + step, y, z0, x0 + width - 1 - step, y, z0 + depth - 1, roof_tile)
    if ruined:
        for _ in range(width * 2):
            x = x0 + rnd.randrange(width)
            z = z0 + rnd.randrange(depth)
            y = 2 + rnd.randrange(max(1, height - 2))
            s.set(x, y, z, AIR)
            if rnd.random() < 0.4:
                s.set(x, 1, z, INF_COBBLE)
        for _ in range(width):
            x = x0 + rnd.randrange(width)
            z = z0 + rnd.randrange(depth)
            s.set(x, 1, z, rnd.choice([BLOOD_ROOTS, VINES, INF_BRICKS]))


def town():
    """A small human town: a crossroads, four houses, lanterns and people who stayed."""
    s = Structure()
    import random as _random
    rnd = _random.Random(3417011)
    s.fill(-16, 0, -3, 16, 0, 3, GRAVEL)
    s.fill(-3, 0, -16, 3, 0, 16, GRAVEL)
    s.fill(-16, 0, -1, 16, 0, 1, CONCRETE)
    s.fill(-1, 0, -16, 1, 0, 16, CONCRETE)
    # four houses at the corners
    house(s, -15, -15, 9, 8, 4, PLANKS, COBBLE, STONE_BRICK)
    house(s, 7, -15, 9, 8, 4, PLANKS, COBBLE, STONE_BRICK)
    house(s, -15, 8, 8, 8, 4, PLANKS, COBBLE, STONE_BRICK)
    house(s, 8, 8, 8, 8, 4, LOG, PLANKS, SPRUCE)
    # a well in the middle and lanterns along the road
    s.shell(-2, 1, -2, 2, 2, 2, COBBLE)
    s.fill(-1, 1, -1, 1, 1, 1, WATER)
    s.fill(-2, 3, -2, 2, 3, 2, AIR)
    for x in range(-14, 15, 6):
        s.set(x, 1, 4, LANTERN)
        s.set(x, 1, -4, LANTERN)
    # market stalls and crates
    s.fill(5, 1, 5, 8, 1, 7, SPRUCE)
    s.set(6, 1, 5, CRATE_H)
    s.set(7, 1, 7, CRATE_H)
    s.spawn(-10, 1, 4, "wotw:human_soldier")
    s.spawn(9, 1, 4, "wotw:human_soldier")
    s.spawn(2, 1, 10, "minecraft:villager")
    s.spawn(-6, 1, 12, "minecraft:villager")
    s.spawn(12, 1, 12, "minecraft:villager")
    return s


def military_base():
    """A fortified human base: walls, a gate, a barracks, a watchtower and a tank on the apron."""
    s = Structure()
    # compound
    s.fill(-17, 0, -15, 17, 0, 15, SMOOTH)
    s.shell(-17, 1, -15, 17, 4, 15, STONE_BRICK)
    s.fill(-2, 1, -15, 2, 3, -15, AIR)                     # gate
    s.fill(-3, 4, -15, 3, 5, -15, STONE_BRICK)             # gate arch
    # walkway and embrasures
    for x in range(-16, 17, 3):
        s.set(x, 4, -14, LANTERN)
        s.set(x, 4, 14, LANTERN)

    # barracks
    house(s, -14, 2, 14, 9, 4, STONE_BRICK, PLANKS, SPRUCE)
    # armoury
    s.shell(6, 1, 3, 14, 4, 9, STONE_BRICK)
    s.fill(7, 1, 4, 13, 3, 8, AIR)
    for x in (7, 13):
        for z in (4, 8):
            s.fill(x, 1, z, x, 3, z, STONE_BRICK)
    s.set(10, 1, 5, CRATE_MIL)
    s.set(11, 1, 5, CRATE_MIL)
    s.set(10, 1, 7, CRATE_MIL)
    s.chest(12, 1, 7, "wotw:chests/complex")

    # watchtower
    for x in (-16, -12):
        for z in (-12, -8):
            s.fill(x, 1, z, x, 7, z, LOG)
    s.fill(-17, 8, -13, -11, 8, -7, SPRUCE)
    s.set(-15, 9, -11, LANTERN)
    s.fill(-16, 4, -12, -16, 6, -12, AIR)
    s.set(-16, 5, -12, LANTERN)

    # helicopter-less apron with a tank and sandbags
    s.fill(-8, 1, -12, 4, 1, -4, CONCRETE)
    s.spawn(-2, 1, -8, "wotw:human_tank")
    for x in range(-8, 5, 2):
        s.set(x, 1, -13, COBBLE)
        s.set(x, 2, -13, COBBLE)
    s.spawn(-10, 1, -2, "wotw:human_soldier")
    s.spawn(-4, 1, 0, "wotw:human_soldier")
    s.spawn(3, 1, -1, "wotw:human_soldier")
    s.spawn(10, 1, 2, "wotw:human_soldier")
    s.spawn(-15, 4, -10, "wotw:human_soldier")
    return s


def abandoned_base():
    """The same base after the Martians came through: breached walls, roots, no survivors."""
    s = Structure()
    import random as _random
    rnd = _random.Random(3417013)
    s.fill(-17, 0, -15, 17, 0, 15, SMOOTH)
    s.shell(-17, 1, -15, 17, 4, 15, STONE_BRICK)
    # blast holes in the perimeter
    for _ in range(7):
        x = rnd.choice((-17, 17))
        z = rnd.randrange(-14, 15)
        s.fill(x, 1, z, x, 3, z + 1, AIR)
    for _ in range(4):
        z = rnd.choice((-15, 15))
        x = rnd.randrange(-16, 17)
        s.fill(x, 1, z, x + 1, 3, z, AIR)
    s.fill(-2, 1, -15, 2, 3, -15, AIR)

    house(s, -14, 2, 14, 9, 4, STONE_BRICK, PLANKS, SPRUCE, ruined=True, rnd_seed=7)
    s.shell(6, 1, 3, 14, 4, 9, STONE_BRICK)
    s.fill(7, 1, 4, 13, 3, 8, AIR)
    for _ in range(10):
        s.set(rnd.randrange(7, 14), rnd.randrange(1, 4), rnd.randrange(4, 9), AIR)
    s.set(10, 1, 5, CRATE_MIL)
    s.set(11, 1, 5, INF_BRICKS)
    # wrecked tower
    for x in (-16, -12):
        for z in (-12, -8):
            s.fill(x, 1, z, x, rnd.randrange(3, 7), z, LOG)
    s.fill(-17, 5, -13, -11, 5, -7, SPRUCE)
    # the weeds took the apron
    for _ in range(60):
        x = rnd.randrange(-16, 17)
        z = rnd.randrange(-14, 15)
        s.set(x, 1, z, rnd.choice([BLOOD_ROOTS, VINES, BLOOD_ROOTS, INF_COBBLE]))
    s.fill(-8, 1, -12, 4, 1, -4, CONCRETE)
    s.set(-6, 1, -9, INF_COBBLE)
    s.set(-1, 1, -6, CRATE_MIL)
    s.spawn(-4, 1, -8, "wotw:human_tank")                  # the tank that never got out
    s.spawn(2, 1, 6, "wotw:martian")
    s.spawn(-9, 1, -1, "wotw:scout_tripod")
    s.spawn(8, 1, -6, "wotw:drone")
    return s


def ruined_houses():
    """A lane of three burnt out houses, the Martian weeds growing through the floors."""
    s = Structure()
    s.fill(-11, 0, -5, 11, 0, 5, GRAVEL)
    s.fill(-11, 0, -1, 11, 0, 1, CONCRETE)
    house(s, -11, -12, 8, 7, 4, COBBLE, PLANKS, SPRUCE, ruined=True, rnd_seed=21)
    house(s, -1, 4, 8, 8, 5, PLANKS, COBBLE, STONE_BRICK, ruined=True, rnd_seed=22)
    house(s, 3, -12, 7, 7, 4, LOG, PLANKS, SPRUCE, ruined=True, rnd_seed=23)
    for x, z in ((-3, 3), (0, -6), (5, 0), (-7, 0), (12, 3)):
        s.set(x, 1, z, VINES)
    s.spawn(-8, 1, -9, "wotw:martian")
    s.spawn(6, 1, 7, "wotw:martian_louse")
    s.spawn(0, 1, 2, "wotw:drone")
    return s


def road():
    """A stretch of the old highway: cracked concrete, gravel shoulders and a broken car."""
    s = Structure()
    import random as _random
    rnd = _random.Random(3417015)
    for z in range(-28, 29):
        s.fill(-5, 0, z, -4, 0, z, GRAVEL)
        s.fill(-3, 0, z, 3, 0, z, CONCRETE)
        s.fill(4, 0, z, 5, 0, z, GRAVEL)
        if rnd.random() < 0.18:
            s.set(rnd.randrange(-3, 4), 0, z, COBBLE)
    # markings
    for z in range(-28, 29, 4):
        s.set(0, 1, z, "minecraft:white_concrete")
    # a burnt out truck and a lamp post
    s.fill(2, 1, -6, 3, 2, -3, IRON)
    s.set(2, 3, -6, GRAVEL)
    s.fill(-4, 1, 8, -4, 4, 8, LOG)
    s.set(-4, 5, 8, LANTERN)
    s.set(3, 1, -10, CRATE_H)
    s.spawn(1, 1, 12, "wotw:human_soldier")
    return s


def port():
    """A harbour: a stone quay, a wooden jetty, a warehouse and a warship at anchor."""
    s = Structure()
    # sea bed and water around the quay
    s.fill(-16, -3, -10, 16, -3, 20, SAND)
    for z in range(-10, 21):
        for x in range(-16, 17):
            if x < -8 or z < 2:
                s.set(x, -2, z, WATER)
                s.set(x, -1, z, WATER)
    # quay
    s.fill(-16, 0, 2, 16, 0, 16, STONE_BRICK)
    s.fill(-16, -2, 2, 16, -1, 16, STONE_BRICK)
    s.fill(-16, 0, -8, -8, 0, 1, STONE_BRICK)
    s.fill(-16, -2, -8, -8, -1, 1, STONE_BRICK)
    # jetty on stilts
    for x in (-3, 3):
        for z in range(-8, 2):
            if z % 3 == 0:
                s.fill(x, -2, z, x, 0, z, LOG)
    s.fill(-4, 1, -8, 4, 1, 1, SPRUCE)
    # bollards
    for z in range(3, 16, 4):
        s.set(-6, 1, z, LOG)
        s.set(6, 1, z, LOG)
    # warehouse
    house(s, 8, 5, 8, 8, 5, STONE_BRICK, CONCRETE, SPRUCE)
    s.set(10, 1, 6, CRATE_H)
    s.set(13, 1, 6, CRATE_MIL)
    s.set(11, 1, 11, CRATE_H)
    # crane
    s.fill(-13, 1, 12, -13, 9, 12, IRON)
    s.fill(-13, 9, 12, -6, 9, 12, IRON)
    s.fill(-7, 8, 12, -7, 9, 12, IRON)
    # the warship at anchor
    s.spawn(1, 1, -5, "wotw:human_warship")
    s.spawn(12, 1, 8, "wotw:human_soldier")
    s.spawn(-10, 1, 8, "wotw:human_soldier")
    s.spawn(-4, 2, -3, "wotw:human_soldier")
    return s


# --------------------------------------------------------------------------- #
#  Martian installations
# --------------------------------------------------------------------------- #

def dome_lab():
    """A dome on the Martian frontier: glass shell, white alloy ring, a laboratory inside."""
    s = Structure()
    radius = 13
    # foundation
    for x in range(-radius - 2, radius + 3):
        for z in range(-radius - 2, radius + 3):
            if abs(x) + abs(z) <= (radius + 2) * 1.45:
                s.set(x, 0, z, WHITE_ALLOY)
    # the dome itself: hollow shell, glass above the base course
    for y in range(1, 13):
        shrink = int((y - 1) * 1.05)
        r = radius - shrink
        if r < 2:
            break
        for x in range(-r, r + 1):
            for z in range(-r, r + 1):
                if x * x + z * z > r * r:
                    continue
                edge = (x + 1) ** 2 + z * z > r * r or (x - 1) ** 2 + z * z > r * r                     or x * x + (z + 1) ** 2 > r * r or x * x + (z - 1) ** 2 > r * r
                if not edge:
                    continue
                s.set(x, y, z, WHITE_ALLOY if y <= 2 else GLASS)
    # the doorway
    s.fill(0, 1, -radius, 1, 3, -radius, AIR)
    # laboratory floor and consoles
    s.fill(-8, 1, -8, 8, 1, 8, WHITE_ALLOY)
    s.fill(-6, 1, -6, 6, 5, 6, AIR)
    for x, z in ((-5, -5), (5, -5), (-5, 5), (5, 5)):
        s.fill(x, 1, z, x, 4, z, HULL)
        s.set(x, 2, z, CONSOLE)
    s.fill(-2, 1, -6, 2, 1, -6, CONSOLE)
    s.fill(-2, 1, 6, 2, 1, 6, CONSOLE)
    s.set(0, 5, 0, CYLINDER)
    s.set(0, 4, 0, LAMP)
    # annex: the tripod workshop door and a turret outside
    s.fill(9, 1, -2, 12, 4, 2, HULL)
    s.fill(10, 1, -1, 11, 3, 1, AIR)
    s.set(13, 1, 0, TURRET)
    # weeds in the corners and the crew
    for x, z in ((-10, 9), (10, 9), (-10, -9), (10, -9), (3, 10), (-4, 11)):
        s.set(x, 1, z, BLOOD_ROOTS)
    s.spawn(0, 1, -3, "wotw:martian_master")
    s.spawn(-3, 1, 3, "wotw:martian")
    s.spawn(3, 1, 3, "wotw:martian")
    s.spawn(-9, 1, 0, "wotw:uberpod")
    s.spawn(9, 1, 0, "wotw:scout_tripod")
    return s


def root_garden():
    """The Martian greenhouse: a glass hall full of red root beds and blood vines."""
    s = Structure()
    s.fill(-11, 0, -7, 11, 0, 7, WHITE_ALLOY)
    s.shell(-11, 1, -7, 11, 6, 7, GLASS)
    s.fill(-10, 1, -6, 10, 5, 6, AIR)
    # alloy ribs
    for x in range(-10, 11, 4):
        s.fill(x, 1, -7, x, 6, -7, WHITE_ALLOY)
        s.fill(x, 1, 7, x, 6, 7, WHITE_ALLOY)
        s.fill(x, 7, -7, x, 7, 7, WHITE_ALLOY)
    # beds
    for bed in range(-8, 9, 5):
        s.fill(bed, 1, -4, bed + 2, 1, 4, DIRT_PATH)
        for x in range(bed, bed + 3):
            for z in range(-4, 5):
                s.set(x, 2, z, VINES if (x + z) % 3 else BLOOD_ROOTS)
    # watering machinery and a console
    for x in (-9, 9):
        s.set(x, 1, 0, CYLINDER)
        s.set(x, 2, 0, HULL)
    s.set(0, 1, -6, CONSOLE)
    s.set(0, 5, 0, LAMP)
    s.set(-6, 1, -6, CRATE_M)
    s.fill(-1, 1, 6, 1, 3, 6, AIR)
    s.spawn(0, 1, 0, "wotw:martian_master")
    s.spawn(-5, 1, 2, "wotw:martian")
    s.spawn(5, 1, -2, "wotw:martian")
    s.spawn(7, 1, 3, "wotw:martian_louse")
    return s


def tripod_garage():
    """A garage for walkers: a tall hangar, three bays, a workshop door and a turret on the roof."""
    s = Structure()
    s.fill(-13, 0, -11, 13, 0, 11, HULL)
    s.shell(-13, 1, -11, 13, 10, 11, HULL)
    s.fill(-12, 1, -10, 12, 9, 10, AIR)
    s.fill(-13, 1, 10, 13, 9, 10, AIR)                     # open front
    # roof beams and lamps
    for x in range(-12, 13, 4):
        s.fill(x, 9, -10, x, 9, 10, WHITE_ALLOY)
        s.set(x, 8, 0, LAMP)
    # bays
    for z in (-6, 0, 6):
        s.fill(-11, 1, z - 2, 11, 1, z + 2, ALLOY)
        s.set(-12, 2, z, CONSOLE)
        s.set(-12, 1, z - 1, CRATE_M)
    s.set(12, 1, 0, TURRET)
    s.set(0, 10, 0, CYLINDER)
    s.spawn(-6, 1, -6, "wotw:bombarding_machine")
    s.spawn(4, 1, 0, "wotw:ram_tripod")
    s.spawn(-2, 1, 6, "wotw:bunker_tripod")
    s.spawn(9, 1, -8, "wotw:martian")
    s.spawn(-10, 1, 8, "wotw:scout_tripod")
    return s


def tripod_workshop():
    """The repair shop: cranes, spare legs, consoles, crates and the blueprints of the fleet."""
    s = Structure()
    s.fill(-10, 0, -9, 10, 0, 9, WHITE_ALLOY)
    s.shell(-10, 1, -9, 10, 8, 9, HULL)
    s.fill(-9, 1, -8, 9, 7, 8, AIR)
    s.fill(-10, 1, 8, 10, 7, 8, AIR)
    # crane gantry
    for x in (-8, 8):
        s.fill(x, 1, -8, x, 7, -8, WHITE_ALLOY)
    s.fill(-8, 8, -8, 8, 8, -8, WHITE_ALLOY)
    s.fill(0, 4, -8, 0, 7, -8, WHITE_ALLOY)
    s.set(0, 3, -8, IRON)
    # workbenches, consoles and parts
    s.fill(-7, 1, 4, 7, 1, 6, ALLOY)
    s.set(-5, 2, 5, CONSOLE)
    s.set(0, 2, 5, CONSOLE)
    s.set(5, 2, 5, CONSOLE)
    s.set(-8, 1, -5, CRATE_M)
    s.set(8, 1, -5, CRATE_M)
    s.set(6, 1, 2, CRATE_M)
    s.chest(3, 1, 5, "wotw:chests/complex")
    s.set(0, 7, 0, LAMP)
    for x, z in ((-9, 6), (9, 6), (-4, -7)):
        s.set(x, 1, z, BLOOD_ROOTS)
    s.spawn(0, 1, 0, "wotw:ram_tripod")
    s.spawn(-5, 1, -3, "wotw:martian_master")
    s.spawn(4, 1, -2, "wotw:martian")
    s.spawn(7, 1, -6, "wotw:drone")
    return s


STRUCTURES_SPEC = {
    "landing_site": (landing_site(), 40, 14, 3417001, 2),
    "hatchery": (hatchery(), 52, 18, 3417002, 2),
    "spire": (spire(), 64, 22, 3417003, 1),
    "complex": (complex_site(), 90, 32, 3417004, 2),
    "dig_site": (dig_site(), 72, 26, 3417005, 1),
    "beacon": (beacon(), 84, 30, 3417006, 1),
    # the human side of the war
    "town": (town(), 96, 30, 3417011, 2),
    "military_base": (military_base(), 86, 26, 3417012, 2),
    "abandoned_base": (abandoned_base(), 92, 30, 3417013, 2),
    "ruined_houses": (ruined_houses(), 58, 16, 3417014, 1),
    "road": (road(), 26, 6, 3417015, 1),
    "port": (port(), 70, 22, 3417016, 2, "water"),
    # Martian installations
    "dome_lab": (dome_lab(), 110, 40, 3417017, 2),
    "root_garden": (root_garden(), 78, 24, 3417018, 1),
    "tripod_garage": (tripod_garage(), 104, 36, 3417019, 2),
    "tripod_workshop": (tripod_workshop(), 96, 30, 3417020, 1),
}


WATER_BIOMES = [
    "minecraft:ocean", "minecraft:deep_ocean", "minecraft:cold_ocean", "minecraft:deep_cold_ocean",
    "minecraft:warm_ocean", "minecraft:lukewarm_ocean", "minecraft:deep_lukewarm_ocean",
    "minecraft:river", "minecraft:beach", "minecraft:snowy_beach", "minecraft:stony_shore",
]


def build_worldgen(name, spacing, separation, salt, size, biomes=None):
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
    if biomes == "water":
        return structure, pool, structure_set, {"replace": False, "values": WATER_BIOMES}
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
    for name, spec in STRUCTURES_SPEC.items():
        structure, spacing, separation, salt, size = spec[:5]
        biomes = spec[5] if len(spec) > 5 else None
        write_nbt(os.path.join(STRUCTURES, f"{name}.nbt"), structure.to_nbt())
        print("structure", name, len(structure.blocks), "blocks,", len(structure.entities), "entities")

        json_spec, pool, structure_set, biome_tag = build_worldgen(name, spacing, separation, salt, size, biomes)
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
