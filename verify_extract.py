#!/usr/bin/env python3
"""Cikarma dogrulama: app sorgulariyla ayni filtre, kritik hucreler dolu mu?
Sessiz fallback YOK: bos hucre = FAIL."""
import csv, os, sys, math

OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "app", "src", "main", "assets", "csv")

def rows(t):
    with open(os.path.join(OUT, t + ".csv"), encoding="utf-8") as f:
        return list(csv.DictReader(f))

FAILS = []
def check(name, cond, detail=""):
    print(("PASS" if cond else "FAIL"), name, detail)
    if not cond: FAILS.append(name)

def first(table, **kw):
    for r in rows(table):
        if all(r.get(k) == v for k, v in kw.items()): return r
    return None

# 1) Flans 2" 150#: OD=152, PCD=120.6 (B16.5 ground truth)
r = first("tbl_flanges_asme_b16_5", nps="2", rating="150")
check("flans-2-150-var", r is not None)
if r:
    check("flans-OD-152", r["outer_diameter_mm"] in ("152", "152.0", "152.4"), r["outer_diameter_mm"])
    check("flans-PCD-120.6", r["pcd_mm"] in ("120.6", "120.7"), r["pcd_mm"])
    check("flans-civata-4", r["num_bolts"] in ("4", "4.0"), r["num_bolts"])
r_wn = first("tbl_flanges_asme_b16_5", nps="2", rating="150", flange_type="Welding Neck")
check("flans-2-150-WN-var", r_wn is not None)
if r_wn:
    check("flans-WN-bore-52.5", r_wn["bore_diameter_mm"] == "52.5", r_wn["bore_diameter_mm"])
    check("flans-WN-hub-A-60.3", r_wn["hub_diameter_mm"] == "78.0", r_wn["hub_diameter_mm"])
r_threaded = first("tbl_flanges_asme_b16_5", nps="2", rating="150", flange_type="Threaded")
if r_threaded:
    check("flans-threaded-no-fake-weight", r_threaded["weight_kg"] == "", r_threaded["weight_kg"])


# 2) Boru 2" Sch40: OD=60.3
r = first("tbl_pipes", nps="2", schedule="40")
check("boru-2-40-var", r is not None)
if r: check("boru-OD-60.3", r["outer_diameter_mm"] in ("60.3", "60.325"), r["outer_diameter_mm"])

# 2b) Boru 14" Sch-STD, Sch-40, Sch-80 tam varlik ve deger dogrulugu
r_std = first("tbl_pipes", nps="14", schedule="STD")
r_40 = first("tbl_pipes", nps="14", schedule="40")
r_80 = first("tbl_pipes", nps="14", schedule="80")
check("boru-14-std-var", r_std is not None)
if r_std:
    check("pipe-14-STD-OD-imperial", r_std["outer_diameter_in"] in ("14", "14.0"), r_std["outer_diameter_in"])
    check("pipe-14-STD-WT-imperial", r_std["wall_thickness_in"] == "0.375", r_std["wall_thickness_in"])
    check("pipe-14-STD-ID-imperial", r_std["inner_diameter_in"] == "13.25", r_std["inner_diameter_in"])
    check("pipe-14-STD-empty-imperial", r_std["weight_empty_lb_ft"] == "54.62", r_std["weight_empty_lb_ft"])
    check("pipe-14-STD-waterfilled-imperial", r_std["weight_water_lb_ft"] == "114.34", r_std["weight_water_lb_ft"])
    check("pipe-14-STD-inertia-imperial", r_std["moment_of_inertia_in4"] == "372.57", r_std["moment_of_inertia_in4"])
    check("pipe-14-STD-imperial-source", r_std["imperial_source_file"] == "pipe_imperial_std.csv", r_std["imperial_source_file"])

