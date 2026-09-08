#!/usr/bin/env python3
"""
Pipedata UI Redesign - Ronas IT "Health Care App - Patient Portal" Aesthetic
Reference: https://dribbble.com/shots/27666382-Health-Care-App-Patient-Portal
Design System Characteristics:
- Light, cool-toned, calming palette (#F4F7FB canvas, pure #FFFFFF cards, #0F172A deep dark accents)
- Bento Grid layout for modular engineering indicators (Heart-rate style dimension vitals: OD, Thk, Hub, Bore)
- Rounded squircles and soft pill chips (border-radius: 20px-24px, pill controls)
- Subtle diffused shadows (0 4px 20px rgba(15, 23, 42, 0.05))
- High typography contrast with razor-sharp geometric sans-serif
- Aspect-ratio fitted technical CAD drawings with compact mini side arrows
- Multi-touch pinch-to-zoom (1x-4x), pan, double-tap zoom/reset
- Floating pill bottom navigation bar (Catalog, Flange Specs, Settings)
- Stepper [+] / [-] text scaling (90% to 140%) and high contrast mode
- 100% English code with live i18n dictionary (en / tr)
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
        if "<?xml" in content:
            content = content[content.find("<svg"):]
        import re
        if "preserveAspectRatio=" not in content:
            content = content.replace("<svg ", '<svg preserveAspectRatio="xMidYMid meet" ')
        else:
            content = re.sub(r'preserveAspectRatio="[^"]*"', 'preserveAspectRatio="xMidYMid meet"', content)
        return content
    return "<svg viewBox='0 0 100 100'><rect width='100' height='100' fill='#eee'/></svg>"

flange_slides = [
    {
        "id": "wn_rf",
        "code": "flan1",
        "title": "Weld Neck Flange",
        "shortName": "Weld Neck",
        "abbr": "WN",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Butt-welded to pipe neck. High stress resistance for extreme thermal & pressure cycles.",
        "svg": load_svg("modern_flan1.svg")
    },
    {
        "id": "so_rf",
        "code": "flan2",
        "title": "Slip-On Flange",
        "shortName": "Slip-On",
        "abbr": "SO",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Bore slides directly over pipe. Double fillet-welded inside and outside hub.",
        "svg": load_svg("modern_flan2.svg")
    },
    {
        "id": "bld_rf",
        "code": "flan3",
        "title": "Blind Flange",
        "shortName": "Blind",
        "abbr": "BL",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Solid disc blank used to seal line ends or isolate pressure vessel nozzles.",
        "svg": load_svg("modern_flan3.svg")
    },
    {
        "id": "thd_rf",
        "code": "flan4",
        "title": "Threaded Flange",
        "shortName": "Threaded",
        "abbr": "TH",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Machined female NPT threads. Installed rapidly without site hot work or welding.",
        "svg": load_svg("modern_flan4.svg")
    },
    {
        "id": "lj_rf",
        "code": "flan5",
        "title": "Lap Joint Flange",
        "shortName": "Lap Joint",
        "abbr": "LJ",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Used with stub end. Freely pivots for effortless bolt hole alignment in tight spaces.",
        "svg": load_svg("modern_flan5.svg")
    },
    {
        "id": "sw_rf",
        "code": "flan6",
        "title": "Socket Weld Flange",
        "shortName": "Socket Weld",
        "abbr": "SW",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Pipe seats into internal shoulder recess. Suited for compact high-pressure lines.",
        "svg": load_svg("modern_flan6.svg")
    },
    {
        "id": "wn_rtj",
        "code": "flan1a",
        "title": "Weld Neck RTJ",
        "shortName": "WN (RTJ)",
        "abbr": "RTJ",
        "facing": "RTJ",
        "badge": "ASME B16.5",
        "desc": "Precision ring type joint groove for metallic ring gaskets in high-integrity piping.",
        "svg": load_svg("modern_flan1a.svg")
    }
]

cat_svgs = {
    "flanges": load_svg("modern_flan1.svg"),
    "pipes": load_svg("modern_pipe1.svg"),
    "fittings_bw": load_svg("modern_ftbw2.svg"),
    "fittings_sw": load_svg("modern_ftsw1.svg"),
    "valves": load_svg("modern_vlfl1.svg"),
    "gaskets": load_svg("modern_gspr1.svg"),
    "blanks": load_svg("modern_blnu3.svg")
}

print(f"Loaded {len(flange_slides)} flange slides and {len(flange_data)} ratings.")
