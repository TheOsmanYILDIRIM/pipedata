#!/usr/bin/env python3
import os, sys, json, csv, re, sqlite3

def parse_nps(val_str):
    if not val_str or str(val_str).upper() == "N/A": return "", None
    val_str = str(val_str).strip()
    date_map = {
        "01/02/2001": ("1/2", 0.5), "01-Feb": ("1/2", 0.5),
        "03/04/2001": ("3/4", 0.75), "03-Apr": ("3/4", 0.75),
        "01/04/2001": ("1/4", 0.25), "01-Apr": ("1/4", 0.25),
        "01/08/2001": ("1/8", 0.125), "01-Aug": ("1/8", 0.125),
        "03/08/2001": ("3/8", 0.375), "03-Aug": ("3/8", 0.375),
        "05/08/2001": ("5/8", 0.625), "05-Aug": ("5/8", 0.625),
        "07/08/2001": ("7/8", 0.875), "07-Aug": ("7/8", 0.875)
    }
    if val_str in date_map: return date_map[val_str]
    m = re.match(r"^(\d+)\+(\d+)/(\d+)$", val_str)
    if m:
        w, num, den = int(m.group(1)), int(m.group(2)), int(m.group(3))
        return f"{num}/{den}" if w == 0 else f"{w} {num}/{den}", w + num / den
    m = re.match(r"^(\d+)/(\d+)$", val_str)
    if m:
        num, den = int(m.group(1)), int(m.group(2))
        return f"{num}/{den}", num / den
    try:
        dec = float(val_str)
        if dec.is_integer(): return str(int(dec)), dec
        return val_str, dec
    except: return val_str, None

def parse_float(val_str):
    if not val_str or str(val_str).upper() == "N/A" or str(val_str).strip() == "": return None
    try: return float(str(val_str).replace(",", "").replace("#", "").strip())
    except ValueError: return None

def get_col(row, idx):
    if row and idx is not None and 0 <= idx < len(row):
        v = row[idx].strip()
        return v if v != "" and v.upper() != "N/A" else None
    return None

