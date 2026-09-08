# Pipedata Modern Alternatifi - Dondurulmuş Test Şartnamesi (test.md)

> [!IMPORTANT]
> **Dondurulmuş Test Protokolü (Frozen Protocol):** Bu test şartnamesi geliştirme başlamadan önce belirlenmiştir ve **kesinlikle değiştirilmeyecektir**. Tüm veri ve uygulama geliştirme süreci tamamlandığında, bu testler nihai kabul kriteri (Acceptance Gate) olarak çalıştırılacaktır.

---

## Test Paketi 1: Veri Tabanı Bütünlüğü ve Kapsam Doğrulaması

### Test 1.1: CSV Dosya Kapsamı (%100 Eşleşme)
* **Hedef:** Orijinal APK'dan çıkarılan 956 CSV dosyasının tamamı `schema_manifest.json` içerisinde hedef SQLite tablolarına haritalanmış olmalıdır.
* **Kriter:** Haritalanmamış dosya sayısı = `0`.
* **Beklenen Değer:** 956 / 956 (%100).

### Test 1.2: Satır Sayısı Korunumu (Sıfır Veri Kaybı)
* **Hedef:** 956 tablodaki kaynak veri satırlarının toplamı eksiksiz olmalıdır.
* **Beklenen Değer:** Tam olarak `22,532` kaynak satır.

### Test 1.3: Standart Kapsamı
* **Hedef:** 17 küresel standardın tamamı şemada yer almalıdır:
  * ASME B16.5, ASME B16.9, ASME B16.11, ASME B16.10, ASME B16.34, ASME B16.47 Seri A/B, ASME B16.48, ASME B16.20, ASME B16.21, ASME B16.36, ASME B36.10M, ASME B36.19M, API Spec 6A, DIN EN 1092-1, MSS-SP-97, MSS-SP-95, ASME B18.2.1/B18.2.2.

### Test 1.4: Çap ve Basınç Sınıfları Aralığı
* **Boru Çap Aralığı:** NPS 1/8" (DN 6) ile NPS 80" (DN 2000) arası eksiksiz.
* **Schedule Serisi:** Sch 5, 5S, 10, 10S, 20, 30, 40, 40S, STD, 60, 80, 80S, XS, 100, 120, 140, 160, XXS (18 schedule serisi).
* **Flanş Basınç Sınıfları:** Class 150, 300, 400, 600, 900, 1500, 2500.
* **DIN Basınç Kademeleri:** PN 2.5, 6, 10, 16, 25, 40, 63, 100, 160, 250, 320, 400.

---

## Test Paketi 2: Gerçek Ekran Görüntüsü Kıyaslama Testleri (Ground Truth Benchmarks)

