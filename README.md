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

Uygulama, `app/src/main/assets/pipe_schedules.csv` dosyasındaki nominal kayıtları kullanır. Veriler resmî, eksiksiz veya lisanslı ASME tablosu olarak sunulmaz. Kritik saha ölçülerini güncel lisanslı standart ve üretici dokümanlarıyla kontrol edin.

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
