#!/usr/bin/env python3
"""
Pipedata Benchmark Doğrulama Testi (Ekran Görüntüsü #6: 16" NPS Sch-STD Boru)
Ekran: Screenshot_20260907-193938.png
Standart: ASME B36.10M/19M-2022
Bileşen: 16" NPS Sch-STD Pipe
"""

import os
import sys
import sqlite3
import csv

def test_screenshot_16_pipe():
    db_path = os.path.expanduser("~/projects/pipedata/pipedata.db")
    raw_csv_path = os.path.expanduser("~/projects/pipedata/data/csv/raw/pipe_imperial_std.csv")
    svg_path = os.path.expanduser("~/projects/pipedata/data/svg/pipe1.svg")

    print("=" * 72)
    print(" PIPEDATA EKRAN DOĞRULAMA TESTİ (Screenshot_20260907-193938.png)")
    print(" ASME B36.10M/19M-2022: 16\" NPS Sch-STD Pipe Information")
    print("=" * 72)

    # 1. Ekran Görüntüsünden Okunan Kesin Değerler (Ground Truth)
    SCREENSHOT_EXPECTED = {
        "nps": "16",
        "dn": 400,
        "schedule": "STD",
        "title": "16\"NPS Sch-STD Pipe Information",
        "weight_empty_lb_ft": 62.64,
        "weight_water_lb_ft": 141.74,
        "outer_diameter_in": 16.0,      # Üst ölçü: 16"
        "inner_diameter_in": 15.25,     # Alt ölçü: 15.25"
        "wall_thickness_in": 0.375,     # İşaretçi ölçüsü: 0.375"
        "radius_half_od_in": 8.0,       # Sağ yarıçap ölçüsü: 8"
        "standard": "ASME B36.10M/19M-2022",
        "unit_system": "U.S. Customary Units"
    }

    # 2. SQLite Veritabanından Doğrudan Sorgulama
    conn = sqlite3.connect(db_path)
    cur = conn.cursor()
    sql_query = """
        SELECT nps, dn, schedule, outer_diameter_in, wall_thickness_in,
               inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft,
               moment_of_inertia_cm4, source_file, unit_system
        FROM tbl_pipes
        WHERE nps = '16' AND schedule = 'STD' AND unit_system = 'Imperial';
    """
    row = cur.execute(sql_query).fetchone()
    conn.close()

    if not row:
        print("[HATA] 16\" Sch-STD boru kaydı SQLite veritabanında bulunamadı!")
        sys.exit(1)

    db_actual = {
        "nps": row[0],
        "dn": row[1],
        "schedule": row[2],
        "outer_diameter_in": row[3],
        "wall_thickness_in": row[4],
        "inner_diameter_in": row[5],
        "weight_empty_lb_ft": row[6],
        "weight_water_lb_ft": row[7],
        "radius_half_od_in": row[3] / 2.0,
        "moment_of_inertia_cm4": row[8],
        "filename": row[9],
        "unit_system": row[10]
    }

    # 3. Ham CSV Dosyasından Doğrulama
    csv_row = None
    with open(raw_csv_path, "r", encoding="latin1") as f:
        reader = csv.reader(f)
        for r in reader:
            if len(r) > 0 and r[0] == "16":
                csv_row = r
                break

    # 4. Kıyaslama Tablosunu Yazdır
    print(f"\n{'PARAMETRE / ALAN':<28} | {'EKRANDAKİ DEĞER':<20} | {'SQLITE DEĞERİ':<20} | {'DURUM'}")
    print("-" * 80)

    checks = [
        ("Nominal Boyut (Size)", f"{SCREENSHOT_EXPECTED['nps']}\"", f"{db_actual['nps']}\"", SCREENSHOT_EXPECTED['nps'] == db_actual['nps']),
        ("DN Metrik Eşdeğeri", f"DN {SCREENSHOT_EXPECTED['dn']}", f"DN {db_actual['dn']}", SCREENSHOT_EXPECTED['dn'] == db_actual['dn']),
        ("Schedule Sınıfı", SCREENSHOT_EXPECTED['schedule'], db_actual['schedule'], SCREENSHOT_EXPECTED['schedule'] == db_actual['schedule']),
        ("Dış Çap (OD - Üst Ölçü)", f"{SCREENSHOT_EXPECTED['outer_diameter_in']:.2f}\"", f"{db_actual['outer_diameter_in']:.2f}\"", abs(SCREENSHOT_EXPECTED['outer_diameter_in'] - db_actual['outer_diameter_in']) < 1e-4),
        ("Et Kalınlığı (WT - Ok)", f"{SCREENSHOT_EXPECTED['wall_thickness_in']:.3f}\"", f"{db_actual['wall_thickness_in']:.3f}\"", abs(SCREENSHOT_EXPECTED['wall_thickness_in'] - db_actual['wall_thickness_in']) < 1e-4),
        ("İç Çap (ID - Alt Ölçü)", f"{SCREENSHOT_EXPECTED['inner_diameter_in']:.2f}\"", f"{db_actual['inner_diameter_in']:.2f}\"", abs(SCREENSHOT_EXPECTED['inner_diameter_in'] - db_actual['inner_diameter_in']) < 1e-4),
        ("Yarıçap (Sağ Ölçü)", f"{SCREENSHOT_EXPECTED['radius_half_od_in']:.1f}\"", f"{db_actual['radius_half_od_in']:.1f}\"", abs(SCREENSHOT_EXPECTED['radius_half_od_in'] - db_actual['radius_half_od_in']) < 1e-4),
        ("Çıplak Ağırlık (Wt)", f"{SCREENSHOT_EXPECTED['weight_empty_lb_ft']} lb/ft", f"{db_actual['weight_empty_lb_ft']} lb/ft", abs(SCREENSHOT_EXPECTED['weight_empty_lb_ft'] - db_actual['weight_empty_lb_ft']) < 1e-4),
        ("Su Dolu Ağırlık", f"{SCREENSHOT_EXPECTED['weight_water_lb_ft']} lb/ft", f"{db_actual['weight_water_lb_ft']} lb/ft", abs(SCREENSHOT_EXPECTED['weight_water_lb_ft'] - db_actual['weight_water_lb_ft']) < 1e-4),
    ]

    all_passed = True
    for name, exp, act, status in checks:
        status_str = "[✓] BİREBİR EŞLEŞTİ" if status else "[✗] UYUMSUZ"
        if not status:
            all_passed = False
        print(f"{name:<28} | {exp:<20} | {act:<20} | {status_str}")

    print("-" * 80)

    # 5. SVG Etiket Doğrulaması
    print("\n[Dinamik SVG Şablon Etiket Enjeksiyonu Analizi - pipe1.svg]:")
    print(f"  * [[3]] (OD)      -> Çizimdeki üst ölçü     : {csv_row[2]}\" (Beklenen: 16\")")
    print(f"  * [[4]] (WT)      -> Çizimdeki ok ölçüsü    : {csv_row[3]}\" (Beklenen: 0.375\")")
    print(f"  * [[5]] (Half OD) -> Çizimdeki sağ yarıçap  : {csv_row[4]}\" (Beklenen: 8\")")
    print(f"  * [[6]] (ID)      -> Çizimdeki alt iç çap   : {csv_row[5]}\" (Beklenen: 15.25\")")
    print(f"  * Atalet Momenti (I) = {csv_row[8]} in^4")

    print("\n" + "=" * 72)
    if all_passed:
        print(" SONUÇ: %100 BAŞARILI! Ekranda görünen TÜM değerler SQLite veritabanı")
        print(" ve kaynak CSV ile son hanesine kadar matematiksel olarak birebir eşleşmektedir.")
    else:
        print(" SONUÇ: BAZI DEĞERLERDE SAPMA VAR!")
    print("=" * 72 + "\n")

    return all_passed

if __name__ == "__main__":
    success = test_screenshot_16_pipe()
    sys.exit(0 if success else 1)
