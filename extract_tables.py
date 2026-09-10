#!/usr/bin/env python3
"""Pipedata tablo cikarma - tek script, sifirdan.
Kaynak: raw_apk/assets/*.csv (956 orijinal, B4A LoadCSV formati).
Cikti : app/src/main/assets/csv/<tablo>.csv (baslikli, UTF-8 LF) + build raporu.
App sozlesmesi: CsvDataStore.kt'nin okudugu 36 kolon adi (asagida APP_COLS).
"""
import csv, io, os, re, sys

PROJECT = os.path.dirname(os.path.abspath(__file__))
SRC = os.path.join(PROJECT, "raw_apk", "assets")
OUT = os.path.join(PROJECT, "app", "src", "main", "assets", "csv")

# --- App sozlesmesi: CsvDataStore.kt'deki tum s()/d() kolon adlari ---
TABLES = {
    "tbl_flanges_asme_b16_5": ["nps", "rating", "flange_type", "outer_diameter_mm",
        "thickness_mm", "hub_diameter_mm", "length_thru_hub_mm", "bore_diameter_mm",
        "rf_diameter_mm", "pcd_mm", "num_bolts", "bolt_diameter_in",
        "stud_bolt_length_mm", "weight_kg", "rtj_ring_number", "source_file"],
    "tbl_pipes": ["nps", "schedule", "outer_diameter_mm", "wall_thickness_mm",
        "inner_diameter_mm", "weight_empty_kg_m", "weight_water_kg_m",
        "moment_of_inertia_cm4", "source_file"],
    "tbl_fittings_buttweld_b16_9": ["nps", "schedule", "component_type",
        "outer_diameter_mm", "center_to_end_mm", "center_to_center_mm",
        "back_to_face_mm", "overall_length_height_mm", "weight_kg", "source_file"],
    "tbl_fittings_socketweld_b16_11": ["nps", "rating", "component_type",
        "socket_diameter_mm", "socket_depth_mm", "center_to_end_mm",
        "bore_diameter_mm", "socket_wall_thickness_mm", "body_wall_thickness_mm",
        "length_mm", "weight_kg", "source_file"],
    "tbl_fittings_threaded_b16_11": ["nps", "rating", "component_type",
        "center_to_end_mm", "bore_diameter_mm", "body_wall_thickness_mm",
        "length_mm", "weight_kg", "source_file"],
    "tbl_valves_asme_b16_10": ["nps", "rating", "face_to_face_mm", "weight_kg",
        "source_file"],
    "tbl_gaskets_and_rtj": ["nps", "rating", "inner_diameter_mm",
        "outer_diameter_mm", "ring_number", "source_file"],
    "tbl_line_blanks_asme_b16_48": ["nps", "rating", "outer_diameter_mm",
        "thickness_mm", "center_to_center_mm", "weight_spectacle_kg", "source_file"],
}

def b4a_rows(path):
    """B4A LoadCSV taklidi: virgul-bol, tirnak-destekli. Doner: (idx, header, body)."""
    with open(path, "r", encoding="utf-8", errors="replace") as f:
        text = f.read()
    text = text.replace("\r\n", "\n").replace("\r", "\n")
    rows = list(csv.reader(io.StringIO(text)))
    rows = [r for r in rows if not (len(r) == 1 and r[0] == "")]
    if len(rows) < 2:
        return None, None, []
    first = [c.strip() for c in rows[0]]
    idx_like = len(first) >= 2 and all(re.fullmatch(r"\d+", c) for c in first)
    ds = 1 if idx_like else 0
    if len(rows) <= ds:
        return None, None, []
    return rows[0] if idx_like else [], [c.strip() for c in rows[ds]], rows[ds + 1:]

def num(x):
    """Hucre -> float. N/A/SPA/bos -> None. Kesirli inç (0+1/2) -> ondalik inç."""
    if x is None:
        return None
    t = str(x).strip().strip("\u00a0")
    if t == "" or t.upper() in ("N/A", "NA", "SPA", "-", "--"):
        return None
    t = t.replace(",", "").strip()
    m = re.fullmatch(r"(\d+)\+(\d+)\s*/\s*(\d+)", t)
    if m:
        return float(m.group(1)) + float(m.group(2)) / float(m.group(3))
    m = re.fullmatch(r"(\d+)\s*/\s*(\d+)", t)
    if m:
        return float(m.group(1)) / float(m.group(2))
    try:
        return float(t)
    except ValueError:
        return None

