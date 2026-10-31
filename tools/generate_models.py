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
    """A conscript: boots, helmet, rifle."""
    m = Model("human_soldier", texture=(128, 128))
    for side, name in ((-1, "leg_l"), (1, "leg_r")):
        limb = m.part(name, pivot=(side * 3, -16, 0))
        limb.box(-2, 0, -2, 4, 16, 4)
        limb.box(-2, 15, -3, 4, 3, 6)

    body = m.part("body", pivot=(0, -16, 0))
    body.box(-5, -14, -3, 10, 14, 6)
    body.box(-6, -13, -3, 12, 4, 7)

    head = m.part("head", parent="body", pivot=(0, -14, 0))
    head.box(-4, -9, -4, 8, 9, 8)
    head.box(-5, -10, -5, 10, 4, 10)
    eye(m, "eye", "head", (0, -5, -5), (5, 2))

    arm_r = m.part("arm_r", parent="body", pivot=(6, -12, 0))
    arm_r.box(-2, 0, -2, 4, 11, 4)
    arm_r.box(-1, 8, -10, 3, 3, 14)                      # rifle
    arm_r.box(-2, 7, -2, 4, 5, 5)                        # magazine at the hands

    arm_l = m.part("arm_l", parent="body", pivot=(-6, -12, 0))
    arm_l.box(-2, 0, -2, 4, 11, 4)
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
]


# The human army: ordinary bipeds and machines, authored at 16 pixels per block as well.
HUMANS = [
    ("soldier", finalise(human_soldier(), "soldier")),
    ("tank", finalise(human_tank(), "tank")),
    ("fighter", finalise(human_fighter(), "fighter")),
    ("warship", finalise(human_warship(), "warship")),
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


def write_humans():
    doc = "The human army: soldiers, tanks, interceptors and warships."
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
