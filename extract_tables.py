#!/usr/bin/env python3
"""Pipedata Tek Adimda Kusursuz Tablo Cikarma Pipeline'i (extract_tables.py)
Kaynak: raw_apk/assets/ (956 CSV, B4A formati).
Cikti : app/src/main/assets/csv/<tablo>.csv
"""
import csv, io, os, re, sys, math

PROJECT = os.path.dirname(os.path.abspath(__file__))
SRC = os.path.join(PROJECT, "raw_apk", "assets")
OUT = os.path.join(PROJECT, "app", "src", "main", "assets", "csv")
os.makedirs(OUT, exist_ok=True)

def b4a_rows(path):
    """B4A LoadCSV: virgulle ayrilmis, cift tirnak destekli."""
    with open(path, "r", encoding="utf-8", errors="replace") as f:
        text = f.read().replace("\r\n", "\n").replace("\r", "\n")
    rows = list(csv.reader(io.StringIO(text)))
    rows = [r for r in rows if not (len(r) == 1 and r[0] == "")]
    if len(rows) < 2: return None, None, []
    first = [c.strip() for c in rows[0]]
    # B4A index header rows can contain empty column markers (e.g. reducersstd.csv).
    idx_like = (len(first) >= 2 and bool(re.fullmatch(r"\d+", first[0]))
                and all(c == "" or re.fullmatch(r"\d+", c) for c in first))
    ds = 1 if idx_like else 0
    if len(rows) <= ds: return None, None, []
    return rows[0] if idx_like else [], [c.strip() for c in rows[ds]], rows[ds + 1:]

def num(x):
    """Hucre degeri -> float / None."""
    if x is None: return None
    t = str(x).strip().strip("\u00a0")
    if t == "" or t.upper() in ("N/A", "NA", "SPA", "-", "--", "NULL", "NONE"): return None
    t = t.replace(",", "").strip()
    m = re.fullmatch(r"(\d+)\+(\d+)\s*/\s*(\d+)", t)
    if m: return float(m.group(1)) + float(m.group(2)) / float(m.group(3))
    m = re.fullmatch(r"(\d+)\s*/\s*(\d+)", t)
    if m: return float(m.group(1)) / float(m.group(2))
    try: return float(t)
    except ValueError: return None

def nps_norm(frac):
    """0+1/2 -> 1/2, 1+1/4 -> 1 1/4, 14 -> 14."""
    if frac is None: return None
    t = str(frac).strip()
    if t.upper() in ("N/A", "", "NONE", "NULL"): return None
    if re.fullmatch(r"\d+", t): return t
    m = re.fullmatch(r"(\d+)\+(.+)", t)
    if m:
        w, f = m.group(1), m.group(2).strip()
        return f if w == "0" else f"{w} {f}"
    return t

def pick(row_ctx, names):
    want = set(n.strip().lower() for n in names)
    for i, h in enumerate(row_ctx["_h"]):
        if h in want and i < len(row_ctx["_v"]):
            return row_ctx["_v"][i]
    return None

def build_rowctx(header, values):
    return {"_h": [h.strip().lower() for h in header], "_v": values}

