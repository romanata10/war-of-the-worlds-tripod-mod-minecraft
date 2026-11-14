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
import math
import os
import re
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

    def walls(self, x0, y0, z0, x1, y1, z1, block):
        """Only the four side walls: the floor and the ceiling stay the caller's business.

        `shell` treats the bottom and the top layer as edges too, which turns a five block tall
        house into a solid slab with one walkable layer in the middle. Buildings want this instead.
        """
        for x in range(x0, x1 + 1):
            for y in range(y0, y1 + 1):
                for z in range(z0, z1 + 1):
                    if x in (x0, x1) or z in (z0, z1):
                        self.set(x, y, z, block)

    def crate(self, x, y, z, block, loot_table):
        """A crate that arrives with loot in it, the way a village chest does."""
        self.set(x, y, z, block, {"LootTable": loot_table})

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

def house(s, x0, z0, width, depth, height, wall, floor, roof_tile, ruined=False, rnd_seed=0,
          floor_y=0):
    """A small house you can actually walk into.

    `height` is the interior height in blocks (three or more), the floor is a single layer at
    `floor_y` and the walls are side walls only: the old version built a solid box, so the inside
    was one block tall and had two floors — the ground one and its own.

    Ruined houses are the same box with the roof partly missing and holes punched through the walls.
    """
    import random as _random
    rnd = _random.Random(rnd_seed)
    x1, z1 = x0 + width - 1, z0 + depth - 1
    top = floor_y + height

    s.fill(x0, floor_y, z0, x1, floor_y, z1, floor)               # the single floor layer
    s.walls(x0, floor_y + 1, z0, x1, top, z1, wall)               # four walls, no bottom, no lid
    s.fill(x0 + 1, floor_y + 1, z0 + 1, x1 - 1, top, z1 - 1, AIR)  # the room itself

    # a two block tall door in the middle of the front wall
    door_x = x0 + width // 2
    s.fill(door_x, floor_y + 1, z0, door_x, floor_y + 2, z0, AIR)
    # windows all around at eye height
    window_y = floor_y + 2
    for x in range(x0 + 2, x1 - 1, 3):
        s.set(x, window_y, z0, WINDOW)
        s.set(x, window_y, z1, WINDOW)
    for z in range(z0 + 2, z1 - 1, 3):
        s.set(x0, window_y, z, WINDOW)
        s.set(x1, window_y, z, WINDOW)

    # a lantern under the ceiling and a crate in the corner
    s.set(x0 + 1, top, z0 + 1, LANTERN)
    if not ruined:
        s.crate(x1 - 1, floor_y + 1, z1 - 1, CRATE_H, "wotw:crates/human")

    # gabled roof, shrinking row by row
    for step in range(width // 2 + 1):
        y = top + 1 + step
        if ruined and rnd.random() < 0.55:
            continue
        s.fill(x0 + step, y, z0, x1 - step, y, z1, roof_tile)

    if ruined:
        for _ in range(width * 2):
            x = x0 + rnd.randrange(width)
            z = z0 + rnd.randrange(depth)
            y = floor_y + 1 + rnd.randrange(max(1, height - 1))
            s.set(x, y, z, AIR)
            if rnd.random() < 0.4:
                s.set(x, floor_y, z, INF_COBBLE)
        for _ in range(width):
            x = x0 + rnd.randrange(width)
            z = z0 + rnd.randrange(depth)
            s.set(x, floor_y + 1, z, rnd.choice([BLOOD_ROOTS, VINES, INF_BRICKS]))


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
    s.crate(6, 1, 5, CRATE_H, "wotw:crates/human")
    s.crate(7, 1, 7, CRATE_H, "wotw:crates/human")
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
    # armoury: four walls, a roof, a door and a hollow room you can walk into
    s.fill(6, 0, 3, 14, 0, 9, SMOOTH)
    s.walls(6, 1, 3, 14, 4, 9, STONE_BRICK)
    s.fill(7, 1, 4, 13, 4, 8, AIR)
    s.fill(6, 5, 3, 14, 5, 9, STONE_BRICK)
    s.fill(10, 1, 3, 10, 2, 3, AIR)
    for x in (7, 13):
        for z in (4, 8):
            s.fill(x, 1, z, x, 3, z, STONE_BRICK)
    s.crate(10, 1, 5, CRATE_MIL, "wotw:crates/military")
    s.crate(11, 1, 5, CRATE_MIL, "wotw:crates/military")
    s.crate(10, 1, 7, CRATE_MIL, "wotw:crates/military")
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
    s.spawn(4, 4, -7, "wotw:human_helicopter")             # gunship on overwatch
    s.spawn(0, 26, -8, "wotw:human_bomber")                # the heavy stuff, high above
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
    s.fill(6, 0, 3, 14, 0, 9, SMOOTH)
    s.walls(6, 1, 3, 14, 4, 9, STONE_BRICK)
    s.fill(7, 1, 4, 13, 4, 8, AIR)
    s.fill(6, 5, 3, 14, 5, 9, STONE_BRICK)
    s.fill(10, 1, 3, 10, 2, 3, AIR)
    for _ in range(10):
        s.set(rnd.randrange(7, 14), rnd.randrange(1, 4), rnd.randrange(4, 9), AIR)
    s.crate(10, 1, 5, CRATE_MIL, "wotw:crates/military")
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
    s.crate(-1, 1, -6, CRATE_MIL, "wotw:crates/military")
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
    s.crate(3, 1, -10, CRATE_H, "wotw:crates/human")
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
    s.crate(10, 1, 6, CRATE_H, "wotw:crates/human")
    s.crate(13, 1, 6, CRATE_MIL, "wotw:crates/military")
    s.crate(11, 1, 11, CRATE_H, "wotw:crates/human")
    # crane
    s.fill(-13, 1, 12, -13, 9, 12, IRON)
    s.fill(-13, 9, 12, -6, 9, 12, IRON)
    s.fill(-7, 8, 12, -7, 9, 12, IRON)
    # the warship at anchor
    s.spawn(1, 1, -5, "wotw:human_warship")
    s.spawn(-12, -2, -6, "wotw:human_submarine")           # the boat under the quay
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
    s.crate(-6, 1, -6, CRATE_M, "wotw:crates/martian")
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
        s.crate(-12, 1, z - 1, CRATE_M, "wotw:crates/martian")
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
    s.crate(-8, 1, -5, CRATE_M, "wotw:crates/martian")
    s.crate(8, 1, -5, CRATE_M, "wotw:crates/martian")
    s.crate(6, 1, 2, CRATE_M, "wotw:crates/martian")
    s.chest(3, 1, 5, "wotw:chests/complex")
    s.set(0, 7, 0, LAMP)
    for x, z in ((-9, 6), (9, 6), (-4, -7)):
        s.set(x, 1, z, BLOOD_ROOTS)
    s.spawn(0, 1, 0, "wotw:ram_tripod")
    s.spawn(-5, 1, -3, "wotw:martian_master")
    s.spawn(4, 1, -2, "wotw:martian")
    s.spawn(7, 1, -6, "wotw:drone")
    return s


def signal_hill():
    """A Martian relay hill: a mound of dead shell and brass, a signal beacon on the crown, pylons,
    spore sacks, supply crates and the machines that answer the call."""
    s = Structure()
    # the mound: three stepped terraces of bone and alloy
    s.fill(-12, 0, -12, 12, 0, 12, "wotw:gorey_bone_block")
    s.fill(-10, 1, -10, 10, 1, 10, "wotw:gorey_bone_block")
    s.fill(-8, 2, -8, 8, 2, 8, ALLOY)
    s.fill(-6, 3, -6, 6, 3, 6, ALLOY)
    s.fill(-4, 4, -4, 4, 4, 4, "wotw:goble_brass_block")
    # a stair up the near side so the hill can be climbed
    for i in range(4):
        s.fill(-1, i + 1, 9 - i, 1, i + 1, 9 - i, "wotw:goble_brass_block")
    # the beacon itself
    s.set(0, 5, 0, "wotw:martian_signal_beacon")
    s.set(-1, 5, 0, HULL)
    s.set(1, 5, 0, HULL)
    s.set(0, 5, -1, HULL)
    s.set(0, 5, 1, HULL)
    for dx, dz in ((-2, -2), (2, -2), (-2, 2), (2, 2)):
        s.fill(dx, 5, dz, dx, 7, dz, "wotw:goble_brass_block")
        s.set(dx, 8, dz, LAMP)
    # pylons and a turret on the rim
    s.set(-5, 4, -5, TURRET)
    s.set(5, 4, 5, TURRET)
    for x, z in ((-8, 0), (8, 0), (0, -8), (0, 8)):
        s.fill(x, 3, z, x, 5, z, "wotw:goble_brass_block")
        s.set(x, 6, z, LAMP)
    # supplies and spore sacks
    s.crate(-4, 4, 3, CRATE_M, "wotw:crates/martian")
    s.crate(4, 4, 3, CRATE_M, "wotw:crates/martian")
    s.set(3, 4, -3, "wotw:spore_sack")
    s.set(-3, 4, -3, "wotw:spore_sack")
    s.set(-2, 1, 6, "wotw:spore_sack")
    s.set(2, 1, 6, "wotw:spore_sack")
    # the garrison
    s.spawn(0, 5, 3, "wotw:goble_tripod")
    s.spawn(-6, 2, -6, "wotw:cameron_tripod")
    s.spawn(6, 1, 6, "wotw:gorey_tripod")
    s.spawn(0, 1, 0, "wotw:martian")
    return s


def goble_yard():
    """The brass yard: a human workshop the Martians have taken over. Brass plates, a lattice crane,
    crates, ruined walls and the machines standing in the middle of it."""
    s = Structure()
    s.fill(-14, 0, -12, 14, 0, 12, GRAVEL)
    s.fill(-12, 1, -10, 12, 1, 10, "wotw:goble_brass_block")
    # ruined yard walls, one with a gate
    s.walls(-12, 1, -10, 12, 3, 10, STONE_BRICK)
    s.fill(-2, 1, 10, 2, 3, 10, AIR)                  # the gate
    for x in range(-12, 13, 4):
        s.set(x, 4, -10, COBBLE)
    # the workshop shed
    house(s, -6, -8, 10, 8, 5, STONE_BRICK, SMOOTH, SPRUCE)
    s.crate(-4, 1, -2, CRATE_MIL, "wotw:crates/military")
    s.crate(-1, 1, -2, CRATE_H, "wotw:crates/human")
    # lattice crane in the middle of the yard
    for dx, dz in ((-3, -3), (3, -3), (-3, 3), (3, 3)):
        s.fill(dx, 1, dz, dx, 7, dz, LOG)
    s.fill(-3, 8, -3, 3, 8, 3, "wotw:goble_brass_block")
    s.fill(-3, 9, -3, 3, 9, 3, LOG)
    s.fill(0, 8, 0, 0, 10, 0, IRON)
    s.set(0, 11, 0, LANTERN)
    s.crate(0, 1, 0, CRATE_MIL, "wotw:crates/military")
    # brass stock and spore sacks left by the occupiers
    s.fill(8, 1, -6, 10, 2, -4, "wotw:goble_brass_block")
    s.set(9, 3, -5, "wotw:spore_sack")
    s.set(-9, 1, 6, "wotw:spore_sack")
    # the occupiers and the crew that came to take it back
    s.spawn(0, 1, -6, "wotw:goble_tripod")
    s.spawn(6, 1, 4, "wotw:cameron_tripod")
    s.spawn(-8, 1, -4, "wotw:human_soldier")
    s.spawn(-6, 1, 4, "wotw:human_tank")
    s.spawn(9, 1, 8, "wotw:wayne_tripod")
    return s


def crash_site():
    """A capsule that came down badly: a crater of ash, the hull split open on its side, one engine
    torn out and the weed already coming up through the wreck."""
    s = Structure()
    ASH = "wotw:ashen_soil"
    # crater bowl of ash, deeper than the landing site
    s.fill(-9, 0, -9, 9, 0, 9, ASH)
    for x in range(-9, 10):
        for z in range(-9, 10):
            distance = max(abs(x), abs(z))
            if distance in (8, 9):
                s.set(x, 1, z, ASH if (x + z) % 2 else ALLOY)
    s.fill(-4, 0, -7, 4, 0, 7, AIR)
    s.fill(-3, 0, -6, 3, 0, 6, ASH)
    # the hull, lying on its side: a broken tube pointing east
    s.shell(-6, 1, -2, 2, 5, 2, HULL)
    s.fill(-5, 1, -1, 1, 4, 1, AIR)
    s.fill(0, 1, -1, 2, 4, 1, AIR)
    s.set(-6, 3, 0, AIR)                       # the rip the cargo came out of
    s.set(-6, 2, -1, AIR)
    s.set(2, 4, 0, GLASS)                      # one porthole still whole
    # the torn-off engine and a scatter of plates
    s.set(-8, 1, -3, CYLINDER)
    s.set(-8, 2, -3, CYLINDER)
    s.set(-9, 1, -2, ALLOY)
    for x, z in ((-3, 5), (4, -5), (6, 3), (-7, 4), (5, 6), (-2, -6)):
        s.set(x, 1, z, ALLOY if (x + z) % 2 else HULL)
    # the weed and the survivors
    for x, z in ((0, -4), (2, -5), (-4, 4), (-5, 5), (6, -2), (-6, -5), (3, 5)):
        s.set(x, 1, z, ROOT_BLOCK)
    s.set(4, 1, -3, "wotw:spore_sack")
    s.set(-5, 1, 3, "wotw:spore_sack")
    s.crate(-1, 1, -4, CRATE_M, "wotw:crates/martian")
    s.spawn(-2, 1, 0, "wotw:martian")
    s.spawn(0, 1, 3, "wotw:martian_louse")
    s.spawn(-4, 1, -3, "wotw:martian_louse")
    s.spawn(5, 1, -5, "wotw:mining_tripod")
    return s


def relay_tower():
    """A Martian relay mast: a pipe column braced with grating platforms, a gravity well humming on
    the crown and a shield pylon at the foot keeping the garrison alive."""
    s = Structure()
    s.fill(-7, 0, -7, 7, 0, 7, "wotw:hull_grating")
    s.fill(-6, 0, -6, 6, 0, 6, ALLOY)
    # the mast itself
    s.fill(-1, 1, -1, 1, 18, 1, "wotw:martian_pipe")
    for level in range(1, 19):
        if level % 4 == 0:
            s.fill(-3, level, -3, 3, level, 3, "wotw:hull_grating")
            for dx, dz in ((-3, -3), (3, -3), (-3, 3), (3, 3)):
                s.set(dx, level + 1, dz, LAMP)
    # the crown: gravity well and its caps
    s.fill(-2, 19, -2, 2, 19, 2, ALLOY)
    s.set(0, 20, 0, "wotw:gravity_well")
    for dx, dz in ((-2, -2), (2, -2), (-2, 2), (2, 2)):
        s.fill(dx, 20, dz, dx, 21, dz, "wotw:goble_brass_block")
    s.set(0, 22, 0, LAMP)
    # the base: a shield pylon, a console and stores
    s.set(-4, 1, 4, "wotw:shield_pylon")
    s.set(4, 1, 4, CONSOLE)
    s.set(4, 1, -4, "wotw:repair_pod")
    s.crate(-5, 1, -5, CRATE_M, "wotw:crates/martian")
    s.crate(5, 1, 5, CRATE_M, "wotw:crates/martian")
    s.set(-6, 1, 0, ROOT_BLOCK)
    s.set(6, 1, 0, ROOT_BLOCK)
    s.set(0, 1, 6, ROOT_BLOCK)
    # the garrison
    s.spawn(0, 1, 4, "wotw:storm_tripod")
    s.spawn(-4, 1, -2, "wotw:signal_scout_tripod")
    s.spawn(3, 1, -3, "wotw:blink_tripod")
    s.spawn(-2, 1, 2, "wotw:martian")
    return s


def refinery():
    """The Martian refinery: three squat tanks of pipe and alloy, a chimney, an ore heap of chitin and
    a repair pod working flat out on the machines that guard it."""
    s = Structure()
    CHITIN = "wotw:chitin_block"
    PIPE = "wotw:martian_pipe"
    GRATE = "wotw:hull_grating"
    s.fill(-10, 0, -9, 10, 0, 9, ALLOY)
    s.fill(-9, 0, -8, 9, 0, 8, GRATE)
    # three tanks
    for index, (cx, cz) in enumerate(((-6, -4), (0, 3), (6, -4))):
        s.fill(cx - 2, 1, cz - 2, cx + 2, 6, cz + 2, HULL)
        s.fill(cx - 1, 1, cz - 1, cx + 1, 6, cz + 1, AIR)
        s.fill(cx - 2, 7, cz - 2, cx + 2, 7, cz + 2, "wotw:goble_brass_block")
        s.set(cx, 8, cz, CYLINDER)
        s.set(cx, 1, cz - 3, PIPE)
        s.set(cx, 2, cz - 3, PIPE)
    # pipe runs between the tanks
    for x in range(-6, 7):
        s.set(x, 5, 0, PIPE)
    s.set(0, 6, 0, PIPE)
    # the chimney and its furnace mouth
    s.fill(-1, 1, -8, 1, 9, -8, HULL)
    s.set(0, 1, -8, "wotw:martian_lamp")
    s.set(0, 10, -8, LAMP)
    # ore heap of chitin and stores
    s.fill(3, 1, 4, 7, 1, 7, CHITIN)
    s.fill(4, 2, 5, 6, 2, 6, CHITIN)
    s.set(5, 3, 5, CHITIN)
    s.set(-6, 1, 6, "wotw:repair_pod")
    s.set(6, 1, 6, "wotw:martian_mortar")
    s.crate(-8, 1, -6, CRATE_M, "wotw:crates/martian")
    s.crate(8, 1, -6, CRATE_M, "wotw:crates/martian")
    for x, z in ((-9, 3), (9, 3), (-9, -3), (9, -3)):
        s.set(x, 1, z, ROOT_BLOCK)
    # the crew
    s.spawn(0, 2, 0, "wotw:factory_tripod")
    s.spawn(-6, 2, 2, "wotw:mining_tripod")
    s.spawn(6, 1, 0, "wotw:harvester_tripod")
    s.spawn(0, 1, 6, "wotw:martian")
    return s


def watch_post():
    """A human observation post: a concrete platform ringed with a railing, a dug-in command car, a
    field gun under a camouflage net and the crew that watches the Martian lines."""
    s = Structure()
    s.fill(-7, 0, -7, 7, 0, 7, CONCRETE)
    s.fill(-6, 0, -6, 6, 0, 6, SMOOTH)
    # the sandbag ring, one gap to walk through
    for x in range(-6, 7):
        for z in range(-6, 7):
            if max(abs(x), abs(z)) == 6 and not (z == 6 and abs(x) <= 1):
                s.set(x, 1, z, COBBLE)
    # the mast with a lantern and a radio
    s.fill(4, 1, -4, 4, 6, -4, IRON)
    s.set(4, 7, -4, LANTERN)
    s.set(3, 1, -4, CONSOLE)
    # the bunker: three walls and a roof
    s.walls(-4, 1, -4, 2, 3, 1, STONE_BRICK)
    s.fill(-4, 4, -4, 2, 4, 1, STONE_BRICK)
    s.fill(-1, 1, 1, 0, 2, 1, AIR)               # doorway
    s.set(-3, 1, -3, LANTERN)
    s.set(1, 1, -3, CRATE_H)
    # the field gun and its ammunition under a net
    s.set(4, 1, 3, CONCRETE)
    s.set(5, 1, 4, CRATE_MIL)
    s.set(3, 1, 5, CRATE_MIL)
    # a grating walkway to the road
    for z in range(7, 12):
        s.fill(-1, 0, z, 1, 0, z, "wotw:hull_grating")
    # the crew
    s.spawn(0, 1, -2, "wotw:human_soldier")
    s.spawn(-2, 1, -1, "wotw:human_soldier")
    s.spawn(-3, 1, 4, "wotw:human_sniper")
    s.spawn(4, 1, 5, "wotw:human_medic")
    s.spawn(5, 1, 3, "wotw:human_apc")
    s.spawn(-5, 1, 4, "wotw:human_artillery")
    return s


def frost_wreck():
    """A wreck of a frost machine frozen into the ground: the spine of a tripod under a sheet of ice,
    frozen hull plates, snow and a repair pod that never finished its work."""
    s = Structure()
    FROZEN = "wotw:frozen_hull"
    ICE = "minecraft:ice"
    SNOW = "minecraft:snow_block"
    PACKED = "minecraft:packed_ice"
    s.fill(-8, 0, -8, 8, 0, 8, PACKED)
    for x in range(-8, 9):
        for z in range(-8, 9):
            distance = max(abs(x), abs(z))
            if distance >= 7:
                s.set(x, 1, z, SNOW if (x + z) % 3 else ICE)
            elif distance >= 5 and (x * z) % 5 == 0:
                s.set(x, 1, z, ICE)
    # the fallen spine: a long frozen hull beam with ribs
    s.fill(-5, 1, -1, 4, 2, 1, FROZEN)
    for x in range(-5, 5, 2):
        s.fill(x, 1, -2, x, 3, 2, FROZEN)
        s.set(x, 4, 0, HULL)
    # the head, half buried
    s.fill(5, 1, -2, 7, 3, 2, FROZEN)
    s.set(7, 2, 0, GLASS)
    # a leg snapped off and sticking out of the ice
    s.fill(-6, 1, 3, -3, 1, 4, FROZEN)
    s.set(-6, 2, 4, HULL)
    s.set(-6, 3, 4, HULL)
    # what is left of the repair pod and the crates
    s.set(-7, 1, -5, "wotw:repair_pod")
    s.crate(3, 1, -5, CRATE_M, "wotw:crates/martian")
    s.set(4, 1, 5, "wotw:spore_sack")
    s.set(-2, 1, 5, "wotw:spore_sack")
    s.set(0, 1, 6, ROOT_BLOCK)
    s.set(-4, 1, -6, ROOT_BLOCK)
    # the machines that never left
    s.spawn(0, 2, 0, "wotw:frost_tripod")
    s.spawn(-4, 1, -3, "wotw:frost_tripod")
    s.spawn(5, 1, 4, "wotw:cage_tripod")
    s.spawn(-6, 1, 6, "wotw:martian")
    return s

def martian_city():
    """The city of the Martians: a walled district of dark plate, lens-lit streets and capsule racks
    where the fleet is docked. The strider soldiers walk the walls."""
    s = Structure()
    PLATE = "wotw:city_plate"
    LENS = "wotw:lens_pillar"
    RACK = "wotw:capsule_rack"
    VENT = "wotw:spore_vent"
    GRATE = "wotw:hull_grating"
    HULL = "wotw:martian_hull"
    ALLOY = "wotw:martian_alloy_block"

    # the district floor
    s.fill(-54, 0, -54, 54, 0, 54, PLATE)
    s.fill(-50, 0, -50, 50, 0, 50, GRATE)

    # the curtain wall: four sides, three blocks thick, fourteen high, with a gate in the middle
    for side in (-1, 1):
        z = 52 * side
        s.fill(-54, 1, z - 1, 54, 14, z + 1, PLATE)
        s.fill(-6, 1, z - 1, 6, 10, z + 1, AIR)
        s.fill(-6, 10, z - 1, 6, 10, z + 1, PLATE)
        x = 52 * side
        s.fill(x - 1, 1, -54, x + 1, 14, 54, PLATE)
        s.fill(x - 1, 1, -6, x + 1, 10, 6, AIR)
        s.fill(x - 1, 10, -6, x + 1, 10, 6, PLATE)
    # crenellations and the four corner towers
    for x in range(-54, 55, 6):
        for z in (-53, 53):
            s.set(x, 15, z, PLATE)
        for z in range(-54, 55, 6):
            s.set(-53, 15, z, PLATE)
            s.set(53, 15, z, PLATE)
    for cx in (-48, 48):
        for cz in (-48, 48):
            s.fill(cx - 5, 1, cz - 5, cx + 5, 20, cz + 5, PLATE)
            s.fill(cx - 4, 1, cz - 4, cx + 4, 20, cz + 4, AIR)
            s.fill(cx - 5, 21, cz - 5, cx + 5, 21, cz + 5, ALLOY)
            s.set(cx, 22, cz, LENS)
            s.set(cx - 3, 22, cz, LENS)
            s.set(cx + 3, 22, cz, LENS)

    # avenues from every gate to the plaza
    s.fill(-3, 1, -52, 3, 1, 52, HULL)
    s.fill(-52, 1, -3, 52, 1, 3, HULL)
    for x in range(-50, 51, 10):
        s.set(x, 2, 4, LENS)
        s.set(x, 2, -4, LENS)
    for z in range(-50, 51, 10):
        s.set(4, 2, z, LENS)
        s.set(-4, 2, z, LENS)

    # the spire in the middle of the plaza
    s.fill(-12, 1, -12, 12, 1, 12, ALLOY)
    s.fill(-6, 2, -6, 6, 34, 6, PLATE)
    for level in range(4, 34, 6):
        s.fill(-8, level, -8, 8, level, 8, ALLOY)
        for dx, dz in ((-8, -8), (8, -8), (-8, 8), (8, 8)):
            s.set(dx, level + 1, dz, LENS)
    s.fill(-9, 35, -9, 9, 35, 9, ALLOY)
    s.set(0, 36, 0, LENS)
    s.set(0, 37, 0, "wotw:gravity_well")
    s.set(-20, 1, 0, "wotw:martian_mortar")
    s.set(20, 1, 0, "wotw:shield_pylon")

    # capsule racks along the plaza edge: the fleet is docked here
    for x in (-30, 30):
        for z in (-16, -8, 8, 16):
            s.fill(x - 4, 1, z - 2, x + 4, 8, z + 2, RACK)
            s.fill(x - 2, 1, z - 1, x + 2, 7, z + 1, AIR)
            s.set(x, 2, z, VENT)
    # districts: workshops of plate with grated doors
    for cx, cz in ((-34, -34), (34, -34), (-34, 34), (34, 34)):
        s.fill(cx - 9, 1, cz - 9, cx + 9, 9, cz + 9, PLATE)
        s.fill(cx - 8, 1, cz - 8, cx + 8, 9, cz + 8, AIR)
        s.fill(cx - 9, 10, cz - 9, cx + 9, 10, cz + 9, ALLOY)
        s.fill(cx - 2, 1, cz + 9, cx + 2, 4, cz + 9, AIR)
        s.set(cx, 5, cz, LENS)
        s.set(cx - 6, 1, cz - 6, RACK)
        s.set(cx + 6, 1, cz + 6, VENT)
        s.crate(cx - 3, 1, cz + 3, CRATE_M, "wotw:crates/martian")
        s.set(cx + 4, 1, cz - 4, ROOT_BLOCK)

    # the weed in the gutters, the vents in the floor
    for x in range(-48, 49, 12):
        for z in range(-48, 49, 12):
            if abs(x) < 8 or abs(z) < 8:
                continue
            s.set(x, 1, z, ROOT_BLOCK if (x + z) % 3 else VENT)
    s.set(-16, 1, -16, "wotw:repair_pod")
    s.set(16, 1, 16, "wotw:repair_pod")

    # the garrison: striders on the walls, a cylops in the plaza
    s.spawn(0, 2, 20, "wotw:strider_tripod")
    s.spawn(0, 2, -20, "wotw:strider_tripod")
    s.spawn(-20, 2, 0, "wotw:strider_tripod")
    s.spawn(20, 2, 0, "wotw:strider_tripod")
    s.spawn(0, 3, 0, "wotw:cyclops_tripod")
    s.spawn(-48, 22, -48, "wotw:saucer_tripod")
    s.spawn(48, 22, 48, "wotw:prism_tripod")
    s.spawn(-30, 2, 10, "wotw:tripod_2005")
    s.spawn(30, 2, -10, "wotw:saucer_tripod")
    s.spawn(-10, 2, -30, "wotw:strider_tripod")
    s.spawn(10, 2, 30, "wotw:martian")
    s.spawn(0, 2, -30, "wotw:martian")
    return s


def foundry_yard():
    """The foundry of the lava machine: open channels of molten rock, slag heaps of chitin and a
    casting floor where the hull plates are poured."""
    s = Structure()
    LAVA = "wotw:lava_channel"
    PLATE = "wotw:city_plate"
    CHITIN = "wotw:chitin_block"
    ASH = "wotw:ashen_soil"

    s.fill(-18, 0, -16, 18, 0, 16, ASH)
    s.fill(-16, 0, -14, 16, 0, 14, PLATE)
    # the channels: two troughs of lava crossing the floor
    s.fill(-16, 0, -2, 16, 0, 2, LAVA)
    s.fill(-2, 0, -14, 2, 0, 14, LAVA)
    for x in range(-16, 17, 4):
        for z in (-2, 2):
            s.set(x, 1, z, PLATE)
    for z in range(-14, 15, 4):
        for x in (-2, 2):
            s.set(x, 1, z, PLATE)
    # the casting floor and the ladles
    for cx, cz in ((-11, -10), (11, -10), (-11, 10), (11, 10)):
        s.fill(cx - 3, 1, cz - 3, cx + 3, 1, cz + 3, CHITIN)
        s.fill(cx - 1, 2, cz - 1, cx + 1, 6, cz + 1, PLATE)
        s.set(cx, 7, cz, "wotw:lens_pillar")
        s.set(cx + 4, 1, cz, "wotw:martian_pipe")
        s.set(cx + 4, 2, cz, "wotw:martian_pipe")
    # the chimney stack
    s.fill(-1, 1, -13, 1, 16, -13, PLATE)
    s.fill(-2, 17, -14, 2, 18, -12, PLATE)
    s.set(0, 1, -12, "wotw:martian_mortar")
    # slag heaps and stores
    s.fill(8, 1, -8, 14, 1, -4, CHITIN)
    s.fill(9, 2, -7, 13, 2, -5, CHITIN)
    s.fill(-14, 1, 6, -8, 1, 10, ASH)
    s.set(-14, 2, 8, "wotw:repair_pod")
    s.set(14, 1, 8, "wotw:shield_pylon")
    s.crate(-12, 1, -12, CRATE_M, "wotw:crates/martian")
    s.crate(12, 1, 12, CRATE_M, "wotw:crates/martian")
    s.set(-16, 1, 0, ROOT_BLOCK)
    s.set(16, 1, 0, ROOT_BLOCK)
    # the crew
    s.spawn(0, 2, 8, "wotw:lava_tripod")
    s.spawn(-8, 2, -8, "wotw:lava_tripod")
    s.spawn(10, 1, 10, "wotw:harvester_tripod")
    s.spawn(-10, 1, -10, "wotw:martian")
    return s


def brood_nest():
    """The nest of the living machine: walls of stacked rib, membranes stretched over the cells and
    vents breathing spores into the dark."""
    s = Structure()
    BONE = "wotw:bone_wall"
    MEMBRANE = "wotw:membrane"
    VENT = "wotw:spore_vent"
    ASH = "wotw:ashen_soil"

    s.fill(-16, 0, -16, 16, 0, 16, ASH)
    s.fill(-14, 0, -14, 14, 0, 14, "wotw:chitin_block")
    # the rib walls: a ring, thick and low, with gaps
    s.fill(-14, 1, -14, 14, 8, -12, BONE)
    s.fill(-14, 1, 12, 14, 8, 14, BONE)
    s.fill(-14, 1, -14, -12, 8, 14, BONE)
    s.fill(12, 1, -14, 14, 8, 14, BONE)
    s.fill(-2, 1, -14, 2, 5, -12, AIR)
    s.fill(-2, 1, 12, 2, 5, 14, AIR)
    for x in range(-14, 15, 4):
        s.set(x, 9, -13, BONE)
        s.set(x, 9, 13, BONE)
    # the cells: membranes over hollows
    for cx, cz in ((-8, -7), (8, -7), (-8, 7), (8, 7)):
        s.fill(cx - 3, 1, cz - 3, cx + 3, 3, cz + 3, BONE)
        s.fill(cx - 2, 1, cz - 2, cx + 2, 3, cz + 2, MEMBRANE)
        s.set(cx, 4, cz, VENT)
    # the brood mound in the middle
    s.fill(-4, 1, -4, 4, 3, 4, "wotw:chitin_block")
    s.fill(-3, 4, -3, 3, 5, 3, MEMBRANE)
    s.set(0, 6, 0, VENT)
    s.set(0, 7, 0, "wotw:spore_sack")
    # the weed that feeds it
    for x, z in ((-11, 0), (11, 0), (0, -10), (0, 10), (-11, -11), (11, 11), (-11, 11), (11, -11)):
        s.set(x, 1, z, ROOT_BLOCK)
    s.set(-13, 1, 0, "wotw:repair_pod")
    s.crate(13, 1, 0, CRATE_M, "wotw:crates/martian")
    # the machine and its brood
    s.spawn(0, 4, 0, "wotw:living_tripod")
    s.spawn(-9, 1, 0, "wotw:living_tripod")
    s.spawn(4, 1, 8, "wotw:martian_louse")
    s.spawn(-4, 1, 8, "wotw:martian_louse")
    s.spawn(4, 1, -8, "wotw:martian_louse")
    s.spawn(-4, 1, -8, "wotw:martian")
    return s


def chain_court():
    """The court of the pincer machine: a grated floor with chains strung from post to post, cages of
    prisoners nobody came back for, and the prism machines that stand guard over it."""
    s = Structure()
    GRATE = "wotw:chain_grating"
    PLATE = "wotw:city_plate"
    LENS = "wotw:lens_pillar"
    RACK = "wotw:capsule_rack"

    s.fill(-16, 0, -16, 16, 0, 16, GRATE)
    s.fill(-14, 0, -14, 14, 0, 14, PLATE)
    # posts with chains strung between them
    for cx, cz in ((-12, -12), (12, -12), (-12, 12), (12, 12)):
        s.fill(cx - 1, 1, cz - 1, cx + 1, 10, cz + 1, PLATE)
        s.set(cx, 11, cz, LENS)
    for x in range(-11, 12):
        s.set(x, 9, -12, "wotw:martian_pipe")
        s.set(x, 9, 12, "wotw:martian_pipe")
    for z in range(-11, 12):
        s.set(-12, 9, z, "wotw:martian_pipe")
        s.set(12, 9, z, "wotw:martian_pipe")
    # the cages: taken prisoners, kept for later
    for cx, cz in ((-7, -3), (7, 3)):
        s.fill(cx - 2, 1, cz - 2, cx + 2, 4, cz + 2, "minecraft:iron_bars")
        s.fill(cx - 1, 1, cz - 1, cx + 1, 3, cz + 1, AIR)
    # the dais of the prisms
    s.fill(-4, 1, -4, 4, 1, 4, RACK)
    s.fill(-3, 2, -3, 3, 2, 3, LENS)
    for dx, dz in ((-3, -3), (3, -3), (-3, 3), (3, 3)):
        s.set(dx, 3, dz, "wotw:spore_sack")
    s.set(-13, 1, -13, "wotw:repair_pod")
    s.set(13, 1, 13, "wotw:shield_pylon")
    s.crate(-13, 1, 13, CRATE_M, "wotw:crates/martian")
    s.crate(13, 1, -13, CRATE_M, "wotw:crates/martian")
    s.set(-15, 1, 0, ROOT_BLOCK)
    s.set(15, 1, 0, ROOT_BLOCK)
    # the garrison
    s.spawn(0, 3, 0, "wotw:prism_tripod")
    s.spawn(-8, 1, 8, "wotw:prism_tripod")
    s.spawn(8, 1, -8, "wotw:saucer_tripod")
    s.spawn(0, 1, 12, "wotw:martian")
    return s


def lens_observatory():
    """The observatory of the cyclops: a ring of lens pillars watching the sky, a stone table of
    charts and the one-eyed machine that never stops staring."""
    s = Structure()
    LENS = "wotw:lens_pillar"
    PLATE = "wotw:city_plate"
    ALLOY = "wotw:martian_alloy_block"
    OBSIDIAN = "minecraft:obsidian"

    s.fill(-15, 0, -15, 15, 0, 15, OBSIDIAN)
    s.fill(-13, 0, -13, 13, 0, 13, PLATE)
    # the ring of lens pillars
    for index in range(12):
        angle = 6.283185307179586 * index / 12.0
        x = int(round(11 * math.cos(angle)))
        z = int(round(11 * math.sin(angle)))
        s.fill(x, 1, z, x, 6, z, LENS)
        s.set(x, 7, z, ALLOY)
    # the chart table
    s.fill(-4, 1, -4, 4, 1, 4, ALLOY)
    s.fill(-3, 2, -3, 3, 2, 3, LENS)
    s.set(0, 3, 0, "wotw:gravity_well")
    s.set(-6, 1, 6, "wotw:martian_console")
    s.set(6, 1, 6, "wotw:martian_console")
    # observation platforms on the rim
    for dx, dz in ((-13, 0), (13, 0), (0, -13), (0, 13)):
        s.set(dx, 1, dz, "wotw:lens_pillar")
        s.set(dx, 2, dz, "wotw:lens_pillar")
    s.set(-14, 1, -14, "wotw:repair_pod")
    s.set(14, 1, 14, "wotw:shield_pylon")
    s.crate(-14, 1, 14, CRATE_M, "wotw:crates/martian")
    s.crate(14, 1, -14, CRATE_M, "wotw:crates/martian")
    for x, z in ((-8, -8), (8, -8), (-8, 8), (8, 8)):
        s.set(x, 1, z, ROOT_BLOCK)
    # the watchers
    s.spawn(0, 2, 6, "wotw:cyclops_tripod")
    s.spawn(0, 2, -6, "wotw:cyclops_tripod")
    s.spawn(-9, 1, 0, "wotw:sketch_tripod")
    s.spawn(9, 1, 0, "wotw:martian")
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
    # the sixth wave installations
    "signal_hill": (signal_hill(), 96, 30, 3417021, 1),
    "goble_yard": (goble_yard(), 88, 28, 3417022, 2),
    # the ninth wave installations
    "crash_site": (crash_site(), 62, 20, 3417023, 2),
    "relay_tower": (relay_tower(), 94, 30, 3417024, 1),
    "refinery": (refinery(), 100, 32, 3417025, 2),
    "watch_post": (watch_post(), 56, 18, 3417026, 1),
    "frost_wreck": (frost_wreck(), 74, 24, 3417027, 1),
    # the tenth wave: the city and its outskirts
    "martian_city": (martian_city(), 260, 70, 3417028, 1),
    "foundry_yard": (foundry_yard(), 86, 26, 3417029, 2),
    "brood_nest": (brood_nest(), 70, 22, 3417030, 2),
    "chain_court": (chain_court(), 76, 24, 3417031, 2),
    "lens_observatory": (lens_observatory(), 80, 24, 3417032, 1),
}