if r_std:
    check("boru-14-std-wall-9.53", r_std["wall_thickness_mm"] in ("9.53", "9.525"), r_std["wall_thickness_mm"])
    check("boru-14-std-wt-81.33", r_std["weight_empty_kg_m"] in ("81.33", "81.28"), r_std["weight_empty_kg_m"])

check("boru-14-sch40-var", r_40 is not None)
if r_40:
    check("boru-14-sch40-wall-11.13", r_40["wall_thickness_mm"] in ("11.13", "11.125"), r_40["wall_thickness_mm"])

check("boru-14-sch80-var", r_80 is not None)
if r_80:
    check("boru-14-sch80-wall-19.05", r_80["wall_thickness_mm"] in ("19.05", "19.050"), r_80["wall_thickness_mm"])

# 3) Vana 2" 150#: F2F=178
r = first("tbl_valves_asme_b16_10", nps="2", rating="150")
check("vana-2-150-var", r is not None)
if r:
    check("vana-F2F-178", r["face_to_face_mm"] in ("178", "178.0"), r["face_to_face_mm"])
    check("vana-RTJ-F2F-191", r["face_to_face_rtj_mm"] in ("191", "191.0"), r["face_to_face_rtj_mm"])
    check("vana-BW-F2F-216", r["face_to_face_bw_mm"] in ("216", "216.0"), r["face_to_face_bw_mm"])

# 3b) Vana 14" 150# vs 1500# rating ayrimi (Rating parse bug testi)
r_v150 = first("tbl_valves_asme_b16_10", nps="14", rating="150", valve_type="Gate Valve")
r_v1500 = first("tbl_valves_asme_b16_10", nps="14", rating="1500", valve_type="Gate Valve")
check("vana-14-150-gate-var", r_v150 is not None)
check("vana-14-1500-gate-var", r_v1500 is not None)
if r_v150: check("vana-14-150-F2F-381", r_v150["face_to_face_mm"] in ("381", "381.0"), r_v150["face_to_face_mm"])
if r_v1500: check("vana-14-1500-F2F-1257", r_v1500["face_to_face_mm"] in ("1257", "1257.0"), r_v1500["face_to_face_mm"])

# 4) BW 45 dirsek 2": OD>0, C2E>0
B = rows("tbl_fittings_buttweld_b16_9")
r = next((x for x in B if x["nps"]=="2" and "45" in x["component_type"]), None)
check("bw-45-2-var", r is not None)
if r:
    check("bw-OD-dolu", float(r["outer_diameter_mm"] or 0) > 0, r["outer_diameter_mm"])
    check("bw-C2E-dolu", float(r["center_to_end_mm"] or 0) > 0, r["center_to_end_mm"])
    raw45 = first("tbl_fittings_buttweld_b16_9", nps="2", schedule="10", component_type="45° Elbow")
    check("bw-45-sch10-radius-76.2", raw45 is not None and raw45["radius_mm"] in ("76.2", "76.20"), str(raw45))

# 4b) BW 90 LR 14" Sch 40 ve Sch 80 ayrimi
bw_14_std = first("tbl_fittings_buttweld_b16_9", nps="14", schedule="STD", component_type="90° Long Radius Elbow")
bw_14_40 = first("tbl_fittings_buttweld_b16_9", nps="14", schedule="40", component_type="90° Long Radius Elbow")
bw_14_80 = first("tbl_fittings_buttweld_b16_9", nps="14", schedule="80", component_type="90° Long Radius Elbow")
check("bw-14-90lr-std-var", bw_14_std is not None)
check("bw-14-90lr-40-var", bw_14_40 is not None)
check("bw-14-90lr-80-var", bw_14_80 is not None)
if bw_14_std: check("bw-14-std-wt-68", bw_14_std["weight_kg"] in ("68", "68.0"), bw_14_std["weight_kg"])
if bw_14_40: check("bw-14-40-wt-79.96", bw_14_40["weight_kg"] in ("79.96", "80.0"), bw_14_40["weight_kg"])
if bw_14_80: check("bw-14-80-wt-132.16", bw_14_80["weight_kg"] in ("132.16", "132.2"), bw_14_80["weight_kg"])

