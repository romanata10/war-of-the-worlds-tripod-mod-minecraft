#!/usr/bin/env python3
"""Generates the Minecraft model code for every machine and Martian.

Geometry lives here (in model pixels, 16 per block, +y downwards, feet at y = 24), and two
outputs are produced from it:

    src/main/java/dev/arena/wotw/client/model/TripodModels.java   (25 machines)
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

import math
import os

from model_lib import Model, drop_to_ground, emit_java, eye, leg, stretch_vertical, tentacle

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
MODEL_DIR = os.path.join(ROOT, "src/main/java/dev/arena/wotw/client/model")


def rounded_hull(part, cx, cy, cz, w, h, d, layers=5, curve=0.6, cell=10):
    """A stack of slabs that narrows towards both ends, so a hull reads as rounded, not as a box."""
    step = max(2, h // layers)
    for index in range(layers):
        t = index / max(1.0, layers - 1.0)
        factor = 1.0 - curve * abs(t - 0.5) * 2.0
        width = max(6, int(round(w * factor)))
        depth = max(6, int(round(d * factor)))
        part.shell(cx, cy + index * step, cz, width, step, depth, cell=max(6, min(cell, width)))
    return part


def barbed_leg(model, name, hip, thigh, shin, thick, splay_x=0.0, splay_z=0.0, foot=0,
               barbs=3, barb=4):
    """A two segment leg with spines down the shin: the legs in the artwork are never smooth."""
    upper = leg(model, name, hip, thigh, shin, thick, splay_x=splay_x, splay_z=splay_z, foot=foot)
    part = model.part(f"{name}_barb", parent=f"{name}_shin", pivot=(0, 6, 0))
    step = max(6, (shin - 12) // max(1, barbs))
    for index in range(barbs):
        y = index * step
        part.box(thick // 2, y, -1, barb, 2, 2)
        part.box(-thick // 2 - barb, y + step // 2, -1, barb, 2, 2)
    return upper


def arc_arm(model, name, parent, pivot, yaw, length, segments=4, lift=-70.0, curl=-14.0, thick=2):
    """A feeler that leaves the hull almost horizontally and curls upwards, like the whip arms in the
    artwork. Every segment uses the same small curl, so it arcs instead of coiling into the body."""
    segment = max(3, length // segments)
    previous = parent
    for index in range(segments):
        part = model.part(f"{name}{index + 1}_{parent}", parent=previous,
                          pivot=pivot if index == 0 else (0, segment, 0),
                          rot=(lift if index == 0 else curl, yaw if index == 0 else 0.0, 0.0))
        part.box(-thick // 2, 0, -thick // 2, thick, segment, thick)
        previous = part.name


def claw_foot(model, name, parent, pivot, size=7, spread=4):
    """Three splayed claws instead of one flat foot block."""
    foot = model.part(name, parent=parent, pivot=pivot)
    foot.box(-spread - 1, 0, -size // 2, 2, 3, size)
    foot.box(spread - 1, 0, -size // 2, 2, 3, size)
    foot.box(-1, 0, -size // 2 - 3, 2, 3, size + 2)
    return foot


def spike_ring(part, cx, cy, cz, radius, count, size=4, height=8):
    """A ring of short spikes around a hull, used for the rim details of the artwork machines."""
    for index in range(count):
        angle = 6.283185307179586 * index / count
        x = cx + int(round(radius * math.cos(angle)))
        z = cz + int(round(radius * math.sin(angle)))
        part.box(x - size // 2, cy - height, z - size // 2, size, height, size)
    return part


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


# --------------------------------------------------------------------------- #
#  11-17. Martian war machines of the second wave
# --------------------------------------------------------------------------- #

def flying_machine():
    """Flying Machine: a flat disc hull with two rotor wings and a single eye."""
    m = Model("flying_machine")
    body = m.part("body")
    body.dome(0, -46, 0, 44, 54, [(8, 46, 56), (8, 44, 54), (6, 34, 42), (4, 22, 28)], cell=12)
    body.shell(0, -30, 0, 20, 9, 26, cell=10)                 # belly pod
    body.shell(0, -46, -28, 28, 5, 10, cell=10)               # brow
    eye(m, "eye", "body", (0, -38, -30), (14, 8))
    for side, name in ((-1, "wing_l"), (1, "wing_r")):
        wing = m.part(name, parent="body", pivot=(side * 20, -44, 2), rot=(0.0, 0.0, side * -8.0))
        wing.box(0 if side > 0 else -18, -2, -13, 18, 6, 26)   # outrigger arm
        for dz in (-11, 7):                                     # rotor pods
            wing.box(4 if side > 0 else -16, -6, dz, 12, 5, 16)
            wing.box(7 if side > 0 else -15, -9, dz + 5, 6, 3, 6)
    for index, (x, z) in enumerate([(-9, -14), (9, -14), (-9, 16), (9, 16)]):
        leg(m, f"leg{index}", (x, -26, z), 14, 12, 2, splay_x=x, splay_z=z * 0.4, foot=3)
    return m


def electric_machine():
    """Electric Machine: a coil capacitor on three legs that throws lightning charges."""
    m = Model("electric_machine")
    leg(m, "leg_l", (-13, -62, -8), 32, 30, 4, splay_x=-12, splay_z=-8, foot=5)
    leg(m, "leg_r", (13, -62, -8), 32, 30, 4, splay_x=12, splay_z=-8, foot=5)
    leg(m, "leg_b", (0, -62, 12), 33, 31, 4, splay_z=10, foot=5)

    body = m.part("body")
    body.shell(0, -102, 0, 26, 42, 28, cell=10)               # capacitor column
    for ring in (-98, -88, -78):                               # coil rings
        body.box(-17, ring, -15, 34, 4, 30)
    body.shell(0, -60, 0, 18, 8, 20, cell=10)                 # hip
    body.dome(0, -122, 0, 22, 24, [(8, 26, 28), (7, 22, 24), (4, 14, 16)], cell=10)
    eye(m, "coil", "body", (0, -120, 0), (16, 12))            # electrode sphere
    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        prong = m.part(name, parent="body", pivot=(side * 12, -108, -10), rot=(-28.0, 0.0, side * 20.0))
        prong.box(-2, -2, -16, 4, 4, 18)
        eye(m, name + "_tip", name, (0, 0, -18), (5, 5))
    return m


def handling_machine():
    """Handling Machine: catches people with two arms and drops them into the cage below."""
    m = Model("handling_machine")
    body = m.part("body")
    body.dome(0, -84, 0, 38, 44, [(12, 40, 46), (12, 38, 44), (8, 28, 32)], cell=12)
    body.shell(0, -56, 0, 22, 10, 26, cell=10)
    eye(m, "eye", "body", (0, -74, -25), (12, 7))

    cage = m.part("cage", parent="body", pivot=(0, -52, -16))
    cage.box(-11, 0, -9, 22, 3, 18)                            # roof
    cage.box(-11, 27, -9, 22, 3, 18)                           # floor
    for dx in (-11, 8):
        for dz in (-9, 6):
            cage.box(dx, 3, dz, 3, 24, 3)                      # corner bars
    for dz in (-9, 6):
        cage.box(-3, 3, dz, 6, 24, 3)                          # middle bars

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        arm = m.part(name, parent="body", pivot=(side * 17, -66, -14), rot=(-32.0, 0.0, side * 14.0))
        arm.box(-3, 0, -28, 6, 6, 32)
        arm.box(-6, 1, -36, 5, 5, 11)                          # claws
        arm.box(1, 1, -36, 5, 5, 11)

    for index, (x, z) in enumerate([(-19, -14), (19, -14), (-19, 14), (19, 14)]):
        leg(m, f"leg{index}", (x, -48, z), 30, 28, 4, splay_x=x * 0.7, splay_z=z * 0.45, foot=5)
    return m


def digging_machine():
    """Digging Machine: a squat borer that eats tunnels through the ground."""
    m = Model("digging_machine")
    body = m.part("body")
    body.shell(0, -58, 0, 42, 16, 50, cell=14)
    body.shell(0, -42, 4, 34, 10, 38, cell=12)
    body.shell(0, -58, -6, 20, 6, 22, cell=10)                 # spine plate
    eye(m, "eye", "body", (0, -50, -23), (10, 6))

    drill = m.part("head", parent="body", pivot=(0, -46, -24))
    drill.box(-11, -9, -12, 22, 18, 13)
    drill.box(-8, -7, -20, 16, 14, 9)
    drill.box(-5, -4, -26, 10, 8, 7)
    drill.box(-2, -2, -30, 4, 4, 5)
    drill.glow_box(-1, -1, -32, 2, 2, 3)

    for index, (x, z) in enumerate([(-21, -16), (21, -16), (-21, 12), (21, 12)]):
        leg(m, f"leg{index}", (x, -54, z), 22, 20, 5, splay_x=x * 0.5, splay_z=z * 0.35, foot=6)
    return m


def tempest():
    """Tempest: a tower on four heavy legs with two heat cannon pods and smoke stacks."""
    m = Model("tempest")
    leg(m, "leg_fl", (-22, -150, -16), 74, 72, 6, splay_x=-9, splay_z=-7, foot=8)
    leg(m, "leg_fr", (22, -150, -16), 74, 72, 6, splay_x=9, splay_z=-7, foot=8)
    leg(m, "leg_l", (-24, -150, 18), 74, 72, 6, splay_x=-9, splay_z=7, foot=8)
    leg(m, "leg_r", (24, -150, 18), 74, 72, 6, splay_x=9, splay_z=7, foot=8)

    body = m.part("body")
    body.shell(0, -232, 0, 34, 82, 40, cell=14)
    body.shell(0, -158, 0, 48, 16, 48, cell=14)                # hip girdle
    body.shell(0, -200, 0, 46, 22, 46, cell=14)                # reactor bulge

    head = m.part("head", parent="body", pivot=(0, -232, -8))
    head.box(-10, -8, -16, 20, 16, 22)
    eye(m, "eye", "head", (0, 0, -17), (10, 6))

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        pod = m.part(name, parent="body", pivot=(side * 16, -212, -20))
        pod.box(-7, -7, -20, 14, 14, 24)
        pod.glow_box(-3, -3, -22, 6, 6, 3)

    for side, name in ((-1, "stack_l"), (1, "stack_r")):
        stack = m.part(name, parent="body", pivot=(side * 13, -216, 22))
        stack.box(-5, 0, -5, 10, 30, 10)
        eye(m, name + "_mouth", name, (0, 30, 0), (6, 6))
    return m


def bombarding_machine():
    """Bombarding Machine: two legs, no ray — one enormous mortar on the roof."""
    m = Model("bombarding_machine")
    leg(m, "leg_l", (-12, -56, 0), 30, 28, 4, splay_x=-10, foot=5)
    leg(m, "leg_r", (12, -56, 0), 30, 28, 4, splay_x=10, foot=5)

    body = m.part("body")
    body.shell(0, -88, 0, 32, 32, 36, cell=12)
    body.shell(0, -90, 6, 24, 8, 22, cell=10)
    eye(m, "eye", "body", (0, -80, -19), (10, 6))

    cannon = m.part("cannon", parent="body", pivot=(0, -92, -6))
    cannon.box(-9, -8, -12, 18, 15, 18)                        # breech
    cannon.box(-8, -6, -16, 16, 4, 6)                          # mantlet
    cannon.box(-5, -14, -30, 10, 9, 20)                        # barrel
    cannon.box(-7, -16, -34, 14, 13, 6)                        # muzzle brake
    cannon.glow_box(-3, -13, -36, 6, 6, 3)
    return m


def drone():
    """Drone: knee high, eight legs, one violet eye, and a belly full of explosives."""
    m = Model("drone")
    body = m.part("body")
    body.dome(0, -14, 0, 13, 15, [(4, 13, 15), (4, 15, 17), (3, 11, 13), (2, 7, 9)], cell=6)
    body.spikes(0, -14, 0, 0, 0, [(-5, -5), (5, -5), (-5, 5), (5, 5)], size=3, step=1)
    eye(m, "eye", "body", (0, -10, -8), (5, 3))
    for index, (x, z) in enumerate([(-7, -6), (7, -6), (-8, 0), (8, 0), (-8, 6), (8, 6), (-6, 9), (6, 9)]):
        leg(m, f"leg{index}", (x, -7, z), 11, 10, 2, splay_x=x * 1.5, splay_z=z * 0.7, foot=2)
    return m


def human_tripod():
    """The machine the blueprint builds: a steel tripod with a cockpit a human can ride."""
    m = Model("human_tripod")
    body = m.part("body")
    body.dome(0, -110, 0, 32, 36, [(10, 28, 32), (12, 36, 40), (12, 34, 38), (6, 22, 26)], cell=12)
    body.shell(0, -86, -16, 22, 18, 14, cell=10)               # cockpit tub
    body.shell(0, -76, -22, 16, 8, 4, cell=8)                  # view slit frame
    eye(m, "eye", "body", (0, -78, -24), (12, 6))
    body.shell(0, -64, 0, 20, 10, 24, cell=10)                 # waist

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        arm = m.part(name, parent="body", pivot=(side * 17, -100, -10), rot=(0.0, 0.0, side * -8.0))
        arm.box(-3, -3, -20, 6, 6, 24)
        arm.glow_box(-2, -2, -22, 4, 4, 3)

    leg(m, "leg_l", (-14, -64, -8), 34, 32, 4, splay_x=-12, splay_z=-8, foot=6)
    leg(m, "leg_r", (14, -64, -8), 34, 32, 4, splay_x=12, splay_z=-8, foot=6)
    leg(m, "leg_b", (0, -64, 14), 35, 33, 4, splay_z=11, foot=6)
    return m



# --------------------------------------------------------------------------- #
#  Third wave: Martian walkers
# --------------------------------------------------------------------------- #

def water_tripod():
    """Water tripod: an oil rig on three enormous legs that reach the ocean floor."""
    m = Model("water_tripod")
    leg(m, "leg_fl", (-22, -74, -18), 62, 58, 7, splay_x=-18, splay_z=-12, foot=9)
    leg(m, "leg_fr", (22, -74, -18), 62, 58, 7, splay_x=18, splay_z=-12, foot=9)
    leg(m, "leg_b", (0, -74, 22), 62, 58, 7, splay_z=16, foot=9)

    body = m.part("body")
    body.shell(0, -116, 0, 58, 34, 58, cell=18)          # rig platform
    body.shell(0, -80, 0, 40, 8, 40, cell=14)            # pontoon under the deck
    body.shell(0, -122, 0, 26, 8, 26, cell=12)           # derrick base

    tower = m.part("head", parent="body", pivot=(0, -116, 0))
    tower.box(-9, -76, -9, 18, 76, 18)                   # derrick
    tower.box(-15, -60, -15, 30, 7, 30)                  # bracing
    tower.box(-13, -40, -13, 26, 6, 26)                  # second brace
    tower.box(-12, -84, -12, 24, 6, 24)                  # crown
    tower.box(-2, -96, -2, 4, 12, 4)                     # flare stack
    eye(m, "eye", "head", (0, -68, -15), (9, 5))

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        pipe = m.part(name, parent="body", pivot=(side * 24, -92, 0), rot=(0.0, 0.0, side * -14.0))
        pipe.box(-4, -4, -18, 8, 8, 36)
        pipe.box(-5, -6, 16, 10, 12, 8)
        pipe.glow_box(-3, -4, -22, 6, 6, 4)
    return m


def scout_tripod():
    """Scout tripod: knee high, one machine gun, and a radio to call the others."""
    m = Model("scout_tripod")
    leg(m, "leg_l", (-8, -22, -4), 22, 20, 3, splay_x=-8, splay_z=-4, foot=4)
    leg(m, "leg_r", (8, -22, -4), 22, 20, 3, splay_x=8, splay_z=-4, foot=4)
    leg(m, "leg_b", (0, -22, 8), 22, 20, 3, splay_z=9, foot=4)

    body = m.part("body")
    body.shell(0, -38, 0, 22, 16, 22, cell=8)
    body.shell(0, -26, 0, 16, 6, 16, cell=6)
    eye(m, "eye", "body", (0, -33, -11), (6, 4))

    gun = m.part("arm_r", parent="body", pivot=(8, -34, -8))
    gun.box(-3, -3, -4, 6, 6, 10)                        # receiver
    gun.box(-2, -2, -18, 4, 4, 16)                       # barrel
    gun.box(-4, -4, -6, 8, 8, 4)                         # drum magazine
    gun.glow_box(-1, -1, -20, 2, 2, 3)

    radio = m.part("arm_l", parent="body", pivot=(-8, -40, 6))
    radio.box(-2, -10, -2, 4, 12, 4)
    radio.glow_box(-2, -14, -2, 4, 4, 4)
    return m


def ram_tripod():
    """Ram tripod: low, wide and built around a battering plate."""
    m = Model("ram_tripod")
    leg(m, "leg_l", (-16, -24, -6), 13, 11, 5, splay_x=-20, splay_z=-6, foot=6)
    leg(m, "leg_r", (16, -24, -6), 13, 11, 5, splay_x=20, splay_z=-6, foot=6)
    leg(m, "leg_b", (0, -24, 12), 13, 11, 5, splay_z=16, foot=6)

    body = m.part("body")
    body.shell(0, -36, 6, 32, 16, 40, cell=12)           # hull
    body.shell(0, -28, -16, 22, 10, 14, cell=10)         # nose block

    plate = m.part("head", parent="body", pivot=(0, -30, -22))
    plate.box(-24, -12, -6, 48, 24, 6)                   # ram plate
    plate.box(-18, -8, -10, 36, 16, 4)                   # brace
    plate.spikes(0, -14, -4, 0, 0, [(-18, 0), (-8, 0), (0, 0), (8, 0), (18, 0)], size=5, step=2)
    eye(m, "eye", "body", (0, -32, -21), (10, 4))

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        roller = m.part(name, parent="body", pivot=(side * 18, -20, 8))
        roller.box(-4, -4, -10, 8, 8, 20)
    return m


def uberpod():
    """Uberpod: the tall shielded pod of the 2005 film, 10 blocks of bad news."""
    m = Model("uberpod")
    leg(m, "leg_fl", (-16, -78, -12), 42, 40, 5, splay_x=-14, splay_z=-10, foot=6)
    leg(m, "leg_fr", (16, -78, -12), 42, 40, 5, splay_x=14, splay_z=-10, foot=6)
    leg(m, "leg_b", (0, -78, 14), 43, 41, 5, splay_z=12, foot=6)

    body = m.part("body")
    body.dome(0, -150, 0, 40, 40, [(10, 24, 26), (14, 36, 38), (16, 42, 44), (12, 34, 36), (8, 22, 24)],
              cell=12)
    body.shell(0, -80, 0, 30, 10, 30, cell=10)           # waist ring
    eye(m, "eye", "body", (0, -112, -20), (14, 8))

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        pod = m.part(name, parent="body", pivot=(side * 18, -118, -12))
        pod.box(-6, -6, -12, 12, 12, 20)
        pod.glow_box(-3, -3, -16, 6, 6, 3)
    return m


def bunker_tripod():
    """Bunker tripod: a grey blockhouse on six thick legs, with six firing ports."""
    m = Model("bunker_tripod")
    for index, (x, z) in enumerate([(-34, -20), (34, -20), (-38, 8), (38, 8), (-18, 30), (18, 30)]):
        leg(m, f"leg{index}", (x, -46, z), 24, 22, 10, splay_x=x * 0.35, splay_z=z * 0.3, foot=11)

    body = m.part("body")
    body.shell(0, -96, 0, 76, 40, 62, cell=14)           # the blockhouse
    body.shell(0, -56, 0, 56, 10, 46, cell=12)           # belly plate
    body.shell(0, -100, 0, 60, 6, 48, cell=12)           # roof deck

    # six firing ports: three down each flank
    for index in range(6):
        side = -1 if index % 2 == 0 else 1
        level = index // 2
        port = m.part(f"arm{index}", parent="body", pivot=(side * 39, -88 + level * 10, -8))
        port.glow_box(-1, -2, -3, 4, 6, 6)
        port.box(-2, -3, -5, 5, 8, 10)
    roof = m.part("head", parent="body", pivot=(0, -100, 0))
    roof.box(-6, -10, -6, 12, 10, 12)                    # cupola
    roof.box(-2, -8, -16, 4, 4, 12)                      # roof gun
    roof.glow_box(-3, -6, -18, 6, 5, 3)
    eye(m, "eye", "body", (0, -84, -32), (12, 6))
    return m


def flamer_tripod():
    """Flamer tripod: a fuel tank on legs with a fire nozzle where the heat ray should be."""
    m = Model("flamer_tripod")
    leg(m, "leg_l", (-14, -52, -8), 28, 26, 4, splay_x=-12, splay_z=-8, foot=6)
    leg(m, "leg_r", (14, -52, -8), 28, 26, 4, splay_x=12, splay_z=-8, foot=6)
    leg(m, "leg_b", (0, -52, 14), 29, 27, 4, splay_z=12, foot=6)

    body = m.part("body")
    body.shell(0, -86, 0, 30, 30, 34, cell=12)
    body.shell(0, -58, 0, 22, 8, 26, cell=10)
    eye(m, "eye", "body", (0, -78, -18), (10, 5))

    nozzle = m.part("head", parent="body", pivot=(0, -80, -16))
    nozzle.box(-5, -5, -14, 10, 10, 16)
    nozzle.box(-3, -3, -28, 6, 6, 16)
    nozzle.glow_box(-2, -2, -32, 4, 4, 4)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        tank = m.part(name, parent="body", pivot=(side * 19, -78, 10))
        tank.box(-6, -18, -6, 12, 26, 12)
        tank.box(-4, -22, -4, 8, 5, 8)
        tank.glow_box(-2, -24, -2, 4, 3, 4)
    return m


def tank_tripod():
    """Tank tripod: the giant. Three heat cannons, a mortar and armour for days."""
    m = Model("tank_tripod")
    leg(m, "leg_fl", (-32, -96, -26), 62, 58, 12, splay_x=-22, splay_z=-16, foot=14)
    leg(m, "leg_fr", (32, -96, -26), 62, 58, 12, splay_x=22, splay_z=-16, foot=14)
    leg(m, "leg_b", (0, -96, 32), 63, 59, 12, splay_z=20, foot=14)

    body = m.part("body")
    body.shell(0, -200, 0, 64, 74, 74, cell=18)          # main hull
    body.shell(0, -120, 0, 84, 24, 88, cell=18)          # skirt
    body.shell(0, -156, 0, 92, 20, 92, cell=18)          # shoulder ring
    eye(m, "eye", "body", (0, -166, -38), (16, 8))

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        cannon = m.part(name, parent="body", pivot=(side * 46, -190, -18))
        cannon.box(-11, -11, -16, 22, 22, 26)
        cannon.box(-6, -6, -60, 12, 12, 46)
        cannon.box(-8, -8, -68, 16, 16, 9)
        cannon.glow_box(-5, -5, -70, 10, 10, 4)

    brow = m.part("arm0", parent="body", pivot=(0, -212, -30))
    brow.box(-8, -8, -14, 16, 16, 20)
    brow.box(-4, -4, -46, 8, 8, 34)
    brow.glow_box(-3, -3, -49, 6, 6, 4)

    mortar = m.part("head", parent="body", pivot=(0, -200, 20))
    mortar.box(-12, -14, -14, 24, 20, 26)
    mortar.box(-8, -34, -8, 16, 22, 16)
    mortar.box(-10, -40, -10, 20, 8, 20)
    mortar.glow_box(-5, -38, -12, 10, 6, 4)

    stack = m.part("stack_r", parent="body", pivot=(28, -200, 10))
    stack.box(-6, -26, -6, 12, 28, 12)
    return m


def taxi_tripod():
    """Taxi tripod: a flat carrier platform with rails, no weapons at all."""
    m = Model("taxi_tripod")
    leg(m, "leg_l", (-18, -24, -8), 16, 14, 5, splay_x=-18, splay_z=-6, foot=6)
    leg(m, "leg_r", (18, -24, -8), 16, 14, 5, splay_x=18, splay_z=-6, foot=6)
    leg(m, "leg_b", (0, -24, 16), 16, 14, 5, splay_z=16, foot=6)

    body = m.part("body")
    body.shell(0, -38, 0, 44, 14, 40, cell=12)           # deck
    body.shell(0, -44, 0, 30, 6, 28, cell=10)            # cargo cradle
    eye(m, "eye", "body", (0, -40, -21), (8, 4))

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        rail = m.part(name, parent="body", pivot=(side * 21, -38, 0))
        rail.box(-2, -10, -18, 4, 10, 36)                # hand rail
        rail.box(-3, -12, -20, 6, 4, 4)                   # lamp post
        rail.glow_box(-2, -14, -21, 4, 3, 3)

    seat = m.part("head", parent="body", pivot=(0, -38, -14))
    seat.box(-10, -12, -4, 20, 12, 6)
    return m


# --------------------------------------------------------------------------- #
#  The human army
# --------------------------------------------------------------------------- #

def human_soldier():
    """A conscript, second pass: helmet with a brim, flak vest, backpack, boots and a rifle held in
    both hands, so the silhouette reads as a soldier and not as a stick figure."""
    m = Model("human_soldier", texture=(128, 128))
    for side, name in ((-1, "leg_l"), (1, "leg_r")):
        limb = m.part(name, pivot=(side * 3, -16, 0))
        limb.box(-2, 0, -2, 4, 13, 4)                    # thigh and shin
        limb.box(-2, 0, -2, 4, 3, 4)                     # knee pad
        limb.box(-2, 12, -3, 4, 4, 6)                    # boot
        limb.box(-2, 14, -4, 4, 2, 3)                    # toe cap

    body = m.part("body", pivot=(0, -16, 0))
    body.box(-5, -14, -3, 10, 14, 6)                     # torso
    body.box(-6, -11, -3, 12, 9, 7)                      # flak vest, a pixel proud of the chest
    body.box(-5, -14, 3, 10, 12, 5)                      # backpack
    body.box(-4, -15, -3, 8, 2, 6)                       # collar

    head = m.part("head", parent="body", pivot=(0, -14, 0))
    head.box(-4, -9, -4, 8, 9, 8)                        # head
    head.box(-5, -11, -5, 10, 3, 10)                     # helmet shell
    head.box(-5, -8, -6, 10, 2, 2)                       # helmet brim
    eye(m, "eye", "head", (0, -5, -5), (6, 2))           # goggles

    arm_r = m.part("arm_r", parent="body", pivot=(6, -12, 0))
    arm_r.box(-2, 0, -2, 4, 10, 4)
    arm_r.box(-1, 8, -2, 3, 3, 12)                       # rifle body
    arm_r.box(-1, 10, -1, 2, 2, 4)                       # magazine
    arm_r.box(-1, 8, 9, 2, 2, 4)                         # muzzle

    arm_l = m.part("arm_l", parent="body", pivot=(-6, -12, 0))
    arm_l.box(-2, 0, -2, 4, 10, 4)
    arm_l.box(-2, 8, -1, 3, 3, 6)                        # the hand on the foregrip
    return m


def human_helicopter():
    """A gunship: bubble cockpit, a long tail boom, a four blade rotor and two door guns."""
    m = Model("human_helicopter", texture=(128, 128))
    body = m.part("body")
    body.shell(0, -18, 0, 14, 14, 34, cell=12)           # cabin
    body.shell(0, -20, -18, 10, 8, 12, cell=8)           # nose
    body.shell(0, -14, 18, 6, 6, 24, cell=6)             # tail boom

    tail = m.part("head", parent="body", pivot=(0, -12, 40))
    tail.box(-1, -14, -2, 2, 16, 6)                      # fin
    tail.box(-8, -2, -1, 16, 2, 6)                       # tailplane

    rotor = m.part("rotor", parent="body", pivot=(0, -24, 0))
    rotor.box(-1, -1, -36, 2, 2, 72)                     # main blades
    rotor.box(-36, -1, -1, 72, 2, 2)
    rotor.box(-4, -4, -4, 8, 5, 8)                       # hub

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        gun = m.part(name, parent="body", pivot=(side * 8, -10, -4))
        gun.box(-1, 0, -1, 2, 6, 2)
        gun.box(-1, 5, -9, 2, 2, 10)                     # door gun barrel

    for side, name in ((-1, "leg_l"), (1, "leg_r")):
        skid = m.part(name, parent="body", pivot=(side * 6, -4, 0))
        skid.box(-1, 0, -12, 2, 2, 26)                   # skid
        skid.box(-1, -5, -7, 2, 5, 2)                    # struts
        skid.box(-1, -5, 6, 2, 5, 2)

    eye(m, "eye", "body", (0, -14, -25), (7, 3))         # cockpit glass
    return m


def human_submarine():
    """A coastal submarine: a long hull, a conning tower, stern planes and a propeller."""
    m = Model("human_submarine", texture=(128, 128))
    body = m.part("body")
    body.shell(0, -14, 0, 16, 14, 76, cell=16)           # hull
    body.shell(0, -14, -40, 10, 9, 12, cell=8)           # bow

    tower = m.part("head", parent="body", pivot=(0, -14, -4))
    tower.box(-5, -14, -9, 10, 14, 18)                   # conning tower
    tower.box(-1, -23, -1, 2, 9, 2)                      # periscope
    tower.glow_box(-4, -10, -10, 8, 3, 1)                # bridge windows

    propeller = m.part("arm_r", parent="body", pivot=(0, -10, 38))
    propeller.box(-1, -9, 0, 2, 18, 2)
    propeller.box(-9, -1, 0, 18, 2, 2)
    propeller.box(-2, -2, -2, 4, 4, 5)                   # hub

    for side, name in ((-1, "wing_l"), (1, "wing_r")):
        plane = m.part(name, parent="body", pivot=(side * 7, -10, 28))
        plane.box(0, -1, -7, side * 5, 2, 16)            # stern plane

    for side, name in ((-1, "leg_l"), (1, "leg_r")):
        tube = m.part(name, parent="body", pivot=(side * 5, -9, -38))
        tube.box(-1, -1, -7, 2, 2, 9)                    # torpedo tube

    eye(m, "eye", "body", (0, -12, -42), (5, 3))         # bow lamp
    return m


def human_bomber():
    """A heavy bomber: long fuselage, four engines on swept wings and a bomb bay."""
    m = Model("human_bomber", texture=(128, 128))
    body = m.part("body")
    body.shell(0, -22, 0, 16, 17, 86, cell=16)           # fuselage
    body.shell(0, -22, -44, 10, 11, 12, cell=8)          # nose
    body.shell(0, -20, 44, 6, 6, 14, cell=6)             # tail cone

    for side, name in ((-1, "wing_l"), (1, "wing_r")):
        wing = m.part(name, parent="body", pivot=(side * 8, -16, -4))
        wing.box(0, -2, -15, side * 48, 4, 28)           # main wing
        wing.box(side * 20, -7, -10, side * 6, 8, 16)    # inner nacelle
        wing.box(side * 34, -7, -8, side * 6, 8, 14)     # outer nacelle
        wing.box(side * 44, -2, -8, side * 5, 4, 14)     # wingtip

    tail = m.part("head", parent="body", pivot=(0, -20, 48))
    tail.box(-1, -16, -2, 2, 18, 7)                      # fin
    tail.box(-13, -3, -1, 26, 3, 9)                      # tailplane
    tail.glow_box(-1, -16, 5, 2, 2, 2)                   # tail light

    bay = m.part("bay", parent="body", pivot=(0, -6, 0))
    bay.box(-4, 0, -14, 8, 3, 28)                        # bomb bay doors
    eye(m, "eye", "body", (0, -20, -45), (7, 3))         # cockpit glass
    return m


def human_tank():
    """A tank: tracks, hull, turret, long barrel."""
    m = Model("human_tank", texture=(128, 128))
    body = m.part("body")
    body.box(-16, -30, -26, 32, 14, 52)                  # hull
    body.box(-22, -20, -28, 5, 20, 56)                   # left track
    body.box(17, -20, -28, 5, 20, 56)                    # right track
    body.box(-16, -34, 6, 32, 4, 18)                     # engine deck

    turret = m.part("turret", parent="body", pivot=(0, -30, -2))
    turret.box(-9, -11, -11, 18, 11, 22)
    turret.box(-2, -8, -32, 4, 4, 22)                    # barrel
    turret.box(-4, -9, -34, 8, 6, 4)                     # muzzle brake
    turret.glow_box(-1, -7, -35, 2, 2, 2)
    return m


def human_fighter():
    """An interceptor: swept wings, a jet pipe and a cannon in each wing root."""
    m = Model("human_fighter", texture=(128, 128))
    body = m.part("body")
    body.shell(0, -16, 0, 12, 12, 62, cell=10)           # fuselage
    body.shell(0, -20, 8, 8, 6, 22, cell=8)              # spine

    for side, name in ((-1, "wing_l"), (1, "wing_r")):
        wing = m.part(name, parent="body", pivot=(side * 5, -12, -4))
        wing.box(0, -2, -8, side * 34, 4, 16)            # main wing
        wing.box(0, -1, 8, side * 20, 3, 8)              # tailplane
        wing.box(side * 26, -2, -4, 6, 4, 6)             # wingtip pod

    tail = m.part("head", parent="body", pivot=(0, -16, 26))
    tail.box(-1, -20, -2, 2, 20, 6)                      # fin
    tail.box(-6, -6, 2, 12, 5, 8)                        # exhaust block
    eye(m, "eye", "body", (0, -12, -31), (6, 4))
    return m


def human_warship():
    """A coastal defence ship: long hull, superstructure and two turrets."""
    m = Model("human_warship", texture=(128, 128))
    body = m.part("body")
    body.shell(0, -22, 0, 34, 22, 100, cell=16)          # hull
    body.shell(0, -28, 0, 20, 8, 60, cell=12)            # deck house base

    bridge = m.part("head", parent="body", pivot=(0, -28, -6))
    bridge.box(-8, -14, -10, 16, 14, 20)
    bridge.box(-3, -24, -3, 6, 10, 6)                    # mast
    bridge.glow_box(-8, -10, -11, 16, 4, 2)              # bridge windows

    turret = m.part("turret", parent="body", pivot=(0, -22, -34))
    turret.box(-7, -8, -7, 14, 8, 14)
    turret.box(-2, -6, -28, 4, 4, 22)                    # forward gun
    turret.box(-2, -6, 20, 4, 4, 20)                     # aft gun

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        mast = m.part(name, parent="body", pivot=(side * 12, -28, 6))
        mast.box(-1, -16, -1, 2, 16, 2)
        mast.glow_box(-1, -18, -1, 2, 2, 2)
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
    "flyingMachine": 1.05,
    "electric": 1.15,
    "handling": 1.10,
    "digging": 1.05,
    "tempest": 1.02,
    "bombarding": 1.15,
    "drone": 1.10,
    "waterTripod": 1.04,
    "scoutTripod": 1.05,
    "ramTripod": 1.05,
    "uberpod": 1.03,
    "bunkerTripod": 1.02,
    "flamerTripod": 1.05,
    "tankTripod": 1.02,
    "taxiTripod": 1.06,
    "humanTripod": 1.12,
    "martian": 1.32,
    "martianMaster": 1.22,
}

def finalise(model, method):
    """Applies the height multiplier and drops the model onto the ground plane."""
    stretch_vertical(model, TALL.get(method, 1.0))
    drop_to_ground(model)
    return model


# built once, already stretched: the preview renderer and the Java emitter share these objects
def construction_tripod():
    """The builder: three enormously thick legs, a glass control tower, two open cargo holds and a
    long crane whose clamshell claw hangs out in front of the machine. One machine gun, chin mounted."""
    m = Model("construction_tripod")
    leg(m, "leg_l", (-28, -176, -16), 84, 88, 10, splay_x=-10, splay_z=-8, foot=12)
    leg(m, "leg_r", (28, -176, -16), 84, 88, 10, splay_x=10, splay_z=-8, foot=12)
    leg(m, "leg_b", (0, -176, 30), 84, 88, 10, splay_x=0, splay_z=9, foot=12)

    body = m.part("body")
    body.shell(0, -268, 0, 40, 64, 52, cell=14)                  # hull
    body.shell(0, -182, 0, 56, 18, 62, cell=14)                  # hip girdle
    body.shell(0, -250, 0, 48, 24, 64, cell=14)                  # cargo frame

    tower = m.part("head", parent="body", pivot=(0, -270, -22))
    tower.box(-13, -28, -12, 26, 28, 24)                         # control cabin
    tower.box(-14, -6, -13, 28, 4, 26)                           # cabin sill
    eye(m, "eye", "head", (0, -16, -13), (20, 14))               # tower glass

    for side, name in ((-1, "hold_l"), (1, "hold_r")):
        hold = m.part(name, parent="body", pivot=(side * 26, -248, 4))
        hold.box(-7, 0, -26, 14, 22, 52)                         # open cargo box, proud of the hull
        hold.glow_box(-6, 18, -22, 12, 3, 44)                    # lit cargo mouth

    # the crane: a long jib out over the cabin, a counterweight behind it, and the claw hanging
    crane = m.part("crane", parent="body", pivot=(0, -276, 20))
    crane.box(-5, -8, -52, 10, 12, 86)                           # jib, out over the cabin
    crane.box(-9, -10, 28, 18, 14, 16)                           # counterweight
    crane.box(-3, 4, -50, 6, 20, 6)                              # hanging post
    claw = m.part("claw", parent="crane", pivot=(0, 24, -48))
    claw.box(-7, 0, -5, 14, 6, 12)                               # wrist
    claw.box(-11, 6, -4, 5, 14, 10)                              # left finger
    claw.box(6, 6, -4, 5, 14, 10)                                # right finger
    claw.box(-9, 18, -3, 18, 4, 8)                               # the two fingers close on this
    claw.glow_box(-2, 2, 6, 4, 4, 2)                             # claw lamp

    gun = m.part("gun", parent="body", pivot=(0, -206, -30))
    gun.box(-8, -8, -14, 16, 16, 16)                             # mantlet
    gun.box(-2, -2, -34, 4, 4, 22)                               # machine gun barrel
    return m


def smoke_tripod():
    """A boiler on legs: a fat riveted drum ringed with two tall vent stacks that pour out the black
    smoke, and a manifold across the back."""
    m = Model("smoke_tripod")
    leg(m, "leg_l", (-20, -124, 0), 62, 64, 6, splay_x=-13, splay_z=2, foot=9)
    leg(m, "leg_r", (20, -124, 0), 62, 64, 6, splay_x=13, splay_z=2, foot=9)
    leg(m, "leg_b", (0, -124, 24), 62, 64, 6, splay_x=0, splay_z=14, foot=9)

    body = m.part("body")
    body.shell(0, -196, 0, 44, 56, 42, cell=12)                  # boiler
    body.shell(0, -132, 0, 40, 12, 38, cell=12)                  # belly
    body.shell(0, -170, 0, 50, 10, 46, cell=12)                  # riveted band
    body.shell(0, -204, 0, 32, 16, 30, cell=10)                  # dome

    head = m.part("head", parent="body", pivot=(0, -204, -16))
    head.box(-9, -12, -10, 18, 14, 14)
    eye(m, "eye", "head", (0, -4, -11), (12, 6))

    for side, name in ((-1, "vent_l"), (1, "vent_r")):
        vent = m.part(name, parent="body", pivot=(side * 17, -196, 6))
        vent.box(-5, -34, -9, 10, 34, 18)                        # chimney, rising above the boiler
        vent.box(-7, -38, -11, 14, 5, 22)                        # chimney cap
        eye(m, name + "_mouth", name, (0, -40, -1), (8, 8))      # glowing mouth

    pipe = m.part("pipe", parent="body", pivot=(0, -196, 22))
    pipe.box(-16, -6, 0, 32, 10, 8)                              # manifold across the back
    return m


def bomb_tripod():
    """A mortar on legs: a stubby hull, a long thick tube angled at the sky, and bomb racks on the
    flanks where the smoke bombs are stacked."""
    m = Model("bomb_tripod")
    leg(m, "leg_l", (-22, -142, -6), 68, 70, 7, splay_x=-12, splay_z=-4, foot=10)
    leg(m, "leg_r", (22, -142, -6), 68, 70, 7, splay_x=12, splay_z=-4, foot=10)
    leg(m, "leg_b", (0, -142, 26), 68, 70, 7, splay_x=0, splay_z=12, foot=10)

    body = m.part("body")
    body.shell(0, -214, 0, 38, 56, 42, cell=12)
    body.shell(0, -148, 0, 46, 14, 46, cell=12)

    head = m.part("head", parent="body", pivot=(0, -214, -18))
    head.box(-9, -12, -11, 18, 14, 16)
    eye(m, "eye", "head", (0, -4, -12), (10, 6))

    mortar = m.part("mortar", parent="body", pivot=(0, -206, 12), rot=(-0.7, 0.0, 0.0))
    mortar.box(-10, -8, -10, 20, 16, 20)                         # breech
    mortar.box(-7, -56, -7, 14, 48, 14)                          # the tube itself
    mortar.box(-9, -60, -9, 18, 6, 18)                           # muzzle brake
    eye(m, "muzzle", "mortar", (0, -60, 0), (11, 11))

    for side, name in ((-1, "rack_l"), (1, "rack_r")):
        rack = m.part(name, parent="body", pivot=(side * 22, -204, 10))
        rack.box(-6, 0, -12, 12, 14, 26)                         # bomb rack, proud of the hull
        rack.glow_box(-5, 12, -10, 10, 2, 22)
    return m


def constrictor():
    """Martian Constrictor: a wide, squat carapace with two chemical sprayers and a heat lens. It
    pins a squad in foam and then boils what is left."""
    m = Model("constrictor")
    leg(m, "leg_l", (-26, -120, -10), 60, 62, 8, splay_x=-8, splay_z=-5, foot=10)
    leg(m, "leg_r", (26, -120, -10), 60, 62, 8, splay_x=8, splay_z=-5, foot=10)
    leg(m, "leg_b", (0, -120, 28), 60, 62, 8, splay_x=0, splay_z=10, foot=10)

    body = m.part("body")
    body.shell(0, -186, 0, 52, 40, 46, cell=14)                  # wide carapace
    body.shell(0, -126, 0, 46, 12, 42, cell=12)                  # belly
    body.shell(0, -160, 0, 58, 10, 50, cell=14)                  # shoulder band

    head = m.part("head", parent="body", pivot=(0, -190, -18))
    head.box(-12, -14, -12, 24, 16, 18)                          # head
    eye(m, "eye", "head", (0, -5, -13), (14, 7))                 # heat lens

    for side, name in ((-1, "nozzle_l"), (1, "nozzle_r")):
        spray = m.part(name, parent="body", pivot=(side * 14, -176, -20))
        spray.box(-4, -4, -24, 8, 8, 26)                         # foam barrel
        spray.glow_box(-3, -3, -27, 6, 6, 3)
        tank = m.part(name + "_tank", parent="body", pivot=(side * 26, -168, 8))
        tank.box(-7, -12, -16, 14, 24, 32)                       # chemical tank, hung outside
    return m


def xeno_telepath():
    """Xeno-Telepath: an open cabin with a Martian standing in the glow of its own lens, roofed by a
    glass bell that broadcasts the telepathic carrier wave on two antennae."""
    m = Model("xeno_telepath")
    leg(m, "leg_l", (-18, -150, 0), 72, 78, 5, splay_x=-12, splay_z=2, foot=8)
    leg(m, "leg_r", (18, -150, 0), 72, 78, 5, splay_x=12, splay_z=2, foot=8)
    leg(m, "leg_b", (0, -150, 22), 72, 78, 5, splay_x=0, splay_z=13, foot=8)

    body = m.part("body")
    body.shell(0, -158, 0, 40, 10, 38, cell=12)                  # collar
    body.shell(0, -186, 0, 34, 28, 34, cell=12)                  # drum

    cabin = m.part("head", parent="body", pivot=(0, -186, 0))
    cabin.box(-16, -30, -13, 32, 30, 26)                         # cabin shell
    cabin.glow_box(-13, -26, -14, 26, 20, 1)                     # the glow it stands in
    cabin.box(-16, -32, -13, 32, 3, 26)                          # cabin roof

    pilot = m.part("pilot", parent="head", pivot=(0, 0, 0))
    pilot.box(-8, -16, -17, 16, 18, 10)                          # the Martian, in front of the glow
    pilot.box(-10, -6, -16, 20, 6, 14)                           # its tentacles
    pilot.glow_box(-6, -13, -18, 4, 4, 2)                        # eyes through the glass
    pilot.glow_box(2, -13, -18, 4, 4, 2)

    bell = m.part("dome", parent="body", pivot=(0, -218, 0))
    bell.dome(0, -16, 0, 26, 26, ((6, 24, 24), (6, 16, 16), (4, 8, 8)), cell=10)

    for side, name in ((-1, "antenna_l"), (1, "antenna_r")):
        ant = m.part(name, parent="body", pivot=(side * 15, -216, 0), rot=(-0.2, 0.0, -side * 0.35))
        ant.box(-1, -30, -1, 2, 30, 2)                           # whip antenna, rising
        eye(m, name + "_tip", name, (0, -30, 0), (5, 5))
    return m


def cameron_tripod():
    """Lou Cameron's machine: an enormous oval glass lens in a gold rim, carried on a slim neck above a
    ball jointed body, on three thick tubular legs with a whip antenna."""
    m = Model("cameron_tripod")
    for index, (x, z, sx, sz) in enumerate(((-20, -12, -7, -6), (20, -12, 7, -6), (0, 25, 0, 7))):
        leg(m, f"leg{index}", (x, -176, z), 80, 86, 6, splay_x=sx, splay_z=sz, foot=10)

    neck = m.part("neck", parent="root", pivot=(0, -186, 0))
    neck.shell(0, 0, 0, 20, 26, 20, cell=10)

    body = m.part("body", parent="neck", pivot=(0, -22, 0))
    body.shell(0, 0, 0, 34, 20, 32, cell=11)
    body.shell(0, 18, 0, 26, 10, 24, cell=9)
    body.shell(0, -12, 0, 24, 14, 22, cell=9)

    for index, (x, z) in enumerate(((-14, -10), (12, -10), (0, 12))):
        spike = m.part(f"spike{index}", parent="body", pivot=(x, 26, z))
        spike.box(-2, 0, -2, 4, 10, 4)
        spike.box(-1, 10, -1, 2, 6, 2)

    head = m.part("head", parent="body", pivot=(0, -4, -8), rot=(-16.0, 0.0, 0.0))
    head.shell(0, -32, 0, 44, 34, 12, cell=11)
    head.box(-16, -30, -8, 32, 28, 3)
    head.glow_box(-5, -26, -9, 10, 12, 2)
    head.box(-24, -36, -5, 5, 32, 10)
    head.box(19, -36, -5, 5, 32, 10)
    head.box(-24, -40, -5, 48, 5, 10)
    head.box(-24, -5, -5, 48, 5, 10)
    nozzle = m.part("nozzle", parent="head", pivot=(0, -2, -8))
    nozzle.box(-3, 0, -10, 6, 6, 14)
    nozzle.box(-6, -1, -16, 12, 8, 6)
    nozzle.glow_box(-2, 1, -15, 4, 4, 2)

    antenna = m.part("antenna", parent="body", pivot=(9, -6, 8))
    antenna.box(-1, -44, -1, 2, 44, 2)
    eye(m, "antenna_tip", "antenna", (0, -46, 0), (4, 4))

    for side, name in ((-1, "pod_l"), (1, "pod_r")):
        pod = m.part(name, parent="body", pivot=(side * 18, 4, 0))
        pod.box(side * 2 if side > 0 else side * 2 - 6, -4, -5, 6, 8, 10)
    return m

def gorey_tripod():
    """Edward Gorey's saucer: a wide dark disc crowned with two yellow eyes, standing on three thick
    ringed legs and fringed with a halo of thin arms that reach out and up."""
    m = Model("gorey_tripod")
    for index, (x, z, sx, sz) in enumerate(((-22, -14, -6, -6), (22, -14, 6, -6), (0, 24, 0, 7))):
        leg(m, f"leg{index}", (x, -178, z), 88, 94, 6, splay_x=sx, splay_z=sz, foot=9)
        rings = m.part(f"leg{index}_rings", parent=f"leg{index}", pivot=(0, 0, 0))
        for step in (10, 26, 42, 58):
            rings.box(-4, step, -4, 8, 3, 8)

    body = m.part("body")
    body.shell(0, -208, 0, 76, 10, 72, cell=19)
    body.shell(0, -218, 0, 62, 10, 58, cell=16)
    body.shell(0, -226, 0, 40, 8, 38, cell=14)
    body.shell(0, -198, 0, 30, 10, 28, cell=10)
    eye(m, "eye_l", "body", (-8, -232, -8), (9, 8))
    eye(m, "eye_r", "body", (8, -232, -8), (9, 8))

    for index in range(8):
        angle = 6.283185307179586 * index / 8.0
        arc_arm(m, f"arm{index}_", "body",
                (int(round(34 * math.cos(angle))), -214, int(round(34 * math.sin(angle)))),
                yaw=index * 45.0, length=96, segments=5, lift=-76.0, curl=-13.0, thick=2)
    return m

def wayne_tripod():
    """Jeff Wayne's war machine: a polished silver egg with two green eyes on the crown, long booms
    carrying trumpet ray projectors and three banded legs."""
    m = Model("wayne_tripod")
    for index, (x, z, sx, sz) in enumerate(((-19, -8, -6, -5), (19, -8, 6, -5), (0, 24, 0, 7))):
        leg(m, f"leg{index}", (x, -198, z), 90, 96, 6, splay_x=sx, splay_z=sz, foot=9)
        bands = m.part(f"leg{index}_bands", parent=f"leg{index}", pivot=(0, 0, 0))
        for step in (8, 24, 40, 56):
            bands.box(-4, step, -4, 9, 3, 9)

    body = m.part("body")
    rounded_hull(body, 0, -262, 0, 42, 62, 42, layers=7, curve=0.78, cell=10)
    rounded_hull(body, 0, -196, 0, 26, 14, 26, layers=2, curve=0.3, cell=8)
    eye(m, "eye_l", "body", (-8, -272, -15), (11, 9))
    eye(m, "eye_r", "body", (8, -272, -15), (11, 9))

    for side, name in ((-1, "boom_l"), (1, "boom_r")):
        boom = m.part(name, parent="body", pivot=(side * 20, -252, 0), rot=(0.0, 0.0, -side * 12.0))
        boom.box(side * 4 if side > 0 else side * 4 - 34, -3, -3, 34, 6, 6)
        horn = m.part(name + "_horn", parent=name, pivot=(side * 38, 0, 0), rot=(0.0, 0.0, side * 18.0))
        horn.box(-6, -6, -6, 12, 12, 12)
        horn.box(-9, -9, -12, 18, 18, 6)
        horn.box(-10, -10, -14, 20, 20, 2)
        horn.glow_box(-5, -5, -16, 10, 10, 2)

    beak = m.part("beak", parent="body", pivot=(0, -206, -12))
    beak.box(-4, 0, -10, 8, 8, 14)
    beak.box(-6, 2, -16, 12, 6, 6)
    return m


def goble_tripod():
    """Warwick Goble, 1906: a wide riveted saucer with a dark equator band, standing on three lattice
    girders, carrying a crane jib with a hook, iron tongs and a glowing basket."""
    m = Model("goble_tripod")
    for index, (x, z, sx, sz) in enumerate(((-27, -18, -6, -5), (27, -18, 6, -5), (0, 29, 0, 6))):
        name = f"leg{index}"
        upper = m.part(name, pivot=(x, -196, z), rot=(sz * 0.04, 0.0, -sx * 0.04))
        upper.box(-2, 0, -2, 4, 74, 4)
        for rung in (0, 16, 32, 48, 62):
            upper.box(-8, rung, -2, 6, 2, 4)
            upper.box(2, rung + 8, -2, 6, 2, 4)
        shin = m.part(name + "_shin", parent=name, pivot=(0, 74, 0), rot=(0.0, 0.0, -sx * 0.02))
        shin.box(-2, 0, -2, 4, 72, 4)
        for rung in (8, 26, 44, 58):
            shin.box(-7, rung, -2, 5, 2, 4)
            shin.box(2, rung + 10, -2, 5, 2, 4)
        claw_foot(m, name + "_foot", name + "_shin", (0, 70, 0), size=8, spread=5)

    body = m.part("body")
    body.shell(0, -220, 0, 74, 10, 70, cell=19)
    body.shell(0, -210, 0, 54, 10, 50, cell=14)
    body.shell(0, -200, 0, 34, 10, 32, cell=12)
    band = m.part("band", parent="body", pivot=(0, -216, 0))
    band.box(-37, -2, -35, 74, 5, 70)
    fin = m.part("fin", parent="body", pivot=(0, -230, 16))
    fin.box(-8, -10, -4, 16, 14, 22)
    fin.box(-1, -34, 4, 3, 26, 3)

    crane = m.part("crane", parent="body", pivot=(0, -224, -30))
    crane.box(-2, -3, -46, 4, 5, 50)
    crane.box(-2, 0, -48, 4, 28, 4)
    hook = m.part("claw", parent="crane", pivot=(0, 28, -48))
    hook.box(-3, 0, -3, 6, 6, 6)
    hook.box(-3, 6, -3, 2, 10, 2)
    hook.box(1, 14, -3, 2, 3, 2)

    tongs = m.part("tongs", parent="crane", pivot=(-14, 20, -46))
    tongs.box(-1, 0, -1, 2, 18, 2)
    tongs.box(-8, 16, -1, 8, 2, 2)
    tongs.box(-14, 16, -1, 2, 12, 2)
    tongs.box(6, 16, -1, 2, 12, 2)

    basket = m.part("basket", parent="crane", pivot=(16, 28, -32))
    basket.box(-7, 0, -7, 14, 14, 14)
    basket.glow_box(-6, 2, -8, 12, 8, 2)
    basket.box(-2, 14, -2, 4, 18, 4)
    return m


def bbc2019_tripod():
    """The 2019 BBC machine: a small dark pod studded with cold blue lights, held high by two long
    arched limbs that bow outwards like a crab's."""
    m = Model("bbc2019_tripod")
    leg(m, "limb_l", (-14, -238, -4), 112, 118, 5, splay_x=-11, splay_z=-4, foot=9)
    leg(m, "limb_r", (14, -238, -4), 112, 118, 5, splay_x=11, splay_z=-4, foot=9)
    leg(m, "limb_b", (0, -238, 14), 112, 118, 5, splay_x=0, splay_z=7, foot=9)

    body = m.part("body")
    body.shell(0, -262, 0, 32, 22, 30, cell=11)
    body.shell(0, -240, 0, 26, 22, 24, cell=10)
    body.shell(0, -272, 0, 22, 10, 20, cell=9)

    lamps = ((-9, -262, -16), (0, -258, -18), (9, -262, -16),
             (-7, -250, -16), (7, -250, -16), (0, -244, -14), (-11, -252, -8), (11, -252, -8))
    for index, (x, y, z) in enumerate(lamps):
        eye(m, f"lamp{index}", "body", (x, y, z), (5, 5))

    for index, x in enumerate((-7, 0, 7)):
        tentacle(m, f"tendril{index}_", "body", (x, -240, -10), 24, segments=2, droop=6.0,
                 spread=index - 1, thick=2)
    return m


