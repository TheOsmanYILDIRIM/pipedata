# Boru Hesaplayıcı — Android

Çevrimdışı çalışan, endüstriyel boru ölçülerine yönelik NPS / DN / SCH hesaplayıcı.

## Özellikler

- **Boru:** NPS seçimi, dış çap, iç çap, et kalınlığı, ağırlık ve iç hacim.
- **SCH Bul:** Ölçülen dış çap ve et kalınlığına yakın nominal kayıtlar.
- **Tablo:** Standarda ve NPS'ye göre mevcut ölçüler.
- **Standart:** ASME B36.10M / B36.19M kapsam bilgisi ve nominal hesaplar.
- **Ayarlar:** Büyük metin ve metrik/imperial birim tercihleri.

Jetpack Compose ile geliştirilmiştir.

## Veri kapsamı

Uygulama, `app/src/main/assets/pipe_schedules.csv` dosyasındaki **inç cinsinden özgün nominal** OD ve et kalınlıklarını kullanır. SI ölçüleri için 1 inç = 25,4 mm dönüşümü hesap sırasında uygulanır, ara değerler yuvarlanmaz; ekranda boyutlar metrikte dört ondalık, inçte üç ondalık basamakla görüntülenir. Mevcut eski SI kayıtlarından inç kaynak değerleri tekil 0,001 in eşlemesiyle yeniden elde edilmiştir; bu işlem bağımsız standart doğrulaması sayılmaz. B36.10-2022'de doğrulanan NPS 1/8–3/8 eksikleri ayrıca eklenmiştir. Veriler resmî, eksiksiz veya lisanslı ASME tablosu olarak sunulmaz. Kritik saha ölçülerini güncel lisanslı standart ve üretici dokümanlarıyla kontrol edin.

- İç çap: `ID = OD − 2t`
- Nominal çelik yoğunluğu: 7850 kg/m³
- Nominal su yoğunluğu: 1000 kg/m³
- Basınç, tolerans ve korozyon payı hesaplanmaz.

## Derleme ve test

```bash
python3 tools/verify_pipe_data.py
./gradlew assembleRelease
```

GitHub Actions veri kontrolü ve release APK derlemesini çalıştırır. CI sonucu gerçek cihaz testi yerine geçmez.

## Güvenlik

Üretim imzalama anahtarları, parolalar ve diğer sırlar herkese açık kaynak kodunda tutulmamalıdır. İmzalama değişiklikleri mevcut uygulama güncellemeleriyle uyumluluk gözetilerek yönetilmelidir.

## Veri kaynağı ve doğrulama sınırı

- B36.10M-2022 için orijinal inç OD/et kalınlığı referansları: https://www.flangeboltchart.com/pipe/schedule/asme-b36-10m ve ASME B36.10M-2022 (lisanslı standart).
- B36.19-2026 yayımlanmıştır: https://webstore.ansi.org/standards/asme/asmeb36192026. 2026 sürümüne ait tam satır mutabakatı henüz tamamlanmamıştır; kayıtlar tamamen uyumlu veya eksiksiz olarak sunulmaz.
- `tools/verify_pipe_data.py` geometrik tutarlılık, hassas inç→mm dönüşümü ve bağımsız seçilmiş benchmark ölçülerini denetler. Kalan tüm kombinasyonlar için lisanslı standartla satır satır mutabakat ayrıca gereklidir.