# 4c) Source branch matrices: no guessed geometry, correct NPS pair.
r_red = first("tbl_fittings_buttweld_b16_9", nps="2", branch_nps="3/4",
              schedule="STD", component_type="Concentric Reducer")
r_tee = first("tbl_fittings_buttweld_b16_9", nps="2", branch_nps="3/4",
              schedule="STD", component_type="Reducing Tee")
check("reducer-2x3-4-source", r_red is not None)
check("reducer-2x3-4-length-76", r_red is not None and r_red["overall_length_height_mm"] in ("76.0", "76"), str(r_red))
check("reducer-2x3-4-weight-0.41", r_red is not None and r_red["weight_kg"] == "0.41", str(r_red))
check("tee-2x3-4-source", r_tee is not None)
check("tee-2x3-4-run-64", r_tee is not None and r_tee["center_to_end_mm"] in ("64.0", "64"), str(r_tee))
check("tee-2x3-4-branch-44", r_tee is not None and r_tee["branch_center_to_end_mm"] in ("44.0", "44"), str(r_tee))

# 5) SW 1" 3000: soket capi + derinlik dolu
S = rows("tbl_fittings_socketweld_b16_11")
r = next((x for x in S if x["nps"]=="1" and x["rating"]=="3000" and float(x["socket_diameter_mm"] or 0) > 0), None)
check("sw-1-3000-soket-dolu", r is not None, (r["socket_diameter_mm"], r["socket_depth_mm"], r["source_file"]) if r else "")

# 6) THR 1" 3000: agirlik kg
T = rows("tbl_fittings_threaded_b16_11")
r = next((x for x in T if x["nps"]=="1" and x["rating"]=="3000" and x["weight_kg"] not in ("","None")), None)
check("thr-1-3000-wt-dolu", r is not None, (r["weight_kg"], r["source_file"]) if r else "")

# 7) Conta 2" 150: OD/ID dolu
r = first("tbl_gaskets_and_rtj", nps="2", rating="150")
check("conta-2-150-var", r is not None)
if r: check("conta-OD-dolu", float(r["outer_diameter_mm"] or 0) > 0, r["outer_diameter_mm"])

# 7b) Source ring geometry is pitch/width, not fabricated gasket ID/OD.
ring = first("tbl_gaskets_and_rtj", ring_number="R-23")
check("RTJ-R23-source", ring is not None)
if ring:
    check("RTJ-R23-pitch-82.55", ring["pitch_diameter_mm"] == "82.55", ring["pitch_diameter_mm"])
    check("RTJ-R23-width-11.13", ring["ring_width_mm"] == "11.13", ring["ring_width_mm"])
    check("RTJ-R23-no-fake-OD", ring["outer_diameter_mm"] == "", ring["outer_diameter_mm"])

# 7c) RTJ flange numerical ring must resolve to a real R-prefixed ring asset.
flange_ring = first("tbl_flanges_asme_b16_5", nps="2", rating="150", flange_type="Welding Neck")
check("flange-2-150-ring-source", flange_ring is not None)
if flange_ring:
    source_ring = flange_ring["rtj_ring_number"].strip().replace("'", "").replace("-", "")
    canonical_ring = source_ring if source_ring.upper().startswith("R") else "R" + source_ring
    ring_ref = next((x for x in rows("tbl_gaskets_and_rtj")
                     if x["ring_number"].replace("-", "").upper() == canonical_ring.upper()), None)
    check("flange-RTJ-ring-resolves", ring_ref is not None, canonical_ring)

# 8) Blank 2" 150: OD/thk dolu
r = first("tbl_line_blanks_asme_b16_48", nps="2", rating="150")
check("blank-2-150-var", r is not None)
if r: check("blank-OD-dolu", float(r["outer_diameter_mm"] or 0) > 0, r["outer_diameter_mm"])

