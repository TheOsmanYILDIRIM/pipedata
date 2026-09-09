# Faz 6: Kanonik Veri Mutabakatı Kontrol Listesi (Canonical Merge Audit)

**Proje:** Pipedata → PipeMatrix Pro
**Tarih:** 2026-09-09
**SON DURUM:** ✅ **KAPANDI — 32/32 test OK** (BUG-1..BUG-6 + ftsw8 borcu; kanıtlar backlog.md ve aşağıdaki tabloda)
**Strateji Kararı (uygulandığı haliyle):** her bileşen+ebat+rating için TEK satır. tbl_pipes: Imperial kazanan + fizik-doğrulamalı kolon fusion (orijinal app kanonik kaynağı, Benchmark A/D 54.62 lb/ft birebir); flanş/vana: Metric önceliği. App sorgu kodu DEĞİŞMEDİ.
**Doğrulama Doktrini:** Anti-Macro Fallacy + No Silent Fallback + Adversarial Subagent Verification (AGENTS.md §3)

---

## 1. Kanıtlanmış Hata Envanteri (Main Agent — ham CLI kanıtlarıyla)

| ID | Hata | Kanıt (sqlite3/csv) | Kök Neden | Durum |
|----|------|---------------------|-----------|-------|
| BUG-1 | Valve 800#/1500# sınıfları DB'de YOK | `GROUP BY rating` → 150/300/400/600/900/2500; 800 ve 1500 sıfır | `re.search(r'(150|300|400|600|900|1500|2500)')` alternasyonu soldan eşleşir: `vlv11500.csv → 150` (Python'da kanıtlandı) | AÇIK |
| BUG-2 | Buttweld imperial birim kaosu | Tee 1 1/2 STD: `outer_diameter_mm=1.9` (inç), `center_to_end_mm=32.512` (1.28 lb/ft × 25.4!), `weight_kg=4.7625` (3/16" kalınlık) | Imperial CSV kolon indeks eşlemesi yanlış (`parse_float(get_col(row,4))` vb.) | AÇIK |
| BUG-3 | Boru ağırlık tabloları buttweld'e karışmış | `source_file LIKE 'piwt%'` → 1534 satır, 2 dosya | piwt* dosyaları (NPS,DN,Diameter,Thickness,Weight,XS,Schedule,water,total,units) fitting değil | AÇIK |
| BUG-4 | Pipe 1/8" STD metrik anomali | Metrik: t=1.24mm, ID=8.5873 (10.3−2×1.24=7.82 ≠ 8.5873 — içsel tutarsız); İmperial: 0.068"=1.7272 ✓ | Orijinal app CSV satırı bozuk; ASME B36.10M STD=1.7272mm | AÇIK |
| BUG-5 | Metrik+İmperial çift kayıtlar | 648 flanş / 378 boru / 978 vana mükerrer grubu; app `LIMIT 1` + unit filtresi yok → rastgele satır | İki CSV seti de aynı tabloya ayrı satır olarak yükleniyor | AÇIK |
| BUG-6 | Line Blanks NULL cepleri | 1304 satırın 300'ünde tüm ağırlıklar NULL, 560'ında OD NULL | Kısmen orijinal N/A (meşru), kısmen eksik parse — kök neden ayrıştırılacak | AÇIK |
| BUG-7 | Audit raporu bayat | `ORIGINAL_APP_VS_SQLITE_MASTER_AUDIT_REPORT.md` (464 eksik) — Flanş 1/2" 1500# WN artık DOLU (doğrulandı) | Sonraki commit'ler raporu geçersiz kıldı | AÇIK |

**Önemli bulgu:** Flanş metrik 121.0 vs imperial 120.65 (=4.75"×25.4, ASME B16.5 tablo değeri) → metrik-öncelik kuralına rağmen >1mm çelişkiler **sessizce seçilmez**, anomaly raporuna loglanır.

---

## 2. Faz Takibi

### Faz A: TDD Regresyon Testleri — owner: `qa-test-engineer`
- [x] T1: Valve rating 800# ve 1500# mevcut; `vlv11500.csv → rating=1500`
- [x] T2: Buttweld'de `piwt%` kaynaklı satır = 0
- [x] T3: BW Tee 1 1/2 STD imperial: OD≈48mm; C2E kolonu ağırlık taşımıyor
- [x] T4: Pipe 1/8" STD tek satır, wall=1.7272±0.03; 14" STD benchmark korunuyor
- [x] T5: Canonical uniqueness — tbl_pipes/flanges/valves doğal anahtarlarında 0 mükerrer grup
- [x] T6: Birim karışımı regesyonu — buttweld OD<3mm yasak; weight_kg>2000 yasak (500+ kg meşru olabilir)
- [x] T7: Line Blanks NULL cep regesyonu (eşik, orijinal N/A analizi sonrası gerekçelendirilir)
- [x] T8: Flanş 1/2" 1500# WN tek satır + ASME B16.5 ground-truth (PCD 82.55-82.6, 4 cıvata)
- [x] Mevcut 24 testin assert'lerine dokunulmadı (sadece yeni TestCase)

### Faz B: Script Düzeltme + DB Rebuild — owner: `data-reconciler`
- [x] BUG-1: Rating çıkarımı "dosya adındaki SON sayı" yöntemiyle; 800# dahil
- [x] BUG-2: Imperial BW kolon eşlemesi semantik başlık haritasıyla yeniden kuruldu
- [x] BUG-3: piwt* dosyaları schema_manifest.json hedefine yönlendirildi (veya belgelenerek atlandı)
- [x] BUG-4: ID=OD−2t tutarlılık kuralı (tol %2) tüm pipe satırlarına; tutarsız metrik + tutarlı imperial → imperial'den mm dönüşümü
- [x] BUG-5: Canonical merge — doğal anahtar başına tek satır; `merged_from` raw_json'da; >1mm çelişki → anomaly raporu (sessiz seçim YASAK)
- [x] BUG-6: Line Blanks NULL kök nedeni ayrıştırıldı (meşru N/A vs parse hatası)
- [x] Rebuild çalıştırıldı (py-cool); 3 DB kopyası senkron (root + app/assets + Downloads, md5 eşit)
- [x] `reports/canonical_merge_anomaly_report.md` üretildi

### Faz C: Adversarial CLI Denetimi — owner: `qa-auditor` + Main Agent
- [x] Alt-ajan çıktıları ana ajan tarafından bağımsız sqlite3 sorgularıyla yeniden doğrulandı (kanıt: ftsw8 dolu=30, rating 800#=108/1500#=432, piwt=0, Reducer=1012, WN 1/2 1500=1)
- [x] `python3 test_suite.py` — T1-T8 dahil **32/32 PASS** (TDD kırmızı aşaması önceden kanıtlandı: 8/8 FAIL)
- [x] `md5sum` üç kopya eşit (3df2f12d...): root + app/assets + Downloads
- [x] Satır sayıları (canonical merge sonrası): flanges 654, pipes 379, valves 1148 — tip çeşitliliği korundu (7 vana, 3 flanş tipi; COUNT(DISTINCT) kanıtı)
- [x] App sorgu katmanı (`PipedataDbHelper.kt` LIMIT 1 sorguları, unit filtresi yok) yeni tekil satırlarla tutarlı — kod değişikliği GEREKTİRMİYOR
- [x] Fusion çelişkileri adversarial yeniden-inşası: 4473 kolon-çelişkisi → `reports/canonical_merge_fusion_conflicts.md` (sessiz seçim denetimi)

### Faz D: Dokümantasyon & Vault Senkronu — owner: Main Agent
- [x] `ORIGINAL_APP_VS_SQLITE_MASTER_AUDIT_REPORT.md` başlık raporu güncellendi (bayat sayılar işaretlendi)
- [x] `backlog.md` Faz 6 kaydı doğrulanmış kanıtlarla mühürlendi
- [x] AGY-Vault `10-Projects/` pipedata kaydı güncellendi (agy-vault script)
- [x] Bu checklist'in tüm kutuları kapatıldı; açık tek kalite-notu backlog'a mühürlendi (PSV model-çöküşü: psv.csv'de 6 farklı model satırı aynı doğal anahtara çöküyor — weight 31/56 kg seçim kaybı)

---

## 3. Standart Doğrulama Komutları (kopyala-çalıştır)

```bash
cd ~/projects/pipedata
# BUG-1: rating dağılımı 800 ve 1500 içermeli
sqlite3 pipedata.db "SELECT rating, COUNT(*) FROM tbl_valves_asme_b16_10 GROUP BY rating ORDER BY rating;"
# BUG-3: sıfır olmalı
sqlite3 pipedata.db "SELECT COUNT(*) FROM tbl_fittings_buttweld_b16_9 WHERE source_file LIKE 'piwt%';"
# BUG-5: üçü de sıfır olmalı
sqlite3 pipedata.db "SELECT 'flange',COUNT(*) FROM (SELECT 1 FROM tbl_flanges_asme_b16_5 GROUP BY nps,rating,flange_type HAVING COUNT(*)>1) UNION ALL SELECT 'pipe',COUNT(*) FROM (SELECT 1 FROM tbl_pipes GROUP BY nps,schedule HAVING COUNT(*)>1) UNION ALL SELECT 'valve',COUNT(*) FROM (SELECT 1 FROM tbl_valves_asme_b16_10 GROUP BY valve_type,nps,rating,end_connection HAVING COUNT(*)>1);"
# BUG-4: tek satır + 1.7272
sqlite3 -header pipedata.db "SELECT nps,schedule,wall_thickness_mm,inner_diameter_mm FROM tbl_pipes WHERE nps='1/8' AND schedule='STD';"
# Test kapısı
nice -n 15 taskset -c 0-5 python3 test_suite.py
# Senkron kanıtı
md5sum pipedata.db app/src/main/assets/pipedata.db /storage/emulated/0/Download/pipedata.db
```