def pick(row, names):
    """Baslik listesinden adayi bul, hucre degerini don (case-insensitive)."""
    want = set(n.strip().lower() for n in names)
    for i, h in enumerate(row["_h"]):
        if h in want and i < len(row["_v"]):
            return row["_v"][i]
    return None

# --- Baslik sozlugu: orijinal baslik -> kanonik anlam ---
H = lambda *a: set(x.lower() for x in a)
HDR = {
    "nps_frac": H("45 deg e l nb", "nb", "nb inche", "nb inch", "size", "nps",
        "ins", "control valve nb inches", "inlet size", "nom.size", "nom. size"),
    "dn": H("nb", "dn", "met size", "control valve nb mm", "met nb", "mm", "nb mm"),
    "od": H("od", "3-od", "od (o)"),
    "nb_dup_note": "ikinci nb = DN",
}

def build_rowctx(header, values):
    return {"_h": [h.strip().lower() for h in header], "_v": values}

def nps_norm(frac, imperial):
    """Kesirli inc -> app formati: '1 1/2' (bosluksuz degil). imperial dosyada '2' gibi duz sayi olabilir."""
    if frac is None:
        return None
    t = str(frac).strip()
    if t.upper() in ("N/A", ""):
        return None
    if re.fullmatch(r"\d+", t):  # imperial duz sayi: oldugu gibi
        return t
    m = re.fullmatch(r"(\d+)\+(.+)", t)  # 1+1/2 -> 1 1/2, 0+1/8 -> 1/8
    if m:
        return m.group(2) if m.group(1) == "0" else m.group(1) + " " + m.group(2)
    return t

def dn_of(frac, dncell):
    v = num(dncell)
    return int(v) if v is not None else None

IN2MM = 25.4

def convert(value, imperial):
    """ imperial (inc) -> mm. mm dosyada aynen. None -> None."""
    if value is None:
        return None
    return round(value * IN2MM, 4) if imperial else round(value, 4)

# ============ TABLO KURALLARI ============
# Her kural: (header -> {kanonik: deger}) + dosya adi filtresi.
# imperial: dosya adinda inches/imperial varsa True.

def is_imperial(fname):
    l = fname.lower()
    return ("inches" in l or "imperial" in l) and "millimetres" not in l

def rule_flange(fname, header, body, imperial):
    """fla*.csv: 49 kolon, basliklar acik. rating dosya adindan."""
    rating = _flg_rating(fname) or _fla_rating(fname)
    if not rating:
        return None
    out = []
    for v in body:
        ctx = build_rowctx(header, v)
        n = nps_norm(v[0] if len(v) > 0 else None, imperial)
        if not n:
            continue
        # nb ikilisi: v[1] = DN
        dn = dn_of(None, v[1] if len(v) > 1 else None)
        g = lambda *names: pick(ctx, [x.lower() for x in names])
        r = {
            "nps": n, "rating": rating, "flange_type": "",
            "outer_diameter_mm": convert(num(g("od")), imperial),
            "thickness_mm": convert(num(g("thickness")), imperial),
            "hub_diameter_mm": convert(num(g("hub-x")), imperial),
            "length_thru_hub_mm": convert(num(g("hub-a")), imperial),
            "bore_diameter_mm": convert(num(g("so bore")), imperial),
            "rf_diameter_mm": convert(num(g("rf dia")), imperial),
            "pcd_mm": convert(num(g("pcd")), imperial),
            "num_bolts": num(g("no of bolts")),
            "bolt_diameter_in": (g("bolt size unc") or "").strip(),
            "stud_bolt_length_mm": convert(num(g("rf sb length", "c bolt length")), imperial),
            "weight_kg": convert(num(g("wn kg", "so kg", "bld kg", "wt", "weight", "flange wt")), imperial),
            "rtj_ring_number": (g("ring no") or "").strip(),
            "source_file": fname,
        }
        # tamami bos satirlari at (ilk uc kritik kolon bos mu?)
        if r["outer_diameter_mm"] is None and r["pcd_mm"] is None and r["weight_kg"] is None:
            continue
        r["_dn"] = dn
        out.append(r)
    return out

