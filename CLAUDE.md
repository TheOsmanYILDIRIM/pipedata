# Pipedata Alternative Project Guidelines (Avenox Doktrini)

## 1. Kural Senkronizasyonu (Sync Rule - Zorunlu)
* `AGENTS.md`, `GEMINI.md` veya `CLAUDE.md` dosyalarından herhangi biri güncellendiğinde, diğer tüm kural dosyaları da birebir aynı içerikle senkronize olarak güncellenmek ZORUNDADIR. Kurallar şişirilmez, öz ve net tutulur.

## 2. Proje Yapısı & Mimari
* **Atölye (Proje Klasörü):** `~/projects/pipedata/`
  - `data/`: APK'dan tersine mühendislikle çıkarılan 956 CSV, 122 SVG, 214 DXF ve JSON katalogları.
  - `notes.md`: Mimari döküm, veri modelleri ve tersine mühendislik bulguları.
  - `reports/`: Araştırma ve detaylı analiz raporları.
  - `backlog.md`: Yarım kalan işler ve geliştirme yol haritası.
  - `constants.py`: Mühendislik katsayıları, malzeme yoğunlukları ve standart parametreler.
* **Beyin (Vault):** `agy-vault/10-Projects/pipedata.md` (özet ve kararlar).

## 3. Mühendislik Standartları ve Doğruluk
* Tüm boru, flanş, vana, fitting ve conta boyutları ASME, API, DIN ve MSS standartlarına %100 sadık kalınarak indekslenmiştir.
* Birim dönüşümleri (Metrik / U.S. Customary / İnç Kesirleri) ve NPS/DN haritalamaları kesin formüllerle korunur.

## 4. Asla Yerelde Derleme Yapma
* Ağır derlemeler yerel cihazda yapılmaz. CI/CD GitHub Actions üzerinden yürütülür.
