#!/usr/bin/env python3
"""Verify canonical inch dimensions and all derived metric geometry (not an ASME license)."""
import csv
import math
from collections import Counter
from decimal import Decimal
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ASSET = ROOT / "app/src/main/assets/pipe_schedules.csv"
with ASSET.open(newline="", encoding="utf-8") as f:
    reader = csv.DictReader(f)
    assert reader.fieldnames == [
        "standard", "nps", "dn", "outer_diameter_in", "schedule", "wall_thickness_in"
    ], "The source of truth must be original inch values"
    rows = list(reader)
EXPECTED_COUNTS = {
    "1": 14,
    "2": 14,
    "3": 14,
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
    "1/8": 12,
    "1/4": 12,
    "3/8": 12,
    "1/2": 14,
    "3/4": 14,
    "1 1/4": 14,
    "1 1/2": 14,
    "2 1/2": 14,
    "3 1/2": 11
}
assert len(rows) == 402, ("Missing or extra catalog rows", len(rows))
assert dict(Counter(r["nps"] for r in rows)) == EXPECTED_COUNTS
source = {}
for row in rows:
    key = (row["standard"], row["nps"], row["schedule"])
    assert key not in source, ("Duplicate standard/NPS/SCH", key)
    assert row["standard"] in ("B36.10", "B36.19"), key
    assert (not row["schedule"].endswith("S") or row["schedule"] in ("XS","XXS")
            or row["standard"] == "B36.19"), key
    od = Decimal(row["outer_diameter_in"])
    wall = Decimal(row["wall_thickness_in"])
    assert Decimal(0) < wall * 2 < od, key
    assert 0 < int(row["dn"]) <= 1200, key
    assert (wall * Decimal("1000")) % 1 == 0, ("Wall not in native thousandths", key)
    # Decimal avoids all interim rounding. The conversion 1 inch = 25.4 mm is exact.
    metric_od, metric_wall = od * Decimal("25.4"), wall * Decimal("25.4")
    metric_id = (od - wall * 2) * Decimal("25.4")
    assert metric_id == metric_od - metric_wall * 2
    mm_area = Decimal(str(math.pi)) / 4 * (metric_od**2 - metric_id**2)
    assert mm_area > 0
    source[key] = (od,wall)
# Independent 2022 B36.10 published imperial benchmarks, not regenerated
# from the app's former rounded SI table.
BENCHMARKS = {
    ("B36.10","1/8","10"): ("0.405","0.049"),
    ("B36.10","1/8","30"): ("0.405","0.057"),
    ("B36.10","1/8","40"): ("0.405","0.068"),
    ("B36.10","1/8","80"): ("0.405","0.095"),
    ("B36.10","1/8","160"): ("0.405","0.124"),
    ("B36.10","1/8","XXS"): ("0.405","0.190"),
    ("B36.10","1/4","160"): ("0.540","0.145"),
    ("B36.10","1/4","XXS"): ("0.540","0.238"),
    ("B36.10","3/8","160"): ("0.675","0.158"),
    ("B36.10","3/8","XXS"): ("0.675","0.252"),
    ("B36.10","2","40"): ("2.375","0.154"),
    ("B36.10","6","40"): ("6.625","0.280"),
    ("B36.10","12","40"): ("12.750","0.406"),
    ("B36.10","38","STD"): ("38.000","0.375"),
    ("B36.10","48","XS"): ("48.000","0.500"),
}
for key, expected in BENCHMARKS.items():
    assert source[key] == tuple(Decimal(v) for v in expected), (key,source.get(key))
# Native NPS OD checks apply to every schedule and both standard groups.
OD_IN = {
    "1": "1.315",
    "2": "2.375",
    "3": "3.500",
    "4": "4.500",
    "5": "5.563",
    "6": "6.625",
    "8": "8.625",
    "10": "10.750",
    "12": "12.750",
    "14": "14.000",
    "16": "16.000",
    "18": "18.000",
    "20": "20.000",
    "22": "22.000",
    "24": "24.000",
    "26": "26.000",
    "28": "28.000",
    "30": "30.000",
    "32": "32.000",
    "34": "34.000",
    "36": "36.000",
    "38": "38.000",
    "40": "40.000",
    "42": "42.000",
    "44": "44.000",
    "46": "46.000",
    "48": "48.000",
    "1/8": "0.405",
    "1/4": "0.540",
    "3/8": "0.675",
    "1/2": "0.840",
    "3/4": "1.050",
    "1 1/4": "1.660",
    "1 1/2": "1.900",
    "2 1/2": "2.875",
    "3 1/2": "4.000"
}
for row in rows:
    assert Decimal(row["outer_diameter_in"]) == Decimal(OD_IN[row["nps"]]), row
kotlin = (ROOT / "app/src/main/java/com/avenox/pipedata/PipeCatalog.kt").read_text()
assert "lines.forEachIndexed { lineNo, raw ->" in kotlin
assert "lines.firstOrNull()" not in kotlin, "Single-pass CSV reader only: a second traversal breaks at runtime"
assert "lines.drop(1)" not in kotlin
assert "val wallInches: Double" in kotlin
assert "val odMm: Double get() = odInches * 25.4" in kotlin
assert "val wallMm: Double get() = wallInches * 25.4" in kotlin
assert "val idMm: Double get() = idInches * 25.4" in kotlin
screen = (ROOT / "app/src/main/java/com/avenox/pipedata/MainActivity.kt").read_text()
assert "private fun compactDimension(value: Double, imperial: Boolean)" in screen
assert "compactDimension(t, imperial)" in screen
assert "compactDimension(inner, imperial)" in screen or "if (imperial) pipe.idInches else pipe.idMm, imperial" in screen
# Display rounding must not replace the canonical exact catalogue dimension.
assert "selectedDiameterMm ?: diameterInput" in screen
assert "catalog.rows.distinctBy { it.odInches }" in screen
print(f"PASS: {len(rows)} inch-native records, {len(EXPECTED_COUNTS)} NPS, exact decimal conversions")
