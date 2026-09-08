#!/usr/bin/env python3
"""
Pipedata Comprehensive SQLite Database Builder (build_full_sqlite.py)
Imports all 956 CSV files into their dedicated 17 standard tables + master catalog + all_components.
"""

import os
import sys
import json
import csv
import re
import sqlite3

def parse_nps(val_str):
    if not val_str or str(val_str).upper() == "N/A":
        return "", None
    val_str = str(val_str).strip()
    m = re.match(r"^(\d+)\+(\d+)/(\d+)$", val_str)
    if m:
        w, num, den = int(m.group(1)), int(m.group(2)), int(m.group(3))
        display = f"{num}/{den}" if w == 0 else f"{w} {num}/{den}"
        dec = w + num / den
        return display, dec
    
    m = re.match(r"^(\d+)/(\d+)$", val_str)
    if m:
        num, den = int(m.group(1)), int(m.group(2))
        return f"{num}/{den}", num / den
        
    try:
        dec = float(val_str)
        if dec.is_integer():
            return str(int(dec)), dec
        return val_str, dec
    except ValueError:
        return val_str, None

def parse_float(val_str):
    if not val_str or str(val_str).upper() == "N/A" or str(val_str).strip() == "":
        return None
    try:
        clean = str(val_str).replace(",", "").replace("#", "").strip()
        return float(clean)
    except ValueError:
        return None

def get_col(row, idx):
    if row and 0 <= idx < len(row):
        v = row[idx].strip()
        return v if v != "" and v.upper() != "N/A" else None
    return None

def get_last_col(row):
    if row:
        for v in reversed(row):
            s = v.strip()
            if s and s.upper() != "N/A":
                return s
    return None