# ==============================================================================
# 1. FLANGES (ASME B16.5 & B16.47)
# ==============================================================================
def extract_flanges():
    out = []
    for f in sorted(os.listdir(SRC)):
        fn = f.lower()
        if not fn.endswith(".csv") or "imperial" in fn: continue
        
        rating = None
        m_flg = re.match(r"flg(1500|2500|150|300|400|600|900)\.csv", fn)
        m_fla = re.match(r"fla(1500|2500|150|300|400|600|900)\.csv", fn)
        if m_flg: rating = m_flg.group(1)
        elif m_fla: rating = m_fla.group(1)
        else: continue
        
        _, header, body = b4a_rows(os.path.join(SRC, f))
        if not header: continue
        
        for v in body:
            if not v or len(v) < 10: continue
            nps = nps_norm(v[0])
            if not nps: continue
            
            ctx = build_rowctx(header, v)
            g = lambda *names: pick(ctx, [x.lower() for x in names])
            
            od = num(g("od"))
            thk = num(g("thickness"))
            hub_x = num(g("hub-x"))
            hub_a = num(g("hub-a"))
            rf_dia = num(g("rf dia"))
            pcd = num(g("pcd"))
            no_bolts = num(g("no of bolts"))
            bolt_sz = (g("bolt size unc") or "").strip()
            rf_sb_len = num(g("rf sb length", "c bolt length"))
            rtj_sb_len = num(g("rtj sb len"))
            socket_depth = num(g("socket depth"))
            lap_radius = num(g("lapped rad"))
            ring_no = (g("ring no") or "").strip()
            
            wn_len = num(g("wn thk", "hub-a"))
            wn_bore = num(g("wn & long neck id", "long neck id"))
            thd_len = num(g("thd thk", "hub-a"))
            lapped_len = num(g("lapped thk", "hub-a"))
            so_bore = num(g("so bore"))
            lapped_bore = num(g("lapped bore"))
            bld_thk = num(g("bld thk", "thickness"))
            
            wn_kg = num(g("wn kg"))
            so_kg = num(g("so kg"))
            bld_kg = num(g("bld kg"))
            
            if od is None and pcd is None: continue
            
            subtypes = [
                ("Welding Neck", "RF", wn_len, wn_bore, wn_kg),
                ("Slip On", "RF", thd_len, so_bore, so_kg),
                ("Blind", "RF", bld_thk, 0.0, bld_kg),
                ("Threaded", "RF", thd_len, None, None),
                ("Lap Joint", "Flat (Stub End)", lapped_len, lapped_bore, None),
                ("Socket Weld", "RF", None, None, None)
            ]
            
            for stype, facing, len_y, bore, wt in subtypes:
                out.append({
                    "nps": nps,
                    "rating": rating,
                    "flange_type": stype,
                    "facing": facing,
                    "outer_diameter_mm": od,
                    "thickness_mm": thk,
                    "hub_diameter_mm": hub_x,
                    "length_thru_hub_mm": len_y,
                    "bore_diameter_mm": bore,
                    "rf_diameter_mm": rf_dia,
                    "pcd_mm": pcd,
                    "num_bolts": int(no_bolts) if no_bolts is not None else None,
                    "bolt_diameter_in": bolt_sz,
                    "stud_bolt_length_mm": rf_sb_len,
                    "stud_bolt_length_rtj_mm": rtj_sb_len,
                    "socket_depth_mm": socket_depth,
                    "lapped_radius_mm": lap_radius,
                    "weight_kg": wt,
                    "rtj_ring_number": ring_no,
                    "source_file": f
                })
    return out

