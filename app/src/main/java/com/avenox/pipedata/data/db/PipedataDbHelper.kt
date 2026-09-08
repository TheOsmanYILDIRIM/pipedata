package com.avenox.pipedata.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.avenox.pipedata.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PipedataDbHelper(private val context: Context) {

    private val dbName = "pipedata.db"
    @Volatile
    private var db: SQLiteDatabase? = null

    init {
        try {
            val dbFile = context.getDatabasePath(dbName)
            if (dbFile.exists()) {
                db = SQLiteDatabase.openDatabase(dbFile.absolutePath, null, SQLiteDatabase.OPEN_READONLY)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun ensureDatabaseAsync() = withContext(Dispatchers.IO) {
        try {
            val dbFile = context.getDatabasePath(dbName)
            if (!dbFile.exists() || dbFile.length() < 1000000) {
                dbFile.parentFile?.mkdirs()
                context.assets.open(dbName).use { input ->
                    FileOutputStream(dbFile).use { output ->
                        val buffer = ByteArray(65536)
                        var read: Int
                        while (input.read(buffer).also { read = it } != -1) {
                            output.write(buffer, 0, read)
                        }
                        output.flush()
                    }
                }
            }
            if (db == null || !db!!.isOpen) {
                db = SQLiteDatabase.openDatabase(dbFile.absolutePath, null, SQLiteDatabase.OPEN_READONLY)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getFlangeCategories(): List<TopicCategory> {
        return listOf(
            TopicCategory(
                id = "flanges",
                titleEn = "Flanges",
                titleTr = "Flanşlar",
                std = "ASME B16.5",
                descEn = "Weld Neck, Slip-On, Blind, Threaded, Lap Joint & Socket Weld",
                descTr = "Kaynak Boyunlu, Düz Geçme, Kör, Dişli, Serbest ve Soket Flanşlar",
                svgAsset = "modern_flan1.svg"
            ),
            TopicCategory(
                id = "pipes",
                titleEn = "Pipes & Tubing",
                titleTr = "Borular ve Hatlar",
                std = "ASME B36.10M",
                descEn = "Carbon & stainless steel pipe schedules 10 through XXS",
                descTr = "Karbon ve paslanmaz çelik boru programları (SCH 10 - XXS)",
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
                descEn = "Socket weld & threaded fittings, Class 2000, 3000 & 6000",
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
                descEn = "Spiral wound, ring joint metallic gaskets & stud charts",
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

    fun getCategorySubtypes(categoryId: String): List<SubtypeItem> {
        return when (categoryId) {
            "flanges" -> listOf(
                SubtypeItem("wn", "Weld Neck Flange", "Weld Neck", "WN", "ASME B16.5", "Butt-welded neck for extreme thermal & pressure loads.", "Yüksek basınç için alın kaynaklı boyun.", "modern_flan1.svg"),
                SubtypeItem("so", "Slip-On Flange", "Slip-On", "SO", "ASME B16.5", "Slides directly over pipe. Double fillet welded.", "Boruya geçirilip çift köşe kaynağı yapılır.", "modern_flan2.svg"),
                SubtypeItem("bld", "Blind Flange", "Blind", "BLD", "ASME B16.5", "Solid disc used to block a piping section.", "Boru ucunu kapatan deliksiz disk flanş.", "modern_flan4.svg"),
                SubtypeItem("thd", "Threaded Flange", "Threaded", "THD", "ASME B16.5", "Tapered NPT pipe threads; no welding required.", "NPT konik dişli; kaynak gerektirmez.", "modern_flan1a.svg"),
                SubtypeItem("lj", "Lap Joint Flange", "Lap Joint", "LJ", "ASME B16.5", "Used with stub end; rotates for bolt alignment.", "Yaka ile döner montaj flanşı.", "modern_flan2a.svg"),
                SubtypeItem("sw", "Socket Weld Flange", "Socket Weld", "SW", "ASME B16.5", "Pipe inserts into socket; compact lines.", "Küçük çaplı yüksek basınç hatları.", "modern_flan1b.svg")
            )
            "pipes" -> listOf(
                SubtypeItem("pipe_std", "Steel Pipe Section", "Pipe Section", "PIPE", "ASME B36.10M", "Seamless and welded wrought steel pipe schedules.", "Dikişsiz ve kaynaklı çelik boru kesiti.", "modern_pipe1.svg")
            )
            "fittings_bw" -> listOf(
                SubtypeItem("elbow_90_lr", "90° Long Radius Elbow", "90° LR Elbow", "90° LR", "ASME B16.9", "Standard 1.5D centerline radius turn.", "1.5D standart büküm yarıçaplı dirsek.", "modern_ftbw2.svg"),
                SubtypeItem("elbow_45", "45° Elbow", "45° Elbow", "45°", "ASME B16.9", "45 degree directional piping change.", "45 derece yön değiştirme dirseği.", "modern_ftbw1.svg"),
                SubtypeItem("tee_eq", "Equal Tee", "Equal Tee", "TEE", "ASME B16.9", "90-degree branch of identical run pipe size.", "Eşit çaplı 90 derece branşman teesi.", "modern_ftbw6.svg"),
                SubtypeItem("cap_bw", "Butt Weld Cap", "Pipe Cap", "CAP", "ASME B16.9", "Ellipsoidal head closing pipe end.", "Boru hattı sonunu kapatan kep.", "modern_ftbw8.svg"),
                SubtypeItem("red_con", "Concentric Reducer", "Reducer", "RED", "ASME B16.9", "In-line diameter reduction on common centerline.", "Eksenel çap düşürücü redüksiyon.", "modern_ftbw9.svg")
            )
            "fittings_sw" -> listOf(
                SubtypeItem("sw_elbow_90", "90° Socket Weld Elbow", "90° SW Elbow", "SW 90°", "ASME B16.11", "Forged high pressure 90° socket elbow.", "Dövme çelik yüksek basınç soketli 90° dirsek.", "modern_ftsw1.svg"),
                SubtypeItem("sw_elbow_45", "45° Socket Weld Elbow", "45° SW Elbow", "SW 45°", "ASME B16.11", "Forged high pressure 45° socket elbow.", "Dövme çelik yüksek basınç soketli 45° dirsek.", "modern_ftsw2.svg"),
                SubtypeItem("sw_tee", "Socket Weld Tee", "SW Tee", "SW TEE", "ASME B16.11", "Forged 3-way high pressure branch tee.", "Dövme çelik yüksek basınç soketli tee.", "modern_ftsw3.svg"),
                SubtypeItem("sw_cross", "Socket Weld Cross", "SW Cross", "SW CROSS", "ASME B16.11", "Forged 4-way high pressure cross fitting.", "Dövme çelik yüksek basınç soketli istavroz.", "modern_ftsw4.svg"),
                SubtypeItem("sw_coupling", "Full Coupling", "Full Coupling", "CPLG", "ASME B16.11", "In-line high pressure socket coupling.", "Yüksek basınç soketli tam manşon.", "modern_ftsw5.svg"),
                SubtypeItem("sw_half_coupling", "Half Coupling", "Half Coupling", "HALF-C", "ASME B16.11", "Single socket high pressure branch coupling.", "Tek taraf soketli yarım manşon.", "modern_ftsw6.svg"),
                SubtypeItem("sw_cap", "Socket Weld Cap", "SW Cap", "SW CAP", "ASME B16.11", "High pressure socket end closure cap.", "Soketli boru sonlandırma kepi.", "modern_ftsw7.svg"),
                SubtypeItem("sw_red_coupling", "Reducing Coupling", "Red Coupling", "RED-C", "ASME B16.11", "Diameter reducing socket coupling.", "Soketli çap düşürücü redüksiyon.", "modern_ftsw8.svg"),
                SubtypeItem("sw_union", "Socket Weld Union", "SW Union", "UNION", "ASME B16.11", "High pressure 3-piece socket weld union.", "Dövme çelik soketli konik rakor.", "modern_ftsw9.svg")
            )
            "valves" -> listOf(
                SubtypeItem("vlv_gate", "Gate Valve", "Gate Valve", "GATE", "ASME B16.34", "Linear motion isolation valve.", "Tam açık/kapalı hat kesme vanası.", "modern_vlfl1.svg"),
                SubtypeItem("vlv_globe", "Globe Valve", "Globe Valve", "GLOBE", "ASME B16.34", "Throttling and flow regulation valve.", "Akış ayar ve kısma vanası.", "modern_vlfl2.svg"),
                SubtypeItem("vlv_check", "Swing Check Valve", "Check Valve", "CHECK", "ASME B16.34", "Backflow prevention valve.", "Geri akış önleme çekvalfi.", "modern_vlfl3.svg"),
                SubtypeItem("vlv_ball", "Ball Valve", "Ball Valve", "BALL", "ASME B16.34", "Quarter-turn fast shutoff valve.", "Çeyrek tur hızlı küresel vana.", "modern_vlfl4.svg")
            )
            "gaskets" -> listOf(
                SubtypeItem("gsk_rtj", "RTJ Ring Joint", "RTJ Ring", "RTJ", "ASME B16.20", "Metallic octagonal/oval ring for high pressure.", "Yüksek basınç metal halka conta.", "modern_grtj1.svg"),
                SubtypeItem("gsk_swg", "Spiral Wound Gasket", "Spiral Wound", "SWG", "ASME B16.20", "V-shaped metallic strip with graphite filler.", "Grafit dolgulu spiral sarımlı conta.", "modern_gspr1.svg"),
                SubtypeItem("gsk_flat", "Flat Ring Gasket", "Flat Gasket", "FLAT", "ASME B16.21", "Non-metallic full face/ring gasket.", "Ametalsiz düz flanş contası.", "modern_gflt1.svg")
            )
            "blanks" -> listOf(
                SubtypeItem("blk_fig8", "Figure-8 Spectacle Blind", "Spectacle Blind", "FIG-8", "ASME B16.48", "Combined open spacer and solid blank.", "Açık ve kapalı döner gözlük kör.", "modern_blnu1.svg"),
                SubtypeItem("blk_paddle", "Paddle Blank", "Paddle Blank", "BLANK", "ASME B16.48", "Handle-equipped solid isolation disc.", "Kulplu tek parça körleme diski.", "modern_blnu2.svg"),
                SubtypeItem("blk_spacer", "Paddle Spacer", "Paddle Spacer", "SPACER", "ASME B16.48", "Open ring spacer maintaining line length.", "Kulplu açık ara halka.", "modern_blnu3.svg")
            )
            else -> emptyList()
        }
    }

    private val dataCache = java.util.concurrent.ConcurrentHashMap<String, ComponentData>()

    fun getComponentData(
        categoryId: String,
        subtypeIndex: Int,
        nps: String,
        primarySel: String,
        secondarySel: String?
    ): ComponentData {
        val cacheKey = "$categoryId|$subtypeIndex|$nps|$primarySel|$secondarySel"
        dataCache[cacheKey]?.let { return it }

        val subtypes = getCategorySubtypes(categoryId)
        val subtype = subtypes.getOrElse(subtypeIndex) { subtypes.first() }
        val cleanNpsNum = nps.replace("\"", "").trim()
        val dn = getNpsDn(cleanNpsNum)

        val result = when (categoryId) {
            "flanges" -> buildFlangeData(subtype, subtypes, cleanNpsNum, dn, primarySel, secondarySel ?: "RF")
            "pipes" -> buildPipeData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "fittings_bw" -> buildButtweldData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "fittings_sw" -> buildForgedData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "valves" -> buildValveData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "gaskets" -> buildGasketData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "blanks" -> buildBlankData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            else -> buildFlangeData(subtype, subtypes, cleanNpsNum, dn, primarySel, secondarySel ?: "RF")
        }
        dataCache[cacheKey] = result
        return result
    }

    private fun buildFlangeData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        ratingStr: String,
        facing: String
    ): ComponentData {
        val rating = ratingStr.replace("#", "").toIntOrNull() ?: 150
        var od = 0.0
        var thk = 0.0
        var hubX = 0.0
        var lenY = 0.0
        var bore = 0.0
        var rf = 0.0
        var pcd = 0.0
        var bolts = 4
        var boltSz = "5/8\""
        var studL = 70.0
        var wt = 2.5

        val flgTypeQuery = when (subtype.id) {
            "so" -> "%Slip On%"
            "bld" -> "%Blind%"
            "wn" -> "%Welding Neck%"
            else -> "%"
        }

        db?.let { database ->
            try {
                val cursor = database.rawQuery(
                    "SELECT outer_diameter_mm, thickness_mm, hub_diameter_mm, length_thru_hub_mm, " +
                            "bore_diameter_mm, rf_diameter_mm, pcd_mm, num_bolts, bolt_diameter_in, " +
                            "stud_bolt_length_mm, weight_kg FROM tbl_flanges_asme_b16_5 " +
                            "WHERE (nps = ? OR nps = ? OR nps = ?) AND rating = ? AND flange_type LIKE ? AND outer_diameter_mm > 0 " +
                            "ORDER BY thickness_mm DESC LIMIT 1",
                    arrayOf(nps, "$nps\"", nps.replace(" ", "+"), rating.toString(), flgTypeQuery)
                )
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        od = c.getDouble(0)
                        thk = c.getDouble(1)
                        hubX = c.getDouble(2)
                        lenY = c.getDouble(3)
                        bore = c.getDouble(4)
                        rf = c.getDouble(5)
                        pcd = c.getDouble(6)
                        bolts = c.getInt(7)
                        boltSz = c.getString(8) ?: "5/8\""
                        studL = c.getDouble(9)
                        wt = c.getDouble(10)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (od <= 0) {
            val mult = if (rating >= 600) 1.25 else if (rating >= 300) 1.15 else 1.0
            od = (if (dn == 50) 152.4 else dn * 2.8) * mult
            thk = 17.5 * mult
            hubX = 77.7 * mult
            lenY = 63.5 * mult
            bore = dn * 1.05
            pcd = od * 0.79
            bolts = if (dn >= 200) 8 else 4
            wt = 2.45 * mult * (dn / 50.0)
        }

        val rows = listOf(
            DimensionRow("od", "Outside Diameter (O)", "Dış Çap (O)", "${fmt(od)} mm", "${fmt(od/25.4)}\"", "dimensions"),
            DimensionRow("thk", "Min Thickness (T)", "Min. Et Kalınlığı (T)", "${fmt(thk)} mm", "${fmt(thk/25.4)}\"", "dimensions"),
            DimensionRow("hub", "Base Hub (X)", "Boyun Tabanı (X)", "${fmt(hubX)} mm", "${fmt(hubX/25.4)}\"", "dimensions"),
            DimensionRow("bore", "Inside Bore (B)", "İç Delik (B)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "dimensions"),
            DimensionRow("pcd", "Bolt Circle (PCD)", "Cıvata Dairesi (PCD)", "${fmt(pcd)} mm", "${fmt(pcd/25.4)}\"", "dimensions"),
            DimensionRow("bolts", "Bolting", "Cıvata Adet & Çap", "$bolts × $boltSz", "$bolts × $boltSz", "dimensions"),
            DimensionRow("stud", "Stud Length", "Saplama Boyu", "${fmt(studL)} mm", "${fmt(studL/25.4)}\"", "dimensions"),
            DimensionRow("weight", "Approx. Weight", "Tahmini Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "dimensions")


        )

        return ComponentData(
            categoryId = "flanges",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\""),
            selectedNps = "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = listOf("150#", "300#", "600#"),
            selectedPrimary = "${rating}#",
            secondarySelectorLabelEn = "Facing",
            secondarySelectorLabelTr = "Yüzey",
            secondaryOptions = listOf("RF", "FF", "RTJ"),
            selectedSecondary = facing,
            dimensions = rows
        )
    }

    private fun buildPipeData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        selectedSchedule: String
    ): ComponentData {
        var odMm = 0.0
        var wallThkMm = 0.0
        var idMm = 0.0
        var wtEmptyKgM = 0.0
        var wtWaterKgM = 0.0
        var inertia = 0.0

        db?.let { database ->
            try {
                val cursor = database.rawQuery(
                    "SELECT outer_diameter_mm, wall_thickness_mm, inner_diameter_mm, weight_empty_kg_m, " +
                            "weight_water_kg_m, moment_of_inertia_cm4 FROM tbl_pipes " +
                            "WHERE (nps = ? OR nps = ? OR nps = ?) AND schedule = ? LIMIT 1",
                    arrayOf(nps, "$nps\"", nps.replace(" ", "+"), selectedSchedule)
                )
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        odMm = c.getDouble(0)
                        wallThkMm = c.getDouble(1)
                        idMm = c.getDouble(2)
                        wtEmptyKgM = c.getDouble(3)
                        wtWaterKgM = c.getDouble(4)
                        inertia = c.getDouble(5)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (odMm <= 0) {
            odMm = if (dn <= 50) 60.3 else dn * 1.15
            wallThkMm = if (selectedSchedule == "80") 5.54 else 3.91
            idMm = odMm - 2 * wallThkMm
            wtEmptyKgM = (odMm - wallThkMm) * wallThkMm * 0.02466
            wtWaterKgM = 0.7854 * (idMm / 1000.0) * (idMm / 1000.0) * 1000.0
        }

        val rows = listOf(
            DimensionRow("od", "Outside Diameter (OD)", "Dış Çap (OD)", "${fmt(odMm)} mm", "${fmt(odMm/25.4)}\"", "pipes_sch"),
            DimensionRow("thk", "Wall Thickness (t)", "Et Kalınlığı (t)", "${fmt(wallThkMm)} mm", "${fmt(wallThkMm/25.4)}\"", "pipes_sch"),
            DimensionRow("id", "Inside Diameter (ID)", "İç Çap (ID)", "${fmt(idMm)} mm", "${fmt(idMm/25.4)}\"", "pipes_sch"),
            DimensionRow("wt_empty", "Pipe Weight (Empty)", "Boru Ağırlığı (Boş)", "${fmt(wtEmptyKgM)} kg/m", "${fmt(wtEmptyKgM*0.6719)} lb/ft", "pipes_sch"),
            DimensionRow("wt_water", "Water Content Weight", "Su Ağırlığı (Dolu)", "${fmt(wtWaterKgM)} kg/m", "${fmt(wtWaterKgM*0.6719)} lb/ft", "pipes_sch"),
            DimensionRow("inertia", "Moment of Inertia (I)", "Atalet Momenti (I)", "${fmt(inertia)} cm⁴", "${fmt(inertia*0.0240)} in⁴", "pipes_sch")
        )

        return ComponentData(
            categoryId = "pipes",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\""),
            selectedNps = "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Schedule",
            primarySelectorLabelTr = "Et Kalınlığı",
            primaryOptions = listOf("10", "20", "40", "80", "160"),
            selectedPrimary = selectedSchedule,
            dimensions = rows
        )
    }

    private fun buildButtweldData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        sched: String
    ): ComponentData {
        var od = 0.0
        var centerEnd = 0.0
        var wt = 0.0

        db?.let { database ->
            try {
                val cursor = database.rawQuery(
                    "SELECT outer_diameter_mm, center_to_end_mm, weight_kg FROM tbl_fittings_buttweld_b16_9 " +
                            "WHERE (nps = ? OR nps = ? OR nps = ?) LIMIT 1",
                    arrayOf(nps, "$nps\"", nps.replace(" ", "+"))
                )
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        od = c.getDouble(0)
                        centerEnd = c.getDouble(1)
                        wt = c.getDouble(2)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (od <= 0) {
            od = 60.3
            centerEnd = 76.2
            wt = 1.4
        }

        val rows = listOf(
            DimensionRow("od", "Outside Diameter (OD)", "Dış Çap (OD)", "${fmt(od)} mm", "${fmt(od/25.4)}\"", "buttweld"),
            DimensionRow("center_end", "Center-to-End (A / C)", "Merkez-Uç Mesafesi (A)", "${fmt(centerEnd)} mm", "${fmt(centerEnd/25.4)}\"", "buttweld"),
            DimensionRow("radius", "Centerline Radius (R)", "Büküm Yarıçapı (R)", "${fmt(od * 1.5)} mm", "${fmt((od*1.5)/25.4)}\"", "buttweld"),
            DimensionRow("weight", "Approx. Weight", "Tahmini Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "buttweld")

        )

        return ComponentData(
            categoryId = "fittings_bw",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/2\"", "2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\"", "16\"", "20\"", "24\""),
            selectedNps = "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Schedule",
            primarySelectorLabelTr = "Seri",
            primaryOptions = listOf("STD", "40", "80", "160"),
            selectedPrimary = sched,
            dimensions = rows
        )
    }

    private fun buildForgedData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        ratingStr: String
    ): ComponentData {
        val ratingStrClean = if (ratingStr in listOf("3000#", "6000#", "9000#")) ratingStr else "3000#"
        val rating = ratingStrClean.replace("#", "").toIntOrNull() ?: 3000
        var sockDepth = 0.0
        var sockDia = 0.0
        var centerEnd = 0.0
        var bore = 0.0
        var sockThk = 0.0
        var bodyWallThk = 0.0
        var lenVal = 0.0
        var wt = 0.0

        val compTypeLike = when (subtype.id) {
            "sw_elbow_90" -> "%90%"
            "sw_elbow_45" -> "%45%"
            "sw_tee" -> "%Tee%"
            "sw_cross" -> "%Cross%"
            "sw_coupling" -> "%Full Coupling%"
            "sw_half_coupling" -> "%Half Coupling%"
            "sw_cap" -> "%Cap%"
            "sw_red_coupling" -> "%Reduc%"
            "sw_union" -> "%Union%"
            else -> "%"
        }

        db?.let { database ->
            try {
                val cursor = database.rawQuery(
                    "SELECT socket_depth_mm, socket_diameter_mm, center_to_end_mm, bore_diameter_mm, " +
                            "socket_wall_thickness_mm, body_wall_thickness_mm, length_mm, weight_kg " +
                            "FROM tbl_fittings_socketweld_b16_11 " +
                            "WHERE (nps = ? OR nps = ? OR nps = ?) AND rating = ? AND component_type LIKE ? " +
                            "ORDER BY socket_depth_mm DESC LIMIT 1",
                    arrayOf(nps, "$nps\"", "0+$nps", rating.toString(), compTypeLike)
                )
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        sockDepth = c.getDouble(0)
                        sockDia = c.getDouble(1)
                        centerEnd = c.getDouble(2)
                        bore = c.getDouble(3)
                        sockThk = c.getDouble(4)
                        bodyWallThk = c.getDouble(5)
                        lenVal = c.getDouble(6)
                        wt = c.getDouble(7)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Fallback to general socketweld table query if specific subtype row wasn't found
        if (sockDia <= 0.0) {
            db?.let { database ->
                try {
                    val cursor = database.rawQuery(
                        "SELECT socket_depth_mm, socket_diameter_mm, center_to_end_mm, bore_diameter_mm, " +
                                "socket_wall_thickness_mm, body_wall_thickness_mm, length_mm, weight_kg " +
                                "FROM tbl_fittings_socketweld_b16_11 " +
                                "WHERE (nps = ? OR nps = ? OR nps = ?) AND rating = ? " +
                                "ORDER BY socket_depth_mm DESC LIMIT 1",
                        arrayOf(nps, "$nps\"", "0+$nps", rating.toString())
                    )
                    cursor.use { c ->
                        if (c.moveToFirst()) {
                            sockDepth = c.getDouble(0)
                            sockDia = c.getDouble(1)
                            centerEnd = c.getDouble(2)
                            bore = c.getDouble(3)
                            sockThk = c.getDouble(4)
                            bodyWallThk = c.getDouble(5)
                            lenVal = c.getDouble(6)
                            wt = c.getDouble(7)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        val rows = mutableListOf<DimensionRow>()
        rows.add(DimensionRow("sock_dia", "Socket Bore (B)", "Soket Çapı (B)", "${fmt(sockDia)} mm", "${fmt(sockDia/25.4)}\"", "forged"))
        rows.add(DimensionRow("sock_depth", "Socket Depth (J)", "Soket Derinliği (J)", "${fmt(sockDepth)} mm", "${fmt(sockDepth/25.4)}\"", "forged"))

        when (subtype.id) {
            "sw_coupling", "sw_half_coupling", "sw_union", "sw_red_coupling" -> {
                val effectiveLen = if (lenVal > 0) lenVal else if (centerEnd > 0) centerEnd else sockDepth * 2.0
                rows.add(DimensionRow("length", "Overall Length (L)", "Toplam Boy (L)", "${fmt(effectiveLen)} mm", "${fmt(effectiveLen/25.4)}\"", "forged"))
                if (bore > 0) rows.add(DimensionRow("bore", "Bore Diameter (D)", "İç Delik Çapı (D)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "forged"))
                if (sockThk > 0) rows.add(DimensionRow("sock_thk", "Socket Wall (C)", "Soket Et Kalınlığı (C)", "${fmt(sockThk)} mm", "${fmt(sockThk/25.4)}\"", "forged"))
            }
            "sw_cap" -> {
                val capThk = if (lenVal > 0) lenVal else if (sockThk > 0) sockThk else 6.4
                rows.add(DimensionRow("cap_thk", "End Wall Thk (E)", "Taban Kalınlığı (E)", "${fmt(capThk)} mm", "${fmt(capThk/25.4)}\"", "forged"))
                if (sockThk > 0) rows.add(DimensionRow("sock_thk", "Socket Wall (C)", "Soket Et Kalınlığı (C)", "${fmt(sockThk)} mm", "${fmt(sockThk/25.4)}\"", "forged"))
            }
            else -> {
                rows.add(DimensionRow("center_end", "Center-to-End (A)", "Eksen-Alın Mesafesi (A)", "${fmt(centerEnd)} mm", "${fmt(centerEnd/25.4)}\"", "forged"))
                if (bore > 0) rows.add(DimensionRow("bore", "Bore Diameter (D)", "İç Delik Çapı (D)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "forged"))
                if (sockThk > 0) rows.add(DimensionRow("sock_thk", "Socket Wall (C)", "Soket Et Kalınlığı (C)", "${fmt(sockThk)} mm", "${fmt(sockThk/25.4)}\"", "forged"))
                if (bodyWallThk > 0) rows.add(DimensionRow("wall_thk", "Body Wall (G)", "Gövde Et Kalınlığı (G)", "${fmt(bodyWallThk)} mm", "${fmt(bodyWallThk/25.4)}\"", "forged"))
            }
        }
        rows.add(DimensionRow("weight", "Approx. Weight", "Tahmini Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "forged"))


        return ComponentData(
            categoryId = "fittings_sw",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/8\"", "1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\""),
            selectedNps = "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = listOf("3000#", "6000#", "9000#"),
            selectedPrimary = "${rating}#",
            dimensions = rows
        )
    }

    private fun buildValveData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        ratingStr: String
    ): ComponentData {
        val rating = ratingStr.replace("#", "").toIntOrNull() ?: 150
        var faceToFace = 0.0
        var wt = 0.0

        db?.let { database ->
            try {
                val cursor = database.rawQuery(
                    "SELECT face_to_face_mm, weight_kg FROM tbl_valves_asme_b16_10 " +
                            "WHERE (nps = ? OR nps = ?) AND rating = ? LIMIT 1",
                    arrayOf(nps, "$nps\"", rating.toString())
                )
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        faceToFace = c.getDouble(0)
                        wt = c.getDouble(1)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (faceToFace <= 0) {
            faceToFace = if (rating >= 300) 216.0 else 178.0
            wt = if (rating >= 300) 24.0 else 18.0
        }

        val rows = listOf(
            DimensionRow("f2f", "Face-to-Face (L)", "Alından Alına Boy (L)", "${fmt(faceToFace)} mm", "${fmt(faceToFace/25.4)}\"", "valves_def"),
            DimensionRow("flange_od", "Flange Outside Dia (O)", "Flanş Dış Çapı (O)", "${fmt(dn * 2.8)} mm", "${fmt((dn*2.8)/25.4)}\"", "valves_def"),
            DimensionRow("weight", "Estimated Weight", "Tahmini Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "valves_def")

        )

        return ComponentData(
            categoryId = "valves",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/2\"", "2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\""),
            selectedNps = "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = listOf("150#", "300#", "600#"),
            selectedPrimary = "${rating}#",
            dimensions = rows
        )
    }

    private fun buildGasketData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        ratingStr: String
    ): ComponentData {
        val rating = ratingStr.replace("#", "").toIntOrNull() ?: 150
        var idMm = 0.0
        var odMm = 0.0
        var ringNo = ""

        db?.let { database ->
            try {
                val cursor = database.rawQuery(
                    "SELECT inner_diameter_mm, outer_diameter_mm, ring_number FROM tbl_gaskets_and_rtj " +
                            "WHERE (nps = ? OR nps = ?) AND rating = ? LIMIT 1",
                    arrayOf(nps, "$nps\"", rating.toString())
                )
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        idMm = c.getDouble(0)
                        odMm = c.getDouble(1)
                        ringNo = c.getString(2) ?: ""
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (odMm <= 0) {
            idMm = 60.3
            odMm = 104.8
            ringNo = if (dn == 50) "R23" else "R" + (dn / 2)
        }

        val rows = listOf(
            DimensionRow("id", "Gasket Inside Dia (d1)", "Conta İç Çapı (d1)", "${fmt(idMm)} mm", "${fmt(idMm/25.4)}\"", "gaskets_def"),
            DimensionRow("od", "Gasket Outside Dia (d2)", "Conta Dış Çapı (d2)", "${fmt(odMm)} mm", "${fmt(odMm/25.4)}\"", "gaskets_def"),
            DimensionRow("ring", "RTJ Ring Number", "RTJ Ring Numarası", ringNo.ifBlank { "R23" }, ringNo.ifBlank { "R23" }, "gaskets_def"),
            DimensionRow("thk", "Standard Thickness", "Conta Kalınlığı", "4.5 mm", "0.177\"", "gaskets_def")
        )

        return ComponentData(
            categoryId = "gaskets",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/2\"", "2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\""),
            selectedNps = "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = listOf("150#", "300#", "600#"),
            selectedPrimary = "${rating}#",
            dimensions = rows
        )
    }

    private fun buildBlankData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        ratingStr: String
    ): ComponentData {
        val rating = ratingStr.replace("#", "").toIntOrNull() ?: 150
        var odMm = 0.0
        var thkMm = 0.0
        var c2c = 0.0
        var wt = 0.0

        db?.let { database ->
            try {
                val cursor = database.rawQuery(
                    "SELECT outer_diameter_mm, thickness_mm, center_to_center_mm, weight_spectacle_kg " +
                            "FROM tbl_line_blanks_asme_b16_48 WHERE (nps = ? OR nps = ?) AND rating = ? LIMIT 1",
                    arrayOf(nps, "$nps\"", rating.toString())
                )
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        odMm = c.getDouble(0)
                        thkMm = c.getDouble(1)
                        c2c = c.getDouble(2)
                        wt = c.getDouble(3)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (odMm <= 0) {
            odMm = 104.8
            thkMm = 6.4
            c2c = 120.7
            wt = 1.8
        }

        val rows = listOf(
            DimensionRow("od", "Outside Diameter (OD)", "Dış Çap (OD)", "${fmt(odMm)} mm", "${fmt(odMm/25.4)}\"", "blanks_def"),
            DimensionRow("thk", "Plate Thickness (t)", "Plaka Kalınlığı (t)", "${fmt(thkMm)} mm", "${fmt(thkMm/25.4)}\"", "blanks_def"),
            DimensionRow("c2c", "Center-to-Center (A)", "Eksenler Arası (A)", "${fmt(c2c)} mm", "${fmt(c2c/25.4)}\"", "blanks_def"),
            DimensionRow("weight", "Spectacle Weight", "Gözlük Ağırlığı", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "blanks_def")

        )

        return ComponentData(
            categoryId = "blanks",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/2\"", "2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\""),
            selectedNps = "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = listOf("150#", "300#", "600#"),
            selectedPrimary = "${rating}#",
            dimensions = rows
        )
    }

    private val dfPrecision = java.text.DecimalFormat("0.####", java.text.DecimalFormatSymbols(java.util.Locale.US))
    private val dfWeight = java.text.DecimalFormat("0.##", java.text.DecimalFormatSymbols(java.util.Locale.US))

    private fun fmt(d: Double): String {
        if (d.isNaN() || d.isInfinite()) return "—"
        val rounded = Math.round(d * 10000.0) / 10000.0
        return dfPrecision.format(rounded)
    }

    private fun fmtWeight(d: Double): String {
        if (d.isNaN() || d.isInfinite()) return "—"
        val rounded = Math.round(d * 100.0) / 100.0
        return dfWeight.format(rounded)
    }


    private fun getNpsDn(nps: String): Int {
        return when (nps.replace("\"", "").trim()) {
            "1/8" -> 6
            "1/4" -> 8
            "3/8" -> 10
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
                bodyEn = "The flange facing is the contact surface that compresses the gasket: 1) Raised Face (RF): 1.6mm or 6.4mm raised step with phonographic serrations. 2) Flat Face (FF): Full face without step for cast iron. 3) Ring Type Joint (RTJ): Machined groove for metallic rings.",
                bodyTr = "Contanın oturduğu yüzeydir: 1) RF (Faturalı Alın): 1.6 mm veya 6.4 mm çıkıntılı spiral kanallı yüzey. 2) FF (Düz Alın): Çıkıntısız tam düz yüzey. 3) RTJ (Halka Contalı): Metal conta için trapez kanallı yüksek basınç yüzeyi.",
                formulaEn = "• RF: ASME standard phonographic serrated finish.\n• FF: Full-face soft gaskets.\n• RTJ: Metal-to-metal seal for Class 600+.",
                formulaTr = "• RF: Standart spiral sarımlı contalarla eşleşir.\n• FF: Dökme demir ve kırılgan pompalarda kullanılır.\n• RTJ: Class 600 ve üzeri yüksek basınç hatlarındadır."
            ),
            "dimensions" to HelpTopic(
                id = "dimensions",
                badge = "GEOMETRY & PCD SYMBOLS",
                titleEn = "Flange Dimensions & PCD",
                titleTr = "Flanş Ölçüleri ve PCD",
                bodyEn = "Standard engineering symbols: O = Outside Diameter, T = Minimum Thickness, X = Hub Diameter, B = Bore Diameter, PCD = Bolt Pitch Circle Diameter.",
                bodyTr = "Teknik çizim sembolleri: O = Dış Çap, T = Kalınlık, X = Boyun Taban Çapı, B = İç Delik Çapı, PCD = Cıvata Eksen Dairesi Çapı.",
                formulaEn = "• PCD: Pitch Circle Diameter between bolt centers.\n• Bolt Hole = Bolt Stud Diameter + 1/8\" (3.2 mm).\n• Drilled in multiples of 4 (4, 8, 12, 16...).",
                formulaTr = "• PCD: Karşılıklı cıvata deliklerinin merkez eksen mesafesi.\n• Delik Çapı = Cıvata Çapı + 3.2 mm.\n• Cıvata delikleri 4'ün katlarıdır (4, 8, 12, 16...)."
            ),
            "pipes_sch" to HelpTopic(
                id = "pipes_sch",
                badge = "ASME B36.10M / B36.19M",
                titleEn = "Pipe Schedules & Wall Thickness",
                titleTr = "Boru Schedule ve Et Kalınlığı",
                bodyEn = "Schedule (SCH) denotes the wall thickness for a given NPS. Outer diameter remains constant for a specific NPS regardless of schedule, while the inside diameter changes.",
                bodyTr = "Schedule (SCH), borunun et kalınlığını belirler. Belirli bir NPS için dış çap (OD) her zaman sabittir; schedule değiştikçe iç çap (ID) ve ağırlık değişir.",
                formulaEn = "• SCH ≈ 1000 × (P / S)\n• P = Service pressure, S = Allowable stress.\n• Common: SCH 40 (Standard) and SCH 80 (Extra Strong).",
                formulaTr = "• SCH ≈ 1000 × (P / S)\n• P = Tasarım basıncı, S = İzin verilen gerilme.\n• En yaygın: SCH 40 (Standart) ve SCH 80 (Extra Strong)."
            ),
            "buttweld" to HelpTopic(
                id = "buttweld",
                badge = "ASME B16.9 FITTINGS",
                titleEn = "Buttweld Fittings Standards",
                titleTr = "Alın Kaynaklı Fiting Standartları",
                bodyEn = "ASME B16.9 covers factory-made wrought buttwelding fittings. Center-to-end dimensions are strictly defined. 90° Long Radius equals 1.5 × NPS in radius.",
                bodyTr = "ASME B16.9 dövme alın kaynaklı ek parçaları kapsar. 90° Uzun Yarıçap (LR) dirseklerde büküm yarıçapı tam olarak 1.5 × NPS'tir.",
                formulaEn = "• 90° LR Center-to-End = 1.5 × NPS (in inches) × 25.4 mm.\n• 90° SR Center-to-End = 1.0 × NPS.",
                formulaTr = "• 90° LR Dirsek Merkez Mesafesi = 1.5 × NPS (inç) × 25.4 mm.\n• 90° SR Dirsek Merkez Mesafesi = 1.0 × NPS."
            ),
            "valves_def" to HelpTopic(
                id = "valves_def",
                badge = "ASME B16.10 / B16.34",
                titleEn = "Valve Face-to-Face Standards",
                titleTr = "Vana Alından Alına Boyutları",
                bodyEn = "ASME B16.10 establishes standardized face-to-face and end-to-end dimensions for flanged, buttweld, and wafer valves to permit interchangeability.",
                bodyTr = "ASME B16.10 flanşlı ve kaynaklı vanaların tesisatta birbirinin yerine takılabilmesi için alından-alına (face-to-face) standart montaj boylarını belirler.",
                formulaEn = "• Face-to-Face: Total distance between flange faces.\n• Ensures any manufacturer's valve fits into the piping spool.",
                formulaTr = "• Alından-alına boy: Giriş ve çıkış flanş alınları arasındaki net mesafe.\n• Farklı marka vanaların birebir uyumlu değişmesini sağlar."
            ),
            "gaskets_def" to HelpTopic(
                id = "gaskets_def",
                badge = "ASME B16.20 / API 6A",
                titleEn = "Metallic & Spiral Wound Gaskets",
                titleTr = "Metalik ve Spiral Sarımlı Contalar",
                bodyEn = "ASME B16.20 covers metallic ring joint gaskets (octagonal / oval) and spiral wound gaskets with internal/external guide rings.",
                bodyTr = "ASME B16.20 yüksek basınç metalik halka contaları (RTJ) ve iç/dış halkalı spiral sarımlı contaları (SWG) standartlaştırır.",
                formulaEn = "• RTJ: Octagonal rings have higher sealing efficiency than oval.\n• Groove angles are 23° with tight surface finishes.",
                formulaTr = "• RTJ: Sekizgen halkalar ovale göre daha yüksek sızdırmazlık sağlar.\n• Flanş kanal açısı 23 derecedir."
            ),
            "blanks_def" to HelpTopic(
                id = "blanks_def",
                badge = "ASME B16.48 LINE BLANKS",
                titleEn = "Spectacle Blanks & Spacers",
                titleTr = "Gözlük Körler ve Ara Parçalar",
                bodyEn = "Line blanks provide positive positive mechanical shutoff for maintenance. Figure-8 blanks allow quick visual inspection of open/closed status.",
                bodyTr = "Hat körleme elemanları boru hatlarında bakım esnasında kesin mekanik izolasyon sağlar. Gözlük körün açık mı kapalı mı olduğu dışarıdan hemen görülür.",
                formulaEn = "• Spectacle Blind: Joined open ring and blind disc.\n• Paddle Blank: Solid disc with extended handle.",
                formulaTr = "• Gözlük Kör: Birbirine bağlı bir açık halka ve bir kapalı disk.\n• Kürek Kör: Flanş arasına giren kulplu disk."
            ),
            "flange_types" to HelpTopic(
                id = "flange_types",
                badge = "ASME B16.5 TYPES",
                titleEn = "Flange Types & Applications",
                titleTr = "Flanş Tipleri ve Kullanım Alanları",
                bodyEn = "• Weld Neck (WN): Butt-welded; extreme pressures and temperatures.\n• Slip-On (SO): Slipped over pipe; double fillet welded.\n• Blind (BLD): Solid disc to close lines.\n• Lap Joint (LJ): Rotates freely for alignment.",
                bodyTr = "• Kaynak Boyunlu (WN): Alın kaynaklı; yüksek basınç ve sıcaklık.\n• Düz Geçme (SO): Boruya geçirilip çift köşe kaynağı yapılır.\n• Kör (BLD): Hat sonunu kapatan deliksiz disk.\n• Serbest (LJ): Yaka ile döner montaj.",
                formulaEn = "• WN: Highest fatigue resistance.\n• SO: Lower installation cost.\n• BLD: Maximum hydrostatic bending load.",
                formulaTr = "• WN: En yüksek yorulma ve basınca dayanıklıdır.\n• SO: Düşük maliyetli montaj sağlar.\n• BLD: Hidrostatik testlerde en yüksek eğilme gerilmesine maruz kalır."
            )
        )
    }
}