def rule_pipe(fname, header, body, imperial):
    """pipe*.csv: 13 kolon, indeks satiri aciklamali (2-DN, 3-OD...)."""
    m = re.search(r"pipe[_]?(\w+)", fname.lower())
    sched_raw = m.group(1) if m else ""
    sched = {"std": "40", "xs": "80"}.get(sched_raw, sched_raw)
    hl = [h.lower() for h in header]
    # kolon yerleri: DN=1, OD=2, WallThk=3, ID=5, Wt=6, WtWater=7, MoI=8, NPS=12
    out = []
    for v in body:
        n = nps_norm(v[12] if len(v) > 12 else (v[0] if v else None), imperial)
        if not n:
            continue
        def C(i):
            return convert(num(v[i]) if len(v) > i else None, imperial)
        r = {"nps": n, "schedule": sched, "outer_diameter_mm": C(2),
             "wall_thickness_mm": C(3), "inner_diameter_mm": C(5),
             "weight_empty_kg_m": C(6), "weight_water_kg_m": C(7),
             "moment_of_inertia_cm4": C(8), "source_file": fname}
        if r["outer_diameter_mm"] is None:
            continue
        out.append(r)
    return out

def rule_buttweld(fname, header, body, imperial):
    """45elbow/90elbow/return/tee/cap/reducers/stubend/srra/srrb*.csv"""
    l = fname.lower()
    if l.startswith(("45elbow", "ftbw1")):
        comp, sched = "45° Elbow", "STD"
    elif "shortradiusreturn" in l:
        comp, sched = "Return", "STD"
    elif l.startswith("return"):
        comp, sched = "Return", "STD"
    elif "reducingtee" in l:
        comp, sched = "Reducing Tee", "STD"
    elif "straighttee" in l:
        comp, sched = "Tee", "STD"
    elif l.startswith("cap"):
        comp, sched = "Cap", "STD"
    elif l.startswith("reducers"):
        comp, sched = "Reducer", "STD"
    elif l.startswith("stubend"):
        comp, sched = "Stub End", "STD"
    elif "90shortradius" in l or "srra" in l or "srrb" in l:
        comp, sched = "90° SR Elbow", "STD"
    elif l.startswith(("90elbow", "ftbw2")):
        comp, sched = "90° Elbow", "STD"
    else:
        return None
    m = re.search(r"(\d+)(s)?(\.csv)?$", l.replace("_imperial", "").replace("imperial", ""))
    hl = [h.lower() for h in header]
    out = []
    for v in body:
        n = nps_norm(v[0] if v else None, imperial)
        if not n:
            continue
        ctx = build_rowctx(header, v)
        g = lambda *names: pick(ctx, [x.lower() for x in names])
        od = convert(num(g("od")), imperial)
        c2e = convert(num(g("45 ctr to end", "90 ctr to end", "ctr to end", "ctr to e",
                             "flat to back", "over cap-e", "overcap-e1", "len short", "len long")), imperial)
        c2c = convert(num(g("total", "ctr to ctr")), imperial)
        wt = convert(num(g("wt", "wt kg", "wt short", "wt long")), imperial)
        if od is None and c2e is None and wt is None:
            continue
        out.append({"nps": n, "schedule": sched, "component_type": comp,
                    "outer_diameter_mm": od, "center_to_end_mm": c2e,
                    "center_to_center_mm": c2c, "back_to_face_mm": None,
                    "overall_length_height_mm": c2e, "weight_kg": wt,
                    "source_file": fname})
    return out

def rule_socketweld_matrix(fname, header, body, imperial, rating):
    """ftsw<nn><rating>.csv matris formati: baslikta NPS degerleri, satirlar M/Kg/O/Len gibi.
    Ornek ftsw1030: nb,nb,M,Kg,N/A,,0+1/8,... (M = boy, Kg = agirlik)."""
    hl = [h.strip().lower() for h in header]
    # M / olcu kolonu + Kg kolonu yerini bul
    mkeys = ("m", "o=len", "k=length", "b", "q-len", "k")
    mi = next((i for i, h in enumerate(hl) if h in mkeys), None)
    ki = next((i for i, h in enumerate(hl) if h in ("kg", "wt", "wtkg")), None)
    if mi is None and ki is None:
        return None
    out = []
    for v in body:
        n = nps_norm(v[0] if v else None, imperial)
        if not n:
            continue
        mval = convert(num(v[mi]) if mi is not None and len(v) > mi else None, imperial)
        if imperial:
            wt_raw = num(v[ki]) if ki is not None and len(v) > ki else None
            wt = round(wt_raw * 0.453592, 4) if wt_raw is not None else None
        else:
            wt = convert(num(v[ki]) if ki is not None and len(v) > ki else None, False)
        if mval is None and wt is None:
            continue
        out.append({"nps": n, "rating": rating, "component_type": "Forged Fitting",
                    "socket_diameter_mm": None, "socket_depth_mm": None,
                    "center_to_end_mm": None, "bore_diameter_mm": None,
                    "socket_wall_thickness_mm": None, "body_wall_thickness_mm": None,
                    "length_mm": mval, "weight_kg": wt, "source_file": fname})
    return out or None


