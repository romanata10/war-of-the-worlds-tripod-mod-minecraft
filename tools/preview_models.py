#!/usr/bin/env python3
"""Renders every tripod and Martian exactly as the mod builds them.

The geometry comes from `tools/generate_models.py` — the same objects the Java model files are
generated from — so this preview cannot drift away from what the game shows. The rasteriser uses
Minecraft's box UV layout, a z-buffer, two light sources and full-bright eyes.

Output:
    docs/models.png            poster with all twelve creatures
    docs/models/<id>.png       single renders, transparent background

Usage: python3 tools/preview_models.py           (needs Pillow + numpy)
"""

import math
import os
import sys

import numpy as np
from PIL import Image, ImageDraw, ImageFilter, ImageFont

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

import generate_models as specs  # noqa: E402
from model_lib import flatten as lib_flatten, part_rotation  # noqa: E402

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
TEXTURES = os.path.join(ROOT, "src/main/resources/assets/wotw/textures/entity")
OUT_DIR = os.path.join(ROOT, "docs/models")
FONT_DIR = "/usr/share/fonts/truetype/dejavu"

CELL_W = 430
CELL_H = 560
RENDER = 640
SUPERSAMPLE = 2

# id, model method, russian title, source, russian ability
TRIPODS = [
    ("tripod_2005", "tripod2005", "Тринога 2005", "Спилберг", "Тепловой луч · щит-отражение"),
    ("bbc_white_tripod", "bbcWhite", "Белый «сборщик»", "BBC, 1984", "Поле захвата щупальцами"),
    ("bbc_red_tripod", "bbcRed", "Красная тринога", "BBC, 1984", "Очередь лазеров"),
    ("flying_manta", "manta", "Летающий скат", "Пэл, 1953", "Полёт · взрыв при смерти"),
    ("book_tripod", "book", "Тринога Уэллса", "роман, 1898", "Чёрный дым · луч"),
    ("asylum_walker", "asylum", "Уокер Asylum", "мокбастер, 2005", "Зелёный луч · топот"),
    ("goliath_tripod", "goliath", "Голиаф", "Goliath, 2012", "Купол · залп лучей"),
    ("martian_heron", "heron", "Марсианская цапля", "Great Martian War", "Копьё на 52 блока"),
    ("martian_spider", "spider", "Марсианский паук", "Great Martian War", "Прыжок · лазание"),
    ("martian_louse", "louse", "Марсианская вошь", "Great Martian War", "Роевое размножение"),
    ("martian", "martian", "Марсианин", "роман, 1898", "Чёрный дым"),
    ("martian_master", "martianMaster", "Марсианин-повелитель", "роман, 1898", "Приказ машинам · луч"),
]


# --------------------------------------------------------------------------- #
#  Geometry
# --------------------------------------------------------------------------- #

def flatten(model):
    return lib_flatten(model)


def model_bounds(parts):
    """Height and width of the machine in blocks."""
    ys, xs, zs = [], [], []
    for part in parts:
        x, y, z, dx, dy, dz = part["box"]
        for cx in (x, x + dx):
            for cy in (y, y + dy):
                for cz in (z, z + dz):
                    point = part["rot"] @ np.array([cx, cy, cz], dtype=np.float64) + part["origin"]
                    xs.append(point[0])
                    ys.append(point[1])
                    zs.append(point[2])
    height = (max(ys) - min(ys)) / 16.0
    width = max(max(xs) - min(xs), max(zs) - min(zs)) / 16.0
    return height, width


# --------------------------------------------------------------------------- #
#  Rendering
# --------------------------------------------------------------------------- #

def rotation(yaw, pitch):
    ca, sa = math.cos(math.radians(yaw)), math.sin(math.radians(yaw))
    cp, sp = math.cos(math.radians(pitch)), math.sin(math.radians(pitch))
    ry = np.array([[ca, 0, sa], [0, 1, 0], [-sa, 0, ca]], dtype=np.float64)
    rx = np.array([[1, 0, 0], [0, cp, -sp], [0, sp, cp]], dtype=np.float64)
    return rx @ ry


