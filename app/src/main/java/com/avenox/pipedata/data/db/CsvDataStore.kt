package com.avenox.pipedata.data.db

import android.content.Context
import com.avenox.pipedata.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Pipedata CSV Veri Depolama Katmani (sifirdan cikarma — extract_tables.py)
 *
 * APK icerisine `assets/csv/` olarak gomulen 8 tablo CSV'sini okur.
 * CSV'ler `extract_tables.py` ile 956 orijinal B4A CSV'sinden tek adimda
 * uretilir; dogruluk kapisi `verify_extract.py` (22 kontrol).
 * Ham orijinal dosyalar APK'ya gomulmez (2.3MB tekrari onlemek icin).
 *
 * - Sorgular bellekte satir-filtreleme ile yapilir; SQL LIKE `%x%` -> `contains`,
 *   `ORDER BY col DESC LIMIT 1` -> `maxByOrNull`.
 */
class CsvDataStore(private val context: Context) {

    /** Tablo adi -> satirlar (kolon adi -> deger). CsvDataStore yuklemesiyle dolar. */
    private val tables = java.util.concurrent.ConcurrentHashMap<String, List<Map<String, String>>>()

    private val csvDir = "csv"

    suspend fun ensureDatabaseAsync() = withContext(Dispatchers.IO) {
        try {
            loadTables()
            prewarmCache()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /** Tum kanonik CSV tablolarini gomulu asset'lerden yukler. */
    private fun loadTables() {
        val tableNames = listOf(
            "tbl_flanges_asme_b16_5",
            "tbl_pipes",
            "tbl_fittings_buttweld_b16_9",
            "tbl_fittings_socketweld_b16_11",
            "tbl_fittings_threaded_b16_11",
            "tbl_valves_asme_b16_10",
            "tbl_gaskets_and_rtj",
            "tbl_line_blanks_asme_b16_48"
        )
        for (t in tableNames) {
            try {
                tables[t] = parseCsv("$csvDir/$t.csv")
            } catch (e: Exception) {
                // No Silent Fallback: tablo yuklenemediyse bos birak, prewarm cache'te hata firlatilir.
                tables.remove(t)
            }
        }
    }

    private fun parseCsv(path: String): List<Map<String, String>> {
        val text = context.assets.open(path).bufferedReader().use { it.readText() }
        val rows = mutableListOf<Map<String, String>>()
        val lines = text.split("\r?\n".toRegex())
        if (lines.isEmpty()) return rows
        val headers = splitCsvLine(lines[0])
        for (li in 1 until lines.size) {
            val line = lines[li].trim()
            if (line.isEmpty()) continue
            val values = splitCsvLine(line)
            val map = LinkedHashMap<String, String>()
            for (ci in 0 until Math.max(headers.size, values.size)) {
                if (ci < headers.size) {
                    map[headers[ci]] = if (ci < values.size) values[ci].trim() else ""
                }
            }
            rows.add(map)
        }
        return rows
    }

    private fun splitCsvLine(line: String): List<String> {
        val out = ArrayList<String>()
        val sb = StringBuilder()
        var inQuotes = false
        var i = 0
        while (i < line.length) {
            val ch = line[i]
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length && line[i + 1] == '"') {
                    sb.append('"')
                    i++
                } else {
                    inQuotes = !inQuotes
                }
            } else if (ch == ',' && !inQuotes) {
                out.add(sb.toString())
                sb.setLength(0)
            } else {
                sb.append(ch)
            }
            i++
        }
        out.add(sb.toString())
        return out
    }

    /** Yuklu tablodan satirlari doner; tablo yuklenmediyse bos liste (sorgu fallback'e duser). */
    private fun tableData(table: String): List<Map<String, String>> =
        tables[table] ?: error("Required CSV table missing: $table")

    private fun d(row: Map<String, String>, col: String): Double {
        return row[col]?.trim()?.toDoubleOrNull() ?: Double.NaN
    }

    private fun s(row: Map<String, String>, col: String): String = row[col] ?: ""