WATER_BIOMES = [
    "minecraft:ocean", "minecraft:deep_ocean", "minecraft:cold_ocean", "minecraft:deep_cold_ocean",
    "minecraft:warm_ocean", "minecraft:lukewarm_ocean", "minecraft:deep_lukewarm_ocean",
    "minecraft:river", "minecraft:beach", "minecraft:snowy_beach", "minecraft:stony_shore",
]


def build_worldgen(name, spacing, separation, salt, size, biomes=None, max_distance=24):
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
        "max_distance_from_center": max_distance,
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


def known_blocks():
    """Every block id the mod registers, read straight from ModBlocks.java."""
    source = os.path.join(ROOT, "src/main/java/dev/arena/wotw/registry/ModBlocks.java")
    with open(source, encoding="utf-8") as handle:
        return {"wotw:" + name for name in re.findall(r'BLOCKS\.register\("([a-z0-9_]+)"', handle.read())}


def known_entities():
    """Every entity id the mod registers, read straight from ModEntities.java."""
    source = os.path.join(ROOT, "src/main/java/dev/arena/wotw/registry/ModEntities.java")
    with open(source, encoding="utf-8") as handle:
        return {"wotw:" + name for name in re.findall(r'ENTITY_TYPES\.register\("([a-z0-9_]+)"', handle.read())}