def box_faces(uv, box):
    """Yields (normal, [(corner, uv), ...]) for the six faces, Minecraft style."""
    x0, y0, z0, dx, dy, dz = box
    x1, y1, z1 = x0 + dx, y0 + dy, z0 + dz
    u, v = uv
    highlight = u + dz
    yield (0, -1, 0), [((x0, y0, z0), (highlight, v)), ((x1, y0, z0), (highlight + dx, v)),
                       ((x1, y0, z1), (highlight + dx, v + dz)), ((x0, y0, z1), (highlight, v + dz))]
    yield (0, 1, 0), [((x0, y1, z1), (highlight + dx, v)), ((x1, y1, z1), (highlight + 2 * dx, v)),
                      ((x1, y1, z0), (highlight + 2 * dx, v + dz)), ((x0, y1, z0), (highlight + dx, v + dz))]
    yield (1, 0, 0), [((x1, y0, z1), (u, v + dz)), ((x1, y0, z0), (u + dz, v + dz)),
                      ((x1, y1, z0), (u + dz, v + dz + dy)), ((x1, y1, z1), (u, v + dz + dy))]
    yield (-1, 0, 0), [((x0, y0, z0), (u + dz + dx, v + dz)), ((x0, y0, z1), (u + 2 * dz + dx, v + dz)),
                       ((x0, y1, z1), (u + 2 * dz + dx, v + dz + dy)), ((x0, y1, z0), (u + dz + dx, v + dz + dy))]
    yield (0, 0, -1), [((x0, y0, z0), (u + dz, v + dz)), ((x1, y0, z0), (u + dz + dx, v + dz)),
                       ((x1, y1, z0), (u + dz + dx, v + dz + dy)), ((x0, y1, z0), (u + dz, v + dz + dy))]
    yield (0, 0, 1), [((x1, y0, z1), (u + 2 * dz + dx, v + dz)), ((x0, y0, z1), (u + 3 * dz + dx, v + dz)),
                      ((x0, y1, z1), (u + 3 * dz + dx, v + dz + dy)), ((x1, y1, z1), (u + 2 * dz + dx, v + dz + dy))]


def render_model(parts, texture, size=RENDER, yaw=150.0, pitch=10.0, supersample=SUPERSAMPLE):
    color = np.zeros((size * supersample, size * supersample, 4), dtype=np.float32)
    depth = np.full(color.shape[:2], -np.inf, dtype=np.float64)   # bigger z = closer
    tex = np.asarray(texture.convert("RGB"), dtype=np.float32) / 255.0
    camera_rot = rotation(yaw, pitch)

    def transform(points, rot, origin):
        pts = np.atleast_2d(np.asarray(points, dtype=np.float64)) @ rot.T + origin
        pts = np.stack([pts[:, 0] / 16.0, (24.0 - pts[:, 1]) / 16.0, pts[:, 2] / 16.0], axis=1)
        return pts @ camera_rot.T

    corners = []
    for part in parts:
        x, y, z, dx, dy, dz = part["box"]
        local = [(x + cx, y + cy, z + cz) for cx in (0, dx) for cy in (0, dy) for cz in (0, dz)]
        corners.append(transform(local, part["rot"], part["origin"]))
    corners = np.concatenate(corners, axis=0)
    centre = (corners.min(axis=0) + corners.max(axis=0)) / 2.0
    extent = max((corners.max(axis=0) - corners.min(axis=0))[:2]) or 1.0
    scale = (size * supersample * 0.80) / extent

    key_light = np.array([-0.45, 0.70, -0.55])
    key_light /= np.linalg.norm(key_light)
    fill_light = np.array([0.65, 0.15, 0.45])
    fill_light /= np.linalg.norm(fill_light)
    glow_spots = []

    for part in parts:
        x, y, z, dx, dy, dz = part["box"]
        for normal, corners_uv in box_faces(part["uv"], (0, 0, 0, dx, dy, dz)):
            local = np.array([p for p, _ in corners_uv], dtype=np.float64) + np.array([x, y, z])
            cam = transform(local, part["rot"], part["origin"])
            world_normal = part["rot"] @ np.array(normal, dtype=np.float64)
            n_cam = camera_rot @ np.array([world_normal[0], -world_normal[1], world_normal[2]])
            if n_cam[2] <= 0.0:
                continue

            key = max(0.0, float(np.dot(n_cam, key_light)))
            fill = max(0.0, float(np.dot(n_cam, fill_light)))
            shade = min(1.45, 0.62 + 0.55 * key + 0.28 * fill)
            if part["glow"]:
                shade = 1.35

            uvs = np.array([uv for _, uv in corners_uv], dtype=np.float64)
            screen = np.stack([(cam[:, 0] - centre[0]) * scale + color.shape[1] / 2.0,
                               -(cam[:, 1] - centre[1]) * scale + color.shape[0] / 2.0], axis=1)
            for tri in ((0, 1, 2), (0, 2, 3)):
                _raster(color, depth, tex, screen[list(tri)], cam[list(tri), 2], uvs[list(tri)], shade)
            if part["glow"]:
                glow_spots.append(screen.mean(axis=0))

    img = Image.fromarray((np.clip(color, 0, 1) * 255).astype(np.uint8), "RGBA")
    img = img.resize((size, size), Image.LANCZOS)

    bbox = img.getbbox()
    if bbox:
        pad = 8
        crop = (max(0, bbox[0] - pad), max(0, bbox[1] - pad), min(size, bbox[2] + pad), min(size, bbox[3] + pad))
        img = img.crop(crop)
        glow_spots = [(x - crop[0], y - crop[1]) for x, y in glow_spots]

    eye_color = tuple(int(c * 255) for c in tex[112, 112])
    return img, ([(p[0] / supersample, p[1] / supersample) for p in glow_spots], eye_color)


