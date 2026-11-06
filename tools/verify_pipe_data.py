#!/usr/bin/env python3
"""Independent integrity and physics gate for the clean pipe-only app."""
import csv
import hashlib
from collections import Counter
import math
from decimal import Decimal, ROUND_HALF_EVEN
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
CSV = ROOT / "app/src/main/assets/pipe_schedules.csv"
ROWS = list(csv.DictReader(CSV.open(encoding="utf-8", newline="")))
assert len(ROWS) >= 390, f"Too few validated nominal records: {len(ROWS)}"
assert set(ROWS[0]) == {
    "standard", "nps", "dn", "outer_diameter_mm", "schedule", "wall_thickness_mm"
}
# Frozen, explicit scope of this app: 1/8 through 48 inch. The baseline is a
# regression lock of the reviewed application CSV, NOT an independent ASME license
# or proof that every published schedule is present. When changing data, separately
# verify the source, review the diff, then deliberately renew the baseline SHA.
EXPECTED_GIT_BLOB_SHA = "ac5e2907ec6d26620df280d8228d97c4a5b3a29c"
EXPECTED_NPS_COUNTS = {
    "1/8": 6,
    "1/4": 10,
    "3/8": 10,
    "1/2": 14,
    "3/4": 14,
    "1": 14,
    "1 1/4": 14,
    "1 1/2": 14,
    "2": 14,
    "2 1/2": 14,
    "3": 14,
    "3 1/2": 11,
    "4": 15,
    "5": 14,
    "6": 14,
    "8": 18,
    "10": 18,
    "12": 18,
    "14": 17,
    "16": 17,
    "18": 17,
    "20": 17,
    "22": 14,
    "24": 17,
    "26": 4,
    "28": 5,
    "30": 8,
    "32": 6,
    "34": 6,
    "36": 6,
    "38": 2,
    "40": 2,
    "42": 2,
    "44": 2,
    "46": 2,
    "48": 2,
}
EXPECTED_DN_OD = {
    "1/8": (6, Decimal("10.30")),
    "1/4": (8, Decimal("13.70")),
    "3/8": (10, Decimal("17.10")),
    "1/2": (15, Decimal("21.30")),
    "3/4": (20, Decimal("26.70")),
    "1": (25, Decimal("33.40")),
    "1 1/4": (32, Decimal("42.20")),
    "1 1/2": (40, Decimal("48.30")),
    "2": (50, Decimal("60.30")),
    "2 1/2": (65, Decimal("73.00")),
    "3": (80, Decimal("88.90")),
    "3 1/2": (90, Decimal("101.60")),
    "4": (100, Decimal("114.30")),
    "5": (125, Decimal("141.30")),
    "6": (150, Decimal("168.30")),
    "8": (200, Decimal("219.10")),
    "10": (250, Decimal("273.00")),
    "12": (300, Decimal("323.80")),
    "14": (350, Decimal("355.60")),
    "16": (400, Decimal("406.40")),
    "18": (450, Decimal("457.20")),
    "20": (500, Decimal("508.00")),
    "22": (550, Decimal("558.80")),
    "24": (600, Decimal("609.60")),
    "26": (650, Decimal("660.40")),
    "28": (700, Decimal("711.20")),
    "30": (750, Decimal("762.00")),
    "32": (800, Decimal("813.00")),
    "34": (850, Decimal("864.00")),
    "36": (900, Decimal("914.00")),
    "38": (950, Decimal("965.00")),
    "40": (1000, Decimal("1016.00")),
    "42": (1050, Decimal("1067.00")),
    "44": (1100, Decimal("1118.00")),
    "46": (1150, Decimal("1168.00")),
    "48": (1200, Decimal("1219.00")),
}

raw_csv = CSV.read_bytes()
actual_blob_sha = hashlib.sha1(
    b"blob " + str(len(raw_csv)).encode("ascii") + b"\0" + raw_csv
).hexdigest()
assert actual_blob_sha == EXPECTED_GIT_BLOB_SHA, (
    "Pipe CSV changed without independent review and baseline approval",
    actual_blob_sha, EXPECTED_GIT_BLOB_SHA
)
assert len(ROWS) == sum(EXPECTED_NPS_COUNTS.values()), "Unexpected total pipe rows"
actual_counts = Counter(r["nps"] for r in ROWS)
assert dict(actual_counts) == EXPECTED_NPS_COUNTS, (
    "NPS coverage changed or size missing", dict(actual_counts)
)
for r in ROWS:
    expected_dn, expected_od = EXPECTED_DN_OD[r["nps"]]
    assert int(r["dn"]) == expected_dn, ("DN mismatch", r)
    assert Decimal(r["outer_diameter_mm"]) == expected_od, ("OD mismatch", r)
