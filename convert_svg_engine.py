#!/usr/bin/env python3
"""
Pipedata AutoCAD Standard SVG Converter Engine (convert_svg_engine.py)
Generates clean, semantic, theme-ready SVGs according to ASME Y14.5M & ISO 129-1 standards.
Updates svg_manifest.json as each SVG is crafted.
"""

import os
import json
import re

COMMON_DEFS = """
  <defs>
    <!-- ASME/ANSI 31 Standard 45° Metal Section Hatching -->
    <pattern id="metalHatch" width="10" height="10" patternTransform="rotate(45 0 0)" patternUnits="userSpaceOnUse">
      <line x1="0" y1="0" x2="0" y2="10" stroke="currentColor" stroke-width="1.2" opacity="0.4" />
    </pattern>

    <!-- AutoCAD Standard Closed Filled Arrowheads (3:1 Aspect Ratio, ASME Y14.5M) -->
    <marker id="arrowStart" viewBox="0 0 12 12" refX="2" refY="6" markerWidth="7" markerHeight="7" orient="auto">
      <path d="M 12 3 L 2 6 L 12 9 Z" fill="currentColor" />
    </marker>
    <marker id="arrowEnd" viewBox="0 0 12 12" refX="10" refY="6" markerWidth="7" markerHeight="7" orient="auto">
      <path d="M 0 3 L 10 6 L 0 9 Z" fill="currentColor" />
    </marker>

    <!-- Inward Arrowheads for tight spaces / thickness dimensions -->
    <marker id="arrowInwardLeft" viewBox="0 0 12 12" refX="2" refY="6" markerWidth="7" markerHeight="7" orient="auto">
      <path d="M 0 6 L 10 3 L 10 9 Z" fill="currentColor" />
    </marker>
    <marker id="arrowInwardRight" viewBox="0 0 12 12" refX="10" refY="6" markerWidth="7" markerHeight="7" orient="auto">
      <path d="M 12 6 L 2 3 L 2 9 Z" fill="currentColor" />
    </marker>
  </defs>

  <style>
    .pipedata-svg { color: #f8fafc; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; }
    .centerline { stroke: #38bdf8; stroke-width: 1.1; stroke-dasharray: 14, 4, 3, 4; opacity: 0.55; }
    .body-line { stroke: currentColor; stroke-width: 2.2; fill: none; stroke-linecap: round; stroke-linejoin: round; }
    .hatch-fill { fill: url(#metalHatch); stroke: currentColor; stroke-width: 2.0; stroke-linejoin: round; }
    .dim-line { stroke: currentColor; stroke-width: 1.2; }
    .ext-line { stroke: currentColor; stroke-width: 0.9; opacity: 0.5; }
    .dim-pill { fill: #1e293b; stroke: #334155; stroke-width: 1; rx: 4; }
    .dim-text { fill: #38bdf8; font-weight: 700; font-size: 15px; text-anchor: middle; dominant-baseline: central; font-family: 'JetBrains Mono', monospace; }
  </style>
"""

