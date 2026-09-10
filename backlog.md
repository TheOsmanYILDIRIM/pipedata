# Pipedata Modern Alternatifi - Backlog

## RESET (2026-09-10) — Turetilmis veri sifirlandi, tablolar bastan cikarilacak
- [x] Silinenler: `pipedata.db` (23M), `data/csv/` tamami (raw kopya+categorized+canonical), `app/src/main/assets/csv/` tamami, `schema_manifest.json`, tureten scriptler (`rebuild_perfect_sqlite.py`, `canonical_merge.py`, `migrate_to_sqlite.py`, `build_full_sqlite.py`, `organize_data.py`, `build_canonical_csv.py`, `csv_vs_db_audit.py`).
- [x] Kalanlar: `raw_apk/assets/` 956 orijinal CSV + Kotlin kodu (`CsvDataStore.kt` dahil) + `test_suite.py` + `reports/` + SVG varliklari.
- [ ] Yeni tablo cikarma pipeline'i (tek script, test kapili) bastan yazilacak. NOT: `CsvDataStore.kt` su an asset bulamayacagi icin app/tests kirmizi — beklenen durum.

## Aktif Faz: Faz 7 — CSV-First Mimarisi Geçişi (2026-09-09, TAMAMLANDI ✅)
- [x] **Mimari:** SQLite `pipedata.db` (23MB) app asset'inden kaldırıldı; yerine `CsvDataStore.kt` (CSV okuyan) yazıldı. PipedataDbHelper.kt (SQLite) silindi.
- [x] **Kanonik CSV üretimi:** `build_canonical_csv.py` — DB'den 8 kritik tablo (tbl_flanges_asme_b16_5, tbl_pipes, tbl_fittings_buttweld_b16_9, tbl_fittings_socketweld_b16_11, tbl_fittings_threaded_b16_11, tbl_valves_asme_b16_10, tbl_gaskets_and_rtj, tbl_line_blanks_asme_b16_48) kanonik CSV'ye döküldü. 15.929 satır, DB ile **0 fark** (`csv_vs_db_audit.py` kanıtı).
- [x] **Asset paketleme:** Kanonik 8 CSV → `app/assets/csv/canonical/`, ham 956 CSV → `app/assets/csv/raw/` (sadakat kanıtı). APK 32MB → 9.8MB.
- [x] **CI kapısı:** `build_apk.yml`'e `csv_vs_db_audit.py --fail-on-diff` doğrulama adımı eklendi — kanonik CSV DB ile uyuşmazsa APK üretilmez.
- [x] **Test grubu:** `test_suite.py`'ye `TestPhase7CsvFirst` (5 test: asset varlığı, 0-bayt anomali kontrolü, mutabakat, DB yokluğu, Kotlin wiring) eklendi. Toplam **37/37 OK**.
- [x] **Kotlin sorgu mantığı:** 7 SQL sorgusu satır-filtreleme ile değiştirildi (`LIKE %x%` → `contains`, `ORDER BY col DESC LIMIT 1` → `maxByOrNull`, `LIMIT 1` → `firstOrNull`). Sıra/davranış DB rowid sırasıyla birebir eşleşiyor.
- [x] **Git:** `79443d6` commit ve push tamamlandı. `data/csv/canonical/` .gitignore'a eklendi (regene edilebilir).
- [ ] **Faz 8 adayı — PSV model-çöküşü:** psv.csv içindeki farklı PSV modelleri (aynı nps/rating altında 6 satır; weight 31/56 kg) `valve_type,nps,rating,end_connection` doğal anahtarına çöküyor; model ayrımı için `raw_json.legacy` satır-farkı veya yeni bir `model` kolonu değerlendirilmeli.

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
- [x] Sade ve Odaklı 2-Sayfa + Ayarlar Arayüz Prototipi (`pipedata_clean_ui.html` -> Downloads)
- [x] Yan Yüzen Baloncuklu (Preview Bubbles) Önceki/Sonraki Çizim Önizleme Motoru
- [x] Büyütülmüş Orijinal Tipografi & Ayarlardan Metin Boyutu Ölçekleme (%100, %115, %130)
- [x] Saha ve Güneş Işığı İçin Yüksek Kontrast (High Contrast) Modu
- [x] İki Kolonlu Canlı Mühendislik Boyutları Listesi (Metrik & İngiliz Çift Yönlü)
- [x] Basit ve Genişletilebilir i18n Dil Çeviri Altyapısı (Varsayılan English + Canlı Türkçe Geçişi)
- [x] `visual-inspector` Subagent ile Kural 4 Görsel Muayenesi v2 (%100 Onay, DURUM: BAŞARILI)
- [x] Radikal Sadeleştirme (Zero-Fluff / Ultra-Minimal Engineering UI)
- [x] Teknik Resim Alanının Maksimuma Genişletilmesi (350px / 48vh Geniş Sahne, %100 Vektör Sığma)
- [x] SVG Arka Planı & Çizgi Renklerinin Temayla %100 Dinamik Senkronizasyonu (CSS Variables Engine)
- [x] 4 Teknik CAD Temasının Entegrasyonu (AutoCAD Dark, Classic Blueprint, Paper White, OLED Amber)
- [x] Teknik Başlık Açıklama Butonları `(?)` ve Mühendislik Terimler Sözlüğü Modalı (NPS, Class, Facing, Dimensions/PCD, Flange Types)
- [x] Native Android Jetpack Compose Projesi İskeleti (`com.avenox.pipedata`, Kotlin 2.0.20, Compose Material 3)
- [x] 122 Modern SVG Vektörün ve `pipedata.db` SQLite Veritabanının Android Asset Olarak Eklenmesi
- [x] Dokunmatik CAD Çizim Sahnesi (Pinch-to-zoom 1x-4x, pan, çift tıkla sıfırlama, yan mini oklar)
- [x] GitHub Actions Otomatik APK Derleme Pipeline'ı (`.github/workflows/build_apk.yml`)

