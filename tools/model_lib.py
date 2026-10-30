#!/usr/bin/env python3
"""Tiny modelling DSL shared by the Java model generator and the preview renderer.

A model is a tree of parts. Every part has a pivot (in model pixels, 16 per block, +y downwards,
feet at y = 24) and an optional rotation in degrees, exactly like Minecraft's `ModelTransform`.
Boxes are plain axis aligned cuboids with a texture offset.

`emit_java()` turns a model into the `TexturedModelData` code that goes into
`client/model/*.java`, so the geometry has a single source of truth: this file plus the specs in
`generate_models.py`. The preview renderer reads the same objects.
"""

GLOW_UV = (96, 96)


class Part:
    def __init__(self, name, pivot=(0, 0, 0), rot=(0.0, 0.0, 0.0), parent=None):
        self.name = name
        self.pivot = tuple(pivot)
        self.rot = tuple(float(a) for a in rot)   # (pitch, yaw, roll) in degrees
        self.parent = parent
        self.boxes = []                            # [(uv, (x, y, z, dx, dy, dz))]
        self.glow = False
        self.children = []

    # -- geometry ---------------------------------------------------------
    def box(self, x, y, z, dx, dy, dz, uv=(0, 0)):
        self.boxes.append(((int(uv[0]), int(uv[1])), (int(x), int(y), int(z), int(dx), int(dy), int(dz))))
        return self

    def glow_box(self, x, y, z, dx, dy, dz):
        self.glow = True
        self.boxes.append((GLOW_UV, (int(x), int(y), int(z), int(dx), int(dy), int(dz))))
        return self

    def shell(self, cx, cy, cz, w, h, d, cell=24, uv=(0, 0)):
        """Fills a volume with cuboids of at most `cell` pixels, which gives riveted panels."""
        for x0, dx in _split(cx - w / 2.0, w, cell):
            for y0, dy in _split(cy, h, cell):
                for z0, dz in _split(cz - d / 2.0, d, cell):
                    self.box(round(x0), round(y0), round(z0), max(1, round(dx)), max(1, round(dy)),
                             max(1, round(dz)), uv)
        return self

    def dome(self, cx, cy, cz, w, d, layers, cell=24):
        """Stack of shrinking slabs: an approximation of a rounded shell."""
        for height, width, depth in layers:
            self.shell(cx, cy, cz, width, height, depth, cell)
            cy += height
        return self

    def spikes(self, cx, cy, cz, w, d, positions, size=4, step=2):
        """Small stepped pyramids along the top rim."""
        for ox, oz in positions:
            x, z = cx + ox, cz + oz
            s = size
            for level in range(3):
                self.box(x - s // 2, cy + level * step, z - s // 2, max(1, s), step, max(1, s))
                s = max(1, s - 2)
        return self


def _split(start, length, cell):
    """Splits [start, start+length] into integer chunks no longer than `cell`."""
    count = max(1, int(round(length / float(cell) + 0.49)))
    size = length / float(count)
    out = []
    position = start
    for index in range(count):
        end = start + size * (index + 1)
        out.append((position, end - position))
        position = end
    return out


class Model:
    def __init__(self, name, texture=(128, 128)):
        self.name = name
        self.texture = texture
        self.root = Part("root")
        self.parts = [self.root]
        self.by_name = {"root": self.root}

    def part(self, name, parent="root", pivot=(0, 0, 0), rot=(0.0, 0.0, 0.0)):
        part = Part(name, pivot, rot, parent)
        self.parts.append(part)
        self.by_name[name] = part
        self.by_name[parent].children.append(part)
        return part


# --------------------------------------------------------------------------- #
#  Composite helpers
# --------------------------------------------------------------------------- #

def leg(model, name, hip, thigh, shin, thick, splay_x=0.0, splay_z=0.0, foot=0):
    """Two segment leg: the thigh swings out, the shin drops back towards vertical."""
    upper = model.part(name, pivot=hip, rot=(splay_z, 0.0, splay_x))
    upper.box(-thick // 2, 0, -thick // 2, thick, thigh, thick)
    lower = model.part(name + "_shin", parent=name, pivot=(0, thigh, 0), rot=(-splay_z * 0.85, 0.0, -splay_x * 0.85))
    lower.box(-thick // 2, 0, -thick // 2, thick, shin, thick)
    if foot:
        lower.box(-foot // 2, shin - 1, -foot // 2, foot, 3, foot)
    return upper


def tentacle(model, name, parent, pivot, length, segments=3, droop=0.0, spread=0.0, thick=3):
    """A chain of thin boxes hanging down, used for the Martian feelers.

    Every segment starts where the previous one ended, so the chain really hangs instead of
    piling all boxes on top of each other.
    """
    segment = max(2, length // max(1, segments))
    previous = parent
    for index in range(segments):
        part = model.part(f"{name}{index + 1}_{parent}", parent=previous,
                          pivot=pivot if index == 0 else (0, segment, 0),
                          rot=(droop, spread, 0.0) if index else (droop * 0.5, spread * 0.5, 0.0))
        part.box(-thick // 2, 0, -thick // 2, thick, segment, thick)
        previous = part.name


def eye(model, part_name, parent, pivot, size, depth=2, rot=(0.0, 0.0, 0.0)):
    """A glowing lens; all lenses sample the bright patch at (96, 96)."""
    part = model.part(part_name, parent=parent, pivot=pivot, rot=rot)
    part.glow_box(-size[0] // 2, -size[1] // 2, -depth // 2, size[0], size[1], depth)
    return part


# --------------------------------------------------------------------------- #
#  Java emitter
# --------------------------------------------------------------------------- #

def _identifier(name):
    out = "".join(ch if ch.isalnum() else "_" for ch in name)
    if out and out[0].isdigit():
        out = "p_" + out
    return out


def emit_java(model, method_name, indent="    "):
    """Emits one `public static TexturedModelData <method>()` method."""
    lines = [f"{indent}public static TexturedModelData {method_name}() {{",
             f"{indent}    ModelData data = new ModelData();",
             f"{indent}    ModelPartData root = data.getRoot();"]

    variables = {"root": "root"}
    used = set()
    for part in model.parts[1:]:
        variable = _identifier(part.name)
        while variable in used or variable in ("data", "root"):
            variable += "_"
        used.add(variable)
        variables[part.name] = variable
        if not part.boxes:
            raise ValueError(f"part {part.name} of {model.name} has no boxes")

        lines.append("")
        lines.append(f'{indent}    ModelPartData {variable} = {variables[part.parent]}.addChild("{part.name}",')
        lines.append(f"{indent}            ModelPartBuilder.create()")
        for index, (uv, (x, y, z, dx, dy, dz)) in enumerate(part.boxes):
            prefix = f".uv({uv[0]}, {uv[1]})" if index == 0 else ""
            lines.append(f"{indent}                    {prefix}.cuboid({x}, {y}, {z}, {dx}, {dy}, {dz})")
        lines[-1] += ","
        px, py, pz = part.pivot
        pitch, yaw, roll = part.rot
        lines.append(f"{indent}            ModelTransform.of({px}, {py}, {pz}, "
                     f"{_radians(pitch)}, {_radians(yaw)}, {_radians(roll)}));")

    lines.append("")
    lines.append(f"{indent}    return TexturedModelData.of(data, {model.texture[0]}, {model.texture[1]});")
    lines.append(f"{indent}}}")
    return "\n".join(lines)


def _radians(degrees):
    value = degrees * 3.14159265358979 / 180.0
    if abs(value) < 1e-6:
        return "0.0F"
    return f"{value:.4f}F"
