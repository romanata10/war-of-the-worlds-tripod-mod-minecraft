#!/usr/bin/env python3
"""Renders the ten tripods exactly as they are defined in TripodModels.java.

The script parses the small modelling DSL used in that file (`addChild` + `ModelPartBuilder`
cuboids + `ModelTransform.pivot`, plus the `leg(...)` / `glow(...)` helpers), builds the same
axis aligned boxes Minecraft builds and rasterises them with the matching box UV layout, so the
result is what the game shows (minus the leg animation).

Output:
    docs/models.png      poster with all ten machines
    docs/models/<id>.png single renders, transparent background

Usage: python3 tools/preview_models.py           (needs Pillow + numpy)
"""

import math
import os
import re

import numpy as np
from PIL import Image, ImageDraw, ImageFilter, ImageFont

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
JAVA = os.path.join(ROOT, "src/main/java/dev/arena/wotw/client/model/TripodModels.java")
TEXTURES = os.path.join(ROOT, "src/main/resources/assets/wotw/textures/entity")
OUT_DIR = os.path.join(ROOT, "docs/models")
FONT_DIR = "/usr/share/fonts/truetype/dejavu"

CELL_W = 440
CELL_H = 570
RENDER = 640
SUPERSAMPLE = 2

# id -> (java method, armed flag for the BBC variant, russian title, russian ability, texture)
TRIPODS = [
    ("tripod_2005", "tripod2005", False, "Тринога 2005", "Спилберг", "Тепловой луч · щит-отражение"),
    ("bbc_white_tripod", "bbc", False, "Белый «сборщик»", "BBC, 1984", "Поле захвата щупальцами"),
    ("bbc_red_tripod", "bbc", True, "Красная тринога", "BBC, 1984", "Очередь лазеров"),
    ("flying_manta", "manta", False, "Летающий скат", "Пэл, 1953", "Полёт · взрыв при смерти"),
    ("book_tripod", "book", False, "Тринога Уэллса", "роман, 1898", "Чёрный дым · луч"),
    ("asylum_walker", "asylum", False, "Уокер Asylum", "мокбастер, 2005", "Зелёный луч · топот"),
    ("goliath_tripod", "goliath", False, "Голиаф", "Goliath, 2012", "Купол · залп лучей"),
    ("martian_heron", "heron", False, "Марсианская цапля", "Great Martian War", "Копьё на 52 блока"),
    ("martian_spider", "spider", False, "Марсианский паук", "Great Martian War", "Прыжок · лазание"),
    ("martian_louse", "louse", False, "Марсианская вошь", "Great Martian War", "Роевое размножение"),
]


# --------------------------------------------------------------------------- #
#  Parsing of TripodModels.java
# --------------------------------------------------------------------------- #

def eval_int(expr):
    """Evaluates the integer arithmetic used inside the Java helpers."""
    expr = expr.strip()
    expr = re.sub(r"\bMath\.max\(([^,]+),([^)]+)\)", r"max(\1,\2)", expr)
    return int(eval(expr, {"__builtins__": {}}, {"max": max}))


def matching_brace(text, start):
    depth = 0
    for i in range(start, len(text)):
        if text[i] == "{":
            depth += 1
        elif text[i] == "}":
            depth -= 1
            if depth == 0:
                return i
    raise ValueError("unbalanced braces")


def method_bodies(source):
    bodies = {}
    for m in re.finditer(r"public static TexturedModelData (\w+)\(([^)]*)\)\s*\{", source):
        start = m.end() - 1
        bodies[m.group(1)] = source[start + 1:matching_brace(source, start)]
    return bodies