def attack2021_tripod():
    """The 2021 machine: a swept glider canopy over a glowing mechanical core, two raptor legs and a
    lashing tail."""
    m = Model("attack2021_tripod")
    for index, (x, sx) in enumerate(((-16, -8), (16, 8))):
        barbed_leg(m, f"leg{index}", (x, -196, -2), 100, 104, 6, splay_x=sx, splay_z=-2, foot=9,
                   barbs=4, barb=4)

    body = m.part("body")
    body.shell(0, -232, 0, 26, 34, 26, cell=13)
    body.shell(0, -248, 0, 18, 16, 18, cell=9)
    body.glow_box(-6, -222, -14, 12, 10, 2)

    for side, name in ((-1, "canopy_l"), (1, "canopy_r")):
        canopy = m.part(name, parent="body", pivot=(side * 8, -252, 0), rot=(0.0, 0.0, -side * 14.0))
        canopy.box(side * 2 if side > 0 else side * 2 - 44, -3, -24, 44, 5, 46)
        canopy.box(side * 40 if side > 0 else side * 40 - 10, -3, -14, 10, 4, 26)
        canopy.box(side * 10 if side > 0 else side * 10 - 26, 1, -18, 26, 3, 20)

    head = m.part("head", parent="body", pivot=(0, -238, -12))
    head.box(-8, -8, -10, 16, 12, 16)
    eye(m, "eye", "head", (0, -2, -11), (11, 5))

    tail = m.part("tail", parent="body", pivot=(0, -206, 12))
    tail.box(-2, 0, 0, 4, 4, 46)
    tail.box(-1, 2, 44, 2, 2, 26)
    return m


