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
- Tek emülatör, tek APK kurulumu, sadece gerektiğinde UI hiyerarşisi dump, hızlı PNG yakalama, `/dev/kvm` erişim kontrolü ve KVM hızlandırması; 15 dakika CI üst sınırı. **Son ölçülen quick süresi 4 dk 28 sn**; kuyruk ve emülatör koşulları nedeniyle her çalıştırmada garanti değildir.
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

## Görsel inceleme kapsamı — başarılı koşu 42219051125

| Konfigürasyon | İncelenen gerçek PNG'ler | Sonuç |
| --- | --- | --- |
| 360dp / 1.0 / inç | `360dp_font1p0_inch_{boru_initial,sch_bul,tablo_standart,ayarlar,id_dialog,sch_keyboard,dial_drag}.png` (+ `boru_return`) | Sayfalar, ID diyaloğu, klavye, NPS halkası görünür; smoke PASS |
| 320dp / 1.3 / mm | `320dp_font1p3_mm_{boru_initial,sch_bul,tablo_standart,ayarlar,id_dialog,sch_keyboard,dial_drag}.png` (+ `boru_return`) | **V-01** ve **V-02**; smoke PASS görsel kusursuzluk anlamına gelmez |
| 411dp / 1.0 / mm | `411dp_font1p0_mm_{boru_initial,sch_bul,tablo_standart,ayarlar,id_dialog,sch_keyboard,dial_drag}.png` (+ `boru_return`) | Sayfalar, ID diyaloğu, klavye, NPS halkası görünür; smoke PASS |

Artefakt: [24 gerçek PNG + XML + results.json](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42219051125/artifacts/13502148749). `results.json` 24 kayıt, 0 hata, 0 uyarı içerir. Bu `quick` çalışmada yalnızca belirtilen **üç** konfigürasyon denenmiştir. Ekran görüntüsü incelemesi, bütün dokunma alanlarının otomatik doğrulandığı veya tam ekranın kaydırılarak tarandığı anlamına gelmez.

## Görsel bulgular

**İki görsel yerleşim bulgusu doğrulandı; son `quick` matrisi PASS, tam matris ve gerçek cihaz doğrulaması açık.** Görsel değerlendirme yalnızca gerçek Android 13 PNG'lerine dayanır.

### V-01 — Dar ekranda alt gezinme etiketinin iki satıra taşması

- **Ekran:** Tüm alt gezinme öğeleri, özellikle `Tablo/Standart`
- **Boyut / ölçek / birim:** 320dp, `font_scale=1.3`, mm; NPS 6 / SCH 40 varsayılanı
- **Önem:** Orta (görsel tutarlılık ve dokunmatik alan yerleşimi)
- **Yeniden üretim:** Android 13 emülatörünü 320dp/160dpi ve 1.3 font ölçeğine ayarla; Boruzi'yi başlat; `Tablo/Standart` alt öğesine bak.
- **PNG kanıtı:** `320dp_font1p3_mm_boru_initial.png` ve `320dp_font1p3_mm_tablo_standart.png`, **son başarılı koşu** [artefakt 13502148749](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42219051125/artifacts/13502148749).
- **Gözlem:** `Tablo/Standart` yazısı iki satıra bölünüyor; diğer üç alt öğe tek satır ve aynı yükseklikte değil. İlgili dokunma alanlarının gerçekten çakıştığı kanıtlanmadı.
- **Öneri:** Alt gezinme etiketlerini daha kısa tutmak veya etiket alanını tüm öğeler için eşit/sabit yükseklikte, erişilebilir büyük yazıyla uyumlu düzenlemek. Uygulama kaynak kodu bu denetim dalında değiştirilmedi.

### V-02 — 320 dp / büyük yazıda dış çapın sayısal değeri ilk ekranda görünmüyor

- **Ekran:** Boru
- **Boyut / ölçek / birim:** 320dp, `font_scale=1.3`, mm; NPS 6 / SCH 40.
- **Önem:** Yüksek (birincil ölçünün görünürlüğü).
- **Yeniden üretim:** Android 13 emülatörünü 320dp / 160dpi ve 1.3 yazı ölçeğine ayarla; mm birimini seç; Boru sayfasının ilk açılış görüntüsüne bak.
- **PNG kanıtı:** `320dp_font1p3_mm_boru_initial.png` ve `320dp_font1p3_mm_boru_return.png`, [başarılı koşu 42219051125 artefaktı](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42219051125/artifacts/13502148749).
- **Gözlem:** Sarı sınırlı `Dış Çap (OD)` kartının başlığı görünür; **168,30 mm sayısal değeri ilk görünümde alt gezinme çubuğunun altında kalır**. 360dp/inç ve 411dp/mm örneklerinin ilk ekranında OD sayısı görünür. Bu kayıt **kaydırma yapınca da ulaşılamıyor** veya **dokunma engelleniyor** anlamına gelmez; kaydırma/erişim doğrulaması yapılmadı.
- **Öneri:** 320dp / 1.3 yazı ölçeğinde dikey boşluk, halka büyüklüğü ve kart yüksekliğini duyarlı şekilde düzenlemek; OD sayısını görünür tutmak. Ayrıca kaydırınca kartın tamamına ve tıklanabilir alana erişilebildiğini test etmek.

### V-03 — Bilgi hiyerarşisinin dar ekranda sıkışması (iyileştirme önerisi, ayrı kusur değil)