def _raster(color, depth, tex, pts, zs, uvs, shade):
    h, w = depth.shape
    x_min = max(int(np.floor(pts[:, 0].min())), 0)
    x_max = min(int(np.ceil(pts[:, 0].max())) + 1, w)
    y_min = max(int(np.floor(pts[:, 1].min())), 0)
    y_max = min(int(np.ceil(pts[:, 1].max())) + 1, h)
    if x_min >= x_max or y_min >= y_max:
        return

    (x0, y0), (x1, y1), (x2, y2) = pts
    denom = (y1 - y2) * (x0 - x2) + (x2 - x1) * (y0 - y2)
    if abs(denom) < 1e-9:
        return

    ys, xs = np.mgrid[y_min:y_max, x_min:x_max]
    xs = xs + 0.5
    ys = ys + 0.5
    a = ((y1 - y2) * (xs - x2) + (x2 - x1) * (ys - y2)) / denom
    b = ((y2 - y0) * (xs - x2) + (x0 - x2) * (ys - y2)) / denom
    c = 1.0 - a - b
    inside = (a >= -1e-6) & (b >= -1e-6) & (c >= -1e-6)
    if not inside.any():
        return

    z = a * zs[0] + b * zs[1] + c * zs[2]
    target = depth[y_min:y_max, x_min:x_max]
    mask = inside & (z > target)
    if not mask.any():
        return

    u = a * uvs[0, 0] + b * uvs[1, 0] + c * uvs[2, 0]
    v = a * uvs[0, 1] + b * uvs[1, 1] + c * uvs[2, 1]
    tex_h, tex_w = tex.shape[:2]
    uu = np.clip(np.floor(u).astype(np.int32), 0, tex_w - 1)
    vv = np.clip(np.floor(v).astype(np.int32), 0, tex_h - 1)
    texels = tex[vv, uu] * shade

    target[mask] = z[mask]
    block = color[y_min:y_max, x_min:x_max]
    block[mask, :3] = texels[mask]
    block[mask, 3] = 1.0


# --------------------------------------------------------------------------- #
#  Poster
# --------------------------------------------------------------------------- #

def font(name, size):
    return ImageFont.truetype(os.path.join(FONT_DIR, name), size)


