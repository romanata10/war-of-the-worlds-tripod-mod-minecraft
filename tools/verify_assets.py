#!/usr/bin/env python3
"""Sanity checks for the mod: JSON validity, model/texture/lang references."""
import json, os, re, sys, pathlib

ROOT = pathlib.Path(__file__).resolve().parent.parent
RES = ROOT / "src" / "main" / "resources"
ASSETS = RES / "assets" / "wotw"
JAVA = ROOT / "src" / "main" / "java"
errors = []

# 1. JSON validity
for f in RES.rglob("*.json"):
    try:
        json.loads(f.read_text())
    except Exception as e:
        errors.append(f"invalid json {f.relative_to(ROOT)}: {e}")

# 2. items from ModItems -> item models
items = set(re.findall(r'register\("([a-z0-9_]+)"', (JAVA / "dev/arena/wotw/registry/ModItems.java").read_text()))
items |= set(re.findall(r'spawnEgg\("([a-z0-9_]+)"', (JAVA / "dev/arena/wotw/registry/ModItems.java").read_text()))
for item in sorted(items):
    model = ASSETS / "models/item" / f"{item}.json"
    if not model.exists():
        errors.append(f"missing item model: {model.relative_to(ROOT)}")

# 3. blockstate -> models -> textures
bs = json.loads((ASSETS / "blockstates/martian_cylinder.json").read_text())
for variant in bs["variants"].values():
    model_path = variant["model"].split(":")[1]
    model_file = (ASSETS / "models" / model_path).with_suffix(".json")
    if not model_file.exists():
        errors.append(f"missing block model {model_file}")
    else:
        m = json.loads(model_file.read_text())
        for tex in m.get("textures", {}).values():
            tex_path = ASSETS / "textures" / (tex.split(":", 1)[1] + ".png")
            if not tex_path.exists():
                errors.append(f"missing block texture {tex_path.relative_to(ROOT)}")

# 4. entity textures referenced by the client
client = (JAVA / "dev/arena/wotw/client/WotwClient.java").read_text()
for tex in re.findall(r'"(tripod_2005|bbc_white_tripod|bbc_red_tripod|flying_manta|book_tripod|asylum_walker|goliath_tripod|martian_heron|martian_spider|martian_louse)"', client):
    pass
names = re.findall(r'ModEntities\.[A-Z_]+,\s*\n?\s*"([a-z0-9_]+)"', client)
for name in names:
    p = ASSETS / "textures/entity" / f"{name}.png"
    if not p.exists():
        errors.append(f"missing entity texture {p.relative_to(ROOT)}")
beams = re.findall(r'^\s{4}[A-Z_]+\("([a-z_]+)"', (JAVA / "dev/arena/wotw/entity/projectile/BeamKind.java").read_text(), re.M)
for b in beams:
    p = ASSETS / "textures/entity" / f"beam_{b}.png"
    if not p.exists():
        errors.append(f"missing beam texture {p.relative_to(ROOT)}")

# 5. item textures for simple item models
for item in ["martian_alloy", "heat_ray_emitter"]:
    m = json.loads((ASSETS / "models/item" / f"{item}.json").read_text())
    tex = m["textures"]["layer0"].split(":", 1)[1]
    p = ASSETS / "textures" / (tex + ".png")
    if not p.exists():
        errors.append(f"missing item texture {p.relative_to(ROOT)}")

# 6. lang keys
en = json.loads((ASSETS / "lang/en_us.json").read_text())
ru = json.loads((ASSETS / "lang/ru_ru.json").read_text())
used = set(re.findall(r'Text\.translatable\("([\w.]+)"', "\n".join(p.read_text() for p in JAVA.rglob("*.java"))))
used.add("itemGroup.wotw")
entities = re.findall(r'tripod\("([a-z0-9_]+)"', (JAVA / "dev/arena/wotw/registry/ModEntities.java").read_text())
for e in entities:
    if e == "beam":      # rays are not nameable living entities
        continue
    used.add(f"entity.wotw.{e}")
for item in items:
    if item == "martian_cylinder":   # BlockItem uses the block translation key
        used.add("block.wotw.martian_cylinder")
        continue
    used.add(f"item.wotw.{item}")
used.add("block.wotw.martian_cylinder")
for key in sorted(used):
    if key not in en:
        errors.append(f"missing en_us key: {key}")
    if key not in ru:
        errors.append(f"missing ru_ru key: {key}")
for key in en:
    if key not in ru:
        errors.append(f"en key without ru translation: {key}")

# 7. icon
if not (ASSETS / "icon.png").exists():
    errors.append("missing mod icon")

if errors:
    print(f"{len(errors)} problem(s):")
    for e in errors:
        print(" -", e)
    sys.exit(1)
print("all asset checks passed")
print(f" items: {len(items)}, entities: {len(entities)}, beams: {len(beams)}, lang keys: {len(en)}")