def make_cap_svg():
    """Generates modern Buttwelding Cap (ASME B16.9 - ftbw9.svg) matching Screenshot #1"""
    return f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 500" width="100%" height="100%" class="pipedata-svg">
{COMMON_DEFS}

  <!-- 1. CENTERLINE (ASME Y14.5M) -->
  <g class="layer-centerlines">
    <line x1="300" y1="100" x2="300" y2="400" class="centerline" />
  </g>

  <!-- 2. BODY GEOMETRY: Left Exterior View & Right Section Cutaway -->
  <g class="layer-body">
    <!-- Left Exterior Contour -->
    <path class="body-line"
          d="M 160 350
             L 160 250
             C 160 170, 230 140, 300 140
             L 300 350
             L 160 350
             Z" />
    <!-- Bottom Weld Bevel on Left -->
    <line x1="160" y1="350" x2="165" y2="344" class="body-line" stroke-width="1.5" />

    <!-- Right Section Cutaway with ANSI 31 Metal Hatching -->
    <path class="hatch-fill"
          d="M 300 140
             C 370 140, 440 170, 440 250
             L 440 350
             L 416 350
             L 416 250
             C 416 190, 360 162, 300 162
             Z" />

    <!-- Inner Cavity Boundary on Right -->
    <line x1="300" y1="350" x2="416" y2="350" class="body-line" />
  </g>

  <!-- 3. DIMENSIONS (AutoCAD ASME Y14.5M Standards) -->
  <g class="layer-dimensions">
    <!-- Height Dimension E / h [[5]] on Right -->
    <line x1="300" y1="140" x2="510" y2="140" class="ext-line" />
    <line x1="440" y1="350" x2="510" y2="350" class="ext-line" />
    <line x1="495" y1="140" x2="495" y2="350" class="dim-line" marker-start="url(#arrowStart)" marker-end="url(#arrowEnd)" />
    <rect x="455" y="233" width="80" height="24" class="dim-pill" />
    <text x="495" y="245" class="dim-text">[[5]]</text>

    <!-- Wall Thickness Dimension WT [[DB2_4]] at Bottom Right -->
    <line x1="416" y1="350" x2="416" y2="410" class="ext-line" />
    <line x1="440" y1="350" x2="440" y2="410" class="ext-line" />
    <!-- Inward facing arrows for narrow thickness -->
    <line x1="380" y1="400" x2="416" y2="400" class="dim-line" marker-end="url(#arrowEnd)" />
    <line x1="476" y1="400" x2="440" y2="400" class="dim-line" marker-end="url(#arrowEnd)" />
    <line x1="416" y1="400" x2="440" y2="400" class="dim-line" />
    <rect x="390" y="420" width="76" height="24" class="dim-pill" />
    <text x="428" y="432" class="dim-text">[[DB2_4]]</text>
  </g>
</svg>"""

def make_90_elbow_lr_svg():
    """Generates modern 90° Long Radius Elbow (ASME B16.9 - ftbw1.svg)"""
    return f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="100%" height="100%" class="pipedata-svg">
{COMMON_DEFS}

  <!-- 1. CENTERLINES -->
  <g class="layer-centerlines">
    <!-- Arc Centerline: Center at (150, 450), Radius R=225 -->
    <path d="M 150 225 A 225 225 0 0 1 375 450" class="centerline" fill="none" />
    <line x1="150" y1="180" x2="150" y2="470" class="centerline" />
    <line x1="130" y1="450" x2="420" y2="450" class="centerline" />
  </g>

  <!-- 2. 90 DEGREE ELBOW BODY -->
  <g class="layer-body">
    <!-- Outer Arc (R=300), Inner Arc (R=150) -->
    <path class="hatch-fill"
          d="M 150 150
             A 300 300 0 0 1 450 450
             L 450 375
             A 225 225 0 0 0 225 150
             Z" />
    
    <!-- Exterior Intrados & Extrados Outlines -->
    <path class="body-line"
          d="M 75 150
             A 300 300 0 0 1 450 525
             L 450 375
             A 150 150 0 0 0 150 375
             L 150 150
             Z" />

    <!-- Weld Ends -->
    <line x1="75" y1="150" x2="225" y2="150" class="body-line" />
    <line x1="450" y1="375" x2="450" y2="525" class="body-line" />
  </g>

  <!-- 3. DIMENSIONS (Center-to-End Dimension A [[5]]) -->
  <g class="layer-dimensions">
    <!-- Horizontal Center to End [[5]] -->
    <line x1="150" y1="450" x2="150" y2="550" class="ext-line" />
    <line x1="450" y1="450" x2="450" y2="550" class="ext-line" />
    <line x1="150" y1="535" x2="450" y2="535" class="dim-line" marker-start="url(#arrowStart)" marker-end="url(#arrowEnd)" />
    <rect x="260" y="523" width="80" height="24" class="dim-pill" />
    <text x="300" y="535" class="dim-text">[[5]]</text>

    <!-- Vertical Center to End [[5]] -->
    <line x1="150" y1="150" x2="50" y2="150" class="ext-line" />
    <line x1="150" y1="450" x2="50" y2="450" class="ext-line" />
    <line x1="65" y1="150" x2="65" y2="450" class="dim-line" marker-start="url(#arrowStart)" marker-end="url(#arrowEnd)" />
    <rect x="25" y="288" width="80" height="24" class="dim-pill" />
    <text x="65" y="300" class="dim-text">[[5]]</text>
  </g>
</svg>"""

