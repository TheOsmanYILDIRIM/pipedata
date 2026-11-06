# Boruzi Android görsel ve etkileşim denetimi

**Durum:** Hızlı Android 13 emülatör smoke testi **PASS**; üç temsilî konfigürasyonda 24 gerçek PNG doğrulandı ve açılıp incelendi. Geniş 12'li matris ve gerçek cihaz testi açık.
**Tarih:** 2026-11-06  
**Kaynak:** `TheOsmanYILDIRIM/pipedata`  
**Başlangıç main HEAD:** `d435597615dbdba8bc341d9976ba0745d2503079`  
**İzole dal:** `audit/boruzi-android-screenshots-20261106`  
**Test iş akışı:** [ui-screenshots.yml](../.github/workflows/ui-screenshots.yml)  
**Güncel başarılı Actions koşusu:** [42219051125](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42219051125) (`e24d0f4f8f24420f6e4aa96391e8c89a046a2cf4`), **4 dakika 28 saniye** (12:37:09–12:41:37 UTC). [PNG/XML/JSON artefaktı](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42219051125/artifacts/13502148749), 24 PNG, 0 hata, 0 uyarı.
**İlk gerçek Android PNG koşusu:** [42216238840](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42216238840), [PNG/XML/JSON artefaktı](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42216238840/artifacts/13501661494). Emülatör Android 13, açılış 33.5 sn; 21 PNG üretildi. `results.json` üç SCH giriş alanı erişilebilirlik araması hatası içeriyor; adım `failure`. Tam koşu 3 dakika 18 saniye sürdü (12:15:11–12:18:29 UTC).
**Hızlı koşunun doğrulanmış arızası:** [42215367381](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42215367381): `ProbeKVM: ... doesn't have permissions to use KVM (/dev/kvm)`, `-accel off`, `Timeout waiting for emulator to boot`, ADB `device offline`. UI testi hiç başlamadı; 210 baytlık tek artefakt yalnızca `run-metadata.txt` içeriyor. Sorun Android uygulama render'ı olarak sınıflandırılmaz.
**Önceki başarısız koşu:** [42188972565](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42188972565), `cancelled`; Android emülatörü step `failure`, PNG artefaktı yok. Doğrudan log indirme API'si `BlobNotFound` döndürdü; kesin hata nedeni teşhis edilemedi.

## Sınırlar ve doğrulanmış kaynaklar

- `AGENTS.md`, `README.md`, `.github/workflows/build_apk.yml` incelendi; mevcut Release APK iş akışı değiştirilmedi.
- `SESSION_HANDOFF.md` bu commit'te mevcut değil.
- Gerçek kaynakta dört alt gezinti öğesi var: **Boru**, **SCH Bul**, **Tablo/Standart**, **Ayarlar**. Tablo ve Standart aynı üçüncü ekranın birleşik içeriği.
- Bu raporda gerçek Android PNG'si incelenmeden hiçbir kırpılma, taşma, hizalama veya işlev hatası **kanıtlanmış görsel kusur** sayılmıyor.
- Avenox mevcut görev: `tasks/pipedata-boru-only-rebuild-20261105.md` (görsel/cihaz doğrulaması açık). Bu rapor görev kaydı yerine geçmez.

## Hızlı test stratejisi (2026-11-06)

- `quick` varsayılanı: **360dp / 1.0 / inç**, **320dp / 1.3 / mm**, **411dp / 1.0 / mm**; Boru, SCH Bul, Tablo/Standart, Ayarlar ve sayısal diyalog/klavye/NPS etkileşim smoke denemeleri.
- `full` manuel iş akışı seçeneği: 320/360/411dp × 1.0/1.3 × inç/mm = 12 konfigürasyon. Tam matrisi üçlü smoke PASS gibi gösterme.
- Tek emülatör, tek APK kurulumu, sadece gerektiğinde UI hiyerarşisi dump, hızlı PNG yakalama, `/dev/kvm` erişim kontrolü ve KVM hızlandırması; 15 dakika CI üst sınırı. **5 dakika hedef; ölçülmüş çalışma süresi değildir.**
- Hata halinde `results.json` ve mevcut PNG/XML `if: always()` ile artefakt olarak yüklenir. Hiçbir dosya üretilmemişse PASS iddia edilmez.

## Emülatör test matrisi

| Parametre | Denenecek değerler |
| --- | --- |
| Ekran | 320, 360, 411 dp; 160 dpi sanal yoğunluk |
| Yazı ölçeği | 1.0, 1.3 |
| Birim | inç (varsayılan), mm (Ayarlar anahtarı) |
| Ekranlar | Boru, SCH Bul, Tablo/Standart, Ayarlar |
| Etkileşim | Alt gezinme, sayısal alan diyaloğu, SCH sorgusu, klavye, kaydırma |
| Artefakt | Android `screencap -p` gerçek PNG, `uiautomator dump` XML, `results.json`, logcat |

İş akışı `quick` modunda 3 temsilî, `full` modunda 12 konfigürasyon dener; her ekranda gerçek PNG, temel etkileşimlerde Android UI hiyerarşisi kaydedilir. `results.json` herhangi bir başarısız durumu açıkça listeleyecek şekilde yazılmıştır. İş akışının tanımlanması emülatörün başarıyla çalıştığını kanıtlamaz.

## Görsel bulgular

**Bir görsel kusur doğrulandı; son `quick` matrisi PASS, tam matris ve gerçek cihaz doğrulaması açık.** Görsel değerlendirme yalnızca gerçek Android 13 PNG'lerine dayanır.

