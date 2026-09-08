# Pipedata APK (A-1.6.02) Kapsamlı Tersine Mühendislik & Veri Analiz Raporu

## 1. Giriş ve Amaç
Bu rapor, `/storage/emulated/0/Download/Pipedata_1602.apk` paket dosyasının ve kullanıcı tarafından sağlanan 5 adet arayüz ekran görüntüsünün detaylı tersine mühendislik analizini içerir. Amaç, endüstri standardı borulama mühendisliği referans uygulaması olan **Pipedata**'nın tüm veri setlerini, menü/özellik hiyerarşisini, hesaplama algoritmalarını ve teknik resim varlıklarını eksiksiz çıkararak modern, hızlı ve yüksek kullanıcı deneyimine sahip yeni nesil bir alternatif için zemin hazırlamaktır.

---

## 2. APK İkili ve Mimari Analizi

### 2.1. Temel Yapı Bilgileri
* **Paket Kimliği:** `b4a.example2`
* **Sürüm Kodu / Adı:** `1602` / `A-1.6.02`
* **Geliştirme Çerçevesi:** Anywhere Software Basic4Android (B4A)
* **Hedef Android Sürümü:** API 34 (Android 14) / Min SDK 5
* **İzinler:** Yalnızca `android.permission.INTERNET` (Lisans kontrolü ve güncelleme yönlendirmesi için).
* **Ekran Uyumluluğu:** Small, Normal, Large, XLarge (Sabit portre oryantasyonu: `android.hardware.screen.portrait`).

### 2.2. Varlık (Asset) Dağılımı
APK paketinin `assets/` dizini analiz edildiğinde toplam **1,608 dosya** tespit edilmiştir:
* **956 CSV Dosyası:** Tüm borulama, flanş, fitting, vana, conta ve körleme boyut tabloları.
* **299 Görsel (PNG/JPG/GIF):** Menü ikonları, parça önizlemeleri ve butonlar.
* **214 DXF Dosyası:** AutoCAD uyumlu 2D vektörel CAD teknik çizim blokları.
* **122 SVG Dosyası:** Ekranlarda ölçü oklarıyla birlikte gösterilen vektörel parça kesitleri.
* **6 SET Dosyası:** Varsayılan seçim ve malzeme/yalıtım ön ayar durumları (`flan1.set`, `srrb1-4.set`).
* **4 Metin / Dokümantasyon:** Safe Span parametre açıklamaları (`doc.txt`, `doc.rtf`), B4A görünürlük betikleri (`visibility.txt`, `list.txt`).
* **1 BAL Dosyası:** B4A derlenmiş ikili arayüz yerleşim dosyası (`main.bal`).
* **1 EXE Dosyası:** **Gereksiz Artık:** Windows Pipedata-Pro 15.0 masaüstü çalıştırılabilir dosyası (4.1 MB).

---

## 3. Ekran Görüntüleri ve UI/UX Akış Analizi

Sağlanan 5 adet ekran görüntüsü incelenerek uygulamanın kullanıcı deneyimi çözümlenmiştir:

1. **Ekran #5 (`Screenshot_20260907-182536.png`) - Ana Menü (Main Menu):**
   * Liste tarzı dikey menü: Flanges, Fittings, Gaskets, Valves, Line Blanks, Pipe, Upgrade, Settings, About.
   * Her kategoriye ait sol tarafta monokrom teknik ikon.

