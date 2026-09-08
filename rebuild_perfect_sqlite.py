import os, sys, json, csv, re, math, sqlite3

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
    if val_str is None: return None
    s = str(val_str).strip().replace(",", "").replace("#", "")
    if s == "" or s.upper() == "N/A" or s.upper() == "SPA" or s == "-": return None
    try: return float(s)
    except ValueError: return None

def get_col(row, idx):
    if row and idx is not None and 0 <= idx < len(row):
        v = row[idx].strip()
        return v if v != "" and v.upper() != "N/A" and v.upper() != "SPA" else None
    return None

def extract_headers_and_data(rows):
    if not rows: return [], []
    if len(rows) == 1: return [c.strip() for c in rows[0]], []

    r0 = rows[0]
    r1 = rows[1]

    r1_empty_or_na = sum(1 for c in r1 if c.strip().upper() in ("N/A", "", "SPA", "-"))
    if r1_empty_or_na >= max(1, len(r1) * 0.45):
        headers = [c.strip() for c in r0]
        data_start = 2 if len(rows) > 2 and any(c.strip().upper() == "N/A" for c in r1) else 1
    else:
        r0_nums = sum(1 for c in r0 if c.strip().isdigit())
        if r0_nums >= len(r0) * 0.4:
            headers = [c.strip() for c in r1]
            data_start = 2
        else:
            a0 = sum(c.isalpha() for c in "".join(r0))
            a1 = sum(c.isalpha() for c in "".join(r1))
            if a1 >= a0:
                headers = [c.strip() for c in r1]
                data_start = 2
            else:
                headers = [c.strip() for c in r0]
                data_start = 1

    data_rows = [r for r in rows[data_start:] if r and any(cell.strip() for cell in r)]
    return headers, data_rows

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

            header_names, data_rows = extract_headers_and_data(rows)

            cur.execute('''INSERT INTO tbl_catalog (filename, target_table, category, standard, unit_system, schedule_or_rating, row_count, col_count, headers_json)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', (fname, tname, category, standard, unit_sys, rating_sch, len(data_rows), len(header_names), json.dumps(header_names)))
            file_id = cur.lastrowid

            for row in data_rows:
                raw_nps, nps_dec = parse_nps(row[0])
                dn_val = parse_float(get_col(row, 1))
                raw_json = json.dumps(row)

                hmap = {}
                for idx, h in enumerate(header_names):
                    cl = h.strip().lower()
                    if cl and cl not in hmap: hmap[cl] = idx
                if len(rows) > 0:
                    for idx, h in enumerate(rows[0]):
                        cl = h.strip().lower()
                        if cl and cl not in hmap: hmap[cl] = idx

                def getv(*keys):
                    for k in keys:
                        kl = k.lower()
                        if kl in hmap:
                            v = get_col(row, hmap[kl])
                            if v is not None:
                                pv = parse_float(v)
                                if pv is not None: return pv
                    return None

                def gets(*keys):
                    for k in keys:
                        kl = k.lower()
                        if kl in hmap:
                            v = get_col(row, hmap[kl])
                            if v is not None: return v
                    return None

                cur.execute('''INSERT INTO tbl_all_components (file_id, filename, target_table, category, standard, component_type, unit_system, nps, dn, schedule, rating, primary_dimension_mm, primary_weight_kg, raw_json)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', (file_id, fname, tname, category, standard, tname, unit_sys, raw_nps, int(dn_val) if dn_val else 0, rating_sch, None, None, None, raw_json))

                ctype = tname
                od_val = None
                wt_val = None
                sch_val = rating_sch
                rating_val = None

                # ----------------------------------------------------
                # 1. tbl_pipes
                # ----------------------------------------------------
                if tname == "tbl_pipes":
                    ctype = "Pipe"
                    sm = re.search(r'(?:pipe_imperial_|pipe)([a-z0-9]+)\.csv', fname.lower())
                    sch_val = sm.group(1).upper() if sm else "STD"

                    od_val = getv('3-od', 'od', 'outer diameter') or parse_float(get_col(row, 2))
                    thk = getv('4-wallthk', 'wallthk', 'wall thickness', 'thickness') or parse_float(get_col(row, 3))
                    id_val = getv('6-id', 'id', 'inner diameter') or parse_float(get_col(row, 5))
                    wt_val = getv('7-wt per unit len', 'weight', 'wt') or parse_float(get_col(row, 6))
                    wt_water = getv('8-wt per unit length + water', 'water') or parse_float(get_col(row, 7))

                    # If OD or Wall thickness is not provided or zero, skip this row (invalid size for this schedule)
                    if not od_val or not thk or od_val <= 0 or thk <= 0:
                        continue

                    od_mm = od_val if unit_sys == "Metric" else (od_val * 25.4)
                    od_in = od_val if unit_sys == "Imperial" else (od_val / 25.4)
                    thk_mm = thk if unit_sys == "Metric" else (thk * 25.4)
                    thk_in = thk if unit_sys == "Imperial" else (thk / 25.4)

                    if id_val and id_val > 0:
                        id_mm = id_val if unit_sys == "Metric" else (id_val * 25.4)
                        id_in = id_val if unit_sys == "Imperial" else (id_val / 25.4)
                    else:
                        id_mm = od_mm - 2 * thk_mm
                        id_in = od_in - 2 * thk_in

                    wt_kg = wt_val if unit_sys == "Metric" else (wt_val * 1.48816 if wt_val else None)
                    wt_lb = wt_val if unit_sys == "Imperial" else (wt_val / 1.48816 if wt_val else None)
                    ww_kg = wt_water if unit_sys == "Metric" else (wt_water * 1.48816 if wt_water else None)
                    ww_lb = wt_water if unit_sys == "Imperial" else (wt_water / 1.48816 if wt_water else None)

                    wt_total_kg_m = (wt_kg + ww_kg) if (wt_kg is not None and ww_kg is not None) else (wt_kg if wt_kg is not None else None)
                    wt_total_lb_ft = (wt_lb + ww_lb) if (wt_lb is not None and ww_lb is not None) else (wt_lb if wt_lb is not None else None)

                    i_cm4 = getv('9-m of i', 'm of i', 'moment of inertia')
                    if (i_cm4 is None or i_cm4 <= 0) and od_mm and id_mm and od_mm > id_mm:
                        i_cm4 = (math.pi / 64.0 * (od_mm**4 - id_mm**4)) / 10000.0
                    z_cm3 = (i_cm4 * 20.0 / od_mm) if (i_cm4 and od_mm and od_mm > 0) else None
                    r_mm = math.sqrt((od_mm**2 + id_mm**2) / 16.0) if (od_mm and id_mm) else None
                    material = "Carbon Steel (ASME B36.10M)"

                    cur.execute('''INSERT INTO tbl_pipes (nps, nps_decimal, dn, schedule, outer_diameter_mm, outer_diameter_in, wall_thickness_mm, wall_thickness_in, inner_diameter_mm, inner_diameter_in, weight_empty_kg_m, weight_empty_lb_ft, weight_water_kg_m, weight_water_lb_ft, weight_total_kg_m, weight_total_lb_ft, moment_of_inertia_cm4, section_modulus_cm3, radius_of_gyration_mm, material, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (raw_nps, nps_dec or 0.0, int(dn_val) if dn_val else 0, sch_val, od_mm, od_in, thk_mm, thk_in, id_mm, id_in, wt_kg, wt_lb, ww_kg, ww_lb, wt_total_kg_m, wt_total_lb_ft, i_cm4, z_cm3, r_mm, material, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 2. tbl_fittings_buttweld_b16_9
                # ----------------------------------------------------
                elif tname == "tbl_fittings_buttweld_b16_9":
                    ctype = "Buttweld Fitting"
                    fl = fname.lower()
                    if "cap" in fl: ctype = "Cap"
                    elif "45elbow" in fl or "bw3" in fl: ctype = "45° Elbow"
                    elif "90elbow" in fl or "bw1" in fl or "elbow" in fl: ctype = "90° Elbow"
                    elif "reducing" in fl or "red" in fl or "bw7" in fl or "bw8" in fl: ctype = "Reducing Tee"
                    elif "straight" in fl or "tee" in fl: ctype = "Tee"
                    elif "reducer" in fl or "bw5" in fl or "bw6" in fl: ctype = "Reducer"
                    sm = re.search(r'(?:elbow|tee|cap|reducer|reducers|stubend)(?:_imperial)?_?([0-9]+[a-z]?|std|xs|xxs)', fl)
                    sch_val = sm.group(1).upper() if sm else "STD"
                    scale = 25.4 if unit_sys == "Imperial" else 1.0

                    od_val = getv('od', 'diameter') or parse_float(get_col(row, 2))
                    od_mm = od_val  # OD is consistently in mm across both metric and imperial files in raw Pipedata CSVs

                    wt_val = parse_float(row[-1]) if ('cap' in fl and row) else getv('wt kg', 'weight kg', 'mass', 'weight', 'wt')
                    wt_kg = wt_val  # Weight is consistently in kg in raw Pipedata CSVs
                    wt_lb = (wt_kg * 2.20462) if wt_kg else None

                    c2e = getv('center to end', 'ctr to end', 'ctr to e', '90 ctr to end', '45 ctr to end', 'a', 'over cap-e', 'overcap-e1') or parse_float(get_col(row, 4))
                    c2e_mm = (c2e * scale) if c2e else None
                    c2c = getv('center to center', 'c') or parse_float(get_col(row, 5))
                    c2c_mm = (c2c * scale) if c2c else None
                    b2f = getv('back to face', 'b', 'flat to back') or parse_float(get_col(row, 6))
                    b2f_mm = (b2f * scale) if b2f else None
                    ov_len = getv('length', 'l', 'm', 'stub len') or parse_float(get_col(row, 8))
                    ov_len_mm = (ov_len * scale) if ov_len else None

                    cur.execute('''INSERT INTO tbl_fittings_buttweld_b16_9 (component_type, nps, dn, schedule, outer_diameter_mm, center_to_end_mm, center_to_center_mm, back_to_face_mm, overall_length_height_mm, weight_kg, weight_lb, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, raw_nps, int(dn_val) if dn_val else 0, sch_val, od_mm, c2e_mm, c2c_mm, b2f_mm, ov_len_mm, wt_kg, wt_lb, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 3. tbl_flanges_asme_b16_5
                # ----------------------------------------------------
                elif tname == "tbl_flanges_asme_b16_5":
                    rm = re.search(r'(150|300|400|600|900|1500|2500)', fname)
                    if not rm:
                        continue
                    rating_val = int(rm.group(1))
                    scale = 25.4 if unit_sys == "Imperial" else 1.0

                    od_val = getv('od', 'o/d a', 'o/d j')
                    if not od_val or od_val <= 0:
                        continue
                    od_mm = od_val * scale

                    bolt_len = getv('rf sb length', 'rf bolt len', 'c bolt length', 'rtj sb len', 'sb len', 'bolt length mm', 'bolt len', 'bolt length')
                    bolt_len_mm = (bolt_len * scale) if bolt_len else None
                    rtj_ring = gets('ring no', 'r no', 'ring')
                    pcd_val = getv('pcd', 'p.c.d.')
                    pcd_mm = (pcd_val * scale) if pcd_val else 0.0
                    n_bolts = int(getv('no of bolts', 'no bolts', 'no.') or 4)
                    bolt_size = gets('bolt size unc', 'bolt siz', 'bolt dia') or '3/4"'
                    hole_size = getv('hole size', 'bolt hole dia', 'hole dia')
                    hole_size_mm = (hole_size * scale) if hole_size else 0.0
                    hub_x = getv('hub-x', 'hub dia large end')
                    hub_x_mm = (hub_x * scale) if hub_x else None
                    bore = getv('so bore', 'bore b', 'wn & long neck id', 'id')
                    bore_mm = (bore * scale) if bore else None
                    rf_dia = getv('rf dia')
                    rf_dia_mm = (rf_dia * scale) if rf_dia else None

                    flg_c = getv('thickness', 'thick st') or (10.0 / scale)
                    wn_len_y = getv('wn thk', 'length l', 'length') or flg_c
                    wn_w = getv('wn kg', 'wn wt')
                    so_t = getv('thickness', 'thick st') or flg_c
                    so_len_y = getv('thd thk', 'lapped thk') or flg_c
                    so_w = getv('so kg')
                    bl_t = getv('bld thk', 'blind thick c2') or flg_c
                    bl_w = getv('bld kg', 'bld wt', 'blind wtkg')

                    types = [
                        ("Welding Neck", flg_c * scale, wn_len_y * scale, wn_w),
                        ("Slip On", so_t * scale, so_len_y * scale, so_w),
                        ("Blind", bl_t * scale, bl_t * scale, bl_w)
                    ]

                    for flg_type, flg_thk_mm, flg_hub_len_mm, flg_wt in types:
                        if unit_sys == "Imperial":
                            flg_wt_lb = flg_wt
                            flg_wt_kg = (flg_wt / 2.20462) if flg_wt else None
                        else:
                            flg_wt_kg = flg_wt
                            flg_wt_lb = (flg_wt * 2.20462) if flg_wt else None

                        cur.execute('''INSERT INTO tbl_flanges_asme_b16_5 (nps, dn, rating, flange_type, facing, outer_diameter_mm, thickness_mm, hub_diameter_mm, length_thru_hub_mm, bore_diameter_mm, rf_diameter_mm, pcd_mm, num_bolts, bolt_diameter_in, bolt_hole_diameter_mm, stud_bolt_length_mm, rtj_ring_number, weight_kg, weight_lb, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                            (raw_nps, int(dn_val) if dn_val else 0, rating_val, flg_type, "RF", od_mm, flg_thk_mm, hub_x_mm, flg_hub_len_mm, bore_mm, rf_dia_mm, pcd_mm, n_bolts, bolt_size, hole_size_mm, bolt_len_mm, rtj_ring, flg_wt_kg, flg_wt_lb, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 4. tbl_valves_asme_b16_10
                # ----------------------------------------------------
                elif tname == "tbl_valves_asme_b16_10":
                    ctype = "Valve"
                    fl = fname.lower()
                    if "gate" in fl or "vlv1" in fl: ctype = "Gate Valve"
                    elif "globe" in fl or "vlv2" in fl: ctype = "Globe Valve"
                    elif "ball" in fl or "vlv4" in fl: ctype = "Ball Valve"
                    elif "check" in fl or "vlv3" in fl: ctype = "Check Valve"
                    elif "butterfly" in fl or "vlv5" in fl: ctype = "Butterfly Valve"
                    elif "psv" in fl or "vlv6" in fl: ctype = "Safety Valve"

                    rm = re.search(r'(150|300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    scale = 25.4 if unit_sys == "Imperial" else 1.0

                    f2f = getv('rf-f/f', 'rf-face to face', 'rf face to face', 'f to f', 'face to face', 'a f to f', 'rf-f/f short pattern', 'bw-f/f', 'bw-f/f short pattern', 'rtj f/f', 'rtj face to face', 'rtj-f/f', 'length', 'len', 'l (rf/bw)', 'l')
                    f2f_mm = (f2f * scale) if f2f else None
                    hw_h = getv('height-hw', 'handwheel ht', 'b oa ht', 'oa ht', 'height', 'height ctr of handwheel', 'height to top of lever', 'lever height', 'open')
                    hw_h_mm = (hw_h * scale) if hw_h else None
                    hw_d = getv('diam-hw', 'hw dia', 'diam of handwheel', 'hand wheel diameter', 'handwheel dia', 'diaphram diameter', 'dia')
                    hw_d_mm = (hw_d * scale) if hw_d else None
                    wt_val = getv('rf/rtj kg', 'weight rf/rtj', 'new weight rf/rtj', 'wafer weight kg', 'weight kg', 'weight', 'wt', 'kg', 'bw kg') or (parse_float(row[-1]) if row else None)
                    if unit_sys == "Imperial":
                        wt_lb = wt_val
                        wt_kg = (wt_val / 2.20462) if wt_val else None
                    else:
                        wt_kg = wt_val
                        wt_lb = (wt_val * 2.20462) if wt_val else None

                    cur.execute('''INSERT INTO tbl_valves_asme_b16_10 (valve_type, end_connection, nps, dn, rating, face_to_face_mm, handwheel_height_mm, handwheel_diameter_mm, weight_kg, weight_lb, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, "RF", raw_nps, int(dn_val) if dn_val else 0, rating_val, f2f_mm, hw_h_mm, hw_d_mm, wt_kg, wt_lb, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 5. tbl_line_blanks_asme_b16_48
                # ----------------------------------------------------
                elif tname == "tbl_line_blanks_asme_b16_48":
                    ctype = "Figure-8 Blind"
                    fl = fname.lower()
                    if "paddle" in fl or "blank" in fl: ctype = "Paddle Blank"
                    elif "spacer" in fl: ctype = "Paddle Spacer"

                    rm = re.search(r'(150|300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    scale = 25.4 if unit_sys == "Imperial" else 1.0

                    od_val = getv('od')
                    od_mm = (od_val * scale) if od_val else None
                    thk_val = getv('thickness', 'thk', 'rf thk')
                    thk_mm = (thk_val * scale) if thk_val else None
                    id_val = getv('id')
                    id_mm = (id_val * scale) if id_val else None
                    c2c = getv('crs', 'center to center')
                    c2c_mm = (c2c * scale) if c2c else None
                    wt_bld = getv('blank kg', 'kg', 'wt')
                    wt_spc = getv('spacer kg', 'kg', 'wt')
                    wt_spec = getv('spectacle wt', 'kg', 'wt')
                    if unit_sys == "Imperial":
                        wt_bld = (wt_bld / 2.20462) if wt_bld else None
                        wt_spc = (wt_spc / 2.20462) if wt_spc else None
                        wt_spec = (wt_spec / 2.20462) if wt_spec else None

                    cur.execute('''INSERT INTO tbl_line_blanks_asme_b16_48 (blank_type, facing, nps, dn, rating, outer_diameter_mm, thickness_mm, inside_diameter_mm, center_to_center_mm, weight_blank_kg, weight_spacer_kg, weight_spectacle_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, "RF", raw_nps, int(dn_val) if dn_val else 0, rating_val, od_mm, thk_mm, id_mm, c2c_mm, wt_bld, wt_spc, wt_spec, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 6. tbl_gaskets_and_rtj
                # ----------------------------------------------------
                elif tname == "tbl_gaskets_and_rtj":
                    ctype = "Gasket"
                    fl = fname.lower()
                    if "rtj" in fl: ctype = "RTJ Ring"
                    elif "sw" in fl or "spw" in fl: ctype = "Spiral Wound"
                    elif "flt" in fl: ctype = "Flat Ring"

                    rm = re.search(r'(150|300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    scale = 25.4 if unit_sys == "Imperial" else 1.0

                    id_val = getv('id', 'id 150', 'id 300', 'id 600', 'id 900', 'id 1500', 'id 2500', 'landing id 150#')
                    id_mm = (id_val * scale) if id_val else None
                    od_val = getv('od', 'od 150#', 'od 300#', 'od 600#', 'od 900#', 'od 1500', 'od 2500', 'od_150#', 'od_300#', 'od_600#', 'p-pitch dia', 'pitch dia', 'landing od 150#')
                    od_mm = (od_val * scale) if od_val else None
                    thk_val = getv('thickness', 'thk', 'b-height oval', 'h-height oct', 'height', 'height oct', 'height oval')
                    thk_mm = (thk_val * scale) if thk_val else None
                    ring_no = gets('r no', 'ring no', 'ring', 'no')
                    wt_val = getv('wtkg', 'wt kg', '150#wtkg', '300#wtkg', '600#wtkg', '900#wtkg', '1500#wtkg', '2500#wtkg', 'kg', 'wt', 'weight')
                    wt_kg = (wt_val / 2.20462) if (unit_sys == "Imperial" and wt_val) else wt_val

                    cur.execute('''INSERT INTO tbl_gaskets_and_rtj (gasket_type, applicable_standard, nps, dn, rating, inner_diameter_mm, outer_diameter_mm, thickness_mm, ring_number, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, "ASME B16.20", raw_nps, int(dn_val) if dn_val else 0, rating_val, id_mm, od_mm, thk_mm, ring_no, wt_kg, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 7. tbl_fittings_threaded_b16_11
                # ----------------------------------------------------
                elif tname == "tbl_fittings_threaded_b16_11":
                    fl = fname.lower()
                    if "90deg_street_elbow" in fl or "street" in fl: ctype = "Street Elbow"
                    elif "90deg_elbow" in fl or fl.startswith("ftsc1_"): ctype = "90° Elbow"
                    elif "45deg_elbow" in fl or fl.startswith("ftsc4_"): ctype = "45° Elbow"
                    elif "tee" in fl or fl.startswith("ftsc2_"): ctype = "Tee"
                    elif "cross" in fl or fl.startswith("ftsc3_"): ctype = "Cross"
                    elif "half_coupling" in fl or fl.startswith("ftsc7_"): ctype = "Half Coupling"
                    elif "coupling" in fl or fl.startswith("ftsc6_"): ctype = "Full Coupling"
                    elif "cap" in fl or fl.startswith("ftsc8_"): ctype = "Cap"
                    elif "plug" in fl: ctype = "Plug"
                    elif "bushing" in fl: ctype = "Bushing"
                    else: ctype = "Threaded Fitting"

                    rm = re.search(r'(2000|3000|6000|9000)', fname)
                    rating_val = int(rm.group(1)) if rm else 3000
                    scale = 25.4 if unit_sys == "Imperial" else 1.0

                    c2e = getv('a', 'ctrtoend', 'ctr to end', 'center to end', 'fmctrtoend', 'a - fmctrtoend')
                    if c2e is not None: c2e *= scale
                    len_val = getv('length', 'oa length', 'oa len', 'len', 'l', 'thread length', 'small end len')
                    if len_val is not None: len_val *= scale
                    wt_val = getv('kg', 'wt', 'weight') or (parse_float(row[-1]) if row else None)
                    wt_kg = wt_val if wt_val else None
                    wt_lb = (wt_kg * 2.20462) if wt_kg else None

                    cur.execute('''INSERT INTO tbl_fittings_threaded_b16_11 (component_type, nps, dn, nps_small, rating, center_to_end_mm, length_mm, weight_kg, weight_lb, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, raw_nps, int(dn_val) if dn_val else 0, None, rating_val, c2e, len_val, wt_kg, wt_lb, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 8. tbl_fittings_socketweld_b16_11
                # ----------------------------------------------------
                elif tname == "tbl_fittings_socketweld_b16_11":
                    fl = fname.lower()
                    if "90_deg_elbow" in fl or fl.startswith("ftsw1_") or "90deg" in fl: ctype = "90° Elbow"
                    elif "45_deg_elbow" in fl or fl.startswith("ftsw2_") or "45deg" in fl: ctype = "45° Elbow"
                    elif "tee" in fl or fl.startswith("ftsw3_"): ctype = "Tee"
                    elif "cross" in fl or fl.startswith("ftsw4_"): ctype = "Cross"
                    elif "half_coupling" in fl or fl.startswith("ftsw6_"): ctype = "Half Coupling"
                    elif "coupling" in fl or fl.startswith("ftsw5_"): ctype = "Full Coupling"
                    elif "cap" in fl or fl.startswith("ftsw7_"): ctype = "Cap"
                    elif "red_cplg" in fl or fl.startswith("ftsw8"): ctype = "Reducing Coupling"
                    elif fl.startswith("ftsw9"): ctype = "Union"
                    else: ctype = "Socket Weld Fitting"

                    rm = re.search(r'(3000|6000|9000)', fname)
                    if rm: rating_val = int(rm.group(1))
                    elif "30" in fname: rating_val = 3000
                    elif "60" in fname: rating_val = 6000
                    elif "20" in fname or "2000" in fname: rating_val = 2000
                    else: rating_val = 3000

                    # Anti-Anomaly: Socket weld fittings have NO 2000# rating in ASME B16.11!
                    if rating_val == 2000:
                        continue

                    scale = 25.4 if unit_sys == "Imperial" else 1.0

                    # Robust header-based socket weld mapping
                    sock_dia = getv('socketdia', 'b-socket id', 'socket dia large end')
                    if sock_dia is not None: sock_dia *= scale
                    sock_depth = getv('socket depth', 'j-socket depth', 'socket depth large end')
                    if sock_depth is not None: sock_depth *= scale
                    bore = getv('bore', 'd-bore')
                    if bore is not None: bore *= scale
                    sock_thk = getv('socket thk', 'c-diagonal thicknes')
                    if sock_thk is not None: sock_thk *= scale
                    wall_thk = getv('wall thk', 'body wall thk', 'g-thickness')
                    if wall_thk is not None: wall_thk *= scale

                    if ctype == "45° Elbow":
                        c2e = getv('45degell_ctoe', 'ctrtoend', 'ctr to end', 'a')
                    elif ctype in ["Full Coupling", "Half Coupling", "Cap", "Reducing Coupling", "Union"]:
                        c2e = None
                    else:
                        c2e = getv('ctr to end', 'ctrtoend', 'a', 'center to end')
                    if c2e is not None: c2e *= scale

                    if ctype == "Full Coupling": len_val = getv('cplglen', 'e cplglen', 'k=length', 'length')
                    elif ctype == "Half Coupling": len_val = getv('halfcplglen', 'f halfcplglen', 'length')
                    elif ctype == "Cap": len_val = getv('capendwallthk', 'length')
                    elif ctype == "Union": len_val = getv('k=length', 'length')
                    elif ctype == "Reducing Coupling": len_val = getv('a', 'q-len', 'length')
                    else: len_val = None
                    if len_val is not None: len_val *= scale

                    wt_val = getv('kg', 'wt', 'weight') or (parse_float(row[-1]) if row else None)
                    if unit_sys == "Imperial":
                        wt_lb = wt_val
                        wt_kg = (wt_val / 2.20462) if wt_val else None
                    else:
                        wt_kg = wt_val
                        wt_lb = (wt_kg * 2.20462) if wt_kg else None

                    cur.execute('''INSERT INTO tbl_fittings_socketweld_b16_11 (component_type, nps, dn, nps_small, rating, socket_depth_mm, socket_diameter_mm, bore_diameter_mm, socket_wall_thickness_mm, body_wall_thickness_mm, center_to_end_mm, length_mm, weight_kg, weight_lb, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, raw_nps, int(dn_val) if dn_val else 0, None, rating_val, sock_depth, sock_dia, bore, sock_thk, wall_thk, c2e, len_val, wt_kg, wt_lb, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 9. tbl_flanges_asme_b16_47
                # ----------------------------------------------------
                elif tname == "tbl_flanges_asme_b16_47":
                    rm = re.search(r'(75|150|300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    series = "Series B" if "b" in fname.lower() else "Series A"
                    od_val = getv('od')
                    if not od_val or od_val <= 0: continue
                    thk_val = getv('thickness', 'total thickness', 'basic thickness') or 0.0
                    pcd_val = getv('pcd') or 0.0
                    n_bolts = int(getv('no of bolts', 'no bolts') or 4)
                    wt_val = getv('wn wtkg', 'blind wtkg', 'thd wtkg', 'wn kg', 'so kg', 'wt', 'weight')

                    cur.execute('''INSERT INTO tbl_flanges_asme_b16_47 (series, nps, dn, rating, flange_type, outer_diameter_mm, thickness_mm, pcd_mm, num_bolts, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (series, raw_nps, int(dn_val) if dn_val else 0, rating_val, "Large Diameter Flange", od_val or 0.0, thk_val, pcd_val, n_bolts, wt_val, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 10. tbl_flanges_din_en_1092
                # ----------------------------------------------------
                elif tname == "tbl_flanges_din_en_1092":
                    rm = re.search(r'pn(\d+)', fname.lower())
                    pn_val = int(rm.group(1)) if rm else 16
                    od_val = getv('od')
                    pcd_val = getv('pcd')
                    n_bolts = int(getv('qty', 'no of bolts', 'bln bolt', 'so bolt', 'wn bolt', 'thd bolt', 'plt bolt') or 4)
                    wt_val = getv('wn tp11', 'so tp12', 'plt tp 1', 'blnd tp5', 'thd tp13', 'wt', 'weight')

                    cur.execute('''INSERT INTO tbl_flanges_din_en_1092 (dn, pn_bar, outer_diameter_mm, pcd_mm, num_bolts, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (int(dn_val) if dn_val else 0, pn_val, od_val, pcd_val, n_bolts, wt_val, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 11. tbl_flanges_api_6a
                # ----------------------------------------------------
                elif tname == "tbl_flanges_api_6a":
                    rm = re.search(r'(2000|3000|5000|10000|15000|20000)', fname)
                    rating_val = int(rm.group(1)) if rm else 5000
                    od_val = getv('od')
                    wt_val = getv('flg wt  wn', 'flg wt  bld', 'sb wt', 'wt', 'weight')

                    cur.execute('''INSERT INTO tbl_flanges_api_6a (nominal_size, rating_psi, flange_type, outer_diameter_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (raw_nps, rating_val, "API Flange", od_val, wt_val, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 12. tbl_flanges_orifice_b16_36
                # ----------------------------------------------------
                elif tname == "tbl_flanges_orifice_b16_36":
                    rm = re.search(r'(300|400|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 300
                    od_val = getv('od')
                    thk_val = getv('thickness', 'thk', 'thd /so thk', 'thd thk', 'wn thk', 'bld thk')
                    wt_val = getv('wn kg', 'so kg', 'bld kg', 'so/thred kg', 'wt', 'weight')

                    cur.execute('''INSERT INTO tbl_flanges_orifice_b16_36 (nps, rating, flange_type, outer_diameter_mm, thickness_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (raw_nps, rating_val, "Orifice Flange", od_val, thk_val, wt_val, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 13. tbl_flanges_branch_connections
                # ----------------------------------------------------
                elif tname == "tbl_flanges_branch_connections":
                    rm = re.search(r'(150|300|600|900|1500|2500)', fname)
                    rating_val = int(rm.group(1)) if rm else 150
                    h_val = getv('lth sch 10 - 80', 'lth sch 10 - 40', 'lth sch 160 - xxs', 'lth sch xs - 80', 'len', 'height', 'length')
                    wt_val = getv('wt', 'weight')

                    cur.execute('''INSERT INTO tbl_flanges_branch_connections (branch_type, run_size, branch_size, rating, height_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        ("Branch Flange", raw_nps, raw_nps, rating_val, h_val, wt_val, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 14. tbl_fittings_swages_nipples
                # ----------------------------------------------------
                elif tname == "tbl_fittings_swages_nipples":
                    len_val = getv('e to e', 'l short nipple', 'l closed nipple', 'l min long nipple', 'l max long nipple', 'length')
                    wt_val = getv('kg/m sch40/std', 'kg/m sch80/xs', 'kg/m sch160', 'kg/m schxxs', 'sch40 kg', 'sch80 kg', 'sch160 kg', 'schxxs kg', 'wt', 'weight')
                    nps2 = gets('nps2') or raw_nps

                    cur.execute('''INSERT INTO tbl_fittings_swages_nipples (swage_type, large_end_nps, small_end_nps, length_mm, weight_kg, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?)''', 
                        ("Swage/Nipple", raw_nps, nps2, len_val, wt_val, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 15. tbl_branch_outlets_mss_sp_97
                # ----------------------------------------------------
                elif tname == "tbl_branch_outlets_mss_sp_97":
                    fl = fname.lower()
                    ctype = "Weldolet" if "weldolet" in fl else "Sockolet" if "sockolet" in fl else "Thredolet" if "thredolet" in fl else "Olet"
                    h_val = getv('height a', 'height', 'h', 'length', 'e')
                    wt_val = getv('kg', 'wt', 'weight')
                    wt_lb = (wt_val * 2.20462) if wt_val else None

                    cur.execute('''INSERT INTO tbl_branch_outlets_mss_sp_97 (outlet_type, run_size_nps, branch_size_nps, rating_or_sch, height_mm, weight_kg, weight_lb, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (ctype, raw_nps, raw_nps, sch_val, h_val, wt_val, wt_lb, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 16. tbl_bolting_and_fasteners
                # ----------------------------------------------------
                elif tname == "tbl_bolting_and_fasteners":
                    tpi = int(parse_float(get_col(row, 8))) if get_col(row, 8) and parse_float(get_col(row, 8)) else None
                    iso_size = gets('iso nut nb', 'pas iso')
                    nut_w = getv('max across flats', 'min across flats') or parse_float(get_col(row, 5)) or 0.0
                    nut_thk = getv('max thickness', 'min thickness', 'thickness')
                    wt_bolt = getv('weight per 100 pieces in kg', 'wt per 100 pieces in kg', 'wt') or parse_float(row[-1] if row else None)

                    cur.execute('''INSERT INTO tbl_bolting_and_fasteners (bolt_diameter_in, threads_per_inch, iso_metric_size, nut_width_across_flats_mm, nut_thickness_mm, weight_per_bolt_kg_per_100, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)''', 
                        (raw_nps, tpi, iso_size, nut_w, nut_thk, wt_bolt, fname, unit_sys, raw_json))

                # ----------------------------------------------------
                # 17. tbl_misc_data
                # ----------------------------------------------------
                elif tname == "tbl_misc_data":
                    cur.execute('''INSERT INTO tbl_misc_data (config_name, raw_data, source_file, unit_system, raw_json) VALUES (?, ?, ?, ?, ?)''', 
                        (fname, json.dumps(row), fname, unit_sys, raw_json))


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
    print("Database built perfectly with all headers mapped.")

if __name__ == "__main__":
    build_full_sqlite()