def unroll_loops(body):
    """The spider and the louse build their legs in a small Java loop — unroll it."""
    arrays = {}
    for m in re.finditer(r"int\[\]\s+(\w+)\s*=\s*\{([^}]*)\}\s*;", body):
        arrays[m.group(1)] = [eval_int(v) for v in m.group(2).split(",") if v.strip()]

    def repl(m):
        values = arrays.get(m.group(1), [])
        chunks = []
        for index, value in enumerate(values):
            chunk = m.group(2).replace(f"{m.group(1)}[i]", str(value))
            chunk = re.sub(r"\(i\s*\+\s*(\d+)\)", lambda mm: str(index + int(mm.group(1))), chunk)
            chunks.append(chunk)
        return "\n".join(chunks)

    body = re.sub(r"for \(int i = 0; i < (\w+)\.length; i\+\+\) \{(.*?)\}", repl, body, flags=re.S)
    return re.sub(r"int\[\]\s+\w+\s*=\s*\{[^}]*\}\s*;", "", body)


def expand_helpers(body, armed):
    """Rewrites leg(...) / glow(...) calls into explicit addChild(...) statements."""
    def leg(m):
        root, name = m.group(1), m.group(2)
        px, py, pz, thickness, thigh, shin, bend_x, bend_z = [eval_int(a) for a in m.group(3).split(",")]
        lower = max(1, thickness - 1)
        # keep in sync with leg(...) in TripodModels.java
        side = (1 if px > 0 else -1 if px < 0 else 0) * 13.0
        front = (1 if pz > 0 else -1 if pz < 0 else 0) * 10.0
        return (f'{root}.addChild("{name}", ModelPartBuilder.create()'
                f'.cuboid({-thickness}, 0, {-thickness}, {thickness * 2}, {thigh}, {thickness * 2})'
                f'.cuboid({-lower + bend_x}, {thigh}, {-lower + bend_z}, {lower * 2}, {shin}, {lower * 2}), '
                f'ModelTransform.poseDeg({px}, {py}, {pz}, {front}, 0.0, {side}));')

    def glow(m):
        root, name = m.group(1), m.group(2)
        px, py, pz, w, h, d = [eval_int(a) for a in m.group(3).split(",")]
        return (f'{root}.addChild("{name}", ModelPartBuilder.create().uv(96, 96)'
                f'.cuboid({-w // 2}, {-h // 2}, {-d // 2}, {w}, {h}, {d}), '
                f'ModelTransform.pivot({px}, {py}, {pz}));')

    # `"leg_l" + 1` from the unrolled loops
    body = re.sub(r'"(\w*)"\s*\+\s*(\d+)', lambda m: f'"{m.group(1)}{m.group(2)}"', body)

    body = re.sub(r'leg\(\s*(\w+),\s*"(\w+)",\s*([^)]*)\)\s*;', leg, body)
    body = re.sub(r'glow\(\s*(\w+),\s*"(\w+)",\s*([^)]*)\)\s*;', glow, body)

    # `if (armed) { ... }` blocks only matter for the red BBC machine
    def conditional(m):
        return m.group(1) if armed else ""

    body = re.sub(r"if \(armed\) \{(.*?)\}", conditional, body, flags=re.S)
    return body


def split_top_level(text):
    parts, depth, current = [], 0, ""
    for ch in text:
        if ch == "(":
            depth += 1
        elif ch == ")":
            depth -= 1
        if ch == "," and depth == 0:
            parts.append(current)
            current = ""
        else:
            current += ch
    parts.append(current)
    return parts