def main():
    os.makedirs(STRUCTURES, exist_ok=True)
    registered = known_blocks()
    entities = known_entities()
    problems = []
    for name, spec in STRUCTURES_SPEC.items():
        structure, spacing, separation, salt, size = spec[:5]
        biomes = spec[5] if len(spec) > 5 else None
        for block, _ in structure.blocks.values():
            if block.startswith("wotw:") and block not in registered:
                problems.append(f"{name}: unknown block {block}")
        for entity in structure.entities:
            if entity["id"].startswith("wotw:") and entity["id"] not in entities:
                problems.append(f"{name}: unknown entity {entity['id']}")
        write_nbt(os.path.join(STRUCTURES, f"{name}.nbt"), structure.to_nbt())
        print("structure", name, len(structure.blocks), "blocks,", len(structure.entities), "entities")

        xs = [position[0] for position in structure.blocks]
        zs = [position[2] for position in structure.blocks]
        span = max(max(xs) - min(xs), max(zs) - min(zs)) if xs else 0
        json_spec, pool, structure_set, biome_tag = build_worldgen(
            name, spacing, separation, salt, size, biomes, max_distance=max(24, span // 2 + 8))
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
    if problems:
        print(f"{len(problems)} problem(s):")
        for problem in problems:
            print(" -", problem)
        raise SystemExit(1)


if __name__ == "__main__":
    main()