# 9) Kritik kolonlarda tamami-bos tablo yok
for t in ["tbl_flanges_asme_b16_5","tbl_pipes","tbl_fittings_buttweld_b16_9","tbl_valves_asme_b16_10"]:
    R = rows(t)
    check(t+"-bossa-degil", len(R) > 50, f"{len(R)} satir")

# 10) OD anomalisi: OD<3mm veya OD>3000mm satir sayisi (0 olmali)
bad = 0
for t in ["tbl_flanges_asme_b16_5","tbl_pipes"]:
    for x in rows(t):
        try: v = float(x["outer_diameter_mm"] or 0)
        except ValueError: v = 0
        if v != 0 and (v < 3 or v > 3000): bad += 1
check("OD-aralik-temiz", bad == 0, f"anormal={bad}")

# 11) INDEPENDENT standard and physical checks.
# Not an APK equality check: geometry and mass identities independently
# validate every populated pipe row, with tolerance for tabular rounding.
audit_count = 0
invalid = []
for p in rows("tbl_pipes"):
    def n(field):
        try: return float(p[field]) if p.get(field, "") not in ("", "None") else None
        except (TypeError, ValueError): return None
    od, t = n("outer_diameter_mm"), n("wall_thickness_mm")
    if od is None or t is None: continue  # No invented schedule or wall value.
    label = (p["nps"], p["schedule"])
    audit_count += 1
    if not (0 < t < od / 2):
        invalid.append((label, "impossible wall", od, t))
        continue
    inner = n("inner_diameter_mm")
    mass = n("weight_empty_kg_m")
    filled = n("weight_water_kg_m")
    inertia = n("moment_of_inertia_cm4")
    if None in (inner, mass, filled, inertia):
        invalid.append((label, "missing independently computable value"))
        continue
    expected_id = od - 2 * t
    expected_mass = 0.0246615 * t * (od - t)  # B36.10 plain-end mass
    expected_filled = mass + (math.pi / 4) * (inner / 1000) ** 2 * 1000
    expected_inertia = (math.pi / 64) * (od ** 4 - inner ** 4) / 10000
    limits = (
        ("ID", inner, expected_id, max(0.12, od * 0.0002)),
        ("steel kg/m", mass, expected_mass, max(0.12, expected_mass * 0.001)),
        ("water-filled kg/m", filled, expected_filled, max(0.65, expected_filled * 0.005)),
        ("I cm4", inertia, expected_inertia, max(0.12, expected_inertia * 0.008)),
    )
    for field, measured, expected, tolerance in limits:
        if abs(measured - expected) > tolerance:
            invalid.append((label, field, measured, round(expected, 4), tolerance))
check("independent-physics-pipes", not invalid,
      f"{audit_count} independently checked; {len(invalid)} physical contradictions: {invalid[:12]}")

p24 = first("tbl_pipes", nps="24", schedule="STD")
check("B36.10-24-STD-imperial-OD", p24 is not None and
      abs(float(p24["outer_diameter_in"])-24.0) < 0.001,
      p24["outer_diameter_in"] if p24 else "missing")
# Ground-truth anchors from separately published ASME B36.10/B16.9/B16.5 tables.
for sch, wall in (("STD", 1.73), ("XS", 2.41)):
    p = first("tbl_pipes", nps="1/8", schedule=sch)
    check("B36.10-1-8-"+sch, p is not None and abs(float(p["wall_thickness_mm"])-wall) < 0.001,
          p["wall_thickness_mm"] if p else "missing")
p = first("tbl_pipes", nps="22", schedule="80S")
check("pipe-22-80S-inside", p is not None and abs(float(p["inner_diameter_mm"])-533.6)<0.01,
      p["inner_diameter_mm"] if p else "missing")