def rule_socketweld(fname, header, body, imperial):
    """ftsw*.csv: NB,DN,Ctr To End,SocketDia,Socket Thk,Bore,Socket Depth,..."""
    l = fname.lower()
    m = re.search(r"ftsw(\d+)", l)
    compmap = {"1": "90° Elbow", "2": "45° Elbow", "3": "Tee", "4": "Cross",
               "5": "Full Coupling", "6": "Half Coupling", "7": "Cap",
               "8": "Reducing Coupling", "9": "Union"}
    comp = compmap.get(m.group(1) if m else "", "Forged Fitting")
    m2 = re.search(r"(3000|6000|9000)", l)
    rating = m2.group(1) if m2 else "3000"
    lname = fname.lower()
    if "socketwelded" not in lname and "threaded" not in lname:
        mx = rule_socketweld_matrix(fname, header, body, imperial, rating)
        if mx:
            return mx
    out = []
    for v in body:
        n = nps_norm(v[0] if v else None, imperial)
        if not n:
            continue
        ctx = build_rowctx(header, v)
        g = lambda *names: pick(ctx, [x.lower() for x in names])
        def C(*names):
            return convert(num(g(*names)), imperial)
        cplg = C("cplglen", "cplg len", "q-len", "k=length")
        if cplg is None and comp in ("Full Coupling", "Half Coupling", "Cap", "Union"):
            cplg = C("halfcplglen", "half cplg len", "capendwallthk", "cap end wall thk")
        # inches->mm: imperial dosyada tum boylar inc. mm dosyada mm.
        if imperial:
            wt_raw = num(g("kg", "wt"))
            wt = round(wt_raw * 0.453592, 4) if wt_raw is not None else None
        else:
            wt = convert(num(g("kg", "wt")), False)
        r = {"nps": n, "rating": rating, "component_type": comp,
             "socket_diameter_mm": C("socketdia", "socket dia", "r-dia",
                                       "socket dia large end"),
             "socket_depth_mm": C("socket depth", "socket depth large end"),
             "center_to_end_mm": C("ctr to end", "ctrtoend"),
             "bore_diameter_mm": C("bore", "d-bore"),
             "socket_wall_thickness_mm": C("socket thk"),
             "body_wall_thickness_mm": C("wall thk", "body wall thk"),
             "length_mm": cplg,
             "weight_kg": wt, "source_file": fname}
        if r["socket_diameter_mm"] is None and r["center_to_end_mm"] is None and r["weight_kg"] is None:
            continue
        out.append(r)
    return out

def rule_threaded(fname, header, body, imperial):
    """ftsc*.csv: NB,DN,A,H,G,B,L2,WT tarzi."""
    l = fname.lower()
    m = re.search(r"ftsc(\d+)", l)
    compmap = {"1": "90° Elbow", "2": "45° Elbow", "3": "Tee", "4": "Cross",
               "5": "Full Coupling", "6": "Full Coupling", "7": "Cap",
               "8": "Reducing Coupling", "9": "Union",
               "10": "Plug", "11": "Plug", "12": "Bushing", "13": "Bushing",
               "14": "Plug", "15": "Plug", "16": "Plug", "17": "Plug",
               "18": "Plug", "19": "Plug", "20": "90° Elbow", "21": "45° Elbow",
               "22": "Tee", "23": "90° Elbow", "24": "45° Elbow", "25": "Tee"}
    comp = compmap.get(m.group(1) if m else "", "Threaded Fitting")
    m2 = re.search(r"(2000|3000|6000)", l)
    rating = m2.group(1) if m2 else "3000"
    out = []
    for v in body:
        n = nps_norm(v[0] if v else None, imperial)
        if not n:
            continue
        ctx = build_rowctx(header, v)
        g = lambda *names: pick(ctx, [x.lower() for x in names])
        def C(*names):
            return convert(num(g(*names)), imperial)
        if imperial:
            wt_raw = num(g("wt", "kg"))
            wt = round(wt_raw * 0.453592, 4) if wt_raw is not None else None
        else:
            wt = convert(num(g("wt", "kg")), False)
        r = {"nps": n, "rating": rating, "component_type": comp,
             "center_to_end_mm": C("a", "ctr to end", "oa length", "oalength",
                                     "across flats", "thread length"),
             "bore_diameter_mm": C("b", "bore", "diam"),
             "body_wall_thickness_mm": None,
             "length_mm": C("l2", "length", "oa length", "oalength",
                              "across flats", "thread length"),
             "weight_kg": wt, "source_file": fname}
        if r["center_to_end_mm"] is None and r["weight_kg"] is None and r["length_mm"] is None:
            continue
        out.append(r)
    return out

