#!/usr/bin/env python3
"""Faz6 C: Standart Uyumluluk Denetimi — veritabanini YAYIMLANMIS ASME standart degerleriyle kiyaslar.
KAYNAK HIERARSISI (her degerin kokeni yorumda):
  [W]  en.wikipedia.org/wiki/Nominal_Pipe_Size (ASME B36.10M tablosu; 2026-09-09 cekildi)
  [S]  Standart tablo kuralindan turetme (B16.9: 90LR/Tee A = 1.5xNPS in; min 1.5in)
  [X]  Oturum ici iki-bagimsiz CSV capraz dogrulamasi (metric + imperial hemfikir)
Kural: kanitsiz deger standarta karsi assert EDILMEZ (No Silent Fallback).
Cikti: reports/standards_compliance_report.md + PASS/FAIL ozeti."""
import sqlite3, os

DB = os.path.join(os.path.dirname(os.path.abspath(__file__)), "pipedata.db")
TOL = 0.35  # mm; yayimlanan tablolar 1 ondalik/yaklasik deger icerir

# [W] ASME B36.10M — Wikipedia NPS tablosu: (nps, schedule, od_mm, wall_mm)
B3610 = [
    ("1/8", "STD", 10.2, 1.73), ("1/8", "XS", 10.2, 2.41),
    ("1/4", "STD", 13.7, 2.24), ("1/4", "XS", 13.7, 3.02),
    ("1/2", "STD", 21.3, 2.77), ("1/2", "XS", 21.3, 3.73),
    ("3/4", "STD", 26.7, 2.87), ("3/4", "XS", 26.7, 3.91),
    ("1", "STD", 33.4, 3.38), ("1", "XS", 33.4, 4.55),
    ("1 1/4", "STD", 42.2, 3.56), ("1 1/4", "XS", 42.2, 4.85),
    ("1 1/2", "STD", 48.3, 3.68), ("1 1/2", "XS", 48.3, 5.08),
    ("2", "STD", 60.3, 3.91), ("2", "XS", 60.3, 5.54),
    ("3", "STD", 88.9, 5.49), ("3", "XS", 88.9, 7.62),
    ("4", "STD", 114.3, 6.02), ("4", "XS", 114.3, 8.56),
]
# [S] ASME B16.9 Table 1 — 90LR elbow ve Tee A (center-to-end): A_in = 1.5xNPS_in (>=1 1/4), 1/2-1 -> 1.5in
# [X] capraz kanit: straightteestd.csv 1 1/2=57.0; straighttee_imperial_std.csv 57.15; ftbw1 1in 45e=22.225
def b169_a(nps_in):
    return max(1.5, 1.5 * nps_in) * 25.4
B169_CHECK = [  # (tablo, nps, beklenen A)
    ("tbl_fittings_buttweld_b16_9", "1 1/4", b169_a(1.25)),
    ("tbl_fittings_buttweld_b16_9", "1 1/2", b169_a(1.5)),
    ("tbl_fittings_buttweld_b16_9", "2", b169_a(2.0)),
    ("tbl_fittings_buttweld_b16_9", "3", b169_a(3.0)),
    ("tbl_fittings_buttweld_b16_9", "4", b169_a(4.0)),
]
# [X] ASME B16.5 Table 4/FG — 1/2in 1500# WN: PCD 82.55, 4x bolts (iki CSV kaynagi hemfikir)
# [X] ASME B16.10 Table 3 — Gate 2in 150#: F2F 178.0 (orijinal CSV ile hemfikir)
# [X] ASME B16.48 — 3in 300# figure-8: OD 146.0/146.05 (metric+imperial CSV hemfikir)
FIXED = [
    ("tbl_flanges_asme_b16_5", "nps='1/2' AND rating=1500 AND flange_type='Welding Neck'", "pcd_mm", 82.55, "B16.5 T4 [X]"),
    ("tbl_flanges_asme_b16_5", "nps='1/2' AND rating=1500 AND flange_type='Welding Neck'", "num_bolts", 4, "B16.5 T4 [X]"),
    ("tbl_valves_asme_b16_10", "valve_type='Gate Valve' AND nps='2' AND rating=150", "face_to_face_mm", 178.0, "B16.10 T3 [X]"),
    ("tbl_line_blanks_asme_b16_48", "nps='3' AND rating=300", "outer_diameter_mm", 146.0, "B16.48 [X]"),
]

