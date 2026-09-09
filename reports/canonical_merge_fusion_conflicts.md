# Fusion Celiski Yeniden-Insasi (Faz 6, eski DB backup vs merge sonrasi DB)

Kural: merged_from kaynaklarindan dusurulen satirlarin mm kolonlari, kazanan satirla >1mm celişiyorsa asagida listelenir (sessiz secim denetimi).

## tbl_pipes

| nps | schedule | kolon | dusurulen | kazanan | fark |
|---|---|---|---|---|---|
| 1/8 | STD | inner_diameter_mm | 8.587 | 6.833 | +1.755 |
| 1/8 | XS | wall_thickness_mm | 1.240 | 2.413 | -1.173 |

_tbl_pipes: 2 celişki satiri_

## tbl_flanges_asme_b16_5

| nps | rating | flange_type | kolon | dusurulen | kazanan | fark |
|---|---|---|---|---|---|---|
| 26 | 150 | Welding Neck | bore_diameter_mm | 16935.450 | 666.750 | +16268.700 |
| 26 | 150 | Welding Neck | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 26 | 150 | Welding Neck | outer_diameter_mm | 785.000 | 870.000 | -85.000 |
| 26 | 150 | Welding Neck | thickness_mm | 39.800 | 66.700 | -26.900 |
| 26 | 150 | Welding Neck | hub_diameter_mm | 684.000 | 676.000 | +8.000 |
| 26 | 150 | Welding Neck | length_thru_hub_mm | 39.800 | 119.000 | -79.200 |
| 26 | 150 | Welding Neck | bore_diameter_mm | 641.350 | 666.750 | -25.400 |
| 26 | 150 | Welding Neck | rf_diameter_mm | 711.000 | 749.000 | -38.000 |
| 26 | 150 | Welding Neck | pcd_mm | 744.500 | 806.400 | -61.900 |
| 26 | 150 | Welding Neck | stud_bolt_length_mm | 140.000 | 220.000 | -80.000 |
| 26 | 150 | Welding Neck | weight_kg | 54.000 | 136.000 | -82.000 |
| 26 | 150 | Welding Neck | outer_diameter_mm | 785.876 | 870.000 | -84.124 |
| 26 | 150 | Welding Neck | thickness_mm | 39.878 | 66.700 | -26.822 |
| 26 | 150 | Welding Neck | hub_diameter_mm | 684.276 | 676.000 | +8.276 |
| 26 | 150 | Welding Neck | length_thru_hub_mm | 39.878 | 119.000 | -79.122 |
| 26 | 150 | Welding Neck | bore_diameter_mm | 16290.290 | 666.750 | +15623.540 |
| 26 | 150 | Welding Neck | rf_diameter_mm | 711.200 | 749.000 | -37.800 |
| 26 | 150 | Welding Neck | pcd_mm | 744.474 | 806.400 | -61.926 |
| 26 | 150 | Welding Neck | stud_bolt_length_mm | 139.700 | 220.000 | -80.300 |
| 26 | 150 | Welding Neck | weight_kg | 54.000 | 136.000 | -82.000 |
| 26 | 150 | Slip On | length_thru_hub_mm | 2177.415 | 85.725 | +2091.690 |
| 26 | 150 | Slip On | bore_diameter_mm | 16935.450 | 666.750 | +16268.700 |
| 26 | 150 | Slip On | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 26 | 150 | Slip On | outer_diameter_mm | 785.000 | 870.000 | -85.000 |
| 26 | 150 | Slip On | thickness_mm | 39.800 | 66.700 | -26.900 |
| 26 | 150 | Slip On | hub_diameter_mm | 684.000 | 676.000 | +8.000 |
| 26 | 150 | Slip On | length_thru_hub_mm | 39.800 | 85.725 | -45.925 |
| 26 | 150 | Slip On | bore_diameter_mm | 641.350 | 666.750 | -25.400 |
| 26 | 150 | Slip On | rf_diameter_mm | 711.000 | 749.000 | -38.000 |
| 26 | 150 | Slip On | pcd_mm | 744.500 | 806.400 | -61.900 |
| 26 | 150 | Slip On | stud_bolt_length_mm | 140.000 | 220.000 | -80.000 |
| 26 | 150 | Slip On | outer_diameter_mm | 785.876 | 870.000 | -84.124 |
| 26 | 150 | Slip On | thickness_mm | 39.878 | 66.700 | -26.822 |
| 26 | 150 | Slip On | hub_diameter_mm | 684.276 | 676.000 | +8.276 |
| 26 | 150 | Slip On | length_thru_hub_mm | 39.878 | 85.725 | -45.847 |
| 26 | 150 | Slip On | bore_diameter_mm | 16290.290 | 666.750 | +15623.540 |
| 26 | 150 | Slip On | rf_diameter_mm | 711.200 | 749.000 | -37.800 |
| 26 | 150 | Slip On | pcd_mm | 744.474 | 806.400 | -61.926 |
| 26 | 150 | Slip On | stud_bolt_length_mm | 139.700 | 220.000 | -80.300 |
| 26 | 150 | Blind | bore_diameter_mm | 16935.450 | 666.750 | +16268.700 |
| 26 | 150 | Blind | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 26 | 150 | Blind | outer_diameter_mm | 785.000 | 870.000 | -85.000 |
| 26 | 150 | Blind | thickness_mm | 43.000 | 66.700 | -23.700 |
| 26 | 150 | Blind | hub_diameter_mm | 684.000 | 676.000 | +8.000 |
| 26 | 150 | Blind | length_thru_hub_mm | 43.000 | 66.700 | -23.700 |
| 26 | 150 | Blind | bore_diameter_mm | 641.350 | 666.750 | -25.400 |
| 26 | 150 | Blind | rf_diameter_mm | 711.000 | 749.000 | -38.000 |
| 26 | 150 | Blind | pcd_mm | 744.500 | 806.400 | -61.900 |
| 26 | 150 | Blind | stud_bolt_length_mm | 140.000 | 220.000 | -80.000 |
| 26 | 150 | Blind | weight_kg | 169.000 | 318.000 | -149.000 |
| 26 | 150 | Blind | outer_diameter_mm | 785.876 | 870.000 | -84.124 |
| 26 | 150 | Blind | thickness_mm | 42.926 | 66.700 | -23.774 |
| 26 | 150 | Blind | hub_diameter_mm | 684.276 | 676.000 | +8.276 |
| 26 | 150 | Blind | length_thru_hub_mm | 42.926 | 66.700 | -23.774 |
| 26 | 150 | Blind | bore_diameter_mm | 16290.290 | 666.750 | +15623.540 |
| 26 | 150 | Blind | rf_diameter_mm | 711.200 | 749.000 | -37.800 |
| 26 | 150 | Blind | pcd_mm | 744.474 | 806.400 | -61.926 |
| 26 | 150 | Blind | stud_bolt_length_mm | 139.700 | 220.000 | -80.300 |
| 26 | 150 | Blind | weight_kg | 169.000 | 318.000 | -149.000 |
| 28 | 150 | Welding Neck | outer_diameter_mm | 927.100 | 925.000 | +2.100 |
| 28 | 150 | Welding Neck | bore_diameter_mm | 18225.770 | 717.550 | +17508.220 |
| 28 | 150 | Welding Neck | stud_bolt_length_mm | 234.950 | 230.000 | +4.950 |
| 28 | 150 | Welding Neck | outer_diameter_mm | 835.000 | 925.000 | -90.000 |
| 28 | 150 | Welding Neck | thickness_mm | 43.000 | 69.900 | -26.900 |
| 28 | 150 | Welding Neck | hub_diameter_mm | 735.000 | 727.000 | +8.000 |
| 28 | 150 | Welding Neck | length_thru_hub_mm | 43.000 | 124.000 | -81.000 |
| 28 | 150 | Welding Neck | bore_diameter_mm | 692.150 | 717.550 | -25.400 |
| 28 | 150 | Welding Neck | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 150 | Welding Neck | pcd_mm | 795.300 | 863.600 | -68.300 |
| 28 | 150 | Welding Neck | stud_bolt_length_mm | 145.000 | 230.000 | -85.000 |
| 28 | 150 | Welding Neck | weight_kg | 63.000 | 156.000 | -93.000 |
| 28 | 150 | Welding Neck | outer_diameter_mm | 836.676 | 925.000 | -88.324 |
| 28 | 150 | Welding Neck | thickness_mm | 42.926 | 69.900 | -26.974 |
| 28 | 150 | Welding Neck | hub_diameter_mm | 735.076 | 727.000 | +8.076 |
| 28 | 150 | Welding Neck | length_thru_hub_mm | 42.926 | 124.000 | -81.074 |
| 28 | 150 | Welding Neck | bore_diameter_mm | 17580.610 | 717.550 | +16863.060 |
| 28 | 150 | Welding Neck | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 150 | Welding Neck | pcd_mm | 795.274 | 863.600 | -68.326 |
| 28 | 150 | Welding Neck | stud_bolt_length_mm | 146.050 | 230.000 | -83.950 |
| 28 | 150 | Welding Neck | weight_kg | 63.000 | 156.000 | -93.000 |
| 28 | 150 | Slip On | outer_diameter_mm | 927.100 | 925.000 | +2.100 |
| 28 | 150 | Slip On | length_thru_hub_mm | 1572.577 | 61.913 | +1510.665 |
| 28 | 150 | Slip On | bore_diameter_mm | 18225.770 | 717.550 | +17508.220 |
| 28 | 150 | Slip On | stud_bolt_length_mm | 234.950 | 230.000 | +4.950 |
| 28 | 150 | Slip On | outer_diameter_mm | 835.000 | 925.000 | -90.000 |
| 28 | 150 | Slip On | thickness_mm | 43.000 | 69.900 | -26.900 |
| 28 | 150 | Slip On | hub_diameter_mm | 735.000 | 727.000 | +8.000 |
| 28 | 150 | Slip On | length_thru_hub_mm | 43.000 | 61.913 | -18.913 |
| 28 | 150 | Slip On | bore_diameter_mm | 692.150 | 717.550 | -25.400 |
| 28 | 150 | Slip On | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 150 | Slip On | pcd_mm | 795.300 | 863.600 | -68.300 |
| 28 | 150 | Slip On | stud_bolt_length_mm | 145.000 | 230.000 | -85.000 |
| 28 | 150 | Slip On | outer_diameter_mm | 836.676 | 925.000 | -88.324 |
| 28 | 150 | Slip On | thickness_mm | 42.926 | 69.900 | -26.974 |
| 28 | 150 | Slip On | hub_diameter_mm | 735.076 | 727.000 | +8.076 |
| 28 | 150 | Slip On | length_thru_hub_mm | 42.926 | 61.913 | -18.987 |
| 28 | 150 | Slip On | bore_diameter_mm | 17580.610 | 717.550 | +16863.060 |
| 28 | 150 | Slip On | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 150 | Slip On | pcd_mm | 795.274 | 863.600 | -68.326 |
| 28 | 150 | Slip On | stud_bolt_length_mm | 146.050 | 230.000 | -83.950 |
| 28 | 150 | Blind | outer_diameter_mm | 927.100 | 925.000 | +2.100 |
| 28 | 150 | Blind | bore_diameter_mm | 18225.770 | 717.550 | +17508.220 |
| 28 | 150 | Blind | stud_bolt_length_mm | 234.950 | 230.000 | +4.950 |
| 28 | 150 | Blind | outer_diameter_mm | 835.000 | 925.000 | -90.000 |
| 28 | 150 | Blind | thickness_mm | 46.200 | 69.900 | -23.700 |
| 28 | 150 | Blind | hub_diameter_mm | 735.000 | 727.000 | +8.000 |
| 28 | 150 | Blind | length_thru_hub_mm | 46.200 | 69.900 | -23.700 |
| 28 | 150 | Blind | bore_diameter_mm | 692.150 | 717.550 | -25.400 |
| 28 | 150 | Blind | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 150 | Blind | pcd_mm | 795.300 | 863.600 | -68.300 |
| 28 | 150 | Blind | stud_bolt_length_mm | 145.000 | 230.000 | -85.000 |
| 28 | 150 | Blind | weight_kg | 205.000 | 377.000 | -172.000 |
| 28 | 150 | Blind | outer_diameter_mm | 836.676 | 925.000 | -88.324 |
| 28 | 150 | Blind | thickness_mm | 46.228 | 69.900 | -23.672 |
| 28 | 150 | Blind | hub_diameter_mm | 735.076 | 727.000 | +8.076 |
| 28 | 150 | Blind | length_thru_hub_mm | 46.228 | 69.900 | -23.672 |
| 28 | 150 | Blind | bore_diameter_mm | 17580.610 | 717.550 | +16863.060 |
| 28 | 150 | Blind | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 150 | Blind | pcd_mm | 795.274 | 863.600 | -68.326 |
| 28 | 150 | Blind | stud_bolt_length_mm | 146.050 | 230.000 | -83.950 |
| 28 | 150 | Blind | weight_kg | 205.000 | 377.000 | -172.000 |
| 30 | 150 | Welding Neck | bore_diameter_mm | 19516.090 | 768.350 | +18747.740 |
| 30 | 150 | Welding Neck | stud_bolt_length_mm | 241.300 | 235.000 | +6.300 |
| 30 | 150 | Welding Neck | outer_diameter_mm | 885.000 | 985.000 | -100.000 |
| 30 | 150 | Welding Neck | thickness_mm | 43.000 | 73.100 | -30.100 |
| 30 | 150 | Welding Neck | hub_diameter_mm | 787.000 | 781.000 | +6.000 |
| 30 | 150 | Welding Neck | length_thru_hub_mm | 43.000 | 135.000 | -92.000 |
| 30 | 150 | Welding Neck | bore_diameter_mm | 742.950 | 768.350 | -25.400 |
| 30 | 150 | Welding Neck | rf_diameter_mm | 813.000 | 857.000 | -44.000 |
| 30 | 150 | Welding Neck | pcd_mm | 846.100 | 914.400 | -68.300 |
| 30 | 150 | Welding Neck | stud_bolt_length_mm | 150.000 | 235.000 | -85.000 |
| 30 | 150 | Welding Neck | weight_kg | 68.000 | 181.000 | -113.000 |
| 30 | 150 | Welding Neck | outer_diameter_mm | 887.476 | 985.000 | -97.524 |
| 30 | 150 | Welding Neck | thickness_mm | 42.926 | 73.100 | -30.174 |
| 30 | 150 | Welding Neck | hub_diameter_mm | 787.400 | 781.000 | +6.400 |
| 30 | 150 | Welding Neck | length_thru_hub_mm | 42.926 | 135.000 | -92.074 |
| 30 | 150 | Welding Neck | bore_diameter_mm | 18870.930 | 768.350 | +18102.580 |
| 30 | 150 | Welding Neck | rf_diameter_mm | 812.800 | 857.000 | -44.200 |
| 30 | 150 | Welding Neck | pcd_mm | 846.074 | 914.400 | -68.326 |
| 30 | 150 | Welding Neck | stud_bolt_length_mm | 152.400 | 235.000 | -82.600 |
| 30 | 150 | Welding Neck | weight_kg | 68.000 | 181.000 | -113.000 |
| 30 | 150 | Slip On | length_thru_hub_mm | 2258.060 | 88.900 | +2169.160 |
| 30 | 150 | Slip On | bore_diameter_mm | 19516.090 | 768.350 | +18747.740 |
| 30 | 150 | Slip On | stud_bolt_length_mm | 241.300 | 235.000 | +6.300 |
| 30 | 150 | Slip On | outer_diameter_mm | 885.000 | 985.000 | -100.000 |
| 30 | 150 | Slip On | thickness_mm | 43.000 | 73.100 | -30.100 |
| 30 | 150 | Slip On | hub_diameter_mm | 787.000 | 781.000 | +6.000 |
| 30 | 150 | Slip On | length_thru_hub_mm | 43.000 | 88.900 | -45.900 |
| 30 | 150 | Slip On | bore_diameter_mm | 742.950 | 768.350 | -25.400 |
| 30 | 150 | Slip On | rf_diameter_mm | 813.000 | 857.000 | -44.000 |
| 30 | 150 | Slip On | pcd_mm | 846.100 | 914.400 | -68.300 |
| 30 | 150 | Slip On | stud_bolt_length_mm | 150.000 | 235.000 | -85.000 |
| 30 | 150 | Slip On | outer_diameter_mm | 887.476 | 985.000 | -97.524 |
| 30 | 150 | Slip On | thickness_mm | 42.926 | 73.100 | -30.174 |
| 30 | 150 | Slip On | hub_diameter_mm | 787.400 | 781.000 | +6.400 |
| 30 | 150 | Slip On | length_thru_hub_mm | 42.926 | 88.900 | -45.974 |
| 30 | 150 | Slip On | bore_diameter_mm | 18870.930 | 768.350 | +18102.580 |
| 30 | 150 | Slip On | rf_diameter_mm | 812.800 | 857.000 | -44.200 |
| 30 | 150 | Slip On | pcd_mm | 846.074 | 914.400 | -68.326 |
| 30 | 150 | Slip On | stud_bolt_length_mm | 152.400 | 235.000 | -82.600 |
| 30 | 150 | Blind | bore_diameter_mm | 19516.090 | 768.350 | +18747.740 |
| 30 | 150 | Blind | stud_bolt_length_mm | 241.300 | 235.000 | +6.300 |
| 30 | 150 | Blind | outer_diameter_mm | 885.000 | 985.000 | -100.000 |
| 30 | 150 | Blind | thickness_mm | 49.300 | 73.100 | -23.800 |
| 30 | 150 | Blind | hub_diameter_mm | 787.000 | 781.000 | +6.000 |
| 30 | 150 | Blind | length_thru_hub_mm | 49.300 | 73.100 | -23.800 |
| 30 | 150 | Blind | bore_diameter_mm | 742.950 | 768.350 | -25.400 |
| 30 | 150 | Blind | rf_diameter_mm | 813.000 | 857.000 | -44.000 |
| 30 | 150 | Blind | pcd_mm | 846.100 | 914.400 | -68.300 |
| 30 | 150 | Blind | stud_bolt_length_mm | 150.000 | 235.000 | -85.000 |
| 30 | 150 | Blind | weight_kg | 246.000 | 445.000 | -199.000 |
| 30 | 150 | Blind | outer_diameter_mm | 887.476 | 985.000 | -97.524 |
| 30 | 150 | Blind | thickness_mm | 49.276 | 73.100 | -23.824 |
| 30 | 150 | Blind | hub_diameter_mm | 787.400 | 781.000 | +6.400 |
| 30 | 150 | Blind | length_thru_hub_mm | 49.276 | 73.100 | -23.824 |
| 30 | 150 | Blind | bore_diameter_mm | 18870.930 | 768.350 | +18102.580 |
| 30 | 150 | Blind | rf_diameter_mm | 812.800 | 857.000 | -44.200 |
| 30 | 150 | Blind | pcd_mm | 846.074 | 914.400 | -68.326 |
| 30 | 150 | Blind | stud_bolt_length_mm | 152.400 | 235.000 | -82.600 |
| 30 | 150 | Blind | weight_kg | 246.000 | 445.000 | -199.000 |
| 32 | 150 | Welding Neck | bore_diameter_mm | 20806.410 | 819.150 | +19987.260 |
| 32 | 150 | Welding Neck | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 32 | 150 | Welding Neck | outer_diameter_mm | 940.000 | 1060.000 | -120.000 |
| 32 | 150 | Welding Neck | thickness_mm | 44.600 | 79.400 | -34.800 |
| 32 | 150 | Welding Neck | hub_diameter_mm | 840.000 | 832.000 | +8.000 |
| 32 | 150 | Welding Neck | length_thru_hub_mm | 44.600 | 143.000 | -98.400 |
| 32 | 150 | Welding Neck | bore_diameter_mm | 793.750 | 819.150 | -25.400 |
| 32 | 150 | Welding Neck | rf_diameter_mm | 864.000 | 914.000 | -50.000 |
| 32 | 150 | Welding Neck | pcd_mm | 900.100 | 977.900 | -77.800 |
| 32 | 150 | Welding Neck | stud_bolt_length_mm | 160.000 | 265.000 | -105.000 |
| 32 | 150 | Welding Neck | weight_kg | 77.000 | 229.000 | -152.000 |
| 32 | 150 | Welding Neck | outer_diameter_mm | 941.324 | 1060.000 | -118.676 |
| 32 | 150 | Welding Neck | thickness_mm | 44.450 | 79.400 | -34.950 |
| 32 | 150 | Welding Neck | hub_diameter_mm | 839.724 | 832.000 | +7.724 |
| 32 | 150 | Welding Neck | length_thru_hub_mm | 44.450 | 143.000 | -98.550 |
| 32 | 150 | Welding Neck | bore_diameter_mm | 20161.250 | 819.150 | +19342.100 |
| 32 | 150 | Welding Neck | rf_diameter_mm | 863.600 | 914.000 | -50.400 |
| 32 | 150 | Welding Neck | pcd_mm | 900.176 | 977.900 | -77.724 |
| 32 | 150 | Welding Neck | stud_bolt_length_mm | 158.750 | 265.000 | -106.250 |
| 32 | 150 | Welding Neck | weight_kg | 77.000 | 229.000 | -152.000 |
| 32 | 150 | Slip On | length_thru_hub_mm | 2338.705 | 92.075 | +2246.630 |
| 32 | 150 | Slip On | bore_diameter_mm | 20806.410 | 819.150 | +19987.260 |
| 32 | 150 | Slip On | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 32 | 150 | Slip On | outer_diameter_mm | 940.000 | 1060.000 | -120.000 |
| 32 | 150 | Slip On | thickness_mm | 44.600 | 79.400 | -34.800 |
| 32 | 150 | Slip On | hub_diameter_mm | 840.000 | 832.000 | +8.000 |
| 32 | 150 | Slip On | length_thru_hub_mm | 44.600 | 92.075 | -47.475 |
| 32 | 150 | Slip On | bore_diameter_mm | 793.750 | 819.150 | -25.400 |
| 32 | 150 | Slip On | rf_diameter_mm | 864.000 | 914.000 | -50.000 |
| 32 | 150 | Slip On | pcd_mm | 900.100 | 977.900 | -77.800 |
| 32 | 150 | Slip On | stud_bolt_length_mm | 160.000 | 265.000 | -105.000 |
| 32 | 150 | Slip On | outer_diameter_mm | 941.324 | 1060.000 | -118.676 |
| 32 | 150 | Slip On | thickness_mm | 44.450 | 79.400 | -34.950 |
| 32 | 150 | Slip On | hub_diameter_mm | 839.724 | 832.000 | +7.724 |
| 32 | 150 | Slip On | length_thru_hub_mm | 44.450 | 92.075 | -47.625 |
| 32 | 150 | Slip On | bore_diameter_mm | 20161.250 | 819.150 | +19342.100 |
| 32 | 150 | Slip On | rf_diameter_mm | 863.600 | 914.000 | -50.400 |
| 32 | 150 | Slip On | pcd_mm | 900.176 | 977.900 | -77.724 |
| 32 | 150 | Slip On | stud_bolt_length_mm | 158.750 | 265.000 | -106.250 |
| 32 | 150 | Blind | bore_diameter_mm | 20806.410 | 819.150 | +19987.260 |
| 32 | 150 | Blind | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 32 | 150 | Blind | outer_diameter_mm | 940.000 | 1060.000 | -120.000 |
| 32 | 150 | Blind | thickness_mm | 52.500 | 79.400 | -26.900 |
| 32 | 150 | Blind | hub_diameter_mm | 840.000 | 832.000 | +8.000 |
| 32 | 150 | Blind | length_thru_hub_mm | 52.500 | 79.400 | -26.900 |
| 32 | 150 | Blind | bore_diameter_mm | 793.750 | 819.150 | -25.400 |
| 32 | 150 | Blind | rf_diameter_mm | 864.000 | 914.000 | -50.000 |
| 32 | 150 | Blind | pcd_mm | 900.100 | 977.900 | -77.800 |
| 32 | 150 | Blind | stud_bolt_length_mm | 160.000 | 265.000 | -105.000 |
| 32 | 150 | Blind | weight_kg | 293.000 | 561.000 | -268.000 |
| 32 | 150 | Blind | outer_diameter_mm | 941.324 | 1060.000 | -118.676 |
| 32 | 150 | Blind | thickness_mm | 52.578 | 79.400 | -26.822 |
| 32 | 150 | Blind | hub_diameter_mm | 839.724 | 832.000 | +7.724 |
| 32 | 150 | Blind | length_thru_hub_mm | 52.578 | 79.400 | -26.822 |
| 32 | 150 | Blind | bore_diameter_mm | 20161.250 | 819.150 | +19342.100 |
| 32 | 150 | Blind | rf_diameter_mm | 863.600 | 914.000 | -50.400 |
| 32 | 150 | Blind | pcd_mm | 900.176 | 977.900 | -77.724 |
| 32 | 150 | Blind | stud_bolt_length_mm | 158.750 | 265.000 | -106.250 |
| 32 | 150 | Blind | weight_kg | 293.000 | 561.000 | -268.000 |
| 34 | 150 | Welding Neck | outer_diameter_mm | 1111.250 | 1110.000 | +1.250 |
| 34 | 150 | Welding Neck | bore_diameter_mm | 22096.730 | 869.950 | +21226.780 |
| 34 | 150 | Welding Neck | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 34 | 150 | Welding Neck | outer_diameter_mm | 1005.000 | 1110.000 | -105.000 |
| 34 | 150 | Welding Neck | thickness_mm | 47.700 | 81.000 | -33.300 |
| 34 | 150 | Welding Neck | hub_diameter_mm | 892.000 | 883.000 | +9.000 |
| 34 | 150 | Welding Neck | length_thru_hub_mm | 47.700 | 148.000 | -100.300 |
| 34 | 150 | Welding Neck | bore_diameter_mm | 844.550 | 869.950 | -25.400 |
| 34 | 150 | Welding Neck | rf_diameter_mm | 921.000 | 965.000 | -44.000 |
| 34 | 150 | Welding Neck | pcd_mm | 957.300 | 1028.700 | -71.400 |
| 34 | 150 | Welding Neck | stud_bolt_length_mm | 170.000 | 265.000 | -95.000 |
| 34 | 150 | Welding Neck | weight_kg | 95.000 | 244.000 | -149.000 |
| 34 | 150 | Welding Neck | outer_diameter_mm | 1004.824 | 1110.000 | -105.176 |
| 34 | 150 | Welding Neck | thickness_mm | 47.752 | 81.000 | -33.248 |
| 34 | 150 | Welding Neck | hub_diameter_mm | 892.048 | 883.000 | +9.048 |
| 34 | 150 | Welding Neck | length_thru_hub_mm | 47.752 | 148.000 | -100.248 |
| 34 | 150 | Welding Neck | bore_diameter_mm | 21451.570 | 869.950 | +20581.620 |
| 34 | 150 | Welding Neck | rf_diameter_mm | 920.750 | 965.000 | -44.250 |
| 34 | 150 | Welding Neck | pcd_mm | 957.326 | 1028.700 | -71.374 |
| 34 | 150 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 0.000 | +25.400 |
| 34 | 150 | Welding Neck | stud_bolt_length_mm | 171.450 | 265.000 | -93.550 |
| 34 | 150 | Welding Neck | weight_kg | 95.000 | 244.000 | -149.000 |
| 34 | 150 | Slip On | outer_diameter_mm | 1111.250 | 1110.000 | +1.250 |
| 34 | 150 | Slip On | length_thru_hub_mm | 2379.027 | 93.662 | +2285.365 |
| 34 | 150 | Slip On | bore_diameter_mm | 22096.730 | 869.950 | +21226.780 |
| 34 | 150 | Slip On | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 34 | 150 | Slip On | outer_diameter_mm | 1005.000 | 1110.000 | -105.000 |
| 34 | 150 | Slip On | thickness_mm | 47.700 | 81.000 | -33.300 |
| 34 | 150 | Slip On | hub_diameter_mm | 892.000 | 883.000 | +9.000 |
| 34 | 150 | Slip On | length_thru_hub_mm | 47.700 | 93.662 | -45.962 |
| 34 | 150 | Slip On | bore_diameter_mm | 844.550 | 869.950 | -25.400 |
| 34 | 150 | Slip On | rf_diameter_mm | 921.000 | 965.000 | -44.000 |
| 34 | 150 | Slip On | pcd_mm | 957.300 | 1028.700 | -71.400 |
| 34 | 150 | Slip On | stud_bolt_length_mm | 170.000 | 265.000 | -95.000 |
| 34 | 150 | Slip On | outer_diameter_mm | 1004.824 | 1110.000 | -105.176 |
| 34 | 150 | Slip On | thickness_mm | 47.752 | 81.000 | -33.248 |
| 34 | 150 | Slip On | hub_diameter_mm | 892.048 | 883.000 | +9.048 |
| 34 | 150 | Slip On | length_thru_hub_mm | 47.752 | 93.662 | -45.910 |
| 34 | 150 | Slip On | bore_diameter_mm | 21451.570 | 869.950 | +20581.620 |
| 34 | 150 | Slip On | rf_diameter_mm | 920.750 | 965.000 | -44.250 |
| 34 | 150 | Slip On | pcd_mm | 957.326 | 1028.700 | -71.374 |
| 34 | 150 | Slip On | bolt_hole_diameter_mm | 25.400 | 0.000 | +25.400 |
| 34 | 150 | Slip On | stud_bolt_length_mm | 171.450 | 265.000 | -93.550 |
| 34 | 150 | Blind | outer_diameter_mm | 1111.250 | 1110.000 | +1.250 |
| 34 | 150 | Blind | bore_diameter_mm | 22096.730 | 869.950 | +21226.780 |
| 34 | 150 | Blind | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 34 | 150 | Blind | outer_diameter_mm | 1005.000 | 1110.000 | -105.000 |
| 34 | 150 | Blind | thickness_mm | 55.700 | 81.000 | -25.300 |
| 34 | 150 | Blind | hub_diameter_mm | 892.000 | 883.000 | +9.000 |
| 34 | 150 | Blind | length_thru_hub_mm | 55.700 | 81.000 | -25.300 |
| 34 | 150 | Blind | bore_diameter_mm | 844.550 | 869.950 | -25.400 |
| 34 | 150 | Blind | rf_diameter_mm | 921.000 | 965.000 | -44.000 |
| 34 | 150 | Blind | pcd_mm | 957.300 | 1028.700 | -71.400 |
| 34 | 150 | Blind | stud_bolt_length_mm | 170.000 | 265.000 | -95.000 |
| 34 | 150 | Blind | weight_kg | 355.000 | 627.000 | -272.000 |
| 34 | 150 | Blind | outer_diameter_mm | 1004.824 | 1110.000 | -105.176 |
| 34 | 150 | Blind | thickness_mm | 55.626 | 81.000 | -25.374 |
| 34 | 150 | Blind | hub_diameter_mm | 892.048 | 883.000 | +9.048 |
| 34 | 150 | Blind | length_thru_hub_mm | 55.626 | 81.000 | -25.374 |
| 34 | 150 | Blind | bore_diameter_mm | 21451.570 | 869.950 | +20581.620 |
| 34 | 150 | Blind | rf_diameter_mm | 920.750 | 965.000 | -44.250 |
| 34 | 150 | Blind | pcd_mm | 957.326 | 1028.700 | -71.374 |
| 34 | 150 | Blind | bolt_hole_diameter_mm | 25.400 | 0.000 | +25.400 |
| 34 | 150 | Blind | stud_bolt_length_mm | 171.450 | 265.000 | -93.550 |
| 34 | 150 | Blind | weight_kg | 355.000 | 627.000 | -272.000 |
| 36 | 150 | Welding Neck | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 36 | 150 | Welding Neck | bore_diameter_mm | 23387.050 | 920.750 | +22466.300 |
| 36 | 150 | Welding Neck | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 36 | 150 | Welding Neck | outer_diameter_mm | 1055.000 | 1170.000 | -115.000 |
| 36 | 150 | Welding Neck | thickness_mm | 50.900 | 88.900 | -38.000 |
| 36 | 150 | Welding Neck | hub_diameter_mm | 945.000 | 933.000 | +12.000 |
| 36 | 150 | Welding Neck | length_thru_hub_mm | 50.900 | 156.000 | -105.100 |
| 36 | 150 | Welding Neck | bore_diameter_mm | 895.350 | 920.750 | -25.400 |
| 36 | 150 | Welding Neck | rf_diameter_mm | 972.000 | 1022.000 | -50.000 |
| 36 | 150 | Welding Neck | pcd_mm | 1009.600 | 1085.800 | -76.200 |
| 36 | 150 | Welding Neck | stud_bolt_length_mm | 170.000 | 280.000 | -110.000 |
| 36 | 150 | Welding Neck | weight_kg | 108.000 | 290.000 | -182.000 |
| 36 | 150 | Welding Neck | outer_diameter_mm | 1057.148 | 1170.000 | -112.852 |
| 36 | 150 | Welding Neck | thickness_mm | 50.800 | 88.900 | -38.100 |
| 36 | 150 | Welding Neck | hub_diameter_mm | 944.626 | 933.000 | +11.626 |
| 36 | 150 | Welding Neck | length_thru_hub_mm | 50.800 | 156.000 | -105.200 |
| 36 | 150 | Welding Neck | bore_diameter_mm | 22741.890 | 920.750 | +21821.140 |
| 36 | 150 | Welding Neck | rf_diameter_mm | 971.550 | 1022.000 | -50.450 |
| 36 | 150 | Welding Neck | pcd_mm | 1009.650 | 1085.800 | -76.150 |
| 36 | 150 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 0.000 | +25.400 |
| 36 | 150 | Welding Neck | stud_bolt_length_mm | 171.450 | 280.000 | -108.550 |
| 36 | 150 | Welding Neck | weight_kg | 108.000 | 290.000 | -182.000 |
| 36 | 150 | Slip On | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 36 | 150 | Slip On | length_thru_hub_mm | 2419.350 | 95.250 | +2324.100 |
| 36 | 150 | Slip On | bore_diameter_mm | 23387.050 | 920.750 | +22466.300 |
| 36 | 150 | Slip On | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 36 | 150 | Slip On | outer_diameter_mm | 1055.000 | 1170.000 | -115.000 |
| 36 | 150 | Slip On | thickness_mm | 50.900 | 88.900 | -38.000 |
| 36 | 150 | Slip On | hub_diameter_mm | 945.000 | 933.000 | +12.000 |
| 36 | 150 | Slip On | length_thru_hub_mm | 50.900 | 95.250 | -44.350 |
| 36 | 150 | Slip On | bore_diameter_mm | 895.350 | 920.750 | -25.400 |
| 36 | 150 | Slip On | rf_diameter_mm | 972.000 | 1022.000 | -50.000 |
| 36 | 150 | Slip On | pcd_mm | 1009.600 | 1085.800 | -76.200 |
| 36 | 150 | Slip On | stud_bolt_length_mm | 170.000 | 280.000 | -110.000 |
| 36 | 150 | Slip On | outer_diameter_mm | 1057.148 | 1170.000 | -112.852 |
| 36 | 150 | Slip On | thickness_mm | 50.800 | 88.900 | -38.100 |
| 36 | 150 | Slip On | hub_diameter_mm | 944.626 | 933.000 | +11.626 |
| 36 | 150 | Slip On | length_thru_hub_mm | 50.800 | 95.250 | -44.450 |
| 36 | 150 | Slip On | bore_diameter_mm | 22741.890 | 920.750 | +21821.140 |
| 36 | 150 | Slip On | rf_diameter_mm | 971.550 | 1022.000 | -50.450 |
| 36 | 150 | Slip On | pcd_mm | 1009.650 | 1085.800 | -76.150 |
| 36 | 150 | Slip On | bolt_hole_diameter_mm | 25.400 | 0.000 | +25.400 |
| 36 | 150 | Slip On | stud_bolt_length_mm | 171.450 | 280.000 | -108.550 |
| 36 | 150 | Blind | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 36 | 150 | Blind | bore_diameter_mm | 23387.050 | 920.750 | +22466.300 |
| 36 | 150 | Blind | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 36 | 150 | Blind | outer_diameter_mm | 1055.000 | 1170.000 | -115.000 |
| 36 | 150 | Blind | thickness_mm | 57.300 | 88.900 | -31.600 |
| 36 | 150 | Blind | hub_diameter_mm | 945.000 | 933.000 | +12.000 |
| 36 | 150 | Blind | length_thru_hub_mm | 57.300 | 88.900 | -31.600 |
| 36 | 150 | Blind | bore_diameter_mm | 895.350 | 920.750 | -25.400 |
| 36 | 150 | Blind | rf_diameter_mm | 972.000 | 1022.000 | -50.000 |
| 36 | 150 | Blind | pcd_mm | 1009.600 | 1085.800 | -76.200 |
| 36 | 150 | Blind | stud_bolt_length_mm | 170.000 | 280.000 | -110.000 |
| 36 | 150 | Blind | weight_kg | 403.000 | 760.000 | -357.000 |
| 36 | 150 | Blind | outer_diameter_mm | 1057.148 | 1170.000 | -112.852 |
| 36 | 150 | Blind | thickness_mm | 57.150 | 88.900 | -31.750 |
| 36 | 150 | Blind | hub_diameter_mm | 944.626 | 933.000 | +11.626 |
| 36 | 150 | Blind | length_thru_hub_mm | 57.150 | 88.900 | -31.750 |
| 36 | 150 | Blind | bore_diameter_mm | 22741.890 | 920.750 | +21821.140 |
| 36 | 150 | Blind | rf_diameter_mm | 971.550 | 1022.000 | -50.450 |
| 36 | 150 | Blind | pcd_mm | 1009.650 | 1085.800 | -76.150 |
| 36 | 150 | Blind | bolt_hole_diameter_mm | 25.400 | 0.000 | +25.400 |
| 36 | 150 | Blind | stud_bolt_length_mm | 171.450 | 280.000 | -108.550 |
| 36 | 150 | Blind | weight_kg | 403.000 | 760.000 | -357.000 |
| 38 | 150 | Welding Neck | outer_diameter_mm | 1238.250 | 1240.000 | -1.750 |
| 38 | 150 | Welding Neck | bore_diameter_mm | 24677.370 | 971.550 | +23705.820 |
| 38 | 150 | Welding Neck | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 38 | 150 | Welding Neck | outer_diameter_mm | 1125.000 | 1240.000 | -115.000 |
| 38 | 150 | Welding Neck | thickness_mm | 52.500 | 85.800 | -33.300 |
| 38 | 150 | Welding Neck | hub_diameter_mm | 997.000 | 991.000 | +6.000 |
| 38 | 150 | Welding Neck | length_thru_hub_mm | 52.500 | 156.000 | -103.500 |
| 38 | 150 | Welding Neck | bore_diameter_mm | 946.150 | 971.550 | -25.400 |
| 38 | 150 | Welding Neck | rf_diameter_mm | 1022.000 | 1073.000 | -51.000 |
| 38 | 150 | Welding Neck | pcd_mm | 1070.000 | 1149.400 | -79.400 |
| 38 | 150 | Welding Neck | stud_bolt_length_mm | 190.000 | 280.000 | -90.000 |
| 38 | 150 | Welding Neck | weight_kg | 131.000 | 326.000 | -195.000 |
| 38 | 150 | Welding Neck | outer_diameter_mm | 1123.950 | 1240.000 | -116.050 |
| 38 | 150 | Welding Neck | thickness_mm | 52.578 | 85.800 | -33.222 |
| 38 | 150 | Welding Neck | hub_diameter_mm | 996.950 | 991.000 | +5.950 |
| 38 | 150 | Welding Neck | length_thru_hub_mm | 52.578 | 156.000 | -103.422 |
| 38 | 150 | Welding Neck | bore_diameter_mm | 24032.210 | 971.550 | +23060.660 |
| 38 | 150 | Welding Neck | rf_diameter_mm | 1022.350 | 1073.000 | -50.650 |
| 38 | 150 | Welding Neck | pcd_mm | 1069.848 | 1149.400 | -79.552 |
| 38 | 150 | Welding Neck | stud_bolt_length_mm | 190.500 | 280.000 | -89.500 |
| 38 | 150 | Welding Neck | weight_kg | 131.000 | 326.000 | -195.000 |
| 38 | 150 | Slip On | outer_diameter_mm | 1238.250 | 1240.000 | -1.750 |
| 38 | 150 | Slip On | length_thru_hub_mm | 2419.350 | 95.250 | +2324.100 |
| 38 | 150 | Slip On | bore_diameter_mm | 24677.370 | 971.550 | +23705.820 |
| 38 | 150 | Slip On | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 38 | 150 | Slip On | outer_diameter_mm | 1125.000 | 1240.000 | -115.000 |
| 38 | 150 | Slip On | thickness_mm | 52.500 | 85.800 | -33.300 |
| 38 | 150 | Slip On | hub_diameter_mm | 997.000 | 991.000 | +6.000 |
| 38 | 150 | Slip On | length_thru_hub_mm | 52.500 | 95.250 | -42.750 |
| 38 | 150 | Slip On | bore_diameter_mm | 946.150 | 971.550 | -25.400 |
| 38 | 150 | Slip On | rf_diameter_mm | 1022.000 | 1073.000 | -51.000 |
| 38 | 150 | Slip On | pcd_mm | 1070.000 | 1149.400 | -79.400 |
| 38 | 150 | Slip On | stud_bolt_length_mm | 190.000 | 280.000 | -90.000 |
| 38 | 150 | Slip On | outer_diameter_mm | 1123.950 | 1240.000 | -116.050 |
| 38 | 150 | Slip On | thickness_mm | 52.578 | 85.800 | -33.222 |
| 38 | 150 | Slip On | hub_diameter_mm | 996.950 | 991.000 | +5.950 |
| 38 | 150 | Slip On | length_thru_hub_mm | 52.578 | 95.250 | -42.672 |
| 38 | 150 | Slip On | bore_diameter_mm | 24032.210 | 971.550 | +23060.660 |
| 38 | 150 | Slip On | rf_diameter_mm | 1022.350 | 1073.000 | -50.650 |
| 38 | 150 | Slip On | pcd_mm | 1069.848 | 1149.400 | -79.552 |
| 38 | 150 | Slip On | stud_bolt_length_mm | 190.500 | 280.000 | -89.500 |
| 38 | 150 | Blind | outer_diameter_mm | 1238.250 | 1240.000 | -1.750 |
| 38 | 150 | Blind | bore_diameter_mm | 24677.370 | 971.550 | +23705.820 |
| 38 | 150 | Blind | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 38 | 150 | Blind | outer_diameter_mm | 1125.000 | 1240.000 | -115.000 |
| 38 | 150 | Blind | thickness_mm | 62.000 | 85.800 | -23.800 |
| 38 | 150 | Blind | hub_diameter_mm | 997.000 | 991.000 | +6.000 |
| 38 | 150 | Blind | length_thru_hub_mm | 62.000 | 85.800 | -23.800 |
| 38 | 150 | Blind | bore_diameter_mm | 946.150 | 971.550 | -25.400 |
| 38 | 150 | Blind | rf_diameter_mm | 1022.000 | 1073.000 | -51.000 |
| 38 | 150 | Blind | pcd_mm | 1070.000 | 1149.400 | -79.400 |
| 38 | 150 | Blind | stud_bolt_length_mm | 190.000 | 280.000 | -90.000 |
| 38 | 150 | Blind | weight_kg | 493.000 | 825.000 | -332.000 |
| 38 | 150 | Blind | outer_diameter_mm | 1123.950 | 1240.000 | -116.050 |
| 38 | 150 | Blind | thickness_mm | 61.976 | 85.800 | -23.824 |
| 38 | 150 | Blind | hub_diameter_mm | 996.950 | 991.000 | +5.950 |
| 38 | 150 | Blind | length_thru_hub_mm | 61.976 | 85.800 | -23.824 |
| 38 | 150 | Blind | bore_diameter_mm | 24032.210 | 971.550 | +23060.660 |
| 38 | 150 | Blind | rf_diameter_mm | 1022.350 | 1073.000 | -50.650 |
| 38 | 150 | Blind | pcd_mm | 1069.848 | 1149.400 | -79.552 |
| 38 | 150 | Blind | stud_bolt_length_mm | 190.500 | 280.000 | -89.500 |
| 38 | 150 | Blind | weight_kg | 493.000 | 825.000 | -332.000 |
| 40 | 150 | Welding Neck | bore_diameter_mm | 25967.690 | 1022.350 | +24945.340 |
| 40 | 150 | Welding Neck | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 40 | 150 | Welding Neck | outer_diameter_mm | 1175.000 | 1290.000 | -115.000 |
| 40 | 150 | Welding Neck | thickness_mm | 54.100 | 88.900 | -34.800 |
| 40 | 150 | Welding Neck | hub_diameter_mm | 1049.000 | 1041.000 | +8.000 |
| 40 | 150 | Welding Neck | length_thru_hub_mm | 54.100 | 162.000 | -107.900 |
| 40 | 150 | Welding Neck | bore_diameter_mm | 996.950 | 1022.350 | -25.400 |
| 40 | 150 | Welding Neck | rf_diameter_mm | 1080.000 | 1124.000 | -44.000 |
| 40 | 150 | Welding Neck | pcd_mm | 1120.800 | 1200.200 | -79.400 |
| 40 | 150 | Welding Neck | stud_bolt_length_mm | 195.000 | 280.000 | -85.000 |
| 40 | 150 | Welding Neck | weight_kg | 140.000 | 351.000 | -211.000 |
| 40 | 150 | Welding Neck | outer_diameter_mm | 1174.750 | 1290.000 | -115.250 |
| 40 | 150 | Welding Neck | thickness_mm | 54.102 | 88.900 | -34.798 |
| 40 | 150 | Welding Neck | hub_diameter_mm | 1049.274 | 1041.000 | +8.274 |
| 40 | 150 | Welding Neck | length_thru_hub_mm | 54.102 | 162.000 | -107.898 |
| 40 | 150 | Welding Neck | bore_diameter_mm | 25322.530 | 1022.350 | +24300.180 |
| 40 | 150 | Welding Neck | rf_diameter_mm | 1079.500 | 1124.000 | -44.500 |
| 40 | 150 | Welding Neck | pcd_mm | 1120.648 | 1200.200 | -79.552 |
| 40 | 150 | Welding Neck | stud_bolt_length_mm | 196.850 | 280.000 | -83.150 |
| 40 | 150 | Welding Neck | weight_kg | 140.000 | 351.000 | -211.000 |
| 40 | 150 | Slip On | length_thru_hub_mm | 2499.995 | 98.425 | +2401.570 |
| 40 | 150 | Slip On | bore_diameter_mm | 25967.690 | 1022.350 | +24945.340 |
| 40 | 150 | Slip On | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 40 | 150 | Slip On | outer_diameter_mm | 1175.000 | 1290.000 | -115.000 |
| 40 | 150 | Slip On | thickness_mm | 54.100 | 88.900 | -34.800 |
| 40 | 150 | Slip On | hub_diameter_mm | 1049.000 | 1041.000 | +8.000 |
| 40 | 150 | Slip On | length_thru_hub_mm | 54.100 | 98.425 | -44.325 |
| 40 | 150 | Slip On | bore_diameter_mm | 996.950 | 1022.350 | -25.400 |
| 40 | 150 | Slip On | rf_diameter_mm | 1080.000 | 1124.000 | -44.000 |
| 40 | 150 | Slip On | pcd_mm | 1120.800 | 1200.200 | -79.400 |
| 40 | 150 | Slip On | stud_bolt_length_mm | 195.000 | 280.000 | -85.000 |
| 40 | 150 | Slip On | outer_diameter_mm | 1174.750 | 1290.000 | -115.250 |
| 40 | 150 | Slip On | thickness_mm | 54.102 | 88.900 | -34.798 |
| 40 | 150 | Slip On | hub_diameter_mm | 1049.274 | 1041.000 | +8.274 |
| 40 | 150 | Slip On | length_thru_hub_mm | 54.102 | 98.425 | -44.323 |
| 40 | 150 | Slip On | bore_diameter_mm | 25322.530 | 1022.350 | +24300.180 |
| 40 | 150 | Slip On | rf_diameter_mm | 1079.500 | 1124.000 | -44.500 |
| 40 | 150 | Slip On | pcd_mm | 1120.648 | 1200.200 | -79.552 |
| 40 | 150 | Slip On | stud_bolt_length_mm | 196.850 | 280.000 | -83.150 |
| 40 | 150 | Blind | bore_diameter_mm | 25967.690 | 1022.350 | +24945.340 |
| 40 | 150 | Blind | stud_bolt_length_mm | 285.750 | 280.000 | +5.750 |
| 40 | 150 | Blind | outer_diameter_mm | 1175.000 | 1290.000 | -115.000 |
| 40 | 150 | Blind | thickness_mm | 65.200 | 88.900 | -23.700 |
| 40 | 150 | Blind | hub_diameter_mm | 1049.000 | 1041.000 | +8.000 |
| 40 | 150 | Blind | length_thru_hub_mm | 65.200 | 88.900 | -23.700 |
| 40 | 150 | Blind | bore_diameter_mm | 996.950 | 1022.350 | -25.400 |
| 40 | 150 | Blind | rf_diameter_mm | 1080.000 | 1124.000 | -44.000 |
| 40 | 150 | Blind | pcd_mm | 1120.800 | 1200.200 | -79.400 |
| 40 | 150 | Blind | stud_bolt_length_mm | 195.000 | 280.000 | -85.000 |
| 40 | 150 | Blind | weight_kg | 565.000 | 925.000 | -360.000 |
| 40 | 150 | Blind | outer_diameter_mm | 1174.750 | 1290.000 | -115.250 |
| 40 | 150 | Blind | thickness_mm | 65.278 | 88.900 | -23.622 |
| 40 | 150 | Blind | hub_diameter_mm | 1049.274 | 1041.000 | +8.274 |
| 40 | 150 | Blind | length_thru_hub_mm | 65.278 | 88.900 | -23.622 |
| 40 | 150 | Blind | bore_diameter_mm | 25322.530 | 1022.350 | +24300.180 |
| 40 | 150 | Blind | rf_diameter_mm | 1079.500 | 1124.000 | -44.500 |
| 40 | 150 | Blind | pcd_mm | 1120.648 | 1200.200 | -79.552 |
| 40 | 150 | Blind | stud_bolt_length_mm | 196.850 | 280.000 | -83.150 |
| 40 | 150 | Blind | weight_kg | 565.000 | 925.000 | -360.000 |
| 42 | 150 | Welding Neck | outer_diameter_mm | 1346.200 | 1345.000 | +1.200 |
| 42 | 150 | Welding Neck | bore_diameter_mm | 27258.010 | 1073.150 | +26184.860 |
| 42 | 150 | Welding Neck | stud_bolt_length_mm | 292.100 | 290.000 | +2.100 |
| 42 | 150 | Welding Neck | outer_diameter_mm | 1225.000 | 1345.000 | -120.000 |
| 42 | 150 | Welding Neck | thickness_mm | 57.300 | 95.300 | -38.000 |
| 42 | 150 | Welding Neck | hub_diameter_mm | 1102.000 | 1092.000 | +10.000 |
| 42 | 150 | Welding Neck | length_thru_hub_mm | 57.300 | 170.000 | -112.700 |
| 42 | 150 | Welding Neck | bore_diameter_mm | 1047.750 | 1073.150 | -25.400 |
| 42 | 150 | Welding Neck | rf_diameter_mm | 1130.000 | 1194.000 | -64.000 |
| 42 | 150 | Welding Neck | pcd_mm | 1171.600 | 1257.300 | -85.700 |
| 42 | 150 | Welding Neck | stud_bolt_length_mm | 195.000 | 290.000 | -95.000 |
| 42 | 150 | Welding Neck | weight_kg | 156.000 | 403.000 | -247.000 |
| 42 | 150 | Welding Neck | outer_diameter_mm | 1225.550 | 1345.000 | -119.450 |
| 42 | 150 | Welding Neck | thickness_mm | 57.150 | 95.300 | -38.150 |
| 42 | 150 | Welding Neck | hub_diameter_mm | 1101.852 | 1092.000 | +9.852 |
| 42 | 150 | Welding Neck | length_thru_hub_mm | 57.150 | 170.000 | -112.850 |
| 42 | 150 | Welding Neck | bore_diameter_mm | 26612.850 | 1073.150 | +25539.700 |
| 42 | 150 | Welding Neck | rf_diameter_mm | 1130.300 | 1194.000 | -63.700 |
| 42 | 150 | Welding Neck | pcd_mm | 1171.448 | 1257.300 | -85.852 |
| 42 | 150 | Welding Neck | stud_bolt_length_mm | 196.850 | 290.000 | -93.150 |
| 42 | 150 | Welding Neck | weight_kg | 156.000 | 403.000 | -247.000 |
| 42 | 150 | Slip On | outer_diameter_mm | 1346.200 | 1345.000 | +1.200 |
| 42 | 150 | Slip On | length_thru_hub_mm | 2580.640 | 101.600 | +2479.040 |
| 42 | 150 | Slip On | bore_diameter_mm | 27258.010 | 1073.150 | +26184.860 |
| 42 | 150 | Slip On | stud_bolt_length_mm | 292.100 | 290.000 | +2.100 |
| 42 | 150 | Slip On | outer_diameter_mm | 1225.000 | 1345.000 | -120.000 |
| 42 | 150 | Slip On | thickness_mm | 57.300 | 95.300 | -38.000 |
| 42 | 150 | Slip On | hub_diameter_mm | 1102.000 | 1092.000 | +10.000 |
| 42 | 150 | Slip On | length_thru_hub_mm | 57.300 | 101.600 | -44.300 |
| 42 | 150 | Slip On | bore_diameter_mm | 1047.750 | 1073.150 | -25.400 |
| 42 | 150 | Slip On | rf_diameter_mm | 1130.000 | 1194.000 | -64.000 |
| 42 | 150 | Slip On | pcd_mm | 1171.600 | 1257.300 | -85.700 |
| 42 | 150 | Slip On | stud_bolt_length_mm | 195.000 | 290.000 | -95.000 |
| 42 | 150 | Slip On | outer_diameter_mm | 1225.550 | 1345.000 | -119.450 |
| 42 | 150 | Slip On | thickness_mm | 57.150 | 95.300 | -38.150 |
| 42 | 150 | Slip On | hub_diameter_mm | 1101.852 | 1092.000 | +9.852 |
| 42 | 150 | Slip On | length_thru_hub_mm | 57.150 | 101.600 | -44.450 |
| 42 | 150 | Slip On | bore_diameter_mm | 26612.850 | 1073.150 | +25539.700 |
| 42 | 150 | Slip On | rf_diameter_mm | 1130.300 | 1194.000 | -63.700 |
| 42 | 150 | Slip On | pcd_mm | 1171.448 | 1257.300 | -85.852 |
| 42 | 150 | Slip On | stud_bolt_length_mm | 196.850 | 290.000 | -93.150 |
| 42 | 150 | Blind | outer_diameter_mm | 1346.200 | 1345.000 | +1.200 |
| 42 | 150 | Blind | bore_diameter_mm | 27258.010 | 1073.150 | +26184.860 |
| 42 | 150 | Blind | stud_bolt_length_mm | 292.100 | 290.000 | +2.100 |
| 42 | 150 | Blind | outer_diameter_mm | 1225.000 | 1345.000 | -120.000 |
| 42 | 150 | Blind | thickness_mm | 66.800 | 95.300 | -28.500 |
| 42 | 150 | Blind | hub_diameter_mm | 1102.000 | 1092.000 | +10.000 |
| 42 | 150 | Blind | length_thru_hub_mm | 66.800 | 95.300 | -28.500 |
| 42 | 150 | Blind | bore_diameter_mm | 1047.750 | 1073.150 | -25.400 |
| 42 | 150 | Blind | rf_diameter_mm | 1130.000 | 1194.000 | -64.000 |
| 42 | 150 | Blind | pcd_mm | 1171.600 | 1257.300 | -85.700 |
| 42 | 150 | Blind | stud_bolt_length_mm | 195.000 | 290.000 | -95.000 |
| 42 | 150 | Blind | weight_kg | 631.000 | 1080.000 | -449.000 |
| 42 | 150 | Blind | outer_diameter_mm | 1225.550 | 1345.000 | -119.450 |
| 42 | 150 | Blind | thickness_mm | 66.802 | 95.300 | -28.498 |
| 42 | 150 | Blind | hub_diameter_mm | 1101.852 | 1092.000 | +9.852 |
| 42 | 150 | Blind | length_thru_hub_mm | 66.802 | 95.300 | -28.498 |
| 42 | 150 | Blind | bore_diameter_mm | 26612.850 | 1073.150 | +25539.700 |
| 42 | 150 | Blind | rf_diameter_mm | 1130.300 | 1194.000 | -63.700 |
| 42 | 150 | Blind | pcd_mm | 1171.448 | 1257.300 | -85.852 |
| 42 | 150 | Blind | stud_bolt_length_mm | 196.850 | 290.000 | -93.150 |
| 42 | 150 | Blind | weight_kg | 631.000 | 1080.000 | -449.000 |
| 44 | 150 | Welding Neck | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 44 | 150 | Welding Neck | bore_diameter_mm | 28548.330 | 1123.950 | +27424.380 |
| 44 | 150 | Welding Neck | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 44 | 150 | Welding Neck | outer_diameter_mm | 1275.000 | 1405.000 | -130.000 |
| 44 | 150 | Welding Neck | thickness_mm | 58.900 | 100.100 | -41.200 |
| 44 | 150 | Welding Neck | hub_diameter_mm | 1153.000 | 1143.000 | +10.000 |
| 44 | 150 | Welding Neck | length_thru_hub_mm | 58.900 | 176.000 | -117.100 |
| 44 | 150 | Welding Neck | bore_diameter_mm | 1098.550 | 1123.950 | -25.400 |
| 44 | 150 | Welding Neck | rf_diameter_mm | 1181.000 | 1245.000 | -64.000 |
| 44 | 150 | Welding Neck | pcd_mm | 1222.400 | 1314.400 | -92.000 |
| 44 | 150 | Welding Neck | stud_bolt_length_mm | 205.000 | 305.000 | -100.000 |
| 44 | 150 | Welding Neck | weight_kg | 167.000 | 449.000 | -282.000 |
| 44 | 150 | Welding Neck | outer_diameter_mm | 1276.350 | 1405.000 | -128.650 |
| 44 | 150 | Welding Neck | thickness_mm | 58.928 | 100.100 | -41.172 |
| 44 | 150 | Welding Neck | hub_diameter_mm | 1152.652 | 1143.000 | +9.652 |
| 44 | 150 | Welding Neck | length_thru_hub_mm | 58.928 | 176.000 | -117.072 |
| 44 | 150 | Welding Neck | bore_diameter_mm | 27903.170 | 1123.950 | +26779.220 |
| 44 | 150 | Welding Neck | rf_diameter_mm | 1181.100 | 1245.000 | -63.900 |
| 44 | 150 | Welding Neck | pcd_mm | 1222.248 | 1314.400 | -92.152 |
| 44 | 150 | Welding Neck | stud_bolt_length_mm | 203.200 | 305.000 | -101.800 |
| 44 | 150 | Welding Neck | weight_kg | 167.000 | 449.000 | -282.000 |
| 44 | 150 | Slip On | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 44 | 150 | Slip On | length_thru_hub_mm | 2580.640 | 101.600 | +2479.040 |
| 44 | 150 | Slip On | bore_diameter_mm | 28548.330 | 1123.950 | +27424.380 |
| 44 | 150 | Slip On | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 44 | 150 | Slip On | outer_diameter_mm | 1275.000 | 1405.000 | -130.000 |
| 44 | 150 | Slip On | thickness_mm | 58.900 | 100.100 | -41.200 |
| 44 | 150 | Slip On | hub_diameter_mm | 1153.000 | 1143.000 | +10.000 |
| 44 | 150 | Slip On | length_thru_hub_mm | 58.900 | 101.600 | -42.700 |
| 44 | 150 | Slip On | bore_diameter_mm | 1098.550 | 1123.950 | -25.400 |
| 44 | 150 | Slip On | rf_diameter_mm | 1181.000 | 1245.000 | -64.000 |
| 44 | 150 | Slip On | pcd_mm | 1222.400 | 1314.400 | -92.000 |
| 44 | 150 | Slip On | stud_bolt_length_mm | 205.000 | 305.000 | -100.000 |
| 44 | 150 | Slip On | outer_diameter_mm | 1276.350 | 1405.000 | -128.650 |
| 44 | 150 | Slip On | thickness_mm | 58.928 | 100.100 | -41.172 |
| 44 | 150 | Slip On | hub_diameter_mm | 1152.652 | 1143.000 | +9.652 |
| 44 | 150 | Slip On | length_thru_hub_mm | 58.928 | 101.600 | -42.672 |
| 44 | 150 | Slip On | bore_diameter_mm | 27903.170 | 1123.950 | +26779.220 |
| 44 | 150 | Slip On | rf_diameter_mm | 1181.100 | 1245.000 | -63.900 |
| 44 | 150 | Slip On | pcd_mm | 1222.248 | 1314.400 | -92.152 |
| 44 | 150 | Slip On | stud_bolt_length_mm | 203.200 | 305.000 | -101.800 |
| 44 | 150 | Blind | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 44 | 150 | Blind | bore_diameter_mm | 28548.330 | 1123.950 | +27424.380 |
| 44 | 150 | Blind | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 44 | 150 | Blind | outer_diameter_mm | 1275.000 | 1405.000 | -130.000 |
| 44 | 150 | Blind | thickness_mm | 70.000 | 100.100 | -30.100 |
| 44 | 150 | Blind | hub_diameter_mm | 1153.000 | 1143.000 | +10.000 |
| 44 | 150 | Blind | length_thru_hub_mm | 70.000 | 100.100 | -30.100 |
| 44 | 150 | Blind | bore_diameter_mm | 1098.550 | 1123.950 | -25.400 |
| 44 | 150 | Blind | rf_diameter_mm | 1181.000 | 1245.000 | -64.000 |
| 44 | 150 | Blind | pcd_mm | 1222.400 | 1314.400 | -92.000 |
| 44 | 150 | Blind | stud_bolt_length_mm | 205.000 | 305.000 | -100.000 |
| 44 | 150 | Blind | weight_kg | 716.000 | 1232.000 | -516.000 |
| 44 | 150 | Blind | outer_diameter_mm | 1276.350 | 1405.000 | -128.650 |
| 44 | 150 | Blind | thickness_mm | 69.850 | 100.100 | -30.250 |
| 44 | 150 | Blind | hub_diameter_mm | 1152.652 | 1143.000 | +9.652 |
| 44 | 150 | Blind | length_thru_hub_mm | 69.850 | 100.100 | -30.250 |
| 44 | 150 | Blind | bore_diameter_mm | 27903.170 | 1123.950 | +26779.220 |
| 44 | 150 | Blind | rf_diameter_mm | 1181.100 | 1245.000 | -63.900 |
| 44 | 150 | Blind | pcd_mm | 1222.248 | 1314.400 | -92.152 |
| 44 | 150 | Blind | stud_bolt_length_mm | 203.200 | 305.000 | -101.800 |
| 44 | 150 | Blind | weight_kg | 716.000 | 1232.000 | -516.000 |
| 46 | 150 | Welding Neck | bore_diameter_mm | 29838.650 | 1174.750 | +28663.900 |
| 46 | 150 | Welding Neck | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 46 | 150 | Welding Neck | outer_diameter_mm | 1340.000 | 1455.000 | -115.000 |
| 46 | 150 | Welding Neck | thickness_mm | 60.400 | 101.600 | -41.200 |
| 46 | 150 | Welding Neck | hub_diameter_mm | 1205.000 | 1197.000 | +8.000 |
| 46 | 150 | Welding Neck | length_thru_hub_mm | 60.400 | 184.000 | -123.600 |
| 46 | 150 | Welding Neck | bore_diameter_mm | 1149.350 | 1174.750 | -25.400 |
| 46 | 150 | Welding Neck | rf_diameter_mm | 1235.000 | 1295.000 | -60.000 |
| 46 | 150 | Welding Neck | pcd_mm | 1284.300 | 1365.200 | -80.900 |
| 46 | 150 | Welding Neck | stud_bolt_length_mm | 215.000 | 305.000 | -90.000 |
| 46 | 150 | Welding Neck | weight_kg | 197.000 | 480.000 | -283.000 |
| 46 | 150 | Welding Neck | outer_diameter_mm | 1341.374 | 1455.000 | -113.626 |
| 46 | 150 | Welding Neck | thickness_mm | 60.452 | 101.600 | -41.148 |
| 46 | 150 | Welding Neck | hub_diameter_mm | 1204.976 | 1197.000 | +7.976 |
| 46 | 150 | Welding Neck | length_thru_hub_mm | 60.452 | 184.000 | -123.548 |
| 46 | 150 | Welding Neck | bore_diameter_mm | 29193.490 | 1174.750 | +28018.740 |
| 46 | 150 | Welding Neck | rf_diameter_mm | 1234.948 | 1295.000 | -60.052 |
| 46 | 150 | Welding Neck | pcd_mm | 1284.224 | 1365.200 | -80.976 |
| 46 | 150 | Welding Neck | stud_bolt_length_mm | 215.900 | 305.000 | -89.100 |
| 46 | 150 | Welding Neck | weight_kg | 197.000 | 480.000 | -283.000 |
| 46 | 150 | Slip On | length_thru_hub_mm | 2620.962 | 103.188 | +2517.775 |
| 46 | 150 | Slip On | bore_diameter_mm | 29838.650 | 1174.750 | +28663.900 |
| 46 | 150 | Slip On | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 46 | 150 | Slip On | outer_diameter_mm | 1340.000 | 1455.000 | -115.000 |
| 46 | 150 | Slip On | thickness_mm | 60.400 | 101.600 | -41.200 |
| 46 | 150 | Slip On | hub_diameter_mm | 1205.000 | 1197.000 | +8.000 |
| 46 | 150 | Slip On | length_thru_hub_mm | 60.400 | 103.188 | -42.788 |
| 46 | 150 | Slip On | bore_diameter_mm | 1149.350 | 1174.750 | -25.400 |
| 46 | 150 | Slip On | rf_diameter_mm | 1235.000 | 1295.000 | -60.000 |
| 46 | 150 | Slip On | pcd_mm | 1284.300 | 1365.200 | -80.900 |
| 46 | 150 | Slip On | stud_bolt_length_mm | 215.000 | 305.000 | -90.000 |
| 46 | 150 | Slip On | outer_diameter_mm | 1341.374 | 1455.000 | -113.626 |
| 46 | 150 | Slip On | thickness_mm | 60.452 | 101.600 | -41.148 |
| 46 | 150 | Slip On | hub_diameter_mm | 1204.976 | 1197.000 | +7.976 |
| 46 | 150 | Slip On | length_thru_hub_mm | 60.452 | 103.188 | -42.736 |
| 46 | 150 | Slip On | bore_diameter_mm | 29193.490 | 1174.750 | +28018.740 |
| 46 | 150 | Slip On | rf_diameter_mm | 1234.948 | 1295.000 | -60.052 |
| 46 | 150 | Slip On | pcd_mm | 1284.224 | 1365.200 | -80.976 |
| 46 | 150 | Slip On | stud_bolt_length_mm | 215.900 | 305.000 | -89.100 |
| 46 | 150 | Blind | bore_diameter_mm | 29838.650 | 1174.750 | +28663.900 |
| 46 | 150 | Blind | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 46 | 150 | Blind | outer_diameter_mm | 1340.000 | 1455.000 | -115.000 |
| 46 | 150 | Blind | thickness_mm | 73.100 | 101.600 | -28.500 |
| 46 | 150 | Blind | hub_diameter_mm | 1205.000 | 1197.000 | +8.000 |
| 46 | 150 | Blind | length_thru_hub_mm | 73.100 | 101.600 | -28.500 |
| 46 | 150 | Blind | bore_diameter_mm | 1149.350 | 1174.750 | -25.400 |
| 46 | 150 | Blind | rf_diameter_mm | 1235.000 | 1295.000 | -60.000 |
| 46 | 150 | Blind | pcd_mm | 1284.300 | 1365.200 | -80.900 |
| 46 | 150 | Blind | stud_bolt_length_mm | 215.000 | 305.000 | -90.000 |
| 46 | 150 | Blind | weight_kg | 827.000 | 1343.000 | -516.000 |
| 46 | 150 | Blind | outer_diameter_mm | 1341.374 | 1455.000 | -113.626 |
| 46 | 150 | Blind | thickness_mm | 73.152 | 101.600 | -28.448 |
| 46 | 150 | Blind | hub_diameter_mm | 1204.976 | 1197.000 | +7.976 |
| 46 | 150 | Blind | length_thru_hub_mm | 73.152 | 101.600 | -28.448 |
| 46 | 150 | Blind | bore_diameter_mm | 29193.490 | 1174.750 | +28018.740 |
| 46 | 150 | Blind | rf_diameter_mm | 1234.948 | 1295.000 | -60.052 |
| 46 | 150 | Blind | pcd_mm | 1284.224 | 1365.200 | -80.976 |
| 46 | 150 | Blind | stud_bolt_length_mm | 215.900 | 305.000 | -89.100 |
| 46 | 150 | Blind | weight_kg | 827.000 | 1343.000 | -516.000 |
| 48 | 150 | Welding Neck | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 48 | 150 | Welding Neck | bore_diameter_mm | 31128.970 | 1225.550 | +29903.420 |
| 48 | 150 | Welding Neck | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 48 | 150 | Welding Neck | outer_diameter_mm | 1390.000 | 1510.000 | -120.000 |
| 48 | 150 | Welding Neck | thickness_mm | 63.600 | 106.400 | -42.800 |
| 48 | 150 | Welding Neck | hub_diameter_mm | 1257.000 | 1248.000 | +9.000 |
| 48 | 150 | Welding Neck | length_thru_hub_mm | 63.600 | 191.000 | -127.400 |
| 48 | 150 | Welding Neck | bore_diameter_mm | 1200.150 | 1225.550 | -25.400 |
| 48 | 150 | Welding Neck | rf_diameter_mm | 1289.000 | 1359.000 | -70.000 |
| 48 | 150 | Welding Neck | pcd_mm | 1335.100 | 1422.400 | -87.300 |
| 48 | 150 | Welding Neck | stud_bolt_length_mm | 220.000 | 320.000 | -100.000 |
| 48 | 150 | Welding Neck | weight_kg | 217.000 | 537.000 | -320.000 |
| 48 | 150 | Welding Neck | outer_diameter_mm | 1392.174 | 1510.000 | -117.826 |
| 48 | 150 | Welding Neck | thickness_mm | 63.500 | 106.400 | -42.900 |
| 48 | 150 | Welding Neck | hub_diameter_mm | 1231.900 | 1248.000 | -16.100 |
| 48 | 150 | Welding Neck | length_thru_hub_mm | 63.500 | 191.000 | -127.500 |
| 48 | 150 | Welding Neck | bore_diameter_mm | 30483.810 | 1225.550 | +29258.260 |
| 48 | 150 | Welding Neck | rf_diameter_mm | 1289.050 | 1359.000 | -69.950 |
| 48 | 150 | Welding Neck | pcd_mm | 1335.024 | 1422.400 | -87.376 |
| 48 | 150 | Welding Neck | stud_bolt_length_mm | 222.250 | 320.000 | -97.750 |
| 48 | 150 | Welding Neck | weight_kg | 217.000 | 537.000 | -320.000 |
| 48 | 150 | Slip On | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 48 | 150 | Slip On | length_thru_hub_mm | 2741.930 | 107.950 | +2633.980 |
| 48 | 150 | Slip On | bore_diameter_mm | 31128.970 | 1225.550 | +29903.420 |
| 48 | 150 | Slip On | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 48 | 150 | Slip On | outer_diameter_mm | 1390.000 | 1510.000 | -120.000 |
| 48 | 150 | Slip On | thickness_mm | 63.600 | 106.400 | -42.800 |
| 48 | 150 | Slip On | hub_diameter_mm | 1257.000 | 1248.000 | +9.000 |
| 48 | 150 | Slip On | length_thru_hub_mm | 63.600 | 107.950 | -44.350 |
| 48 | 150 | Slip On | bore_diameter_mm | 1200.150 | 1225.550 | -25.400 |
| 48 | 150 | Slip On | rf_diameter_mm | 1289.000 | 1359.000 | -70.000 |
| 48 | 150 | Slip On | pcd_mm | 1335.100 | 1422.400 | -87.300 |
| 48 | 150 | Slip On | stud_bolt_length_mm | 220.000 | 320.000 | -100.000 |
| 48 | 150 | Slip On | outer_diameter_mm | 1392.174 | 1510.000 | -117.826 |
| 48 | 150 | Slip On | thickness_mm | 63.500 | 106.400 | -42.900 |
| 48 | 150 | Slip On | hub_diameter_mm | 1231.900 | 1248.000 | -16.100 |
| 48 | 150 | Slip On | length_thru_hub_mm | 63.500 | 107.950 | -44.450 |
| 48 | 150 | Slip On | bore_diameter_mm | 30483.810 | 1225.550 | +29258.260 |
| 48 | 150 | Slip On | rf_diameter_mm | 1289.050 | 1359.000 | -69.950 |
| 48 | 150 | Slip On | pcd_mm | 1335.024 | 1422.400 | -87.376 |
| 48 | 150 | Slip On | stud_bolt_length_mm | 222.250 | 320.000 | -97.750 |
| 48 | 150 | Blind | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 48 | 150 | Blind | bore_diameter_mm | 31128.970 | 1225.550 | +29903.420 |
| 48 | 150 | Blind | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 48 | 150 | Blind | outer_diameter_mm | 1390.000 | 1510.000 | -120.000 |
| 48 | 150 | Blind | thickness_mm | 76.300 | 106.400 | -30.100 |
| 48 | 150 | Blind | hub_diameter_mm | 1257.000 | 1248.000 | +9.000 |
| 48 | 150 | Blind | length_thru_hub_mm | 76.300 | 106.400 | -30.100 |
| 48 | 150 | Blind | bore_diameter_mm | 1200.150 | 1225.550 | -25.400 |
| 48 | 150 | Blind | rf_diameter_mm | 1289.000 | 1359.000 | -70.000 |
| 48 | 150 | Blind | pcd_mm | 1335.100 | 1422.400 | -87.300 |
| 48 | 150 | Blind | stud_bolt_length_mm | 220.000 | 320.000 | -100.000 |
| 48 | 150 | Blind | weight_kg | 927.000 | 1518.000 | -591.000 |
| 48 | 150 | Blind | outer_diameter_mm | 1392.174 | 1510.000 | -117.826 |
| 48 | 150 | Blind | thickness_mm | 76.200 | 106.400 | -30.200 |
| 48 | 150 | Blind | hub_diameter_mm | 1231.900 | 1248.000 | -16.100 |
| 48 | 150 | Blind | length_thru_hub_mm | 76.200 | 106.400 | -30.200 |
| 48 | 150 | Blind | bore_diameter_mm | 30483.810 | 1225.550 | +29258.260 |
| 48 | 150 | Blind | rf_diameter_mm | 1289.050 | 1359.000 | -69.950 |
| 48 | 150 | Blind | pcd_mm | 1335.024 | 1422.400 | -87.376 |
| 48 | 150 | Blind | stud_bolt_length_mm | 222.250 | 320.000 | -97.750 |
| 48 | 150 | Blind | weight_kg | 927.000 | 1518.000 | -591.000 |
| 50 | 150 | Welding Neck | outer_diameter_mm | 1568.450 | 1570.000 | -1.550 |
| 50 | 150 | Welding Neck | bore_diameter_mm | 31775.400 | 1251.000 | +30524.400 |
| 50 | 150 | Welding Neck | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 50 | 150 | Welding Neck | outer_diameter_mm | 1445.000 | 1570.000 | -125.000 |
| 50 | 150 | Welding Neck | thickness_mm | 66.800 | 109.600 | -42.800 |
| 50 | 150 | Welding Neck | hub_diameter_mm | 1308.000 | 1302.000 | +6.000 |
| 50 | 150 | Welding Neck | length_thru_hub_mm | 66.800 | 202.000 | -135.200 |
| 50 | 150 | Welding Neck | rf_diameter_mm | 1340.000 | 1410.000 | -70.000 |
| 50 | 150 | Welding Neck | pcd_mm | 1385.900 | 1479.600 | -93.700 |
| 50 | 150 | Welding Neck | stud_bolt_length_mm | 230.000 | 335.000 | -105.000 |
| 50 | 150 | Welding Neck | weight_kg | 235.000 | 576.000 | -341.000 |
| 50 | 150 | Welding Neck | outer_diameter_mm | 1442.974 | 1570.000 | -127.026 |
| 50 | 150 | Welding Neck | thickness_mm | 66.802 | 109.600 | -42.798 |
| 50 | 150 | Welding Neck | hub_diameter_mm | 1308.100 | 1302.000 | +6.100 |
| 50 | 150 | Welding Neck | length_thru_hub_mm | 66.802 | 202.000 | -135.198 |
| 50 | 150 | Welding Neck | bore_diameter_mm | 31774.130 | 1251.000 | +30523.130 |
| 50 | 150 | Welding Neck | rf_diameter_mm | 1339.850 | 1410.000 | -70.150 |
| 50 | 150 | Welding Neck | pcd_mm | 1385.824 | 1479.600 | -93.776 |
| 50 | 150 | Welding Neck | stud_bolt_length_mm | 228.600 | 335.000 | -106.400 |
| 50 | 150 | Welding Neck | weight_kg | 235.000 | 576.000 | -341.000 |
| 50 | 150 | Slip On | outer_diameter_mm | 1568.450 | 1570.000 | -1.550 |
| 50 | 150 | Slip On | bore_diameter_mm | 31775.400 | 1251.000 | +30524.400 |
| 50 | 150 | Slip On | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 50 | 150 | Slip On | outer_diameter_mm | 1445.000 | 1570.000 | -125.000 |
| 50 | 150 | Slip On | thickness_mm | 66.800 | 109.600 | -42.800 |
| 50 | 150 | Slip On | hub_diameter_mm | 1308.000 | 1302.000 | +6.000 |
| 50 | 150 | Slip On | length_thru_hub_mm | 66.800 | 109.600 | -42.800 |
| 50 | 150 | Slip On | rf_diameter_mm | 1340.000 | 1410.000 | -70.000 |
| 50 | 150 | Slip On | pcd_mm | 1385.900 | 1479.600 | -93.700 |
| 50 | 150 | Slip On | stud_bolt_length_mm | 230.000 | 335.000 | -105.000 |
| 50 | 150 | Slip On | outer_diameter_mm | 1442.974 | 1570.000 | -127.026 |
| 50 | 150 | Slip On | thickness_mm | 66.802 | 109.600 | -42.798 |
| 50 | 150 | Slip On | hub_diameter_mm | 1308.100 | 1302.000 | +6.100 |
| 50 | 150 | Slip On | length_thru_hub_mm | 66.802 | 109.600 | -42.798 |
| 50 | 150 | Slip On | bore_diameter_mm | 31774.130 | 1251.000 | +30523.130 |
| 50 | 150 | Slip On | rf_diameter_mm | 1339.850 | 1410.000 | -70.150 |
| 50 | 150 | Slip On | pcd_mm | 1385.824 | 1479.600 | -93.776 |
| 50 | 150 | Slip On | stud_bolt_length_mm | 228.600 | 335.000 | -106.400 |
| 50 | 150 | Blind | outer_diameter_mm | 1568.450 | 1570.000 | -1.550 |
| 50 | 150 | Blind | bore_diameter_mm | 31775.400 | 1251.000 | +30524.400 |
| 50 | 150 | Blind | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 50 | 150 | Blind | outer_diameter_mm | 1445.000 | 1570.000 | -125.000 |
| 50 | 150 | Blind | thickness_mm | 79.500 | 109.600 | -30.100 |
| 50 | 150 | Blind | hub_diameter_mm | 1308.000 | 1302.000 | +6.000 |
| 50 | 150 | Blind | length_thru_hub_mm | 79.500 | 109.600 | -30.100 |
| 50 | 150 | Blind | rf_diameter_mm | 1340.000 | 1410.000 | -70.000 |
| 50 | 150 | Blind | pcd_mm | 1385.900 | 1479.600 | -93.700 |
| 50 | 150 | Blind | stud_bolt_length_mm | 230.000 | 335.000 | -105.000 |
| 50 | 150 | Blind | weight_kg | 1036.000 | 1685.000 | -649.000 |
| 50 | 150 | Blind | outer_diameter_mm | 1442.974 | 1570.000 | -127.026 |
| 50 | 150 | Blind | thickness_mm | 79.502 | 109.600 | -30.098 |
| 50 | 150 | Blind | hub_diameter_mm | 1308.100 | 1302.000 | +6.100 |
| 50 | 150 | Blind | length_thru_hub_mm | 79.502 | 109.600 | -30.098 |
| 50 | 150 | Blind | bore_diameter_mm | 31774.130 | 1251.000 | +30523.130 |
| 50 | 150 | Blind | rf_diameter_mm | 1339.850 | 1410.000 | -70.150 |
| 50 | 150 | Blind | pcd_mm | 1385.824 | 1479.600 | -93.776 |
| 50 | 150 | Blind | stud_bolt_length_mm | 228.600 | 335.000 | -106.400 |
| 50 | 150 | Blind | weight_kg | 1036.000 | 1685.000 | -649.000 |
| 52 | 150 | Welding Neck | bore_diameter_mm | 33065.720 | 1301.800 | +31763.920 |
| 52 | 150 | Welding Neck | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 52 | 150 | Welding Neck | outer_diameter_mm | 1495.000 | 1625.000 | -130.000 |
| 52 | 150 | Welding Neck | thickness_mm | 68.400 | 114.300 | -45.900 |
| 52 | 150 | Welding Neck | hub_diameter_mm | 1360.000 | 1353.000 | +7.000 |
| 52 | 150 | Welding Neck | length_thru_hub_mm | 68.400 | 208.000 | -139.600 |
| 52 | 150 | Welding Neck | rf_diameter_mm | 1391.000 | 1461.000 | -70.000 |
| 52 | 150 | Welding Neck | pcd_mm | 1436.700 | 1536.700 | -100.000 |
| 52 | 150 | Welding Neck | stud_bolt_length_mm | 235.000 | 350.000 | -115.000 |
| 52 | 150 | Welding Neck | weight_kg | 249.000 | 639.000 | -390.000 |
| 52 | 150 | Welding Neck | outer_diameter_mm | 1493.774 | 1625.000 | -131.226 |
| 52 | 150 | Welding Neck | thickness_mm | 68.326 | 114.300 | -45.974 |
| 52 | 150 | Welding Neck | hub_diameter_mm | 1360.424 | 1353.000 | +7.424 |
| 52 | 150 | Welding Neck | length_thru_hub_mm | 68.326 | 208.000 | -139.674 |
| 52 | 150 | Welding Neck | bore_diameter_mm | 33064.450 | 1301.800 | +31762.650 |
| 52 | 150 | Welding Neck | rf_diameter_mm | 1390.650 | 1461.000 | -70.350 |
| 52 | 150 | Welding Neck | pcd_mm | 1436.624 | 1536.700 | -100.076 |
| 52 | 150 | Welding Neck | stud_bolt_length_mm | 234.950 | 350.000 | -115.050 |
| 52 | 150 | Welding Neck | weight_kg | 249.000 | 639.000 | -390.000 |
| 52 | 150 | Slip On | bore_diameter_mm | 33065.720 | 1301.800 | +31763.920 |
| 52 | 150 | Slip On | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 52 | 150 | Slip On | outer_diameter_mm | 1495.000 | 1625.000 | -130.000 |
| 52 | 150 | Slip On | thickness_mm | 68.400 | 114.300 | -45.900 |
| 52 | 150 | Slip On | hub_diameter_mm | 1360.000 | 1353.000 | +7.000 |
| 52 | 150 | Slip On | length_thru_hub_mm | 68.400 | 114.300 | -45.900 |
| 52 | 150 | Slip On | rf_diameter_mm | 1391.000 | 1461.000 | -70.000 |
| 52 | 150 | Slip On | pcd_mm | 1436.700 | 1536.700 | -100.000 |
| 52 | 150 | Slip On | stud_bolt_length_mm | 235.000 | 350.000 | -115.000 |
| 52 | 150 | Slip On | outer_diameter_mm | 1493.774 | 1625.000 | -131.226 |
| 52 | 150 | Slip On | thickness_mm | 68.326 | 114.300 | -45.974 |
| 52 | 150 | Slip On | hub_diameter_mm | 1360.424 | 1353.000 | +7.424 |
| 52 | 150 | Slip On | length_thru_hub_mm | 68.326 | 114.300 | -45.974 |
| 52 | 150 | Slip On | bore_diameter_mm | 33064.450 | 1301.800 | +31762.650 |
| 52 | 150 | Slip On | rf_diameter_mm | 1390.650 | 1461.000 | -70.350 |
| 52 | 150 | Slip On | pcd_mm | 1436.624 | 1536.700 | -100.076 |
| 52 | 150 | Slip On | stud_bolt_length_mm | 234.950 | 350.000 | -115.050 |
| 52 | 150 | Blind | bore_diameter_mm | 33065.720 | 1301.800 | +31763.920 |
| 52 | 150 | Blind | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 52 | 150 | Blind | outer_diameter_mm | 1495.000 | 1625.000 | -130.000 |
| 52 | 150 | Blind | thickness_mm | 82.700 | 114.300 | -31.600 |
| 52 | 150 | Blind | hub_diameter_mm | 1360.000 | 1353.000 | +7.000 |
| 52 | 150 | Blind | length_thru_hub_mm | 82.700 | 114.300 | -31.600 |
| 52 | 150 | Blind | rf_diameter_mm | 1391.000 | 1461.000 | -70.000 |
| 52 | 150 | Blind | pcd_mm | 1436.700 | 1536.700 | -100.000 |
| 52 | 150 | Blind | stud_bolt_length_mm | 235.000 | 350.000 | -115.000 |
| 52 | 150 | Blind | weight_kg | 1155.000 | 1885.000 | -730.000 |
| 52 | 150 | Blind | outer_diameter_mm | 1493.774 | 1625.000 | -131.226 |
| 52 | 150 | Blind | thickness_mm | 82.550 | 114.300 | -31.750 |
| 52 | 150 | Blind | hub_diameter_mm | 1360.424 | 1353.000 | +7.424 |
| 52 | 150 | Blind | length_thru_hub_mm | 82.550 | 114.300 | -31.750 |
| 52 | 150 | Blind | bore_diameter_mm | 33064.450 | 1301.800 | +31762.650 |
| 52 | 150 | Blind | rf_diameter_mm | 1390.650 | 1461.000 | -70.350 |
| 52 | 150 | Blind | pcd_mm | 1436.624 | 1536.700 | -100.076 |
| 52 | 150 | Blind | stud_bolt_length_mm | 234.950 | 350.000 | -115.050 |
| 52 | 150 | Blind | weight_kg | 1155.000 | 1885.000 | -730.000 |
| 54 | 150 | Welding Neck | outer_diameter_mm | 1682.750 | 1685.000 | -2.250 |
| 54 | 150 | Welding Neck | bore_diameter_mm | 34356.040 | 1352.600 | +33003.440 |
| 54 | 150 | Welding Neck | outer_diameter_mm | 1550.000 | 1685.000 | -135.000 |
| 54 | 150 | Welding Neck | thickness_mm | 70.000 | 119.100 | -49.100 |
| 54 | 150 | Welding Neck | hub_diameter_mm | 1413.000 | 1403.000 | +10.000 |
| 54 | 150 | Welding Neck | length_thru_hub_mm | 70.000 | 214.000 | -144.000 |
| 54 | 150 | Welding Neck | rf_diameter_mm | 1441.000 | 1511.000 | -70.000 |
| 54 | 150 | Welding Neck | pcd_mm | 1492.200 | 1593.800 | -101.600 |
| 54 | 150 | Welding Neck | stud_bolt_length_mm | 240.000 | 355.000 | -115.000 |
| 54 | 150 | Welding Neck | weight_kg | 281.000 | 718.000 | -437.000 |
| 54 | 150 | Welding Neck | outer_diameter_mm | 1549.400 | 1685.000 | -135.600 |
| 54 | 150 | Welding Neck | thickness_mm | 69.850 | 119.100 | -49.250 |
| 54 | 150 | Welding Neck | hub_diameter_mm | 1412.748 | 1403.000 | +9.748 |
| 54 | 150 | Welding Neck | length_thru_hub_mm | 69.850 | 214.000 | -144.150 |
| 54 | 150 | Welding Neck | bore_diameter_mm | 34354.770 | 1352.600 | +33002.170 |
| 54 | 150 | Welding Neck | rf_diameter_mm | 1441.450 | 1511.000 | -69.550 |
| 54 | 150 | Welding Neck | pcd_mm | 1492.250 | 1593.800 | -101.550 |
| 54 | 150 | Welding Neck | stud_bolt_length_mm | 241.300 | 355.000 | -113.700 |
| 54 | 150 | Welding Neck | weight_kg | 281.000 | 718.000 | -437.000 |
| 54 | 150 | Slip On | outer_diameter_mm | 1682.750 | 1685.000 | -2.250 |
| 54 | 150 | Slip On | bore_diameter_mm | 34356.040 | 1352.600 | +33003.440 |
| 54 | 150 | Slip On | outer_diameter_mm | 1550.000 | 1685.000 | -135.000 |
| 54 | 150 | Slip On | thickness_mm | 70.000 | 119.100 | -49.100 |
| 54 | 150 | Slip On | hub_diameter_mm | 1413.000 | 1403.000 | +10.000 |
| 54 | 150 | Slip On | length_thru_hub_mm | 70.000 | 119.100 | -49.100 |
| 54 | 150 | Slip On | rf_diameter_mm | 1441.000 | 1511.000 | -70.000 |
| 54 | 150 | Slip On | pcd_mm | 1492.200 | 1593.800 | -101.600 |
| 54 | 150 | Slip On | stud_bolt_length_mm | 240.000 | 355.000 | -115.000 |
| 54 | 150 | Slip On | outer_diameter_mm | 1549.400 | 1685.000 | -135.600 |
| 54 | 150 | Slip On | thickness_mm | 69.850 | 119.100 | -49.250 |
| 54 | 150 | Slip On | hub_diameter_mm | 1412.748 | 1403.000 | +9.748 |
| 54 | 150 | Slip On | length_thru_hub_mm | 69.850 | 119.100 | -49.250 |
| 54 | 150 | Slip On | bore_diameter_mm | 34354.770 | 1352.600 | +33002.170 |
| 54 | 150 | Slip On | rf_diameter_mm | 1441.450 | 1511.000 | -69.550 |
| 54 | 150 | Slip On | pcd_mm | 1492.250 | 1593.800 | -101.550 |
| 54 | 150 | Slip On | stud_bolt_length_mm | 241.300 | 355.000 | -113.700 |
| 54 | 150 | Blind | outer_diameter_mm | 1682.750 | 1685.000 | -2.250 |
| 54 | 150 | Blind | bore_diameter_mm | 34356.040 | 1352.600 | +33003.440 |
| 54 | 150 | Blind | outer_diameter_mm | 1550.000 | 1685.000 | -135.000 |
| 54 | 150 | Blind | thickness_mm | 85.800 | 119.100 | -33.300 |
| 54 | 150 | Blind | hub_diameter_mm | 1413.000 | 1403.000 | +10.000 |
| 54 | 150 | Blind | length_thru_hub_mm | 85.800 | 119.100 | -33.300 |
| 54 | 150 | Blind | rf_diameter_mm | 1441.000 | 1511.000 | -70.000 |
| 54 | 150 | Blind | pcd_mm | 1492.200 | 1593.800 | -101.600 |
| 54 | 150 | Blind | stud_bolt_length_mm | 240.000 | 355.000 | -115.000 |
| 54 | 150 | Blind | weight_kg | 1291.000 | 2104.000 | -813.000 |
| 54 | 150 | Blind | outer_diameter_mm | 1549.400 | 1685.000 | -135.600 |
| 54 | 150 | Blind | thickness_mm | 85.852 | 119.100 | -33.248 |
| 54 | 150 | Blind | hub_diameter_mm | 1412.748 | 1403.000 | +9.748 |
| 54 | 150 | Blind | length_thru_hub_mm | 85.852 | 119.100 | -33.248 |
| 54 | 150 | Blind | bore_diameter_mm | 34354.770 | 1352.600 | +33002.170 |
| 54 | 150 | Blind | rf_diameter_mm | 1441.450 | 1511.000 | -69.550 |
| 54 | 150 | Blind | pcd_mm | 1492.250 | 1593.800 | -101.550 |
| 54 | 150 | Blind | stud_bolt_length_mm | 241.300 | 355.000 | -113.700 |
| 54 | 150 | Blind | weight_kg | 1291.000 | 2104.000 | -813.000 |
| 56 | 150 | Welding Neck | outer_diameter_mm | 1746.250 | 1745.000 | +1.250 |
| 56 | 150 | Welding Neck | bore_diameter_mm | 35646.360 | 1403.400 | +34242.960 |
| 56 | 150 | Welding Neck | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 56 | 150 | Welding Neck | outer_diameter_mm | 1600.000 | 1745.000 | -145.000 |
| 56 | 150 | Welding Neck | thickness_mm | 71.600 | 122.300 | -50.700 |
| 56 | 150 | Welding Neck | hub_diameter_mm | 1465.000 | 1457.000 | +8.000 |
| 56 | 150 | Welding Neck | length_thru_hub_mm | 71.600 | 227.000 | -155.400 |
| 56 | 150 | Welding Neck | rf_diameter_mm | 1492.000 | 1575.000 | -83.000 |
| 56 | 150 | Welding Neck | pcd_mm | 1543.000 | 1651.000 | -108.000 |
| 56 | 150 | Welding Neck | stud_bolt_length_mm | 250.000 | 360.000 | -110.000 |
| 56 | 150 | Welding Neck | weight_kg | 294.000 | 798.000 | -504.000 |
| 56 | 150 | Welding Neck | outer_diameter_mm | 1600.200 | 1745.000 | -144.800 |
| 56 | 150 | Welding Neck | thickness_mm | 71.628 | 122.300 | -50.672 |
| 56 | 150 | Welding Neck | hub_diameter_mm | 1465.326 | 1457.000 | +8.326 |
| 56 | 150 | Welding Neck | length_thru_hub_mm | 71.628 | 227.000 | -155.372 |
| 56 | 150 | Welding Neck | bore_diameter_mm | 35645.090 | 1403.400 | +34241.690 |
| 56 | 150 | Welding Neck | rf_diameter_mm | 1492.250 | 1575.000 | -82.750 |
| 56 | 150 | Welding Neck | pcd_mm | 1543.050 | 1651.000 | -107.950 |
| 56 | 150 | Welding Neck | stud_bolt_length_mm | 247.650 | 360.000 | -112.350 |
| 56 | 150 | Welding Neck | weight_kg | 294.000 | 798.000 | -504.000 |
| 56 | 150 | Slip On | outer_diameter_mm | 1746.250 | 1745.000 | +1.250 |
| 56 | 150 | Slip On | bore_diameter_mm | 35646.360 | 1403.400 | +34242.960 |
| 56 | 150 | Slip On | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 56 | 150 | Slip On | outer_diameter_mm | 1600.000 | 1745.000 | -145.000 |
| 56 | 150 | Slip On | thickness_mm | 71.600 | 122.300 | -50.700 |
| 56 | 150 | Slip On | hub_diameter_mm | 1465.000 | 1457.000 | +8.000 |
| 56 | 150 | Slip On | length_thru_hub_mm | 71.600 | 122.300 | -50.700 |
| 56 | 150 | Slip On | rf_diameter_mm | 1492.000 | 1575.000 | -83.000 |
| 56 | 150 | Slip On | pcd_mm | 1543.000 | 1651.000 | -108.000 |
| 56 | 150 | Slip On | stud_bolt_length_mm | 250.000 | 360.000 | -110.000 |
| 56 | 150 | Slip On | outer_diameter_mm | 1600.200 | 1745.000 | -144.800 |
| 56 | 150 | Slip On | thickness_mm | 71.628 | 122.300 | -50.672 |
| 56 | 150 | Slip On | hub_diameter_mm | 1465.326 | 1457.000 | +8.326 |
| 56 | 150 | Slip On | length_thru_hub_mm | 71.628 | 122.300 | -50.672 |
| 56 | 150 | Slip On | bore_diameter_mm | 35645.090 | 1403.400 | +34241.690 |
| 56 | 150 | Slip On | rf_diameter_mm | 1492.250 | 1575.000 | -82.750 |
| 56 | 150 | Slip On | pcd_mm | 1543.050 | 1651.000 | -107.950 |
| 56 | 150 | Slip On | stud_bolt_length_mm | 247.650 | 360.000 | -112.350 |
| 56 | 150 | Blind | outer_diameter_mm | 1746.250 | 1745.000 | +1.250 |
| 56 | 150 | Blind | bore_diameter_mm | 35646.360 | 1403.400 | +34242.960 |
| 56 | 150 | Blind | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 56 | 150 | Blind | outer_diameter_mm | 1600.000 | 1745.000 | -145.000 |
| 56 | 150 | Blind | thickness_mm | 89.000 | 122.300 | -33.300 |
| 56 | 150 | Blind | hub_diameter_mm | 1465.000 | 1457.000 | +8.000 |
| 56 | 150 | Blind | length_thru_hub_mm | 89.000 | 122.300 | -33.300 |
| 56 | 150 | Blind | rf_diameter_mm | 1492.000 | 1575.000 | -83.000 |
| 56 | 150 | Blind | pcd_mm | 1543.000 | 1651.000 | -108.000 |
| 56 | 150 | Blind | stud_bolt_length_mm | 250.000 | 360.000 | -110.000 |
| 56 | 150 | Blind | weight_kg | 1426.000 | 2327.000 | -901.000 |
| 56 | 150 | Blind | outer_diameter_mm | 1600.200 | 1745.000 | -144.800 |
| 56 | 150 | Blind | thickness_mm | 88.900 | 122.300 | -33.400 |
| 56 | 150 | Blind | hub_diameter_mm | 1465.326 | 1457.000 | +8.326 |
| 56 | 150 | Blind | length_thru_hub_mm | 88.900 | 122.300 | -33.400 |
| 56 | 150 | Blind | bore_diameter_mm | 35645.090 | 1403.400 | +34241.690 |
| 56 | 150 | Blind | rf_diameter_mm | 1492.250 | 1575.000 | -82.750 |
| 56 | 150 | Blind | pcd_mm | 1543.050 | 1651.000 | -107.950 |
| 56 | 150 | Blind | stud_bolt_length_mm | 247.650 | 360.000 | -112.350 |
| 56 | 150 | Blind | weight_kg | 1426.000 | 2327.000 | -901.000 |
| 58 | 150 | Welding Neck | outer_diameter_mm | 1803.400 | 1805.000 | -1.600 |
| 58 | 150 | Welding Neck | bore_diameter_mm | 36936.680 | 1454.200 | +35482.480 |
| 58 | 150 | Welding Neck | stud_bolt_length_mm | 381.000 | 375.000 | +6.000 |
| 58 | 150 | Welding Neck | outer_diameter_mm | 1675.000 | 1805.000 | -130.000 |
| 58 | 150 | Welding Neck | thickness_mm | 73.100 | 127.000 | -53.900 |
| 58 | 150 | Welding Neck | hub_diameter_mm | 1516.000 | 1508.000 | +8.000 |
| 58 | 150 | Welding Neck | length_thru_hub_mm | 73.100 | 233.000 | -159.900 |
| 58 | 150 | Welding Neck | rf_diameter_mm | 1543.000 | 1626.000 | -83.000 |
| 58 | 150 | Welding Neck | pcd_mm | 1611.300 | 1708.200 | -96.900 |
| 58 | 150 | Welding Neck | stud_bolt_length_mm | 255.000 | 375.000 | -120.000 |
| 58 | 150 | Welding Neck | weight_kg | 353.000 | 868.000 | -515.000 |
| 58 | 150 | Welding Neck | outer_diameter_mm | 1674.876 | 1805.000 | -130.124 |
| 58 | 150 | Welding Neck | thickness_mm | 73.152 | 127.000 | -53.848 |
| 58 | 150 | Welding Neck | hub_diameter_mm | 1516.126 | 1508.000 | +8.126 |
| 58 | 150 | Welding Neck | length_thru_hub_mm | 73.152 | 233.000 | -159.848 |
| 58 | 150 | Welding Neck | bore_diameter_mm | 36935.410 | 1454.200 | +35481.210 |
| 58 | 150 | Welding Neck | rf_diameter_mm | 1543.050 | 1626.000 | -82.950 |
| 58 | 150 | Welding Neck | pcd_mm | 1611.376 | 1708.200 | -96.824 |
| 58 | 150 | Welding Neck | stud_bolt_length_mm | 254.000 | 375.000 | -121.000 |
| 58 | 150 | Welding Neck | weight_kg | 353.000 | 868.000 | -515.000 |
| 58 | 150 | Slip On | outer_diameter_mm | 1803.400 | 1805.000 | -1.600 |
| 58 | 150 | Slip On | bore_diameter_mm | 36936.680 | 1454.200 | +35482.480 |
| 58 | 150 | Slip On | stud_bolt_length_mm | 381.000 | 375.000 | +6.000 |
| 58 | 150 | Slip On | outer_diameter_mm | 1675.000 | 1805.000 | -130.000 |
| 58 | 150 | Slip On | thickness_mm | 73.100 | 127.000 | -53.900 |
| 58 | 150 | Slip On | hub_diameter_mm | 1516.000 | 1508.000 | +8.000 |
| 58 | 150 | Slip On | length_thru_hub_mm | 73.100 | 127.000 | -53.900 |
| 58 | 150 | Slip On | rf_diameter_mm | 1543.000 | 1626.000 | -83.000 |
| 58 | 150 | Slip On | pcd_mm | 1611.300 | 1708.200 | -96.900 |
| 58 | 150 | Slip On | stud_bolt_length_mm | 255.000 | 375.000 | -120.000 |
| 58 | 150 | Slip On | outer_diameter_mm | 1674.876 | 1805.000 | -130.124 |
| 58 | 150 | Slip On | thickness_mm | 73.152 | 127.000 | -53.848 |
| 58 | 150 | Slip On | hub_diameter_mm | 1516.126 | 1508.000 | +8.126 |
| 58 | 150 | Slip On | length_thru_hub_mm | 73.152 | 127.000 | -53.848 |
| 58 | 150 | Slip On | bore_diameter_mm | 36935.410 | 1454.200 | +35481.210 |
| 58 | 150 | Slip On | rf_diameter_mm | 1543.050 | 1626.000 | -82.950 |
| 58 | 150 | Slip On | pcd_mm | 1611.376 | 1708.200 | -96.824 |
| 58 | 150 | Slip On | stud_bolt_length_mm | 254.000 | 375.000 | -121.000 |
| 58 | 150 | Blind | outer_diameter_mm | 1803.400 | 1805.000 | -1.600 |
| 58 | 150 | Blind | bore_diameter_mm | 36936.680 | 1454.200 | +35482.480 |
| 58 | 150 | Blind | stud_bolt_length_mm | 381.000 | 375.000 | +6.000 |
| 58 | 150 | Blind | outer_diameter_mm | 1675.000 | 1805.000 | -130.000 |
| 58 | 150 | Blind | thickness_mm | 91.900 | 127.000 | -35.100 |
| 58 | 150 | Blind | hub_diameter_mm | 1516.000 | 1508.000 | +8.000 |
| 58 | 150 | Blind | length_thru_hub_mm | 91.900 | 127.000 | -35.100 |
| 58 | 150 | Blind | rf_diameter_mm | 1543.000 | 1626.000 | -83.000 |
| 58 | 150 | Blind | pcd_mm | 1611.300 | 1708.200 | -96.900 |
| 58 | 150 | Blind | stud_bolt_length_mm | 255.000 | 375.000 | -120.000 |
| 58 | 150 | Blind | weight_kg | 1614.000 | 2574.000 | -960.000 |
| 58 | 150 | Blind | outer_diameter_mm | 1674.876 | 1805.000 | -130.124 |
| 58 | 150 | Blind | thickness_mm | 91.948 | 127.000 | -35.052 |
| 58 | 150 | Blind | hub_diameter_mm | 1516.126 | 1508.000 | +8.126 |
| 58 | 150 | Blind | length_thru_hub_mm | 91.948 | 127.000 | -35.052 |
| 58 | 150 | Blind | bore_diameter_mm | 36935.410 | 1454.200 | +35481.210 |
| 58 | 150 | Blind | rf_diameter_mm | 1543.050 | 1626.000 | -82.950 |
| 58 | 150 | Blind | pcd_mm | 1611.376 | 1708.200 | -96.824 |
| 58 | 150 | Blind | stud_bolt_length_mm | 254.000 | 375.000 | -121.000 |
| 58 | 150 | Blind | weight_kg | 1614.000 | 2574.000 | -960.000 |
| 60 | 150 | Welding Neck | bore_diameter_mm | 38227.000 | 1505.000 | +36722.000 |
| 60 | 150 | Welding Neck | outer_diameter_mm | 1725.000 | 1855.000 | -130.000 |
| 60 | 150 | Welding Neck | thickness_mm | 74.700 | 130.200 | -55.500 |
| 60 | 150 | Welding Neck | hub_diameter_mm | 1570.000 | 1559.000 | +11.000 |
| 60 | 150 | Welding Neck | length_thru_hub_mm | 74.700 | 238.000 | -163.300 |
| 60 | 150 | Welding Neck | rf_diameter_mm | 1600.000 | 1676.000 | -76.000 |
| 60 | 150 | Welding Neck | pcd_mm | 1662.100 | 1759.000 | -96.900 |
| 60 | 150 | Welding Neck | stud_bolt_length_mm | 260.000 | 380.000 | -120.000 |
| 60 | 150 | Welding Neck | weight_kg | 385.000 | 927.000 | -542.000 |
| 60 | 150 | Welding Neck | outer_diameter_mm | 1725.676 | 1855.000 | -129.324 |
| 60 | 150 | Welding Neck | thickness_mm | 74.676 | 130.200 | -55.524 |
| 60 | 150 | Welding Neck | hub_diameter_mm | 1569.974 | 1559.000 | +10.974 |
| 60 | 150 | Welding Neck | length_thru_hub_mm | 74.676 | 238.000 | -163.324 |
| 60 | 150 | Welding Neck | bore_diameter_mm | 38225.730 | 1505.000 | +36720.730 |
| 60 | 150 | Welding Neck | rf_diameter_mm | 1600.200 | 1676.000 | -75.800 |
| 60 | 150 | Welding Neck | pcd_mm | 1662.176 | 1759.000 | -96.824 |
| 60 | 150 | Welding Neck | stud_bolt_length_mm | 260.350 | 380.000 | -119.650 |
| 60 | 150 | Welding Neck | weight_kg | 385.000 | 927.000 | -542.000 |
| 60 | 150 | Slip On | bore_diameter_mm | 38227.000 | 1505.000 | +36722.000 |
| 60 | 150 | Slip On | outer_diameter_mm | 1725.000 | 1855.000 | -130.000 |
| 60 | 150 | Slip On | thickness_mm | 74.700 | 130.200 | -55.500 |
| 60 | 150 | Slip On | hub_diameter_mm | 1570.000 | 1559.000 | +11.000 |
| 60 | 150 | Slip On | length_thru_hub_mm | 74.700 | 130.200 | -55.500 |
| 60 | 150 | Slip On | rf_diameter_mm | 1600.000 | 1676.000 | -76.000 |
| 60 | 150 | Slip On | pcd_mm | 1662.100 | 1759.000 | -96.900 |
| 60 | 150 | Slip On | stud_bolt_length_mm | 260.000 | 380.000 | -120.000 |
| 60 | 150 | Slip On | outer_diameter_mm | 1725.676 | 1855.000 | -129.324 |
| 60 | 150 | Slip On | thickness_mm | 74.676 | 130.200 | -55.524 |
| 60 | 150 | Slip On | hub_diameter_mm | 1569.974 | 1559.000 | +10.974 |
| 60 | 150 | Slip On | length_thru_hub_mm | 74.676 | 130.200 | -55.524 |
| 60 | 150 | Slip On | bore_diameter_mm | 38225.730 | 1505.000 | +36720.730 |
| 60 | 150 | Slip On | rf_diameter_mm | 1600.200 | 1676.000 | -75.800 |
| 60 | 150 | Slip On | pcd_mm | 1662.176 | 1759.000 | -96.824 |
| 60 | 150 | Slip On | stud_bolt_length_mm | 260.350 | 380.000 | -119.650 |
| 60 | 150 | Blind | bore_diameter_mm | 38227.000 | 1505.000 | +36722.000 |
| 60 | 150 | Blind | outer_diameter_mm | 1725.000 | 1855.000 | -130.000 |
| 60 | 150 | Blind | thickness_mm | 95.400 | 130.200 | -34.800 |
| 60 | 150 | Blind | hub_diameter_mm | 1570.000 | 1559.000 | +11.000 |
| 60 | 150 | Blind | length_thru_hub_mm | 95.400 | 130.200 | -34.800 |
| 60 | 150 | Blind | rf_diameter_mm | 1600.000 | 1676.000 | -76.000 |
| 60 | 150 | Blind | pcd_mm | 1662.100 | 1759.000 | -96.900 |
| 60 | 150 | Blind | stud_bolt_length_mm | 260.000 | 380.000 | -120.000 |
| 60 | 150 | Blind | weight_kg | 1774.000 | 2791.000 | -1017.000 |
| 60 | 150 | Blind | outer_diameter_mm | 1725.676 | 1855.000 | -129.324 |
| 60 | 150 | Blind | thickness_mm | 95.250 | 130.200 | -34.950 |
| 60 | 150 | Blind | hub_diameter_mm | 1569.974 | 1559.000 | +10.974 |
| 60 | 150 | Blind | length_thru_hub_mm | 95.250 | 130.200 | -34.950 |
| 60 | 150 | Blind | bore_diameter_mm | 38225.730 | 1505.000 | +36720.730 |
| 60 | 150 | Blind | rf_diameter_mm | 1600.200 | 1676.000 | -75.800 |
| 60 | 150 | Blind | pcd_mm | 1662.176 | 1759.000 | -96.824 |
| 60 | 150 | Blind | stud_bolt_length_mm | 260.350 | 380.000 | -119.650 |
| 60 | 150 | Blind | weight_kg | 1774.000 | 2791.000 | -1017.000 |
| 26 | 300 | Welding Neck | outer_diameter_mm | 971.550 | 970.000 | +1.550 |
| 26 | 300 | Welding Neck | bore_diameter_mm | 16291.560 | 641.400 | +15650.160 |
| 26 | 300 | Welding Neck | stud_bolt_length_mm | 279.400 | 275.000 | +4.400 |
| 26 | 300 | Welding Neck | outer_diameter_mm | 865.000 | 970.000 | -105.000 |
| 26 | 300 | Welding Neck | thickness_mm | 87.400 | 77.800 | +9.600 |
| 26 | 300 | Welding Neck | hub_diameter_mm | 702.000 | 721.000 | -19.000 |
| 26 | 300 | Welding Neck | length_thru_hub_mm | 87.400 | 183.000 | -95.600 |
| 26 | 300 | Welding Neck | rf_diameter_mm | 737.000 | 749.300 | -12.300 |
| 26 | 300 | Welding Neck | pcd_mm | 803.300 | 876.300 | -73.000 |
| 26 | 300 | Welding Neck | stud_bolt_length_mm | 265.000 | 275.000 | -10.000 |
| 26 | 300 | Welding Neck | weight_kg | 181.000 | 274.000 | -93.000 |
| 26 | 300 | Welding Neck | outer_diameter_mm | 866.648 | 970.000 | -103.352 |
| 26 | 300 | Welding Neck | thickness_mm | 87.376 | 77.800 | +9.576 |
| 26 | 300 | Welding Neck | hub_diameter_mm | 701.548 | 721.000 | -19.452 |
| 26 | 300 | Welding Neck | length_thru_hub_mm | 87.376 | 183.000 | -95.624 |
| 26 | 300 | Welding Neck | bore_diameter_mm | 16290.290 | 641.400 | +15648.890 |
| 26 | 300 | Welding Neck | rf_diameter_mm | 736.600 | 749.300 | -12.700 |
| 26 | 300 | Welding Neck | pcd_mm | 803.148 | 876.300 | -73.152 |
| 26 | 300 | Welding Neck | stud_bolt_length_mm | 266.700 | 275.000 | -8.300 |
| 26 | 300 | Welding Neck | weight_kg | 181.000 | 274.000 | -93.000 |
| 26 | 300 | Slip On | outer_diameter_mm | 971.550 | 970.000 | +1.550 |
| 26 | 300 | Slip On | length_thru_hub_mm | 4677.410 | 184.150 | +4493.260 |
| 26 | 300 | Slip On | bore_diameter_mm | 16291.560 | 641.400 | +15650.160 |
| 26 | 300 | Slip On | stud_bolt_length_mm | 279.400 | 275.000 | +4.400 |
| 26 | 300 | Slip On | outer_diameter_mm | 865.000 | 970.000 | -105.000 |
| 26 | 300 | Slip On | thickness_mm | 87.400 | 77.800 | +9.600 |
| 26 | 300 | Slip On | hub_diameter_mm | 702.000 | 721.000 | -19.000 |
| 26 | 300 | Slip On | length_thru_hub_mm | 87.400 | 184.150 | -96.750 |
| 26 | 300 | Slip On | rf_diameter_mm | 737.000 | 749.300 | -12.300 |
| 26 | 300 | Slip On | pcd_mm | 803.300 | 876.300 | -73.000 |
| 26 | 300 | Slip On | stud_bolt_length_mm | 265.000 | 275.000 | -10.000 |
| 26 | 300 | Slip On | outer_diameter_mm | 866.648 | 970.000 | -103.352 |
| 26 | 300 | Slip On | thickness_mm | 87.376 | 77.800 | +9.576 |
| 26 | 300 | Slip On | hub_diameter_mm | 701.548 | 721.000 | -19.452 |
| 26 | 300 | Slip On | length_thru_hub_mm | 87.376 | 184.150 | -96.774 |
| 26 | 300 | Slip On | bore_diameter_mm | 16290.290 | 641.400 | +15648.890 |
| 26 | 300 | Slip On | rf_diameter_mm | 736.600 | 749.300 | -12.700 |
| 26 | 300 | Slip On | pcd_mm | 803.148 | 876.300 | -73.152 |
| 26 | 300 | Slip On | stud_bolt_length_mm | 266.700 | 275.000 | -8.300 |
| 26 | 300 | Blind | outer_diameter_mm | 971.550 | 970.000 | +1.550 |
| 26 | 300 | Blind | bore_diameter_mm | 16291.560 | 641.400 | +15650.160 |
| 26 | 300 | Blind | stud_bolt_length_mm | 279.400 | 275.000 | +4.400 |
| 26 | 300 | Blind | outer_diameter_mm | 865.000 | 970.000 | -105.000 |
| 26 | 300 | Blind | thickness_mm | 87.400 | 82.600 | +4.800 |
| 26 | 300 | Blind | hub_diameter_mm | 702.000 | 721.000 | -19.000 |
| 26 | 300 | Blind | length_thru_hub_mm | 87.400 | 82.600 | +4.800 |
| 26 | 300 | Blind | rf_diameter_mm | 737.000 | 749.300 | -12.300 |
| 26 | 300 | Blind | pcd_mm | 803.300 | 876.300 | -73.000 |
| 26 | 300 | Blind | stud_bolt_length_mm | 265.000 | 275.000 | -10.000 |
| 26 | 300 | Blind | weight_kg | 411.000 | 488.000 | -77.000 |
| 26 | 300 | Blind | outer_diameter_mm | 866.648 | 970.000 | -103.352 |
| 26 | 300 | Blind | thickness_mm | 87.376 | 82.600 | +4.776 |
| 26 | 300 | Blind | hub_diameter_mm | 701.548 | 721.000 | -19.452 |
| 26 | 300 | Blind | length_thru_hub_mm | 87.376 | 82.600 | +4.776 |
| 26 | 300 | Blind | bore_diameter_mm | 16290.290 | 641.400 | +15648.890 |
| 26 | 300 | Blind | rf_diameter_mm | 736.600 | 749.300 | -12.700 |
| 26 | 300 | Blind | pcd_mm | 803.148 | 876.300 | -73.152 |
| 26 | 300 | Blind | stud_bolt_length_mm | 266.700 | 275.000 | -8.300 |
| 26 | 300 | Blind | weight_kg | 411.000 | 488.000 | -77.000 |
| 28 | 300 | Welding Neck | bore_diameter_mm | 17581.880 | 692.200 | +16889.680 |
| 28 | 300 | Welding Neck | outer_diameter_mm | 920.000 | 1035.000 | -115.000 |
| 28 | 300 | Welding Neck | thickness_mm | 87.400 | 84.200 | +3.200 |
| 28 | 300 | Welding Neck | hub_diameter_mm | 756.000 | 775.000 | -19.000 |
| 28 | 300 | Welding Neck | length_thru_hub_mm | 87.400 | 195.000 | -107.600 |
| 28 | 300 | Welding Neck | rf_diameter_mm | 787.000 | 800.100 | -13.100 |
| 28 | 300 | Welding Neck | pcd_mm | 857.200 | 939.800 | -82.600 |
| 28 | 300 | Welding Neck | stud_bolt_length_mm | 265.000 | 285.000 | -20.000 |
| 28 | 300 | Welding Neck | weight_kg | 204.000 | 337.000 | -133.000 |
| 28 | 300 | Welding Neck | outer_diameter_mm | 920.750 | 1035.000 | -114.250 |
| 28 | 300 | Welding Neck | thickness_mm | 87.376 | 84.200 | +3.176 |
| 28 | 300 | Welding Neck | hub_diameter_mm | 755.650 | 775.000 | -19.350 |
| 28 | 300 | Welding Neck | length_thru_hub_mm | 87.376 | 195.000 | -107.624 |
| 28 | 300 | Welding Neck | bore_diameter_mm | 17580.610 | 692.200 | +16888.410 |
| 28 | 300 | Welding Neck | rf_diameter_mm | 787.400 | 800.100 | -12.700 |
| 28 | 300 | Welding Neck | pcd_mm | 857.250 | 939.800 | -82.550 |
| 28 | 300 | Welding Neck | stud_bolt_length_mm | 266.700 | 285.000 | -18.300 |
| 28 | 300 | Welding Neck | weight_kg | 204.000 | 337.000 | -133.000 |
| 28 | 300 | Slip On | length_thru_hub_mm | 4999.990 | 196.850 | +4803.140 |
| 28 | 300 | Slip On | bore_diameter_mm | 17581.880 | 692.200 | +16889.680 |
| 28 | 300 | Slip On | outer_diameter_mm | 920.000 | 1035.000 | -115.000 |
| 28 | 300 | Slip On | thickness_mm | 87.400 | 84.200 | +3.200 |
| 28 | 300 | Slip On | hub_diameter_mm | 756.000 | 775.000 | -19.000 |
| 28 | 300 | Slip On | length_thru_hub_mm | 87.400 | 196.850 | -109.450 |
| 28 | 300 | Slip On | rf_diameter_mm | 787.000 | 800.100 | -13.100 |
| 28 | 300 | Slip On | pcd_mm | 857.200 | 939.800 | -82.600 |
| 28 | 300 | Slip On | stud_bolt_length_mm | 265.000 | 285.000 | -20.000 |
| 28 | 300 | Slip On | outer_diameter_mm | 920.750 | 1035.000 | -114.250 |
| 28 | 300 | Slip On | thickness_mm | 87.376 | 84.200 | +3.176 |
| 28 | 300 | Slip On | hub_diameter_mm | 755.650 | 775.000 | -19.350 |
| 28 | 300 | Slip On | length_thru_hub_mm | 87.376 | 196.850 | -109.474 |
| 28 | 300 | Slip On | bore_diameter_mm | 17580.610 | 692.200 | +16888.410 |
| 28 | 300 | Slip On | rf_diameter_mm | 787.400 | 800.100 | -12.700 |
| 28 | 300 | Slip On | pcd_mm | 857.250 | 939.800 | -82.550 |
| 28 | 300 | Slip On | stud_bolt_length_mm | 266.700 | 285.000 | -18.300 |
| 28 | 300 | Blind | bore_diameter_mm | 17581.880 | 692.200 | +16889.680 |
| 28 | 300 | Blind | outer_diameter_mm | 920.000 | 1035.000 | -115.000 |
| 28 | 300 | Blind | thickness_mm | 87.400 | 88.900 | -1.500 |
| 28 | 300 | Blind | hub_diameter_mm | 756.000 | 775.000 | -19.000 |
| 28 | 300 | Blind | length_thru_hub_mm | 87.400 | 88.900 | -1.500 |
| 28 | 300 | Blind | rf_diameter_mm | 787.000 | 800.100 | -13.100 |
| 28 | 300 | Blind | pcd_mm | 857.200 | 939.800 | -82.600 |
| 28 | 300 | Blind | stud_bolt_length_mm | 265.000 | 285.000 | -20.000 |
| 28 | 300 | Blind | weight_kg | 464.000 | 596.000 | -132.000 |
| 28 | 300 | Blind | outer_diameter_mm | 920.750 | 1035.000 | -114.250 |
| 28 | 300 | Blind | thickness_mm | 87.376 | 88.900 | -1.524 |
| 28 | 300 | Blind | hub_diameter_mm | 755.650 | 775.000 | -19.350 |
| 28 | 300 | Blind | length_thru_hub_mm | 87.376 | 88.900 | -1.524 |
| 28 | 300 | Blind | bore_diameter_mm | 17580.610 | 692.200 | +16888.410 |
| 28 | 300 | Blind | rf_diameter_mm | 787.400 | 800.100 | -12.700 |
| 28 | 300 | Blind | pcd_mm | 857.250 | 939.800 | -82.550 |
| 28 | 300 | Blind | stud_bolt_length_mm | 266.700 | 285.000 | -18.300 |
| 28 | 300 | Blind | weight_kg | 464.000 | 596.000 | -132.000 |
| 30 | 300 | Welding Neck | outer_diameter_mm | 1092.200 | 1090.000 | +2.200 |
| 30 | 300 | Welding Neck | bore_diameter_mm | 18872.200 | 743.000 | +18129.200 |
| 30 | 300 | Welding Neck | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 30 | 300 | Welding Neck | outer_diameter_mm | 990.000 | 1090.000 | -100.000 |
| 30 | 300 | Welding Neck | thickness_mm | 92.100 | 90.500 | +1.600 |
| 30 | 300 | Welding Neck | hub_diameter_mm | 813.000 | 827.000 | -14.000 |
| 30 | 300 | Welding Neck | length_thru_hub_mm | 92.100 | 208.000 | -115.900 |
| 30 | 300 | Welding Neck | rf_diameter_mm | 845.000 | 857.250 | -12.250 |
| 30 | 300 | Welding Neck | pcd_mm | 920.800 | 997.000 | -76.200 |
| 30 | 300 | Welding Neck | stud_bolt_length_mm | 285.000 | 305.000 | -20.000 |
| 30 | 300 | Welding Neck | weight_kg | 249.000 | 394.000 | -145.000 |
| 30 | 300 | Welding Neck | outer_diameter_mm | 990.600 | 1090.000 | -99.400 |
| 30 | 300 | Welding Neck | thickness_mm | 92.202 | 90.500 | +1.702 |
| 30 | 300 | Welding Neck | hub_diameter_mm | 812.800 | 827.000 | -14.200 |
| 30 | 300 | Welding Neck | length_thru_hub_mm | 92.202 | 208.000 | -115.798 |
| 30 | 300 | Welding Neck | bore_diameter_mm | 18870.930 | 743.000 | +18127.930 |
| 30 | 300 | Welding Neck | rf_diameter_mm | 844.550 | 857.250 | -12.700 |
| 30 | 300 | Welding Neck | pcd_mm | 920.750 | 997.000 | -76.250 |
| 30 | 300 | Welding Neck | stud_bolt_length_mm | 285.750 | 305.000 | -19.250 |
| 30 | 300 | Welding Neck | weight_kg | 249.000 | 394.000 | -145.000 |
| 30 | 300 | Slip On | outer_diameter_mm | 1092.200 | 1090.000 | +2.200 |
| 30 | 300 | Slip On | length_thru_hub_mm | 5322.570 | 209.550 | +5113.020 |
| 30 | 300 | Slip On | bore_diameter_mm | 18872.200 | 743.000 | +18129.200 |
| 30 | 300 | Slip On | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 30 | 300 | Slip On | outer_diameter_mm | 990.000 | 1090.000 | -100.000 |
| 30 | 300 | Slip On | thickness_mm | 92.100 | 90.500 | +1.600 |
| 30 | 300 | Slip On | hub_diameter_mm | 813.000 | 827.000 | -14.000 |
| 30 | 300 | Slip On | length_thru_hub_mm | 92.100 | 209.550 | -117.450 |
| 30 | 300 | Slip On | rf_diameter_mm | 845.000 | 857.250 | -12.250 |
| 30 | 300 | Slip On | pcd_mm | 920.800 | 997.000 | -76.200 |
| 30 | 300 | Slip On | stud_bolt_length_mm | 285.000 | 305.000 | -20.000 |
| 30 | 300 | Slip On | outer_diameter_mm | 990.600 | 1090.000 | -99.400 |
| 30 | 300 | Slip On | thickness_mm | 92.202 | 90.500 | +1.702 |
| 30 | 300 | Slip On | hub_diameter_mm | 812.800 | 827.000 | -14.200 |
| 30 | 300 | Slip On | length_thru_hub_mm | 92.202 | 209.550 | -117.348 |
| 30 | 300 | Slip On | bore_diameter_mm | 18870.930 | 743.000 | +18127.930 |
| 30 | 300 | Slip On | rf_diameter_mm | 844.550 | 857.250 | -12.700 |
| 30 | 300 | Slip On | pcd_mm | 920.750 | 997.000 | -76.250 |
| 30 | 300 | Slip On | stud_bolt_length_mm | 285.750 | 305.000 | -19.250 |
| 30 | 300 | Blind | outer_diameter_mm | 1092.200 | 1090.000 | +2.200 |
| 30 | 300 | Blind | bore_diameter_mm | 18872.200 | 743.000 | +18129.200 |
| 30 | 300 | Blind | stud_bolt_length_mm | 311.150 | 305.000 | +6.150 |
| 30 | 300 | Blind | outer_diameter_mm | 990.000 | 1090.000 | -100.000 |
| 30 | 300 | Blind | thickness_mm | 92.100 | 93.700 | -1.600 |
| 30 | 300 | Blind | hub_diameter_mm | 813.000 | 827.000 | -14.000 |
| 30 | 300 | Blind | length_thru_hub_mm | 92.100 | 93.700 | -1.600 |
| 30 | 300 | Blind | rf_diameter_mm | 845.000 | 857.250 | -12.250 |
| 30 | 300 | Blind | pcd_mm | 920.800 | 997.000 | -76.200 |
| 30 | 300 | Blind | stud_bolt_length_mm | 285.000 | 305.000 | -20.000 |
| 30 | 300 | Blind | weight_kg | 566.000 | 699.000 | -133.000 |
| 30 | 300 | Blind | outer_diameter_mm | 990.600 | 1090.000 | -99.400 |
| 30 | 300 | Blind | thickness_mm | 92.202 | 93.700 | -1.498 |
| 30 | 300 | Blind | hub_diameter_mm | 812.800 | 827.000 | -14.200 |
| 30 | 300 | Blind | length_thru_hub_mm | 92.202 | 93.700 | -1.498 |
| 30 | 300 | Blind | bore_diameter_mm | 18870.930 | 743.000 | +18127.930 |
| 30 | 300 | Blind | rf_diameter_mm | 844.550 | 857.250 | -12.700 |
| 30 | 300 | Blind | pcd_mm | 920.750 | 997.000 | -76.250 |
| 30 | 300 | Blind | stud_bolt_length_mm | 285.750 | 305.000 | -19.250 |
| 30 | 300 | Blind | weight_kg | 566.000 | 699.000 | -133.000 |
| 32 | 300 | Welding Neck | bore_diameter_mm | 20162.520 | 793.800 | +19368.720 |
| 32 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 32 | 300 | Welding Neck | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 32 | 300 | Welding Neck | outer_diameter_mm | 1055.000 | 1150.000 | -95.000 |
| 32 | 300 | Welding Neck | thickness_mm | 101.600 | 96.900 | +4.700 |
| 32 | 300 | Welding Neck | hub_diameter_mm | 864.000 | 881.000 | -17.000 |
| 32 | 300 | Welding Neck | length_thru_hub_mm | 101.600 | 221.000 | -119.400 |
| 32 | 300 | Welding Neck | rf_diameter_mm | 902.000 | 914.400 | -12.400 |
| 32 | 300 | Welding Neck | pcd_mm | 977.900 | 1054.100 | -76.200 |
| 32 | 300 | Welding Neck | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 32 | 300 | Welding Neck | stud_bolt_length_mm | 305.000 | 320.000 | -15.000 |
| 32 | 300 | Welding Neck | weight_kg | 310.000 | 455.000 | -145.000 |
| 32 | 300 | Welding Neck | outer_diameter_mm | 1054.100 | 1150.000 | -95.900 |
| 32 | 300 | Welding Neck | thickness_mm | 101.600 | 96.900 | +4.700 |
| 32 | 300 | Welding Neck | hub_diameter_mm | 863.600 | 881.000 | -17.400 |
| 32 | 300 | Welding Neck | length_thru_hub_mm | 101.600 | 221.000 | -119.400 |
| 32 | 300 | Welding Neck | bore_diameter_mm | 20161.250 | 793.800 | +19367.450 |
| 32 | 300 | Welding Neck | rf_diameter_mm | 901.700 | 914.400 | -12.700 |
| 32 | 300 | Welding Neck | pcd_mm | 977.900 | 1054.100 | -76.200 |
| 32 | 300 | Welding Neck | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 32 | 300 | Welding Neck | stud_bolt_length_mm | 304.800 | 320.000 | -15.200 |
| 32 | 300 | Welding Neck | weight_kg | 310.000 | 455.000 | -145.000 |
| 32 | 300 | Slip On | length_thru_hub_mm | 5645.150 | 222.250 | +5422.900 |
| 32 | 300 | Slip On | bore_diameter_mm | 20162.520 | 793.800 | +19368.720 |
| 32 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 32 | 300 | Slip On | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 32 | 300 | Slip On | outer_diameter_mm | 1055.000 | 1150.000 | -95.000 |
| 32 | 300 | Slip On | thickness_mm | 101.600 | 96.900 | +4.700 |
| 32 | 300 | Slip On | hub_diameter_mm | 864.000 | 881.000 | -17.000 |
| 32 | 300 | Slip On | length_thru_hub_mm | 101.600 | 222.250 | -120.650 |
| 32 | 300 | Slip On | rf_diameter_mm | 902.000 | 914.400 | -12.400 |
| 32 | 300 | Slip On | pcd_mm | 977.900 | 1054.100 | -76.200 |
| 32 | 300 | Slip On | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 32 | 300 | Slip On | stud_bolt_length_mm | 305.000 | 320.000 | -15.000 |
| 32 | 300 | Slip On | outer_diameter_mm | 1054.100 | 1150.000 | -95.900 |
| 32 | 300 | Slip On | thickness_mm | 101.600 | 96.900 | +4.700 |
| 32 | 300 | Slip On | hub_diameter_mm | 863.600 | 881.000 | -17.400 |
| 32 | 300 | Slip On | length_thru_hub_mm | 101.600 | 222.250 | -120.650 |
| 32 | 300 | Slip On | bore_diameter_mm | 20161.250 | 793.800 | +19367.450 |
| 32 | 300 | Slip On | rf_diameter_mm | 901.700 | 914.400 | -12.700 |
| 32 | 300 | Slip On | pcd_mm | 977.900 | 1054.100 | -76.200 |
| 32 | 300 | Slip On | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 32 | 300 | Slip On | stud_bolt_length_mm | 304.800 | 320.000 | -15.200 |
| 32 | 300 | Blind | bore_diameter_mm | 20162.520 | 793.800 | +19368.720 |
| 32 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 32 | 300 | Blind | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 32 | 300 | Blind | outer_diameter_mm | 1055.000 | 1150.000 | -95.000 |
| 32 | 300 | Blind | thickness_mm | 101.600 | 98.500 | +3.100 |
| 32 | 300 | Blind | hub_diameter_mm | 864.000 | 881.000 | -17.000 |
| 32 | 300 | Blind | length_thru_hub_mm | 101.600 | 98.500 | +3.100 |
| 32 | 300 | Blind | rf_diameter_mm | 902.000 | 914.400 | -12.400 |
| 32 | 300 | Blind | pcd_mm | 977.900 | 1054.100 | -76.200 |
| 32 | 300 | Blind | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 32 | 300 | Blind | stud_bolt_length_mm | 305.000 | 320.000 | -15.000 |
| 32 | 300 | Blind | weight_kg | 705.000 | 814.000 | -109.000 |
| 32 | 300 | Blind | outer_diameter_mm | 1054.100 | 1150.000 | -95.900 |
| 32 | 300 | Blind | thickness_mm | 101.600 | 98.500 | +3.100 |
| 32 | 300 | Blind | hub_diameter_mm | 863.600 | 881.000 | -17.400 |
| 32 | 300 | Blind | length_thru_hub_mm | 101.600 | 98.500 | +3.100 |
| 32 | 300 | Blind | bore_diameter_mm | 20161.250 | 793.800 | +19367.450 |
| 32 | 300 | Blind | rf_diameter_mm | 901.700 | 914.400 | -12.700 |
| 32 | 300 | Blind | pcd_mm | 977.900 | 1054.100 | -76.200 |
| 32 | 300 | Blind | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 32 | 300 | Blind | stud_bolt_length_mm | 304.800 | 320.000 | -15.200 |
| 32 | 300 | Blind | weight_kg | 705.000 | 814.000 | -109.000 |
| 34 | 300 | Welding Neck | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 34 | 300 | Welding Neck | bore_diameter_mm | 21452.840 | 844.600 | +20608.240 |
| 34 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 34 | 300 | Welding Neck | outer_diameter_mm | 1110.000 | 1205.000 | -95.000 |
| 34 | 300 | Welding Neck | thickness_mm | 101.600 | 100.100 | +1.500 |
| 34 | 300 | Welding Neck | hub_diameter_mm | 918.000 | 937.000 | -19.000 |
| 34 | 300 | Welding Neck | length_thru_hub_mm | 101.600 | 230.000 | -128.400 |
| 34 | 300 | Welding Neck | rf_diameter_mm | 953.000 | 965.200 | -12.200 |
| 34 | 300 | Welding Neck | pcd_mm | 1031.900 | 1104.900 | -73.000 |
| 34 | 300 | Welding Neck | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 34 | 300 | Welding Neck | stud_bolt_length_mm | 305.000 | 330.000 | -25.000 |
| 34 | 300 | Welding Neck | weight_kg | 340.000 | 519.000 | -179.000 |
| 34 | 300 | Welding Neck | outer_diameter_mm | 1107.948 | 1205.000 | -97.052 |
| 34 | 300 | Welding Neck | thickness_mm | 101.600 | 100.100 | +1.500 |
| 34 | 300 | Welding Neck | hub_diameter_mm | 917.448 | 937.000 | -19.552 |
| 34 | 300 | Welding Neck | length_thru_hub_mm | 101.600 | 230.000 | -128.400 |
| 34 | 300 | Welding Neck | bore_diameter_mm | 21451.570 | 844.600 | +20606.970 |
| 34 | 300 | Welding Neck | rf_diameter_mm | 952.500 | 965.200 | -12.700 |
| 34 | 300 | Welding Neck | pcd_mm | 1031.748 | 1104.900 | -73.152 |
| 34 | 300 | Welding Neck | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 34 | 300 | Welding Neck | stud_bolt_length_mm | 304.800 | 330.000 | -25.200 |
| 34 | 300 | Welding Neck | weight_kg | 340.000 | 519.000 | -179.000 |
| 34 | 300 | Slip On | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 34 | 300 | Slip On | length_thru_hub_mm | 5887.085 | 231.775 | +5655.310 |
| 34 | 300 | Slip On | bore_diameter_mm | 21452.840 | 844.600 | +20608.240 |
| 34 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 34 | 300 | Slip On | outer_diameter_mm | 1110.000 | 1205.000 | -95.000 |
| 34 | 300 | Slip On | thickness_mm | 101.600 | 100.100 | +1.500 |
| 34 | 300 | Slip On | hub_diameter_mm | 918.000 | 937.000 | -19.000 |
| 34 | 300 | Slip On | length_thru_hub_mm | 101.600 | 231.775 | -130.175 |
| 34 | 300 | Slip On | rf_diameter_mm | 953.000 | 965.200 | -12.200 |
| 34 | 300 | Slip On | pcd_mm | 1031.900 | 1104.900 | -73.000 |
| 34 | 300 | Slip On | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 34 | 300 | Slip On | stud_bolt_length_mm | 305.000 | 330.000 | -25.000 |
| 34 | 300 | Slip On | outer_diameter_mm | 1107.948 | 1205.000 | -97.052 |
| 34 | 300 | Slip On | thickness_mm | 101.600 | 100.100 | +1.500 |
| 34 | 300 | Slip On | hub_diameter_mm | 917.448 | 937.000 | -19.552 |
| 34 | 300 | Slip On | length_thru_hub_mm | 101.600 | 231.775 | -130.175 |
| 34 | 300 | Slip On | bore_diameter_mm | 21451.570 | 844.600 | +20606.970 |
| 34 | 300 | Slip On | rf_diameter_mm | 952.500 | 965.200 | -12.700 |
| 34 | 300 | Slip On | pcd_mm | 1031.748 | 1104.900 | -73.152 |
| 34 | 300 | Slip On | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 34 | 300 | Slip On | stud_bolt_length_mm | 304.800 | 330.000 | -25.200 |
| 34 | 300 | Blind | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 34 | 300 | Blind | bore_diameter_mm | 21452.840 | 844.600 | +20608.240 |
| 34 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 34 | 300 | Blind | outer_diameter_mm | 1110.000 | 1205.000 | -95.000 |
| 34 | 300 | Blind | thickness_mm | 101.600 | 103.200 | -1.600 |
| 34 | 300 | Blind | hub_diameter_mm | 918.000 | 937.000 | -19.000 |
| 34 | 300 | Blind | length_thru_hub_mm | 101.600 | 103.200 | -1.600 |
| 34 | 300 | Blind | rf_diameter_mm | 953.000 | 965.200 | -12.200 |
| 34 | 300 | Blind | pcd_mm | 1031.900 | 1104.900 | -73.000 |
| 34 | 300 | Blind | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 34 | 300 | Blind | stud_bolt_length_mm | 305.000 | 330.000 | -25.000 |
| 34 | 300 | Blind | weight_kg | 779.000 | 938.000 | -159.000 |
| 34 | 300 | Blind | outer_diameter_mm | 1107.948 | 1205.000 | -97.052 |
| 34 | 300 | Blind | thickness_mm | 101.600 | 103.200 | -1.600 |
| 34 | 300 | Blind | hub_diameter_mm | 917.448 | 937.000 | -19.552 |
| 34 | 300 | Blind | length_thru_hub_mm | 101.600 | 103.200 | -1.600 |
| 34 | 300 | Blind | bore_diameter_mm | 21451.570 | 844.600 | +20606.970 |
| 34 | 300 | Blind | rf_diameter_mm | 952.500 | 965.200 | -12.700 |
| 34 | 300 | Blind | pcd_mm | 1031.748 | 1104.900 | -73.152 |
| 34 | 300 | Blind | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 34 | 300 | Blind | stud_bolt_length_mm | 304.800 | 330.000 | -25.200 |
| 34 | 300 | Blind | weight_kg | 779.000 | 938.000 | -159.000 |
| 36 | 300 | Welding Neck | bore_diameter_mm | 22743.160 | 895.400 | +21847.760 |
| 36 | 300 | Welding Neck | stud_bolt_length_mm | 349.250 | 345.000 | +4.250 |
| 36 | 300 | Welding Neck | outer_diameter_mm | 1170.000 | 1270.000 | -100.000 |
| 36 | 300 | Welding Neck | thickness_mm | 101.600 | 103.200 | -1.600 |
| 36 | 300 | Welding Neck | hub_diameter_mm | 965.000 | 991.000 | -26.000 |
| 36 | 300 | Welding Neck | length_thru_hub_mm | 101.600 | 240.000 | -138.400 |
| 36 | 300 | Welding Neck | rf_diameter_mm | 1010.000 | 1022.350 | -12.350 |
| 36 | 300 | Welding Neck | pcd_mm | 1089.000 | 1168.400 | -79.400 |
| 36 | 300 | Welding Neck | stud_bolt_length_mm | 320.000 | 345.000 | -25.000 |
| 36 | 300 | Welding Neck | weight_kg | 381.000 | 578.000 | -197.000 |
| 36 | 300 | Welding Neck | outer_diameter_mm | 1171.448 | 1270.000 | -98.552 |
| 36 | 300 | Welding Neck | thickness_mm | 101.600 | 103.200 | -1.600 |
| 36 | 300 | Welding Neck | hub_diameter_mm | 965.200 | 991.000 | -25.800 |
| 36 | 300 | Welding Neck | length_thru_hub_mm | 101.600 | 240.000 | -138.400 |
| 36 | 300 | Welding Neck | bore_diameter_mm | 22741.890 | 895.400 | +21846.490 |
| 36 | 300 | Welding Neck | rf_diameter_mm | 1009.650 | 1022.350 | -12.700 |
| 36 | 300 | Welding Neck | pcd_mm | 1089.152 | 1168.400 | -79.248 |
| 36 | 300 | Welding Neck | stud_bolt_length_mm | 317.500 | 345.000 | -27.500 |
| 36 | 300 | Welding Neck | weight_kg | 381.000 | 578.000 | -197.000 |
| 36 | 300 | Slip On | length_thru_hub_mm | 6129.020 | 241.300 | +5887.720 |
| 36 | 300 | Slip On | bore_diameter_mm | 22743.160 | 895.400 | +21847.760 |
| 36 | 300 | Slip On | stud_bolt_length_mm | 349.250 | 345.000 | +4.250 |
| 36 | 300 | Slip On | outer_diameter_mm | 1170.000 | 1270.000 | -100.000 |
| 36 | 300 | Slip On | thickness_mm | 101.600 | 103.200 | -1.600 |
| 36 | 300 | Slip On | hub_diameter_mm | 965.000 | 991.000 | -26.000 |
| 36 | 300 | Slip On | length_thru_hub_mm | 101.600 | 241.300 | -139.700 |
| 36 | 300 | Slip On | rf_diameter_mm | 1010.000 | 1022.350 | -12.350 |
| 36 | 300 | Slip On | pcd_mm | 1089.000 | 1168.400 | -79.400 |
| 36 | 300 | Slip On | stud_bolt_length_mm | 320.000 | 345.000 | -25.000 |
| 36 | 300 | Slip On | outer_diameter_mm | 1171.448 | 1270.000 | -98.552 |
| 36 | 300 | Slip On | thickness_mm | 101.600 | 103.200 | -1.600 |
| 36 | 300 | Slip On | hub_diameter_mm | 965.200 | 991.000 | -25.800 |
| 36 | 300 | Slip On | length_thru_hub_mm | 101.600 | 241.300 | -139.700 |
| 36 | 300 | Slip On | bore_diameter_mm | 22741.890 | 895.400 | +21846.490 |
| 36 | 300 | Slip On | rf_diameter_mm | 1009.650 | 1022.350 | -12.700 |
| 36 | 300 | Slip On | pcd_mm | 1089.152 | 1168.400 | -79.248 |
| 36 | 300 | Slip On | stud_bolt_length_mm | 317.500 | 345.000 | -27.500 |
| 36 | 300 | Blind | bore_diameter_mm | 22743.160 | 895.400 | +21847.760 |
| 36 | 300 | Blind | stud_bolt_length_mm | 349.250 | 345.000 | +4.250 |
| 36 | 300 | Blind | outer_diameter_mm | 1170.000 | 1270.000 | -100.000 |
| 36 | 300 | Blind | thickness_mm | 101.600 | 109.600 | -8.000 |
| 36 | 300 | Blind | hub_diameter_mm | 965.000 | 991.000 | -26.000 |
| 36 | 300 | Blind | length_thru_hub_mm | 101.600 | 109.600 | -8.000 |
| 36 | 300 | Blind | rf_diameter_mm | 1010.000 | 1022.350 | -12.350 |
| 36 | 300 | Blind | pcd_mm | 1089.000 | 1168.400 | -79.400 |
| 36 | 300 | Blind | stud_bolt_length_mm | 320.000 | 345.000 | -25.000 |
| 36 | 300 | Blind | weight_kg | 871.000 | 1104.000 | -233.000 |
| 36 | 300 | Blind | outer_diameter_mm | 1171.448 | 1270.000 | -98.552 |
| 36 | 300 | Blind | thickness_mm | 101.600 | 109.600 | -8.000 |
| 36 | 300 | Blind | hub_diameter_mm | 965.200 | 991.000 | -25.800 |
| 36 | 300 | Blind | length_thru_hub_mm | 101.600 | 109.600 | -8.000 |
| 36 | 300 | Blind | bore_diameter_mm | 22741.890 | 895.400 | +21846.490 |
| 36 | 300 | Blind | rf_diameter_mm | 1009.650 | 1022.350 | -12.700 |
| 36 | 300 | Blind | pcd_mm | 1089.152 | 1168.400 | -79.248 |
| 36 | 300 | Blind | stud_bolt_length_mm | 317.500 | 345.000 | -27.500 |
| 36 | 300 | Blind | weight_kg | 871.000 | 1104.000 | -233.000 |
| 38 | 300 | Welding Neck | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 38 | 300 | Welding Neck | bore_diameter_mm | 24033.480 | 946.200 | +23087.280 |
| 38 | 300 | Welding Neck | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 38 | 300 | Welding Neck | outer_diameter_mm | 1220.000 | 1170.000 | +50.000 |
| 38 | 300 | Welding Neck | thickness_mm | 109.600 | 106.400 | +3.200 |
| 38 | 300 | Welding Neck | hub_diameter_mm | 1016.000 | 994.000 | +22.000 |
| 38 | 300 | Welding Neck | length_thru_hub_mm | 109.600 | 179.000 | -69.400 |
| 38 | 300 | Welding Neck | rf_diameter_mm | 1060.000 | 1029.000 | +31.000 |
| 38 | 300 | Welding Neck | pcd_mm | 1139.800 | 1092.200 | +47.600 |
| 38 | 300 | Welding Neck | stud_bolt_length_mm | 330.000 | 320.000 | +10.000 |
| 38 | 300 | Welding Neck | weight_kg | 415.000 | 315.000 | +100.000 |
| 38 | 300 | Welding Neck | outer_diameter_mm | 1222.248 | 1170.000 | +52.248 |
| 38 | 300 | Welding Neck | thickness_mm | 109.474 | 106.400 | +3.074 |
| 38 | 300 | Welding Neck | hub_diameter_mm | 1016.000 | 994.000 | +22.000 |
| 38 | 300 | Welding Neck | length_thru_hub_mm | 109.474 | 179.000 | -69.526 |
| 38 | 300 | Welding Neck | bore_diameter_mm | 24032.210 | 946.200 | +23086.010 |
| 38 | 300 | Welding Neck | rf_diameter_mm | 1060.450 | 1029.000 | +31.450 |
| 38 | 300 | Welding Neck | pcd_mm | 1139.952 | 1092.200 | +47.752 |
| 38 | 300 | Welding Neck | stud_bolt_length_mm | 330.200 | 320.000 | +10.200 |
| 38 | 300 | Welding Neck | weight_kg | 415.000 | 315.000 | +100.000 |
| 38 | 300 | Slip On | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 38 | 300 | Slip On | bore_diameter_mm | 24033.480 | 946.200 | +23087.280 |
| 38 | 300 | Slip On | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 38 | 300 | Slip On | outer_diameter_mm | 1220.000 | 1170.000 | +50.000 |
| 38 | 300 | Slip On | thickness_mm | 109.600 | 106.400 | +3.200 |
| 38 | 300 | Slip On | hub_diameter_mm | 1016.000 | 994.000 | +22.000 |
| 38 | 300 | Slip On | length_thru_hub_mm | 109.600 | 106.400 | +3.200 |
| 38 | 300 | Slip On | rf_diameter_mm | 1060.000 | 1029.000 | +31.000 |
| 38 | 300 | Slip On | pcd_mm | 1139.800 | 1092.200 | +47.600 |
| 38 | 300 | Slip On | stud_bolt_length_mm | 330.000 | 320.000 | +10.000 |
| 38 | 300 | Slip On | outer_diameter_mm | 1222.248 | 1170.000 | +52.248 |
| 38 | 300 | Slip On | thickness_mm | 109.474 | 106.400 | +3.074 |
| 38 | 300 | Slip On | hub_diameter_mm | 1016.000 | 994.000 | +22.000 |
| 38 | 300 | Slip On | length_thru_hub_mm | 109.474 | 106.400 | +3.074 |
| 38 | 300 | Slip On | bore_diameter_mm | 24032.210 | 946.200 | +23086.010 |
| 38 | 300 | Slip On | rf_diameter_mm | 1060.450 | 1029.000 | +31.450 |
| 38 | 300 | Slip On | pcd_mm | 1139.952 | 1092.200 | +47.752 |
| 38 | 300 | Slip On | stud_bolt_length_mm | 330.200 | 320.000 | +10.200 |
| 38 | 300 | Blind | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 38 | 300 | Blind | bore_diameter_mm | 24033.480 | 946.200 | +23087.280 |
| 38 | 300 | Blind | stud_bolt_length_mm | 323.850 | 320.000 | +3.850 |
| 38 | 300 | Blind | outer_diameter_mm | 1220.000 | 1170.000 | +50.000 |
| 38 | 300 | Blind | thickness_mm | 109.600 | 106.400 | +3.200 |
| 38 | 300 | Blind | hub_diameter_mm | 1016.000 | 994.000 | +22.000 |
| 38 | 300 | Blind | length_thru_hub_mm | 109.600 | 106.400 | +3.200 |
| 38 | 300 | Blind | rf_diameter_mm | 1060.000 | 1029.000 | +31.000 |
| 38 | 300 | Blind | pcd_mm | 1139.800 | 1092.200 | +47.600 |
| 38 | 300 | Blind | stud_bolt_length_mm | 330.000 | 320.000 | +10.000 |
| 38 | 300 | Blind | weight_kg | 1023.000 | 907.000 | +116.000 |
| 38 | 300 | Blind | outer_diameter_mm | 1222.248 | 1170.000 | +52.248 |
| 38 | 300 | Blind | thickness_mm | 109.474 | 106.400 | +3.074 |
| 38 | 300 | Blind | hub_diameter_mm | 1016.000 | 994.000 | +22.000 |
| 38 | 300 | Blind | length_thru_hub_mm | 109.474 | 106.400 | +3.074 |
| 38 | 300 | Blind | bore_diameter_mm | 24032.210 | 946.200 | +23086.010 |
| 38 | 300 | Blind | rf_diameter_mm | 1060.450 | 1029.000 | +31.450 |
| 38 | 300 | Blind | pcd_mm | 1139.952 | 1092.200 | +47.752 |
| 38 | 300 | Blind | stud_bolt_length_mm | 330.200 | 320.000 | +10.200 |
| 38 | 300 | Blind | weight_kg | 1023.000 | 907.000 | +116.000 |
| 40 | 300 | Welding Neck | outer_diameter_mm | 1238.250 | 1240.000 | -1.750 |
| 40 | 300 | Welding Neck | bore_diameter_mm | 25323.800 | 997.000 | +24326.800 |
| 40 | 300 | Welding Neck | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 40 | 300 | Welding Neck | outer_diameter_mm | 1275.000 | 1240.000 | +35.000 |
| 40 | 300 | Welding Neck | thickness_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Welding Neck | hub_diameter_mm | 1067.000 | 1048.000 | +19.000 |
| 40 | 300 | Welding Neck | length_thru_hub_mm | 114.300 | 192.000 | -77.700 |
| 40 | 300 | Welding Neck | rf_diameter_mm | 1114.000 | 1086.000 | +28.000 |
| 40 | 300 | Welding Neck | pcd_mm | 1190.600 | 1155.700 | +34.900 |
| 40 | 300 | Welding Neck | stud_bolt_length_mm | 345.000 | 335.000 | +10.000 |
| 40 | 300 | Welding Neck | weight_kg | 449.000 | 381.000 | +68.000 |
| 40 | 300 | Welding Neck | outer_diameter_mm | 1273.048 | 1240.000 | +33.048 |
| 40 | 300 | Welding Neck | thickness_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Welding Neck | hub_diameter_mm | 1066.800 | 1048.000 | +18.800 |
| 40 | 300 | Welding Neck | length_thru_hub_mm | 114.300 | 192.000 | -77.700 |
| 40 | 300 | Welding Neck | bore_diameter_mm | 25322.530 | 997.000 | +24325.530 |
| 40 | 300 | Welding Neck | rf_diameter_mm | 1114.552 | 1086.000 | +28.552 |
| 40 | 300 | Welding Neck | pcd_mm | 1190.752 | 1155.700 | +35.052 |
| 40 | 300 | Welding Neck | stud_bolt_length_mm | 342.900 | 335.000 | +7.900 |
| 40 | 300 | Welding Neck | weight_kg | 449.000 | 381.000 | +68.000 |
| 40 | 300 | Slip On | outer_diameter_mm | 1238.250 | 1240.000 | -1.750 |
| 40 | 300 | Slip On | bore_diameter_mm | 25323.800 | 997.000 | +24326.800 |
| 40 | 300 | Slip On | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 40 | 300 | Slip On | outer_diameter_mm | 1275.000 | 1240.000 | +35.000 |
| 40 | 300 | Slip On | thickness_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Slip On | hub_diameter_mm | 1067.000 | 1048.000 | +19.000 |
| 40 | 300 | Slip On | length_thru_hub_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Slip On | rf_diameter_mm | 1114.000 | 1086.000 | +28.000 |
| 40 | 300 | Slip On | pcd_mm | 1190.600 | 1155.700 | +34.900 |
| 40 | 300 | Slip On | stud_bolt_length_mm | 345.000 | 335.000 | +10.000 |
| 40 | 300 | Slip On | outer_diameter_mm | 1273.048 | 1240.000 | +33.048 |
| 40 | 300 | Slip On | thickness_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Slip On | hub_diameter_mm | 1066.800 | 1048.000 | +18.800 |
| 40 | 300 | Slip On | length_thru_hub_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Slip On | bore_diameter_mm | 25322.530 | 997.000 | +24325.530 |
| 40 | 300 | Slip On | rf_diameter_mm | 1114.552 | 1086.000 | +28.552 |
| 40 | 300 | Slip On | pcd_mm | 1190.752 | 1155.700 | +35.052 |
| 40 | 300 | Slip On | stud_bolt_length_mm | 342.900 | 335.000 | +7.900 |
| 40 | 300 | Blind | outer_diameter_mm | 1238.250 | 1240.000 | -1.750 |
| 40 | 300 | Blind | bore_diameter_mm | 25323.800 | 997.000 | +24326.800 |
| 40 | 300 | Blind | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 40 | 300 | Blind | outer_diameter_mm | 1275.000 | 1240.000 | +35.000 |
| 40 | 300 | Blind | thickness_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Blind | hub_diameter_mm | 1067.000 | 1048.000 | +19.000 |
| 40 | 300 | Blind | length_thru_hub_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Blind | rf_diameter_mm | 1114.000 | 1086.000 | +28.000 |
| 40 | 300 | Blind | pcd_mm | 1190.600 | 1155.700 | +34.900 |
| 40 | 300 | Blind | stud_bolt_length_mm | 345.000 | 335.000 | +10.000 |
| 40 | 300 | Blind | weight_kg | 1156.000 | 1079.000 | +77.000 |
| 40 | 300 | Blind | outer_diameter_mm | 1273.048 | 1240.000 | +33.048 |
| 40 | 300 | Blind | thickness_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Blind | hub_diameter_mm | 1066.800 | 1048.000 | +18.800 |
| 40 | 300 | Blind | length_thru_hub_mm | 114.300 | 112.800 | +1.500 |
| 40 | 300 | Blind | bore_diameter_mm | 25322.530 | 997.000 | +24325.530 |
| 40 | 300 | Blind | rf_diameter_mm | 1114.552 | 1086.000 | +28.552 |
| 40 | 300 | Blind | pcd_mm | 1190.752 | 1155.700 | +35.052 |
| 40 | 300 | Blind | stud_bolt_length_mm | 342.900 | 335.000 | +7.900 |
| 40 | 300 | Blind | weight_kg | 1156.000 | 1079.000 | +77.000 |
| 42 | 300 | Welding Neck | bore_diameter_mm | 26614.120 | 1047.800 | +25566.320 |
| 42 | 300 | Welding Neck | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 42 | 300 | Welding Neck | outer_diameter_mm | 1335.000 | 1290.000 | +45.000 |
| 42 | 300 | Welding Neck | hub_diameter_mm | 1118.000 | 1099.000 | +19.000 |
| 42 | 300 | Welding Neck | length_thru_hub_mm | 117.500 | 198.000 | -80.500 |
| 42 | 300 | Welding Neck | rf_diameter_mm | 1168.000 | 1137.000 | +31.000 |
| 42 | 300 | Welding Neck | pcd_mm | 1244.600 | 1206.500 | +38.100 |
| 42 | 300 | Welding Neck | stud_bolt_length_mm | 355.000 | 350.000 | +5.000 |
| 42 | 300 | Welding Neck | weight_kg | 514.000 | 430.000 | +84.000 |
| 42 | 300 | Welding Neck | outer_diameter_mm | 1333.500 | 1290.000 | +43.500 |
| 42 | 300 | Welding Neck | hub_diameter_mm | 1117.600 | 1099.000 | +18.600 |
| 42 | 300 | Welding Neck | length_thru_hub_mm | 117.602 | 198.000 | -80.398 |
| 42 | 300 | Welding Neck | bore_diameter_mm | 26612.850 | 1047.800 | +25565.050 |
| 42 | 300 | Welding Neck | rf_diameter_mm | 1168.400 | 1137.000 | +31.400 |
| 42 | 300 | Welding Neck | pcd_mm | 1244.600 | 1206.500 | +38.100 |
| 42 | 300 | Welding Neck | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 42 | 300 | Welding Neck | weight_kg | 514.000 | 430.000 | +84.000 |
| 42 | 300 | Slip On | length_thru_hub_mm | 6532.245 | 257.175 | +6275.070 |
| 42 | 300 | Slip On | bore_diameter_mm | 26614.120 | 1047.800 | +25566.320 |
| 42 | 300 | Slip On | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 42 | 300 | Slip On | outer_diameter_mm | 1335.000 | 1290.000 | +45.000 |
| 42 | 300 | Slip On | hub_diameter_mm | 1118.000 | 1099.000 | +19.000 |
| 42 | 300 | Slip On | length_thru_hub_mm | 117.500 | 257.175 | -139.675 |
| 42 | 300 | Slip On | rf_diameter_mm | 1168.000 | 1137.000 | +31.000 |
| 42 | 300 | Slip On | pcd_mm | 1244.600 | 1206.500 | +38.100 |
| 42 | 300 | Slip On | stud_bolt_length_mm | 355.000 | 350.000 | +5.000 |
| 42 | 300 | Slip On | outer_diameter_mm | 1333.500 | 1290.000 | +43.500 |
| 42 | 300 | Slip On | hub_diameter_mm | 1117.600 | 1099.000 | +18.600 |
| 42 | 300 | Slip On | length_thru_hub_mm | 117.602 | 257.175 | -139.573 |
| 42 | 300 | Slip On | bore_diameter_mm | 26612.850 | 1047.800 | +25565.050 |
| 42 | 300 | Slip On | rf_diameter_mm | 1168.400 | 1137.000 | +31.400 |
| 42 | 300 | Slip On | pcd_mm | 1244.600 | 1206.500 | +38.100 |
| 42 | 300 | Slip On | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 42 | 300 | Blind | bore_diameter_mm | 26614.120 | 1047.800 | +25566.320 |
| 42 | 300 | Blind | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 42 | 300 | Blind | outer_diameter_mm | 1335.000 | 1290.000 | +45.000 |
| 42 | 300 | Blind | hub_diameter_mm | 1118.000 | 1099.000 | +19.000 |
| 42 | 300 | Blind | rf_diameter_mm | 1168.000 | 1137.000 | +31.000 |
| 42 | 300 | Blind | pcd_mm | 1244.600 | 1206.500 | +38.100 |
| 42 | 300 | Blind | stud_bolt_length_mm | 355.000 | 350.000 | +5.000 |
| 42 | 300 | Blind | weight_kg | 1304.000 | 1219.000 | +85.000 |
| 42 | 300 | Blind | outer_diameter_mm | 1333.500 | 1290.000 | +43.500 |
| 42 | 300 | Blind | hub_diameter_mm | 1117.600 | 1099.000 | +18.600 |
| 42 | 300 | Blind | bore_diameter_mm | 26612.850 | 1047.800 | +25565.050 |
| 42 | 300 | Blind | rf_diameter_mm | 1168.400 | 1137.000 | +31.400 |
| 42 | 300 | Blind | pcd_mm | 1244.600 | 1206.500 | +38.100 |
| 42 | 300 | Blind | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 42 | 300 | Blind | weight_kg | 1304.000 | 1219.000 | +85.000 |
| 44 | 300 | Welding Neck | outer_diameter_mm | 1352.550 | 1355.000 | -2.450 |
| 44 | 300 | Welding Neck | bore_diameter_mm | 27904.440 | 1098.600 | +26805.840 |
| 44 | 300 | Welding Neck | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 44 | 300 | Welding Neck | outer_diameter_mm | 1385.000 | 1355.000 | +30.000 |
| 44 | 300 | Welding Neck | thickness_mm | 125.500 | 122.300 | +3.200 |
| 44 | 300 | Welding Neck | hub_diameter_mm | 1173.000 | 1149.000 | +24.000 |
| 44 | 300 | Welding Neck | length_thru_hub_mm | 125.500 | 205.000 | -79.500 |
| 44 | 300 | Welding Neck | rf_diameter_mm | 1219.000 | 1194.000 | +25.000 |
| 44 | 300 | Welding Neck | pcd_mm | 1295.400 | 1263.600 | +31.800 |
| 44 | 300 | Welding Neck | stud_bolt_length_mm | 370.000 | 360.000 | +10.000 |
| 44 | 300 | Welding Neck | weight_kg | 560.000 | 478.000 | +82.000 |
| 44 | 300 | Welding Neck | outer_diameter_mm | 1384.300 | 1355.000 | +29.300 |
| 44 | 300 | Welding Neck | thickness_mm | 125.476 | 122.300 | +3.176 |
| 44 | 300 | Welding Neck | hub_diameter_mm | 1173.226 | 1149.000 | +24.226 |
| 44 | 300 | Welding Neck | length_thru_hub_mm | 125.476 | 205.000 | -79.524 |
| 44 | 300 | Welding Neck | bore_diameter_mm | 27903.170 | 1098.600 | +26804.570 |
| 44 | 300 | Welding Neck | rf_diameter_mm | 1219.200 | 1194.000 | +25.200 |
| 44 | 300 | Welding Neck | pcd_mm | 1295.400 | 1263.600 | +31.800 |
| 44 | 300 | Welding Neck | stud_bolt_length_mm | 368.300 | 360.000 | +8.300 |
| 44 | 300 | Welding Neck | weight_kg | 560.000 | 478.000 | +82.000 |
| 44 | 300 | Slip On | outer_diameter_mm | 1352.550 | 1355.000 | -2.450 |
| 44 | 300 | Slip On | bore_diameter_mm | 27904.440 | 1098.600 | +26805.840 |
| 44 | 300 | Slip On | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 44 | 300 | Slip On | outer_diameter_mm | 1385.000 | 1355.000 | +30.000 |
| 44 | 300 | Slip On | thickness_mm | 125.500 | 122.300 | +3.200 |
| 44 | 300 | Slip On | hub_diameter_mm | 1173.000 | 1149.000 | +24.000 |
| 44 | 300 | Slip On | length_thru_hub_mm | 125.500 | 122.300 | +3.200 |
| 44 | 300 | Slip On | rf_diameter_mm | 1219.000 | 1194.000 | +25.000 |
| 44 | 300 | Slip On | pcd_mm | 1295.400 | 1263.600 | +31.800 |
| 44 | 300 | Slip On | stud_bolt_length_mm | 370.000 | 360.000 | +10.000 |
| 44 | 300 | Slip On | outer_diameter_mm | 1384.300 | 1355.000 | +29.300 |
| 44 | 300 | Slip On | thickness_mm | 125.476 | 122.300 | +3.176 |
| 44 | 300 | Slip On | hub_diameter_mm | 1173.226 | 1149.000 | +24.226 |
| 44 | 300 | Slip On | length_thru_hub_mm | 125.476 | 122.300 | +3.176 |
| 44 | 300 | Slip On | bore_diameter_mm | 27903.170 | 1098.600 | +26804.570 |
| 44 | 300 | Slip On | rf_diameter_mm | 1219.200 | 1194.000 | +25.200 |
| 44 | 300 | Slip On | pcd_mm | 1295.400 | 1263.600 | +31.800 |
| 44 | 300 | Slip On | stud_bolt_length_mm | 368.300 | 360.000 | +8.300 |
| 44 | 300 | Blind | outer_diameter_mm | 1352.550 | 1355.000 | -2.450 |
| 44 | 300 | Blind | bore_diameter_mm | 27904.440 | 1098.600 | +26805.840 |
| 44 | 300 | Blind | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 44 | 300 | Blind | outer_diameter_mm | 1385.000 | 1355.000 | +30.000 |
| 44 | 300 | Blind | thickness_mm | 125.500 | 122.300 | +3.200 |
| 44 | 300 | Blind | hub_diameter_mm | 1173.000 | 1149.000 | +24.000 |
| 44 | 300 | Blind | length_thru_hub_mm | 125.500 | 122.300 | +3.200 |
| 44 | 300 | Blind | rf_diameter_mm | 1219.000 | 1194.000 | +25.000 |
| 44 | 300 | Blind | pcd_mm | 1295.400 | 1263.600 | +31.800 |
| 44 | 300 | Blind | stud_bolt_length_mm | 370.000 | 360.000 | +10.000 |
| 44 | 300 | Blind | weight_kg | 1498.000 | 1396.000 | +102.000 |
| 44 | 300 | Blind | outer_diameter_mm | 1384.300 | 1355.000 | +29.300 |
| 44 | 300 | Blind | thickness_mm | 125.476 | 122.300 | +3.176 |
| 44 | 300 | Blind | hub_diameter_mm | 1173.226 | 1149.000 | +24.226 |
| 44 | 300 | Blind | length_thru_hub_mm | 125.476 | 122.300 | +3.176 |
| 44 | 300 | Blind | bore_diameter_mm | 27903.170 | 1098.600 | +26804.570 |
| 44 | 300 | Blind | rf_diameter_mm | 1219.200 | 1194.000 | +25.200 |
| 44 | 300 | Blind | pcd_mm | 1295.400 | 1263.600 | +31.800 |
| 44 | 300 | Blind | stud_bolt_length_mm | 368.300 | 360.000 | +8.300 |
| 44 | 300 | Blind | weight_kg | 1498.000 | 1396.000 | +102.000 |
| 46 | 300 | Welding Neck | outer_diameter_mm | 1416.050 | 1415.000 | +1.050 |
| 46 | 300 | Welding Neck | bore_diameter_mm | 29194.760 | 1149.400 | +28045.360 |
| 46 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 46 | 300 | Welding Neck | outer_diameter_mm | 1460.000 | 1415.000 | +45.000 |
| 46 | 300 | Welding Neck | hub_diameter_mm | 1229.000 | 1203.000 | +26.000 |
| 46 | 300 | Welding Neck | length_thru_hub_mm | 127.000 | 214.000 | -87.000 |
| 46 | 300 | Welding Neck | rf_diameter_mm | 1270.000 | 1245.000 | +25.000 |
| 46 | 300 | Welding Neck | pcd_mm | 1365.200 | 1320.800 | +44.400 |
| 46 | 300 | Welding Neck | weight_kg | 666.000 | 560.000 | +106.000 |
| 46 | 300 | Welding Neck | outer_diameter_mm | 1460.500 | 1415.000 | +45.500 |
| 46 | 300 | Welding Neck | hub_diameter_mm | 1228.852 | 1203.000 | +25.852 |
| 46 | 300 | Welding Neck | length_thru_hub_mm | 127.000 | 214.000 | -87.000 |
| 46 | 300 | Welding Neck | bore_diameter_mm | 29193.490 | 1149.400 | +28044.090 |
| 46 | 300 | Welding Neck | rf_diameter_mm | 1270.000 | 1245.000 | +25.000 |
| 46 | 300 | Welding Neck | pcd_mm | 1365.250 | 1320.800 | +44.450 |
| 46 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 46 | 300 | Welding Neck | weight_kg | 666.000 | 560.000 | +106.000 |
| 46 | 300 | Slip On | outer_diameter_mm | 1416.050 | 1415.000 | +1.050 |
| 46 | 300 | Slip On | bore_diameter_mm | 29194.760 | 1149.400 | +28045.360 |
| 46 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 46 | 300 | Slip On | outer_diameter_mm | 1460.000 | 1415.000 | +45.000 |
| 46 | 300 | Slip On | hub_diameter_mm | 1229.000 | 1203.000 | +26.000 |
| 46 | 300 | Slip On | rf_diameter_mm | 1270.000 | 1245.000 | +25.000 |
| 46 | 300 | Slip On | pcd_mm | 1365.200 | 1320.800 | +44.400 |
| 46 | 300 | Slip On | outer_diameter_mm | 1460.500 | 1415.000 | +45.500 |
| 46 | 300 | Slip On | hub_diameter_mm | 1228.852 | 1203.000 | +25.852 |
| 46 | 300 | Slip On | bore_diameter_mm | 29193.490 | 1149.400 | +28044.090 |
| 46 | 300 | Slip On | rf_diameter_mm | 1270.000 | 1245.000 | +25.000 |
| 46 | 300 | Slip On | pcd_mm | 1365.250 | 1320.800 | +44.450 |
| 46 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 46 | 300 | Blind | outer_diameter_mm | 1416.050 | 1415.000 | +1.050 |
| 46 | 300 | Blind | bore_diameter_mm | 29194.760 | 1149.400 | +28045.360 |
| 46 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 46 | 300 | Blind | outer_diameter_mm | 1460.000 | 1415.000 | +45.000 |
| 46 | 300 | Blind | thickness_mm | 128.600 | 127.000 | +1.600 |
| 46 | 300 | Blind | hub_diameter_mm | 1229.000 | 1203.000 | +26.000 |
| 46 | 300 | Blind | length_thru_hub_mm | 128.600 | 127.000 | +1.600 |
| 46 | 300 | Blind | rf_diameter_mm | 1270.000 | 1245.000 | +25.000 |
| 46 | 300 | Blind | pcd_mm | 1365.200 | 1320.800 | +44.400 |
| 46 | 300 | Blind | weight_kg | 1708.000 | 1587.000 | +121.000 |
| 46 | 300 | Blind | outer_diameter_mm | 1460.500 | 1415.000 | +45.500 |
| 46 | 300 | Blind | thickness_mm | 128.524 | 127.000 | +1.524 |
| 46 | 300 | Blind | hub_diameter_mm | 1228.852 | 1203.000 | +25.852 |
| 46 | 300 | Blind | length_thru_hub_mm | 128.524 | 127.000 | +1.524 |
| 46 | 300 | Blind | bore_diameter_mm | 29193.490 | 1149.400 | +28044.090 |
| 46 | 300 | Blind | rf_diameter_mm | 1270.000 | 1245.000 | +25.000 |
| 46 | 300 | Blind | pcd_mm | 1365.250 | 1320.800 | +44.450 |
| 46 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 46 | 300 | Blind | weight_kg | 1708.000 | 1587.000 | +121.000 |
| 48 | 300 | Welding Neck | outer_diameter_mm | 1466.850 | 1465.000 | +1.850 |
| 48 | 300 | Welding Neck | bore_diameter_mm | 30485.080 | 1200.200 | +29284.880 |
| 48 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 48 | 300 | Welding Neck | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 48 | 300 | Welding Neck | outer_diameter_mm | 1510.000 | 1465.000 | +45.000 |
| 48 | 300 | Welding Neck | thickness_mm | 127.000 | 131.800 | -4.800 |
| 48 | 300 | Welding Neck | hub_diameter_mm | 1278.000 | 1254.000 | +24.000 |
| 48 | 300 | Welding Neck | length_thru_hub_mm | 127.000 | 222.000 | -95.000 |
| 48 | 300 | Welding Neck | rf_diameter_mm | 1327.000 | 1302.000 | +25.000 |
| 48 | 300 | Welding Neck | pcd_mm | 1416.000 | 1371.600 | +44.400 |
| 48 | 300 | Welding Neck | weight_kg | 714.000 | 625.000 | +89.000 |
| 48 | 300 | Welding Neck | outer_diameter_mm | 1511.300 | 1465.000 | +46.300 |
| 48 | 300 | Welding Neck | thickness_mm | 127.000 | 131.800 | -4.800 |
| 48 | 300 | Welding Neck | hub_diameter_mm | 1277.874 | 1254.000 | +23.874 |
| 48 | 300 | Welding Neck | length_thru_hub_mm | 127.000 | 222.000 | -95.000 |
| 48 | 300 | Welding Neck | bore_diameter_mm | 30483.810 | 1200.200 | +29283.610 |
| 48 | 300 | Welding Neck | rf_diameter_mm | 1327.150 | 1302.000 | +25.150 |
| 48 | 300 | Welding Neck | pcd_mm | 1416.050 | 1371.600 | +44.450 |
| 48 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 48 | 300 | Welding Neck | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 48 | 300 | Welding Neck | weight_kg | 714.000 | 625.000 | +89.000 |
| 48 | 300 | Slip On | outer_diameter_mm | 1466.850 | 1465.000 | +1.850 |
| 48 | 300 | Slip On | bore_diameter_mm | 30485.080 | 1200.200 | +29284.880 |
| 48 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 48 | 300 | Slip On | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 48 | 300 | Slip On | outer_diameter_mm | 1510.000 | 1465.000 | +45.000 |
| 48 | 300 | Slip On | thickness_mm | 127.000 | 131.800 | -4.800 |
| 48 | 300 | Slip On | hub_diameter_mm | 1278.000 | 1254.000 | +24.000 |
| 48 | 300 | Slip On | length_thru_hub_mm | 127.000 | 131.800 | -4.800 |
| 48 | 300 | Slip On | rf_diameter_mm | 1327.000 | 1302.000 | +25.000 |
| 48 | 300 | Slip On | pcd_mm | 1416.000 | 1371.600 | +44.400 |
| 48 | 300 | Slip On | outer_diameter_mm | 1511.300 | 1465.000 | +46.300 |
| 48 | 300 | Slip On | thickness_mm | 127.000 | 131.800 | -4.800 |
| 48 | 300 | Slip On | hub_diameter_mm | 1277.874 | 1254.000 | +23.874 |
| 48 | 300 | Slip On | length_thru_hub_mm | 127.000 | 131.800 | -4.800 |
| 48 | 300 | Slip On | bore_diameter_mm | 30483.810 | 1200.200 | +29283.610 |
| 48 | 300 | Slip On | rf_diameter_mm | 1327.150 | 1302.000 | +25.150 |
| 48 | 300 | Slip On | pcd_mm | 1416.050 | 1371.600 | +44.450 |
| 48 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 48 | 300 | Slip On | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 48 | 300 | Blind | outer_diameter_mm | 1466.850 | 1465.000 | +1.850 |
| 48 | 300 | Blind | bore_diameter_mm | 30485.080 | 1200.200 | +29284.880 |
| 48 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 48 | 300 | Blind | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 48 | 300 | Blind | outer_diameter_mm | 1510.000 | 1465.000 | +45.000 |
| 48 | 300 | Blind | thickness_mm | 133.400 | 131.800 | +1.600 |
| 48 | 300 | Blind | hub_diameter_mm | 1278.000 | 1254.000 | +24.000 |
| 48 | 300 | Blind | length_thru_hub_mm | 133.400 | 131.800 | +1.600 |
| 48 | 300 | Blind | rf_diameter_mm | 1327.000 | 1302.000 | +25.000 |
| 48 | 300 | Blind | pcd_mm | 1416.000 | 1371.600 | +44.400 |
| 48 | 300 | Blind | weight_kg | 1897.000 | 1767.000 | +130.000 |
| 48 | 300 | Blind | outer_diameter_mm | 1511.300 | 1465.000 | +46.300 |
| 48 | 300 | Blind | thickness_mm | 133.350 | 131.800 | +1.550 |
| 48 | 300 | Blind | hub_diameter_mm | 1277.874 | 1254.000 | +23.874 |
| 48 | 300 | Blind | length_thru_hub_mm | 133.350 | 131.800 | +1.550 |
| 48 | 300 | Blind | bore_diameter_mm | 30483.810 | 1200.200 | +29283.610 |
| 48 | 300 | Blind | rf_diameter_mm | 1327.150 | 1302.000 | +25.150 |
| 48 | 300 | Blind | pcd_mm | 1416.050 | 1371.600 | +44.450 |
| 48 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 48 | 300 | Blind | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 48 | 300 | Blind | weight_kg | 1897.000 | 1767.000 | +130.000 |
| 50 | 300 | Welding Neck | bore_diameter_mm | 31775.400 | 1251.000 | +30524.400 |
| 50 | 300 | Welding Neck | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 50 | 300 | Welding Neck | outer_diameter_mm | 1560.000 | 1530.000 | +30.000 |
| 50 | 300 | Welding Neck | thickness_mm | 136.600 | 138.200 | -1.600 |
| 50 | 300 | Welding Neck | hub_diameter_mm | 1330.000 | 1305.000 | +25.000 |
| 50 | 300 | Welding Neck | length_thru_hub_mm | 136.600 | 230.000 | -93.400 |
| 50 | 300 | Welding Neck | rf_diameter_mm | 1378.000 | 1359.000 | +19.000 |
| 50 | 300 | Welding Neck | pcd_mm | 1466.800 | 1428.800 | +38.000 |
| 50 | 300 | Welding Neck | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 50 | 300 | Welding Neck | stud_bolt_length_mm | 400.000 | 405.000 | -5.000 |
| 50 | 300 | Welding Neck | weight_kg | 775.000 | 693.000 | +82.000 |
| 50 | 300 | Welding Neck | outer_diameter_mm | 1562.100 | 1530.000 | +32.100 |
| 50 | 300 | Welding Neck | thickness_mm | 136.652 | 138.200 | -1.548 |
| 50 | 300 | Welding Neck | hub_diameter_mm | 1330.452 | 1305.000 | +25.452 |
| 50 | 300 | Welding Neck | length_thru_hub_mm | 136.652 | 230.000 | -93.348 |
| 50 | 300 | Welding Neck | bore_diameter_mm | 31774.130 | 1251.000 | +30523.130 |
| 50 | 300 | Welding Neck | rf_diameter_mm | 1377.950 | 1359.000 | +18.950 |
| 50 | 300 | Welding Neck | pcd_mm | 1466.850 | 1428.800 | +38.050 |
| 50 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 50 | 300 | Welding Neck | stud_bolt_length_mm | 400.050 | 405.000 | -4.950 |
| 50 | 300 | Welding Neck | weight_kg | 775.000 | 693.000 | +82.000 |
| 50 | 300 | Slip On | bore_diameter_mm | 31775.400 | 1251.000 | +30524.400 |
| 50 | 300 | Slip On | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 50 | 300 | Slip On | outer_diameter_mm | 1560.000 | 1530.000 | +30.000 |
| 50 | 300 | Slip On | thickness_mm | 136.600 | 138.200 | -1.600 |
| 50 | 300 | Slip On | hub_diameter_mm | 1330.000 | 1305.000 | +25.000 |
| 50 | 300 | Slip On | length_thru_hub_mm | 136.600 | 138.200 | -1.600 |
| 50 | 300 | Slip On | rf_diameter_mm | 1378.000 | 1359.000 | +19.000 |
| 50 | 300 | Slip On | pcd_mm | 1466.800 | 1428.800 | +38.000 |
| 50 | 300 | Slip On | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 50 | 300 | Slip On | stud_bolt_length_mm | 400.000 | 405.000 | -5.000 |
| 50 | 300 | Slip On | outer_diameter_mm | 1562.100 | 1530.000 | +32.100 |
| 50 | 300 | Slip On | thickness_mm | 136.652 | 138.200 | -1.548 |
| 50 | 300 | Slip On | hub_diameter_mm | 1330.452 | 1305.000 | +25.452 |
| 50 | 300 | Slip On | length_thru_hub_mm | 136.652 | 138.200 | -1.548 |
| 50 | 300 | Slip On | bore_diameter_mm | 31774.130 | 1251.000 | +30523.130 |
| 50 | 300 | Slip On | rf_diameter_mm | 1377.950 | 1359.000 | +18.950 |
| 50 | 300 | Slip On | pcd_mm | 1466.850 | 1428.800 | +38.050 |
| 50 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 50 | 300 | Slip On | stud_bolt_length_mm | 400.050 | 405.000 | -4.950 |
| 50 | 300 | Blind | bore_diameter_mm | 31775.400 | 1251.000 | +30524.400 |
| 50 | 300 | Blind | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 50 | 300 | Blind | outer_diameter_mm | 1560.000 | 1530.000 | +30.000 |
| 50 | 300 | Blind | hub_diameter_mm | 1330.000 | 1305.000 | +25.000 |
| 50 | 300 | Blind | rf_diameter_mm | 1378.000 | 1359.000 | +19.000 |
| 50 | 300 | Blind | pcd_mm | 1466.800 | 1428.800 | +38.000 |
| 50 | 300 | Blind | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 50 | 300 | Blind | stud_bolt_length_mm | 400.000 | 405.000 | -5.000 |
| 50 | 300 | Blind | weight_kg | 2099.000 | 2014.000 | +85.000 |
| 50 | 300 | Blind | outer_diameter_mm | 1562.100 | 1530.000 | +32.100 |
| 50 | 300 | Blind | hub_diameter_mm | 1330.452 | 1305.000 | +25.452 |
| 50 | 300 | Blind | bore_diameter_mm | 31774.130 | 1251.000 | +30523.130 |
| 50 | 300 | Blind | rf_diameter_mm | 1377.950 | 1359.000 | +18.950 |
| 50 | 300 | Blind | pcd_mm | 1466.850 | 1428.800 | +38.050 |
| 50 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 50 | 300 | Blind | stud_bolt_length_mm | 400.050 | 405.000 | -4.950 |
| 50 | 300 | Blind | weight_kg | 2099.000 | 2014.000 | +85.000 |
| 52 | 300 | Welding Neck | outer_diameter_mm | 1581.150 | 1580.000 | +1.150 |
| 52 | 300 | Welding Neck | bore_diameter_mm | 33065.720 | 1301.800 | +31763.920 |
| 52 | 300 | Welding Neck | stud_bolt_length_mm | 425.450 | 420.000 | +5.450 |
| 52 | 300 | Welding Neck | outer_diameter_mm | 1615.000 | 1580.000 | +35.000 |
| 52 | 300 | Welding Neck | thickness_mm | 141.300 | 142.900 | -1.600 |
| 52 | 300 | Welding Neck | hub_diameter_mm | 1383.000 | 1356.000 | +27.000 |
| 52 | 300 | Welding Neck | length_thru_hub_mm | 141.300 | 237.000 | -95.700 |
| 52 | 300 | Welding Neck | rf_diameter_mm | 1429.000 | 1410.000 | +19.000 |
| 52 | 300 | Welding Neck | pcd_mm | 1517.600 | 1479.600 | +38.000 |
| 52 | 300 | Welding Neck | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 52 | 300 | Welding Neck | stud_bolt_length_mm | 405.000 | 420.000 | -15.000 |
| 52 | 300 | Welding Neck | weight_kg | 834.000 | 752.000 | +82.000 |
| 52 | 300 | Welding Neck | outer_diameter_mm | 1612.900 | 1580.000 | +32.900 |
| 52 | 300 | Welding Neck | thickness_mm | 141.224 | 142.900 | -1.676 |
| 52 | 300 | Welding Neck | hub_diameter_mm | 1382.776 | 1356.000 | +26.776 |
| 52 | 300 | Welding Neck | length_thru_hub_mm | 141.224 | 237.000 | -95.776 |
| 52 | 300 | Welding Neck | bore_diameter_mm | 33064.450 | 1301.800 | +31762.650 |
| 52 | 300 | Welding Neck | rf_diameter_mm | 1428.750 | 1410.000 | +18.750 |
| 52 | 300 | Welding Neck | pcd_mm | 1517.650 | 1479.600 | +38.050 |
| 52 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 52 | 300 | Welding Neck | stud_bolt_length_mm | 406.400 | 420.000 | -13.600 |
| 52 | 300 | Welding Neck | weight_kg | 834.000 | 752.000 | +82.000 |
| 52 | 300 | Slip On | outer_diameter_mm | 1581.150 | 1580.000 | +1.150 |
| 52 | 300 | Slip On | bore_diameter_mm | 33065.720 | 1301.800 | +31763.920 |
| 52 | 300 | Slip On | stud_bolt_length_mm | 425.450 | 420.000 | +5.450 |
| 52 | 300 | Slip On | outer_diameter_mm | 1615.000 | 1580.000 | +35.000 |
| 52 | 300 | Slip On | thickness_mm | 141.300 | 142.900 | -1.600 |
| 52 | 300 | Slip On | hub_diameter_mm | 1383.000 | 1356.000 | +27.000 |
| 52 | 300 | Slip On | length_thru_hub_mm | 141.300 | 142.900 | -1.600 |
| 52 | 300 | Slip On | rf_diameter_mm | 1429.000 | 1410.000 | +19.000 |
| 52 | 300 | Slip On | pcd_mm | 1517.600 | 1479.600 | +38.000 |
| 52 | 300 | Slip On | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 52 | 300 | Slip On | stud_bolt_length_mm | 405.000 | 420.000 | -15.000 |
| 52 | 300 | Slip On | outer_diameter_mm | 1612.900 | 1580.000 | +32.900 |
| 52 | 300 | Slip On | thickness_mm | 141.224 | 142.900 | -1.676 |
| 52 | 300 | Slip On | hub_diameter_mm | 1382.776 | 1356.000 | +26.776 |
| 52 | 300 | Slip On | length_thru_hub_mm | 141.224 | 142.900 | -1.676 |
| 52 | 300 | Slip On | bore_diameter_mm | 33064.450 | 1301.800 | +31762.650 |
| 52 | 300 | Slip On | rf_diameter_mm | 1428.750 | 1410.000 | +18.750 |
| 52 | 300 | Slip On | pcd_mm | 1517.650 | 1479.600 | +38.050 |
| 52 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 52 | 300 | Slip On | stud_bolt_length_mm | 406.400 | 420.000 | -13.600 |
| 52 | 300 | Blind | outer_diameter_mm | 1581.150 | 1580.000 | +1.150 |
| 52 | 300 | Blind | bore_diameter_mm | 33065.720 | 1301.800 | +31763.920 |
| 52 | 300 | Blind | stud_bolt_length_mm | 425.450 | 420.000 | +5.450 |
| 52 | 300 | Blind | outer_diameter_mm | 1615.000 | 1580.000 | +35.000 |
| 52 | 300 | Blind | hub_diameter_mm | 1383.000 | 1356.000 | +27.000 |
| 52 | 300 | Blind | rf_diameter_mm | 1429.000 | 1410.000 | +19.000 |
| 52 | 300 | Blind | pcd_mm | 1517.600 | 1479.600 | +38.000 |
| 52 | 300 | Blind | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 52 | 300 | Blind | stud_bolt_length_mm | 405.000 | 420.000 | -15.000 |
| 52 | 300 | Blind | weight_kg | 2311.000 | 2225.000 | +86.000 |
| 52 | 300 | Blind | outer_diameter_mm | 1612.900 | 1580.000 | +32.900 |
| 52 | 300 | Blind | hub_diameter_mm | 1382.776 | 1356.000 | +26.776 |
| 52 | 300 | Blind | bore_diameter_mm | 33064.450 | 1301.800 | +31762.650 |
| 52 | 300 | Blind | rf_diameter_mm | 1428.750 | 1410.000 | +18.750 |
| 52 | 300 | Blind | pcd_mm | 1517.650 | 1479.600 | +38.050 |
| 52 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 52 | 300 | Blind | stud_bolt_length_mm | 406.400 | 420.000 | -13.600 |
| 52 | 300 | Blind | weight_kg | 2311.000 | 2225.000 | +86.000 |
| 54 | 300 | Welding Neck | outer_diameter_mm | 1657.350 | 1660.000 | -2.650 |
| 54 | 300 | Welding Neck | bore_diameter_mm | 34356.040 | 1352.600 | +33003.440 |
| 54 | 300 | Welding Neck | stud_bolt_length_mm | 450.850 | 445.000 | +5.850 |
| 54 | 300 | Welding Neck | outer_diameter_mm | 1675.000 | 1660.000 | +15.000 |
| 54 | 300 | Welding Neck | thickness_mm | 135.000 | 150.900 | -15.900 |
| 54 | 300 | Welding Neck | hub_diameter_mm | 1435.000 | 1410.000 | +25.000 |
| 54 | 300 | Welding Neck | length_thru_hub_mm | 135.000 | 251.000 | -116.000 |
| 54 | 300 | Welding Neck | rf_diameter_mm | 1480.000 | 1467.000 | +13.000 |
| 54 | 300 | Welding Neck | pcd_mm | 1578.000 | 1549.400 | +28.600 |
| 54 | 300 | Welding Neck | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 54 | 300 | Welding Neck | stud_bolt_length_mm | 405.000 | 445.000 | -40.000 |
| 54 | 300 | Welding Neck | weight_kg | 898.000 | 929.000 | -31.000 |
| 54 | 300 | Welding Neck | outer_diameter_mm | 1673.352 | 1660.000 | +13.352 |
| 54 | 300 | Welding Neck | thickness_mm | 135.128 | 150.900 | -15.772 |
| 54 | 300 | Welding Neck | hub_diameter_mm | 1435.100 | 1410.000 | +25.100 |
| 54 | 300 | Welding Neck | length_thru_hub_mm | 135.128 | 251.000 | -115.872 |
| 54 | 300 | Welding Neck | bore_diameter_mm | 34354.770 | 1352.600 | +33002.170 |
| 54 | 300 | Welding Neck | rf_diameter_mm | 1479.550 | 1467.000 | +12.550 |
| 54 | 300 | Welding Neck | pcd_mm | 1577.848 | 1549.400 | +28.448 |
| 54 | 300 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 54 | 300 | Welding Neck | stud_bolt_length_mm | 406.400 | 445.000 | -38.600 |
| 54 | 300 | Welding Neck | weight_kg | 898.000 | 929.000 | -31.000 |
| 54 | 300 | Slip On | outer_diameter_mm | 1657.350 | 1660.000 | -2.650 |
| 54 | 300 | Slip On | bore_diameter_mm | 34356.040 | 1352.600 | +33003.440 |
| 54 | 300 | Slip On | stud_bolt_length_mm | 450.850 | 445.000 | +5.850 |
| 54 | 300 | Slip On | outer_diameter_mm | 1675.000 | 1660.000 | +15.000 |
| 54 | 300 | Slip On | thickness_mm | 135.000 | 150.900 | -15.900 |
| 54 | 300 | Slip On | hub_diameter_mm | 1435.000 | 1410.000 | +25.000 |
| 54 | 300 | Slip On | length_thru_hub_mm | 135.000 | 150.900 | -15.900 |
| 54 | 300 | Slip On | rf_diameter_mm | 1480.000 | 1467.000 | +13.000 |
| 54 | 300 | Slip On | pcd_mm | 1578.000 | 1549.400 | +28.600 |
| 54 | 300 | Slip On | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 54 | 300 | Slip On | stud_bolt_length_mm | 405.000 | 445.000 | -40.000 |
| 54 | 300 | Slip On | outer_diameter_mm | 1673.352 | 1660.000 | +13.352 |
| 54 | 300 | Slip On | thickness_mm | 135.128 | 150.900 | -15.772 |
| 54 | 300 | Slip On | hub_diameter_mm | 1435.100 | 1410.000 | +25.100 |
| 54 | 300 | Slip On | length_thru_hub_mm | 135.128 | 150.900 | -15.772 |
| 54 | 300 | Slip On | bore_diameter_mm | 34354.770 | 1352.600 | +33002.170 |
| 54 | 300 | Slip On | rf_diameter_mm | 1479.550 | 1467.000 | +12.550 |
| 54 | 300 | Slip On | pcd_mm | 1577.848 | 1549.400 | +28.448 |
| 54 | 300 | Slip On | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 54 | 300 | Slip On | stud_bolt_length_mm | 406.400 | 445.000 | -38.600 |
| 54 | 300 | Blind | outer_diameter_mm | 1657.350 | 1660.000 | -2.650 |
| 54 | 300 | Blind | bore_diameter_mm | 34356.040 | 1352.600 | +33003.440 |
| 54 | 300 | Blind | stud_bolt_length_mm | 450.850 | 445.000 | +5.850 |
| 54 | 300 | Blind | outer_diameter_mm | 1675.000 | 1660.000 | +15.000 |
| 54 | 300 | Blind | thickness_mm | 147.700 | 150.900 | -3.200 |
| 54 | 300 | Blind | hub_diameter_mm | 1435.000 | 1410.000 | +25.000 |
| 54 | 300 | Blind | length_thru_hub_mm | 147.700 | 150.900 | -3.200 |
| 54 | 300 | Blind | rf_diameter_mm | 1480.000 | 1467.000 | +13.000 |
| 54 | 300 | Blind | pcd_mm | 1578.000 | 1549.400 | +28.600 |
| 54 | 300 | Blind | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 54 | 300 | Blind | stud_bolt_length_mm | 405.000 | 445.000 | -40.000 |
| 54 | 300 | Blind | weight_kg | 2575.000 | 2578.000 | -3.000 |
| 54 | 300 | Blind | outer_diameter_mm | 1673.352 | 1660.000 | +13.352 |
| 54 | 300 | Blind | thickness_mm | 147.574 | 150.900 | -3.326 |
| 54 | 300 | Blind | hub_diameter_mm | 1435.100 | 1410.000 | +25.100 |
| 54 | 300 | Blind | length_thru_hub_mm | 147.574 | 150.900 | -3.326 |
| 54 | 300 | Blind | bore_diameter_mm | 34354.770 | 1352.600 | +33002.170 |
| 54 | 300 | Blind | rf_diameter_mm | 1479.550 | 1467.000 | +12.550 |
| 54 | 300 | Blind | pcd_mm | 1577.848 | 1549.400 | +28.448 |
| 54 | 300 | Blind | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 54 | 300 | Blind | stud_bolt_length_mm | 406.400 | 445.000 | -38.600 |
| 54 | 300 | Blind | weight_kg | 2575.000 | 2578.000 | -3.000 |
| 56 | 300 | Welding Neck | outer_diameter_mm | 1708.150 | 1710.000 | -1.850 |
| 56 | 300 | Welding Neck | bore_diameter_mm | 35646.360 | 1403.400 | +34242.960 |
| 56 | 300 | Welding Neck | outer_diameter_mm | 1765.000 | 1710.000 | +55.000 |
| 56 | 300 | Welding Neck | hub_diameter_mm | 1494.000 | 1464.000 | +30.000 |
| 56 | 300 | Welding Neck | length_thru_hub_mm | 152.400 | 259.000 | -106.600 |
| 56 | 300 | Welding Neck | rf_diameter_mm | 1537.000 | 1518.000 | +19.000 |
| 56 | 300 | Welding Neck | pcd_mm | 1651.000 | 1600.200 | +50.800 |
| 56 | 300 | Welding Neck | weight_kg | 1177.000 | 977.000 | +200.000 |
| 56 | 300 | Welding Neck | outer_diameter_mm | 1765.300 | 1710.000 | +55.300 |
| 56 | 300 | Welding Neck | hub_diameter_mm | 1493.774 | 1464.000 | +29.774 |
| 56 | 300 | Welding Neck | length_thru_hub_mm | 152.400 | 259.000 | -106.600 |
| 56 | 300 | Welding Neck | bore_diameter_mm | 35645.090 | 1403.400 | +34241.690 |
| 56 | 300 | Welding Neck | rf_diameter_mm | 1536.700 | 1518.000 | +18.700 |
| 56 | 300 | Welding Neck | pcd_mm | 1651.000 | 1600.200 | +50.800 |
| 56 | 300 | Welding Neck | weight_kg | 1177.000 | 977.000 | +200.000 |
| 56 | 300 | Slip On | outer_diameter_mm | 1708.150 | 1710.000 | -1.850 |
| 56 | 300 | Slip On | bore_diameter_mm | 35646.360 | 1403.400 | +34242.960 |
| 56 | 300 | Slip On | outer_diameter_mm | 1765.000 | 1710.000 | +55.000 |
| 56 | 300 | Slip On | hub_diameter_mm | 1494.000 | 1464.000 | +30.000 |
| 56 | 300 | Slip On | rf_diameter_mm | 1537.000 | 1518.000 | +19.000 |
| 56 | 300 | Slip On | pcd_mm | 1651.000 | 1600.200 | +50.800 |
| 56 | 300 | Slip On | outer_diameter_mm | 1765.300 | 1710.000 | +55.300 |
| 56 | 300 | Slip On | hub_diameter_mm | 1493.774 | 1464.000 | +29.774 |
| 56 | 300 | Slip On | bore_diameter_mm | 35645.090 | 1403.400 | +34241.690 |
| 56 | 300 | Slip On | rf_diameter_mm | 1536.700 | 1518.000 | +18.700 |
| 56 | 300 | Slip On | pcd_mm | 1651.000 | 1600.200 | +50.800 |
| 56 | 300 | Blind | outer_diameter_mm | 1708.150 | 1710.000 | -1.850 |
| 56 | 300 | Blind | bore_diameter_mm | 35646.360 | 1403.400 | +34242.960 |
| 56 | 300 | Blind | outer_diameter_mm | 1765.000 | 1710.000 | +55.000 |
| 56 | 300 | Blind | thickness_mm | 155.400 | 152.400 | +3.000 |
| 56 | 300 | Blind | hub_diameter_mm | 1494.000 | 1464.000 | +30.000 |
| 56 | 300 | Blind | length_thru_hub_mm | 155.400 | 152.400 | +3.000 |
| 56 | 300 | Blind | rf_diameter_mm | 1537.000 | 1518.000 | +19.000 |
| 56 | 300 | Blind | pcd_mm | 1651.000 | 1600.200 | +50.800 |
| 56 | 300 | Blind | weight_kg | 3012.000 | 2766.000 | +246.000 |
| 56 | 300 | Blind | outer_diameter_mm | 1765.300 | 1710.000 | +55.300 |
| 56 | 300 | Blind | thickness_mm | 155.448 | 152.400 | +3.048 |
| 56 | 300 | Blind | hub_diameter_mm | 1493.774 | 1464.000 | +29.774 |
| 56 | 300 | Blind | length_thru_hub_mm | 155.448 | 152.400 | +3.048 |
| 56 | 300 | Blind | bore_diameter_mm | 35645.090 | 1403.400 | +34241.690 |
| 56 | 300 | Blind | rf_diameter_mm | 1536.700 | 1518.000 | +18.700 |
| 56 | 300 | Blind | pcd_mm | 1651.000 | 1600.200 | +50.800 |
| 56 | 300 | Blind | weight_kg | 3012.000 | 2766.000 | +246.000 |
| 58 | 300 | Welding Neck | outer_diameter_mm | 1758.950 | 1760.000 | -1.050 |
| 58 | 300 | Welding Neck | bore_diameter_mm | 36936.680 | 1454.200 | +35482.480 |
| 58 | 300 | Welding Neck | stud_bolt_length_mm | 457.200 | 455.000 | +2.200 |
| 58 | 300 | Welding Neck | outer_diameter_mm | 1825.000 | 1760.000 | +65.000 |
| 58 | 300 | Welding Neck | thickness_mm | 152.400 | 157.200 | -4.800 |
| 58 | 300 | Welding Neck | hub_diameter_mm | 1548.000 | 1514.000 | +34.000 |
| 58 | 300 | Welding Neck | length_thru_hub_mm | 152.400 | 265.000 | -112.600 |
| 58 | 300 | Welding Neck | rf_diameter_mm | 1594.000 | 1575.000 | +19.000 |
| 58 | 300 | Welding Neck | pcd_mm | 1712.900 | 1651.000 | +61.900 |
| 58 | 300 | Welding Neck | weight_kg | 1256.000 | 1029.000 | +227.000 |
| 58 | 300 | Welding Neck | outer_diameter_mm | 1827.276 | 1760.000 | +67.276 |
| 58 | 300 | Welding Neck | thickness_mm | 152.400 | 157.200 | -4.800 |
| 58 | 300 | Welding Neck | hub_diameter_mm | 1547.876 | 1514.000 | +33.876 |
| 58 | 300 | Welding Neck | length_thru_hub_mm | 152.400 | 265.000 | -112.600 |
| 58 | 300 | Welding Neck | bore_diameter_mm | 36935.410 | 1454.200 | +35481.210 |
| 58 | 300 | Welding Neck | rf_diameter_mm | 1593.850 | 1575.000 | +18.850 |
| 58 | 300 | Welding Neck | pcd_mm | 1712.976 | 1651.000 | +61.976 |
| 58 | 300 | Welding Neck | stud_bolt_length_mm | 457.200 | 455.000 | +2.200 |
| 58 | 300 | Welding Neck | weight_kg | 1256.000 | 1029.000 | +227.000 |
| 58 | 300 | Slip On | outer_diameter_mm | 1758.950 | 1760.000 | -1.050 |
| 58 | 300 | Slip On | bore_diameter_mm | 36936.680 | 1454.200 | +35482.480 |
| 58 | 300 | Slip On | stud_bolt_length_mm | 457.200 | 455.000 | +2.200 |
| 58 | 300 | Slip On | outer_diameter_mm | 1825.000 | 1760.000 | +65.000 |
| 58 | 300 | Slip On | thickness_mm | 152.400 | 157.200 | -4.800 |
| 58 | 300 | Slip On | hub_diameter_mm | 1548.000 | 1514.000 | +34.000 |
| 58 | 300 | Slip On | length_thru_hub_mm | 152.400 | 157.200 | -4.800 |
| 58 | 300 | Slip On | rf_diameter_mm | 1594.000 | 1575.000 | +19.000 |
| 58 | 300 | Slip On | pcd_mm | 1712.900 | 1651.000 | +61.900 |
| 58 | 300 | Slip On | outer_diameter_mm | 1827.276 | 1760.000 | +67.276 |
| 58 | 300 | Slip On | thickness_mm | 152.400 | 157.200 | -4.800 |
| 58 | 300 | Slip On | hub_diameter_mm | 1547.876 | 1514.000 | +33.876 |
| 58 | 300 | Slip On | length_thru_hub_mm | 152.400 | 157.200 | -4.800 |
| 58 | 300 | Slip On | bore_diameter_mm | 36935.410 | 1454.200 | +35481.210 |
| 58 | 300 | Slip On | rf_diameter_mm | 1593.850 | 1575.000 | +18.850 |
| 58 | 300 | Slip On | pcd_mm | 1712.976 | 1651.000 | +61.976 |
| 58 | 300 | Slip On | stud_bolt_length_mm | 457.200 | 455.000 | +2.200 |
| 58 | 300 | Blind | outer_diameter_mm | 1758.950 | 1760.000 | -1.050 |
| 58 | 300 | Blind | bore_diameter_mm | 36936.680 | 1454.200 | +35482.480 |
| 58 | 300 | Blind | stud_bolt_length_mm | 457.200 | 455.000 | +2.200 |
| 58 | 300 | Blind | outer_diameter_mm | 1825.000 | 1760.000 | +65.000 |
| 58 | 300 | Blind | thickness_mm | 160.400 | 157.200 | +3.200 |
| 58 | 300 | Blind | hub_diameter_mm | 1548.000 | 1514.000 | +34.000 |
| 58 | 300 | Blind | length_thru_hub_mm | 160.400 | 157.200 | +3.200 |
| 58 | 300 | Blind | rf_diameter_mm | 1594.000 | 1575.000 | +19.000 |
| 58 | 300 | Blind | pcd_mm | 1712.900 | 1651.000 | +61.900 |
| 58 | 300 | Blind | weight_kg | 3332.000 | 3025.000 | +307.000 |
| 58 | 300 | Blind | outer_diameter_mm | 1827.276 | 1760.000 | +67.276 |
| 58 | 300 | Blind | thickness_mm | 160.274 | 157.200 | +3.074 |
| 58 | 300 | Blind | hub_diameter_mm | 1547.876 | 1514.000 | +33.876 |
| 58 | 300 | Blind | length_thru_hub_mm | 160.274 | 157.200 | +3.074 |
| 58 | 300 | Blind | bore_diameter_mm | 36935.410 | 1454.200 | +35481.210 |
| 58 | 300 | Blind | rf_diameter_mm | 1593.850 | 1575.000 | +18.850 |
| 58 | 300 | Blind | pcd_mm | 1712.976 | 1651.000 | +61.976 |
| 58 | 300 | Blind | stud_bolt_length_mm | 457.200 | 455.000 | +2.200 |
| 58 | 300 | Blind | weight_kg | 3332.000 | 3025.000 | +307.000 |
| 60 | 300 | Welding Neck | bore_diameter_mm | 38227.000 | 1505.000 | +36722.000 |
| 60 | 300 | Welding Neck | stud_bolt_length_mm | 476.250 | 470.000 | +6.250 |
| 60 | 300 | Welding Neck | outer_diameter_mm | 1880.000 | 1810.000 | +70.000 |
| 60 | 300 | Welding Neck | thickness_mm | 149.300 | 162.000 | -12.700 |
| 60 | 300 | Welding Neck | hub_diameter_mm | 1599.000 | 1565.000 | +34.000 |
| 60 | 300 | Welding Neck | length_thru_hub_mm | 149.300 | 271.000 | -121.700 |
| 60 | 300 | Welding Neck | rf_diameter_mm | 1651.000 | 1626.000 | +25.000 |
| 60 | 300 | Welding Neck | pcd_mm | 1763.700 | 1701.800 | +61.900 |
| 60 | 300 | Welding Neck | stud_bolt_length_mm | 455.000 | 470.000 | -15.000 |
| 60 | 300 | Welding Neck | weight_kg | 1301.000 | 1120.000 | +181.000 |
| 60 | 300 | Welding Neck | outer_diameter_mm | 1878.076 | 1810.000 | +68.076 |
| 60 | 300 | Welding Neck | thickness_mm | 149.352 | 162.000 | -12.648 |
| 60 | 300 | Welding Neck | hub_diameter_mm | 1598.676 | 1565.000 | +33.676 |
| 60 | 300 | Welding Neck | length_thru_hub_mm | 149.352 | 271.000 | -121.648 |
| 60 | 300 | Welding Neck | bore_diameter_mm | 38225.730 | 1505.000 | +36720.730 |
| 60 | 300 | Welding Neck | rf_diameter_mm | 1651.000 | 1626.000 | +25.000 |
| 60 | 300 | Welding Neck | pcd_mm | 1763.776 | 1701.800 | +61.976 |
| 60 | 300 | Welding Neck | stud_bolt_length_mm | 457.200 | 470.000 | -12.800 |
| 60 | 300 | Welding Neck | weight_kg | 1301.000 | 1120.000 | +181.000 |
| 60 | 300 | Slip On | bore_diameter_mm | 38227.000 | 1505.000 | +36722.000 |
| 60 | 300 | Slip On | stud_bolt_length_mm | 476.250 | 470.000 | +6.250 |
| 60 | 300 | Slip On | outer_diameter_mm | 1880.000 | 1810.000 | +70.000 |
| 60 | 300 | Slip On | thickness_mm | 149.300 | 162.000 | -12.700 |
| 60 | 300 | Slip On | hub_diameter_mm | 1599.000 | 1565.000 | +34.000 |
| 60 | 300 | Slip On | length_thru_hub_mm | 149.300 | 162.000 | -12.700 |
| 60 | 300 | Slip On | rf_diameter_mm | 1651.000 | 1626.000 | +25.000 |
| 60 | 300 | Slip On | pcd_mm | 1763.700 | 1701.800 | +61.900 |
| 60 | 300 | Slip On | stud_bolt_length_mm | 455.000 | 470.000 | -15.000 |
| 60 | 300 | Slip On | outer_diameter_mm | 1878.076 | 1810.000 | +68.076 |
| 60 | 300 | Slip On | thickness_mm | 149.352 | 162.000 | -12.648 |
| 60 | 300 | Slip On | hub_diameter_mm | 1598.676 | 1565.000 | +33.676 |
| 60 | 300 | Slip On | length_thru_hub_mm | 149.352 | 162.000 | -12.648 |
| 60 | 300 | Slip On | bore_diameter_mm | 38225.730 | 1505.000 | +36720.730 |
| 60 | 300 | Slip On | rf_diameter_mm | 1651.000 | 1626.000 | +25.000 |
| 60 | 300 | Slip On | pcd_mm | 1763.776 | 1701.800 | +61.976 |
| 60 | 300 | Slip On | stud_bolt_length_mm | 457.200 | 470.000 | -12.800 |
| 60 | 300 | Blind | bore_diameter_mm | 38227.000 | 1505.000 | +36722.000 |
| 60 | 300 | Blind | stud_bolt_length_mm | 476.250 | 470.000 | +6.250 |
| 60 | 300 | Blind | outer_diameter_mm | 1880.000 | 1810.000 | +70.000 |
| 60 | 300 | Blind | thickness_mm | 165.100 | 162.000 | +3.100 |
| 60 | 300 | Blind | hub_diameter_mm | 1599.000 | 1565.000 | +34.000 |
| 60 | 300 | Blind | length_thru_hub_mm | 165.100 | 162.000 | +3.100 |
| 60 | 300 | Blind | rf_diameter_mm | 1651.000 | 1626.000 | +25.000 |
| 60 | 300 | Blind | pcd_mm | 1763.700 | 1701.800 | +61.900 |
| 60 | 300 | Blind | stud_bolt_length_mm | 455.000 | 470.000 | -15.000 |
| 60 | 300 | Blind | weight_kg | 3619.000 | 3299.000 | +320.000 |
| 60 | 300 | Blind | outer_diameter_mm | 1878.076 | 1810.000 | +68.076 |
| 60 | 300 | Blind | thickness_mm | 165.100 | 162.000 | +3.100 |
| 60 | 300 | Blind | hub_diameter_mm | 1598.676 | 1565.000 | +33.676 |
| 60 | 300 | Blind | length_thru_hub_mm | 165.100 | 162.000 | +3.100 |
| 60 | 300 | Blind | bore_diameter_mm | 38225.730 | 1505.000 | +36720.730 |
| 60 | 300 | Blind | rf_diameter_mm | 1651.000 | 1626.000 | +25.000 |
| 60 | 300 | Blind | pcd_mm | 1763.776 | 1701.800 | +61.976 |
| 60 | 300 | Blind | stud_bolt_length_mm | 457.200 | 470.000 | -12.800 |
| 60 | 300 | Blind | weight_kg | 3619.000 | 3299.000 | +320.000 |
| 26 | 400 | Welding Neck | outer_diameter_mm | 971.550 | 970.000 | +1.550 |
| 26 | 400 | Welding Neck | stud_bolt_length_mm | 317.500 | 315.000 | +2.500 |
| 26 | 400 | Welding Neck | outer_diameter_mm | 850.000 | 970.000 | -120.000 |
| 26 | 400 | Welding Neck | hub_diameter_mm | 689.000 | 727.000 | -38.000 |
| 26 | 400 | Welding Neck | length_thru_hub_mm | 88.900 | 194.000 | -105.100 |
| 26 | 400 | Welding Neck | rf_diameter_mm | 711.000 | 749.000 | -38.000 |
| 26 | 400 | Welding Neck | pcd_mm | 781.000 | 876.300 | -95.300 |
| 26 | 400 | Welding Neck | stud_bolt_length_mm | 310.000 | 315.000 | -5.000 |
| 26 | 400 | Welding Neck | weight_kg | 223.000 | 565.000 | -342.000 |
| 26 | 400 | Welding Neck | outer_diameter_mm | 850.900 | 970.000 | -119.100 |
| 26 | 400 | Welding Neck | hub_diameter_mm | 688.848 | 727.000 | -38.152 |
| 26 | 400 | Welding Neck | length_thru_hub_mm | 88.900 | 194.000 | -105.100 |
| 26 | 400 | Welding Neck | rf_diameter_mm | 711.200 | 749.000 | -37.800 |
| 26 | 400 | Welding Neck | pcd_mm | 781.050 | 876.300 | -95.250 |
| 26 | 400 | Welding Neck | stud_bolt_length_mm | 311.150 | 315.000 | -3.850 |
| 26 | 400 | Welding Neck | weight_kg | 223.000 | 565.000 | -342.000 |
| 26 | 400 | Slip On | outer_diameter_mm | 971.550 | 970.000 | +1.550 |
| 26 | 400 | Slip On | stud_bolt_length_mm | 317.500 | 315.000 | +2.500 |
| 26 | 400 | Slip On | outer_diameter_mm | 850.000 | 970.000 | -120.000 |
| 26 | 400 | Slip On | hub_diameter_mm | 689.000 | 727.000 | -38.000 |
| 26 | 400 | Slip On | rf_diameter_mm | 711.000 | 749.000 | -38.000 |
| 26 | 400 | Slip On | pcd_mm | 781.000 | 876.300 | -95.300 |
| 26 | 400 | Slip On | stud_bolt_length_mm | 310.000 | 315.000 | -5.000 |
| 26 | 400 | Slip On | outer_diameter_mm | 850.900 | 970.000 | -119.100 |
| 26 | 400 | Slip On | hub_diameter_mm | 688.848 | 727.000 | -38.152 |
| 26 | 400 | Slip On | rf_diameter_mm | 711.200 | 749.000 | -37.800 |
| 26 | 400 | Slip On | pcd_mm | 781.050 | 876.300 | -95.250 |
| 26 | 400 | Slip On | stud_bolt_length_mm | 311.150 | 315.000 | -3.850 |
| 26 | 400 | Blind | outer_diameter_mm | 971.550 | 970.000 | +1.550 |
| 26 | 400 | Blind | stud_bolt_length_mm | 317.500 | 315.000 | +2.500 |
| 26 | 400 | Blind | outer_diameter_mm | 850.000 | 970.000 | -120.000 |
| 26 | 400 | Blind | thickness_mm | 88.900 | 98.500 | -9.600 |
| 26 | 400 | Blind | hub_diameter_mm | 689.000 | 727.000 | -38.000 |
| 26 | 400 | Blind | length_thru_hub_mm | 88.900 | 98.500 | -9.600 |
| 26 | 400 | Blind | rf_diameter_mm | 711.000 | 749.000 | -38.000 |
| 26 | 400 | Blind | pcd_mm | 781.000 | 876.300 | -95.300 |
| 26 | 400 | Blind | stud_bolt_length_mm | 310.000 | 315.000 | -5.000 |
| 26 | 400 | Blind | weight_kg | 477.050 | 565.000 | -87.950 |
| 26 | 400 | Blind | outer_diameter_mm | 850.900 | 970.000 | -119.100 |
| 26 | 400 | Blind | thickness_mm | 88.900 | 98.500 | -9.600 |
| 26 | 400 | Blind | hub_diameter_mm | 688.848 | 727.000 | -38.152 |
| 26 | 400 | Blind | length_thru_hub_mm | 88.900 | 98.500 | -9.600 |
| 26 | 400 | Blind | rf_diameter_mm | 711.200 | 749.000 | -37.800 |
| 26 | 400 | Blind | pcd_mm | 781.050 | 876.300 | -95.250 |
| 26 | 400 | Blind | stud_bolt_length_mm | 311.150 | 315.000 | -3.850 |
| 26 | 400 | Blind | weight_kg | 477.050 | 565.000 | -87.950 |
| 28 | 400 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 28 | 400 | Welding Neck | outer_diameter_mm | 915.000 | 1035.000 | -120.000 |
| 28 | 400 | Welding Neck | hub_diameter_mm | 740.000 | 783.000 | -43.000 |
| 28 | 400 | Welding Neck | length_thru_hub_mm | 95.300 | 206.000 | -110.700 |
| 28 | 400 | Welding Neck | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 400 | Welding Neck | pcd_mm | 838.200 | 939.800 | -101.600 |
| 28 | 400 | Welding Neck | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 28 | 400 | Welding Neck | weight_kg | 252.950 | 715.000 | -462.050 |
| 28 | 400 | Welding Neck | outer_diameter_mm | 914.400 | 1035.000 | -120.600 |
| 28 | 400 | Welding Neck | hub_diameter_mm | 739.648 | 783.000 | -43.352 |
| 28 | 400 | Welding Neck | length_thru_hub_mm | 95.250 | 206.000 | -110.750 |
| 28 | 400 | Welding Neck | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 400 | Welding Neck | pcd_mm | 838.200 | 939.800 | -101.600 |
| 28 | 400 | Welding Neck | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 28 | 400 | Welding Neck | weight_kg | 252.950 | 715.000 | -462.050 |
| 28 | 400 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 28 | 400 | Slip On | outer_diameter_mm | 915.000 | 1035.000 | -120.000 |
| 28 | 400 | Slip On | hub_diameter_mm | 740.000 | 783.000 | -43.000 |
| 28 | 400 | Slip On | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 400 | Slip On | pcd_mm | 838.200 | 939.800 | -101.600 |
| 28 | 400 | Slip On | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 28 | 400 | Slip On | outer_diameter_mm | 914.400 | 1035.000 | -120.600 |
| 28 | 400 | Slip On | hub_diameter_mm | 739.648 | 783.000 | -43.352 |
| 28 | 400 | Slip On | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 400 | Slip On | pcd_mm | 838.200 | 939.800 | -101.600 |
| 28 | 400 | Slip On | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 28 | 400 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 28 | 400 | Blind | outer_diameter_mm | 915.000 | 1035.000 | -120.000 |
| 28 | 400 | Blind | thickness_mm | 95.300 | 104.800 | -9.500 |
| 28 | 400 | Blind | hub_diameter_mm | 740.000 | 783.000 | -43.000 |
| 28 | 400 | Blind | length_thru_hub_mm | 95.300 | 104.800 | -9.500 |
| 28 | 400 | Blind | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 400 | Blind | pcd_mm | 838.200 | 939.800 | -101.600 |
| 28 | 400 | Blind | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 28 | 400 | Blind | weight_kg | 555.660 | 715.000 | -159.340 |
| 28 | 400 | Blind | outer_diameter_mm | 914.400 | 1035.000 | -120.600 |
| 28 | 400 | Blind | thickness_mm | 95.250 | 104.800 | -9.550 |
| 28 | 400 | Blind | hub_diameter_mm | 739.648 | 783.000 | -43.352 |
| 28 | 400 | Blind | length_thru_hub_mm | 95.250 | 104.800 | -9.550 |
| 28 | 400 | Blind | rf_diameter_mm | 762.000 | 800.000 | -38.000 |
| 28 | 400 | Blind | pcd_mm | 838.200 | 939.800 | -101.600 |
| 28 | 400 | Blind | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 28 | 400 | Blind | weight_kg | 555.660 | 715.000 | -159.340 |
| 30 | 400 | Welding Neck | outer_diameter_mm | 1092.200 | 1090.000 | +2.200 |
| 30 | 400 | Welding Neck | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 30 | 400 | Welding Neck | outer_diameter_mm | 970.000 | 1090.000 | -120.000 |
| 30 | 400 | Welding Neck | hub_diameter_mm | 794.000 | 837.000 | -43.000 |
| 30 | 400 | Welding Neck | length_thru_hub_mm | 101.600 | 219.000 | -117.400 |
| 30 | 400 | Welding Neck | rf_diameter_mm | 819.000 | 857.000 | -38.000 |
| 30 | 400 | Welding Neck | pcd_mm | 895.400 | 997.000 | -101.600 |
| 30 | 400 | Welding Neck | stud_bolt_length_mm | 335.000 | 350.000 | -15.000 |
| 30 | 400 | Welding Neck | weight_kg | 320.450 | 830.000 | -509.550 |
| 30 | 400 | Welding Neck | outer_diameter_mm | 971.550 | 1090.000 | -118.450 |
| 30 | 400 | Welding Neck | hub_diameter_mm | 793.750 | 837.000 | -43.250 |
| 30 | 400 | Welding Neck | length_thru_hub_mm | 101.600 | 219.000 | -117.400 |
| 30 | 400 | Welding Neck | rf_diameter_mm | 819.150 | 857.000 | -37.850 |
| 30 | 400 | Welding Neck | pcd_mm | 895.350 | 997.000 | -101.650 |
| 30 | 400 | Welding Neck | stud_bolt_length_mm | 336.550 | 350.000 | -13.450 |
| 30 | 400 | Welding Neck | weight_kg | 320.450 | 830.000 | -509.550 |
| 30 | 400 | Slip On | outer_diameter_mm | 1092.200 | 1090.000 | +2.200 |
| 30 | 400 | Slip On | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 30 | 400 | Slip On | outer_diameter_mm | 970.000 | 1090.000 | -120.000 |
| 30 | 400 | Slip On | hub_diameter_mm | 794.000 | 837.000 | -43.000 |
| 30 | 400 | Slip On | rf_diameter_mm | 819.000 | 857.000 | -38.000 |
| 30 | 400 | Slip On | pcd_mm | 895.400 | 997.000 | -101.600 |
| 30 | 400 | Slip On | stud_bolt_length_mm | 335.000 | 350.000 | -15.000 |
| 30 | 400 | Slip On | outer_diameter_mm | 971.550 | 1090.000 | -118.450 |
| 30 | 400 | Slip On | hub_diameter_mm | 793.750 | 837.000 | -43.250 |
| 30 | 400 | Slip On | rf_diameter_mm | 819.150 | 857.000 | -37.850 |
| 30 | 400 | Slip On | pcd_mm | 895.350 | 997.000 | -101.650 |
| 30 | 400 | Slip On | stud_bolt_length_mm | 336.550 | 350.000 | -13.450 |
| 30 | 400 | Blind | outer_diameter_mm | 1092.200 | 1090.000 | +2.200 |
| 30 | 400 | Blind | stud_bolt_length_mm | 355.600 | 350.000 | +5.600 |
| 30 | 400 | Blind | outer_diameter_mm | 970.000 | 1090.000 | -120.000 |
| 30 | 400 | Blind | thickness_mm | 101.600 | 111.200 | -9.600 |
| 30 | 400 | Blind | hub_diameter_mm | 794.000 | 837.000 | -43.000 |
| 30 | 400 | Blind | length_thru_hub_mm | 101.600 | 111.200 | -9.600 |
| 30 | 400 | Blind | rf_diameter_mm | 819.000 | 857.000 | -38.000 |
| 30 | 400 | Blind | pcd_mm | 895.400 | 997.000 | -101.600 |
| 30 | 400 | Blind | stud_bolt_length_mm | 335.000 | 350.000 | -15.000 |
| 30 | 400 | Blind | weight_kg | 692.630 | 830.000 | -137.370 |
| 30 | 400 | Blind | outer_diameter_mm | 971.550 | 1090.000 | -118.450 |
| 30 | 400 | Blind | thickness_mm | 101.600 | 111.200 | -9.600 |
| 30 | 400 | Blind | hub_diameter_mm | 793.750 | 837.000 | -43.250 |
| 30 | 400 | Blind | length_thru_hub_mm | 101.600 | 111.200 | -9.600 |
| 30 | 400 | Blind | rf_diameter_mm | 819.150 | 857.000 | -37.850 |
| 30 | 400 | Blind | pcd_mm | 895.350 | 997.000 | -101.650 |
| 30 | 400 | Blind | stud_bolt_length_mm | 336.550 | 350.000 | -13.450 |
| 30 | 400 | Blind | weight_kg | 692.630 | 830.000 | -137.370 |
| 32 | 400 | Welding Neck | stud_bolt_length_mm | 368.300 | 365.000 | +3.300 |
| 32 | 400 | Welding Neck | outer_diameter_mm | 1035.000 | 1150.000 | -115.000 |
| 32 | 400 | Welding Neck | hub_diameter_mm | 845.000 | 889.000 | -44.000 |
| 32 | 400 | Welding Neck | length_thru_hub_mm | 108.000 | 232.000 | -124.000 |
| 32 | 400 | Welding Neck | rf_diameter_mm | 873.000 | 914.000 | -41.000 |
| 32 | 400 | Welding Neck | pcd_mm | 952.500 | 1054.100 | -101.600 |
| 32 | 400 | Welding Neck | weight_kg | 381.450 | 955.000 | -573.550 |
| 32 | 400 | Welding Neck | outer_diameter_mm | 1035.050 | 1150.000 | -114.950 |
| 32 | 400 | Welding Neck | hub_diameter_mm | 844.550 | 889.000 | -44.450 |
| 32 | 400 | Welding Neck | length_thru_hub_mm | 107.950 | 232.000 | -124.050 |
| 32 | 400 | Welding Neck | rf_diameter_mm | 873.252 | 914.000 | -40.748 |
| 32 | 400 | Welding Neck | pcd_mm | 952.500 | 1054.100 | -101.600 |
| 32 | 400 | Welding Neck | stud_bolt_length_mm | 368.300 | 365.000 | +3.300 |
| 32 | 400 | Welding Neck | weight_kg | 381.450 | 955.000 | -573.550 |
| 32 | 400 | Slip On | stud_bolt_length_mm | 368.300 | 365.000 | +3.300 |
| 32 | 400 | Slip On | outer_diameter_mm | 1035.000 | 1150.000 | -115.000 |
| 32 | 400 | Slip On | hub_diameter_mm | 845.000 | 889.000 | -44.000 |
| 32 | 400 | Slip On | rf_diameter_mm | 873.000 | 914.000 | -41.000 |
| 32 | 400 | Slip On | pcd_mm | 952.500 | 1054.100 | -101.600 |
| 32 | 400 | Slip On | outer_diameter_mm | 1035.050 | 1150.000 | -114.950 |
| 32 | 400 | Slip On | hub_diameter_mm | 844.550 | 889.000 | -44.450 |
| 32 | 400 | Slip On | rf_diameter_mm | 873.252 | 914.000 | -40.748 |
| 32 | 400 | Slip On | pcd_mm | 952.500 | 1054.100 | -101.600 |
| 32 | 400 | Slip On | stud_bolt_length_mm | 368.300 | 365.000 | +3.300 |
| 32 | 400 | Blind | stud_bolt_length_mm | 368.300 | 365.000 | +3.300 |
| 32 | 400 | Blind | outer_diameter_mm | 1035.000 | 1150.000 | -115.000 |
| 32 | 400 | Blind | thickness_mm | 108.000 | 115.900 | -7.900 |
| 32 | 400 | Blind | hub_diameter_mm | 845.000 | 889.000 | -44.000 |
| 32 | 400 | Blind | length_thru_hub_mm | 108.000 | 115.900 | -7.900 |
| 32 | 400 | Blind | rf_diameter_mm | 873.000 | 914.000 | -41.000 |
| 32 | 400 | Blind | pcd_mm | 952.500 | 1054.100 | -101.600 |
| 32 | 400 | Blind | weight_kg | 843.460 | 955.000 | -111.540 |
| 32 | 400 | Blind | outer_diameter_mm | 1035.050 | 1150.000 | -114.950 |
| 32 | 400 | Blind | thickness_mm | 107.950 | 115.900 | -7.950 |
| 32 | 400 | Blind | hub_diameter_mm | 844.550 | 889.000 | -44.450 |
| 32 | 400 | Blind | length_thru_hub_mm | 107.950 | 115.900 | -7.950 |
| 32 | 400 | Blind | rf_diameter_mm | 873.252 | 914.000 | -40.748 |
| 32 | 400 | Blind | pcd_mm | 952.500 | 1054.100 | -101.600 |
| 32 | 400 | Blind | stud_bolt_length_mm | 368.300 | 365.000 | +3.300 |
| 32 | 400 | Blind | weight_kg | 843.460 | 955.000 | -111.540 |
| 34 | 400 | Welding Neck | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 34 | 400 | Welding Neck | outer_diameter_mm | 1085.000 | 1205.000 | -120.000 |
| 34 | 400 | Welding Neck | hub_diameter_mm | 899.000 | 945.000 | -46.000 |
| 34 | 400 | Welding Neck | length_thru_hub_mm | 111.200 | 241.000 | -129.800 |
| 34 | 400 | Welding Neck | rf_diameter_mm | 927.000 | 965.000 | -38.000 |
| 34 | 400 | Welding Neck | pcd_mm | 1003.300 | 1104.900 | -101.600 |
| 34 | 400 | Welding Neck | stud_bolt_length_mm | 370.000 | 380.000 | -10.000 |
| 34 | 400 | Welding Neck | weight_kg | 451.400 | 1120.000 | -668.600 |
| 34 | 400 | Welding Neck | outer_diameter_mm | 1085.850 | 1205.000 | -119.150 |
| 34 | 400 | Welding Neck | hub_diameter_mm | 898.652 | 945.000 | -46.348 |
| 34 | 400 | Welding Neck | length_thru_hub_mm | 111.252 | 241.000 | -129.748 |
| 34 | 400 | Welding Neck | rf_diameter_mm | 927.100 | 965.000 | -37.900 |
| 34 | 400 | Welding Neck | pcd_mm | 1003.300 | 1104.900 | -101.600 |
| 34 | 400 | Welding Neck | stud_bolt_length_mm | 374.650 | 380.000 | -5.350 |
| 34 | 400 | Welding Neck | weight_kg | 451.400 | 1120.000 | -668.600 |
| 34 | 400 | Slip On | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 34 | 400 | Slip On | outer_diameter_mm | 1085.000 | 1205.000 | -120.000 |
| 34 | 400 | Slip On | hub_diameter_mm | 899.000 | 945.000 | -46.000 |
| 34 | 400 | Slip On | rf_diameter_mm | 927.000 | 965.000 | -38.000 |
| 34 | 400 | Slip On | pcd_mm | 1003.300 | 1104.900 | -101.600 |
| 34 | 400 | Slip On | stud_bolt_length_mm | 370.000 | 380.000 | -10.000 |
| 34 | 400 | Slip On | outer_diameter_mm | 1085.850 | 1205.000 | -119.150 |
| 34 | 400 | Slip On | hub_diameter_mm | 898.652 | 945.000 | -46.348 |
| 34 | 400 | Slip On | rf_diameter_mm | 927.100 | 965.000 | -37.900 |
| 34 | 400 | Slip On | pcd_mm | 1003.300 | 1104.900 | -101.600 |
| 34 | 400 | Slip On | stud_bolt_length_mm | 374.650 | 380.000 | -5.350 |
| 34 | 400 | Blind | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 34 | 400 | Blind | outer_diameter_mm | 1085.000 | 1205.000 | -120.000 |
| 34 | 400 | Blind | thickness_mm | 111.200 | 122.300 | -11.100 |
| 34 | 400 | Blind | hub_diameter_mm | 899.000 | 945.000 | -46.000 |
| 34 | 400 | Blind | length_thru_hub_mm | 111.200 | 122.300 | -11.100 |
| 34 | 400 | Blind | rf_diameter_mm | 927.000 | 965.000 | -38.000 |
| 34 | 400 | Blind | pcd_mm | 1003.300 | 1104.900 | -101.600 |
| 34 | 400 | Blind | stud_bolt_length_mm | 370.000 | 380.000 | -10.000 |
| 34 | 400 | Blind | weight_kg | 990.910 | 1120.000 | -129.090 |
| 34 | 400 | Blind | outer_diameter_mm | 1085.850 | 1205.000 | -119.150 |
| 34 | 400 | Blind | thickness_mm | 111.252 | 122.300 | -11.048 |
| 34 | 400 | Blind | hub_diameter_mm | 898.652 | 945.000 | -46.348 |
| 34 | 400 | Blind | length_thru_hub_mm | 111.252 | 122.300 | -11.048 |
| 34 | 400 | Blind | rf_diameter_mm | 927.100 | 965.000 | -37.900 |
| 34 | 400 | Blind | pcd_mm | 1003.300 | 1104.900 | -101.600 |
| 34 | 400 | Blind | stud_bolt_length_mm | 374.650 | 380.000 | -5.350 |
| 34 | 400 | Blind | weight_kg | 990.910 | 1120.000 | -129.090 |
| 36 | 400 | Welding Neck | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 36 | 400 | Welding Neck | outer_diameter_mm | 1155.000 | 1270.000 | -115.000 |
| 36 | 400 | Welding Neck | thickness_mm | 119.100 | 114.300 | +4.800 |
| 36 | 400 | Welding Neck | hub_diameter_mm | 952.000 | 1000.000 | -48.000 |
| 36 | 400 | Welding Neck | length_thru_hub_mm | 119.100 | 251.000 | -131.900 |
| 36 | 400 | Welding Neck | rf_diameter_mm | 981.000 | 1022.000 | -41.000 |
| 36 | 400 | Welding Neck | pcd_mm | 1066.800 | 1168.400 | -101.600 |
| 36 | 400 | Welding Neck | stud_bolt_length_mm | 400.000 | 405.000 | -5.000 |
| 36 | 400 | Welding Neck | weight_kg | 496.950 | 1290.000 | -793.050 |
| 36 | 400 | Welding Neck | outer_diameter_mm | 1155.700 | 1270.000 | -114.300 |
| 36 | 400 | Welding Neck | thickness_mm | 119.126 | 114.300 | +4.826 |
| 36 | 400 | Welding Neck | hub_diameter_mm | 952.500 | 1000.000 | -47.500 |
| 36 | 400 | Welding Neck | length_thru_hub_mm | 119.126 | 251.000 | -131.874 |
| 36 | 400 | Welding Neck | rf_diameter_mm | 980.948 | 1022.000 | -41.052 |
| 36 | 400 | Welding Neck | pcd_mm | 1066.800 | 1168.400 | -101.600 |
| 36 | 400 | Welding Neck | stud_bolt_length_mm | 400.050 | 405.000 | -4.950 |
| 36 | 400 | Welding Neck | weight_kg | 496.950 | 1290.000 | -793.050 |
| 36 | 400 | Slip On | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 36 | 400 | Slip On | outer_diameter_mm | 1155.000 | 1270.000 | -115.000 |
| 36 | 400 | Slip On | thickness_mm | 119.100 | 114.300 | +4.800 |
| 36 | 400 | Slip On | hub_diameter_mm | 952.000 | 1000.000 | -48.000 |
| 36 | 400 | Slip On | length_thru_hub_mm | 119.100 | 114.300 | +4.800 |
| 36 | 400 | Slip On | rf_diameter_mm | 981.000 | 1022.000 | -41.000 |
| 36 | 400 | Slip On | pcd_mm | 1066.800 | 1168.400 | -101.600 |
| 36 | 400 | Slip On | stud_bolt_length_mm | 400.000 | 405.000 | -5.000 |
| 36 | 400 | Slip On | outer_diameter_mm | 1155.700 | 1270.000 | -114.300 |
| 36 | 400 | Slip On | thickness_mm | 119.126 | 114.300 | +4.826 |
| 36 | 400 | Slip On | hub_diameter_mm | 952.500 | 1000.000 | -47.500 |
| 36 | 400 | Slip On | length_thru_hub_mm | 119.126 | 114.300 | +4.826 |
| 36 | 400 | Slip On | rf_diameter_mm | 980.948 | 1022.000 | -41.052 |
| 36 | 400 | Slip On | pcd_mm | 1066.800 | 1168.400 | -101.600 |
| 36 | 400 | Slip On | stud_bolt_length_mm | 400.050 | 405.000 | -4.950 |
| 36 | 400 | Blind | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 36 | 400 | Blind | outer_diameter_mm | 1155.000 | 1270.000 | -115.000 |
| 36 | 400 | Blind | thickness_mm | 119.100 | 128.600 | -9.500 |
| 36 | 400 | Blind | hub_diameter_mm | 952.000 | 1000.000 | -48.000 |
| 36 | 400 | Blind | length_thru_hub_mm | 119.100 | 128.600 | -9.500 |
| 36 | 400 | Blind | rf_diameter_mm | 981.000 | 1022.000 | -41.000 |
| 36 | 400 | Blind | pcd_mm | 1066.800 | 1168.400 | -101.600 |
| 36 | 400 | Blind | stud_bolt_length_mm | 400.000 | 405.000 | -5.000 |
| 36 | 400 | Blind | weight_kg | 1120.510 | 1290.000 | -169.490 |
| 36 | 400 | Blind | outer_diameter_mm | 1155.700 | 1270.000 | -114.300 |
| 36 | 400 | Blind | thickness_mm | 119.126 | 128.600 | -9.474 |
| 36 | 400 | Blind | hub_diameter_mm | 952.500 | 1000.000 | -47.500 |
| 36 | 400 | Blind | length_thru_hub_mm | 119.126 | 128.600 | -9.474 |
| 36 | 400 | Blind | rf_diameter_mm | 980.948 | 1022.000 | -41.052 |
| 36 | 400 | Blind | pcd_mm | 1066.800 | 1168.400 | -101.600 |
| 36 | 400 | Blind | stud_bolt_length_mm | 400.050 | 405.000 | -4.950 |
| 36 | 400 | Blind | weight_kg | 1120.510 | 1290.000 | -169.490 |
| 38 | 400 | Welding Neck | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 38 | 400 | Welding Neck | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 38 | 400 | Slip On | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 38 | 400 | Slip On | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 38 | 400 | Blind | outer_diameter_mm | 1206.500 | 1205.000 | +1.500 |
| 38 | 400 | Blind | stud_bolt_length_mm | 361.950 | 360.000 | +1.950 |
| 40 | 400 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 40 | 400 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 40 | 400 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 42 | 400 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 42 | 400 | Welding Neck | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 42 | 400 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 42 | 400 | Slip On | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 42 | 400 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 42 | 400 | Blind | stud_bolt_length_mm | 387.350 | 385.000 | +2.350 |
| 44 | 400 | Welding Neck | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 44 | 400 | Slip On | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 44 | 400 | Blind | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 46 | 400 | Welding Neck | outer_diameter_mm | 1441.450 | 1440.000 | +1.450 |
| 46 | 400 | Slip On | outer_diameter_mm | 1441.450 | 1440.000 | +1.450 |
| 46 | 400 | Blind | outer_diameter_mm | 1441.450 | 1440.000 | +1.450 |
| 48 | 400 | Welding Neck | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 48 | 400 | Welding Neck | stud_bolt_length_mm | 444.500 | 440.000 | +4.500 |
| 48 | 400 | Slip On | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 48 | 400 | Slip On | stud_bolt_length_mm | 444.500 | 440.000 | +4.500 |
| 48 | 400 | Blind | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 48 | 400 | Blind | stud_bolt_length_mm | 444.500 | 440.000 | +4.500 |
| 50 | 400 | Welding Neck | outer_diameter_mm | 1568.450 | 1570.000 | -1.550 |
| 50 | 400 | Welding Neck | bore_diameter_mm | 31612.840 | 1244.600 | +30368.240 |
| 50 | 400 | Welding Neck | stud_bolt_length_mm | 469.900 | 465.000 | +4.900 |
| 50 | 400 | Slip On | outer_diameter_mm | 1568.450 | 1570.000 | -1.550 |
| 50 | 400 | Slip On | bore_diameter_mm | 31612.840 | 1244.600 | +30368.240 |
| 50 | 400 | Slip On | stud_bolt_length_mm | 469.900 | 465.000 | +4.900 |
| 50 | 400 | Blind | outer_diameter_mm | 1568.450 | 1570.000 | -1.550 |
| 50 | 400 | Blind | bore_diameter_mm | 31612.840 | 1244.600 | +30368.240 |
| 50 | 400 | Blind | stud_bolt_length_mm | 469.900 | 465.000 | +4.900 |
| 52 | 400 | Welding Neck | bore_diameter_mm | 32903.160 | 1295.400 | +31607.760 |
| 52 | 400 | Welding Neck | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 52 | 400 | Slip On | bore_diameter_mm | 32903.160 | 1295.400 | +31607.760 |
| 52 | 400 | Slip On | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 52 | 400 | Blind | bore_diameter_mm | 32903.160 | 1295.400 | +31607.760 |
| 52 | 400 | Blind | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 54 | 400 | Welding Neck | outer_diameter_mm | 1701.800 | 1700.000 | +1.800 |
| 54 | 400 | Welding Neck | bore_diameter_mm | 34193.480 | 1346.200 | +32847.280 |
| 54 | 400 | Welding Neck | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 54 | 400 | Slip On | outer_diameter_mm | 1701.800 | 1700.000 | +1.800 |
| 54 | 400 | Slip On | bore_diameter_mm | 34193.480 | 1346.200 | +32847.280 |
| 54 | 400 | Slip On | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 54 | 400 | Blind | outer_diameter_mm | 1701.800 | 1700.000 | +1.800 |
| 54 | 400 | Blind | bore_diameter_mm | 34193.480 | 1346.200 | +32847.280 |
| 54 | 400 | Blind | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 56 | 400 | Welding Neck | outer_diameter_mm | 1752.600 | 1755.000 | -2.400 |
| 56 | 400 | Welding Neck | bore_diameter_mm | 35483.800 | 1397.000 | +34086.800 |
| 56 | 400 | Welding Neck | stud_bolt_length_mm | 514.350 | 510.000 | +4.350 |
| 56 | 400 | Slip On | outer_diameter_mm | 1752.600 | 1755.000 | -2.400 |
| 56 | 400 | Slip On | bore_diameter_mm | 35483.800 | 1397.000 | +34086.800 |
| 56 | 400 | Slip On | stud_bolt_length_mm | 514.350 | 510.000 | +4.350 |
| 56 | 400 | Blind | outer_diameter_mm | 1752.600 | 1755.000 | -2.400 |
| 56 | 400 | Blind | bore_diameter_mm | 35483.800 | 1397.000 | +34086.800 |
| 56 | 400 | Blind | stud_bolt_length_mm | 514.350 | 510.000 | +4.350 |
| 58 | 400 | Welding Neck | outer_diameter_mm | 1803.400 | 1805.000 | -1.600 |
| 58 | 400 | Welding Neck | bore_diameter_mm | 36774.120 | 1447.800 | +35326.320 |
| 58 | 400 | Welding Neck | stud_bolt_length_mm | 520.700 | 515.000 | +5.700 |
| 58 | 400 | Slip On | outer_diameter_mm | 1803.400 | 1805.000 | -1.600 |
| 58 | 400 | Slip On | bore_diameter_mm | 36774.120 | 1447.800 | +35326.320 |
| 58 | 400 | Slip On | stud_bolt_length_mm | 520.700 | 515.000 | +5.700 |
| 58 | 400 | Blind | outer_diameter_mm | 1803.400 | 1805.000 | -1.600 |
| 58 | 400 | Blind | bore_diameter_mm | 36774.120 | 1447.800 | +35326.320 |
| 58 | 400 | Blind | stud_bolt_length_mm | 520.700 | 515.000 | +5.700 |
| 60 | 400 | Welding Neck | bore_diameter_mm | 38064.440 | 1498.600 | +36565.840 |
| 60 | 400 | Welding Neck | stud_bolt_length_mm | 539.750 | 535.000 | +4.750 |
| 60 | 400 | Slip On | bore_diameter_mm | 38064.440 | 1498.600 | +36565.840 |
| 60 | 400 | Slip On | stud_bolt_length_mm | 539.750 | 535.000 | +4.750 |
| 60 | 400 | Blind | bore_diameter_mm | 38064.440 | 1498.600 | +36565.840 |
| 60 | 400 | Blind | stud_bolt_length_mm | 539.750 | 535.000 | +4.750 |
| 26 | 600 | Welding Neck | bore_diameter_mm | 16129.000 | 635.000 | +15494.000 |
| 26 | 600 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 26 | 600 | Welding Neck | stud_bolt_length_mm | 374.650 | 370.000 | +4.650 |
| 26 | 600 | Welding Neck | outer_diameter_mm | 890.000 | 1015.000 | -125.000 |
| 26 | 600 | Welding Neck | thickness_mm | 111.200 | 108.000 | +3.200 |
| 26 | 600 | Welding Neck | hub_diameter_mm | 698.000 | 748.000 | -50.000 |
| 26 | 600 | Welding Neck | length_thru_hub_mm | 111.200 | 222.000 | -110.800 |
| 26 | 600 | Welding Neck | rf_diameter_mm | 727.000 | 749.000 | -22.000 |
| 26 | 600 | Welding Neck | pcd_mm | 806.400 | 914.400 | -108.000 |
| 26 | 600 | Welding Neck | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 26 | 600 | Welding Neck | stud_bolt_length_mm | 345.000 | 370.000 | -25.000 |
| 26 | 600 | Welding Neck | weight_kg | 249.000 | 426.000 | -177.000 |
| 26 | 600 | Welding Neck | outer_diameter_mm | 889.000 | 1015.000 | -126.000 |
| 26 | 600 | Welding Neck | thickness_mm | 111.252 | 108.000 | +3.252 |
| 26 | 600 | Welding Neck | hub_diameter_mm | 698.500 | 748.000 | -49.500 |
| 26 | 600 | Welding Neck | length_thru_hub_mm | 111.252 | 222.000 | -110.748 |
| 26 | 600 | Welding Neck | bore_diameter_mm | 16129.000 | 635.000 | +15494.000 |
| 26 | 600 | Welding Neck | rf_diameter_mm | 726.948 | 749.000 | -22.052 |
| 26 | 600 | Welding Neck | pcd_mm | 806.450 | 914.400 | -107.950 |
| 26 | 600 | Welding Neck | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 26 | 600 | Welding Neck | stud_bolt_length_mm | 342.900 | 370.000 | -27.100 |
| 26 | 600 | Welding Neck | weight_kg | 249.000 | 426.000 | -177.000 |
| 26 | 600 | Slip On | bore_diameter_mm | 16129.000 | 635.000 | +15494.000 |
| 26 | 600 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 26 | 600 | Slip On | stud_bolt_length_mm | 374.650 | 370.000 | +4.650 |
| 26 | 600 | Slip On | outer_diameter_mm | 890.000 | 1015.000 | -125.000 |
| 26 | 600 | Slip On | thickness_mm | 111.200 | 108.000 | +3.200 |
| 26 | 600 | Slip On | hub_diameter_mm | 698.000 | 748.000 | -50.000 |
| 26 | 600 | Slip On | length_thru_hub_mm | 111.200 | 108.000 | +3.200 |
| 26 | 600 | Slip On | rf_diameter_mm | 727.000 | 749.000 | -22.000 |
| 26 | 600 | Slip On | pcd_mm | 806.400 | 914.400 | -108.000 |
| 26 | 600 | Slip On | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 26 | 600 | Slip On | stud_bolt_length_mm | 345.000 | 370.000 | -25.000 |
| 26 | 600 | Slip On | outer_diameter_mm | 889.000 | 1015.000 | -126.000 |
| 26 | 600 | Slip On | thickness_mm | 111.252 | 108.000 | +3.252 |
| 26 | 600 | Slip On | hub_diameter_mm | 698.500 | 748.000 | -49.500 |
| 26 | 600 | Slip On | length_thru_hub_mm | 111.252 | 108.000 | +3.252 |
| 26 | 600 | Slip On | bore_diameter_mm | 16129.000 | 635.000 | +15494.000 |
| 26 | 600 | Slip On | rf_diameter_mm | 726.948 | 749.000 | -22.052 |
| 26 | 600 | Slip On | pcd_mm | 806.450 | 914.400 | -107.950 |
| 26 | 600 | Slip On | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 26 | 600 | Slip On | stud_bolt_length_mm | 342.900 | 370.000 | -27.100 |
| 26 | 600 | Blind | bore_diameter_mm | 16129.000 | 635.000 | +15494.000 |
| 26 | 600 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 26 | 600 | Blind | stud_bolt_length_mm | 374.650 | 370.000 | +4.650 |
| 26 | 600 | Blind | outer_diameter_mm | 890.000 | 1015.000 | -125.000 |
| 26 | 600 | Blind | thickness_mm | 111.300 | 125.500 | -14.200 |
| 26 | 600 | Blind | hub_diameter_mm | 698.000 | 748.000 | -50.000 |
| 26 | 600 | Blind | length_thru_hub_mm | 111.300 | 125.500 | -14.200 |
| 26 | 600 | Blind | rf_diameter_mm | 727.000 | 749.000 | -22.000 |
| 26 | 600 | Blind | pcd_mm | 806.400 | 914.400 | -108.000 |
| 26 | 600 | Blind | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 26 | 600 | Blind | stud_bolt_length_mm | 345.000 | 370.000 | -25.000 |
| 26 | 600 | Blind | weight_kg | 541.000 | 797.000 | -256.000 |
| 26 | 600 | Blind | outer_diameter_mm | 889.000 | 1015.000 | -126.000 |
| 26 | 600 | Blind | thickness_mm | 111.252 | 125.500 | -14.248 |
| 26 | 600 | Blind | hub_diameter_mm | 698.500 | 748.000 | -49.500 |
| 26 | 600 | Blind | length_thru_hub_mm | 111.252 | 125.500 | -14.248 |
| 26 | 600 | Blind | bore_diameter_mm | 16129.000 | 635.000 | +15494.000 |
| 26 | 600 | Blind | rf_diameter_mm | 726.948 | 749.000 | -22.052 |
| 26 | 600 | Blind | pcd_mm | 806.450 | 914.400 | -107.950 |
| 26 | 600 | Blind | bolt_hole_diameter_mm | 0.000 | 2.000 | -2.000 |
| 26 | 600 | Blind | stud_bolt_length_mm | 342.900 | 370.000 | -27.100 |
| 26 | 600 | Blind | weight_kg | 541.000 | 797.000 | -256.000 |
| 28 | 600 | Welding Neck | outer_diameter_mm | 1073.150 | 1075.000 | -1.850 |
| 28 | 600 | Welding Neck | bore_diameter_mm | 17419.320 | 685.800 | +16733.520 |
| 28 | 600 | Welding Neck | outer_diameter_mm | 950.000 | 1075.000 | -125.000 |
| 28 | 600 | Welding Neck | thickness_mm | 115.900 | 111.200 | +4.700 |
| 28 | 600 | Welding Neck | hub_diameter_mm | 752.000 | 803.000 | -51.000 |
| 28 | 600 | Welding Neck | length_thru_hub_mm | 115.900 | 235.000 | -119.100 |
| 28 | 600 | Welding Neck | rf_diameter_mm | 784.000 | 800.000 | -16.000 |
| 28 | 600 | Welding Neck | pcd_mm | 863.600 | 965.200 | -101.600 |
| 28 | 600 | Welding Neck | stud_bolt_length_mm | 360.000 | 380.000 | -20.000 |
| 28 | 600 | Welding Neck | weight_kg | 294.000 | 480.000 | -186.000 |
| 28 | 600 | Welding Neck | outer_diameter_mm | 952.500 | 1075.000 | -122.500 |
| 28 | 600 | Welding Neck | thickness_mm | 115.824 | 111.200 | +4.624 |
| 28 | 600 | Welding Neck | hub_diameter_mm | 752.348 | 803.000 | -50.652 |
| 28 | 600 | Welding Neck | length_thru_hub_mm | 115.824 | 235.000 | -119.176 |
| 28 | 600 | Welding Neck | bore_diameter_mm | 17419.320 | 685.800 | +16733.520 |
| 28 | 600 | Welding Neck | rf_diameter_mm | 784.352 | 800.000 | -15.648 |
| 28 | 600 | Welding Neck | pcd_mm | 863.600 | 965.200 | -101.600 |
| 28 | 600 | Welding Neck | stud_bolt_length_mm | 361.950 | 380.000 | -18.050 |
| 28 | 600 | Welding Neck | weight_kg | 294.000 | 480.000 | -186.000 |
| 28 | 600 | Slip On | outer_diameter_mm | 1073.150 | 1075.000 | -1.850 |
| 28 | 600 | Slip On | bore_diameter_mm | 17419.320 | 685.800 | +16733.520 |
| 28 | 600 | Slip On | outer_diameter_mm | 950.000 | 1075.000 | -125.000 |
| 28 | 600 | Slip On | thickness_mm | 115.900 | 111.200 | +4.700 |
| 28 | 600 | Slip On | hub_diameter_mm | 752.000 | 803.000 | -51.000 |
| 28 | 600 | Slip On | length_thru_hub_mm | 115.900 | 111.200 | +4.700 |
| 28 | 600 | Slip On | rf_diameter_mm | 784.000 | 800.000 | -16.000 |
| 28 | 600 | Slip On | pcd_mm | 863.600 | 965.200 | -101.600 |
| 28 | 600 | Slip On | stud_bolt_length_mm | 360.000 | 380.000 | -20.000 |
| 28 | 600 | Slip On | outer_diameter_mm | 952.500 | 1075.000 | -122.500 |
| 28 | 600 | Slip On | thickness_mm | 115.824 | 111.200 | +4.624 |
| 28 | 600 | Slip On | hub_diameter_mm | 752.348 | 803.000 | -50.652 |
| 28 | 600 | Slip On | length_thru_hub_mm | 115.824 | 111.200 | +4.624 |
| 28 | 600 | Slip On | bore_diameter_mm | 17419.320 | 685.800 | +16733.520 |
| 28 | 600 | Slip On | rf_diameter_mm | 784.352 | 800.000 | -15.648 |
| 28 | 600 | Slip On | pcd_mm | 863.600 | 965.200 | -101.600 |
| 28 | 600 | Slip On | stud_bolt_length_mm | 361.950 | 380.000 | -18.050 |
| 28 | 600 | Blind | outer_diameter_mm | 1073.150 | 1075.000 | -1.850 |
| 28 | 600 | Blind | bore_diameter_mm | 17419.320 | 685.800 | +16733.520 |
| 28 | 600 | Blind | outer_diameter_mm | 950.000 | 1075.000 | -125.000 |
| 28 | 600 | Blind | thickness_mm | 115.900 | 131.800 | -15.900 |
| 28 | 600 | Blind | hub_diameter_mm | 752.000 | 803.000 | -51.000 |
| 28 | 600 | Blind | length_thru_hub_mm | 115.900 | 131.800 | -15.900 |
| 28 | 600 | Blind | rf_diameter_mm | 784.000 | 800.000 | -16.000 |
| 28 | 600 | Blind | pcd_mm | 863.600 | 965.200 | -101.600 |
| 28 | 600 | Blind | stud_bolt_length_mm | 360.000 | 380.000 | -20.000 |
| 28 | 600 | Blind | weight_kg | 647.000 | 934.000 | -287.000 |
| 28 | 600 | Blind | outer_diameter_mm | 952.500 | 1075.000 | -122.500 |
| 28 | 600 | Blind | thickness_mm | 115.824 | 131.800 | -15.976 |
| 28 | 600 | Blind | hub_diameter_mm | 752.348 | 803.000 | -50.652 |
| 28 | 600 | Blind | length_thru_hub_mm | 115.824 | 131.800 | -15.976 |
| 28 | 600 | Blind | bore_diameter_mm | 17419.320 | 685.800 | +16733.520 |
| 28 | 600 | Blind | rf_diameter_mm | 784.352 | 800.000 | -15.648 |
| 28 | 600 | Blind | pcd_mm | 863.600 | 965.200 | -101.600 |
| 28 | 600 | Blind | stud_bolt_length_mm | 361.950 | 380.000 | -18.050 |
| 28 | 600 | Blind | weight_kg | 647.000 | 934.000 | -287.000 |
| 30 | 600 | Welding Neck | bore_diameter_mm | 18709.640 | 736.600 | +17973.040 |
| 30 | 600 | Welding Neck | stud_bolt_length_mm | 400.050 | 395.000 | +5.050 |
| 30 | 600 | Welding Neck | outer_diameter_mm | 1020.000 | 1130.000 | -110.000 |
| 30 | 600 | Welding Neck | thickness_mm | 125.500 | 114.300 | +11.200 |
| 30 | 600 | Welding Neck | hub_diameter_mm | 806.000 | 862.000 | -56.000 |
| 30 | 600 | Welding Neck | length_thru_hub_mm | 125.500 | 248.000 | -122.500 |
| 30 | 600 | Welding Neck | rf_diameter_mm | 841.000 | 857.000 | -16.000 |
| 30 | 600 | Welding Neck | pcd_mm | 927.100 | 1022.400 | -95.300 |
| 30 | 600 | Welding Neck | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 30 | 600 | Welding Neck | stud_bolt_length_mm | 385.000 | 395.000 | -10.000 |
| 30 | 600 | Welding Neck | weight_kg | 367.000 | 548.000 | -181.000 |
| 30 | 600 | Welding Neck | outer_diameter_mm | 1022.350 | 1130.000 | -107.650 |
| 30 | 600 | Welding Neck | thickness_mm | 125.476 | 114.300 | +11.176 |
| 30 | 600 | Welding Neck | hub_diameter_mm | 806.450 | 862.000 | -55.550 |
| 30 | 600 | Welding Neck | length_thru_hub_mm | 125.476 | 248.000 | -122.524 |
| 30 | 600 | Welding Neck | bore_diameter_mm | 18709.640 | 736.600 | +17973.040 |
| 30 | 600 | Welding Neck | rf_diameter_mm | 841.248 | 857.000 | -15.752 |
| 30 | 600 | Welding Neck | pcd_mm | 927.100 | 1022.400 | -95.300 |
| 30 | 600 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 30 | 600 | Welding Neck | stud_bolt_length_mm | 387.350 | 395.000 | -7.650 |
| 30 | 600 | Welding Neck | weight_kg | 367.000 | 548.000 | -181.000 |
| 30 | 600 | Slip On | bore_diameter_mm | 18709.640 | 736.600 | +17973.040 |
| 30 | 600 | Slip On | stud_bolt_length_mm | 400.050 | 395.000 | +5.050 |
| 30 | 600 | Slip On | outer_diameter_mm | 1020.000 | 1130.000 | -110.000 |
| 30 | 600 | Slip On | thickness_mm | 125.500 | 114.300 | +11.200 |
| 30 | 600 | Slip On | hub_diameter_mm | 806.000 | 862.000 | -56.000 |
| 30 | 600 | Slip On | length_thru_hub_mm | 125.500 | 114.300 | +11.200 |
| 30 | 600 | Slip On | rf_diameter_mm | 841.000 | 857.000 | -16.000 |
| 30 | 600 | Slip On | pcd_mm | 927.100 | 1022.400 | -95.300 |
| 30 | 600 | Slip On | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 30 | 600 | Slip On | stud_bolt_length_mm | 385.000 | 395.000 | -10.000 |
| 30 | 600 | Slip On | outer_diameter_mm | 1022.350 | 1130.000 | -107.650 |
| 30 | 600 | Slip On | thickness_mm | 125.476 | 114.300 | +11.176 |
| 30 | 600 | Slip On | hub_diameter_mm | 806.450 | 862.000 | -55.550 |
| 30 | 600 | Slip On | length_thru_hub_mm | 125.476 | 114.300 | +11.176 |
| 30 | 600 | Slip On | bore_diameter_mm | 18709.640 | 736.600 | +17973.040 |
| 30 | 600 | Slip On | rf_diameter_mm | 841.248 | 857.000 | -15.752 |
| 30 | 600 | Slip On | pcd_mm | 927.100 | 1022.400 | -95.300 |
| 30 | 600 | Slip On | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 30 | 600 | Slip On | stud_bolt_length_mm | 387.350 | 395.000 | -7.650 |
| 30 | 600 | Blind | bore_diameter_mm | 18709.640 | 736.600 | +17973.040 |
| 30 | 600 | Blind | stud_bolt_length_mm | 400.050 | 395.000 | +5.050 |
| 30 | 600 | Blind | outer_diameter_mm | 1020.000 | 1130.000 | -110.000 |
| 30 | 600 | Blind | thickness_mm | 127.000 | 139.700 | -12.700 |
| 30 | 600 | Blind | hub_diameter_mm | 806.000 | 862.000 | -56.000 |
| 30 | 600 | Blind | length_thru_hub_mm | 127.000 | 139.700 | -12.700 |
| 30 | 600 | Blind | rf_diameter_mm | 841.000 | 857.000 | -16.000 |
| 30 | 600 | Blind | pcd_mm | 927.100 | 1022.400 | -95.300 |
| 30 | 600 | Blind | bolt_hole_diameter_mm | 2.000 | 0.000 | +2.000 |
| 30 | 600 | Blind | stud_bolt_length_mm | 385.000 | 395.000 | -10.000 |
| 30 | 600 | Blind | weight_kg | 817.000 | 1099.000 | -282.000 |
| 30 | 600 | Blind | outer_diameter_mm | 1022.350 | 1130.000 | -107.650 |
| 30 | 600 | Blind | thickness_mm | 127.000 | 139.700 | -12.700 |
| 30 | 600 | Blind | hub_diameter_mm | 806.450 | 862.000 | -55.550 |
| 30 | 600 | Blind | length_thru_hub_mm | 127.000 | 139.700 | -12.700 |
| 30 | 600 | Blind | bore_diameter_mm | 18709.640 | 736.600 | +17973.040 |
| 30 | 600 | Blind | rf_diameter_mm | 841.248 | 857.000 | -15.752 |
| 30 | 600 | Blind | pcd_mm | 927.100 | 1022.400 | -95.300 |
| 30 | 600 | Blind | bolt_hole_diameter_mm | 50.800 | 0.000 | +50.800 |
| 30 | 600 | Blind | stud_bolt_length_mm | 387.350 | 395.000 | -7.650 |
| 30 | 600 | Blind | weight_kg | 817.000 | 1099.000 | -282.000 |
| 32 | 600 | Welding Neck | outer_diameter_mm | 1193.800 | 1195.000 | -1.200 |
| 32 | 600 | Welding Neck | bore_diameter_mm | 19999.960 | 787.400 | +19212.560 |
| 32 | 600 | Welding Neck | stud_bolt_length_mm | 425.450 | 420.000 | +5.450 |
| 32 | 600 | Welding Neck | outer_diameter_mm | 1085.000 | 1195.000 | -110.000 |
| 32 | 600 | Welding Neck | thickness_mm | 130.200 | 117.500 | +12.700 |
| 32 | 600 | Welding Neck | hub_diameter_mm | 860.000 | 918.000 | -58.000 |
| 32 | 600 | Welding Neck | length_thru_hub_mm | 130.200 | 260.000 | -129.800 |
| 32 | 600 | Welding Neck | rf_diameter_mm | 895.000 | 914.000 | -19.000 |
| 32 | 600 | Welding Neck | pcd_mm | 984.200 | 1079.500 | -95.300 |
| 32 | 600 | Welding Neck | stud_bolt_length_mm | 405.000 | 420.000 | -15.000 |
| 32 | 600 | Welding Neck | weight_kg | 430.000 | 623.000 | -193.000 |
| 32 | 600 | Welding Neck | outer_diameter_mm | 1085.850 | 1195.000 | -109.150 |
| 32 | 600 | Welding Neck | thickness_mm | 130.048 | 117.500 | +12.548 |
| 32 | 600 | Welding Neck | hub_diameter_mm | 860.552 | 918.000 | -57.448 |
| 32 | 600 | Welding Neck | length_thru_hub_mm | 130.048 | 260.000 | -129.952 |
| 32 | 600 | Welding Neck | bore_diameter_mm | 19999.960 | 787.400 | +19212.560 |
| 32 | 600 | Welding Neck | rf_diameter_mm | 895.350 | 914.000 | -18.650 |
| 32 | 600 | Welding Neck | pcd_mm | 984.250 | 1079.500 | -95.250 |
| 32 | 600 | Welding Neck | stud_bolt_length_mm | 406.400 | 420.000 | -13.600 |
| 32 | 600 | Welding Neck | weight_kg | 430.000 | 623.000 | -193.000 |
| 32 | 600 | Slip On | outer_diameter_mm | 1193.800 | 1195.000 | -1.200 |
| 32 | 600 | Slip On | bore_diameter_mm | 19999.960 | 787.400 | +19212.560 |
| 32 | 600 | Slip On | stud_bolt_length_mm | 425.450 | 420.000 | +5.450 |
| 32 | 600 | Slip On | outer_diameter_mm | 1085.000 | 1195.000 | -110.000 |
| 32 | 600 | Slip On | thickness_mm | 130.200 | 117.500 | +12.700 |
| 32 | 600 | Slip On | hub_diameter_mm | 860.000 | 918.000 | -58.000 |
| 32 | 600 | Slip On | length_thru_hub_mm | 130.200 | 117.500 | +12.700 |
| 32 | 600 | Slip On | rf_diameter_mm | 895.000 | 914.000 | -19.000 |
| 32 | 600 | Slip On | pcd_mm | 984.200 | 1079.500 | -95.300 |
| 32 | 600 | Slip On | stud_bolt_length_mm | 405.000 | 420.000 | -15.000 |
| 32 | 600 | Slip On | outer_diameter_mm | 1085.850 | 1195.000 | -109.150 |
| 32 | 600 | Slip On | thickness_mm | 130.048 | 117.500 | +12.548 |
| 32 | 600 | Slip On | hub_diameter_mm | 860.552 | 918.000 | -57.448 |
| 32 | 600 | Slip On | length_thru_hub_mm | 130.048 | 117.500 | +12.548 |
| 32 | 600 | Slip On | bore_diameter_mm | 19999.960 | 787.400 | +19212.560 |
| 32 | 600 | Slip On | rf_diameter_mm | 895.350 | 914.000 | -18.650 |
| 32 | 600 | Slip On | pcd_mm | 984.250 | 1079.500 | -95.250 |
| 32 | 600 | Slip On | stud_bolt_length_mm | 406.400 | 420.000 | -13.600 |
| 32 | 600 | Blind | outer_diameter_mm | 1193.800 | 1195.000 | -1.200 |
| 32 | 600 | Blind | bore_diameter_mm | 19999.960 | 787.400 | +19212.560 |
| 32 | 600 | Blind | stud_bolt_length_mm | 425.450 | 420.000 | +5.450 |
| 32 | 600 | Blind | outer_diameter_mm | 1085.000 | 1195.000 | -110.000 |
| 32 | 600 | Blind | thickness_mm | 134.900 | 147.700 | -12.800 |
| 32 | 600 | Blind | hub_diameter_mm | 860.000 | 918.000 | -58.000 |
| 32 | 600 | Blind | length_thru_hub_mm | 134.900 | 147.700 | -12.800 |
| 32 | 600 | Blind | rf_diameter_mm | 895.000 | 914.000 | -19.000 |
| 32 | 600 | Blind | pcd_mm | 984.200 | 1079.500 | -95.300 |
| 32 | 600 | Blind | stud_bolt_length_mm | 405.000 | 420.000 | -15.000 |
| 32 | 600 | Blind | weight_kg | 979.000 | 1295.000 | -316.000 |
| 32 | 600 | Blind | outer_diameter_mm | 1085.850 | 1195.000 | -109.150 |
| 32 | 600 | Blind | thickness_mm | 134.874 | 147.700 | -12.826 |
| 32 | 600 | Blind | hub_diameter_mm | 860.552 | 918.000 | -57.448 |
| 32 | 600 | Blind | length_thru_hub_mm | 134.874 | 147.700 | -12.826 |
| 32 | 600 | Blind | bore_diameter_mm | 19999.960 | 787.400 | +19212.560 |
| 32 | 600 | Blind | rf_diameter_mm | 895.350 | 914.000 | -18.650 |
| 32 | 600 | Blind | pcd_mm | 984.250 | 1079.500 | -95.250 |
| 32 | 600 | Blind | stud_bolt_length_mm | 406.400 | 420.000 | -13.600 |
| 32 | 600 | Blind | weight_kg | 979.000 | 1295.000 | -316.000 |
| 34 | 600 | Welding Neck | bore_diameter_mm | 21290.280 | 838.200 | +20452.080 |
| 34 | 600 | Welding Neck | outer_diameter_mm | 1160.000 | 1245.000 | -85.000 |
| 34 | 600 | Welding Neck | thickness_mm | 141.300 | 120.700 | +20.600 |
| 34 | 600 | Welding Neck | hub_diameter_mm | 914.000 | 973.000 | -59.000 |
| 34 | 600 | Welding Neck | length_thru_hub_mm | 141.300 | 270.000 | -128.700 |
| 34 | 600 | Welding Neck | rf_diameter_mm | 953.000 | 965.000 | -12.000 |
| 34 | 600 | Welding Neck | pcd_mm | 1054.100 | 1130.300 | -76.200 |
| 34 | 600 | Welding Neck | stud_bolt_length_mm | 440.000 | 425.000 | +15.000 |
| 34 | 600 | Welding Neck | weight_kg | 546.000 | 698.000 | -152.000 |
| 34 | 600 | Welding Neck | outer_diameter_mm | 1162.050 | 1245.000 | -82.950 |
| 34 | 600 | Welding Neck | thickness_mm | 141.224 | 120.700 | +20.524 |
| 34 | 600 | Welding Neck | hub_diameter_mm | 914.400 | 973.000 | -58.600 |
| 34 | 600 | Welding Neck | length_thru_hub_mm | 141.224 | 270.000 | -128.776 |
| 34 | 600 | Welding Neck | bore_diameter_mm | 21290.280 | 838.200 | +20452.080 |
| 34 | 600 | Welding Neck | rf_diameter_mm | 952.500 | 965.000 | -12.500 |
| 34 | 600 | Welding Neck | pcd_mm | 1054.100 | 1130.300 | -76.200 |
| 34 | 600 | Welding Neck | stud_bolt_length_mm | 438.150 | 425.000 | +13.150 |
| 34 | 600 | Welding Neck | weight_kg | 546.000 | 698.000 | -152.000 |
| 34 | 600 | Slip On | bore_diameter_mm | 21290.280 | 838.200 | +20452.080 |
| 34 | 600 | Slip On | outer_diameter_mm | 1160.000 | 1245.000 | -85.000 |
| 34 | 600 | Slip On | thickness_mm | 141.300 | 120.700 | +20.600 |
| 34 | 600 | Slip On | hub_diameter_mm | 914.000 | 973.000 | -59.000 |
| 34 | 600 | Slip On | length_thru_hub_mm | 141.300 | 120.700 | +20.600 |
| 34 | 600 | Slip On | rf_diameter_mm | 953.000 | 965.000 | -12.000 |
| 34 | 600 | Slip On | pcd_mm | 1054.100 | 1130.300 | -76.200 |
| 34 | 600 | Slip On | stud_bolt_length_mm | 440.000 | 425.000 | +15.000 |
| 34 | 600 | Slip On | outer_diameter_mm | 1162.050 | 1245.000 | -82.950 |
| 34 | 600 | Slip On | thickness_mm | 141.224 | 120.700 | +20.524 |
| 34 | 600 | Slip On | hub_diameter_mm | 914.400 | 973.000 | -58.600 |
| 34 | 600 | Slip On | length_thru_hub_mm | 141.224 | 120.700 | +20.524 |
| 34 | 600 | Slip On | bore_diameter_mm | 21290.280 | 838.200 | +20452.080 |
| 34 | 600 | Slip On | rf_diameter_mm | 952.500 | 965.000 | -12.500 |
| 34 | 600 | Slip On | pcd_mm | 1054.100 | 1130.300 | -76.200 |
| 34 | 600 | Slip On | stud_bolt_length_mm | 438.150 | 425.000 | +13.150 |
| 34 | 600 | Blind | bore_diameter_mm | 21290.280 | 838.200 | +20452.080 |
| 34 | 600 | Blind | outer_diameter_mm | 1160.000 | 1245.000 | -85.000 |
| 34 | 600 | Blind | thickness_mm | 144.200 | 154.000 | -9.800 |
| 34 | 600 | Blind | hub_diameter_mm | 914.000 | 973.000 | -59.000 |
| 34 | 600 | Blind | length_thru_hub_mm | 144.200 | 154.000 | -9.800 |
| 34 | 600 | Blind | rf_diameter_mm | 953.000 | 965.000 | -12.000 |
| 34 | 600 | Blind | pcd_mm | 1054.100 | 1130.300 | -76.200 |
| 34 | 600 | Blind | stud_bolt_length_mm | 440.000 | 425.000 | +15.000 |
| 34 | 600 | Blind | weight_kg | 1199.000 | 1468.000 | -269.000 |
| 34 | 600 | Blind | outer_diameter_mm | 1162.050 | 1245.000 | -82.950 |
| 34 | 600 | Blind | thickness_mm | 144.272 | 154.000 | -9.728 |
| 34 | 600 | Blind | hub_diameter_mm | 914.400 | 973.000 | -58.600 |
| 34 | 600 | Blind | length_thru_hub_mm | 144.272 | 154.000 | -9.728 |
| 34 | 600 | Blind | bore_diameter_mm | 21290.280 | 838.200 | +20452.080 |
| 34 | 600 | Blind | rf_diameter_mm | 952.500 | 965.000 | -12.500 |
| 34 | 600 | Blind | pcd_mm | 1054.100 | 1130.300 | -76.200 |
| 34 | 600 | Blind | stud_bolt_length_mm | 438.150 | 425.000 | +13.150 |
| 34 | 600 | Blind | weight_kg | 1199.000 | 1468.000 | -269.000 |
| 36 | 600 | Welding Neck | bore_diameter_mm | 22580.600 | 889.000 | +21691.600 |
| 36 | 600 | Welding Neck | outer_diameter_mm | 1215.000 | 1315.000 | -100.000 |
| 36 | 600 | Welding Neck | thickness_mm | 146.100 | 123.900 | +22.200 |
| 36 | 600 | Welding Neck | hub_diameter_mm | 968.000 | 1032.000 | -64.000 |
| 36 | 600 | Welding Neck | length_thru_hub_mm | 146.100 | 283.000 | -136.900 |
| 36 | 600 | Welding Neck | rf_diameter_mm | 1010.000 | 1022.000 | -12.000 |
| 36 | 600 | Welding Neck | pcd_mm | 1104.900 | 1193.800 | -88.900 |
| 36 | 600 | Welding Neck | weight_kg | 607.000 | 773.000 | -166.000 |
| 36 | 600 | Welding Neck | outer_diameter_mm | 1212.850 | 1315.000 | -102.150 |
| 36 | 600 | Welding Neck | thickness_mm | 146.050 | 123.900 | +22.150 |
| 36 | 600 | Welding Neck | hub_diameter_mm | 968.248 | 1032.000 | -63.752 |
| 36 | 600 | Welding Neck | length_thru_hub_mm | 146.050 | 283.000 | -136.950 |
| 36 | 600 | Welding Neck | bore_diameter_mm | 22580.600 | 889.000 | +21691.600 |
| 36 | 600 | Welding Neck | rf_diameter_mm | 1009.650 | 1022.000 | -12.350 |
| 36 | 600 | Welding Neck | pcd_mm | 1104.900 | 1193.800 | -88.900 |
| 36 | 600 | Welding Neck | weight_kg | 607.000 | 773.000 | -166.000 |
| 36 | 600 | Slip On | bore_diameter_mm | 22580.600 | 889.000 | +21691.600 |
| 36 | 600 | Slip On | outer_diameter_mm | 1215.000 | 1315.000 | -100.000 |
| 36 | 600 | Slip On | thickness_mm | 146.100 | 123.900 | +22.200 |
| 36 | 600 | Slip On | hub_diameter_mm | 968.000 | 1032.000 | -64.000 |
| 36 | 600 | Slip On | length_thru_hub_mm | 146.100 | 123.900 | +22.200 |
| 36 | 600 | Slip On | rf_diameter_mm | 1010.000 | 1022.000 | -12.000 |
| 36 | 600 | Slip On | pcd_mm | 1104.900 | 1193.800 | -88.900 |
| 36 | 600 | Slip On | outer_diameter_mm | 1212.850 | 1315.000 | -102.150 |
| 36 | 600 | Slip On | thickness_mm | 146.050 | 123.900 | +22.150 |
| 36 | 600 | Slip On | hub_diameter_mm | 968.248 | 1032.000 | -63.752 |
| 36 | 600 | Slip On | length_thru_hub_mm | 146.050 | 123.900 | +22.150 |
| 36 | 600 | Slip On | bore_diameter_mm | 22580.600 | 889.000 | +21691.600 |
| 36 | 600 | Slip On | rf_diameter_mm | 1009.650 | 1022.000 | -12.350 |
| 36 | 600 | Slip On | pcd_mm | 1104.900 | 1193.800 | -88.900 |
| 36 | 600 | Blind | bore_diameter_mm | 22580.600 | 889.000 | +21691.600 |
| 36 | 600 | Blind | outer_diameter_mm | 1215.000 | 1315.000 | -100.000 |
| 36 | 600 | Blind | thickness_mm | 150.900 | 162.000 | -11.100 |
| 36 | 600 | Blind | hub_diameter_mm | 968.000 | 1032.000 | -64.000 |
| 36 | 600 | Blind | length_thru_hub_mm | 150.900 | 162.000 | -11.100 |
| 36 | 600 | Blind | rf_diameter_mm | 1010.000 | 1022.000 | -12.000 |
| 36 | 600 | Blind | pcd_mm | 1104.900 | 1193.800 | -88.900 |
| 36 | 600 | Blind | weight_kg | 1366.000 | 1724.000 | -358.000 |
| 36 | 600 | Blind | outer_diameter_mm | 1212.850 | 1315.000 | -102.150 |
| 36 | 600 | Blind | thickness_mm | 150.876 | 162.000 | -11.124 |
| 36 | 600 | Blind | hub_diameter_mm | 968.248 | 1032.000 | -63.752 |
| 36 | 600 | Blind | length_thru_hub_mm | 150.876 | 162.000 | -11.124 |
| 36 | 600 | Blind | bore_diameter_mm | 22580.600 | 889.000 | +21691.600 |
| 36 | 600 | Blind | rf_diameter_mm | 1009.650 | 1022.000 | -12.350 |
| 36 | 600 | Blind | pcd_mm | 1104.900 | 1193.800 | -88.900 |
| 36 | 600 | Blind | weight_kg | 1366.000 | 1724.000 | -358.000 |
| 38 | 600 | Welding Neck | bore_diameter_mm | 23870.920 | 939.800 | +22931.120 |
| 38 | 600 | Welding Neck | stud_bolt_length_mm | 469.900 | 465.000 | +4.900 |
| 38 | 600 | Welding Neck | length_thru_hub_mm | 152.400 | 254.000 | -101.600 |
| 38 | 600 | Welding Neck | length_thru_hub_mm | 152.400 | 254.000 | -101.600 |
| 38 | 600 | Welding Neck | stud_bolt_length_mm | 463.550 | 465.000 | -1.450 |
| 38 | 600 | Slip On | bore_diameter_mm | 23870.920 | 939.800 | +22931.120 |
| 38 | 600 | Slip On | stud_bolt_length_mm | 469.900 | 465.000 | +4.900 |
| 38 | 600 | Slip On | stud_bolt_length_mm | 463.550 | 465.000 | -1.450 |
| 38 | 600 | Blind | bore_diameter_mm | 23870.920 | 939.800 | +22931.120 |
| 38 | 600 | Blind | stud_bolt_length_mm | 469.900 | 465.000 | +4.900 |
| 38 | 600 | Blind | stud_bolt_length_mm | 463.550 | 465.000 | -1.450 |
| 40 | 600 | Welding Neck | bore_diameter_mm | 25161.240 | 990.600 | +24170.640 |
| 40 | 600 | Welding Neck | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 40 | 600 | Welding Neck | length_thru_hub_mm | 158.800 | 264.000 | -105.200 |
| 40 | 600 | Welding Neck | length_thru_hub_mm | 158.750 | 264.000 | -105.250 |
| 40 | 600 | Welding Neck | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 40 | 600 | Slip On | bore_diameter_mm | 25161.240 | 990.600 | +24170.640 |
| 40 | 600 | Slip On | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 40 | 600 | Slip On | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 40 | 600 | Blind | bore_diameter_mm | 25161.240 | 990.600 | +24170.640 |
| 40 | 600 | Blind | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 40 | 600 | Blind | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 42 | 600 | Welding Neck | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 42 | 600 | Welding Neck | bore_diameter_mm | 26451.560 | 1041.400 | +25410.160 |
| 42 | 600 | Welding Neck | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 42 | 600 | Welding Neck | length_thru_hub_mm | 168.300 | 279.000 | -110.700 |
| 42 | 600 | Welding Neck | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 42 | 600 | Welding Neck | length_thru_hub_mm | 168.148 | 279.000 | -110.852 |
| 42 | 600 | Welding Neck | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 42 | 600 | Slip On | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 42 | 600 | Slip On | bore_diameter_mm | 26451.560 | 1041.400 | +25410.160 |
| 42 | 600 | Slip On | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 42 | 600 | Slip On | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 42 | 600 | Slip On | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 42 | 600 | Blind | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 42 | 600 | Blind | bore_diameter_mm | 26451.560 | 1041.400 | +25410.160 |
| 42 | 600 | Blind | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 42 | 600 | Blind | outer_diameter_mm | 1403.350 | 1405.000 | -1.650 |
| 42 | 600 | Blind | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 44 | 600 | Welding Neck | bore_diameter_mm | 27741.880 | 1092.200 | +26649.680 |
| 44 | 600 | Welding Neck | stud_bolt_length_mm | 520.700 | 515.000 | +5.700 |
| 44 | 600 | Welding Neck | length_thru_hub_mm | 173.100 | 289.000 | -115.900 |
| 44 | 600 | Welding Neck | length_thru_hub_mm | 172.974 | 289.000 | -116.026 |
| 44 | 600 | Slip On | bore_diameter_mm | 27741.880 | 1092.200 | +26649.680 |
| 44 | 600 | Slip On | stud_bolt_length_mm | 520.700 | 515.000 | +5.700 |
| 44 | 600 | Blind | bore_diameter_mm | 27741.880 | 1092.200 | +26649.680 |
| 44 | 600 | Blind | stud_bolt_length_mm | 520.700 | 515.000 | +5.700 |
| 46 | 600 | Welding Neck | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 46 | 600 | Welding Neck | bore_diameter_mm | 29032.200 | 1143.000 | +27889.200 |
| 46 | 600 | Welding Neck | stud_bolt_length_mm | 527.050 | 525.000 | +2.050 |
| 46 | 600 | Welding Neck | length_thru_hub_mm | 179.400 | 300.000 | -120.600 |
| 46 | 600 | Welding Neck | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 46 | 600 | Welding Neck | length_thru_hub_mm | 179.324 | 300.000 | -120.676 |
| 46 | 600 | Welding Neck | stud_bolt_length_mm | 527.050 | 525.000 | +2.050 |
| 46 | 600 | Slip On | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 46 | 600 | Slip On | bore_diameter_mm | 29032.200 | 1143.000 | +27889.200 |
| 46 | 600 | Slip On | stud_bolt_length_mm | 527.050 | 525.000 | +2.050 |
| 46 | 600 | Slip On | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 46 | 600 | Slip On | stud_bolt_length_mm | 527.050 | 525.000 | +2.050 |
| 46 | 600 | Blind | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 46 | 600 | Blind | bore_diameter_mm | 29032.200 | 1143.000 | +27889.200 |
| 46 | 600 | Blind | stud_bolt_length_mm | 527.050 | 525.000 | +2.050 |
| 46 | 600 | Blind | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 46 | 600 | Blind | stud_bolt_length_mm | 527.050 | 525.000 | +2.050 |
| 48 | 600 | Welding Neck | outer_diameter_mm | 1593.850 | 1595.000 | -1.150 |
| 48 | 600 | Welding Neck | bore_diameter_mm | 30322.520 | 1193.800 | +29128.720 |
| 48 | 600 | Welding Neck | length_thru_hub_mm | 189.000 | 316.000 | -127.000 |
| 48 | 600 | Welding Neck | outer_diameter_mm | 1593.850 | 1595.000 | -1.150 |
| 48 | 600 | Welding Neck | length_thru_hub_mm | 188.976 | 316.000 | -127.024 |
| 48 | 600 | Slip On | outer_diameter_mm | 1593.850 | 1595.000 | -1.150 |
| 48 | 600 | Slip On | bore_diameter_mm | 30322.520 | 1193.800 | +29128.720 |
| 48 | 600 | Slip On | outer_diameter_mm | 1593.850 | 1595.000 | -1.150 |
| 48 | 600 | Blind | outer_diameter_mm | 1593.850 | 1595.000 | -1.150 |
| 48 | 600 | Blind | bore_diameter_mm | 30322.520 | 1193.800 | +29128.720 |
| 48 | 600 | Blind | outer_diameter_mm | 1593.850 | 1595.000 | -1.150 |
| 50 | 600 | Welding Neck | bore_diameter_mm | 31612.840 | 1244.600 | +30368.240 |
| 50 | 600 | Welding Neck | length_thru_hub_mm | 196.900 | 329.000 | -132.100 |
| 50 | 600 | Welding Neck | length_thru_hub_mm | 196.850 | 329.000 | -132.150 |
| 50 | 600 | Slip On | bore_diameter_mm | 31612.840 | 1244.600 | +30368.240 |
| 50 | 600 | Blind | bore_diameter_mm | 31612.840 | 1244.600 | +30368.240 |
| 52 | 600 | Welding Neck | bore_diameter_mm | 32903.160 | 1295.400 | +31607.760 |
| 52 | 600 | Welding Neck | stud_bolt_length_mm | 609.600 | 605.000 | +4.600 |
| 52 | 600 | Welding Neck | length_thru_hub_mm | 203.200 | 337.000 | -133.800 |
| 52 | 600 | Welding Neck | length_thru_hub_mm | 203.200 | 337.000 | -133.800 |
| 52 | 600 | Welding Neck | stud_bolt_length_mm | 603.250 | 605.000 | -1.750 |
| 52 | 600 | Slip On | bore_diameter_mm | 32903.160 | 1295.400 | +31607.760 |
| 52 | 600 | Slip On | stud_bolt_length_mm | 609.600 | 605.000 | +4.600 |
| 52 | 600 | Slip On | stud_bolt_length_mm | 603.250 | 605.000 | -1.750 |
| 52 | 600 | Blind | bore_diameter_mm | 32903.160 | 1295.400 | +31607.760 |
| 52 | 600 | Blind | stud_bolt_length_mm | 609.600 | 605.000 | +4.600 |
| 52 | 600 | Blind | stud_bolt_length_mm | 603.250 | 605.000 | -1.750 |
| 54 | 600 | Welding Neck | outer_diameter_mm | 1778.000 | 1780.000 | -2.000 |
| 54 | 600 | Welding Neck | bore_diameter_mm | 34193.480 | 1346.200 | +32847.280 |
| 54 | 600 | Welding Neck | length_thru_hub_mm | 209.600 | 349.000 | -139.400 |
| 54 | 600 | Welding Neck | outer_diameter_mm | 1778.000 | 1780.000 | -2.000 |
| 54 | 600 | Welding Neck | length_thru_hub_mm | 209.550 | 349.000 | -139.450 |
| 54 | 600 | Slip On | outer_diameter_mm | 1778.000 | 1780.000 | -2.000 |
| 54 | 600 | Slip On | bore_diameter_mm | 34193.480 | 1346.200 | +32847.280 |
| 54 | 600 | Slip On | outer_diameter_mm | 1778.000 | 1780.000 | -2.000 |
| 54 | 600 | Blind | outer_diameter_mm | 1778.000 | 1780.000 | -2.000 |
| 54 | 600 | Blind | bore_diameter_mm | 34193.480 | 1346.200 | +32847.280 |
| 54 | 600 | Blind | outer_diameter_mm | 1778.000 | 1780.000 | -2.000 |
| 56 | 600 | Welding Neck | bore_diameter_mm | 35483.800 | 1397.000 | +34086.800 |
| 56 | 600 | Welding Neck | stud_bolt_length_mm | 654.050 | 650.000 | +4.050 |
| 56 | 600 | Welding Neck | length_thru_hub_mm | 217.500 | 362.000 | -144.500 |
| 56 | 600 | Welding Neck | length_thru_hub_mm | 217.424 | 362.000 | -144.576 |
| 56 | 600 | Welding Neck | stud_bolt_length_mm | 647.700 | 650.000 | -2.300 |
| 56 | 600 | Slip On | bore_diameter_mm | 35483.800 | 1397.000 | +34086.800 |
| 56 | 600 | Slip On | stud_bolt_length_mm | 654.050 | 650.000 | +4.050 |
| 56 | 600 | Slip On | stud_bolt_length_mm | 647.700 | 650.000 | -2.300 |
| 56 | 600 | Blind | bore_diameter_mm | 35483.800 | 1397.000 | +34086.800 |
| 56 | 600 | Blind | stud_bolt_length_mm | 654.050 | 650.000 | +4.050 |
| 56 | 600 | Blind | stud_bolt_length_mm | 647.700 | 650.000 | -2.300 |
| 58 | 600 | Welding Neck | bore_diameter_mm | 36774.120 | 1447.800 | +35326.320 |
| 58 | 600 | Welding Neck | length_thru_hub_mm | 222.300 | 370.000 | -147.700 |
| 58 | 600 | Welding Neck | length_thru_hub_mm | 222.250 | 370.000 | -147.750 |
| 58 | 600 | Slip On | bore_diameter_mm | 36774.120 | 1447.800 | +35326.320 |
| 58 | 600 | Blind | bore_diameter_mm | 36774.120 | 1447.800 | +35326.320 |
| 60 | 600 | Welding Neck | outer_diameter_mm | 1993.900 | 1995.000 | -1.100 |
| 60 | 600 | Welding Neck | bore_diameter_mm | 38064.440 | 1498.600 | +36565.840 |
| 60 | 600 | Welding Neck | stud_bolt_length_mm | 692.150 | 690.000 | +2.150 |
| 60 | 600 | Welding Neck | length_thru_hub_mm | 233.400 | 389.000 | -155.600 |
| 60 | 600 | Welding Neck | outer_diameter_mm | 1993.900 | 1995.000 | -1.100 |
| 60 | 600 | Welding Neck | length_thru_hub_mm | 233.426 | 389.000 | -155.574 |
| 60 | 600 | Welding Neck | stud_bolt_length_mm | 692.150 | 690.000 | +2.150 |
| 60 | 600 | Slip On | outer_diameter_mm | 1993.900 | 1995.000 | -1.100 |
| 60 | 600 | Slip On | bore_diameter_mm | 38064.440 | 1498.600 | +36565.840 |
| 60 | 600 | Slip On | stud_bolt_length_mm | 692.150 | 690.000 | +2.150 |
| 60 | 600 | Slip On | outer_diameter_mm | 1993.900 | 1995.000 | -1.100 |
| 60 | 600 | Slip On | stud_bolt_length_mm | 692.150 | 690.000 | +2.150 |
| 60 | 600 | Blind | outer_diameter_mm | 1993.900 | 1995.000 | -1.100 |
| 60 | 600 | Blind | bore_diameter_mm | 38064.440 | 1498.600 | +36565.840 |
| 60 | 600 | Blind | stud_bolt_length_mm | 692.150 | 690.000 | +2.150 |
| 60 | 600 | Blind | outer_diameter_mm | 1993.900 | 1995.000 | -1.100 |
| 60 | 600 | Blind | stud_bolt_length_mm | 692.150 | 690.000 | +2.150 |
| 26 | 900 | Welding Neck | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 26 | 900 | Welding Neck | outer_diameter_mm | 1020.000 | 1085.000 | -65.000 |
| 26 | 900 | Welding Neck | thickness_mm | 135.000 | 139.700 | -4.700 |
| 26 | 900 | Welding Neck | hub_diameter_mm | 743.000 | 775.000 | -32.000 |
| 26 | 900 | Welding Neck | length_thru_hub_mm | 135.000 | 286.000 | -151.000 |
| 26 | 900 | Welding Neck | rf_diameter_mm | 762.000 | 749.000 | +13.000 |
| 26 | 900 | Welding Neck | pcd_mm | 901.700 | 952.500 | -50.800 |
| 26 | 900 | Welding Neck | stud_bolt_length_mm | 450.000 | 475.000 | -25.000 |
| 26 | 900 | Welding Neck | weight_kg | 476.000 | 691.000 | -215.000 |
| 26 | 900 | Welding Neck | outer_diameter_mm | 1022.350 | 1085.000 | -62.650 |
| 26 | 900 | Welding Neck | thickness_mm | 134.874 | 139.700 | -4.826 |
| 26 | 900 | Welding Neck | hub_diameter_mm | 742.950 | 775.000 | -32.050 |
| 26 | 900 | Welding Neck | length_thru_hub_mm | 134.874 | 286.000 | -151.126 |
| 26 | 900 | Welding Neck | rf_diameter_mm | 762.000 | 749.000 | +13.000 |
| 26 | 900 | Welding Neck | pcd_mm | 901.700 | 952.500 | -50.800 |
| 26 | 900 | Welding Neck | stud_bolt_length_mm | 450.850 | 475.000 | -24.150 |
| 26 | 900 | Welding Neck | weight_kg | 476.000 | 691.000 | -215.000 |
| 26 | 900 | Slip On | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 26 | 900 | Slip On | outer_diameter_mm | 1020.000 | 1085.000 | -65.000 |
| 26 | 900 | Slip On | thickness_mm | 135.000 | 139.700 | -4.700 |
| 26 | 900 | Slip On | hub_diameter_mm | 743.000 | 775.000 | -32.000 |
| 26 | 900 | Slip On | length_thru_hub_mm | 135.000 | 139.700 | -4.700 |
| 26 | 900 | Slip On | rf_diameter_mm | 762.000 | 749.000 | +13.000 |
| 26 | 900 | Slip On | pcd_mm | 901.700 | 952.500 | -50.800 |
| 26 | 900 | Slip On | stud_bolt_length_mm | 450.000 | 475.000 | -25.000 |
| 26 | 900 | Slip On | outer_diameter_mm | 1022.350 | 1085.000 | -62.650 |
| 26 | 900 | Slip On | thickness_mm | 134.874 | 139.700 | -4.826 |
| 26 | 900 | Slip On | hub_diameter_mm | 742.950 | 775.000 | -32.050 |
| 26 | 900 | Slip On | length_thru_hub_mm | 134.874 | 139.700 | -4.826 |
| 26 | 900 | Slip On | rf_diameter_mm | 762.000 | 749.000 | +13.000 |
| 26 | 900 | Slip On | pcd_mm | 901.700 | 952.500 | -50.800 |
| 26 | 900 | Slip On | stud_bolt_length_mm | 450.850 | 475.000 | -24.150 |
| 26 | 900 | Slip On | weight_kg | 990.000 | 647.000 | +343.000 |
| 26 | 900 | Blind | stud_bolt_length_mm | 476.250 | 475.000 | +1.250 |
| 26 | 900 | Blind | outer_diameter_mm | 1020.000 | 1085.000 | -65.000 |
| 26 | 900 | Blind | thickness_mm | 154.000 | 160.400 | -6.400 |
| 26 | 900 | Blind | hub_diameter_mm | 743.000 | 775.000 | -32.000 |
| 26 | 900 | Blind | length_thru_hub_mm | 154.000 | 160.400 | -6.400 |
| 26 | 900 | Blind | rf_diameter_mm | 762.000 | 749.000 | +13.000 |
| 26 | 900 | Blind | pcd_mm | 901.700 | 952.500 | -50.800 |
| 26 | 900 | Blind | stud_bolt_length_mm | 450.000 | 475.000 | -25.000 |
| 26 | 900 | Blind | outer_diameter_mm | 1022.350 | 1085.000 | -62.650 |
| 26 | 900 | Blind | thickness_mm | 153.924 | 160.400 | -6.476 |
| 26 | 900 | Blind | hub_diameter_mm | 742.950 | 775.000 | -32.050 |
| 26 | 900 | Blind | length_thru_hub_mm | 153.924 | 160.400 | -6.476 |
| 26 | 900 | Blind | rf_diameter_mm | 762.000 | 749.000 | +13.000 |
| 26 | 900 | Blind | pcd_mm | 901.700 | 952.500 | -50.800 |
| 26 | 900 | Blind | stud_bolt_length_mm | 450.850 | 475.000 | -24.150 |
| 28 | 900 | Welding Neck | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 28 | 900 | Welding Neck | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 28 | 900 | Welding Neck | outer_diameter_mm | 1105.000 | 1170.000 | -65.000 |
| 28 | 900 | Welding Neck | thickness_mm | 147.700 | 142.900 | +4.800 |
| 28 | 900 | Welding Neck | hub_diameter_mm | 797.000 | 832.000 | -35.000 |
| 28 | 900 | Welding Neck | length_thru_hub_mm | 147.700 | 298.000 | -150.300 |
| 28 | 900 | Welding Neck | rf_diameter_mm | 819.000 | 800.000 | +19.000 |
| 28 | 900 | Welding Neck | pcd_mm | 971.600 | 1022.400 | -50.800 |
| 28 | 900 | Welding Neck | stud_bolt_length_mm | 495.000 | 500.000 | -5.000 |
| 28 | 900 | Welding Neck | weight_kg | 689.000 | 821.000 | -132.000 |
| 28 | 900 | Welding Neck | outer_diameter_mm | 1104.900 | 1170.000 | -65.100 |
| 28 | 900 | Welding Neck | thickness_mm | 147.574 | 142.900 | +4.674 |
| 28 | 900 | Welding Neck | hub_diameter_mm | 797.052 | 832.000 | -34.948 |
| 28 | 900 | Welding Neck | length_thru_hub_mm | 147.574 | 298.000 | -150.426 |
| 28 | 900 | Welding Neck | rf_diameter_mm | 819.150 | 800.000 | +19.150 |
| 28 | 900 | Welding Neck | pcd_mm | 971.550 | 1022.400 | -50.850 |
| 28 | 900 | Welding Neck | stud_bolt_length_mm | 495.300 | 500.000 | -4.700 |
| 28 | 900 | Welding Neck | weight_kg | 689.000 | 821.000 | -132.000 |
| 28 | 900 | Slip On | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 28 | 900 | Slip On | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 28 | 900 | Slip On | outer_diameter_mm | 1105.000 | 1170.000 | -65.000 |
| 28 | 900 | Slip On | thickness_mm | 147.700 | 142.900 | +4.800 |
| 28 | 900 | Slip On | hub_diameter_mm | 797.000 | 832.000 | -35.000 |
| 28 | 900 | Slip On | length_thru_hub_mm | 147.700 | 142.900 | +4.800 |
| 28 | 900 | Slip On | rf_diameter_mm | 819.000 | 800.000 | +19.000 |
| 28 | 900 | Slip On | pcd_mm | 971.600 | 1022.400 | -50.800 |
| 28 | 900 | Slip On | stud_bolt_length_mm | 495.000 | 500.000 | -5.000 |
| 28 | 900 | Slip On | outer_diameter_mm | 1104.900 | 1170.000 | -65.100 |
| 28 | 900 | Slip On | thickness_mm | 147.574 | 142.900 | +4.674 |
| 28 | 900 | Slip On | hub_diameter_mm | 797.052 | 832.000 | -34.948 |
| 28 | 900 | Slip On | length_thru_hub_mm | 147.574 | 142.900 | +4.674 |
| 28 | 900 | Slip On | rf_diameter_mm | 819.150 | 800.000 | +19.150 |
| 28 | 900 | Slip On | pcd_mm | 971.550 | 1022.400 | -50.850 |
| 28 | 900 | Slip On | stud_bolt_length_mm | 495.300 | 500.000 | -4.700 |
| 28 | 900 | Slip On | weight_kg | 1252.000 | 764.000 | +488.000 |
| 28 | 900 | Blind | outer_diameter_mm | 1168.400 | 1170.000 | -1.600 |
| 28 | 900 | Blind | stud_bolt_length_mm | 501.650 | 500.000 | +1.650 |
| 28 | 900 | Blind | outer_diameter_mm | 1105.000 | 1170.000 | -65.000 |
| 28 | 900 | Blind | thickness_mm | 166.700 | 171.500 | -4.800 |
| 28 | 900 | Blind | hub_diameter_mm | 797.000 | 832.000 | -35.000 |
| 28 | 900 | Blind | length_thru_hub_mm | 166.700 | 171.500 | -4.800 |
| 28 | 900 | Blind | rf_diameter_mm | 819.000 | 800.000 | +19.000 |
| 28 | 900 | Blind | pcd_mm | 971.600 | 1022.400 | -50.800 |
| 28 | 900 | Blind | stud_bolt_length_mm | 495.000 | 500.000 | -5.000 |
| 28 | 900 | Blind | outer_diameter_mm | 1104.900 | 1170.000 | -65.100 |
| 28 | 900 | Blind | thickness_mm | 166.624 | 171.500 | -4.876 |
| 28 | 900 | Blind | hub_diameter_mm | 797.052 | 832.000 | -34.948 |
| 28 | 900 | Blind | length_thru_hub_mm | 166.624 | 171.500 | -4.876 |
| 28 | 900 | Blind | rf_diameter_mm | 819.150 | 800.000 | +19.150 |
| 28 | 900 | Blind | pcd_mm | 971.550 | 1022.400 | -50.850 |
| 28 | 900 | Blind | stud_bolt_length_mm | 495.300 | 500.000 | -4.700 |
| 30 | 900 | Welding Neck | outer_diameter_mm | 1231.900 | 1230.000 | +1.900 |
| 30 | 900 | Welding Neck | outer_diameter_mm | 1180.000 | 1230.000 | -50.000 |
| 30 | 900 | Welding Neck | thickness_mm | 155.600 | 149.300 | +6.300 |
| 30 | 900 | Welding Neck | hub_diameter_mm | 851.000 | 889.000 | -38.000 |
| 30 | 900 | Welding Neck | length_thru_hub_mm | 155.600 | 311.000 | -155.400 |
| 30 | 900 | Welding Neck | rf_diameter_mm | 876.000 | 857.000 | +19.000 |
| 30 | 900 | Welding Neck | pcd_mm | 1035.000 | 1085.800 | -50.800 |
| 30 | 900 | Welding Neck | weight_kg | 825.000 | 961.000 | -136.000 |
| 30 | 900 | Welding Neck | outer_diameter_mm | 1181.100 | 1230.000 | -48.900 |
| 30 | 900 | Welding Neck | thickness_mm | 155.448 | 149.300 | +6.148 |
| 30 | 900 | Welding Neck | hub_diameter_mm | 850.900 | 889.000 | -38.100 |
| 30 | 900 | Welding Neck | length_thru_hub_mm | 155.448 | 311.000 | -155.552 |
| 30 | 900 | Welding Neck | rf_diameter_mm | 876.300 | 857.000 | +19.300 |
| 30 | 900 | Welding Neck | pcd_mm | 1035.050 | 1085.800 | -50.750 |
| 30 | 900 | Welding Neck | weight_kg | 825.000 | 961.000 | -136.000 |
| 30 | 900 | Slip On | outer_diameter_mm | 1231.900 | 1230.000 | +1.900 |
| 30 | 900 | Slip On | outer_diameter_mm | 1180.000 | 1230.000 | -50.000 |
| 30 | 900 | Slip On | thickness_mm | 155.600 | 149.300 | +6.300 |
| 30 | 900 | Slip On | hub_diameter_mm | 851.000 | 889.000 | -38.000 |
| 30 | 900 | Slip On | length_thru_hub_mm | 155.600 | 149.300 | +6.300 |
| 30 | 900 | Slip On | rf_diameter_mm | 876.000 | 857.000 | +19.000 |
| 30 | 900 | Slip On | pcd_mm | 1035.000 | 1085.800 | -50.800 |
| 30 | 900 | Slip On | outer_diameter_mm | 1181.100 | 1230.000 | -48.900 |
| 30 | 900 | Slip On | thickness_mm | 155.448 | 149.300 | +6.148 |
| 30 | 900 | Slip On | hub_diameter_mm | 850.900 | 889.000 | -38.100 |
| 30 | 900 | Slip On | length_thru_hub_mm | 155.448 | 149.300 | +6.148 |
| 30 | 900 | Slip On | rf_diameter_mm | 876.300 | 857.000 | +19.300 |
| 30 | 900 | Slip On | pcd_mm | 1035.050 | 1085.800 | -50.750 |
| 30 | 900 | Slip On | weight_kg | 1512.000 | 872.000 | +640.000 |
| 30 | 900 | Blind | outer_diameter_mm | 1231.900 | 1230.000 | +1.900 |
| 30 | 900 | Blind | outer_diameter_mm | 1180.000 | 1230.000 | -50.000 |
| 30 | 900 | Blind | thickness_mm | 176.100 | 182.600 | -6.500 |
| 30 | 900 | Blind | hub_diameter_mm | 851.000 | 889.000 | -38.000 |
| 30 | 900 | Blind | length_thru_hub_mm | 176.100 | 182.600 | -6.500 |
| 30 | 900 | Blind | rf_diameter_mm | 876.000 | 857.000 | +19.000 |
| 30 | 900 | Blind | pcd_mm | 1035.000 | 1085.800 | -50.800 |
| 30 | 900 | Blind | outer_diameter_mm | 1181.100 | 1230.000 | -48.900 |
| 30 | 900 | Blind | thickness_mm | 176.022 | 182.600 | -6.578 |
| 30 | 900 | Blind | hub_diameter_mm | 850.900 | 889.000 | -38.100 |
| 30 | 900 | Blind | length_thru_hub_mm | 176.022 | 182.600 | -6.578 |
| 30 | 900 | Blind | rf_diameter_mm | 876.300 | 857.000 | +19.300 |
| 30 | 900 | Blind | pcd_mm | 1035.050 | 1085.800 | -50.750 |
| 32 | 900 | Welding Neck | stud_bolt_length_mm | 565.150 | 560.000 | +5.150 |
| 32 | 900 | Welding Neck | outer_diameter_mm | 1240.000 | 1315.000 | -75.000 |
| 32 | 900 | Welding Neck | thickness_mm | 160.400 | 158.800 | +1.600 |
| 32 | 900 | Welding Neck | hub_diameter_mm | 908.000 | 946.000 | -38.000 |
| 32 | 900 | Welding Neck | length_thru_hub_mm | 160.400 | 330.000 | -169.600 |
| 32 | 900 | Welding Neck | rf_diameter_mm | 927.000 | 914.000 | +13.000 |
| 32 | 900 | Welding Neck | pcd_mm | 1092.200 | 1155.700 | -63.500 |
| 32 | 900 | Welding Neck | stud_bolt_length_mm | 535.000 | 560.000 | -25.000 |
| 32 | 900 | Welding Neck | weight_kg | 936.000 | 1154.000 | -218.000 |
| 32 | 900 | Welding Neck | outer_diameter_mm | 1238.250 | 1315.000 | -76.750 |
| 32 | 900 | Welding Neck | thickness_mm | 160.274 | 158.800 | +1.474 |
| 32 | 900 | Welding Neck | hub_diameter_mm | 908.050 | 946.000 | -37.950 |
| 32 | 900 | Welding Neck | length_thru_hub_mm | 160.274 | 330.000 | -169.726 |
| 32 | 900 | Welding Neck | rf_diameter_mm | 927.100 | 914.000 | +13.100 |
| 32 | 900 | Welding Neck | pcd_mm | 1092.200 | 1155.700 | -63.500 |
| 32 | 900 | Welding Neck | stud_bolt_length_mm | 533.400 | 560.000 | -26.600 |
| 32 | 900 | Welding Neck | weight_kg | 936.000 | 1154.000 | -218.000 |
| 32 | 900 | Slip On | stud_bolt_length_mm | 565.150 | 560.000 | +5.150 |
| 32 | 900 | Slip On | outer_diameter_mm | 1240.000 | 1315.000 | -75.000 |
| 32 | 900 | Slip On | thickness_mm | 160.400 | 158.800 | +1.600 |
| 32 | 900 | Slip On | hub_diameter_mm | 908.000 | 946.000 | -38.000 |
| 32 | 900 | Slip On | length_thru_hub_mm | 160.400 | 158.800 | +1.600 |
| 32 | 900 | Slip On | rf_diameter_mm | 927.000 | 914.000 | +13.000 |
| 32 | 900 | Slip On | pcd_mm | 1092.200 | 1155.700 | -63.500 |
| 32 | 900 | Slip On | stud_bolt_length_mm | 535.000 | 560.000 | -25.000 |
| 32 | 900 | Slip On | outer_diameter_mm | 1238.250 | 1315.000 | -76.750 |
| 32 | 900 | Slip On | thickness_mm | 160.274 | 158.800 | +1.474 |
| 32 | 900 | Slip On | hub_diameter_mm | 908.050 | 946.000 | -37.950 |
| 32 | 900 | Slip On | length_thru_hub_mm | 160.274 | 158.800 | +1.474 |
| 32 | 900 | Slip On | rf_diameter_mm | 927.100 | 914.000 | +13.100 |
| 32 | 900 | Slip On | pcd_mm | 1092.200 | 1155.700 | -63.500 |
| 32 | 900 | Slip On | stud_bolt_length_mm | 533.400 | 560.000 | -26.600 |
| 32 | 900 | Slip On | weight_kg | 1753.000 | 1056.000 | +697.000 |
| 32 | 900 | Blind | stud_bolt_length_mm | 565.150 | 560.000 | +5.150 |
| 32 | 900 | Blind | outer_diameter_mm | 1240.000 | 1315.000 | -75.000 |
| 32 | 900 | Blind | thickness_mm | 186.000 | 193.700 | -7.700 |
| 32 | 900 | Blind | hub_diameter_mm | 908.000 | 946.000 | -38.000 |
| 32 | 900 | Blind | length_thru_hub_mm | 186.000 | 193.700 | -7.700 |
| 32 | 900 | Blind | rf_diameter_mm | 927.000 | 914.000 | +13.000 |
| 32 | 900 | Blind | pcd_mm | 1092.200 | 1155.700 | -63.500 |
| 32 | 900 | Blind | stud_bolt_length_mm | 535.000 | 560.000 | -25.000 |
| 32 | 900 | Blind | outer_diameter_mm | 1238.250 | 1315.000 | -76.750 |
| 32 | 900 | Blind | thickness_mm | 185.674 | 193.700 | -8.026 |
| 32 | 900 | Blind | hub_diameter_mm | 908.050 | 946.000 | -37.950 |
| 32 | 900 | Blind | length_thru_hub_mm | 185.674 | 193.700 | -8.026 |
| 32 | 900 | Blind | rf_diameter_mm | 927.100 | 914.000 | +13.100 |
| 32 | 900 | Blind | pcd_mm | 1092.200 | 1155.700 | -63.500 |
| 32 | 900 | Blind | stud_bolt_length_mm | 533.400 | 560.000 | -26.600 |
| 34 | 900 | Welding Neck | outer_diameter_mm | 1397.000 | 1395.000 | +2.000 |
| 34 | 900 | Welding Neck | stud_bolt_length_mm | 590.550 | 585.000 | +5.550 |
| 34 | 900 | Welding Neck | outer_diameter_mm | 1315.000 | 1395.000 | -80.000 |
| 34 | 900 | Welding Neck | thickness_mm | 171.500 | 165.100 | +6.400 |
| 34 | 900 | Welding Neck | hub_diameter_mm | 962.000 | 1006.000 | -44.000 |
| 34 | 900 | Welding Neck | length_thru_hub_mm | 171.500 | 349.000 | -177.500 |
| 34 | 900 | Welding Neck | rf_diameter_mm | 991.000 | 965.000 | +26.000 |
| 34 | 900 | Welding Neck | pcd_mm | 1155.700 | 1225.600 | -69.900 |
| 34 | 900 | Welding Neck | stud_bolt_length_mm | 570.000 | 585.000 | -15.000 |
| 34 | 900 | Welding Neck | weight_kg | 1111.000 | 1347.000 | -236.000 |
| 34 | 900 | Welding Neck | outer_diameter_mm | 1314.450 | 1395.000 | -80.550 |
| 34 | 900 | Welding Neck | thickness_mm | 171.450 | 165.100 | +6.350 |
| 34 | 900 | Welding Neck | hub_diameter_mm | 962.152 | 1006.000 | -43.848 |
| 34 | 900 | Welding Neck | length_thru_hub_mm | 171.450 | 349.000 | -177.550 |
| 34 | 900 | Welding Neck | rf_diameter_mm | 990.600 | 965.000 | +25.600 |
| 34 | 900 | Welding Neck | pcd_mm | 1155.700 | 1225.600 | -69.900 |
| 34 | 900 | Welding Neck | stud_bolt_length_mm | 571.500 | 585.000 | -13.500 |
| 34 | 900 | Welding Neck | weight_kg | 1111.000 | 1347.000 | -236.000 |
| 34 | 900 | Slip On | outer_diameter_mm | 1397.000 | 1395.000 | +2.000 |
| 34 | 900 | Slip On | stud_bolt_length_mm | 590.550 | 585.000 | +5.550 |
| 34 | 900 | Slip On | outer_diameter_mm | 1315.000 | 1395.000 | -80.000 |
| 34 | 900 | Slip On | thickness_mm | 171.500 | 165.100 | +6.400 |
| 34 | 900 | Slip On | hub_diameter_mm | 962.000 | 1006.000 | -44.000 |
| 34 | 900 | Slip On | length_thru_hub_mm | 171.500 | 165.100 | +6.400 |
| 34 | 900 | Slip On | rf_diameter_mm | 991.000 | 965.000 | +26.000 |
| 34 | 900 | Slip On | pcd_mm | 1155.700 | 1225.600 | -69.900 |
| 34 | 900 | Slip On | stud_bolt_length_mm | 570.000 | 585.000 | -15.000 |
| 34 | 900 | Slip On | outer_diameter_mm | 1314.450 | 1395.000 | -80.550 |
| 34 | 900 | Slip On | thickness_mm | 171.450 | 165.100 | +6.350 |
| 34 | 900 | Slip On | hub_diameter_mm | 962.152 | 1006.000 | -43.848 |
| 34 | 900 | Slip On | length_thru_hub_mm | 171.450 | 165.100 | +6.350 |
| 34 | 900 | Slip On | rf_diameter_mm | 990.600 | 965.000 | +25.600 |
| 34 | 900 | Slip On | pcd_mm | 1155.700 | 1225.600 | -69.900 |
| 34 | 900 | Slip On | stud_bolt_length_mm | 571.500 | 585.000 | -13.500 |
| 34 | 900 | Slip On | weight_kg | 2075.000 | 1242.000 | +833.000 |
| 34 | 900 | Blind | outer_diameter_mm | 1397.000 | 1395.000 | +2.000 |
| 34 | 900 | Blind | stud_bolt_length_mm | 590.550 | 585.000 | +5.550 |
| 34 | 900 | Blind | outer_diameter_mm | 1315.000 | 1395.000 | -80.000 |
| 34 | 900 | Blind | thickness_mm | 195.000 | 204.800 | -9.800 |
| 34 | 900 | Blind | hub_diameter_mm | 962.000 | 1006.000 | -44.000 |
| 34 | 900 | Blind | length_thru_hub_mm | 195.000 | 204.800 | -9.800 |
| 34 | 900 | Blind | rf_diameter_mm | 991.000 | 965.000 | +26.000 |
| 34 | 900 | Blind | pcd_mm | 1155.700 | 1225.600 | -69.900 |
| 34 | 900 | Blind | stud_bolt_length_mm | 570.000 | 585.000 | -15.000 |
| 34 | 900 | Blind | outer_diameter_mm | 1314.450 | 1395.000 | -80.550 |
| 34 | 900 | Blind | thickness_mm | 195.072 | 204.800 | -9.728 |
| 34 | 900 | Blind | hub_diameter_mm | 962.152 | 1006.000 | -43.848 |
| 34 | 900 | Blind | length_thru_hub_mm | 195.072 | 204.800 | -9.728 |
| 34 | 900 | Blind | rf_diameter_mm | 990.600 | 965.000 | +25.600 |
| 34 | 900 | Blind | pcd_mm | 1155.700 | 1225.600 | -69.900 |
| 34 | 900 | Blind | stud_bolt_length_mm | 571.500 | 585.000 | -13.500 |
| 36 | 900 | Welding Neck | stud_bolt_length_mm | 609.600 | 605.000 | +4.600 |
| 36 | 900 | Welding Neck | outer_diameter_mm | 1345.000 | 1460.000 | -115.000 |
| 36 | 900 | Welding Neck | thickness_mm | 173.100 | 171.500 | +1.600 |
| 36 | 900 | Welding Neck | hub_diameter_mm | 1016.000 | 1064.000 | -48.000 |
| 36 | 900 | Welding Neck | length_thru_hub_mm | 173.100 | 362.000 | -188.900 |
| 36 | 900 | Welding Neck | rf_diameter_mm | 1029.000 | 1022.000 | +7.000 |
| 36 | 900 | Welding Neck | pcd_mm | 1200.200 | 1289.000 | -88.800 |
| 36 | 900 | Welding Neck | stud_bolt_length_mm | 565.000 | 605.000 | -40.000 |
| 36 | 900 | Welding Neck | weight_kg | 1143.000 | 1539.000 | -396.000 |
| 36 | 900 | Welding Neck | outer_diameter_mm | 1346.200 | 1460.000 | -113.800 |
| 36 | 900 | Welding Neck | thickness_mm | 172.974 | 171.500 | +1.474 |
| 36 | 900 | Welding Neck | hub_diameter_mm | 1016.000 | 1064.000 | -48.000 |
| 36 | 900 | Welding Neck | length_thru_hub_mm | 172.974 | 362.000 | -189.026 |
| 36 | 900 | Welding Neck | rf_diameter_mm | 1028.700 | 1022.000 | +6.700 |
| 36 | 900 | Welding Neck | pcd_mm | 1200.150 | 1289.000 | -88.850 |
| 36 | 900 | Welding Neck | stud_bolt_length_mm | 565.150 | 605.000 | -39.850 |
| 36 | 900 | Welding Neck | weight_kg | 1143.000 | 1539.000 | -396.000 |
| 36 | 900 | Slip On | stud_bolt_length_mm | 609.600 | 605.000 | +4.600 |
| 36 | 900 | Slip On | outer_diameter_mm | 1345.000 | 1460.000 | -115.000 |
| 36 | 900 | Slip On | thickness_mm | 173.100 | 171.500 | +1.600 |
| 36 | 900 | Slip On | hub_diameter_mm | 1016.000 | 1064.000 | -48.000 |
| 36 | 900 | Slip On | length_thru_hub_mm | 173.100 | 171.500 | +1.600 |
| 36 | 900 | Slip On | rf_diameter_mm | 1029.000 | 1022.000 | +7.000 |
| 36 | 900 | Slip On | pcd_mm | 1200.200 | 1289.000 | -88.800 |
| 36 | 900 | Slip On | stud_bolt_length_mm | 565.000 | 605.000 | -40.000 |
| 36 | 900 | Slip On | outer_diameter_mm | 1346.200 | 1460.000 | -113.800 |
| 36 | 900 | Slip On | thickness_mm | 172.974 | 171.500 | +1.474 |
| 36 | 900 | Slip On | hub_diameter_mm | 1016.000 | 1064.000 | -48.000 |
| 36 | 900 | Slip On | length_thru_hub_mm | 172.974 | 171.500 | +1.474 |
| 36 | 900 | Slip On | rf_diameter_mm | 1028.700 | 1022.000 | +6.700 |
| 36 | 900 | Slip On | pcd_mm | 1200.150 | 1289.000 | -88.850 |
| 36 | 900 | Slip On | stud_bolt_length_mm | 565.150 | 605.000 | -39.850 |
| 36 | 900 | Slip On | weight_kg | 2251.000 | 1388.000 | +863.000 |
| 36 | 900 | Blind | stud_bolt_length_mm | 609.600 | 605.000 | +4.600 |
| 36 | 900 | Blind | outer_diameter_mm | 1345.000 | 1460.000 | -115.000 |
| 36 | 900 | Blind | thickness_mm | 201.700 | 214.400 | -12.700 |
| 36 | 900 | Blind | hub_diameter_mm | 1016.000 | 1064.000 | -48.000 |
| 36 | 900 | Blind | length_thru_hub_mm | 201.700 | 214.400 | -12.700 |
| 36 | 900 | Blind | rf_diameter_mm | 1029.000 | 1022.000 | +7.000 |
| 36 | 900 | Blind | pcd_mm | 1200.200 | 1289.000 | -88.800 |
| 36 | 900 | Blind | stud_bolt_length_mm | 565.000 | 605.000 | -40.000 |
| 36 | 900 | Blind | outer_diameter_mm | 1346.200 | 1460.000 | -113.800 |
| 36 | 900 | Blind | thickness_mm | 201.676 | 214.400 | -12.724 |
| 36 | 900 | Blind | hub_diameter_mm | 1016.000 | 1064.000 | -48.000 |
| 36 | 900 | Blind | length_thru_hub_mm | 201.676 | 214.400 | -12.724 |
| 36 | 900 | Blind | rf_diameter_mm | 1028.700 | 1022.000 | +6.700 |
| 36 | 900 | Blind | pcd_mm | 1200.150 | 1289.000 | -88.850 |
| 36 | 900 | Blind | stud_bolt_length_mm | 565.150 | 605.000 | -39.850 |
| 38 | 900 | Welding Neck | stud_bolt_length_mm | 622.300 | 620.000 | +2.300 |
| 38 | 900 | Welding Neck | length_thru_hub_mm | 190.500 | 352.000 | -161.500 |
| 38 | 900 | Welding Neck | length_thru_hub_mm | 190.500 | 352.000 | -161.500 |
| 38 | 900 | Welding Neck | stud_bolt_length_mm | 622.300 | 620.000 | +2.300 |
| 38 | 900 | Slip On | stud_bolt_length_mm | 622.300 | 620.000 | +2.300 |
| 38 | 900 | Slip On | stud_bolt_length_mm | 622.300 | 620.000 | +2.300 |
| 38 | 900 | Slip On | weight_kg | 2836.000 | 1435.000 | +1401.000 |
| 38 | 900 | Blind | stud_bolt_length_mm | 622.300 | 620.000 | +2.300 |
| 38 | 900 | Blind | stud_bolt_length_mm | 622.300 | 620.000 | +2.300 |
| 40 | 900 | Welding Neck | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 40 | 900 | Welding Neck | length_thru_hub_mm | 196.900 | 364.000 | -167.100 |
| 40 | 900 | Welding Neck | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 40 | 900 | Welding Neck | length_thru_hub_mm | 196.850 | 364.000 | -167.150 |
| 40 | 900 | Slip On | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 40 | 900 | Slip On | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 40 | 900 | Slip On | weight_kg | 3147.000 | 1547.000 | +1600.000 |
| 40 | 900 | Blind | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 40 | 900 | Blind | outer_diameter_mm | 1511.300 | 1510.000 | +1.300 |
| 42 | 900 | Welding Neck | outer_diameter_mm | 1562.100 | 1560.000 | +2.100 |
| 42 | 900 | Welding Neck | stud_bolt_length_mm | 660.400 | 655.000 | +5.400 |
| 42 | 900 | Welding Neck | length_thru_hub_mm | 206.400 | 371.000 | -164.600 |
| 42 | 900 | Welding Neck | outer_diameter_mm | 1562.100 | 1560.000 | +2.100 |
| 42 | 900 | Welding Neck | length_thru_hub_mm | 206.248 | 371.000 | -164.752 |
| 42 | 900 | Slip On | outer_diameter_mm | 1562.100 | 1560.000 | +2.100 |
| 42 | 900 | Slip On | stud_bolt_length_mm | 660.400 | 655.000 | +5.400 |
| 42 | 900 | Slip On | outer_diameter_mm | 1562.100 | 1560.000 | +2.100 |
| 42 | 900 | Slip On | weight_kg | 3481.000 | 1688.000 | +1793.000 |
| 42 | 900 | Blind | outer_diameter_mm | 1562.100 | 1560.000 | +2.100 |
| 42 | 900 | Blind | stud_bolt_length_mm | 660.400 | 655.000 | +5.400 |
| 42 | 900 | Blind | outer_diameter_mm | 1562.100 | 1560.000 | +2.100 |
| 44 | 900 | Welding Neck | outer_diameter_mm | 1647.952 | 1650.000 | -2.048 |
| 44 | 900 | Welding Neck | length_thru_hub_mm | 214.400 | 391.000 | -176.600 |
| 44 | 900 | Welding Neck | outer_diameter_mm | 1647.952 | 1650.000 | -2.048 |
| 44 | 900 | Welding Neck | length_thru_hub_mm | 214.376 | 391.000 | -176.624 |
| 44 | 900 | Slip On | outer_diameter_mm | 1647.952 | 1650.000 | -2.048 |
| 44 | 900 | Slip On | outer_diameter_mm | 1647.952 | 1650.000 | -2.048 |
| 44 | 900 | Slip On | weight_kg | 4061.000 | 1976.000 | +2085.000 |
| 44 | 900 | Blind | outer_diameter_mm | 1647.952 | 1650.000 | -2.048 |
| 44 | 900 | Blind | outer_diameter_mm | 1647.952 | 1650.000 | -2.048 |
| 46 | 900 | Welding Neck | outer_diameter_mm | 1733.550 | 1735.000 | -1.450 |
| 46 | 900 | Welding Neck | stud_bolt_length_mm | 730.250 | 725.000 | +5.250 |
| 46 | 900 | Welding Neck | length_thru_hub_mm | 225.500 | 411.000 | -185.500 |
| 46 | 900 | Welding Neck | outer_diameter_mm | 1733.550 | 1735.000 | -1.450 |
| 46 | 900 | Welding Neck | length_thru_hub_mm | 225.552 | 411.000 | -185.448 |
| 46 | 900 | Welding Neck | stud_bolt_length_mm | 723.900 | 725.000 | -1.100 |
| 46 | 900 | Slip On | outer_diameter_mm | 1733.550 | 1735.000 | -1.450 |
| 46 | 900 | Slip On | stud_bolt_length_mm | 730.250 | 725.000 | +5.250 |
| 46 | 900 | Slip On | outer_diameter_mm | 1733.550 | 1735.000 | -1.450 |
| 46 | 900 | Slip On | stud_bolt_length_mm | 723.900 | 725.000 | -1.100 |
| 46 | 900 | Slip On | weight_kg | 4729.000 | 2324.000 | +2405.000 |
| 46 | 900 | Blind | outer_diameter_mm | 1733.550 | 1735.000 | -1.450 |
| 46 | 900 | Blind | stud_bolt_length_mm | 730.250 | 725.000 | +5.250 |
| 46 | 900 | Blind | outer_diameter_mm | 1733.550 | 1735.000 | -1.450 |
| 46 | 900 | Blind | stud_bolt_length_mm | 723.900 | 725.000 | -1.100 |
| 48 | 900 | Welding Neck | stud_bolt_length_mm | 736.600 | 735.000 | +1.600 |
| 48 | 900 | Welding Neck | length_thru_hub_mm | 233.400 | 419.000 | -185.600 |
| 48 | 900 | Welding Neck | length_thru_hub_mm | 233.426 | 419.000 | -185.574 |
| 48 | 900 | Welding Neck | stud_bolt_length_mm | 736.600 | 735.000 | +1.600 |
| 48 | 900 | Slip On | stud_bolt_length_mm | 736.600 | 735.000 | +1.600 |
| 48 | 900 | Slip On | stud_bolt_length_mm | 736.600 | 735.000 | +1.600 |
| 48 | 900 | Slip On | weight_kg | 5170.000 | 2491.000 | +2679.000 |
| 48 | 900 | Blind | stud_bolt_length_mm | 736.600 | 735.000 | +1.600 |
| 48 | 900 | Blind | stud_bolt_length_mm | 736.600 | 735.000 | +1.600 |
| 1/2 | 150 | Welding Neck | stud_bolt_length_mm | 57.150 | 55.000 | +2.150 |
| 1/2 | 150 | Slip On | stud_bolt_length_mm | 57.150 | 55.000 | +2.150 |
| 1/2 | 150 | Blind | stud_bolt_length_mm | 57.150 | 55.000 | +2.150 |
| 3/4 | 150 | Welding Neck | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 3/4 | 150 | Slip On | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 3/4 | 150 | Blind | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 1 | 150 | Welding Neck | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 1 | 150 | Slip On | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 1 | 150 | Blind | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 2 | 150 | Welding Neck | stud_bolt_length_mm | 82.550 | 85.000 | -2.450 |
| 2 | 150 | Slip On | stud_bolt_length_mm | 82.550 | 85.000 | -2.450 |
| 2 | 150 | Blind | stud_bolt_length_mm | 82.550 | 85.000 | -2.450 |
| 2 1/2 | 150 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 2 1/2 | 150 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 2 1/2 | 150 | Blind | thickness_mm | 20.574 | 22.225 | -1.651 |
| 2 1/2 | 150 | Blind | length_thru_hub_mm | 20.574 | 22.225 | -1.651 |
| 2 1/2 | 150 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3 | 150 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3 | 150 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3 | 150 | Blind | thickness_mm | 22.352 | 20.700 | +1.652 |
| 3 | 150 | Blind | length_thru_hub_mm | 22.352 | 20.700 | +1.652 |
| 3 | 150 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3 1/2 | 150 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3 1/2 | 150 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3 1/2 | 150 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 4 | 150 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 4 | 150 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 4 | 150 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 6 | 150 | Welding Neck | stud_bolt_length_mm | 101.600 | 100.000 | +1.600 |
| 6 | 150 | Slip On | stud_bolt_length_mm | 101.600 | 100.000 | +1.600 |
| 6 | 150 | Blind | thickness_mm | 23.876 | 22.300 | +1.576 |
| 6 | 150 | Blind | length_thru_hub_mm | 23.876 | 22.300 | +1.576 |
| 6 | 150 | Blind | stud_bolt_length_mm | 101.600 | 100.000 | +1.600 |
| 8 | 150 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 8 | 150 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 8 | 150 | Blind | thickness_mm | 26.924 | 23.900 | +3.024 |
| 8 | 150 | Blind | length_thru_hub_mm | 26.924 | 23.900 | +3.024 |
| 8 | 150 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 10 | 150 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 10 | 150 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 10 | 150 | Blind | thickness_mm | 28.448 | 27.000 | +1.448 |
| 10 | 150 | Blind | length_thru_hub_mm | 28.448 | 27.000 | +1.448 |
| 10 | 150 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 12 | 150 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 12 | 150 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 12 | 150 | Blind | thickness_mm | 30.226 | 28.600 | +1.626 |
| 12 | 150 | Blind | length_thru_hub_mm | 30.226 | 28.600 | +1.626 |
| 12 | 150 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 14 | 150 | Welding Neck | stud_bolt_length_mm | 133.350 | 135.000 | -1.650 |
| 14 | 150 | Slip On | stud_bolt_length_mm | 133.350 | 135.000 | -1.650 |
| 14 | 150 | Blind | thickness_mm | 33.274 | 30.200 | +3.074 |
| 14 | 150 | Blind | length_thru_hub_mm | 33.274 | 30.200 | +3.074 |
| 14 | 150 | Blind | stud_bolt_length_mm | 133.350 | 135.000 | -1.650 |
| 16 | 150 | Welding Neck | stud_bolt_length_mm | 133.350 | 135.000 | -1.650 |
| 16 | 150 | Slip On | stud_bolt_length_mm | 133.350 | 135.000 | -1.650 |
| 16 | 150 | Blind | thickness_mm | 35.052 | 33.400 | +1.652 |
| 16 | 150 | Blind | length_thru_hub_mm | 35.052 | 33.400 | +1.652 |
| 16 | 150 | Blind | stud_bolt_length_mm | 133.350 | 135.000 | -1.650 |
| 18 | 150 | Welding Neck | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 18 | 150 | Slip On | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 18 | 150 | Blind | thickness_mm | 38.100 | 35.000 | +3.100 |
| 18 | 150 | Blind | length_thru_hub_mm | 38.100 | 35.000 | +3.100 |
| 18 | 150 | Blind | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 20 | 150 | Welding Neck | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 20 | 150 | Slip On | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 20 | 150 | Blind | thickness_mm | 41.148 | 38.100 | +3.048 |
| 20 | 150 | Blind | length_thru_hub_mm | 41.148 | 38.100 | +3.048 |
| 20 | 150 | Blind | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 24 | 150 | Welding Neck | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 24 | 150 | Slip On | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 24 | 150 | Blind | thickness_mm | 45.974 | 41.600 | +4.374 |
| 24 | 150 | Blind | length_thru_hub_mm | 45.974 | 41.600 | +4.374 |
| 24 | 150 | Blind | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 1/2 | 1500 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1/2 | 1500 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1/2 | 1500 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1 | 1500 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 | 1500 | Welding Neck | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 | 1500 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 | 1500 | Slip On | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 | 1500 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 | 1500 | Blind | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 1/4 | 1500 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 1/4 | 1500 | Welding Neck | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 1/4 | 1500 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 1/4 | 1500 | Slip On | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 1/4 | 1500 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 1/4 | 1500 | Blind | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 1/2 | 1500 | Welding Neck | length_thru_hub_mm | 85.090 | 83.000 | +2.090 |
| 2 | 1500 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 2 | 1500 | Welding Neck | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 2 | 1500 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 2 | 1500 | Slip On | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 2 | 1500 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 2 | 1500 | Blind | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 2 1/2 | 1500 | Welding Neck | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 2 1/2 | 1500 | Slip On | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 2 1/2 | 1500 | Blind | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 3 | 1500 | Welding Neck | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 3 | 1500 | Slip On | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 3 | 1500 | Blind | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 4 | 1500 | Welding Neck | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 4 | 1500 | Slip On | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 4 | 1500 | Blind | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 5 | 1500 | Welding Neck | stud_bolt_length_mm | 247.650 | 250.000 | -2.350 |
| 5 | 1500 | Slip On | stud_bolt_length_mm | 247.650 | 250.000 | -2.350 |
| 5 | 1500 | Blind | stud_bolt_length_mm | 247.650 | 250.000 | -2.350 |
| 8 | 1500 | Welding Neck | stud_bolt_length_mm | 292.100 | 290.000 | +2.100 |
| 8 | 1500 | Slip On | stud_bolt_length_mm | 292.100 | 290.000 | +2.100 |
| 8 | 1500 | Blind | stud_bolt_length_mm | 292.100 | 290.000 | +2.100 |
| 10 | 1500 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 10 | 1500 | Welding Neck | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 10 | 1500 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 10 | 1500 | Slip On | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 10 | 1500 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 10 | 1500 | Blind | stud_bolt_length_mm | 336.550 | 335.000 | +1.550 |
| 14 | 1500 | Welding Neck | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 14 | 1500 | Slip On | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 14 | 1500 | Blind | stud_bolt_length_mm | 406.400 | 405.000 | +1.400 |
| 3/4 | 2500 | Welding Neck | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 3/4 | 2500 | Slip On | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 3/4 | 2500 | Blind | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 | 2500 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 | 2500 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 | 2500 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 1/4 | 2500 | Welding Neck | stud_bolt_length_mm | 152.400 | 150.000 | +2.400 |
| 1 1/4 | 2500 | Slip On | stud_bolt_length_mm | 152.400 | 150.000 | +2.400 |
| 1 1/4 | 2500 | Blind | stud_bolt_length_mm | 152.400 | 150.000 | +2.400 |
| 1 1/2 | 2500 | Welding Neck | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 1 1/2 | 2500 | Slip On | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 1 1/2 | 2500 | Blind | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 2 | 2500 | Welding Neck | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 2 | 2500 | Slip On | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 2 | 2500 | Blind | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 2 1/2 | 2500 | Welding Neck | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 2 1/2 | 2500 | Slip On | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 2 1/2 | 2500 | Blind | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 3 | 2500 | Welding Neck | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 3 | 2500 | Slip On | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 3 | 2500 | Blind | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 5 | 2500 | Welding Neck | stud_bolt_length_mm | 298.450 | 300.000 | -1.550 |
| 5 | 2500 | Slip On | stud_bolt_length_mm | 298.450 | 300.000 | -1.550 |
| 5 | 2500 | Blind | stud_bolt_length_mm | 298.450 | 300.000 | -1.550 |
| 6 | 2500 | Welding Neck | stud_bolt_length_mm | 342.900 | 345.000 | -2.100 |
| 6 | 2500 | Slip On | stud_bolt_length_mm | 342.900 | 345.000 | -2.100 |
| 6 | 2500 | Blind | stud_bolt_length_mm | 342.900 | 345.000 | -2.100 |
| 10 | 2500 | Welding Neck | stud_bolt_length_mm | 488.950 | 490.000 | -1.050 |
| 10 | 2500 | Slip On | stud_bolt_length_mm | 488.950 | 490.000 | -1.050 |
| 10 | 2500 | Blind | stud_bolt_length_mm | 488.950 | 490.000 | -1.050 |
| 1/2 | 300 | Welding Neck | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 1/2 | 300 | Slip On | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 1/2 | 300 | Blind | stud_bolt_length_mm | 63.500 | 65.000 | -1.500 |
| 3/4 | 300 | Welding Neck | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 3/4 | 300 | Slip On | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 3/4 | 300 | Blind | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 1 | 300 | Welding Neck | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 1 | 300 | Slip On | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 1 | 300 | Blind | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 1 1/4 | 300 | Welding Neck | stud_bolt_length_mm | 82.550 | 85.000 | -2.450 |
| 1 1/4 | 300 | Slip On | stud_bolt_length_mm | 82.550 | 85.000 | -2.450 |
| 1 1/4 | 300 | Blind | stud_bolt_length_mm | 82.550 | 85.000 | -2.450 |
| 1 1/2 | 300 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 1/2 | 300 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 1/2 | 300 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 2 | 300 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 2 | 300 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 2 | 300 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 2 1/2 | 300 | Welding Neck | stud_bolt_length_mm | 101.600 | 100.000 | +1.600 |
| 2 1/2 | 300 | Slip On | stud_bolt_length_mm | 101.600 | 100.000 | +1.600 |
| 2 1/2 | 300 | Blind | stud_bolt_length_mm | 101.600 | 100.000 | +1.600 |
| 3 | 300 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 3 | 300 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 3 | 300 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 3 1/2 | 300 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 3 1/2 | 300 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 3 1/2 | 300 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 8 | 300 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 8 | 300 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 8 | 300 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 10 | 300 | Welding Neck | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 10 | 300 | Slip On | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 10 | 300 | Blind | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 12 | 300 | Welding Neck | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 12 | 300 | Slip On | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 12 | 300 | Blind | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 14 | 300 | Welding Neck | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 14 | 300 | Slip On | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 14 | 300 | Blind | stud_bolt_length_mm | 177.800 | 180.000 | -2.200 |
| 18 | 300 | Welding Neck | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 18 | 300 | Slip On | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 18 | 300 | Blind | stud_bolt_length_mm | 196.850 | 195.000 | +1.850 |
| 20 | 300 | Welding Neck | stud_bolt_length_mm | 203.200 | 205.000 | -1.800 |
| 20 | 300 | Slip On | stud_bolt_length_mm | 203.200 | 205.000 | -1.800 |
| 20 | 300 | Blind | stud_bolt_length_mm | 203.200 | 205.000 | -1.800 |
| 24 | 300 | Welding Neck | stud_bolt_length_mm | 228.600 | 230.000 | -1.400 |
| 24 | 300 | Slip On | stud_bolt_length_mm | 228.600 | 230.000 | -1.400 |
| 24 | 300 | Blind | stud_bolt_length_mm | 228.600 | 230.000 | -1.400 |
| 1/2 | 400 | Welding Neck | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 1/2 | 400 | Slip On | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 1/2 | 400 | Blind | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 3/4 | 400 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3/4 | 400 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3/4 | 400 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 | 400 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 | 400 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 | 400 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 1/2 | 400 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1 1/2 | 400 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1 1/2 | 400 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 2 | 400 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 2 | 400 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 2 | 400 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 3 | 400 | Welding Neck | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 3 | 400 | Slip On | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 3 | 400 | Blind | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 3 1/2 | 400 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 3 1/2 | 400 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 3 1/2 | 400 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 4 | 400 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 4 | 400 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 4 | 400 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 5 | 400 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 5 | 400 | Welding Neck | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 5 | 400 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 5 | 400 | Slip On | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 5 | 400 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 5 | 400 | Blind | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 6 | 400 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 6 | 400 | Welding Neck | stud_bolt_length_mm | 152.400 | 150.000 | +2.400 |
| 6 | 400 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 6 | 400 | Slip On | stud_bolt_length_mm | 152.400 | 150.000 | +2.400 |
| 6 | 400 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 6 | 400 | Blind | stud_bolt_length_mm | 152.400 | 150.000 | +2.400 |
| 8 | 400 | Welding Neck | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 8 | 400 | Slip On | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 8 | 400 | Blind | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 12 | 400 | Welding Neck | stud_bolt_length_mm | 203.200 | 205.000 | -1.800 |
| 12 | 400 | Slip On | stud_bolt_length_mm | 203.200 | 205.000 | -1.800 |
| 12 | 400 | Blind | stud_bolt_length_mm | 203.200 | 205.000 | -1.800 |
| 16 | 400 | Welding Neck | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 16 | 400 | Slip On | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 16 | 400 | Blind | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 18 | 400 | Welding Neck | stud_bolt_length_mm | 228.600 | 230.000 | -1.400 |
| 18 | 400 | Slip On | stud_bolt_length_mm | 228.600 | 230.000 | -1.400 |
| 18 | 400 | Blind | stud_bolt_length_mm | 228.600 | 230.000 | -1.400 |
| 20 | 400 | Welding Neck | stud_bolt_length_mm | 241.300 | 240.000 | +1.300 |
| 20 | 400 | Slip On | stud_bolt_length_mm | 241.300 | 240.000 | +1.300 |
| 20 | 400 | Blind | stud_bolt_length_mm | 241.300 | 240.000 | +1.300 |
| 24 | 400 | Welding Neck | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 24 | 400 | Slip On | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 24 | 400 | Blind | stud_bolt_length_mm | 266.700 | 265.000 | +1.700 |
| 1/2 | 600 | Welding Neck | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 1/2 | 600 | Slip On | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 1/2 | 600 | Blind | thickness_mm | 14.224 | 22.000 | -7.776 |
| 1/2 | 600 | Blind | length_thru_hub_mm | 14.224 | 22.000 | -7.776 |
| 1/2 | 600 | Blind | stud_bolt_length_mm | 76.200 | 75.000 | +1.200 |
| 3/4 | 600 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3/4 | 600 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 3/4 | 600 | Blind | thickness_mm | 15.748 | 25.000 | -9.252 |
| 3/4 | 600 | Blind | length_thru_hub_mm | 15.748 | 25.000 | -9.252 |
| 3/4 | 600 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 | 600 | Welding Neck | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 | 600 | Slip On | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 | 600 | Blind | thickness_mm | 17.526 | 27.000 | -9.474 |
| 1 | 600 | Blind | length_thru_hub_mm | 17.526 | 27.000 | -9.474 |
| 1 | 600 | Blind | stud_bolt_length_mm | 88.900 | 90.000 | -1.100 |
| 1 1/4 | 600 | Blind | thickness_mm | 20.574 | 29.000 | -8.426 |
| 1 1/4 | 600 | Blind | length_thru_hub_mm | 20.574 | 29.000 | -8.426 |
| 1 1/2 | 600 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1 1/2 | 600 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1 1/2 | 600 | Blind | thickness_mm | 22.352 | 32.000 | -9.648 |
| 1 1/2 | 600 | Blind | length_thru_hub_mm | 22.352 | 32.000 | -9.648 |
| 1 1/2 | 600 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 2 | 600 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 2 | 600 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 2 | 600 | Blind | thickness_mm | 25.400 | 37.000 | -11.600 |
| 2 | 600 | Blind | length_thru_hub_mm | 25.400 | 37.000 | -11.600 |
| 2 | 600 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 2 1/2 | 600 | Blind | thickness_mm | 28.448 | 41.000 | -12.552 |
| 2 1/2 | 600 | Blind | length_thru_hub_mm | 28.448 | 41.000 | -12.552 |
| 3 | 600 | Welding Neck | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 3 | 600 | Slip On | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 3 | 600 | Blind | thickness_mm | 31.750 | 46.000 | -14.250 |
| 3 | 600 | Blind | length_thru_hub_mm | 31.750 | 46.000 | -14.250 |
| 3 | 600 | Blind | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 3 1/2 | 600 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 3 1/2 | 600 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 3 1/2 | 600 | Blind | thickness_mm | 35.052 | 49.000 | -13.948 |
| 3 1/2 | 600 | Blind | length_thru_hub_mm | 35.052 | 49.000 | -13.948 |
| 3 1/2 | 600 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 4 | 600 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 4 | 600 | Welding Neck | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 4 | 600 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 4 | 600 | Slip On | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 4 | 600 | Blind | thickness_mm | 38.100 | 54.000 | -15.900 |
| 4 | 600 | Blind | length_thru_hub_mm | 38.100 | 54.000 | -15.900 |
| 4 | 600 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 4 | 600 | Blind | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 5 | 600 | Blind | thickness_mm | 44.450 | 60.000 | -15.550 |
| 5 | 600 | Blind | length_thru_hub_mm | 44.450 | 60.000 | -15.550 |
| 6 | 600 | Welding Neck | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 6 | 600 | Slip On | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 6 | 600 | Blind | thickness_mm | 47.752 | 67.000 | -19.248 |
| 6 | 600 | Blind | length_thru_hub_mm | 47.752 | 67.000 | -19.248 |
| 6 | 600 | Blind | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 8 | 600 | Blind | thickness_mm | 55.626 | 76.000 | -20.374 |
| 8 | 600 | Blind | length_thru_hub_mm | 55.626 | 76.000 | -20.374 |
| 10 | 600 | Blind | thickness_mm | 63.500 | 86.000 | -22.500 |
| 10 | 600 | Blind | length_thru_hub_mm | 63.500 | 86.000 | -22.500 |
| 12 | 600 | Welding Neck | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 12 | 600 | Slip On | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 12 | 600 | Blind | thickness_mm | 66.548 | 92.000 | -25.452 |
| 12 | 600 | Blind | length_thru_hub_mm | 66.548 | 92.000 | -25.452 |
| 12 | 600 | Blind | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 14 | 600 | Blind | thickness_mm | 69.850 | 94.000 | -24.150 |
| 14 | 600 | Blind | length_thru_hub_mm | 69.850 | 94.000 | -24.150 |
| 16 | 600 | Blind | thickness_mm | 76.200 | 106.000 | -29.800 |
| 16 | 600 | Blind | length_thru_hub_mm | 76.200 | 106.000 | -29.800 |
| 18 | 600 | Welding Neck | stud_bolt_length_mm | 273.050 | 275.000 | -1.950 |
| 18 | 600 | Slip On | stud_bolt_length_mm | 273.050 | 275.000 | -1.950 |
| 18 | 600 | Blind | thickness_mm | 82.550 | 117.000 | -34.450 |
| 18 | 600 | Blind | length_thru_hub_mm | 82.550 | 117.000 | -34.450 |
| 18 | 600 | Blind | stud_bolt_length_mm | 273.050 | 275.000 | -1.950 |
| 20 | 600 | Blind | thickness_mm | 88.900 | 127.000 | -38.100 |
| 20 | 600 | Blind | length_thru_hub_mm | 88.900 | 127.000 | -38.100 |
| 24 | 600 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 24 | 600 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 24 | 600 | Blind | thickness_mm | 101.600 | 140.000 | -38.400 |
| 24 | 600 | Blind | length_thru_hub_mm | 101.600 | 140.000 | -38.400 |
| 24 | 600 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 1/2 | 900 | Welding Neck | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1/2 | 900 | Slip On | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1/2 | 900 | Blind | stud_bolt_length_mm | 107.950 | 110.000 | -2.050 |
| 1 | 900 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 | 900 | Welding Neck | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 | 900 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 | 900 | Slip On | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 | 900 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 | 900 | Blind | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 1/4 | 900 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 1/4 | 900 | Welding Neck | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 1/4 | 900 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 1/4 | 900 | Slip On | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 1/4 | 900 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 1 1/4 | 900 | Blind | stud_bolt_length_mm | 127.000 | 125.000 | +2.000 |
| 1 1/2 | 900 | Welding Neck | length_thru_hub_mm | 85.090 | 83.000 | +2.090 |
| 2 | 900 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 2 | 900 | Welding Neck | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 2 | 900 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 2 | 900 | Slip On | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 2 | 900 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 2 | 900 | Blind | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 2 1/2 | 900 | Welding Neck | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 2 1/2 | 900 | Slip On | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 2 1/2 | 900 | Blind | stud_bolt_length_mm | 158.750 | 160.000 | -1.250 |
| 3 | 900 | Welding Neck | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 3 | 900 | Welding Neck | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 3 | 900 | Slip On | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 3 | 900 | Slip On | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 3 | 900 | Blind | bolt_hole_diameter_mm | 25.400 | 1.000 | +24.400 |
| 3 | 900 | Blind | stud_bolt_length_mm | 146.050 | 145.000 | +1.050 |
| 4 | 900 | Welding Neck | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 4 | 900 | Slip On | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 4 | 900 | Blind | stud_bolt_length_mm | 171.450 | 170.000 | +1.450 |
| 8 | 900 | Welding Neck | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 8 | 900 | Slip On | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 8 | 900 | Blind | stud_bolt_length_mm | 222.250 | 220.000 | +2.250 |
| 14 | 900 | Welding Neck | stud_bolt_length_mm | 273.050 | 275.000 | -1.950 |
| 14 | 900 | Slip On | stud_bolt_length_mm | 273.050 | 275.000 | -1.950 |
| 14 | 900 | Blind | stud_bolt_length_mm | 273.050 | 275.000 | -1.950 |
| 18 | 900 | Welding Neck | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 18 | 900 | Welding Neck | stud_bolt_length_mm | 323.850 | 325.000 | -1.150 |
| 18 | 900 | Slip On | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 18 | 900 | Slip On | stud_bolt_length_mm | 323.850 | 325.000 | -1.150 |
| 18 | 900 | Blind | bolt_hole_diameter_mm | 50.800 | 2.000 | +48.800 |
| 18 | 900 | Blind | stud_bolt_length_mm | 323.850 | 325.000 | -1.150 |
| 24 | 900 | Welding Neck | stud_bolt_length_mm | 438.150 | 440.000 | -1.850 |
| 24 | 900 | Slip On | stud_bolt_length_mm | 438.150 | 440.000 | -1.850 |
| 24 | 900 | Blind | stud_bolt_length_mm | 438.150 | 440.000 | -1.850 |

_tbl_flanges_asme_b16_5: 3637 celişki satiri_

## tbl_valves_asme_b16_10

| valve_type | nps | rating | end_connection | kolon | dusurulen | kazanan | fark |
|---|---|---|---|---|---|---|---|
| Safety Valve | 1 1/2 | 150 | RF | weight_kg | 2.000 | 10.000 | -8.000 |
| Safety Valve | 1 1/2 | 150 | RF | weight_kg | 2.000 | 10.000 | -8.000 |
| Safety Valve | 3 | 150 | RF | weight_kg | 7.000 | 72.000 | -65.000 |
| Safety Valve | 3 | 150 | RF | weight_kg | 7.000 | 72.000 | -65.000 |
| Safety Valve | 4 | 150 | RF | weight_kg | 8.000 | 140.000 | -132.000 |
| Safety Valve | 4 | 150 | RF | weight_kg | 8.000 | 140.000 | -132.000 |
| Safety Valve | 6 | 150 | RF | weight_kg | 18.000 | 230.000 | -212.000 |
| Safety Valve | 6 | 150 | RF | weight_kg | 18.000 | 230.000 | -212.000 |
| Safety Valve | 8 | 150 | RF | weight_kg | 32.000 | 277.000 | -245.000 |
| Safety Valve | 8 | 150 | RF | weight_kg | 32.000 | 277.000 | -245.000 |
| Valve | 2 | 150 | RF | face_to_face_mm | 44.000 | 254.000 | -210.000 |
| Valve | 2 | 150 | RF | handwheel_height_mm | 222.000 | 1025.525 | -803.525 |
| Valve | 2 | 150 | RF | weight_kg | 3.180 | 23.600 | -20.420 |
| Valve | 2 | 150 | RF | face_to_face_mm | 43.942 | 254.000 | -210.058 |
| Valve | 2 | 150 | RF | handwheel_height_mm | 171.450 | 1025.525 | -854.075 |
| Valve | 2 | 150 | RF | handwheel_diameter_mm | 152.400 | 333.375 | -180.975 |
| Valve | 2 | 150 | RF | weight_kg | 3.180 | 23.600 | -20.420 |
| Valve | 2 | 150 | RF | face_to_face_mm | 44.000 | 254.000 | -210.000 |
| Valve | 2 | 150 | RF | handwheel_height_mm | 223.000 | 1025.525 | -802.525 |
| Valve | 2 | 150 | RF | weight_kg | 4.540 | 23.600 | -19.060 |
| Valve | 2 | 150 | RF | face_to_face_mm | 43.942 | 254.000 | -210.058 |
| Valve | 2 | 150 | RF | handwheel_height_mm | 170.942 | 1025.525 | -854.583 |
| Valve | 2 | 150 | RF | handwheel_diameter_mm | 152.400 | 333.375 | -180.975 |
| Valve | 2 | 150 | RF | weight_kg | 4.540 | 23.600 | -19.060 |
| Valve | 3 | 150 | RF | face_to_face_mm | 48.000 | 298.450 | -250.450 |
| Valve | 3 | 150 | RF | handwheel_height_mm | 254.000 | 1066.800 | -812.800 |
| Valve | 3 | 150 | RF | weight_kg | 4.540 | 43.000 | -38.460 |
| Valve | 3 | 150 | RF | face_to_face_mm | 47.752 | 298.450 | -250.698 |
| Valve | 3 | 150 | RF | handwheel_height_mm | 190.500 | 1066.800 | -876.300 |
| Valve | 3 | 150 | RF | handwheel_diameter_mm | 152.400 | 333.375 | -180.975 |
| Valve | 3 | 150 | RF | weight_kg | 4.540 | 43.000 | -38.460 |
| Valve | 3 | 150 | RF | face_to_face_mm | 48.000 | 298.450 | -250.450 |
| Valve | 3 | 150 | RF | handwheel_height_mm | 262.000 | 1066.800 | -804.800 |
| Valve | 3 | 150 | RF | weight_kg | 6.360 | 43.000 | -36.640 |
| Valve | 3 | 150 | RF | face_to_face_mm | 47.752 | 298.450 | -250.698 |
| Valve | 3 | 150 | RF | handwheel_height_mm | 189.992 | 1066.800 | -876.808 |
| Valve | 3 | 150 | RF | handwheel_diameter_mm | 152.400 | 333.375 | -180.975 |
| Valve | 3 | 150 | RF | weight_kg | 6.360 | 43.000 | -36.640 |
| Valve | 4 | 150 | RF | face_to_face_mm | 54.000 | 352.425 | -298.425 |
| Valve | 4 | 150 | RF | handwheel_height_mm | 292.000 | 1244.600 | -952.600 |
| Valve | 4 | 150 | RF | weight_kg | 8.630 | 61.200 | -52.570 |
| Valve | 4 | 150 | RF | face_to_face_mm | 53.848 | 352.425 | -298.577 |
| Valve | 4 | 150 | RF | handwheel_height_mm | 209.550 | 1244.600 | -1035.050 |
| Valve | 4 | 150 | RF | handwheel_diameter_mm | 152.400 | 406.400 | -254.000 |
| Valve | 4 | 150 | RF | weight_kg | 8.630 | 61.200 | -52.570 |
| Valve | 4 | 150 | RF | face_to_face_mm | 54.000 | 352.425 | -298.425 |
| Valve | 4 | 150 | RF | handwheel_height_mm | 307.000 | 1244.600 | -937.600 |
| Valve | 4 | 150 | RF | weight_kg | 10.900 | 61.200 | -50.300 |
| Valve | 4 | 150 | RF | face_to_face_mm | 53.848 | 352.425 | -298.577 |
| Valve | 4 | 150 | RF | handwheel_height_mm | 208.788 | 1244.600 | -1035.812 |
| Valve | 4 | 150 | RF | handwheel_diameter_mm | 152.400 | 406.400 | -254.000 |
| Valve | 4 | 150 | RF | weight_kg | 10.900 | 61.200 | -50.300 |
| Valve | 6 | 150 | RF | face_to_face_mm | 57.000 | 450.850 | -393.850 |
| Valve | 6 | 150 | RF | handwheel_height_mm | 349.000 | 1447.800 | -1098.800 |
| Valve | 6 | 150 | RF | weight_kg | 13.180 | 108.800 | -95.620 |
| Valve | 6 | 150 | RF | face_to_face_mm | 56.896 | 450.850 | -393.954 |
| Valve | 6 | 150 | RF | handwheel_height_mm | 237.490 | 1447.800 | -1210.310 |
| Valve | 6 | 150 | RF | handwheel_diameter_mm | 152.400 | 406.400 | -254.000 |
| Valve | 6 | 150 | RF | weight_kg | 13.180 | 108.800 | -95.620 |
| Valve | 6 | 150 | RF | face_to_face_mm | 57.000 | 450.850 | -393.850 |
| Valve | 6 | 150 | RF | handwheel_height_mm | 363.000 | 1447.800 | -1084.800 |
| Valve | 6 | 150 | RF | weight_kg | 15.900 | 108.800 | -92.900 |
| Valve | 6 | 150 | RF | face_to_face_mm | 56.896 | 450.850 | -393.954 |
| Valve | 6 | 150 | RF | handwheel_height_mm | 237.998 | 1447.800 | -1209.802 |
| Valve | 6 | 150 | RF | handwheel_diameter_mm | 152.400 | 406.400 | -254.000 |
| Valve | 6 | 150 | RF | weight_kg | 15.900 | 108.800 | -92.900 |
| Valve | 8 | 150 | RF | face_to_face_mm | 64.000 | 542.925 | -478.925 |
| Valve | 8 | 150 | RF | handwheel_height_mm | 408.000 | 1473.200 | -1065.200 |
| Valve | 8 | 150 | RF | weight_kg | 21.360 | 170.500 | -149.140 |
| Valve | 8 | 150 | RF | face_to_face_mm | 63.754 | 542.925 | -479.171 |
| Valve | 8 | 150 | RF | handwheel_height_mm | 274.828 | 1473.200 | -1198.372 |
| Valve | 8 | 150 | RF | handwheel_diameter_mm | 152.400 | 473.075 | -320.675 |
| Valve | 8 | 150 | RF | weight_kg | 21.360 | 170.500 | -149.140 |
| Valve | 8 | 150 | RF | face_to_face_mm | 64.000 | 542.925 | -478.925 |
| Valve | 8 | 150 | RF | handwheel_height_mm | 421.000 | 1473.200 | -1052.200 |
| Valve | 8 | 150 | RF | weight_kg | 26.810 | 170.500 | -143.690 |
| Valve | 8 | 150 | RF | face_to_face_mm | 63.754 | 542.925 | -479.171 |
| Valve | 8 | 150 | RF | handwheel_height_mm | 274.828 | 1473.200 | -1198.372 |
| Valve | 8 | 150 | RF | handwheel_diameter_mm | 152.400 | 473.075 | -320.675 |
| Valve | 8 | 150 | RF | weight_kg | 26.810 | 170.500 | -143.690 |
| Valve | 10 | 150 | RF | face_to_face_mm | 71.000 | 542.925 | -471.925 |
| Valve | 10 | 150 | RF | handwheel_height_mm | 489.000 | 1473.200 | -984.200 |
| Valve | 10 | 150 | RF | weight_kg | 34.090 | 170.500 | -136.410 |
| Valve | 10 | 150 | RF | face_to_face_mm | 70.866 | 542.925 | -472.059 |
| Valve | 10 | 150 | RF | handwheel_height_mm | 321.310 | 1473.200 | -1151.890 |
| Valve | 10 | 150 | RF | handwheel_diameter_mm | 203.200 | 473.075 | -269.875 |
| Valve | 10 | 150 | RF | weight_kg | 34.090 | 170.500 | -136.410 |
| Valve | 10 | 150 | RF | face_to_face_mm | 71.000 | 542.925 | -471.925 |
| Valve | 10 | 150 | RF | handwheel_height_mm | 515.000 | 1473.200 | -958.200 |
| Valve | 10 | 150 | RF | weight_kg | 40.000 | 170.500 | -130.500 |
| Valve | 10 | 150 | RF | face_to_face_mm | 70.866 | 542.925 | -472.059 |
| Valve | 10 | 150 | RF | handwheel_height_mm | 320.802 | 1473.200 | -1152.398 |
| Valve | 10 | 150 | RF | handwheel_diameter_mm | 203.200 | 473.075 | -269.875 |
| Valve | 10 | 150 | RF | weight_kg | 40.000 | 170.500 | -130.500 |
| Gate Valve | 1/4 | 150 | RF | handwheel_diameter_mm | 78.740 | 80.000 | -1.260 |
| Gate Valve | 3/8 | 150 | RF | handwheel_diameter_mm | 88.900 | 90.000 | -1.100 |
| Gate Valve | 1/2 | 150 | RF | handwheel_height_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 1/2 | 150 | RF | handwheel_diameter_mm | 99.060 | 101.000 | -1.940 |
| Gate Valve | 3/4 | 150 | RF | handwheel_height_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 3/4 | 150 | RF | handwheel_diameter_mm | 99.060 | 101.000 | -1.940 |
| Gate Valve | 1 | 150 | RF | handwheel_height_mm | 266.700 | 269.000 | -2.300 |
| Gate Valve | 1 1/4 | 150 | RF | handwheel_diameter_mm | 162.560 | 165.000 | -2.440 |
| Gate Valve | 1 1/2 | 150 | RF | handwheel_height_mm | 327.660 | 330.000 | -2.340 |
| Gate Valve | 1 1/2 | 150 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Gate Valve | 2 | 150 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Gate Valve | 2 1/2 | 150 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Gate Valve | 3 | 150 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 4 | 150 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 5 | 150 | RF | handwheel_height_mm | 670.560 | 673.000 | -2.440 |
| Gate Valve | 5 | 150 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 6 | 150 | RF | handwheel_height_mm | 764.540 | 767.000 | -2.460 |
| Gate Valve | 8 | 150 | RF | handwheel_height_mm | 957.580 | 960.000 | -2.420 |
| Gate Valve | 8 | 150 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Gate Valve | 10 | 150 | RF | handwheel_height_mm | 1155.700 | 1158.000 | -2.300 |
| Gate Valve | 10 | 150 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Gate Valve | 12 | 150 | RF | handwheel_height_mm | 1376.680 | 1378.000 | -1.320 |
| Gate Valve | 14 | 150 | RF | handwheel_height_mm | 1541.780 | 1543.000 | -1.220 |
| Gate Valve | 14 | 150 | RF | handwheel_diameter_mm | 497.840 | 500.000 | -2.160 |
| Gate Valve | 20 | 150 | RF | handwheel_height_mm | 2212.340 | 2214.000 | -1.660 |
| Gate Valve | 20 | 150 | RF | handwheel_diameter_mm | 678.180 | 680.000 | -1.820 |
| Gate Valve | 22 | 150 | RF | handwheel_height_mm | 2362.200 | 2400.000 | -37.800 |
| Gate Valve | 22 | 150 | RF | handwheel_diameter_mm | 762.000 | 720.000 | +42.000 |
| Gate Valve | 22 | 150 | RF | weight_kg | 1850.000 | 1250.000 | +600.000 |
| Gate Valve | 24 | 150 | RF | handwheel_diameter_mm | 762.000 | 760.000 | +2.000 |
| Gate Valve | 26 | 150 | RF | handwheel_height_mm | 3083.560 | 3086.000 | -2.440 |
| Gate Valve | 26 | 150 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 28 | 150 | RF | handwheel_height_mm | 3324.860 | 3327.000 | -2.140 |
| Gate Valve | 28 | 150 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 30 | 150 | RF | handwheel_height_mm | 3683.000 | 3606.000 | +77.000 |
| Gate Valve | 30 | 150 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 32 | 150 | RF | handwheel_height_mm | 3683.000 | 3800.000 | -117.000 |
| Gate Valve | 32 | 150 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 32 | 150 | RF | weight_kg | 2500.000 | 2490.000 | +10.000 |
| Gate Valve | 34 | 150 | RF | handwheel_height_mm | 3683.000 | 3800.000 | -117.000 |
| Gate Valve | 34 | 150 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 34 | 150 | RF | weight_kg | 2500.000 | 2600.000 | -100.000 |
| Gate Valve | 36 | 150 | RF | handwheel_height_mm | 3683.000 | 3800.000 | -117.000 |
| Gate Valve | 36 | 150 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 1/2 | 2500 | RF | handwheel_diameter_mm | 149.860 | 152.000 | -2.140 |
| Gate Valve | 3/4 | 2500 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Gate Valve | 1 | 2500 | RF | handwheel_height_mm | 497.840 | 500.000 | -2.160 |
| Gate Valve | 1 | 2500 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Gate Valve | 1 1/4 | 2500 | RF | handwheel_height_mm | 568.960 | 570.000 | -1.040 |
| Gate Valve | 1 1/4 | 2500 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 1 1/2 | 2500 | RF | handwheel_height_mm | 637.540 | 640.000 | -2.460 |
| Gate Valve | 2 | 2500 | RF | handwheel_height_mm | 698.500 | 700.000 | -1.500 |
| Gate Valve | 2 | 2500 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Gate Valve | 3 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 4 | 2500 | RF | handwheel_height_mm | 1076.960 | 1079.000 | -2.040 |
| Gate Valve | 4 | 2500 | RF | handwheel_diameter_mm | 718.820 | 720.000 | -1.180 |
| Gate Valve | 5 | 2500 | RF | handwheel_height_mm | 1076.960 | 1079.000 | -2.040 |
| Gate Valve | 5 | 2500 | RF | handwheel_diameter_mm | 718.820 | 720.000 | -1.180 |
| Gate Valve | 6 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 8 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 10 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 12 | 2500 | RF | handwheel_height_mm | 2143.760 | 2145.000 | -1.240 |
| Gate Valve | 12 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 1/2 | 300 | RF | handwheel_diameter_mm | 149.860 | 152.000 | -2.140 |
| Gate Valve | 3/4 | 300 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Gate Valve | 1 | 300 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 1 1/4 | 300 | RF | handwheel_height_mm | 317.500 | 320.000 | -2.500 |
| Gate Valve | 1 1/4 | 300 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 1 1/2 | 300 | RF | handwheel_height_mm | 388.620 | 390.000 | -1.380 |
| Gate Valve | 1 1/2 | 300 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 2 | 300 | RF | handwheel_height_mm | 421.640 | 424.000 | -2.360 |
| Gate Valve | 2 | 300 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Gate Valve | 2 1/2 | 300 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Gate Valve | 3 | 300 | RF | handwheel_height_mm | 533.400 | 535.000 | -1.600 |
| Gate Valve | 3 | 300 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 4 | 300 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 5 | 300 | RF | handwheel_height_mm | 660.400 | 662.000 | -1.600 |
| Gate Valve | 5 | 300 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Gate Valve | 6 | 300 | RF | handwheel_height_mm | 792.480 | 795.000 | -2.520 |
| Gate Valve | 6 | 300 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Gate Valve | 8 | 300 | RF | handwheel_height_mm | 1010.920 | 1012.000 | -1.080 |
| Gate Valve | 8 | 300 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Gate Valve | 10 | 300 | RF | handwheel_height_mm | 1229.360 | 1231.000 | -1.640 |
| Gate Valve | 12 | 300 | RF | handwheel_height_mm | 1447.800 | 1450.000 | -2.200 |
| Gate Valve | 12 | 300 | RF | handwheel_diameter_mm | 558.800 | 500.000 | +58.800 |
| Gate Valve | 14 | 300 | RF | handwheel_height_mm | 1643.380 | 1645.000 | -1.620 |
| Gate Valve | 14 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 16 | 300 | RF | handwheel_height_mm | 1838.960 | 1841.000 | -2.040 |
| Gate Valve | 16 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 18 | 300 | RF | handwheel_height_mm | 1940.560 | 1943.000 | -2.440 |
| Gate Valve | 18 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 20 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 24 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 26 | 300 | RF | handwheel_height_mm | 2748.280 | 2750.000 | -1.720 |
| Gate Valve | 26 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 28 | 300 | RF | handwheel_height_mm | 3098.800 | 3100.000 | -1.200 |
| Gate Valve | 28 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 30 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 32 | 300 | RF | handwheel_height_mm | 3558.540 | 3560.000 | -1.460 |
| Gate Valve | 32 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 34 | 300 | RF | handwheel_height_mm | 3810.000 | 3600.000 | +210.000 |
| Gate Valve | 34 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 36 | 300 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 1/2 | 400 | RF | handwheel_diameter_mm | 149.860 | 152.000 | -2.140 |
| Gate Valve | 3/4 | 400 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Gate Valve | 1 | 400 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 1 1/4 | 400 | RF | handwheel_height_mm | 317.500 | 320.000 | -2.500 |
| Gate Valve | 1 1/4 | 400 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 1 1/2 | 400 | RF | handwheel_height_mm | 388.620 | 390.000 | -1.380 |
| Gate Valve | 1 1/2 | 400 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 2 | 400 | RF | handwheel_height_mm | 421.640 | 424.000 | -2.360 |
| Gate Valve | 2 | 400 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Gate Valve | 2 1/2 | 400 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Gate Valve | 3 | 400 | RF | handwheel_height_mm | 533.400 | 535.000 | -1.600 |
| Gate Valve | 3 | 400 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 4 | 400 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 5 | 400 | RF | handwheel_height_mm | 660.400 | 662.000 | -1.600 |
| Gate Valve | 5 | 400 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Gate Valve | 6 | 400 | RF | handwheel_height_mm | 792.480 | 795.000 | -2.520 |
| Gate Valve | 6 | 400 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Gate Valve | 8 | 400 | RF | handwheel_height_mm | 1010.920 | 1012.000 | -1.080 |
| Gate Valve | 8 | 400 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Gate Valve | 10 | 400 | RF | handwheel_height_mm | 1229.360 | 1231.000 | -1.640 |
| Gate Valve | 12 | 400 | RF | handwheel_height_mm | 1447.800 | 1450.000 | -2.200 |
| Gate Valve | 12 | 400 | RF | handwheel_diameter_mm | 558.800 | 500.000 | +58.800 |
| Gate Valve | 14 | 400 | RF | handwheel_height_mm | 1643.380 | 1645.000 | -1.620 |
| Gate Valve | 14 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 16 | 400 | RF | handwheel_height_mm | 1838.960 | 1841.000 | -2.040 |
| Gate Valve | 16 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 18 | 400 | RF | handwheel_height_mm | 1940.560 | 1943.000 | -2.440 |
| Gate Valve | 18 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 20 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 22 | 400 | RF | handwheel_height_mm | 2336.800 | 2200.000 | +136.800 |
| Gate Valve | 22 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 22 | 400 | RF | weight_kg | 1950.000 | 1500.000 | +450.000 |
| Gate Valve | 24 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 26 | 400 | RF | handwheel_height_mm | 2748.280 | 2750.000 | -1.720 |
| Gate Valve | 26 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 28 | 400 | RF | handwheel_height_mm | 3098.800 | 3100.000 | -1.200 |
| Gate Valve | 28 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 30 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 32 | 400 | RF | handwheel_height_mm | 3558.540 | 3560.000 | -1.460 |
| Gate Valve | 32 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 34 | 400 | RF | handwheel_height_mm | 3810.000 | 3600.000 | +210.000 |
| Gate Valve | 34 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 36 | 400 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 1/2 | 600 | RF | handwheel_height_mm | 302.260 | 304.000 | -1.740 |
| Gate Valve | 1/2 | 600 | RF | handwheel_diameter_mm | 149.860 | 152.000 | -2.140 |
| Gate Valve | 3/4 | 600 | RF | handwheel_height_mm | 327.660 | 330.000 | -2.340 |
| Gate Valve | 3/4 | 600 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Gate Valve | 1 | 600 | RF | handwheel_height_mm | 353.060 | 355.000 | -1.940 |
| Gate Valve | 1 | 600 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 1 1/4 | 600 | RF | handwheel_height_mm | 368.300 | 370.000 | -1.700 |
| Gate Valve | 1 1/4 | 600 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 1 1/2 | 600 | RF | handwheel_height_mm | 398.780 | 400.000 | -1.220 |
| Gate Valve | 2 | 600 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 2 1/2 | 600 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 3 | 600 | RF | handwheel_height_mm | 568.960 | 570.000 | -1.040 |
| Gate Valve | 3 | 600 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Gate Valve | 4 | 600 | RF | handwheel_height_mm | 1254.760 | 1257.000 | -2.240 |
| Gate Valve | 5 | 600 | RF | handwheel_height_mm | 1254.760 | 1257.000 | -2.240 |
| Gate Valve | 6 | 600 | RF | handwheel_diameter_mm | 497.840 | 500.000 | -2.160 |
| Gate Valve | 8 | 600 | RF | handwheel_height_mm | 1099.820 | 1101.000 | -1.180 |
| Gate Valve | 8 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 10 | 600 | RF | handwheel_height_mm | 1277.620 | 1279.000 | -1.380 |
| Gate Valve | 10 | 600 | RF | handwheel_diameter_mm | 637.540 | 640.000 | -2.460 |
| Gate Valve | 12 | 600 | RF | handwheel_height_mm | 1465.580 | 1468.000 | -2.420 |
| Gate Valve | 12 | 600 | RF | handwheel_diameter_mm | 678.180 | 680.000 | -1.820 |
| Gate Valve | 14 | 600 | RF | handwheel_height_mm | 1620.520 | 1623.000 | -2.480 |
| Gate Valve | 16 | 600 | RF | handwheel_height_mm | 1813.560 | 1816.000 | -2.440 |
| Gate Valve | 18 | 600 | RF | handwheel_height_mm | 2258.060 | 2260.000 | -1.940 |
| Gate Valve | 18 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 20 | 600 | RF | handwheel_height_mm | 2702.560 | 2705.000 | -2.440 |
| Gate Valve | 20 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 24 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 26 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 28 | 600 | RF | handwheel_height_mm | 3134.360 | 3136.000 | -1.640 |
| Gate Valve | 28 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 30 | 600 | RF | handwheel_height_mm | 3324.860 | 3327.000 | -2.140 |
| Gate Valve | 30 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 32 | 600 | RF | handwheel_height_mm | 3578.860 | 3581.000 | -2.140 |
| Gate Valve | 32 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 34 | 600 | RF | handwheel_height_mm | 3810.000 | 3750.000 | +60.000 |
| Gate Valve | 34 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 36 | 600 | RF | handwheel_height_mm | 4010.660 | 4013.000 | -2.340 |
| Gate Valve | 36 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 3/4 | 900 | RF | handwheel_height_mm | 358.140 | 360.000 | -1.860 |
| Gate Valve | 3/4 | 900 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Gate Valve | 1 | 900 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Gate Valve | 1 1/4 | 900 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Gate Valve | 1 1/2 | 900 | RF | handwheel_height_mm | 487.680 | 490.000 | -2.320 |
| Gate Valve | 2 1/2 | 900 | RF | handwheel_height_mm | 703.580 | 705.000 | -1.420 |
| Gate Valve | 2 1/2 | 900 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Gate Valve | 3 | 900 | RF | handwheel_height_mm | 693.420 | 695.000 | -1.580 |
| Gate Valve | 3 | 900 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Gate Valve | 4 | 900 | RF | handwheel_height_mm | 822.960 | 825.000 | -2.040 |
| Gate Valve | 4 | 900 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Gate Valve | 5 | 900 | RF | handwheel_height_mm | 822.960 | 825.000 | -2.040 |
| Gate Valve | 5 | 900 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Gate Valve | 5 | 900 | RF | weight_kg | 260.000 | 280.000 | -20.000 |
| Gate Valve | 6 | 900 | RF | handwheel_diameter_mm | 497.840 | 500.000 | -2.160 |
| Gate Valve | 8 | 900 | RF | handwheel_height_mm | 1318.260 | 1320.000 | -1.740 |
| Gate Valve | 12 | 900 | RF | handwheel_height_mm | 1838.960 | 1840.000 | -1.040 |
| Gate Valve | 12 | 900 | RF | handwheel_diameter_mm | 848.360 | 850.000 | -1.640 |
| Gate Valve | 14 | 900 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 16 | 900 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 18 | 900 | RF | handwheel_height_mm | 2230.120 | 2232.000 | -1.880 |
| Gate Valve | 18 | 900 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 20 | 900 | RF | handwheel_height_mm | 2433.320 | 2435.000 | -1.680 |
| Gate Valve | 20 | 900 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Gate Valve | 24 | 900 | RF | handwheel_height_mm | 2862.580 | 2865.000 | -2.420 |
| Gate Valve | 24 | 900 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 1/4 | 150 | RF | handwheel_diameter_mm | 78.740 | 80.000 | -1.260 |
| Globe Valve | 3/8 | 150 | RF | handwheel_diameter_mm | 88.900 | 90.000 | -1.100 |
| Globe Valve | 1/2 | 150 | RF | handwheel_height_mm | 248.920 | 250.000 | -1.080 |
| Globe Valve | 1/2 | 150 | RF | handwheel_diameter_mm | 99.060 | 101.000 | -1.940 |
| Globe Valve | 3/4 | 150 | RF | handwheel_height_mm | 248.920 | 250.000 | -1.080 |
| Globe Valve | 3/4 | 150 | RF | handwheel_diameter_mm | 99.060 | 101.000 | -1.940 |
| Globe Valve | 1 | 150 | RF | handwheel_height_mm | 266.700 | 269.000 | -2.300 |
| Globe Valve | 1 1/4 | 150 | RF | handwheel_diameter_mm | 162.560 | 165.000 | -2.440 |
| Globe Valve | 1 1/2 | 150 | RF | handwheel_height_mm | 327.660 | 330.000 | -2.340 |
| Globe Valve | 1 1/2 | 150 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Globe Valve | 2 | 150 | RF | handwheel_height_mm | 408.940 | 410.000 | -1.060 |
| Globe Valve | 2 | 150 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Globe Valve | 2 1/2 | 150 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Globe Valve | 3 | 150 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Globe Valve | 4 | 150 | RF | handwheel_height_mm | 497.840 | 500.000 | -2.160 |
| Globe Valve | 4 | 150 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Globe Valve | 5 | 150 | RF | handwheel_height_mm | 535.940 | 538.000 | -2.060 |
| Globe Valve | 6 | 150 | RF | handwheel_height_mm | 548.640 | 550.000 | -1.360 |
| Globe Valve | 6 | 150 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Globe Valve | 8 | 150 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 12 | 150 | RF | handwheel_height_mm | 858.520 | 860.000 | -1.480 |
| Globe Valve | 14 | 150 | RF | handwheel_height_mm | 1440.180 | 1442.000 | -1.820 |
| Globe Valve | 14 | 150 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 16 | 150 | RF | handwheel_height_mm | 1623.060 | 1625.000 | -1.940 |
| Globe Valve | 16 | 150 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 1/2 | 2500 | RF | handwheel_diameter_mm | 152.400 | 150.000 | +2.400 |
| Globe Valve | 3/4 | 2500 | RF | handwheel_diameter_mm | 152.400 | 150.000 | +2.400 |
| Globe Valve | 1 | 2500 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Globe Valve | 1 1/4 | 2500 | RF | handwheel_height_mm | 373.380 | 375.000 | -1.620 |
| Globe Valve | 1 1/4 | 2500 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 2 | 2500 | RF | handwheel_height_mm | 698.500 | 700.000 | -1.500 |
| Globe Valve | 2 | 2500 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Globe Valve | 3 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 4 | 2500 | RF | handwheel_height_mm | 1076.960 | 1079.000 | -2.040 |
| Globe Valve | 4 | 2500 | RF | handwheel_diameter_mm | 718.820 | 720.000 | -1.180 |
| Globe Valve | 5 | 2500 | RF | handwheel_height_mm | 1076.960 | 1079.000 | -2.040 |
| Globe Valve | 5 | 2500 | RF | handwheel_diameter_mm | 718.820 | 720.000 | -1.180 |
| Globe Valve | 5 | 2500 | RF | weight_kg | 650.000 | 600.000 | +50.000 |
| Globe Valve | 6 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 8 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 10 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 12 | 2500 | RF | handwheel_height_mm | 2143.760 | 2145.000 | -1.240 |
| Globe Valve | 12 | 2500 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 1/2 | 300 | RF | handwheel_diameter_mm | 149.860 | 152.000 | -2.140 |
| Globe Valve | 3/4 | 300 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Globe Valve | 1 | 300 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 1 1/4 | 300 | RF | handwheel_height_mm | 317.500 | 320.000 | -2.500 |
| Globe Valve | 1 1/4 | 300 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 1 1/2 | 300 | RF | handwheel_height_mm | 388.620 | 390.000 | -1.380 |
| Globe Valve | 1 1/2 | 300 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 2 | 300 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Globe Valve | 2 1/2 | 300 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Globe Valve | 3 | 300 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Globe Valve | 4 | 300 | RF | handwheel_height_mm | 518.160 | 520.000 | -1.840 |
| Globe Valve | 4 | 300 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Globe Valve | 5 | 300 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 6 | 300 | RF | handwheel_height_mm | 647.700 | 650.000 | -2.300 |
| Globe Valve | 6 | 300 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 8 | 300 | RF | handwheel_height_mm | 797.560 | 800.000 | -2.440 |
| Globe Valve | 8 | 300 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 10 | 300 | RF | handwheel_height_mm | 1038.860 | 1040.000 | -1.140 |
| Globe Valve | 10 | 300 | RF | handwheel_diameter_mm | 497.840 | 500.000 | -2.160 |
| Globe Valve | 12 | 300 | RF | handwheel_height_mm | 1137.920 | 1140.000 | -2.080 |
| Globe Valve | 1/2 | 400 | RF | handwheel_diameter_mm | 149.860 | 152.000 | -2.140 |
| Globe Valve | 3/4 | 400 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Globe Valve | 1 | 400 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 1 1/4 | 400 | RF | handwheel_height_mm | 317.500 | 320.000 | -2.500 |
| Globe Valve | 1 1/4 | 400 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 1 1/2 | 400 | RF | handwheel_height_mm | 388.620 | 390.000 | -1.380 |
| Globe Valve | 1 1/2 | 400 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 2 | 400 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Globe Valve | 2 1/2 | 400 | RF | handwheel_diameter_mm | 198.120 | 200.000 | -1.880 |
| Globe Valve | 3 | 400 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Globe Valve | 4 | 400 | RF | handwheel_height_mm | 518.160 | 520.000 | -1.840 |
| Globe Valve | 4 | 400 | RF | handwheel_diameter_mm | 248.920 | 250.000 | -1.080 |
| Globe Valve | 5 | 400 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 6 | 400 | RF | handwheel_height_mm | 647.700 | 650.000 | -2.300 |
| Globe Valve | 6 | 400 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 8 | 400 | RF | handwheel_height_mm | 797.560 | 800.000 | -2.440 |
| Globe Valve | 8 | 400 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 10 | 400 | RF | handwheel_height_mm | 1038.860 | 1040.000 | -1.140 |
| Globe Valve | 10 | 400 | RF | handwheel_diameter_mm | 497.840 | 500.000 | -2.160 |
| Globe Valve | 12 | 400 | RF | handwheel_height_mm | 1137.920 | 1140.000 | -2.080 |
| Globe Valve | 1/2 | 600 | RF | handwheel_height_mm | 302.260 | 304.000 | -1.740 |
| Globe Valve | 1/2 | 600 | RF | handwheel_diameter_mm | 149.860 | 152.000 | -2.140 |
| Globe Valve | 3/4 | 600 | RF | handwheel_height_mm | 327.660 | 330.000 | -2.340 |
| Globe Valve | 3/4 | 600 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Globe Valve | 1 | 600 | RF | handwheel_height_mm | 353.060 | 355.000 | -1.940 |
| Globe Valve | 1 | 600 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 1 1/4 | 600 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 1 1/2 | 600 | RF | handwheel_height_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 2 1/2 | 600 | RF | handwheel_height_mm | 657.860 | 660.000 | -2.140 |
| Globe Valve | 2 1/2 | 600 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Globe Valve | 3 | 600 | RF | handwheel_height_mm | 662.940 | 665.000 | -2.060 |
| Globe Valve | 3 | 600 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Globe Valve | 4 | 600 | RF | handwheel_height_mm | 797.560 | 800.000 | -2.440 |
| Globe Valve | 4 | 600 | RF | handwheel_diameter_mm | 497.840 | 500.000 | -2.160 |
| Globe Valve | 5 | 600 | RF | handwheel_height_mm | 1013.460 | 1015.000 | -1.540 |
| Globe Valve | 5 | 600 | RF | weight_kg | 330.000 | 270.000 | +60.000 |
| Globe Valve | 6 | 600 | RF | handwheel_height_mm | 1143.000 | 1145.000 | -2.000 |
| Globe Valve | 8 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 10 | 600 | RF | handwheel_height_mm | 1409.700 | 1411.000 | -1.300 |
| Globe Valve | 10 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 12 | 600 | RF | handwheel_height_mm | 1465.580 | 1468.000 | -2.420 |
| Globe Valve | 12 | 600 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 3/4 | 900 | RF | handwheel_diameter_mm | 152.400 | 160.000 | -7.600 |
| Globe Valve | 1 | 900 | RF | handwheel_diameter_mm | 175.260 | 177.000 | -1.740 |
| Globe Valve | 1 1/4 | 900 | RF | handwheel_height_mm | 373.380 | 375.000 | -1.620 |
| Globe Valve | 1 1/4 | 900 | RF | handwheel_diameter_mm | 200.660 | 203.000 | -2.340 |
| Globe Valve | 2 1/2 | 900 | RF | handwheel_height_mm | 657.860 | 660.000 | -2.140 |
| Globe Valve | 2 1/2 | 900 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Globe Valve | 3 | 900 | RF | handwheel_height_mm | 662.940 | 665.000 | -2.060 |
| Globe Valve | 3 | 900 | RF | handwheel_diameter_mm | 347.980 | 350.000 | -2.020 |
| Globe Valve | 4 | 900 | RF | handwheel_height_mm | 797.560 | 800.000 | -2.440 |
| Globe Valve | 4 | 900 | RF | handwheel_diameter_mm | 497.840 | 500.000 | -2.160 |
| Globe Valve | 5 | 900 | RF | handwheel_height_mm | 822.960 | 825.000 | -2.040 |
| Globe Valve | 5 | 900 | RF | handwheel_diameter_mm | 398.780 | 400.000 | -1.220 |
| Globe Valve | 5 | 900 | RF | weight_kg | 380.000 | 310.000 | +70.000 |
| Globe Valve | 6 | 900 | RF | handwheel_height_mm | 1013.460 | 1015.000 | -1.540 |
| Globe Valve | 8 | 900 | RF | handwheel_height_mm | 1143.000 | 1145.000 | -2.000 |
| Globe Valve | 10 | 900 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 12 | 900 | RF | handwheel_height_mm | 1409.700 | 1411.000 | -1.300 |
| Globe Valve | 12 | 900 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Globe Valve | 14 | 900 | RF | handwheel_diameter_mm | 558.800 | 560.000 | -1.200 |
| Check Valve | 1/4 | 150 | RF | weight_kg | 2.268 | 5.000 | -2.732 |
| Check Valve | 3/8 | 150 | RF | weight_kg | 2.268 | 5.000 | -2.732 |
| Check Valve | 1/2 | 150 | RF | handwheel_height_mm | 76.200 | 90.000 | -13.800 |
| Check Valve | 1/2 | 150 | RF | weight_kg | 2.268 | 5.000 | -2.732 |
| Check Valve | 3/4 | 150 | RF | handwheel_height_mm | 76.200 | 90.000 | -13.800 |
| Check Valve | 3/4 | 150 | RF | weight_kg | 3.175 | 7.000 | -3.825 |
| Check Valve | 1 | 150 | RF | handwheel_height_mm | 76.200 | 100.000 | -23.800 |
| Check Valve | 1 | 150 | RF | weight_kg | 4.536 | 10.000 | -5.464 |
| Check Valve | 1 1/4 | 150 | RF | weight_kg | 6.804 | 15.000 | -8.196 |
| Check Valve | 1 1/2 | 150 | RF | handwheel_height_mm | 101.600 | 110.000 | -8.400 |
| Check Valve | 1 1/2 | 150 | RF | weight_kg | 6.804 | 15.000 | -8.196 |
| Check Valve | 2 | 150 | RF | handwheel_height_mm | 152.400 | 222.000 | -69.600 |
| Check Valve | 2 | 150 | RF | weight_kg | 7.711 | 17.000 | -9.289 |
| Check Valve | 2 1/2 | 150 | RF | handwheel_height_mm | 152.400 | 165.000 | -12.600 |
| Check Valve | 2 1/2 | 150 | RF | weight_kg | 8.618 | 19.000 | -10.382 |
| Check Valve | 3 | 150 | RF | handwheel_height_mm | 177.800 | 251.000 | -73.200 |
| Check Valve | 3 | 150 | RF | weight_kg | 14.515 | 32.000 | -17.485 |
| Check Valve | 4 | 150 | RF | handwheel_height_mm | 228.600 | 275.000 | -46.400 |
| Check Valve | 4 | 150 | RF | weight_kg | 24.948 | 55.000 | -30.052 |
| Check Valve | 6 | 150 | RF | handwheel_height_mm | 279.400 | 330.000 | -50.600 |
| Check Valve | 6 | 150 | RF | weight_kg | 102.966 | 227.000 | -124.034 |
| Check Valve | 8 | 150 | RF | handwheel_height_mm | 304.800 | 320.000 | -15.200 |
| Check Valve | 8 | 150 | RF | weight_kg | 145.603 | 321.000 | -175.397 |
| Check Valve | 10 | 150 | RF | handwheel_height_mm | 381.000 | 390.000 | -9.000 |
| Check Valve | 10 | 150 | RF | weight_kg | 235.415 | 519.000 | -283.585 |
| Check Valve | 12 | 150 | RF | handwheel_height_mm | 482.600 | 490.000 | -7.400 |
| Check Valve | 12 | 150 | RF | weight_kg | 354.256 | 781.000 | -426.744 |
| Check Valve | 14 | 150 | RF | handwheel_height_mm | 508.000 | 515.000 | -7.000 |
| Check Valve | 14 | 150 | RF | weight_kg | 462.665 | 1020.000 | -557.335 |
| Check Valve | 16 | 150 | RF | handwheel_height_mm | 533.400 | 555.000 | -21.600 |
| Check Valve | 16 | 150 | RF | weight_kg | 635.484 | 1401.000 | -765.516 |
| Check Valve | 18 | 150 | RF | handwheel_height_mm | 584.200 | 600.000 | -15.800 |
| Check Valve | 18 | 150 | RF | weight_kg | 940.298 | 2073.000 | -1132.702 |
| Check Valve | 20 | 150 | RF | handwheel_height_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 20 | 150 | RF | weight_kg | 1034.192 | 2280.000 | -1245.808 |
| Check Valve | 22 | 150 | RF | handwheel_height_mm | 685.800 | 700.000 | -14.200 |
| Check Valve | 22 | 150 | RF | weight_kg | 1241.937 | 2738.000 | -1496.063 |
| Check Valve | 24 | 150 | RF | handwheel_height_mm | 736.600 | 750.000 | -13.400 |
| Check Valve | 24 | 150 | RF | weight_kg | 1847.938 | 4074.000 | -2226.062 |
| Check Valve | 26 | 150 | RF | handwheel_height_mm | 787.400 | 800.000 | -12.600 |
| Check Valve | 26 | 150 | RF | weight_kg | 2513.358 | 5541.000 | -3027.642 |
| Check Valve | 28 | 150 | RF | handwheel_height_mm | 838.200 | 850.000 | -11.800 |
| Check Valve | 28 | 150 | RF | weight_kg | 2948.354 | 6500.000 | -3551.646 |
| Check Valve | 30 | 150 | RF | handwheel_height_mm | 889.000 | 900.000 | -11.000 |
| Check Valve | 30 | 150 | RF | weight_kg | 3446.853 | 7599.000 | -4152.147 |
| Check Valve | 32 | 150 | RF | handwheel_height_mm | 939.800 | 950.000 | -10.200 |
| Check Valve | 34 | 150 | RF | handwheel_height_mm | 990.600 | 1000.000 | -9.400 |
| Check Valve | 36 | 150 | RF | handwheel_height_mm | 1041.400 | 1050.000 | -8.600 |
| Check Valve | 1/2 | 2500 | RF | weight_kg | 2.268 | 5.000 | -2.732 |
| Check Valve | 3/4 | 2500 | RF | weight_kg | 2.268 | 5.000 | -2.732 |
| Check Valve | 1 | 2500 | RF | weight_kg | 3.175 | 7.000 | -3.825 |
| Check Valve | 1 1/4 | 2500 | RF | weight_kg | 21.772 | 12.000 | +9.772 |
| Check Valve | 1 1/2 | 2500 | RF | weight_kg | 31.752 | 14.000 | +17.752 |
| Check Valve | 2 | 2500 | RF | handwheel_height_mm | 203.200 | 215.000 | -11.800 |
| Check Valve | 2 | 2500 | RF | weight_kg | 39.916 | 88.000 | -48.084 |
| Check Valve | 2 1/2 | 2500 | RF | handwheel_height_mm | 203.200 | 215.000 | -11.800 |
| Check Valve | 2 1/2 | 2500 | RF | weight_kg | 54.431 | 120.000 | -65.569 |
| Check Valve | 3 | 2500 | RF | handwheel_height_mm | 254.000 | 265.000 | -11.000 |
| Check Valve | 3 | 2500 | RF | weight_kg | 111.584 | 246.000 | -134.416 |
| Check Valve | 4 | 2500 | RF | handwheel_height_mm | 304.800 | 311.000 | -6.200 |
| Check Valve | 4 | 2500 | RF | weight_kg | 151.500 | 334.000 | -182.500 |
| Check Valve | 6 | 2500 | RF | weight_kg | 400.976 | 884.000 | -483.024 |
| Check Valve | 8 | 2500 | RF | handwheel_diameter_mm | 1117.600 | 1120.000 | -2.400 |
| Check Valve | 8 | 2500 | RF | weight_kg | 842.776 | 1858.000 | -1015.224 |
| Check Valve | 10 | 2500 | RF | handwheel_diameter_mm | 1117.600 | 1120.000 | -2.400 |
| Check Valve | 10 | 2500 | RF | weight_kg | 1531.330 | 3376.000 | -1844.670 |
| Check Valve | 12 | 2500 | RF | handwheel_diameter_mm | 1117.600 | 1120.000 | -2.400 |
| Check Valve | 12 | 2500 | RF | weight_kg | 2067.023 | 4551.000 | -2483.977 |
| Check Valve | 1/2 | 300 | RF | handwheel_height_mm | 76.200 | 85.000 | -8.800 |
| Check Valve | 1/2 | 300 | RF | weight_kg | 2.722 | 6.000 | -3.278 |
| Check Valve | 3/4 | 300 | RF | handwheel_height_mm | 76.200 | 85.000 | -8.800 |
| Check Valve | 3/4 | 300 | RF | weight_kg | 3.175 | 7.000 | -3.825 |
| Check Valve | 1 | 300 | RF | handwheel_height_mm | 101.600 | 111.000 | -9.400 |
| Check Valve | 1 | 300 | RF | weight_kg | 4.536 | 10.000 | -5.464 |
| Check Valve | 1 1/4 | 300 | RF | handwheel_height_mm | 101.600 | 114.000 | -12.400 |
| Check Valve | 1 1/4 | 300 | RF | weight_kg | 5.443 | 12.000 | -6.557 |
| Check Valve | 1 1/2 | 300 | RF | handwheel_height_mm | 101.600 | 117.000 | -15.400 |
| Check Valve | 1 1/2 | 300 | RF | weight_kg | 6.350 | 14.000 | -7.650 |
| Check Valve | 2 | 300 | RF | handwheel_height_mm | 152.400 | 194.000 | -41.600 |
| Check Valve | 2 | 300 | RF | weight_kg | 9.072 | 20.000 | -10.928 |
| Check Valve | 2 1/2 | 300 | RF | handwheel_height_mm | 152.400 | 165.000 | -12.600 |
| Check Valve | 2 1/2 | 300 | RF | weight_kg | 12.247 | 27.000 | -14.753 |
| Check Valve | 3 | 300 | RF | handwheel_height_mm | 177.800 | 251.000 | -73.200 |
| Check Valve | 3 | 300 | RF | weight_kg | 19.504 | 43.000 | -23.496 |
| Check Valve | 4 | 300 | RF | handwheel_height_mm | 228.600 | 275.000 | -46.400 |
| Check Valve | 4 | 300 | RF | weight_kg | 34.473 | 76.000 | -41.527 |
| Check Valve | 6 | 300 | RF | handwheel_height_mm | 279.400 | 285.000 | -5.600 |
| Check Valve | 6 | 300 | RF | weight_kg | 114.759 | 253.000 | -138.241 |
| Check Valve | 8 | 300 | RF | handwheel_height_mm | 304.800 | 325.000 | -20.200 |
| Check Valve | 8 | 300 | RF | weight_kg | 166.469 | 367.000 | -200.531 |
| Check Valve | 10 | 300 | RF | handwheel_height_mm | 381.000 | 393.000 | -12.000 |
| Check Valve | 10 | 300 | RF | weight_kg | 257.187 | 567.000 | -309.813 |
| Check Valve | 12 | 300 | RF | handwheel_height_mm | 482.600 | 488.000 | -5.400 |
| Check Valve | 12 | 300 | RF | weight_kg | 395.987 | 873.000 | -477.013 |
| Check Valve | 14 | 300 | RF | handwheel_height_mm | 508.000 | 515.000 | -7.000 |
| Check Valve | 14 | 300 | RF | weight_kg | 520.725 | 1148.000 | -627.275 |
| Check Valve | 16 | 300 | RF | handwheel_height_mm | 533.400 | 552.000 | -18.600 |
| Check Valve | 16 | 300 | RF | weight_kg | 716.223 | 1579.000 | -862.777 |
| Check Valve | 18 | 300 | RF | handwheel_height_mm | 584.200 | 600.000 | -15.800 |
| Check Valve | 18 | 300 | RF | weight_kg | 521.178 | 1149.000 | -627.822 |
| Check Valve | 20 | 300 | RF | handwheel_height_mm | 584.200 | 600.000 | -15.800 |
| Check Valve | 20 | 300 | RF | weight_kg | 859.559 | 1895.000 | -1035.441 |
| Check Valve | 22 | 300 | RF | weight_kg | 1145.322 | 2525.000 | -1379.678 |
| Check Valve | 24 | 300 | RF | handwheel_height_mm | 584.200 | 600.000 | -15.800 |
| Check Valve | 24 | 300 | RF | weight_kg | 2032.550 | 4481.000 | -2448.450 |
| Check Valve | 26 | 300 | RF | handwheel_height_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 26 | 300 | RF | weight_kg | 2735.619 | 6031.000 | -3295.381 |
| Check Valve | 28 | 300 | RF | handwheel_height_mm | 685.800 | 700.000 | -14.200 |
| Check Valve | 28 | 300 | RF | weight_kg | 3175.150 | 7000.000 | -3824.850 |
| Check Valve | 30 | 300 | RF | handwheel_height_mm | 736.600 | 750.000 | -13.400 |
| Check Valve | 30 | 300 | RF | weight_kg | 3727.173 | 8217.000 | -4489.827 |
| Check Valve | 32 | 300 | RF | handwheel_height_mm | 787.400 | 800.000 | -12.600 |
| Check Valve | 34 | 300 | RF | handwheel_height_mm | 838.200 | 850.000 | -11.800 |
| Check Valve | 36 | 300 | RF | handwheel_height_mm | 889.000 | 900.000 | -11.000 |
| Check Valve | 1/2 | 400 | RF | handwheel_height_mm | 76.200 | 85.000 | -8.800 |
| Check Valve | 1/2 | 400 | RF | weight_kg | 2.722 | 6.000 | -3.278 |
| Check Valve | 3/4 | 400 | RF | handwheel_height_mm | 76.200 | 85.000 | -8.800 |
| Check Valve | 3/4 | 400 | RF | weight_kg | 3.175 | 7.000 | -3.825 |
| Check Valve | 1 | 400 | RF | handwheel_height_mm | 101.600 | 111.000 | -9.400 |
| Check Valve | 1 | 400 | RF | weight_kg | 4.536 | 10.000 | -5.464 |
| Check Valve | 1 1/4 | 400 | RF | handwheel_height_mm | 101.600 | 114.000 | -12.400 |
| Check Valve | 1 1/4 | 400 | RF | weight_kg | 5.443 | 12.000 | -6.557 |
| Check Valve | 1 1/2 | 400 | RF | handwheel_height_mm | 101.600 | 117.000 | -15.400 |
| Check Valve | 1 1/2 | 400 | RF | weight_kg | 6.350 | 14.000 | -7.650 |
| Check Valve | 2 | 400 | RF | handwheel_height_mm | 152.400 | 194.000 | -41.600 |
| Check Valve | 2 | 400 | RF | weight_kg | 9.072 | 20.000 | -10.928 |
| Check Valve | 2 1/2 | 400 | RF | handwheel_height_mm | 152.400 | 165.000 | -12.600 |
| Check Valve | 2 1/2 | 400 | RF | weight_kg | 12.247 | 27.000 | -14.753 |
| Check Valve | 3 | 400 | RF | handwheel_height_mm | 177.800 | 251.000 | -73.200 |
| Check Valve | 3 | 400 | RF | weight_kg | 19.504 | 43.000 | -23.496 |
| Check Valve | 4 | 400 | RF | handwheel_height_mm | 228.600 | 275.000 | -46.400 |
| Check Valve | 4 | 400 | RF | weight_kg | 34.473 | 76.000 | -41.527 |
| Check Valve | 6 | 400 | RF | handwheel_height_mm | 279.400 | 285.000 | -5.600 |
| Check Valve | 6 | 400 | RF | weight_kg | 114.759 | 253.000 | -138.241 |
| Check Valve | 8 | 400 | RF | handwheel_height_mm | 304.800 | 325.000 | -20.200 |
| Check Valve | 8 | 400 | RF | weight_kg | 166.469 | 367.000 | -200.531 |
| Check Valve | 10 | 400 | RF | handwheel_height_mm | 381.000 | 393.000 | -12.000 |
| Check Valve | 10 | 400 | RF | weight_kg | 257.187 | 567.000 | -309.813 |
| Check Valve | 12 | 400 | RF | handwheel_height_mm | 482.600 | 488.000 | -5.400 |
| Check Valve | 12 | 400 | RF | weight_kg | 395.987 | 873.000 | -477.013 |
| Check Valve | 14 | 400 | RF | handwheel_height_mm | 508.000 | 515.000 | -7.000 |
| Check Valve | 14 | 400 | RF | weight_kg | 520.725 | 1148.000 | -627.275 |
| Check Valve | 16 | 400 | RF | handwheel_height_mm | 533.400 | 552.000 | -18.600 |
| Check Valve | 16 | 400 | RF | weight_kg | 716.223 | 1579.000 | -862.777 |
| Check Valve | 18 | 400 | RF | handwheel_height_mm | 584.200 | 600.000 | -15.800 |
| Check Valve | 18 | 400 | RF | weight_kg | 521.178 | 1149.000 | -627.822 |
| Check Valve | 20 | 400 | RF | handwheel_height_mm | 584.200 | 600.000 | -15.800 |
| Check Valve | 20 | 400 | RF | weight_kg | 859.559 | 1895.000 | -1035.441 |
| Check Valve | 22 | 400 | RF | weight_kg | 1145.322 | 2525.000 | -1379.678 |
| Check Valve | 24 | 400 | RF | handwheel_height_mm | 584.200 | 600.000 | -15.800 |
| Check Valve | 24 | 400 | RF | weight_kg | 2032.550 | 4481.000 | -2448.450 |
| Check Valve | 26 | 400 | RF | handwheel_height_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 26 | 400 | RF | weight_kg | 2735.619 | 6031.000 | -3295.381 |
| Check Valve | 28 | 400 | RF | handwheel_height_mm | 685.800 | 700.000 | -14.200 |
| Check Valve | 28 | 400 | RF | weight_kg | 3175.150 | 7000.000 | -3824.850 |
| Check Valve | 30 | 400 | RF | handwheel_height_mm | 736.600 | 750.000 | -13.400 |
| Check Valve | 30 | 400 | RF | weight_kg | 3727.173 | 8217.000 | -4489.827 |
| Check Valve | 32 | 400 | RF | handwheel_height_mm | 787.400 | 800.000 | -12.600 |
| Check Valve | 34 | 400 | RF | handwheel_height_mm | 838.200 | 850.000 | -11.800 |
| Check Valve | 36 | 400 | RF | handwheel_height_mm | 889.000 | 900.000 | -11.000 |
| Check Valve | 1/2 | 600 | RF | handwheel_height_mm | 50.800 | 65.000 | -14.200 |
| Check Valve | 1/2 | 600 | RF | weight_kg | 2.268 | 5.000 | -2.732 |
| Check Valve | 3/4 | 600 | RF | handwheel_height_mm | 76.200 | 75.000 | +1.200 |
| Check Valve | 3/4 | 600 | RF | weight_kg | 4.536 | 10.000 | -5.464 |
| Check Valve | 1 | 600 | RF | handwheel_height_mm | 76.200 | 80.000 | -3.800 |
| Check Valve | 1 | 600 | RF | weight_kg | 6.804 | 15.000 | -8.196 |
| Check Valve | 1 1/4 | 600 | RF | weight_kg | 11.793 | 26.000 | -14.207 |
| Check Valve | 1 1/2 | 600 | RF | handwheel_height_mm | 101.600 | 120.000 | -18.400 |
| Check Valve | 1 1/2 | 600 | RF | weight_kg | 12.247 | 27.000 | -14.753 |
| Check Valve | 2 | 600 | RF | handwheel_height_mm | 152.400 | 165.000 | -12.600 |
| Check Valve | 2 | 600 | RF | weight_kg | 11.340 | 25.000 | -13.660 |
| Check Valve | 2 1/2 | 600 | RF | handwheel_height_mm | 152.400 | 165.000 | -12.600 |
| Check Valve | 2 1/2 | 600 | RF | weight_kg | 16.329 | 36.000 | -19.671 |
| Check Valve | 3 | 600 | RF | handwheel_height_mm | 177.800 | 180.000 | -2.200 |
| Check Valve | 3 | 600 | RF | weight_kg | 22.226 | 49.000 | -26.774 |
| Check Valve | 4 | 600 | RF | handwheel_height_mm | 228.600 | 230.000 | -1.400 |
| Check Valve | 4 | 600 | RF | weight_kg | 44.452 | 98.000 | -53.548 |
| Check Valve | 6 | 600 | RF | handwheel_height_mm | 279.400 | 285.000 | -5.600 |
| Check Valve | 6 | 600 | RF | weight_kg | 132.449 | 292.000 | -159.551 |
| Check Valve | 8 | 600 | RF | handwheel_height_mm | 330.200 | 350.000 | -19.800 |
| Check Valve | 8 | 600 | RF | weight_kg | 261.723 | 577.000 | -315.277 |
| Check Valve | 10 | 600 | RF | handwheel_height_mm | 431.800 | 450.000 | -18.200 |
| Check Valve | 10 | 600 | RF | weight_kg | 366.503 | 808.000 | -441.497 |
| Check Valve | 12 | 600 | RF | handwheel_height_mm | 482.600 | 490.000 | -7.400 |
| Check Valve | 12 | 600 | RF | weight_kg | 607.361 | 1339.000 | -731.639 |
| Check Valve | 14 | 600 | RF | handwheel_height_mm | 457.200 | 460.000 | -2.800 |
| Check Valve | 14 | 600 | RF | weight_kg | 822.818 | 1814.000 | -991.182 |
| Check Valve | 16 | 600 | RF | handwheel_height_mm | 482.600 | 500.000 | -17.400 |
| Check Valve | 16 | 600 | RF | weight_kg | 886.774 | 1955.000 | -1068.226 |
| Check Valve | 18 | 600 | RF | handwheel_height_mm | 558.800 | 575.000 | -16.200 |
| Check Valve | 18 | 600 | RF | weight_kg | 1354.428 | 2986.000 | -1631.572 |
| Check Valve | 20 | 600 | RF | handwheel_height_mm | 609.600 | 615.000 | -5.400 |
| Check Valve | 20 | 600 | RF | weight_kg | 1479.166 | 3261.000 | -1781.834 |
| Check Valve | 24 | 600 | RF | handwheel_height_mm | 660.400 | 680.000 | -19.600 |
| Check Valve | 24 | 600 | RF | handwheel_diameter_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 26 | 600 | RF | handwheel_height_mm | 736.600 | 750.000 | -13.400 |
| Check Valve | 26 | 600 | RF | handwheel_diameter_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 28 | 600 | RF | handwheel_height_mm | 787.400 | 800.000 | -12.600 |
| Check Valve | 28 | 600 | RF | handwheel_diameter_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 30 | 600 | RF | handwheel_height_mm | 838.200 | 850.000 | -11.800 |
| Check Valve | 30 | 600 | RF | handwheel_diameter_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 32 | 600 | RF | handwheel_height_mm | 889.000 | 900.000 | -11.000 |
| Check Valve | 32 | 600 | RF | handwheel_diameter_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 36 | 600 | RF | handwheel_height_mm | 990.600 | 1000.000 | -9.400 |
| Check Valve | 36 | 600 | RF | handwheel_diameter_mm | 635.000 | 650.000 | -15.000 |
| Check Valve | 3/4 | 900 | RF | weight_kg | 4.082 | 9.000 | -4.918 |
| Check Valve | 1 | 900 | RF | weight_kg | 5.443 | 12.000 | -6.557 |
| Check Valve | 1 1/4 | 900 | RF | weight_kg | 19.958 | 44.000 | -24.042 |
| Check Valve | 1 1/2 | 900 | RF | weight_kg | 20.865 | 46.000 | -25.135 |
| Check Valve | 2 | 900 | RF | weight_kg | 22.226 | 49.000 | -26.774 |
| Check Valve | 2 1/2 | 900 | RF | weight_kg | 34.019 | 75.000 | -40.981 |
| Check Valve | 3 | 900 | RF | handwheel_height_mm | 228.600 | 230.000 | -1.400 |
| Check Valve | 3 | 900 | RF | weight_kg | 34.019 | 75.000 | -40.981 |
| Check Valve | 4 | 900 | RF | handwheel_height_mm | 254.000 | 265.000 | -11.000 |
| Check Valve | 4 | 900 | RF | weight_kg | 64.410 | 142.000 | -77.590 |
| Check Valve | 6 | 900 | RF | handwheel_height_mm | 355.600 | 370.000 | -14.400 |
| Check Valve | 6 | 900 | RF | weight_kg | 150.593 | 332.000 | -181.407 |
| Check Valve | 8 | 900 | RF | handwheel_height_mm | 406.400 | 415.000 | -8.600 |
| Check Valve | 8 | 900 | RF | weight_kg | 303.454 | 669.000 | -365.546 |
| Check Valve | 10 | 900 | RF | handwheel_height_mm | 584.200 | 600.000 | -15.800 |
| Check Valve | 10 | 900 | RF | weight_kg | 403.244 | 889.000 | -485.756 |
| Check Valve | 12 | 900 | RF | handwheel_height_mm | 660.400 | 685.000 | -24.600 |
| Check Valve | 12 | 900 | RF | weight_kg | 690.822 | 1523.000 | -832.178 |
| Check Valve | 14 | 900 | RF | handwheel_height_mm | 787.400 | 785.000 | +2.400 |
| Check Valve | 14 | 900 | RF | handwheel_diameter_mm | 1092.200 | 1120.000 | -27.800 |
| Check Valve | 14 | 900 | RF | weight_kg | 948.916 | 2092.000 | -1143.084 |
| Check Valve | 16 | 900 | RF | handwheel_height_mm | 863.600 | 860.000 | +3.600 |
| Check Valve | 16 | 900 | RF | handwheel_diameter_mm | 1092.200 | 1120.000 | -27.800 |
| Check Valve | 16 | 900 | RF | weight_kg | 1061.861 | 2341.000 | -1279.139 |
| Check Valve | 18 | 900 | RF | handwheel_height_mm | 1016.000 | 1000.000 | +16.000 |
| Check Valve | 18 | 900 | RF | handwheel_diameter_mm | 1092.200 | 1120.000 | -27.800 |
| Check Valve | 18 | 900 | RF | weight_kg | 1494.135 | 3294.000 | -1799.865 |
| Check Valve | 20 | 900 | RF | handwheel_height_mm | 1219.200 | 1245.000 | -25.800 |
| Check Valve | 20 | 900 | RF | handwheel_diameter_mm | 1092.200 | 1120.000 | -27.800 |
| Check Valve | 24 | 900 | RF | handwheel_height_mm | 1219.200 | 1245.000 | -25.800 |
| Check Valve | 24 | 900 | RF | handwheel_diameter_mm | 1092.200 | 1120.000 | -27.800 |
| Ball Valve | 1/2 | 150 | RF | weight_kg | 2.268 | 5.000 | -2.732 |
| Ball Valve | 3/4 | 150 | RF | weight_kg | 3.402 | 7.500 | -4.098 |
| Ball Valve | 1 | 150 | RF | weight_kg | 4.536 | 10.000 | -5.464 |
| Ball Valve | 1 1/4 | 150 | RF | weight_kg | 6.350 | 14.000 | -7.650 |
| Ball Valve | 1 1/2 | 150 | RF | weight_kg | 8.165 | 18.000 | -9.835 |
| Ball Valve | 2 | 150 | RF | weight_kg | 10.705 | 23.600 | -12.895 |
| Ball Valve | 2 1/2 | 150 | RF | weight_kg | 19.504 | 43.000 | -23.496 |
| Ball Valve | 3 | 150 | RF | weight_kg | 19.504 | 43.000 | -23.496 |
| Ball Valve | 4 | 150 | RF | weight_kg | 27.760 | 61.200 | -33.440 |
| Ball Valve | 6 | 150 | RF | weight_kg | 49.351 | 108.800 | -59.449 |
| Ball Valve | 8 | 150 | RF | weight_kg | 77.338 | 170.500 | -93.162 |
| Ball Valve | 10 | 150 | RF | weight_kg | 125.464 | 276.600 | -151.136 |
| Ball Valve | 12 | 150 | RF | weight_kg | 174.860 | 385.500 | -210.640 |
| Ball Valve | 1/2 | 2500 | RF | weight_kg | 13.608 | 30.000 | -16.392 |
| Ball Valve | 3/4 | 2500 | RF | weight_kg | 18.144 | 40.000 | -21.856 |
| Ball Valve | 1 | 2500 | RF | weight_kg | 27.216 | 60.000 | -32.784 |
| Ball Valve | 1 1/4 | 2500 | RF | weight_kg | 34.019 | 75.000 | -40.981 |
| Ball Valve | 1 1/2 | 2500 | RF | weight_kg | 40.823 | 90.000 | -49.177 |
| Ball Valve | 2 | 2500 | RF | weight_kg | 54.431 | 120.000 | -65.569 |
| Ball Valve | 2 1/2 | 2500 | RF | weight_kg | 68.039 | 150.000 | -81.961 |
| Ball Valve | 3 | 2500 | RF | weight_kg | 63.503 | 140.000 | -76.497 |
| Ball Valve | 4 | 2500 | RF | handwheel_height_mm | 1117.600 | 1123.000 | -5.400 |
| Ball Valve | 4 | 2500 | RF | weight_kg | 102.058 | 225.000 | -122.942 |
| Ball Valve | 6 | 2500 | RF | handwheel_height_mm | 1181.100 | 1184.000 | -2.900 |
| Ball Valve | 6 | 2500 | RF | weight_kg | 349.267 | 770.000 | -420.733 |
| Ball Valve | 1/2 | 300 | RF | weight_kg | 2.948 | 6.500 | -3.552 |
| Ball Valve | 3/4 | 300 | RF | weight_kg | 3.402 | 7.500 | -4.098 |
| Ball Valve | 1 | 300 | RF | weight_kg | 4.944 | 10.900 | -5.956 |
| Ball Valve | 1 1/2 | 300 | RF | weight_kg | 12.156 | 26.800 | -14.644 |
| Ball Valve | 2 | 300 | RF | weight_kg | 13.971 | 30.800 | -16.829 |
| Ball Valve | 3 | 300 | RF | weight_kg | 28.803 | 63.500 | -34.697 |
| Ball Valve | 4 | 300 | RF | weight_kg | 43.182 | 95.200 | -52.018 |
| Ball Valve | 6 | 300 | RF | weight_kg | 88.451 | 195.000 | -106.549 |
| Ball Valve | 8 | 300 | RF | weight_kg | 129.591 | 285.700 | -156.109 |
| Ball Valve | 10 | 300 | RF | weight_kg | 205.704 | 453.500 | -247.796 |
| Ball Valve | 12 | 300 | RF | weight_kg | 315.973 | 696.600 | -380.627 |
| Ball Valve | 1/2 | 400 | RF | weight_kg | 2.948 | 6.500 | -3.552 |
| Ball Valve | 3/4 | 400 | RF | weight_kg | 3.402 | 7.500 | -4.098 |
| Ball Valve | 1 | 400 | RF | weight_kg | 4.944 | 10.900 | -5.956 |
| Ball Valve | 1 1/2 | 400 | RF | weight_kg | 12.156 | 26.800 | -14.644 |
| Ball Valve | 2 | 400 | RF | weight_kg | 13.971 | 30.800 | -16.829 |
| Ball Valve | 3 | 400 | RF | weight_kg | 28.803 | 63.500 | -34.697 |
| Ball Valve | 4 | 400 | RF | weight_kg | 43.182 | 95.200 | -52.018 |
| Ball Valve | 6 | 400 | RF | weight_kg | 88.451 | 195.000 | -106.549 |
| Ball Valve | 8 | 400 | RF | weight_kg | 129.591 | 285.700 | -156.109 |
| Ball Valve | 10 | 400 | RF | weight_kg | 205.704 | 453.500 | -247.796 |
| Ball Valve | 12 | 400 | RF | weight_kg | 315.973 | 696.600 | -380.627 |
| Ball Valve | 1/2 | 600 | RF | weight_kg | 2.268 | 5.000 | -2.732 |
| Ball Valve | 3/4 | 600 | RF | weight_kg | 4.536 | 10.000 | -5.464 |
| Ball Valve | 1 | 600 | RF | weight_kg | 6.985 | 15.400 | -8.415 |
| Ball Valve | 1 1/4 | 600 | RF | weight_kg | 9.072 | 20.000 | -10.928 |
| Ball Valve | 1 1/2 | 600 | RF | weight_kg | 12.338 | 27.200 | -14.862 |
| Ball Valve | 2 | 600 | RF | weight_kg | 19.504 | 43.000 | -23.496 |
| Ball Valve | 2 1/2 | 600 | RF | weight_kg | 28.123 | 62.000 | -33.877 |
| Ball Valve | 3 | 600 | RF | weight_kg | 36.741 | 81.000 | -44.259 |
| Ball Valve | 4 | 600 | RF | weight_kg | 68.946 | 152.000 | -83.054 |
| Ball Valve | 6 | 600 | RF | weight_kg | 148.778 | 328.000 | -179.222 |
| Ball Valve | 8 | 600 | RF | weight_kg | 256.734 | 566.000 | -309.266 |
| Ball Valve | 10 | 600 | RF | weight_kg | 386.461 | 852.000 | -465.539 |
| Ball Valve | 1 | 900 | RF | weight_kg | 17.463 | 38.500 | -21.037 |
| Ball Valve | 1 1/4 | 900 | RF | weight_kg | 22.226 | 49.000 | -26.774 |
| Ball Valve | 1 1/2 | 900 | RF | weight_kg | 27.760 | 61.200 | -33.440 |
| Ball Valve | 2 | 900 | RF | weight_kg | 38.056 | 83.900 | -45.844 |
| Ball Valve | 2 1/2 | 900 | RF | weight_kg | 44.906 | 99.000 | -54.094 |
| Ball Valve | 3 | 900 | RF | weight_kg | 52.662 | 116.100 | -63.438 |
| Ball Valve | 4 | 900 | RF | weight_kg | 90.492 | 199.500 | -109.008 |
| Ball Valve | 6 | 900 | RF | weight_kg | 185.157 | 408.200 | -223.043 |
| Butterfly Valve | 22 | 150 | RF | weight_kg | 1200.000 | 1580.000 | -380.000 |
| Butterfly Valve | 5 | 2500 | RF | weight_kg | 650.000 | 660.000 | -10.000 |
| Butterfly Valve | 5 | 600 | RF | weight_kg | 0.000 | 170.000 | -170.000 |
| Butterfly Valve | 22 | 600 | RF | weight_kg | 2700.000 | 3000.000 | -300.000 |
| Butterfly Valve | 5 | 900 | RF | weight_kg | 190.000 | 220.000 | -30.000 |
| Valve | 5 | 150 | RF | handwheel_height_mm | 224.790 | 320.000 | -95.210 |
| Valve | 5 | 150 | RF | handwheel_height_mm | 334.000 | 320.000 | +14.000 |
| Valve | 5 | 150 | RF | weight_kg | 15.900 | 13.180 | +2.720 |
| Valve | 5 | 150 | RF | handwheel_height_mm | 224.790 | 320.000 | -95.210 |
| Valve | 5 | 150 | RF | weight_kg | 15.900 | 13.180 | +2.720 |
| Valve | 12 | 150 | RF | handwheel_height_mm | 345.440 | 540.000 | -194.560 |
| Valve | 12 | 150 | RF | handwheel_height_mm | 572.000 | 540.000 | +32.000 |
| Valve | 12 | 150 | RF | weight_kg | 62.270 | 48.630 | +13.640 |
| Valve | 12 | 150 | RF | handwheel_height_mm | 344.932 | 540.000 | -195.068 |
| Valve | 12 | 150 | RF | weight_kg | 62.270 | 48.630 | +13.640 |
| Valve | 3 | 2500 | RF | weight_kg | 18.000 | 12.270 | +5.730 |
| Valve | 3 | 2500 | RF | weight_kg | 18.000 | 12.270 | +5.730 |
| Valve | 4 | 2500 | RF | weight_kg | 26.000 | 18.180 | +7.820 |
| Valve | 4 | 2500 | RF | weight_kg | 26.000 | 18.180 | +7.820 |
| Valve | 6 | 2500 | RF | weight_kg | 55.000 | 30.900 | +24.100 |
| Valve | 6 | 2500 | RF | weight_kg | 55.000 | 30.900 | +24.100 |
| Valve | 8 | 2500 | RF | weight_kg | 100.000 | 57.720 | +42.280 |
| Valve | 8 | 2500 | RF | weight_kg | 100.000 | 57.720 | +42.280 |
| Valve | 10 | 2500 | RF | weight_kg | 181.000 | 113.180 | +67.820 |
| Valve | 10 | 2500 | RF | weight_kg | 181.000 | 113.180 | +67.820 |
| Valve | 12 | 2500 | RF | weight_kg | 264.000 | 153.180 | +110.820 |
| Valve | 12 | 2500 | RF | weight_kg | 264.000 | 153.180 | +110.820 |
| Valve | 3 | 300 | RF | handwheel_diameter_mm | 228.600 | 230.000 | -1.400 |
| Valve | 3 | 300 | RF | handwheel_height_mm | 295.000 | 289.000 | +6.000 |
| Valve | 3 | 300 | RF | weight_kg | 11.360 | 6.810 | +4.550 |
| Valve | 3 | 300 | RF | handwheel_height_mm | 294.894 | 289.000 | +5.894 |
| Valve | 3 | 300 | RF | weight_kg | 11.360 | 6.810 | +4.550 |
| Valve | 4 | 300 | RF | handwheel_diameter_mm | 228.600 | 230.000 | -1.400 |
| Valve | 4 | 300 | RF | handwheel_height_mm | 348.000 | 342.000 | +6.000 |
| Valve | 4 | 300 | RF | weight_kg | 17.720 | 10.900 | +6.820 |
| Valve | 4 | 300 | RF | handwheel_height_mm | 347.980 | 342.000 | +5.980 |
| Valve | 4 | 300 | RF | weight_kg | 17.720 | 10.900 | +6.820 |
| Valve | 6 | 300 | RF | handwheel_diameter_mm | 228.600 | 230.000 | -1.400 |
| Valve | 6 | 300 | RF | handwheel_height_mm | 412.000 | 407.000 | +5.000 |
| Valve | 6 | 300 | RF | weight_kg | 28.180 | 15.900 | +12.280 |
| Valve | 6 | 300 | RF | handwheel_height_mm | 411.988 | 407.000 | +4.988 |
| Valve | 6 | 300 | RF | weight_kg | 28.180 | 15.900 | +12.280 |
| Valve | 8 | 300 | RF | handwheel_diameter_mm | 228.600 | 230.000 | -1.400 |
| Valve | 8 | 300 | RF | handwheel_height_mm | 480.000 | 473.000 | +7.000 |
| Valve | 8 | 300 | RF | weight_kg | 42.270 | 26.810 | +15.460 |
| Valve | 8 | 300 | RF | handwheel_height_mm | 479.806 | 473.000 | +6.806 |
| Valve | 8 | 300 | RF | weight_kg | 42.270 | 26.810 | +15.460 |
| Valve | 10 | 300 | RF | handwheel_diameter_mm | 228.600 | 230.000 | -1.400 |
| Valve | 10 | 300 | RF | handwheel_height_mm | 560.000 | 585.000 | -25.000 |
| Valve | 10 | 300 | RF | weight_kg | 78.180 | 49.090 | +29.090 |
| Valve | 10 | 300 | RF | handwheel_height_mm | 559.816 | 585.000 | -25.184 |
| Valve | 10 | 300 | RF | weight_kg | 78.180 | 49.090 | +29.090 |
| Valve | 12 | 300 | RF | handwheel_diameter_mm | 228.600 | 230.000 | -1.400 |
| Valve | 12 | 300 | RF | weight_kg | 130.900 | 71.810 | +59.090 |
| Valve | 12 | 300 | RF | weight_kg | 130.900 | 71.810 | +59.090 |
| Valve | 3 | 400 | RF | handwheel_height_mm | 295.000 | 289.000 | +6.000 |
| Valve | 3 | 400 | RF | weight_kg | 11.360 | 6.810 | +4.550 |
| Valve | 3 | 400 | RF | handwheel_height_mm | 294.894 | 289.000 | +5.894 |
| Valve | 3 | 400 | RF | weight_kg | 11.360 | 6.810 | +4.550 |
| Valve | 4 | 400 | RF | handwheel_height_mm | 348.000 | 342.000 | +6.000 |
| Valve | 4 | 400 | RF | weight_kg | 17.720 | 10.900 | +6.820 |
| Valve | 4 | 400 | RF | handwheel_height_mm | 347.980 | 342.000 | +5.980 |
| Valve | 4 | 400 | RF | weight_kg | 17.720 | 10.900 | +6.820 |
| Valve | 6 | 400 | RF | handwheel_height_mm | 412.000 | 407.000 | +5.000 |
| Valve | 6 | 400 | RF | weight_kg | 28.180 | 15.900 | +12.280 |
| Valve | 6 | 400 | RF | handwheel_height_mm | 411.988 | 407.000 | +4.988 |
| Valve | 6 | 400 | RF | weight_kg | 28.180 | 15.900 | +12.280 |
| Valve | 8 | 400 | RF | handwheel_height_mm | 480.000 | 473.000 | +7.000 |
| Valve | 8 | 400 | RF | weight_kg | 42.270 | 26.810 | +15.460 |
| Valve | 8 | 400 | RF | handwheel_height_mm | 479.806 | 473.000 | +6.806 |
| Valve | 8 | 400 | RF | weight_kg | 42.270 | 26.810 | +15.460 |
| Valve | 10 | 400 | RF | handwheel_height_mm | 560.000 | 585.000 | -25.000 |
| Valve | 10 | 400 | RF | weight_kg | 78.180 | 49.090 | +29.090 |
| Valve | 10 | 400 | RF | handwheel_height_mm | 559.816 | 585.000 | -25.184 |
| Valve | 10 | 400 | RF | weight_kg | 78.180 | 49.090 | +29.090 |
| Valve | 12 | 400 | RF | weight_kg | 130.900 | 71.810 | +59.090 |
| Valve | 12 | 400 | RF | weight_kg | 130.900 | 71.810 | +59.090 |
| Valve | 3 | 600 | RF | weight_kg | 18.000 | 12.270 | +5.730 |
| Valve | 3 | 600 | RF | weight_kg | 18.000 | 12.270 | +5.730 |
| Valve | 4 | 600 | RF | weight_kg | 26.000 | 18.180 | +7.820 |
| Valve | 4 | 600 | RF | weight_kg | 26.000 | 18.180 | +7.820 |
| Valve | 6 | 600 | RF | weight_kg | 55.000 | 30.900 | +24.100 |
| Valve | 6 | 600 | RF | weight_kg | 55.000 | 30.900 | +24.100 |
| Valve | 8 | 600 | RF | weight_kg | 100.000 | 57.720 | +42.280 |
| Valve | 8 | 600 | RF | weight_kg | 100.000 | 57.720 | +42.280 |
| Valve | 10 | 600 | RF | weight_kg | 181.000 | 113.180 | +67.820 |
| Valve | 10 | 600 | RF | weight_kg | 181.000 | 113.180 | +67.820 |
| Valve | 12 | 600 | RF | weight_kg | 264.000 | 153.180 | +110.820 |
| Valve | 12 | 600 | RF | weight_kg | 264.000 | 153.180 | +110.820 |
| Valve | 3 | 900 | RF | weight_kg | 18.000 | 12.270 | +5.730 |
| Valve | 3 | 900 | RF | weight_kg | 18.000 | 12.270 | +5.730 |
| Valve | 4 | 900 | RF | weight_kg | 26.000 | 18.180 | +7.820 |
| Valve | 4 | 900 | RF | weight_kg | 26.000 | 18.180 | +7.820 |
| Valve | 6 | 900 | RF | weight_kg | 55.000 | 30.900 | +24.100 |
| Valve | 6 | 900 | RF | weight_kg | 55.000 | 30.900 | +24.100 |
| Valve | 8 | 900 | RF | weight_kg | 100.000 | 57.720 | +42.280 |
| Valve | 8 | 900 | RF | weight_kg | 100.000 | 57.720 | +42.280 |
| Valve | 10 | 900 | RF | weight_kg | 181.000 | 113.180 | +67.820 |
| Valve | 10 | 900 | RF | weight_kg | 181.000 | 113.180 | +67.820 |
| Valve | 12 | 900 | RF | weight_kg | 264.000 | 153.180 | +110.820 |
| Valve | 12 | 900 | RF | weight_kg | 264.000 | 153.180 | +110.820 |

_tbl_valves_asme_b16_10: 834 celişki satiri_


**TOPLAM: 4473 kolon-celişkisi loglandi.**
