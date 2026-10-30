#!/usr/bin/env python3
"""Generates the Minecraft model code for every machine and Martian.

Geometry lives here (in model pixels, 16 per block, +y downwards, feet at y = 24), and two
outputs are produced from it:

    src/main/java/dev/arena/wotw/client/model/TripodModels.java   (10 machines)
    src/main/java/dev/arena/wotw/client/model/MartianModels.java  (2 Martians)

`tools/preview_models.py` renders the very same objects, so the preview cannot drift away from
what the game shows.

Design notes
------------
* The machines are built from chunky, panelled cuboids (`shell()` splits big volumes into
  plate-sized boxes), which is how the references read in a blocky world.
* Legs are two segments with a knee: the thigh splays out, the shin comes back under the body.
* Lenses and eyes sample the bright patch of the texture at (96, 96) and are drawn with
  `RenderLayer.getEyes`, so they glow in the dark.

Usage:  python3 tools/generate_models.py
"""

import os

from model_lib import Model, drop_to_ground, emit_java, eye, leg, stretch_vertical, tentacle

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
MODEL_DIR = os.path.join(ROOT, "src/main/java/dev/arena/wotw/client/model")


# --------------------------------------------------------------------------- #
#  1. 2005 Steven Spielberg tripod — the iconic one, 8 blocks tall
# --------------------------------------------------------------------------- #

def tripod_2005():
    m = Model("tripod_2005")
    body = m.part("body")
    # the big cobra-hood head: wide at the jaw, narrow at the crown
    body.dome(0, -104, 0, 34, 40, [(12, 28, 32), (16, 38, 44), (16, 44, 50), (12, 34, 40), (6, 24, 28)])
    # brow plate that shades the two sensor eyes
    body.shell(0, -94, -18, 46, 7, 20, cell=16)
    eye(m, "eye_l", "body", (-11, -84, -24), (11, 8))
    eye(m, "eye_r", "body", (11, -84, -24), (11, 8))
    # underslung jaw + collar
    body.shell(0, -48, 0, 22, 8, 26, cell=12)

    for index, (x, z, droop, spread) in enumerate([(-12, -8, 16, -6), (12, -8, 16, 6),
                                                   (-9, 9, 22, -10), (9, 9, 22, 10)]):
        tentacle(m, f"tent{index}_", "body", (x, -44, z), 34, segments=3, droop=droop, spread=spread)

    leg(m, "leg_fl", (-16, -46, -12), 36, 33, 4, splay_x=-14, splay_z=-10, foot=5)
    leg(m, "leg_fr", (16, -46, -12), 36, 33, 4, splay_x=14, splay_z=-10, foot=5)
    leg(m, "leg_b", (0, -46, 18), 37, 34, 4, splay_z=12, foot=5)
    return m


# --------------------------------------------------------------------------- #
#  2 + 3. BBC "The Tripods" — white collector and red laser variant, 6.5 blocks
# --------------------------------------------------------------------------- #

def bbc_white():
    m = Model("bbc_white_tripod")
    body = m.part("body")
    body.dome(0, -80, 0, 22, 24, [(10, 20, 22), (12, 28, 30), (10, 24, 26), (6, 16, 18)])
    body.shell(0, -84, 0, 10, 5, 10, cell=8)          # antenna knob
    body.shell(0, -46, 0, 18, 6, 20, cell=10)         # belly
    eye(m, "eye", "body", (0, -66, -16), (9, 9))
    # the collector: a cage of thin arms under the belly
    for index, (x, z) in enumerate([(-9, -6), (9, -6), (0, 9)]):
        arm = m.part(f"arm{index}", parent="body", pivot=(x, -44, z), rot=(6.0, 0.0, x * 0.9))
        arm.box(-1, 0, -1, 3, 16, 3)
        arm.box(-1, 15, -1, 3, 6, 3)

    leg(m, "leg_l", (-10, -44, 0), 35, 34, 3, splay_x=-12, foot=4)
    leg(m, "leg_r", (10, -44, 0), 35, 34, 3, splay_x=12, foot=4)
    leg(m, "leg_b", (0, -44, 10), 35, 34, 3, splay_z=9, foot=4)
    return m


def bbc_red():
    m = bbc_white()
    m.name = "bbc_red_tripod"
    # laser cannon slung under the head, pointing forward
    cannon = m.part("cannon", parent="body", pivot=(0, -58, -14))
    cannon.box(-2, -2, -22, 5, 5, 24)
    cannon.box(-3, -3, -26, 7, 7, 4)
    cannon.glow_box(-2, -2, -27, 4, 4, 1)
    return m


