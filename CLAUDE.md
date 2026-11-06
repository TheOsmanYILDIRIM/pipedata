# Boru Hesaplayıcı — Geliştirme Sözleşmesi

## Kapsam
Bu depo, çevrimdışı NPS / DN / SCH boru ölçüleri ve nominal hesaplar için Android uygulamasıdır. Kapsam dışı ekranları ve doğrulanmamış veri varsayımlarını eklemeyin.

## Veri doğruluğu
- Nominal ölçü kayıtları `app/src/main/assets/pipe_schedules.csv` dosyasından okunur.
- Eksik, sıfır veya negatif et kalınlığı kullanıcıya gösterilmez; sentetik OD veya et kalınlığı oluşturulmaz.
- `PipeCatalog.kt`: ID = OD − 2t; nominal çelik yoğunluğu 7850 kg/m³, su yoğunluğu 1000 kg/m³.
- ASME B36.10 ve B36.19 kayıtları ile S-serisi etiketleri karıştırılmaz.
- Veri setinin eksiksiz veya lisanslı standartla tamamen doğrulanmış olduğu ileri sürülmez.
- Hesaplar nominaldir; üretim toleransları ve basınç tasarımı kapsam dışıdır.

## Arayüz
- Jetpack Compose, portre ekranlar ve büyük yazı desteği.
- Boru, SCH Bul, Tablo, Standart ve Ayarlar sayfaları.
- Görsel bileşenler ölçü değerlerini gizlememeli; ölçüler koddan üretilmelidir.

## Güvenlik ve teslim
1. Yazmadan önce güncel GitHub HEAD ve ilgili dosya blob SHA'sını doğrulayın; force/reset uygulamayın.
2. API anahtarı, parola, imzalama materyali veya diğer sırları repoya eklemeyin. Mevcut Android uygulamasının imza sürekliliğini koruyacak güvenli geçiş planı olmadan imza değişikliği yapmayın.
3. `python3 tools/verify_pipe_data.py` ile veri testlerini, GitHub Actions ile Android derlemesini doğrulayın.
4. CI ≠ gerçek cihaz testi; çeşitli ekran genişliği, büyük yazı ve dokunmatik etkileşim ayrıca doğrulanmalıdır.
5. `AGENTS.md`, `CLAUDE.md`, `GEMINI.md` daima aynı içerikte tutulur.
