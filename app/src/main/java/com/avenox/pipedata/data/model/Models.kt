package com.avenox.pipedata.data.model

enum class CadTheme(val title: String) {
    CAD_DARK("CAD Dark"),
    CLASSIC_BLUEPRINT("Blueprint"),
    ENGINEERING_WHITE("Paper White"),
    OLED_AMBER("OLED Amber")
}

enum class UnitSystem(val symbol: String, val label: String) {
    METRIC("mm", "Metric (mm / kg)"),
    IMPERIAL("in", "Imperial (in / lb)")
}

enum class AppLanguage(val code: String, val label: String) {
    EN("en", "English"),
    TR("tr", "Türkçe")
}

data class FlangeTypeItem(
    val id: String,
    val title: String,
    val shortName: String,
    val abbr: String,
    val facing: String,
    val standard: String,
    val descEn: String,
    val descTr: String,
    val svgAsset: String
)

data class FlangeSpec(
    val nps: String,
    val dn: Int,
    val rating: Int,
    val facing: String,
    val odMm: Double,
    val thkMm: Double,
    val hubXMm: Double,
    val lengthYMm: Double,
    val boreBMm: Double,
    val rfDiaMm: Double,
    val pcdMm: Double,
    val boltsCount: Int,
    val boltSizeIn: String,
    val studLengthMm: Double,
    val weightKg: Double,
    // Imperial values
    val odIn: Double = odMm / 25.4,
    val thkIn: Double = thkMm / 25.4,
    val hubXIn: Double = hubXMm / 25.4,
    val lengthYIn: Double = lengthYMm / 25.4,
    val boreBIn: Double = boreBMm / 25.4,
    val rfDiaIn: Double = rfDiaMm / 25.4,
    val pcdIn: Double = pcdMm / 25.4,
    val studLengthIn: Double = studLengthMm / 25.4,
    val weightLb: Double = weightKg * 2.20462
)

data class TopicCategory(
    val id: String,
    val titleEn: String,
    val titleTr: String,
    val std: String,
    val descEn: String,
    val descTr: String,
    val svgAsset: String
)

data class HelpTopic(
    val id: String,
    val badge: String,
    val titleEn: String,
    val titleTr: String,
    val bodyEn: String,
    val bodyTr: String,
    val formulaEn: String,
    val formulaTr: String
)