# ==============================================================================
# 2. PIPES (ASME B36.10M / B36.19M)
# ==============================================================================
def extract_pipes():
    out = []
    corrections = []
    pipe_files = [f for f in sorted(os.listdir(SRC)) if f.startswith("pipe") and f.endswith(".csv") and "imperial" not in f.lower()]
    for f in pipe_files:
        fn = f.lower().replace(".csv", "")
        sched = fn.replace("pipe", "").upper()
        if not sched: continue
        
        imperial_file = "pipe_imperial_" + sched.lower() + ".csv"
        imperial_rows = {}
        if os.path.isfile(os.path.join(SRC, imperial_file)):
            _, imperial_header, imperial_body = b4a_rows(os.path.join(SRC, imperial_file))
            for iv in imperial_body:
                if len(iv) < 9: continue
                raw_size = iv[12] if len(iv) > 12 and iv[12].strip() not in ("", "N/A") else iv[0]
                imperial_nps = nps_norm(raw_size)
                if not imperial_nps: continue
                imperial_rows[imperial_nps] = iv
        _, header, body = b4a_rows(os.path.join(SRC, f))
        if not header: continue
        
        for v in body:
            if not v or len(v) < 7: continue
            nps = nps_norm(v[12] if len(v) > 12 and v[12].strip() not in ("", "N/A") else v[0])
            if not nps: continue
            
            od = num(v[2])
            wall = num(v[3])
            id_in = num(v[5]) if len(v) > 5 else None
            wt_empty = num(v[6]) if len(v) > 6 else None
            wt_water = num(v[7]) if len(v) > 7 else None
            moi = num(v[8]) if len(v) > 8 else None

            # INDEPENDENT PHYSICAL CONSTRAINTS: nominal pipe geometry,
            # B36.10 plain-end steel mass and 1000 kg/m^3 water.
            # Independently tabulated B36.10/B36.19 nominal thickness anchors.
            standard_wall = {
                ("1/8", "STD"): 1.73, ("1/8", "XS"): 2.41,
                ("22", "40S"): 9.53,
            }.get((nps, sched))
            if standard_wall is not None and wall != standard_wall:
                corrections.append((f, nps, sched, "wall", wall, standard_wall))
                wall = standard_wall
            if od is not None and wall is not None and 0 < wall < od / 2:
                predicted_id = od - 2 * wall
                if id_in is None or abs(id_in - predicted_id) > max(0.12, od * 0.0002):
                    fixed = round(predicted_id, 3)
                    corrections.append((f, nps, sched, "inside_diameter", id_in, fixed))
                    id_in = fixed
                predicted_mass = 0.0246615 * wall * (od - wall)
                if wt_empty is None or abs(wt_empty - predicted_mass) > max(0.12, predicted_mass * 0.001):
                    fixed = round(predicted_mass, 2)
                    corrections.append((f, nps, sched, "steel_mass", wt_empty, fixed))
                    wt_empty = fixed
                predicted_water = wt_empty + math.pi / 4 * (id_in / 1000.0) ** 2 * 1000.0
                if wt_water is None or abs(wt_water - predicted_water) > max(0.65, predicted_water * 0.005):
                    fixed = round(predicted_water, 2)
                    corrections.append((f, nps, sched, "filled_mass", wt_water, fixed))
                    wt_water = fixed
                predicted_inertia = math.pi / 64 * (od ** 4 - id_in ** 4) / 10000.0
                if moi is None or abs(moi - predicted_inertia) > max(0.12, predicted_inertia * 0.008):
                    fixed = round(predicted_inertia, 2)
                    corrections.append((f, nps, sched, "area_inertia", moi, fixed))
                    moi = fixed

            iv = imperial_rows.get(nps)
            original_imperial = (lambda index: num(iv[index]) if iv is not None and len(iv) > index else None)
            def adjusted_imperial(index, metric_value, conversion, tolerance):
                original = original_imperial(index)
                if metric_value is None: return original
                expected = metric_value * conversion
                if original is not None and abs(original - expected) <= tolerance:
                    return original
                fixed = round(expected, 4)
                corrections.append((f, nps, sched, "imperial_" + str(index), original, fixed))
                return fixed
            if od is None and wall is None: continue
            
            out.append({
                "nps": nps,
                "schedule": sched,
                "outer_diameter_mm": od,
                "wall_thickness_mm": wall,
                "inner_diameter_mm": id_in,
                "weight_empty_kg_m": wt_empty,
                "weight_water_kg_m": wt_water,
                "moment_of_inertia_cm4": moi,
                "outer_diameter_in": adjusted_imperial(2, od, 1 / 25.4, 0.025),
                "wall_thickness_in": adjusted_imperial(3, wall, 1 / 25.4, 0.004),
                "inner_diameter_in": adjusted_imperial(5, id_in, 1 / 25.4, 0.025),
                "weight_empty_lb_ft": adjusted_imperial(6, wt_empty, 0.671968975, max(0.02, wt_empty * 0.001) if wt_empty is not None else 0.02),
                "weight_water_lb_ft": adjusted_imperial(7, wt_water, 0.671968975, max(0.5, wt_water * 0.003) if wt_water is not None else 0.5),
                "moment_of_inertia_in4": adjusted_imperial(8, moi, 0.024025096, max(0.06, moi * 0.0005) if moi is not None else 0.06),
                "source_file": f,
                "imperial_source_file": imperial_file if iv is not None else None
            })
    print("INDEPENDENT_PHYSICS_CORRECTIONS", len(corrections))
    for item in corrections[:35]:
        print("  corrected:", *item)
    return out