    fun prewarmCache() {
        try {
            getComponentData("flanges", 0, "2\"", "150#", "RF")
            getComponentData("pipes", 0, "2\"", "40", null)
            getComponentData("fittings_bw", 0, "2\"", "STD", null)
            getComponentData("fittings_sw", 0, "2\"", "3000#", null)
            getComponentData("valves", 0, "2\"", "150#", null)
            getComponentData("gaskets", 0, "2\"", "150#", null)
            getComponentData("blanks", 0, "2\"", "150#", null)
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
                SubtypeItem("wn", "Weld Neck Flange", "Weld Neck", "WN", "ASME B16.5", "Butt-welded neck for extreme thermal & pressure loads.", "Yüksek basınç için alın kaynaklı boyun.", "modern_flan1.svg", "Kaynak Boyunlu Flanş", "Kaynak Boyunlu"),
                SubtypeItem("so", "Slip-On Flange", "Slip-On", "SO", "ASME B16.5", "Slides directly over pipe. Double fillet welded.", "Boruya geçirilip çift köşe kaynağı yapılır.", "modern_flan2.svg", "Geçme Flanş (Slip-On)", "Geçme Flanş"),
                SubtypeItem("bld", "Blind Flange", "Blind", "BLD", "ASME B16.5", "Solid disc used to block a piping section.", "Boru ucunu kapatan deliksiz disk flanş.", "modern_flan4.svg", "Kör Flanş", "Kör Flanş"),
                SubtypeItem("thd", "Threaded Flange", "Threaded", "THD", "ASME B16.5", "Tapered NPT pipe threads; no welding required.", "NPT konik dişli; kaynak gerektirmez.", "modern_flan1a.svg", "Dişli Flanş", "Dişli Flanş"),
                SubtypeItem("lj", "Lap Joint Flange", "Lap Joint", "LJ", "ASME B16.5", "Used with stub end; rotates for bolt alignment.", "Yaka ile döner montaj flanşı.", "modern_flan2a.svg", "Serbest Yaka Flanşı", "Yaka Flanşı"),
                SubtypeItem("sw", "Socket Weld Flange", "Socket Weld", "SW", "ASME B16.5", "Pipe inserts into socket; compact lines.", "Küçük çaplı yüksek basınç hatları.", "modern_flan1b.svg", "Soket Kaynaklı Flanş", "Soket Flanş")
            )
            "pipes" -> listOf(
                SubtypeItem("pipe_std", "Steel Pipe Section", "Pipe Section", "PIPE", "ASME B36.10M", "Seamless and welded wrought steel pipe schedules.", "Dikişsiz ve kaynaklı çelik boru kesiti.", "modern_pipe1.svg", "Çelik Boru Kesiti", "Boru Kesiti")
            )
            "fittings_bw" -> listOf(
                SubtypeItem("elbow_90_lr", "90° Long Radius Elbow", "90° LR Elbow", "90° LR", "ASME B16.9", "Standard 1.5D centerline radius turn.", "1.5D standart büküm yarıçaplı dirsek.", "modern_ftbw2.svg", "90° Uzun Yarıçap Dirsek", "90° LR"),
                SubtypeItem("elbow_90_sr", "90° Short Radius Elbow", "90° SR Elbow", "90° SR", "ASME B16.9", "Compact 1.0D centerline radius turn.", "1.0D dar alanlar için kısa yarıçaplı dirsek.", "modern_ftbw4.svg", "90° Kısa Yarıçap Dirsek", "90° SR"),
                SubtypeItem("elbow_45", "45° Elbow", "45° Elbow", "45°", "ASME B16.9", "45 degree directional piping change.", "45 derece yön değiştirme dirseği.", "modern_ftbw1.svg", "45° Dirsek", "45° Dirsek"),
                SubtypeItem("return_180_lr", "180° Long Radius Return", "180° LR Return", "180° LR", "ASME B16.9", "180-degree return bend (3.0D center-to-center).", "180 derece uzun yarıçaplı U-dönüş bükümü.", "modern_ftbw3.svg", "180° Uzun Yarıçap U-Dönüş", "180° LR"),
                SubtypeItem("return_180_sr", "180° Short Radius Return", "180° SR Return", "180° SR", "ASME B16.9", "180-degree return bend (2.0D center-to-center).", "180 derece kısa yarıçaplı kompakt U-dönüş bükümü.", "modern_ftbw5.svg", "180° Kısa Yarıçap U-Dönüş", "180° SR"),
                SubtypeItem("tee_eq", "Equal Tee", "Equal Tee", "TEE", "ASME B16.9", "90-degree branch of identical run pipe size.", "Eşit çaplı 90 derece branşman teesi.", "modern_ftbw6.svg", "Eşit Te (Equal Tee)", "Eşit Te"),
                SubtypeItem("tee_red", "Reducing Tee", "Reducing Tee", "RED-TEE", "ASME B16.9", "90-degree branch with smaller outlet size.", "Çapı daraltılmış branşman çıkışlı inegal tee.", "modern_ftbw7.svg", "İnegal Te (Reducing Tee)", "İnegal Te"),
                SubtypeItem("cap_bw", "Butt Weld Cap", "Pipe Cap", "CAP", "ASME B16.9", "Ellipsoidal head closing pipe end.", "Boru hattı sonunu kapatan kep.", "modern_ftbw8.svg", "Boru Sonu Kepi (Cap)", "Boru Kepi"),
                SubtypeItem("red_con", "Concentric Reducer", "Con Reducer", "CON-RED", "ASME B16.9", "In-line diameter reduction on common centerline.", "Eksenel çap düşürücü redüksiyon.", "modern_ftbw9.svg", "Konsantrik Redüksiyon", "Kon. Red."),
                SubtypeItem("red_ecc", "Eccentric Reducer", "Ecc Reducer", "ECC-RED", "ASME B16.9", "In-line diameter reduction maintaining flat bottom/top.", "Düz taban/tavan hattı koruyan eksantrik redüksiyon.", "modern_ftbw10.svg", "Eksantrik Redüksiyon", "Eks. Red."),
                SubtypeItem("stub_end", "Lap Joint Stub End", "Stub End", "STUB", "ASME B16.9", "Flared fitting used with lap joint loose flanges.", "Serbest döner flanşlar için alın kaynaklı yaka.", "modern_ftbw11.svg", "Alın Kaynaklı Yaka (Stub End)", "Yaka (Stub)")
            )
            "fittings_sw" -> listOf(
                SubtypeItem("sw_elbow_90", "90° Socket Weld Elbow", "90° SW Elbow", "SW 90°", "ASME B16.11", "Forged high pressure 90° socket elbow.", "Dövme çelik yüksek basınç soketli 90° dirsek.", "modern_ftsw1.svg", "90° Soket Dirsek", "90° Dirsek"),
                SubtypeItem("sw_elbow_45", "45° Socket Weld Elbow", "45° SW Elbow", "SW 45°", "ASME B16.11", "Forged high pressure 45° socket elbow.", "Dövme çelik yüksek basınç soketli 45° dirsek.", "modern_ftsw2.svg", "45° Soket Dirsek", "45° Dirsek"),
                SubtypeItem("sw_tee", "Socket Weld Tee", "SW Tee", "SW TEE", "ASME B16.11", "Forged 3-way high pressure branch tee.", "Dövme çelik yüksek basınç soketli tee.", "modern_ftsw3.svg", "Soketli Te", "Soketli Te"),
                SubtypeItem("sw_cross", "Socket Weld Cross", "SW Cross", "SW CROSS", "ASME B16.11", "Forged 4-way high pressure cross fitting.", "Dövme çelik yüksek basınç soketli istavroz.", "modern_ftsw4.svg", "Soketli İstavroz", "İstavroz"),
                SubtypeItem("sw_coupling", "Full Coupling", "Full Coupling", "CPLG", "ASME B16.11", "In-line high pressure socket coupling.", "Yüksek basınç soketli tam manşon.", "modern_ftsw5.svg", "Soketli Tam Manşon", "Tam Manşon"),
                SubtypeItem("sw_half_coupling", "Half Coupling", "Half Coupling", "HALF-C", "ASME B16.11", "Single socket high pressure branch coupling.", "Tek taraf soketli yarım manşon.", "modern_ftsw6.svg", "Soketli Yarım Manşon", "Yarım Manşon"),
                SubtypeItem("sw_cap", "Socket Weld Cap", "SW Cap", "SW CAP", "ASME B16.11", "High pressure socket end closure cap.", "Soketli boru sonlandırma kepi.", "modern_ftsw7.svg", "Soketli Kep", "Soketli Kep"),
                SubtypeItem("sw_red_coupling", "Reducing Coupling", "Red Coupling", "RED-C", "ASME B16.11", "Diameter reducing socket coupling.", "Soketli çap düşürücü redüksiyon.", "modern_ftsw8.svg", "Soketli Redüksiyon", "Redüksiyon"),
                SubtypeItem("sw_union", "Socket Weld Union", "SW Union", "UNION", "ASME B16.11", "High pressure 3-piece socket weld union.", "Dövme çelik soketli konik rakor.", "modern_ftsw9.svg", "Soketli Konik Rakor", "Konik Rakor")
            )
            "valves" -> listOf(
                SubtypeItem("vlv_gate", "Gate Valve", "Gate Valve", "GATE", "ASME B16.34", "Linear motion isolation valve.", "Tam açık/kapalı hat kesme vanası.", "modern_vlfl1.svg", "Sürgülü Vana (Gate Valve)", "Sürgülü Vana"),
                SubtypeItem("vlv_globe", "Globe Valve", "Globe Valve", "GLOBE", "ASME B16.34", "Throttling and flow regulation valve.", "Akış ayar ve kısma vanası.", "modern_vlfl2.svg", "Glob Vana (Globe Valve)", "Glob Vana"),
                SubtypeItem("vlv_check", "Swing Check Valve", "Check Valve", "CHECK", "ASME B16.34", "Backflow prevention valve.", "Geri akış önleme çekvalfi.", "modern_vlfl3.svg", "Çalpara Çekvalf (Check Valve)", "Çekvalf"),
                SubtypeItem("vlv_ball", "Ball Valve", "Ball Valve", "BALL", "ASME B16.34", "Quarter-turn fast shutoff valve.", "Çeyrek tur hızlı küresel vana.", "modern_vlfl4.svg", "Küresel Vana (Ball Valve)", "Küresel Vana")
            )
            "gaskets" -> listOf(
                SubtypeItem("gsk_rtj", "RTJ Ring Joint", "RTJ Ring", "RTJ", "ASME B16.20", "Metallic octagonal/oval ring for high pressure.", "Yüksek basınç metal halka conta.", "modern_grtj1.svg", "RTJ Metal Halka Conta", "RTJ Conta"),
                SubtypeItem("gsk_swg", "Spiral Wound Gasket", "Spiral Wound", "SWG", "ASME B16.20", "V-shaped metallic strip with graphite filler.", "Grafit dolgulu spiral sarımlı conta.", "modern_gspr1.svg", "Spiral Sarımlı Conta", "Spiral Conta"),
                SubtypeItem("gsk_flat", "Flat Ring Gasket", "Flat Gasket", "FLAT", "ASME B16.21", "Non-metallic full face/ring gasket.", "Ametalsiz düz flanş contası.", "modern_gflt1.svg", "Düz Conta (Flat Gasket)", "Düz Conta")
            )
            "blanks" -> listOf(
                SubtypeItem("blk_fig8", "Figure-8 Spectacle Blind", "Spectacle Blind", "FIG-8", "ASME B16.48", "Combined open spacer and solid blank.", "Açık ve kapalı döner gözlük kör.", "modern_blnu1.svg", "Gözlük Kör (Spectacle Blind)", "Gözlük Kör"),
                SubtypeItem("blk_paddle", "Paddle Blank", "Paddle Blank", "BLANK", "ASME B16.48", "Handle-equipped solid isolation disc.", "Kulplu tek parça körleme diski.", "modern_blnu2.svg", "Saplı Körleme Diski", "Saplı Kör"),
                SubtypeItem("blk_spacer", "Paddle Spacer", "Paddle Spacer", "SPACER", "ASME B16.48", "Open ring spacer maintaining line length.", "Kulplu açık ara halka.", "modern_blnu3.svg", "Saplı Ara Halka (Spacer)", "Ara Halka")
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
            "fittings_bw" -> buildButtweldData(subtype, subtypes, cleanNpsNum, dn, primarySel, secondarySel)
            "fittings_sw" -> buildForgedData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "valves" -> buildValveData(subtype, subtypes, cleanNpsNum, dn, primarySel, secondarySel ?: "RF")
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
        val primaryOptions = when (subtype.id) {
            "sw" -> listOf("150#", "300#", "600#", "1500#")
            "so" -> listOf("150#", "300#", "400#", "600#", "900#", "1500#")
            else -> listOf("150#", "300#", "400#", "600#", "900#", "1500#", "2500#")
        }
        val effectiveRatingStr = if (ratingStr in primaryOptions) ratingStr else primaryOptions.first()
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 150

        val availableNps = when {
            subtype.id == "sw" -> listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"")
            rating == 2500 -> listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"", "5\"", "6\"", "8\"", "10\"", "12\"")
            else -> listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\"")
        }
        val rawNpsWithQuotes = if (nps.endsWith("\"")) nps else "$nps\""
        val effectiveNps = if (rawNpsWithQuotes in availableNps) rawNpsWithQuotes else if (availableNps.contains("2\"")) "2\"" else availableNps.first()
        val cleanNps = effectiveNps.replace("\"", "").trim()
        val effectiveDn = getNpsDn(cleanNps)

        val secondaryOptions = when {
            subtype.id == "lj" -> listOf("Flat (Stub End)")
            rating == 150 -> listOf("RF", "FF")
            rating == 300 -> listOf("RF", "FF", "RTJ")
            rating == 2500 -> listOf("RTJ", "RF")
            else -> listOf("RF", "RTJ")
        }
        val effectiveFacing = if (facing in secondaryOptions) facing else secondaryOptions.first()

        var od = Double.NaN
        var thk = Double.NaN
        var hubX = Double.NaN
        var lenY = Double.NaN
        var bore = Double.NaN
        var rf = Double.NaN
        var pcd = Double.NaN
        var bolts = 0
        var boltSz = "N/A"
        var studL = Double.NaN
        var wt = Double.NaN
        var ringNumber = ""

        val flgLike = when (subtype.id) {
            "bld" -> "Blind"
            "wn" -> "Welding Neck"
            "thd" -> "Threaded"
            "lj" -> "Lap Joint"
            "sw" -> "Socket Weld"
            else -> "Slip On"
        }
        val flgRows = tableData("tbl_flanges_asme_b16_5").filter { r ->
            val n = s(r, "nps")
            (n == cleanNps || n == "$cleanNps\"" || n == cleanNps.replace(" ", "+")) &&
                    s(r, "rating") == rating.toString() &&
                    s(r, "flange_type").contains(flgLike) &&
                    d(r, "outer_diameter_mm") > 0.0
        }
        val found = flgRows.firstOrNull()
        if (found != null) {
            od = d(found, "outer_diameter_mm")
            thk = d(found, "thickness_mm")
            hubX = d(found, "hub_diameter_mm")
            lenY = d(found, "length_thru_hub_mm")
            bore = d(found, "bore_diameter_mm")
            rf = d(found, "rf_diameter_mm")
            pcd = d(found, "pcd_mm")
            bolts = d(found, "num_bolts").toInt()
            val rawBolt = s(found, "bolt_diameter_in")
            boltSz = rawBolt.replace("0+", "").replace("\"", "").trim() + "\""
            studL = d(found, "stud_bolt_length_mm")
            wt = d(found, "weight_kg")
            val rawRing = s(found, "rtj_ring_number")
            ringNumber = if (!rawRing.isNullOrBlank()) {
                val r = rawRing.trim().replace("'", "")
                if (r.startsWith("R", ignoreCase = true)) r else "R$r"
            } else ""
        }

        // Missing flange rows remain N/A; no estimated dimensions.

        val rows = mutableListOf<DimensionRow>()
        rows.add(DimensionRow("od", "Outside Diameter (O)", "Dış Çap (O)", "${fmt(od)} mm", "${fmt(od/25.4)}\"", "dimensions"))
        rows.add(DimensionRow("thk", "Min Thickness (T)", "Min. Et Kalınlığı (T)", "${fmt(thk)} mm", "${fmt(thk/25.4)}\"", "dimensions"))

        if (subtype.id != "bld") {
            rows.add(DimensionRow("hub", "Base Hub (X)", "Boyun Tabanı (X)", "${fmt(hubX)} mm", "${fmt(hubX/25.4)}\"", "dimensions"))
            rows.add(DimensionRow("len", "Length Thru Hub (Y)", "Toplam Boyun Boyu (Y)", "${fmt(lenY)} mm", "${fmt(lenY/25.4)}\"", "dimensions"))
        }

        when (subtype.id) {
            "bld" -> {
                rows.add(DimensionRow("bore", "Inside Bore (B)", "İç Delik (B)", "N/A (Solid Disc)", "N/A (Kör Disk)", "dimensions"))
            }
            "thd" -> {
                rows.add(DimensionRow("bore", "Thread Standard", "Diş Standardı", "$cleanNps\" NPT (ASME B1.20.1)", "$cleanNps\" NPT (ASME B1.20.1)", "dimensions"))
            }
            "sw" -> {
                rows.add(DimensionRow("bore", "Socket Bore (B)", "Soket Çapı (B)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "dimensions"))
                val socketDepth = when (cleanNps) {
                    "1/2" -> 9.5; "3/4" -> 11.0; "1" -> 12.5; "1 1/4" -> 14.5; "1 1/2" -> 16.0; "2" -> 17.5; "2 1/2" -> 19.0; "3" -> 20.5; else -> 17.5
                }
                rows.add(DimensionRow("sock_depth", "Socket Depth (J)", "Soket Derinliği (J)", "${fmt(socketDepth)} mm", "${fmt(socketDepth/25.4)}\"", "dimensions"))
            }
            "lj" -> {
                rows.add(DimensionRow("bore", "Bore Clearance (B)", "Yaka Boşluk Çapı (B)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "dimensions"))
                val cornerRadius = when {
                    effectiveDn <= 50 -> 8.0
                    effectiveDn <= 100 -> 10.0
                    effectiveDn <= 150 -> 11.0
                    else -> 13.0
                }
                rows.add(DimensionRow("radius", "Corner Radius (r)", "Köşe Radyüsü (r)", "${fmt(cornerRadius)} mm", "${fmt(cornerRadius/25.4)}\"", "dimensions"))
            }
            else -> {
                rows.add(DimensionRow("bore", "Inside Bore (B)", "İç Delik (B)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "dimensions"))
            }
        }

        if (subtype.id == "lj") {
            rows.add(DimensionRow("rf", "Flange Facing", "Flanş Alnı", "Flat (Mates with Stub End)", "Düz (Yaka ile Temas)", "dimensions"))
        } else if (effectiveFacing == "RTJ") {
            rows.add(DimensionRow("rf", "Facing Type", "Conta Yüzeyi", "RTJ Groove", "RTJ Halka Kanalı", "dimensions"))
            if (ringNumber.isNotBlank()) {
                rows.add(DimensionRow("ring", "RTJ Ring Number", "RTJ Halka No", ringNumber, ringNumber, "dimensions"))
            }
        } else if (effectiveFacing == "FF") {
            rows.add(DimensionRow("rf", "Flange Facing", "Conta Yüzeyi", "Flat Face (FF)", "Düz Alın (FF)", "dimensions"))
        } else {
            rows.add(DimensionRow("rf", "Raised Face Dia (R)", "Conta Yüzeyi Çapı (R)", "${fmt(rf)} mm", "${fmt(rf/25.4)}\"", "dimensions"))
        }

        rows.add(DimensionRow("pcd", "Bolt Circle (PCD)", "Cıvata Dairesi (PCD)", "${fmt(pcd)} mm", "${fmt(pcd/25.4)}\"", "dimensions"))
        rows.add(DimensionRow("bolts", "Bolting", "Cıvata Adet & Çap", "$bolts × $boltSz", "$bolts × $boltSz", "dimensions"))
        rows.add(DimensionRow("stud", "Stud Length", "Saplama Boyu", "${fmt(studL)} mm", "${fmt(studL/25.4)}\"", "dimensions"))
        rows.add(DimensionRow("weight", "Approx. Weight", "Tahmini Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "dimensions"))

        return ComponentData(
            categoryId = "flanges",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = availableNps,
            selectedNps = effectiveNps,
            dn = effectiveDn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveRatingStr,
            secondarySelectorLabelEn = "Facing",
            secondarySelectorLabelTr = "Yüzey",
            secondaryOptions = secondaryOptions,
            selectedSecondary = effectiveFacing,
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
        val primaryOptions = listOf(
            "5", "5S", "10", "10S", "20", "30", "40", "40S", "STD", "60", "80", "80S", "XS", "100", "120", "140", "160", "XXS"
        )
        val effectiveSchedule = if (selectedSchedule in primaryOptions) selectedSchedule else "STD"

        var odMm = Double.NaN
        var wallThkMm = Double.NaN
        var idMm = Double.NaN
        var wtEmptyKgM = Double.NaN
        var wtWaterKgM = Double.NaN
        var inertia = Double.NaN

        val cleanNps = nps.replace("\"", "").trim()
        val pipeRow = tableData("tbl_pipes").firstOrNull { r ->
            val n = s(r, "nps")
            (n == cleanNps || n == "$cleanNps\"" || n == cleanNps.replace(" ", "+")) &&
                    s(r, "schedule").equals(effectiveSchedule, ignoreCase = true)
        }
        if (pipeRow != null) {
            odMm = d(pipeRow, "outer_diameter_mm")
            wallThkMm = d(pipeRow, "wall_thickness_mm")
            idMm = d(pipeRow, "inner_diameter_mm")
            wtEmptyKgM = d(pipeRow, "weight_empty_kg_m")
            wtWaterKgM = d(pipeRow, "weight_water_kg_m")
            inertia = d(pipeRow, "moment_of_inertia_cm4")
        }

        // Missing source row: never manufacture pipe dimensions.

        val rows = listOf(
            DimensionRow("od", "Outside Diameter (OD)", "Dış Çap (OD)", "${fmt(odMm)} mm", "${fmt(odMm/25.4)}\"", "pipes_sch"),
            DimensionRow("thk", "Wall Thickness (t)", "Et Kalınlığı (t)", "${fmt(wallThkMm)} mm", "${fmt(wallThkMm/25.4)}\"", "pipes_sch"),
            DimensionRow("id", "Inside Diameter (ID)", "İç Çap (ID)", "${fmt(idMm)} mm", "${fmt(idMm/25.4)}\"", "pipes_sch"),
            DimensionRow("wt_empty", "Pipe Weight (Empty)", "Boru Ağırlığı (Boş)", "${fmt(wtEmptyKgM)} kg/m", "${fmt(wtEmptyKgM*0.6719)} lb/ft", "pipes_sch"),
            DimensionRow("wt_water", "Water-Filled Pipe Weight", "Su Dolu Boru Ağırlığı", "${fmt(wtWaterKgM)} kg/m", "${fmt(wtWaterKgM*0.6719)} lb/ft", "pipes_sch"),
            DimensionRow("inertia", "Moment of Inertia (I)", "Atalet Momenti (I)", "${fmt(inertia)} cm⁴", "${fmt(inertia*0.0240)} in⁴", "pipes_sch")
        )

        return ComponentData(
            categoryId = "pipes",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/8\"", "1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "3 1/2\"", "4\"", "5\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\""),
            selectedNps = if (nps.endsWith("\"")) nps else "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Schedule",
            primarySelectorLabelTr = "Et Kalınlığı",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveSchedule,
            dimensions = rows
        )
    }

    private fun buildButtweldData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        sched: String,
        branchSel: String?
    ): ComponentData {
        val compType = when (subtype.id) {
            "elbow_90_lr" -> "90° Long Radius Elbow"
            "elbow_90_sr" -> "90° Short Radius Elbow"
            "elbow_45" -> "45° Elbow"
            "return_180_lr" -> "180° Long Radius Return"
            "return_180_sr" -> "180° Short Radius Return"
            "tee_eq" -> "Equal Tee"
            "tee_red" -> "Reducing Tee"
            "cap_bw" -> "Butt Weld Cap"
            "red_con" -> "Concentric Reducer"
            "red_ecc" -> "Eccentric Reducer"
            "stub_end" -> "Lap Joint Stub End"
            else -> subtype.title
        }

        val primaryOptions = when (subtype.id) {
            "stub_end" -> listOf("5", "5S", "10S", "40S", "STD")
            else -> listOf("5", "5S", "10", "10S", "20", "30", "40", "40S", "STD", "60", "80", "80S", "XS", "100", "120", "140", "160", "XXS")
        }
        val effectiveSched = if (sched in primaryOptions) sched else "STD"

        var od = Double.NaN
        var centerEnd = Double.NaN
        var radiusMm = Double.NaN
        var branchCenterEnd = Double.NaN
        var centerCenter = Double.NaN
        var backToFace = Double.NaN
        var lengthHeight = Double.NaN
        var wt = Double.NaN

        val cleanNps = nps.replace("\"", "").trim()
        val matrixType = subtype.id in setOf("tee_red", "red_con")
        val branchOptions = if (matrixType) tableData("tbl_fittings_buttweld_b16_9")
            .filter { s(it, "nps") == cleanNps && s(it, "schedule") == effectiveSched && s(it, "component_type") == compType }
            .map { s(it, "branch_nps") }.filter { it.isNotBlank() }.distinct() else emptyList()
        val selectedBranch = branchSel?.replace("\"", "")?.takeIf { it in branchOptions } ?: branchOptions.firstOrNull()
        val found = tableData("tbl_fittings_buttweld_b16_9").firstOrNull { r ->
            val n = s(r, "nps")
            (n == cleanNps || n == "$cleanNps\"" || n == cleanNps.replace(" ", "+")) &&
                    s(r, "component_type") == compType &&
                    s(r, "schedule").equals(effectiveSched, ignoreCase = true) &&
                    (!matrixType || s(r, "branch_nps") == selectedBranch)
        }

        if (found != null) {
            od = d(found, "outer_diameter_mm")
            centerEnd = d(found, "center_to_end_mm")
            radiusMm = found["radius_mm"]?.toDoubleOrNull() ?: Double.NaN
            branchCenterEnd = d(found, "branch_center_to_end_mm")
            centerCenter = d(found, "center_to_center_mm")
            backToFace = d(found, "back_to_face_mm")
            lengthHeight = d(found, "overall_length_height_mm")
            wt = d(found, "weight_kg")
        }

        // Missing fitting measurements are N/A, not OD-based estimates.

        val rows = mutableListOf<DimensionRow>()
        rows.add(DimensionRow("od", "Outside Diameter (OD)", "Dış Çap (OD)", "${fmt(od)} mm", "${fmt(od / 25.4)}\"", "buttweld"))

        when (subtype.id) {
            "elbow_90_lr", "elbow_90_sr", "elbow_45" -> {
                rows.add(DimensionRow("center_end", "Center-to-End (A)", "Merkez-Uç Mesafesi (A)", "${fmt(centerEnd)} mm", "${fmt(centerEnd / 25.4)}\"", "buttweld"))
                val radius = radiusMm // Missing radius is unknown; do not derive from OD.
                rows.add(DimensionRow("radius", "Centerline Radius (R)", "Büküm Yarıçapı (R)", "${fmt(radius)} mm", "${fmt(radius / 25.4)}\"", "buttweld"))
            }
            "return_180_lr", "return_180_sr" -> {
                rows.add(DimensionRow("center_center", "Center-to-Center (O)", "Merkezden Merkeze (O)", "${fmt(centerCenter)} mm", "${fmt(centerCenter / 25.4)}\"", "buttweld"))
                rows.add(DimensionRow("back_to_face", "Back-to-Face (K)", "Arka-Ön Yüz Mesafesi (K)", "${fmt(backToFace)} mm", "${fmt(backToFace / 25.4)}\"", "buttweld"))
            }
            "tee_eq" -> {
                rows.add(DimensionRow("center_end", "Center-to-End Run (C)", "Gövde Merkez-Uç (C)", "${fmt(centerEnd)} mm", "${fmt(centerEnd / 25.4)}\"", "buttweld"))
                rows.add(DimensionRow("branch_center_end", "Center-to-End Branch (M)", "Branşman Merkez-Uç (M)", "${fmt(centerEnd)} mm", "${fmt(centerEnd / 25.4)}\"", "buttweld"))
            }
            "tee_red" -> {
                rows.add(DimensionRow("center_end", "Center-to-End Run (C)", "Gövde Merkez-Uç (C)", "${fmt(centerEnd)} mm", "${fmt(centerEnd / 25.4)}\"", "buttweld"))
                val branchCe = branchCenterEnd
                rows.add(DimensionRow("branch_center_end", "Center-to-End Branch (M)", "Branşman Merkez-Uç (M)", "${fmt(branchCe)} mm", "${fmt(branchCe / 25.4)}\"", "buttweld"))
            }
            "cap_bw" -> {
                rows.add(DimensionRow("length", "Length / Height (E)", "Kep Toplam Boyu (E)", "${fmt(lengthHeight)} mm", "${fmt(lengthHeight / 25.4)}\"", "buttweld"))
            }
            "red_con", "red_ecc" -> {
                val branch = selectedBranch?.let { "$it\"" } ?: "N/A"
                rows.add(DimensionRow("small_nps", "Small End Nominal Size (NPS)", "Küçük Uç Anma Çapı (NPS)", branch, branch, "buttweld"))
                rows.add(DimensionRow("length", "End-to-End Length (H)", "Toplam Redüksiyon Boyu (H)", "${fmt(lengthHeight)} mm", "${fmt(lengthHeight / 25.4)}\"", "buttweld"))
            }
            "stub_end" -> {
                val lapDia = if (backToFace > 0) backToFace else od * 1.5
                rows.add(DimensionRow("lap_dia", "Flared Lap Diameter (G)", "Yaka Flanş Çapı (G)", "${fmt(lapDia)} mm", "${fmt(lapDia / 25.4)}\"", "buttweld"))
                rows.add(DimensionRow("length", "Overall Length (F)", "Toplam Yaka Boyu (F)", "${fmt(lengthHeight)} mm", "${fmt(lengthHeight / 25.4)}\"", "buttweld"))
                rows.add(DimensionRow("radius", "Corner Radius (r)", "Yaka Köşe Radyüsü (r)", if (dn <= 50) "8.0 mm" else "12.5 mm", if (dn <= 50) "0.31\"" else "0.50\"", "buttweld"))
            }
            else -> {
                rows.add(DimensionRow("center_end", "Center-to-End", "Merkez-Uç Mesafesi", "${fmt(centerEnd)} mm", "${fmt(centerEnd / 25.4)}\"", "buttweld"))
            }
        }

        rows.add(DimensionRow("weight", "Approx. Weight", "Tahmini Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt * 2.20462)} lb", "buttweld"))

        return ComponentData(
            categoryId = "fittings_bw",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"", "5\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\""),
            selectedNps = if (nps.endsWith("\"")) nps else "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Schedule",
            primarySelectorLabelTr = "Seri",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveSched,
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
        val primaryOptions = when (subtype.id) {
            "sw_union", "sw_red_coupling" -> listOf("3000#", "6000#")
            else -> listOf("3000#", "6000#", "9000#")
        }
        val effectiveRatingStr = if (ratingStr in primaryOptions) ratingStr else primaryOptions.first()
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 3000

        val availableNps = when (subtype.id) {
            "sw_union" -> listOf("1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"")
            "sw_red_coupling" -> {
                if (rating == 6000) {
                    listOf("1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"")
                } else {
                    listOf("1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"")
                }
            }
            "sw_elbow_90", "sw_elbow_45", "sw_tee", "sw_cross" -> {
                when (rating) {
                    9000 -> listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"")
                    6000 -> listOf("1/8\"", "1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"")
                    else -> listOf("1/8\"", "1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"")
                }
            }
            "sw_cap" -> {
                when (rating) {
                    9000 -> listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"")
                    6000 -> listOf("1/8\"", "1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"")
                    else -> listOf("1/8\"", "1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"")
                }
            }
            else -> {
                if (rating == 9000) {
                    listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"")
                } else {
                    listOf("1/8\"", "1/4\"", "3/8\"", "1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"")
                }
            }
        }

        val cleanNpsWithQuote = if (nps.endsWith("\"")) nps else "$nps\""
        val effectiveNpsWithQuote = if (cleanNpsWithQuote in availableNps) cleanNpsWithQuote else availableNps.first()
        val effectiveNps = effectiveNpsWithQuote.replace("\"", "")

        var sockDepth = 0.0
        var sockDia = 0.0
        var centerEnd = 0.0
        var bore = 0.0
        var sockThk = 0.0
        var bodyWallThk = 0.0
        var lenVal = 0.0
        var wt = 0.0

        val compTypeLike = when (subtype.id) {
            "sw_elbow_90" -> "90° Elbow"
            "sw_elbow_45" -> "45° Elbow"
            "sw_tee" -> "Tee"
            "sw_cross" -> "Cross"
            "sw_coupling" -> "Full Coupling"
            "sw_half_coupling" -> "Half Coupling"
            "sw_cap" -> "Cap"
            "sw_red_coupling" -> "Reducing Coupling"
            "sw_union" -> "Union"
            else -> subtype.title
        }

        val swRows = tableData("tbl_fittings_socketweld_b16_11").filter { r ->
            val n = s(r, "nps")
            (n == effectiveNps || n == "$effectiveNps\"" || n == "0+$effectiveNps") &&
                    s(r, "rating") == rating.toString() &&
                    s(r, "component_type").contains(compTypeLike)
        }
        val swFound = swRows.firstOrNull()
        if (swFound != null) {
            sockDepth = d(swFound, "socket_depth_mm")
            sockDia = d(swFound, "socket_diameter_mm")
            centerEnd = d(swFound, "center_to_end_mm")
            bore = d(swFound, "bore_diameter_mm")
            sockThk = d(swFound, "socket_wall_thickness_mm")
            bodyWallThk = d(swFound, "body_wall_thickness_mm")
            lenVal = d(swFound, "length_mm")
            wt = d(swFound, "weight_kg")
        }

        val rows = mutableListOf<DimensionRow>()
        rows.add(DimensionRow("sock_dia", "Socket Bore (B)", "Soket Çapı (B)", "${fmt(sockDia)} mm", "${fmt(sockDia/25.4)}\"", "forged"))
        rows.add(DimensionRow("sock_depth", "Socket Depth (J)", "Soket Derinliği (J)", "${fmt(sockDepth)} mm", "${fmt(sockDepth/25.4)}\"", "forged"))

        when (subtype.id) {
            "sw_coupling" -> {
                val centerGap = lenVal
                val overallLen = if (centerGap > 0) sockDepth * 2.0 + centerGap else sockDepth * 2.0
                rows.add(DimensionRow("overall_len", "Overall Length (W)", "Toplam Boy (W)", "${fmt(overallLen)} mm", "${fmt(overallLen/25.4)}\"", "forged"))
                if (centerGap > 0) {
                    rows.add(DimensionRow("center_gap", "Center Gap (E)", "Merkez Boşluğu (E)", "${fmt(centerGap)} mm", "${fmt(centerGap/25.4)}\"", "forged"))
                }
                if (bore > 0) rows.add(DimensionRow("bore", "Bore Diameter (D)", "İç Delik Çapı (D)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "forged"))
                if (sockThk > 0) rows.add(DimensionRow("sock_thk", "Socket Wall (C)", "Soket Et Kalınlığı (C)", "${fmt(sockThk)} mm", "${fmt(sockThk/25.4)}\"", "forged"))
            }
            "sw_half_coupling", "sw_union", "sw_red_coupling" -> {
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
            availableNps = availableNps,
            selectedNps = effectiveNpsWithQuote,
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveRatingStr,
            dimensions = rows
        )
    }

    private fun buildValveData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        ratingStr: String,
        connection: String
    ): ComponentData {
        val primaryOptions = listOf("150#", "300#", "400#", "600#", "900#", "1500#", "2500#")
        val effectiveRatingStr = if (ratingStr in primaryOptions) ratingStr else "150#"
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 150
        val connectionOptions = listOf("RF", "RTJ", "BW")
        val effectiveConnection = if (connection in connectionOptions) connection else "RF"

        var faceToFace = Double.NaN
        var hwH = Double.NaN
        var hwD = Double.NaN
        var wt = Double.NaN

        val cleanNps = nps.replace("\"", "").trim()
        val valveType = when (subtype.id) {
            "vlv_gate" -> "Gate Valve"
            "vlv_globe" -> "Globe Valve"
            "vlv_check" -> "Check Valve"
            "vlv_ball" -> "Ball Valve"
            else -> subtype.title
        }

        val valveRow = tableData("tbl_valves_asme_b16_10").firstOrNull { r ->
            val n = s(r, "nps")
            (n == cleanNps || n == "$cleanNps\"" || n == cleanNps.replace(" ", "+")) &&
                    s(r, "rating") == rating.toString() &&
                    s(r, "valve_type").equals(valveType, ignoreCase = true)
        }
        if (valveRow != null) {
            val faceColumn = when (effectiveConnection) {
                "RTJ" -> "face_to_face_rtj_mm"
                "BW" -> "face_to_face_bw_mm"
                else -> "face_to_face_mm"
            }
            faceToFace = valveRow[faceColumn]?.toDoubleOrNull() ?: Double.NaN
            hwH = d(valveRow, "handwheel_height_mm")
            hwD = d(valveRow, "handwheel_diameter_mm")
            wt = if (effectiveConnection == "BW") d(valveRow, "weight_bw_kg") else d(valveRow, "weight_kg")
        }

        // Missing measurement stays unknown.

        val rows = mutableListOf(
            DimensionRow("f2f", "Face-to-Face (L)", "Alından Alına Boy (L)", "${fmt(faceToFace)} mm", "${fmt(faceToFace/25.4)}\"", "valves_def"),
            DimensionRow("flange_od", "Flange Outside Dia (O)", "Flanş Dış Çapı (O)", "N/A", "N/A", "valves_def")
        )
        if (hwH > 0) {
            rows.add(DimensionRow("hw_h", "Handwheel Height (H)", "Volan Yüksekliği (H)", "${fmt(hwH)} mm", "${fmt(hwH/25.4)}\"", "valves_def"))
        }
        if (hwD > 0) {
            rows.add(DimensionRow("hw_d", "Handwheel Diameter (W)", "Volan Çapı (W)", "${fmt(hwD)} mm", "${fmt(hwD/25.4)}\"", "valves_def"))
        }
        rows.add(DimensionRow("weight", "Estimated Weight", "Tahmini Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "valves_def"))

        return ComponentData(
            categoryId = "valves",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/2\"", "2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\""),
            selectedNps = if (nps.endsWith("\"")) nps else "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveRatingStr,
            secondarySelectorLabelEn = "End connection",
            secondarySelectorLabelTr = "Bağlantı",
            secondaryOptions = connectionOptions,
            selectedSecondary = effectiveConnection,
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
        val primaryOptions = when (subtype.id) {
            "gsk_rtj" -> listOf("300#", "400#", "600#", "900#", "1500#", "2500#")
            "gsk_flat" -> listOf("150#", "300#", "400#", "600#", "900#")
            else -> listOf("150#", "300#", "400#", "600#", "900#", "1500#", "2500#")
        }
        val effectiveRatingStr = if (ratingStr in primaryOptions) ratingStr else primaryOptions.first()
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 150

        val cleanNps = nps.replace("\"", "").trim()
        val type = when (subtype.id) {
            "gsk_rtj" -> "RTJ Ring Joint"
            "gsk_flat" -> "Flat Ring Gasket"
            else -> "Spiral Wound Gasket"
        }
        val sourceRing = if (subtype.id == "gsk_rtj") {
            tableData("tbl_flanges_asme_b16_5").firstOrNull { row ->
                s(row, "nps") == cleanNps && s(row, "rating") == rating.toString() &&
                    s(row, "flange_type") == "Welding Neck"
            }?.get("rtj_ring_number")?.replace("'", "")?.trim().orEmpty()
        } else ""
        val gasketRow = tableData("tbl_gaskets_and_rtj").firstOrNull { row ->
            if (subtype.id == "gsk_rtj") {
                val wanted = sourceRing.replace("-", "").uppercase()
                wanted.isNotEmpty() && wanted != "N/A" &&
                    s(row, "ring_number").replace("-", "").uppercase() == wanted
            } else {
                s(row, "nps") == cleanNps && s(row, "rating") == rating.toString() &&
                    s(row, "gasket_type") == type
            }
        }
        fun measured(column: String): Double =
            gasketRow?.get(column)?.toDoubleOrNull() ?: Double.NaN
        fun dim(id: String, en: String, tr: String, value: Double) =
            DimensionRow(id, en, tr, "${fmt(value)} mm", "${fmt(value / 25.4)}\"", "gaskets_def")
        val rows = mutableListOf<DimensionRow>()
        if (subtype.id == "gsk_rtj") {
            rows.add(dim("pitch", "Pitch Diameter (P)", "Hatve Çapı (P)", measured("pitch_diameter_mm")))
            rows.add(dim("width", "Ring Width (A)", "Halka Genişliği (A)", measured("ring_width_mm")))
            rows.add(dim("oval", "Oval Height (B)", "Oval Yüksekliği (B)", measured("oval_height_mm")))
            rows.add(dim("oct", "Octagonal Height (H)", "Sekizgen Yüksekliği (H)", measured("oct_height_mm")))
            val ring = gasketRow?.get("ring_number") ?: "N/A"
            rows.add(DimensionRow("ring", "RTJ Ring Number", "RTJ Halka Numarası", ring, ring, "gaskets_def"))
        } else {
            rows.add(dim("id", "Inside Diameter (d1)", "İç Çap (d1)", measured("inner_diameter_mm")))
            rows.add(dim("od", "Outside Diameter (d2)", "Dış Çap (d2)", measured("outer_diameter_mm")))
            rows.add(dim("thk", "Gasket Thickness", "Conta Kalınlığı", measured("thickness_mm")))
        }

        return ComponentData(
            categoryId = "gaskets",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/2\"", "2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\""),
            selectedNps = if (nps.endsWith("\"")) nps else "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveRatingStr,
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
        val primaryOptions = listOf("150#", "300#", "400#", "600#", "900#", "1500#", "2500#")
        val effectiveRatingStr = if (ratingStr in primaryOptions) ratingStr else "150#"
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 150

        var odMm = Double.NaN
        var thkMm = Double.NaN
        var c2c = Double.NaN
        var wt = Double.NaN

        val cleanNps = nps.replace("\"", "").trim()
        val selectedBlankType = when (subtype.id) {
            "blk_paddle" -> "Paddle Blank"
            "blk_spacer" -> "Paddle Spacer"
            else -> "Figure-8 Spectacle Blind"
        }
        val blankRow = tableData("tbl_line_blanks_asme_b16_48").firstOrNull { r ->
            val n = s(r, "nps")
            (n == cleanNps || n == "$cleanNps\"" || n == cleanNps.replace(" ", "+")) &&
                s(r, "rating") == rating.toString() && s(r, "blank_type") == selectedBlankType
        }
        if (blankRow != null) {
            odMm = d(blankRow, "outer_diameter_mm")
            thkMm = d(blankRow, "thickness_mm")
            c2c = d(blankRow, "center_to_center_mm")
            wt = d(blankRow, "weight_spectacle_kg")
        }

        // Missing blank measurements remain N/A.

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
            availableNps = listOf("1/2\"", "3/4\"", "1\"", "1 1/2\"", "2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\""),
            selectedNps = if (nps.endsWith("\"")) nps else "$nps\"",
            dn = dn,
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveRatingStr,
            dimensions = rows
        )
    }

    private val dfPrecision = java.text.DecimalFormat("0.####", java.text.DecimalFormatSymbols(java.util.Locale.US))
    private val dfWeight = java.text.DecimalFormat("0.##", java.text.DecimalFormatSymbols(java.util.Locale.US))

    private fun fmt(d: Double): String {
        if (d.isNaN() || d.isInfinite()) return "N/A"
        val rounded = Math.round(d * 10000.0) / 10000.0
        return dfPrecision.format(rounded)
    }

    private fun fmtWeight(d: Double): String {
        if (d.isNaN() || d.isInfinite()) return "N/A"
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
