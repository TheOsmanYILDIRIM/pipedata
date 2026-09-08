# Pipedata Reverse Engineering & Mimari Notları

## 1. Orijinal Uygulama Bilgileri
* **Uygulama Adı:** Pipedata
* **Sürüm:** A-1.6.02 (Version Code: 1602)
* **Geliştirici Altyapısı:** Anywhere Software Basic4Android (B4A)
* **Paket Adı:** `b4a.example2`
* **Target SDK:** Android 34 (Android 14) / Minimum SDK: 5
* **Görsel/Teknik Çizim Motoru:** Dahili B4A 2D Canvas / Webview SVG renderer & DXF CAD dosyaları
* **Veri Depolama Mimarisi:** Doğrudan APK `assets/` içerisine gömülmüş düz metin CSV tabloları ve `.set` konfigürasyon dosyaları.
* **Gereksiz/Artık Veri Tespiti:** APK assets içerisinde 4.1 MB boyutunda eski bir Windows çalıştırılabilir dosyası (`pipedata-pro 15.0.exe`) ve Visual Basic / XAML artık dosyaları (`visibility.txt`, `list.txt`, `.tmp` dosyaları) tespit edilmiştir.

## 2. Çıkarılan Veri Varlıkları
* **956 CSV Dosyası:** 22,532 satır saf mühendislik verisi (Boru, Flanş, Fitting, Vana, Conta, Körleme, Nozul boyutları, ağırlıkları ve toleransları).
* **122 SVG Dosyası:** ASME standartlarına uygun 2D kesit ve montaj teknik resimleri.
* **214 DXF Dosyası:** AutoCAD uyumlu tam ölçekli 2D vektörel CAD çizimleri.
* **299 Görsel:** Menü ikonları, diyagramlar ve açıklama grafikleri.

## 3. Desteklenen Mühendislik Standartları
1. **ASME B16.5:** Pipe Flanges and Flanged Fittings (1/2" - 24", Sınıf 150-2500)
2. **ASME B16.47 Series A & B:** Large Diameter Steel Flanges (26" - 60", Sınıf 75-900)
3. **ASME B16.9:** Factory-Made Wrought Buttwelding Fittings (Tüm Sch serileri)
4. **ASME B16.11:** Forged Fittings, Socket-Welding and Threaded (Sınıf 2000, 3000, 6000, 9000)
5. **ASME B16.10 / B16.34:** Face-to-Face and End-to-End Dimensions of Valves
6. **ASME B16.20 & B16.21:** Metallic (Spiral-Wound, RTJ R/RX/BX) ve Non-metallic Flat Gaskets
7. **ASME B16.48:** Line Blanks (Figure-8 Blanks, Paddle Blanks & Spacers)
8. **ASME B36.10M / B36.19M:** Welded and Seamless Wrought Steel and Stainless Steel Pipe
9. **ASME B16.36:** Orifice Flanges (WN, SO, Threaded)
10. **API Spec 6A:** Wellhead and Christmas Tree Equipment (6B/6BX Flanges, 2000-20000 psi)
11. **DIN EN 1092-1:** European Standard Flanges (PN 2.5 - PN 400)
12. **MSS-SP-97 / MSS-SP-95:** Branch Outlets (Weldolet, Sockolet, Thredolet, Elbolet, Latrolet) ve Swage Nipples
13. **ASME B18.2.1 / B18.2.2:** Stud Bolts, Heavy Hex Nuts ve Delik Ölçüleri

