# Boru Hesaplayıcı — Android

Yeni, yalnızca endüstriyel boru ustaları için geliştirilmiş çevrimdışı **NPS / DN / SCH** hesaplama uygulaması.

## Ekranlar

1. **Boru** — Döner NPS halkası, mevcut çapa uygun SCH seçenekleri, ölçüler, ağırlık ve iç hacim.
2. **SCH Bul** — Ölçülen OD ve et kalınlığından yakın nominal SCH kayıtlarını gösterir.
3. **Tablo** — Seçilen standarda/NPS'ye ait tüm geçerli nominal SCH satırları.
4. **Standart** — ASME B36.10M, B36.19M kapsamı ve hesap formülleri.
5. **Ayarlar** — Büyük metin, mm/kg/L veya in/lb/US gal birimleri.

Döner gösterge, kullanıcı tarafından sağlanan metal boru PNG'sinin şeffaf WebP optimizasyonunu kullanır (`app/src/main/res/drawable-nodpi/pipe_ring.webp`). Etkileşimli tıklama, döndürme ve veri katmanı Jetpack Compose ile yazılmıştır.

Tamamen **Jetpack Compose** ile yeniden yazılmıştır. Eski Pipedata flanş/vana/CAD/CSV çıkarıcı ekranları bu sürüme dahil edilmemiştir.

### Veri ve dürüst kapsam

Kaynak başlangıcı: Önceki uygulamanın nomimal boru ölçü tablosundan **sadece fiziksel olarak mümkün, dolu et kalınlığı olan** satırlar filtrelenerek hazırlanan yerel \`app/src/main/assets/pipe_schedules.csv\`. İlk veri setinde **373 kayıt** vardır; eksik/0 mm et kalınlıklı **101 kayıt görünmez**. Bu, eksiksiz resmî ASME lisanslı bir tablo veya tüm kombinasyonların doğrulanması değildir. Saha öncesi kritik boyutları güncel lisanslı ASME B36.10M/B36.19M veya üretici sertifikasıyla kontrol edin.

- OD ve t: mevcut nominal veri kaydından.
- ID = OD − 2t.
- Çelik kütle = π(OD² − ID²)/4 × 0,00785 kg/m (7850 kg/m³ nominal).
- İç hacim = πID²/4 / 1000 L/m.
- Su dolu toplam kütle = boş boru kütlesi + su içeriği (1000 kg/m³).
- Hesaplar nominal; üretim toleransı/korozif payı/basınç derecesi içermez.
- **SCH Bul**, toleranslı nominal ölçü eşleştirmesi yapar, basınç hesabı değildir.

## Derleme / test

\`\`\`bash
python3 tools/verify_pipe_data.py
./gradlew assembleDebug assembleRelease
\`\`\`

GitHub Actions hem veri doğrulamasını hem Gradle APK derlemesini koşar. Release imzası önceki sürümün mevcut \`app/antigravity.keystore\` dosyasıyla korunmuştur. Anahtar değiştirilmemiştir.

## Yedek

Eski uygulama ve 956 ham CSV kaynağı **\`backup/legacy-main-2026-11-05\`** dalında, \`06b7e2095c1e08d3654fc33f267b9aadf76d8344\` commit'inde korunur. Yeni \`main\` ağacı sıfırdan oluşturulur.

## Bekleyen doğrulama

Gerçek Android cihazda görsel, döner göstergenin dokunma ergonomisi, 320–411dp ekranlarda taşma ve \`fontScale=1.3\` kontrol edilmelidir. CI başarısı cihaz testi değildir.