# ==============================================================================
# 3. BUTTWELD FITTINGS (ASME B16.9)
# ==============================================================================
def extract_buttweld():
    out = []
    bw_configs = [
        ("90shortradiuselbow", "90° Short Radius Elbow"),
        ("90elbow", "90° Long Radius Elbow"),
        ("45elbow", "45° Elbow"),
        ("shortradiusreturn", "180° Short Radius Return"),
        ("return", "180° Long Radius Return"),
        ("straighttee", "Equal Tee"),
        ("reducingtee", "Reducing Tee"),
        ("cap", "Butt Weld Cap"),
        ("reducers", "Concentric Reducer"),
        ("stubend", "Lap Joint Stub End")
    ]
    
    for f in sorted(os.listdir(SRC)):
        fn = f.lower()
        if not fn.endswith(".csv") or "imperial" in fn: continue
        
        matched_pfx, comp_type = None, None
        for pfx, ctype in bw_configs:
            if fn.startswith(pfx):
                matched_pfx, comp_type = pfx, ctype
                break
        if not matched_pfx: continue
        if comp_type in ("Concentric Reducer", "Reducing Tee"): continue  # matrix rows below
        
        sched = fn[len(matched_pfx):].replace(".csv", "").upper()
        if not sched or sched.startswith("_"): continue
        if "(" in sched: continue
        
        _, header, body = b4a_rows(os.path.join(SRC, f))
        if not header: continue
        
        for v in body:
            if not v or len(v) < 3: continue
            nps = nps_norm(v[0])
            if not nps: continue
            
            ctx = build_rowctx(header, v)
            g = lambda *names: pick(ctx, [x.lower() for x in names])
            
            od = num(g("od", "3-od"))
            c2e = num(g("90 ctr to end", "45 ctr to end", "ctr to e", "ctr to end", "m"))
            branch_c2e = num(g("m"))
            radius = num(g("radius", "r"))
            c2c = num(g("total", "ctr to ctr"))
            b2f = num(g("flat to back", "back to face"))
            length = num(g("over cap-e", "overcap-e1", "len short", "len long", "len", "length"))
            wt = num(g("wt kg", "wt", "kg", "wt short", "wt long"))
            
            if od is None and c2e is None and length is None and wt is None: continue
            
            out.append({
                "nps": nps,
                "schedule": sched,
                "component_type": comp_type,
                "outer_diameter_mm": od,
                "center_to_end_mm": c2e,
                "radius_mm": radius,
                "branch_center_to_end_mm": branch_c2e,
                "center_to_center_mm": c2c,
                "back_to_face_mm": b2f,
                "overall_length_height_mm": length if length is not None else c2e,
                "weight_kg": wt,
                "source_file": f
            })
            
    out.extend(extract_matrix_fittings())
    return out

def extract_matrix_fittings():
    """Flatten original reducer/reducing-tee NPS × branch matrix without guesses.

    Left matrix stores dimensions; second matrix stores matching weights.
    Both are indexed by branch NPS headers, not by arbitrary column offsets.
    """
    out = []
    families = (("reducers", "Concentric Reducer"), ("reducingtee", "Reducing Tee"))
    for fname in sorted(os.listdir(SRC)):
        fn = fname.lower()
        if "imperial" in fn or not fn.endswith(".csv"): continue
        family = next(((prefix, name) for prefix, name in families if fn.startswith(prefix)), None)
        if family is None: continue
        prefix, comp_type = family
        schedule = fn[len(prefix):-4].upper()
        if schedule not in {"5", "5S", "10", "10S", "20", "30", "40", "40S",
                            "STD", "60", "80", "80S", "XS", "100", "120", "140", "160", "XXS"}:
            continue
        _, headers, body = b4a_rows(os.path.join(SRC, fname))
        if not headers or len(headers) < 75: continue
        for values in body:
            if len(values) < 75: continue
            main_nps = nps_norm(values[0])
            main_od = num(values[2])
            if not main_nps or main_od is None: continue
            weights = {}
            for j in range(43, min(len(headers), len(values), 79)):
                branch = nps_norm(headers[j])
                wt = num(values[j])
                if branch and wt is not None:
                    weights[branch] = wt
            for j in range(4, min(40, len(headers), len(values))):
                branch = nps_norm(headers[j])
                branch_measure = num(values[j])
                if not branch or branch_measure is None or branch_measure <= 0: continue
                if branch == main_nps: continue
                if comp_type == "Reducing Tee":
                    run_end = num(values[3])
                    branch_end = branch_measure
                    overall_len = None
                else:
                    run_end = None
                    branch_end = None
                    overall_len = branch_measure
                out.append({
                    "nps": main_nps,
                    "branch_nps": branch,
                    "schedule": schedule,
                    "component_type": comp_type,
                    "outer_diameter_mm": main_od,
                    "center_to_end_mm": run_end,
                    "branch_center_to_end_mm": branch_end,
                    "radius_mm": None,
                    "center_to_center_mm": None,
                    "back_to_face_mm": None,
                    "overall_length_height_mm": overall_len,
                    "weight_kg": weights.get(branch),
                    "source_file": fname
                })
    return out