### V-01 — Dar ekranda alt gezinme etiketinin iki satıra taşması

- **Ekran:** Tüm alt gezinme öğeleri, özellikle `Tablo/Standart`
- **Boyut / ölçek / birim:** 320dp, `font_scale=1.3`, mm; NPS 6 / SCH 40 varsayılanı
- **Önem:** Orta (görsel tutarlılık ve dokunmatik alan yerleşimi)
- **Yeniden üretim:** Android 13 emülatörünü 320dp/160dpi ve 1.3 font ölçeğine ayarla; Boruzi'yi başlat; `Tablo/Standart` alt öğesine bak.
- **PNG kanıtı:** `320dp_font1p3_mm_boru.png` ve `320dp_font1p3_mm_tablo_standart.png`, [artefakt 13501661494](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42216238840/artifacts/13501661494).
- **Gözlem:** `Tablo/Standart` yazısı iki satıra bölünüyor; diğer üç alt öğe tek satır ve aynı yükseklikte değil. İlgili dokunma alanlarının gerçekten çakıştığı kanıtlanmadı.
- **Öneri:** Alt gezinme etiketlerini daha kısa tutmak veya etiket alanını tüm öğeler için eşit/sabit yükseklikte, erişilebilir büyük yazıyla uyumlu düzenlemek. Uygulama kaynak kodu bu denetim dalında değiştirilmedi.

### E-01 — UI sürücüsünde SCH alanının metin etiketiyle bulunamaması (test altyapısı)

- **Ekran:** SCH Bul; 320dp/1.3/mm, 360dp/1.0/inç, 411dp/1.0/mm
- **Önem:** Yüksek, otomatik test güvenilirliği açısından; uygulama hatası olarak sınıflandırılmadı.
- **Yeniden üretim:** Eski test sürücüsü `uiautomator dump` üzerinde `Dış çap (mm)` / `Dış çap (inç)` metnini arar; Compose `OutlinedTextField` etiketi bu düğümde bağımsız metin olarak görünmez. Ekran geçişi için 180 ms bekleme de yetersizdir.
- **Kanıt:** Aynı artefaktın `results.json` dosyasında 3 hata; `*_lookup_source.xml` Android hiyerarşisinde beklenen etiket yok.
- **Çözüm:** EditText sınıfını ve bounds değerini kullan; ekran başlığını doğrula; geçişi bekle. Düzeltme commit `0bac6360` ile yeni CI koşusunda test ediliyor.

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
| İlk GitHub Actions emülatör koşusu | İptal edildi; PNG artefaktı yok |
| İkinci hızlı koşu | Başarısız: `/dev/kvm` izni yok, emülatör açılış timeout; yalnız metadata artefaktı |
| KVM düzeltmeli ilk quick koşusu | Android 13 emülatör açıldı, 21 PNG artefaktı PASS; 3 test sürücüsü erişilebilirlik hatası nedeniyle CI FAIL |
| Erişilebilirlik düzeltmeli eski quick koşusu | CI FAIL (2 geçici XML parse hatası); retry eklendi |
| Birim durumu kontrolü | 411dp ilk denemede geçiş doğrulanmadı; tekrar kontrol ile düzeltildi |
| **Son quick koşusu** | **PASS**, [42219051125](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42219051125), **4 dk 28 sn**, 24 PNG, 0 hata/uyarı |
| Önceki koşuda Debug APK derlemesi | PASS; yeni koşu ayrıca doğrulanacak |
| Gerçek Android emülatörü çalıştı | PASS: Android 13 log + 24 gerçek PNG |
| PNG artefaktı üretildi | PASS: 24 PNG, XML ve JSON, artefakt 13502148749 |
| PNG dosyaları açılıp görsel olarak incelendi | Evet, son quick montajı; V-01 devam ediyor, tam matris açık |
| Tüm dokunmatik hedefler çalışıyor | Doğrulanmadı |
| Gerçek Android cihaz testi | Yapılmadı |
| Avenox görev ilerleme güncellemesi | Ayrı resmî Beyin işlemiyle doğrulanmalı |

> Not: GitHub Actions iş akışı başarısız olursa bu rapor **PASS** olarak okunmamalıdır. Kanıt için koşu logu, PNG ZIP artefaktı ve `results.json` birlikte incelenmelidir.

## Son kabul özeti — 2026-11-06

- **Gerçek emülatör:** Android 13, KVM etkin; uygulama ekranı hazır oluşu ve inç/mm seçiminin ölçü metnine yansıması kontrol edildi.
- **Hız:** CI run 42219051125 başladı 12:37:09, tamamlandı 12:41:37 UTC = **4 dk 28 sn**. Bu bir ölçümdür; her GitHub sırasının 5 dakikada biteceği garantisi değildir.
- **Kaynak artefakt:** [13502148749](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42219051125/artifacts/13502148749), gerçek Android `screencap` ile yakalanmış **24 PNG**, erişilebilirlik XML'leri, `results.json` (24 capture / 0 errors / 0 warnings).
- **Görsel gözden geçirme:** 360dp/inç/1.0, 320dp/mm/1.3, 411dp/mm/1.0; Boru, SCH Bul, Tablo/Standart, Ayarlar, ID diyaloğu, klavye ve NPS sürükleme PNG'leri incelendi. V-01 (320dp büyük yazıda alt etiket iki satır) açık.
- **Açık test borcu:** 12'li `full` matris, farklı SCH seçimleri, uzun değerler ve fiziksel Android cihaz. `quick` PASS bunların geçtiğini kanıtlamaz.