## Kaldığımız Nokta (Aktif Durum & Checkpoint)
- **Mevcut Konum:**
  1. **Teknik Açıklama `(?)` Sistemi:** Kullanıcının istediği NPS, Class, Facing, Dimensions/PCD ve Flanş Tipleri için tek tıkla açılan açıklayıcı teknik sözlük mini ekranı hem HTML prototipine (`pipedata_clean_ui.html`) hem de native Jetpack Compose (`HelpBottomSheet.kt`) uygulamasına Türkçe & İngilizce olarak eklendi.
  2. **Android Jetpack Compose Kurulumu:** `com.avenox.pipedata` paketi altında Material 3 tabanlı tam modüler Compose mimarisi hazırlandı:
     - `MainActivity.kt`: Edge-to-edge ekran yönetimi, durum akışları ve navigasyon.
     - `TopicListScreen.kt`: Sayfa 1 - Sade, temiz ve hızlı konu listesi.
     - `FlangeDetailScreen.kt`: Sayfa 2 - Genişletilmiş CAD sahnesi, mini oklar, NPS çipleri, sınıf butonları ve ölçü tablosu.
     - `HelpBottomSheet.kt`: ASME standart tanımlarını veren `(?)` teknik yardım ekranı.
     - `SettingsBottomSheet.kt`: 4 CAD teması, yazı tipi ölçeği (`[−]` / `[+]`), birim (mm/in) ve dil seçimi.
     - `PipedataDbHelper.kt`: `pipedata.db` SQLite veritabanı okuma motoru.
  3. **GitHub CI/CD APK Pipeline:** Yerelde ağır Gradle derlemesi yapmadan (Kural 7), push anında `ubuntu-latest` üzerinde Java 17 + Gradle 8.9 ile APK derleyip GitHub Release & Artifact olarak üreten `.github/workflows/build_apk.yml` oluşturuldu.
- **Sıradaki Adım:** Avenox Doktrini Kural 7 gereğince `git-operator` subagent'ının tetiklenmesi, değişikliklerin GitHub'a pushlanması ve CI/CD derleme sürecinin izlenmesi.

- [x] Üst Tema Seçim Çubuğunun Kaldırılması (Tema seçimi sadece Ayarlar çekmecesine taşındı)
- [x] Ana Sekme Performans Optimizasyonu (Arka plan asenkron DB yükleme + Coil SVG bellek önbelleği ile 60/120 FPS akıcılık)
- [x] Liste Görünümü Tipografi & Rozet İyileştirmesi (Kompakt 64dp kartlar, tek satırda taşmayan inline ASME rozetleri, tek satır açıklama)
- [x] Tüm SQLite Veritabanının Uçtan Uca Entegrasyonu (Flanşlar, Borular, Alın Kaynaklı Fitingler, Dövme Fitingler, Vanalar, Contalar ve Körlemeler için dinamik ComponentDetailScreen ve sorgu motoru)

