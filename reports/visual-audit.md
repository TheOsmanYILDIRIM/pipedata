# Boruzi Android görsel ve etkileşim denetimi

**Durum:** İlk koşu iptal, hızlı ikinci koşu emülatör başlatma hatasıyla başarısız; KVM izin düzeltmesiyle üçüncü koşu başladı. PNG/görsel kusur hâlâ doğrulanmadı (`pending_unverified`).  
**Tarih:** 2026-11-06  
**Kaynak:** `TheOsmanYILDIRIM/pipedata`  
**Başlangıç main HEAD:** `d435597615dbdba8bc341d9976ba0745d2503079`  
**İzole dal:** `audit/boruzi-android-screenshots-20261106`  
**Test iş akışı:** [ui-screenshots.yml](../.github/workflows/ui-screenshots.yml)  
**Güncel KVM düzeltmeli Actions koşusu:** [42216238840](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42216238840) (`97019d2deeb3a9180e227705bb84be8fec110c6a`)
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
| İlk GitHub Actions emülatör koşusu | İptal edildi; PNG artefaktı yok |
| İkinci hızlı koşu | Başarısız: `/dev/kvm` izni yok, emülatör açılış timeout; yalnız metadata artefaktı |
| KVM düzeltmeli quick koşusu | [42216238840](https://github.com/TheOsmanYILDIRIM/pipedata/actions/runs/42216238840), sonuç bekleniyor |
| Önceki koşuda Debug APK derlemesi | PASS; yeni koşu ayrıca doğrulanacak |
| Gerçek Android emülatörü çalıştı | Doğrulanmadı |
| PNG artefaktı üretildi | Doğrulanmadı |
| PNG dosyaları açılıp görsel olarak incelendi | Doğrulanmadı |
| Tüm dokunmatik hedefler çalışıyor | Doğrulanmadı |
| Gerçek Android cihaz testi | Yapılmadı |
| Avenox görev ilerleme güncellemesi | Ayrı resmî Beyin işlemiyle doğrulanmalı |

> Not: GitHub Actions iş akışı başarısız olursa bu rapor **PASS** olarak okunmamalıdır. Kanıt için koşu logu, PNG ZIP artefaktı ve `results.json` birlikte incelenmelidir.