# ==============================================================================
# 4. SOCKET WELD FITTINGS (ASME B16.11)
# ==============================================================================
def extract_socketweld():
    out = []
    sw_types = {
        "1": "90° Elbow",
        "2": "45° Elbow",
        "3": "Tee",
        "4": "Cross",
        "5": "Full Coupling",
        "6": "Half Coupling",
        "7": "Cap",
        "8": "Reducing Coupling",
        "9": "Union"
    }
    
    for f in sorted(os.listdir(SRC)):
        fn = f.lower()
        if not fn.startswith("ftsw") or not fn.endswith(".csv") or "inche" in fn or "imperial" in fn: continue
        
        m = re.match(r"ftsw([1-9])_.*_(3000|6000|9000)_millimetre.*\.csv", fn)
        if not m:
            m2 = re.match(r"ftsw(\d)(\d{2})(?:0)?\.csv", fn)
            if m2:
                t_idx, rat_code = m2.group(1), m2.group(2)
                rating = "3000" if rat_code == "30" else ("6000" if rat_code == "60" else "9000")
                comp = sw_types.get(t_idx, "Forged Fitting")
            else:
                continue
        else:
            comp = sw_types.get(m.group(1), "Forged Fitting")
            rating = m.group(2)
            
        _, header, body = b4a_rows(os.path.join(SRC, f))
        if not header: continue
        
        for v in body:
            if not v: continue
            nps = nps_norm(v[0])
            if not nps: continue
            
            ctx = build_rowctx(header, v)
            g = lambda *names: pick(ctx, [x.lower() for x in names])
            
            sock_dia = num(g("socketdia", "socket dia", "r-dia", "b"))
            sock_depth = num(g("socket depth", "c", "j"))
            c2e = num(g("ctr to end", "ctrtoend", "a"))
            bore = num(g("bore", "d-bore", "d"))
            sock_thk = num(g("socket thk", "c"))
            body_thk = num(g("wall thk", "body wall thk", "g"))
            cplg_len = num(g("cplglen", "cplg len", "q-len", "k=length", "k", "length", "halfcplglen"))
            wt = num(g("kg", "wt", "wtkg"))
            
            if sock_dia is None and c2e is None and cplg_len is None and wt is None: continue
            
            out.append({
                "nps": nps,
                "rating": rating,
                "component_type": comp,
                "socket_diameter_mm": sock_dia,
                "socket_depth_mm": sock_depth,
                "center_to_end_mm": c2e,
                "bore_diameter_mm": bore,
                "socket_wall_thickness_mm": sock_thk,
                "body_wall_thickness_mm": body_thk,
                "length_mm": cplg_len,
                "weight_kg": wt,
                "source_file": f
            })
    return out