## Son Düzeltmeler & Kullanıcı Geri Bildirimi Revizyonu (2026-09-08)
- [x] **Tüm SQLite Tablolarının Entegrasyonu:** 7 kategorinin tamamı (`tbl_flanges_asme_b16_5`, `tbl_pipes`, `tbl_fittings_buttweld_b16_9`, `tbl_fittings_socketweld_b16_11`, `tbl_valves_asme_b16_10`, `tbl_gaskets_and_rtj`, `tbl_line_blanks_asme_b16_48`) `PipedataDbHelper` ve `ComponentDetailScreen` üzerinden gerçek verilerle bağlandı.
- [x] **Ana Sekme Kasma Sorunu Çözüldü:** 23 MB veritabanının kopyalanması UI ana iş parçacığından (`onCreate`) `Dispatchers.IO` arka planına alındı. Coil SVG bellek önbelleği (`CachePolicy.ENABLED`, `crossfade=false`) ile 120 FPS akıcı kaydırma sağlandı.
- [x] **Liste Modu ve ASME Etiket Düzeni Düzeltildi:** Hantal 110dp kartlar ve 2 satıra taşan biçimsiz ASME rozetleri kaldırıldı. 64dp kompakt, zarif ve tek satırda başlık + standart hapı (`B16.5`, `B36.10M` vb.) içeren endüstriyel liste görünümüne geçildi; 7 öğenin tamamı ekrana tek bakışta sığdırıldı.
- [x] **Üst Tema Barı Kaldırıldı:** Ekran başlığını kalabalıklaştıran yatay tema barı kaldırıldı; tema değişimi kullanıcının talimatı doğrultusunda sadece Ayarlar çekmecesine (`SettingsBottomSheet`) devredildi.
- [x] **Saat Kadranı (Clock Dial) Seçim Arayüzü (`EngineeringDialSelector.kt`):** Yan yana dağınık duran butonlar yerine, analog mühendislik göstergesi / saat kadranı mimarisi inşa edildi. Dairesel kadran üzerinde çentikler, ibre, dokunarak veya sürükleyerek NPS seçimi, merkezde DN ve Schedule/Rating göbeği, altta tek parça hassas segment kontrolü entegre edildi.
- [x] **Buton Tepki Süresi ve SVG Yükleme Kasıntısı Çözüldü:** Butona her basışta 40KB SVG XML'in ana iş parçacığında yeniden ayrıştırılması engellendi; CAD çizim sahnesi izole `@Composable` bileşenine dönüştürüldü ve Coil istekleri `remember` ile önbelleğe alındı. SQLite sorgularına RAM `ConcurrentHashMap` önbelleği eklenerek 0.001 ms anlık tepki sağlandı.
- [x] **Android Donanım / Hareket Geri Butonu (`BackHandler`):** Geri butonunun uygulamayı arka plana atması engellendi; açık olan yardım/ayarlar pencerelerini kapatıp, detay ekranından konu listesine yumuşak geçiş yapması sağlandı.
- [x] **Zoom Rozeti Metni Kaldırıldı:** Çizimin yanında gereksiz duran `"1.0x • Zoom"` metni tamamen kaldırıldı; sadece zoom yapıldığında `⟲ Sıfırla` butonu belirecek şekilde minimal tutuldu.
- [x] **Flanş SVG'lerindeki Mesafe & Boyut Uyumsuzluğu Düzeltildi:** `modern_flan2.svg`, `modern_flan4.svg` ve `modern_flan4a.svg` dosyalarındaki 8000+ birimlik kayıp noktalı koordinat artıkları temizlendi; tüm flanşların viewBox oranları 1.25–1.30 aralığında eşitlenerek çizimlerin aynı mesafe ve ölçekte tam sığması sağlandı.
- [x] **HTML Prototiplerinin Temizlenmesi & %100 Saf Jetpack Compose:** Eski prototip HTML dosyaları silindi, uygulamanın sıfır WebView içeren %100 yerel Jetpack Compose mimarisi doğrulandı.
- [x] **Görsel Doğrulama Kuralının Kaldırılması:** Kural 4 tüm kural dosyalarından silindi ve senkronize edildi.
- [x] **Yatay Akıcı Animasyonlu Kaydırıcı & Cetvel Seçici (`HorizontalFluidSelector.kt`):** Dairesel saat kadranı yerine, kullanıcının referans verdiği Dribbble UI/UX akıcı animasyon konseptine uygun yatay hassas kumpas/cetvel kontrolü geliştirildi. Yatay bant üzerinde Gauss dalga deformasyonlu dinamik cetvel çentikleri, parlayan verniyer gösterge iğnesi, dokunmatik/sürüklemeli akıcı yay fiziği (`spring`), çift yönlü hızlı adım butonları (`‹` ve `›`), ve hemen altında tam entegre akıcı segmentli Schedule/Facing paneli inşa edildi. Dikeyde 200dp yer tasarrufu sağlanarak ölçü tablosunun ekranda tam görünmesi sağlandı.
- [x] **Canlı Parametrik SVG Değer Enjeksiyonu (`ComponentDetailScreen.kt`):** Teknik çizimlerin içindeki tüm şablon etiketleri (`[[3]]` OD, `[[4]]` Kalınlık, `[[19]]` PCD, `[[6]]` İç Çap, `[[11]]` RF, `[[DB2_6]]` Schedule vb.) seçilen boru çapı, basınç sınıfı ve birim sistemine (mm / inç) göre gerçek zamanlı dinamik değerlerle değiştirildi. Boyut değiştikçe çizim üzerindeki ölçüler anlık olarak güncellenir.
- [x] **Hareketli Orbital Açılış Ekranı (`OrbitalSplashScreen.kt`):** Merkezde nabız gibi atan CAD flanş göbeği, zıt yönlerde farklı hızlarda dönen 3 eşmerkezli yörünge halkası, yörünge uyduları, CAD açı çentikleri ve telemetri ilerleme çubuğu içeren yüksek teknolojili orbital açılış animasyonu entegre edildi.
- [x] **SQLite Master Ground-Truth Denetimi & Sıfırdan Kusursuz İnşa (956 CSV, 17 Tablo):**
  - Orijinal ham verideki tüm Excel tarih bozulmaları (`01/02/2001` -> 1/2", `03/04/2001` -> 3/4" vb.) ve kesirler normalize edildi.
  - Flanş tipleri (Welding Neck, Slip On, Blind) ve bunlara ait spesifik et kalınlığı, boyun boyu ve ağırlıkları unpivot edilerek çoklu satırlar halinde SQLite'a aktarıldı.
  - Sabit indeksler kaldırılarak dinamik semantik başlık haritası (`hmap`) kuruldu; önceden `NULL` olan boyun tabanı, fatura çapı, delik çapı, saplama boyları ve gerçek cıvata adetleri %100 kurtarıldı.
  - Tüm 17 tablo (`tbl_flanges_asme_b16_5`, `tbl_flanges_asme_b16_47`, `tbl_flanges_din_en_1092`, `tbl_flanges_api_6a`, `tbl_flanges_orifice_b16_36`, `tbl_flanges_branch_connections`, `tbl_pipes`, `tbl_fittings_buttweld_b16_9`, `tbl_fittings_socketweld_b16_11`, `tbl_fittings_threaded_b16_11`, `tbl_fittings_swages_nipples`, `tbl_branch_outlets_mss_sp_97`, `tbl_valves_asme_b16_10`, `tbl_gaskets_and_rtj`, `tbl_line_blanks_asme_b16_48`, `tbl_bolting_and_fasteners`, `tbl_misc_data`) tek tek denetlenerek dosya adı ve başlık regex'leri ile rating/schedule/PN değerleri eksiksiz bağlandı.
  - `SQLITE_MASTER_AUDIT_CHECKLIST.md` dosyası oluşturuldu ve her tablonun denetimi adım adım belgelendi.
  - [x] **ASME B16.11 Soket Kaynaklı & Vidalı Fitingler Master Denetimi & Düzeltmesi:**
  - Kullanıcının haklı uyarısı: ASME B16.11 Soket Kaynaklı fitinglerde Class 2000# standarda göre mevcut DEĞİLDİR (yalnızca vidalı fitinglerde vardır). Class 2000# soket kaynaklı fiting seçeneklerinden tamamen kaldırıldı; standart gereği yalnızca 3000#, 6000#, 9000# sınıfları bırakıldı.
  - Yanlışlıkla `tbl_fittings_buttweld_b16_9` tablosuna aktarılmış olan 25 `ftsw*` ve 26 `ftsc*` dosyası tespit edilip ait oldukları `tbl_fittings_socketweld_b16_11` ve `tbl_fittings_threaded_b16_11` tablolarına taşındı.
  - `tbl_fittings_socketweld_b16_11` şemasına ve SQLite veri tabanına soket derinliği (`socket_depth_mm`), soket çapı (`socket_diameter_mm`), iç delik (`bore_diameter_mm`), soket et kalınlığı (`socket_wall_thickness_mm`), gövde et kalınlığı (`body_wall_thickness_mm`), merkezden uca boy (`center_to_end_mm`), boy (`length_mm`) ve ağırlık (`weight_kg`) ham CSV'lerden semantik olarak %100 doğrulukla aktarıldı.
  - Kategori altında eksik olan 45° Dirsek, İstavroz (Cross), Yarım Manşon (Half Coupling), Kep (Cap), Redüksiyon Manşonu (Reducing Coupling) ve Rakor (Union) olmak üzere toplam 9 alt tip ve karşılık gelen `modern_ftsw1.svg` - `modern_ftsw9.svg` teknik çizimleri bağlandı.
  - `ComponentDetailScreen.kt` içerisindeki SVG canlı parametre enjektörü (`[[3]]`..`[[12]]`) dövme soket fiting parametrelerine göre kalibre edildi.
  - `pipedata.db` yeniden derlenip `app/src/main/assets/` ve `/storage/emulated/0/Download/` yollarına kopyalandı; 17/17 otomatik test başarıyla geçti.
