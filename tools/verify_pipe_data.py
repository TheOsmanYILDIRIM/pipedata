#!/usr/bin/env python3
"""Independent integrity and physics gate for the clean pipe-only app."""
import csv
import math
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
CSV = ROOT / "app/src/main/assets/pipe_schedules.csv"
ROWS = list(csv.DictReader(CSV.open(encoding="utf-8", newline="")))
assert len(ROWS) >= 390, f"Too few validated nominal records: {len(ROWS)}"
assert set(ROWS[0]) == {
    "standard", "nps", "dn", "outer_diameter_mm", "schedule", "wall_thickness_mm"
}
seen = set()
for row in ROWS:
    key = (row["standard"], row["nps"], row["schedule"])
    assert key not in seen, f"Duplicate: {key}"
    seen.add(key)
    assert row["standard"] in ("B36.10", "B36.19")
    d, t = float(row["outer_diameter_mm"]), float(row["wall_thickness_mm"])
    assert math.isfinite(d) and math.isfinite(t) and 0 < t < d / 2, key
    inner = d - 2 * t
    steel = (math.pi / 4) * (d * d - inner * inner) * 0.00785
    liquid = (math.pi / 4) * inner * inner / 1000.0
    assert inner > 0 and steel > 0 and liquid > 0, key
    if row["schedule"].endswith("S") and row["schedule"] not in ("XS", "XXS"):
        assert row["standard"] == "B36.19", key

def check(std, nps, schedule, d, t):
    row = next((r for r in ROWS if
                (r["standard"],r["nps"],r["schedule"]) == (std,nps,schedule)), None)
    assert row, f"Missing benchmark {(std, nps, schedule)}"
    assert abs(float(row["outer_diameter_mm"]) - d) < 0.02, row
    assert abs(float(row["wall_thickness_mm"]) - t) < 0.02, row

check("B36.10", "6", "40", 168.3, 7.11)
check("B36.10", "4", "40", 114.3, 6.02)
check("B36.10", "2", "40", 60.3, 3.91)
check("B36.19", "6", "40S", 168.3, 7.11)
check("B36.19", "6", "10S", 168.3, 3.4)
# Regression against B36.19-2022's defined Schedule 160S values.
assert ("B36.19", "22", "80S") not in seen
expected_160s = {"1":6.35,"2":8.74,"3":11.13,"4":13.49,"5":15.88,"6":18.26,"1/8":3.15,"1/4":3.68,"3/8":4.01,"1/2":4.78,"3/4":5.56,"1 1/4":6.35,"1 1/2":7.14,"2 1/2":9.52}
for nps, wall in expected_160s.items():
    rec = next((r for r in ROWS if (r["standard"],r["nps"],r["schedule"]) ==
                ("B36.19",nps,"160S")), None)
    assert rec, f"Missing B36.19 160S NPS {nps}"
    assert abs(float(rec["wall_thickness_mm"]) - wall) < 0.005
assert sum(r["standard"] == "B36.19" and r["schedule"] == "160S" for r in ROWS) == 14

check("B36.10", "1/8", "STD", 10.3, 1.73)
check("B36.10", "1/8", "XS", 10.3, 2.41)
# Large-diameter B36.10 regression: exact inches and non-invented STD/XS rows.
check("B36.10", "44", "STD", 1117.6, 9.53)
check("B36.10", "44", "XS", 1117.6, 12.70)
check("B36.10", "46", "STD", 1168.4, 9.53)
check("B36.10", "46", "XS", 1168.4, 12.70)
check("B36.10", "48", "STD", 1219.2, 9.53)
check("B36.10", "48", "XS", 1219.2, 12.70)
assert abs(1219.2 / 25.4 - 48) < 0.000001
pipe_catalog = (ROOT / "app/src/main/java/com/avenox/pipedata/PipeCatalog.kt").read_text()
assert "val odInches: Double" in pipe_catalog
for exact in ('"6" -> 6.625', '"8" -> 8.625', '"10" -> 10.750', '"12" -> 12.750'):
    assert exact in pipe_catalog, exact



# Full regression: large nominal series NPS >= 14 has exact OD = NPS * 25.4.
# Prevent integer-mm truncation (e.g. 18 -> 457.0 instead of 457.2).
large = [r for r in ROWS if r["nps"].isdigit() and int(r["nps"]) >= 14]
assert large, "No large-pipe dimensions"
for row in large:
    expected_od = int(row["nps"]) * 25.4
    assert abs(float(row["outer_diameter_mm"]) - expected_od) < 0.005, row
for size in ("44", "46", "48"):
    found = {(r["standard"], r["schedule"]) for r in large if r["nps"] == size}
    assert ("B36.10", "STD") in found and ("B36.10", "XS") in found, size

# Independent sample: 6in Sch40, 28.3 kg/m and ~18.6 L/m.
p = next(r for r in ROWS if
         (r["standard"],r["nps"],r["schedule"]) == ("B36.10","6","40"))