for sch in ("40S", "80S"):
    p = first("tbl_pipes", nps="18", schedule=sch)
    check("pipe-18-"+sch+"-filled-is-heavy", p is not None and
          float(p["weight_water_kg_m"]) > float(p["weight_empty_kg_m"])+140,
          p["weight_water_kg_m"] if p else "missing")
for nps, sch, name, expected in (
    ("2", "40", "45° Elbow", 35),
    ("2", "40", "90° Long Radius Elbow", 76),
    ("14", "40", "90° Long Radius Elbow", 533)):
    fitting = first("tbl_fittings_buttweld_b16_9",
                    nps=nps, schedule=sch, component_type=name)
    check("B16.9-" + nps + "-" + name,
          fitting is not None and abs(float(fitting["center_to_end_mm"])-expected) <= 1.0,
          fitting["center_to_end_mm"] if fitting else "missing")

# 11b) Previously missing subtype recovered from actual source tables.
union = first("tbl_fittings_socketweld_b16_11",
              nps="1/2", rating="3000", component_type="Union")
check("socket-union-1-2-3000-present", union is not None)
if union:
    check("socket-union-length-33.34", union["length_mm"] in ("33.34", "33.340"), union["length_mm"])
    check("socket-union-depth-10", union["socket_depth_mm"] in ("10", "10.0"), union["socket_depth_mm"])
    check("socket-union-mass-0.145", union["weight_kg"] == "0.145", union["weight_kg"])

# 11c) Threaded fittings were previously unreachable and lost their source fields.
thd_hex = first("tbl_fittings_threaded_b16_11",
                nps="1/2", rating="3000", component_type="Hex Head Plug")
check("threaded-hex-1-2-3000-present", thd_hex is not None)
if thd_hex:
    check("threaded-hex-length-14", float(thd_hex["length_mm"]) == 14.0, thd_hex["length_mm"])
    check("threaded-hex-across-flats-22.23",
          float(thd_hex["across_flats_mm"]) == 22.23, thd_hex["across_flats_mm"])
    check("threaded-hex-head-depth-8",
          float(thd_hex["head_depth_mm"]) == 8.0, thd_hex["head_depth_mm"])
thd_elbow = first("tbl_fittings_threaded_b16_11",
                  nps="1/2", rating="3000", component_type="90° Elbow")
check("threaded-elbow-1-2-3000-present", thd_elbow is not None)
if thd_elbow:
    check("threaded-elbow-A-33", float(thd_elbow["center_to_end_mm"]) == 33, thd_elbow["center_to_end_mm"])
    check("threaded-elbow-H-38", float(thd_elbow["height_mm"]) == 38, thd_elbow["height_mm"])
    check("threaded-elbow-G-4.09", float(thd_elbow["body_wall_thickness_mm"]) == 4.09, thd_elbow["body_wall_thickness_mm"])

# 12) Screen-level selection regression: actual fields must not get lost in CSV mapping.
wn = first("tbl_flanges_asme_b16_5", nps="6", rating="300", flange_type="Welding Neck")
check("screen-6-300-WN-exists", wn is not None)
if wn:
    check("screen-6-300-WN-hub-X-206", wn["hub_diameter_mm"] in ("206.0", "206"), wn["hub_diameter_mm"])
    check("screen-6-300-WN-length-Y-97", wn["length_thru_hub_mm"] in ("97.0", "97"), wn["length_thru_hub_mm"])
    check("screen-6-300-WN-hub-A-168.3", wn["hub_a_mm"] == "168.3", wn["hub_a_mm"])
    check("screen-6-300-WN-long-neck-OD-206",
          wn["long_neck_outer_diameter_mm"] in ("206.0", "206"), wn["long_neck_outer_diameter_mm"])
    check("screen-6-300-WN-weld-flat-1.6", wn["weld_flat_mm"] in ("1.6", "1.60"), wn["weld_flat_mm"])
    check("screen-6-300-WN-RF-height-1.5", wn["raised_face_height_mm"] in ("1.5", "1.50"), wn["raised_face_height_mm"])
    check("screen-6-300-WN-bore-from-source-152.4",
          wn["bore_diameter_mm"] in ("152.4", "152.40"), wn["bore_diameter_mm"])
    check("screen-6-300-WN-stud-RF-120",
          wn["stud_bolt_length_mm"] in ("120.0", "120"), wn["stud_bolt_length_mm"])