# --------------------------------------------------------------------------- #
#  4. 1953 George Pal flying manta — a 7 block wingspan flying wing
# --------------------------------------------------------------------------- #

def manta():
    m = Model("flying_manta")
    hull = m.part("body")
    hull.shell(0, -20, 0, 34, 18, 62, cell=18)
    hull.shell(0, -30, -6, 24, 10, 34, cell=14)
    # swept wings, built as three tapering plates per side
    for side, name in ((-1, "wing_l"), (1, "wing_r")):
        wing = m.part(name, parent="body", pivot=(side * 16, -18, 0), rot=(0.0, 0.0, side * -6.0))
        wing.box(0 if side > 0 else -30, -2, -20, 30, 5, 34)
        wing.box(30 if side > 0 else -46, -1, -12, 16, 4, 22)
        wing.box(46 if side > 0 else -56, 0, -6, 10, 3, 12)
    tail = m.part("tail", parent="body", pivot=(0, -16, 30))
    tail.box(-3, -3, 0, 6, 6, 18)
    eye(m, "eye", "body", (0, -26, -30), (12, 7))
    for index, (x, z) in enumerate([(-12, -14), (12, -14), (0, 12)]):
        leg(m, f"leg{index}", (x, -12, z), 10, 9, 2, splay_x=x * 0.5, foot=3)
    return m


# --------------------------------------------------------------------------- #
#  5. 1898 H. G. Wells tripod — a boiler on three legs, 9 blocks
# --------------------------------------------------------------------------- #

def book():
    m = Model("book_tripod")
    body = m.part("body")
    body.dome(0, -120, 0, 30, 32, [(14, 26, 28), (18, 36, 40), (16, 34, 38), (10, 26, 30), (6, 18, 20)])
    body.shell(0, -112, -16, 34, 8, 16, cell=14)      # front hood
    eye(m, "eye", "body", (0, -100, -20), (8, 6))
    body.shell(0, -60, 0, 18, 6, 20, cell=10)

    for index, (x, z, spread) in enumerate([(-11, -9, -8), (11, -9, 8), (0, 11, 0)]):
        tentacle(m, f"tent{index}_", "body", (x, -62, z), 30, segments=3, droop=14, spread=spread)

    leg(m, "leg_l", (-14, -62, -10), 44, 42, 4, splay_x=-15, splay_z=-11, foot=5)
    leg(m, "leg_r", (14, -62, -10), 44, 42, 4, splay_x=15, splay_z=-11, foot=5)
    leg(m, "leg_b", (0, -62, 14), 45, 43, 4, splay_z=13, foot=5)
    return m


# --------------------------------------------------------------------------- #
#  6. Asylum walker — six legs, wide spiked hull, one green eye, 4.2 blocks
# --------------------------------------------------------------------------- #

def asylum():
    m = Model("asylum_walker")
    body = m.part("body")
    body.shell(0, -43, 0, 50, 10, 56, cell=16)
    body.shell(0, -33, 0, 44, 8, 50, cell=16)
    body.shell(0, -25, 0, 34, 5, 40, cell=14)
    body.spikes(0, -43, 0, 0, 0, [(-16, -18), (16, -18), (-18, 4), (18, 4), (-14, 22), (14, 22)], size=8, step=2)
    eye(m, "eye", "body", (0, -37, -32), (13, 9))
    body.shell(0, -41, -26, 26, 6, 10, cell=12)       # brow over the eye

    for index, (x, z) in enumerate([(-20, -20), (20, -20), (-22, 0), (22, 0), (-18, 20), (18, 20)]):
        leg(m, f"leg{index}", (x, -24, z), 25, 24, 3, splay_x=x * 0.75, splay_z=z * 0.55, foot=4)
    return m


# --------------------------------------------------------------------------- #
#  7. The War of the Worlds: Goliath — 10 blocks, two red eyes, tentacles
# --------------------------------------------------------------------------- #