def build_poster(renders):
    cols, rows = 4, math.ceil(len(renders) / 4)
    header = 150
    width, height = CELL_W * cols, header + CELL_H * rows
    poster = Image.new("RGB", (width, height), (10, 12, 14))
    draw = ImageDraw.Draw(poster)

    for y in range(height):
        t = y / height
        draw.line([(0, y), (width, y)], fill=(int(12 + 14 * t), int(14 + 16 * t), int(17 + 19 * t)))

    draw.text((44, 40), "ВОЙНА МИРОВ: ТРИНОГИ", font=font("DejaVuSans-Bold.ttf", 46), fill=(228, 234, 228))
    draw.text((44, 98), "10 боевых машин + 2 марсианина · Minecraft 1.20.1 (Fabric) · рендер реальной "
                        "геометрии из tools/generate_models.py",
              font=font("DejaVuSans.ttf", 20), fill=(132, 142, 138))

    box_w, box_h = CELL_W - 40, CELL_H - 175
    for index, (item, render) in enumerate(zip(TRIPODS, renders)):
        img, (spots, eye_color), (height_blocks, width_blocks) = render
        col, row = index % cols, index // cols
        cell_x = col * CELL_W
        cell_y = header + row * CELL_H

        glow = Image.new("RGBA", img.size, (0, 0, 0, 0))
        gd = ImageDraw.Draw(glow)
        for x, y in spots:
            r = 18
            gd.ellipse([x - r, y - r, x + r, y + r], fill=eye_color + (130,))
        glow = glow.filter(ImageFilter.GaussianBlur(11))
        composite = Image.alpha_composite(glow, img)

        ratio = min(box_w / composite.width, box_h / composite.height)
        sprite = composite.resize((max(1, int(composite.width * ratio)), max(1, int(composite.height * ratio))),
                                  Image.LANCZOS)
        px = cell_x + (CELL_W - sprite.width) // 2
        py = cell_y + 10 + (box_h - sprite.height) // 2

        shadow = Image.new("RGBA", (sprite.width, 40), (0, 0, 0, 0))
        ImageDraw.Draw(shadow).ellipse([sprite.width * 0.22, 8, sprite.width * 0.78, 32], fill=(0, 0, 0, 120))
        shadow = shadow.filter(ImageFilter.GaussianBlur(9))
        poster.paste(shadow, (px, py + sprite.height - 32), shadow)
        poster.paste(sprite, (px, py), sprite)

        _, _, title, source, ability = item
        draw.text((cell_x + CELL_W // 2, cell_y + CELL_H - 100), title,
                  font=font("DejaVuSans-Bold.ttf", 24), fill=(234, 240, 234), anchor="mm")
        draw.text((cell_x + CELL_W // 2, cell_y + CELL_H - 72), source,
                  font=font("DejaVuSans.ttf", 16), fill=(120, 148, 128), anchor="mm")
        draw.text((cell_x + CELL_W // 2, cell_y + CELL_H - 46), ability,
                  font=font("DejaVuSans.ttf", 16), fill=(204, 146, 112), anchor="mm")
        draw.text((cell_x + CELL_W // 2, cell_y + CELL_H - 22),
                  f"{height_blocks:.1f} блока в высоту · {width_blocks:.1f} в ширину",
                  font=font("DejaVuSans.ttf", 14), fill=(108, 116, 112), anchor="mm")

    return poster


def main():
    os.makedirs(OUT_DIR, exist_ok=True)
    methods = {method: model for method, model in specs.TRIPODS + specs.MARTIANS}

    renders = []
    lines = []
    for tripod_id, method, title, source, ability in TRIPODS:
        model = methods[method]
        parts = flatten(model)
        texture = Image.open(os.path.join(TEXTURES, f"{tripod_id}.png"))
        img, extra = render_model(parts, texture)
        img.save(os.path.join(OUT_DIR, f"{tripod_id}.png"))
        height, width = model_bounds(parts)
        renders.append((img, extra, (height, width)))
        lines.append(f"{tripod_id}: {len(parts)} boxes, {height:.2f} x {width:.2f} blocks")
        print(lines[-1])

    poster = build_poster(renders)
    poster.save(os.path.join(ROOT, "docs/models.png"))
    print("docs/models.png", poster.size)
    return lines


if __name__ == "__main__":
    main()
