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

# 3. every blockstate -> models -> textures
for state_file in sorted((ASSETS / "blockstates").glob("*.json")):
    bs = json.loads(state_file.read_text())
    for variant in bs.get("variants", {}).values():
        model_path = variant["model"].split(":")[1]
        model_file = (ASSETS / "models" / model_path).with_suffix(".json")
        if not model_file.exists():
            errors.append(f"missing block model {model_file}")
            continue
        m = json.loads(model_file.read_text())
        for tex in m.get("textures", {}).values():
            if not tex.startswith("wotw:"):
                continue
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

# 5. item textures for every model that uses layer0
for model_file in sorted((ASSETS / "models/item").glob("*.json")):
    m = json.loads(model_file.read_text())
    layer = m.get("textures", {}).get("layer0")
    if not layer or not layer.startswith("wotw:"):
        continue
    p = ASSETS / "textures" / (layer.split(":", 1)[1] + ".png")
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
block_items = {f.stem for f in (ASSETS / "blockstates").glob("*.json")}
for item in items:
    if item in block_items:   # BlockItems use the block translation key
        used.add(f"block.wotw.{item}")
        continue
    used.add(f"item.wotw.{item}")
for block in block_items:
    used.add(f"block.wotw.{block}")
for key in sorted(used):
    if key not in en:
        errors.append(f"missing en_us key: {key}")
    if key not in ru:
        errors.append(f"missing ru_ru key: {key}")
for key in en:
    if key not in ru:
        errors.append(f"en key without ru translation: {key}")

# 7. sounds: every event in sounds.json needs its ogg and its subtitle
sounds_file = ASSETS / "sounds.json"
if sounds_file.exists():
    sounds = json.loads(sounds_file.read_text())
    for event, definition in sounds.items():
        for entry in definition.get("sounds", []):
            if isinstance(entry, str):
                name = entry
            else:
                name = entry["name"]
            path = ASSETS / "sounds" / (name.split(":", 1)[1] + ".ogg")
            if not path.exists():
                errors.append(f"missing sound file {path.relative_to(ROOT)} (event {event})")
        subtitle = definition.get("subtitle")
        if subtitle and subtitle not in en:
            errors.append(f"missing subtitle key: {subtitle}")
    for ogg in (ASSETS / "sounds").glob("*.ogg"):
        if not any(ogg.stem == (e if isinstance(e, str) else e["name"]).split(":")[-1]
                   for d in sounds.values() for e in d.get("sounds", [])):
            errors.append(f"orphan sound file: {ogg.relative_to(ROOT)}")
else:
    errors.append("missing sounds.json")

# 8. structures: nbt files, worldgen json and the template pool chain
structures = sorted(f.stem for f in (RES / "data/wotw/structures").glob("*.nbt"))
if not structures:
    errors.append("no structure files")
for name in structures:
    structure_json = RES / "data/wotw/worldgen/structure" / f"{name}.json"
    pool_json = RES / "data/wotw/worldgen/template_pool" / name / "start_pool.json"
    set_json = RES / "data/wotw/worldgen/structure_set" / f"{name}.json"
    for path in (structure_json, pool_json, set_json):
        if not path.exists():
            errors.append(f"missing worldgen file {path.relative_to(ROOT)}")
            continue
    if structure_json.exists():
        spec = json.loads(structure_json.read_text())
        pool_ref = spec.get("start_pool", "").split(":", 1)[-1]
        if not (RES / "data/wotw/worldgen/template_pool" / f"{pool_ref}.json").exists():
            errors.append(f"{name}: start_pool {pool_ref} has no file")
        biome_tag = spec.get("biomes", "").lstrip("#").split(":", 1)[-1]
        if not (RES / "data/wotw/tags/worldgen/biome" / f"{biome_tag}.json").exists():
            errors.append(f"{name}: missing biome tag {biome_tag}")
    if pool_json.exists():
        element = json.loads(pool_json.read_text())["elements"][0]["element"]
        target = element["location"].split(":", 1)[1]
        if not (RES / "data/wotw/structures" / f"{target}.nbt").exists():
            errors.append(f"{name}: pool points at missing nbt {target}.nbt")

# 9. icon
if not (ASSETS / "icon.png").exists():
    errors.append("missing mod icon")

if errors:
    print(f"{len(errors)} problem(s):")
    for e in errors:
        print(" -", e)
    sys.exit(1)
print("all asset checks passed")
print(f" items: {len(items)}, entities: {len(entities)}, beams: {len(beams)}, lang keys: {len(en)}")
