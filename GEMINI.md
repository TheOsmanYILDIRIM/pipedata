# Boru Hesaplayıcı — Geliştirme Sözleşmesi

## Güncel kapsam (2026-11-05)
Bu repo artık endüstriyel boru montaj ustalarının kullanacağı, çevrimdışı, dikey **NPS/DN/SCH** hesaplayıcı Android uygulamasıdır. Önceki Pipedata mühendislik/CAD/kapsamlı fitting sürümü silinmemiştir: `backup/legacy-main-2026-11-05` dalında `06b7e209` commit'i ile korunmuştur. Eski sayfaları, çıkarıcıları ve sahte fallback alanlarını geri taşımayın.

## Tek kaynak ve doğruluk
- Nominal boru kayıtları yalnız `app/src/main/assets/pipe_schedules.csv` kaynağında tutulur. Her satırın standardı, NPS, DN, OD, SCH ve et kalınlığı bulunur.
- Herhangi bir et kalınlığı 0, negatif veya eksikse kullanıcıya ilgili SCH gösterilmez. Kodla keyfî et kalınlığı/OD üretmeyin.
- Hesaplar `PipeCatalog.kt` içinde: ID=OD−2t; çelik kütlesi nominal 7850 kg/m³; su içeriği nominal 1000 kg/m³. Basınç tasarımı ayrı iş kapsamıdır.
- B36.10 ve B36.19 tablolarının S serilerini karıştırmayın. Veri kapsamını lisanslı standartlara karşı doğrulanmış sanmayın; ilk aşamada 373 filtrelenmiş nominal satır vardır.
- Değerler fiziksel/nominal hesaplar, gerçek tolerans/sertifika değildir. Kullanıcıyı yanıltabilecek sessiz fallback yasaktır.

## Mobil arayüz
- Jetpack Compose; 320–411dp portre ekran ve büyük yazı için tasarım.
- Koyu teal/altın tasarım, döndürülebilir NPS göstergesi, kullanıcının sağladığı şeffaf metal halka (`drawable-nodpi/pipe_ring.webp`), büyük tıklama hedefleri.
- Sayfalar: Boru, SCH Bul, Tablo, Standart, Ayarlar. Asıl boyut yazıları Compose tarafından üretilir; fotoğraf üstüne yazı basmayın.
- Mevcut Android `applicationId` ve `antigravity.keystore` korunur; cihazdaki uygulama güncellemeleri için imza değiştirilmez.

## Kabul ve değişiklik güvenliği
1. Önce GitHub HEAD + ilgili dosya blob SHA'sını inceleyin; ana dal/yedeklere kör reset veya force yazmayın.
2. `python3 tools/verify_pipe_data.py` tüm kayıtları ve kontrol örneklerini doğrulamalı.
3. GitHub Actions `assembleDebug assembleRelease` ve APK artefaktı olumlu sonuçlanmalı.
4. CI ≠ gerçek cihaz testi. Dokunmatik halka, 320/360/411dp görünümü ve fontScale testi gerçek cihazda ayrıca doğrulanmalı.
5. `AGENTS.md`, `CLAUDE.md`, `GEMINI.md` değiştiğinde üçü birebir senkron tutulur.