# ==============================================================================
# 5. THREADED FITTINGS (ASME B16.11)
# ==============================================================================
def extract_threaded():
    out = []
    thd_types = {
        "1": "90° Elbow", "2": "Tee", "3": "Cross", "4": "45° Elbow",
        "5": "Street Elbow", "6": "Full Coupling", "7": "Half Coupling", "8": "Cap",
        "9": "Square Head Plug", "10": "Hex Head Plug", "11": "Round Head Plug",
        "12": "Hex Head Bushing", "13": "Flush Bushing", "20": "90° Elbow",
        "21": "45° Elbow", "22": "Tee", "23": "90° Elbow", "24": "45° Elbow", "25": "Tee"
    }
    
    for f in sorted(os.listdir(SRC)):
        fn = f.lower()
        if not fn.startswith("ftsc") or not fn.endswith(".csv") or "inche" in fn or "imperial" in fn: continue
        
        m = re.match(r"ftsc(\d+)_.*_(2000|3000|6000|9000)_millimetre.*\.csv", fn)
        if not m:
            m2 = re.match(r"ftsc(\d+)(\d{2})\.csv", fn)
            if m2:
                t_idx, rat_code = m2.group(1), m2.group(2)
                rating = "2000" if rat_code == "20" else ("3000" if rat_code == "30" else "6000")
                comp = thd_types.get(t_idx, "Threaded Fitting")
            else:
                continue
        else:
            comp = thd_types.get(m.group(1), "Threaded Fitting")
            rating = m.group(2)
            
        _, header, body = b4a_rows(os.path.join(SRC, f))
        if not header: continue
        
        for v in body:
            if not v: continue
            nps = nps_norm(v[0])
            if not nps: continue
            
            ctx = build_rowctx(header, v)
            g = lambda *names: pick(ctx, [x.lower() for x in names])
            
            c2e = num(g("a", "ctr to end", "ctrtoend"))
            bore = num(g("b", "bore", "diam"))
            length = num(g("l2", "length", "oa length", "oalength", "c"))
            wt = num(g("wt", "kg", "wtkg"))
            
            if c2e is None and length is None and wt is None: continue
            
            out.append({
                "nps": nps,
                "rating": rating,
                "component_type": comp,
                "center_to_end_mm": c2e,
                "bore_diameter_mm": bore,
                "body_wall_thickness_mm": None,
                "length_mm": length,
                "weight_kg": wt,
                "source_file": f
            })
    return out

# ==============================================================================
# 6. VALVES (ASME B16.10 / B16.34)
# ==============================================================================
def extract_valves():
    out = []
    valve_names = {
        "1": "Gate Valve",
        "2": "Globe Valve",
        "3": "Check Valve",
        "4": "Ball Valve",
        "5": "Butterfly Valve",
        "6": "Safety Relief Valve",
        "7": "Plug Valve",
        "8": "Diaphragm Valve"
    }
    
    for f in sorted(os.listdir(SRC)):
        fn = f.lower()
        if not fn.startswith("vlv") or not fn.endswith(".csv") or "imperial" in fn: continue
        
        m = re.match(r"vlv([1-8])_?(1500|2500|150|300|400|600|900)", fn)
        if not m: continue
        
        vt_idx, rating = m.group(1), m.group(2)
        vname = valve_names.get(vt_idx, "Valve")
        
        _, header, body = b4a_rows(os.path.join(SRC, f))
        if not header: continue
        
        for v in body:
            if not v or len(v) < 3: continue
            nps = nps_norm(v[0])
            if not nps: continue
            
            ctx = build_rowctx(header, v)
            g = lambda *names: pick(ctx, [x.lower() for x in names])
            
            f2f = num(g("rf-f/f", "rf face to face", "f-f"))
            rtj_f2f = num(g("rtj f/f", "rtj-f/f"))
            bw_f2f = num(g("bw-f/f", "bw f/f"))
            bw_weight = num(g("bw kg"))
            rtj_add_len = num(g("rtj add len"))
            hw_h = num(g("height-hw", "height"))
            hw_d = num(g("diam-hw", "handwheel"))
            wt = num(g("rf/rtj kg", "weight rf/rtj", "weight kg", "wt"))
            
            if f2f is None and wt is None: continue
            
            out.append({
                "nps": nps,
                "rating": rating,
                "valve_type": vname,
                "face_to_face_mm": f2f,
                "face_to_face_rtj_mm": rtj_f2f,
                "face_to_face_bw_mm": bw_f2f,
                "weight_bw_kg": bw_weight,
                "rtj_add_length_mm": rtj_add_len,
                "handwheel_height_mm": hw_h,
                "handwheel_diameter_mm": hw_d,
                "weight_kg": wt,
                "source_file": f
            })
    return out

