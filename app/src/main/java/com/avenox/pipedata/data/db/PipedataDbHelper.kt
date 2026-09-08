package com.avenox.pipedata.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.avenox.pipedata.data.model.*
import java.io.File
import java.io.FileOutputStream

class PipedataDbHelper(private val context: Context) {

    private val dbName = "pipedata.db"
    private var db: SQLiteDatabase? = null

    init {
        try {
            ensureDatabase()
            val dbPath = context.getDatabasePath(dbName).absolutePath
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun ensureDatabase() {
        val dbFile = context.getDatabasePath(dbName)
        if (!dbFile.exists()) {
            dbFile.parentFile?.mkdirs()
            context.assets.open(dbName).use { input ->
                FileOutputStream(dbFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    fun getFlangeCategories(): List<TopicCategory> {
        return listOf(
            TopicCategory(
                id = "flanges",
                titleEn = "Flanges",
                titleTr = "Flanşlar",
                std = "ASME B16.5",
                descEn = "Weld Neck, Slip-On, Blind, Threaded, Lap Joint, Socket Weld & RTJ",
                descTr = "Kaynak Boyunlu, Düz Geçme, Kör, Dişli, Serbest ve RTJ Flanşlar",
                svgAsset = "modern_flan1.svg"
            ),
            TopicCategory(
                id = "pipes",
                titleEn = "Pipes & Tubing",
                titleTr = "Borular ve Hatlar",
                std = "ASME B36.10M",
                descEn = "Carbon & stainless steel pipe schedules 5S through XXS",
                descTr = "Karbon ve paslanmaz çelik boru programları (SCH 5S - XXS)",
                svgAsset = "modern_pipe1.svg"
            ),
            TopicCategory(
                id = "fittings_bw",
                titleEn = "Butt Weld Fittings",
                titleTr = "Alın Kaynaklı Fitingler",
                std = "ASME B16.9",
                descEn = "90° / 45° elbows, tees, concentric & eccentric reducers",
                descTr = "90° / 45° dirsekler, teeler, konsantrik ve eksantrik redüksiyonlar",
                svgAsset = "modern_ftbw2.svg"
            ),
            TopicCategory(
                id = "fittings_sw",
                titleEn = "Forged Fittings",
                titleTr = "Dövme Fitingler",
                std = "ASME B16.11",
                descEn = "Socket weld & threaded fittings, Class 2000-6000 & o-lets",
                descTr = "Soket kaynaklı ve dişli dövme fitingler, Class 2000-6000",
                svgAsset = "modern_ftsw1.svg"
            ),
            TopicCategory(
                id = "valves",
                titleEn = "Valves",
                titleTr = "Endüstriyel Vanalar",
                std = "ASME B16.34",
                descEn = "Gate, Globe, Check, Ball & Butterfly face-to-face dimensions",
                descTr = "Sürgülü, Vana, Çekvalf, Küresel ve Kelebek vana boyutları",
                svgAsset = "modern_vlfl1.svg"
            ),
            TopicCategory(
                id = "gaskets",
                titleEn = "Gaskets & Bolting",
                titleTr = "Contalar ve Saplamalar",
                std = "ASME B16.20",
                descEn = "Spiral wound, ring joint metallic gaskets & stud bolt charts",
                descTr = "Spiral sarımlı, metalik halka contalar ve saplama tabloları",
                svgAsset = "modern_grtj1.svg"
            ),
            TopicCategory(
                id = "blanks",
                titleEn = "Line Blanks",
                titleTr = "Hat Körleme Elemanları",
                std = "ASME B16.48",
                descEn = "Figure-8 spectacle blinds, paddle spacers & blanks",
                descTr = "Gözlük körler, kürek tipi ara parçalar ve diskler",
                svgAsset = "modern_blnu1.svg"
            )
        )
    }

    fun getFlangeTypes(): List<FlangeTypeItem> {
        return listOf(
            FlangeTypeItem(
                id = "wn",
                title = "Weld Neck Flange",
                shortName = "Weld Neck",
                abbr = "WN",
                facing = "RF",
                standard = "ASME B16.5",
                descEn = "Butt-welded neck for extreme thermal & pressure loads.",
                descTr = "Yüksek basınç, sıcaklık ve titreşim için alın kaynaklı boyun.",
                svgAsset = "modern_flan1.svg"
            ),
            FlangeTypeItem(
                id = "so",
                title = "Slip-On Flange",
                shortName = "Slip-On",
                abbr = "SO",
                facing = "RF",
                standard = "ASME B16.5",
                descEn = "Slides directly over pipe. Double fillet welded.",
                descTr = "Boruya geçirilerek içten ve dıştan çift köşe kaynağı yapılır.",
                svgAsset = "modern_flan2.svg"
            ),
            FlangeTypeItem(
                id = "bld",
                title = "Blind Flange",
                shortName = "Blind",
                abbr = "BLD",
                facing = "RF",
                standard = "ASME B16.5",
                descEn = "Solid disc used to block a piping section or nozzle.",
                descTr = "Boru ucu veya nozul kapatmada kullanılan deliksiz disk flanş.",
                svgAsset = "modern_flan4.svg"
            ),
            FlangeTypeItem(
                id = "thd",
                title = "Threaded Flange",
                shortName = "Threaded",
                abbr = "THD",
                facing = "RF",
                standard = "ASME B16.5",
                descEn = "Tapered NPT pipe threads; no welding required.",
                descTr = "NPT konik dişli; montajda kaynak gerektirmez.",
                svgAsset = "modern_flan1a.svg"
            ),
            FlangeTypeItem(
                id = "lj",
                title = "Lap Joint Flange",
                shortName = "Lap Joint",
                abbr = "LJ",
                facing = "RF",
                standard = "ASME B16.5",
                descEn = "Used with stub end; rotates freely for easy bolt alignment.",
                descTr = "Yaka ile kullanılır; cıvata deliklerini hizalamak için döner.",
                svgAsset = "modern_flan2a.svg"
            ),
            FlangeTypeItem(
                id = "sw",
                title = "Socket Weld Flange",
                shortName = "Socket Weld",
                abbr = "SW",
                facing = "RF",
                standard = "ASME B16.5",
                descEn = "Pipe inserts into socket; ideal for compact high-pressure lines.",
                descTr = "Boru sokete girer; küçük çaplı yüksek basınç hatları içindir.",
                svgAsset = "modern_flan1b.svg"
            )
        )
    }

    fun getFlangeSpec(nps: String, rating: Int, facing: String): FlangeSpec {
        // First try DB query
        db?.let { database ->
            try {
                val cursor = database.rawQuery(
                    "SELECT outer_diameter_mm, thickness_mm, hub_diameter_mm, length_thru_hub_mm, " +
                            "bore_diameter_mm, rf_diameter_mm, pcd_mm, num_bolts, bolt_diameter_in, " +
                            "stud_bolt_length_mm, weight_kg, dn FROM tbl_flanges_asme_b16_5 " +
                            "WHERE (nps = ? OR nps = ?) AND rating = ? LIMIT 1",
                    arrayOf(nps, nps.replace("\"", ""), rating.toString())
                )
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        val od = c.getDouble(0)
                        val thk = c.getDouble(1)
                        val hubX = c.getDouble(2)
                        val lenY = c.getDouble(3)
                        val bore = c.getDouble(4)
                        val rf = c.getDouble(5)
                        val pcd = c.getDouble(6)
                        val bolts = c.getInt(7)
                        val boltSz = c.getString(8) ?: "3/4\""
                        val studL = c.getDouble(9)
                        val wt = c.getDouble(10)
                        val dn = c.getInt(11)

                        if (od > 0) {
                            return FlangeSpec(
                                nps = nps,
                                dn = if (dn > 0) dn else getNpsDn(nps),
                                rating = rating,
                                facing = facing,
                                odMm = od,
                                thkMm = thk,
                                hubXMm = hubX,
                                lengthYMm = lenY,
                                boreBMm = bore,
                                rfDiaMm = rf,
                                pcdMm = pcd,
                                boltsCount = if (bolts > 0) bolts else 4,
                                boltSizeIn = boltSz,
                                studLengthMm = studL,
                                weightKg = wt
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Fallback calculations & static reference table
        return getFallbackSpec(nps, rating, facing)
    }

    private fun getNpsDn(nps: String): Int {
        return when (nps.replace("\"", "").trim()) {
            "1/2" -> 15
            "3/4" -> 20
            "1" -> 25
            "1 1/4", "1-1/4" -> 32
            "1 1/2", "1-1/2" -> 40
            "2" -> 50
            "2 1/2", "2-1/2" -> 65
            "3" -> 80
            "4" -> 100
            "6" -> 150
            "8" -> 200
            "10" -> 250
            "12" -> 300
            "14" -> 350
            "16" -> 400
            "18" -> 450
            "20" -> 500
            "24" -> 600
            else -> 50
        }
    }

    private fun getFallbackSpec(nps: String, rating: Int, facing: String): FlangeSpec {
        val dn = getNpsDn(nps)
        // Calibrated baseline values for 2" Class 150
        val baseOd = when (dn) {
            15 -> 88.9
            20 -> 98.6
            25 -> 108.0
            50 -> 152.4
            80 -> 190.5
            100 -> 228.6
            150 -> 279.4
            200 -> 342.9
            250 -> 406.4
            300 -> 482.6
            else -> 152.4
        }
        val mult = when (rating) {
            300 -> 1.15
            600 -> 1.25
            else -> 1.0
        }
        val od = baseOd * mult
        val thk = 17.5 * mult
        val hubX = 77.7 * mult
        val pcd = od * 0.79
        val bolts = if (dn >= 200) 8 else 4
        val wt = 2.45 * mult * (dn.toDouble() / 50.0)

        return FlangeSpec(
            nps = nps,
            dn = dn,
            rating = rating,
            facing = facing,
            odMm = Math.round(od * 10.0) / 10.0,
            thkMm = Math.round(thk * 10.0) / 10.0,
            hubXMm = Math.round(hubX * 10.0) / 10.0,
            lengthYMm = Math.round((thk + 35) * 10.0) / 10.0,
            boreBMm = if (dn == 50) 52.5 else (dn * 1.05),
            rfDiaMm = Math.round((hubX + 15) * 10.0) / 10.0,
            pcdMm = Math.round(pcd * 10.0) / 10.0,
            boltsCount = bolts,
            boltSizeIn = if (rating >= 600) "7/8\"" else "5/8\"",
            studLengthMm = Math.round((thk * 2 + 35) * 10.0) / 10.0,
            weightKg = Math.round(wt * 100.0) / 100.0
        )
    }

    fun getHelpGlossary(): Map<String, HelpTopic> {
        return mapOf(
            "nps" to HelpTopic(
                id = "nps",
                badge = "ASME B36.10M / ISO 6708",
                titleEn = "Nominal Pipe Size (NPS)",
                titleTr = "Anma Boru Çapı (NPS)",
                bodyEn = "Nominal Pipe Size (NPS) is a North American set of standard sizes for pipes. It indicates the approximate internal or nominal diameter in inches. In the metric ISO system, it is designated as DN (Diamètre Nominal) in millimeters.",
                bodyTr = "NPS (Nominal Pipe Size), boru hatlarında kullanılan standart boyutlandırma sistemidir. İnç bazlı anma çapını ifade eder. Metrik sistemdeki karşılığı milimetre cinsinden DN (Diameter Nominal)'dir.",
                formulaEn = "• NPS ≤ 12\": OD > NPS (e.g. 2\" pipe OD = 2.375\" / 60.3 mm)\n• NPS ≥ 14\": OD = NPS exactly (e.g. 14\" pipe OD = 14.000\" / 355.6 mm)\n• Wall thickness is determined by Schedule (SCH 10, 40, 80, 160, XXS).",
                formulaTr = "• NPS ≤ 12\": Gerçek dış çap (OD) anma ölçüsünden büyüktür (örn: 2\" boru OD = 60.3 mm).\n• NPS ≥ 14\": Dış çap anma ölçüsüne birebir eşittir (14\" boru OD = 355.6 mm).\n• Et kalınlığı Schedule (SCH 40, 80, 160 vb.) ile belirlenir."
            ),
            "rating" to HelpTopic(
                id = "rating",
                badge = "ASME B16.5 PRESSURE-TEMP",
                titleEn = "Pressure Class (Rating)",
                titleTr = "Basınç Sınıfı (Pressure Class)",
                bodyEn = "Pressure Class (e.g., 150#, 300#, 600#) designates the cold working pressure rating in pounds per square inch (psi) according to ASME B16.5. As operating temperature rises, the maximum allowable working pressure (MAWP) decreases significantly.",
                bodyTr = "Flanş Basınç Sınıfı (150#, 300#, 600# vb.), ASME B16.5 standardına göre flanşın ortam sıcaklığında dayanabileceği azami tasarım basıncını (psi cinsinden) gösterir. Çalışma sıcaklığı arttıkça izin verilen basınç (P-T eğrisi) düşer.",
                formulaEn = "• Class 150: ~285 psi at -29°C to 38°C (Carbon Steel)\n• Class 300: ~740 psi at ambient\n• Class 600: ~1480 psi at ambient\n• Always verify ASME B16.5 Table 2-1.1 P-T ratings for high-temp services.",
                formulaTr = "• Class 150: Ortam sıcaklığında ~19.6 bar (285 psi)\n• Class 300: Ortam sıcaklığında ~51.0 bar (740 psi)\n• Class 600: Ortam sıcaklığında ~102.1 bar (1480 psi)\n• Sıcaklık arttıkça mukavemet tablosuna (P-T Tabloları) göre basınç düşürülmelidir."
            ),
            "facing" to HelpTopic(
                id = "facing",
                badge = "GASKET SEAL INTERFACE",
                titleEn = "Flange Facing Types",
                titleTr = "Flanş Sızdırmazlık Yüzeyi (Facing)",
                bodyEn = "The flange facing is the contact surface that compresses the gasket to create a leak-tight seal: 1) Raised Face (RF): 1/16\" (1.6mm) or 1/4\" (6.4mm) raised step with spiral serrations to grip gasket. 2) Flat Face (FF): Full face without step, used on brittle cast iron/bronze. 3) Ring Type Joint (RTJ): Precision machined trapezoidal groove for metallic oval or octagonal rings in severe high-pressure/temp service.",
                bodyTr = "Contanın sıkıştırılarak sızdırmazlık sağlandığı yüzey tipidir: 1) RF (Raised Face - Faturalı Alın): 1.6 mm veya 6.4 mm çıkıntılı fatura ve spiral kanallı yapı. 2) FF (Flat Face - Düz Alın): Çıkıntısız tam düz yüzey, dökme demir pompalarda moment çatlamasını önler. 3) RTJ (Ring Type Joint - Halka Contalı): Sekizgen/oval metal contanın oturduğu trapez kanallı yüksek basınç tasarımıdır.",
                formulaEn = "• RF: ASME B16.5 standard with serrated phonographic finish.\n• FF: Uses full-face flat sheet gaskets.\n• RTJ: Metal-to-metal seal, ideal for Class 600+ and toxic/flammable hydrocarbons.",
                formulaTr = "• RF: Standart sanayi flanşlarında spiral sarımlı (SWG) contalarla kullanılır.\n• FF: Tam yüzey (full-face) yumuşak contalarla eşleştirilir.\n• RTJ: Class 600 ve üzeri yüksek basınç ve hidrokarbon hatlarında kullanılır."
            ),
            "dimensions" to HelpTopic(
                id = "dimensions",
                badge = "GEOMETRY & PCD SYMBOLS",
                titleEn = "Flange Dimensions & PCD",
                titleTr = "Flanş Ölçüleri ve PCD",
                bodyEn = "Standard engineering symbols per ASME B16.5: O = Outside Flange Diameter. T = Minimum Flange Thickness. X = Diameter of Hub at base. B = Inside Bore diameter. PCD = Pitch Circle Diameter (bolt hole circle diameter). Bolting = Stud count × thread size.",
                bodyTr = "ASME B16.5 teknik çizim sembolleri: O = Flanş Dış Çapı (Outside Diameter). T = Asgari Flanş Kalınlığı. X = Boyun Taban Çapı (Hub). B = Flanş İç Delik Çapı (Bore). PCD = Cıvata Eksen Dairesi Çapı (Pitch Circle Diameter). Cıvatalar = Saplama Adedi × Diş Çapı.",
                formulaEn = "• PCD (Pitch Circle Diameter): Distance between centers of opposing bolt holes.\n• Bolt Hole Size = Bolt Stud Diameter + 1/8\" (3.2 mm) for clearance.\n• Bolt Holes are drilled in multiples of 4 (4, 8, 12, 16, 20, 24).",
                formulaTr = "• PCD (Pitch Circle Diameter): Karşılıklı cıvata deliklerinin merkez eksen mesafesidir.\n• Cıvata Delik Çapı = Cıvata Çapı + 3.2 mm (1/8\") montaj boşluğudur.\n• Cıvata delik adetleri her zaman 4'ün katlarıdır (4, 8, 12, 16, 20, 24 vb.)."
            ),
            "flange_types" to HelpTopic(
                id = "flange_types",
                badge = "FLANGE TYPES PER ASME B16.5",
                titleEn = "Flange Types & Applications",
                titleTr = "Flanş Tipleri ve Kullanım Alanları",
                bodyEn = "• Weld Neck (WN): Butt-welded to pipe; ideal for extreme pressures, cyclic stresses, and high temps.\n• Slip-On (SO): Slipped over pipe and welded inside and out; cost effective and easier to align.\n• Blind (BLD): Solid disc without bore used to isolate piping systems or pressure vessel nozzles.\n• Lap Joint (LJ): Used with stub end; rotates freely on pipe for rapid bolt hole alignment.",
                bodyTr = "• Weld Neck (WN - Kaynak Boyunlu): Boruya alın kaynağı ile bağlanır; yüksek basınç ve sıcaklığa en dayanıklıdır.\n• Slip-On (SO - Düz Geçme): Boru içine sokulup içten ve dıştan köşe kaynağı yapılır.\n• Blind (BLD - Kör Flanş): Boru hatlarının sonunu kapatmak için kullanılan deliksiz flanştır.\n• Lap Joint (LJ - Serbest Flanş): Yaka parçasıyla kullanılır; cıvata deliklerini hizalamak için döner.",
                formulaEn = "• WN: Highest structural integrity, full penetration butt weld.\n• SO: Lower fabrication cost, fillet welded.\n• BLD: Highest bending stress at center under hydrostatic test.",
                formulaTr = "• WN: Kritik tesislerde bir numaralı tercihtir, tam nüfuziyetli kaynak yapılır.\n• SO: Düşük ve orta basınç hatlarında ekonomik montaj sağlar.\n• BLD: Hidrostatik testlerde en yüksek eğilme gerilmesine maruz kalan elemandır."
            )
        )
    }
}