def parse_model(body):
    """Returns {'root': node} where node = {name, pivot, cuboids, glow, children}."""
    root = {"name": "root", "pivot": (0, 0, 0), "rotation": (0.0, 0.0, 0.0),
            "cuboids": [], "glow": False, "children": []}
    variables = {"root": root}
    glow_uv = (96, 96)

    for match in re.finditer(r"(\w+)\.addChild\(", body):
        receiver = match.group(1)
        open_paren = match.end() - 1
        close = open_paren
        depth = 0
        for i in range(open_paren, len(body)):
            if body[i] == "(":
                depth += 1
            elif body[i] == ")":
                depth -= 1
                if depth == 0:
                    close = i
                    break
        args = split_top_level(body[open_paren + 1:close])
        if len(args) != 3:
            continue
        name = args[0].strip().strip('"')
        builder, transform = args[1], args[2]

        uv_match = re.search(r"\.uv\(\s*(-?\d+)\s*,\s*(-?\d+)\s*\)", builder)
        uv = (int(uv_match.group(1)), int(uv_match.group(2))) if uv_match else (0, 0)
        cuboids = []
        for c in re.finditer(r"\.cuboid\(\s*([-\d\s]+),([-\d\s]+),([-\d\s]+),([-\d\s]+),([-\d\s]+),([-\d\s]+)\)", builder):
            cuboids.append((uv, tuple(eval_int(g) for g in c.groups())))

        pivot_match = re.search(r"pivot\(\s*([-\d]+)\s*,\s*([-\d]+)\s*,\s*([-\d]+)\s*\)", transform)
        pose_match = re.search(r"poseDeg\(\s*([-\d]+)\s*,\s*([-\d]+)\s*,\s*([-\d]+)\s*,"
                               r"\s*([-\d.]+)\s*,\s*([-\d.]+)\s*,\s*([-\d.]+)\s*\)", transform)
        if pose_match:
            values = [float(g) for g in pose_match.groups()]
            pivot = tuple(int(v) for v in values[:3])
            rotation = tuple(values[3:])          # (pitch, yaw, roll) in degrees
        else:
            pivot = tuple(eval_int(g) for g in pivot_match.groups())
            rotation = (0.0, 0.0, 0.0)

        node = {"name": name, "pivot": pivot, "rotation": rotation, "cuboids": cuboids,
                "glow": uv == glow_uv, "children": []}
        variables[receiver]["children"].append(node)

        # `ModelPartData x = root.addChild(...)` binds a variable for later children
        assignment = re.search(r"ModelPartData\s+(\w+)\s*=\s*$", body[:match.start()])
        if assignment:
            variables[assignment.group(1)] = node

    return root


# --------------------------------------------------------------------------- #
#  Box faces with Minecraft's UV layout
# --------------------------------------------------------------------------- #

def box_faces(uv, box):
    """Yields (normal, [(pos, uv), ...]) for the six faces of an axis aligned box."""
    x0, y0, z0, dx, dy, dz = box
    x1, y1, z1 = x0 + dx, y0 + dy, z0 + dz
    u, v = uv

    # Minecraft's box layout: (2*dz + 2*dx) wide, (dz + dy) tall
    def quad(points, uv_points, normal):
        return normal, list(zip(points, uv_points))

    top = u + dz
    yield quad([(x0, y0, z0), (x1, y0, z0), (x1, y0, z1), (x0, y0, z1)],
               [(top, v), (top + dx, v), (top + dx, v + dz), (top, v + dz)], (0, -1, 0))
    yield quad([(x0, y1, z1), (x1, y1, z1), (x1, y1, z0), (x0, y1, z0)],
               [(top + dx, v), (top + 2 * dx, v), (top + 2 * dx, v + dz), (top + dx, v + dz)], (0, 1, 0))
    east = u
    yield quad([(x1, y0, z1), (x1, y0, z0), (x1, y1, z0), (x1, y1, z1)],
               [(east, v + dz), (east + dz, v + dz), (east + dz, v + dz + dy), (east, v + dz + dy)], (1, 0, 0))
    west = u + dz + dx
    yield quad([(x0, y0, z0), (x0, y0, z1), (x0, y1, z1), (x0, y1, z0)],
               [(west, v + dz), (west + dz, v + dz), (west + dz, v + dz + dy), (west, v + dz + dy)], (-1, 0, 0))
    north = u + dz
    yield quad([(x0, y0, z0), (x1, y0, z0), (x1, y1, z0), (x0, y1, z0)],
               [(north, v + dz), (north + dx, v + dz), (north + dx, v + dz + dy), (north, v + dz + dy)], (0, 0, -1))
    south = u + dz + dx + dz
    yield quad([(x1, y0, z1), (x0, y0, z1), (x0, y1, z1), (x1, y1, z1)],
               [(south, v + dz), (south + dx, v + dz), (south + dx, v + dz + dy), (south, v + dz + dy)], (0, 0, 1))