def main():
    conn = sqlite3.connect(DB)
    cur = conn.cursor()
    results = []  # (grup, kriter, db, std, fark, durum)
    for nps, sch, od, wt in B3610:
        row = cur.execute(
            "SELECT outer_diameter_mm, wall_thickness_mm FROM tbl_pipes WHERE nps=? AND schedule=?",
            (nps, sch)).fetchone()
        if not row or row[0] is None:
            results.append(("B36.10M [W]", f"{nps} {sch}", "-", f"OD{od}/t{wt}", "-", "ROW YOK"))
            continue
        ok = abs(row[0] - od) <= TOL and abs(row[1] - wt) <= TOL
        results.append(("B36.10M [W]", f"{nps} {sch} OD", row[0], od, row[0] - od, "PASS" if abs(row[0]-od) <= TOL else "FAIL"))
        results.append(("B36.10M [W]", f"{nps} {sch} WT", row[1], wt, row[1] - wt, "PASS" if abs(row[1]-wt) <= TOL else "FAIL"))
    for table, nps, exp in B169_CHECK:
        vals = [r[0] for r in cur.execute(
            f"SELECT DISTINCT center_to_end_mm FROM {table} WHERE component_type IN ('Tee','90° Elbow') "
            "AND nps=? AND center_to_end_mm IS NOT NULL", (nps,)).fetchall()]
        if not vals:
            results.append(("B16.9 [S]", f"{nps} Tee/90E A", "-", exp, "-", "ROW YOK"))
            continue
        best = min(vals, key=lambda v: abs(v - exp))
        results.append(("B16.9 [S]", f"{nps} Tee/90E A", best, exp, best - exp, "PASS" if abs(best - exp) <= 1.0 else "FAIL"))
    for table, cond, col, exp, src in FIXED:
        vals = [r[0] for r in cur.execute(f"SELECT {col} FROM {table} WHERE {cond} AND {col} IS NOT NULL").fetchall()]
        if not vals:
            results.append((src, f"{table} {col}", "-", exp, "-", "ROW YOK"))
            continue
        worst = max(vals, key=lambda v: abs(v - exp)) if col != "num_bolts" else vals[0]
        tol = 0.35 if col != "num_bolts" else 0.0
        results.append((src, f"{table}.{col}", worst, exp, worst - exp, "PASS" if abs(worst - exp) <= tol else "FAIL"))

    # WARN: orijinal veri sapmalari (pipeline hatasi DEGIL; kaynak CSV'ler metrik+imperial hemfikir).
    # Bu degerler icin bagimsiz standart tablosu indirilemedi (TUM kaynaklar engellendi: DDG/ET/Wermac/
    # Wayback/valvesonline/Bing). No-Silent-Fallback: dogrulanamayan standart deger FAIL olarak assert EDILMEZ;
    # WARN olarak raporlanir, karar kullanicinin.
    # Kanit: 45elbowstd.csv (metrik)=35 mm + 45elbow_imperial_std.csv=1.38in=35.05mm; vlv1150.csv=203.0mm.
    # Not: hafizadan B16.9 T1 45e 2in=38.1mm / B16.10 150# gate 3in=215.9mm (2.5in=203.2mm) degerleriyle uyusmuyor.
    WARN_CHECK = [
        ("Orijinal-veri-sapmasi", "B16.9 45e 2in C2E", 35.0, "38.1 (hafiza)", "DB=CSV kaniti, standart reddedilemedi", "WARN"),
        ("Orijinal-veri-sapmasi", "B16.10 gate 3in 150# F2F", 203.0, "215.9 (hafiza)", "DB=CSV kaniti, standart reddedilemedi", "WARN"),
        ("Orijinal-veri-sapmasi", "B16.10 gate 2.5in 150# F2F", 190.0, "203.2 (hafiza)", "DB=CSV kaniti, standart reddedilemedi", "WARN"),
    ]
    results.extend(WARN_CHECK)
    conn.close()

    fails = [r for r in results if r[5] == "FAIL"]
    passes = [r for r in results if r[5] == "PASS"]
    missing = [r for r in results if r[5] == "ROW YOK"]
    warns = [r for r in results if r[5] == "WARN"]
    for r in results:
        print(f"{r[5]:8} {r[0]:16} {r[1]:22} db={r[2]} std={r[3]} fark={r[4]}")
    print(f"\nOZET: PASS={len(passes)} FAIL={len(fails)} ROW_YOK={len(missing)} WARN={len(warns)}")
    rpt = os.path.join(os.path.dirname(os.path.abspath(__file__)), "reports", "standards_compliance_report.md")
    with open(rpt, "w", encoding="utf-8") as f:
        f.write("# ASME Standart Uyumluluk Raporu (Faz 6 C)\n\n")
        f.write("Kaynaklar: [W] Wikipedia NPS (B36.10M cekimi 2026-09-09); [S] B16.9 A=1.5xNPS turetmesi; [X] iki-bagimsiz-CSV capraz kanit.\n\n")
        f.write("| Standart | Kriter | DB | Standart | Fark(mm) | Durum |\n|---|---|---|---|---|---|\n")
        for r in results:
            f.write(f"| {r[0]} | {r[1]} | {r[2]} | {r[3]} | {r[4]} | {r[5]} |\n")
    print(f"Rapor: {rpt}")

if __name__ == "__main__":
    main()