## 4. Hesaplama Motorları & Algoritmaları
1. **Boru Güvenli Açıklık Hesabı (Pipe Safe Span):**
   * Çift kriterli değerlendirme: Sehim Sınırı (Deflection limit: 10 mm / 0.375") ve Eğilme Gerilmesi Sınırı (Allowable stress: 22 - 68 MPa / 1.4 Tonf/in²).
   * Kiriş formülleri: Basit mesnet ($L = \sqrt[4]{\frac{384 E I \delta}{5 w}}$) veya sürekli kiriş kabulleri.
   * Rüzgar yükü: 50 m/s rüzgar hızı etkisi.
   * Hat doluluğu: Boş (gaz/hava, 0 kg/m³) veya Su dolu (1000 kg/m³).
   * Yalıtım yükü hesabı: Yalıtım yoğunluğu (145-150 kg/m³), dış çap ve kalınlık üzerinden hacim/ağırlık entegrasyonu.
   * Korozyon payı düşümü: 1.5 mm (1/16").
2. **Atalet Momenti (Moment of Inertia):**
   * $I = \frac{\pi}{64} (OD^4 - ID^4)$
3. **Boru Esneklik & Genleşme U-Loop Hesabı:**
   * Isıl genleşme $\Delta L = L \cdot \alpha \cdot \Delta T$ ve bunu absorbe edecek loop bacak boyları.
4. **Birim Sistemleri ve Dönüşümler:**
   * Metrik (mm, kg, kg/m, MPa, °C)
   * U.S. Customary (inç ondalık, lbs, lb/ft, psi, Tonf/in², °F)
   * İnç Kesirleri (Örn: 0.375" -> 3/8", 14.25" -> 14 1/4")
   * NPS / DN Çift Yönlü Haritalama

## 5. Teknik Resim ve Vektör (SVG) Görsel Tasarım Notları

### 5.1 AutoCAD / ASME Y14.5M Standartları (Rastgele Oklar Yerine)
* **Ölçülendirme Ok Uçları (AutoCAD Closed Filled Arrowhead):**
  * Boy/En Oranı: $3:1$ (Uzunluk: 3 birim, Genişlik: 1 birim, Uç Açısı: $\approx 30^\circ$).
  * Standart SVG `<marker>` tanımı ile tüm çizimlerde yeknesak, kayıpsız vektörel oklar.
* **Ölçü ve Uzatma Çizgileri (Extension Lines - ASME Y14.5M):**
  * Parçadan başlama boşluğu: $1.5\text{ mm}$ ($1/16"$).
  * Ölçü çizgisini aşma mesafesi: $3.0\text{ mm}$ ($1/8"$).
* **Eksen Çizgileri (Centerlines):**
  * ASME standart deseni: Uzun çizgi ($12\text{px}$) - Boşluk ($4\text{px}$) - Kısa çizgi ($3\text{px}$) - Boşluk ($4\text{px}$).
* **Metal Kesit Taraması (Section Hatching):**
  * İlkel yüzlerce çizgi yerine standart ASME/ANSI 31 $45^\circ$ `<pattern>` deseni.
* **Ölçü Kapsülleri (Pill-Badges):**
  * `[[n]]` etiketleri ölçü çizgisini kesintiye uğratmaz; arkasındaki yarı saydam kontrast kutu sayesinde her zaman %100 okunabilir kalır.

### 5.2 Teknik Resim Temaları (Multi-Theme CAD Engine)
1. **Tema 1: AutoCAD Modern CAD Dark (Varsayılan):**
   * Arka Plan: `#0f172a` (Koyu Arduvaz), Gövde: `#f8fafc` (Beyaz), Ölçüler: `#38bdf8` (Cyan), Eksen: `#0284c7`, Tarama: `#334155`.
2. **Tema 2: Klasik Mühendislik Ozaliti (Classic Blueprint):**
   * Arka Plan: `#0b2545` (Ozalit Mavisi), Izgara: `#133b68`, Gövde: `#ffffff`, Ölçüler: `#93c5fd`, Tarama: `#1d4ed8`.
3. **Tema 3: Teknik Rapor & Kağıt Çıktısı (Engineering White / Print):**
   * Arka Plan: `#ffffff` (Kağıt Beyazı), Gövde: `#0f172a` (Koyu Grafit), Eksen: `#dc2626` (Teknik Kırmızı), Ölçüler: `#2563eb` (Mühendislik Mavisi).
4. **Tema 4: OLED Monospace Amber / Green (Retro CAD):**
   * Arka Plan: `#000000` (OLED Siyah), Gövde & Ölçüler: `#f59e0b` (Amber) veya `#22c55e` (Terminal Yeşili).

### 5.3 ASME B16.9 Buttweld Fittings Kesin Dosya Eşleştirmeleri (DEX Doğrulaması)
APK DEX string tablosu ve `_ftbw1` .. `_ftbw11` method dump analizi ile ortaya çıkarılan kesin harita:
* `pipe1.svg`: ASME B36.10M Boru Enkesiti (OD, ID, t, r)
* `ftbw1.svg`: ASME B16.9 Buttwelding 45° Dirsek (Elbow)
* `ftbw2.svg`: ASME B16.9 Buttwelding 90° Uzun Yarıçap Dirsek (90° LR Elbow)
* `ftbw3.svg`: ASME B16.9 Buttwelding 180° Uzun Yarıçap Dönüş (180° LR Return)
* `ftbw4.svg`: ASME B16.9 Buttwelding 90° Kısa Yarıçap Dirsek (90° SR Elbow)
* `ftbw5.svg`: ASME B16.9 Buttwelding 180° Kısa Yarıçap Dönüş (180° SR Return)
* `ftbw6.svg`: ASME B16.9 Buttwelding Eşit Te (Equal Tee)
* `ftbw7.svg`: ASME B16.9 Buttwelding İneçli Te (Reducing Tee)
* `ftbw8.svg`: ASME B16.9 Buttwelding Kep (Cap - Ekran Görüntüsü #1 ile %100 eşleşen parça)
* `ftbw9.svg`: ASME B16.9 Buttwelding Konsantrik Redüksiyon (Concentric Reducer)
* `ftbw10.svg`: ASME B16.9 Buttwelding Eksantrik Redüksiyon (Eccentric Reducer)
* `ftbw11.svg`: ASME B16.9 Buttwelding Fırdöndü Yaka (Lap Joint Stub End)

## 6. Kıdemli Mühendisler İçin 7 Radikal Kullanım ve Katman Mimarisi (Profesyonel Referanslar)

1. **Mimari 1: "The Industrial Keypad Terminal" (HP 48G / Fluke Multimetre Referansı)**
   * **Mantık:** Sıfır kaydırma (scroll), sıfır açılır menü (dropdown). Ekranın üst %40'ı canlı LCD ve teknik çizim göstergesidir; alt %60'ı ise asla yeri değişmeyen 4x4 sabit donanım tuş takımıdır. Kas hafızasıyla tek tıkta boyuta erişilir.
   * **Compose:** `Column { HUD(weight=0.4f), KeypadGrid(weight=0.6f) }`

2. **Mimari 2: "The Rotary Caliper Jog-Dial" (Leica DISTO / Dijital Kumpas Referansı)**
   * **Mantık:** Eldivenli veya titreyen eller için sağ kenara gömülü sonsuz dikey döner tambur (jog-dial). Başparmak tamburu kaydırdıkça kumpas açar gibi boru çapları tıkır tıkır büyür, çizim canlı ölçeklenir.
   * **Compose:** `Row { Viewport(weight=1f), RotaryWheel(width=88.dp) }`

3. **Mimari 3: "The Parametric Stepper Pipeline" (McMaster-Carr / Misumi Referansı)**
   * **Mantık:** Kullanıcıyı onlarca parametreyle boğmaz. Tek seferde tek karar: [Bileşen] -> [Çap] -> [Basınç] -> [Yüzey]. Tıklandığı an bir sonraki adıma otomatik akar. Sıfır bilişsel yük.
   * **Compose:** `StepperRibbon + AnimatedContent(transitionSpec)`

4. **Mimari 4: "The Avionics Line-Select Rocker" (Garmin Pilot MCDU / PLC HMI Referansı)**
   * **Mantık:** Havacılık kokpit mantığı. Ekranın solunda L1-L4, sağında R1-R4 donanım rocker butonları bulunur. Telefon iki elle tutulur; parmaklar çizimi asla kapatmadan kenarlardan kontrol eder.
   * **Compose:** `Row { LeftRockerBar(), CenterDisplay(weight=1f), RightRockerBar() }`

5. **Mimari 5: "The Direct Touch CAD Canvas" (Shapr3D / AutoCAD Mobile Referansı)**
   * **Mantık:** Görsel odaklı. Tablolar içinde ölçü aramak yerine doğrudan çizimdeki ölçü çizgisine veya etiketine dokunulur. Dokunulan yer parlar, alt kartta tolerans ve standart detayları açılır.
   * **Compose:** `Box { InteractiveSvgCanvas(), FloatingInspectorHUD() }`

6. **Mimari 6: "The Thumb-Indexed Dossier" (Machinery's Handbook Referansı)**
   * **Mantık:** 40 yıllık fiziksel kitap parmak oyukları (thumb tabs) ekranın sağ kenarına dizilmiştir. Menü aramadan tek tıkla standart fasikülleri (Boru, Flanş, Kep, Te, Vana...) arasında geçiş yapılır.
   * **Compose:** `Row { LedgerPage(weight=1f), VerticalIndexRail(width=62.dp) }`

7. **Mimari 7: "The Dual Comparator Split" (Beyond Compare / Saha Uyuşmazlık Dedektörü)**
   * **Mantık:** Sahada en sık yapılan "150# ile 300# flanş uyar mı?" hatasını çözer. Ekran ikiye bölünür; üstte Parça A, altta Parça B yer alır. Ortadaki canlı şerit cıvata ve ölçü farklarını uyarır.
   * **Compose:** `Column { SpecCardA(weight=1f), DiffBanner(), SpecCardB(weight=1f) }`


