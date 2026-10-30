#!/usr/bin/env python3
"""Procedural texture generator for the War of the Worlds: Tripods mod.

Every texture is generated from code, so the repository stays small, the palette of all ten
machines stays consistent and anybody can re-roll the art by editing the palette table below.

Usage:  python3 tools/generate_textures.py
"""

import math
import os
import random

from PIL import Image, ImageDraw

ASSETS = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "assets", "wotw")
ENTITY = os.path.join(ASSETS, "textures", "entity")
ITEM = os.path.join(ASSETS, "textures", "item")
BLOCK = os.path.join(ASSETS, "textures", "block")

# name -> (base, dark, light, glow, accent)
PALETTES = {
    "tripod_2005": ((62, 66, 58), (26, 29, 24), (104, 110, 92), (206, 250, 196), (128, 152, 120)),
    "bbc_white_tripod": ((214, 211, 198), (128, 126, 116), (245, 243, 232), (255, 253, 232), (176, 200, 214)),
    "bbc_red_tripod": ((142, 43, 34), (72, 18, 14), (196, 74, 52), (255, 96, 60), (240, 170, 120)),
    "flying_manta": ((74, 79, 82), (34, 37, 40), (120, 128, 130), (156, 255, 122), (96, 168, 128)),
    "book_tripod": ((90, 90, 74), (42, 42, 33), (140, 138, 112), (216, 195, 138), (128, 120, 84)),
    "asylum_walker": ((59, 43, 32), (26, 18, 13), (104, 76, 52), (109, 255, 74), (108, 92, 60)),
    "goliath_tripod": ((122, 90, 46), (58, 41, 19), (186, 148, 84), (255, 179, 71), (150, 120, 70)),
    "martian_heron": ((185, 179, 162), (94, 90, 80), (226, 222, 208), (255, 42, 26), (196, 168, 132)),
    "martian_spider": ((44, 38, 32), (16, 13, 11), (86, 74, 60), (255, 74, 31), (120, 60, 40)),
    "martian_louse": ((92, 58, 36), (38, 24, 15), (140, 96, 58), (255, 207, 90), (150, 112, 60)),
    # the second wave: Martian machines that are not tripods
    "flying_machine": ((96, 104, 110), (38, 44, 50), (150, 160, 168), (150, 255, 120), (170, 120, 70)),
    "electric_machine": ((62, 66, 74), (24, 26, 32), (110, 118, 128), (140, 240, 255), (120, 200, 210)),
    "handling_machine": ((104, 96, 82), (40, 36, 30), (156, 148, 130), (255, 226, 120), (150, 110, 70)),
    "digging_machine": ((124, 96, 62), (46, 34, 22), (176, 146, 102), (255, 170, 80), (150, 90, 50)),
    "tempest": ((112, 106, 96), (42, 38, 34), (168, 162, 148), (255, 120, 60), (150, 118, 80)),
    "bombarding_machine": ((88, 92, 64), (32, 34, 22), (132, 138, 100), (255, 190, 70), (120, 110, 70)),
    "drone": ((136, 140, 146), (58, 60, 66), (184, 188, 196), (190, 110, 255), (150, 150, 160)),
    "human_tripod": ((92, 104, 96), (32, 38, 34), (146, 158, 148), (140, 255, 170), (120, 130, 120)),
    # the creatures themselves: wet, leathery hide
    "martian": ((104, 96, 74), (43, 39, 30), (150, 140, 108), (255, 118, 74), (120, 96, 60)),
    "martian_master": ((74, 62, 62), (28, 22, 22), (118, 100, 96), (255, 64, 48), (96, 64, 56)),
}

BEAMS = {
    "heat_ray": ((198, 255, 186), (84, 236, 128)),
    "laser": ((255, 186, 170), (255, 44, 32)),
    "green_beam": ((214, 255, 174), (96, 255, 64)),
    "lance": ((196, 240, 255), (140, 196, 255)),
}


def clamp(value, low=0, high=255):
    return max(low, min(high, int(value)))


def shade(color, factor, noise=0.0, rnd=None):
    out = []
    for channel in color:
        value = channel * factor
        if noise and rnd is not None:
            value += rnd.uniform(-noise, noise)
        out.append(clamp(value))
    return tuple(out)


