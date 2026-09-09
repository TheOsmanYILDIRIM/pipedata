#!/usr/bin/env python3
"""Faz6 B-2: Canonical Merge — BUG-5 (metrik+imperial çift kayıt) ve BUG-4 (pipe içsel tutarsızlık) çözümü.
Kural: her doğal anahtar için TEK satır; pipe'ta ID=OD-2t tutarlılığı (tol %2) önce, sonra Metric önceliği.
mm kolonlarında grup içi çelişki >1mm ise sessiz seçim YASAK -> reports/canonical_merge_anomaly_report.md'ye loglanır.
Kazanan satırın raw_json'ına merged_from eklenir (izlenebilirlik)."""
import sqlite3, json, os, math

DB = os.path.join(os.path.dirname(os.path.abspath(__file__)), "pipedata.db")
TOL_MM = 1.0          # mm kolonlarinda metrik/imperial celsesi icin esik
CONSIST_TOL = 0.02    # ID = OD - 2t butunlugu toleransi (%2)

MM_COLS = {
    "tbl_pipes": ["outer_diameter_mm", "wall_thickness_mm", "inner_diameter_mm", "weight_kg"],
    "tbl_flanges_asme_b16_5": ["outer_diameter_mm", "thickness_mm", "hub_diameter_mm", "length_thru_hub_mm",
                               "bore_diameter_mm", "rf_diameter_mm", "pcd_mm", "bolt_hole_diameter_mm",
                               "stud_bolt_length_mm", "weight_kg"],
    "tbl_valves_asme_b16_10": ["face_to_face_mm", "handwheel_height_mm", "handwheel_diameter_mm", "weight_kg"],
}
# Fusion (yalniz tbl_pipes): iki kaynak dosyanin hatalari asimetrik oldugu icin kolon-bazli fizik-dogrulamali
# fusion yapilir: (1) mm ucllusu OD/WT/ID ID=OD-2t butunlugunu SAGLAYAN kaynaktan (1/8 STD'de metrik dosya
# bozuk: WT=1.24, ID tutarsiz; imperial dogru), (2) MoI/Sm/RoG pi/64 formulune uyan kaynaktan (2|40'ta imperial
# dosyanin MoI'u in-birim kalmis: 0.66; metrik 27.65 dogru), (3) weight_*_lb_ft + unit_system Imperial kazananin
# (orijinal app kanonik kaynagi: Benchmark A/D 54.62 lb/ft). Tum sapmalar anomali raporuna loglanir.
KEYS = {
    "tbl_pipes": ["nps", "schedule"],
    "tbl_flanges_asme_b16_5": ["nps", "rating", "flange_type"],
    "tbl_valves_asme_b16_10": ["valve_type", "nps", "rating", "end_connection"],
}

def kval(v):
    return "" if v is None else str(v)

def pipe_consistent(r):
    """ID = OD - 2t butunlugu (tol: max(%2*OD, 0.3mm)); eksik kolon varsa None (kararsiz)."""
    od, wt, idm = r.get("outer_diameter_mm"), r.get("wall_thickness_mm"), r.get("inner_diameter_mm")
    if od is None or wt is None or idm is None:
        return None
    return abs(idm - (od - 2.0 * wt)) <= max(CONSIST_TOL * od, 0.3)

def pick_winner(table, rows):
    """Kazanan secimi: pipe'ta ID=OD-2t tutarli satirlar arasinda Imperial ONCELIKLI (orijinal uygulama bu satiri
    gosterir: Benchmark A/D 54.62 lb/ft birebir Imperial satirda; iki satirin mm kolonlari da dolu ve esit oldugu
    icin metrik veri KAYBI YOK). Diger tablolar: Metric onceligi."""
    if table == "tbl_pipes":
        cons = [r for r in rows if pipe_consistent(r) is True]
        pool = cons if cons else rows
    else:
        pool = rows
    pref = ("Imperial", "Metric") if table == "tbl_pipes" else ("Metric", "Imperial")
    for u in pref:
        for r in pool:
            if r.get("unit_system") == u:
                return r
    return pool[0]

def scan_conflicts(table, rows, anomalies):
    cols = MM_COLS[table]
    for c in cols:
        vals = [r.get(c) for r in rows if r.get(c) is not None]
        if len(vals) >= 2 and (max(vals) - min(vals)) > TOL_MM:
            anomalies.append((table, c, rows[0], min(vals), max(vals)))

