# Orijinal Uygulama (Pipedata Pro) vs. SQLite Kapsamlı Mutabakat Raporu

**Tarih:** 2026-09-08
**Toplam Karşılaştırılan Semantik Değer:** 5,726
**Birebir Eşleşme (Match):** 4,963 (%86.67)
**Uyuşmazlık (Mismatch):** 299
**SQLite'ta Eksik (Missing):** 464

---

## Kategori Bazında Uyum Tablosu

| Mühendislik Kategorisi | Toplam Değer | Eşleşen | Uyuşmazlık | Eksik | Uyum Oranı |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Flanges B16.5 | 1,062 | 910 | 0 | 152 | **%85.69** |
| Pipes B36.10M | 1,315 | 1,313 | 2 | 0 | **%99.85** |
| Valves B16.10 | 1,521 | 1,345 | 0 | 176 | **%88.43** |
| Buttweld Fittings B16.9 | 396 | 307 | 89 | 0 | **%77.53** |
| Socket Weld B16.11 | 648 | 617 | 28 | 3 | **%95.22** |
| Line Blanks B16.48 | 784 | 471 | 180 | 133 | **%60.08** |

---

## Uyuşmazlık ve Eksiklik Detayları

| Kategori | Bileşen / NPS | Parametre | Durum | Orijinal Değer | SQLite Değeri |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Flanges B16.5 | Flange 1/2 1500# WN | OD | `MISSING_IN_DB` | 121.0 | None |
| Flanges B16.5 | Flange 1/2 1500# WN | FlangeThickness_C | `MISSING_IN_DB` | 22.4 | None |
| Flanges B16.5 | Flange 1/2 1500# WN | Hub_X | `MISSING_IN_DB` | 38.0 | None |
| Flanges B16.5 | Flange 1/2 1500# WN | LengthThruHub_Y | `MISSING_IN_DB` | 60.0 | None |
| Flanges B16.5 | Flange 1/2 1500# WN | PCD | `MISSING_IN_DB` | 82.6 | None |
| Flanges B16.5 | Flange 1/2 1500# WN | NumBolts | `MISSING_IN_DB` | 4.0 | None |
| Flanges B16.5 | Flange 1/2 1500# WN | StudBoltLength | `MISSING_IN_DB` | 110.0 | None |
| Flanges B16.5 | Flange 1/2 1500# WN | Weight_WN | `MISSING_IN_DB` | 2.3 | None |
| Flanges B16.5 | Flange 3/4" 1500# WN | OD | `MISSING_IN_DB` | 130.0 | None |
| Flanges B16.5 | Flange 3/4" 1500# WN | FlangeThickness_C | `MISSING_IN_DB` | 25.4 | None |
| Flanges B16.5 | Flange 3/4" 1500# WN | Hub_X | `MISSING_IN_DB` | 44.0 | None |
| Flanges B16.5 | Flange 3/4" 1500# WN | LengthThruHub_Y | `MISSING_IN_DB` | 70.0 | None |
| Flanges B16.5 | Flange 3/4" 1500# WN | PCD | `MISSING_IN_DB` | 88.9 | None |
| Flanges B16.5 | Flange 3/4" 1500# WN | NumBolts | `MISSING_IN_DB` | 4.0 | None |
| Flanges B16.5 | Flange 3/4" 1500# WN | StudBoltLength | `MISSING_IN_DB` | 115.0 | None |
| Flanges B16.5 | Flange 3/4" 1500# WN | Weight_WN | `MISSING_IN_DB` | 2.7 | None |
| Flanges B16.5 | Flange 1" 1500# WN | OD | `MISSING_IN_DB` | 149.0 | None |
| Flanges B16.5 | Flange 1" 1500# WN | FlangeThickness_C | `MISSING_IN_DB` | 28.6 | None |
| Flanges B16.5 | Flange 1" 1500# WN | Hub_X | `MISSING_IN_DB` | 52.0 | None |
| Flanges B16.5 | Flange 1" 1500# WN | LengthThruHub_Y | `MISSING_IN_DB` | 73.0 | None |
| Flanges B16.5 | Flange 1" 1500# WN | PCD | `MISSING_IN_DB` | 101.6 | None |
| Flanges B16.5 | Flange 1" 1500# WN | NumBolts | `MISSING_IN_DB` | 4.0 | None |
| Flanges B16.5 | Flange 1" 1500# WN | StudBoltLength | `MISSING_IN_DB` | 125.0 | None |
| Flanges B16.5 | Flange 1" 1500# WN | Weight_WN | `MISSING_IN_DB` | 4.1 | None |
| Flanges B16.5 | Flange 1 1/4" 1500# WN | OD | `MISSING_IN_DB` | 159.0 | None |
| Pipes B36.10M | Pipe 1/8" Sch-STD | ID | `MISMATCH` | 8.5873 | 6.8326 |
| Pipes B36.10M | Pipe 1/8" Sch-XS | WallThk | `MISMATCH` | 1.24 | 2.413 |
| Valves B16.10 | Valve 1" 1500# Gate Valve | FaceToFace | `MISSING_IN_DB` | 254.0 | None |
| Valves B16.10 | Valve 1" 1500# Gate Valve | HandwheelHeight | `MISSING_IN_DB` | 460.0 | None |
| Valves B16.10 | Valve 1" 1500# Gate Valve | HandwheelDiameter | `MISSING_IN_DB` | 177.0 | None |
| Valves B16.10 | Valve 1" 1500# Gate Valve | Weight | `MISSING_IN_DB` | 38.0 | None |
| Valves B16.10 | Valve 1 1/4" 1500# Gate Valve | FaceToFace | `MISSING_IN_DB` | 279.0 | None |
| Valves B16.10 | Valve 1 1/4" 1500# Gate Valve | HandwheelHeight | `MISSING_IN_DB` | 500.0 | None |
| Valves B16.10 | Valve 1 1/4" 1500# Gate Valve | HandwheelDiameter | `MISSING_IN_DB` | 203.0 | None |
| Valves B16.10 | Valve 1 1/4" 1500# Gate Valve | Weight | `MISSING_IN_DB` | 49.0 | None |
| Valves B16.10 | Valve 1 1/2 1500# Gate Valve | FaceToFace | `MISSING_IN_DB` | 305.0 | None |
| Valves B16.10 | Valve 1 1/2 1500# Gate Valve | HandwheelHeight | `MISSING_IN_DB` | 570.0 | None |
| Valves B16.10 | Valve 1 1/2 1500# Gate Valve | HandwheelDiameter | `MISSING_IN_DB` | 254.0 | None |
| Valves B16.10 | Valve 1 1/2 1500# Gate Valve | Weight | `MISSING_IN_DB` | 61.0 | None |
| Valves B16.10 | Valve 2" 1500# Gate Valve | FaceToFace | `MISSING_IN_DB` | 368.0 | None |
| Valves B16.10 | Valve 2" 1500# Gate Valve | HandwheelHeight | `MISSING_IN_DB` | 559.0 | None |
| Valves B16.10 | Valve 2" 1500# Gate Valve | HandwheelDiameter | `MISSING_IN_DB` | 250.0 | None |
| Valves B16.10 | Valve 2" 1500# Gate Valve | Weight | `MISSING_IN_DB` | 82.0 | None |
| Valves B16.10 | Valve 2 1/2" 1500# Gate Valve | FaceToFace | `MISSING_IN_DB` | 419.0 | None |
| Valves B16.10 | Valve 2 1/2" 1500# Gate Valve | HandwheelHeight | `MISSING_IN_DB` | 673.0 | None |
| Valves B16.10 | Valve 2 1/2" 1500# Gate Valve | HandwheelDiameter | `MISSING_IN_DB` | 400.0 | None |
| Valves B16.10 | Valve 2 1/2" 1500# Gate Valve | Weight | `MISSING_IN_DB` | 127.0 | None |
| Valves B16.10 | Valve 3" 1500# Gate Valve | FaceToFace | `MISSING_IN_DB` | 470.0 | None |
| Valves B16.10 | Valve 3" 1500# Gate Valve | HandwheelHeight | `MISSING_IN_DB` | 711.0 | None |
| Valves B16.10 | Valve 3" 1500# Gate Valve | HandwheelDiameter | `MISSING_IN_DB` | 400.0 | None |
| Valves B16.10 | Valve 3" 1500# Gate Valve | Weight | `MISSING_IN_DB` | 168.0 | None |
| Valves B16.10 | Valve 4" 1500# Gate Valve | FaceToFace | `MISSING_IN_DB` | 546.0 | None |
| Buttweld Fittings B16.9 | BW Tee 1/2 Sch-STD | OD | `MISMATCH` | 21.0 | 0.84 |
| Buttweld Fittings B16.9 | BW Tee 1/2 Sch-STD | CenterToEnd | `MISMATCH` | 25.0 | 13.716 |
| Buttweld Fittings B16.9 | BW Tee 3/4" Sch-STD | OD | `MISMATCH` | 27.0 | 1.05 |
| Buttweld Fittings B16.9 | BW Tee 3/4" Sch-STD | CenterToEnd | `MISMATCH` | 29.0 | 17.525999999999996 |
| Buttweld Fittings B16.9 | BW Tee 1" Sch-STD | OD | `MISMATCH` | 33.0 | 1.315 |
| Buttweld Fittings B16.9 | BW Tee 1" Sch-STD | CenterToEnd | `MISMATCH` | 38.0 | 22.098 |
| Buttweld Fittings B16.9 | BW Tee 1 1/4" Sch-STD | OD | `MISMATCH` | 42.0 | 1.66 |
| Buttweld Fittings B16.9 | BW Tee 1 1/4" Sch-STD | CenterToEnd | `MISMATCH` | 48.0 | 28.194000000000003 |
| Buttweld Fittings B16.9 | BW Tee 1 1/2 Sch-STD | OD | `MISMATCH` | 48.0 | 1.9 |
| Buttweld Fittings B16.9 | BW Tee 1 1/2 Sch-STD | CenterToEnd | `MISMATCH` | 57.0 | 32.512 |
| Buttweld Fittings B16.9 | BW Tee 2" Sch-STD | OD | `MISMATCH` | 60.0 | 2.375 |
| Buttweld Fittings B16.9 | BW Tee 2" Sch-STD | CenterToEnd | `MISMATCH` | 64.0 | 40.894 |
| Buttweld Fittings B16.9 | BW Tee 2 1/2" Sch-STD | OD | `MISMATCH` | 73.0 | 2.875 |
| Buttweld Fittings B16.9 | BW Tee 2 1/2" Sch-STD | CenterToEnd | `MISMATCH` | 76.0 | 62.992 |
| Buttweld Fittings B16.9 | BW Tee 3" Sch-STD | OD | `MISMATCH` | 89.0 | 3.5 |
| Buttweld Fittings B16.9 | BW Tee 3" Sch-STD | CenterToEnd | `MISMATCH` | 86.0 | 76.96199999999999 |
| Buttweld Fittings B16.9 | BW Tee 3 1/2" Sch-STD | OD | `MISMATCH` | 102.0 | 4.0 |
| Buttweld Fittings B16.9 | BW Tee 3 1/2" Sch-STD | CenterToEnd | `MISMATCH` | 95.0 | 88.392 |
| Buttweld Fittings B16.9 | BW Tee 3 1/2" Sch-STD | Weight | `MISMATCH` | 5.18 | 3.48 |
| Buttweld Fittings B16.9 | BW Tee 4" Sch-STD | OD | `MISMATCH` | 114.0 | 4.5 |
| Buttweld Fittings B16.9 | BW Tee 4" Sch-STD | CenterToEnd | `MISMATCH` | 105.0 | 99.568 |
| Buttweld Fittings B16.9 | BW Tee 4" Sch-STD | Weight | `MISMATCH` | 6.0 | 3.92 |
| Buttweld Fittings B16.9 | BW Tee 5 Sch-STD | OD | `MISMATCH` | 141.0 | 5.563 |
| Buttweld Fittings B16.9 | BW Tee 5 Sch-STD | Weight | `MISMATCH` | 9.94 | 4.86 |
| Buttweld Fittings B16.9 | BW Tee 6 Sch-STD | OD | `MISMATCH` | 168.0 | 6.625 |
| Socket Weld B16.11 | SW 90° Elbow 2" 3000# | Weight | `MISMATCH` | 1.54 | 0.6985330805308853 |
| Socket Weld B16.11 | SW 90° Elbow 2 1/2" 3000# | Weight | `MISMATCH` | 2.46 | 1.1158385572116738 |
| Socket Weld B16.11 | SW 90° Elbow 3" 3000# | Weight | `MISMATCH` | 3.93 | 1.7826201340820642 |
| Socket Weld B16.11 | SW 90° Elbow 4" 3000# | Weight | `MISMATCH` | 6.29 | 2.853099400350174 |
| Socket Weld B16.11 | SW 90° Elbow 2" 6000# | Weight | `MISMATCH` | 2.31 | 1.0477996207963278 |
| Socket Weld B16.11 | SW 45° Elbow 2 1/2" 3000# | Weight | `MISMATCH` | 2.21 | 1.0024403298527638 |
| Socket Weld B16.11 | SW 45° Elbow 3" 3000# | Weight | `MISMATCH` | 3.53 | 1.6011829703078082 |
| Socket Weld B16.11 | SW 45° Elbow 4" 3000# | Weight | `MISMATCH` | 5.66 | 2.567335867405721 |
| Socket Weld B16.11 | SW 45° Elbow 2" 6000# | Weight | `MISMATCH` | 2.08 | 0.9434732516261307 |
| Socket Weld B16.11 | SW Tee 2" 3000# | Weight | `MISMATCH` | 2.16 | 0.979760684380982 |
| Socket Weld B16.11 | SW Tee 2 1/2" 3000# | Weight | `MISMATCH` | 3.44 | 1.5603596084586007 |
| Socket Weld B16.11 | SW Tee 3" 3000# | Weight | `MISMATCH` | 5.5 | 2.4947610018960185 |
| Socket Weld B16.11 | SW Tee 4" 3000# | Weight | `MISMATCH` | 8.8 | 3.99161760303363 |
| Socket Weld B16.11 | SW Tee 1 1/2 6000# | Weight | `MISMATCH` | 2.02 | 0.9162576770599923 |
| Socket Weld B16.11 | SW Tee 2" 6000# | Weight | `MISMATCH` | 3.23 | 1.4651050974771163 |
| Socket Weld B16.11 | SW Cross 1 1/2 3000# | Weight | `MISMATCH` | 1.69 | 0.7665720169462311 |
| Socket Weld B16.11 | SW Cross 2" 3000# | Weight | `MISMATCH` | 2.71 | 1.2292367845705836 |
| Socket Weld B16.11 | SW Cross 2 1/2" 3000# | Weight | `MISMATCH` | 4.33 | 1.9640572978563202 |
| Socket Weld B16.11 | SW Cross 3" 3000# | Weight | `MISMATCH` | 6.92 | 3.138862933294627 |
| Socket Weld B16.11 | SW Cross 4" 3000# | Weight | `MISMATCH` | 11.08 | 5.025809436546888 |
| Socket Weld B16.11 | SW Full Coupling 2 1/2" 3000# | Weight | `MISMATCH` | 1.47 | 0.6667815768703904 |
| Socket Weld B16.11 | SW Full Coupling 3" 3000# | Weight | `MISMATCH` | 2.35 | 1.0659433371737534 |
| Socket Weld B16.11 | SW Full Coupling 4" 3000# | Weight | `MISMATCH` | 3.77 | 1.710045268572362 |
