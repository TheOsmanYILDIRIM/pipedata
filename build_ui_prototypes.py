#!/usr/bin/env python3
"""
Build Standalone Interactive HTML Prototype Showcase with 10 Distinct Senior-Friendly Designs.
"""

import os, re, shutil

PROJECT_DIR = '/data/data/com.termux/files/home/projects/pipedata'
OUT_HTML = os.path.join(PROJECT_DIR, 'pipedata_ui_prototypes.html')
DOWNLOAD_HTML = '/storage/emulated/0/Download/pipedata_ui_prototypes.html'

# Load real modernized SVGs
def load_svg(fname):
    p = os.path.join(PROJECT_DIR, 'data/svg_modern', fname)
    if os.path.exists(p):
        with open(p) as f:
            content = f.read()
        # strip xml declaration if present
        content = re.sub(r'<\?xml.*?\?>', '', content)
        return content
    return '<svg viewBox="0 0 100 100"><text x="10" y="50" fill="#fff">SVG Missing</text></svg>'

svg_ftbw8 = load_svg('modern_ftbw8.svg').replace('[[4]]', '6.50"').replace('[[DB2_4]]', '0.375"')
svg_pipe1 = load_svg('modern_pipe1.svg').replace('[[3]]', '14"').replace('[[4]]', '0.375"').replace('[[5]]', '5.5"').replace('[[6]]', '13.25"')
svg_flan1 = load_svg('modern_flan1.svg')
svg_ftbw6 = load_svg('modern_ftbw6.svg').replace('[[4]]', '11.0"').replace('[[5]]', '11.0"')
svg_gspr1 = load_svg('modern_gspr1.svg')
svg_vlfl1 = load_svg('modern_vlfl1.svg')

print('Loaded SVGs for embedding.')