def goliath():
    m = Model("goliath_tripod")
    body = m.part("body")
    body.dome(0, -136, 0, 34, 38, [(14, 30, 34), (18, 46, 50), (16, 44, 48), (10, 32, 36), (6, 22, 26)])
    body.shell(0, -122, -22, 50, 8, 18, cell=16)      # brow
    eye(m, "eye_l", "body", (-12, -112, -26), (12, 8))
    eye(m, "eye_r", "body", (12, -112, -26), (12, 8))
    body.shell(0, -82, 0, 24, 8, 28, cell=12)

    for index, (x, z, spread) in enumerate([(-16, -12, -12), (16, -12, 12), (-20, 4, -18),
                                            (20, 4, 18), (-10, 16, -8), (10, 16, 8)]):
        tentacle(m, f"tent{index}_", "body", (x, -78, z), 36, segments=3, droop=18, spread=spread)

    leg(m, "leg_l", (-20, -80, -12), 52, 50, 5, splay_x=-13, splay_z=-10, foot=6)
    leg(m, "leg_r", (20, -80, -12), 52, 50, 5, splay_x=13, splay_z=-10, foot=6)
    leg(m, "leg_b", (0, -80, 22), 53, 51, 5, splay_z=12, foot=6)
    return m


# --------------------------------------------------------------------------- #
#  8. The Great Martian War — Martian Heron, 11.5 blocks of stilt legs
# --------------------------------------------------------------------------- #

def heron():
    """Martian Heron: the tallest machine in the mod — nearly fifteen blocks of stilts and hoods.

    An open front hood arches over the cockpit like a heron's head, with the Martian pilot sitting
    under it in plain sight; behind him a closed tin hood stores energy. Both hoods carry three
    comb fins on their rear edge and two heat cannons hang from the chin of the front one.
    """
    m = Model("martian_heron")

    # three stilt legs, hips high up the spine
    leg(m, "leg_l", (-11, -150, -6), 82, 80, 3, splay_x=-7, splay_z=-4, foot=4)
    leg(m, "leg_r", (11, -150, -6), 82, 80, 3, splay_x=7, splay_z=-4, foot=4)
    leg(m, "leg_b", (0, -150, 12), 82, 80, 3, splay_z=6, foot=4)

    body = m.part("body")
    body.shell(0, -204, 0, 16, 44, 18, cell=10)                 # iron spine, up to the hoods
    body.shell(0, -160, 0, 28, 14, 28, cell=12)                 # hip collar
    body.box(-14, -172, -13, 28, 5, 26)                         # tin belt
    body.box(-14, -138, -13, 28, 4, 26)                         # lower belt
    body.box(-13, -190, -13, 26, 14, 26)                        # shoulder yoke

    # rear hood: closed tin reservoir behind the pilot
    rear = m.part("rear_hood", parent="body", pivot=(0, -204, 20), rot=(6.0, 0.0, 0.0))
    rear.dome(0, 0, 0, 28, 26, [(11, 30, 26), (11, 28, 22), (8, 20, 16), (4, 12, 10)], cell=11)
    for index in range(3):
        rear.box(-10 + index * 10, -5, 13, 3, 22, 11)           # comb fins behind the rear hood
    rear.glow_box(-7, 4, 16, 5, 9, 2)                           # energy cells
    rear.glow_box(2, 4, 16, 5, 9, 2)

    # front hood: an open copper cowl over the cockpit, leaning forward
    head = m.part("head", parent="body", pivot=(0, -204, -22), rot=(-12.0, 0.0, 0.0))
    head.box(-17, -3, -18, 34, 7, 36)                           # roof
    head.box(-13, -7, -14, 26, 4, 28)                           # crown step
    head.box(-17, 4, -18, 5, 26, 34)                            # left wall
    head.box(12, 4, -18, 5, 26, 34)                             # right wall
    head.box(-12, 4, 10, 24, 26, 6)                             # back wall
    head.box(-17, 4, -24, 34, 6, 8)                             # chin lip, juts out in front
    head.box(-12, 30, -16, 24, 4, 28)                           # floor of the cockpit
    head.box(-20, -1, -6, 3, 5, 20)                             # copper brow rails
    head.box(17, -1, -6, 3, 5, 20)
    for index in range(3):
        head.box(-10 + index * 10, 2, 14, 3, 24, 11)            # comb fins behind the front hood

    # the Martian pilot, sitting in the open cockpit
    pilot = m.part("pilot", parent="body", pivot=(0, -186, -14))
    pilot.box(-8, 0, -4, 16, 14, 12)                            # torso and seat
    pilot.box(-5, -9, -2, 10, 9, 10)                            # head
    pilot.glow_box(-4, -7, -4, 3, 2, 2)                         # eyes
    pilot.glow_box(1, -7, -4, 3, 2, 2)

    # twin heat cannons under the chin
    for index, offset in enumerate((-11, 11)):
        cannon = m.part(f"cannon{index}", parent="body", pivot=(offset, -180, -20))
        cannon.box(-2, -2, -16, 5, 5, 18)
        cannon.glow_box(-3, -3, -18, 7, 7, 2)
        cannon.box(-1, 2, -8, 3, 4, 8)                          # mount strut

    # thin sensor arms hanging off the spine
    for index, (x, z, spread) in enumerate([(-9, -2, -12), (9, -2, 12)]):
        tentacle(m, f"tent{index}_", "body", (x, -120, z), 40, segments=3, droop=8, spread=spread)
    return m