def rule_valve(fname, header, body, imperial):
    """vlv*.csv: NPS,DN,RF-F/F,...,RF/RTJ KG. vlv tipi dosya adindaki ilk rakam."""
    m = re.search(r"vlv(\d+)", fname.lower())
    if not m:
        return None
    m2 = re.search(r"(150|300|400|600|900|1500|2500)\.csv$", fname.lower())
    rating = m2.group(1) if m2 else "150"
    out = []
    for v in body:
        n = nps_norm(v[0] if v else None, imperial)
        if not n:
            continue
        ctx = build_rowctx(header, v)
        g = lambda *names: pick(ctx, [x.lower() for x in names])
        def C(*names):
            return convert(num(g(*names)), imperial)
        f2f = C("rf-f/f", "rf face to face")
        wt = C("rf/rtj kg", "weight rf/rtj", "weight kg", "wt")
        if f2f is None and wt is None:
            continue
        out.append({"nps": n, "rating": rating, "face_to_face_mm": f2f,
                    "weight_kg": wt, "source_file": fname})
    return out

def rule_gasket(fname, header, body, imperial):
    """Conta tablolari iki cesit:
    a) grtj*.csv: R-numarasi anahtarli halka tablosu (R no, P-pitch dia, ...).
       App nps+rating ile arar; halka tablosu ayri tutulur: nps = halka no.
    b) fldi*/gflt*/gspr* flans-olculu conta tablolari: NB/DN + OD/PCD/... kolonlu.
    """
    l = fname.lower()
    if l.startswith("grtj"):
        out = []
        for v in body:
            ctx = build_rowctx(header, v)
            g = lambda *names: pick(ctx, [x.lower() for x in names])
            ring = (g("r no") or (v[0] if v else "")).strip()
            if not ring or ring.upper() == "N/A":
                continue
            def C(*names):
                return convert(num(g(*names)), imperial)
            out.append({"nps": ring, "rating": "RTJ",
                        "inner_diameter_mm": C("p-pitch dia", "b-height oval"),
                        "outer_diameter_mm": C("a-width"),
                        "ring_number": ring, "source_file": fname})
        return out or None
    out = []
    for v in body:
        n = nps_norm(v[0] if v else None, imperial)
        if not n:
            continue
        ctx = build_rowctx(header, v)
        g = lambda *names: pick(ctx, [x.lower() for x in names])
        def C(*names):
            return convert(num(g(*names)), imperial)
        od = C("od", "d", "outside dia")
        idm = C("pcd", "id", "inside dia", "bore")
        ring = (g("ring no") or "").strip()
        m = __import__("re").search(r"(150|300|600|900|1500|2500)", l)
        rating = m.group(1) if m else "150"
        if od is None and idm is None:
            continue
        out.append({"nps": n, "rating": rating, "inner_diameter_mm": idm,
                    "outer_diameter_mm": od, "ring_number": ring,
                    "source_file": fname})
    return out or None

def rule_blank(fname, header, body, imperial):
    """blnu*.csv: Figure-8 / paddle / spacer."""
    m = re.search(r"(150|300|600|900|1500|2500)", fname.lower())
    rating = m.group(1) if m else "150"
    out = []
    for v in body:
        n = nps_norm(v[0] if v else None, imperial)
        if not n:
            continue
        ctx = build_rowctx(header, v)
        g = lambda *names: pick(ctx, [x.lower() for x in names])
        def C(*names):
            return convert(num(g(*names)), imperial)
        od = C("od")
        thk = C("thk", "basic thickness", "total thickness")
        wt = C("blank kg", "kg", "wt")
        if od is None and thk is None and wt is None:
            continue
        out.append({"nps": n, "rating": rating, "outer_diameter_mm": od,
                    "thickness_mm": thk, "center_to_center_mm": C("crs", "dist"),
                    "weight_spectacle_kg": wt, "source_file": fname})
    return out

