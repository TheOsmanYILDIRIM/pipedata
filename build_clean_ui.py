#!/usr/bin/env python3
"""
Pipedata Clean Minimal UI Builder
Builds a two-page minimal engineering reference prototype with:
- Page 1: Clean category list with vector thumbnails and concise descriptions
- Page 2: Flange component detail with 3D-peeking swipeable carousel (mini previous/next on edges)
- Clean Settings drawer (Units, Themes, i18n Language Toggle: English / Turkish)
- 100% coded in English, with clean i18n architecture ready for any language
"""
import os
import csv
import json
import shutil

BASE_DIR = "/data/data/com.termux/files/home/projects/pipedata"
SVG_DIR = os.path.join(BASE_DIR, "data", "svg_modern")
ASSETS_DIR = os.path.join(BASE_DIR, "raw_apk", "assets")

def clean_val(v):
    if v is None or v == "N/A" or v == "":
        return None
    try:
        val = float(v)
        return int(val) if val.is_integer() else val
    except:
        return v

def clean_nps(n):
    return n.replace("0+", "").replace("+", " ").replace("'", "").strip()

def parse_flg_file(filepath):
    with open(filepath, encoding="utf-8", errors="replace") as f:
        rows = list(csv.reader(f))
    res = {}
    for r in rows[5:]:
        if len(r) < 30 or r[0] == "N/A" or r[0] == "":
            continue
        nps_raw = r[0]
        nps = clean_nps(nps_raw)
        dn = int(r[1]) if r[1].isdigit() else 0
        od = clean_val(r[2])
        thk = clean_val(r[3])
        hub_x = clean_val(r[4])
        thd_thk = clean_val(r[6])
        wn_y = clean_val(r[8])
        so_b = clean_val(r[9])
        rf_dia = clean_val(r[10])
        rf_h = clean_val(r[11])
        pcd = clean_val(r[18])
        bolts = int(r[19]) if r[19].isdigit() else 4
        bolt_sz = clean_nps(r[20])
        stud_l = clean_val(r[22])
        hole_sz = clean_nps(r[24])
        wn_kg = clean_val(r[26])
        so_kg = clean_val(r[27])
        bld_kg = clean_val(r[28])
        
        res[nps] = {
            "nps": nps, "dn": dn, "od": od, "thk": thk, "hub_x": hub_x,
            "thd_thk": thd_thk, "wn_y": wn_y, "so_b": so_b, "rf_dia": rf_dia, "rf_h": rf_h,
            "pcd": pcd, "bolts": bolts, "bolt_sz": bolt_sz, "stud_l": stud_l,
            "hole_sz": hole_sz, "wn_kg": wn_kg, "so_kg": so_kg, "bld_kg": bld_kg
        }
    return res

flange_data = {
    "150": parse_flg_file(os.path.join(ASSETS_DIR, "flg150.csv")),
    "300": parse_flg_file(os.path.join(ASSETS_DIR, "flg300.csv")),
    "600": parse_flg_file(os.path.join(ASSETS_DIR, "flg600.csv"))
}

def load_svg(filename):
    path = os.path.join(SVG_DIR, filename)
    if os.path.exists(path):
        with open(path, encoding="utf-8") as f:
            content = f.read()
        # Clean up XML headers if present
        if "<?xml" in content:
            content = content[content.find("<svg"):]
        return content
    return "<svg viewBox='0 0 100 100'><rect width='100' height='100' fill='#eee'/></svg>"

# Load carousel SVGs
flange_slides = [
    {
        "id": "wn_rf",
        "code": "flan1",
        "title": "Weld Neck (WN)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Butt-welded to pipe neck. Superior stress distribution for high pressure/temperature.",
        "svg": load_svg("modern_flan1.svg")
    },
    {
        "id": "so_rf",
        "code": "flan2",
        "title": "Slip-On (SO)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Slips over pipe and fillet-welded inside and outside. Easy alignment and economical.",
        "svg": load_svg("modern_flan2.svg")
    },
    {
        "id": "bld_rf",
        "code": "flan3",
        "title": "Blind Flange (BL)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Solid disc with bolt holes. Used to block or isolate a piping run or vessel nozzle.",
        "svg": load_svg("modern_flan3.svg")
    },
    {
        "id": "thd_rf",
        "code": "flan4",
        "title": "Threaded Flange (TH)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Tapered NPT internal threads. Assembled without welding; suitable for low-pressure utilities.",
        "svg": load_svg("modern_flan4.svg")
    },
    {
        "id": "lj_rf",
        "code": "flan5",
        "title": "Lap Joint (LJ)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Used with stub-end. Freely rotates around pipe for quick bolt hole alignment.",
        "svg": load_svg("modern_flan5.svg")
    },
    {
        "id": "sw_rf",
        "code": "flan6",
        "title": "Socket Weld (SW)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Pipe inserts into socket counter-bore and fillet-welded. Ideal for small-bore high pressure.",
        "svg": load_svg("modern_flan6.svg")
    },
    {
        "id": "wn_rtj",
        "code": "flan1a",
        "title": "Weld Neck (RTJ)",
        "facing": "RTJ",
        "badge": "ASME B16.5",
        "desc": "Ring Type Joint with precision metallic ring groove for extreme pressure service.",
        "svg": load_svg("modern_flan1a.svg")
    }
]

# Load category thumbnails
cat_svgs = {
    "flanges": load_svg("modern_flan1.svg"),
    "pipes": load_svg("modern_pipe1.svg"),
    "fittings_bw": load_svg("modern_ftbw2.svg"),
    "fittings_sw": load_svg("modern_ftsw1.svg"),
    "valves": load_svg("modern_vlfl1.svg"),
    "gaskets": load_svg("modern_gspr1.svg"),
    "blanks": load_svg("modern_blnu3.svg")
}

print(f"Data parsed successfully: {len(flange_data['150'])} sizes for 150#, {len(flange_slides)} slides.")
