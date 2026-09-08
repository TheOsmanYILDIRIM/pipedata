#!/usr/bin/env python3
"""
Generates the clean minimal Pipedata prototype:
- 2 Pages + Settings
- Page 1: Category list with vector thumbnails and descriptions
- Page 2: Flange component detail with 3D-peeking swipeable carousel (mini prev/next on sides)
- Full specifications and options below the carousel
- 100% English code with clean i18n architecture ready for multi-language
- Saved to /data/data/com.termux/files/home/projects/pipedata/pipedata_clean_ui.html
- Copied to /storage/emulated/0/Download/pipedata_clean_ui.html
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
        return content
    return "<svg viewBox='0 0 100 100'><rect width='100' height='100' fill='#eee'/></svg>"

flange_slides = [
    {
        "id": "wn_rf",
        "code": "flan1",
        "title": "Weld Neck (WN)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Butt-welded to pipe neck. High structural strength and stress dissipation.",
        "svg": load_svg("modern_flan1.svg")
    },
    {
        "id": "so_rf",
        "code": "flan2",
        "title": "Slip-On (SO)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Bore slides over pipe, welded at hub and inside. Cost-effective assembly.",
        "svg": load_svg("modern_flan2.svg")
    },
    {
        "id": "bld_rf",
        "code": "flan3",
        "title": "Blind Flange (BL)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Solid blanking plate used to isolate or terminate pressure lines.",
        "svg": load_svg("modern_flan3.svg")
    },
    {
        "id": "thd_rf",
        "code": "flan4",
        "title": "Threaded Flange (TH)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Internal NPT threads. Screwed directly without welding.",
        "svg": load_svg("modern_flan4.svg")
    },
    {
        "id": "lj_rf",
        "code": "flan5",
        "title": "Lap Joint (LJ)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Used with stub end. Freely pivots for effortless bolt hole alignment.",
        "svg": load_svg("modern_flan5.svg")
    },
    {
        "id": "sw_rf",
        "code": "flan6",
        "title": "Socket Weld (SW)",
        "facing": "RF",
        "badge": "ASME B16.5",
        "desc": "Pipe inserts into socket counterbore. Suitable for compact high-pressure lines.",
        "svg": load_svg("modern_flan6.svg")
    },
    {
        "id": "wn_rtj",
        "code": "flan1a",
        "title": "Weld Neck (RTJ)",
        "facing": "RTJ",
        "badge": "ASME B16.5",
        "desc": "Precision ring type joint groove for leak-tight metallic ring seals.",
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

html_content = f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
  <title>Pipedata - Clean Piping Engineering Reference</title>
  <style>
    :root {{
      --bg-app: #f8fafc;
      --bg-surface: #ffffff;
      --bg-surface-elevated: #ffffff;
      --bg-subtle: #f1f5f9;
      --bg-active: #e2e8f0;
      --bg-carousel: #0f172a;
      
      --text-primary: #0f172a;
      --text-secondary: #475569;
      --text-muted: #64748b;
      --text-inverse: #ffffff;
      
      --border-color: #e2e8f0;
      --border-subtle: #cbd5e1;
      --border-focus: #2563eb;
      
      --accent: #2563eb;
      --accent-hover: #1d4ed8;
      --accent-subtle: #eff6ff;
      --accent-border: #bfdbfe;
      
      --radius-sm: 8px;
      --radius-md: 12px;
      --radius-lg: 16px;
      --radius-full: 9999px;
      
      --shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
      --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.07), 0 2px 4px -2px rgba(0, 0, 0, 0.07);
      --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.08), 0 4px 6px -4px rgba(0, 0, 0, 0.08);
      
      --font-sans: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
      --font-mono: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace;
    }}

    [data-theme="dark"] {{
      --bg-app: #090d16;
      --bg-surface: #111827;
      --bg-surface-elevated: #1a2234;
      --bg-subtle: #1e293b;
      --bg-active: #283548;
      --bg-carousel: #050811;
      
      --text-primary: #f8fafc;
      --text-secondary: #cbd5e1;
      --text-muted: #94a3b8;
      --text-inverse: #ffffff;
      
      --border-color: #1e293b;
      --border-subtle: #334155;
      --border-focus: #38bdf8;
      
      --accent: #38bdf8;
      --accent-hover: #0ea5e9;
      --accent-subtle: #0c2135;
      --accent-border: #164e63;
    }}

    * {{
      box-sizing: border-box;
      margin: 0;
      padding: 0;
      -webkit-tap-highlight-color: transparent;
    }}

    body {{
      font-family: var(--font-sans);
      background-color: #0b0f19;
      color: var(--text-primary);
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 16px;
      line-height: 1.5;
    }}

    /* Mobile Device Frame for Desktop Preview */
    .app-viewport {{
      width: 100%;
      max-width: 440px;
      height: 900px;
      max-height: 94vh;
      background-color: var(--bg-app);
      border-radius: 28px;
      box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5), 0 0 0 1px rgba(255, 255, 255, 0.1);
      display: flex;
      flex-direction: column;
      overflow: hidden;
      position: relative;
    }}

    @media (max-width: 480px) {{
      body {{
        padding: 0;
        background-color: var(--bg-app);
      }}
      .app-viewport {{
        max-width: 100%;
        height: 100vh;
        max-height: 100vh;
        border-radius: 0;
        box-shadow: none;
      }}
    }}

    /* Top App Bar */
    .app-header {{
      background-color: var(--bg-surface);
      border-bottom: 1px solid var(--border-color);
      padding: 12px 16px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      flex-shrink: 0;
      z-index: 10;
    }}

    .header-title-group {{
      display: flex;
      align-items: center;
      gap: 8px;
    }}

    .header-back-btn {{
      display: none;
      align-items: center;
      gap: 4px;
      background: none;
      border: none;
      color: var(--accent);
      font-size: 15px;
      font-weight: 600;
      cursor: pointer;
      padding: 4px 8px 4px 0;
      border-radius: var(--radius-sm);
    }}

    .header-back-btn svg {{
      width: 18px;
      height: 18px;
      stroke: currentColor;
    }}

    .app-branding h1 {{
      font-size: 18px;
      font-weight: 700;
      letter-spacing: -0.02em;
      color: var(--text-primary);
    }}

    .app-branding p {{
      font-size: 12px;
      color: var(--text-muted);
      font-weight: 500;
    }}

    .header-actions {{
      display: flex;
      align-items: center;
      gap: 6px;
    }}

    .icon-btn {{
      background: var(--bg-subtle);
      border: 1px solid var(--border-color);
      color: var(--text-secondary);
      width: 36px;
      height: 36px;
      border-radius: var(--radius-full);
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: background-color 0.15s, border-color 0.15s;
    }}

    .icon-btn:hover {{
      background: var(--bg-active);
      color: var(--text-primary);
    }}

    .unit-pill-btn {{
      background: var(--bg-subtle);
      border: 1px solid var(--border-color);
      color: var(--text-primary);
      font-size: 12px;
      font-weight: 700;
      padding: 4px 10px;
      border-radius: var(--radius-full);
      cursor: pointer;
    }}

    /* Main Scrollable View Area */
    .app-content {{
      flex: 1;
      overflow-y: auto;
      overflow-x: hidden;
      display: flex;
      flex-direction: column;
      position: relative;
    }}

    .page-view {{
      width: 100%;
      display: none;
      flex-direction: column;
      flex: 1;
    }}

    .page-view.active {{
      display: flex;
    }}

    /* =========================================
       PAGE 1: TOPIC / CATEGORY LIST
       ========================================= */
    .search-box-wrap {{
      padding: 12px 16px 8px 16px;
    }}

    .search-input-wrap {{
      position: relative;
      display: flex;
      align-items: center;
    }}

    .search-input-wrap svg {{
      position: absolute;
      left: 12px;
      width: 16px;
      height: 16px;
      stroke: var(--text-muted);
    }}

    .search-input {{
      width: 100%;
      background: var(--bg-surface);
      border: 1px solid var(--border-color);
      border-radius: var(--radius-md);
      padding: 10px 14px 10px 36px;
      font-size: 14px;
      color: var(--text-primary);
      outline: none;
      transition: border-color 0.2s, box-shadow 0.2s;
    }}

    .search-input:focus {{
      border-color: var(--border-focus);
      box-shadow: 0 0 0 3px var(--accent-subtle);
    }}

    .topic-list {{
      padding: 8px 16px 24px 16px;
      display: flex;
      flex-direction: column;
      gap: 10px;
    }}

    .topic-card {{
      background: var(--bg-surface);
      border: 1px solid var(--border-color);
      border-radius: var(--radius-md);
      padding: 12px 14px;
      display: flex;
      align-items: center;
      gap: 14px;
      cursor: pointer;
      transition: transform 0.15s, border-color 0.15s, box-shadow 0.15s;
      text-decoration: none;
      color: inherit;
    }}

    .topic-card:hover {{
      border-color: var(--border-subtle);
      transform: translateY(-1px);
      box-shadow: var(--shadow-sm);
    }}

    .topic-card:active {{
      transform: scale(0.99);
      background: var(--bg-subtle);
    }}

    .topic-thumb {{
      width: 52px;
      height: 52px;
      border-radius: var(--radius-sm);
      background: #0d1520;
      border: 1px solid rgba(56, 189, 248, 0.2);
      flex-shrink: 0;
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden;
      padding: 3px;
    }}

    .topic-thumb svg {{
      width: 100%;
      height: 100%;
      display: block;
    }}

    .topic-info {{
      flex: 1;
      min-width: 0;
    }}

    .topic-title-row {{
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 3px;
    }}

    .topic-title {{
      font-size: 15px;
      font-weight: 600;
      color: var(--text-primary);
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }}

    .topic-badge {{
      font-size: 10px;
      font-weight: 700;
      text-transform: uppercase;
      padding: 2px 6px;
      border-radius: 4px;
      background: var(--accent-subtle);
      color: var(--accent);
      border: 1px solid var(--accent-border);
      flex-shrink: 0;
    }}

    .topic-desc {{
      font-size: 12px;
      color: var(--text-muted);
      line-height: 1.35;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }}

    .topic-chevron svg {{
      width: 18px;
      height: 18px;
      stroke: var(--text-muted);
      transition: transform 0.15s;
    }}

    .topic-card:hover .topic-chevron svg {{
      transform: translateX(2px);
      stroke: var(--text-primary);
    }}

    /* =========================================
       PAGE 2: FLANGE COMPONENT DETAIL VIEW
       ========================================= */
    /* 3D-Peeking Carousel Section */
    .carousel-section {{
      background: var(--bg-carousel);
      padding: 16px 0 12px 0;
      position: relative;
      user-select: none;
      overflow: hidden;
    }}

    .carousel-container {{
      width: 100%;
      height: 220px;
      position: relative;
      touch-action: pan-y;
      cursor: grab;
    }}

    .carousel-container:active {{
      cursor: grabbing;
    }}

    .carousel-track {{
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      position: relative;
      transition: transform 0.32s cubic-bezier(0.2, 0.9, 0.4, 1.1);
    }}

    .carousel-slide {{
      position: absolute;
      width: 68%;
      height: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      transition: transform 0.32s cubic-bezier(0.2, 0.9, 0.4, 1.1), opacity 0.32s;
      border-radius: var(--radius-md);
      overflow: hidden;
      background: #090d16;
      border: 1px solid rgba(255, 255, 255, 0.08);
      box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.6);
    }}

    .carousel-slide.active {{
      transform: translateX(0) scale(1);
      opacity: 1;
      z-index: 3;
      border-color: rgba(56, 189, 248, 0.4);
    }}

    .carousel-slide.prev {{
      transform: translateX(-76%) scale(0.82);
      opacity: 0.35;
      z-index: 1;
      cursor: pointer;
    }}

    .carousel-slide.next {{
      transform: translateX(76%) scale(0.82);
      opacity: 0.35;
      z-index: 1;
      cursor: pointer;
    }}

    .carousel-slide.hidden-left {{
      transform: translateX(-150%) scale(0.65);
      opacity: 0;
      z-index: 0;
      pointer-events: none;
    }}

    .carousel-slide.hidden-right {{
      transform: translateX(150%) scale(0.65);
      opacity: 0;
      z-index: 0;
      pointer-events: none;
    }}

    .slide-svg-box {{
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 6px;
    }}

    .slide-svg-box svg {{
      width: 100%;
      height: 100%;
      object-fit: contain;
    }}

    /* Carousel Nav Arrows */
    .carousel-arrow {{
      position: absolute;
      top: 50%;
      transform: translateY(-50%);
      width: 32px;
      height: 32px;
      border-radius: 50%;
      background: rgba(15, 23, 42, 0.7);
      border: 1px solid rgba(255, 255, 255, 0.15);
      color: #ffffff;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      z-index: 4;
      backdrop-filter: blur(4px);
      transition: background 0.15s, transform 0.15s;
    }}

    .carousel-arrow:hover {{
      background: rgba(37, 99, 235, 0.9);
      transform: translateY(-50%) scale(1.08);
    }}

    .carousel-arrow.prev-btn {{
      left: 8px;
    }}

    .carousel-arrow.next-btn {{
      right: 8px;
    }}

    .carousel-arrow svg {{
      width: 16px;
      height: 16px;
      stroke: currentColor;
    }}

    /* Carousel Status and Indicators */
    .carousel-meta {{
      padding: 10px 16px 4px 16px;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 6px;
      text-align: center;
    }}

    .flange-type-pill {{
      display: inline-flex;
      align-items: center;
      gap: 6px;
      background: var(--bg-surface);
      border: 1px solid var(--border-color);
      padding: 4px 12px;
      border-radius: var(--radius-full);
      box-shadow: var(--shadow-sm);
    }}

    .flange-type-name {{
      font-size: 13px;
      font-weight: 700;
      color: var(--text-primary);
    }}

    .flange-standard-badge {{
      font-size: 10px;
      font-weight: 700;
      padding: 1px 6px;
      background: var(--accent-subtle);
      color: var(--accent);
      border-radius: 4px;
    }}

    .carousel-dots {{
      display: flex;
      align-items: center;
      gap: 6px;
    }}

    .dot {{
      width: 6px;
      height: 6px;
      border-radius: 50%;
      background: var(--border-subtle);
      transition: all 0.2s;
      cursor: pointer;
    }}

    .dot.active {{
      width: 18px;
      border-radius: 4px;
      background: var(--accent);
    }}

    .swipe-instruction {{
      font-size: 11px;
      color: var(--text-muted);
      font-weight: 500;
    }}

    /* Controls & Options Section */
    .controls-panel {{
      padding: 12px 16px;
      display: flex;
      flex-direction: column;
      gap: 14px;
      background: var(--bg-surface);
      border-bottom: 1px solid var(--border-color);
    }}

    .control-group {{
      display: flex;
      flex-direction: column;
      gap: 6px;
    }}

    .control-label {{
      font-size: 11px;
      font-weight: 700;
      text-transform: uppercase;
      letter-spacing: 0.05em;
      color: var(--text-muted);
      display: flex;
      justify-content: space-between;
    }}

    .control-label span.highlight {{
      color: var(--accent);
      font-weight: 800;
    }}

    /* Horizontal Scrollable Chips for NPS */
    .chip-scroller {{
      display: flex;
      gap: 6px;
      overflow-x: auto;
      padding-bottom: 4px;
      scrollbar-width: none;
      -ms-overflow-style: none;
    }}

    .chip-scroller::-webkit-scrollbar {{
      display: none;
    }}

    .chip-pill {{
      padding: 6px 14px;
      border-radius: var(--radius-full);
      background: var(--bg-subtle);
      border: 1px solid var(--border-color);
      color: var(--text-secondary);
      font-size: 13px;
      font-weight: 600;
      white-space: nowrap;
      cursor: pointer;
      transition: all 0.15s;
    }}

    .chip-pill:hover {{
      background: var(--bg-active);
      color: var(--text-primary);
    }}

    .chip-pill.active {{
      background: var(--accent);
      border-color: var(--accent);
      color: var(--text-inverse);
      box-shadow: 0 2px 4px rgba(37, 99, 235, 0.25);
    }}

    /* Segmented Button Group (Ratings & Facing) */
    .segmented-control {{
      display: flex;
      background: var(--bg-subtle);
      border: 1px solid var(--border-color);
      padding: 3px;
      border-radius: var(--radius-md);
      gap: 2px;
    }}

    .seg-btn {{
      flex: 1;
      padding: 6px 8px;
      border-radius: var(--radius-sm);
      border: none;
      background: transparent;
      color: var(--text-secondary);
      font-size: 12px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.15s;
      text-align: center;
    }}

    .seg-btn.active {{
      background: var(--bg-surface);
      color: var(--text-primary);
      box-shadow: var(--shadow-sm);
      font-weight: 700;
    }}

    /* Specifications Table */
    .specs-section {{
      padding: 14px 16px 28px 16px;
      display: flex;
      flex-direction: column;
      gap: 12px;
    }}

    .specs-header {{
      display: flex;
      align-items: center;
      justify-content: space-between;
    }}

    .specs-title {{
      font-size: 13px;
      font-weight: 700;
      text-transform: uppercase;
      letter-spacing: 0.04em;
      color: var(--text-muted);
    }}

    .copy-btn {{
      background: none;
      border: none;
      color: var(--accent);
      font-size: 12px;
      font-weight: 600;
      display: flex;
      align-items: center;
      gap: 4px;
      cursor: pointer;
      padding: 3px 8px;
      border-radius: var(--radius-sm);
    }}

    .copy-btn:hover {{
      background: var(--accent-subtle);
    }}

    .specs-card {{
      background: var(--bg-surface);
      border: 1px solid var(--border-color);
      border-radius: var(--radius-md);
      overflow: hidden;
      box-shadow: var(--shadow-sm);
    }}

    .spec-row {{
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px 14px;
      border-bottom: 1px solid var(--border-color);
      font-size: 13px;
    }}

    .spec-row:last-child {{
      border-bottom: none;
    }}

    .spec-row:nth-child(even) {{
      background: var(--bg-subtle);
    }}

    .spec-label-group {{
      display: flex;
      flex-direction: column;
      gap: 1px;
    }}

    .spec-key {{
      font-weight: 600;
      color: var(--text-primary);
    }}

    .spec-subkey {{
      font-size: 11px;
      color: var(--text-muted);
      font-family: var(--font-mono);
    }}

    .spec-value-group {{
      text-align: right;
    }}

    .spec-val-primary {{
      font-family: var(--font-mono);
      font-size: 14px;
      font-weight: 700;
      color: var(--text-primary);
    }}

    .spec-val-secondary {{
      font-size: 11px;
      color: var(--text-muted);
      font-family: var(--font-mono);
    }}

    /* =========================================
       SETTINGS DRAWER / MODAL
       ========================================= */
    .settings-overlay {{
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.6);
      backdrop-filter: blur(3px);
      z-index: 50;
      display: none;
      align-items: flex-end;
      opacity: 0;
      transition: opacity 0.25s;
    }}

    .settings-overlay.open {{
      display: flex;
      opacity: 1;
    }}

    .settings-drawer {{
      width: 100%;
      max-height: 85%;
      background: var(--bg-surface);
      border-top-left-radius: 20px;
      border-top-right-radius: 20px;
      padding: 20px 20px 32px 20px;
      display: flex;
      flex-direction: column;
      gap: 20px;
      overflow-y: auto;
      box-shadow: 0 -10px 25px rgba(0, 0, 0, 0.2);
      transform: translateY(100%);
      transition: transform 0.25s cubic-bezier(0.1, 0.9, 0.2, 1);
    }}

    .settings-overlay.open .settings-drawer {{
      transform: translateY(0);
    }}

    .settings-drawer-header {{
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid var(--border-color);
      padding-bottom: 12px;
    }}

    .settings-drawer-header h2 {{
      font-size: 17px;
      font-weight: 700;
    }}

    .close-btn {{
      background: var(--bg-subtle);
      border: 1px solid var(--border-color);
      width: 30px;
      height: 30px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      color: var(--text-secondary);
    }}

    .setting-item {{
      display: flex;
      flex-direction: column;
      gap: 8px;
    }}

    .setting-item-label {{
      font-size: 13px;
      font-weight: 600;
      color: var(--text-primary);
    }}

    .setting-item-desc {{
      font-size: 12px;
      color: var(--text-muted);
    }}

    .about-info-box {{
      padding: 12px;
      background: var(--bg-subtle);
      border-radius: var(--radius-md);
      font-size: 12px;
      color: var(--text-secondary);
      line-height: 1.5;
    }}
  </style>
</head>
<body data-theme="light">

  <div class="app-viewport">
    
    <!-- App Top Bar -->
    <header class="app-header">
      <div class="header-title-group">
        <button id="headerBackBtn" class="header-back-btn" onclick="navigateToPage('page-topics')">
          <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" /></svg>
          <span id="backBtnText">Topics</span>
        </button>
        <div class="app-branding" id="headerBranding">
          <h1 id="appTitle">Pipedata</h1>
          <p id="appSubtitle">Piping Engineering Reference</p>
        </div>
      </div>
      <div class="header-actions">
        <button id="quickUnitBtn" class="unit-pill-btn" onclick="toggleQuickUnit()">Metric (mm)</button>
        <button class="icon-btn" onclick="openSettings()" title="Settings">
          <svg width="18" height="18" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
            <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
        </button>
      </div>
    </header>

    <!-- Scrollable Content View -->
    <main class="app-content">
      
      <!-- =========================================
           PAGE 1: TOPICS / CATEGORY LIST
           ========================================= -->
      <section id="page-topics" class="page-view active">
        
        <div class="search-box-wrap">
          <div class="search-input-wrap">
            <svg fill="none" viewBox="0 0 24 24" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" /></svg>
            <input type="text" id="topicSearchInput" class="search-input" placeholder="Search topics, standards, fittings..." oninput="filterTopics()" />
          </div>
        </div>

        <div class="topic-list" id="topicListContainer">
          
          <!-- Flanges Item (Primary Entry to Page 2) -->
          <div class="topic-card" onclick="navigateToPage('page-detail')">
            <div class="topic-thumb">
              {cat_svgs['flanges']}
            </div>
            <div class="topic-info">
              <div class="topic-title-row">
                <span class="topic-title" data-i18n="flanges">Flanges</span>
                <span class="topic-badge">ASME B16.5</span>
              </div>
              <p class="topic-desc" data-i18n="flangesDesc">Weld Neck, Slip-On, Blind, Threaded, Lap Joint & Socket Weld • Class 150–2500</p>
            </div>
            <div class="topic-chevron">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </div>
          </div>

          <!-- Pipes Item -->
          <div class="topic-card" onclick="navigateToPage('page-detail')">
            <div class="topic-thumb">
              {cat_svgs['pipes']}
            </div>
            <div class="topic-info">
              <div class="topic-title-row">
                <span class="topic-title" data-i18n="pipes">Pipes & Tubing</span>
                <span class="topic-badge">ASME B36.10M</span>
              </div>
              <p class="topic-desc" data-i18n="pipesDesc">Carbon and stainless steel wall thicknesses, schedules 5 through XXS, weights & capacity.</p>
            </div>
            <div class="topic-chevron">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </div>
          </div>

          <!-- Butt Weld Fittings -->
          <div class="topic-card" onclick="navigateToPage('page-detail')">
            <div class="topic-thumb">
              {cat_svgs['fittings_bw']}
            </div>
            <div class="topic-info">
              <div class="topic-title-row">
                <span class="topic-title" data-i18n="fittingsBw">Butt Weld Fittings</span>
                <span class="topic-badge">ASME B16.9</span>
              </div>
              <p class="topic-desc" data-i18n="fittingsBwDesc">45°/90°/180° Elbows, Equal & Reducing Tees, Concentric/Eccentric Reducers, Caps.</p>
            </div>
            <div class="topic-chevron">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </div>
          </div>

          <!-- Forged Fittings & Olets -->
          <div class="topic-card" onclick="navigateToPage('page-detail')">
            <div class="topic-thumb">
              {cat_svgs['fittings_sw']}
            </div>
            <div class="topic-info">
              <div class="topic-title-row">
                <span class="topic-title" data-i18n="fittingsForged">Forged Fittings & O-lets</span>
                <span class="topic-badge">ASME B16.11</span>
              </div>
              <p class="topic-desc" data-i18n="fittingsForgedDesc">Class 2000–9000 Socket-Welding & Threaded Couplings, Weldolets, Sockolets, Nipples.</p>
            </div>
            <div class="topic-chevron">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </div>
          </div>

          <!-- Valves -->
          <div class="topic-card" onclick="navigateToPage('page-detail')">
            <div class="topic-thumb">
              {cat_svgs['valves']}
            </div>
            <div class="topic-info">
              <div class="topic-title-row">
                <span class="topic-title" data-i18n="valves">Industrial Valves</span>
                <span class="topic-badge">ASME B16.10</span>
              </div>
              <p class="topic-desc" data-i18n="valvesDesc">Gate, Globe, Ball, Check & Butterfly valves, face-to-face dimensions and end connections.</p>
            </div>
            <div class="topic-chevron">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </div>
          </div>

          <!-- Gaskets & Fasteners -->
          <div class="topic-card" onclick="navigateToPage('page-detail')">
            <div class="topic-thumb">
              {cat_svgs['gaskets']}
            </div>
            <div class="topic-info">
              <div class="topic-title-row">
                <span class="topic-title" data-i18n="gaskets">Gaskets & Bolting</span>
                <span class="topic-badge">ASME B16.20</span>
              </div>
              <p class="topic-desc" data-i18n="gasketsDesc">Spiral Wound, Ring Type Joint (RTJ), Flat Gaskets, Stud Bolt lengths and wrench sizes.</p>
            </div>
            <div class="topic-chevron">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </div>
          </div>

          <!-- Line Blanks -->
          <div class="topic-card" onclick="navigateToPage('page-detail')">
            <div class="topic-thumb">
              {cat_svgs['blanks']}
            </div>
            <div class="topic-info">
              <div class="topic-title-row">
                <span class="topic-title" data-i18n="lineBlanks">Line Blanks & Spacers</span>
                <span class="topic-badge">ASME B16.48</span>
              </div>
              <p class="topic-desc" data-i18n="lineBlanksDesc">Figure-8 spectacle blinds, paddle blanks, paddle spacers for isolation testing.</p>
            </div>
            <div class="topic-chevron">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </div>
          </div>

          <!-- Calculations -->
          <div class="topic-card" onclick="navigateToPage('page-detail')">
            <div class="topic-thumb" style="background:#132238; border-color:#38bdf8;">
              <svg viewBox="0 0 100 100" style="padding:6px;">
                <path d="M20 70 L50 30 L80 70 Z" fill="none" stroke="#38bdf8" stroke-width="5" />
                <line x1="10" y1="80" x2="90" y2="80" stroke="#94a3b8" stroke-width="4" stroke-dasharray="4,4" />
                <circle cx="50" cy="50" r="10" fill="#fbbf24" />
              </svg>
            </div>
            <div class="topic-info">
              <div class="topic-title-row">
                <span class="topic-title" data-i18n="calculators">Calculators & Spans</span>
                <span class="topic-badge">Engine</span>
              </div>
              <p class="topic-desc" data-i18n="calculatorsDesc">Pipe safe span deflection & stress, moment of inertia, thermal expansion loop sizing.</p>
            </div>
            <div class="topic-chevron">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </div>
          </div>

        </div>

      </section>

      <!-- =========================================
           PAGE 2: FLANGES COMPONENT DETAIL VIEW
           ========================================= -->
      <section id="page-detail" class="page-view">
        
        <!-- 3D-Peeking Drawing Carousel -->
        <div class="carousel-section">
          
          <div class="carousel-container" id="carouselContainer">
            
            <!-- Left Navigation Arrow -->
            <button class="carousel-arrow prev-btn" onclick="prevSlide()" title="Previous Flange">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" /></svg>
            </button>

            <!-- Slide Track with 7 Flanges -->
            <div class="carousel-track" id="carouselTrack">
              {"".join([f'''
              <div class="carousel-slide" data-index="{i}" data-id="{s['id']}" data-facing="{s['facing']}" onclick="handleSlideClick({i})">
                <div class="slide-svg-box">
                  {s['svg']}
                </div>
              </div>
              ''' for i, s in enumerate(flange_slides)])}
            </div>

            <!-- Right Navigation Arrow -->
            <button class="carousel-arrow next-btn" onclick="nextSlide()" title="Next Flange">
              <svg fill="none" viewBox="0 0 24 24" stroke-width="2.5"><path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" /></svg>
            </button>

          </div>

          <!-- Carousel Metadata & Pagination -->
          <div class="carousel-meta">
            <div class="flange-type-pill">
              <span id="carouselFlangeTitle" class="flange-type-name">Weld Neck (WN)</span>
              <span id="carouselFlangeBadge" class="flange-standard-badge">ASME B16.5</span>
            </div>
            
            <div class="carousel-dots" id="carouselDots">
              {"".join([f'<span class="dot {"active" if i==0 else ""}" onclick="goToSlide({i})"></span>' for i in range(len(flange_slides))])}
            </div>

            <span class="swipe-instruction" data-i18n="swipeHint">Swipe drawing left / right to change flange type</span>
          </div>

        </div>

        <!-- Options / Selection Controls Below Carousel -->
        <div class="controls-panel">
          
          <!-- Size Selector (NPS) -->
          <div class="control-group">
            <div class="control-label">
              <span data-i18n="size">Nominal Pipe Size (NPS)</span>
              <span id="selectedNpsLabel" class="highlight">4" (DN 100)</span>
            </div>
            <div class="chip-scroller" id="npsChipScroller">
              <!-- Rendered via JS -->
            </div>
          </div>

          <!-- Rating Selector -->
          <div class="control-group">
            <div class="control-label">
              <span data-i18n="rating">Pressure Class</span>
              <span id="selectedRatingLabel" class="highlight">Class 150</span>
            </div>
            <div class="segmented-control">
              <button class="seg-btn active" data-rating="150" onclick="selectRating('150')">Class 150</button>
              <button class="seg-btn" data-rating="300" onclick="selectRating('300')">Class 300</button>
              <button class="seg-btn" data-rating="600" onclick="selectRating('600')">Class 600</button>
            </div>
          </div>

          <!-- Facing Selector -->
          <div class="control-group">
            <div class="control-label">
              <span data-i18n="facing">Flange Facing</span>
              <span id="selectedFacingLabel" class="highlight">Raised Face (RF)</span>
            </div>
            <div class="segmented-control">
              <button class="seg-btn active" id="facingRfBtn" onclick="selectFacing('RF')">Raised Face (RF)</button>
              <button class="seg-btn" id="facingRtjBtn" onclick="selectFacing('RTJ')">Ring Joint (RTJ)</button>
            </div>
          </div>

        </div>

        <!-- Specifications & Values Table -->
        <div class="specs-section">
          
          <div class="specs-header">
            <span class="specs-title" data-i18n="specifications">Dimensions & Specifications</span>
            <button class="copy-btn" onclick="copySpecsToClipboard()">
              <svg width="14" height="14" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" /></svg>
              <span id="copyBtnLabel" data-i18n="copySpecs">Copy Values</span>
            </button>
          </div>

          <div class="specs-card">
            
            <div class="spec-row">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimOd">Outside Diameter</span>
                <span class="spec-subkey">O</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_od">229 mm</div>
                <div class="spec-val-secondary" id="sub_od">9.00 in</div>
              </div>
            </div>

            <div class="spec-row">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimThk">Flange Thickness</span>
                <span class="spec-subkey">C / tf</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_thk">22.4 mm</div>
                <div class="spec-val-secondary" id="sub_thk">0.88 in</div>
              </div>
            </div>

            <div class="spec-row" id="row_hub_x">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimHubX">Hub Diameter at Base</span>
                <span class="spec-subkey">X</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_hub_x">135 mm</div>
                <div class="spec-val-secondary" id="sub_hub_x">5.31 in</div>
              </div>
            </div>

            <div class="spec-row" id="row_hub_y">
              <div class="spec-label-group">
                <span class="spec-key" id="label_hub_y" data-i18n="dimLenY">Length Through Hub</span>
                <span class="spec-subkey">Y</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_hub_y">75 mm</div>
                <div class="spec-val-secondary" id="sub_hub_y">2.95 in</div>
              </div>
            </div>

            <div class="spec-row" id="row_bore">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimBore">Bore Diameter</span>
                <span class="spec-subkey">B</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_bore">116.8 mm</div>
                <div class="spec-val-secondary" id="sub_bore">4.60 in</div>
              </div>
            </div>

            <div class="spec-row" id="row_rf_dia">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimRfDia">Raised Face Diameter</span>
                <span class="spec-subkey">R</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_rf_dia">157.2 mm</div>
                <div class="spec-val-secondary" id="sub_rf_dia">6.19 in</div>
              </div>
            </div>

            <div class="spec-row">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimPcd">Bolt Circle Diameter</span>
                <span class="spec-subkey">W / PCD</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_pcd">190.5 mm</div>
                <div class="spec-val-secondary" id="sub_pcd">7.50 in</div>
              </div>
            </div>

            <div class="spec-row">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimBolts">Number of Bolts</span>
                <span class="spec-subkey">n</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_bolts">8</div>
                <div class="spec-val-secondary" id="sub_bolts">holes</div>
              </div>
            </div>

            <div class="spec-row">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimBoltSize">Bolt Diameter</span>
                <span class="spec-subkey">UNC</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_bolt_sz">5/8 in</div>
                <div class="spec-val-secondary" id="sub_bolt_sz">M16 eq.</div>
              </div>
            </div>

            <div class="spec-row">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimHoleDia">Bolt Hole Diameter</span>
                <span class="spec-subkey">d</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_hole_sz">3/4 in</div>
                <div class="spec-val-secondary" id="sub_hole_sz">19 mm</div>
              </div>
            </div>

            <div class="spec-row">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimStudLen">Stud Bolt Length</span>
                <span class="spec-subkey">L</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_stud_l">90 mm</div>
                <div class="spec-val-secondary" id="sub_stud_l">3.50 in</div>
              </div>
            </div>

            <div class="spec-row">
              <div class="spec-label-group">
                <span class="spec-key" data-i18n="dimWeight">Approx. Weight</span>
                <span class="spec-subkey">Mass</span>
              </div>
              <div class="spec-value-group">
                <div class="spec-val-primary" id="val_weight">6.8 kg</div>
                <div class="spec-val-secondary" id="sub_weight">15.0 lb</div>
              </div>
            </div>

          </div>

        </div>

      </section>

    </main>

    <!-- Settings Overlay Drawer -->
    <div id="settingsOverlay" class="settings-overlay" onclick="handleOverlayClick(event)">
      <div class="settings-drawer" id="settingsDrawer">
        
        <div class="settings-drawer-header">
          <h2 data-i18n="settings">Settings</h2>
          <button class="close-btn" onclick="closeSettings()">&times;</button>
        </div>

        <!-- Unit System -->
        <div class="setting-item">
          <span class="setting-item-label" data-i18n="unitSystem">Unit System</span>
          <div class="segmented-control">
            <button class="seg-btn active" id="unitMetricBtn" onclick="setUnitSystem('metric')">Metric (mm, kg)</button>
            <button class="seg-btn" id="unitImperialBtn" onclick="setUnitSystem('imperial')">Imperial (in, lb)</button>
          </div>
        </div>

        <!-- Language (i18n) -->
        <div class="setting-item">
          <span class="setting-item-label" data-i18n="language">Language</span>
          <div class="segmented-control">
            <button class="seg-btn active" id="langEnBtn" onclick="setLanguage('en')">English</button>
            <button class="seg-btn" id="langTrBtn" onclick="setLanguage('tr')">Türkçe</button>
          </div>
          <span class="setting-item-desc" data-i18n="langDesc">Instant live UI translation across all labels.</span>
        </div>

        <!-- Appearance / Theme -->
        <div class="setting-item">
          <span class="setting-item-label" data-i18n="appearance">Appearance</span>
          <div class="segmented-control">
            <button class="seg-btn active" id="themeLightBtn" onclick="setTheme('light')">Light</button>
            <button class="seg-btn" id="themeDarkBtn" onclick="setTheme('dark')">Dark</button>
          </div>
        </div>

        <!-- About / Database Stats -->
        <div class="about-info-box">
          <strong>Pipedata Reference v2.0</strong><br />
          Built from 956 standardized ASME/API/DIN catalogs with 22,525 engineering entries.<br />
          Zero clutter, 100% offline responsive architecture.
        </div>

      </div>
    </div>

  </div>

  <script>
    // =========================================
    // I18N DICTIONARY SYSTEM (English / Turkish)
    // =========================================
    const i18n = {{
      en: {{
        appTitle: "Pipedata",
        appSubtitle: "Piping Engineering Reference",
        topics: "Topics",
        backToTopics: "Topics",
        searchPlaceholder: "Search topics, standards, fittings...",
        flanges: "Flanges",
        flangesDesc: "Weld Neck, Slip-On, Blind, Threaded, Lap Joint & Socket Weld • Class 150–2500",
        pipes: "Pipes & Tubing",
        pipesDesc: "Carbon and stainless steel wall thicknesses, schedules 5 through XXS, weights & capacity.",
        fittingsBw: "Butt Weld Fittings",
        fittingsBwDesc: "45°/90°/180° Elbows, Equal & Reducing Tees, Concentric/Eccentric Reducers, Caps.",
        fittingsForged: "Forged Fittings & O-lets",
        fittingsForgedDesc: "Class 2000–9000 Socket-Welding & Threaded Couplings, Weldolets, Sockolets, Nipples.",
        valves: "Industrial Valves",
        valvesDesc: "Gate, Globe, Ball, Check & Butterfly valves, face-to-face dimensions and end connections.",
        gaskets: "Gaskets & Bolting",
        gasketsDesc: "Spiral Wound, Ring Type Joint (RTJ), Flat Gaskets, Stud Bolt lengths and wrench sizes.",
        lineBlanks: "Line Blanks & Spacers",
        lineBlanksDesc: "Figure-8 spectacle blinds, paddle blanks, paddle spacers for isolation testing.",
        calculators: "Calculators & Spans",
        calculatorsDesc: "Pipe safe span deflection & stress, moment of inertia, thermal expansion loop sizing.",
        size: "Nominal Pipe Size (NPS)",
        rating: "Pressure Class",
        facing: "Flange Facing",
        specifications: "Dimensions & Specifications",
        copySpecs: "Copy Values",
        copied: "Copied!",
        swipeHint: "Swipe drawing left / right to change flange type",
        settings: "Settings",
        unitSystem: "Unit System",
        language: "Language",
        langDesc: "Instant live UI translation across all labels.",
        appearance: "Appearance",
        dimOd: "Outside Diameter",
        dimThk: "Flange Thickness",
        dimHubX: "Hub Diameter at Base",
        dimLenY: "Length Through Hub",
        dimBore: "Bore Diameter",
        dimRfDia: "Raised Face Diameter",
        dimPcd: "Bolt Circle Diameter",
        dimBolts: "Number of Bolts",
        dimBoltSize: "Bolt Diameter",
        dimHoleDia: "Bolt Hole Diameter",
        dimStudLen: "Stud Bolt Length",
        dimWeight: "Approx. Weight"
      }},
      tr: {{
        appTitle: "Pipedata",
        appSubtitle: "Borulama Mühendislik Referansı",
        topics: "Konular",
        backToTopics: "Konular",
        searchPlaceholder: "Konu, standart veya ek parça ara...",
        flanges: "Flanşlar",
        flangesDesc: "Kaynak Boyunlu, Düz Geçme, Kör, Dişli, Serbest ve Soketli Flanşlar • Class 150–2500",
        pipes: "Borular & Hat Donatısı",
        pipesDesc: "Karbon ve paslanmaz çelik et kalınlıkları, Sch 5 - XXS serileri, ağırlık ve hacim.",
        fittingsBw: "Alın Kaynaklı Ek Parçalar",
        fittingsBwDesc: "45°/90°/180° Dirsekler, Eşit ve İneçli Teler, Redüksiyonlar, Kepler.",
        fittingsForged: "Dövme Çelik Ek Parçalar",
        fittingsForgedDesc: "Class 2000–9000 Soketli & Dişli Manşonlar, Weldolet, Sockolet, Nipeller.",
        valves: "Endüstriyel Vanalar",
        valvesDesc: "Sürgülü, Vana, Küresel, Çekvalf ve Kelebek vana yapısal boyları ve flanş bağlantıları.",
        gaskets: "Contalar ve Saplamalar",
        gasketsDesc: "Spiral Sarımlı, RTJ Metalik, Düz Contalar, Saplama boyları ve anahtar ölçüleri.",
        lineBlanks: "Hat Körlemeleri & Halkalar",
        lineBlanksDesc: "Gözlük Körleme, Raket Körleme ve Mesafe Halkaları (ASME B16.48).",
        calculators: "Mühendislik Hesaplayıcıları",
        calculatorsDesc: "Boru güvenli açıklık sehimi ve gerilmesi, atalet momenti, genleşme döngüleri.",
        size: "Anma Boru Çapı (NPS)",
        rating: "Basınç Sınıfı",
        facing: "Flanş Yüzeyi",
        specifications: "Ölçüler ve Mühendislik Değerleri",
        copySpecs: "Ölçüleri Kopyala",
        copied: "Kopyalandı!",
        swipeHint: "Flanş tipini değiştirmek için çizimi sağa/sola kaydırın",
        settings: "Ayarlar",
        unitSystem: "Birim Sistemi",
        language: "Dil",
        langDesc: "Tüm etiketlerde anlık canlı arayüz çevirisi.",
        appearance: "Görünüm",
        dimOd: "Dış Çap",
        dimThk: "Flanş Kalınlığı",
        dimHubX: "Göbek Taban Çapı",
        dimLenY: "Toplam Göbek Boyu",
        dimBore: "İç Delik Çapı",
        dimRfDia: "Faturalı Yüzey Çapı",
        dimPcd: "Cıvata Dairesi Çapı",
        dimBolts: "Cıvata Adedi",
        dimBoltSize: "Cıvata Çapı",
        dimHoleDia: "Cıvata Delik Çapı",
        dimStudLen: "Saplama Boyu",
        dimWeight: "Yaklaşık Ağırlık"
      }}
    }};

    // Current State
    let currentLang = "en";
    let currentUnit = "metric"; // 'metric' (mm, kg) or 'imperial' (in, lb)
    let currentTheme = "light";
    let activeSlideIndex = 0;
    let selectedRating = "150";
    let selectedNps = '4';
    let selectedFacing = "RF";

    // Injected Data
    const FLANGE_SLIDES = {json.dumps([{k: v for k, v in s.items() if k != "svg"} for s in flange_slides])};
    const FLANGE_DATA = {json.dumps(flange_data)};

    // Navigation Between Page 1 and Page 2
    function navigateToPage(pageId) {{
      document.querySelectorAll(".page-view").forEach(el => el.classList.remove("active"));
      document.getElementById(pageId).classList.add("active");
      
      const backBtn = document.getElementById("headerBackBtn");
      const branding = document.getElementById("headerBranding");
      
      if (pageId === "page-detail") {{
        backBtn.style.display = "flex";
        branding.querySelector("h1").textContent = i18n[currentLang].flanges;
        branding.querySelector("p").textContent = "ASME B16.5";
        updateCarousel();
        renderSpecifications();
      }} else {{
        backBtn.style.display = "none";
        branding.querySelector("h1").textContent = i18n[currentLang].appTitle;
        branding.querySelector("p").textContent = i18n[currentLang].appSubtitle;
      }}
      document.querySelector(".app-content").scrollTop = 0;
    }}

    // =========================================
    // 3D-PEEKING CAROUSEL ENGINE & SWIPE
    // =========================================
    function updateCarousel() {{
      const slides = document.querySelectorAll(".carousel-slide");
      const total = slides.length;
      
      slides.forEach((slide, i) => {{
        slide.className = "carousel-slide";
        if (i === activeSlideIndex) {{
          slide.classList.add("active");
        }} else if (i === (activeSlideIndex - 1 + total) % total) {{
          slide.classList.add("prev");
        }} else if (i === (activeSlideIndex + 1) % total) {{
          slide.classList.add("next");
        }} else if (i < activeSlideIndex) {{
          slide.classList.add("hidden-left");
        }} else {{
          slide.classList.add("hidden-right");
        }}
      }});

      // Update dots
      document.querySelectorAll(".dot").forEach((dot, i) => {{
        dot.classList.toggle("active", i === activeSlideIndex);
      }});

      // Update Flange metadata label
      const activeInfo = FLANGE_SLIDES[activeSlideIndex];
      document.getElementById("carouselFlangeTitle").textContent = `${{activeSlideIndex + 1}} of ${{total}}: ${{activeInfo.title}}`;
      document.getElementById("carouselFlangeBadge").textContent = activeInfo.badge;
      
      // Update Facing button state if applicable
      selectedFacing = activeInfo.facing;
      document.getElementById("facingRfBtn").classList.toggle("active", selectedFacing === "RF");
      document.getElementById("facingRtjBtn").classList.toggle("active", selectedFacing === "RTJ");
      document.getElementById("selectedFacingLabel").textContent = selectedFacing === "RF" ? "Raised Face (RF)" : "Ring Joint (RTJ)";
      
      renderSpecifications();
    }}

    function nextSlide() {{
      activeSlideIndex = (activeSlideIndex + 1) % FLANGE_SLIDES.length;
      updateCarousel();
    }}

    function prevSlide() {{
      activeSlideIndex = (activeSlideIndex - 1 + FLANGE_SLIDES.length) % FLANGE_SLIDES.length;
      updateCarousel();
    }}

    function goToSlide(index) {{
      activeSlideIndex = index;
      updateCarousel();
    }}

    function handleSlideClick(index) {{
      if (index !== activeSlideIndex) {{
        goToSlide(index);
      }}
    }}

    // Pointer-based Touch & Mouse Swipe Gestures
    const container = document.getElementById("carouselContainer");
    let startX = 0;
    let currentX = 0;
    let isDragging = false;

    container.addEventListener("pointerdown", (e) => {{
      startX = e.clientX;
      currentX = startX;
      isDragging = true;
      container.setPointerCapture(e.pointerId);
    }});

    container.addEventListener("pointermove", (e) => {{
      if (!isDragging) return;
      currentX = e.clientX;
    }});

    container.addEventListener("pointerup", (e) => {{
      if (!isDragging) return;
      isDragging = false;
      const diff = currentX - startX;
      if (diff > 45) {{
        prevSlide();
      }} else if (diff < -45) {{
        nextSlide();
      }}
    }});

    container.addEventListener("pointercancel", () => {{
      isDragging = false;
    }});

    // Keyboard Arrow Controls
    window.addEventListener("keydown", (e) => {{
      if (document.getElementById("page-detail").classList.contains("active")) {{
        if (e.key === "ArrowLeft") prevSlide();
        if (e.key === "ArrowRight") nextSlide();
      }}
    }});

    // =========================================
    // OPTIONS & SPECIFICATIONS LOGIC
    // =========================================
    function initNpsChips() {{
      const sizes = Object.keys(FLANGE_DATA["150"]);
      const scroller = document.getElementById("npsChipScroller");
      scroller.innerHTML = "";
      
      sizes.forEach(sz => {{
        const btn = document.createElement("button");
        btn.className = `chip-pill ${{sz === selectedNps ? "active" : ""}}`;
        btn.textContent = `${{sz}}"`;
        btn.onclick = () => selectNps(sz);
        scroller.appendChild(btn);
      }});
    }}

    function selectNps(nps) {{
      selectedNps = nps;
      document.querySelectorAll(".chip-pill").forEach(el => {{
        el.classList.toggle("active", el.textContent === `${{nps}}"`);
      }});
      const entry = (FLANGE_DATA[selectedRating] || {{}})[selectedNps] || {{}};
      document.getElementById("selectedNpsLabel").textContent = `${{nps}}" (DN ${{entry.dn || ""}})`;
      renderSpecifications();
    }}

    function selectRating(r) {{
      selectedRating = r;
      document.querySelectorAll("[data-rating]").forEach(el => {{
        el.classList.toggle("active", el.getAttribute("data-rating") === r);
      }});
      document.getElementById("selectedRatingLabel").textContent = `Class ${{r}}`;
      renderSpecifications();
    }}

    function selectFacing(f) {{
      selectedFacing = f;
      document.getElementById("facingRfBtn").classList.toggle("active", f === "RF");
      document.getElementById("facingRtjBtn").classList.toggle("active", f === "RTJ");
      document.getElementById("selectedFacingLabel").textContent = f === "RF" ? "Raised Face (RF)" : "Ring Joint (RTJ)";
      
      // Auto-jump to RTJ slide if selected
      if (f === "RTJ" && FLANGE_SLIDES[activeSlideIndex].facing !== "RTJ") {{
        const rtjIdx = FLANGE_SLIDES.findIndex(s => s.facing === "RTJ");
        if (rtjIdx !== -1) goToSlide(rtjIdx);
      }} else if (f === "RF" && FLANGE_SLIDES[activeSlideIndex].facing === "RTJ") {{
        goToSlide(0); // return to WN RF
      }}
    }}

    function renderSpecifications() {{
      const ratingMap = FLANGE_DATA[selectedRating] || {{}};
      const data = ratingMap[selectedNps] || {{}};
      const activeFlg = FLANGE_SLIDES[activeSlideIndex];
      const isMetric = currentUnit === "metric";

      function formatDim(mmVal) {{
        if (mmVal === null || mmVal === undefined) return {{ prim: "—", sec: "—" }};
        const inVal = (mmVal / 25.4);
        if (isMetric) {{
          return {{
            prim: `${{mmVal}} mm`,
            sec: `${{inVal.toFixed(2)}} in`
          }};
        }} else {{
          return {{
            prim: `${{inVal.toFixed(2)}} in`,
            sec: `${{mmVal}} mm`
          }};
        }}
      }}

      function formatWeight(kgVal) {{
        if (kgVal === null || kgVal === undefined) return {{ prim: "—", sec: "—" }};
        const lbVal = (kgVal * 2.20462);
        if (isMetric) {{
          return {{
            prim: `${{kgVal}} kg`,
            sec: `${{lbVal.toFixed(1)}} lb`
          }};
        }} else {{
          return {{
            prim: `${{lbVal.toFixed(1)}} lb`,
            sec: `${{kgVal}} kg`
          }};
        }}
      }}

      // Outside Diameter
      const odFmt = formatDim(data.od);
      document.getElementById("val_od").textContent = odFmt.prim;
      document.getElementById("sub_od").textContent = odFmt.sec;

      // Thickness
      const thkVal = (activeFlg.id === "bld_rf") ? (data.thk || data.bld_kg) : data.thk;
      const thkFmt = formatDim(thkVal);
      document.getElementById("val_thk").textContent = thkFmt.prim;
      document.getElementById("sub_thk").textContent = thkFmt.sec;

      // Hub Diameter X (hide for Blind)
      const hubRow = document.getElementById("row_hub_x");
      if (activeFlg.id === "bld_rf") {{
        hubRow.style.display = "none";
      }} else {{
        hubRow.style.display = "flex";
        const hubFmt = formatDim(data.hub_x);
        document.getElementById("val_hub_x").textContent = hubFmt.prim;
        document.getElementById("sub_hub_x").textContent = hubFmt.sec;
      }}

      // Length Through Hub Y
      const lenYRow = document.getElementById("row_hub_y");
      if (activeFlg.id === "bld_rf") {{
        lenYRow.style.display = "none";
      }} else {{
        lenYRow.style.display = "flex";
        let yVal = data.wn_y;
        if (activeFlg.id === "so_rf") yVal = data.thd_thk || data.wn_y;
        if (activeFlg.id === "thd_rf") yVal = data.thd_thk;
        const lenFmt = formatDim(yVal);
        document.getElementById("val_hub_y").textContent = lenFmt.prim;
        document.getElementById("sub_hub_y").textContent = lenFmt.sec;
      }}

      // Bore (hide for Blind)
      const boreRow = document.getElementById("row_bore");
      if (activeFlg.id === "bld_rf") {{
        boreRow.style.display = "none";
      }} else {{
        boreRow.style.display = "flex";
        const boreFmt = formatDim(data.so_b);
        document.getElementById("val_bore").textContent = boreFmt.prim;
        document.getElementById("sub_bore").textContent = boreFmt.sec;
      }}

      // Raised Face Dia
      const rfFmt = formatDim(data.rf_dia);
      document.getElementById("val_rf_dia").textContent = rfFmt.prim;
      document.getElementById("sub_rf_dia").textContent = rfFmt.sec;

      // PCD
      const pcdFmt = formatDim(data.pcd);
      document.getElementById("val_pcd").textContent = pcdFmt.prim;
      document.getElementById("sub_pcd").textContent = pcdFmt.sec;

      // Bolts
      document.getElementById("val_bolts").textContent = data.bolts || "—";
      document.getElementById("sub_bolts").textContent = (data.bolts ? "holes" : "—");

      // Bolt Size
      document.getElementById("val_bolt_sz").textContent = data.bolt_sz ? `${{data.bolt_sz}} in` : "—";
      document.getElementById("sub_bolt_sz").textContent = "UNC Heavy Hex";

      // Hole Diameter
      document.getElementById("val_hole_sz").textContent = data.hole_sz ? `${{data.hole_sz}} in` : "—";
      document.getElementById("sub_hole_sz").textContent = data.hole_sz ? "Standard clearance" : "—";

      // Stud Length
      const studFmt = formatDim(data.stud_l);
      document.getElementById("val_stud_l").textContent = studFmt.prim;
      document.getElementById("sub_stud_l").textContent = studFmt.sec;

      // Weight (active flange specific)
      let wtVal = data.wn_kg;
      if (activeFlg.id === "so_rf") wtVal = data.so_kg;
      if (activeFlg.id === "bld_rf") wtVal = data.bld_kg;
      const wtFmt = formatWeight(wtVal);
      document.getElementById("val_weight").textContent = wtFmt.prim;
      document.getElementById("sub_weight").textContent = wtFmt.sec;
    }}

    // =========================================
    // SEARCH & FILTERING (PAGE 1)
    // =========================================
    function filterTopics() {{
      const query = document.getElementById("topicSearchInput").value.toLowerCase().trim();
      const cards = document.querySelectorAll(".topic-card");
      cards.forEach(card => {{
        const text = card.textContent.toLowerCase();
        card.style.display = text.includes(query) ? "flex" : "none";
      }});
    }}

    // =========================================
    // UNIT, THEME & I18N MANAGEMENT
    // =========================================
    function toggleQuickUnit() {{
      setUnitSystem(currentUnit === "metric" ? "imperial" : "metric");
    }}

    function setUnitSystem(unit) {{
      currentUnit = unit;
      document.getElementById("quickUnitBtn").textContent = unit === "metric" ? "Metric (mm)" : "Imperial (in)";
      document.getElementById("unitMetricBtn").classList.toggle("active", unit === "metric");
      document.getElementById("unitImperialBtn").classList.toggle("active", unit === "imperial");
      renderSpecifications();
    }}

    function setTheme(theme) {{
      currentTheme = theme;
      document.body.setAttribute("data-theme", theme);
      document.getElementById("themeLightBtn").classList.toggle("active", theme === "light");
      document.getElementById("themeDarkBtn").classList.toggle("active", theme === "dark");
    }}

    function setLanguage(lang) {{
      currentLang = lang;
      document.getElementById("langEnBtn").classList.toggle("active", lang === "en");
      document.getElementById("langTrBtn").classList.toggle("active", lang === "tr");
      
      const t = i18n[lang];
      document.getElementById("backBtnText").textContent = t.backToTopics;
      document.getElementById("topicSearchInput").placeholder = t.searchPlaceholder;
      document.getElementById("copyBtnLabel").textContent = t.copySpecs;
      
      // Update all elements with data-i18n attribute
      document.querySelectorAll("[data-i18n]").forEach(el => {{
        const key = el.getAttribute("data-i18n");
        if (t[key]) {{
          el.textContent = t[key];
        }}
      }});
      
      // Update header branding based on active page
      const isDetail = document.getElementById("page-detail").classList.contains("active");
      const branding = document.getElementById("headerBranding");
      if (isDetail) {{
        branding.querySelector("h1").textContent = t.flanges;
      }} else {{
        branding.querySelector("h1").textContent = t.appTitle;
        branding.querySelector("p").textContent = t.appSubtitle;
      }}
      
      updateCarousel();
    }}

    // =========================================
    // SETTINGS OVERLAY LOGIC
    // =========================================
    function openSettings() {{
      const overlay = document.getElementById("settingsOverlay");
      overlay.classList.add("open");
    }}

    function closeSettings() {{
      const overlay = document.getElementById("settingsOverlay");
      overlay.classList.remove("open");
    }}

    function handleOverlayClick(e) {{
      if (e.target.id === "settingsOverlay") {{
        closeSettings();
      }}
    }}

    // Copy to clipboard
    function copySpecsToClipboard() {{
      const activeFlg = FLANGE_SLIDES[activeSlideIndex];
      const data = (FLANGE_DATA[selectedRating] || {{}})[selectedNps] || {{}};
      const text = [
        `Pipedata Specification`,
        `Component: ${{activeFlg.title}} (${{selectedFacing}})`,
        `Standard: ASME B16.5`,
        `Size: ${{selectedNps}}" (DN ${{data.dn}}) | Rating: Class ${{selectedRating}}`,
        `OD: ${{document.getElementById("val_od").textContent}}`,
        `Thickness: ${{document.getElementById("val_thk").textContent}}`,
        `PCD: ${{document.getElementById("val_pcd").textContent}}`,
        `Bolts: ${{data.bolts}} x ${{data.bolt_sz}}"`,
        `Stud Length: ${{document.getElementById("val_stud_l").textContent}}`,
        `Approx. Weight: ${{document.getElementById("val_weight").textContent}}`
      ].join("\\n");

      navigator.clipboard.writeText(text).then(() => {{
        const label = document.getElementById("copyBtnLabel");
        const prev = label.textContent;
        label.textContent = i18n[currentLang].copied;
        setTimeout(() => {{ label.textContent = prev; }}, 1800);
      }});
    }}

    // Initialize on page load
    window.addEventListener("DOMContentLoaded", () => {{
      initNpsChips();
      updateCarousel();
    }});
  </script>
</body>
</html>
"""

output_path = os.path.join(BASE_DIR, "pipedata_clean_ui.html")
with open(output_path, "w", encoding="utf-8") as f:
    f.write(html_content)

download_path = "/storage/emulated/0/Download/pipedata_clean_ui.html"
try:
    shutil.copyfile(output_path, download_path)
    print(f"[OK] Successfully wrote: {output_path}")
    print(f"[OK] Synced to Download: {download_path}")
except Exception as e:
    print(f"[WARN] Download sync error: {e}")