def part_rotation(pitch_deg, yaw_deg, roll_deg):
    """Vanilla order: Rz(roll) * Ry(yaw) * Rx(pitch)."""
    p, y, r = (math.radians(a) for a in (pitch_deg, yaw_deg, roll_deg))
    rx = np.array([[1, 0, 0], [0, math.cos(p), -math.sin(p)], [0, math.sin(p), math.cos(p)]])
    ry = np.array([[math.cos(y), 0, math.sin(y)], [0, 1, 0], [-math.sin(y), 0, math.cos(y)]])
    rz = np.array([[math.cos(r), -math.sin(r), 0], [math.sin(r), math.cos(r), 0], [0, 0, 1]])
    return rz @ ry @ rx


def collect(node, parent_rot=None, parent_origin=None, out=None):
    """Flattens the part tree into world space boxes, honouring the per-part rotations."""
    if out is None:
        out = []
    if parent_rot is None:
        parent_rot = np.eye(3)
    if parent_origin is None:
        parent_origin = np.zeros(3)

    rot = parent_rot @ part_rotation(*node["rotation"])
    origin = parent_rot @ np.array(node["pivot"], dtype=np.float64) + parent_origin
    for uv, box in node["cuboids"]:
        out.append({"uv": uv, "box": box, "rot": rot, "origin": origin, "glow": node["glow"]})
    for child in node["children"]:
        collect(child, rot, origin, out)
    return out


# --------------------------------------------------------------------------- #
#  Rendering
# --------------------------------------------------------------------------- #

def rotation(yaw, pitch):
    ca, sa = math.cos(math.radians(yaw)), math.sin(math.radians(yaw))
    cp, sp = math.cos(math.radians(pitch)), math.sin(math.radians(pitch))
    ry = np.array([[ca, 0, sa], [0, 1, 0], [-sa, 0, ca]], dtype=np.float64)
    rx = np.array([[1, 0, 0], [0, cp, -sp], [0, sp, cp]], dtype=np.float64)
    return rx @ ry