def main():
    conn = sqlite3.connect(DB)
    conn.row_factory = sqlite3.Row
    cur = conn.cursor()
    anomalies = []
    for table, keys in KEYS.items():
        rows = [dict(r) for r in cur.execute(f"SELECT rowid AS _rid, * FROM {table}").fetchall()]
        before = len(rows)
        groups = {}
        for r in rows:
            gkey = tuple(kval(r[k]) for k in keys)
            groups.setdefault(gkey, []).append(r)
        dup_groups = 0
        for gkey, grp in groups.items():
            if len(grp) > 1:
                dup_groups += 1
                scan_conflicts(table, grp, anomalies)
                w = pick_winner(table, grp)
                drop = [r["_rid"] for r in grp if r["_rid"] != w["_rid"]]
                # Pipe unit-fusion: fizik-dogrulamali kolon bazli birlestirme (yukaridaki aciklama bloku).
                fused = []
                if table == "tbl_pipes":
                    met = next((r for r in grp if r.get("unit_system") == "Metric"), None)
                    met_ok = met is not None and pipe_consistent(met) is True
                    src3 = met if met_ok else w
                    for c in ("outer_diameter_mm", "wall_thickness_mm", "inner_diameter_mm"):
                        v = src3.get(c)
                        if v is not None and w.get(c) != v:
                            if w.get(c) is not None and abs(w[c] - v) > TOL_MM:
                                anomalies.append((table, c, grp[0], min(w[c], v), max(w[c], v)))
                            w[c] = v
                            fused.append(c)
                    if met is not None and not met_ok:
                        _od, _wt, _idm = (met.get("outer_diameter_mm"), met.get("wall_thickness_mm"),
                                          met.get("inner_diameter_mm"))
                        if _od and _wt and _idm:
                            _exp = _od - 2.0 * _wt
                            anomalies.append((table, "inner_diameter_mm (ID=OD-2t ihlali: metrik kaynak bozuk)",
                                              met, min(_idm, _exp), max(_idm, _exp)))

                    def _moi_cm4(od, idm):
                        return math.pi / 64.0 * (od ** 4 - idm ** 4) / 1e4

                    od_f, id_f = w.get("outer_diameter_mm"), w.get("inner_diameter_mm")
                    if od_f and id_f and od_f > id_f:
                        tgt = _moi_cm4(od_f, id_f)
                        cands = []
                        for r in ([met] if met else []) + [w]:
                            mv = r.get("moment_of_inertia_cm4")
                            if mv and tgt > 0:
                                cands.append((abs(mv - tgt) / tgt, r))
                        if cands:
                            rel, srcm = min(cands, key=lambda x: x[0])
                            if rel <= 0.05:
                                for c in ("moment_of_inertia_cm4", "section_modulus_cm3", "radius_of_gyration_mm"):
                                    v = srcm.get(c)
                                    if v is not None and w.get(c) != v:
                                        w[c] = v
                                        fused.append(c)
                            else:
                                anomalies.append((table, "moment_of_inertia_cm4 (formul uyusmazligi)",
                                                  grp[0], tgt * 0.95, tgt * 1.05))
                        for c in ("weight_empty_kg_m", "weight_total_kg_m"):
                            if met and met.get(c) is not None and w.get(c) != met[c]:
                                w[c] = met[c]
                                fused.append(c)
                cur.executemany(f"DELETE FROM {table} WHERE rowid = ?", [(d,) for d in drop])
                srcs = [r.get("source_file") or "?" for r in grp]
                try:
                    wj = json.loads(w.get("raw_json") or "{}")
                    if not isinstance(wj, dict):
                        wj = {"legacy": wj}
                except Exception:
                    wj = {}
                wj["merged_from"] = srcs
                wj["merge_note"] = ("Faz6 canonical merge: Imperial onceligi (orijinal app kanonik kaynagi); ID=OD-2t fizik-dogrulamali kolon fusion: "
                                    + (','.join(fused) if fused else "fusion gerekmedi")
                                    if table == "tbl_pipes" else "Faz6 canonical merge: Metric onceligi")
                cur.execute(f"UPDATE {table} SET raw_json = ? WHERE rowid = ?",
                            (json.dumps(wj, ensure_ascii=False), w["_rid"]))
                if fused:
                    cur.execute(f"UPDATE {table} SET {', '.join(f'{c} = ?' for c in fused)} WHERE rowid = ?",
                                [w[c] for c in fused] + [w["_rid"]])
        after = cur.execute(f"SELECT COUNT(*) FROM {table}").fetchone()[0]
        print(f"{table}: {before} -> {after} satir (merge edilen grup: {dup_groups})")
    conn.commit()
    conn.close()

    rpt_dir = os.path.join(os.path.dirname(os.path.abspath(__file__)), "reports")
    os.makedirs(rpt_dir, exist_ok=True)
    rpt = os.path.join(rpt_dir, "canonical_merge_anomaly_report.md")
    with open(rpt, "w", encoding="utf-8") as f:
        f.write("# Canonical Merge Anomali Raporu (Faz 6)\n\n")
        f.write("Kural: mm kolonlarinda metrik/imperial celsesi > 1mm ise sessiz secim YAPILMAZ; asagida loglanir.\n\n")
        f.write("| Tablo | Kolon | Anahtar | Min | Max | Fark | Karar |\n|---|---|---|---|---|---|---|\n")
        for table, col, r0, mn, mx in anomalies:
            key = ", ".join(f"{k}={kval(r0.get(k))}" for k in KEYS[table])
            f.write(f"| {table} | {col} | {key} | {mn:.2f} | {mx:.2f} | {mx-mn:.2f} | Metric onceligiyle secildi (loglandi) |\n")
        if not anomalies:
            f.write("\n_>1mm celiski saptanmadi._\n")
    print(f"Anomali raporu: {rpt} ({len(anomalies)} kayit)")

if __name__ == "__main__":
    main()