# Hues of the separate plates on some hulls: copper, tin and iron are welded together on the Great
# Martian War walkers, so those sheets are tinted instead of being one uniform alloy.
MATERIAL_BANDS = {
    "martian_heron": ((178, 104, 64), (215, 216, 212), (108, 112, 120)),
}


def make_hull_texture(name, palette, size=128, seed=0):
    """Metal hull: vertical gradient, panel seams, rivets, grime and a glowing eye patch."""
    base, dark, light, glow, accent = palette
    rnd = random.Random(f"{name}-{seed}")
    img = Image.new("RGBA", (size, size), base + (255,))
    px = img.load()

    for y in range(size):
        row_factor = 1.12 - 0.34 * (y / (size - 1))
        for x in range(size):
            factor = row_factor + rnd.uniform(-0.05, 0.05)
            px[x, y] = shade(base, factor, 6.0, rnd) + (255,)

    # different sheet metals welded side by side: copper, tin and iron
    bands = MATERIAL_BANDS.get(name)
    if bands:
        width = size // len(bands)
        for index, band in enumerate(bands):
            for x in range(index * width, min(size, (index + 1) * width)):
                for y in range(size):
                    pixel = px[x, y]
                    luma = (pixel[0] + pixel[1] + pixel[2]) / (3.0 * 150.0)
                    px[x, y] = (
                        clamp(pixel[0] * 0.62 + band[0] * luma * 0.42),
                        clamp(pixel[1] * 0.62 + band[1] * luma * 0.42),
                        clamp(pixel[2] * 0.62 + band[2] * luma * 0.42),
                        255,
                    )

    draw = ImageDraw.Draw(img, "RGBA")

    # panel seams
    for y in range(6, size, 11):
        draw.line([(0, y), (size, y)], fill=dark + (170,), width=1)
        draw.line([(0, y + 1), (size, y + 1)], fill=light + (40,), width=1)
    for x in range(9, size, 17):
        draw.line([(x, 0), (x, size)], fill=dark + (90,), width=1)

    # rivets along the seams
    for y in range(6, size, 11):
        for x in range(4, size, 9):
            if rnd.random() < 0.7:
                draw.point((x, y - 1), fill=light + (200,))
                draw.point((x + 1, y - 1), fill=dark + (200,))

    # accent stripes
    for _ in range(3):
        y = rnd.randrange(0, size - 4)
        draw.rectangle([0, y, size, y + 1], fill=accent + (60,))

    # grime towards the bottom
    for _ in range(90):
        x = rnd.randrange(size)
        y = rnd.randrange(int(size * 0.55), size)
        w = rnd.randrange(1, 5)
        h = rnd.randrange(1, 4)
        draw.rectangle([x, y, x + w, y + h], fill=dark + (rnd.randrange(20, 70),))

    # glowing eye patch: the models sample this area for eyes and lenses
    glow_layer = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    gp = glow_layer.load()
    cx, cy = 16.0, 16.0
    for y in range(32):
        for x in range(32):
            # Keep the whole 32x32 patch bright: the models sample it with small cuboids,
            # so a soft halo would end up grey instead of glowing.
            distance = min(1.0, math.hypot(x - cx, y - cy) / 15.0)
            intensity = 0.72 + 0.28 * (1.0 - distance)
            hot = max(0.0, 1.0 - distance * 1.45) ** 0.8
            color = (
                clamp(glow[0] * intensity + 255 * hot),
                clamp(glow[1] * intensity + 255 * hot),
                clamp(glow[2] * intensity + 255 * hot),
            )
            gp[x, y] = color + (clamp(255 * (intensity ** 0.6)),)
    img.alpha_composite(glow_layer, (96, 96))

    return img


