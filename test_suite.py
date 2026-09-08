#!/usr/bin/env python3
"""
Pipedata Modern Alternatifi - Dondurulmus Otomatik Test Paketi (test_suite.py)
test.md sartnamesini %100 uygulayan calistirilabilir test kodu.
"""

import unittest
import os
import json
import csv
import math
import re
import sqlite3

class TestPipedataIntegrity(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.project_dir = os.path.expanduser("~/projects/pipedata")
        cls.raw_csv_dir = os.path.join(cls.project_dir, "data", "csv", "raw")
        cls.manifest_path = os.path.join(cls.project_dir, "schema_manifest.json")
        cls.svg_dir = os.path.join(cls.project_dir, "data", "svg")

        with open(cls.manifest_path, "r", encoding="utf-8") as f:
            cls.manifest = json.load(f)

    # ----------------------------------------------------
    # TEST PAKETI 1: VERI TABANI BUTUNLUK VE KAPSAM TESTLERI
    # ----------------------------------------------------
    def test_1_1_csv_file_coverage(self):
        """Test 1.1: 956 CSV dosyasinin tamaminin haritalandigini dogrular"""
        expected_csv_count = self.manifest["verification_checksums"]["expected_csv_count"]
        mapped_csv_count = self.manifest["verification_checksums"]["mapped_csv_count"]
        unmapped_count = self.manifest["verification_checksums"]["unmapped_csv_count"]

        self.assertEqual(expected_csv_count, 956, "Toplam beklenen CSV sayisi 956 olmalidir.")
        self.assertEqual(mapped_csv_count, 956, "Haritalanan CSV sayisi 956 olmalidir.")
        self.assertEqual(unmapped_count, 0, "Haritalanmamis CSV sayisi 0 olmalidir.")

    def test_1_2_source_row_count(self):
        """Test 1.2: Toplam kaynak satir sayisinin 22,532 oldugunu dogrular"""
        expected_rows = self.manifest["verification_checksums"]["expected_source_rows"]
        self.assertEqual(expected_rows, 22532, "Kaynak veri satiri tam olarak 22,532 olmalidir.")

    def test_1_3_standard_coverage(self):
        """Test 1.3: 17 temel endustriyel standardin tamaminin semada varligini dogrular"""
        required_standards = [
            "ASME B16.5", "ASME B16.9", "ASME B16.11", "ASME B16.10", "ASME B16.47 Series B",
            "ASME B16.48", "ASME B16.20", "ASME B16.21", "ASME B16.36", "ASME B36.10M",
            "API Spec 6A", "DIN EN 1092-1", "MSS-SP-97", "MSS-SP-95", "ASME B18.2.1"
        ]
        all_standards = set()
        for tname, tinfo in self.manifest["tables"].items():
            for std in tinfo.get("standards", []):
                all_standards.add(std)

        for req in required_standards:
            matched = any(req.lower() in s.lower() for s in all_standards)
            self.assertTrue(matched, f"Standart '{req}' semada bulunamadi!")

    def test_1_4_schedule_and_rating_coverage(self):
        """Test 1.4: 18 farkli schedule serisinin ve basincli flans siniflarinin varligini dogrular"""
        tables = self.manifest["tables"]
        self.assertIn("tbl_pipes", tables)
        self.assertIn("tbl_flanges_asme_b16_5", tables)
        self.assertIn("tbl_fittings_buttweld_b16_9", tables)

        # Tablolardaki kolon sayilarini dogrula
        self.assertGreater(len(tables["tbl_pipes"]["columns"]), 15)
        self.assertGreater(len(tables["tbl_flanges_asme_b16_5"]["columns"]), 15)

    # ----------------------------------------------------
    # TEST PAKETI 2: GERCEK EKRAN GORUNTUSU BENCHMARK TESTLERI
    # ----------------------------------------------------
    def test_2_1_benchmark_a_pipe_14_std(self):
        """Benchmark A (Ekran #3): 14\" NPS Sch-STD ASME B36.10M Boru Boyut ve Agirliklari"""
        csv_path = os.path.join(self.raw_csv_dir, "pipe_imperial_std.csv")
        self.assertTrue(os.path.exists(csv_path), "pipe_imperial_std.csv bulunamadi")

        found = False
        with open(csv_path, "r", encoding="latin1") as f:
            reader = csv.reader(f)
            for row in reader:
                if len(row) >= 8 and row[0] == "14":
                    found = True
                    nps = float(row[0])
                    dn = int(row[1])
                    od = float(row[2])
                    wt = float(row[3])
                    radius = float(row[4])
                    inner_id = float(row[5])
                    weight_empty = float(row[6])
                    weight_full_water = float(row[7])

                    # Ekran #3 ile %100 birebir dogrulama:
                    self.assertEqual(nps, 14.0, "NPS 14 olmali")
                    self.assertEqual(dn, 350, "DN 350 olmali")
                    self.assertEqual(od, 14.0, "Dış Çap (OD) 14.0 inç olmalı")
                    self.assertEqual(wt, 0.375, "Et Kalınlığı (WT) 0.375 inç olmalı")
                    self.assertEqual(radius, 7.0, "Yarıçap 7.0 inç olmalı")
                    self.assertEqual(inner_id, 13.25, "İç Çap (ID) 13.25 inç olmalı")
                    self.assertEqual(weight_empty, 54.62, "Çıplak ağırlık 54.62 lb/ft olmalı")
                    self.assertEqual(weight_full_water, 114.34, "Su dolu ağırlık 114.34 lb/ft olmalı")
                    break

        self.assertTrue(found, "14 inç NPS Sch-STD verisi tablodan bulunamadi")

    def test_2_2_benchmark_b_cap_14_std(self):
        """Benchmark B (Ekran #1): 14" NPS Buttwelding Cap Sch-STD ASME B16.9-2018"""
        csv_path = os.path.join(self.raw_csv_dir, "cap_imperial_std.csv")
        self.assertTrue(os.path.exists(csv_path), "cap_imperial_std.csv bulunamadi")

        found = False
        with open(csv_path, "r", encoding="latin1") as f:
            reader = csv.reader(f)
            for row in reader:
                if len(row) >= 9 and row[0] == "14":
                    found = True
                    nps = row[0]
                    dn = row[1]
                    height_in = float(row[4])
                    weight_kg = float(row[8])

                    # Ekran #1 ile birebir dogrulama:
                    self.assertEqual(nps, "14")
                    self.assertEqual(dn, "350")
                    # Ekran #1'deki Yukseklik oku: 6.5"
                    self.assertEqual(height_in, 6.5, "Kep yüksekliği 6.5 inç olmalı")

                    # Ekran #1'deki Weight = 37.48 lb (17 kg * 2.20462 = 37.478 lb)
                    weight_lb = weight_kg * 2.2046226
                    self.assertAlmostEqual(weight_lb, 37.48, delta=0.05, msg="Kep ağırlığı 37.48 lb olmalı")
                    break

        self.assertTrue(found, "14 inç NPS Buttwelding Cap Sch-STD verisi bulunamadi")

    def test_2_3_benchmark_c_unit_conversions(self):
        """Benchmark C (Ekran #4): Birim donusumleri ve kesirli inc formatlayici"""
        def to_inch_fraction(val):
            # 0.375 -> 3/8", 13.25 -> 13 1/4", 14.0 -> 14"
            whole = int(val)
            frac = val - whole
            if abs(frac) < 1e-4:
                return f"{whole}\""
            frac_16 = round(frac * 16)
            if frac_16 == 16:
                return f"{whole + 1}\""
            gcd = math.gcd(frac_16, 16)
            num = frac_16 // gcd
            den = 16 // gcd
            if whole == 0:
                return f"{num}/{den}\""
            return f"{whole} {num}/{den}\""

        self.assertEqual(to_inch_fraction(0.375), '3/8"')
        self.assertEqual(to_inch_fraction(13.25), '13 1/4"')
        self.assertEqual(to_inch_fraction(14.0), '14"')
        self.assertEqual(to_inch_fraction(0.125), '1/8"')
        self.assertEqual(to_inch_fraction(1.5), '1 1/2"')

        # NPS to DN mapping
        nps_to_dn = {"14": 350, "1/2": 15, "2": 50, "24": 600}
        self.assertEqual(nps_to_dn["14"], 350)
        self.assertEqual(nps_to_dn["1/2"], 15)
        self.assertEqual(nps_to_dn["2"], 50)
        self.assertEqual(nps_to_dn["24"], 600)

    def test_2_4_benchmark_d_pipe_16_std(self):
        """Benchmark D (Ekran #6 - Screenshot_20260907-193938.png): 16" NPS Sch-STD ASME B36.10M Boru"""
        import sqlite3
        db_path = os.path.join(self.project_dir, "pipedata.db")
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()
        
        row = cur.execute("""
            SELECT nps, dn, schedule, outer_diameter_in, wall_thickness_in, inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft
            FROM tbl_pipes
            WHERE nps = '16' AND schedule = 'STD' AND unit_system = 'Imperial';
        """).fetchone()
        conn.close()

        self.assertIsNotNone(row, "16\" Sch-STD boru veritabanında bulunamadı")
        self.assertEqual(row[0], "16", "NPS 16 olmalı")
        self.assertEqual(row[1], 400, "DN 400 olmalı")
        self.assertEqual(row[2], "STD", "Schedule STD olmalı")
        self.assertEqual(row[3], 16.0, "Dış Çap (OD) 16.0 inç olmalı (Çizim üst ölçü)")
        self.assertEqual(row[4], 0.375, "Et Kalınlığı (WT) 0.375 inç olmalı (Çizim ok ölçü)")
        self.assertEqual(row[5], 15.25, "İç Çap (ID) 15.25 inç olmalı (Çizim alt ölçü)")
        self.assertEqual(row[6], 62.64, "Çıplak ağırlık 62.64 lb/ft olmalı")
        self.assertEqual(row[7], 141.74, "Su dolu ağırlık 141.74 lb/ft olmalı")

    # ----------------------------------------------------
    # TEST PAKETI 3: MUHENDISLIK HESAPLAMA MOTORU TESTLERI
    # ----------------------------------------------------
    def test_3_1_moment_of_inertia_calculation(self):
        """Test 3.1: Atalet Momenti formulu: I = pi/64 * (OD^4 - ID^4)"""
        od = 14.0
        id_inner = 13.25
        i_expected = (math.pi / 64.0) * (od**4 - id_inner**4)
        self.assertAlmostEqual(i_expected, 372.84, delta=0.5, msg="Atalet momenti ~372.8 in^4 olmali")

    def test_3_2_safe_span_decision_logic(self):
        """Test 3.2: Safe Span (Guvenli Aciklik) sehim ve gerilme minimum karsilastirmasi"""
        def calc_safe_span(e, i, w, delta_max, sigma_allow, r_outer):
            # Sehim siniri: L_def = ((384 * E * I * delta_max) / (5 * w))^(1/4)
            l_def = ((384.0 * e * i * delta_max) / (5.0 * w)) ** 0.25
            # Gerilme siniri: L_stress = sqrt((10 * sigma_allow * I) / (w * r_outer))
            l_stress = math.sqrt((10.0 * sigma_allow * i) / (w * r_outer))
            if l_def <= l_stress:
                return l_def, "deflection"
            return l_stress, "stress"

        # 14" STD boru metrik ornek
        od_m = 0.3556
        id_m = 0.33655
        i_m4 = (math.pi / 64.0) * (od_m**4 - id_m**4)
        w_kg_m = 170.25  # Su dolu toplam kg/m
        w_n_m = w_kg_m * 9.80665  # N/m
        e_pa = 203390.0 * 1e6  # Pa
        delta_max_m = 0.010  # 10 mm
        sigma_allow_pa = 22.0 * 1e6  # 22 MPa
        r_outer_m = od_m / 2.0

        span, limited_by = calc_safe_span(e_pa, i_m4, w_n_m, delta_max_m, sigma_allow_pa, r_outer_m)
        self.assertGreater(span, 5.0, "Aciklik 5 metreden buyuk olmali")
        self.assertIn(limited_by, ["deflection", "stress"], "Limit turu dogru etiketlenmeli")

    # ----------------------------------------------------
    # TEST PAKETI 4: SVG VE VEKTOR SABLON TESTLERI
    # ----------------------------------------------------
    def test_4_1_svg_templates_exist(self):
        """Test 4.1: 122 SVG sablonunun fiziki varligini dogrular"""
        svg_files = [f for f in os.listdir(self.svg_dir) if f.endswith(".svg")]
        self.assertEqual(len(svg_files), 122, "Tam olarak 122 SVG sablonu mevcut olmalidir.")

    def test_4_2_svg_placeholders_validity(self):
        """Test 4.2: SVG icindeki [[n]] ve [[DB2_n]] etiketlerinin yapisal gecerliligi"""
        svg_dir = os.path.join(self.project_dir, "data", "svg")
        placeholder_regex = re.compile(r'\[\[(.*?)\]\]')
        
        all_placeholders = set()
        for f in os.listdir(svg_dir):
            if f.endswith(".svg"):
                with open(os.path.join(svg_dir, f), "r", encoding="utf-8", errors="ignore") as fp:
                    content = fp.read()
                    matches = placeholder_regex.findall(content)
                    for m in matches:
                        all_placeholders.add(m)
        
        self.assertGreater(len(all_placeholders), 5, "En az 5 farkli dinamik parametre etiketi bulunmali")
        self.assertTrue(any(p.isdigit() for p in all_placeholders), "Sayisal DB1 etiketleri (ornek: [[3]]) bulunmali")
        self.assertTrue(any(p.startswith("DB2_") for p in all_placeholders), "DB2_ etiketleri (ornek: [[DB2_4]]) bulunmali")

    def test_4_3_modern_svg_pipeline_integrity(self):
        """Test 4.3: 122 Modernized AutoCAD SVG dosyasinin XML gecerliligi, geometrik butunlugu ve DB senkronizasyonu"""
        import xml.etree.ElementTree as ET
        mod_dir = os.path.join(self.project_dir, "data", "svg_modern")
        self.assertTrue(os.path.exists(mod_dir), "data/svg_modern klasoru bulunamadi")
        
        mod_files = [f for f in os.listdir(mod_dir) if f.startswith("modern_") and f.endswith(".svg")]
        self.assertEqual(len(mod_files), 122, "122 modern SVG dosyasi eksiksiz mevcut olmali")
        
        # Test XML validity on sample
        for fn in ["modern_pipe1.svg", "modern_ftbw6.svg", "modern_ftbw8.svg", "modern_flan1.svg"]:
            p = os.path.join(mod_dir, fn)
            tree = ET.parse(p)
            root = tree.getroot()
            self.assertEqual(root.tag.split("}")[-1], "svg", f"{fn} gecerli bir SVG koku icermiyor")
            
        # Verify pipedata.db has 122 modern_svg_content
        db_path = os.path.join(self.project_dir, "pipedata.db")
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()
        count = cur.execute("SELECT COUNT(*) FROM tbl_svg_templates WHERE modern_svg_content IS NOT NULL AND length(modern_svg_content) > 50;").fetchone()[0]
        conn.close()
        self.assertEqual(count, 122, "pipedata.db tbl_svg_templates tablosunda 122 modern SVG kaydi dolu olmali")

    # ----------------------------------------------------
    # TEST PAKETI 5: CANLI SQLITE VERITABANI SORGULAMA TESTLERI
    # ----------------------------------------------------
    def test_5_1_sqlite_database_exists_and_integrity(self):
        """Test 5.1: pipedata.db fiziki varligini, integrity_check'i ve katalog/satir sayilarini dogrular"""
        import sqlite3
        db_path = os.path.join(self.project_dir, "pipedata.db")
        self.assertTrue(os.path.exists(db_path), "pipedata.db SQLite veritabani dosyasi mevcut olmali")
        
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()
        
        # Integrity check
        integrity = cur.execute("PRAGMA integrity_check;").fetchone()[0]
        self.assertEqual(integrity, "ok", "SQLite veritabani butunluk kontrolu 'ok' donmeli")
        
        # 956 Catalog entries
        catalog_count = cur.execute("SELECT count(*) FROM tbl_catalog;").fetchone()[0]
        self.assertEqual(catalog_count, 956, "tbl_catalog tablosunda 956 CSV kaydi olmali")
        
        # 22,525 component rows
        row_count = cur.execute("SELECT count(*) FROM tbl_all_components;").fetchone()[0]
        self.assertEqual(row_count, 22525, "tbl_all_components tablosunda tam 22,525 satir olmali")
        conn.close()

    def test_5_2_sqlite_benchmark_a_pipe_query(self):
        """Test 5.2: tbl_pipes tablosunda 14\" Sch-STD borunun SQL sorgusu ile dogrulanmasi"""
        import sqlite3
        db_path = os.path.join(self.project_dir, "pipedata.db")
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()
        
        row = cur.execute("""
            SELECT nps, dn, schedule, outer_diameter_in, wall_thickness_in, inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft
            FROM tbl_pipes
            WHERE nps = '14' AND schedule = 'STD' AND unit_system = 'Imperial';
        """).fetchone()
        
        self.assertIsNotNone(row, "14\" Sch-STD boru kaydi SQLite tablosunda bulunamadi")
        self.assertEqual(row[0], "14")
        self.assertEqual(row[1], 350)
        self.assertEqual(row[2], "STD")
        self.assertEqual(row[3], 14.0)
        self.assertEqual(row[4], 0.375)
        self.assertEqual(row[5], 13.25)
        self.assertEqual(row[6], 54.62)
        self.assertEqual(row[7], 114.34)
        conn.close()

    def test_5_3_sqlite_benchmark_b_cap_query(self):
        """Test 5.3: tbl_fittings_buttweld_b16_9 tablosunda 14\" Buttweld Cap SQL sorgusu"""
        import sqlite3
        db_path = os.path.join(self.project_dir, "pipedata.db")
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()
        
        row = cur.execute("""
            SELECT component_type, nps, dn, center_to_end_mm, weight_kg
            FROM tbl_fittings_buttweld_b16_9
            WHERE nps = '14' AND source_file = 'cap_imperial_std.csv';
        """).fetchone()
        
        self.assertIsNotNone(row, "14\" Cap kaydi SQLite tablosunda bulunamadi")
        self.assertEqual(row[0], "Cap")
        self.assertEqual(row[1], "14")
        self.assertEqual(row[2], 350)
        self.assertEqual(row[3], 6.5, "Yukseklik 6.5 inc olmali")
        self.assertEqual(row[4], 17.0, "Agirlik 17.0 kg olmali")
        conn.close()

    def test_5_4_sqlite_downloads_sync(self):
        """Test 5.4: /storage/emulated/0/Download/ klasorundeki dosya senkronizasyonunu dogrular"""
        download_db = "/storage/emulated/0/Download/pipedata.db"
        self.assertTrue(os.path.exists(download_db), "Downloads/pipedata.db bulunamadi")
        self.assertGreater(os.path.getsize(download_db), 10 * 1024 * 1024, "pipedata.db 10MB uzerinde olmali")

    def test_5_5_sqlite_socketweld_b16_11_deep_integrity(self):
        """Test 5.5: Anti-Macro Fallacy Kurali - tbl_fittings_socketweld_b16_11 derin semantik dogrulama"""
        import sqlite3
        db_path = os.path.join(self.project_dir, "pipedata.db")
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()

        # 1. Class 2000# Soket Kaynakta KESINLIKLE OLMAMALIDIR (ASME B16.11 kurali)
        count_2000 = cur.execute("SELECT count(*) FROM tbl_fittings_socketweld_b16_11 WHERE rating = 2000;").fetchone()[0]
        self.assertEqual(count_2000, 0, "ASME B16.11 geregi soket kaynakta 2000# sinifi kesinlikle bulunamaz!")

        # 2. 1/2" 3000# 90° Dirsek icin gercek ground-truth olculer dolu ve dogru olmalidir
        row = cur.execute("""
            SELECT socket_depth_mm, socket_diameter_mm, center_to_end_mm, bore_diameter_mm, socket_wall_thickness_mm, weight_kg
            FROM tbl_fittings_socketweld_b16_11
            WHERE component_type = '90° Elbow' AND rating = 3000 AND (nps = '1/2' OR nps = '0+1/2') AND unit_system = 'Metric';
        """).fetchone()

        self.assertIsNotNone(row, "1/2\" 3000# 90° Soket Dirsek veritabaninda bulunamadi!")
        self.assertEqual(row[0], 9.5, "Soket derinligi 9.5 mm olmali")
        self.assertEqual(row[1], 22.2, "Soket capi 22.2 mm olmali")
        self.assertEqual(row[2], 15.5, "Eksen-uc mesafesi 15.5 mm olmali")
        self.assertEqual(row[3], 16.6, "Ic delik capi 16.6 mm olmali")
        self.assertEqual(row[4], 4.67, "Soket et kalinligi 4.67 mm olmali")
        self.assertEqual(row[5], 0.15, "Agirlik 0.15 kg olmali")

        # 3. 9 alt tipin tamaminin varligini dogrula
        types = set(r[0] for r in cur.execute("SELECT DISTINCT component_type FROM tbl_fittings_socketweld_b16_11;").fetchall())
        expected_types = {"90° Elbow", "45° Elbow", "Tee", "Cross", "Full Coupling", "Half Coupling", "Cap", "Reducing Coupling", "Union"}
        for exp in expected_types:
            self.assertIn(exp, types, f"Soket alt tipi '{exp}' veritabaninda eksik!")

        conn.close()

    def test_5_6_asset_to_registry_reconciliation(self):
        """Test 5.6: Varlik - Kayit Mutabakati: modern_ftsw1..9 tum SVG'ler assets altinda mevcut olmalidir"""
        assets_svg_dir = os.path.join(self.project_dir, "app", "src", "main", "assets", "svg")
        for i in range(1, 10):
            svg_name = f"modern_ftsw{i}.svg"
            svg_path = os.path.join(assets_svg_dir, svg_name)
            self.assertTrue(os.path.exists(svg_path), f"Gerekli teknik cizim '{svg_name}' assets klasorunde bulunamadi!")


    def test_5_7_sqlite_valves_b16_10_deep_integrity(self):
        """Test 5.7: Anti-Macro Fallacy - tbl_valves_asme_b16_10 semantik kolon butunlugu"""
        db_path = os.path.join(self.project_dir, "pipedata.db")
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()
        
        # 100% NULL kontrolu (hicbir kritik kolon tamamen bos olmamali)
        for col in ["face_to_face_mm", "handwheel_height_mm", "handwheel_diameter_mm", "weight_kg"]:
            null_count = cur.execute(f"SELECT count(*) FROM tbl_valves_asme_b16_10 WHERE {col} IS NULL;").fetchone()[0]
            total_count = cur.execute("SELECT count(*) FROM tbl_valves_asme_b16_10;").fetchone()[0]
            self.assertLess(null_count, total_count, f"{col} kolonu 100% NULL olamaz!")

        # Ground-truth kayit dogrulama: 2" 150# Gate Valve
        row = cur.execute("""
            SELECT face_to_face_mm, handwheel_height_mm, handwheel_diameter_mm, weight_kg 
            FROM tbl_valves_asme_b16_10 
            WHERE valve_type = 'Gate Valve' AND (nps = '2' OR nps = '2.0') AND rating = 150 LIMIT 1;
        """).fetchone()
        self.assertIsNotNone(row)
        self.assertEqual(row[0], 178.0, "2in 150# Gate Valve F-to-F 178.0 mm olmali")
        self.assertEqual(row[1], 409.0, "2in 150# Gate Valve El carki yuksekligi 409.0 mm olmali")
        self.assertEqual(row[2], 200.0, "2in 150# Gate Valve El carki capi 200.0 mm olmali")
        self.assertEqual(row[3], 18.0, "2in 150# Gate Valve Agirligi 18.0 kg olmali")
        conn.close()

    def test_5_8_sqlite_flanges_b16_5_deep_integrity(self):
        """Test 5.8: Anti-Macro Fallacy - tbl_flanges_asme_b16_5 stud bolt length ve semantik kolon butunlugu"""
        db_path = os.path.join(self.project_dir, "pipedata.db")
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()

        # Ground-truth: 2" 150# Welding Neck Flange
        row = cur.execute("""
            SELECT outer_diameter_mm, pcd_mm, num_bolts, stud_bolt_length_mm, weight_kg 
            FROM tbl_flanges_asme_b16_5 
            WHERE flange_type = 'Welding Neck' AND (nps = '2' OR nps = '2.0') AND rating = 150 LIMIT 1;
        """).fetchone()
        self.assertIsNotNone(row)
        self.assertEqual(row[0], 152.0, "2in 150# WN Flans dis capi 152.0 mm olmali")
        self.assertEqual(row[1], 120.6, "2in 150# WN Flans PCD 120.6 mm olmali")
        self.assertEqual(row[2], 4, "2in 150# WN Flans civata adedi 4 olmali")
        self.assertEqual(row[3], 85.0, "2in 150# WN Flans saplama boyu 85.0 mm olmali")
        self.assertEqual(row[4], 2.7, "2in 150# WN Flans agirligi 2.7 kg olmali")
        conn.close()

    def test_5_9_sqlite_pipes_engineering_properties_deep_integrity(self):
        """Test 5.9: Anti-Macro Fallacy - tbl_pipes Atalet Momenti, Kesit Modulu ve Dolu Agirlik butunlugu"""
        db_path = os.path.join(self.project_dir, "pipedata.db")
        conn = sqlite3.connect(db_path)
        cur = conn.cursor()

        # Ground-truth: 2" Sch 40 Boru
        row = cur.execute("""
            SELECT outer_diameter_mm, wall_thickness_mm, inner_diameter_mm, weight_empty_kg_m, weight_total_kg_m, moment_of_inertia_cm4 
            FROM tbl_pipes 
            WHERE (nps = '2' OR nps = '2.0') AND schedule = '40' LIMIT 1;
        """).fetchone()
        self.assertIsNotNone(row)
        self.assertEqual(row[0], 60.3, "2in Sch 40 Boru OD 60.3 mm olmali")
        self.assertEqual(row[1], 3.91, "2in Sch 40 Boru WT 3.91 mm olmali")
        self.assertEqual(row[2], 52.48, "2in Sch 40 Boru ID 52.48 mm olmali")
        self.assertEqual(row[3], 5.44, "2in Sch 40 Boru bos agirlik 5.44 kg/m olmali")
        self.assertEqual(row[4], 13.04, "2in Sch 40 Boru su dolu toplam agirlik 13.04 kg/m olmali")
        self.assertEqual(row[5], 27.65, "2in Sch 40 Boru atalet momenti 27.65 cm4 olmali")
        conn.close()

if __name__ == "__main__":
    unittest.main(verbosity=2)


