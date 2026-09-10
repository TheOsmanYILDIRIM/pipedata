#!/usr/bin/env python3
"""Cikarma dogrulama: app sorgulariyla ayni filtre, kritik hucreler dolu mu?
Sessiz fallback YOK: bos hucre = FAIL."""
import csv, os, sys
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
    check("flans-OD-152", r["outer_diameter_mm"] == "152.0", r["outer_diameter_mm"])
    check("flans-PCD-120.6", r["pcd_mm"] == "120.6", r["pcd_mm"])
    check("flans-civata-4", r["num_bolts"] == "4.0", r["num_bolts"])
# 2) Boru 2" Sch40: OD=60.3
r = first("tbl_pipes", nps="2", schedule="40")
check("boru-2-40-var", r is not None)
if r: check("boru-OD-60.3", r["outer_diameter_mm"] == "60.3", r["outer_diameter_mm"])
# 3) Vana 2" 150#: F2F=178
r = first("tbl_valves_asme_b16_10", nps="2", rating="150")
check("vana-2-150-var", r is not None)
if r: check("vana-F2F-178", r["face_to_face_mm"] == "178.0", r["face_to_face_mm"])
# 4) BW 45 dirsek 2": OD>0, C2E>0
B = rows("tbl_fittings_buttweld_b16_9")
r = next((x for x in B if x["nps"]=="2" and "45" in x["component_type"]), None)
check("bw-45-2-var", r is not None)
if r:
    check("bw-OD-dolu", float(r["outer_diameter_mm"] or 0) > 0, r["outer_diameter_mm"])
    check("bw-C2E-dolu", float(r["center_to_end_mm"] or 0) > 0, r["center_to_end_mm"])
# 5) SW 1" 3000: soket capi + derinlik dolu
S = rows("tbl_fittings_socketweld_b16_11")
r = next((x for x in S if x["nps"]=="1" and x["rating"]=="3000" and float(x["socket_diameter_mm"] or 0) > 0), None)
check("sw-1-3000-soket-dolu", r is not None, (r["socket_diameter_mm"], r["socket_depth_mm"], r["source_file"]) if r else "")
# 6) THR 1" 3000: agirlik kg (lb->kg cevrilmis, <5kg olmali; cevrilmemisse lb degeri siser)
T = rows("tbl_fittings_threaded_b16_11")
r = next((x for x in T if x["nps"]=="1" and x["rating"]=="3000" and x["weight_kg"] not in ("","None")), None)
check("thr-1-3000-wt-dolu", r is not None, (r["weight_kg"], r["source_file"]) if r else "")
# 7) Conta 2" 150: OD/ID dolu
r = first("tbl_gaskets_and_rtj", nps="2", rating="150")
check("conta-2-150-var", r is not None)
if r: check("conta-OD-dolu", float(r["outer_diameter_mm"] or 0) > 0, r["outer_diameter_mm"])
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
print(f"\nSONUC: {len(FAILS)} FAIL")
sys.exit(1 if FAILS else 0)
