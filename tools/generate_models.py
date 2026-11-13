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


def ball(part, cx, cy, cz, radius, depth=None, cell=5):
    """Stacked slabs that read as a ball: the ball joints the artwork machines stand on."""
    depth = depth or radius * 2
    steps = max(3, int(radius // 2))
    for index in range(steps):
        t = (index + 0.5) / steps
        factor = math.sqrt(max(0.08, 1.0 - (2.0 * t - 1.0) ** 2))
        width = max(4, int(round(2 * radius * factor)))
        height = max(2, int(round(radius / steps)) + 1)
        y = int(round(cy - radius + 2 * radius * t))
        part.box(cx - width // 2, y, cz - width // 2, width, height, width)
    return part


def collar(part, cx, cy, cz, w, h, d, thickness=3):
    """A square ring of plates: the gold rims, brass bands and collars of the artwork machines."""
    part.box(cx - w // 2, cy, cz - d // 2, w, h, thickness)
    part.box(cx - w // 2, cy, cz + d // 2 - thickness, w, h, thickness)
    part.box(cx - w // 2, cy, cz - d // 2 + thickness, thickness, h, d - 2 * thickness)
    part.box(cx + w // 2 - thickness, cy, cz - d // 2 + thickness, thickness, h, d - 2 * thickness)
    return part


def girder(part, length, width=10, depth=4, rungs=5):
    """Two rails and a ladder of cross braces: the lattice legs of Gobl's saucer."""
    part.box(-width // 2, 0, -depth // 2, 3, length, depth)
    part.box(width // 2 - 3, 0, -depth // 2, 3, length, depth)
    step = max(6, length // max(1, rungs))
    for index in range(rungs + 1):
        part.box(-width // 2, min(length - 2, index * step), -depth // 2, width, 2, depth)
    return part


def girder_leg(model, name, hip, thigh, shin, splay_x=0.0, splay_z=0.0, width=10, rungs=5, foot=0):
    upper = model.part(name, pivot=hip, rot=(splay_z, 0.0, -splay_x))
    girder(upper, thigh, width, rungs=rungs)
    lower = model.part(name + "_shin", parent=name, pivot=(0, thigh, 0),
                       rot=(splay_z * 0.3, 0.0, -splay_x * 0.3))
    girder(lower, shin, width, rungs=rungs)
    if foot:
        lower.box(-foot // 2, shin - 2, -foot // 2, foot, 4, foot)
    return upper


def trumpet(part, x, y, z, length, base, mouth):
    """A flared horn pointing towards -z, the ray projectors of Jeff Wayne's machine."""
    part.box(x - base // 2, y - base // 2, z, base, base, length)
    part.box(x - mouth // 2, y - mouth // 2, z - 8, mouth, mouth, 8)
    part.box(x - mouth // 2 - 1, y - mouth // 2 - 1, z - 10, mouth + 2, mouth + 2, 2)
    return part


def tapered_leg(model, name, hip, thigh, shin, top, mid, bottom, splay_x=0.0, splay_z=0.0,
                knee=0.4, spike=4):
    """A leg that tapers from the hip to a pointed tip, the way the artwork legs do: thick ball joint,
    knee, thin shin, sharp foot."""
    upper = model.part(name, pivot=hip, rot=(splay_z, 0.0, -splay_x))
    upper.box(-top // 2, 0, -top // 2, top, thigh // 2, top)
    upper.box(-mid // 2, thigh // 2, -mid // 2, mid, thigh - thigh // 2, mid)
    lower = model.part(name + "_shin", parent=name, pivot=(0, thigh, 0),
                       rot=(splay_z * knee, 0.0, -splay_x * knee))
    lower.box(-mid // 2, 0, -mid // 2, mid, shin // 2, mid)
    lower.box(-bottom // 2, shin // 2, -bottom // 2, bottom, shin - shin // 2, bottom)
    if spike:
        lower.box(-spike // 2, shin, -spike // 2, spike, 5, spike)
        lower.box(-max(2, spike // 2 - 1), shin + 4, -max(2, spike // 2 - 1),
                  max(2, spike // 2 - 1) * 2, 3, max(2, spike // 2 - 1) * 2)
    return upper


def arched_limb(model, name, hip, upper, mid, lower, thick, out_deg, in_deg, foot=8):
    """A limb that bows outwards at the shoulder and comes back under the body, like the BBC machine's
    crab legs."""
    root = model.part(name, pivot=hip, rot=(0.0, 0.0, -out_deg))
    root.box(-thick // 2, 0, -thick // 2, thick, upper, thick)
    elbow = model.part(name + "_mid", parent=name, pivot=(0, upper, 0), rot=(0.0, 0.0, -in_deg))
    elbow.box(-thick // 2, 0, -thick // 2, thick, mid, thick)
    shin = model.part(name + "_shin", parent=name + "_mid", pivot=(0, mid, 0),
                      rot=(0.0, 0.0, (out_deg - in_deg) * 0.25))
    shin.box(-thick // 2, 0, -thick // 2, thick, lower, thick)
    if foot:
        shin.box(-foot // 2, lower - 2, -foot // 2, foot, 4, foot)
    return root


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


def claw_foot(model, name, parent, pivot, size=7, spread=4):
    """Three splayed claws instead of one flat foot block."""
    foot = model.part(name, parent=parent, pivot=pivot)
    foot.box(-spread - 1, 0, -size // 2, 2, 3, size)
    foot.box(spread - 1, 0, -size // 2, 2, 3, size)
    foot.box(-1, 0, -size // 2 - 3, 2, 3, size + 2)
    return foot


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


def leg_bands(model, name, thigh, shin, thick, thigh_bands=(), shin_bands=()):
    """Cuffs and rings around the legs, so the limbs read as machined joints."""
    upper = model.part(name + "_rings", parent=name, pivot=(0, 0, 0))
    for y in thigh_bands:
        upper.box(-thick // 2 - 1, y, -thick // 2 - 1, thick + 2, 3, thick + 2)
    lower = model.part(name + "_shin_rings", parent=name + "_shin", pivot=(0, 0, 0))
    for y in shin_bands:
        lower.box(-thick // 2 - 1, y, -thick // 2 - 1, thick + 2, 3, thick + 2)
    return upper


def spike_ring(part, cx, cy, cz, radius, count, size=4, height=8):
    """A ring of short spikes around a hull, used for the rim details of the artwork machines."""
    for index in range(count):
        angle = 6.283185307179586 * index / count
        x = cx + int(round(radius * math.cos(angle)))
        z = cz + int(round(radius * math.sin(angle)))
        part.box(x - size // 2, cy - height, z - size // 2, size, height, size)
    return part

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
    """Lou Cameron's machine: a huge oval glass lens in a gold rim on a slim neck, a ball jointed hub
    and three thick tubular legs with a whip antenna."""
    m = Model("cameron_tripod")
    for index, (x, z, sx, sz) in enumerate(((-21, -13, -9, -7), (21, -13, 9, -7), (0, 26, 0, 9))):
        tapered_leg(m, f"leg{index}", (x, -150, z), 72, 78, 9, 7, 4, splay_x=sx, splay_z=sz,
                    knee=0.45, spike=3)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 10)
        ball(m.by_name[f"leg{index}_shin"], 0, 0, 0, 8)

    hub = m.part("hub", pivot=(0, -176, 0))
    ball(hub, 0, 0, 0, 21)
    collar(hub, 0, 16, 0, 36, 6, 36, 4)
    collar(hub, 0, -16, 0, 26, 5, 26, 3)

    neck = m.part("neck", parent="hub", pivot=(0, -21, 0))
    neck.box(-6, -26, -6, 12, 26, 12)
    collar(neck, 0, -4, 0, 18, 4, 18, 2)
    collar(neck, 0, -20, 0, 16, 4, 16, 2)

    head = m.part("head", parent="neck", pivot=(0, -26, -2), rot=(-14.0, 0.0, 0.0))
    head.shell(0, -32, 0, 54, 38, 14, cell=13)
    head.shell(0, -24, 0, 58, 22, 16, cell=14)
    collar(head, 0, -34, 0, 62, 42, 18, 5)
    head.box(-19, -30, -10, 38, 28, 3)
    head.glow_box(-7, -24, -11, 14, 14, 2)
    head.box(-24, -44, -7, 48, 6, 12)
    head.box(-24, 2, -7, 48, 6, 12)
    head.box(-6, -46, -6, 12, 4, 10)

    barrel = m.part("barrel", parent="head", pivot=(0, -4, -6))
    barrel.box(-4, 0, -14, 8, 8, 16)
    barrel.box(-7, -3, -19, 14, 14, 6)
    barrel.glow_box(-3, 1, -20, 6, 6, 2)

    antenna = m.part("antenna", parent="head", pivot=(16, -44, 4))
    antenna.box(-1, -46, -1, 2, 46, 2)
    antenna.box(-4, -22, -1, 3, 2, 2)
    eye(m, "antenna_tip", "antenna", (0, -48, 0), (4, 4))

    for index, x in enumerate((-10, 0, 10)):
        tentacle(m, f"probe{index}_", "hub", (x, 12, -2), 28, segments=3, droop=12.0,
                 spread=index - 1, thick=2)
    return m

def gorey_tripod():
    """Edward Gorey's saucer: a wide black disc with two yellow eyes on the dome and a halo of thin
    wiry arms, standing on three thick ringed legs."""
    m = Model("gorey_tripod")
    for index, (x, z, sx, sz) in enumerate(((-24, -14, -7, -7), (24, -14, 7, -7), (0, 26, 0, 8))):
        leg(m, f"leg{index}", (x, -188, z), 90, 96, 7, splay_x=sx, splay_z=sz, foot=0)
        leg_bands(m, f"leg{index}", 90, 96, 7, thigh_bands=(14, 34, 54, 74),
                  shin_bands=(16, 40, 64, 82))
        foot = m.part(f"leg{index}_foot", parent=f"leg{index}_shin", pivot=(0, 94, 0))
        foot.box(-5, 0, -5, 10, 4, 10)
        foot.box(-7, 2, -7, 14, 3, 14)

    body = m.part("body")
    body.shell(0, -214, 0, 78, 9, 74, cell=20)
    body.shell(0, -222, 0, 66, 8, 62, cell=17)
    collar(body, 0, -206, 0, 80, 7, 76, 5)
    body.shell(0, -198, 0, 32, 8, 30, cell=11)
    body.shell(0, -230, 0, 32, 8, 30, cell=11)
    body.shell(0, -238, 0, 22, 7, 20, cell=8)
    eye(m, "eye_l", "body", (-8, -234, -11), (9, 8))
    eye(m, "eye_r", "body", (8, -234, -11), (9, 8))

    for index in range(10):
        angle = 6.283185307179586 * index / 10.0
        arc_arm(m, f"arm{index}_", "body",
                (int(round(38 * math.cos(angle))), -216, int(round(38 * math.sin(angle)))),
                yaw=index * 36.0, length=150, segments=7, lift=-78.0, curl=-8.0, thick=2)
    return m


def wayne_tripod():
    """Jeff Wayne's war machine: a polished silver egg with two green eyes on the crown, ray trumpets on
    long articulated booms and three banded legs."""
    m = Model("wayne_tripod")
    for index, (x, z, sx, sz) in enumerate(((-19, -8, -7, -5), (19, -8, 7, -5), (0, 24, 0, 8))):
        tapered_leg(m, f"leg{index}", (x, -196, z), 90, 96, 7, 5, 3, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=3)
        leg_bands(m, f"leg{index}", 90, 96, 5, thigh_bands=(12, 34, 56),
                  shin_bands=(14, 38, 64))

    body = m.part("body")
    for index, (width, height) in enumerate(((26, 8), (36, 10), (44, 10), (46, 12),
                                             (44, 12), (38, 10), (28, 8))):
        body.shell(0, -268 + index * 10, 0, width, height, width, cell=max(7, width // 3))
    collar(body, 0, -260, 0, 32, 6, 30, 4)
    eye(m, "eye_l", "body", (-8, -266, -15), (9, 8))
    eye(m, "eye_r", "body", (8, -266, -15), (9, 8))

    beak = m.part("beak", parent="body", pivot=(0, -204, -10))
    beak.box(-5, 0, -10, 10, 10, 14)
    beak.box(-7, 3, -16, 14, 6, 6)

    for side, name in ((-1, "boom_l"), (1, "boom_r")):
        boom = m.part(name, parent="body", pivot=(side * 22, -240, 0), rot=(0.0, 0.0, -side * 10.0))
        boom.box(side * 4 if side > 0 else side * 4 - 30, -3, -3, 30, 6, 6)
        joint = m.part(name + "_elbow", parent=name, pivot=(side * 32, 0, 0))
        joint.box(-4, -4, -4, 8, 8, 8)
        horn = m.part(name + "_horn", parent=name + "_elbow", pivot=(side * 5, 0, -3))
        trumpet(horn, 0, 0, 0, 12, 8, 18)
        horn.box(-5, -5, -12, 10, 10, 2)
        horn.glow_box(-2, -2, -13, 4, 4, 1)
    return m


def goble_tripod():
    """Warwick Goble, 1906: a wide riveted saucer with a dark equator band, three lattice legs, a crane
    boom with a hook, iron tongs and a glowing basket."""
    m = Model("goble_tripod")
    for index, (x, z, sx, sz) in enumerate(((-27, -18, -7, -6), (27, -18, 7, -6), (0, 29, 0, 7))):
        girder_leg(m, f"leg{index}", (x, -204, z), 82, 88, splay_x=sx, splay_z=sz,
                   width=11, rungs=5, foot=9)

    body = m.part("body")
    body.shell(0, -226, 0, 78, 9, 72, cell=20)
    body.shell(0, -217, 0, 62, 8, 56, cell=16)
    body.shell(0, -207, 0, 38, 8, 34, cell=12)
    collar(body, 0, -226, 0, 80, 8, 74, 5)
    body.glow_box(-6, -208, -18, 12, 6, 2)

    ramp = m.part("ramp", parent="body", pivot=(0, -232, 10))
    ramp.box(-9, -12, 0, 18, 12, 22)
    ramp.box(-7, -19, 3, 14, 7, 15)
    ramp.glow_box(-3, -17, 17, 6, 3, 2)

    crane = m.part("crane", parent="body", pivot=(38, -212, -8), rot=(0.0, 0.0, -6.0))
    crane.box(0, -3, -3, 54, 6, 6)
    crane.box(50, -3, -20, 4, 6, 24)
    crane.box(0, -7, -4, 8, 8, 8)
    crane.box(20, -6, -3, 4, 4, 6)

    cable = m.part("cable", parent="crane", pivot=(52, 3, -14))
    cable.box(-1, 0, -1, 2, 26, 2)
    hook = m.part("hook", parent="cable", pivot=(0, 26, 0))
    hook.box(-3, 0, -3, 6, 6, 6)
    hook.box(-3, 6, -3, 2, 9, 2)
    hook.box(1, 13, -3, 2, 3, 2)

    tongs = m.part("tongs", parent="crane", pivot=(24, 3, -14))
    tongs.box(-1, 0, -1, 2, 22, 2)
    tongs.box(-10, 20, -1, 10, 2, 2)
    tongs.box(-18, 20, -1, 2, 16, 2)
    tongs.box(8, 20, -1, 2, 16, 2)

    basket = m.part("basket", parent="crane", pivot=(52, 3, 8))
    basket.box(-7, 0, -7, 14, 14, 14)
    basket.glow_box(-6, 2, -8, 12, 9, 2)
    basket.glow_box(-6, 2, 6, 12, 9, 2)
    basket.box(-2, 14, -2, 4, 14, 4)
    return m


def bbc2019_tripod():
    """The 2019 BBC machine: a small dark pod studded with cold blue lights, carried high by two long
    limbs that arch outwards like a crab's."""
    m = Model("bbc2019_tripod")
    arched_limb(m, "limb_l", (-12, -250, -4), 70, 66, 78, 6, -34.0, 22.0, foot=9)
    arched_limb(m, "limb_r", (12, -250, -4), 70, 66, 78, 6, 34.0, -22.0, foot=9)
    leg(m, "limb_b", (0, -250, 16), 100, 106, 5, splay_x=0, splay_z=9, foot=9)

    body = m.part("body")
    body.shell(0, -266, 0, 30, 22, 28, cell=10)
    body.shell(0, -246, 0, 24, 20, 22, cell=9)
    body.shell(0, -276, 0, 22, 10, 20, cell=8)
    collar(body, 0, -258, 0, 32, 6, 30, 3)

    lamps = ((-9, -268, -15), (0, -264, -17), (9, -268, -15),
             (-7, -256, -14), (7, -256, -14), (0, -250, -13),
             (-12, -258, -6), (12, -258, -6), (0, -270, -4))
    for index, (x, y, z) in enumerate(lamps):
        eye(m, f"lamp{index}", "body", (x, y, z), (5, 5))

    for index, x in enumerate((-7, 0, 7)):
        tentacle(m, f"tendril{index}_", "body", (x, -244, -8), 26, segments=2, droop=8.0,
                 spread=index - 1, thick=2)

    antenna = m.part("antenna", parent="body", pivot=(0, -278, 0))
    antenna.box(-1, -24, -1, 2, 24, 2)
    antenna.box(-6, -22, -1, 5, 2, 2)
    return m


def attack2021_tripod():
    """The 2021 machine: a swept glider canopy over a glowing core, two raptor legs and a lashing tail."""
    m = Model("attack2021_tripod")
    for index, (x, sx) in enumerate(((-16, -9), (16, 9))):
        barbed_leg(m, f"leg{index}", (x, -198, -2), 102, 106, 6, splay_x=sx, splay_z=-2, foot=0,
                   barbs=4, barb=4)
        foot = m.part(f"leg{index}_foot", parent=f"leg{index}_shin", pivot=(0, 104, 0))
        foot.box(-5, 0, -5, 10, 4, 10)
        foot.box(-6, 2, -12, 4, 3, 8)

    body = m.part("body")
    body.shell(0, -238, 0, 26, 34, 26, cell=13)
    body.shell(0, -252, 0, 18, 16, 18, cell=9)
    body.shell(0, -220, 0, 18, 12, 18, cell=9)
    body.glow_box(-6, -232, -14, 12, 10, 2)
    body.glow_box(-2, -252, -10, 4, 6, 2)

    for side, name in ((-1, "wing_l"), (1, "wing_r")):
        wing = m.part(name, parent="body", pivot=(side * 5, -256, 6),
                      rot=(0.0, side * 14.0, -side * 9.0))
        if side > 0:
            wing.box(0, -3, -20, 40, 5, 26)
            wing.box(38, -3, -14, 12, 4, 18)
            wing.box(8, 0, 3, 26, 4, 14)
        else:
            wing.box(-40, -3, -20, 40, 5, 26)
            wing.box(-50, -3, -14, 12, 4, 18)
            wing.box(-34, 0, 3, 26, 4, 14)

    head = m.part("head", parent="body", pivot=(0, -240, -12))
    head.box(-8, -8, -10, 16, 12, 16)
    eye(m, "eye", "head", (0, -2, -11), (11, 5))

    tail = m.part("tail", parent="body", pivot=(0, -208, 12))
    tail.box(-2, 0, 0, 4, 4, 46)
    tail.box(-1, 2, 44, 2, 2, 26)
    tail.box(-3, 0, 20, 6, 3, 6)
    return m


def attack_tripod():
    """War of the Worlds: The Attack: a small crown of red eyes hung with a curtain of long feelers, on
    three enormous thin legs."""
    m = Model("attack_tripod")
    for index, (x, z, sx, sz) in enumerate(((-18, -4, -8, -3), (18, -4, 8, -3), (0, 24, 0, 8))):
        leg(m, f"leg{index}", (x, -252, z), 118, 124, 4, splay_x=sx, splay_z=sz, foot=0)
        leg_bands(m, f"leg{index}", 118, 124, 4, thigh_bands=(20, 60, 100),
                  shin_bands=(20, 60, 100))
        foot = m.part(f"leg{index}_foot", parent=f"leg{index}_shin", pivot=(0, 122, 0))
        foot.box(-4, 0, -4, 8, 4, 8)

    body = m.part("body")
    body.shell(0, -282, 0, 28, 26, 28, cell=10)
    body.shell(0, -292, 0, 20, 12, 20, cell=8)
    collar(body, 0, -280, 0, 30, 5, 30, 3)
    for index, (x, y) in enumerate(((-8, -286), (0, -288), (8, -286), (-4, -278), (4, -278))):
        eye(m, f"eye{index}", "body", (x, y, -14), (5, 5))

    for index in range(12):
        angle = 6.283185307179586 * index / 12.0
        x = int(round(12 * math.cos(angle)))
        z = int(round(12 * math.sin(angle)))
        tentacle(m, f"arm{index}_", "body", (x, -270, z), 70, segments=5, droop=9.0,
                 spread=(index - 5.5) * 8.0, thick=2)
    return m


def vegetable_tripod():
    """The 'vegetable' machine: a pale segmented cylinder with a pointed cap, portholes, two long arms
    with claws and three ringed legs."""
    m = Model("vegetable_tripod")
    for index, (x, z, sx, sz) in enumerate(((-17, -8, -9, -4), (17, -8, 9, -4), (0, 21, 0, 9))):
        leg(m, f"leg{index}", (x, -198, z), 100, 106, 5, splay_x=sx, splay_z=sz, foot=0)
        leg_bands(m, f"leg{index}", 100, 106, 5, thigh_bands=(16, 40, 64, 86),
                  shin_bands=(18, 44, 70, 92))
        claw_foot(m, f"leg{index}_claw", f"leg{index}_shin", (0, 102, 0), size=9, spread=4)

    body = m.part("body")
    for index, width in enumerate((30, 34, 34, 32)):
        body.shell(0, -200 - index * 12, 0, width, 12, width, cell=15)
    collar(body, 0, -248, 0, 34, 5, 34, 3)
    collar(body, 0, -200, 0, 32, 5, 32, 3)
    body.box(-9, -226, -16, 8, 8, 3)
    body.box(2, -226, -16, 8, 8, 3)

    cap = m.part("head", parent="body", pivot=(0, -248, 0))
    for index, width in enumerate((36, 30, 22, 14, 6)):
        cap.box(-width // 2, -index * 8, -width // 2, width, 8, width)
    cap.glow_box(-2, -44, -2, 4, 6, 2)
    for index, side in enumerate((-1, 1)):
        fin = m.part("fin_l" if side < 0 else "fin_r", parent="head", pivot=(side * 16, -4, 0))
        fin.box(0 if side > 0 else -12, -3, -8, 12, 5, 16)
        fin.box(0 if side > 0 else -6, -8, -3, 6, 6, 6)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        arm = m.part(name, parent="body", pivot=(side * 17, -222, 0), rot=(0.0, 0.0, -side * 36.0))
        arm.box(-2, 0, -2, 5, 48, 5)
        fore = m.part(name + "_fore", parent=name, pivot=(0, 46, 0), rot=(-14.0, 0.0, side * 48.0))
        fore.box(-2, 0, -2, 4, 36, 4)
        claw_foot(m, name + "_claw", name + "_fore", (0, 34, 0), size=8, spread=4)
    return m


def mexican_tripod():
    """The Mexican poster machine: a bronze bulb with two huge yellow eyes, a red lamp on the cap and two
    black feeler arms that end in barbed claw heads."""
    m = Model("mexican_tripod")
    for index, (x, z, sx, sz) in enumerate(((-21, -6, -8, -3), (21, -6, 8, -3), (0, 24, 0, 8))):
        leg(m, f"leg{index}", (x, -196, z), 98, 104, 6, splay_x=sx, splay_z=sz, foot=0)
        foot = m.part(f"leg{index}_foot", parent=f"leg{index}_shin", pivot=(0, 102, 0))
        foot.box(-6, 0, -6, 12, 4, 12)

    body = m.part("body")
    for index, (width, height) in enumerate(((30, 8), (40, 10), (46, 12), (44, 12), (34, 10))):
        body.shell(0, -266 + index * 10, 0, width, height, width, cell=max(7, width // 3))
    collar(body, 0, -226, 0, 34, 6, 34, 3)
    eye(m, "eye_l", "body", (-11, -250, -21), (14, 12))
    eye(m, "eye_r", "body", (11, -250, -21), (14, 12))

    cap = m.part("cap", parent="body", pivot=(0, -276, 0))
    cap.box(-9, -10, -9, 18, 10, 18)
    cap.box(-6, -14, -6, 12, 5, 12)
    cap.glow_box(-3, -17, -3, 6, 4, 6)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        tentacle(m, f"{name}_", "body", (side * 21, -246, -8), 56, segments=5, droop=13.0,
                 spread=side * 18.0, thick=3)
        claw = m.part(name + "_head", parent=f"{name}_5_body", pivot=(0, 8, 0))
        claw.box(-5, 0, -3, 10, 5, 6)
        claw.box(-6, 3, -10, 3, 9, 3)
        claw.box(3, 3, -10, 3, 9, 3)
        claw.box(-2, 3, -8, 4, 8, 2)
        claw.box(-8, 1, -4, 3, 3, 6)
        claw.box(5, 1, -4, 3, 3, 6)
        claw.glow_box(-1, 5, -9, 2, 3, 1)
    return m


def four_legged_tripod():
    """The four legged machine: a copper saucer crowned with two yellow eyes, a tapering waist, four long
    angular banded legs and a segmented tail ending in a claw."""
    m = Model("four_legged_tripod")
    for index, (x, z, sx, sz) in enumerate(((-25, -22, -9, -8), (25, -22, 9, -8),
                                            (-25, 22, -9, 8), (25, 22, 9, 8))):
        leg(m, f"leg{index}", (x, -200, z), 102, 106, 6, splay_x=sx, splay_z=sz, foot=0, knee=0.5)
        leg_bands(m, f"leg{index}", 102, 106, 6, thigh_bands=(18, 48, 78),
                  shin_bands=(20, 52, 84))
        claw_foot(m, f"leg{index}_claw", f"leg{index}_shin", (0, 102, 0), size=11, spread=3)

    body = m.part("body")
    body.shell(0, -248, 0, 64, 10, 60, cell=16)
    body.shell(0, -258, 0, 48, 10, 44, cell=12)
    body.shell(0, -266, 0, 30, 8, 28, cell=9)
    body.shell(0, -240, 0, 34, 8, 32, cell=11)
    eye(m, "eye_l", "body", (-7, -268, -10), (9, 8))
    eye(m, "eye_r", "body", (7, -268, -10), (9, 8))

    waist = m.part("waist", parent="body", pivot=(0, -240, 0))
    for index, width in enumerate((32, 26, 20, 14)):
        waist.box(-width // 2, index * 9, -width // 2, width, 9, width)
    waist.box(-9, 6, -10, 4, 8, 3)
    waist.box(5, 6, -10, 4, 8, 3)

    tail = m.part("tail", parent="body", pivot=(0, -212, 0))
    tail.box(-5, 0, -5, 10, 12, 10)
    tail.box(-4, 11, -4, 8, 14, 8)
    tail.box(-3, 24, -3, 6, 14, 6)
    tail.box(-2, 37, -2, 4, 12, 4)
    claw = m.part("tail_claw", parent="tail", pivot=(0, 48, 0))
    claw.box(-7, 0, -4, 4, 4, 12)
    claw.box(3, 0, -4, 4, 4, 12)
    claw.box(-2, 0, -8, 4, 4, 6)
    return m


def series1998_tripod():
    """The 1998 series machine: a wide blue mantle that droops at the rim around a burning orange bowl,
    carried by three long smooth legs."""
    m = Model("series1998_tripod")
    for index, (x, z, sx, sz) in enumerate(((-25, -10, -9, -5), (25, -10, 9, -5), (0, 27, 0, 9))):
        tapered_leg(m, f"leg{index}", (x, -214, z), 108, 114, 6, 4, 2, splay_x=sx, splay_z=sz,
                    knee=0.4, spike=2)
        leg_bands(m, f"leg{index}", 108, 114, 4, thigh_bands=(28, 70),
                  shin_bands=(30, 76))

    body = m.part("body")
    body.shell(0, -236, 0, 68, 8, 64, cell=17)
    body.shell(0, -244, 0, 56, 8, 52, cell=14)
    body.shell(0, -252, 0, 40, 8, 38, cell=10)
    body.shell(0, -228, 0, 34, 8, 32, cell=11)
    collar(body, 0, -230, 0, 70, 6, 66, 4)

    for index in range(8):
        angle = 6.283185307179586 * index / 8.0
        side = 1 if index < 4 else -1
        petal = m.part(f"petal{index}", parent="body",
                       pivot=(int(round(32 * math.cos(angle))), -232,
                              int(round(30 * math.sin(angle)))),
                       rot=(0.0, math.degrees(angle), side * 34.0))
        petal.box(-9, 0, -3, 18, 5, 6)
        petal.box(-8, 5, -2, 16, 5, 5)

    bowl = m.part("bowl", parent="body", pivot=(0, -254, 0))
    bowl.box(-17, -5, -15, 34, 5, 30)
    bowl.box(-13, -9, -11, 26, 4, 22)
    bowl.glow_box(-11, -8, -13, 22, 3, 26)
    bowl.box(-19, -5, -3, 3, 6, 6)
    bowl.box(16, -5, -3, 3, 6, 6)

    for index, x in enumerate((-8, 0, 8)):
        tentacle(m, f"feel{index}_", "body", (x, -232, 12), 30, segments=3, droop=12.0,
                 spread=(index - 1) * 7.0, thick=2)
    return m


def pendragon_tripod():
    """Pendragon's beetle: a hammered golden carapace with a plated mask, a proboscis and an antenna,
    four hooked legs and a mane of braided feeler whips."""
    m = Model("pendragon_tripod")
    for index, (x, z, sx, sz) in enumerate(((-25, -16, -8, -7), (25, -16, 8, -7),
                                            (-25, 16, -8, 7), (25, 16, 8, 7))):
        tapered_leg(m, f"leg{index}", (x, -208, z), 98, 102, 10, 7, 4, splay_x=sx, splay_z=sz,
                    knee=0.45, spike=4)
        leg_bands(m, f"leg{index}", 98, 102, 7, thigh_bands=(26, 62), shin_bands=(30, 70))
        claw_foot(m, f"leg{index}_claw", f"leg{index}_shin", (0, 98, 0), size=11, spread=5)

    body = m.part("body")
    for index, (width, height) in enumerate(((30, 8), (40, 10), (48, 12), (46, 10), (34, 8))):
        body.shell(0, -254 + index * 10, 0, width, height, width - 4, cell=max(8, width // 3))
    collar(body, 0, -216, 0, 36, 6, 32, 3)
    eye(m, "eye_l", "body", (-11, -238, -22), (9, 7))
    eye(m, "eye_r", "body", (11, -238, -22), (9, 7))

    head = m.part("head", parent="body", pivot=(0, -246, -22))
    head.box(-16, -16, -10, 32, 30, 12)
    for index, x in enumerate((-12, -6, 1, 7, 12)):
        head.box(x, -12, -13, 4, 24, 4)
    head.box(-14, -18, -9, 28, 4, 10)
    head.box(-15, 12, -9, 30, 5, 10)
    proboscis = m.part("proboscis", parent="head", pivot=(0, 14, -6))
    proboscis.box(-5, 0, -10, 10, 9, 14)
    proboscis.box(-3, 2, -20, 6, 7, 12)
    proboscis.glow_box(-2, 4, -22, 4, 4, 2)
    for side, name in ((-1, "jaw_l"), (1, "jaw_r")):
        jaw = m.part(name, parent="head", pivot=(side * 12, 14, -8))
        jaw.box(-2, 0, -11, 4, 4, 13)
        jaw.box(-2, -5, -13, 4, 5, 4)

    antenna = m.part("antenna", parent="head", pivot=(0, -18, 2))
    antenna.box(-1, -30, -1, 2, 30, 2)
    antenna.glow_box(-1, -32, -1, 3, 3, 3)

    for index in range(6):
        arc_arm(m, f"braid{index}_", "body", (0, -232, -8), yaw=index * 60.0 + 30.0,
                length=112, segments=6, lift=-60.0, curl=-16.0, thick=3)
    return m


def nextwave_cyborg_tripod():
    """The Asylum cyborg: a small charred head with orange eyes and a molten core, standing on three
    enormously long legs plated with scavenged scrap."""
    m = Model("nextwave_cyborg_tripod")
    for index, (x, z, sx, sz) in enumerate(((-18, -10, -8, -5), (18, -10, 8, -5), (0, 24, 0, 8))):
        barbed_leg(m, f"leg{index}", (x, -250, z), 120, 124, 6, splay_x=sx, splay_z=sz, foot=0,
                   barbs=5, barb=4)
        leg_bands(m, f"leg{index}", 120, 124, 6, thigh_bands=(26, 70), shin_bands=(28, 74))
        foot = m.part(f"leg{index}_foot", parent=f"leg{index}_shin", pivot=(0, 122, 0))
        foot.box(-5, 0, -5, 10, 4, 10)

    body = m.part("body")
    for index, (width, height) in enumerate(((24, 8), (32, 10), (34, 12), (28, 10))):
        body.shell(0, -288 + index * 9, 0, width, height, width, cell=max(7, width // 3))
    eye(m, "eye_l", "body", (-7, -284, -17), (7, 6))
    eye(m, "eye_r", "body", (7, -284, -17), (7, 6))
    body.glow_box(-6, -262, -13, 12, 10, 2)

    for index, (x, y, z, dx, dy, dz) in enumerate(((-19, -276, -6, 9, 18, 9),
                                                    (17, -292, 3, 8, 11, 9),
                                                    (-4, -252, 11, 11, 8, 9),
                                                    (12, -264, -9, 7, 13, 7),
                                                    (-13, -258, 4, 6, 8, 6))):
        scrap = m.part(f"scrap{index}", parent="body", pivot=(x, y, z))
        scrap.box(-dx // 2, -dy // 2, -dz // 2, dx, dy, dz)

    antenna = m.part("antenna", parent="body", pivot=(6, -294, 0))
    antenna.box(-1, -22, -1, 2, 22, 2)

    for index, x in enumerate((-8, 0, 8)):
        tentacle(m, f"cable{index}_", "body", (x, -250, -10), 28, segments=2, droop=10.0,
                 spread=index - 1, thick=2)
    return m


def true2012_tripod():
    """True War of the Worlds 2012: a heavy copper engine bolted on top of three braced legs, with a side
    cannon and cables hanging off the block."""
    m = Model("true2012_tripod")
    for index, (x, z, sx, sz) in enumerate(((-26, -16, -8, -4), (26, -16, 8, -4), (0, 28, 0, 8))):
        tapered_leg(m, f"leg{index}", (x, -198, z), 94, 100, 7, 5, 3, splay_x=sx, splay_z=sz,
                    knee=0.4, spike=3)
        leg_bands(m, f"leg{index}", 94, 100, 5, thigh_bands=(24, 58), shin_bands=(24, 60))
        brace = m.part(f"leg{index}_brace", parent=f"leg{index}", pivot=(0, 40, 0))
        brace.box(-1, -24, -1, 2, 30, 2)
        brace.box(-1, -10, -6, 2, 16, 2)

    body = m.part("body")
    for index, (width, height, depth) in enumerate(((40, 10, 36), (50, 12, 46), (46, 10, 42),
                                                    (34, 10, 30))):
        body.shell(0, -246 + index * 10, 0, width, height, depth, cell=14)
    body.shell(-14, -256, -8, 22, 10, 20, cell=11)
    body.shell(14, -252, 6, 18, 10, 18, cell=9)
    eye(m, "eye", "body", (0, -232, -23), (14, 9))

    gun = m.part("gun", parent="body", pivot=(25, -238, -4))
    gun.box(0, -5, -5, 26, 10, 10)
    gun.box(24, -3, -3, 12, 6, 6)
    gun.box(34, -5, -5, 4, 10, 10)
    gun.box(8, 5, -3, 6, 7, 6)

    pipes = m.part("pipes", parent="body", pivot=(0, -262, 0))
    for index, x in enumerate((-17, -6, 6, 17)):
        pipes.box(x, -7, -9, 5, 15, 18)
    pipes.box(-20, -12, -4, 40, 5, 8)

    stack = m.part("stack", parent="body", pivot=(-8, -266, 9))
    stack.box(-4, -17, -4, 9, 17, 9)
    stack.box(-6, -21, -6, 13, 4, 13)

    for index, x in enumerate((-15, -4, 7, 16)):
        tentacle(m, f"cable{index}_", "body", (x, -204, 11), 36, segments=3, droop=7.0,
                 spread=index - 1.5, thick=2)
    return m


def steam_tower_tripod():
    """Steam Towers: a dark riveted dome with a brass equator, hanging between two brass yoke arms
    drilled with round holes, on three thin legs."""
    m = Model("steam_tower_tripod")
    for index, (x, z, sx, sz) in enumerate(((-27, -16, -8, -4), (27, -16, 8, -4), (0, 28, 0, 7))):
        tapered_leg(m, f"leg{index}", (x, -240, z), 108, 112, 6, 4, 2, splay_x=sx, splay_z=sz,
                    knee=0.4, spike=2)
        leg_bands(m, f"leg{index}", 108, 112, 4, thigh_bands=(24, 64), shin_bands=(26, 68))

    body = m.part("body")
    for index, (width, height) in enumerate(((30, 8), (42, 12), (48, 12), (44, 10), (30, 8))):
        body.shell(0, -282 + index * 9, 0, width, height, width, cell=max(8, width // 3))
    collar(body, 0, -250, 0, 50, 7, 50, 4)
    collar(body, 0, -240, 0, 36, 6, 36, 3)

    star = m.part("star", parent="body", pivot=(0, -240, 0))
    for index in range(6):
        angle = 6.283185307179586 * index / 6.0
        star.box(int(round(9 * math.cos(angle))) - 1, -2, int(round(9 * math.sin(angle))) - 1, 3, 4, 3)
    star.glow_box(-3, -3, -3, 7, 4, 7)

    for side, name in ((-1, "yoke_l"), (1, "yoke_r")):
        yoke = m.part(name, parent="body", pivot=(side * 20, -262, 0), rot=(0.0, 0.0, -side * 5.0))
        # two rails with spacers: the round holes of the reference
        yoke.box(0 if side > 0 else -52, -6, -5, 52, 4, 10)
        yoke.box(0 if side > 0 else -52, 2, -5, 52, 4, 10)
        for index in range(5):
            x0 = index * 13
            yoke.box(x0 if side > 0 else -x0 - 4, -6, -5, 4, 12, 10)
        yoke.box(side * 52, -3, -3, 10, 7, 7)
        yoke.glow_box(side * 6 - 2, -2, -6, 5, 5, 2)
        yoke.glow_box(side * 34 - 2, -2, -6, 5, 5, 2)

    for index, x in enumerate((-8, 0, 8)):
        vent = m.part(f"vent{index}", parent="body", pivot=(x, -238, -8))
        vent.glow_box(-2, 0, -2, 5, 3, 5)
    return m


def alien_dawn_tripod():
    """Alien Dawn: a huge ribbed drum of a head with a blue light bar across its face, riding on four
    long thin legs."""
    m = Model("alien_dawn_tripod")
    for index, (x, z, sx, sz) in enumerate(((-27, -18, -8, -5), (27, -18, 8, -5),
                                            (-27, 18, -8, 5), (27, 18, 8, 5))):
        tapered_leg(m, f"leg{index}", (x, -234, z), 108, 112, 6, 4, 2, splay_x=sx, splay_z=sz,
                    knee=0.4, spike=2)
        ball(m.by_name[f"leg{index}_shin"], 0, 0, 0, 7)
        leg_bands(m, f"leg{index}", 108, 112, 4, thigh_bands=(34, 78), shin_bands=(34, 84))

    body = m.part("body")
    for index, width in enumerate((42, 54, 58, 58, 50, 34)):
        body.shell(0, -258 - index * 9, 0, width, 9, 50, cell=max(9, width // 3))
    collar(body, 0, -266, 0, 60, 5, 52, 3)
    collar(body, 0, -293, 0, 52, 5, 46, 3)
    body.shell(0, -252, 0, 30, 8, 28, cell=10)

    bar = m.part("lightbar", parent="body", pivot=(0, -278, -25))
    bar.glow_box(-21, -6, -2, 42, 10, 3)
    bar.box(-23, -9, -4, 46, 3, 6)
    bar.box(-23, 4, -4, 46, 3, 6)

    for index, (x, y) in enumerate(((-15, -292), (0, -296), (15, -292))):
        eye(m, f"port{index}", "body", (x, y, -13), (5, 5))

    for index, side in enumerate((-1, 1)):
        antenna = m.part(f"antenna{index}", parent="body", pivot=(side * 18, -302, 0))
        antenna.box(-1, -30, -1, 2, 30, 2)
        antenna.box(0 if side > 0 else -7, -32, -1, 7, 2, 2)
    return m


def alien_dawn_mk2_tripod():
    """The second Alien Dawn machine: a mirrored scoop with a slotted grille face and a flared funnel, on
    three slender ringed legs."""
    m = Model("alien_dawn_mk2_tripod")
    for index, (x, z, sx, sz) in enumerate(((-23, -12, -9, -5), (23, -12, 9, -5), (0, 27, 0, 9))):
        tapered_leg(m, f"leg{index}", (x, -232, z), 110, 114, 6, 4, 2, splay_x=sx, splay_z=sz,
                    knee=0.4, spike=2)
        leg_bands(m, f"leg{index}", 110, 114, 4, thigh_bands=(26, 62),
                  shin_bands=(28, 66))
        ball(m.by_name[f"leg{index}_shin"], 0, 0, 0, 7)

    body = m.part("body")
    for index, width in enumerate((24, 32, 40, 44, 42, 32)):
        body.shell(0, -286 + index * 9, 0, width, 9, width - 4, cell=max(8, width // 3))
    body.shell(0, -240, 0, 20, 10, 20, cell=10)

    scoop = m.part("scoop", parent="body", pivot=(0, -280, -18))
    scoop.box(-18, -4, -8, 36, 30, 10)
    scoop.box(-20, -6, -12, 40, 4, 12)
    for index in range(5):
        scoop.box(-14, 2 + index * 5, -11, 28, 2, 3)
    scoop.glow_box(-18, -5, -13, 36, 2, 2)

    horn = m.part("horn", parent="body", pivot=(0, -252, 16))
    horn.box(-9, -9, 0, 18, 18, 12)
    horn.box(-7, -7, 11, 14, 14, 9)
    horn.box(-11, -11, 19, 22, 22, 3)

    for index, side in enumerate((-1, 1)):
        antenna = m.part(f"antenna{index}", parent="body", pivot=(side * 14, -292, 4))
        antenna.box(-1, -28, -1, 2, 28, 2)
        antenna.box(0 if side > 0 else -6, -30, -1, 6, 2, 2)
    return m


def scary_movie_tripod():
    """Scary Movie 4: a swept dark canopy carrying one huge lamp lens between two smaller ones, on thin
    riveted legs with cylindrical feet."""
    m = Model("scary_movie_tripod")
    for index, (x, z, sx, sz) in enumerate(((-24, -14, -9, -5), (24, -14, 9, -5), (0, 26, 0, 8))):
        tapered_leg(m, f"leg{index}", (x, -218, z), 104, 108, 6, 4, 2, splay_x=sx, splay_z=sz,
                    knee=0.4, spike=0)
        rivets = m.part(f"leg{index}_rivets", parent=f"leg{index}", pivot=(0, 0, 0))
        for y in (14, 40, 66, 90):
            rivets.box(-4, y, -4, 8, 3, 8)
        boot = m.part(f"leg{index}_boot", parent=f"leg{index}_shin", pivot=(0, 104, 0))
        boot.box(-7, 0, -7, 14, 12, 14)
        boot.box(-8, 9, -8, 16, 4, 16)
        boot.box(-5, 12, -5, 10, 3, 10)

    body = m.part("body")
    body.shell(0, -250, 0, 54, 10, 44, cell=18)
    body.shell(0, -240, 0, 36, 10, 30, cell=12)
    body.shell(0, -228, 0, 30, 14, 26, cell=10)

    for side, name in ((-1, "cape_l"), (1, "cape_r")):
        cape = m.part(name, parent="body", pivot=(side * 20, -254, 6),
                      rot=(0.0, -side * 8.0, -side * 12.0))
        cape.box(0 if side > 0 else -34, -3, -20, 34, 5, 40)
        cape.box(side * 30 if side > 0 else side * 30 - 14, -3, -12, 14, 4, 26)
        cape.box(side * 6 if side > 0 else side * 6 - 22, 0, 12, 22, 4, 12)

    lamp = m.part("lamp", parent="body", pivot=(0, -248, -20))
    lamp.box(-14, -14, -6, 28, 28, 8)
    lamp.box(-16, -16, -2, 32, 32, 3)
    lamp.glow_box(-11, -11, -8, 22, 22, 2)
    for index, side in enumerate((-1, 1)):
        small = m.part(f"side_lamp{index}", parent="body", pivot=(side * 21, -246, -18))
        small.box(-6, -6, -3, 12, 12, 5)
        small.glow_box(-4, -4, -5, 8, 8, 2)

    fin = m.part("fin", parent="body", pivot=(0, -254, 18))
    fin.box(-2, -20, 0, 4, 20, 16)
    return m


def ussr_book_tripod():
    """The Soviet book illustration: a dark bell with a finial and two side cups, hanging on three
    enormously long legs."""
    m = Model("ussr_book_tripod")
    for index, (x, z, sx, sz) in enumerate(((-15, -6, -7, -3), (15, -6, 7, -3), (0, 20, 0, 8))):
        tapered_leg(m, f"leg{index}", (x, -250, z), 126, 130, 6, 4, 2, splay_x=sx, splay_z=sz,
                    knee=0.4, spike=2)
        leg_bands(m, f"leg{index}", 126, 130, 4, thigh_bands=(30, 82), shin_bands=(34, 86))

    body = m.part("body")
    for index, width in enumerate((14, 20, 28, 38, 48, 52)):
        body.shell(0, -310 + index * 10, 0, width, 10, width, cell=max(7, width // 2))
    collar(body, 0, -262, 0, 54, 5, 54, 3)
    finial = m.part("finial", parent="body", pivot=(0, -316, 0))
    finial.box(-5, -6, -5, 10, 6, 10)
    finial.box(-3, -13, -3, 6, 7, 6)
    finial.glow_box(-2, -16, -2, 4, 3, 4)

    for side, name in ((-1, "cup_l"), (1, "cup_r")):
        cup = m.part(name, parent="body", pivot=(side * 26, -278, 0))
        cup.box(side * 4 if side > 0 else side * 4 - 10, -6, -6, 10, 12, 12)
        cup.box(side * 12 if side > 0 else side * 12 - 4, -3, -3, 4, 6, 6)

    slit = m.part("slit", parent="body", pivot=(0, -266, -27))
    slit.glow_box(-10, -3, -2, 20, 6, 3)

    for index, x in enumerate((-6, 4)):
        tentacle(m, f"chain{index}_", "body", (x, -254, -8), 30, segments=3, droop=5.0,
                 spread=index - 0.5, thick=2)
    return m


def annihilation_tripod():
    """War of the Worlds: Annihilation: a boxy two legged machine with cyan panels and two long scythe
    arms folded in front of it."""
    m = Model("annihilation_tripod")
    for index, side in enumerate((-1, 1)):
        barbed_leg(m, f"leg{index}", (side * 18, -224, 0), 114, 118, 7, splay_x=side * 6,
                   splay_z=-2, foot=0, barbs=3, barb=5)
        hip = m.part(f"hip{index}", parent="root", pivot=(side * 18, -224, 0))
        hip.box(-9, -12, -8, 18, 14, 16)
        knee = m.part(f"knee{index}", parent=f"leg{index}_shin", pivot=(0, 0, 0))
        knee.box(-9, -6, -6, 18, 12, 12)
        foot = m.part(f"leg{index}_foot", parent=f"leg{index}_shin", pivot=(0, 116, 0))
        foot.box(-8, 0, -12, 16, 5, 22)
        foot.box(-6, 3, 8, 12, 4, 8)

    body = m.part("body")
    body.shell(0, -274, 0, 38, 16, 34, cell=19)
    body.shell(0, -258, 0, 32, 14, 30, cell=16)
    body.shell(0, -244, 0, 26, 12, 26, cell=13)
    collar(body, 0, -272, 0, 40, 6, 36, 4)
    body.glow_box(-14, -272, -18, 10, 14, 2)
    body.glow_box(5, -272, -18, 10, 14, 2)
    body.glow_box(-4, -252, -16, 8, 6, 2)

    head = m.part("head", parent="body", pivot=(0, -280, -12))
    head.box(-11, -13, -9, 22, 13, 15)
    head.glow_box(-8, -9, -11, 16, 5, 2)
    head.box(-2, -24, -4, 4, 13, 7)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        shoulder = m.part(name, parent="body", pivot=(side * 18, -266, -2),
                          rot=(4.0, 0.0, -side * 14.0))
        shoulder.box(-4, 0, -4, 8, 46, 8)
        blade = m.part(name + "_blade", parent=name, pivot=(0, 44, 0), rot=(-56.0, 0.0, -side * 8.0))
        blade.box(-3, 0, -3, 7, 38, 7)
        blade.box(-4, 34, -26, 8, 8, 28)
        blade.box(-3, 36, -33, 5, 5, 9)
        blade.glow_box(-2, 36, -28, 4, 4, 5)

    for index, z in enumerate((-10, 8)):
        vent = m.part(f"vent{index}", parent="body", pivot=(0, -256, z))
        vent.box(-14, -2, -2, 28, 4, 4)
    return m

def mothership_tripod():
    """The Mother Ship: a wide saucer with a glazed cabin band, two long arms holding a ray emitter
    in front of it and three landing struts. A small probe tripod hangs under the hull."""
    m = Model("mothership_tripod")

    for index, (x, z, sx, sz) in enumerate(((-26, -12, -10, -7), (26, -12, 10, -7), (0, 28, 0, 9))):
        tapered_leg(m, f"leg{index}", (x, -192, z), 92, 96, 9, 6, 3, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=4)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 10)
        ball(m.by_name[f"leg{index}_shin"], 0, 0, 0, 8)

    body = m.part("body")
    body.shell(0, -240, 0, 44, 8, 40, cell=15)
    body.shell(0, -234, 0, 66, 8, 60, cell=18)
    body.shell(0, -226, 0, 86, 8, 80, cell=22)
    body.shell(0, -218, 0, 98, 8, 92, cell=26)
    collar(body, 0, -214, 0, 100, 6, 94, 5)
    body.shell(0, -208, 0, 92, 8, 86, cell=24)
    body.shell(0, -200, 0, 72, 8, 68, cell=18)
    body.shell(0, -192, 0, 46, 8, 44, cell=12)
    collar(body, 0, -186, 0, 40, 6, 38, 4)

    for index, x in enumerate((-32, -16, 0, 16, 32)):
        body.glow_box(x - 6, -216, -48, 12, 8, 3)
    body.glow_box(-44, -214, -20, 3, 8, 24)
    body.glow_box(42, -214, -20, 3, 8, 24)

    spike_ring(body, 0, -206, 0, 46, 12, size=5, height=6)
    for index in range(8):
        angle = 6.283185307179586 * index / 8.0
        body.box(int(round(44 * math.cos(angle))) - 2, -204, int(round(42 * math.sin(angle))) - 2,
                 4, 4, 4)

    # one articulated arm reaches up off the crown and holds the ray emitter
    pylon = m.part("pylon", parent="body", pivot=(0, -240, 8))
    pylon.box(-8, -14, -8, 16, 14, 16)
    collar(pylon, 0, -16, 0, 20, 4, 20, 2)
    arm = m.part("arm", parent="pylon", pivot=(0, -10, 0), rot=(26.0, 0.0, 0.0))
    arm.box(-5, -44, -5, 10, 46, 10)
    ball(arm, 0, -42, 0, 8)
    elbow = m.part("elbow", parent="arm", pivot=(0, -42, 0), rot=(-74.0, 0.0, 0.0))
    elbow.box(-4, -48, -4, 9, 48, 9)
    emitter = m.part("emitter", parent="elbow", pivot=(0, -46, 0), rot=(52.0, 0.0, 0.0))
    emitter.box(-11, -18, -11, 22, 24, 22)
    emitter.box(-13, -6, -22, 26, 12, 14)
    emitter.glow_box(-7, -9, -24, 14, 14, 3)
    emitter.box(-2, 18, -2, 5, 10, 5)

    probe = m.part("probe", parent="body", pivot=(0, -186, 6))
    probe.box(-1, 0, -1, 3, 30, 3)
    cage = m.part("probe_cage", parent="probe", pivot=(0, 30, 0))
    cage.shell(0, -8, 0, 18, 16, 18, cell=9)
    cage.glow_box(-4, -4, -10, 8, 6, 2)
    for index, (x, z) in enumerate(((-6, -6), (6, -6), (0, 8))):
        cage.box(x - 1, 8, z - 1, 3, 14, 3)
    return m

def mining_tripod():
    """The Martian mining machine: a squat red body bent forward over a bucket of iron teeth, with a
    drill cone, exhaust stacks and three heavy legs."""
    m = Model("mining_tripod")

    for index, (x, z, sx, sz) in enumerate(((-24, -10, -10, -6), (24, -10, 10, -6), (0, 28, 0, 8))):
        barbed_leg(m, f"leg{index}", (x, -168, z), 86, 90, 10, splay_x=sx, splay_z=sz, foot=0,
                   barbs=3, barb=5)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 11)
        ball(m.by_name[f"leg{index}_shin"], 0, 0, 0, 9)
        foot = m.part(f"leg{index}_foot", parent=f"leg{index}_shin", pivot=(0, 88, 0))
        foot.box(-9, 0, -12, 18, 5, 24)
        foot.box(-7, 4, -14, 14, 4, 8)

    body = m.part("body")
    rounded_hull(body, 0, -206, 4, 76, 40, 62, layers=5, curve=0.55, cell=18)
    collar(body, 0, -170, 0, 78, 6, 64, 4)
    collar(body, 0, -214, 0, 60, 6, 50, 4)

    # the great scoop of teeth sloping down at the front
    scoop = m.part("scoop", parent="body", pivot=(0, -190, -26), rot=(22.0, 0.0, 0.0))
    scoop.box(-30, -6, -34, 60, 8, 36)
    scoop.box(-26, 2, -44, 52, 12, 12)
    for index, x in enumerate((-22, -11, 0, 11, 22)):
        scoop.box(x - 3, 10, -48, 6, 8, 10)
        scoop.box(x - 2, 16, -52, 5, 6, 8)
    scoop.glow_box(-18, 2, -30, 36, 4, 3)

    # drill cone under the chin
    drill = m.part("drill", parent="body", pivot=(0, -176, -40), rot=(38.0, 0.0, 0.0))
    drill.box(-9, -9, 0, 18, 18, 16)
    drill.box(-6, -6, 14, 12, 12, 14)
    drill.box(-3, -3, 26, 7, 7, 12)
    drill.glow_box(-2, -2, 36, 4, 4, 2)

    # exhaust stacks and ore bins on the back
    for index, x in enumerate((-20, 20)):
        stack = m.part(f"stack{index}", parent="body", pivot=(x, -212, 18))
        stack.box(-5, -22, -5, 10, 22, 10)
        stack.box(-7, -26, -7, 14, 5, 14)
        stack.glow_box(-3, -24, -3, 6, 3, 6)
    bin_part = m.part("bin", parent="body", pivot=(0, -206, 28))
    bin_part.box(-20, -10, 0, 40, 22, 16)
    bin_part.glow_box(-14, -4, -2, 28, 6, 2)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        arm = m.part(name, parent="body", pivot=(side * 36, -190, -8), rot=(18.0, 0.0, side * 16.0))
        arm.box(-5, -4, -4, 10, 10, 34)
        claw = m.part(name + "_claw", parent=name, pivot=(0, 4, 32), rot=(10.0, 0.0, 0.0))
        claw.box(-7, -2, 0, 14, 8, 12)
        claw.box(-8, 4, 8, 4, 4, 14)
        claw.box(4, 4, 8, 4, 4, 14)

    for index, z in enumerate((-8, 6)):
        vent = m.part(f"vent{index}", parent="body", pivot=(0, -186, z))
        vent.box(-30, -2, -2, 60, 4, 4)
    return m


def roger_dean_tripod():
    """Roger Dean's machine: a copper bird of prey — a long crested skull with a hooked beak on an
    S-shaped neck, a slim pelvis, folded blade arms and long thin legs on ball joints."""
    m = Model("roger_dean_tripod")

    for index, (x, z, sx, sz) in enumerate(((-13, -8, -8, -6), (13, -8, 8, -6), (0, 22, 0, 9))):
        tapered_leg(m, f"leg{index}", (x, -178, z), 96, 104, 8, 5, 2, splay_x=sx, splay_z=sz,
                    knee=0.55, spike=3)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 8)
        ball(m.by_name[f"leg{index}_shin"], 0, 0, 0, 7)
        claw_foot(m, f"leg{index}_claw", f"leg{index}_shin", (0, 102, 0), size=9, spread=5)

    pelvis = m.part("pelvis")
    rounded_hull(pelvis, 0, -210, 0, 34, 34, 30, layers=4, curve=0.5, cell=11)
    collar(pelvis, 0, -184, 0, 36, 5, 32, 3)

    neck1 = m.part("neck1", parent="pelvis", pivot=(0, -210, -6), rot=(-28.0, 0.0, 0.0))
    neck1.box(-5, -30, -5, 10, 30, 10)
    collar(neck1, 0, -8, 0, 14, 4, 14, 2)
    neck2 = m.part("neck2", parent="neck1", pivot=(0, -28, 0), rot=(38.0, 0.0, 0.0))
    neck2.box(-4, -26, -4, 9, 26, 9)
    neck2.box(-6, -6, -6, 12, 5, 12)

    head = m.part("head", parent="neck2", pivot=(0, -26, 0), rot=(18.0, 0.0, 0.0))
    head.shell(0, -14, -2, 24, 14, 24, cell=9)
    head.box(-11, -10, -26, 22, 9, 18)
    head.box(-7, -6, -40, 14, 6, 16)
    head.box(-4, -2, -52, 9, 4, 14)
    head.box(-3, 2, -60, 7, 3, 9)
    head.box(-9, 3, -34, 18, 4, 14)
    head.glow_box(-8, -8, -14, 16, 6, 2)
    # crest fin and the brow horns of the skull
    head.box(-2, -24, -2, 5, 12, 18)
    head.box(-11, -20, -18, 3, 9, 6)
    head.box(8, -20, -18, 3, 9, 6)
    for index, x in enumerate((-6, 0, 6)):
        head.box(x - 1, -3, -36, 3, 3, 6)
    for index, x in enumerate((-9, 9)):
        head.box(x - 2, 8, 0, 5, 5, 10)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        arm = m.part(name, parent="pelvis", pivot=(side * 22, -206, -2), rot=(-40.0, 0.0, -side * 24.0))
        arm.box(-3, 0, -3, 6, 34, 6)
        ball(arm, 0, 33, 0, 6)
        fore = m.part(name + "_fore", parent=name, pivot=(0, 32, 0), rot=(66.0, 0.0, side * 12.0))
        fore.box(-3, 0, -3, 6, 32, 6)
        fore.box(-4, 28, -8, 8, 6, 12)
        fore.box(-3, 32, -12, 2, 6, 6)
        fore.box(1, 32, -12, 2, 6, 6)
    tail = m.part("tail", parent="pelvis", pivot=(0, -192, 18), rot=(58.0, 0.0, 0.0))
    tail.box(-2, 0, -2, 5, 40, 5)
    tail.box(-1, 38, -1, 3, 26, 3)
    tail.glow_box(-1, 62, -1, 3, 5, 3)

    for index, y in enumerate((-204, -196, -188)):
        coil = m.part(f"coil{index}", parent="pelvis", pivot=(0, y, 0))
        collar(coil, 0, 0, 0, 38, 3, 34, 2)
    return m

def city_tripod():
    """The city walker: a small dark carriage carrying a huge glass dome, every window of the city
    inside burning behind the panes. Three thin legs, no weapons in sight."""
    m = Model("city_tripod")

    for index, (x, z, sx, sz) in enumerate(((-16, -8, -9, -6), (16, -8, 9, -6), (0, 24, 0, 9))):
        tapered_leg(m, f"leg{index}", (x, -168, z), 88, 92, 7, 5, 2, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=3)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 8)
        leg_bands(m, f"leg{index}", 88, 92, 5, thigh_bands=(24, 56), shin_bands=(28, 62))

    carriage = m.part("carriage")
    rounded_hull(carriage, 0, -186, 0, 44, 20, 40, layers=4, curve=0.5, cell=13)
    collar(carriage, 0, -170, 0, 46, 5, 42, 3)
    collar(carriage, 0, -192, 0, 42, 5, 38, 3)
    for index, x in enumerate((-12, 0, 12)):
        carriage.glow_box(x - 4, -180, -21, 8, 10, 2)

    # the dome: sphere of panes with lit windows inside
    dome = m.part("dome", parent="carriage", pivot=(0, -192, 0))
    radius = 34
    levels = 9
    for level in range(levels):
        t = (level + 0.5) / levels
        shrink = math.sqrt(max(0.06, 1.0 - (2.0 * t - 1.0) ** 2))
        width = max(4, int(round(2 * radius * shrink)))
        y = int(round(-2 * radius * t))
        ring = max(6, int(round(width / 7.0)))
        for index in range(ring):
            angle = 6.283185307179586 * index / ring
            x = int(round(width * 0.5 * math.cos(angle)))
            z = int(round(width * 0.5 * math.sin(angle)))
            dome.box(x - 3, y, z - 3, 6, 7, 6)
    # the windows of the city
    for index in range(18):
        angle = 6.283185307179586 * index / 18.0
        level = 2 + index % 5
        t = (level + 0.5) / levels
        shrink = math.sqrt(max(0.06, 1.0 - (2.0 * t - 1.0) ** 2))
        ring_radius = radius * shrink * 0.62
        dome.glow_box(int(round(ring_radius * math.cos(angle))) - 2, int(round(-2 * radius * t)),
                      int(round(ring_radius * math.sin(angle))) - 2, 4, 4, 4)
    dome.box(-6, -2 * radius - 6, -6, 12, 8, 12)
    dome.glow_box(-4, -2 * radius - 10, -4, 8, 4, 8)

    spire = m.part("spire", parent="dome", pivot=(0, -2 * radius - 6, 0))
    spire.box(-2, -26, -2, 4, 26, 4)
    eye(m, "spire_light", "spire", (0, -28, 0), (5, 5))
    return m


def factory_tripod():
    """The Martian factory: a riveted barrel of vertical slats with two furnace mouths, a chimney
    spitting flame, pincer cranes and four thin legs."""
    m = Model("factory_tripod")

    for index, (x, z, sx, sz) in enumerate(((-20, -14, -10, -6), (20, -14, 10, -6),
                                            (-14, 20, -6, 8), (14, 20, 6, 8))):
        tapered_leg(m, f"leg{index}", (x, -168, z), 86, 90, 7, 5, 2, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=3)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 8)
        leg_bands(m, f"leg{index}", 86, 90, 5, thigh_bands=(22, 54), shin_bands=(26, 60))

    body = m.part("body")
    body.shell(0, -186, 0, 58, 12, 54, cell=14)
    for level in range(5):
        collar(body, 0, -216 + level * 14, 0, 60 - level, 4, 56 - level, 3)
    body.shell(0, -216, 0, 52, 10, 48, cell=13)
    # vertical slats around the barrel
    for index in range(10):
        angle = 6.283185307179586 * index / 10.0
        x = int(round(29 * math.cos(angle)))
        z = int(round(27 * math.sin(angle)))
        body.box(x - 2, -214, z - 2, 5, 58, 5)
    # furnace mouths
    for index, x in enumerate((-20, 20)):
        body.box(x - 9, -196, -28, 18, 16, 4)
        body.glow_box(x - 6, -192, -30, 12, 9, 2)

    chimney = m.part("chimney", parent="body", pivot=(0, -216, 6))
    chimney.box(-9, -32, -9, 18, 32, 18)
    chimney.box(-11, -36, -11, 22, 6, 22)
    flame = m.part("flame", parent="chimney", pivot=(0, -38, 0))
    flame.glow_box(-7, -18, -7, 14, 18, 14)
    flame.glow_box(-4, -30, -4, 8, 14, 8)
    flame.glow_box(-2, -40, -2, 4, 12, 4)

    # pincer cranes on the shoulders
    for side, name in ((-1, "crane_l"), (1, "crane_r")):
        boom = m.part(name, parent="body", pivot=(side * 28, -206, -6), rot=(0.0, 0.0, -side * 34.0))
        boom.box(-4, 0, -4, 8, 44, 8)
        boom.box(-3, 0, -10, 6, 6, 20)
        elb = m.part(name + "_fore", parent=name, pivot=(0, 42, 0), rot=(0.0, 0.0, side * 54.0))
        elb.box(-3, 0, -3, 7, 40, 7)
        jaw = m.part(name + "_jaw", parent=name + "_fore", pivot=(0, 38, 0))
        jaw.box(-8, 0, -5, 16, 6, 10)
        jaw.box(-8, 6, -5, 4, 10, 5)
        jaw.box(4, 6, -5, 4, 10, 5)

    for index, x in enumerate((-14, 14)):
        pipe = m.part(f"pipe{index}", parent="body", pivot=(x, -212, -22))
        pipe.box(-3, -6, -3, 7, 26, 7)
        pipe.box(-4, 18, -8, 9, 5, 12)
    return m


def cage_tripod():
    """The cage walker: a wireframe sphere of Martian ribs with a single red eye burning inside,
    carried on four long angular legs that stride far out from the hull."""
    m = Model("cage_tripod")

    for index, (x, z, sx, sz) in enumerate(((-30, -20, 12, 8), (30, -20, -12, 8),
                                            (-24, 26, 10, -8), (24, 26, -10, -8))):
        hip = m.part(f"leg{index}", pivot=(x, -196, z), rot=(0.0, 0.0, sx))
        hip.box(-4, 0, -4, 9, 58, 9)
        ball(hip, 0, 0, 0, 9)
        knee = m.part(f"leg{index}_mid", parent=f"leg{index}", pivot=(0, 56, 0),
                      rot=(sz * 0.4, 0.0, -sx * 0.5))
        knee.box(-4, 0, -4, 8, 52, 8)
        ball(knee, 0, 0, 0, 8)
        shin = m.part(f"leg{index}_shin", parent=f"leg{index}_mid", pivot=(0, 50, 0),
                      rot=(-sz * 0.3, 0.0, sx * 0.18))
        shin.box(-3, 0, -3, 7, 70, 7)
        shin.box(-6, 68, -8, 12, 5, 18)
        shin.box(-5, 73, -14, 10, 4, 8)

    hub = m.part("hub")
    rounded_hull(hub, 0, -206, 0, 36, 16, 34, layers=3, curve=0.45, cell=11)
    collar(hub, 0, -194, 0, 38, 5, 36, 3)

    cage_part = m.part("cage", parent="hub", pivot=(0, -214, 0))
    radius = 32
    levels = 7
    for level in range(levels):
        t = (level + 0.5) / levels
        shrink = math.sqrt(max(0.08, 1.0 - (2.0 * t - 1.0) ** 2))
        ring_radius = radius * shrink
        y = int(round(-2 * radius * t))
        count = max(8, int(round(ring_radius / 4.0)))
        for index in range(count):
            angle = 6.283185307179586 * index / count
            x = int(round(ring_radius * math.cos(angle)))
            z = int(round(ring_radius * math.sin(angle)))
            cage_part.box(x - 2, y, z - 2, 4, 4, 4)
    for index in range(8):
        angle = 6.283185307179586 * index / 8.0
        steps = 9
        for step in range(steps):
            t = (step + 0.5) / steps
            shrink = math.sqrt(max(0.08, 1.0 - (2.0 * t - 1.0) ** 2))
            ring_radius = radius * shrink
            y = int(round(-2 * radius * t))
            x = int(round(ring_radius * math.cos(angle)))
            z = int(round(ring_radius * math.sin(angle)))
            cage_part.box(x - 2, y, z - 2, 4, 4, 4)
    cage_part.box(-4, -2 * radius - 4, -4, 8, 8, 8)
    cage_part.box(-4, 2 * radius - 4, -4, 8, 8, 8)

    core = m.part("core", parent="cage", pivot=(0, -radius, 0))
    ball(core, 0, 0, 0, 14)
    eye(m, "eye", "core", (0, -2, -13), (12, 12), depth=3)
    for index, x in enumerate((-18, 18)):
        core.glow_box(x - 3, -3, -10, 6, 6, 3)

    for side, name in ((-1, "claw_l"), (1, "claw_r")):
        arm = m.part(name, parent="hub", pivot=(side * 16, -200, -12), rot=(24.0, 0.0, -side * 18.0))
        arm.box(-2, 0, -2, 5, 26, 5)
        arm.box(-4, 24, -6, 9, 5, 12)
        arm.box(-5, 28, -10, 3, 8, 3)
        arm.box(2, 28, -10, 3, 8, 3)
    return m

def humanoid_tripod():
    """The humanoid Martian: a hooded skull with a lit visor, a chest full of instruments, two
    jointed arms with grippers hanging free of the hull, on three legs."""
    m = Model("humanoid_tripod")

    for index, (x, z, sx, sz) in enumerate(((-20, -12, -9, -6), (20, -12, 9, -6), (0, 24, 0, 9))):
        tapered_leg(m, f"leg{index}", (x, -182, z), 90, 94, 9, 6, 3, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=3)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 10)
        ball(m.by_name[f"leg{index}_shin"], 0, 0, 0, 8)

    torso = m.part("torso")
    rounded_hull(torso, 0, -230, 0, 46, 46, 36, layers=5, curve=0.42, cell=14)
    collar(torso, 0, -188, 0, 44, 6, 34, 3)
    collar(torso, 0, -226, 0, 40, 5, 32, 3)
    torso.glow_box(-13, -222, -19, 26, 6, 3)
    for index, x in enumerate((-12, 0, 12)):
        torso.box(x - 5, -212, -19, 10, 18, 3)
        torso.glow_box(x - 2, -208, -20, 4, 10, 2)

    # the cowl: side plates and a back plate, open at the front
    hood = m.part("hood", parent="torso", pivot=(0, -234, -2))
    hood.box(-26, -22, -18, 5, 24, 40)
    hood.box(21, -22, -18, 5, 24, 40)
    hood.box(-26, -22, 16, 52, 24, 6)
    hood.box(-22, -26, -14, 44, 6, 32)
    hood.box(-20, -18, -20, 40, 4, 5)
    collar(hood, 0, -26, 2, 50, 4, 40, 3)

    head = m.part("head", parent="hood", pivot=(0, -10, -6))
    head.box(-12, -16, -14, 24, 16, 22)
    head.box(-8, -12, -20, 16, 8, 8)
    head.glow_box(-9, -10, -22, 18, 5, 2)
    head.box(-5, -2, -18, 10, 5, 8)
    head.box(-2, -22, -6, 4, 7, 12)

    for side, name in ((-1, "arm_l"), (1, "arm_r")):
        shoulder = m.part(name, parent="torso", pivot=(side * 25, -222, -4),
                          rot=(-14.0, 0.0, -side * 14.0))
        ball(shoulder, 0, 0, 0, 9)
        shoulder.box(-5, 0, -5, 10, 36, 10)
        fore = m.part(name + "_fore", parent=name, pivot=(0, 34, 0), rot=(26.0, 0.0, 0.0))
        fore.box(-4, 0, -4, 9, 32, 9)
        fore.box(-6, 28, -6, 12, 8, 12)
        for index, x in enumerate((-5, 0, 5)):
            fore.box(x - 1, 34, -8, 3, 10, 4)
            fore.box(x - 1, 40, -12, 3, 4, 6)

    for index, z in enumerate((-10, 8)):
        belt = m.part(f"belt{index}", parent="torso", pivot=(0, -196, z))
        belt.box(-22, -2, -2, 44, 4, 4)
    return m

def signal_scout_tripod():
    """The scout: a bare sphere of lenses on a stem, a radar dish above it and three very thin legs.
    It carries no weapon — only the whip antenna that calls the fleet."""
    m = Model("signal_scout_tripod")

    for index, (x, z, sx, sz) in enumerate(((-15, -8, -8, -6), (15, -8, 8, -6), (0, 22, 0, 8))):
        tapered_leg(m, f"leg{index}", (x, -206, z), 96, 100, 6, 4, 2, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=2)
        leg_bands(m, f"leg{index}", 96, 100, 4, thigh_bands=(26, 62), shin_bands=(30, 68))

    body = m.part("body")
    rounded_hull(body, 0, -224, 0, 30, 18, 28, layers=3, curve=0.4, cell=10)
    collar(body, 0, -208, 0, 32, 5, 30, 3)

    head = m.part("head", parent="body", pivot=(0, -228, 0))
    ball(head, 0, -14, 0, 20)
    collar(head, 0, -6, 0, 34, 4, 32, 2)
    eye(m, "eye_main", "head", (0, -12, -18), (14, 14), depth=3)
    eye(m, "eye_side_l", "head", (-12, -20, -12), (6, 6), depth=2)
    eye(m, "eye_side_r", "head", (12, -20, -12), (6, 6), depth=2)
    head.box(-18, -16, -4, 4, 8, 10)
    head.box(14, -16, -4, 4, 8, 10)

    dish = m.part("dish", parent="head", pivot=(0, -34, 0), rot=(-24.0, 0.0, 0.0))
    dish.box(-2, -16, -2, 4, 16, 4)
    dish.box(-16, -20, -4, 32, 4, 10)
    dish.box(-12, -24, -3, 24, 4, 8)
    dish.glow_box(-6, -22, -4, 12, 3, 2)
    mast = m.part("mast", parent="dish", pivot=(0, -20, 0))
    mast.box(-1, -34, -1, 3, 34, 3)
    mast.box(-5, -30, -1, 3, 2, 3)
    mast.box(3, -24, -1, 3, 2, 3)
    mast.glow_box(-2, -38, -2, 5, 5, 5)

    for index, x in enumerate((-9, 9)):
        fin = m.part(f"fin{index}", parent="body", pivot=(x, -214, 6))
        fin.box(-2, -10, -1, 4, 12, 10)
    return m

def frost_tripod():
    """The frost walker: a ribbed barrel wrapped in vent fins, a crown of ice prongs on top and three
    thin legs. Everything around it is rimed white."""
    m = Model("frost_tripod")

    for index, (x, z, sx, sz) in enumerate(((-18, -10, -9, -6), (18, -10, 9, -6), (0, 24, 0, 8))):
        tapered_leg(m, f"leg{index}", (x, -186, z), 94, 98, 8, 5, 2, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=3)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 9)
        leg_bands(m, f"leg{index}", 94, 98, 5, thigh_bands=(26, 62), shin_bands=(30, 68))

    body = m.part("body")
    body.shell(0, -196, 0, 40, 10, 36, cell=13)
    for level in range(4):
        collar(body, 0, -230 + level * 12, 0, 42 - level * 2, 4, 38 - level * 2, 3)
    body.shell(0, -238, 0, 34, 12, 32, cell=11)
    body.shell(0, -226, 0, 38, 12, 34, cell=12)
    # vent fins down both flanks
    for side, name in ((-1, "fin_l"), (1, "fin_r")):
        fin = m.part(name, parent="body", pivot=(side * 19, -230, 0))
        for row in range(4):
            fin.box(side * 2 if side > 0 else side * 6, row * 12, -14, 8, 3, 28)
    # the crown of ice prongs
    crown = m.part("crown", parent="body", pivot=(0, -238, 0))
    for index in range(6):
        angle = 6.283185307179586 * index / 6.0
        x = int(round(12 * math.cos(angle)))
        z = int(round(12 * math.sin(angle)))
        crown.box(x - 2, -14, z - 2, 4, 14, 4)
        crown.box(x - 1, -20, z - 1, 3, 8, 3)
    crown.glow_box(-5, -24, -5, 10, 6, 10)
    # the vent that does the freezing
    mouth = m.part("mouth", parent="body", pivot=(0, -212, -18))
    mouth.box(-9, -9, -10, 18, 18, 12)
    mouth.glow_box(-6, -6, -12, 12, 12, 3)
    mouth.box(-11, -4, 0, 4, 8, 6)
    mouth.box(7, -4, 0, 4, 8, 6)
    for index, z in enumerate((-10, 0, 10)):
        pipe_part = m.part(f"pipe{index}", parent="body", pivot=(0, -206, z))
        pipe_part.box(-22, -2, -2, 44, 4, 4)
        ball(pipe_part, 0, 0, 0, 5)
    return m


def storm_tripod():
    """The storm caller: a narrow tower of copper coil rings with three prongs on the crown and a
    lightning rod, carried on three legs."""
    m = Model("storm_tripod")

    for index, (x, z, sx, sz) in enumerate(((-20, -12, -8, -5), (20, -12, 8, -5), (0, 26, 0, 8))):
        tapered_leg(m, f"leg{index}", (x, -176, z), 88, 92, 9, 6, 3, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=3)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 10)
        ball(m.by_name[f"leg{index}_shin"], 0, 0, 0, 8)

    base = m.part("base")
    rounded_hull(base, 0, -196, 0, 40, 18, 36, layers=3, curve=0.4, cell=13)
    collar(base, 0, -182, 0, 42, 5, 38, 3)

    tower = m.part("tower", parent="base", pivot=(0, -196, 0))
    tower.box(-11, -70, -11, 22, 70, 22)
    for level in range(5):
        collar(tower, 0, -66 + level * 14, 0, 26, 4, 26, 3)
    # the copper coil: rings that get wider towards the crown
    coil = m.part("coil", parent="tower", pivot=(0, -66, 0))
    for level in range(4):
        width = 26 + level * 6
        coil.box(-width // 2, -level * 9 - 6, -width // 2, width, 3, width)
        coil.glow_box(-6, -level * 9 - 5, -width // 2 - 1, 12, 2, 2)
        coil.glow_box(-6, -level * 9 - 5, width // 2 - 1, 12, 2, 2)

    crown = m.part("crown", parent="tower", pivot=(0, -70, 0))
    crown.box(-9, -6, -9, 18, 6, 18)
    for index, (x, z) in enumerate(((-8, -8), (8, -8), (0, 9))):
        prong = m.part(f"prong{index}", parent="crown", pivot=(x, -6, z), rot=(-12.0 if z < 0 else 12.0, 0.0, 0.0))
        prong.box(-2, -26, -2, 5, 26, 5)
        prong.glow_box(-3, -30, -3, 7, 5, 7)
    rod = m.part("rod", parent="crown", pivot=(0, -6, 0))
    rod.box(-1, -22, -1, 3, 22, 3)
    rod.glow_box(-3, -28, -3, 7, 7, 7)
    for index in range(3):
        ring = m.part(f"ring{index}", parent="crown", pivot=(0, -10 - index * 6, 0))
        collar(ring, 0, 0, 0, 14 - index * 3, 2, 14 - index * 3, 2)
    return m


def weaver_tripod():
    """The weaver: a low, round abdomen on six arched legs, with a bank of spinnerets at the back and
    a cluster of small eyes at the front."""
    m = Model("weaver_tripod")

    for index, (x, z, out, inn) in enumerate(((-24, -18, 22, 4), (24, -18, 22, 4),
                                              (-27, 2, 26, 6), (27, 2, 26, 6),
                                              (-20, 22, 20, 3), (20, 22, 20, 3))):
        arched_limb(m, f"leg{index}", (x, -206, z), 58, 52, 84, 7, out_deg=out, in_deg=inn, foot=9)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 9)
        ball(m.by_name[f"leg{index}_mid"], 0, 0, 0, 7)

    body = m.part("body")
    rounded_hull(body, 0, -240, 4, 52, 26, 56, layers=5, curve=0.55, cell=16)
    collar(body, 0, -218, 4, 54, 5, 58, 4)
    # chitin ridges over the abdomen
    for index in range(4):
        ridge = m.part(f"ridge{index}", parent="body", pivot=(0, -238 + index * 6, 4))
        ridge.box(-26, -2, -28, 52, 3, 56)
    eye(m, "eye_l", "body", (-13, -232, -25), (7, 7))
    eye(m, "eye_r", "body", (13, -232, -25), (7, 7))
    for index, x in enumerate((-6, 0, 6)):
        eye(m, f"eye_small{index}", "body", (x, -224, -27), (4, 4))

    # spinnerets
    spinner = m.part("spinner", parent="body", pivot=(0, -226, 30))
    spinner.box(-12, -8, 0, 24, 16, 10)
    for index, x in enumerate((-8, -3, 3, 8)):
        nozzle = m.part(f"nozzle{index}", parent="spinner", pivot=(x, 0, 9))
        nozzle.box(-2, -2, 0, 4, 4, 8)
        nozzle.glow_box(-1, -1, 8, 3, 3, 3)
    for side, name in ((-1, "fang_l"), (1, "fang_r")):
        fang = m.part(name, parent="body", pivot=(side * 12, -224, -26), rot=(28.0, 0.0, 0.0))
        fang.box(-3, 0, -10, 6, 6, 12)
        fang.box(-2, 4, -12, 4, 8, 4)
    return m


def blink_tripod():
    """The blinker: a slim hexagonal core inside a frame of drifting vanes, one huge violet lens and
    three legs that never quite touch the ground."""
    m = Model("blink_tripod")

    for index, (x, z, sx, sz) in enumerate(((-19, -11, -8, -5), (19, -11, 8, -5), (0, 25, 0, 8))):
        tapered_leg(m, f"leg{index}", (x, -182, z), 92, 96, 8, 5, 2, splay_x=sx, splay_z=sz,
                    knee=0.5, spike=3)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 9)
        leg_bands(m, f"leg{index}", 92, 96, 5, thigh_bands=(24, 58), shin_bands=(28, 64))

    body = m.part("body")
    body.shell(0, -212, 0, 34, 12, 30, cell=11)
    body.shell(0, -224, 0, 30, 12, 26, cell=10)
    body.shell(0, -200, 0, 26, 12, 24, cell=9)
    collar(body, 0, -216, 0, 36, 4, 32, 3)
    collar(body, 0, -198, 0, 30, 4, 28, 3)

    # the frame of vanes
    for index in range(4):
        angle = 6.283185307179586 * index / 4.0
        vane = m.part(f"vane{index}", parent="body",
                      pivot=(int(round(20 * math.cos(angle))), -212, int(round(20 * math.sin(angle)))),
                      rot=(0.0, index * 90.0, 0.0))
        vane.box(-3, -22, -3, 6, 44, 6)
        vane.box(-9, -26, -2, 18, 4, 4)
        vane.glow_box(-2, 16, -4, 4, 4, 4)

    head = m.part("head", parent="body", pivot=(0, -218, -15))
    head.box(-12, -12, -10, 24, 24, 12)
    eye(m, "eye", "head", (0, 0, -12), (18, 18), depth=3)
    head.glow_box(-16, -4, -4, 5, 8, 6)
    head.glow_box(11, -4, -4, 5, 8, 6)

    for index, x in enumerate((-9, 9)):
        node = m.part(f"node{index}", parent="body", pivot=(x, -196, 4))
        node.box(-4, -4, -4, 8, 8, 8)
        node.glow_box(-2, -2, -5, 4, 4, 3)
    return m


def harvester_tripod():
    """The harvester: a wide reaping body with a hopper on its back and two long scythe arms that
    sweep the ground in front of it."""
    m = Model("harvester_tripod")

    for index, (x, z, sx, sz) in enumerate(((-22, -12, -9, -6), (22, -12, 9, -6), (0, 26, 0, 8))):
        barbed_leg(m, f"leg{index}", (x, -180, z), 90, 94, 9, splay_x=sx, splay_z=sz, foot=0,
                   barbs=3, barb=5)
        ball(m.by_name[f"leg{index}"], 0, 0, 0, 10)
        foot = m.part(f"leg{index}_foot", parent=f"leg{index}_shin", pivot=(0, 92, 0))
        foot.box(-8, 0, -12, 16, 5, 24)

    body = m.part("body")
    rounded_hull(body, 0, -214, 0, 66, 24, 48, layers=4, curve=0.45, cell=17)
    collar(body, 0, -190, 0, 68, 5, 50, 4)
    body.glow_box(-24, -206, -24, 48, 6, 2)
    # the hopper on the back
    hopper = m.part("hopper", parent="body", pivot=(0, -214, 16))
    hopper.box(-24, -16, 0, 48, 16, 26)
    hopper.box(-20, -22, 4, 40, 8, 18)
    hopper.glow_box(-14, -20, 22, 28, 4, 2)

    # the scythes
    for side, name in ((-1, "scythe_l"), (1, "scythe_r")):
        arm = m.part(name, parent="body", pivot=(side * 30, -204, -8), rot=(0.0, 0.0, -side * 18.0))
        arm.box(-4, -6, -4, 8, 34, 8)
        blade = m.part(name + "_blade", parent=name, pivot=(0, 30, 0), rot=(0.0, -side * 34.0, side * 46.0))
        blade.box(-3, 0, -3, 7, 40, 7)
        blade.box(-4, 34, -22, 8, 8, 26)
        blade.box(-5, 36, -30, 10, 5, 10)
        blade.glow_box(-2, 37, -24, 4, 3, 6)

    # the reaping mouth
    mouth = m.part("mouth", parent="body", pivot=(0, -196, -26))
    mouth.box(-20, -10, -6, 40, 16, 8)
    for index, x in enumerate((-14, -7, 0, 7, 14)):
        mouth.box(x - 2, -12, -10, 4, 8, 6)
        mouth.box(x - 1, -16, -12, 3, 5, 4)
    mouth.glow_box(-16, -4, -8, 32, 5, 3)
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

    # eighth wave: eight machines drawn from the next set of cover art
    ("mothership", finalise(mothership_tripod(), "mothership")),
    ("mining", finalise(mining_tripod(), "mining")),
    ("rogerDean", finalise(roger_dean_tripod(), "rogerDean")),
    ("city", finalise(city_tripod(), "city")),
    ("factory", finalise(factory_tripod(), "factory")),
    ("cage", finalise(cage_tripod(), "cage")),
    ("humanoid", finalise(humanoid_tripod(), "humanoid")),
    ("signalScout", finalise(signal_scout_tripod(), "signalScout")),

    # ninth wave: five machines invented for this update
    ("frost", finalise(frost_tripod(), "frost")),
    ("storm", finalise(storm_tripod(), "storm")),
    ("weaver", finalise(weaver_tripod(), "weaver")),
    ("blink", finalise(blink_tripod(), "blink")),
    ("harvester", finalise(harvester_tripod(), "harvester")),
]


# The human army: ordinary bipeds and machines, authored at 16 pixels per block as well.
def human_apc():
    """An armoured personnel carrier: a sloped hull on eight wheels with a small autocannon turret."""
    m = Model("human_apc", texture=(128, 128))
    body = m.part("body")
    body.box(-14, -26, -24, 28, 12, 48)                  # hull
    body.box(-12, -32, -18, 24, 6, 30)                   # raised roof
    body.box(-13, -20, -26, 26, 4, 4)                    # glacis lip
    body.box(-14, -14, -22, 28, 4, 44)                   # belly plate

    for side, name in ((-1, "wheels_l"), (1, "wheels_r")):
        wheels = m.part(name, parent="body", pivot=(side * 15, -8, 0))
        for index, z in enumerate((-18, -7, 8, 19)):
            wheels.box(-2, -5, z - 5, 4, 10, 10)          # tyre
            wheels.box(-2, -2, z - 2, 4, 4, 4)            # hub

    turret = m.part("turret", parent="body", pivot=(0, -32, -4))
    turret.box(-8, -8, -9, 16, 8, 18)
    turret.box(-2, -5, -26, 4, 4, 18)                     # autocannon
    turret.box(-3, -6, -28, 6, 6, 3)                      # muzzle
    turret.glow_box(-5, -6, 6, 10, 3, 2)                  # sight
    eye(m, "lamp_l", "body", (-12, -22, -24), (4, 3))
    eye(m, "lamp_r", "body", (8, -22, -24), (4, 3))
    return m


def human_artillery():
    """A towed field gun: split trail, shield plate, two spoked wheels and a long barrel raised to
    lob shells over the horizon."""
    m = Model("human_artillery", texture=(128, 128))
    body = m.part("body")
    body.box(-7, -12, -10, 14, 6, 22)                     # cradle
    body.box(-9, -20, 2, 4, 10, 18)                       # trail left
    body.box(5, -20, 2, 4, 10, 18)                        # trail right
    body.box(-1, -18, 18, 2, 8, 6)                        # spade

    for side, name in ((-1, "wheel_l"), (1, "wheel_r")):
        wheel = m.part(name, parent="body", pivot=(side * 10, -8, -2))
        wheel.box(-2, -8, -8, 4, 16, 16)                  # tyre
        wheel.box(-2, -4, -4, 4, 8, 8)                    # hub
        for index in range(4):
            wheel.box(-2, -7 + index * 4, -8, 4, 1, 16)   # spokes read as tread

    shield = m.part("shield", parent="body", pivot=(0, -18, -8))
    shield.box(-11, -8, 0, 22, 16, 3)
    shield.glow_box(-6, -4, -1, 12, 3, 2)

    barrel = m.part("barrel", parent="body", pivot=(0, -16, -6), rot=(-24.0, 0.0, 0.0))
    barrel.box(-3, -3, -34, 6, 6, 34)
    barrel.box(-4, -4, -38, 8, 8, 6)
    barrel.box(-5, -5, -40, 10, 10, 3)
    barrel.glow_box(-2, -2, -41, 4, 4, 2)
    return m


def human_jet():
    """A jet interceptor: needle nose, delta wings, twin tail fins and two burning exhausts."""
    m = Model("human_jet", texture=(128, 128))
    body = m.part("body")
    body.shell(0, -18, 0, 10, 10, 58, cell=9)             # fuselage
    body.box(-3, -22, -28, 6, 6, 14)                      # nose
    body.box(-1, -21, -32, 2, 3, 6)                       # pitot
    body.box(-6, -14, 22, 12, 8, 6)                       # exhaust block

    for side, name in ((-1, "wing_l"), (1, "wing_r")):
        wing = m.part(name, parent="body", pivot=(side * 4, -14, 2))
        wing.box(0, -2, -14, side * 30, 3, 26)            # delta wing
        wing.box(side * 12, -2, 10, side * 14, 3, 10)     # tailplane
        wing.box(side * 22, -3, -6, 5, 5, 5)              # wingtip pod
        wing.box(side * 16, -4, -2, 3, 3, 14)             # missile rail

    tail = m.part("tail", parent="body", pivot=(0, -18, 22))
    tail.box(-1, -20, -2, 2, 20, 8)                       # fin
    tail.box(-10, -6, 0, 20, 3, 6)                        # tailplane
    eye(m, "canopy", "body", (0, -20, -18), (6, 4))
    exhaust = m.part("exhaust", parent="body", pivot=(0, -10, 26))
    exhaust.glow_box(-4, -3, 0, 8, 6, 4)
    return m


def human_sniper():
    """A sniper in a ghillie cape: hooded head, long scoped rifle, one knee down."""
    m = Model("human_sniper", texture=(128, 128))
    for side, name in ((-1, "leg_l"), (1, "leg_r")):
        limb = m.part(name, pivot=(side * 3, -16, 0))
        limb.box(-2, 0, -2, 4, 12, 4)
        limb.box(-2, 11, -3, 4, 4, 6)
        if side > 0:                                       # the right knee is down
            limb.box(-3, 6, -5, 6, 5, 10)

    body = m.part("body", pivot=(0, -16, 0))
    body.box(-5, -14, -3, 10, 14, 6)
    body.box(-6, -13, 3, 12, 11, 4)                       # cape back
    body.box(-7, -4, -4, 14, 4, 8)                        # cape hem
    head = m.part("head", parent="body", pivot=(0, -14, 0))
    head.box(-4, -8, -4, 8, 8, 8)
    head.box(-5, -11, -5, 10, 4, 10)                      # ghillie hood
    head.box(-6, -8, -6, 12, 3, 2)                        # hood brim
    eye(m, "scope", "head", (0, -5, -6), (4, 2))

    arm = m.part("arm_r", parent="body", pivot=(6, -12, 0))
    arm.box(-2, 0, -2, 4, 9, 4)
    arm.box(-1, 7, -12, 3, 3, 26)                         # long rifle
    arm.box(-1, 6, -4, 3, 5, 6)                           # stock and magazine
    arm.box(-1, 9, -14, 3, 3, 6)                          # scope
    arm.box(-1, 8, -20, 2, 2, 6)                          # barrel
    arm_l = m.part("arm_l", parent="body", pivot=(-6, -12, 0))
    arm_l.box(-2, 0, -2, 4, 9, 4)
    arm_l.box(-2, 7, -8, 4, 3, 8)                         # support hand under the barrel
    return m


def human_medic():
    """A field medic: helmet with a white cross, satchel of bandages, a pistol and a lantern."""
    m = Model("human_medic", texture=(128, 128))
    for side, name in ((-1, "leg_l"), (1, "leg_r")):
        limb = m.part(name, pivot=(side * 3, -16, 0))
        limb.box(-2, 0, -2, 4, 13, 4)
        limb.box(-2, 12, -3, 4, 4, 6)

    body = m.part("body", pivot=(0, -16, 0))
    body.box(-5, -14, -3, 10, 14, 6)
    body.box(-6, -12, -3, 12, 8, 7)                       # vest
    body.box(-5, -13, 3, 10, 10, 5)                       # satchel
    body.glow_box(-4, -9, 3, 8, 3, 2)                     # red cross plate
    body.glow_box(-2, -11, 3, 4, 7, 2)

    head = m.part("head", parent="body", pivot=(0, -14, 0))
    head.box(-4, -9, -4, 8, 9, 8)
    head.box(-5, -11, -5, 10, 3, 10)                      # helmet
    head.box(-1, -12, -6, 2, 4, 2)                        # cross bar
    head.box(-3, -11, -6, 6, 2, 2)                        # cross arm
    eye(m, "eye", "head", (0, -5, -5), (5, 2))

    arm_r = m.part("arm_r", parent="body", pivot=(6, -12, 0))
    arm_r.box(-2, 0, -2, 4, 10, 4)
    arm_r.box(-1, 8, -6, 3, 3, 8)                         # pistol
    arm_l = m.part("arm_l", parent="body", pivot=(-6, -12, 0))
    arm_l.box(-2, 0, -2, 4, 10, 4)
    arm_l.box(-3, 8, -4, 5, 6, 5)                         # lantern
    arm_l.glow_box(-2, 9, -3, 3, 4, 3)
    return m

HUMANS = [
    ("soldier", finalise(human_soldier(), "soldier")),
    ("tank", finalise(human_tank(), "tank")),
    ("fighter", finalise(human_fighter(), "fighter")),
    ("warship", finalise(human_warship(), "warship")),
    # fourth wave: the rest of the human army
    ("helicopter", finalise(human_helicopter(), "helicopter")),
    ("submarine", finalise(human_submarine(), "submarine")),
    ("bomber", finalise(human_bomber(), "bomber")),
    # ninth wave: the rest of the human order of battle
    ("apc", finalise(human_apc(), "apc")),
    ("artillery", finalise(human_artillery(), "artillery")),
    ("jet", finalise(human_jet(), "jet")),
    ("sniper", finalise(human_sniper(), "sniper")),
    ("medic", finalise(human_medic(), "medic")),
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