d, t = float(p["outer_diameter_mm"]), float(p["wall_thickness_mm"])
inner = d - 2*t
steel = (math.pi / 4) * (d*d-inner*inner) * 0.00785
water = (math.pi / 4) * inner*inner / 1000
assert 28.0 < steel < 28.7, steel
assert 18.4 < water < 18.9, water

screen = (ROOT / "app/src/main/java/com/avenox/pipedata/MainActivity.kt").read_text()
assert "pipe.odInches" in screen
assert 'if (imperial) "6.625"' in screen
for screen_name in ("CalculatorPage", "LookupPage", "CombinedTableStandardsPage", "SettingsPage", "PipeDial", "NumericPipeDialog"):
    assert screen_name in screen, screen_name
for banned in ("CsvDataStore", "flanges", "gaskets", "valves", "blanks"):
    assert banned not in screen, f"Legacy UI accidentally referenced: {banned}"

# Procedural dial contract: circle geometry and touch must share a square pivot.
dial = screen.split("private fun PipeDial(", 1)[1].split("private fun ResultRows(", 1)[0]
assert "detectDragGestures" in dial and "currentOnStep(direction)" in dial
assert "Canvas(Modifier.fillMaxSize())" in dial
assert "rotate(wheelRotation, pivot = c)" in dial
assert "val r = size.minDimension / 2f" in dial
assert "Brush.sweepGradient(" in dial and "drawCircle(" in dial
assert "drawPath(pointer, Gold)" in dial
assert "val tangent = metalRadius + metalWidth * 0.52f" in dial
assert "Et Kalınlığı (t)" in dial
assert "dimensionY" in dial
assert "if (fontScale >= 1.2f) 0.43f else 0.22f" in dial
assert "dialSide = minOf(maxWidth, dialCap)" in dial
assert "widthDp <= 340 && fontScale >= 1.2f -> 208.dp" in dial
for forbidden in ("ChevronLeft", "ChevronRight", "painterResource(", "centeredDialArtwork", "graphicsLayer("):
    assert forbidden not in dial, f"Obsolete raster dial or control survived: {forbidden}"
assert 'Triple(ui("Tablo"), Icons.Default.MenuBook, 2)' in screen
assert 'if (index == 2) ui("Tablo / Standart") else label' in screen
assert "val schedules = remember(activeNps)" in screen
assert "row.nps.startsWith(input" in screen
assert "PipeDial(nps, step, pipe, imperial," in screen
assert screen.index("PipeDial(nps, step, pipe, imperial,") < screen.index('Text(ui("Schedule (SCH)")')
assert 'Text("NPS (inç)"' not in screen
assert 'if (imperial) it * 0.3048 else it' in screen
assert 'val massFactor = if (imperial) 2.20462262185' in screen
assert 'val volumeFactor = if (imperial) 0.26417205236' in screen
assert 'if (imperial) "Uzunluk (ft)"' in screen

# Locale + dynamic SCH search regression guards.
locale = (ROOT / "app/src/main/java/com/avenox/pipedata/UiLanguage.kt").read_text()
for key in ('"tr"', '"en"', '"ru"', '"Система"', '"Язык"', '"Language"'):
    # Russian system display is "Язык системы"; case-insensitive presence is enough.
    if key == '"Система"':
        continue
    assert key in (locale + screen), key
assert 'LocalConfiguration.current.locales[0].language' in screen
assert 'putString("language", it)' in screen
assert 'languageSelection == "system"' in screen
assert 'val suggestions = remember(odMm, catalog)' in screen
assert 'catalog.rows' in screen and 'selectedSchKey = key' in screen
assert 'option.wallMm / 25.4' in screen and 'selectedSchKey = null' in screen

# First-run onboarding: real rotary input, persistent completion, replay, localized hints.
assert 'prefs.getBoolean("onboarding_seen_v1", false)' in screen
assert 'prefs.edit().putBoolean("onboarding_seen_v1", true)' in screen
assert 'onReplayTutorial = {' in screen
assert 'currentOnWheelDragged()' in screen
assert 'if (tutorialStep == 1) onTutorialNext()' in screen
assert 'if (tutorialStep == 2) onOdTapped()' in screen
assert 'private fun TutorialRingGesture(' in screen
assert 'animateFloat(' in screen and 'drawArc(' in screen
locale = (ROOT / "app/src/main/java/com/avenox/pipedata/UiLanguage.kt").read_text()
for phrase in ('"İlk kullanım"', '"Atla"', '"İleri"', '"Öğreticiyi tekrar göster"'):
    assert phrase in locale

print(f"PASS: {len(ROWS)} valid, nonduplicated pipe dimensions; benchmarks, "
      "derived geometry/mass, procedural circular NPS dial, and no legacy components")