# --------------------------------------------------------------------------- #
#  9. The Great Martian War — Martian Spider, low hull on eight legs
# --------------------------------------------------------------------------- #

def spider():
    """Martian Spider: 7.3 m tall (4.6 blocks), no cannons — just razor feelers and speed."""
    m = Model("martian_spider")
    body = m.part("body")
    body.dome(0, -60, 0, 38, 46, [(8, 32, 40), (9, 38, 46), (7, 32, 40), (4, 22, 30)], cell=14)

    # a different head altogether: a low arrow head, wider than it is tall, jutting forward
    head = m.part("head", parent="body", pivot=(0, -52, -20), rot=(-4.0, 0.0, 0.0))
    head.box(-13, -3, -10, 26, 9, 14)
    head.box(-9, -2, -18, 18, 7, 9)
    head.box(-4, -1, -24, 8, 5, 7)                              # arrow point
    eye(m, "eye", "head", (0, 2, -22), (12, 4))

    # razor feelers: four thin blades fanning out in front of the arrow head
    for index, (x, tilt, yaw) in enumerate([(-9, 10, -14), (-4, 4, -5), (4, 4, 5), (9, 10, 14)]):
        blade = m.part(f"blade{index}", parent="head", pivot=(x, 4, -20), rot=(tilt, yaw, 0.0))
        blade.box(-1, 0, -22, 2, 3, 22)

    for index in range(3):
        body.box(-9 + index * 9, -64, 6, 3, 9, 14)             # comb fins on the shell

    # eight short, steeply splayed legs: the low, crouched stance of the spider
    for index, (x, z) in enumerate([(-17, -16), (17, -16), (-19, -5), (19, -5),
                                    (-19, 6), (19, 6), (-17, 17), (17, 17)]):
        leg(m, f"leg{index}", (x, -36, z), 30, 28, 2,
            splay_x=x * 1.5, splay_z=z * 0.9, foot=3, knee=0.6)
    return m


# --------------------------------------------------------------------------- #
#  10. The Great Martian War — Martian Louse, a knee high riveted shell
# --------------------------------------------------------------------------- #

def louse():
    m = Model("martian_louse")
    body = m.part("body")
    body.dome(0, -8, 0, 18, 22, [(6, 16, 20), (6, 20, 24), (4, 16, 20)])
    body.spikes(0, -8, 0, 0, 0, [(-8, -9), (8, -9), (-8, 9), (8, 9)], size=4, step=1)
    eye(m, "eye", "body", (0, -4, -12), (7, 4))
    for index, (x, z) in enumerate([(-9, -8), (9, -8), (-9, 8), (9, 8), (0, -9), (0, 9)]):
        leg(m, f"leg{index}", (x, 4, z), 7, 6, 2, splay_x=x * 1.2, splay_z=z * 0.8, foot=2)
    return m


# How much taller than the authored geometry each machine should be. Vertical stretch only:
# the footprint and the leg thickness stay the same, so the machines read as tall walkers.
TALL = {
    "tripod2005": 1.38,
    "bbcWhite": 1.34,
    "bbcRed": 1.34,
    "manta": 1.25,
    "book": 1.36,
    "asylum": 1.55,
    "goliath": 1.42,
    "heron": 1.00,
    "spider": 1.00,
    "louse": 1.25,
    "martian": 1.32,
    "martianMaster": 1.22,
}

def finalise(model, method):
    """Applies the height multiplier and drops the model onto the ground plane."""
    stretch_vertical(model, TALL.get(method, 1.0))
    drop_to_ground(model)
    return model


# built once, already stretched: the preview renderer and the Java emitter share these objects
TRIPODS = [
    ("tripod2005", finalise(tripod_2005(), "tripod2005")),
    ("bbcWhite", finalise(bbc_white(), "bbcWhite")),
    ("bbcRed", finalise(bbc_red(), "bbcRed")),
    ("manta", finalise(manta(), "manta")),
    ("book", finalise(book(), "book")),
    ("asylum", finalise(asylum(), "asylum")),
    ("goliath", finalise(goliath(), "goliath")),
    ("heron", finalise(heron(), "heron")),
    ("spider", finalise(spider(), "spider")),
    ("louse", finalise(louse(), "louse")),
]