HTML_CONTENT = f'''<!DOCTYPE html>
<html lang="tr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Pipedata Pro - 10 Farklı Yaşlı / Kıdemli Mühendis Dostu Arayüz Tasarımı</title>
<style>
  :root {{
    --font-main: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
    --font-mono: "SF Mono", "Roboto Mono", Menlo, Consolas, monospace;
  }}
  * {{ box-sizing: border-box; margin: 0; padding: 0; }}
  body {{
    font-family: var(--font-main);
    background: #090d16;
    color: #e2e8f0;
    line-height: 1.5;
    padding-bottom: 60px;
  }}
  
  /* Top Master Navigation Bar */
  .master-header {{
    background: #0f172a;
    border-bottom: 2px solid #1e293b;
    position: sticky;
    top: 0;
    z-index: 9999;
    box-shadow: 0 4px 20px rgba(0,0,0,0.5);
  }}
  .master-title-bar {{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 24px;
    border-bottom: 1px solid #1e293b;
  }}
  .master-title {{
    font-size: 19px;
    font-weight: 800;
    color: #38bdf8;
    display: flex;
    align-items: center;
    gap: 10px;
  }}
  .master-title span {{
    background: #0284c7;
    color: #fff;
    font-size: 11px;
    padding: 2px 8px;
    border-radius: 999px;
    letter-spacing: 0.5px;
  }}
  .header-actions {{
    display: flex;
    gap: 12px;
    align-items: center;
  }}
  .view-toggle {{
    background: #1e293b;
    border: 1px solid #334155;
    color: #cbd5e1;
    padding: 6px 14px;
    border-radius: 8px;
    font-size: 13px;
    cursor: pointer;
    font-weight: 600;
    transition: all 0.2s;
  }}
  .view-toggle:hover {{ background: #334155; color: #fff; }}
  .view-toggle.active {{ background: #0284c7; color: #fff; border-color: #38bdf8; }}
  
  /* Tab Bar for 10 Designs */
  .tab-bar {{
    display: flex;
    overflow-x: auto;
    padding: 8px 16px;
    gap: 8px;
    scrollbar-width: thin;
    scrollbar-color: #334155 #0f172a;
  }}
  .tab-btn {{
    background: #1e293b;
    border: 1px solid #334155;
    color: #94a3b8;
    padding: 10px 18px;
    border-radius: 8px;
    font-size: 13px;
    font-weight: 700;
    cursor: pointer;
    white-space: nowrap;
    display: flex;
    align-items: center;
    gap: 8px;
    transition: all 0.2s;
  }}
  .tab-btn:hover {{ background: #2d3748; color: #f8fafc; }}
  .tab-btn.active {{
    background: linear-gradient(135deg, #0284c7, #0369a1);
    color: #ffffff;
    border-color: #38bdf8;
    box-shadow: 0 0 12px rgba(56, 189, 248, 0.4);
  }}
  
  /* Main Container */
  .main-stage {{
    max-width: 1300px;
    margin: 24px auto;
    padding: 0 16px;
    display: flex;
    flex-direction: column;
    gap: 24px;
  }}
  
  /* Prototype Display Section */
  .design-display {{
    display: none;
    animation: fadeIn 0.3s ease-in-out;
  }}
  .design-display.active {{ display: block; }}
  
  @keyframes fadeIn {{
    from {{ opacity: 0; transform: translateY(6px); }}
    to {{ opacity: 1; transform: translateY(0); }}
  }}
  
  /* Design Analysis Banner */
  .analysis-card {{
    background: #131d31;
    border: 1px solid #1e293b;
    border-left: 5px solid #38bdf8;
    border-radius: 12px;
    padding: 20px 24px;
    margin-bottom: 24px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.3);
  }}
  .analysis-header {{
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 12px;
  }}
  .analysis-title {{
    font-size: 22px;
    font-weight: 800;
    color: #f8fafc;
  }}
  .analysis-badge {{
    background: rgba(56, 189, 248, 0.15);
    color: #38bdf8;
    border: 1px solid #0284c7;
    padding: 4px 12px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 700;
  }}
  .analysis-desc {{
    font-size: 15px;
    color: #94a3b8;
    margin-bottom: 16px;
  }}
  .analysis-grid {{
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: 16px;
  }}
  .analysis-item {{
    background: #0b1322;
    padding: 12px 16px;
    border-radius: 8px;
    border: 1px solid #1e293b;
  }}
  .analysis-item-label {{
    font-size: 11px;
    text-transform: uppercase;
    color: #64748b;
    font-weight: 800;
    letter-spacing: 0.5px;
    margin-bottom: 4px;
  }}
  .analysis-item-val {{
    font-size: 14px;
    color: #e2e8f0;
    font-weight: 600;
  }}
  
  /* Phone Frame Simulation */
  .device-wrapper {{
    display: flex;
    justify-content: center;
    padding: 20px 0;
  }}
  .phone-frame {{
    width: 412px;
    height: 892px;
    background: #000;
    border-radius: 46px;
    box-shadow: 0 25px 60px -15px rgba(0,0,0,0.8), 0 0 0 12px #1f242d, 0 0 0 14px #333a46;
    overflow: hidden;
    position: relative;
    display: flex;
    flex-direction: column;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  }}
  
  /* Full Width Mode */
  .phone-frame.full-width {{
    width: 100%;
    max-width: 900px;
    height: auto;
    min-height: 850px;
    border-radius: 20px;
    box-shadow: 0 10px 40px rgba(0,0,0,0.6);
  }}
  
  /* Android Status Bar */
  .status-bar {{
    height: 38px;
    padding: 0 24px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 13px;
    font-weight: 600;
    color: #fff;
    background: transparent;
    z-index: 10;
    flex-shrink: 0;
  }}
  .status-icons {{ display: flex; gap: 8px; align-items: center; }}
  
  /* Phone Screen Body */
  .screen-content {{
    flex: 1;
    overflow-y: auto;
    display: flex;
    flex-direction: column;
    position: relative;
  }}
  
  /* Common UI Elements across prototypes */
  .cad-preview-box {{
    width: 100%;
    height: 250px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
  }}
  .cad-preview-box svg {{
    width: 100%;
    height: 100%;
    max-height: 240px;
  }}
  
  /* Interactive pulse on active dimension */
  .glow-active {{
    box-shadow: 0 0 15px rgba(251, 191, 36, 0.8) !important;
    border-color: #fbbf24 !important;
  }}
</style>
</head>
<body>

<header class="master-header">
  <div class="master-title-bar">
    <div class="master-title">
      📐 PIPEDATA PRO <span>JETPACK COMPOSE PROTOTİPLERİ</span>
    </div>
    <div class="header-actions">
      <button class="view-toggle active" id="btn-phone" onclick="setViewMode('phone')">📱 Mobil Çerçeve</button>
      <button class="view-toggle" id="btn-full" onclick="setViewMode('full')">🖥️ Geniş Ekran</button>
    </div>
  </div>
  
  <div class="tab-bar">
    <button class="tab-btn active" onclick="switchDesign(1)">1. AutoCAD Koyu CAD</button>
    <button class="tab-btn" onclick="switchDesign(2)">2. Yüksek Kontrast (Sarı/Siyah)</button>
    <button class="tab-btn" onclick="switchDesign(3)">3. Norm Tablosu (Ozalit)</button>
    <button class="tab-btn" onclick="switchDesign(4)">4. Tek El / Başparmak</button>
    <button class="tab-btn" onclick="switchDesign(5)">5. Modüler Kart Yığını</button>
    <button class="tab-btn" onclick="switchDesign(6)">6. Kademeli Adım Seçici</button>
    <button class="tab-btn" onclick="switchDesign(7)">7. Dokun-Parla Canlı Çizim</button>
    <button class="tab-btn" onclick="switchDesign(8)">8. Hızlı Sık Kullanılanlar</button>
    <button class="tab-btn" onclick="switchDesign(9)">9. Açık Ofis Kağıt Modu</button>
    <button class="tab-btn" onclick="switchDesign(10)">10. Montaj & Cıvata Paketi</button>
  </div>
</header>

<main class="main-stage">

  <!-- ========================================== -->
  <!-- DESIGN 1: AutoCAD Dark Blueprint -->
  <!-- ========================================== -->
  <section class="design-display active" id="design-1">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 1: "AutoCAD Dark Blueprint (Koyu CAD Modu)"</div>
        <div class="analysis-badge">ÖNERİLEN STANDART</div>
      </div>
      <div class="analysis-desc">
        Kıdemli boru mühendislerinin 30 yıldır AutoCAD ekranlarından aşina olduğu koyu arduvaz mavisi ve parlak cyan/sarı çizgiler. Küçük açılır listeler yerine yatay kaydırılabilir dev hap butonlar (Pills).
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">28sp dev rakamlar, sıfır dropdown, doğrudan görünür parametreler</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Minimum Dokunma</div>
          <div class="analysis-item-val">Sadece 1 tık ile 14" STD ölçülerini ekrana döker</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Kontrast Oranı</div>
          <div class="analysis-item-val">WCAG AAA (14.2:1) - Yüksek parıltı ve okunabilirlik</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">Scaffold, LazyRow, AssistChip, ElevatedCard</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #0d1520; color: #f8fafc;">
          <!-- Top App Bar -->
          <div style="padding: 12px 18px; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #1e293b;">
            <div>
              <div style="font-size: 11px; color: #38bdf8; font-weight: 700; text-transform: uppercase;">ASME B16.9 FITTINGS</div>
              <div style="font-size: 20px; font-weight: 800; color: #fff;">Alın Kaynaklı Kep (Cap)</div>
            </div>
            <div style="display: flex; gap: 8px;">
              <div style="background: #1e293b; border: 1px solid #38bdf8; border-radius: 20px; padding: 4px 10px; font-size: 12px; font-weight: 800; color: #38bdf8; cursor: pointer;">İnç / lb</div>
              <div style="background: #1e293b; width: 34px; height: 34px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 15px;">🔍</div>
            </div>
          </div>
          
          <!-- Category Selector -->
          <div style="padding: 10px 14px; display: flex; gap: 8px; overflow-x: auto;">
            <div style="background: #1e293b; color: #94a3b8; padding: 8px 14px; border-radius: 10px; font-size: 13px; font-weight: 700; white-space: nowrap;">Boru</div>
            <div style="background: #1e293b; color: #94a3b8; padding: 8px 14px; border-radius: 10px; font-size: 13px; font-weight: 700; white-space: nowrap;">Flanş</div>
            <div style="background: #0284c7; color: #fff; padding: 8px 16px; border-radius: 10px; font-size: 13px; font-weight: 800; white-space: nowrap; border: 1px solid #38bdf8;">Fitting (Kep)</div>
            <div style="background: #1e293b; color: #94a3b8; padding: 8px 14px; border-radius: 10px; font-size: 13px; font-weight: 700; white-space: nowrap;">Vana</div>
            <div style="background: #1e293b; color: #94a3b8; padding: 8px 14px; border-radius: 10px; font-size: 13px; font-weight: 700; white-space: nowrap;">Conta</div>
          </div>
          
          <!-- Large NPS Pill Selector -->
          <div style="padding: 8px 14px;">
            <div style="font-size: 11px; color: #94a3b8; font-weight: 700; margin-bottom: 6px;">BORU ANMA ÇAPI (NPS):</div>
            <div style="display: flex; gap: 8px; overflow-x: auto; padding-bottom: 4px;">
              <div style="background: #1e293b; color: #e2e8f0; padding: 10px 16px; border-radius: 12px; font-size: 16px; font-weight: 800;">8"</div>
              <div style="background: #1e293b; color: #e2e8f0; padding: 10px 16px; border-radius: 12px; font-size: 16px; font-weight: 800;">10"</div>
              <div style="background: #1e293b; color: #e2e8f0; padding: 10px 16px; border-radius: 12px; font-size: 16px; font-weight: 800;">12"</div>
              <div style="background: #38bdf8; color: #0f172a; padding: 10px 20px; border-radius: 12px; font-size: 18px; font-weight: 900; box-shadow: 0 0 15px rgba(56,189,248,0.5);">14"</div>
              <div style="background: #1e293b; color: #e2e8f0; padding: 10px 16px; border-radius: 12px; font-size: 16px; font-weight: 800;">16"</div>
              <div style="background: #1e293b; color: #e2e8f0; padding: 10px 16px; border-radius: 12px; font-size: 16px; font-weight: 800;">18"</div>
            </div>
          </div>
          
          <!-- Schedule Pill Selector -->
          <div style="padding: 4px 14px 10px; display: flex; gap: 8px;">
            <div style="background: #38bdf8; color: #0f172a; padding: 6px 14px; border-radius: 8px; font-size: 13px; font-weight: 800;">Sch-STD</div>
            <div style="background: #1e293b; color: #94a3b8; padding: 6px 14px; border-radius: 8px; font-size: 13px; font-weight: 700;">Sch-40</div>
            <div style="background: #1e293b; color: #94a3b8; padding: 6px 14px; border-radius: 8px; font-size: 13px; font-weight: 700;">Sch-XS</div>
            <div style="background: #1e293b; color: #94a3b8; padding: 6px 14px; border-radius: 8px; font-size: 13px; font-weight: 700;">Sch-80</div>
          </div>
          
          <!-- Live CAD Drawing Container -->
          <div style="margin: 0 14px; background: #080d14; border: 2px solid #1e293b; border-radius: 14px; padding: 10px; position: relative;">
            <div style="position: absolute; top: 8px; left: 12px; font-size: 10px; color: #38bdf8; font-weight: 800;">AUTOCAD DARK BLUEPRINT</div>
            <div style="position: absolute; top: 8px; right: 12px; font-size: 10px; color: #22c55e; font-weight: 800;">ASME B16.25 KAYNAK AĞZI</div>
            <div class="cad-preview-box">
              {svg_ftbw8}
            </div>
          </div>
          
          <!-- Key Dimension Metric Cards (Big Numbers) -->
          <div style="padding: 14px; display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
            <div style="background: #131d2e; border: 1px solid #1e293b; border-left: 4px solid #38bdf8; border-radius: 12px; padding: 12px;">
              <div style="font-size: 11px; color: #94a3b8; font-weight: 700;">DIŞ ÇAP (OD)</div>
              <div style="font-size: 26px; font-weight: 900; color: #fff;">14.00"</div>
              <div style="font-size: 12px; color: #38bdf8;">355.6 mm</div>
            </div>
            <div style="background: #131d2e; border: 1px solid #1e293b; border-left: 4px solid #fbbf24; border-radius: 12px; padding: 12px;">
              <div style="font-size: 11px; color: #94a3b8; font-weight: 700;">KEP BOYU (E)</div>
              <div style="font-size: 26px; font-weight: 900; color: #fff;">6.50"</div>
              <div style="font-size: 12px; color: #fbbf24;">165.1 mm</div>
            </div>
            <div style="background: #131d2e; border: 1px solid #1e293b; border-left: 4px solid #22c55e; border-radius: 12px; padding: 12px;">
              <div style="font-size: 11px; color: #94a3b8; font-weight: 700;">ET KALINLIĞI (t)</div>
              <div style="font-size: 26px; font-weight: 900; color: #fff;">0.375"</div>
              <div style="font-size: 12px; color: #22c55e;">9.53 mm (3/8")</div>
            </div>
            <div style="background: #131d2e; border: 1px solid #1e293b; border-left: 4px solid #a855f7; border-radius: 12px; padding: 12px;">
              <div style="font-size: 11px; color: #94a3b8; font-weight: 700;">AĞIRLIK (WT)</div>
              <div style="font-size: 26px; font-weight: 900; color: #fff;">37.5 lb</div>
              <div style="font-size: 12px; color: #a855f7;">17.0 kg</div>
            </div>
          </div>
          
          <!-- Bottom Navigation Bar (Large Touch Targets) -->
          <div style="margin-top: auto; background: #090e17; border-top: 1px solid #1e293b; display: flex; justify-content: space-around; padding: 12px 0;">
            <div style="display: flex; flex-direction: column; align-items: center; color: #38bdf8;">
              <div style="font-size: 20px;">📐</div>
              <div style="font-size: 12px; font-weight: 800;">KATALOG</div>
            </div>
            <div style="display: flex; flex-direction: column; align-items: center; color: #64748b;">
              <div style="font-size: 20px;">🔩</div>
              <div style="font-size: 12px; font-weight: 700;">MONTAJ</div>
            </div>
            <div style="display: flex; flex-direction: column; align-items: center; color: #64748b;">
              <div style="font-size: 20px;">🧮</div>
              <div style="font-size: 12px; font-weight: 700;">HESAP</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 2: High-Visibility Senior Contrast -->
  <!-- ========================================== -->
  <section class="design-display" id="design-2">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 2: "High-Visibility Senior Contrast (Sarı/Siyah Saha Modu)"</div>
        <div class="analysis-badge">GÜNEŞ IŞIĞI VE SAHA ŞAMPİYONU</div>
      </div>
      <div class="analysis-desc">
        Güneşli şantiyede, katarakt veya görme güçlüğü çekenler için tasarlandı. Zemin OLED saf siyah, yazılar ve butonlar ikaz sarısı (#FFE600). 3px kalın kontrast çerçeveler ve dev dokunmatik butonlar.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">32sp devasa rakamlar, sıfır açık gri, maksimum göz dinlendirici kontrast</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Eldiven Dostu</div>
          <div class="analysis-item-val">56dp minimum buton yüksekliği, eldivenle dahi ıskalamak imkansız</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Kontrast Oranı</div>
          <div class="analysis-item-val">Maksimum 19.5:1 (Ultra High Contrast)</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">MaterialTheme(colorScheme = highContrastDarkColorScheme)</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar" style="background: #000; color: #ffe600;"><span>10:30</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #000000; color: #ffffff;">
          <!-- Top High-Vis Bar -->
          <div style="background: #ffe600; color: #000; padding: 14px 18px; display: flex; justify-content: space-between; align-items: center;">
            <div style="font-size: 22px; font-weight: 900; letter-spacing: -0.5px;">BORU ENKESİTİ (PIPE)</div>
            <div style="background: #000; color: #ffe600; font-size: 13px; font-weight: 900; padding: 4px 10px; border-radius: 4px;">İNÇ / LB</div>
          </div>
          
          <!-- Tactile Giant Size Stepper -->
          <div style="padding: 16px; border-bottom: 2px solid #333;">
            <div style="font-size: 12px; font-weight: 900; color: #ffe600; margin-bottom: 8px; text-transform: uppercase;">SEÇİLİ BORU ÇAPI:</div>
            <div style="display: flex; align-items: center; justify-content: space-between; background: #111; border: 3px solid #ffe600; border-radius: 14px; padding: 6px 12px;">
              <button style="width: 50px; height: 50px; background: #ffe600; color: #000; border: none; border-radius: 10px; font-size: 28px; font-weight: 900; cursor: pointer;">-</button>
              <div style="text-align: center;">
                <div style="font-size: 34px; font-weight: 900; color: #fff; line-height: 1;">16" NPS</div>
                <div style="font-size: 13px; font-weight: 800; color: #ffe600;">DN 400 | ASME B36.10M</div>
              </div>
              <button style="width: 50px; height: 50px; background: #ffe600; color: #000; border: none; border-radius: 10px; font-size: 28px; font-weight: 900; cursor: pointer;">+</button>
            </div>
          </div>
          
          <!-- Schedule Buttons (Large) -->
          <div style="padding: 12px 16px; display: flex; gap: 10px;">
            <div style="flex: 1; background: #ffe600; color: #000; text-align: center; padding: 12px; border-radius: 10px; font-size: 18px; font-weight: 900; border: 2px solid #ffe600;">Sch-STD</div>
            <div style="flex: 1; background: #111; color: #fff; text-align: center; padding: 12px; border-radius: 10px; font-size: 18px; font-weight: 900; border: 2px solid #444;">Sch-40</div>
            <div style="flex: 1; background: #111; color: #fff; text-align: center; padding: 12px; border-radius: 10px; font-size: 18px; font-weight: 900; border: 2px solid #444;">Sch-80</div>
          </div>
          
          <!-- High-Contrast Vector Box -->
          <div style="margin: 0 16px; background: #000; border: 3px solid #ffe600; border-radius: 14px; padding: 8px;">
            <div class="cad-preview-box">
              {svg_pipe1}
            </div>
          </div>
          
          <!-- Massive Single Metric Readouts -->
          <div style="padding: 16px; display: flex; flex-direction: column; gap: 10px;">
            <div style="background: #111; border: 2px solid #fff; border-radius: 10px; padding: 12px 18px; display: flex; justify-content: space-between; align-items: center;">
              <span style="font-size: 16px; font-weight: 900; color: #ffe600;">DIŞ ÇAP (OD):</span>
              <span style="font-size: 30px; font-weight: 900; color: #fff;">16.00" <small style="font-size: 14px; color: #aaa;">(406.4 mm)</small></span>
            </div>
            <div style="background: #111; border: 2px solid #fff; border-radius: 10px; padding: 12px 18px; display: flex; justify-content: space-between; align-items: center;">
              <span style="font-size: 16px; font-weight: 900; color: #ffe600;">ET KALINLIĞI (t):</span>
              <span style="font-size: 30px; font-weight: 900; color: #fff;">0.375" <small style="font-size: 14px; color: #aaa;">(9.53 mm)</small></span>
            </div>
          </div>
          
          <!-- High-Vis Bottom Bar -->
          <div style="margin-top: auto; background: #ffe600; padding: 12px 0; display: flex; justify-content: space-around;">
            <div style="color: #000; font-weight: 900; font-size: 15px; text-align: center;">[ 📐 KATALOG ]</div>
            <div style="color: #555; font-weight: 900; font-size: 15px; text-align: center;">[ 🔩 MONTAJ ]</div>
            <div style="color: #555; font-weight: 900; font-size: 15px; text-align: center;">[ 🧮 HESAP ]</div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 3: Technical Standards Ledger -->
  <!-- ========================================== -->
  <section class="design-display" id="design-3">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 3: "Technical Standards Ledger (Mühendislik Ozaliti & Norm Tablosu)"</div>
        <div class="analysis-badge">KLASİK EL KİTABI SEVENLER İÇİN</div>
      </div>
      <div class="analysis-desc">
        Kıdemli mühendislerin yıllarca masasında duran ASME B16.5 ve B36.10 standart el kitapları formatında. Üst kısımda harfli şema (A, C, M, t), alt kısımda ise standart norm tablosu.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">Alışıldık standart tablo yapısı, arama yapmadan tüm ölçüleri yan yana görme</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Minimum Efor</div>
          <div class="analysis-item-val">Tablo satırına dokunulduğunda ilgili değer otomatik büyür ve mavi parlar</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Estetik</div>
          <div class="analysis-item-val">Klasik Mühendislik Ozalit Mavisi (#10375C)</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">LazyColumn + HorizontalScroll Grid + Canvas Grid Lines</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar" style="background: #0b2545;"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #0c2b4d; color: #f8fafc;">
          <!-- Top Bar -->
          <div style="background: #071e3d; padding: 12px 18px; display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #1f4287;">
            <div>
              <div style="font-size: 11px; color: #00d2d3; font-weight: 800;">ASME B16.9 STANDART NORMU</div>
              <div style="font-size: 19px; font-weight: 800;">Eşit Te (Equal Tee)</div>
            </div>
            <div style="background: #1f4287; padding: 4px 10px; border-radius: 6px; font-size: 12px; font-weight: 700;">Tablo: 14</div>
          </div>
          
          <!-- Drawing -->
          <div style="padding: 10px; background: #071e3d; border-bottom: 2px solid #1f4287;">
            <div class="cad-preview-box" style="height: 200px;">
              {svg_ftbw6}
            </div>
          </div>
          
          <!-- Engineering Data Table -->
          <div style="padding: 14px;">
            <div style="font-size: 12px; font-weight: 800; color: #00d2d3; margin-bottom: 8px;">ÖLÇÜ TABLOSU (NPS 10" - 16"):</div>
            <div style="background: #071e3d; border: 2px solid #1f4287; border-radius: 10px; overflow: hidden;">
              <table style="width: 100%; border-collapse: collapse; font-size: 13px; text-align: center;">
                <tr style="background: #1f4287; color: #fff; font-weight: 800;">
                  <th style="padding: 8px;">NPS</th>
                  <th style="padding: 8px;">OD</th>
                  <th style="padding: 8px;">Merkez C</th>
                  <th style="padding: 8px;">Branşman M</th>
                  <th style="padding: 8px;">Ağırlık</th>
                </tr>
                <tr style="border-bottom: 1px solid #1f4287; color: #cbd5e1;">
                  <td style="padding: 10px; font-weight: 800;">10"</td>
                  <td>10.75"</td>
                  <td>8.50"</td>
                  <td>8.50"</td>
                  <td>58 lb</td>
                </tr>
                <tr style="border-bottom: 1px solid #1f4287; color: #cbd5e1;">
                  <td style="padding: 10px; font-weight: 800;">12"</td>
                  <td>12.75"</td>
                  <td>10.00"</td>
                  <td>10.00"</td>
                  <td>92 lb</td>
                </tr>
                <tr style="background: rgba(0, 210, 211, 0.2); border-left: 4px solid #00d2d3; color: #fff; font-weight: 900;">
                  <td style="padding: 12px; font-size: 16px; color: #00d2d3;">14" ★</td>
                  <td style="font-size: 15px;">14.00"</td>
                  <td style="font-size: 15px;">11.00"</td>
                  <td style="font-size: 15px;">11.00"</td>
                  <td style="font-size: 15px;">124 lb</td>
                </tr>
                <tr style="color: #cbd5e1;">
                  <td style="padding: 10px; font-weight: 800;">16"</td>
                  <td>16.00"</td>
                  <td>12.00"</td>
                  <td>12.00"</td>
                  <td>168 lb</td>
                </tr>
              </table>
            </div>
          </div>
          
          <div style="margin-top: auto; background: #071e3d; padding: 12px; text-align: center; font-size: 13px; font-weight: 800; border-top: 1px solid #1f4287; color: #00d2d3;">
            TÜM NORM DEĞERLERİ ASME B16.9-2018 İLE DOĞRULANMIŞTIR
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 4: One-Handed Thumb-First -->
  <!-- ========================================== -->
  <section class="design-display" id="design-4">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 4: "One-Handed Thumb-First (Saha Tek Elle Başparmak Modu)"</div>
        <div class="analysis-badge">ERGONOMİK BAŞPARMAK ALANI</div>
      </div>
      <div class="analysis-desc">
        Sahada bir elle boruya tutunup diğer elle telefonu kullanan mühendisler için. Tüm butonlar ve seçimler ekranın alt %40'lık başparmak erişim yayına toplanmıştır. Yukarı uzanmaya gerek kalmaz.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">Sıfır parmak gerilmesi, tek el rahatlığı, alt yay kontrolü</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Büyük Görsel</div>
          <div class="analysis-item-val">Üst %60 tamamen dev teknik resim ve ölçülere ayrılmıştır</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Renk Paleti</div>
          <div class="analysis-item-val">Modern Koyu Grafit + Canlı Zümrüt Yeşili (#10b981)</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">BottomSheetScaffold + ModalBottomSheet / Docked Bar</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #0f172a; color: #f8fafc;">
          <!-- Top Area: Giant Drawing & Readouts -->
          <div style="flex: 1; padding: 14px; display: flex; flex-direction: column; justify-content: center; align-items: center;">
            <div style="font-size: 13px; color: #10b981; font-weight: 800; margin-bottom: 4px;">KAYNAK BOYUNLU FLANŞ (WN RF)</div>
            <div style="font-size: 28px; font-weight: 900; color: #fff; margin-bottom: 8px;">8" NPS - Class 300#</div>
            
            <div class="cad-preview-box" style="height: 280px;">
              {svg_flan1}
            </div>
            
            <!-- Floating Giant Metrics -->
            <div style="width: 100%; display: flex; gap: 10px; margin-top: 10px;">
              <div style="flex: 1; background: #1e293b; border-radius: 12px; padding: 10px; text-align: center; border: 1px solid #334155;">
                <div style="font-size: 11px; color: #94a3b8; font-weight: 700;">FLANŞ DIŞ ÇAP</div>
                <div style="font-size: 22px; font-weight: 900; color: #fff;">15.00"</div>
              </div>
              <div style="flex: 1; background: #1e293b; border-radius: 12px; padding: 10px; text-align: center; border: 1px solid #334155;">
                <div style="flex: 1; font-size: 11px; color: #94a3b8; font-weight: 700;">CIVATA SAYISI</div>
                <div style="font-size: 22px; font-weight: 900; color: #10b981;">12 Adet</div>
              </div>
            </div>
          </div>
          
          <!-- Bottom 40% Thumb Dock -->
          <div style="background: #1e293b; border-top: 3px solid #10b981; border-radius: 28px 28px 0 0; padding: 18px 16px; box-shadow: 0 -10px 30px rgba(0,0,0,0.5);">
            <div style="width: 40px; height: 5px; background: #475569; border-radius: 999px; margin: 0 auto 12px;"></div>
            
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px;">
              <span style="font-size: 13px; font-weight: 800; color: #94a3b8;">BAŞPARMAK HIZLI SEÇİCİ</span>
              <span style="background: #10b981; color: #0f172a; font-size: 11px; font-weight: 900; padding: 2px 8px; border-radius: 6px;">İNÇ / LB</span>
            </div>
            
            <!-- Thumb Size Bar (Large 48dp buttons) -->
            <div style="display: flex; gap: 10px; overflow-x: auto; padding-bottom: 10px;">
              <button style="background: #334155; color: #fff; border: none; padding: 14px 20px; border-radius: 14px; font-size: 18px; font-weight: 800;">4"</button>
              <button style="background: #334155; color: #fff; border: none; padding: 14px 20px; border-radius: 14px; font-size: 18px; font-weight: 800;">6"</button>
              <button style="background: #10b981; color: #0f172a; border: none; padding: 14px 26px; border-radius: 14px; font-size: 20px; font-weight: 900; box-shadow: 0 0 15px rgba(16,185,129,0.5);">8"</button>
              <button style="background: #334155; color: #fff; border: none; padding: 14px 20px; border-radius: 14px; font-size: 18px; font-weight: 800;">10"</button>
              <button style="background: #334155; color: #fff; border: none; padding: 14px 20px; border-radius: 14px; font-size: 18px; font-weight: 800;">12"</button>
            </div>
            
            <!-- Pressure Rating Buttons -->
            <div style="display: flex; gap: 8px; margin-top: 6px;">
              <button style="flex: 1; background: #334155; color: #cbd5e1; border: none; padding: 12px; border-radius: 10px; font-size: 15px; font-weight: 800;">150#</button>
              <button style="flex: 1; background: #10b981; color: #0f172a; border: none; padding: 12px; border-radius: 10px; font-size: 15px; font-weight: 900;">300#</button>
              <button style="flex: 1; background: #334155; color: #cbd5e1; border: none; padding: 12px; border-radius: 10px; font-size: 15px; font-weight: 800;">600#</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 5: Card-Deck Modular Stack -->
  <!-- ========================================== -->
  <section class="design-display" id="design-5">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 5: "Card-Deck Modular Stack (3 Büyük Ayrık Kart)"</div>
        <div class="analysis-badge">GÖZ YORMAYAN MODÜLERLİK</div>
      </div>
      <div class="analysis-desc">
        Ekranı birbirine karışan yüzlerce bilgi yerine 3 belirgin, dokunsal karta böler: [1. Seçim Kartı], [2. Teknik Çizim Kartı], [3. Kritik Veri Kartı]. Her kartın sınırları çok nettir.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">Kognitif yükü 3 parçaya böler, nereye bakacağını şaşırtmaz</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Kart Ayrımı</div>
          <div class="analysis-item-val">8dp Elevation, yüksek kontrastlı kenar çizgisi</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Renk Paleti</div>
          <div class="analysis-item-val">Slate Charcoal + Canlı Kobalt Mavisi (#3b82f6)</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">Column + 3x Card(elevation = CardDefaults.cardElevation(8.dp))</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #111827; color: #f9fafb; padding: 14px; gap: 14px;">
          <!-- Header -->
          <div style="display: flex; justify-content: space-between; align-items: center;">
            <div style="font-size: 20px; font-weight: 800;">Vanalar (Valves)</div>
            <div style="background: #1f2937; border: 1px solid #3b82f6; color: #3b82f6; padding: 4px 10px; border-radius: 8px; font-size: 12px; font-weight: 700;">mm / kg</div>
          </div>
          
          <!-- Card 1: Selection -->
          <div style="background: #1f2937; border: 1px solid #374151; border-radius: 16px; padding: 14px; box-shadow: 0 4px 15px rgba(0,0,0,0.3);">
            <div style="font-size: 11px; color: #3b82f6; font-weight: 800; text-transform: uppercase; margin-bottom: 8px;">KART 1: VANA TÜRÜ VE ÇAPI</div>
            <div style="font-size: 18px; font-weight: 800; margin-bottom: 10px;">Flanşlı Sürgülü Vana (Gate Valve)</div>
            <div style="display: flex; gap: 8px;">
              <div style="background: #374151; padding: 8px 14px; border-radius: 8px; font-weight: 700; font-size: 15px;">6"</div>
              <div style="background: #3b82f6; color: #fff; padding: 8px 18px; border-radius: 8px; font-weight: 900; font-size: 16px;">8" (DN 200)</div>
              <div style="background: #374151; padding: 8px 14px; border-radius: 8px; font-weight: 700; font-size: 15px;">10"</div>
            </div>
          </div>
          
          <!-- Card 2: Drawing -->
          <div style="background: #1f2937; border: 1px solid #374151; border-radius: 16px; padding: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.3);">
            <div style="font-size: 11px; color: #3b82f6; font-weight: 800; text-transform: uppercase; margin-bottom: 4px;">KART 2: ZARF VE BOYUT ÇİZİMİ</div>
            <div class="cad-preview-box" style="height: 220px;">
              {svg_vlfl1}
            </div>
          </div>
          
          <!-- Card 3: Metrics -->
          <div style="background: #1f2937; border: 1px solid #374151; border-radius: 16px; padding: 14px; box-shadow: 0 4px 15px rgba(0,0,0,0.3);">
            <div style="font-size: 11px; color: #3b82f6; font-weight: 800; text-transform: uppercase; margin-bottom: 8px;">KART 3: MONTAJ BOYU VE AĞIRLIK</div>
            <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #374151; padding-bottom: 8px; margin-bottom: 8px;">
              <span style="font-size: 14px; color: #9ca3af;">Yüzeyden Yüze Boy (A):</span>
              <span style="font-size: 22px; font-weight: 900; color: #fff;">292 mm (11.5")</span>
            </div>
            <div style="display: flex; justify-content: space-between; align-items: center;">
              <span style="font-size: 14px; color: #9ca3af;">Tahmini Vana Ağırlığı:</span>
              <span style="font-size: 22px; font-weight: 900; color: #10b981;">105 kg (231 lb)</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 6: Tactile Stepper Jog-Dial -->
  <!-- ========================================== -->
  <section class="design-display" id="design-6">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 6: "Tactile Stepper Jog-Dial (Kademeli Adım Seçici)"</div>
        <div class="analysis-badge">TİTREK VE ISLAK EL DOSTU</div>
      </div>
      <div class="analysis-desc">
        Kaydırma (swipe) hareketlerinde elin kayması veya istenen boyutu kaçırma derdine son. Büyük [+] ve [-] donanım hissi veren butonlarla boru çapı adım adım büyütülür veya küçültülür.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">Sıfır kaydırma karmaşası, net klik hissi, asla yanlış boyuta atlamaz</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Buton Boyutu</div>
          <div class="analysis-item-val">64x64 dp dev endüstriyel basmalı butonlar</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">FilledTonalIconButton + AnimatedContent(transitionSpec)</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #181b20; color: #fff; padding: 16px;">
          <div style="text-align: center; margin-bottom: 12px;">
            <div style="font-size: 12px; color: #10b981; font-weight: 800;">ASME B16.48 SPECTACLE BLIND</div>
            <div style="font-size: 22px; font-weight: 900;">Gözlük Kör Sacı (Figure-8)</div>
          </div>
          
          <!-- Tactile Giant Stepper Control -->
          <div style="background: #23272e; border: 2px solid #323842; border-radius: 20px; padding: 12px 16px; display: flex; align-items: center; justify-content: space-between; box-shadow: inset 0 2px 6px rgba(0,0,0,0.5);">
            <button style="width: 60px; height: 60px; background: #323842; color: #fff; border: 2px solid #454d5a; border-radius: 14px; font-size: 32px; font-weight: 900; cursor: pointer;">◀</button>
            <div style="text-align: center;">
              <div style="font-size: 11px; color: #10b981; font-weight: 800;">SEÇİLİ BOYUT</div>
              <div style="font-size: 36px; font-weight: 900; color: #fff; line-height: 1;">10" NPS</div>
              <div style="font-size: 13px; color: #9ca3af; font-weight: 700;">Class 300# RF</div>
            </div>
            <button style="width: 60px; height: 60px; background: #10b981; color: #000; border: 2px solid #34d399; border-radius: 14px; font-size: 32px; font-weight: 900; cursor: pointer;">▶</button>
          </div>
          
          <!-- Drawing -->
          <div style="margin: 16px 0; background: #111317; border: 2px solid #23272e; border-radius: 16px; padding: 10px;">
            <div class="cad-preview-box" style="height: 240px;">
              {svg_gspr1}
            </div>
          </div>
          
          <!-- Big Readouts -->
          <div style="background: #23272e; border-radius: 14px; padding: 16px; display: flex; flex-direction: column; gap: 12px;">
            <div style="display: flex; justify-content: space-between; font-size: 16px;">
              <span style="color: #9ca3af;">Dış Çap (A):</span>
              <span style="font-weight: 900; font-size: 22px;">17.50" (444.5 mm)</span>
            </div>
            <div style="display: flex; justify-content: space-between; font-size: 16px;">
              <span style="color: #9ca3af;">Kör Sac Kalınlığı (t):</span>
              <span style="font-weight: 900; font-size: 22px; color: #10b981;">1.50" (38.1 mm)</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 7: Interactive Blueprint Callout -->
  <!-- ========================================== -->
  <section class="design-display" id="design-7">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 7: "Interactive Blueprint Callout (Dokun-Parla Canlı Çizim)"</div>
        <div class="analysis-badge">KUSURSUZ ANLAŞILIRLIK</div>
      </div>
      <div class="analysis-desc">
        Mühendis alt tablodaki "Et Kalınlığı: 0.375\"" satırına dokunduğu anda, yukarıdaki teknik resimde ilgili ölçü çizgisi ve okları altın sarısı parlama (glow) ile aydınlanır. Hangi ölçünün nereye ait olduğu anında kavranır.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">Harf ve şekil eşleştirme kafa karışıklığını %100 yok eder</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">İnteraktif Geri Bildirim</div>
          <div class="analysis-item-val">Dokunulan hücre neon sarı parlar ve çizimle senkronize olur</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">SharedTransitionLayout + remember {{ mutableStateOf(selectedDim) }}</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #090e17; color: #fff; padding: 14px;">
          <div style="padding-bottom: 8px; border-bottom: 1px solid #1e293b; margin-bottom: 10px;">
            <div style="font-size: 11px; color: #38bdf8; font-weight: 800;">DOKUN-PARLA TEKNİK RESİM</div>
            <div style="font-size: 20px; font-weight: 800;">14" Alın Kaynaklı Kep</div>
          </div>
          
          <!-- Drawing with interactive glow -->
          <div style="background: #030712; border: 2px solid #1e293b; border-radius: 14px; padding: 10px; margin-bottom: 14px;">
            <div class="cad-preview-box" style="height: 250px;">
              {svg_ftbw8}
            </div>
          </div>
          
          <!-- Interactive List Rows -->
          <div style="font-size: 12px; font-weight: 800; color: #94a3b8; margin-bottom: 8px;">ÖLÇÜYE DOKUN (ÇİZİMDE PARLASIN):</div>
          
          <div style="display: flex; flex-direction: column; gap: 8px;">
            <div class="glow-active" style="background: #111c2e; border: 2px solid #38bdf8; border-radius: 12px; padding: 12px 16px; display: flex; justify-content: space-between; align-items: center; cursor: pointer;">
              <div>
                <div style="font-size: 12px; color: #fbbf24; font-weight: 800;">[ E ] KEP BOYU (SEÇİLİ - PARLIYOR)</div>
                <div style="font-size: 22px; font-weight: 900;">6.50" (165.1 mm)</div>
              </div>
              <div style="font-size: 24px;">✨</div>
            </div>
            
            <div style="background: #111c2e; border: 1px solid #1e293b; border-radius: 12px; padding: 12px 16px; display: flex; justify-content: space-between; align-items: center; cursor: pointer;">
              <div>
                <div style="font-size: 12px; color: #94a3b8; font-weight: 800;">[ t ] ET KALINLIĞI (SCH-STD)</div>
                <div style="font-size: 20px; font-weight: 900;">0.375" (9.53 mm)</div>
              </div>
              <div style="font-size: 20px; color: #475569;">👉</div>
            </div>
            
            <div style="background: #111c2e; border: 1px solid #1e293b; border-radius: 12px; padding: 12px 16px; display: flex; justify-content: space-between; align-items: center; cursor: pointer;">
              <div>
                <div style="font-size: 12px; color: #94a3b8; font-weight: 800;">[ OD ] DIŞ ÇAP</div>
                <div style="font-size: 20px; font-weight: 900;">14.00" (355.6 mm)</div>
              </div>
              <div style="font-size: 20px; color: #475569;">👉</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 8: Quick-Preset Favorites Matrix -->
  <!-- ========================================== -->
  <section class="design-display" id="design-8">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 8: "Quick-Preset Favorites Matrix (Sık Kullanılanlar Matrisi)"</div>
        <div class="analysis-badge">EN HIZLI ULAŞIM (1 TIK)</div>
      </div>
      <div class="analysis-desc">
        Sahada yapılan işlerin %80'i 8 standart boyutta (2", 3", 4", 6", 8", 10", 12", 14") döner. Ana ekranda bu 8 dev kart doğrudan hazırdır. Menü aramadan tek tıkla doğrudan sonuca ulaşılır.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">Arama veya menü gezinmesi sıfır; devasa 2x4 ızgara butonları</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Hız</div>
          <div class="analysis-item-val">Uygulama açılır açılmaz 1 saniyede veriye ulaşım</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">LazyVerticalGrid(GridCells.Fixed(2))</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #141820; color: #fff; padding: 16px;">
          <div style="margin-bottom: 14px;">
            <div style="font-size: 12px; color: #f59e0b; font-weight: 800;">HIZLI SAHA ERİŞİMİ</div>
            <div style="font-size: 22px; font-weight: 900;">Sık Kullanılan Borular (STD)</div>
          </div>
          
          <!-- 2x4 Giant Preset Grid -->
          <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 12px;">
            <div style="background: #222733; border: 2px solid #333c4d; border-radius: 14px; padding: 14px; text-align: center; cursor: pointer;">
              <div style="font-size: 28px; font-weight: 900; color: #fff;">2" NPS</div>
              <div style="font-size: 12px; color: #f59e0b; font-weight: 700;">OD: 2.375" | t: 0.154"</div>
            </div>
            <div style="background: #222733; border: 2px solid #333c4d; border-radius: 14px; padding: 14px; text-align: center; cursor: pointer;">
              <div style="font-size: 28px; font-weight: 900; color: #fff;">3" NPS</div>
              <div style="font-size: 12px; color: #f59e0b; font-weight: 700;">OD: 3.500" | t: 0.216"</div>
            </div>
            <div style="background: #222733; border: 2px solid #333c4d; border-radius: 14px; padding: 14px; text-align: center; cursor: pointer;">
              <div style="font-size: 28px; font-weight: 900; color: #fff;">4" NPS</div>
              <div style="font-size: 12px; color: #f59e0b; font-weight: 700;">OD: 4.500" | t: 0.237"</div>
            </div>
            <div style="background: #222733; border: 2px solid #333c4d; border-radius: 14px; padding: 14px; text-align: center; cursor: pointer;">
              <div style="font-size: 28px; font-weight: 900; color: #fff;">6" NPS</div>
              <div style="font-size: 12px; color: #f59e0b; font-weight: 700;">OD: 6.625" | t: 0.280"</div>
            </div>
            <div style="background: #222733; border: 2px solid #333c4d; border-radius: 14px; padding: 14px; text-align: center; cursor: pointer;">
              <div style="font-size: 28px; font-weight: 900; color: #fff;">8" NPS</div>
              <div style="font-size: 12px; color: #f59e0b; font-weight: 700;">OD: 8.625" | t: 0.322"</div>
            </div>
            <div style="background: #222733; border: 2px solid #333c4d; border-radius: 14px; padding: 14px; text-align: center; cursor: pointer;">
              <div style="font-size: 28px; font-weight: 900; color: #fff;">10" NPS</div>
              <div style="font-size: 12px; color: #f59e0b; font-weight: 700;">OD: 10.75" | t: 0.365"</div>
            </div>
            <div style="background: #222733; border: 2px solid #333c4d; border-radius: 14px; padding: 14px; text-align: center; cursor: pointer;">
              <div style="font-size: 28px; font-weight: 900; color: #fff;">12" NPS</div>
              <div style="font-size: 12px; color: #f59e0b; font-weight: 700;">OD: 12.75" | t: 0.375"</div>
            </div>
            <div style="background: #f59e0b; border: 2px solid #f59e0b; border-radius: 14px; padding: 14px; text-align: center; cursor: pointer; color: #000; box-shadow: 0 0 15px rgba(245,158,11,0.4);">
              <div style="font-size: 28px; font-weight: 900;">14" NPS ★</div>
              <div style="font-size: 12px; font-weight: 900;">OD: 14.00" | t: 0.375"</div>
            </div>
          </div>
          
          <div style="margin-top: 16px; background: #222733; border-radius: 12px; padding: 12px; text-align: center; font-size: 14px; font-weight: 800; color: #f59e0b;">
            + TÜM DİĞER BOYUTLAR VE SCHEDULE SERİLERİ (KATALOG)
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 9: Daylight Office Clean -->
  <!-- ========================================== -->
  <section class="design-display" id="design-9">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 9: "Daylight Office Clean (Ofis & Masaüstü Açık Kağıt Modu)"</div>
        <div class="analysis-badge">AYDINLIK OFİS VE RAPORLAMA</div>
      </div>
      <div class="analysis-desc">
        Koyu temalardan hoşlanmayan veya iyi aydınlatılmış ofislerde masa başı çalışan mühendisler için kağıt beyazı temiz tema. Lacivert ve siyah mühendislik çizgileri, yüksek okuma netliği.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">Alışıldık kağıt doküman hissi, parlamayan temiz beyaz arka plan</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Tipografi</div>
          <div class="analysis-item-val">Koyu gri ve lacivert keskin metinler, sıfır göz yorgunluğu</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">lightColorScheme(surface = Color(0xFFF8FAFC))</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar" style="color: #0f172a;"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #f8fafc; color: #0f172a; padding: 16px;">
          <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #e2e8f0; padding-bottom: 10px; margin-bottom: 12px;">
            <div>
              <div style="font-size: 11px; color: #0284c7; font-weight: 800;">ASME B36.10M PIPE</div>
              <div style="font-size: 22px; font-weight: 900; color: #0f172a;">Boru Enkesiti</div>
            </div>
            <div style="background: #0284c7; color: #fff; padding: 6px 12px; border-radius: 8px; font-size: 13px; font-weight: 800;">METRİK (mm)</div>
          </div>
          
          <!-- Size Selector Light -->
          <div style="display: flex; gap: 8px; margin-bottom: 12px;">
            <div style="flex: 1; background: #fff; border: 2px solid #cbd5e1; border-radius: 10px; padding: 10px; text-align: center; font-size: 16px; font-weight: 800;">12"</div>
            <div style="flex: 1; background: #0284c7; color: #fff; border: 2px solid #0284c7; border-radius: 10px; padding: 10px; text-align: center; font-size: 18px; font-weight: 900;">14"</div>
            <div style="flex: 1; background: #fff; border: 2px solid #cbd5e1; border-radius: 10px; padding: 10px; text-align: center; font-size: 16px; font-weight: 800;">16"</div>
          </div>
          
          <!-- Drawing White Container -->
          <div style="background: #ffffff; border: 2px solid #cbd5e1; border-radius: 14px; padding: 10px; margin-bottom: 14px; box-shadow: 0 4px 10px rgba(0,0,0,0.05);">
            <div class="cad-preview-box" style="height: 240px;">
              {svg_pipe1}
            </div>
          </div>
          
          <!-- Large White Cards -->
          <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
            <div style="background: #fff; border: 1px solid #cbd5e1; border-left: 4px solid #0284c7; border-radius: 10px; padding: 12px;">
              <div style="font-size: 11px; color: #64748b; font-weight: 700;">DIŞ ÇAP</div>
              <div style="font-size: 26px; font-weight: 900; color: #0f172a;">355.6 mm</div>
            </div>
            <div style="background: #fff; border: 1px solid #cbd5e1; border-left: 4px solid #0284c7; border-radius: 10px; padding: 12px;">
              <div style="font-size: 11px; color: #64748b; font-weight: 700;">ET KALINLIĞI</div>
              <div style="font-size: 26px; font-weight: 900; color: #0f172a;">9.53 mm</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- ========================================== -->
  <!-- DESIGN 10: Flange Joint Assembly Hub -->
  <!-- ========================================== -->
  <section class="design-display" id="design-10">
    <div class="analysis-card">
      <div class="analysis-header">
        <div class="analysis-title">Tasarım 10: "Flange Joint Assembly Hub (Flanş + Conta + Cıvata Seti)"</div>
        <div class="analysis-badge">PİPEDATA'DA OLMAYAN KATİL ÖZELLİK</div>
      </div>
      <div class="analysis-desc">
        Kullanıcının sahada yaşadığı en büyük çileyi çözer: Tek tek flanş, conta ve cıvata sayfalarına girmek yerine; boru çapı ve basıncı seçildiğinde tüm montaj takımını tek reçete kartında verir.
      </div>
      <div class="analysis-grid">
        <div class="analysis-item">
          <div class="analysis-item-label">Yaşlı Ergonomisi</div>
          <div class="analysis-item-val">3 ayrı sayfayı gezme yükü sıfır; komple montaj paketi tek ekranda</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Anahtar Ağzı Gösterimi</div>
          <div class="analysis-item-val">Hangi anahtarı (36 mm / 1-7/16") alacağını koca harflerle yazar</div>
        </div>
        <div class="analysis-item">
          <div class="analysis-item-label">Compose Karşılığı</div>
          <div class="analysis-item-val">Scaffold + StickyHeader + Multi-Entity Joint State Holder</div>
        </div>
      </div>
    </div>
    
    <div class="device-wrapper">
      <div class="phone-frame">
        <div class="status-bar"><span>09:41</span><div class="status-icons"><span>5G</span><span>100%</span></div></div>
        
        <div class="screen-content" style="background: #0b1329; color: #f8fafc; padding: 14px;">
          <!-- Top Selection -->
          <div style="background: #16203b; border-radius: 14px; padding: 12px; margin-bottom: 12px; border: 1px solid #2d3d63;">
            <div style="font-size: 11px; color: #38bdf8; font-weight: 800; text-transform: uppercase;">MONTAJ REÇETESİ</div>
            <div style="font-size: 22px; font-weight: 900; color: #fff;">10" NPS - Class 300# RF</div>
          </div>
          
          <!-- Drawing showing Gasket / Flange Connection -->
          <div style="background: #080d1a; border: 2px solid #1e293b; border-radius: 14px; padding: 10px; margin-bottom: 12px;">
            <div class="cad-preview-box" style="height: 200px;">
              {svg_gspr1}
            </div>
          </div>
          
          <!-- 3-Way Assembly Recipe Cards -->
          <div style="display: flex; flex-direction: column; gap: 10px;">
            <!-- Flange Part -->
            <div style="background: #16203b; border-left: 4px solid #38bdf8; border-radius: 10px; padding: 10px 14px;">
              <div style="font-size: 11px; color: #38bdf8; font-weight: 800;">1. FLANŞ (2 ADET ASME B16.5 WN):</div>
              <div style="font-size: 16px; font-weight: 800; color: #fff;">Dış Çap: 17.50" | Kalınlık: 1.88" | Ağırlık: 122 lb/ad.</div>
            </div>
            
            <!-- Stud Bolt Part (The Most Important!) -->
            <div style="background: #16203b; border-left: 4px solid #fbbf24; border-radius: 10px; padding: 10px 14px;">
              <div style="font-size: 11px; color: #fbbf24; font-weight: 800;">2. SAPLAMA VE SOMUN TAKIMI:</div>
              <div style="font-size: 20px; font-weight: 900; color: #fff;">16 Adet 1" - 8 UNC x 165 mm (6.5")</div>
              <div style="font-size: 13px; color: #fbbf24; font-weight: 800; margin-top: 2px;">
                🔧 Anahtar Ağzı: 41 mm (1-5/8") | Tork: 450 Nm
              </div>
            </div>
            
            <!-- Gasket Part -->
            <div style="background: #16203b; border-left: 4px solid #10b981; border-radius: 10px; padding: 10px 14px;">
              <div style="font-size: 11px; color: #10b981; font-weight: 800;">3. CONTA (ASME B16.20):</div>
              <div style="font-size: 16px; font-weight: 800; color: #fff;">Spiral Sarımlı Conta (300#) | Kalınlık: 4.5 mm</div>
            </div>
            
            <!-- Total Assembly Weight -->
            <div style="background: #0284c7; color: #fff; border-radius: 10px; padding: 10px 14px; text-align: center; font-weight: 900; font-size: 16px;">
              TOPLAM PAKET AĞIRLIĞI: 134 kg (295 lb)
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

</main>

<script>
  function switchDesign(num) {{
    document.querySelectorAll('.design-display').forEach(el => el.classList.remove('active'));
    document.querySelectorAll('.tab-btn').forEach(el => el.classList.remove('active'));
    
    const activeSection = document.getElementById('design-' + num);
    if (activeSection) activeSection.classList.add('active');
    
    const activeTab = document.querySelectorAll('.tab-btn')[num - 1];
    if (activeTab) activeTab.classList.add('active');
    
    try {{ history.replaceState(null, null, '#design-' + num); }} catch (e) {{}}
  }}
  
  function setViewMode(mode) {{
    document.querySelectorAll('.view-toggle').forEach(el => el.classList.remove('active'));
    const frames = document.querySelectorAll('.phone-frame');
    
    if (mode === 'phone') {{
      document.getElementById('btn-phone').classList.add('active');
      frames.forEach(f => f.classList.remove('full-width'));
    }} else {{
      document.getElementById('btn-full').classList.add('active');
      frames.forEach(f => f.classList.add('full-width'));
    }}
  }}

  function checkHash() {{
    const hash = window.location.hash;
    if (hash && hash.startsWith('#design-')) {{
      const num = parseInt(hash.replace('#design-', ''));
      if (!isNaN(num) && num >= 1 && num <= 10) switchDesign(num);
    }}
  }}

  window.addEventListener('DOMContentLoaded', checkHash);
  window.addEventListener('hashchange', checkHash);
</script>

</body>
</html>
'''

with open(OUT_HTML, 'w', encoding='utf-8') as f:
    f.write(HTML_CONTENT)

shutil.copy(OUT_HTML, DOWNLOAD_HTML)
print(f'UI Prototype Showcase written to:\n  - {OUT_HTML}\n  - {DOWNLOAD_HTML}')