sch40 = first("tbl_pipes", nps="6", schedule="40")
sch80 = first("tbl_pipes", nps="6", schedule="80")
check("screen-6-SCH40-bore-154.08",
      sch40 is not None and abs(float(sch40["inner_diameter_mm"])-154.08)<0.03,
      sch40["inner_diameter_mm"] if sch40 else "")
check("screen-6-SCH80-bore-146.36",
      sch80 is not None and abs(float(sch80["inner_diameter_mm"])-146.36)<0.03,
      sch80["inner_diameter_mm"] if sch80 else "")

# Coverage ledger covers ALL live category tables and their subtypes, not a few screenshots.
# A blank is not automatically an error: a blind flange has no bore; a check valve has
# no handwheel. The diagnostic reports raw nulls; mandatory fields are asserted separately.
required_by_table = {
    "tbl_flanges_asme_b16_5": ("outer_diameter_mm", "thickness_mm", "pcd_mm", "num_bolts"),
    "tbl_pipes": ("outer_diameter_mm",),
    "tbl_fittings_buttweld_b16_9": ("outer_diameter_mm",),
    "tbl_fittings_socketweld_b16_11": (),
    "tbl_fittings_threaded_b16_11": (),
    # Check/plug valves may legitimately omit manufacturer mass.
    # A missing optional weight must be reported, not treated as invalid geometry.
    "tbl_valves_asme_b16_10": (),
    "tbl_gaskets_and_rtj": ("weight_kg",),
    "tbl_line_blanks_asme_b16_48": ("thickness_mm",),
}
category_fields = ("flange_type", "component_type", "valve_type", "gasket_type", "blank_type")
for tablename, essentials in required_by_table.items():
    data = rows(tablename)
    check("coverage-table-"+tablename, bool(data), f"{len(data)} rows")
    headers = list(data[0]) if data else []
    measures = [h for h in headers if h.endswith(("_mm", "_kg", "_kg_m", "_lb_ft", "_in4"))]
    summary = {}
    critical_missing = []
    for item in data:
        kind = next((item.get(k) for k in category_fields if item.get(k)), "pipe")
        category = summary.setdefault(kind, {"total":0, "missing_measurements":0, "all_measures_missing":0})
        category["total"] += 1
        missing = [h for h in measures if not item.get(h)]
        if missing:
            category["missing_measurements"] += 1
        if measures and len(missing) == len(measures):
            category["all_measures_missing"] += 1
        for field in essentials:
            if not item.get(field):
                critical_missing.append((kind, item.get("nps"), item.get("rating") or item.get("schedule"),
                                         field, item.get("source_file")))
    print("COVERAGE", tablename, "rows", len(data), "by_type", summary,
          "critical_missing_examples", critical_missing[:12])
    check("coverage-essential-"+tablename, not critical_missing,
          f"{len(critical_missing)} mandatory blanks: {critical_missing[:8]}")


# 13) User-visible selector regression grid: measurements must resolve for
# each concrete fitting variant, not just pass a general row-count test.
check("SCH-present-for-2in", all(first("tbl_pipes", nps="2", schedule=sch)
    and first("tbl_pipes", nps="2", schedule=sch)["wall_thickness_mm"]
    for sch in ("10", "40", "80", "STD")))
for family in ("90° Elbow", "45° Elbow", "Tee", "Cross"):
    fixture = first("tbl_fittings_socketweld_b16_11",
                    nps="1", rating="9000", component_type=family)
    check("socket-9000-1in-"+family, fixture is not None and
          float(fixture["socket_diameter_mm"] or 0) > 0 and
          float(fixture["socket_depth_mm"] or 0) > 0,
          fixture.get("source_file", "") if fixture else "missing")