def _fla_rating(fname):
    m = re.search(r"fla(?:_imperial)?[_]?(\d+)", fname.lower())
    return m.group(1) if m else None


def _flg_rating(fname):
    m = re.search(r"flg(?:_imperial)?[_]?(\d+)", fname.lower())
    return m.group(1) if m else None


ROUTER = [
    # flg = kucuk cap (1/2"-24"), fla = buyuk cap (26"-60"). Ikisi de ayni tabloya.
    ("tbl_flanges_asme_b16_5", lambda f: _flg_rating(f) is not None, rule_flange),
    ("tbl_flanges_asme_b16_5", lambda f: _fla_rating(f) is not None, rule_flange),
    ("tbl_pipes", lambda f: f.lower().startswith("pipe"), rule_pipe),
    ("tbl_fittings_buttweld_b16_9", lambda f: f.lower().startswith(
        ("45elbow", "90elbow", "90shortradius", "return", "shortradiusreturn",
         "straighttee", "reducingtee", "cap", "reducers", "stubend",
         "srra", "srrb", "ftbw")), rule_buttweld),
    ("tbl_fittings_socketweld_b16_11", lambda f: f.lower().startswith("ftsw"), rule_socketweld),
    ("tbl_fittings_threaded_b16_11", lambda f: f.lower().startswith("ftsc"), rule_threaded),
    ("tbl_valves_asme_b16_10", lambda f: f.lower().startswith("vlv"), rule_valve),
    ("tbl_gaskets_and_rtj", lambda f: f.lower().startswith(("grtj", "gflt", "gspr", "fldi")), rule_gasket),
    ("tbl_line_blanks_asme_b16_48", lambda f: f.lower().startswith("blnu"), rule_blank),
]

def main():
    files = sorted(f for f in os.listdir(SRC) if f.lower().endswith(".csv"))
    buckets = {t: [] for t in TABLES}
    routed, skipped, routed_files = 0, [], {}
    for fname in files:
        path = os.path.join(SRC, fname)
        if os.path.getsize(path) == 0:
            skipped.append((fname, "0-bayt"))
            continue
        idx, header, body = b4a_rows(path)
        if header is None:
            skipped.append((fname, "basliksiz"))
            continue
        imperial = is_imperial(fname)
        hit = False
        for tname, filt, rule in ROUTER:
            if filt(fname):
                rows = rule(fname, header, body, imperial)
                if rows:
                    buckets[tname].extend(rows)
                    routed_files[fname] = tname
                    routed += 1
                else:
                    skipped.append((fname, tname + ": satir uretilemedi"))
                hit = True
                break
        if not hit:
            skipped.append((fname, "yonlendirici disi (app disi kategori)"))
    os.makedirs(OUT, exist_ok=True)
    print(f"KAYNAK: {len(files)} dosya")
    total = 0
    for tname, cols in TABLES.items():
        rows = buckets[tname]
        total += len(rows)
        with open(os.path.join(OUT, tname + ".csv"), "w", encoding="utf-8", newline="") as f:
            w = csv.writer(f, lineterminator="\n")
            w.writerow(cols)
            for r in rows:
                w.writerow(["" if r.get(c) is None else r.get(c) for c in cols])
        print(f"  {tname}: {len(rows)} satir")
    print(f"CIKTI: {total} satir -> {OUT}")
    print(f"YONLENDIRILEN DOSYA: {routed} / {len(files)}")
    if skipped:
        print(f"ATLANAN ({len(skipped)}):")
        for f, why in sorted(skipped)[:60]:
            print(f"  - {f} :: {why}")
        if len(skipped) > 60:
            print(f"  ... +{len(skipped) - 60} dosya daha")
    # fail kapisi: kritik tablolardan biri bossa cikis kodu 1
    critical = ["tbl_flanges_asme_b16_5", "tbl_pipes", "tbl_fittings_buttweld_b16_9",
                "tbl_valves_asme_b16_10"]
    empty = [t for t in critical if not buckets[t]]
    if empty:
        print("KRITIK TABLO BOS:", empty)
        return 1
    return 0

if __name__ == "__main__":
    sys.exit(main())
