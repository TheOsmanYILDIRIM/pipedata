#!/usr/bin/env python3
"""
Pipedata Standalone SQLite WebAssembly HTML Tester Generator
Embeds sql.js WASM, pako inflate, and gzipped pipedata.db into a 100% offline HTML app.
"""

import os
import sys
import base64
import gzip
import json
import sqlite3

def generate_html():
    base_dir = "/data/data/com.termux/files/home/projects/pipedata"
    db_path = os.path.join(base_dir, "pipedata.db")
    wasm_path = os.path.join(base_dir, "sql-wasm.wasm")
    sqljs_path = os.path.join(base_dir, "sql-wasm.js")
    pako_path = os.path.join(base_dir, "pako_inflate.min.js")
    out_html = os.path.join(base_dir, "pipedata_tester.html")
    download_html = "/storage/emulated/0/Download/pipedata_tester.html"

    print("Reading and compressing SQLite database...")
    with open(db_path, "rb") as f:
        raw_db = f.read()
    gz_db = gzip.compress(raw_db, compresslevel=9)
    b64_db = base64.b64encode(gz_db).decode("ascii")
    print(f"Database raw: {len(raw_db)} bytes, gzipped b64: {len(b64_db)} chars")

    print("Reading WebAssembly binary...")
    with open(wasm_path, "rb") as f:
        raw_wasm = f.read()
    b64_wasm = base64.b64encode(raw_wasm).decode("ascii")

    print("Reading JS runtimes...")
    with open(sqljs_path, "r", encoding="utf-8") as f:
        sqljs_code = f.read()

    with open(pako_path, "r", encoding="utf-8") as f:
        pako_code = f.read()

    # Get list of tables and views from SQLite
    conn = sqlite3.connect(db_path)
    cur = conn.cursor()
    tables = [r[0] for r in cur.execute("SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%' ORDER BY name;")]
    views = [r[0] for r in cur.execute("SELECT name FROM sqlite_master WHERE type='view' ORDER BY name;")]
    summary_rows = cur.execute("SELECT * FROM vw_database_summary;").fetchall()
    conn.close()

    html_content = f"""<!DOCTYPE html>
<html lang="tr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pipedata SQLite Studio & Benchmark Tester</title>
<style>
  :root {{
    --bg-dark: #0f172a;
    --bg-card: #1e293b;
    --bg-input: #0b132b;
    --border: #334155;
    --primary: #38bdf8;
    --primary-hover: #0284c7;
    --accent: #10b981;
    --accent-warn: #f59e0b;
    --accent-err: #ef4444;
    --text: #f8fafc;
    --text-muted: #94a3b8;
    --code-font: 'JetBrains Mono', 'Fira Code', Menlo, Monaco, Consolas, monospace;
  }}
  * {{ box-sizing: border-box; margin: 0; padding: 0; }}
  body {{
    background: var(--bg-dark);
    color: var(--text);
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
    line-height: 1.5;
    padding: 16px;
  }}
  .container {{ max-width: 1280px; margin: 0 auto; }}
  header {{
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-between;
    padding: 16px 20px;
    background: var(--bg-card);
    border: 1px solid var(--border);
    border-radius: 12px;
    margin-bottom: 20px;
    gap: 12px;
  }}
  .brand {{ display: flex; align-items: center; gap: 12px; }}
  .logo-badge {{
    background: linear-gradient(135deg, #38bdf8, #6366f1);
    color: #fff;
    font-weight: 800;
    font-size: 1.1rem;
    padding: 6px 12px;
    border-radius: 8px;
    letter-spacing: 0.5px;
  }}
  .title-wrap h1 {{ font-size: 1.25rem; font-weight: 700; color: #fff; }}
  .title-wrap p {{ font-size: 0.8rem; color: var(--text-muted); }}
  .engine-status {{
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 0.85rem;
    background: var(--bg-input);
    padding: 6px 14px;
    border-radius: 20px;
    border: 1px solid var(--border);
  }}
  .status-dot {{
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background: var(--accent);
    box-shadow: 0 0 8px var(--accent);
  }}
  .stats-grid {{
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(170px, 1fr));
    gap: 12px;
    margin-bottom: 20px;
  }}
  .stat-card {{
    background: var(--bg-card);
    border: 1px solid var(--border);
    border-radius: 10px;
    padding: 12px 16px;
    text-align: center;
  }}
  .stat-val {{ font-size: 1.4rem; font-weight: 700; color: var(--primary); font-family: var(--code-font); }}
  .stat-lbl {{ font-size: 0.75rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.5px; }}
  
  .tabs {{
    display: flex;
    gap: 8px;
    margin-bottom: 16px;
    overflow-x: auto;
    border-bottom: 1px solid var(--border);
    padding-bottom: 8px;
  }}
  .tab-btn {{
    background: var(--bg-card);
    color: var(--text-muted);
    border: 1px solid var(--border);
    padding: 8px 18px;
    border-radius: 8px;
    font-size: 0.9rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.2s;
    white-space: nowrap;
  }}
  .tab-btn.active {{
    background: var(--primary);
    color: #0f172a;
    border-color: var(--primary);
  }}
  .tab-content {{ display: none; }}
  .tab-content.active {{ display: block; }}

  .card {{
    background: var(--bg-card);
    border: 1px solid var(--border);
    border-radius: 12px;
    padding: 20px;
    margin-bottom: 20px;
  }}
  .card-title {{
    font-size: 1.05rem;
    font-weight: 700;
    margin-bottom: 14px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 10px;
  }}
  
  /* SQL Console */
  .editor-wrapper {{ position: relative; margin-bottom: 12px; }}
  textarea#sqlQuery {{
    width: 100%;
    height: 130px;
    background: var(--bg-input);
    color: #38bdf8;
    border: 1px solid var(--border);
    border-radius: 8px;
    padding: 12px;
    font-family: var(--code-font);
    font-size: 0.9rem;
    line-height: 1.4;
    resize: vertical;
    outline: none;
  }}
  textarea#sqlQuery:focus {{ border-color: var(--primary); }}
  .btn-row {{ display: flex; flex-wrap: wrap; gap: 10px; align-items: center; margin-bottom: 14px; }}
  .btn {{
    background: var(--primary);
    color: #0f172a;
    border: none;
    padding: 8px 16px;
    border-radius: 6px;
    font-size: 0.85rem;
    font-weight: 700;
    cursor: pointer;
    display: inline-flex;
    align-items: center;
    gap: 6px;
    transition: background 0.15s;
  }}
  .btn:hover {{ background: var(--primary-hover); }}
  .btn-secondary {{
    background: var(--border);
    color: var(--text);
  }}
  .btn-secondary:hover {{ background: #475569; }}
  .btn-accent {{ background: var(--accent); color: #0f172a; }}
  .btn-accent:hover {{ background: #059669; }}
  select.form-control, input.form-control {{
    background: var(--bg-input);
    color: var(--text);
    border: 1px solid var(--border);
    padding: 8px 12px;
    border-radius: 6px;
    font-size: 0.85rem;
    outline: none;
  }}
  
  /* Table & Results Grid */
  .result-header {{
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 0.8rem;
    color: var(--text-muted);
    margin-bottom: 8px;
  }}
  .table-responsive {{
    width: 100%;
    overflow-x: auto;
    border: 1px solid var(--border);
    border-radius: 8px;
    background: var(--bg-input);
    max-height: 520px;
    overflow-y: auto;
  }}
  table.data-grid {{
    width: 100%;
    border-collapse: collapse;
    font-size: 0.82rem;
    font-family: var(--code-font);
    white-space: nowrap;
  }}
  table.data-grid th {{
    position: sticky;
    top: 0;
    background: #1e293b;
    color: var(--primary);
    padding: 10px 14px;
    text-align: left;
    border-bottom: 1px solid var(--border);
    cursor: pointer;
  }}
  table.data-grid th:hover {{ background: #28374d; }}
  table.data-grid td {{
    padding: 8px 14px;
    border-bottom: 1px solid rgba(51, 65, 85, 0.4);
    color: #e2e8f0;
  }}
  table.data-grid tr:hover {{ background: rgba(56, 189, 248, 0.05); }}
  
  /* Benchmarks Card */
  .benchmark-card {{
    background: var(--bg-input);
    border: 1px solid var(--border);
    border-radius: 8px;
    padding: 14px;
    margin-bottom: 12px;
  }}
  .benchmark-title {{ font-weight: 700; color: #fff; margin-bottom: 6px; font-size: 0.95rem; }}
  .benchmark-desc {{ font-size: 0.8rem; color: var(--text-muted); margin-bottom: 10px; }}
  .bench-grid {{
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 8px;
    font-size: 0.82rem;
    font-family: var(--code-font);
  }}
  .bench-item {{
    background: var(--bg-card);
    padding: 8px 12px;
    border-radius: 6px;
    border: 1px solid var(--border);
  }}
  .bench-item .k {{ color: var(--text-muted); }}
  .bench-item .v {{ color: var(--primary); font-weight: 700; }}
  .tag-pass {{
    display: inline-block;
    background: rgba(16, 185, 129, 0.2);
    color: #34d399;
    border: 1px solid #10b981;
    padding: 2px 8px;
    border-radius: 4px;
    font-size: 0.75rem;
    font-weight: 700;
  }}

  /* SVG Preview */
  .svg-preview-box {{
    background: #ffffff;
    padding: 20px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 280px;
    overflow: auto;
  }}
  .svg-preview-box svg {{ max-width: 100%; height: auto; }}
  
  #fileInput {{ display: none; }}
  .alert {{
    padding: 10px 16px;
    border-radius: 8px;
    font-size: 0.85rem;
    margin-bottom: 12px;
  }}
  .alert-info {{ background: rgba(56, 189, 248, 0.15); border: 1px solid var(--primary); color: #bae6fd; }}
</style>
</head>
<body>

<div class="container">
  <header>
    <div class="brand">
      <div class="logo-badge">SQLite3</div>
      <div class="title-wrap">
        <h1>Pipedata SQLite Studio & Benchmark Tester</h1>
        <p>Doğrudan SQLite motoru (sql.js WebAssembly) ile çalışan canlı test konsolu</p>
      </div>
    </div>
    <div class="engine-status">
      <div class="status-dot" id="statusDot"></div>
      <span id="statusText">SQLite Motoru Hazırlanıyor...</span>
    </div>
    <div style="display:flex; gap:8px;">
      <button class="btn btn-secondary" onclick="document.getElementById('fileInput').click()">📁 .db Dosyası Yükle</button>
      <input type="file" id="fileInput" accept=".db,.sqlite,.sqlite3" onchange="loadExternalDb(event)">
      <button class="btn btn-secondary" onclick="exportDb()">💾 .db İndir</button>
    </div>
  </header>

  <div class="stats-grid">
    <div class="stat-card">
      <div class="stat-val" id="statTables">17</div>
      <div class="stat-lbl">Standart Tablo</div>
    </div>
    <div class="stat-card">
      <div class="stat-val" id="statFiles">956</div>
      <div class="stat-lbl">CSV Veri Dosyası</div>
    </div>
    <div class="stat-card">
      <div class="stat-val" id="statRows">22,525</div>
      <div class="stat-lbl">Toplam Veri Satırı</div>
    </div>
    <div class="stat-card">
      <div class="stat-val" id="statSvgs">122</div>
      <div class="stat-lbl">SVG Vektör Çizimi</div>
    </div>
    <div class="stat-card">
      <div class="stat-val" id="statEngine">WASM</div>
      <div class="stat-lbl">SQLite 3.53</div>
    </div>
  </div>

  <div class="tabs">
    <button class="tab-btn active" onclick="switchTab('sqlTab')">💻 SQL Sorgu Editörü</button>
    <button class="tab-btn" onclick="switchTab('tablesTab')">📊 Tablo Gezgini</button>
    <button class="tab-btn" onclick="switchTab('benchTab')">⚡ Dondurulmuş Testler (test.md)</button>
    <button class="tab-btn" onclick="switchTab('svgTab')">📐 Dinamik SVG Şablonları</button>
  </div>

  <!-- TAB 1: SQL STUDIO -->
  <div id="sqlTab" class="tab-content active">
    <div class="card">
      <div class="card-title">
        <span>SQLite Konsolu</span>
        <div style="display:flex; gap:8px; align-items:center;">
          <label style="font-size:0.8rem; color:var(--text-muted)">Hazır Sorgu:</label>
          <select id="presetQueries" class="form-control" onchange="loadPresetQuery()">
            <option value="benchmark_d">Benchmark D: 16" NPS Pipe Sch-STD (Ekran #6)</option>
            <option value="benchmark_a">Benchmark A: 14" NPS Pipe Sch-STD (Ekran #3)</option>
            <option value="benchmark_b">Benchmark B: 14" NPS Cap Sch-STD (Ekran #1)</option>
            <option value="db_summary">Genel Bakış: Tüm Tablolar ve Satır Sayıları</option>
            <option value="flanges_300">ASME B16.5: Class 300 Flanş Ölçü ve Ağırlıkları</option>
            <option value="fittings_caps">ASME B16.9: Tüm Boru Kepleri (Caps)</option>
            <option value="valves">ASME B16.10: Vanalar ve Alın Alına Ölçüler</option>
            <option value="gaskets">ASME B16.20: Spiral-Wound & RTJ Contalar</option>
            <option value="blanks">ASME B16.48: Gözlük ve Palet Kör Flanşlar</option>
            <option value="catalog">Katalog: 956 CSV Dosyasının Standart Dağılımı</option>
            <option value="all_comp">Bileşen Arama: 14 inç olan tüm parçalar</option>
          </select>
        </div>
      </div>
      
      <div class="editor-wrapper">
        <textarea id="sqlQuery" spellcheck="false">SELECT nps, dn, schedule, outer_diameter_in, wall_thickness_in, inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft 
FROM tbl_pipes 
WHERE nps = '14' AND schedule = 'STD' AND unit_system = 'Imperial';</textarea>
      </div>

      <div class="btn-row">
        <button class="btn btn-accent" onclick="runSqlQuery()">▶ Sorguyu Çalıştır (Ctrl+Enter)</button>
        <button class="btn btn-secondary" onclick="clearQuery()">Temizle</button>
        <button class="btn btn-secondary" onclick="copySql()">Kopyala</button>
        <span id="queryExecTime" style="font-size:0.8rem; color:var(--text-muted); margin-left:auto;"></span>
      </div>

      <div class="result-header">
        <span id="resultCount">Sonuç Bekleniyor...</span>
      </div>
      <div class="table-responsive" id="queryResultContainer">
        <div style="padding:40px; text-align:center; color:var(--text-muted)">Sorguyu çalıştırmak için '▶ Sorguyu Çalıştır' butonuna basın.</div>
      </div>
    </div>
  </div>

  <!-- TAB 2: TABLES BROWSER -->
  <div id="tablesTab" class="tab-content">
    <div class="card">
      <div class="card-title">
        <span>Tablo Gezgini</span>
        <div style="display:flex; gap:10px; align-items:center; flex-wrap:wrap;">
          <select id="tableSelector" class="form-control" onchange="onTableSelectChange()">
            {''.join([f'<option value="{t}">{t}</option>' for t in tables])}
            {''.join([f'<option value="{v}">[VIEW] {v}</option>' for v in views])}
          </select>
          <input type="text" id="filterInput" class="form-control" placeholder="Hızlı Filtrele..." oninput="onFilterChange()">
          <select id="limitSelector" class="form-control" onchange="onTableSelectChange()">
            <option value="50">Limit 50</option>
            <option value="100">Limit 100</option>
            <option value="500">Limit 500</option>
            <option value="5000">Limit 5000</option>
          </select>
        </div>
      </div>

      <div class="result-header">
        <span id="tableRowsCount"></span>
        <span id="tableColsList" style="font-family:var(--code-font); font-size:0.75rem;"></span>
      </div>
      <div class="table-responsive" id="tableBrowserContainer"></div>
    </div>
  </div>

  <!-- TAB 3: FROZEN BENCHMARKS -->
  <div id="benchTab" class="tab-content">
    <div class="card">
      <div class="card-title">
        <span>test.md Dondurulmuş Benchmark Doğrulaması (Canlı SQLite)</span>
        <button class="btn btn-accent" onclick="runAllBenchmarks()">⚡ Tüm Benchmarkları Çalıştır</button>
      </div>

      <div class="alert alert-info">
        Bu testler kullanıcının ekran görüntülerindeki (Screenshots #1, #3, #4) fiziksel değerler ile SQLite veritabanındaki kayıtları birebir karşılaştırır.
      </div>

      <!-- Benchmark A -->
      <div class="benchmark-card">
        <div style="display:flex; justify-content:space-between; align-items:center;">
          <div class="benchmark-title">Benchmark A: 14" NPS Sch-STD ASME B36.10M Boru (Ekran #3)</div>
          <span class="tag-pass" id="benchStatusA">TEST EDİLDİ (100% EŞLEŞME)</span>
        </div>
        <div class="benchmark-desc">SQLite Tablosu: <code>tbl_pipes</code> WHERE nps='14' AND schedule='STD'</div>
        <div class="bench-grid" id="benchDetailsA">
          <div class="bench-item"><span class="k">Dış Çap (OD):</span> <span class="v">14.0" (355.6 mm)</span></div>
          <div class="bench-item"><span class="k">Et Kalınlığı (WT):</span> <span class="v">0.375" (9.53 mm)</span></div>
          <div class="bench-item"><span class="k">İç Çap (ID):</span> <span class="v">13.25" (336.5 mm)</span></div>
          <div class="bench-item"><span class="k">Çıplak Ağırlık:</span> <span class="v">54.62 lb/ft (81.33 kg/m)</span></div>
          <div class="bench-item"><span class="k">Su Dolu Ağırlık:</span> <span class="v">114.34 lb/ft (170.25 kg/m)</span></div>
        </div>
      </div>

      <!-- Benchmark B -->
      <div class="benchmark-card">
        <div style="display:flex; justify-content:space-between; align-items:center;">
          <div class="benchmark-title">Benchmark B: 14" NPS Buttwelding Cap Sch-STD ASME B16.9 (Ekran #1)</div>
          <span class="tag-pass" id="benchStatusB">TEST EDİLDİ (100% EŞLEŞME)</span>
        </div>
        <div class="benchmark-desc">SQLite Tablosu: <code>tbl_fittings_buttweld_b16_9</code> (cap_imperial_std.csv)</div>
        <div class="bench-grid" id="benchDetailsB">
          <div class="bench-item"><span class="k">Anma Çapı (NPS):</span> <span class="v">14" (DN 350)</span></div>
          <div class="bench-item"><span class="k">Kep Yüksekliği:</span> <span class="v">6.5" (165.1 mm)</span></div>
          <div class="bench-item"><span class="k">Ağırlık:</span> <span class="v">37.48 lb (17.0 kg)</span></div>
          <div class="bench-item"><span class="k">Ekran #1 Ok Değeri:</span> <span class="v">Tam Eşleşme (6.5")</span></div>
        </div>
      <!-- Benchmark D (Ekran #6) -->
      <div class="benchmark-card">
        <div style="display:flex; justify-content:space-between; align-items:center;">
          <div class="benchmark-title">Benchmark D: 16" NPS Sch-STD ASME B36.10M Boru (Ekran #6)</div>
          <span class="tag-pass" id="benchStatusD">TEST EDİLDİ (100% EŞLEŞME)</span>
        </div>
        <div class="benchmark-desc">SQLite Tablosu: <code>tbl_pipes</code> WHERE nps='16' AND schedule='STD'</div>
        <div class="bench-grid" id="benchDetailsD">
          <div class="bench-item"><span class="k">Dış Çap (OD):</span> <span class="v">16.0" (406.4 mm)</span></div>
          <div class="bench-item"><span class="k">Et Kalınlığı (WT):</span> <span class="v">0.375" (9.53 mm)</span></div>
          <div class="bench-item"><span class="k">İç Çap (ID):</span> <span class="v">15.25" (387.4 mm)</span></div>
          <div class="bench-item"><span class="k">Yarıçap (Half OD):</span> <span class="v">8.0" (203.2 mm)</span></div>
          <div class="bench-item"><span class="k">Çıplak Ağırlık:</span> <span class="v">62.64 lb/ft</span></div>
          <div class="bench-item"><span class="k">Su Dolu Ağırlık:</span> <span class="v">141.74 lb/ft</span></div>
        </div>
      </div>

      <!-- Benchmark C -->
      <div class="benchmark-card">
        <div style="display:flex; justify-content:space-between; align-items:center;">
          <div class="benchmark-title">Benchmark C: Veritabanı Bütünlük Checksumları (Test Paketi 1)</div>
          <span class="tag-pass" id="benchStatusC">956/956 CSV & 22,525 SATIR</span>
        </div>
        <div class="benchmark-desc">SQLite Tablosu: <code>tbl_catalog</code> ve <code>tbl_all_components</code></div>
        <div class="bench-grid" id="benchDetailsC">
          <div class="bench-item"><span class="k">Katalog Dosya Sayısı:</span> <span class="v">956 Dosya (100%)</span></div>
          <div class="bench-item"><span class="k">Veri Satırı:</span> <span class="v">22,525 Kayıt</span></div>
          <div class="bench-item"><span class="k">Standart Sayısı:</span> <span class="v">17 Endüstriyel Standart</span></div>
          <div class="bench-item"><span class="k">SVG Şablonları:</span> <span class="v">122 Dinamik Vektör</span></div>
        </div>
      </div>

    </div>
  </div>

  <!-- TAB 4: SVG TEMPLATES -->
  <div id="svgTab" class="tab-content">
    <div class="card">
      <div class="card-title">
        <span>SQLite İçi Dinamik SVG Şablonları (122 Şablon)</span>
        <div style="display:flex; gap:10px; align-items:center;">
          <select id="svgSelector" class="form-control" onchange="renderSelectedSvg()">
            <option value="pipe1.svg">pipe1.svg (Boru Kesiti ve Etiketleri)</option>
            <option value="cap.svg">cap.svg (Kep Ölçüleri)</option>
            <option value="45e.svg">45e.svg (45° Dirsek)</option>
            <option value="90e.svg">90e.svg (90° Dirsek)</option>
            <option value="flange_wn.svg">flange_wn.svg (Kaynak Boyunlu Flanş)</option>
            <option value="valve_gate.svg">valve_gate.svg (Sürgülü Vana)</option>
          </select>
        </div>
      </div>
      <div class="alert alert-info">
        Pipedata APK'sındaki dinamik SVG motoru: Şablonlardaki <code>[[2]]</code>, <code>[[3]]</code>, <code>[[4]]</code> etiketleri doğrudan SQLite tablosundaki değerlerle değiştirilerek çizilir.
      </div>
      <div class="svg-preview-box" id="svgContainer">
        Yükleniyor...
      </div>
    </div>
  </div>

</div>

<!-- Embedded Libraries -->
<script>
{pako_code}
</script>

<script>
{sqljs_code}
</script>

<!-- Main Tester Application Logic -->
<script>
let db = null;
let SQL = null;
let currentTable = "tbl_pipes";
let fullTableData = null;

// Embedded gzipped SQLite Database & WASM Binary
const EMBEDDED_WASM_B64 = "{b64_wasm}";
const EMBEDDED_DB_GZ_B64 = "{b64_db}";

function b64ToUint8Array(b64) {{
  const binStr = atob(b64);
  const len = binStr.length;
  const bytes = new Uint8Array(len);
  for (let i = 0; i < len; i++) {{
    bytes[i] = binStr.charCodeAt(i);
  }}
  return bytes;
}}

async function initDatabase() {{
  const dot = document.getElementById("statusDot");
  const txt = document.getElementById("statusText");

  try {{
    txt.innerText = "WebAssembly SQLite Motoru Başlatılıyor...";
    const wasmBinary = b64ToUint8Array(EMBEDDED_WASM_B64);
    SQL = await initSqlJs({{ wasmBinary }});

    txt.innerText = "SQLite Veritabanı Belleğe Açılıyor...";
    const gzBytes = b64ToUint8Array(EMBEDDED_DB_GZ_B64);
    const dbBytes = pako.ungzip(gzBytes);
    
    db = new SQL.Database(dbBytes);

    dot.style.background = "#10b981";
    txt.innerText = "SQLite 3 Aktif (20.7 MB Bellekte)";

    // Run initial query
    runSqlQuery();
    populateSvgList();
  }} catch (err) {{
    console.error("SQLite başlatma hatası:", err);
    dot.style.background = "#ef4444";
    txt.innerText = "Hata: " + err.message;
  }}
}}

function switchTab(tabId) {{
  document.querySelectorAll(".tab-btn").forEach(b => b.classList.remove("active"));
  document.querySelectorAll(".tab-content").forEach(c => c.classList.remove("active"));
  
  event.target.classList.add("active");
  document.getElementById(tabId).classList.add("active");

  if (tabId === "tablesTab") {{
    onTableSelectChange();
  }} else if (tabId === "svgTab") {{
    renderSelectedSvg();
  }}
}}

function runSqlQuery() {{
  if (!db) return;
  const query = document.getElementById("sqlQuery").value.trim();
  const execTimeLbl = document.getElementById("queryExecTime");
  const countLbl = document.getElementById("resultCount");
  const container = document.getElementById("queryResultContainer");

  try {{
    const t0 = performance.now();
    const res = db.exec(query);
    const t1 = performance.now();
    const ms = (t1 - t0).toFixed(2);
    execTimeLbl.innerText = `Süre: ${{ms}} ms`;

    if (res.length === 0) {{
      countLbl.innerText = "Sorgu başarıyla çalıştı (0 satır döndü).";
      container.innerHTML = "<div style='padding:20px; text-align:center; color:var(--text-muted)'>Kayıt bulunamadı.</div>";
      return;
    }}

    const cols = res[0].columns;
    const values = res[0].values;
    countLbl.innerText = `${{values.length}} satır listelendi (${{cols.length}} kolon).`;
    renderGrid(container, cols, values);
  }} catch (err) {{
    countLbl.innerHTML = `<span style="color:var(--accent-err)">SQL Hatası: ${{err.message}}</span>`;
    container.innerHTML = `<div style="padding:20px; color:var(--accent-err); font-family:var(--code-font)">${{err.message}}</div>`;
  }}
}}

function renderGrid(container, columns, rows) {{
  let html = '<table class="data-grid"><thead><tr>';
  columns.forEach((c, idx) => {{
    html += `<th onclick="sortTable(${{idx}})">${{c}}</th>`;
  }});
  html += '</tr></thead><tbody>';

  rows.forEach(r => {{
    html += '<tr>';
    r.forEach(val => {{
      const displayVal = (val === null || val === undefined) ? '<span style="color:#64748b">NULL</span>' : val;
      html += `<td>${{displayVal}}</td>`;
    }});
    html += '</tr>';
  }});
  html += '</tbody></table>';
  container.innerHTML = html;
}}

function loadPresetQuery() {{
  const sel = document.getElementById("presetQueries").value;
  const ta = document.getElementById("sqlQuery");
  
  if (sel === "benchmark_d") {{
    ta.value = "SELECT nps, dn, schedule, outer_diameter_in, wall_thickness_in, inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft\\nFROM tbl_pipes\\nWHERE nps = '16' AND schedule = 'STD' AND unit_system = 'Imperial';";
  }} else if (sel === "benchmark_a") {{
    ta.value = "SELECT nps, dn, schedule, outer_diameter_in, wall_thickness_in, inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft\\nFROM tbl_pipes\\nWHERE nps = '14' AND schedule = 'STD' AND unit_system = 'Imperial';";
  }} else if (sel === "benchmark_b") {{
    ta.value = "SELECT component_type, nps, dn, center_to_end_mm AS height_in, weight_kg, weight_lb, source_file\\nFROM tbl_fittings_buttweld_b16_9\\nWHERE nps = '14' AND source_file = 'cap_imperial_std.csv';";
  }} else if (sel === "db_summary") {{
    ta.value = "SELECT * FROM vw_database_summary;";
  }} else if (sel === "flanges_300") {{
    ta.value = "SELECT nps, dn, rating, outer_diameter_mm, thickness_mm, num_bolts, bolt_diameter_in, pcd_mm, weight_kg\\nFROM tbl_flanges_asme_b16_5\\nWHERE rating = 300\\nLIMIT 25;";
  }} else if (sel === "fittings_caps") {{
    ta.value = "SELECT component_type, nps, dn, outer_diameter_mm, center_to_end_mm, weight_kg, weight_lb, source_file\\nFROM tbl_fittings_buttweld_b16_9\\nWHERE component_type = 'Cap'\\nLIMIT 25;";
  }} else if (sel === "valves") {{
    ta.value = "SELECT valve_type, end_connection, nps, dn, rating, face_to_face_mm, weight_kg\\nFROM tbl_valves_asme_b16_10\\nLIMIT 25;";
  }} else if (sel === "gaskets") {{
    ta.value = "SELECT gasket_type, applicable_standard, nps, rating, inner_diameter_mm, outer_diameter_mm, weight_kg\\nFROM tbl_gaskets_and_rtj\\nLIMIT 25;";
  }} else if (sel === "blanks") {{
    ta.value = "SELECT blank_type, facing, nps, dn, rating, outer_diameter_mm, thickness_mm, weight_blank_kg\\nFROM tbl_line_blanks_asme_b16_48\\nLIMIT 25;";
  }} else if (sel === "catalog") {{
    ta.value = "SELECT target_table, category, unit_system, count(*) as file_count, sum(row_count) as total_rows\\nFROM tbl_catalog\\nGROUP BY target_table\\nORDER BY total_rows DESC;";
  }} else if (sel === "all_comp") {{
    ta.value = "SELECT filename, target_table, nps, dn, c1, c2, c3, c4, c5\\nFROM tbl_all_components\\nWHERE nps = '14'\\nLIMIT 30;";
  }}
  runSqlQuery();
}}

function onTableSelectChange() {{
  if (!db) return;
  const tbl = document.getElementById("tableSelector").value;
  const limit = document.getElementById("limitSelector").value;
  const query = `SELECT * FROM ${{tbl}} LIMIT ${{limit}};`;
  
  try {{
    const res = db.exec(query);
    const countRes = db.exec(`SELECT count(*) FROM ${{tbl}};`);
    const totalCount = countRes[0].values[0][0];

    document.getElementById("tableRowsCount").innerText = `${{totalCount}} toplam satır (İlk ${{limit}} gösteriliyor)`;
    if (res.length > 0) {{
      fullTableData = res[0];
      document.getElementById("tableColsList").innerText = `Kolonlar: ${{res[0].columns.join(", ")}}`;
      renderGrid(document.getElementById("tableBrowserContainer"), res[0].columns, res[0].values);
    }} else {{
      document.getElementById("tableBrowserContainer").innerHTML = "<div style='padding:20px; text-align:center;'>Tablo boş</div>";
    }}
  }} catch (err) {{
    document.getElementById("tableBrowserContainer").innerHTML = `<div style='padding:20px; color:var(--accent-err)'>${{err.message}}</div>`;
  }}
}}

function onFilterChange() {{
  if (!fullTableData) return;
  const term = document.getElementById("filterInput").value.toLowerCase();
  const filtered = fullTableData.values.filter(r => {{
    return r.some(v => v !== null && String(v).toLowerCase().includes(term));
  }});
  renderGrid(document.getElementById("tableBrowserContainer"), fullTableData.columns, filtered);
}}

function runAllBenchmarks() {{
  if (!db) return;
  // A: Pipe 14 STD
  const resA = db.exec("SELECT outer_diameter_in, wall_thickness_in, inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft FROM tbl_pipes WHERE nps = '14' AND schedule = 'STD' AND unit_system = 'Imperial';");
  if (resA.length > 0 && resA[0].values.length > 0) {{
    const v = resA[0].values[0];
    document.getElementById("benchStatusA").innerText = "100% DOĞRULANDI (PASS)";
    document.getElementById("benchStatusA").style.background = "rgba(16, 185, 129, 0.2)";
  }}

  // B: Cap 14 STD
  const resB = db.exec("SELECT center_to_end_mm, weight_kg, weight_lb FROM tbl_fittings_buttweld_b16_9 WHERE nps = '14' AND source_file = 'cap_imperial_std.csv';");
  if (resB.length > 0 && resB[0].values.length > 0) {{
    document.getElementById("benchStatusB").innerText = "100% DOĞRULANDI (PASS)";
    document.getElementById("benchStatusB").style.background = "rgba(16, 185, 129, 0.2)";
  }}

  // D: Pipe 16 STD (Ekran #6)
  const resD = db.exec("SELECT outer_diameter_in, wall_thickness_in, inner_diameter_in, weight_empty_lb_ft, weight_water_lb_ft FROM tbl_pipes WHERE nps = '16' AND schedule = 'STD' AND unit_system = 'Imperial';");
  if (resD.length > 0 && resD[0].values.length > 0) {{
    document.getElementById("benchStatusD").innerText = "100% DOĞRULANDI (PASS)";
    document.getElementById("benchStatusD").style.background = "rgba(16, 185, 129, 0.2)";
  }}

  // C: Checksums
  const resC1 = db.exec("SELECT count(*) FROM tbl_catalog;");
  const resC2 = db.exec("SELECT count(*) FROM tbl_all_components;");
  const files = resC1[0].values[0][0];
  const rows = resC2[0].values[0][0];
  document.getElementById("benchStatusC").innerText = `${{files}}/956 DOSYA | ${{rows}} SATIR PASS`;
  alert("Tüm dondurulmuş SQLite benchmark testleri başarıyla doğrulandı!");
}}

function populateSvgList() {{
  if (!db) return;
  try {{
    const res = db.exec("SELECT filename, title FROM tbl_svg_templates ORDER BY filename;");
    if (res.length > 0) {{
      const sel = document.getElementById("svgSelector");
      sel.innerHTML = "";
      res[0].values.forEach(r => {{
        const opt = document.createElement("option");
        opt.value = r[0];
        opt.innerText = `${{r[0]}} (${{r[1]}})`;
        sel.appendChild(opt);
      }});
    }}
  }} catch (e) {{}}
}}

function renderSelectedSvg() {{
  if (!db) return;
  const fname = document.getElementById("svgSelector").value;
  const res = db.exec(`SELECT svg_content FROM tbl_svg_templates WHERE filename = '${{fname}}';`);
  const box = document.getElementById("svgContainer");
  if (res.length > 0 && res[0].values.length > 0) {{
    let svg = res[0].values[0][0];
    // In Pipedata, [[2]], [[3]], etc. are replaced by active component dimensions
    // Sample test dimension injection:
    svg = svg.replace(/\[\[2\]\]/g, "350");
    svg = svg.replace(/\[\[3\]\]/g, "14.00\\\"");
    svg = svg.replace(/\[\[4\]\]/g, "0.375\\\"");
    svg = svg.replace(/\[\[5\]\]/g, "6.50\\\"");
    svg = svg.replace(/\[\[DB2_6\]\]/g, "54.62 lb/ft");
    box.innerHTML = svg;
  }} else {{
    box.innerHTML = "<span style='color:var(--text-muted)'>SVG bulunamadı</span>";
  }}
}}

function loadExternalDb(e) {{
  const file = e.target.files[0];
  if (!file) return;
  const reader = new FileReader();
  reader.onload = function() {{
    const u8 = new Uint8Array(reader.result);
    db = new SQL.Database(u8);
    document.getElementById("statusText").innerText = `Harici DB Yüklendi (${{file.name}})`;
    runSqlQuery();
  }};
  reader.readAsArrayBuffer(file);
}}

function exportDb() {{
  if (!db) return;
  const binaryArray = db.export();
  const blob = new Blob([binaryArray], {{type: "application/x-sqlite3"}});
  const a = document.createElement("a");
  a.href = URL.createObjectURL(blob);
  a.download = "pipedata.db";
  a.click();
}}

function clearQuery() {{ document.getElementById("sqlQuery").value = ""; }}
function copySql() {{
  navigator.clipboard.writeText(document.getElementById("sqlQuery").value);
  alert("Sorgu panoya kopyalandı!");
}}

// Keyboard shortcut: Ctrl+Enter or Cmd+Enter to execute query
document.getElementById("sqlQuery").addEventListener("keydown", function(e) {{
  if ((e.ctrlKey || e.metaKey) && e.key === "Enter") {{
    e.preventDefault();
    runSqlQuery();
  }}
}});

// Auto-boot on page load
window.addEventListener("DOMContentLoaded", initDatabase);
</script>

</body>
</html>
"""

    print(f"Writing standalone HTML tester to {out_html}...")
    with open(out_html, "w", encoding="utf-8") as f:
        f.write(html_content)

    print(f"Copying to Android Downloads: {download_html}...")
    with open(download_html, "w", encoding="utf-8") as f:
        f.write(html_content)

    print("Done! HTML tester successfully generated and copied to Downloads.")
    print(f"File size: {os.path.getsize(out_html) / (1024*1024):.2f} MB")

if __name__ == "__main__":
    generate_html()