stub = first("tbl_fittings_buttweld_b16_9",
             nps="2", schedule="40S", component_type="Lap Joint Stub End")
check("stub-2-40S-found", stub is not None)
if stub:
    check("stub-pipe-end-60", float(stub["outer_diameter_mm"]) in (60,60.0,60.3), stub["outer_diameter_mm"])
    check("stub-lap-G-92", abs(float(stub["lap_diameter_mm"])-92)<1, stub["lap_diameter_mm"])
    check("stub-long-F-152", abs(float(stub["overall_length_height_mm"])-152)<1, stub["overall_length_height_mm"])
    check("stub-short-F-64", abs(float(stub["short_length_mm"])-64)<1, stub["short_length_mm"])
reducer = first("tbl_fittings_buttweld_b16_9",
                nps="2", branch_nps="3/4", schedule="STD", component_type="Eccentric Reducer")
check("eccentric-2x3-4-exists", reducer is not None)
if reducer:
    check("eccentric-shares-H-76", abs(float(reducer["overall_length_height_mm"])-76)<1,
          reducer["overall_length_height_mm"])
    check("eccentric-small-OD-26.7", abs(float(reducer["small_end_outer_diameter_mm"])-26.7)<1,
          reducer["small_end_outer_diameter_mm"])
    check("eccentric-weight-unknown", reducer["weight_kg"] == "", reducer["weight_kg"])
spacer = first("tbl_line_blanks_asme_b16_48",
               nps="1", rating="150", blank_type="Paddle Spacer")
check("paddle-spacer-1in-exists", spacer is not None)
if spacer:
    check("paddle-spacer-1in-ID-34", abs(float(spacer["inner_diameter_mm"])-34)<1,
          spacer["inner_diameter_mm"])
    check("paddle-spacer-no-fake-OD", spacer["outer_diameter_mm"] == "", spacer["outer_diameter_mm"])
    check("paddle-spacer-own-mass", abs(float(spacer["weight_spectacle_kg"])-0.06)<0.001,
          spacer["weight_spectacle_kg"])


# Ensure threaded fittings are an actual reachable UI category, not orphan CSV.
from pathlib import Path
app_source = (Path(__file__).parent / "app" / "src" / "main" / "java" /
              "com" / "avenox" / "pipedata" / "data" / "db" / "CsvDataStore.kt").read_text(encoding="utf-8")
main_source = (Path(__file__).parent / "app" / "src" / "main" / "java" /
               "com" / "avenox" / "pipedata" / "MainActivity.kt").read_text(encoding="utf-8")
check("threaded-category-registered", 'id = "fittings_thd"' in app_source)
check("threaded-data-wired", '"fittings_thd" -> buildThreadedData' in app_source)
check("threaded-class-default", '"fittings_thd" -> "3000#"' in main_source)
check("threaded-13-subtypes", all(marker in app_source for marker in (
    "thd_elbow_90", "thd_elbow_45", "thd_tee", "thd_cross", "thd_street",
    "thd_full", "thd_half", "thd_cap", "thd_square", "thd_hex",
    "thd_round", "thd_bushing", "thd_flush"
)))
for kind, source_col in (("90° Elbow", "center_to_end_mm"),
                         ("Round Head Plug", "weight_kg"),
                         ("Full Coupling", "length_mm")):
    candidates = [r for r in rows("tbl_fittings_threaded_b16_11") if
                  r["nps"] == "1" and r["rating"] == "3000" and
                  r["component_type"] == kind and float(r[source_col] or 0) > 0]
    check("threaded-3000-1in-" + kind, bool(candidates),
          candidates[0][source_col] if candidates else "missing")

print(f"\nSONUC: {len(FAILS)} FAIL")
sys.exit(1 if FAILS else 0)