def attack_tripod():
    """War of the Worlds: The Attack: a small crown of red eyes hung with a curtain of long feelers,
    carried by three enormous thin legs."""
    m = Model("attack_tripod")
    for index, (x, z, sx, sz) in enumerate(((-18, -4, -6, -3), (18, -4, 6, -3), (0, 24, 0, 7))):
        leg(m, f"leg{index}", (x, -252, z), 118, 124, 5, splay_x=sx, splay_z=sz, foot=8)

    body = m.part("body")
    body.shell(0, -282, 0, 28, 26, 28, cell=10)
    body.shell(0, -292, 0, 20, 12, 20, cell=8)
    for index, (x, y) in enumerate(((-8, -286), (0, -288), (8, -286), (-4, -278), (4, -278))):
        eye(m, f"eye{index}", "body", (x, y, -14), (5, 5))

    for index in range(10):
        angle = 6.283185307179586 * index / 10
        x = int(round(12 * math.cos(angle)))
        z = int(round(12 * math.sin(angle)))
        tentacle(m, f"arm{index}_", "body", (x, -268, z), 62, segments=5, droop=8.0,
                 spread=(index - 4.5) * 9.0, thick=2)
    return m


def vegetable_tripod():
    """The 'vegetable' machine: a pale cylinder with a pointed cap, portholes, two long arms ending in
    claws and three segmented legs."""
    m = Model("vegetable_tripod")
    for index, (x, z, sx, sz) in enumerate(((-16, -8, -8, -4), (16, -8, 8, -4), (0, 20, 0, 8))):
        leg(m, f"leg{index}", (x, -196, z), 98, 104, 5, splay_x=sx, splay_z=sz, foot=8)
        claw_foot(m, f"leg{index}_claw", f"leg{index}_shin", (0, 92, 0), size=8, spread=4)
        rings = m.part(f"leg{index}_rings", parent=f"leg{index}", pivot=(0, 0, 0))
        for step in (16, 34, 52, 70):
            rings.box(-4, step, -4, 8, 3, 8)

    body = m.part("body")
    for index in range(4):
        body.shell(0, -200 - index * 12, 0, 30, 12, 30, cell=15)
    cap = m.part("head", parent="body", pivot=(0, -248, 0))
    for index, width in enumerate((34, 30, 24, 16, 8)):
        cap.box(-width // 2, -index * 8, -width // 2, width, 8, width)
    cap.glow_box(-2, -42, -2, 4, 6, 2)
    for index, side in enumerate((-1, 1)):
        fin = m.part("fin_l" if side < 0 else "fin_r", parent="head", pivot=(side * 15, -6, 0))
        fin.box(0 if side > 0 else -10, -3, -8, 10, 5, 16)

    ports = m.part("ports", parent="body", pivot=(0, -212, -15))
    ports.box(-8, -4, -2, 7, 7, 3)
    ports.box(2, -4, -2, 7, 7, 3)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        arm = m.part(name, parent="body", pivot=(side * 16, -216, 0), rot=(0.0, 0.0, -side * 34.0))
        arm.box(-2, 0, -2, 5, 46, 5)
        fore = m.part(name + "_fore", parent=name, pivot=(0, 44, 0), rot=(-12.0, 0.0, side * 46.0))
        fore.box(-2, 0, -2, 4, 34, 4)
        claw_foot(m, name + "_claw", name + "_fore", (0, 32, 0), size=7, spread=4)
    return m


def mexican_tripod():
    """The Mexican poster machine: a bronze bulb with two huge yellow eyes, a red lamp on the cap and
    two black feeler arms that end in barbed claw heads."""
    m = Model("mexican_tripod")
    for index, (x, z, sx, sz) in enumerate(((-21, -6, -7, -3), (21, -6, 7, -3), (0, 24, 0, 7))):
        leg(m, f"leg{index}", (x, -196, z), 98, 104, 6, splay_x=sx, splay_z=sz, foot=8)

    body = m.part("body")
    rounded_hull(body, 0, -262, 0, 46, 40, 44, layers=5, curve=0.5, cell=11)
    body.shell(0, -222, 0, 24, 12, 24, cell=10)
    eye(m, "eye_l", "body", (-10, -248, -20), (13, 11))
    eye(m, "eye_r", "body", (10, -248, -20), (13, 11))

    cap = m.part("cap", parent="body", pivot=(0, -274, 0))
    cap.box(-9, -10, -9, 18, 10, 18)
    cap.glow_box(-3, -14, -3, 6, 4, 6)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        tentacle(m, f"{name}_", "body", (side * 20, -244, -8), 54, segments=5, droop=12.0,
                 spread=side * 16.0, thick=3)
        claw = m.part(name + "_head", parent=f"{name}_5_body", pivot=(0, 8, 0))
        claw.box(-5, 0, -3, 10, 5, 6)
        claw.box(-6, 3, -9, 3, 9, 3)
        claw.box(3, 3, -9, 3, 9, 3)
        claw.box(-2, 3, -7, 4, 7, 2)
        claw.glow_box(-1, 5, -8, 2, 3, 1)
    return m


def four_legged_tripod():
    """The four legged machine: a copper saucer crowned with two yellow eyes, a tapering waist and four
    long angular legs, with a segmented tail ending in a claw."""
    m = Model("four_legged_tripod")
    for index, (x, z, sx, sz) in enumerate(((-24, -22, -8, -7), (24, -22, 8, -7),
                                            (-24, 22, -8, 7), (24, 22, 8, 7))):
        leg(m, f"leg{index}", (x, -198, z), 100, 104, 6, splay_x=sx, splay_z=sz, foot=0, knee=0.45)
        claw_foot(m, f"leg{index}_claw", f"leg{index}_shin", (0, 100, 0), size=10, spread=3)

    body = m.part("body")
    body.shell(0, -246, 0, 62, 10, 60, cell=16)
    body.shell(0, -256, 0, 46, 10, 44, cell=12)
    body.shell(0, -262, 0, 28, 8, 26, cell=9)
    eye(m, "eye_l", "body", (-7, -264, -9), (9, 8))
    eye(m, "eye_r", "body", (7, -264, -9), (9, 8))

    waist = m.part("waist", parent="body", pivot=(0, -238, 0))
    for index, width in enumerate((30, 24, 18, 12)):
        waist.box(-width // 2, index * 8, -width // 2, width, 8, width)
    waist.glow_box(-2, 4, -9, 4, 6, 2)

    tail = m.part("tail", parent="body", pivot=(0, -212, 0))
    tail.box(-4, 0, -4, 8, 12, 8)
    tail.box(-3, 10, -3, 6, 14, 6)
    tail.box(-2, 22, -2, 4, 16, 4)
    claw = m.part("tail_claw", parent="tail", pivot=(0, 36, 0))
    claw.box(-6, 0, -4, 4, 4, 10)
    claw.box(2, 0, -4, 4, 4, 10)
    return m


def series1998_tripod():
    """The 1998 series machine: a wide blue-steel mantle that droops at the rim around a burning orange
    bowl, carried by three long smooth legs."""
    m = Model("series1998_tripod")
    for index, (x, z, sx, sz) in enumerate(((-24, -10, -8, -5), (24, -10, 8, -5), (0, 26, 0, 8))):
        leg(m, f"leg{index}", (x, -218, z), 110, 116, 4, splay_x=sx, splay_z=sz, foot=8)
        rings = m.part(f"leg{index}_rings", parent=f"leg{index}", pivot=(0, 0, 0))
        for step in (20, 48, 76, 100):
            rings.box(-3, step, -3, 7, 3, 7)

    body = m.part("body")
    body.shell(0, -234, 0, 70, 8, 66, cell=18)
    body.shell(0, -242, 0, 58, 8, 54, cell=15)
    body.shell(0, -250, 0, 42, 8, 40, cell=11)
    body.shell(0, -260, 0, 20, 8, 20, cell=10)

    for index in range(4):
        side = -1 if index < 2 else 1
        axis = index % 2
        petal = m.part(f"petal{index}", parent="body",
                       pivot=(side * 30 if axis == 0 else 0, -230, side * 30 if axis == 1 else 0),
                       rot=(0.0, 0.0, -side * 22.0))
        if axis == 0:
            petal.box(0 if side > 0 else -26, 0, -8, 26, 5, 18)
        else:
            petal.box(-8, -2, 0 if side > 0 else -26, 18, 5, 26)

    bowl = m.part("bowl", parent="body", pivot=(0, -252, 0))
    bowl.box(-16, -6, -14, 32, 6, 28)
    bowl.box(-12, -10, -10, 24, 5, 20)
    bowl.glow_box(-10, -8, -12, 20, 3, 24)

    for index, x in enumerate((-9, 0, 9)):
        tentacle(m, f"feel{index}_", "body", (x, -232, 10), 30, segments=3, droop=10.0,
                 spread=(index - 1) * 6.0, thick=2)
    return m


def pendragon_tripod():
    """Pendragon's beetle: a hammered golden carapace with a plated mask and a proboscis, four hooked
    legs and a mane of braided feeler whips."""
    m = Model("pendragon_tripod")
    for index, (x, z, sx, sz) in enumerate(((-24, -16, -7, -6), (24, -16, 7, -6),
                                            (-24, 16, -7, 6), (24, 16, 7, 6))):
        leg(m, f"leg{index}", (x, -206, z), 96, 100, 7, splay_x=sx, splay_z=sz, foot=0)
        claw_foot(m, f"leg{index}_claw", f"leg{index}_shin", (0, 98, 0), size=10, spread=4)

    body = m.part("body")
    rounded_hull(body, 0, -252, 0, 48, 34, 46, layers=5, curve=0.6, cell=12)
    rounded_hull(body, 0, -216, 0, 32, 12, 30, layers=2, curve=0.35, cell=10)

    head = m.part("head", parent="body", pivot=(0, -246, -22))
    head.box(-15, -14, -10, 30, 28, 12)
    for index, x in enumerate((-11, -5, 2, 8)):
        head.box(x, -10, -13, 6, 20, 4)
    head.box(-13, -16, -8, 26, 4, 10)
    proboscis = m.part("proboscis", parent="head", pivot=(0, 12, -6))
    proboscis.box(-4, 0, -8, 8, 8, 12)
    proboscis.box(-2, -2, -18, 4, 8, 12)
    for side, name in ((-1, "jaw_l"), (1, "jaw_r")):
        jaw = m.part(name, parent="head", pivot=(side * 11, 12, -8))
        jaw.box(-2, 0, -10, 4, 4, 12)
        jaw.box(-2, -4, -12, 4, 4, 4)
    eye(m, "eye_l", "body", (-10, -236, -20), (8, 6))
    eye(m, "eye_r", "body", (10, -236, -20), (8, 6))

    for index in range(6):
        arc_arm(m, f"braid{index}_", "body", (0, -234, -10), yaw=index * 60.0 + 30.0,
                length=84, segments=5, lift=-58.0, curl=-20.0, thick=3)
    return m

def nextwave_cyborg_tripod():
    """The Asylum cyborg: a small charred head with orange eyes and a molten core, standing on three
    enormously long legs plated with scavenged scrap."""
    m = Model("nextwave_cyborg_tripod")
    for index, (x, z, sx, sz) in enumerate(((-18, -10, -7, -5), (18, -10, 7, -5), (0, 24, 0, 7))):
        barbed_leg(m, f"leg{index}", (x, -246, z), 118, 122, 6, splay_x=sx, splay_z=sz, foot=9,
                   barbs=4, barb=4)

    body = m.part("body")
    rounded_hull(body, 0, -276, 0, 32, 34, 32, layers=4, curve=0.45, cell=11)
    rounded_hull(body, 0, -246, 0, 22, 12, 22, layers=2, curve=0.3, cell=8)
    eye(m, "eye_l", "body", (-7, -272, -16), (7, 5))
    eye(m, "eye_r", "body", (7, -272, -16), (7, 5))
    body.glow_box(-5, -256, -12, 10, 8, 2)

    for index, (x, y, z, dx, dy, dz) in enumerate(((-18, -268, -6, 9, 16, 8),
                                                    (16, -286, 2, 8, 10, 9),
                                                    (-3, -240, 10, 10, 7, 8),
                                                    (11, -252, -8, 6, 12, 6))):
        scrap = m.part(f"scrap{index}", parent="body", pivot=(x, y, z))
        scrap.box(-dx // 2, -dy // 2, -dz // 2, dx, dy, dz)

    for index, x in enumerate((-7, 0, 7)):
        tentacle(m, f"cable{index}_", "body", (x, -242, -9), 26, segments=2, droop=8.0,
                 spread=index - 1, thick=2)
    return m


def true2012_tripod():
    """True War of the Worlds 2012: a heavy copper engine bolted on top of three braced legs, with a
    side cannon and cables hanging off the block."""
    m = Model("true2012_tripod")
    for index, (x, z, sx, sz) in enumerate(((-25, -16, -7, -4), (25, -16, 7, -4), (0, 27, 0, 7))):
        leg(m, f"leg{index}", (x, -196, z), 96, 100, 5, splay_x=sx, splay_z=sz, foot=9)
        brace = m.part(f"leg{index}_brace", parent=f"leg{index}", pivot=(0, 40, 0))
        brace.box(-1, -22, -1, 2, 26, 2)

    body = m.part("body")
    rounded_hull(body, 0, -238, 0, 52, 40, 46, layers=4, curve=0.3, cell=13)
    body.shell(-12, -258, -10, 26, 12, 24, cell=13)
    body.shell(12, -254, 6, 22, 12, 22, cell=11)
    eye(m, "eye", "body", (0, -230, -22), (13, 9))

    gun = m.part("gun", parent="body", pivot=(26, -238, -4))
    gun.box(0, -5, -5, 24, 10, 10)
    gun.box(24, -3, -3, 10, 6, 6)
    gun.box(8, 5, -3, 6, 6, 6)

    stack = m.part("stack", parent="body", pivot=(-8, -262, 8))
    stack.box(-4, -16, -4, 8, 16, 8)
    stack.box(-6, -20, -6, 12, 4, 12)

    pipes = m.part("pipes", parent="body", pivot=(0, -256, -4))
    for index, x in enumerate((-18, 18)):
        pipes.box(x, -6, -10, 5, 14, 20)
    for index, x in enumerate((-14, -2, 10)):
        tentacle(m, f"cable{index}_", "body", (x, -204, 10), 34, segments=3, droop=6.0,
                 spread=index - 1, thick=2)
    return m


def steam_tower_tripod():
    """Steam Towers: a dark riveted dome with a brass equator, hanging between two brass yoke arms
    drilled with round holes, on three thin legs."""
    m = Model("steam_tower_tripod")
    for index, (x, z, sx, sz) in enumerate(((-26, -16, -7, -4), (26, -16, 7, -4), (0, 28, 0, 6))):
        leg(m, f"leg{index}", (x, -240, z), 108, 112, 4, splay_x=sx, splay_z=sz, foot=9)
        rings = m.part(f"leg{index}_rings", parent=f"leg{index}", pivot=(0, 0, 0))
        for step in (14, 40, 66, 92):
            rings.box(-3, step, -3, 7, 3, 7)

    body = m.part("body")
    for index, width in enumerate((44, 40, 30, 16)):
        body.shell(0, -276 + index * 10, 0, width, 10, width, cell=max(8, width // 2))
    body.shell(0, -246, 0, 46, 8, 46, cell=23)
    body.shell(0, -240, 0, 34, 6, 34, cell=17)

    band = m.part("band", parent="body", pivot=(0, -248, 0))
    band.box(-24, -3, -24, 48, 6, 48)
    band.glow_box(-13, -1, -25, 10, 4, 2)
    band.glow_box(4, -1, -25, 10, 4, 2)

    for side, name in ((-1, "yoke_l"), (1, "yoke_r")):
        yoke = m.part(name, parent="body", pivot=(side * 22, -262, 0), rot=(0.0, 0.0, -side * 6.0))
        for index in range(4):
            x0 = side * (index * 14)
            yoke.box(min(x0, x0 + side * 9), -5, -5, 9, 10, 10)
        yoke.box(side * 50 if side > 0 else side * 50 - 12, -2, -2, 12, 4, 4)
        yoke.glow_box(side * 6, -3, -6, 5, 5, 2)
        yoke.glow_box(side * 34, -3, -6, 5, 5, 2)

    for index, x in enumerate((-8, 0, 8)):
        vent = m.part(f"vent{index}", parent="body", pivot=(x, -238, -10))
        vent.glow_box(-2, 0, -2, 5, 3, 5)
    return m


def alien_dawn_tripod():
    """Alien Dawn: a huge ribbed drum of a head with a blue light bar across its face, riding on four
    long thin legs."""
    m = Model("alien_dawn_tripod")
    for index, (x, z, sx, sz) in enumerate(((-26, -18, -7, -5), (26, -18, 7, -5),
                                            (-26, 18, -7, 5), (26, 18, 7, 5))):
        leg(m, f"leg{index}", (x, -232, z), 106, 112, 4, splay_x=sx, splay_z=sz, foot=9)
        knuckle = m.part(f"leg{index}_knee", parent=f"leg{index}", pivot=(0, 104, 0))
        knuckle.box(-4, -3, -4, 9, 6, 9)

    body = m.part("body")
    for index, width in enumerate((44, 54, 58, 56, 46)):
        body.shell(0, -256 - index * 9, 0, width, 9, 50, cell=max(8, width // 3))
    body.shell(0, -298, 0, 28, 8, 26, cell=9)
    body.shell(0, -252, 0, 30, 8, 28, cell=10)

    bar = m.part("lightbar", parent="body", pivot=(0, -268, -25))
    bar.glow_box(-20, -5, -2, 40, 9, 3)
    bar.box(-22, -8, -3, 44, 3, 5)

    for index, (x, y) in enumerate(((-14, -290), (0, -294), (14, -290))):
        eye(m, f"port{index}", "body", (x, y, -14), (5, 5))

    for index, side in enumerate((-1, 1)):
        antenna = m.part(f"antenna{index}", parent="body", pivot=(side * 18, -300, 0))
        antenna.box(-1, -30, -1, 2, 30, 2)
        antenna.box(-7, -32, -1, 7, 2, 2)
    return m


def alien_dawn_mk2_tripod():
    """The second Alien Dawn machine: a mirrored scoop with a slotted grille face and a flared funnel,
    on three slender ringed legs."""
    m = Model("alien_dawn_mk2_tripod")
    for index, (x, z, sx, sz) in enumerate(((-22, -12, -8, -5), (22, -12, 8, -5), (0, 26, 0, 8))):
        leg(m, f"leg{index}", (x, -228, z), 108, 112, 4, splay_x=sx, splay_z=sz, foot=9)
        rings = m.part(f"leg{index}_rings", parent=f"leg{index}", pivot=(0, 0, 0))
        for step in (18, 44, 70, 96):
            rings.box(-3, step, -3, 7, 3, 7)

    body = m.part("body")
    for index, width in enumerate((26, 34, 40, 44, 42, 34)):
        body.shell(0, -282 + index * 9, 0, width, 9, width, cell=max(8, width // 2))
    body.shell(0, -240, 0, 20, 10, 20, cell=10)

    scoop = m.part("scoop", parent="body", pivot=(0, -276, -18))
    scoop.box(-16, -2, -8, 32, 26, 10)
    for index in range(4):
        scoop.box(-13, 2 + index * 6, -11, 26, 3, 3)
    scoop.glow_box(-16, -4, -10, 32, 2, 2)
    scoop.box(-18, 24, -10, 36, 4, 12)

    horn = m.part("horn", parent="body", pivot=(0, -250, 18))
    horn.box(-8, -8, 0, 16, 16, 12)
    horn.box(-6, -6, 10, 12, 12, 8)

    for index, side in enumerate((-1, 1)):
        antenna = m.part(f"antenna{index}", parent="body", pivot=(side * 14, -290, 4))
        antenna.box(-1, -28, -1, 2, 28, 2)
        antenna.box(-6, -30, -1, 6, 2, 2)
    return m


def scary_movie_tripod():
    """Scary Movie 4: a swept dark canopy carrying one huge lamp lens between two smaller ones, on thin
    riveted legs with cylindrical feet."""
    m = Model("scary_movie_tripod")
    for index, (x, z, sx, sz) in enumerate(((-24, -14, -8, -5), (24, -14, 8, -5), (0, 26, 0, 8))):
        leg(m, f"leg{index}", (x, -216, z), 102, 108, 4, splay_x=sx, splay_z=sz, foot=0)
        boot = m.part(f"leg{index}_boot", parent=f"leg{index}_shin", pivot=(0, 104, 0))
        boot.box(-5, 0, -5, 10, 10, 10)
        boot.box(-6, 8, -6, 12, 4, 12)

    body = m.part("body")
    body.shell(0, -248, 0, 52, 10, 44, cell=17)
    body.shell(0, -238, 0, 34, 8, 30, cell=11)
    body.shell(0, -222, 0, 32, 16, 28, cell=11)
    body.shell(0, -232, 0, 22, 8, 20, cell=10)

    for side, name in ((-1, "cape_l"), (1, "cape_r")):
        cape = m.part(name, parent="body", pivot=(side * 22, -252, 4), rot=(0.0, 0.0, -side * 10.0))
        cape.box(0 if side > 0 else -30, -2, -20, 30, 4, 40)
        cape.box(side * 26 if side > 0 else side * 26 - 12, -2, -12, 12, 3, 24)

    lamp = m.part("lamp", parent="body", pivot=(0, -246, -20))
    lamp.box(-12, -12, -6, 24, 24, 8)
    lamp.glow_box(-10, -10, -8, 20, 20, 2)
    lamp.box(-13, -13, -1, 26, 26, 3)
    for index, side in enumerate((-1, 1)):
        small = m.part(f"side_lamp{index}", parent="body", pivot=(side * 20, -244, -18))
        small.box(-5, -5, -3, 10, 10, 5)
        small.glow_box(-4, -4, -5, 8, 8, 2)

    fin = m.part("fin", parent="body", pivot=(0, -252, 18))
    fin.box(-2, -22, 0, 4, 22, 18)
    return m


def ussr_book_tripod():
    """The Soviet book illustration: a dark bell with a small finial and two side cups, hanging on three
    enormously long legs."""
    m = Model("ussr_book_tripod")
    for index, (x, z, sx, sz) in enumerate(((-15, -6, -6, -3), (15, -6, 6, -3), (0, 20, 0, 7))):
        leg(m, f"leg{index}", (x, -246, z), 124, 128, 4, splay_x=sx, splay_z=sz, foot=7)

    body = m.part("body")
    for index, width in enumerate((14, 20, 28, 38, 48, 52)):
        body.shell(0, -308 + index * 10, 0, width, 10, width, cell=max(7, width // 2))
    body.shell(0, -314, 0, 10, 6, 10, cell=5)
    body.shell(0, -320, 0, 6, 6, 6, cell=3)
    body.glow_box(-9, -262, -27, 18, 5, 2)

    for side, name in ((-1, "cup_l"), (1, "cup_r")):
        cup = m.part(name, parent="body", pivot=(side * 26, -278, 0))
        cup.box(side * 4 if side > 0 else side * 4 - 10, -6, -6, 10, 12, 12)
        cup.box(side * 12 if side > 0 else side * 12 - 4, -3, -3, 4, 6, 6)

    for index, x in enumerate((-6, 4)):
        tentacle(m, f"chain{index}_", "body", (x, -252, -8), 30, segments=3, droop=4.0,
                 spread=index - 0.5, thick=2)
    return m


def annihilation_tripod():
    """War of the Worlds: Annihilation: a boxy two legged machine with cyan panels and two long scythe
    arms folded in front of it."""
    m = Model("annihilation_tripod")
    for index, side in enumerate((-1, 1)):
        barbed_leg(m, f"leg{index}", (side * 18, -222, 0), 112, 116, 7, splay_x=side * 6,
                   splay_z=-2, foot=11, barbs=3, barb=5)
        hip = m.part(f"hip{index}", parent="root", pivot=(side * 18, -222, 0))
        hip.box(-8, -10, -7, 16, 12, 15)

    body = m.part("body")
    body.shell(0, -270, 0, 36, 16, 32, cell=18)
    body.shell(0, -252, 0, 30, 14, 28, cell=15)
    body.shell(0, -238, 0, 24, 12, 24, cell=12)
    body.glow_box(-13, -266, -17, 9, 12, 2)
    body.glow_box(4, -266, -17, 9, 12, 2)

    head = m.part("head", parent="body", pivot=(0, -276, -12))
    head.box(-10, -12, -8, 20, 12, 14)
    head.glow_box(-7, -8, -10, 14, 5, 2)
    head.box(-2, -22, -3, 4, 12, 6)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        shoulder = m.part(name, parent="body", pivot=(side * 17, -266, 0), rot=(6.0, 0.0, -side * 16.0))
        shoulder.box(-3, 0, -3, 6, 44, 6)
        blade = m.part(name + "_blade", parent=name, pivot=(0, 42, 0), rot=(46.0, 0.0, -side * 16.0))
        blade.box(-3, 0, -3, 6, 40, 6)
        blade.box(-3, 34, -26, 6, 7, 28)
        blade.box(-2, 36, -30, 4, 4, 8)
        blade.glow_box(-2, 36, -27, 4, 3, 4)

    for index, z in enumerate((-8, 6)):
        vent = m.part(f"vent{index}", parent="body", pivot=(0, -250, z))
        vent.box(-13, -2, -2, 26, 4, 4)
    return m

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
    ("flyingMachine", finalise(flying_machine(), "flyingMachine")),
    ("electric", finalise(electric_machine(), "electric")),
    ("handling", finalise(handling_machine(), "handling")),
    ("digging", finalise(digging_machine(), "digging")),
    ("tempest", finalise(tempest(), "tempest")),
    ("bombarding", finalise(bombarding_machine(), "bombarding")),
    ("drone", finalise(drone(), "drone")),
    ("humanTripod", finalise(human_tripod(), "humanTripod")),
    # third wave: the new Martian walkers
    ("waterTripod", finalise(water_tripod(), "waterTripod")),
    ("scoutTripod", finalise(scout_tripod(), "scoutTripod")),
    ("ramTripod", finalise(ram_tripod(), "ramTripod")),
    ("uberpod", finalise(uberpod(), "uberpod")),
    ("bunkerTripod", finalise(bunker_tripod(), "bunkerTripod")),
    ("flamerTripod", finalise(flamer_tripod(), "flamerTripod")),
    ("tankTripod", finalise(tank_tripod(), "tankTripod")),
    ("taxiTripod", finalise(taxi_tripod(), "taxiTripod")),
    # fifth wave: the Martian engineers and the psychological weapons
    ("constructionTripod", finalise(construction_tripod(), "constructionTripod")),
    ("smokeTripod", finalise(smoke_tripod(), "smokeTripod")),
    ("bombTripod", finalise(bomb_tripod(), "bombTripod")),
    ("constrictor", finalise(constrictor(), "constrictor")),
    ("xenoTelepath", finalise(xeno_telepath(), "xenoTelepath")),
    # sixth wave: ten machines straight from the artwork
    ("cameron", finalise(cameron_tripod(), "cameron")),
    ("gorey", finalise(gorey_tripod(), "gorey")),
    ("wayne", finalise(wayne_tripod(), "wayne")),
    ("goble", finalise(goble_tripod(), "goble")),
    ("bbc2019", finalise(bbc2019_tripod(), "bbc2019")),
    ("attack2021", finalise(attack2021_tripod(), "attack2021")),
    ("attack", finalise(attack_tripod(), "attack")),
    ("vegetable", finalise(vegetable_tripod(), "vegetable")),
    ("mexican", finalise(mexican_tripod(), "mexican")),
    ("fourLegged", finalise(four_legged_tripod(), "fourLegged")),

    # seventh wave: ten machines rebuilt from the new artwork, and ten brand new walkers
    ("series1998", finalise(series1998_tripod(), "series1998")),
    ("pendragon", finalise(pendragon_tripod(), "pendragon")),
    ("nextwaveCyborg", finalise(nextwave_cyborg_tripod(), "nextwaveCyborg")),
    ("true2012", finalise(true2012_tripod(), "true2012")),
    ("steamTower", finalise(steam_tower_tripod(), "steamTower")),
    ("alienDawn", finalise(alien_dawn_tripod(), "alienDawn")),
    ("alienDawnMk2", finalise(alien_dawn_mk2_tripod(), "alienDawnMk2")),
    ("scaryMovie", finalise(scary_movie_tripod(), "scaryMovie")),
    ("ussrBook", finalise(ussr_book_tripod(), "ussrBook")),
    ("annihilation", finalise(annihilation_tripod(), "annihilation")),
]


# The human army: ordinary bipeds and machines, authored at 16 pixels per block as well.
HUMANS = [
    ("soldier", finalise(human_soldier(), "soldier")),
    ("tank", finalise(human_tank(), "tank")),
    ("fighter", finalise(human_fighter(), "fighter")),
    ("warship", finalise(human_warship(), "warship")),
    # fourth wave: the rest of the human army
    ("helicopter", finalise(human_helicopter(), "helicopter")),
    ("submarine", finalise(human_submarine(), "submarine")),
    ("bomber", finalise(human_bomber(), "bomber")),
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

import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;

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
    public static LayerDefinition bbc(boolean armed) {
        return armed ? bbcRed() : bbcWhite();
    }
}
''')
    path = os.path.join(MODEL_DIR, "TripodModels.java")
    with open(path, "w", encoding="utf-8") as handle:
        handle.write("\n".join(parts))
    print("wrote", path)


def write_humans():
    doc = "The human army: soldiers, tanks, interceptors, warships, gunships, submarines and bombers."
    parts = [HEADER.format(doc=doc, class_name="HumanModels")]
    for method, model in HUMANS:
        parts.append(emit_java(model, method) + "\n")
    parts.append("}\n")
    path = os.path.join(MODEL_DIR, "HumanModels.java")
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
    write_humans()
    write_martians()