2. **Ekran #4 (`Screenshot_20260907-182543.png`) - Ayarlar (Settings):**
   * Birim Seçenekleri:
     * `Metric Units` (Milimetre, Kilogram, Metre, MPa)
     * `U.S. Customary Units` (İnç ondalık, Pound, Feet, PSI)
     * `Inch Fractions` (Kesirli inç gösterimi: 3/8", 1/2", 14 1/4" vb.)
   * Boru Çap Gösterimi:
     * `NPS` (Nominal Pipe Size - İnç cinsinden: örn. 14")
     * `DN` (Diameter Nominal - Metrik milimetre cinsinden: örn. DN 350)
   * Altta "Apply" aksiyon butonu.

3. **Ekran #2 (`Screenshot_20260907-182607.png`) - Alt Menü (Fittings Menüsü):**
   * Fittings kategorisi alt türleri:
     * `Buttwelding` (Alın kaynaklı - ASME B16.9)
     * `Threaded` (Dişli - ASME B16.11)
     * `Socket-Welded` (Soket kaynaklı - ASME B16.11)

4. **Ekran #3 (`Screenshot_20260907-182559.png`) - Boru Bilgi Ekranı (Pipe Information):**
   * Standart: `ASME B36.10M/19M-2022 Pipe`
   * Başlık: `14" NPS Sch-STD Pipe Information`
   * Hesaplanan Değerler:
     * `Wt = 54.62 lb/ft` (Metrikte `81.33 kg/m`)
     * `Weight full with water = 114.34 lb/ft`
   * Merkezde Teknik Çizim: Boru kesiti üzerinde anlık OD (`14"`), ID (`13.25"`), Et Kalınlığı (`0.375"`) ve Yarıçap (`7"`) okları.
   * Parametre Seçiciler: `Size` (`14"`) ve `Sch` (`STD`).
   * Alt Sabit Navigasyon: `Menu`, `Settings`, `About`.

5. **Ekran #1 (`Screenshot_20260907-182612.png`) - Fitting Detay Ekranı (Buttwelding Cap):**
   * Standart: `ASME B16.9 Buttwelding Fittings - ASME B16.9-2018`
   * Parça Özeti: `14" NPS Buttwelding Cap Sch-STD`
   * Ağırlık: `Weight = 37.48 lb`
   * Teknik Çizim: Kep yüksekliği (`6.5"`), et kalınlığı (`0.375"`).
   * Parametre Seçiciler: Tip (`Buttwelding Cap`), `Size` (`14"`), `Sch` (`STD`).

---

## 4. Kapsamlı Veri Tabanı İndeksi (956 CSV Dosyası & 22,532 Satır)

| Kategori | Dosya Sayısı | Satır Sayısı | İlgili Standartlar | Kapsanan Sınıf / Schedule / Boyutlar |
| :--- | :---: | :---: | :--- | :--- |
| **Alın Kaynaklı Fittingler (Buttweld)** | 398 | 11,033 | ASME B16.9 | Sch 5 - Sch XXS (45/90 LR/SR Dirsekler, Teeler, Redüksiyonlar, Kepler, Stub End) |
| **Endüstriyel Vanalar (Valves)** | 139 | 2,841 | ASME B16.10, B16.34 | Sınıf 150 - 2500 (Gate, Globe, Ball, Check, Kelebek, Kontrol Vana) |
| **Dişli Fittingler (Threaded)** | 90 | 1,084 | ASME B16.11 | Sınıf 2000, 3000, 6000 (Dirsek, Te, Manşon, Kör Tapa, Bushing, Nipel) |
| **Standart Boru Flanşları** | 59 | 1,868 | ASME B16.5 | Sınıf 150 - 2500 (WN, SO, BLD, THD, SW, LJ, LWN - RF/FF/RTJ) |
| **Contalar ve RTJ Halkaları** | 56 | 1,426 | ASME B16.20, B16.21 | Düz Levha, Spiral Sarımlı (CG/CGI), Ring Joint (R, RX, BX) |
| **Hat Körlemeleri (Line Blanks)** | 55 | 1,304 | ASME B16.48 | Sınıf 150 - 2500 (Gözlük Körleme, Raket Körleme ve Halka) |
| **Soket Kaynaklı Fittingler (SW)** | 37 | 455 | ASME B16.11 | Sınıf 3000, 6000, 9000 (Dirsek, Te, Manşon, Rakor, Redüksiyon Parçası) |
| **Çelik Boru Boyutları & Ağırlıkları** | 36 | 978 | ASME B36.10M / 19M | NPS 1/8" - 80" tüm et kalınlıkları, su ağırlığı, atalet momenti |
| **Güçlendirilmiş Flanş Nozulları** | 24 | 215 | MSS-SP / Üretici | Weldoflange, Nipoflange, Latroflange, Elboflange |
| **Boru Nozulları (Branch Outlets - Olets)**| 12 | 208 | MSS-SP-97 | Weldolet, Sockolet, Thredolet, Elbolet, Latrolet |
| **Yüksek Basınç Kuyu Başı Flanşları** | 12 | 81 | API Spec 6A | Type 6B & 6BX (2,000 - 20,000 psi) |
| **Avrupa Standardı Flanşlar (DIN/EN)** | 12 | 311 | DIN EN 1092-1 | PN 2.5 - PN 400 |
| **Orifis Flanşları (Orifice Flanges)** | 10 | 219 | ASME B16.36 | Sınıf 300 - 2500 (Weld Neck, Slip-on, Threaded) |
| **Büyük Çaplı Flanşlar (B16.47 Series B)** | 6 | 60 | ASME B16.47 Series B | Sınıf 75 - 900 (NPS 26" - 60") |
| **Swage ve Boru Nipelleri** | 5 | 302 | BS 3799 / MSS-SP-95 | Konsantrik & Eksantrik Swage Nipeller |
| **Saplama & Ağır Somunlar (Bolting)** | 2 | 45 | ASME B18.2.1, B18.2.2 | UNC & Metrik saplama çapları, boyları, ağırlıkları ve delik toleransları |
| **Sistem / Boş Şablonlar** | 3 | 102 | Genel | Sistem şablonları |
| **TOPLAM** | **956** | **22,532** | **17 Küresel Standart** | **Eksiksiz Endüstri Veritabanı** |

---

## 5. Mühendislik Hesaplama Algoritmaları & Formülleri

### 5.1. Boru Güvenli Açıklık Hesabı (Pipe Safe Span)
Boru hatlarının sehimsiz ve kırılmadan taşınabileceği maksimum mesnet aralığı (`L`) iki kritik kriter üzerinden hesaplanır:
1. **Eğilme Gerilmesi Kriteri ($L_{stress}$):**
   * $\sigma_{max} = rac{M \cdot y}{I} \le \sigma_{allowable}$
   * $M = rac{w \cdot L^2}{10}$ (sürekli kiriş kabulü) veya $rac{w \cdot L^2}{8}$ (basit kiriş kabulü)
   * $L_{stress} = \sqrt{rac{10 \cdot \sigma_{allowable} \cdot I}{w \cdot r_{outer}}}$
   * Metrik Kabul: $\sigma_{allowable} = 22	ext{ MPa}$ (aşırı güvenli) - $68	ext{ MPa}$
   * İngiliz Birim Kabulü: $\sigma_{allowable} = 1.4	ext{ Tonf/in}^2 pprox 3,136	ext{ psi}$

2. **Sehim (Deflection) Kriteri ($L_{deflection}$):**
   * $\delta_{max} = rac{5 \cdot w \cdot L^4}{384 \cdot E \cdot I} \le \delta_{allowable}$
   * $L_{deflection} = \sqrt[4]{rac{384 \cdot E \cdot I \cdot \delta_{allowable}}{5 \cdot w}}$
   * Metrik Kabul: $\delta_{allowable} = 10	ext{ mm}$, $E = 203,390	ext{ MPa}$ ($T \le 400^\circ	ext{C}$)
   * İngiliz Birim Kabulü: $\delta_{allowable} = 0.375	ext{ inç}$ (3/8"), $E = 13,169	ext{ Tonf/in}^2$ ($T \le 750^\circ	ext{F}$)

3. **Birim Hat Yükü ($w$):**
   * $w = w_{pipe} + w_{fluid} + w_{insulation} + w_{wind}$
   * Dolu hat: Su yoğunluğu ($1000	ext{ kg/m}^3$) $	imes$ iç kesit alanı.
   * Yalıtım: Yalıtım yoğunluğu ($145 - 150	ext{ kg/m}^3$) $	imes$ yalıtım halkası alanı.
   * Rüzgar: $50	ext{ m/s}$ rüzgar hızı etkisi.
   * Korozyon Payı: Et kalınlığından $1.5	ext{ mm}$ (1/16") düşülür.
4. **Nihai Karar:** $L_{safe} = \min(L_{stress}, L_{deflection})$

### 5.2. Kesit Özellikleri (Section Properties)
* **Atalet Momenti:** $I = rac{\pi}{64} (OD^4 - ID^4)$
* **Mukavemet Momenti:** $Z = rac{2 I}{OD}$
* **Dönme Yarıçapı:** $r = rac{\sqrt{OD^2 + ID^2}}{4}$
* **Su Ağırlığı:** $W_{water} = rac{\pi \cdot ID^2}{4} \cdot ho_{water}$

---

## 6. Orijinal Uygulamanın Eksiklikleri ve Darboğazları
1. **Arama ve Hızlı Filtreleme Yok:** Kullanıcı onlarca açılır menü (dropdown) arasında kaybolmakta; serbest metinli arama ("14 inç STD dirsek" veya "300# WN 8 inç") bulunmamaktadır.
2. **Eski ve Statik UI:** Android 2.2 devrinden kalma B4A listview ve butonları, modern akıllı telefonların yüksek yenileme hızları ve jestlerine (swipe, pinch-to-zoom) ayak uyduramamaktadır.
3. **Kör Karşılaştırma:** İki farklı et kalınlığı (Sch 40 ile Sch 80) veya iki farklı basınç sınıfı (150# ile 300#) yan yana kıyaslanamamaktadır.
4. **Dışa Aktarma / Paylaşım Eksikliği:** Ölçüleri PDF şartname sayfası, CAD bloğu veya JSON/CSV olarak dışarı aktarma imkanı yoktur.
5. **Ağır ve Şişkin APK:** Kullanılmayan 4.1 MB'lık Windows masaüstü exe dosyası pakette unutulmuştur.
6. **Agresif Satış Baskısı:** Belli bir listeden sonra "Pipedata-Plus satın alın" uyarısı vererek kullanıcı akışını kesmektedir.

---

## 7. Modern Alternatif İçin Yol Haritası
1. **Normalize Edilmiş Veritabanı:** 956 CSV dosyasını tek bir yüksek performanslı SQLite / DuckDB veritabanında birleştirip FTS5 tam metin araması ekleme.
2. **Dinamik Parametrik SVG Motoru:** 122 SVG şablonunu kullanıcının seçtiği her boyuta göre dinamik ölçülendirilen vektörel bir render bileşeni haline getirme.
3. **Akıllı Karşılaştırma Modu:** İki boru veya flanşı seçip fark tablosu oluşturma.
4. **Gelişmiş Mühendislik Hesaplama Modülleri:** İnteraktif Safe Span, Loop hesaplayıcı, Cıvata sıkma torku (Bolt Torque).