- **Ekran:** Boru; `320dp_font1p3_mm_boru_initial.png`.
- **Gözlem:** Halka / et kalınlığı / iç çap / dış çap bileşenleri dikey alanın önemli kısmını kaplıyor. İlk ekranda hesapların devamı görünmüyor. Dikey kaydırmalı tasarımda sayfanın tamamının ilk görünümde olmaması tek başına hata sayılmaz.
- **Öneri:** Mobil ölçüler için kartların arasındaki boşlukları azaltırken 48dp dokunma hedeflerini korumak; yazıları okunaklı bırakmak. **Durum:** Tasarım iyileştirme adayı, kanıtlı işlevsel hata değil.

### E-01 — UI sürücüsünde SCH alanının metin etiketiyle bulunamaması (test altyapısı; giderildi)

- **Ekran:** SCH Bul; 320dp/1.3/mm, 360dp/1.0/inç, 411dp/1.0/mm
- **Önem:** Yüksek, otomatik test güvenilirliği açısından; uygulama hatası olarak sınıflandırılmadı.
- **Yeniden üretim:** Eski test sürücüsü `uiautomator dump` üzerinde `Dış çap (mm)` / `Dış çap (inç)` metnini arar; Compose `OutlinedTextField` etiketi bu düğümde bağımsız metin olarak görünmez. Ekran geçişi için 180 ms bekleme de yetersizdir.
- **Kanıt:** Aynı artefaktın `results.json` dosyasında 3 hata; `*_lookup_source.xml` Android hiyerarşisinde beklenen etiket yok.
- **Çözüm:** EditText sınıfını ve bounds değerini kullan; ekran başlığını doğrula; geçişi bekle. EditText erişimi, ekran başlığı kontrolü ve beklemeler güncellendi; son başarılı koşu `42219051125` içinde 3/3 quick senaryosu PASS.

### Statik incelemede kontrol edilmesi gereken riskler (görsel kanıt değil)

| Kimlik | Risk / gözlem | Kaynak | Doğrulama gereksinimi |
| --- | --- | --- | --- |
| R-01 | 320 dp ve 1.3 yazı ölçeğinde `Tablo/Standart` alt gezinme etiketinin taşma riski (V-01 görselde doğrulandı) | `MainActivity.kt`, dört eşit ağırlıklı alt gezinme öğesi, 11 sp metin | 320 dp / 1.3 / tüm sayfalar PNG ve XML |
| R-02 | Diyalog, klavye ve uzun ölçü değerlerinde görünür alan kaybı riski | `NumericPipeDialog` ve `LookupPage` | 320/360/411 dp, 1.3 font, sayısal giriş PNG |
| R-03 | Döner halkanın daireselliği, OD/ID/et okunabilirliği yalnız statik koddan değerlendirilemez | `PipeDial` Compose Canvas | Her boyut ve birimde PNG görsel inceleme |
| R-04 | Farklı NPS/SCH varyantları ve yatay SCH sürüklemesi tam otomatik senaryoda henüz yok | `CalculatorPage`, `LazyRow` | Ek emülatör etkileşimi, PNG/XML ve dokunma logu |

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
| Son quick koşusunda Debug APK derlemesi | PASS: run 42219051125 |
| 320dp ilk ekranda OD rakamı | V-02: görünür değil; scroll ile erişim henüz test edilmedi |
| Gerçek Android emülatörü çalıştı | PASS: Android 13 log + 24 gerçek PNG |
| PNG artefaktı üretildi | PASS: 24 PNG, XML ve JSON, artefakt 13502148749 |
| PNG dosyaları açılıp görsel olarak incelendi | Evet, son quick montajı; V-01 ve V-02 saptandı, tam matris açık |
| Tüm dokunmatik hedefler çalışıyor | Doğrulanmadı |
| Gerçek Android cihaz testi | Yapılmadı |
| Avenox görev ilerleme güncellemesi | Ayrı resmî Beyin işlemiyle doğrulanmalı |

> Not: GitHub Actions iş akışı başarısız olursa bu rapor **PASS** olarak okunmamalıdır. Kanıt için koşu logu, PNG ZIP artefaktı ve `results.json` birlikte incelenmelidir.

## Son kabul özeti — 2026-11-06

- **Gerçek emülatör:** Android 13, KVM etkin; uygulama ekranı hazır oluşu ve inç/mm seçiminin ölçü metnine yansıması kontrol edildi.
- **Hız:** CI run 42219051125 başladı 12:37:09, tamamlandı 12:41:37 UTC = **4 dk 28 sn**. Bu bir ölçümdür; her GitHub sırasının 5 dakikada biteceği garantisi değildir.
- **Kaynak artefakt:** [13502148749](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42219051125/artifacts/13502148749), gerçek Android `screencap` ile yakalanmış **24 PNG**, erişilebilirlik XML'leri, `results.json` (24 capture / 0 errors / 0 warnings).
- **Görsel gözden geçirme:** 360dp/inç/1.0, 320dp/mm/1.3, 411dp/mm/1.0; Boru, SCH Bul, Tablo/Standart, Ayarlar, ID diyaloğu, klavye ve NPS sürükleme PNG'leri incelendi. V-01 (320dp büyük yazıda alt etiket iki satır) ve V-02 (320dp büyük yazıda ilk görünümde OD sayısı gizli) açık.
- **Açık test borcu:** 12'li `full` matris, farklı SCH seçimleri, uzun değerler ve fiziksel Android cihaz. `quick` PASS bunların geçtiğini kanıtlamaz.
