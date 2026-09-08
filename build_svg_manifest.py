#!/usr/bin/env python3
"""
Pipedata SVG Master Manifest Generator (build_svg_manifest.py)
Catalogs all 122 SVGs with category, placeholders, dimensions, standards, and conversion status.
"""

import os
import glob
import re
import json

def get_category_and_standard(fname):
    fn = fname.lower()
    if fn.startswith("pipe"):
        return "Pipes", "ASME B36.10M / B36.19M", "Pipe Cross-Section & Dimensions"
    elif fn.startswith("ftbw"):
        ftbw_map = {
            "ftbw1.svg": "90° Long Radius Elbow (ASME B16.9)",
            "ftbw2.svg": "90° Short Radius Elbow (ASME B16.9)",
            "ftbw3.svg": "45° Elbow (ASME B16.9)",
            "ftbw4.svg": "Equal Tee (ASME B16.9)",
            "ftbw5.svg": "Reducing Tee (ASME B16.9)",
            "ftbw6.svg": "Concentric Reducer (ASME B16.9)",
            "ftbw7.svg": "Eccentric Reducer (ASME B16.9)",
            "ftbw8.svg": "Lap Joint Stub End (ASME B16.9)",
            "ftbw9.svg": "Buttwelding Cap (ASME B16.9)",
            "ftbw10.svg": "180° LR Return (ASME B16.9)",
            "ftbw11.svg": "180° SR Return (ASME B16.9)"
        }
        return "Buttweld Fittings", "ASME B16.9", ftbw_map.get(fn, "Buttweld Fitting (ASME B16.9)")
    elif fn.startswith("flan"):
        return "Flanges", "ASME B16.5 / B16.47", "Flange Dimensions & Facing"
    elif fn.startswith("blnu"):
        return "Line Blanks", "ASME B16.48", "Figure-8 Blind / Paddle Blank / Spacer"
    elif fn.startswith("ftsc"):
        return "Threaded Fittings", "ASME B16.11", "Forged Threaded Fitting"
    elif fn.startswith("ftsw"):
        return "Socketweld Fittings", "ASME B16.11", "Forged Socketweld Fitting"
    elif fn.startswith("fswa"):
        return "Swage Nipples", "BS 3799 / MSS-SP-95", "Swage Nipple Dimensions"
    elif fn.startswith("gflt") or fn.startswith("grtj") or fn.startswith("gspr"):
        return "Gaskets", "ASME B16.20 / B16.21", "Flat / Spiral-Wound / RTJ Gasket"
    elif fn.startswith("vl"):
        return "Valves", "ASME B16.10 / API 600", "Valve Face-to-Face & Envelope"
    elif fn.startswith("headers"):
        return "UI Components", "Internal", "Table Header Banner"
    else:
        return "General Components", "Industrial", "Engineering Technical Drawing"

def build_svg_manifest():
    svg_dir = "/data/data/com.termux/files/home/projects/pipedata/data/svg"
    manifest_out = "/data/data/com.termux/files/home/projects/pipedata/svg_manifest.json"

    svg_files = sorted(glob.glob(os.path.join(svg_dir, "*.svg")))
    print(f"Found {len(svg_files)} SVG files in {svg_dir}")

    catalog = {}
    family_counts = {}

    for idx, s_path in enumerate(svg_files, start=1):
        fname = os.path.basename(s_path)
        with open(s_path, "r", encoding="utf-8", errors="ignore") as f:
            content = f.read()

        category, standard, comp_name = get_category_and_standard(fname)
        family_counts[category] = family_counts.get(category, 0) + 1

        # Extract placeholders [[x]]
        raw_placeholders = re.findall(r"\[\[(.*?)\]\]", content)
        unique_ph = list(dict.fromkeys(raw_placeholders))

        # Check existing primitives count
        line_count = content.count("<line")
        path_count = content.count("<path")
        circle_count = content.count("<circle")
        text_count = content.count("<text")

        # Map placeholder semantics if known
        ph_details = {}
        for p in unique_ph:
            ph_details[f"[[{p}]]"] = {
                "tag": p,
                "is_secondary_db": p.startswith("DB2_"),
                "description": f"Dynamic parameter {p} from SQLite"
            }

        status = "completed" if fname == "pipe1.svg" else "pending"

        catalog[fname] = {
            "id": idx,
            "filename": fname,
            "modern_filename": f"modern_{fname}",
            "category": category,
            "standard": standard,
            "component_name": comp_name,
            "status": status,
            "original_metrics": {
                "file_size_bytes": len(content),
                "line_tags": line_count,
                "path_tags": path_count,
                "circle_tags": circle_count,
                "text_tags": text_count
            },
            "placeholders": ph_details,
            "placeholder_count": len(unique_ph),
            "cad_standards": {
                "arrowheads": "AutoCAD Closed Filled 3:1 (ASME Y14.5M)",
                "centerlines": "Long-Short-Long Dash (ASME Y14.5M)",
                "hatching": "ANSI 31 45° Cross-Hatch Pattern",
                "pill_badges": True,
                "theme_ready": True
            }
        }

    manifest = {
        "metadata": {
            "specification_version": "1.0.0",
            "title": "Pipedata AutoCAD-Standard Modern SVG Manifest",
            "author": "Antigravity Engineering",
            "total_svgs": len(svg_files),
            "completed_count": 1,
            "pending_count": len(svg_files) - 1,
            "cad_standards": "ASME Y14.5M / ISO 129-1 (AutoCAD Compatible)",
            "family_distribution": family_counts
        },
        "themes": {
            "cad_dark": {
                "name": "AutoCAD Modern CAD Dark (Default)",
                "background": "#0f172a",
                "body_stroke": "#f8fafc",
                "centerline_stroke": "#38bdf8",
                "dim_stroke": "#38bdf8",
                "dim_text": "#38bdf8",
                "hatch_color": "#334155",
                "pill_bg": "#1e293b",
                "pill_border": "#334155"
            },
            "classic_blueprint": {
                "name": "Classic Engineering Blueprint",
                "background": "#0b2545",
                "grid_stroke": "#133b68",
                "body_stroke": "#ffffff",
                "centerline_stroke": "#60a5fa",
                "dim_stroke": "#93c5fd",
                "dim_text": "#ffffff",
                "hatch_color": "#1d4ed8",
                "pill_bg": "#0f3460",
                "pill_border": "#60a5fa"
            },
            "engineering_white": {
                "name": "Engineering Paper & Print",
                "background": "#ffffff",
                "body_stroke": "#0f172a",
                "centerline_stroke": "#dc2626",
                "dim_stroke": "#2563eb",
                "dim_text": "#0f172a",
                "hatch_color": "#94a3b8",
                "pill_bg": "#f1f5f9",
                "pill_border": "#cbd5e1"
            },
            "oled_amber": {
                "name": "OLED Monospace Amber (Retro CAD)",
                "background": "#000000",
                "body_stroke": "#f59e0b",
                "centerline_stroke": "#d97706",
                "dim_stroke": "#fbbf24",
                "dim_text": "#fbbf24",
                "hatch_color": "#451a03",
                "pill_bg": "#1c1917",
                "pill_border": "#78350f"
            }
        },
        "svg_catalog": catalog
    }

    with open(manifest_out, "w", encoding="utf-8") as f:
        json.dump(manifest, f, indent=2)

    print(f"\n[OK] Master SVG manifest generated at: {manifest_out}")
    print(f"Total SVGs cataloged: {len(catalog)}")
    for fam, cnt in family_counts.items():
        print(f"  - {fam:<25}: {cnt} dosya")

if __name__ == "__main__":
    build_svg_manifest()