def render_model(parts, texture, size=RENDER, yaw=142.0, pitch=16.0, supersample=SUPERSAMPLE):
    """Orthographic rasteriser with a z-buffer; model space is 1/16 block, +y downwards."""
    color = np.zeros((size * supersample, size * supersample, 4), dtype=np.float32)
    depth = np.full(color.shape[:2], -np.inf, dtype=np.float64)   # bigger z = closer

    tex = np.asarray(texture.convert("RGB"), dtype=np.float32) / 255.0
    tex_h, tex_w = tex.shape[:2]

    # camera: model -> blocks, y up, then rotate
    def to_camera(points, _rot=rotation(yaw, pitch)):
        pts = np.atleast_2d(np.asarray(points, dtype=np.float64))
        pts = np.stack([pts[:, 0] / 16.0, (24.0 - pts[:, 1]) / 16.0, pts[:, 2] / 16.0], axis=1)
        return pts @ _rot.T

    def transform(points, rot, origin):
        """Model space points -> camera space."""
        return to_camera(np.asarray(points, dtype=np.float64) @ rot.T + origin)

    corners = []
    for part in parts:
        x, y, z, dx, dy, dz = part["box"]
        local = [(x + cx, y + cy, z + cz) for cx in (0, dx) for cy in (0, dy) for cz in (0, dz)]
        corners.append(transform(local, part["rot"], part["origin"]))
    corners = np.concatenate(corners, axis=0)
    centre = (corners.min(axis=0) + corners.max(axis=0)) / 2.0
    extent = max((corners.max(axis=0) - corners.min(axis=0))[:2]) or 1.0
    scale = (size * supersample * 0.78) / extent

    key_light = np.array([-0.45, 0.70, -0.55])
    key_light /= np.linalg.norm(key_light)
    fill_light = np.array([0.65, 0.15, 0.45])
    fill_light /= np.linalg.norm(fill_light)
    camera_rot = rotation(yaw, pitch)
    glow_spots = []

    for part in parts:
        x, y, z, dx, dy, dz = part["box"]
        for normal, corners_uv in box_faces(part["uv"], (0, 0, 0, dx, dy, dz)):
            local = np.array([p for p, _ in corners_uv], dtype=np.float64) + np.array([x, y, z])
            cam = transform(local, part["rot"], part["origin"])
            # back face culling: the normal must point towards the viewer (+z in camera space)
            world_normal = part["rot"] @ np.array(normal, dtype=np.float64)
            n_cam = camera_rot @ np.array([world_normal[0], -world_normal[1], world_normal[2]])
            if n_cam[2] <= 0.0:
                continue

            # key + cool fill so the very dark hulls still read against the background
            key = max(0.0, float(np.dot(n_cam, key_light)))
            fill = max(0.0, float(np.dot(n_cam, fill_light)))
            shade = min(1.45, 0.62 + 0.55 * key + 0.28 * fill)
            if part["glow"]:
                shade = 1.35    # eyes and lenses are full bright in game (RenderLayer.getEyes)

            uvs = np.array([uv for _, uv in corners_uv], dtype=np.float64)
            screen = np.stack([(cam[:, 0] - centre[0]) * scale + color.shape[1] / 2.0,
                               -(cam[:, 1] - centre[1]) * scale + color.shape[0] / 2.0], axis=1)

            for tri in ((0, 1, 2), (0, 2, 3)):
                _raster(color, depth, tex, screen[list(tri)], cam[list(tri), 2], uvs[list(tri)], shade)

            if part["glow"]:
                glow_spots.append(screen.mean(axis=0))

    img = Image.fromarray((np.clip(color, 0, 1) * 255).astype(np.uint8), "RGBA")
    img = img.resize((size, size), Image.LANCZOS)

    # crop to the silhouette so every cell fits the machine and the shadow sits under its feet
    bbox = img.getbbox()
    if bbox:
        pad = 8
        img = img.crop((max(0, bbox[0] - pad), max(0, bbox[1] - pad),
                        min(size, bbox[2] + pad), min(size, bbox[3] + pad)))
        glow_spots = [(x - max(0, bbox[0] - pad), y - max(0, bbox[1] - pad)) for x, y in glow_spots]
        size = img.size[0]
    eye = tuple(int(c * 255) for c in tex[112, 112])
    return img, ([(p[0] / supersample, p[1] / supersample) for p in glow_spots], eye)


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
    mask = inside & (z > target)          # camera looks down -z, bigger z is closer
    if not mask.any():
        return

    u = a * uvs[0, 0] + b * uvs[1, 0] + c * uvs[2, 0]
    v = a * uvs[0, 1] + b * uvs[1, 1] + c * uvs[2, 1]
    tex_h, tex_w = tex.shape[:2]
    uu = np.clip(np.floor(u).astype(np.int32), 0, tex_w - 1)
    vv = np.clip(np.floor(v).astype(np.int32), 0, tex_h - 1)
    texels = tex[vv, uu] * shade

    alpha = mask
    target[alpha] = z[alpha]
    block = color[y_min:y_max, x_min:x_max]
    block[alpha, :3] = texels[alpha]
    block[alpha, 3] = 1.0


def add_glow(img, spots):
    """Soft halo around the eye lenses."""
    halo = Image.new("RGBA", img.size, (0, 0, 0, 0))
    draw = ImageDraw.Draw(halo)
    for x, y in spots:
        r = img.size[0] * 0.045
        draw.ellipse([x - r, y - r, x + r, y + r], fill=(150, 255, 130, 150))
    halo = halo.filter(ImageFilter.GaussianBlur(img.size[0] * 0.02))
    return Image.alpha_composite(halo, img)


# --------------------------------------------------------------------------- #
#  Poster
# --------------------------------------------------------------------------- #