def build_full_sqlite():
    base_dir = "/data/data/com.termux/files/home/projects/pipedata"
    raw_csv_dir = os.path.join(base_dir, "data", "csv", "raw")
    svg_dir = os.path.join(base_dir, "data", "svg")
    manifest_path = os.path.join(base_dir, "schema_manifest.json")
    db_path = os.path.join(base_dir, "pipedata.db")

    if os.path.exists(db_path):
        os.remove(db_path)

    print(f"Connecting to SQLite: {db_path}")
    conn = sqlite3.connect(db_path)
    cur = conn.cursor()

    cur.execute("PRAGMA journal_mode = WAL;")
    cur.execute("PRAGMA synchronous = NORMAL;")

    # 1. Master Catalog Table
    cur.execute("""
    CREATE TABLE tbl_catalog (
        file_id INTEGER PRIMARY KEY AUTOINCREMENT,
        filename TEXT UNIQUE NOT NULL,
        target_table TEXT NOT NULL,
        category TEXT NOT NULL,
        standard TEXT NOT NULL,
        unit_system TEXT NOT NULL,
        schedule_or_rating TEXT,
        row_count INTEGER NOT NULL,
        col_count INTEGER NOT NULL,
        headers_json TEXT NOT NULL
    );
    """)

    # 2. Universal Components Search Table
    cur.execute("""
    CREATE TABLE tbl_all_components (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        file_id INTEGER NOT NULL,
        filename TEXT NOT NULL,
        target_table TEXT NOT NULL,
        category TEXT NOT NULL,
        standard TEXT NOT NULL,
        unit_system TEXT NOT NULL,
        schedule_or_rating TEXT,
        row_idx INTEGER NOT NULL,
        nps TEXT,
        nps_display TEXT,
        nps_decimal REAL,
        dn INTEGER,
        c1 TEXT, c2 TEXT, c3 TEXT, c4 TEXT, c5 TEXT,
        c6 TEXT, c7 TEXT, c8 TEXT, c9 TEXT, c10 TEXT,
        c11 TEXT, c12 TEXT, c13 TEXT, c14 TEXT, c15 TEXT,
        FOREIGN KEY (file_id) REFERENCES tbl_catalog(file_id)
    );
    """)

    # 3. Load Schema Manifest for 17 Target Tables
    with open(manifest_path, "r", encoding="utf-8") as f:
        manifest = json.load(f)

    tables_def = manifest["tables"]
    for tname, tdef in tables_def.items():
        col_defs = []
        existing_cols = set()
        for col in tdef["columns"]:
            cname = col["name"]
            ctype = col.get("type", "TEXT")
            if "PRIMARY KEY" not in ctype:
                ctype = ctype.replace("NOT NULL", "")
            col_defs.append(f"{cname} {ctype}")
            existing_cols.add(cname)
        
        # Add metadata columns if missing
        if "source_file" not in existing_cols:
            col_defs.append("source_file TEXT")
        if "unit_system" not in existing_cols:
            col_defs.append("unit_system TEXT")
        if "raw_json" not in existing_cols:
            col_defs.append("raw_json TEXT")

        create_sql = f"CREATE TABLE IF NOT EXISTS {tname} (\n  " + ",\n  ".join(col_defs) + "\n);"
        cur.execute(create_sql)
        for idx in tdef.get("indexes", []):
            try:
                cur.execute(idx)
            except Exception:
                pass

    # 4. SVG Templates Table
    cur.execute("""
    CREATE TABLE tbl_svg_templates (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        filename TEXT UNIQUE NOT NULL,
        title TEXT,
        category TEXT,
        placeholder_count INTEGER,
        placeholders_json TEXT,
        svg_content TEXT NOT NULL
    );
    """)

    if os.path.exists(svg_dir):
        svg_files = [f for f in os.listdir(svg_dir) if f.endswith(".svg")]
        print(f"Loading {len(svg_files)} SVG templates...")
        for sf in svg_files:
            spath = os.path.join(svg_dir, sf)
            with open(spath, "r", encoding="utf-8", errors="ignore") as f:
                content = f.read()
            placeholders = re.findall(r"\[\[([a-zA-Z0-9_]+)\]\]", content)
            unique_ph = list(dict.fromkeys(placeholders))
            title = sf.replace(".svg", "").replace("_", " ").title()
            cur.execute("""
                INSERT INTO tbl_svg_templates (filename, title, category, placeholder_count, placeholders_json, svg_content)
                VALUES (?, ?, ?, ?, ?, ?)
            """, (sf, title, "General", len(unique_ph), json.dumps(unique_ph), content))

    # Process all 956 files
    print("Populating SQLite tables from 956 CSV files...")
    total_files = 0
    total_data_rows = 0

    for tname, tdef in tables_def.items():
        source_files = tdef.get("source_files", [])
        for sf in source_files:
            fname = sf["filename"]
            csv_path = os.path.join(raw_csv_dir, fname)
            unit_sys = sf.get("unit_system", "Metric")
            rating_sch = sf.get("rating_or_sch", "")
            standard = ", ".join(tdef.get("standards", []))
            category = tdef.get("category_key", "")

            rows = []
            if os.path.exists(csv_path):
                with open(csv_path, "r", encoding="latin1") as f:
                    rows = list(csv.reader(f))

            header_names = rows[1] if len(rows) > 1 else []
            data_rows = [r for r in rows[2:] if r and any(cell.strip() for cell in r)] if len(rows) > 2 else []

            # Insert Catalog
            cur.execute("""
                INSERT INTO tbl_catalog (filename, target_table, category, standard, unit_system, schedule_or_rating, row_count, col_count, headers_json)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """, (fname, tname, category, standard, unit_sys, rating_sch, len(data_rows), len(header_names), json.dumps(header_names)))
            file_id = cur.lastrowid
            total_files += 1

            for r_idx, row in enumerate(data_rows):
                total_data_rows += 1
                raw_nps = get_col(row, 0) or ""
                nps_display, nps_dec = parse_nps(raw_nps)
                dn_val = parse_float(get_col(row, 1))
                c_cols = [get_col(row, i) for i in range(15)]

                # Universal components table
                cur.execute("""
                    INSERT INTO tbl_all_components (
                        file_id, filename, target_table, category, standard, unit_system, schedule_or_rating,
                        row_idx, nps, nps_display, nps_decimal, dn,
                        c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, (
                    file_id, fname, tname, category, standard, unit_sys, rating_sch,
                    r_idx, raw_nps, nps_display, nps_dec, int(dn_val) if dn_val else None,
                    c_cols[0], c_cols[1], c_cols[2], c_cols[3], c_cols[4],
                    c_cols[5], c_cols[6], c_cols[7], c_cols[8], c_cols[9],
                    c_cols[10], c_cols[11], c_cols[12], c_cols[13], c_cols[14]
                ))

                # Insert into the dedicated standard table
                raw_json = json.dumps(dict(zip(header_names, row))) if header_names else json.dumps(row)
                
                if tname == "tbl_pipes":
                    sch = fname.replace("pipe_imperial_", "").replace("pipe", "").replace(".csv", "").upper()
                    c2_f = parse_float(get_col(row, 2))
                    c3_f = parse_float(get_col(row, 3))
                    c5_f = parse_float(get_col(row, 5))
                    c6_f = parse_float(get_col(row, 6))
                    c7_f = parse_float(get_col(row, 7))
                    c8_f = parse_float(get_col(row, 8))
                    cur.execute("""
                        INSERT INTO tbl_pipes (
                            nps, nps_decimal, dn, schedule,
                            outer_diameter_mm, outer_diameter_in,
                            wall_thickness_mm, wall_thickness_in,
                            inner_diameter_mm, inner_diameter_in,
                            weight_empty_kg_m, weight_empty_lb_ft,
                            weight_water_kg_m, weight_water_lb_ft,
                            moment_of_inertia_cm4,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        raw_nps, nps_dec, int(dn_val) if dn_val else None, sch,
                        c2_f if unit_sys == "Metric" else ((c2_f * 25.4) if c2_f else None),
                        c2_f if unit_sys != "Metric" else ((c2_f / 25.4) if c2_f else None),
                        c3_f if unit_sys == "Metric" else ((c3_f * 25.4) if c3_f else None),
                        c3_f if unit_sys != "Metric" else ((c3_f / 25.4) if c3_f else None),
                        c5_f if unit_sys == "Metric" else ((c5_f * 25.4) if c5_f else None),
                        c5_f if unit_sys != "Metric" else ((c5_f / 25.4) if c5_f else None),
                        c6_f if unit_sys == "Metric" else ((c6_f * 1.48816) if c6_f else None),
                        c6_f if unit_sys != "Metric" else ((c6_f * 0.67197) if c6_f else None),
                        c7_f if unit_sys == "Metric" else ((c7_f * 1.48816) if c7_f else None),
                        c7_f if unit_sys != "Metric" else ((c7_f * 0.67197) if c7_f else None),
                        c8_f,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_fittings_buttweld_b16_9":
                    ctype = "Buttweld Fitting"
                    if "cap" in fname: ctype = "Cap"
                    elif "45elbow" in fname: ctype = "45° Elbow"
                    elif "90elbow" in fname or "elbow" in fname: ctype = "90° Elbow"
                    elif "tee" in fname: ctype = "Tee"
                    elif "reducer" in fname: ctype = "Reducer"
                    c2_f = parse_float(get_col(row, 2))
                    c4_f = parse_float(get_col(row, 4))
                    c8_f = parse_float(get_col(row, 8))
                    last_f = parse_float(get_last_col(row))
                    primary_wt = c8_f if c8_f is not None else last_f
                    cur.execute("""
                        INSERT INTO tbl_fittings_buttweld_b16_9 (
                            component_type, nps, dn,
                            outer_diameter_mm,
                            center_to_end_mm,
                            weight_kg, weight_lb,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        ctype, raw_nps, int(dn_val) if dn_val else None,
                        c2_f, c4_f, primary_wt,
                        (primary_wt * 2.20462) if (primary_wt is not None and unit_sys == "Metric") else primary_wt,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_flanges_asme_b16_5":
                    rating_m = re.search(r"(\d+)", fname)
                    c2_f = parse_float(get_col(row, 2))
                    c3_f = parse_float(get_col(row, 3))
                    c4_f = parse_float(get_col(row, 4))
                    c5_f = parse_float(get_col(row, 5))
                    c6_f = parse_float(get_col(row, 6))
                    last_f = parse_float(get_last_col(row))
                    cur.execute("""
                        INSERT INTO tbl_flanges_asme_b16_5 (
                            nps, dn, rating, flange_type, facing,
                            outer_diameter_mm, thickness_mm,
                            num_bolts, bolt_diameter_in, bolt_hole_diameter_mm,
                            pcd_mm, weight_kg,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        raw_nps, int(dn_val) if dn_val else None,
                        int(rating_m.group(1)) if rating_m else 150,
                        "Flange B16.5", "RF",
                        c2_f, c3_f,
                        int(c6_f) if c6_f is not None else 4,
                        "3/4", c5_f, c4_f, last_f,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_valves_asme_b16_10":
                    vtype = "Valve"
                    if "gate" in fname: vtype = "Gate Valve"
                    elif "globe" in fname: vtype = "Globe Valve"
                    elif "ball" in fname: vtype = "Ball Valve"
                    elif "check" in fname: vtype = "Check Valve"
                    elif "butterfly" in fname: vtype = "Butterfly Valve"
                    elif "psv" in fname: vtype = "Safety Valve"
                    c2_f = parse_float(get_col(row, 2))
                    c3_f = parse_float(get_col(row, 3))
                    c4_f = parse_float(get_col(row, 4))
                    last_f = parse_float(get_last_col(row))
                    cur.execute("""
                        INSERT INTO tbl_valves_asme_b16_10 (
                            valve_type, end_connection, nps, dn, rating,
                            face_to_face_mm, weight_kg,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        vtype, "Flanged", raw_nps, int(dn_val) if dn_val else None,
                        150, c2_f or c3_f or 0.0, last_f or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_gaskets_and_rtj":
                    gtype = "Gasket"
                    if "rtj" in fname: gtype = "RTJ Ring"
                    elif "sw" in fname or "spw" in fname: gtype = "Spiral Wound"
                    elif "flt" in fname: gtype = "Flat Ring"
                    c2_f = parse_float(get_col(row, 2))
                    c3_f = parse_float(get_col(row, 3))
                    last_f = parse_float(get_last_col(row))
                    cur.execute("""
                        INSERT INTO tbl_gaskets_and_rtj (
                            gasket_type, applicable_standard, nps, dn, rating,
                            inner_diameter_mm, outer_diameter_mm, weight_kg,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        gtype, "ASME B16.20 / B16.21", raw_nps, int(dn_val) if dn_val else None,
                        150, c2_f or 0.0, c3_f or 0.0, last_f or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_line_blanks_asme_b16_48":
                    btype = "Figure-8 Blind"
                    if "paddle" in fname: btype = "Paddle Blank"
                    elif "spacer" in fname: btype = "Paddle Spacer"
                    c2_f = parse_float(get_col(row, 2))
                    c3_f = parse_float(get_col(row, 3))
                    last_f = parse_float(get_last_col(row))
                    cur.execute("""
                        INSERT INTO tbl_line_blanks_asme_b16_48 (
                            blank_type, facing, nps, dn, rating,
                            outer_diameter_mm, thickness_mm, weight_blank_kg,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        btype, "RF", raw_nps, int(dn_val) if dn_val else None,
                        150, c2_f or 0.0, c3_f or 0.0, last_f or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_fittings_threaded_b16_11":
                    cur.execute("""
                        INSERT INTO tbl_fittings_threaded_b16_11 (
                            component_type, nps, dn, rating,
                            center_to_end_mm, weight_kg,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        "Threaded Fitting", raw_nps, int(dn_val) if dn_val else None,
                        3000, parse_float(get_col(row, 2)) or 0.0, parse_float(get_last_col(row)) or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_fittings_socketweld_b16_11":
                    cur.execute("""
                        INSERT INTO tbl_fittings_socketweld_b16_11 (
                            component_type, nps, dn, rating,
                            center_to_end_mm, weight_kg,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        "Socketweld Fitting", raw_nps, int(dn_val) if dn_val else None,
                        3000, parse_float(get_col(row, 2)) or 0.0, parse_float(get_last_col(row)) or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_flanges_asme_b16_47":
                    cur.execute("""
                        INSERT INTO tbl_flanges_asme_b16_47 (
                            series, nps, dn, rating, flange_type,
                            outer_diameter_mm, thickness_mm,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        "Series B" if "b" in fname.lower() else "Series A",
                        raw_nps, int(dn_val) if dn_val else None,
                        150, "Large Diameter Flange",
                        parse_float(get_col(row, 2)) or 0.0, parse_float(get_col(row, 3)) or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_flanges_din_en_1092":
                    cur.execute("""
                        INSERT INTO tbl_flanges_din_en_1092 (
                            dn, pn_bar, outer_diameter_mm,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?)
                    """, (
                        int(dn_val) if dn_val else None, 16,
                        parse_float(get_col(row, 2)) or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_branch_outlets_mss_sp_97":
                    cur.execute("""
                        INSERT INTO tbl_branch_outlets_mss_sp_97 (
                            outlet_type, run_size_nps, branch_size_nps, rating_or_sch,
                            height_mm, weight_kg,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """, (
                        "Olet Branch", raw_nps, raw_nps, rating_sch,
                        parse_float(get_col(row, 2)) or 0.0, parse_float(get_last_col(row)) or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_bolting_and_fasteners":
                    cur.execute("""
                        INSERT INTO tbl_bolting_and_fasteners (
                            bolt_diameter_in, threads_per_inch,
                            nut_width_across_flats_mm, weight_per_bolt_kg_per_100,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?, ?, ?)
                    """, (
                        raw_nps, int(parse_float(get_col(row, 8))) if get_col(row, 8) and parse_float(get_col(row, 8)) else None,
                        parse_float(get_col(row, 5)) or 0.0, parse_float(get_last_col(row)) or 0.0,
                        fname, unit_sys, raw_json
                    ))

                elif tname == "tbl_misc_data":
                    cur.execute("""
                        INSERT INTO tbl_misc_data (
                            config_name, raw_data,
                            source_file, unit_system, raw_json
                        ) VALUES (?, ?, ?, ?, ?)
                    """, (
                        fname, json.dumps(row),
                        fname, unit_sys, raw_json
                    ))

                else:
                    # Generic insert for any other tables
                    pass

    # Add useful query indexes
    print("Creating indexes...")
    cur.execute("CREATE INDEX IF NOT EXISTS idx_all_comp_file ON tbl_all_components(filename);")
    cur.execute("CREATE INDEX IF NOT EXISTS idx_all_comp_cat ON tbl_all_components(category);")
    cur.execute("CREATE INDEX IF NOT EXISTS idx_all_comp_nps ON tbl_all_components(nps);")
    cur.execute("CREATE INDEX IF NOT EXISTS idx_all_comp_dn ON tbl_all_components(dn);")
    cur.execute("CREATE INDEX IF NOT EXISTS idx_catalog_file ON tbl_catalog(filename);")
    cur.execute("CREATE INDEX IF NOT EXISTS idx_catalog_table ON tbl_catalog(target_table);")

    # Benchmarking Views
    cur.execute("""
    CREATE VIEW vw_benchmark_a_pipe_14_std AS
    SELECT nps, dn, schedule, outer_diameter_in, wall_thickness_in, inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft
    FROM tbl_pipes
    WHERE nps = '14' AND schedule = 'STD' AND unit_system = 'Imperial';
    """)

    cur.execute("""
    CREATE VIEW vw_benchmark_b_cap_14_std AS
    SELECT component_type, nps, dn, center_to_end_mm, weight_kg, weight_lb, source_file
    FROM tbl_fittings_buttweld_b16_9
    WHERE nps = '14' AND source_file = 'cap_imperial_std.csv';
    """)

    cur.execute("""
    CREATE VIEW vw_database_summary AS
    SELECT 'tbl_catalog' AS table_name, count(*) AS row_count FROM tbl_catalog
    UNION ALL SELECT 'tbl_all_components', count(*) FROM tbl_all_components
    UNION ALL SELECT 'tbl_pipes', count(*) FROM tbl_pipes
    UNION ALL SELECT 'tbl_flanges_asme_b16_5', count(*) FROM tbl_flanges_asme_b16_5
    UNION ALL SELECT 'tbl_fittings_buttweld_b16_9', count(*) FROM tbl_fittings_buttweld_b16_9
    UNION ALL SELECT 'tbl_valves_asme_b16_10', count(*) FROM tbl_valves_asme_b16_10
    UNION ALL SELECT 'tbl_gaskets_and_rtj', count(*) FROM tbl_gaskets_and_rtj
    UNION ALL SELECT 'tbl_line_blanks_asme_b16_48', count(*) FROM tbl_line_blanks_asme_b16_48
    UNION ALL SELECT 'tbl_fittings_threaded_b16_11', count(*) FROM tbl_fittings_threaded_b16_11
    UNION ALL SELECT 'tbl_fittings_socketweld_b16_11', count(*) FROM tbl_fittings_socketweld_b16_11
    UNION ALL SELECT 'tbl_flanges_asme_b16_47', count(*) FROM tbl_flanges_asme_b16_47
    UNION ALL SELECT 'tbl_flanges_din_en_1092', count(*) FROM tbl_flanges_din_en_1092
    UNION ALL SELECT 'tbl_branch_outlets_mss_sp_97', count(*) FROM tbl_branch_outlets_mss_sp_97
    UNION ALL SELECT 'tbl_bolting_and_fasteners', count(*) FROM tbl_bolting_and_fasteners
    UNION ALL SELECT 'tbl_misc_data', count(*) FROM tbl_misc_data
    UNION ALL SELECT 'tbl_svg_templates', count(*) FROM tbl_svg_templates;
    """)

    conn.commit()
    conn.close()

    print(f"\n[OK] Full Database successfully built at: {db_path}")
    print(f"Total catalog files indexed: {total_files} / 956")
    print(f"Total rows inserted: {total_data_rows} / 22525")
    print(f"Database size: {os.path.getsize(db_path) / (1024*1024):.2f} MB")

if __name__ == "__main__":
    build_full_sqlite()