# --------------------------------------------------------------------------- #
#  Martians themselves
# --------------------------------------------------------------------------- #

def martian_model(name, body_h, width, crown, master=False):
    """The creature itself: a leathery blob with a beak and a ring of feelers for legs."""
    m = Model(name)
    bottom = 14 if not master else 12
    top = bottom - body_h
    body = m.part("body")
    # smooth taper: narrow crown, wide middle, narrow foot
    step = max(2, body_h // 9)
    taper = [0.55, 0.72, 0.86, 0.96, 1.02, 1.02, 0.96, 0.84, 0.66]
    layers = [(step, max(2, int(width * factor)), max(2, int(width * 1.1 * factor))) for factor in taper]
    body.dome(0, top, 0, width, int(width * 1.1), layers, cell=max(6, width // 3))

    # beak: two tapering boxes pushing forward out of the front of the blob
    beak = m.part("beak", parent="body", pivot=(0, top + body_h // 2, -int(width * 0.5)))
    beak.box(-width // 5, -width // 5, -width // 3, width * 2 // 5, width * 2 // 5, width // 3)
    beak.box(-width // 7, -width // 7, -width // 2, width * 2 // 7, width * 2 // 7, width // 4)

    eye(m, "eye_l", "body", (-width // 4, top + 2 * step, -int(width * 0.45)), (max(3, width // 5), max(2, width // 7)))
    eye(m, "eye_r", "body", (width // 4, top + 2 * step, -int(width * 0.45)), (max(3, width // 5), max(2, width // 7)))

    if crown:
        body.spikes(0, top, 0, 0, 0, [(-width // 3, -width // 4), (width // 3, -width // 4),
                                      (-width // 3, width // 4), (width // 3, width // 4)],
                    size=max(3, width // 4), step=2)

    # feelers: they start under the blob and splay out to the ground
    import math as _math
    for index in range(9):
        angle = -90 + index * 40
        radius = int(width * 0.55)
        x = int(_math.cos(_math.radians(angle)) * radius)
        z = int(_math.sin(_math.radians(angle)) * radius)
        droop = int(_math.sin(_math.radians(angle % 180)) * 30) + 46
        tentacle(m, f"tent{index}_", "body", (x, bottom - 2, z), 16 if not master else 22,
                 segments=3, droop=droop, spread=angle // 5, thick=max(2, width // 7))
    return m


MARTIANS = [
    ("martian", finalise(martian_model("martian", body_h=22, width=20, crown=False), "martian")),
    ("martianMaster",
     finalise(martian_model("martian_master", body_h=30, width=28, crown=True, master=True), "martianMaster")),
]


# --------------------------------------------------------------------------- #
#  Output
# --------------------------------------------------------------------------- #

HEADER = '''package dev.arena.wotw.client.model;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;

/**
 * {doc}
 *
 * <p><b>Generated file.</b> The geometry lives in `tools/generate_models.py` (see
 * `tools/model_lib.py`); edit that and run `python3 tools/generate_models.py`.
 * Model pixels: 16 per block, +y downwards, feet at y = 24, the machine looks towards
 * (0, +y down). Lenses sample the glowing patch of the texture at (96, 96).</p>
 */
public final class {class_name} {{

'''


def write_tripods():
    doc = "Hand tuned blocky geometry for the ten war machines, following the film references."
    parts = [HEADER.format(doc=doc, class_name="TripodModels")]
    for method, model in TRIPODS:
        parts.append(emit_java(model, method) + "\n")
    parts.append('''    /** The BBC machines differ only by the laser cannon, so one entry point serves both. */
    public static TexturedModelData bbc(boolean armed) {
        return armed ? bbcRed() : bbcWhite();
    }
}
''')
    path = os.path.join(MODEL_DIR, "TripodModels.java")
    with open(path, "w", encoding="utf-8") as handle:
        handle.write("\n".join(parts))
    print("wrote", path)


def write_martians():
    doc = "The Martians themselves: the leathery, tentacled creatures from the novel."
    parts = [HEADER.format(doc=doc, class_name="MartianModels")]
    for method, model in MARTIANS:
        parts.append(emit_java(model, method) + "\n")
    parts.append("}\n")
    path = os.path.join(MODEL_DIR, "MartianModels.java")
    with open(path, "w", encoding="utf-8") as handle:
        handle.write("\n".join(parts))
    print("wrote", path)


if __name__ == "__main__":
    write_tripods()
    write_martians()
