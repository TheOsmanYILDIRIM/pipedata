# Pipedata (Modern Piping Engineering Reference & Calculator)

Endüstri standardı borulama mühendisliği referans uygulaması olan **Pipedata**'nın tersine mühendislikle çözümlenmiş tam veri tabanına dayanan, modern, platform bağımsız ve akıllı bir mühendislik referans ve hesaplama kütüphanesi.

---

## 1. Proje Özeti ve Kapsam

Pipedata projesi; petrol, doğalgaz, petrokimya ve enerji santrali borulama mühendisliğinde kullanılan standart boyutları, toleransları, malzeme ağırlıklarını ve teknik resimleri tek bir yüksek performanslı SQLite veritabanında ve modern vektörel CAD motorunda bir araya getirir.

### Desteklenen Küresel Standartlar:
* **Borular:** ASME B36.10M (Karbon Çelik), ASME B36.19M (Paslanmaz Çelik) (NPS 1/8" - 80", Sch 5 - XXS)
* **Flanşlar:** ASME B16.5, ASME B16.47 (Seri A & B), ASME B16.36 (Orifice), API Spec 6A (6B/6BX), DIN EN 1092-1
* **Fittingler:** ASME B16.9 (Alın Kaynaklı / Buttweld), ASME B16.11 (Dövme Çelik Soket & Dişli)
* **Endüstriyel Vanalar:** ASME B16.10, ASME B16.34 (Gate, Globe, Ball, Check, Butterfly - Class 150-2500)
* **Contalar & Bağlantı Elemanları:** ASME B16.20 (Spiral-Wound, RTJ R/RX/BX), ASME B16.21 (Flat), ASME B18.2.1/B18.2.2 (Saplama & Somun)
* **Hat Körlemeleri:** ASME B16.48 (Figure-8, Paddle Blanks & Spacers)
* **Nozullar & Branşmanlar:** MSS-SP-97 (Weldolet, Sockolet, Thredolet, Elbolet, Latrolet), MSS-SP-95 (Swage Nipples)

---

## 2. Veri Tabanı Mimarisi

* **SQLite Veritabanı (`pipedata.db`):** 17 Tablo, 956 CSV kaynağından normalize edilmiş 22,525 veri satırı.
* **Master Manifest (`schema_manifest.json`):** 956/956 dosya %100 haritalandı (0 veri kaybı).
* **Teknik Çizim Motoru (`svg_manifest.json`):** 122 SVG teknik çizimi, AutoCAD ASME Y14.5M vektörel standartlarına dönüştürüldü (45° ASME 31 tarama deseni, 3:1 standart ok uçları, kontrast ölçü rozetleri).
* **CAD Çizimleri:** 214 tam ölçekli 2D DXF vektör dosyası.

---

## 3. Doğrulama ve Test Protokolü

Proje, 17/17 otomatik kabul testinden oluşan dondurulmuş bir test şartnamesine (`test.md` & `test_suite.py`) sahiptir:

```bash
# Test paketini çalıştırma
python3 test_suite.py
```
* **Test Kapsamı:** CSV dosya ve satır bütünlüğü, 18 schedule serisi ve basınç kademesi, Benchmark A (14" STD Boru), Benchmark B (14" STD Kep), Kesirli inç dönüştürücüler, Atalet momenti ($I = \frac{\pi}{64}(OD^4 - ID^4)$), Güvenli Açıklık (Safe Span) sehim/gerilme motoru ve 122 SVG şablonunun XML geçerliliği.

---

## 4. UI & Etkileşim Paradigmaları (Industrial UX)

Kıdemli saha ve tasarım mühendislerinin ihtiyaçlarına göre 7 farklı profesyonel arayüz mimarisi modellenmiştir:
1. **The Industrial Keypad Terminal:** HP 48G / Fluke referanslı, sıfır scroll, 4x4 donanım tuş takımı.
2. **The Rotary Caliper Jog-Dial:** Leica DISTO referanslı, sağ kenara gömülü sonsuz tambur.
3. **The McMaster Density Table:** Kompakt, yüksek yoğunluklu veri matrisi.
4. **The Cockpit Multi-HUD:** Garmin havacılık göstergeleri tarzı çoklu bilgi kadranları.
5. **The Interactive Drawing Hotspots:** Teknik çizim üzerindeki ölçülere dokunarak doğrudan veri alma.
6. **The Split Workbench:** Üstte canlı CAD çizimi, altta filtreleme ve hesaplama modülleri.
7. **The Swipe Roller:** Tam ekran akıcı silindir kaydırma.

*Canlı interaktif prototipler `pipedata_tester.html` ve `pipedata_interaction_paradigms.html` üzerinden doğrudan görüntülenebilir.*

---

## 5. Proje Yapısı

```
projects/pipedata/
├── AGENTS.md / CLAUDE.md / GEMINI.md  # Avenox doktrini proje kuralları
├── notes.md                           # Detaylı tersine mühendislik ve mimari döküm
├── backlog.md                         # Geliştirme yol haritası ve durum takibi
├── constants.py                       # Mühendislik katsayıları ve yoğunluklar
├── schema_manifest.json               # 956 CSV şema haritası
├── svg_manifest.json                  # 122 SVG teknik resim manifestosu
├── pipedata.db                        # Tam SQLite3 veritabanı
├── pipedata_tester.html               # WebAssembly sql.js canlı SQL test konsolu
├── pipedata_interaction_paradigms.html # 7 Etkileşim paradigması interaktif HTML
├── data/                              # Çıkarılan CSV, SVG, DXF ve görseller
├── reports/                           # Araştırma ve tersine mühendislik raporları
├── test.md                            # Dondurulmuş test şartnamesi
└── test_suite.py                      # 17/17 Otomatik kabul testi
```
