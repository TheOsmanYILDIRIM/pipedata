#!/usr/bin/env python3
"""
Generate High-Resolution Master Comparison Boards for All SVG Categories:
1. Flanges (flan1 to flan7a - 14 files)
2. Gaskets & Line Blanks (gspr, gflt, grtj, blnu - 15 files)
3. Valves (vlfl, vlbw, vlsw, vlsc - 12 core files)
4. Forged Fittings & Swages (ftsw, ftsc, fswa - 12 core files)
"""

import os, re, subprocess, shutil
from PIL import Image, ImageDraw, ImageFont

OUT_DIR = '/storage/emulated/0/Download/comparisons'
LOCAL_DIR = '/data/data/com.termux/files/home/projects/pipedata/scratch/comparisons'
os.makedirs(OUT_DIR, exist_ok=True)
os.makedirs(LOCAL_DIR, exist_ok=True)

FONT_BOARD_TITLE = ImageFont.truetype('/data/data/com.termux/files/usr/share/fonts/TTF/DejaVuSans-Bold.ttf', 38)
FONT_BOARD_SUB = ImageFont.truetype('/data/data/com.termux/files/usr/share/fonts/TTF/DejaVuSans-Bold.ttf', 20)
FONT_BOARD_DESC = ImageFont.truetype('/data/data/com.termux/files/usr/share/fonts/TTF/DejaVuSans.ttf', 15)

FONT_CARD_TITLE = ImageFont.truetype('/data/data/com.termux/files/usr/share/fonts/TTF/DejaVuSans-Bold.ttf', 17)
FONT_CARD_SUB = ImageFont.truetype('/data/data/com.termux/files/usr/share/fonts/TTF/DejaVuSans.ttf', 13)
FONT_TAG_LABEL = ImageFont.truetype('/data/data/com.termux/files/usr/share/fonts/TTF/DejaVuSans-Bold.ttf', 13)

def clean_orig_svg(content):
    c = re.sub(r'<text\s+([^>]*?)\s+tspan\s+([^>]*?)>(.*?)</tspan></text>', r'<text \1 \2><tspan>\3</tspan></text>', content)
    c = re.sub(r'"([a-zA-Z_:][-a-zA-Z0-9_:.]*\s*=)', r'" \1', c)
    c = re.sub(r'Class\s*=', 'class=', c)
    return c

def render_svg_to_png(svg_str, out_png, w=800, h=600):
    tmp_svg = out_png + '.tmp.svg'
    with open(tmp_svg, 'w') as f:
        f.write(svg_str)
    subprocess.run(['rsvg-convert', '-w', str(w), '-h', str(h), '-o', out_png, tmp_svg], check=True)
    if os.path.exists(tmp_svg): os.remove(tmp_svg)