def make_beam_texture(name, colors):
    """Soft glowing rod: hot core, dark edges, a bit of sparks."""
    edge, hot = colors
    size = 64
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    px = img.load()
    rnd = random.Random(name)
    for y in range(size):
        for x in range(size):
            distance = max(abs(x - 31.5), abs(y - 31.5)) / 32.0
            core = max(0.0, 1.0 - distance) ** 1.4
            sparkle = 1.0 if rnd.random() < 0.04 else 0.0
            color = (
                clamp(edge[0] + (hot[0] - edge[0]) * core + 90 * sparkle),
                clamp(edge[1] + (hot[1] - edge[1]) * core + 90 * sparkle),
                clamp(edge[2] + (hot[2] - edge[2]) * core + 90 * sparkle),
            )
            px[x, y] = color + (clamp(255 * min(1.0, 0.25 + core * 1.4)),)
    return img


def make_alloy_texture():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.polygon([(4, 12), (6, 4), (11, 2), (13, 7), (9, 13)], fill=(126, 132, 118, 255))
    draw.polygon([(5, 11), (7, 5), (10, 4), (9, 9)], fill=(166, 178, 152, 255))
    draw.line([(6, 5), (9, 9)], fill=(198, 250, 190, 255))
    draw.point((10, 6), fill=(96, 240, 128, 255))
    draw.line([(5, 12), (9, 13)], fill=(58, 62, 54, 255))
    return img


def make_ray_emitter_texture():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    # barrel
    draw.rectangle([1, 5, 12, 8], fill=(74, 78, 70, 255))
    draw.rectangle([1, 5, 12, 6], fill=(112, 118, 104, 255))
    draw.rectangle([2, 9, 6, 12], fill=(52, 55, 48, 255))
    draw.rectangle([11, 4, 13, 9], fill=(96, 102, 88, 255))
    # glowing muzzle
    draw.rectangle([13, 5, 15, 8], fill=(150, 255, 140, 255))
    draw.point((14, 6), fill=(255, 255, 230, 255))
    # power cell
    draw.rectangle([3, 9, 5, 11], fill=(70, 200, 110, 255))
    return img


def make_cylinder_texture():
    img = Image.new("RGBA", (16, 16), (90, 74, 52, 255))
    draw = ImageDraw.Draw(img)
    for y in range(16):
        factor = 1.15 - 0.35 * (y / 15)
        draw.line([(0, y), (15, y)], fill=shade((90, 74, 52), factor) + (255,))
    draw.rectangle([0, 5, 15, 6], fill=(140, 255, 120, 255))
    draw.rectangle([0, 7, 15, 7], fill=(160, 200, 140, 255))
    draw.rectangle([0, 11, 15, 11], fill=(52, 42, 28, 255))
    for x in range(1, 16, 4):
        draw.point((x, 2), fill=(150, 132, 96, 255))
        draw.point((x, 13), fill=(52, 42, 28, 255))
    draw.line([(0, 0), (15, 0)], fill=(58, 48, 32, 255))
    return img


def make_alloy_block_texture():
    """Riveted Martian alloy: the plating the capsules and structures are built from."""
    img = Image.new("RGBA", (16, 16), (118, 124, 110, 255))
    draw = ImageDraw.Draw(img)
    for y in range(16):
        factor = 1.14 - 0.3 * (y / 15.0)
        draw.line([(0, y), (15, y)], fill=shade((118, 124, 110), factor) + (255,))
    draw.line([(0, 3), (15, 3)], fill=(72, 78, 66, 255))
    draw.line([(0, 11), (15, 11)], fill=(72, 78, 66, 255))
    draw.line([(7, 0), (7, 15)], fill=(88, 94, 80, 255))
    for x, y in ((2, 1), (12, 1), (2, 9), (12, 9), (2, 14), (12, 14)):
        draw.point((x, y), fill=(168, 178, 150, 255))
        draw.point((x + 1, y), fill=(66, 70, 60, 255))
    # a vein of glowing Martian green runs through the plate
    draw.line([(3, 6), (6, 7), (9, 6), (12, 8)], fill=(120, 226, 132, 255))
    return img


