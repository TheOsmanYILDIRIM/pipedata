#!/usr/bin/env python3
"""
Pipedata Kanonik CSV Üretici (Faz 7 — CSV-First Mimarisi)

Amaç: Android uygulamasının SQLite yerine doğrudan metin CSV'den okumasini
saglamak. Bu script, Faz 6'da 32/32 testle dogrulanmis kanonik pipedata.db
icerisindeki uygulama-kritik tablolari BIREBIR kanonik CSV dosyalarina duser.

- raw_json kolonu HARIC tutulur (app o kolonu kullanmaz; ham veri zaten
  assets/csv/raw/ altinda 956 orijinal CSV olarak gomuluyor).
- Kolon adlari, PipedataDbHelper/Kotlin sorgularindaki SQL kolon adlariyla
  BIREBIR AYNI olur -> Kotlin'de kolon kaymasi imkansiz.
- Cikti: data/csv/canonical/tbl_*.csv  (UTF-8, LF, baslikli)
- Mutabakat: csv_vs_db_audit.py ile her satir birebir kiyaslanir (0 fark hedefi).

Kullanim:
    py-cool build_canonical_csv.py [--verify]
"""
import csv
import io
import os
import sqlite3
import sys

PROJECT = os.path.dirname(os.path.abspath(__file__))
DB = os.path.join(PROJECT, "pipedata.db")
OUT_DIR = os.path.join(PROJECT, "data", "csv", "canonical")

# Uygulamanin (PipedataDbHelper/CsvDataStore) okudugu tablolar.
# Kotlin sorgularindaki kolon adlariyla birebir uyumlu.
APP_TABLES = [
    "tbl_flanges_asme_b16_5",
    "tbl_pipes",
    "tbl_fittings_buttweld_b16_9",
    "tbl_fittings_socketweld_b16_11",
    "tbl_fittings_threaded_b16_11",
    "tbl_valves_asme_b16_10",
    "tbl_gaskets_and_rtj",
    "tbl_line_blanks_asme_b16_48",
]

# raw_json disinda da uygulamanin tasimadigi (veya app sorgularinda
# kullanilmayan) agir/gorsel kolonlar: svg icerikleri assets'te duruyor.
EXCLUDE_COLS = {"raw_json"}


def canonical_tables(conn):
    return {r[0] for r in conn.execute(
        "SELECT name FROM sqlite_master WHERE type='table' AND name LIKE 'tbl_%'")}


def write_table(conn, tname, out_dir):
    cols = [r[1] for r in conn.execute(f"PRAGMA table_info({tname})").fetchall()]
    cols = [c for c in cols if c not in EXCLUDE_COLS and c != "id"]
    rows = conn.execute(f"SELECT {', '.join(cols)} FROM {tname}").fetchall()
    out = os.path.join(out_dir, f"{tname}.csv")
    with open(out, "w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, lineterminator="\n")
        writer.writerow(cols)
        for r in rows:
            writer.writerow(["" if v is None else v for v in r])
    return len(rows)


def main():
    only_app = "--app-only" in sys.argv
    verify = "--verify" in sys.argv

    if not os.path.exists(DB):
        print(f"HATA: {DB} bulunamadi. Once rebuild + canonical_merge calistirilmali.")
        return 1

    os.makedirs(OUT_DIR, exist_ok=True)
    conn = sqlite3.connect(f"file:{DB}?mode=ro", uri=True)
    conn.row_factory = sqlite3.Row

    tables = APP_TABLES if only_app else sorted(canonical_tables(conn))
    summary = {}
    for t in tables:
        summary[t] = write_table(conn, t, OUT_DIR)

    conn.close()

    total_rows = 0
    for t, n in sorted(summary.items()):
        total_rows += n
        print(f"  {t}: {n} satir")
    print(f"Kanonik CSV uretildi: {len(summary)} tablo / {total_rows} satir -> {OUT_DIR}")

    if verify:
        import csv_vs_db_audit as audit
        code = audit.main(["--csv-dir", OUT_DIR])
        if code != 0:
            print("==> VERIFY BASARISIZ: kanonik CSV ile DB uyusmuyor.")
            return code
        print("==> VERIFY OK: kanonik CSV, pipedata.db ile 0 fark.")
    return 0


if __name__ == "__main__":
    sys.exit(main())