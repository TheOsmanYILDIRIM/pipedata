# Boruzi — Kod ve Hata Denetimi

- **Durum:** Statik denetim tamamlandı; CI, Python betiğinin bizzat çalıştırılması ve cihaz doğrulaması açık.
- **Kaynak:** `TheOsmanYILDIRIM/pipedata`, `main` commit `d435597615dbdba8bc341d9976ba0745d2503079`.
- **Rapor zamanı (UTC):** unknown (GitHub yazma zamanı ayrı kayıttır).
- **İnceleme türü:** GitHub üzerinden salt-okunur kaynak denetimi; CSV üzerinde ayrı JavaScript bütünlük taraması.
- **Dosya sahipliği:** Yalnız `reports/code-audit.md` yazıldı; uygulama kodu ve CI değiştirilmedi.
- **Avenox Beyin:** `agy-vault/10-Projects/pipedata.md` okundu; eski mühendislik uygulamasını tarif ediyor. Bu işin kanonik görev ID/revision'ı doğrulanamadığından görev/receipt güncellemesi **yapılmadı**.

## Bulgu tablosu

| ID | Önem | Durum | Konum | Kısa açıklama |
|---|---|---|---|---|
| BZ-A01 | High | Kaynakta doğrulandı | `app/build.gradle.kts:17-32` | APK imza sırrı açık metin ve debug/release aynı anahtarı kullanıyor |
| BZ-A02 | Medium | Kaynakta doğrulandı | `MainActivity.kt:706-724` | Imperial seçiliyken uzunluk hesabı daima m, kg ve L gösteriyor |
| BZ-A03 | Medium | Veri/belge farkı doğrulandı | `README.md`, `AGENTS.md`, `pipe_schedules.csv:1-378` | Belgedeki 373 kayıt sayısı mevcut 377 satırla uyuşmuyor |
| BZ-A04 | Medium | Test boşluğu doğrulandı | `tools/verify_pipe_data.py:10,52-106`, `build_apk.yml:29-41` | Testler gerçek Compose etkileşimini, cihazı ve debug derlemesini doğrulamıyor |
| BZ-A05 | Low | Davranış riski; cihazda doğrulanmadı | `MainActivity.kt:742-752` | SCH Bul standard parametresini kullanmıyor; iki standardı birden eşliyor |

> `MainActivity.kt` yolları: `app/src/main/java/com/avenox/pipedata/MainActivity.kt`. Kaynakta doğrulama, Android üzerinde yeniden üretimin yapıldığı anlamına gelmez.

## Bulgular ve kanıt

### BZ-A01 — Açık metin APK imza parolası (High)

- **Kanıt:** `app/build.gradle.kts:19-22` içinde `storeFile = file("antigravity.keystore")`, sabit `storePassword` ve `keyPassword` değerleri vardır. `:27,32` hem release hem debug için aynı signing config'i seçer. Gizli parola raporda tekrar edilmiyor.
- **Mekanizma:** Depo erişimi olan aktörler kaynakta duran imza bilgilerini öğrenebilir. Keystore de bu ortamda erişilebiliyorsa uygulama güncellemesi imzalama riski doğar; üçüncü taraf sömürüsü/anahtar ele geçirilmesi kanıtlanmadı.
- **Yeniden üretim:** İlgili Gradle satırlarını görüntüle; release/debug signing eşlemesini incele.
- **Öneri:** İmzalama materyalini kontrollü sır yönetimine taşı; mevcut kurulu uygulamalarla imza uyumluluğunu koruyarak anahtar rotasyonu/koruması için ayrı geçiş planı oluştur. Keystore'u veya kullanıcı verisini bu denetim sırasında değiştirme.
- **Doğrulama:** Sır taraması, CI secrets erişim denetimi ve eski APK'dan yeni APK'ya kontrollü güncelleme testi.

### BZ-A02 — Imperial birim seçeneği uzunluk hesaplayıcıda yok sayılıyor (Medium)