assert {r["schedule"] for r in ROWS if r["nps"] == "38"} == {"STD", "XS"}
for nps in ("38", "40", "42", "44", "46", "48"):
    assert {r["schedule"] for r in ROWS if r["nps"] == nps} == {"STD", "XS"}, nps
print(f"PASS: locked full catalog, {len(ROWS)} rows, {len(EXPECTED_NPS_COUNTS)} NPS sizes")

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
check("B36.10", "44", "STD", 1118, 9.52)
check("B36.10", "44", "XS", 1118, 12.70)
check("B36.10", "46", "STD", 1168, 9.52)
check("B36.10", "46", "XS", 1168, 12.70)
check("B36.10", "48", "STD", 1219, 9.52)
check("B36.10", "48", "XS", 1219, 12.70)
# NPS 38 / DN950 exists in B36.10-2022, including STD and XS.
check("B36.10", "38", "STD", 965, 9.52)
check("B36.10", "38", "XS", 965, 12.70)
assert {r["schedule"] for r in ROWS if r["nps"] == "38"} == {"STD", "XS"}

assert abs(1219.2 / 25.4 - 48) < 0.000001
pipe_catalog = (ROOT / "app/src/main/java/com/avenox/pipedata/PipeCatalog.kt").read_text()
assert "val odInches: Double" in pipe_catalog
for exact in ('"6" -> 6.625', '"8" -> 8.625', '"10" -> 10.750', '"12" -> 12.750'):
    assert exact in pipe_catalog, exact



# ASME B36.10/B36.19 2022: native OD in inches; SI OD roundings differ
# by DN: to 0.1 mm through DN750, whole mm from DN800 onward.
large = [r for r in ROWS if r["nps"].isdigit() and int(r["nps"]) >= 14]
assert large, "No large-pipe dimensions"
for row in large:
    n = int(row["nps"])
    expected_od = (Decimal(n) * Decimal("25.4"))
    places = Decimal("1") if n >= 32 else Decimal("0.1")
    expected_si = expected_od.quantize(places, rounding=ROUND_HALF_EVEN)
    assert Decimal(row["outer_diameter_mm"]) == expected_si, row
# Independent exact-inch benchmarks for the imperial presentation path.
assert "val idInches: Double get() = odInches - 2.0 * (wallMm / 25.4)" in pipe_catalog
for size in ("44", "46", "48"):
    found = {(r["standard"], r["schedule"]) for r in large if r["nps"] == size}
    assert ("B36.10", "STD") in found and ("B36.10", "XS") in found, size

# PASS 2: independent, exact-decimal standard SI wall rounding on every record.
# Published 2022 tables use ties-to-even: 0.375in -> 9.52mm,
# 0.875in -> 22.22mm, 1.375in -> 34.92mm.
by_key = {(r["standard"], r["nps"], r["schedule"]): r for r in ROWS}
for row in ROWS:
    wall_mm = Decimal(row["wall_thickness_mm"])
    thousandth_in = (wall_mm / Decimal("25.4")).quantize(
        Decimal("0.001"), rounding=ROUND_HALF_EVEN
    )
    reference_si = (thousandth_in * Decimal("25.4")).quantize(
        Decimal("0.01"), rounding=ROUND_HALF_EVEN
    )
    assert wall_mm == reference_si, ("ASME 2022 SI wall conversion", row, reference_si)
    # Check the two independent algebraic expressions for the same bore.
    od, t = Decimal(row["outer_diameter_mm"]), wall_mm
    assert od - 2 * t == (od / 2 - t) * 2, row
# Full alias relationships for existing nominal records.
for (std, nps, sch), row in by_key.items():
    if std != "B36.10": continue
    nominal = Decimal(nps.split()[-1]) if nps.isdigit() else None
    if sch in ("STD", "XS"):
        expected = (Decimal("9.52") if sch == "STD" and nominal and nominal > 10
                    else Decimal("12.70") if sch == "XS" and nominal and nominal > 8
                    else None)
        if expected is not None:
            assert Decimal(row["wall_thickness_mm"]) == expected, row
        alias = "40" if sch == "STD" and nominal and nominal <= 10 else (
            "80" if sch == "XS" and nominal and nominal <= 8 else None)
        if alias and (std, nps, alias) in by_key:
            assert (Decimal(row["wall_thickness_mm"]) ==
                    Decimal(by_key[(std, nps, alias)]["wall_thickness_mm"])), row
