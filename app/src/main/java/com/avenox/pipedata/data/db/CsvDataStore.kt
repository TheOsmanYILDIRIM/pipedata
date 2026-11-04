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
                id = "fittings_thd",
                titleEn = "Threaded Fittings",
                titleTr = "Dişli Fitingler",
                std = "ASME B16.11",
                descEn = "Forged threaded elbows, tees, couplings, caps, plugs and bushings",
                descTr = "Dişli dirsek, tee, manşon, kep, tapa ve redüksiyon burçları",
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
            "fittings_thd" -> listOf(
                SubtypeItem("thd_elbow_90", "90° Threaded Elbow", "90° Threaded", "90°", "ASME B16.11", "Forged threaded 90-degree elbow.", "Dişli 90 derece dirsek.", "modern_ftsc1.svg", "90° Dişli Dirsek"),
                SubtypeItem("thd_elbow_45", "45° Threaded Elbow", "45° Threaded", "45°", "ASME B16.11", "Forged threaded 45-degree elbow.", "Dişli 45 derece dirsek.", "modern_ftsc4.svg", "45° Dişli Dirsek"),
                SubtypeItem("thd_tee", "Threaded Tee", "Tee", "TEE", "ASME B16.11", "Forged threaded tee.", "Dişli tee.", "modern_ftsc2.svg", "Dişli Te"),
                SubtypeItem("thd_cross", "Threaded Cross", "Cross", "CROSS", "ASME B16.11", "Forged threaded cross.", "Dişli istavroz.", "modern_ftsc3.svg", "Dişli İstavroz"),
                SubtypeItem("thd_street", "Street Elbow", "Street", "ST", "ASME B16.11", "Street elbow.", "Dişli sokak dirseği.", "modern_ftsc5.svg", "Dişli Sokak Dirseği"),
                SubtypeItem("thd_full", "Full Coupling", "Coupling", "CPL", "ASME B16.11", "Threaded full coupling.", "Dişli tam manşon.", "modern_ftsc6.svg", "Dişli Tam Manşon"),
                SubtypeItem("thd_half", "Half Coupling", "Half", "HALF", "ASME B16.11", "Threaded half coupling.", "Dişli yarım manşon.", "modern_ftsc7.svg", "Dişli Yarım Manşon"),
                SubtypeItem("thd_cap", "Threaded Cap", "Cap", "CAP", "ASME B16.11", "Threaded cap.", "Dişli kep.", "modern_ftsc8.svg", "Dişli Kep"),
                SubtypeItem("thd_square", "Square Head Plug", "Square Plug", "SQ", "ASME B16.11", "Square head plug.", "Kare başlı tapa.", "modern_ftsc9.svg", "Kare Başlı Tapa"),
                SubtypeItem("thd_hex", "Hex Head Plug", "Hex Plug", "HEX", "ASME B16.11", "Hex head plug.", "Altıgen başlı tapa.", "modern_ftsc10.svg", "Altıgen Başlı Tapa"),
                SubtypeItem("thd_round", "Round Head Plug", "Round Plug", "RND", "ASME B16.11", "Round head plug.", "Yuvarlak başlı tapa.", "modern_ftsc11.svg", "Yuvarlak Başlı Tapa"),
                SubtypeItem("thd_bushing", "Hex Head Bushing", "Bushing", "BSH", "ASME B16.11", "Hex head bushing.", "Altıgen başlı burç.", "modern_ftsc12.svg", "Altıgen Başlı Burç"),
                SubtypeItem("thd_flush", "Flush Bushing", "Flush Bushing", "FL", "ASME B16.11", "Flush bushing.", "Gömme burç.", "modern_ftsc13.svg", "Gömme Burç")
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
        secondarySel: String?,
        flangeSchedule: String? = null
    ): ComponentData {
        val cacheKey = "$categoryId|$subtypeIndex|$nps|$primarySel|$secondarySel|$flangeSchedule"
        dataCache[cacheKey]?.let { return it }

        val subtypes = getCategorySubtypes(categoryId)
        val subtype = subtypes.getOrElse(subtypeIndex) { subtypes.first() }
        val cleanNpsNum = nps.replace("\"", "").trim()
        val dn = getNpsDn(cleanNpsNum)

        val result = when (categoryId) {
            "flanges" -> buildFlangeData(subtype, subtypes, cleanNpsNum, dn, primarySel, secondarySel ?: "RF", flangeSchedule)
            "pipes" -> buildPipeData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "fittings_bw" -> buildButtweldData(subtype, subtypes, cleanNpsNum, dn, primarySel, secondarySel)
            "fittings_sw" -> buildForgedData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "fittings_thd" -> buildThreadedData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "valves" -> buildValveData(subtype, subtypes, cleanNpsNum, dn, primarySel, secondarySel ?: "RF")
            "gaskets" -> buildGasketData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            "blanks" -> buildBlankData(subtype, subtypes, cleanNpsNum, dn, primarySel)
            else -> buildFlangeData(subtype, subtypes, cleanNpsNum, dn, primarySel, secondarySel ?: "RF", flangeSchedule)
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
        facing: String,
        flangeSchedule: String?
    ): ComponentData {
        val flgLike = when (subtype.id) {
            "bld" -> "Blind"
            "wn" -> "Welding Neck"
            "thd" -> "Threaded"
            "lj" -> "Lap Joint"
            "sw" -> "Socket Weld"
            else -> "Slip On"
        }
        val availableFlangeRows = tableData("tbl_flanges_asme_b16_5").filter { r ->
            s(r, "flange_type") == flgLike && d(r, "outer_diameter_mm") > 0 &&
                d(r, "pcd_mm") > 0 && d(r, "num_bolts") > 0
        }
        val requestedNps = nps.replace("\"", "").trim()
        val ratingsAtSize = availableFlangeRows.filter { s(it, "nps") == requestedNps }
            .map { s(it, "rating") }.toSet()
        val allowedRatings = when (subtype.id) {
            "sw" -> listOf("150#", "300#", "600#", "1500#")
            "so" -> listOf("150#", "300#", "400#", "600#", "900#", "1500#")
            else -> listOf("150#", "300#", "400#", "600#", "900#", "1500#", "2500#")
        }
        val primaryOptions = allowedRatings.filter { it.removeSuffix("#") in ratingsAtSize }
            .ifEmpty { allowedRatings.filter { option ->
                availableFlangeRows.any { s(it, "rating") == option.removeSuffix("#") }
            } }
        val effectiveRatingStr = if (ratingStr in primaryOptions) ratingStr else primaryOptions.first()
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 150

        val nominalSizeList = listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"",
            "2 1/2\"", "3\"", "3 1/2\"", "4\"", "5\"", "6\"", "8\"", "10\"",
            "12\"", "14\"", "16\"", "18\"", "20\"", "22\"", "24\"")
        val actualSizes = availableFlangeRows.filter { s(it, "rating") == rating.toString() }
            .map { s(it, "nps") }.toSet()
        val availableNps = nominalSizeList.filter { it.removeSuffix("\"") in actualSizes &&
            (subtype.id != "sw" || nominalSizeList.indexOf(it) <= 7) }
        val rawNpsWithQuotes = if (nps.endsWith("\"")) nps else "$nps\""
        val effectiveNps = if (rawNpsWithQuotes in availableNps) rawNpsWithQuotes else if (availableNps.contains("2\"")) "2\"" else availableNps.first()
        val cleanNps = effectiveNps.replace("\"", "").trim()
        val effectiveDn = getNpsDn(cleanNps)
        val scheduleOptions = if (subtype.id == "wn") tableData("tbl_pipes")
            .filter { s(it, "nps") == cleanNps && it["wall_thickness_mm"]?.toDoubleOrNull()?.let { w -> w > 0 } == true }
            .map { s(it, "schedule") }.distinct() else emptyList()
        val selectedSchedule = flangeSchedule?.takeIf { it in scheduleOptions }
            ?: "STD".takeIf { it in scheduleOptions } ?: scheduleOptions.firstOrNull()

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

        val flgRows = availableFlangeRows.filter { r ->
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
            if (subtype.id == "wn" && selectedSchedule != null) {
                val pipe = tableData("tbl_pipes").firstOrNull { r ->
                    s(r, "nps") == cleanNps && s(r, "schedule") == selectedSchedule
                }
                bore = pipe?.get("inner_diameter_mm")?.toDoubleOrNull() ?: Double.NaN
            }
            rf = d(found, "rf_diameter_mm")
            pcd = d(found, "pcd_mm")
            bolts = d(found, "num_bolts").toInt()
            val rawBolt = s(found, "bolt_diameter_in")
            boltSz = rawBolt.replace("0+", "").replace("\"", "").trim() + "\""
            studL = d(found, if (effectiveFacing == "RTJ") "stud_bolt_length_rtj_mm" else "stud_bolt_length_mm")
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
            if (hubX > 0) rows.add(DimensionRow("hub", "Base Hub (X)", "Boyun Tabanı (X)", "${fmt(hubX)} mm", "${fmt(hubX/25.4)}\"", "dimensions"))
            if (lenY > 0) rows.add(DimensionRow("len", "Length Thru Hub (Y)", "Toplam Boyun Boyu (Y)", "${fmt(lenY)} mm", "${fmt(lenY/25.4)}\"", "dimensions"))
        }
        if (found != null) {
            fun sourceDimension(column: String, key: String, en: String, tr: String) {
                val number = found[column]?.toDoubleOrNull()
                if (number != null && number > 0) rows.add(DimensionRow(key, en, tr,
                    "${fmt(number)} mm", "${fmt(number / 25.4)}\"", "dimensions"))
            }
            sourceDimension("hub_a_mm", "hub_a", "Pipe-end Outside Dia (A)", "Boru Ucu Dış Çapı (A)")
            if (subtype.id == "wn") {
                sourceDimension("long_neck_outer_diameter_mm", "long_neck_od", "Long Neck Outside Dia", "Uzun Boyun Dış Çapı")
                sourceDimension("weld_flat_mm", "weld_flat", "Weld Flat", "Kaynak Alın Düzlüğü")
            }
            sourceDimension("raised_face_height_mm", "rf_height", "Raised Face Height", "Conta Alnı Yüksekliği")
            sourceDimension("rtj_pitch_diameter_mm", "rtj_pitch", "RTJ Pitch Diameter", "RTJ Hatve Çapı")
            sourceDimension("rtj_depth_mm", "rtj_depth", "RTJ Groove Depth", "RTJ Kanal Derinliği")
            sourceDimension("rtj_width_mm", "rtj_width", "RTJ Groove Width", "RTJ Kanal Genişliği")
        }

        when (subtype.id) {
            "bld" -> {
                rows.add(DimensionRow("bore", "Inside Bore (B)", "İç Delik (B)", "N/A (Solid Disc)", "N/A (Kör Disk)", "dimensions"))
            }
            "thd" -> {
                rows.add(DimensionRow("bore", "Thread Standard", "Diş Standardı", "$cleanNps\" NPT (ASME B1.20.1)", "$cleanNps\" NPT (ASME B1.20.1)", "dimensions"))
            }
            "sw" -> {
                if (bore > 0) rows.add(DimensionRow("bore", "Socket Bore (B)", "Soket Çapı (B)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "dimensions"))
                val socketDepth = found?.get("socket_depth_mm")?.toDoubleOrNull() ?: Double.NaN
                if (socketDepth > 0) rows.add(DimensionRow("sock_depth", "Socket Depth (J)", "Soket Derinliği (J)", "${fmt(socketDepth)} mm", "${fmt(socketDepth/25.4)}\"", "dimensions"))
            }
            "lj" -> {
                rows.add(DimensionRow("bore", "Bore Clearance (B)", "Yaka Boşluk Çapı (B)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "dimensions"))
                val cornerRadius = found?.get("lapped_radius_mm")?.toDoubleOrNull() ?: Double.NaN
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
        val bolting = if (bolts > 0 && boltSz != "N/A") "$bolts × $boltSz" else "N/A"
        rows.add(DimensionRow("bolts", "Bolting", "Cıvata Adet & Çap", bolting, bolting, "dimensions"))
        if (studL > 0) rows.add(DimensionRow("stud", "Stud Length", "Saplama Boyu", "${fmt(studL)} mm", "${fmt(studL/25.4)}\"", "dimensions"))
        if (wt > 0) rows.add(DimensionRow("weight", "Reference Weight", "Referans Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "dimensions"))

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
            dimensions = rows,
            flangeScheduleOptions = scheduleOptions,
            selectedFlangeSchedule = selectedSchedule
        )
    }

    private fun buildPipeData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        selectedSchedule: String
    ): ComponentData {
        val preferredSchedules = listOf(
            "5", "5S", "10", "10S", "20", "30", "40", "40S", "STD",
            "60", "80", "80S", "XS", "100", "120", "140", "160", "XXS"
        )
        val pipeRows = tableData("tbl_pipes")
        val validRows = pipeRows.filter { d(it, "wall_thickness_mm") > 0.0 && d(it, "outer_diameter_mm") > 0.0 }
        val npsChoices = listOf("1/8", "1/4", "3/8", "1/2", "3/4", "1",
            "1 1/4", "1 1/2", "2", "2 1/2", "3", "3 1/2", "4",
            "5", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24",
            "26", "28", "30", "32", "34", "36", "38", "40", "42", "44", "46", "48")
        val validNps = validRows.map { s(it, "nps") }.toSet()
        val availableSizes = npsChoices.filter { it in validNps }
        val requestedNps = nps.replace("\"", "").trim()
        val effectiveNps = requestedNps.takeIf { it in validNps } ?: availableSizes.firstOrNull() ?: requestedNps
        val optionsAvailable = validRows.filter { s(it, "nps") == effectiveNps }.map { s(it, "schedule") }.toSet()
        val primaryOptions = preferredSchedules.filter { it in optionsAvailable }
        val effectiveSchedule = selectedSchedule.takeIf { it in primaryOptions }
            ?: (if ("40" in primaryOptions) "40" else primaryOptions.firstOrNull() ?: "STD")

        var odMm = Double.NaN
        var wallThkMm = Double.NaN
        var idMm = Double.NaN
        var wtEmptyKgM = Double.NaN
        var wtWaterKgM = Double.NaN
        var inertia = Double.NaN

        val cleanNps = effectiveNps
        val pipeRow = validRows.firstOrNull { r ->
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

        // Imperial figures come from original APK CSV, not converted rounded metric values.
        fun imperial(column: String): Double = pipeRow?.get(column)?.toDoubleOrNull() ?: Double.NaN

        val rows = listOf(
            DimensionRow("od", "Outside Diameter (OD)", "Dış Çap (OD)", "${fmt(odMm)} mm", "${fmt(imperial("outer_diameter_in"))}\"", "pipes_sch"),
            DimensionRow("thk", "Wall Thickness (t)", "Et Kalınlığı (t)", "${fmt(wallThkMm)} mm", "${fmt(imperial("wall_thickness_in"))}\"", "pipes_sch"),
            DimensionRow("id", "Inside Diameter (ID)", "İç Çap (ID)", "${fmt(idMm)} mm", "${fmt(imperial("inner_diameter_in"))}\"", "pipes_sch"),
            DimensionRow("wt_empty", "Pipe Weight (Empty)", "Boru Ağırlığı (Boş)", "${fmt(wtEmptyKgM)} kg/m", "${fmt(imperial("weight_empty_lb_ft"))} lb/ft", "pipes_sch"),
            DimensionRow("wt_water", "Water-Filled Pipe Weight", "Su Dolu Boru Ağırlığı", "${fmt(wtWaterKgM)} kg/m", "${fmt(imperial("weight_water_lb_ft"))} lb/ft", "pipes_sch"),
            DimensionRow("inertia", "Moment of Inertia (I)", "Atalet Momenti (I)", "${fmt(inertia)} cm⁴", "${fmt(imperial("moment_of_inertia_in4"))} in⁴", "pipes_sch")
        )

        return ComponentData(
            categoryId = "pipes",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = availableSizes.map { "$it\"" },
            selectedNps = "$effectiveNps\"",
            dn = getNpsDn(effectiveNps),
            primarySelectorLabelEn = "SCH / Schedule",
            primarySelectorLabelTr = "SCH / Et Kalınlığı",
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

        val preferredSchedules = listOf("5", "5S", "10", "10S", "20", "30",
            "40", "40S", "STD", "60", "80", "80S", "XS", "100", "120", "140", "160", "XXS")
        val componentRows = tableData("tbl_fittings_buttweld_b16_9")
            .filter { r ->
                s(r, "component_type") == compType &&
                    when (subtype.id) {
                        "elbow_90_lr", "elbow_90_sr", "elbow_45", "tee_eq" ->
                            d(r, "center_to_end_mm") > 0
                        "return_180_lr", "return_180_sr" ->
                            d(r, "center_to_center_mm") > 0
                        "tee_red" -> d(r, "branch_center_to_end_mm") > 0
                        else -> d(r, "overall_length_height_mm") > 0
                    }
            }
        val supportedNps = componentRows.map { s(it, "nps") }.toSet()
        val sizeOrder = listOf("1/2", "3/4", "1", "1 1/4", "1 1/2", "2",
            "2 1/2", "3", "3 1/2", "4", "5", "6", "8", "10", "12", "14",
            "16", "18", "20", "22", "24", "26", "28", "30", "32", "34",
            "36", "38", "40", "42", "44", "46", "48")
        val availableSizes = sizeOrder.filter { it in supportedNps }
        val requestedNps = nps.replace("\"", "").trim()
        val effectiveNps = requestedNps.takeIf { it in supportedNps }
            ?: "2".takeIf { it in supportedNps } ?: availableSizes.firstOrNull() ?: requestedNps
        val presentSchedules = componentRows.filter { s(it, "nps") == effectiveNps }
            .map { s(it, "schedule") }.toSet()
        val primaryOptions = preferredSchedules.filter { it in presentSchedules }
        val effectiveSched = sched.takeIf { it in primaryOptions }
            ?: "STD".takeIf { it in primaryOptions }
            ?: primaryOptions.firstOrNull() ?: "STD"

        var od = Double.NaN
        var centerEnd = Double.NaN
        var radiusMm = Double.NaN
        var branchCenterEnd = Double.NaN
        var centerCenter = Double.NaN
        var backToFace = Double.NaN
        var lengthHeight = Double.NaN
        var wt = Double.NaN

        val cleanNps = effectiveNps
        val matrixType = subtype.id in setOf("tee_red", "red_con", "red_ecc")
        val branchOptions = if (matrixType) tableData("tbl_fittings_buttweld_b16_9")
            .filter { s(it, "nps") == cleanNps && s(it, "schedule") == effectiveSched && s(it, "component_type") == compType }
            .map { s(it, "branch_nps") }.filter { it.isNotBlank() }.distinct() else emptyList()
        val selectedBranch = branchSel?.replace("\"", "")?.takeIf { it in branchOptions } ?: branchOptions.firstOrNull()
        val found = componentRows.firstOrNull { r ->
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
                rows.add(DimensionRow("branch_center_end", "Center-to-End Branch (M)", "Branşman Merkez-Uç (M)", "${fmt(branchCenterEnd)} mm", "${fmt(branchCenterEnd / 25.4)}\"", "buttweld"))
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
                val smallOd = found?.get("small_end_outer_diameter_mm")?.toDoubleOrNull() ?: Double.NaN
                if (smallOd > 0) rows.add(DimensionRow("small_od", "Small End Outside Dia (OD2)", "Küçük Uç Dış Çapı (OD2)", "${fmt(smallOd)} mm", "${fmt(smallOd / 25.4)}\"", "buttweld"))
                rows.add(DimensionRow("length", "End-to-End Length (H)", "Toplam Redüksiyon Boyu (H)", "${fmt(lengthHeight)} mm", "${fmt(lengthHeight / 25.4)}\"", "buttweld"))
            }
            "stub_end" -> {
                val lapDia = found?.get("lap_diameter_mm")?.toDoubleOrNull() ?: Double.NaN
                val shortLen = found?.get("short_length_mm")?.toDoubleOrNull() ?: Double.NaN
                val shortWt = found?.get("short_weight_kg")?.toDoubleOrNull() ?: Double.NaN
                rows.add(DimensionRow("lap_dia", "Flared Lap Diameter (G)", "Yaka Flanş Çapı (G)", "${fmt(lapDia)} mm", "${fmt(lapDia / 25.4)}\"", "buttweld"))
                if (shortLen > 0) rows.add(DimensionRow("short_len", "Short Pattern Length", "Kısa Tip Boy (F)", "${fmt(shortLen)} mm", "${fmt(shortLen / 25.4)}\"", "buttweld"))
                rows.add(DimensionRow("length", "Long Pattern Length (F)", "Uzun Tip Boy (F)", "${fmt(lengthHeight)} mm", "${fmt(lengthHeight / 25.4)}\"", "buttweld"))
                if (radiusMm > 0) rows.add(DimensionRow("radius", "Corner Radius (r)", "Yaka Köşe Radyüsü (r)", "${fmt(radiusMm)} mm", "${fmt(radiusMm / 25.4)}\"", "buttweld"))
                if (shortWt > 0) rows.add(DimensionRow("short_wt", "Short Pattern Weight", "Kısa Tip Ağırlık", "${fmtWeight(shortWt)} kg", "${fmtWeight(shortWt * 2.20462)} lb", "buttweld"))
            }
            else -> {
                rows.add(DimensionRow("center_end", "Center-to-End", "Merkez-Uç Mesafesi", "${fmt(centerEnd)} mm", "${fmt(centerEnd / 25.4)}\"", "buttweld"))
            }
        }

        if (wt > 0) rows.add(DimensionRow("weight", "Source Weight", "Kaynak Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt * 2.20462)} lb", "buttweld"))

        return ComponentData(
            categoryId = "fittings_bw",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = availableSizes.map { "$it\"" },
            selectedNps = "$effectiveNps\"",
            dn = getNpsDn(effectiveNps),
            primarySelectorLabelEn = "SCH / Schedule",
            primarySelectorLabelTr = "SCH / Et Kalınlığı",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveSched,
            secondarySelectorLabelEn = if (matrixType) "Branch NPS" else null,
            secondarySelectorLabelTr = if (matrixType) "Branşman Çapı" else null,
            secondaryOptions = branchOptions,
            selectedSecondary = selectedBranch,
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
        if (subtype.id.startsWith("thd_")) {
            return buildThreadedData(subtype, allSubtypes, nps, ratingStr)
        }
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

        val allForType = tableData("tbl_fittings_socketweld_b16_11")
            .filter { s(it, "component_type") == compTypeLike }
        val measuredRows = allForType.filter { row ->
            when (subtype.id) {
                "sw_union", "sw_red_coupling", "sw_half_coupling", "sw_cap" ->
                    d(row, "length_mm") > 0
                else -> d(row, "socket_diameter_mm") > 0 && d(row, "socket_depth_mm") > 0
            }
        }
        val requestedSize = nps.replace("\"", "").trim()
        val supportedRatings = measuredRows.filter { s(it, "nps") == requestedSize }
            .map { s(it, "rating") }.toSet()
        val preferredRatings = when (subtype.id) {
            "sw_union", "sw_red_coupling" -> listOf("3000#", "6000#")
            else -> listOf("3000#", "6000#", "9000#")
        }
        val primaryOptions = preferredRatings.filter { it.removeSuffix("#") in supportedRatings }
            .ifEmpty { preferredRatings.filter { candidate ->
                measuredRows.any { s(it, "rating") == candidate.removeSuffix("#") }
            } }
        val effectiveRatingStr = if (ratingStr in primaryOptions) ratingStr else primaryOptions.first()
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 3000

        val potentialNps = when (subtype.id) {
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

        val presentSizes = measuredRows.filter { s(it, "rating") == rating.toString() }
            .map { s(it, "nps") }.toSet()
        val availableNps = potentialNps.filter { it.replace("\"", "") in presentSizes }
        val cleanNpsWithQuote = if (nps.endsWith("\"")) nps else "$nps\""
        val effectiveNpsWithQuote = cleanNpsWithQuote.takeIf { it in availableNps }
            ?: "2\"".takeIf { it in availableNps }
            ?: availableNps.firstOrNull() ?: cleanNpsWithQuote
        val effectiveNps = effectiveNpsWithQuote.replace("\"", "")

        var sockDepth = Double.NaN
        var sockDia = Double.NaN
        var centerEnd = Double.NaN
        var bore = Double.NaN
        var sockThk = Double.NaN
        var bodyWallThk = Double.NaN
        var lenVal = Double.NaN
        var wt = Double.NaN


        val swRows = measuredRows.filter { r ->
            val n = s(r, "nps")
            (n == effectiveNps || n == "$effectiveNps\"" || n == "0+$effectiveNps") &&
                    s(r, "rating") == rating.toString() &&
                    s(r, "component_type").contains(compTypeLike)
        }
        val swFound = swRows.maxByOrNull { row ->
            listOf("socket_diameter_mm", "socket_depth_mm", "center_to_end_mm",
                "bore_diameter_mm", "socket_wall_thickness_mm", "body_wall_thickness_mm",
                "length_mm", "weight_kg").count { key ->
                row[key]?.trim()?.toDoubleOrNull()?.let { it > 0.0 } == true
            }
        }
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
        if (sockDia > 0) rows.add(DimensionRow("sock_dia", "Socket Bore (B)", "Soket Çapı (B)", "${fmt(sockDia)} mm", "${fmt(sockDia/25.4)}\"", "forged"))
        if (sockDepth > 0) rows.add(DimensionRow("sock_depth", "Socket Depth (J)", "Soket Derinliği (J)", "${fmt(sockDepth)} mm", "${fmt(sockDepth/25.4)}\"", "forged"))

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
                val effectiveLen = if (lenVal > 0) lenVal else centerEnd
                rows.add(DimensionRow("length", "Overall Length (L)", "Toplam Boy (L)", "${fmt(effectiveLen)} mm", "${fmt(effectiveLen/25.4)}\"", "forged"))
                if (bore > 0) rows.add(DimensionRow("bore", "Bore Diameter (D)", "İç Delik Çapı (D)", "${fmt(bore)} mm", "${fmt(bore/25.4)}\"", "forged"))
                if (sockThk > 0) rows.add(DimensionRow("sock_thk", "Socket Wall (C)", "Soket Et Kalınlığı (C)", "${fmt(sockThk)} mm", "${fmt(sockThk/25.4)}\"", "forged"))
            }
            "sw_cap" -> {
                val capThk = if (lenVal > 0) lenVal else sockThk
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
        if (wt > 0) rows.add(DimensionRow("weight", "Source Weight", "Kaynak Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "forged"))

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

    private fun buildThreadedData(
        subtype: SubtypeItem,
        allSubtypes: List<SubtypeItem>,
        nps: String,
        dn: Int,
        ratingStr: String
    ): ComponentData {
        val type = when (subtype.id) {
            "thd_elbow_90" -> "90° Elbow"
            "thd_elbow_45" -> "45° Elbow"
            "thd_tee" -> "Tee"
            "thd_cross" -> "Cross"
            "thd_street" -> "Street Elbow"
            "thd_full" -> "Full Coupling"
            "thd_half" -> "Half Coupling"
            "thd_cap" -> "Cap"
            "thd_square" -> "Square Head Plug"
            "thd_hex" -> "Hex Head Plug"
            "thd_round" -> "Round Head Plug"
            "thd_bushing" -> "Hex Head Bushing"
            "thd_flush" -> "Flush Bushing"
            else -> subtype.title
        }
        val records = tableData("tbl_fittings_threaded_b16_11").filter { row ->
            s(row, "component_type") == type &&
                listOf("center_to_end_mm", "length_mm", "bore_diameter_mm", "weight_kg")
                    .any { d(row, it) > 0 }
        }
        val requestedNps = nps.replace("\"", "").trim()
        val atSize = records.filter { s(it, "nps") == requestedNps }
            .map { s(it, "rating") }.toSet()
        val preferred = listOf("2000#", "3000#", "6000#", "9000#")
        val primaryOptions = preferred.filter { it.removeSuffix("#") in atSize }
            .ifEmpty { preferred.filter { c -> records.any { s(it, "rating") == c.removeSuffix("#") } } }
        val effectiveRating = ratingStr.takeIf { it in primaryOptions }
            ?: primaryOptions.firstOrNull() ?: "3000#"
        val rating = effectiveRating.removeSuffix("#")
        val sizes = records.filter { s(it, "rating") == rating }.map { s(it, "nps") }.toSet()
        val availableNps = listOf("1/8", "1/4", "3/8", "1/2", "3/4",
            "1", "1 1/4", "1 1/2", "2", "2 1/2", "3", "4")
            .filter { it in sizes }.map { "$it\"" }
        val effectiveNps = nps.takeIf { it in availableNps }
            ?: "1\"".takeIf { it in availableNps }
            ?: availableNps.firstOrNull() ?: nps
        val cleanNps = effectiveNps.replace("\"", "").trim()
        val matching = records.filter { s(it, "nps") == cleanNps && s(it, "rating") == rating }
        val record = matching.maxByOrNull { row ->
            listOf("center_to_end_mm", "bore_diameter_mm", "body_wall_thickness_mm",
                "length_mm", "weight_kg").count { d(row, it) > 0 }
        }
        val rows = mutableListOf<DimensionRow>()
        fun addDim(field: String, id: String, en: String, tr: String) {
            val value = record?.get(field)?.toDoubleOrNull()
            if (value != null && value > 0) {
                rows.add(DimensionRow(id, en, tr, "${fmt(value)} mm",
                    "${fmt(value / 25.4)}\"", "forged"))
            }
        }
        addDim("center_to_end_mm", "c2e", "Center-to-End (A)", "Merkezden Uca (A)")
        addDim("bore_diameter_mm", "bore", "Bore Diameter (B)", "İç Delik Çapı (B)")
        addDim("body_wall_thickness_mm", "wall", "Body Wall Thickness", "Gövde Et Kalınlığı")
        addDim("height_mm", "height", "Outside Height (H)", "Dış Yükseklik (H)")
        addDim("across_flats_mm", "across_flats", "Across Flats", "Anahtar Ağzı")
        addDim("head_depth_mm", "head_depth", "Head Depth", "Baş Derinliği")
        addDim("length_mm", "len", "Overall Length (L)", "Toplam Boy (L)")
        val weight = record?.get("weight_kg")?.toDoubleOrNull()
        if (weight != null && weight > 0) rows.add(DimensionRow("weight", "Source Weight",
            "Kaynak Ağırlık", "${fmtWeight(weight)} kg",
            "${fmtWeight(weight * 2.20462)} lb", "forged"))
        return ComponentData(
            categoryId = "fittings_thd",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = availableNps,
            selectedNps = effectiveNps,
            dn = getNpsDn(cleanNps),
            primarySelectorLabelEn = "Class",
            primarySelectorLabelTr = "Sınıf",
            primaryOptions = primaryOptions,
            selectedPrimary = effectiveRating,
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
        val valveType = when (subtype.id) {
            "vlv_gate" -> "Gate Valve"
            "vlv_globe" -> "Globe Valve"
            "vlv_check" -> "Check Valve"
            "vlv_ball" -> "Ball Valve"
            else -> subtype.title
        }
        val allValveRows = tableData("tbl_valves_asme_b16_10").filter {
            s(it, "valve_type").equals(valveType, ignoreCase = true) &&
                (d(it, "face_to_face_mm") > 0 || d(it, "face_to_face_rtj_mm") > 0 ||
                    d(it, "face_to_face_bw_mm") > 0)
        }
        val requestedNps = nps.replace("\"", "").trim()
        val ratingsAtSize = allValveRows.filter { s(it, "nps") == requestedNps }
            .map { s(it, "rating") }.toSet()
        val allowedRatings = listOf("150#", "300#", "400#", "600#", "900#", "1500#", "2500#")
        val primaryOptions = allowedRatings.filter { it.removeSuffix("#") in ratingsAtSize }
            .ifEmpty { allowedRatings.filter { c -> allValveRows.any { s(it, "rating") == c.removeSuffix("#") } } }
        val effectiveRatingStr = ratingStr.takeIf { it in primaryOptions }
            ?: primaryOptions.firstOrNull() ?: "150#"
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 150
        val presentSizes = allValveRows.filter { s(it, "rating") == rating.toString() }
            .map { s(it, "nps") }.toSet()
        val availableNps = listOf("1/2", "3/4", "1", "1 1/4", "1 1/2", "2",
            "2 1/2", "3", "4", "5", "6", "8", "10", "12", "14",
            "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36")
            .filter { it in presentSizes }.map { "$it\"" }
        val effectiveNps = nps.takeIf { it in availableNps }
            ?: "2\"".takeIf { it in availableNps }
            ?: availableNps.firstOrNull() ?: nps

        var faceToFace = Double.NaN
        var hwH = Double.NaN
        var hwD = Double.NaN
        var wt = Double.NaN

        val cleanNps = effectiveNps.replace("\"", "").trim()


        val valveRow = allValveRows.firstOrNull { r ->
            val n = s(r, "nps")
            (n == cleanNps || n == "$cleanNps\"" || n == cleanNps.replace(" ", "+")) &&
                    s(r, "rating") == rating.toString() &&
                    s(r, "valve_type").equals(valveType, ignoreCase = true)
        }
        val connectionColumns = mapOf("RF" to "face_to_face_mm",
            "RTJ" to "face_to_face_rtj_mm", "BW" to "face_to_face_bw_mm")
        val connectionOptions = connectionColumns.filter { (_, column) ->
            valveRow?.get(column)?.toDoubleOrNull()?.let { it > 0.0 } == true
        }.keys.toList()
        val effectiveConnection = connection.takeIf { it in connectionOptions }
            ?: connectionOptions.firstOrNull() ?: "RF"
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

        val rows = mutableListOf<DimensionRow>()
        if (faceToFace > 0) {
            rows.add(DimensionRow("f2f", "Face-to-Face (L)", "Alından Alına Boy (L)", "${fmt(faceToFace)} mm", "${fmt(faceToFace/25.4)}\"", "valves_def"))
        }
        // Flanged valve ends use the mating flange OD from B16.5.
        // For butt-weld valves this is inapplicable and omitted.
        if (effectiveConnection != "BW") {
            val matingFlange = tableData("tbl_flanges_asme_b16_5").firstOrNull { r ->
                s(r, "nps") == cleanNps && s(r, "rating") == rating.toString() &&
                    s(r, "flange_type") == "Welding Neck"
            }
            val flangeOd = matingFlange?.get("outer_diameter_mm")?.toDoubleOrNull()
            if (flangeOd != null && flangeOd > 0) {
                rows.add(DimensionRow("flange_od", "Mating Flange Outside Dia (O)", "Karşı Flanş Dış Çapı (O)",
                    "${fmt(flangeOd)} mm", "${fmt(flangeOd/25.4)}\"", "valves_def"))
            }
        }
        if (hwH > 0) {
            rows.add(DimensionRow("hw_h", "Handwheel Height (H)", "Volan Yüksekliği (H)", "${fmt(hwH)} mm", "${fmt(hwH/25.4)}\"", "valves_def"))
        }
        if (hwD > 0) {
            rows.add(DimensionRow("hw_d", "Handwheel Diameter (W)", "Volan Çapı (W)", "${fmt(hwD)} mm", "${fmt(hwD/25.4)}\"", "valves_def"))
        }
        if (wt > 0) rows.add(DimensionRow("weight", "Source Weight", "Kaynak Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "valves_def"))

        return ComponentData(
            categoryId = "valves",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = availableNps,
            selectedNps = effectiveNps,
            dn = getNpsDn(cleanNps),
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
        val type = when (subtype.id) {
            "gsk_rtj" -> "RTJ Ring Joint"
            "gsk_flat" -> "Flat Ring Gasket"
            else -> "Spiral Wound Gasket"
        }
        val sourceGaskets = tableData("tbl_gaskets_and_rtj")
        val preferredRatings = when (subtype.id) {
            "gsk_rtj" -> listOf("300#", "400#", "600#", "900#", "1500#", "2500#")
            "gsk_flat" -> listOf("150#", "300#", "400#", "600#", "900#")
            else -> listOf("150#", "300#", "400#", "600#", "900#", "1500#", "2500#")
        }
        val matchingFlanges = if (subtype.id == "gsk_rtj") {
            val ringKeys = sourceGaskets.filter { s(it, "gasket_type") == type }
                .map { s(it, "ring_number").replace("-", "").uppercase() }.toSet()
            tableData("tbl_flanges_asme_b16_5").filter { row ->
                val rawRing = s(row, "rtj_ring_number").replace("-", "").uppercase()
                val key = if (rawRing.startsWith("R")) rawRing else "R$rawRing"
                s(row, "flange_type") == "Welding Neck" && key in ringKeys
            }
        } else emptyList()
        val compatible = if (subtype.id == "gsk_rtj") matchingFlanges
            else sourceGaskets.filter { s(it, "gasket_type") == type &&
                d(it, "inner_diameter_mm") > 0 && d(it, "outer_diameter_mm") > 0 }
        val requestedNps = nps.replace("\"", "").trim()
        val classesForSize = compatible.filter { s(it, "nps") == requestedNps }
            .map { s(it, "rating") }.toSet()
        val primaryOptions = preferredRatings.filter { it.removeSuffix("#") in classesForSize }
            .ifEmpty { preferredRatings.filter { cls -> compatible.any {
                s(it, "rating") == cls.removeSuffix("#")
            } } }
        val effectiveRatingStr = ratingStr.takeIf { it in primaryOptions }
            ?: primaryOptions.firstOrNull() ?: preferredRatings.first()
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 150
        val sizesForRating = compatible.filter { s(it, "rating") == rating.toString() }
            .map { s(it, "nps") }.toSet()
        val availableNps = listOf("1/2", "3/4", "1", "1 1/4", "1 1/2", "2",
            "2 1/2", "3", "3 1/2", "4", "5", "6", "8", "10", "12",
            "14", "16", "18", "20", "22", "24")
            .filter { it in sizesForRating }.map { "$it\"" }
        val effectiveNps = nps.takeIf { it in availableNps }
            ?: "2\"".takeIf { it in availableNps }
            ?: availableNps.firstOrNull() ?: nps
        val cleanNps = effectiveNps.replace("\"", "").trim()

        val sourceRing = if (subtype.id == "gsk_rtj") {
            tableData("tbl_flanges_asme_b16_5").firstOrNull { row ->
                s(row, "nps") == cleanNps && s(row, "rating") == rating.toString() &&
                    s(row, "flange_type") == "Welding Neck"
            }?.get("rtj_ring_number")?.replace("'", "")?.trim().orEmpty()
        } else ""
        val gasketRow = tableData("tbl_gaskets_and_rtj").firstOrNull { row ->
            if (subtype.id == "gsk_rtj") {
                val canonical = sourceRing.replace("-", "").uppercase()
                val wanted = if (canonical.isNotBlank() && !canonical.startsWith("R")) "R$canonical" else canonical
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
            val thickness = measured("thickness_mm")
            if (thickness > 0) {
                rows.add(dim("thk", "Gasket Thickness", "Conta Kalınlığı", thickness))
            }
        }

        return ComponentData(
            categoryId = "gaskets",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = availableNps,
            selectedNps = effectiveNps,
            dn = getNpsDn(cleanNps),
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
        val selectedBlankType = when (subtype.id) {
            "blk_paddle" -> "Paddle Blank"
            "blk_spacer" -> "Paddle Spacer"
            else -> "Figure-8 Spectacle Blind"
        }
        val validRecords = tableData("tbl_line_blanks_asme_b16_48").filter { row ->
            s(row, "blank_type") == selectedBlankType && d(row, "thickness_mm") > 0 &&
                if (subtype.id == "blk_spacer") d(row, "inner_diameter_mm") > 0
                else d(row, "outer_diameter_mm") > 0
        }
        val requestedSize = nps.replace("\"", "").trim()
        val presentRatings = validRecords.filter { s(it, "nps") == requestedSize }
            .map { s(it, "rating") }.toSet()
        val allowedRatings = listOf("150#", "300#", "400#", "600#", "900#", "1500#", "2500#")
        val primaryOptions = allowedRatings.filter { it.removeSuffix("#") in presentRatings }
            .ifEmpty { allowedRatings.filter { c -> validRecords.any { s(it, "rating") == c.removeSuffix("#") } } }
        val effectiveRatingStr = ratingStr.takeIf { it in primaryOptions }
            ?: primaryOptions.firstOrNull() ?: "150#"
        val rating = effectiveRatingStr.replace("#", "").toIntOrNull() ?: 150
        val presentSizes = validRecords.filter { s(it, "rating") == rating.toString() }
            .map { s(it, "nps") }.toSet()
        val availableNps = listOf("1/2", "3/4", "1", "1 1/4", "1 1/2", "2",
            "2 1/2", "3", "3 1/2", "4", "5", "6", "8", "10", "12",
            "14", "16", "18", "20", "22", "24")
            .filter { it in presentSizes }.map { "$it\"" }
        val effectiveSize = nps.takeIf { it in availableNps }
            ?: "2\"".takeIf { it in availableNps } ?: availableNps.firstOrNull() ?: nps

        var odMm = Double.NaN
        var idMm = Double.NaN
        var thkMm = Double.NaN
        var c2c = Double.NaN
        var wt = Double.NaN

        val cleanNps = effectiveSize.replace("\"", "").trim()
        val blankRow = validRecords.firstOrNull { r ->
            val n = s(r, "nps")
            (n == cleanNps || n == "$cleanNps\"" || n == cleanNps.replace(" ", "+")) &&
                s(r, "rating") == rating.toString() && s(r, "blank_type") == selectedBlankType
        }
        if (blankRow != null) {
            odMm = d(blankRow, "outer_diameter_mm")
            idMm = d(blankRow, "inner_diameter_mm")
            thkMm = d(blankRow, "thickness_mm")
            c2c = d(blankRow, "center_to_center_mm")
            wt = d(blankRow, "weight_spectacle_kg")
        }

        // Missing blank measurements remain N/A.

        val rows = mutableListOf<DimensionRow>()
        if (odMm > 0) rows.add(DimensionRow("od", "Outside Diameter (OD)", "Dış Çap (OD)", "${fmt(odMm)} mm", "${fmt(odMm/25.4)}\"", "blanks_def"))
        if (idMm > 0) rows.add(DimensionRow("id", "Inside Diameter (ID)", "İç Çap (ID)", "${fmt(idMm)} mm", "${fmt(idMm/25.4)}\"", "blanks_def"))
        if (thkMm > 0) rows.add(DimensionRow("thk", "Plate Thickness (t)", "Plaka Kalınlığı (t)", "${fmt(thkMm)} mm", "${fmt(thkMm/25.4)}\"", "blanks_def"))
        if (c2c > 0) rows.add(DimensionRow("c2c", "Center-to-Center (A)", "Eksenler Arası (A)", "${fmt(c2c)} mm", "${fmt(c2c/25.4)}\"", "blanks_def"))
        if (wt > 0) rows.add(DimensionRow("weight", "Reference Weight", "Referans Ağırlık", "${fmtWeight(wt)} kg", "${fmtWeight(wt*2.20462)} lb", "blanks_def"))

        return ComponentData(
            categoryId = "blanks",
            currentSubtype = subtype,
            allSubtypes = allSubtypes,
            availableNps = availableNps,
            selectedNps = effectiveSize,
            dn = getNpsDn(cleanNps),
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
            "3 1/2", "3-1/2" -> 90
            "4" -> 100
            "5" -> 125
            "6" -> 150
            "8" -> 200
            "10" -> 250
            "12" -> 300
            "14" -> 350
            "16" -> 400
            "18" -> 450
            "20" -> 500
            "22" -> 550
            "24" -> 600
            "26" -> 650
            "28" -> 700
            "30" -> 750
            "32" -> 800
            "34" -> 850
            "36" -> 900
            "38" -> 950
            "40" -> 1000
            "42" -> 1050
            "44" -> 1100
            "46" -> 1150
            "48" -> 1200
            else -> 0
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