### Benchmark A: Boru Bilgisi Ekranı (Ekran #3 Doğrulaması)
* **Girdi Parametreleri:** Boyut = `14" NPS`, Schedule = `STD`, Standart = `ASME B36.10M`.
* **Doğrulanacak Değerler (Görsel Ekran #3):**
  | Parametre | Orijinal Ekran Değeri | Metrik Karşılığı | İzin Verilen Hata Toleransı |
  | :--- | :---: | :---: | :---: |
  | **Dış Çap (OD)** | `14.000"` | `355.60 mm` | `%0.00` (Kesin Eşleşme) |
  | **İç Çap (ID)** | `13.250"` | `336.55 mm` | `%0.00` (Kesin Eşleşme) |
  | **Et Kalınlığı (WT)** | `0.375"` | `9.53 mm` | `%0.00` (Kesin Eşleşme) |
  | **Yarıçap (r)** | `7.000"` | `177.80 mm` | `%0.00` (Kesin Eşleşme) |
  | **Çıplak Boru Ağırlığı** | `54.62 lb/ft` | `81.33 kg/m` | `< %0.05` |
  | **Su Dolu Ağırlık** | `114.34 lb/ft` | `170.25 kg/m` | `< %0.05` |

### Benchmark B: Alın Kaynaklı Kep Ekranı (Ekran #1 Doğrulaması)
* **Girdi Parametreleri:** Parça = `Buttwelding Cap`, Boyut = `14" NPS`, Schedule = `STD`, Standart = `ASME B16.9-2018`.
* **Doğrulanacak Değerler (Görsel Ekran #1):**
  | Parametre | Orijinal Ekran Değeri | Metrik Karşılığı | İzin Verilen Hata Toleransı |
  | :--- | :---: | :---: | :---: |
  | **Kep Yüksekliği** | `6.500"` | `165.10 mm` | `%0.00` (Kesin Eşleşme) |
  | **Et Kalınlığı** | `0.375"` | `9.53 mm` | `%0.00` (Kesin Eşleşme) |
  | **Parça Ağırlığı** | `37.48 lb` | `17.00 kg` | `< %0.05` |

### Benchmark C: Ayarlar & Birim Dönüşümleri (Ekran #4 Doğrulaması)
* **Birim Katsayıları:**
  * $1	ext{ inç} = 25.400	ext{ mm}$ (Kesin)
  * $1	ext{ lb/ft} = 1.4881639	ext{ kg/m}$ (Kesin)
  * $1	ext{ lb} = 0.45359237	ext{ kg}$ (Kesin)
* **Kesirli İnç Formatlayıcı:**
  * `0.375` $ightarrow$ `3/8"`
  * `13.250` $ightarrow$ `13 1/4"`
  * `14.000` $ightarrow$ `14"`
  * `0.125` $ightarrow$ `1/8"`
  * `1.500` $ightarrow$ `1 1/2"`
* **NPS $\leftrightarrow$ DN Çift Yönlü Eşleşmesi:**
  * `14"` $\leftrightarrow$ `DN 350`, `1/2"` $\leftrightarrow$ `DN 15`, `2"` $\leftrightarrow$ `DN 50`, `24"` $\leftrightarrow$ `DN 600`.

### Benchmark D: 16" NPS Boru Ekranı (Ekran #6 - Screenshot_20260907-193938.png Doğrulaması)
* **Girdi Parametreleri:** Standart = `ASME B36.10M/19M-2022`, Boyut = `16" NPS`, Schedule = `STD`.
* **Doğrulanacak Değerler (Görsel Ekran #6):**
  | Parametre | Orijinal Ekran Değeri | Metrik Karşılığı | İzin Verilen Hata Toleransı |
  | :--- | :---: | :---: | :---: |
  | **Dış Çap (OD - Üst Ölçü)** | `16.00"` | `406.40 mm` | `%0.00` (Kesin Eşleşme) |
  | **İç Çap (ID - Alt Ölçü)** | `15.25"` | `387.35 mm` | `%0.00` (Kesin Eşleşme) |
  | **Et Kalınlığı (WT - Ok Ölçü)** | `0.375"` | `9.53 mm` | `%0.00` (Kesin Eşleşme) |
  | **Yarıçap (Half OD - Sağ Ölçü)** | `8.00"` | `203.20 mm` | `%0.00` (Kesin Eşleşme) |
  | **Çıplak Boru Ağırlığı (Wt)** | `62.64 lb/ft` | `93.22 kg/m` | `%0.00` (Kesin Eşleşme) |
  | **Su Dolu Ağırlık** | `141.74 lb/ft` | `210.93 kg/m` | `%0.00` (Kesin Eşleşme) |
  | **Atalet Momenti ($I$)** | `561.79 in^4` | `23,383 cm^4` | `%0.00` (Kesin Eşleşme) |

---

## Test Paketi 3: Mühendislik Hesaplama Motoru Testleri

### Test 3.1: Kesit Geometrisi & Atalet Momenti
* $OD = 14.000	ext{ inç}$, $ID = 13.250	ext{ inç}$
* **Formül:** $I = rac{\pi}{64} (OD^4 - ID^4)$
* **Beklenen $I$:** $pprox 372.8	ext{ in}^4$ ($15,517	ext{ cm}^4$).
* **Mukavemet Momenti ($Z$):** $Z = rac{2 I}{OD} pprox 53.26	ext{ in}^3$ ($872.7	ext{ cm}^3$).

### Test 3.2: Boru Su Hacmi & Ağırlığı
* $ID = 13.250	ext{ inç} = 0.33655	ext{ m}$
* Kesit Hacmi = $rac{\pi \cdot ID^2}{4} = 0.08896	ext{ m}^3	ext{/m}$
* Su Ağırlığı = $0.08896 	imes 1000 = 88.96	ext{ kg/m} = 59.78	ext{ lb/ft}$
* Toplam Ağırlık = Çıplak ($54.62$) + Su ($59.78$) = $114.40	ext{ lb/ft}$ (Ekran #3 ile tam tutarlı).

### Test 3.3: Güvenli Açıklık (Safe Span) Çift Kriter Testi
* **Formüller:**
  * $L_{sehim} = \sqrt[4]{rac{384 E I \delta_{max}}{5 w}}$ ($\delta_{max} = 10	ext{ mm}$, $E = 203,390	ext{ MPa}$)
  * $L_{gerilme} = \sqrt{rac{10 \sigma_{allowable} I}{w \cdot r_{outer}}}$ ($\sigma_{allowable} = 22	ext{ MPa}$ ve $68	ext{ MPa}$)
* **Kural:** $L_{safe} = \min(L_{sehim}, L_{gerilme})$ ve kısıtlayan faktör doğru etiketlenmelidir (`deflection` veya `stress`).

---

## Test Paketi 4: Dinamik SVG Şablon & Etiket Eşleme Testleri

### Test 4.1: Şablon Varlık Doğrulaması
* Toplam 122 SVG dosyasının eksiksiz mevcut olduğu doğrulanmalıdır.

### Test 4.2: Yer Tutucu (Placeholder) Eşleme
* 122 SVG içerisindeki tüm `[[n]]` ve `[[DB2_n]]` etiketleri taranacak;
* Her etiketin ilgili tablonun geçerli bir kolonuna işaret ettiği, hiçbir tanımsız/kopuk (dangling) etiket bulunmadığı doğrulanacaktır.

### Test 4.3: Modern AutoCAD / ASME Y14.5M Vektör Dönüşüm Bütünlüğü
* 122 adet modernize edilmiş AutoCAD SVG dosyasının (`modern_*.svg`) fiziki varlığı ve XML syntax geçerliliği denetlenir;
* Orijinal CAD parça geometrilerinin, ASME B16.25 kaynak ağzı pahlarının (weld bevel) ve ANSI-31 kesit taramalarının %100 sıfır çizgi kaybı (zero loss) ile korunduğu doğrulanır;
* Tel okların tamamının AutoCAD 3:1 kapalı-dolu oklara dönüştürüldüğü ve `pipedata.db` içerisindeki `tbl_svg_templates.modern_svg_content` kolonunda 122 kaydın eksiksiz güncellendiği teyit edilir.

---

## Test Çalıştırma Protokolü
1. Bu test şartnamesi `test_suite.py` komut dosyası ile otomatik çalıştırılır.
2. Testler sırayla koşulur: `python3 test_suite.py`
3. Tüm testler `%100 BAŞARILI` (PASS) olmadan uygulama "bitti" kabul edilemez.