def make_45_elbow_svg():
    """Generates modern 45° Elbow (ASME B16.9 - ftbw3.svg)"""
    return f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="100%" height="100%" class="pipedata-svg">
{COMMON_DEFS}

  <!-- 1. CENTERLINES -->
  <g class="layer-centerlines">
    <line x1="200" y1="500" x2="200" y2="250" class="centerline" />
    <line x1="200" y1="250" x2="420" y2="100" class="centerline" />
  </g>

  <!-- 2. 45 DEGREE ELBOW BODY -->
  <g class="layer-body">
    <!-- Torus section cut at 45 degrees -->
    <path class="body-line"
          d="M 130 500
             L 130 380
             C 130 280, 200 210, 310 135
             L 360 185
             C 280 240, 230 290, 230 380
             L 230 500
             Z" />
    <!-- Weld End Lines -->
    <line x1="130" y1="500" x2="230" y2="500" class="body-line" />
    <line x1="310" y1="135" x2="360" y2="185" class="body-line" />

    <!-- 45° Section Hatching on Right Half -->
    <path class="hatch-fill"
          d="M 230 500
             L 230 380
             C 230 290, 280 240, 360 185
             L 345 170
             C 260 230, 210 280, 210 380
             L 210 500
             Z" />
  </g>

  <!-- 3. DIMENSIONS: 45° Center to End [[5]], OD [[4]] -->
  <g class="layer-dimensions">
    <!-- Center to End Dimension B [[5]] -->
    <line x1="200" y1="500" x2="80" y2="500" class="ext-line" />
    <line x1="200" y1="250" x2="80" y2="250" class="ext-line" />
    <line x1="95" y1="250" x2="95" y2="500" class="dim-line" marker-start="url(#arrowStart)" marker-end="url(#arrowEnd)" />
    <rect x="55" y="363" width="80" height="24" class="dim-pill" />
    <text x="95" y="375" class="dim-text">[[5]]</text>

    <!-- Outer Diameter OD [[4]] at Bottom Weld Face -->
    <line x1="130" y1="500" x2="130" y2="550" class="ext-line" />
    <line x1="230" y1="500" x2="230" y2="550" class="ext-line" />
    <line x1="130" y1="540" x2="230" y2="540" class="dim-line" marker-start="url(#arrowStart)" marker-end="url(#arrowEnd)" />
    <rect x="140" y="528" width="80" height="24" class="dim-pill" />
    <text x="180" y="540" class="dim-text">[[4]]</text>
  </g>