# ==============================================================================
# 7. GASKETS (ASME B16.20 / B16.21)
# ==============================================================================
def extract_gaskets():
    out = []
    p_grtj = os.path.join(SRC, "grtj1.csv")
    if os.path.exists(p_grtj):
        _, header, body = b4a_rows(p_grtj)
        for v in body:
            if not v or len(v) < 5: continue
            ring = v[0].strip()
            if not ring or ring.upper() in ("N/A", "R NO"): continue
            pitch_dia = num(v[2])
            oval_height = num(v[4]) if len(v) > 4 else None
            oct_height = num(v[5]) if len(v) > 5 else None
            oct_width = num(v[6]) if len(v) > 6 else None
            width = num(v[3])
            wt = num(v[8]) if len(v) > 8 else None
            out.append({
                "nps": ring,
                "rating": "RTJ",
                "gasket_type": "RTJ Ring Joint",
                "inner_diameter_mm": None,
                "outer_diameter_mm": None,
                "pitch_diameter_mm": pitch_dia,
                "ring_width_mm": width,
                "oval_height_mm": oval_height,
                "oct_height_mm": oct_height,
                "oct_width_mm": oct_width,
                "ring_number": ring if ring.startswith("R") else f"R{ring}",
                "weight_kg": wt,
                "source_file": "grtj1.csv"
            })
            
    p_gspr = os.path.join(SRC, "gspr1.csv")
    if os.path.exists(p_gspr):
        _, header, body = b4a_rows(p_gspr)
        ratings = ["150", "300", "400", "600", "900", "1500", "2500"]
        for v in body:
            if not v: continue
            nps = nps_norm(v[0])
            if not nps: continue
            for r_idx, rat in enumerate(ratings):
                id_val = num(v[2 + r_idx]) if len(v) > 2 + r_idx else None
                od_val = num(v[18 + r_idx]) if len(v) > 18 + r_idx else None
                wt_val = num(v[25 + r_idx]) if len(v) > 25 + r_idx else None
                if id_val or od_val:
                    out.append({
                        "nps": nps,
                        "rating": rat,
                        "gasket_type": "Spiral Wound Gasket",
                        "inner_diameter_mm": id_val,
                        "outer_diameter_mm": od_val,
                        "ring_number": "",
                        "thickness_mm": num(v[32]) if len(v) > 32 else None,
                        "weight_kg": wt_val,
                        "source_file": "gspr1.csv"
                    })
                    
    p_gflt = os.path.join(SRC, "gflt1.csv")
    if os.path.exists(p_gflt):
        _, header, body = b4a_rows(p_gflt)
        for v in body:
            if not v: continue
            nps = nps_norm(v[0])
            if not nps: continue
            id_val = num(v[3]) if len(v) > 3 else None
            for col_idx, rat in [(6, "150"), (7, "300"), (8, "400"), (9, "600"), (10, "900")]:
                od_val = num(v[col_idx]) if len(v) > col_idx else None
                wt_val = num(v[col_idx + 7]) if len(v) > col_idx + 7 else None
                if id_val or od_val:
                    out.append({
                        "nps": nps,
                        "rating": rat,
                        "gasket_type": "Flat Ring Gasket",
                        "inner_diameter_mm": id_val,
                        "outer_diameter_mm": od_val,
                        "ring_number": "",
                        "weight_kg": wt_val,
                        "source_file": "gflt1.csv"
                    })
    return out

# ==============================================================================
# 8. LINE BLANKS (ASME B16.48)
# ==============================================================================
def extract_blanks():
    out = []
    blank_configs = [
        ("blnu1", "Figure-8 Spectacle Blind"),
        ("blnu2", "Paddle Blank"),
        ("blnu3", "Paddle Spacer"),
        ("blnu4", "Line Blank")
    ]
    
    for f in sorted(os.listdir(SRC)):
        fn = f.lower()
        if not fn.startswith("blnu") or not fn.endswith(".csv") or "inche" in fn or "imperial" in fn: continue
        
        m = re.match(r"(blnu[1-4][a-z]?)_.*_(1500|2500|150|300|400|600|900)_millimetre.*\.csv", fn)
        if not m: continue
        
        pfx, rating = m.group(1), m.group(2)
        btype = next((bt for p, bt in blank_configs if pfx.startswith(p)), "Line Blank")
        
        _, header, body = b4a_rows(os.path.join(SRC, f))
        if not header: continue
        
        for v in body:
            if not v or len(v) < 4: continue
            nps = nps_norm(v[0])
            if not nps: continue
            
            ctx = build_rowctx(header, v)
            g = lambda *names: pick(ctx, [x.lower() for x in names])
            
            od = num(g("od", "outside dia"))
            thk = num(g("thk", "thickness", "basic thickness", "total thickness"))
            c2c = num(g("crs", "dist", "center to center"))
            wt = num(g("blank kg", "kg", "wt"))
            
            if od is None and thk is None: continue
            
            out.append({
                "nps": nps,
                "rating": rating,
                "blank_type": btype,
                "outer_diameter_mm": od,
                "thickness_mm": thk,
                "center_to_center_mm": c2c,
                "weight_spectacle_kg": wt,
                "source_file": f
            })
    return out