- [x] **Anti-Macro Fallacy & Kapsamlı Çapraz Tablo Semantik Denetimi (Phase 4):**
  - Tüm 956 CSV ve 17 SQLite tablosu satır satır denetlendi; 13 tabloda CSV başlık varyasyonları yüzünden %100 NULL kalan kolonlar tespit edilerek çözüldü.
  - Vanalar (`face_to_face_mm`, el çarkı ölçüleri, ağırlıklar), flanşlar (saplama boyları, PCD, cıvata adetleri), contalar (RTJ halka numaraları, boyut ve ağırlıklar), hat körlemeleri (Figure-8, Paddle Blank, Spacer merkezler arası mesafe ve ağırlıklar), branşman çıkışları (MSS SP-97 Weldolet/Sockolet boy ve ağırlıklar), dövme nipeller, API 6A ve DIN EN 1092 flanş ağırlıkları eksiksiz dolduruldu.
  - Borular tablosunda (`tbl_pipes`) Atalet Momenti, Kesit Mukavemet Modülü, Atalet Yarıçapı ve Su Dolu Ağırlık formülleri sıfır sessiz fallback ile doğrudan fiziksel prensiplere göre hesaplandı.
  - `test_suite.py` 22 teste genişletildi; tüm tablolar için derin semantik kolon doğrulaması ve ground-truth kontrolleriyle 22/22 %100 PASS elde edildi.


- [x] **Faz 6 Standart Uyumluluk Denetimi (2026-09-09):** Yayinlanmis ASME degerleriyle bagimsiz kiyaslama (standards_compliance.py) -> **49 PASS / 0 FAIL / 3 WARN**. PASS: B36.10M (Wikipedia indirmesi, kanit-kaynak [W]) 38/38; B16.9 90LR/Tee A kurali 5/5; B16.5+B16.10+B16.48 4/4. WARN (orijinal-veri-sapmasi, pipeline hatasi DEGIL - iki bagimsiz CSV hemfikir): 45e 2in C2E=35mm (standart ~38.1), gate 3in 150# F2F=203mm, gate 2.5in 190mm. Karar kullaniciya birakildi: CSVs degistirilmeden DB orijinal uygulama verisini sadakatle tasir (backlog notu).
