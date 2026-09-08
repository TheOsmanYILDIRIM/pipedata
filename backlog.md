# Pipedata Modern Alternatifi - Backlog

## Tamamlanan Fazlar
- [x] APK Varlıklarının Tersine Mühendislikle Çıkarılması (956 CSV, 122 SVG, 214 DXF)
- [x] Telif Hakkı ve Standardizasyon Denetimi (Telif riski %0 izolasyon)
- [x] SQLite Master Schema Manifest (`schema_manifest.json`)
- [x] Dondurulmuş Test Şartnamesi (`test.md` & `test_suite.py`)
- [x] SQLite3 Veritabanı İnşası (`pipedata.db` - 17 Tablo, 956 CSV, 22,525 Satır, 122 SVG)
- [x] Canlı SQLite WebAssembly HTML Test Konsolu (`pipedata_tester.html` -> Downloads)
- [x] 17/17 Otomatik Test Doğrulaması (`test_suite.py` %100 PASS)
- [x] Görsel Muayene ve Doğrulama (`view_file` ile `sqlite_studio_preview.png`)
- [x] 122 SVG Teknik Çizim Master Manifest (`svg_manifest.json` - 184 KB)
- [x] Orijinal APK SVG Tersine Mühendislik & DEX Doğrulaması (11 Buttweld parça kesin haritası)
- [x] Orijinal vs Modern AutoCAD 5 Örnek Yan Yana Kıyaslama Doğrulaması (`svg_real_comparison.png`)
- [x] 122 SVG Dosyasının Tamamının AutoCAD / ASME Standartlarına Dönüştürülmesi (122/122 %100 QC PASS, Sıfır Çizgi Kaybı)
- [x] Modern SVG Vitrin Panosu Oluşturulması (`modern_svg_showcase.png` -> Downloads)
- [x] SQLite `tbl_svg_templates` tablosuna `modern_svg_content` entegrasyonu
- [x] Canlı Test Konsolu Güncellemesi (`pipedata_tester.html` -> Downloads)
- [x] ASME B16.25 Kaynak Ağzı Pahı ve Enkesit Geometri Doğrulaması (Sıfır Çizgi Kaybı AST Pipeline)
- [x] Orijinal vs Yeni Yüksek Çözünürlüklü 12 Parça Karşılaştırma Kartları (2400x1200 PNG'ler -> `/storage/emulated/0/Download/comparisons/`)
- [x] Master Buttweld Katalog Panosu (`all_buttweld_comparison_master.png` -> Downloads)
- [x] Master Flanşlar Karşılaştırma Panosu (14 Parça -> `all_flanges_comparison_master.png` -> Downloads)
- [x] Master Contalar & Körlemeler Karşılaştırma Panosu (15 Parça -> `all_gaskets_bolting_master.png` -> Downloads)
- [x] Master Endüstriyel Vanalar Karşılaştırma Panosu (12 Parça -> `all_valves_comparison_master.png` -> Downloads)
- [x] Master Dövme Çelik Ek Parçaları Panosu (12 Parça -> `all_forged_fittings_master.png` -> Downloads)
- [x] 122/122 Modern SVG'nin rsvg-convert & XML Ayrıştırma ile %0 Hata Doğrulaması
- [x] 10 Kıdemli Mühendis Görsel Arayüz Prototipi (`pipedata_ui_prototypes.html` -> Downloads)
- [x] 10 Arayüz Prototipi Karşılaştırma Panoları (`ui_prototypes_board_1_to_5.png` & `ui_prototypes_board_6_to_10.png` -> Downloads)
- [x] Profesyonel Endüstriyel Uygulamalar ve Donanım Araçları UX Araştırması (HP 48G, Fluke, Leica, McMaster-Carr, Garmin Cockpit, Shapr3D, Machinery's Handbook, Beyond Compare)
- [x] 7 Radikal Kullanım & Katman Mimarisi Prototipi (`pipedata_interaction_paradigms.html` -> Downloads)
- [x] 7 Mimarinin Tamamının Headless Chromium ile Ekran Görüntülerinin Alınması ve `view_file` ile Görsel Muayenesi (Kural 4 %100 Sağlandı)
- [x] 7 Mimari Karşılaştırma Master Panosu (`paradigms_master_board_7.png` -> Downloads)
- [x] GitHub Reposu Oluşturuldu ve Komple Proje Pushlandı (https://github.com/TheOsmanYILDIRIM/pipedata)

## Kaldığımız Nokta (Aktif Durum & Checkpoint)
- **Mevcut Konum:** Renk/tema odaklı yaklaşımdan tamamen ayrılıp, gerçek profesyonel mühendislik araçlarını baz alan **7 farklı kullanım/etkileşim ve katman dizilim mimarisi** eksiksiz inşa edildi.
- **İncelenebilir Varlıklar:**
  * İnteraktif HTML: `/storage/emulated/0/Download/pipedata_interaction_paradigms.html`
  * 7 Mimari Karşılaştırma Görseli: `/storage/emulated/0/Download/paradigms_master_board_7.png`
- **Karar Bekleyen Konu:** Kullanıcının bu 7 model arasından ana etkileşim mantığını (veya hibrit bir kombinasyonu, örn: Donanım Tuş Takımı + Çizim Üstü Canlı Dokunma) seçmesi.

## Yapılması Gerekenler (Sıradaki Adımlar)
1. **Ana Kullanım Mimarisi Seçimi:** 7 modelden hangisinin (veya hangi hibrit yapının) temel alınacağının kullanıcı ile netleştirilmesi.
2. **Ekran Sayısı ve Bilgi Mimarisi (Screen Hierarchy):**
   - Uygulamada kaç ana ekran/sekme olacağı (Örn: 1. Katalog / Boyut Bulucu, 2. Flanş & Montaj Hub'ı, 3. Boru Hesaplama & Span Motoru, 4. Karşılaştırıcı / Saha Kontrol).
   - Her ekranda yer alacak katmanlar, geçiş mantıkları ve durum yönetimi (UI State).
3. **Jetpack Compose Katman Mimarisi Tasarımı:**
   - Seçilen mimariye uygun Compose bileşenleri (Scaffold, Custom WheelPicker, Rocker Layout, Canvas Touch-Overlay).
   - `pipedata.db` SQLite veritabanı ile Room / SqlDelight / Native SQLite bağlantı katmanı.
   - Parametrik SVG render motorunun Compose Canvas / AndroidView katmanına taşınması.
4. **GitHub CI/CD & Build Pipeline (Kural 7):**
   - Asla yerelde ağır derleme yapmadan, GitHub Actions üzerinden test ve APK üretim iş akışının kurulması.