def build_full_sqlite():
    base_dir = "/data/data/com.termux/files/home/projects/pipedata"
    raw_csv_dir = os.path.join(base_dir, "data", "csv", "raw")
    svg_dir = os.path.join(base_dir, "data", "svg")
    manifest_path = os.path.join(base_dir, "schema_manifest.json")
    db_path = os.path.join(base_dir, "pipedata.db")

    if os.path.exists(db_path): os.remove(db_path)
    conn = sqlite3.connect(db_path)
    cur = conn.cursor()
    cur.execute("PRAGMA journal_mode = WAL;")
    cur.execute("PRAGMA synchronous = NORMAL;")

    cur.execute('''CREATE TABLE tbl_catalog (
        file_id INTEGER PRIMARY KEY AUTOINCREMENT, filename TEXT UNIQUE NOT NULL, target_table TEXT NOT NULL,
        category TEXT NOT NULL, standard TEXT NOT NULL, unit_system TEXT NOT NULL, schedule_or_rating TEXT,
        row_count INTEGER NOT NULL, col_count INTEGER NOT NULL, headers_json TEXT NOT NULL
    );''')
    cur.execute('''CREATE TABLE tbl_all_components (
        id INTEGER PRIMARY KEY AUTOINCREMENT, file_id INTEGER NOT NULL, filename TEXT NOT NULL, target_table TEXT NOT NULL,
        category TEXT NOT NULL, standard TEXT NOT NULL, component_type TEXT, unit_system TEXT NOT NULL, nps TEXT, dn INTEGER,
        schedule TEXT, rating INTEGER, primary_dimension_mm REAL, primary_weight_kg REAL, raw_json TEXT NOT NULL
    );''')

    cur.execute('''CREATE TABLE tbl_svg_templates (
        id INTEGER PRIMARY KEY AUTOINCREMENT, filename TEXT UNIQUE NOT NULL, title TEXT,
        category TEXT, placeholder_count INTEGER, placeholders_json TEXT, svg_content TEXT NOT NULL, modern_svg_content TEXT
    );''')

    with open(manifest_path, "r") as f: manifest = json.load(f)
    for tname, tinfo in manifest["tables"].items():
        cols = [f"{c['name']} {c['type']}" for c in tinfo["columns"]]
        cols.append("source_file TEXT")
        cols.append("unit_system TEXT")
        cols.append("raw_json TEXT")
        cur.execute(f"CREATE TABLE {tname} (\n    {',\n    '.join(cols)}\n);")

    if os.path.exists(svg_dir):
        svg_files = [f for f in os.listdir(svg_dir) if f.endswith(".svg")]
        for sf in svg_files:
            with open(os.path.join(svg_dir, sf), "r", encoding="utf-8", errors="ignore") as f: content = f.read()
            placeholders = re.findall(r"\[\[([a-zA-Z0-9_]+)\]\]", content)
            unique_ph = list(dict.fromkeys(placeholders))
            title = sf.replace(".svg", "").replace("_", " ").title()
            cur.execute('''INSERT INTO tbl_svg_templates (filename, title, category, placeholder_count, placeholders_json, svg_content, modern_svg_content)
                VALUES (?, ?, ?, ?, ?, ?, ?)''', (sf, title, "General", len(unique_ph), json.dumps(unique_ph), content, content))

    tables_def = manifest["tables"]
    for tname, tdef in tables_def.items():
        for sf in tdef.get("source_files", []):
            fname = sf["filename"]
            csv_path = os.path.join(raw_csv_dir, fname)
            unit_sys = sf.get("unit_system", "Metric")
            rating_sch = sf.get("rating_or_sch", "")
            standard = ", ".join(tdef.get("standards", []))
            category = tdef.get("category_key", "")

            if not os.path.exists(csv_path): continue
            with open(csv_path, "r", encoding="latin1") as f:
                rows = list(csv.reader(f))
            header_names = rows[1] if len(rows) > 1 else []
            data_rows = [r for r in rows[2:] if r and any(cell.strip() for cell in r)] if len(rows) > 2 else []

            cur.execute('''INSERT INTO tbl_catalog (filename, target_table, category, standard, unit_system, schedule_or_rating, row_count, col_count, headers_json)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', (fname, tname, category, standard, unit_sys, rating_sch, len(data_rows), len(header_names), json.dumps(header_names)))
            file_id = cur.lastrowid

            for row in data_rows:
                raw_nps, nps_dec = parse_nps(row[0])
                dn_val = parse_float(get_col(row, 1))
                raw_json = json.dumps(row)
                hmap = {h.strip().lower(): idx for idx, h in enumerate(header_names)}
                
                def getv(*keys):
                    for k in keys:
                        if k in hmap:
                            v = get_col(row, hmap[k])
                            if v: return parse_float(v)
                    return None
                    
                def gets(*keys):
                    for k in keys:
                        if k in hmap:
                            v = get_col(row, hmap[k])
                            if v: return v
                    return None

                ctype = tname
                od_val = None
                wt_val = None
                sch_val = rating_sch
                rating_val = None

                if tname == "tbl_pipes":
                    ctype = "Pipe"
                    # extract XYZ from pipe_imperial_XYZ.csv or pipeXYZ.csv
                    sm = re.search(r'(?:pipe_imperial_|pipe)([a-z0-9]+)\.csv', fname.lower())
                    sch_val = sm.group(1).upper() if sm else "STD"

                    od_val = parse_float(get_col(row, 2))
                    thk = parse_float(get_col(row, 3))
                    id_val = parse_float(get_col(row, 5))
                    wt_val = parse_float(get_col(row, 6))
                    wt_water = parse_float(get_col(row, 7))
                    
                    od_mm = od_val if unit_sys == "Metric" else (od_val * 25.4 if od_val else 0.0)
                    od_in = od_val if unit_sys == "Imperial" else (od_val / 25.4 if od_val else 0.0)
                    thk_mm = thk if unit_sys == "Metric" else (thk * 25.4 if thk else 0.0)
                    thk_in = thk if unit_sys == "Imperial" else (thk / 25.4 if thk else 0.0)
                    id_mm = id_val if unit_sys == "Metric" else (id_val * 25.4 if id_val else 0.0)
                    id_in = id_val if unit_sys == "Imperial" else (id_val / 25.4 if id_val else 0.0)
                    wt_kg = wt_val if unit_sys == "Metric" else (wt_val * 1.48816 if wt_val else 0.0)
                    wt_lb = wt_val if unit_sys == "Imperial" else (wt_val / 1.48816 if wt_val else 0.0)
                    ww_kg = wt_water if unit_sys == "Metric" else (wt_water * 1.48816 if wt_water else 0.0)
                    ww_lb = wt_water if unit_sys == "Imperial" else (wt_water / 1.48816 if wt_water else 0.0)
                    
                    cur.execute('''INSERT INTO tbl_pipes (nps, nps_decimal, dn, schedule, outer_diameter_mm, outer_diameter_in, wall_thickness_mm, wall_thickness_in, inner_diameter_mm, inner_diameter_in, weight_empty_kg_m, weight_empty_lb_ft, weight_water_kg_m, weight_water_lb_ft, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (raw_nps, nps_dec or 0.0, int(dn_val) if dn_val else 0, sch_val, od_mm or 0.0, od_in or 0.0, thk_mm or 0.0, thk_in or 0.0, id_mm or 0.0, id_in or 0.0, wt_kg or 0.0, wt_lb or 0.0, ww_kg or 0.0, ww_lb or 0.0, fname, unit_sys, raw_json))

                elif tname == "tbl_fittings_buttweld_b16_9":
                    ctype = "Buttweld Fitting"
                    if "cap" in fname: ctype = "Cap"
                    elif "45elbow" in fname or "bw3" in fname: ctype = "45° Elbow"
                    elif "90elbow" in fname or "bw1" in fname or "elbow" in fname: ctype = "90° Elbow"
                    elif "tee" in fname or "bw7" in fname or "bw8" in fname: ctype = "Tee"
                    elif "reducer" in fname or "bw5" in fname or "bw6" in fname: ctype = "Reducer"
                    sm = re.search(r'(sch[a-z0-9]+|std|xs|xxs)', fname.lower())
                    sch_val = sm.group(1).upper().replace('SCH', '') if sm else "STD"
                    od_val = getv('od') or parse_float(get_col(row, 2))
                    wt_val = getv('wt', 'weight') or parse_float(row[-1] if row else None)
                    c2e = getv('center to end', 'a', 'over cap-e') or parse_float(get_col(row, 4))
                    if "cap" in fname: wt_val = parse_float(row[-1])
                    cur.execute('''INSERT INTO tbl_fittings_buttweld_b16_9 (component_type, nps, dn, schedule, outer_diameter_mm, center_to_end_mm, center_to_center_mm, back_to_face_mm, overall_length_height_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, raw_nps, int(dn_val) if dn_val else 0, sch_val, od_val, c2e, getv('center to center', 'c') or parse_float(get_col(row, 5)), getv('back to face', 'b') or parse_float(get_col(row, 6)), getv('length', 'l') or parse_float(get_col(row, 8)), wt_val, fname, unit_sys, raw_json))

                elif tname == "tbl_flanges_asme_b16_5":
                    rm = re.search(r'(\d+)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    od_val = getv('od')
                    wt_val = getv('wn kg', 'so kg')
                    types = []
                    wn_t = getv('wn thk', 'thickness')
                    if wn_t: types.append(("Welding Neck", wn_t, getv('wn kg')))
                    so_t = getv('thickness')
                    if so_t: types.append(("Slip On", so_t, getv('so kg')))
                    bl_t = getv('bld thk') or so_t
                    if bl_t: types.append(("Blind", bl_t, getv('bld kg')))
                    if not types: types.append(("Flange B16.5", so_t, wt_val))
                    for flg_type, flg_thk, flg_wt in types:
                        cur.execute('''INSERT INTO tbl_flanges_asme_b16_5 (nps, dn, rating, flange_type, facing, outer_diameter_mm, thickness_mm, hub_diameter_mm, length_thru_hub_mm, bore_diameter_mm, rf_diameter_mm, pcd_mm, num_bolts, bolt_diameter_in, bolt_hole_diameter_mm, stud_bolt_length_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                            (raw_nps, int(dn_val) if dn_val else 0, rating_val, flg_type, "RF", od_val or 0.0, flg_thk or 0.0, getv('hub-x'), getv('wn thk'), getv('so bore', 'id'), getv('rf dia'), getv('pcd') or 0.0, int(getv('no of bolts') or 4), gets('bolt size unc') or '3/4"', getv('hole size') or 0.0, getv('c bolt length'), flg_wt, fname, unit_sys, raw_json))

                elif tname == "tbl_valves_asme_b16_10":
                    ctype = "Valve"
                    if "gate" in fname or "vlv1" in fname: ctype = "Gate Valve"
                    elif "globe" in fname or "vlv2" in fname: ctype = "Globe Valve"
                    elif "ball" in fname or "vlv4" in fname: ctype = "Ball Valve"
                    elif "check" in fname or "vlv3" in fname: ctype = "Check Valve"
                    elif "butterfly" in fname or "vlv5" in fname: ctype = "Butterfly Valve"
                    elif "psv" in fname or "vlv6" in fname: ctype = "Safety Valve"
                    rm = re.search(r'(150|300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    wt_val = parse_float(row[-1]) if row else 0.0
                    od_val = getv('l (rf/bw)', 'l')
                    cur.execute('''INSERT INTO tbl_valves_asme_b16_10 (valve_type, end_connection, nps, dn, rating, face_to_face_mm, handwheel_height_mm, handwheel_diameter_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, "RF", raw_nps, int(dn_val) if dn_val else 0, rating_val, od_val, getv('height', 'open'), getv('dia', 'hw dia'), wt_val, fname, unit_sys, raw_json))

                elif tname == "tbl_line_blanks_asme_b16_48":
                    ctype = "Figure-8 Blind"
                    if "paddle" in fname: ctype = "Paddle Blank"
                    elif "spacer" in fname: ctype = "Paddle Spacer"
                    rm = re.search(r'(150|300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    od_val = getv('od')
                    cur.execute('''INSERT INTO tbl_line_blanks_asme_b16_48 (blank_type, facing, nps, dn, rating, outer_diameter_mm, thickness_mm, weight_blank_kg, weight_spacer_kg, weight_spectacle_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, "RF", raw_nps, int(dn_val) if dn_val else 0, rating_val, od_val, getv('thickness', 'thk'), getv('wt'), getv('spacer wt'), getv('spectacle wt'), fname, unit_sys, raw_json))

                elif tname == "tbl_gaskets_and_rtj":
                    ctype = "Gasket"
                    if "rtj" in fname: ctype = "RTJ Ring"
                    elif "sw" in fname or "spw" in fname: ctype = "Spiral Wound"
                    elif "flt" in fname: ctype = "Flat Ring"
                    rm = re.search(r'(150|300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    od_val = getv('od')
                    cur.execute('''INSERT INTO tbl_gaskets_and_rtj (gasket_type, applicable_standard, nps, dn, rating, inner_diameter_mm, outer_diameter_mm, thickness_mm, ring_number, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, "ASME B16.20", raw_nps, int(dn_val) if dn_val else 0, rating_val, getv('id'), od_val, getv('thickness', 'thk'), gets('ring no', 'ring'), getv('wt', 'weight'), fname, unit_sys, raw_json))

                elif tname == "tbl_fittings_threaded_b16_11":
                    rm = re.search(r'(2000|3000|6000)', fname)
                    rating_val = int(rm.group(1)) if rm else 3000
                    wt_val = parse_float(row[-1]) if row else 0.0
                    cur.execute('''INSERT INTO tbl_fittings_threaded_b16_11 (component_type, nps, dn, rating, center_to_end_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        ("Threaded Fitting", raw_nps, int(dn_val) if dn_val else 0, rating_val, getv('a', 'center to end'), wt_val, fname, unit_sys, raw_json))

                elif tname == "tbl_fittings_socketweld_b16_11":
                    rm = re.search(r'(3000|6000|9000)', fname)
                    rating_val = int(rm.group(1)) if rm else 3000
                    wt_val = parse_float(row[-1]) if row else 0.0
                    cur.execute('''INSERT INTO tbl_fittings_socketweld_b16_11 (component_type, nps, dn, rating, center_to_end_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        ("Socketweld Fitting", raw_nps, int(dn_val) if dn_val else 0, rating_val, getv('a', 'center to end'), wt_val, fname, unit_sys, raw_json))

                elif tname == "tbl_flanges_asme_b16_47":
                    rm = re.search(r'(75|150|300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    series = "Series B" if "b" in fname.lower() else "Series A"
                    od_val = getv('od')
                    cur.execute('''INSERT INTO tbl_flanges_asme_b16_47 (series, nps, dn, rating, flange_type, outer_diameter_mm, thickness_mm, pcd_mm, num_bolts, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (series, raw_nps, int(dn_val) if dn_val else 0, rating_val, "Large Diameter Flange", od_val or 0.0, getv('thickness') or 0.0, getv('pcd') or 0.0, int(getv('no of bolts') or 4), getv('wn kg', 'so kg'), fname, unit_sys, raw_json))

                elif tname == "tbl_flanges_din_en_1092":
                    rm = re.search(r'pn(\d+)', fname.lower())
                    pn_val = int(rm.group(1)) if rm else 16
                    od_val = getv('od')
                    cur.execute('''INSERT INTO tbl_flanges_din_en_1092 (dn, pn_bar, outer_diameter_mm, pcd_mm, num_bolts, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (int(dn_val) if dn_val else 0, pn_val, od_val, getv('pcd'), int(getv('no of bolts') or 4), getv('wt', 'weight'), fname, unit_sys, raw_json))

                elif tname == "tbl_flanges_api_6a":
                    rm = re.search(r'(2000|3000|5000|10000|15000|20000)', fname)
                    rating_val = int(rm.group(1)) if rm else 5000
                    od_val = getv('od')
                    cur.execute('''INSERT INTO tbl_flanges_api_6a (nominal_size, rating_psi, flange_type, outer_diameter_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (raw_nps, rating_val, "API Flange", od_val, getv('wt', 'weight'), fname, unit_sys, raw_json))

                elif tname == "tbl_flanges_orifice_b16_36":
                    rm = re.search(r'(300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 300
                    od_val = getv('od')
                    cur.execute('''INSERT INTO tbl_flanges_orifice_b16_36 (nps, rating, flange_type, outer_diameter_mm, thickness_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (raw_nps, rating_val, "Orifice Flange", od_val, getv('thickness', 'thk'), getv('wt', 'weight'), fname, unit_sys, raw_json))

                elif tname == "tbl_flanges_branch_connections":
                    rm = re.search(r'(150|300|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    cur.execute('''INSERT INTO tbl_flanges_branch_connections (branch_type, run_size, branch_size, rating, height_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        ("Branch Flange", raw_nps, raw_nps, rating_val, getv('height'), getv('wt'), fname, unit_sys, raw_json))

                elif tname == "tbl_fittings_swages_nipples":
                    cur.execute('''INSERT INTO tbl_fittings_swages_nipples (swage_type, large_end_nps, small_end_nps, length_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?)''', 
                        ("Swage/Nipple", raw_nps, raw_nps, getv('length'), getv('wt'), fname, unit_sys, raw_json))

                elif tname == "tbl_branch_outlets_mss_sp_97":
                    ctype = "Weldolet" if "weldolet" in fname.lower() else "Sockolet" if "sockolet" in fname.lower() else "Thredolet" if "thredolet" in fname.lower() else "Olet"
                    cur.execute('''INSERT INTO tbl_branch_outlets_mss_sp_97 (outlet_type, run_size_nps, branch_size_nps, rating_or_sch, height_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, raw_nps, raw_nps, sch_val, getv('height'), getv('wt'), fname, unit_sys, raw_json))

                elif tname == "tbl_bolting_and_fasteners":
                    cur.execute('''INSERT INTO tbl_bolting_and_fasteners (bolt_diameter_in, threads_per_inch, nut_width_across_flats_mm, nut_thickness_mm, weight_per_bolt_kg_per_100, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (raw_nps, int(parse_float(get_col(row, 8))) if get_col(row, 8) and parse_float(get_col(row, 8)) else None, parse_float(get_col(row, 5)) or 0.0, getv('thickness'), parse_float(row[-1]) if row else 0.0, fname, unit_sys, raw_json))

                elif tname == "tbl_misc_data":
                    cur.execute('''INSERT INTO tbl_misc_data (config_name, raw_data, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?)''', 
                        (fname, json.dumps(row), fname, unit_sys, raw_json))

                cur.execute('''INSERT INTO tbl_all_components (file_id, filename, target_table, category, standard, component_type, unit_system, nps, dn, schedule, rating, primary_dimension_mm, primary_weight_kg, raw_json)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', (file_id, fname, tname, category, standard, ctype, unit_sys, raw_nps, int(dn_val) if dn_val else 0, sch_val, rating_val, od_val, wt_val, raw_json))
                
    cur.execute('''CREATE VIEW IF NOT EXISTS vw_database_summary AS
        SELECT 'tbl_pipes' AS table_name, count(*) as row_count FROM tbl_pipes
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
    ''')
    conn.commit()
    conn.close()
    print("Database built perfectly.")

if __name__ == "__main__": build_full_sqlite()
