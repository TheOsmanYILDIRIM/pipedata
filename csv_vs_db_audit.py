#!/usr/bin/env python3
"""
Pipedata CSV <-> SQLite Mutabakat Denetcisi (csv_vs_db_audit.py)

Kanonik CSV dosyalari ile kanonik pipedata.db tablolarini SATIR SATIR,
KOLON KOLON kiyaslar. Amac: "app CSV'den okur" mimarisinde, CSV'nin DB'deki
dogrulanmis veriden SAPMASI durumunda CI'da derhal fail edilmesi.

Kurallar:
  - Her CSV basligi DB kolonuyla birebir eslesmeli (fazlalik/eksik kolon = fail).
  - NULL DB degeri <-> bos CSV hucresi olarak esit kabul edilir.
  - Sayisal kolonlarda metinsel birebir (string) esitlik aranir; DB'den gelen
    REAL degerlerin yuvarlama farki olmamasi icin CSV uretici duyarliligi korur.
  - --fail-on-diff ile fark varsa exit 1 (CI kapisi).

Kullanim:
    py-cool csv_vs_db_audit.py [--csv-dir data/csv/canonical] [--db pipedata.db] [--fail-on-diff]
"""
import argparse
import csv
import os
import sqlite3
import sys

PROJECT = os.path.dirname(os.path.abspath(__file__))
DEFAULT_CSV_DIR = os.path.join(PROJECT, "data", "csv", "canonical")
DEFAULT_DB = os.path.join(PROJECT, "pipedata.db")

SKIP_COLS = {"id", "raw_json"}


def norm_db(v):
    """DB degeri -> CSV karsilastirma formu (None -> '')."""
    if v is None:
        return ""
    return str(v)


def norm_csv(v):
    return "" if v is None else str(v).strip()


def audit(csv_dir, db_path, fail_on_diff=False):  # noqa: C901
    if not os.path.isdir(csv_dir):
        print(f"HATA: CSV dizini yok: {csv_dir}")
        return 2
    if not os.path.exists(db_path):
        print(f"HATA: DB yok: {db_path}")
        return 2

    conn = sqlite3.connect(f"file:{db_path}?mode=ro", uri=True)
    total_checks = 0
    mismatches = 0
    missing_tables = 0
    row_mismatch_tables = []

    csv_files = sorted(f for f in os.listdir(csv_dir) if f.endswith(".csv"))
    for fname in csv_files:
        tname = fname[:-4]
        # Tablo DB'de var mi?
        exists = conn.execute(
            "SELECT 1 FROM sqlite_master WHERE type='table' AND name=?", (tname,)
        ).fetchone()
        if not exists:
            print(f"  [KAYIP-TABLO] {fname} -> DB'de {tname} tablosu yok")
            missing_tables += 1
            continue

        with open(os.path.join(csv_dir, fname), encoding="utf-8", newline="") as f:
            reader = csv.reader(f)
            try:
                headers = next(reader)
            except StopIteration:
                print(f"  [BOS] {fname}")
                missing_tables += 1
                continue
            csv_rows = [row for row in reader]

        db_cols = [r[1] for r in conn.execute(f"PRAGMA table_info({tname})").fetchall()]
        db_cols = [c for c in db_cols if c not in SKIP_COLS]
        if headers != db_cols:
            print(f"  [KOLON-FARKI] {fname}: CSV başlıkları DB ile uyuşmuyor")
            mismatch_h = ", ".join(headers)
            mismatch_d = ", ".join(db_cols)
            print(f"    CSV : {mismatch_h}")
            print(f"    DB  : {mismatch_d}")
            mismatches += max(len(headers), len(db_cols))
            continue

        db_rows = conn.execute(f"SELECT {', '.join(db_cols)} FROM {tname}").fetchall()
        if len(csv_rows) != len(db_rows):
            print(
                f"  [SATIR-SAYISI] {fname}: CSV={len(csv_rows)} DB={len(db_rows)}"
            )
            mismatches += abs(len(csv_rows) - len(db_rows))
            row_mismatch_tables.append(tname)
            continue

        table_mismatch = 0
        for i, (cr, dr) in enumerate(zip(csv_rows, db_rows)):
            if len(cr) != len(dr):
                table_mismatch += 1
                continue
            for ci, (cv, dv) in enumerate(zip(cr, dr)):
                total_checks += 1
                if norm_csv(cv) != norm_db(dv):
                    table_mismatch += 1
                    mismatches += 1
                    if mismatches <= 25:
                        print(
                            f"  [FARK] {fname} satir {i + 2} kolon[{headers[ci]}] "
                            f"CSV='{cv}' DB='{dv}'"
                        )
        if table_mismatch:
            row_mismatch_tables.append(f"{tname}({table_mismatch})")

    conn.close()
    print(f"\nMutabakat sonucu: {len(csv_files)} CSV, {total_checks} kolon-kontrol, "
          f"{mismatches} fark")
    if mismatches:
        print(f"Fark li tablolar: {', '.join(row_mismatch_tables)}")
        if fail_on_diff:
            print("==> FAIL: fark var (--fail-on-diff)")
            return 1
    else:
        print("==> OK: CSV <-> DB 0 fark")
    return 0 if mismatches == 0 else 1 if fail_on_diff else 0


def main(argv=None):
    p = argparse.ArgumentParser()
    p.add_argument("--csv-dir", default=DEFAULT_CSV_DIR)
    p.add_argument("--db", default=DEFAULT_DB)
    p.add_argument("--fail-on-diff", action="store_true")
    a = p.parse_args(argv)
    return audit(a.csv_dir, a.db, a.fail_on_diff)


if __name__ == "__main__":
    sys.exit(main())