def make_red_root_texture():
    """Tangled red weed: what a tripod leaves behind on Martian soil."""
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    stems = [((7, 15), (7, 9)), ((7, 11), (3, 6)), ((7, 11), (12, 6)),
             ((7, 13), (4, 11)), ((7, 13), (11, 10)), ((7, 9), (9, 3)), ((7, 9), (5, 4))]
    for (x0, y0), (x1, y1) in stems:
        draw.line([(x0, y0), (x1, y1)], fill=(150, 36, 34, 255))
        draw.point((x1, y1), fill=(196, 58, 44, 255))
    for x, y in ((7, 8), (4, 12), (10, 12), (6, 5), (9, 4)):
        draw.point((x, y), fill=(232, 96, 60, 255))
    draw.line([(7, 15), (7, 13)], fill=(96, 22, 22, 255))
    return img


def make_capsule_item_texture():
    """Small capsule icon: a capped cylinder with a glowing seam."""
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([5, 3, 10, 13], fill=(122, 128, 112, 255))
    draw.rectangle([5, 3, 6, 13], fill=(158, 164, 142, 255))
    draw.rectangle([9, 3, 10, 13], fill=(86, 90, 78, 255))
    draw.rectangle([4, 2, 11, 3], fill=(96, 74, 48, 255))
    draw.rectangle([4, 13, 11, 14], fill=(96, 74, 48, 255))
    draw.rectangle([5, 8, 10, 9], fill=(140, 255, 120, 255))
    draw.point((7, 1), fill=(148, 154, 132, 255))
    draw.point((8, 1), fill=(148, 154, 132, 255))
    draw.point((7, 15), fill=(148, 154, 132, 255))
    draw.point((8, 15), fill=(148, 154, 132, 255))
    return img


def make_white_alloy_texture():
    """The pale plate of the Martian complexes: white alloy with a cold green seam."""
    img = Image.new("RGBA", (16, 16), (206, 208, 202, 255))
    draw = ImageDraw.Draw(img)
    for y in range(16):
        factor = 1.06 - 0.16 * (y / 15.0)
        draw.line([(0, y), (15, y)], fill=shade((206, 208, 202), factor) + (255,))
    draw.line([(0, 4), (15, 4)], fill=(150, 154, 150, 255))
    draw.line([(0, 11), (15, 11)], fill=(150, 154, 150, 255))
    for x, y in ((2, 2), (12, 2), (2, 9), (12, 9), (7, 14)):
        draw.point((x, y), fill=(238, 240, 236, 255))
    draw.line([(5, 6), (8, 7), (11, 6)], fill=(126, 226, 150, 255))
    return img


def make_martian_glass_texture():
    """Strong Martian glass: frosted alloy-glass with a green lattice."""
    img = Image.new("RGBA", (16, 16), (196, 222, 214, 150))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 15, 15], outline=(168, 198, 190, 210))
    for i in range(0, 16, 4):
        draw.line([(i, 0), (i, 15)], fill=(150, 190, 180, 70))
        draw.line([(0, i), (15, i)], fill=(150, 190, 180, 70))
    draw.line([(2, 13), (13, 3)], fill=(226, 255, 240, 180))
    draw.point((11, 11), fill=(150, 255, 170, 255))
    return img


def make_blood_roots_texture():
    """Blood roots: thick crimson vines with pale spore sacs."""
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    vines = [((8, 15), (6, 8)), ((6, 8), (2, 4)), ((6, 8), (10, 3)), ((8, 15), (11, 9)), ((11, 9), (14, 5))]
    for (x0, y0), (x1, y1) in vines:
        draw.line([(x0, y0), (x1, y1)], fill=(128, 20, 26, 255))
        draw.point((x1, y1), fill=(186, 42, 44, 255))
    for x, y in ((6, 6), (10, 5), (12, 10), (3, 6)):
        draw.point((x, y), fill=(255, 128, 128, 255))
        draw.point((x + 1, y), fill=(150, 30, 34, 255))
    draw.line([(8, 15), (8, 12)], fill=(88, 14, 18, 255))
    return img


