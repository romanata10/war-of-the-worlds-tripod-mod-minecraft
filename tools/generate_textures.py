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
            distance = math.hypot(x - cx, y - cy) / 16.0
            intensity = max(0.0, 1.0 - distance) ** 0.75
            hot = max(0.0, 1.0 - distance * 1.15)
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
    print("items + block done")


if __name__ == "__main__":
    main()