def write_table_csv(tname, rows, cols):
    p = os.path.join(OUT, tname + ".csv")
    with open(p, "w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(cols)
        for r in rows:
            w.writerow([r.get(c, "") for c in cols])
    print(f"✅ {tname}.csv yazildi: {len(rows)} satir")

def main():
    print("--- PIPEDATA CANONICAL EXTRACTOR BAŞLADI ---")
    
    flanges = extract_flanges()
    write_table_csv("tbl_flanges_asme_b16_5", flanges, [
        "nps", "rating", "flange_type", "facing", "outer_diameter_mm",
        "thickness_mm", "hub_diameter_mm", "length_thru_hub_mm", "bore_diameter_mm",
        "rf_diameter_mm", "pcd_mm", "num_bolts", "bolt_diameter_in",
        "stud_bolt_length_mm", "stud_bolt_length_rtj_mm",
        "socket_depth_mm", "lapped_radius_mm", "weight_kg", "rtj_ring_number", "source_file"
    ])
    
    pipes = extract_pipes()
    write_table_csv("tbl_pipes", pipes, [
        "nps", "schedule", "outer_diameter_mm", "wall_thickness_mm",
        "inner_diameter_mm", "weight_empty_kg_m", "weight_water_kg_m",
        "moment_of_inertia_cm4", "outer_diameter_in", "wall_thickness_in",
        "inner_diameter_in", "weight_empty_lb_ft", "weight_water_lb_ft",
        "moment_of_inertia_in4", "source_file", "imperial_source_file"
    ])
    
    bw = extract_buttweld()
    write_table_csv("tbl_fittings_buttweld_b16_9", bw, [
        "nps", "branch_nps", "schedule", "component_type", "outer_diameter_mm",
        "center_to_end_mm", "branch_center_to_end_mm", "radius_mm",
        "center_to_center_mm", "back_to_face_mm",
        "overall_length_height_mm", "weight_kg", "source_file"
    ])
    
    sw = extract_socketweld()
    write_table_csv("tbl_fittings_socketweld_b16_11", sw, [
        "nps", "rating", "component_type", "socket_diameter_mm",
        "socket_depth_mm", "center_to_end_mm", "bore_diameter_mm",
        "socket_wall_thickness_mm", "body_wall_thickness_mm", "length_mm",
        "weight_kg", "source_file"
    ])
    
    thd = extract_threaded()
    write_table_csv("tbl_fittings_threaded_b16_11", thd, [
        "nps", "rating", "component_type", "center_to_end_mm",
        "bore_diameter_mm", "body_wall_thickness_mm", "length_mm",
        "weight_kg", "source_file"
    ])
    
    valves = extract_valves()
    write_table_csv("tbl_valves_asme_b16_10", valves, [
        "nps", "rating", "valve_type", "face_to_face_mm",
        "face_to_face_rtj_mm", "face_to_face_bw_mm", "weight_bw_kg", "rtj_add_length_mm",
        "handwheel_height_mm", "handwheel_diameter_mm", "weight_kg", "source_file"
    ])
    
    gaskets = extract_gaskets()
    write_table_csv("tbl_gaskets_and_rtj", gaskets, [
        "nps", "rating", "gasket_type", "inner_diameter_mm",
        "outer_diameter_mm", "pitch_diameter_mm", "ring_width_mm",
        "oval_height_mm", "oct_height_mm", "oct_width_mm", "thickness_mm",
        "ring_number", "weight_kg", "source_file"
    ])
    
    blanks = extract_blanks()
    write_table_csv("tbl_line_blanks_asme_b16_48", blanks, [
        "nps", "rating", "blank_type", "outer_diameter_mm",
        "thickness_mm", "center_to_center_mm", "weight_spectacle_kg", "source_file"
    ])
    
    print("--- TAMAMLANDI ---")

if __name__ == "__main__":
    main()