def make_blueprint_texture():
    """The blueprint block: a glowing schematic of a tripod on dark paper."""
    img = Image.new("RGBA", (16, 16), (30, 44, 58, 255))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 0, 15, 15], outline=(52, 76, 94, 255))
    for y in range(1, 15, 3):
        draw.line([(1, y), (14, y)], fill=(44, 62, 78, 255))
    # a tiny tripod drawing
    draw.line([(8, 2), (5, 9)], fill=(150, 226, 255, 255))
    draw.line([(8, 2), (11, 9)], fill=(150, 226, 255, 255))
    draw.line([(8, 2), (8, 10)], fill=(196, 240, 255, 255))
    draw.rectangle([6, 1, 10, 5], outline=(140, 220, 255, 255))
    draw.point((8, 3), fill=(255, 255, 255, 255))
    draw.line([(2, 12), (13, 12)], fill=(96, 150, 180, 255))
    return img


def make_arc_rifle_texture():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([1, 6, 11, 9], fill=(78, 84, 92, 255))
    draw.rectangle([1, 6, 11, 7], fill=(120, 128, 138, 255))
    draw.rectangle([3, 9, 6, 12], fill=(54, 58, 64, 255))
    draw.rectangle([10, 5, 13, 10], fill=(150, 220, 235, 255))
    draw.rectangle([13, 6, 15, 9], fill=(196, 250, 255, 255))
    draw.point((14, 7), fill=(255, 255, 255, 255))
    draw.rectangle([2, 10, 4, 11], fill=(90, 200, 220, 255))
    return img


def make_alloy_cannon_texture():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([0, 6, 12, 10], fill=(96, 98, 88, 255))
    draw.rectangle([0, 6, 12, 7], fill=(136, 140, 126, 255))
    draw.rectangle([12, 4, 15, 12], fill=(112, 116, 104, 255))
    draw.rectangle([14, 6, 15, 10], fill=(255, 176, 80, 255))
    draw.rectangle([3, 10, 7, 13], fill=(62, 64, 56, 255))
    draw.rectangle([8, 4, 11, 6], fill=(150, 154, 138, 255))
    return img


def make_blueprint_item_texture():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.rectangle([2, 3, 13, 13], fill=(38, 56, 74, 255))
    draw.rectangle([2, 3, 13, 13], outline=(70, 104, 130, 255))
    draw.line([(8, 5), (5, 11)], fill=(150, 226, 255, 255))
    draw.line([(8, 5), (11, 11)], fill=(150, 226, 255, 255))
    draw.line([(8, 5), (8, 12)], fill=(200, 244, 255, 255))
    draw.rectangle([2, 3, 13, 4], fill=(150, 214, 240, 255))
    return img


def main():
    os.makedirs(ENTITY, exist_ok=True)
    os.makedirs(ITEM, exist_ok=True)
    os.makedirs(BLOCK, exist_ok=True)

    for name, palette in PALETTES.items():
        image = make_hull_texture(name, palette)
        image.save(os.path.join(ENTITY, f"{name}.png"))
        print("entity", name)

    for name, colors in BEAMS.items():
        make_beam_texture(name, colors).save(os.path.join(ENTITY, f"beam_{name}.png"))
        print("beam", name)

    make_alloy_texture().save(os.path.join(ITEM, "martian_alloy.png"))
    make_ray_emitter_texture().save(os.path.join(ITEM, "heat_ray_emitter.png"))
    make_cylinder_texture().save(os.path.join(BLOCK, "martian_cylinder.png"))
    make_alloy_block_texture().save(os.path.join(BLOCK, "martian_alloy_block.png"))
    make_red_root_texture().save(os.path.join(BLOCK, "red_root.png"))
    make_capsule_item_texture().save(os.path.join(ITEM, "martian_capsule.png"))
    make_white_alloy_texture().save(os.path.join(BLOCK, "white_martian_alloy.png"))
    make_martian_glass_texture().save(os.path.join(BLOCK, "martian_glass.png"))
    make_blood_roots_texture().save(os.path.join(BLOCK, "blood_roots.png"))
    make_blueprint_texture().save(os.path.join(BLOCK, "tripod_blueprint.png"))
    make_arc_rifle_texture().save(os.path.join(ITEM, "arc_rifle.png"))
    make_alloy_cannon_texture().save(os.path.join(ITEM, "alloy_cannon.png"))
    make_blueprint_item_texture().save(os.path.join(ITEM, "blueprint.png"))
    print("items + block done")


if __name__ == "__main__":
    main()