def font(name, size):
    return ImageFont.truetype(os.path.join(FONT_DIR, name), size)


def build_poster(renders):
    cols, rows = 5, 2
    header = 150
    width, height = CELL_W * cols, header + CELL_H * rows
    poster = Image.new("RGB", (width, height), (10, 12, 14))
    draw = ImageDraw.Draw(poster)

    for y in range(height):
        t = y / height
        draw.line([(0, y), (width, y)], fill=(int(12 + 14 * t), int(14 + 16 * t), int(17 + 19 * t)))

    draw.text((44, 40), "ВОЙНА МИРОВ: ТРИНОГИ", font=font("DejaVuSans-Bold.ttf", 46), fill=(228, 234, 228))
    draw.text((44, 98), "10 боевых машин · Minecraft 1.20.1 (Fabric) · рендер реальной геометрии из TripodModels.java",
              font=font("DejaVuSans.ttf", 20), fill=(132, 142, 138))

    box_w, box_h = CELL_W - 40, CELL_H - 165
    for index, (item, (img, (spots, eye))) in enumerate(zip(TRIPODS, renders)):
        col, row = index % cols, index // cols
        cell_x = col * CELL_W
        cell_y = header + row * CELL_H

        glow = Image.new("RGBA", img.size, (0, 0, 0, 0))
        gd = ImageDraw.Draw(glow)
        for x, y in spots:
            r = 18
            gd.ellipse([x - r, y - r, x + r, y + r], fill=eye + (130,))
        glow = glow.filter(ImageFilter.GaussianBlur(11))
        composite = Image.alpha_composite(glow, img)

        ratio = min(box_w / img.width, box_h / img.height)
        sprite = composite.resize((int(img.width * ratio), int(img.height * ratio)), Image.LANCZOS)
        px = cell_x + (CELL_W - sprite.width) // 2
        py = cell_y + 10 + (box_h - sprite.height) // 2

        # soft contact shadow so the machines do not float
        shadow = Image.new("RGBA", (sprite.width, 40), (0, 0, 0, 0))
        ImageDraw.Draw(shadow).ellipse([sprite.width * 0.22, 8, sprite.width * 0.78, 32], fill=(0, 0, 0, 120))
        shadow = shadow.filter(ImageFilter.GaussianBlur(9))
        poster.paste(shadow, (px, py + sprite.height - 32), shadow)
        poster.paste(sprite, (px, py), sprite)

        _, _, _, title, source, ability = item
        draw.text((cell_x + CELL_W // 2, cell_y + CELL_H - 92), title,
                  font=font("DejaVuSans-Bold.ttf", 25), fill=(234, 240, 234), anchor="mm")
        draw.text((cell_x + CELL_W // 2, cell_y + CELL_H - 62), source,
                  font=font("DejaVuSans.ttf", 17), fill=(120, 148, 128), anchor="mm")
        draw.text((cell_x + CELL_W // 2, cell_y + CELL_H - 34), ability,
                  font=font("DejaVuSans.ttf", 17), fill=(204, 146, 112), anchor="mm")

    return poster


def main():
    os.makedirs(OUT_DIR, exist_ok=True)
    source = open(JAVA, encoding="utf-8").read()
    bodies = method_bodies(source)

    renders = []
    for tripod_id, method, armed, title, source_name, ability in TRIPODS:
        body = expand_helpers(unroll_loops(bodies[method]), armed)
        root = parse_model(body)
        parts = collect(root)
        texture_path = os.path.join(TEXTURES, f"{tripod_id}.png")
        texture = Image.open(texture_path)
        img, spots = render_model(parts, texture)
        img.save(os.path.join(OUT_DIR, f"{tripod_id}.png"))
        renders.append((img, spots))
        print(f"{tripod_id}: {len(parts)} boxes, {len(spots[0])} glow faces")

    poster = build_poster(renders)
    poster.save(os.path.join(ROOT, "docs/models.png"))
    print("docs/models.png", poster.size)


if __name__ == "__main__":
    main()