def render_comparison_card(code, title, standard, sub_info, reps=None):
    orig_path = f'/data/data/com.termux/files/home/projects/pipedata/data/svg/{code}.svg'
    mod_path = f'/data/data/com.termux/files/home/projects/pipedata/data/svg_modern/modern_{code}.svg'
    
    with open(orig_path) as f:
        orig_raw = f.read()
    with open(mod_path) as f:
        mod_raw = f.read()
        
    if reps:
        for k, v in reps.items():
            orig_raw = orig_raw.replace(f'[[{k}]]', v)
            mod_raw = mod_raw.replace(f'[[{k}]]', v)
            
    clean_orig = clean_orig_svg(orig_raw)
    orig_wrapped = f'''<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="130 25 1487 1116" preserveAspectRatio="xMidYMin">
<rect width="100%" height="100%" fill="#ffffff"/>
{clean_orig}
</svg>'''

    orig_raster = f'{LOCAL_DIR}/tmp_orig_{code}.png'
    mod_raster = f'{LOCAL_DIR}/tmp_mod_{code}.png'
    
    render_svg_to_png(orig_wrapped, orig_raster, 800, 600)
    render_svg_to_png(mod_raw, mod_raster, 800, 600)
    
    CW, CH = 1360, 680
    card = Image.new('RGB', (CW, CH), (15, 23, 42))
    draw = ImageDraw.Draw(card)
    
    # Header
    draw.rectangle([0, 0, CW, 75], fill=(30, 41, 59))
    draw.text((25, 12), f'[{code}.svg] {title}', font=FONT_CARD_TITLE, fill=(56, 189, 248))
    draw.text((25, 42), f'{standard} | {sub_info}', font=FONT_CARD_SUB, fill=(203, 213, 225))
    
    # Left box: Original
    bw = 640
    bh = 570
    lx = 25
    by = 90
    draw.rectangle([lx, by, lx + bw, by + bh], fill=(255, 255, 255), outline=(100, 116, 139), width=2)
    draw.rectangle([lx, by, lx + bw, by + 30], fill=(241, 245, 249))
    draw.text((lx + 12, by + 7), 'ASIL PIPEDATA (Eski Tel Oklar / Beyaz Zemin)', font=FONT_TAG_LABEL, fill=(15, 23, 42))
    
    im_o = Image.open(orig_raster)
    im_o.thumbnail((bw - 20, bh - 40))
    card.paste(im_o, (lx + (bw - im_o.width) // 2, by + 32 + (bh - 35 - im_o.height) // 2))
    
    # Right box: Modern AutoCAD
    rx = lx + bw + 30
    draw.rectangle([rx, by, rx + bw, by + bh], fill=(13, 21, 32), outline=(56, 189, 248), width=2)
    draw.rectangle([rx, by, rx + bw, by + 30], fill=(30, 41, 59))
    draw.text((rx + 12, by + 7), 'YENİ AUTOCAD DARK BLUEPRINT (3:1 Dolu Ok | Sıfır Kayıp)', font=FONT_TAG_LABEL, fill=(56, 189, 248))
    
    im_m = Image.open(mod_raster)
    im_m.thumbnail((bw - 20, bh - 40))
    card.paste(im_m, (rx + (bw - im_m.width) // 2, by + 32 + (bh - 35 - im_m.height) // 2))
    
    # Cleanup temp
    if os.path.exists(orig_raster): os.remove(orig_raster)
    if os.path.exists(mod_raster): os.remove(mod_raster)
    
    return card

def assemble_master_board(board_title, board_sub, board_desc, items, out_filename):
    print(f'\nGenerating Master Board: {out_filename} ({len(items)} items)...')
    cols = 2
    rows = (len(items) + cols - 1) // cols
    
    card_w = 1360
    card_h = 680
    
    bw = cols * card_w + 60
    bh = rows * card_h + 230
    
    board = Image.new('RGB', (bw, bh), (11, 15, 25))
    draw = ImageDraw.Draw(board)
    
    # Board Header
    draw.rectangle([0, 0, bw, 180], fill=(15, 23, 42))
    draw.text((35, 25), board_title, font=FONT_BOARD_TITLE, fill=(56, 189, 248))
    draw.text((35, 85), board_sub, font=FONT_BOARD_SUB, fill=(248, 250, 252))
    draw.text((35, 125), board_desc, font=FONT_BOARD_DESC, fill=(148, 163, 184))
    
    for idx, item in enumerate(items):
        c = idx % cols
        r = idx // cols
        x = 20 + c * (card_w + 20)
        y = 200 + r * (card_h + 20)
        
        card = render_comparison_card(item['code'], item['title'], item['standard'], item['sub_info'], item.get('reps'))
        board.paste(card, (x, y))
        print(f'  [{idx+1}/{len(items)}] Card ready: {item["code"]}.svg')
        
    local_path = f'{LOCAL_DIR}/{out_filename}'
    out_path1 = f'{OUT_DIR}/{out_filename}'
    out_path2 = f'/storage/emulated/0/Download/{out_filename}'
    
    board.save(local_path, quality=95)
    shutil.copy(local_path, out_path1)
    shutil.copy(local_path, out_path2)
    print(f'Saved: {out_path1} and {out_path2}')
    return local_path

# ==========================================
# 1. FLANGES CATALOG (14 SVGs)
# ==========================================
FLANGE_ITEMS = [
    {'code': 'flan1',  'title': 'Weldneck Flange (Kaynak Boyunlu Flanş)', 'standard': 'ASME B16.5 / B16.47', 'sub_info': 'Raised Face (RF) | Boyun Kaynak Ağzı & Et Kalınlığı'},
    {'code': 'flan1a', 'title': 'Weldneck Flange RTJ (Kaynak Boyunlu RTJ)', 'standard': 'ASME B16.5 / B16.47', 'sub_info': 'Ring Type Joint (RTJ) Kanalı & Boyun'},
    {'code': 'flan2',  'title': 'Slip-on Flange (Düz Geçme Flanş)', 'standard': 'ASME B16.5', 'sub_info': 'Raised Face (RF) | İç Çap & Göbek Ölçüleri'},
    {'code': 'flan2a', 'title': 'Slip-on Flange RTJ (Düz Geçme RTJ)', 'standard': 'ASME B16.5', 'sub_info': 'Ring Type Joint (RTJ) Kanalı & Göbek'},
    {'code': 'flan3',  'title': 'Blind Flange (Kör Flanş)', 'standard': 'ASME B16.5 / B16.47', 'sub_info': 'Raised Face (RF) | Kalınlık & Cıvata Dairesi'},
    {'code': 'flan3a', 'title': 'Blind Flange RTJ (Kör Flanş RTJ)', 'standard': 'ASME B16.5 / B16.47', 'sub_info': 'Ring Type Joint (RTJ) Kanalı & Kalınlık'},
    {'code': 'flan4',  'title': 'Threaded Flange (Dişli Flanş)', 'standard': 'ASME B16.5', 'sub_info': 'Raised Face (RF) | NPT Diş Geometrisi & Göbek'},
    {'code': 'flan4a', 'title': 'Threaded Flange RTJ (Dişli Flanş RTJ)', 'standard': 'ASME B16.5', 'sub_info': 'Ring Type Joint (RTJ) Kanalı & NPT Diş'},
    {'code': 'flan5',  'title': 'Lap Joint Flange (Gevşek / Serbest Flanş)', 'standard': 'ASME B16.5', 'sub_info': 'Raised Face (RF) | Yaka Yarıçapı & İç Çap'},
    {'code': 'flan5a', 'title': 'Lap Joint Flange RTJ (Gevşek Flanş RTJ)', 'standard': 'ASME B16.5', 'sub_info': 'Ring Type Joint (RTJ) & Yaka Radyüsü'},
    {'code': 'flan6',  'title': 'Socket-Welding Flange (Soket Kaynaklı Flanş)', 'standard': 'ASME B16.5', 'sub_info': 'Raised Face (RF) | Soket Derinliği & Boru Yuvası'},
    {'code': 'flan6a', 'title': 'Socket-Welding Flange RTJ (Soket Flanş RTJ)', 'standard': 'ASME B16.5', 'sub_info': 'Ring Type Joint (RTJ) | Soket Yuvası'},
    {'code': 'flan7',  'title': 'Orifice Flange (Orifis Flanşı)', 'standard': 'ASME B16.36', 'sub_info': 'Raised Face (RF) | Basınç Prizi Delikleri & Cıvatalar'},
    {'code': 'flan7a', 'title': 'Orifice Flange RTJ (Orifis Flanşı RTJ)', 'standard': 'ASME B16.36', 'sub_info': 'Ring Type Joint (RTJ) | Basınç Prizleri'}
]

# ==========================================
# 2. GASKETS & LINE BLANKS CATALOG (15 SVGs)
# ==========================================
GASKET_ITEMS = [
    {'code': 'gspr1',  'title': 'Spiral-Wound Gasket (Spiral Sarımlı Conta)', 'standard': 'ASME B16.20 (B16.5)', 'sub_info': 'İç Halka, Dış Merkezleme Halkası & Sarım Alanı'},
    {'code': 'gspr2',  'title': 'Spiral-Wound Gasket Series A', 'standard': 'ASME B16.20 (B16.47 A)', 'sub_info': 'Büyük Çap Seri A Spiral Sarımlı Conta'},
    {'code': 'gspr3',  'title': 'Spiral-Wound Gasket Series B', 'standard': 'ASME B16.20 (B16.47 B)', 'sub_info': 'Büyük Çap Seri B Spiral Sarımlı Conta'},
    {'code': 'gflt1',  'title': 'Flat Non-Metallic Gasket (Düz Ametlik Conta)', 'standard': 'ASME B16.21 (B16.5)', 'sub_info': 'IBC (Inside Bolt Circle) & Full Face'},
    {'code': 'gflt2',  'title': 'Flat Gasket Series A', 'standard': 'ASME B16.21 (B16.47 A)', 'sub_info': 'Büyük Çap Seri A Düz Conta Boyutları'},
    {'code': 'gflt3',  'title': 'Flat Gasket Series B', 'standard': 'ASME B16.21 (B16.47 B)', 'sub_info': 'Büyük Çap Seri B Düz Conta Boyutları'},
    {'code': 'grtj1',  'title': 'Ring-Joint Gasket Type R (RTJ Oval/Sekizgen)', 'standard': 'ASME B16.20 / API 6A', 'sub_info': 'Tip R Oval ve Octagonal Halka Conta'},
    {'code': 'grtj2',  'title': 'Ring-Joint Gasket Type RX (Basınç Aktif)', 'standard': 'ASME B16.20 / API 6A', 'sub_info': 'Tip RX Yüksek Basınç Kendinden Sızdırmaz'},
    {'code': 'grtj3',  'title': 'Ring-Joint Gasket Type BX (Aşırı Yüksek Basınç)', 'standard': 'API Spec 6A (20k psi)', 'sub_info': 'Tip BX Basınç Dengeleme Delikli RTJ'},
    {'code': 'blnu1',  'title': 'Figure-8 Blank (Gözlük Körleme)', 'standard': 'ASME B16.48', 'sub_info': 'Açık/Kapalı Konum Değiştirilebilir Gözlük Sacı'},
    {'code': 'blnu1a', 'title': 'Figure-8 Blank RTJ', 'standard': 'ASME B16.48', 'sub_info': 'Ring-Joint Kanal Uyumlu Gözlük Sacı'},
    {'code': 'blnu2',  'title': 'Paddle Blank (Raket Kör Sacı)', 'standard': 'ASME B16.48', 'sub_info': 'Kulaklı Düz Körleme Plakası'},
    {'code': 'blnu2a', 'title': 'Paddle Blank RTJ', 'standard': 'ASME B16.48', 'sub_info': 'RTJ Kanallı Raket Kör Sacı'},
    {'code': 'blnu3',  'title': 'Paddle Spacer (Raket Halka / Açma Parçası)', 'standard': 'ASME B16.48', 'sub_info': 'Kulaklı Hat Açıcı Boşluk Halkası'},
    {'code': 'blnu4',  'title': 'Paddle Spacer RTJ (RTJ Raket Açma Halkası)', 'standard': 'ASME B16.48', 'sub_info': 'RTJ Kanallı Hat Açıcı Raket Halka'}
]

# ==========================================
# 3. VALVES CATALOG (12 Core SVGs)
# ==========================================
VALVE_ITEMS = [
    {'code': 'vlfl1',  'title': 'Flanged Gate Valve (Flanşlı Sürgülü Vana)', 'standard': 'ASME B16.10 / API 600', 'sub_info': 'Yüzeyden Yüze Boy (A), El Simidi & Mil Yüksekliği'},
    {'code': 'vlfl2',  'title': 'Flanged Globe Valve (Flanşlı Glop Vana)', 'standard': 'ASME B16.10 / BS 1873', 'sub_info': 'Akış Kontrol Geometrisi & Yüzey Boyu (A)'},
    {'code': 'vlfl3',  'title': 'Flanged Swing Check Valve (Flanşlı Çalpara Çekvalf)', 'standard': 'ASME B16.10 / BS 1868', 'sub_info': 'Geri Akış Önleme Diski & Montaj Boyu'},
    {'code': 'vlfl5',  'title': 'Flanged Ball Valve (Flanşlı Küresel Vana)', 'standard': 'ASME B16.10 / API 6D', 'sub_info': 'Tam Geçişli Küre, Kol Boyu & Flanş Açıklığı'},
    {'code': 'vlfl6',  'title': 'Flanged Wafer Check Valve (Wafer Çekvalf)', 'standard': 'ASME B16.10 / API 594', 'sub_info': 'Kompakt Flanş Arası Montaj Gövdesi'},
    {'code': 'vlfl7',  'title': 'Flanged Plug Valve (Flanşlı Plug / Konik Vana)', 'standard': 'ASME B16.10 / API 599', 'sub_info': 'Döner Konik Tıpa & Yüzey Boyutları'},
    {'code': 'vlbw1',  'title': 'Buttweld Gate Valve (Alın Kaynaklı Sürgülü Vana)', 'standard': 'ASME B16.10 / B16.34', 'sub_info': 'Uçtan Uca Boy & B16.25 Kaynak Ağzı Pahı'},
    {'code': 'vlbw2',  'title': 'Buttweld Globe Valve (Alın Kaynaklı Glop Vana)', 'standard': 'ASME B16.10 / B16.34', 'sub_info': 'Alın Kaynak Boyunları & Gövde Zarfı'},
    {'code': 'vlbw3',  'title': 'Buttweld Swing Check Valve (Kaynaklı Çekvalf)', 'standard': 'ASME B16.10 / B16.34', 'sub_info': 'Kaynak Ağızlı Geri Dönüşsüz Vana'},
    {'code': 'vlbw4',  'title': 'Buttweld Ball Valve (Alın Kaynaklı Küresel Vana)', 'standard': 'ASME B16.10 / API 6D', 'sub_info': 'Kaynak Uçlu Küresel Vana Gövdesi'},
    {'code': 'vlsw1',  'title': 'Socket Weld Gate Valve (Soket Kaynaklı Sürgülü)', 'standard': 'API 602 / ASME B16.34', 'sub_info': 'Kompakt Dövme Çelik Soketli Gövde'},
    {'code': 'vlsc1',  'title': 'Threaded Gate Valve (Dişli Sürgülü Vana)', 'standard': 'API 602 / ASME B16.34', 'sub_info': 'NPT Dişli Kompakt Dövme Çelik Gövde'}
]

# ==========================================
# 4. FORGED FITTINGS CATALOG (12 Core SVGs)
# ==========================================
FITTING_ITEMS = [
    {'code': 'ftsw1',  'title': 'Socket Weld 90° Elbow (Soket 90° Dirsek)', 'standard': 'ASME B16.11 (3000/6000/9000)', 'sub_info': 'Merkez-Dip A, Soket Çapı B & Taban C'},
    {'code': 'ftsw2',  'title': 'Socket Weld 45° Elbow (Soket 45° Dirsek)', 'standard': 'ASME B16.11', 'sub_info': 'Merkez-Dip C, Soket Derinliği & Et Kalınlığı'},
    {'code': 'ftsw3',  'title': 'Socket Weld Equal Tee (Soket Eşit Te)', 'standard': 'ASME B16.11', 'sub_info': 'Gövde & Branşman Soket Yuvaları'},
    {'code': 'ftsw4',  'title': 'Socket Weld Cross (Soket İstavroz / Dörtlü)', 'standard': 'ASME B16.11', 'sub_info': '4 Yönlü Simetrik Soket Geometrisi'},
    {'code': 'ftsw5',  'title': 'Socket Weld Full Coupling (Soket Manşon)', 'standard': 'ASME B16.11', 'sub_info': 'Tam Boy Manşon & Orta Emniyet Boşluğu'},
    {'code': 'ftsw6',  'title': 'Socket Weld Half Coupling (Soket Yarım Manşon)', 'standard': 'ASME B16.11', 'sub_info': 'Kazan/Boru Bağlantı Yarım Manşonu'},
    {'code': 'ftsw7',  'title': 'Socket Weld Cap (Soket Kör Tapa / Kep)', 'standard': 'ASME B16.11', 'sub_info': 'Soketli Hat Körleme Kepi'},
    {'code': 'ftsc1',  'title': 'Threaded 90° Elbow (Dişli 90° Dirsek)', 'standard': 'ASME B16.11', 'sub_info': 'NPT Konik Diş & Merkez-Uç Mesafesi'},
    {'code': 'ftsc2',  'title': 'Threaded Tee (Dişli Eşit Te)', 'standard': 'ASME B16.11', 'sub_info': '3 Ağızlı NPT Dişli Gövde'},
    {'code': 'ftsc10', 'title': 'Threaded Hex Head Plug (Altıköşe Başlı Kör Tapa)', 'standard': 'ASME B16.11', 'sub_info': 'Altıköşe Anahtar Ağzı & NPT Diş Boyu'},
    {'code': 'ftsc12', 'title': 'Threaded Hex Bushing (Altıköşe Redüksiyon Bushing)', 'standard': 'ASME B16.11', 'sub_info': 'Dış Erkek NPT x İç Dişi NPT Redüksiyon'},
    {'code': 'fswa1',  'title': 'Swage Nipple (Konsantrik Dövme Boru Nipeli)', 'standard': 'MSS-SP-95 / BS 3799', 'sub_info': 'Büyük Çap x Küçük Çap Geçiş Nipeli'}
]

if __name__ == '__main__':
    print('Starting Master Catalog Comparison Generation for All Categories...')
    
    # 1. Flanges
    assemble_master_board(
        'PIPEDATA PRO - ASME FLANŞLAR KATALOĞU (FLANGES)',
        'ASME B16.5 & ASME B16.47 (Tüm Boyutlar, RF & RTJ Yüzeyler) - Orijinal APK vs Yeni AutoCAD Standardı',
        'Sol: Orijinal Pipedata APK (Açık tel oklar, beyaz zemin) | Sağ: Modernize AutoCAD Dark Blueprint (Dolu 3:1 oklar, sıfır çizgi kaybı)',
        FLANGE_ITEMS,
        'all_flanges_comparison_master.png'
    )
    
    # 2. Gaskets & Blanks
    assemble_master_board(
        'PIPEDATA PRO - CONTALAR VE KÖRLEME SACLARI (GASKETS & LINE BLANKS)',
        'ASME B16.20, B16.21, B16.48 & API 6A - Orijinal APK vs Yeni AutoCAD Standardı',
        'Spiral Sarımlı (Spiral-Wound), Düz (Flat), RTJ Ring-Joint (R, RX, BX), Gözlük Körleme (Figure-8) & Raket Saclar',
        GASKET_ITEMS,
        'all_gaskets_bolting_master.png'
    )
    
    # 3. Valves
    assemble_master_board(
        'PIPEDATA PRO - ENDÜSTRİYEL VANALAR KATALOĞU (VALVES)',
        'ASME B16.10, B16.34, API 600, API 6D, API 594 - Orijinal APK vs Yeni AutoCAD Standardı',
        'Flanşlı (Flanged), Alın Kaynaklı (Buttweld), Soketli (Socket Weld) ve Dişli (Screwed) Vana Boyut ve Zarf Çizimleri',
        VALVE_ITEMS,
        'all_valves_comparison_master.png'
    )
    
    # 4. Forged Fittings
    assemble_master_board(
        'PIPEDATA PRO - DÖVME ÇELİK EK PARÇALARI (FORGED FITTINGS & SWAGES)',
        'ASME B16.11 & MSS-SP-95 (Class 3000 / 6000 / 9000) - Orijinal APK vs Yeni AutoCAD Standardı',
        'Soket Kaynaklı (Socket Weld) ve Dişli (Threaded/NPT) Dirsekler, Teler, Manşonlar, Kepler, Tapalar ve Nipel Geçişleri',
        FITTING_ITEMS,
        'all_forged_fittings_master.png'
    )
    
    print('\nALL MASTER BOARDS SUCCESSFULLY CREATED!')