# Published 2022 benchmark cases, including the three user screenshots.
check("B36.10", "8", "40", 219.1, 8.18)
check("B36.10", "10", "40", 273.0, 9.27)
check("B36.19", "12", "40S", 323.8, 9.52)
check("B36.10", "12", "STD", 323.8, 9.52)
check("B36.10", "12", "40", 323.8, 10.31)
check("B36.10", "1 1/2", "XXS", 48.3, 10.16)
check("B36.10", "8", "XXS", 219.1, 22.22)
check("B36.10", "22", "140", 558.8, 47.62)
for size, si in (("32", 813), ("34", 864), ("36", 914),
                 ("42", 1067), ("44", 1118), ("46", 1168), ("48", 1219)):
    for item in ROWS:
        if item["nps"] == size:
            assert Decimal(item["outer_diameter_mm"]) == Decimal(si), item

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

# Procedural metallic bore and large technical dimension markers.
dial = screen.split("private fun PipeDial(", 1)[1].split("private fun PipeDimensionIcon(", 1)[0]
assert "detectDragGestures" in dial and "currentOnStep(direction)" in dial
assert "Canvas(Modifier.fillMaxSize())" in dial
assert "rotate(wheelRotation * 0.25f, pivot = c)" in dial
assert "Brush.sweepGradient(" in dial and "Brush.radialGradient(" in dial
assert "Brush.verticalGradient(" in dial
assert "val metalWidth = outer - bore" in dial
assert "Color(0xFF80F889)" in dial and "drawLine(innerGreen" in dial
assert "drawLine(Gold, innerTip, outerTip" in dial
assert "drawLine(Gold, Offset(x1, y), Offset(x2, y)" in dial
assert 'Text(ui("İç Çap (ID)")' in dial and 'Text(ui("Dış Çap (OD)")' in dial
assert "Et Kalınlığı (t)" in dial
assert ".clickable { onEdit(\"ID\") }" in dial
assert '.clickable { onEdit("NPS"); if (tutorialStep == 2) onOdTapped() }' in dial
assert "dialSide = minOf(maxWidth, dialCap)" in dial
assert "widthDp <= 340 && fontScale >= 1.2f -> 256.dp" in dial
for forbidden in ("painterResource(", "centeredDialArtwork", "graphicsLayer(", "drawPath(pointer"):
    assert forbidden not in dial, f"Old texture or tick pointer survived: {forbidden}"
# 36 NPS sizes: explicit previous/next buttons plus complete dialog, not a long chip strip.
assert 'Icons.Default.ChevronLeft' in screen
assert 'Icons.Default.ChevronRight' in screen
assert 'if (npsMenuExpanded) ScrollableOptionDialog(' in screen
assert 'Triple(ui("Tablo"), Icons.Default.MenuBook, 2)' in screen
assert 'if (index == 2) ui("Tablo / Standart") else label' in screen
assert "val schedules = remember(activeNps)" in screen
assert "row.nps.startsWith(input" in screen
assert "PipeDial(nps, step, pipe, imperial," in screen
# Both calculator and table NPS selectors must reach their final item on small screens.
assert "private fun ScrollableOptionDialog(" in screen
assert "rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)" in screen
assert "LazyColumn(" in screen
assert "if (npsMenuExpanded) ScrollableOptionDialog(" in screen
assert "if (options.size > 20)" in screen

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
# Real SCH chips and OD selection visibly pulse during their tutorial steps.
assert 'bringIntoViewRequester(schTarget)' in screen
assert 'if (tutorialStep == 1) TutorialPulseFrame(Modifier.matchParentSize())' in screen
assert 'if (tutorialStep == 2) TutorialPulseFrame(Modifier.matchParentSize())' in screen
assert 'if (tutorialStep == 1 && sch == schedules.first())' in screen
assert 'TutorialTouchMarker()' in screen
assert 'private fun TutorialRingGesture(' in screen
assert 'animateFloat(' in screen and 'drawArc(' in screen
locale = (ROOT / "app/src/main/java/com/avenox/pipedata/UiLanguage.kt").read_text()
for phrase in ('"İlk kullanım"', '"Atla"', '"İleri"', '"Öğreticiyi tekrar göster"'):
    assert phrase in locale

# Language names are written in each language, independently of current locale.
assert '"tr" to "Türkçe"' in screen
assert '"en" to "English"' in screen
assert '"ru" to "Русский"' in screen
assert '"system" to ui("Sistem dili")' in screen
# Large translated labels stay bounded inside the pipe and remain tappable.
assert '.widthIn(max = dialSide * 0.68f)' in screen
assert '.background(boreDark.copy(alpha = 0.90f))' in screen
assert 'contentDescription = uiInnerDiameterDescription' in screen
assert 'ui("Önceki boru çapı")' in screen

print(f"PASS: {len(ROWS)} valid, nonduplicated pipe dimensions; benchmarks, "
      "derived geometry/mass, procedural circular NPS dial, and no legacy components")
