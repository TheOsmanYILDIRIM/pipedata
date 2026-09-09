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

data class TopicCategory(
    val id: String,
    val titleEn: String,
    val titleTr: String,
    val std: String,
    val descEn: String,
    val descTr: String,
    val svgAsset: String
)

data class SubtypeItem(
    val id: String,
    val title: String,
    val shortName: String,
    val abbr: String,
    val standard: String,
    val descEn: String,
    val descTr: String,
    val svgAsset: String,
    val titleTr: String = title,
    val shortNameTr: String = shortName
)

data class DimensionRow(
    val key: String,
    val nameEn: String,
    val nameTr: String,
    val valueMetric: String,
    val valueImperial: String,
    val helpKey: String? = null
)

data class ComponentData(
    val categoryId: String,
    val currentSubtype: SubtypeItem,
    val allSubtypes: List<SubtypeItem>,
    val availableNps: List<String>,
    val selectedNps: String,
    val dn: Int,
    val primarySelectorLabelEn: String, // e.g. "Class" or "Schedule"
    val primarySelectorLabelTr: String,
    val primaryOptions: List<String>,
    val selectedPrimary: String,
    val secondarySelectorLabelEn: String? = null, // e.g. "Facing"
    val secondarySelectorLabelTr: String? = null,
    val secondaryOptions: List<String> = emptyList(),
    val selectedSecondary: String? = null,
    val dimensions: List<DimensionRow>
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
