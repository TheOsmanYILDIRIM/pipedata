# Boruzi Android görsel ve etkileşim denetimi

**Durum:** CI başlatıldı; emülatör çıktısı ve görsel kusurlar henüz doğrulanmadı (`pending_unverified`).  
**Tarih:** 2026-11-06  
**Kaynak:** `TheOsmanYILDIRIM/pipedata`  
**Başlangıç main HEAD:** `d435597615dbdba8bc341d9976ba0745d2503079`  
**İzole dal:** `audit/boruzi-android-screenshots-20261106`  
**Test iş akışı:** [ui-screenshots.yml](../.github/workflows/ui-screenshots.yml)  
**Güncel Actions koşusu:** [42188972565](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42188972565) (`2a1a429399cb1b9a80b85b6e5cccab2bf6e26c6a`)

## Sınırlar ve doğrulanmış kaynaklar

- `AGENTS.md`, `README.md`, `.github/workflows/build_apk.yml` incelendi; mevcut Release APK iş akışı değiştirilmedi.
- `SESSION_HANDOFF.md` bu commit'te mevcut değil.
- Gerçek kaynakta dört alt gezinti öğesi var: **Boru**, **SCH Bul**, **Tablo/Standart**, **Ayarlar**. Tablo ve Standart aynı üçüncü ekranın birleşik içeriği.
- Bu raporda gerçek Android PNG'si incelenmeden hiçbir kırpılma, taşma, hizalama veya işlev hatası **kanıtlanmış görsel kusur** sayılmıyor.
- Avenox mevcut görev: `tasks/pipedata-boru-only-rebuild-20261105.md` (görsel/cihaz doğrulaması açık). Bu rapor görev kaydı yerine geçmez.

## Emülatör test matrisi

| Parametre | Denenecek değerler |
| --- | --- |
| Ekran | 320, 360, 411 dp; 160 dpi sanal yoğunluk |
| Yazı ölçeği | 1.0, 1.3 |
| Birim | inç (varsayılan), mm (Ayarlar anahtarı) |
| Ekranlar | Boru, SCH Bul, Tablo/Standart, Ayarlar |
| Etkileşim | Alt gezinme, sayısal alan diyaloğu, SCH sorgusu, klavye, kaydırma |
| Artefakt | Android `screencap -p` gerçek PNG, `uiautomator dump` XML, `results.json`, logcat |

İş akışı tek emülatörde **12 konfigürasyon** dener; her konfigürasyonda sayfalar ve erişilebilirlik ağacı kaydedilir. `results.json` herhangi bir başarısız durumu açıkça listeleyecek şekilde yazılmıştır. İş akışının tanımlanması emülatörün başarıyla çalıştığını kanıtlamaz.

## Görsel bulgular

**Henüz doğrulanmış hata yok; ekran görüntüleri incelenmedi.** Bu ifade tüm testlerin geçtiği anlamına gelmez.

### Statik incelemede kontrol edilmesi gereken riskler (görsel kanıt değil)

| Kimlik | Risk / gözlem | Kaynak | Doğrulama gereksinimi |
| --- | --- | --- | --- |
| R-01 | 320 dp ve 1.3 yazı ölçeğinde `Tablo/Standart` alt gezinme etiketinin taşma riski | `MainActivity.kt`, dört eşit ağırlıklı alt gezinme öğesi, 11 sp metin | 320 dp / 1.3 / tüm sayfalar PNG ve XML |
| R-02 | Diyalog, klavye ve uzun ölçü değerlerinde görünür alan kaybı riski | `NumericPipeDialog` ve `LookupPage` | 320/360/411 dp, 1.3 font, sayısal giriş PNG |
| R-03 | Döner halkanın daireselliği, OD/ID/et okunabilirliği yalnız statik koddan değerlendirilemez | `PipeDial` Compose Canvas | Her boyut ve birimde PNG görsel inceleme |
| R-04 | Farklı NPS/SCH varyantları ve yatay SCH sürüklemesi tam otomatik senaryoda henüz yok | `CalculatorPage`, `LazyRow` | Ek emülatör etkileşimi, PNG/XML ve dokunma logu |

## Hata kaydı şablonu

Gerçek hata doğrulandığında her kayıt için **ekran, ekran boyutu (dp), font ölçeği, birim, NPS/SCH, yeniden üretim adımları, PNG artefakt yolu, önem derecesi ve çözüm önerisi** eklenecek. Kaynak kod varsayımına dayanarak görsel hata uydurulmaz.

## Kabul / kanıt durumu

| Kabul maddesi | Durum |
| --- | --- |
| İzole dal ve workflow GitHub'a kaydedildi | Doğrulandı |
| GitHub Actions koşusu oluşturuldu | Doğrulandı |
| APK derlemesi | Koşu sonucu bekleniyor |
| Gerçek Android emülatörü çalıştı | Doğrulanmadı |
| PNG artefaktı üretildi | Doğrulanmadı |
| PNG dosyaları açılıp görsel olarak incelendi | Doğrulanmadı |
| Tüm dokunmatik hedefler çalışıyor | Doğrulanmadı |
| Gerçek Android cihaz testi | Yapılmadı |
| Avenox görev ilerleme güncellemesi | Ayrı resmî Beyin işlemiyle doğrulanmalı |

> Not: GitHub Actions iş akışı başarısız olursa bu rapor **PASS** olarak okunmamalıdır. Kanıt için koşu logu, PNG ZIP artefaktı ve `results.json` birlikte incelenmelidir.