- **Kanıt:** `MainActivity.kt:706` `imperial` parametresi alır ancak `:714-723` uzunluğu daima metre, kütleyi kg ve hacmi L olarak üretir. `:918` ayarlar inç/lb/gal seçeneği sunar.
- **Mekanizma:** Birim tercihi hesaplama diyaloğunda uygulanmaz; kullanıcı mevcut ekranın birimleriyle aynı sistemde sonuç alacağını düşünebilir. Formüller metrik için tutarlı görünür.
- **Yeniden üretim:** Ayarlarda Imperial aç → Boru → Hesapla; diyalogda `Uzunluğu metre olarak girin`, `kg`, `L` etiketlerini kontrol et. Bu adımlar kaynakla desteklenir; cihazda uygulanmadı.
- **Öneri:** Uzunluğu ft/m seçimine bağla, (1\,ft=0.3048\,m\) dönüşümünü ve lb/gal çıktılarını merkezi birim fonksiyonlarıyla uygula; veya metrik-only kısıtını diyalog açılmadan belirgin açıkla.
- **Doğrulama:** Imperial ve metrikte aynı fiziksel uzunluk için eşit SI sonucu, round-trip ve Compose UI testi.

### BZ-A03 — Belgelenmiş veri sayısı güncel CSV ile uyuşmuyor (Medium)

- **Kanıt:** `README.md` ve `AGENTS.md` 373 satır bildirir; `app/src/main/assets/pipe_schedules.csv` dosyasında başlık hariç **377** kayıt vardır (**283 B36.10 + 94 B36.19**). Blob SHA: `7b51df527282dbe26ebc02d1a94f1d233463f3b0`.
- **Mekanizma:** Veri kapsamının yanlış aktarılması kabul ölçütünü ve eksik kayıt denetimini yanıltır; 377 satırın lisanslı ASME tablolarına tam uygunluğu buradan çıkarılamaz.
- **Yeniden üretim:** CSV başlık dışı kayıtları say ve standarda göre grupla; README/AGENTS içindeki 373 değerleriyle karşılaştır.
- **Öneri:** Veri üretim kaynağına göre değişiklik farkını denetle ve belgeleri doğrulanmış güncel kapsamla senkronize et; dış standart mutabakatı için ayrı kaynaklı test ekle.
- **Doğrulama:** CSV kayıt sayısı ve standart dağılımını CI'da raporla; beklenen sürüm/veri kaynağı ile eşleştir.

### BZ-A04 — Testlerin kapsamadığı Android davranışları (Medium)

- **Kanıt:** `tools/verify_pipe_data.py:10` yalnız minimum 300 kayıt şartı koyar; `:52-106` Kotlin içeriğinde belirli metinlerin bulunmasını denetler. `.github/workflows/build_apk.yml:29-41` test betiğini ve yalnız `assembleRelease` komutunu koşar; Compose UI/otomasyon veya `assembleDebug` görünmez.
- **Mekanizma:** Kodda doğru dize kaldığı hâlde işlevin bozulması mümkün; eşik testi bazı kayıt silinmelerini kaçırabilir. Release build, ekranların kullanılabilirliğini kanıtlamaz.
- **Yeniden üretim:** Test betiği ve CI steps satırlarını incele. *Negatif kontrol önerisi:* İzole deney dalında bir UI işlevini bozup metinleri koruyarak mevcut kapının yanlış pozitif verip vermediğini test et; bu denetimde kod değiştirilmedi.
- **Öneri:** Kotlin birim testleri, CSV fixture regression, Compose UI ekran/etkileşim testleri, erişilebilirlik ve 320/360/411dp + fontScale=1.3 görsel testleri ekle. Debug ve release derlemelerini ayrı kontrol et.
- **Doğrulama:** Gerçek test logları, CI job artefaktları ve emülatör/cihaz etkileşim kayıtları.

