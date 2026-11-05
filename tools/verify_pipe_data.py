#!/usr/bin/env python3
"""Independent integrity and physics gate for the clean pipe-only app."""
import csv
import math
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
CSV = ROOT / "app/src/main/assets/pipe_schedules.csv"
ROWS = list(csv.DictReader(CSV.open(encoding="utf-8", newline="")))
assert len(ROWS) >= 300, f"Too few validated nominal records: {len(ROWS)}"
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
check("B36.10", "1/8", "STD", 10.3, 1.73)
check("B36.10", "1/8", "XS", 10.3, 2.41)

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
for screen_name in ("CalculatorPage", "LookupPage", "TablePage", "StandardsPage", "SettingsPage", "PipeDial"):
    assert screen_name in screen, screen_name
for banned in ("CsvDataStore", "flanges", "gaskets", "valves", "blanks"):
    assert banned not in screen, f"Legacy UI accidentally referenced: {banned}"

print(f"PASS: {len(ROWS)} valid, nonduplicated pipe dimensions; benchmarks, "
      "derived geometry/mass, UI sections, and no legacy components")