</svg>"""

def make_equal_tee_svg():
    """Generates modern Equal Tee (ASME B16.9 - ftbw4.svg)"""
    return f"""<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="100%" height="100%" class="pipedata-svg">
{COMMON_DEFS}

  <!-- 1. CENTERLINES -->
  <g class="layer-centerlines">
    <!-- Run Centerline -->
    <line x1="80" y1="350" x2="520" y2="350" class="centerline" />
    <!-- Branch Centerline -->
    <line x1="300" y1="100" x2="300" y2="450" class="centerline" />
  </g>

  <!-- 2. TEE BODY GEOMETRY -->
  <g class="layer-body">
    <!-- Left Run + Branch + Right Run Outline with Smooth Crotch Radii -->
    <path class="body-line"
          d="M 120 420
             L 120 280
             L 230 280
             C 240 280, 245 260, 245 250
             L 245 150
             L 355 150
             L 355 250
             C 355 260, 360 280, 370 280
             L 480 280
             L 480 420
             L 120 420
             Z" />

    <!-- Internal Half-Section Hatching (Upper Right & Branch Cut) -->
    <path class="hatch-fill"
          d="M 300 150
             L 355 150
             L 355 250
             C 355 260, 360 280, 370 280
             L 480 280
             L 480 300
             L 390 300
             C 375 300, 335 285, 335 250
             L 335 150
             Z" />

    <!-- Weld End Faces -->
    <line x1="120" y1="280" x2="120" y2="420" class="body-line" />
    <line x1="480" y1="280" x2="480" y2="420" class="body-line" />
    <line x1="245" y1="150" x2="355" y2="150" class="body-line" />
  </g>

  <!-- 3. DIMENSIONS: Center to End Run C [[5]], Center to End Branch M [[5]] -->
  <g class="layer-dimensions">
    <!-- Run Center-to-End [[5]] -->
    <line x1="120" y1="420" x2="120" y2="490" class="ext-line" />
    <line x1="300" y1="420" x2="300" y2="490" class="ext-line" />
    <line x1="120" y1="480" x2="300" y2="480" class="dim-line" marker-start="url(#arrowStart)" marker-end="url(#arrowEnd)" />
    <rect x="170" y="468" width="80" height="24" class="dim-pill" />
    <text x="210" y="480" class="dim-text">[[5]]</text>

    <!-- Branch Center-to-End [[5]] -->
    <line x1="355" y1="150" x2="430" y2="150" class="ext-line" />
    <line x1="480" y1="350" x2="430" y2="350" class="ext-line" />
    <line x1="420" y1="150" x2="420" y2="350" class="dim-line" marker-start="url(#arrowStart)" marker-end="url(#arrowEnd)" />
    <rect x="380" y="238" width="80" height="24" class="dim-pill" />
    <text x="420" y="250" class="dim-text">[[5]]</text>
  </g>
</svg>"""

def run_conversions():
    base_dir = "/data/data/com.termux/files/home/projects/pipedata"
    svg_modern_dir = os.path.join(base_dir, "data", "svg_modern")
    manifest_path = os.path.join(base_dir, "svg_manifest.json")

    conversions = {
        "ftbw9.svg": ("Buttwelding Cap", make_cap_svg()),
        "ftbw1.svg": ("90° Long Radius Elbow", make_90_elbow_lr_svg()),
        "ftbw3.svg": ("45° Elbow", make_45_elbow_svg()),
        "ftbw4.svg": ("Equal Tee", make_equal_tee_svg())
    }

    print(f"Converting {len(conversions)} core Buttweld SVGs...")
    for fn, (name, svg_content) in conversions.items():
        out_p = os.path.join(svg_modern_dir, fn)
        with open(out_p, "w", encoding="utf-8") as f:
            f.write(svg_content)
        sz = len(svg_content)
        print(f"  [✓] {fn:<12}: {name:<30} -> {sz} bytes ({sz/1024:.1f} KB)")

    # Update svg_manifest.json
    with open(manifest_path, "r", encoding="utf-8") as f:
        manifest = json.load(f)

    for fn in conversions.keys():
        if fn in manifest["svg_catalog"]:
            manifest["svg_catalog"][fn]["status"] = "completed"
            manifest["svg_catalog"][fn]["modern_size_bytes"] = len(conversions[fn][1])

    completed = sum(1 for x in manifest["svg_catalog"].values() if x["status"] == "completed")
    manifest["metadata"]["completed_count"] = completed
    manifest["metadata"]["pending_count"] = len(manifest["svg_catalog"]) - completed

    with open(manifest_path, "w", encoding="utf-8") as f:
        json.dump(manifest, f, indent=2)

    print(f"\n[Manifest Updated] Completed: {completed} / {len(manifest['svg_catalog'])}")

if __name__ == "__main__":
    run_conversions()