### BZ-A05 — SCH Bul standarda bağlı filtre uygulamıyor (Low, davranış şüphesi)

- **Kanıt:** `MainActivity.kt:742` `LookupPage(catalog, standard, ...)` alırken `:752` `catalog.findByMeasurement(odMm, wallMm)` çağrısında `standard` iletmez. `PipeCatalog.kt` fonksiyonu isteğe bağlı standart filtresini destekler.
- **Mekanizma:** Seçili standart B36.10 olsa bile B36.19 eşleşmeleri gösterilir. İki standardın birlikte sunulması bilinçli ürün tercihi olabilir; kesin hata kabul edilmemeli.
- **Yeniden üretim:** B36.10 kaydı seç → SCH Bul'da her iki standartta bulunan ölçüyü ara → sonuçların standart etiketlerini gözle; cihaz test edilmedi.
- **Öneri:** Birleşik eşleşme isteniyorsa bunu açıkça belirt; değilse standart filtresini ilet ve standart değiştirme denetimi ekle.
- **Doğrulama:** B36.10 ve B36.19 örtüşen ölçü fixture'larıyla UI testi.

## Tamamlanan kontroller

- GitHub `main` HEAD doğrulandı: başlangıç ref'i ile aynı (`d4355976...`).
- `AGENTS.md`, `README.md`, `PipeCatalog.kt`, `MainActivity.kt`, CSV, test betiği ve APK workflow'u kaynaktan okundu.
- CSV'yi okuyarak bağımsız **JavaScript** bütünlük taraması çalıştırıldı: 377 kayıt, mükerrer standard/NPS/SCH anahtarı **0**, fiziksel aralık ihlali **0**, S seri etiket ihlali **0**. Üç benchmark (6/40, 6/40S, 44/STD) değer kontrolü **geçti**.
- Bu kontrol **Python `tools/verify_pipe_data.py` çalıştırması değildir**. Matematiksel denetim, eksiksiz ASME veri doğrulaması değildir.
- İncelemede üretim koduna, CI'ya ve diğer ajanın raporuna yazılmadı.

## Engeller ve doğrulanamayanlar

1. Bu GitHub araçları kaynak okuma/yazma sağlar; uzak repo üstünde Python/Gradle komut yürütücüsü sunmadığından **resmî Python test betiği çalıştırılamadı**.
2. **Kotlin/Gradle derleme sonucu, CI run sonucu ve release APK kullanılabilirliği doğrulanmadı.**
3. **Emülatör / gerçek Android cihaz:** Çalıştırılmadı; çökme, lifecycle, ekran taşması, büyük yazı, dokunmatik halka ve TalkBack hakkında kesin sonuç yok.
4. **ASME lisanslı dış kaynak mutabakatı:** Yapılmadı. CSV'nin fiziksel aralık testlerini geçmesi standart doğruluğu kanıtlamaz.
5. **Avenox görev kaydı:** Eski proje notu bulundu; ilgili kanonik görev ID ve revision doğrulanmadı. Mükerrer görev yaratılmadı, Beyin görevi güncellenmedi.

## Sonraki adımlar

1. Önce BZ-A01 imza materyali riskini kontrollü ve mevcut uygulama imzasını koruyacak şekilde ele al.
2. BZ-A02 Imperial uzunluk davranışını ürün beklentisine göre düzelt; BZ-A03 kapsam sayısını kaynakla mutabıklaştır.
3. Ayrı görev/ajan sahipliğinde UI emülatör görüntülerini BZ-A04 ve BZ-A05'in gerçek etkileşim doğrulamasına bağla.
4. Mevcut EşRota/Avenox kanonik görevinin ID/revision bilgisi doğrulandığında bu raporun commit bağlantısını mevcut göreve iliştir; yeni görev açma.

---
**Bağımsız denetçi sınırı:** Kaynağın görüntülenmesi, testin geçmesi veya gerçek cihazda doğrulama birbirinin yerine kullanılmadı.
