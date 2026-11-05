package com.avenox.pipedata

import java.io.Reader
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max

/** Nominal dimensions, not fabrication tolerances or pressure ratings. */
data class PipeSpec(
    val standard: String,
    val nps: String,
    val dn: Int,
    val odMm: Double,
    val schedule: String,
    val wallMm: Double
) {
    init {
        require(odMm > 0.0 && wallMm > 0.0 && wallMm < odMm / 2.0)
    }

    /**
     * Native ASME outside diameter in inches. Sub-14 NPS is a nominal label,
     * not an OD. Rounded metric catalog values must not be reconverted into
     * false inch measurements (168.3 mm must display 6.625 in, not 6.626).
     */
    val odInches: Double get() = when (nps) {
        "1/8" -> 0.405
        "1/4" -> 0.540
        "3/8" -> 0.675
        "1/2" -> 0.840
        "3/4" -> 1.050
        "1" -> 1.315
        "1 1/4" -> 1.660
        "1 1/2" -> 1.900
        "2" -> 2.375
        "2 1/2" -> 2.875
        "3" -> 3.500
        "3 1/2" -> 4.000
        "4" -> 4.500
        "5" -> 5.563
        "6" -> 6.625
        "8" -> 8.625
        "10" -> 10.750
        "12" -> 12.750
        else -> nps.toDoubleOrNull()?.takeIf { it >= 14.0 } ?: odMm / 25.4
    }

    val idMm: Double get() = odMm - 2.0 * wallMm
    val metalAreaMm2: Double get() = PI / 4.0 * (odMm * odMm - idMm * idMm)
    val boreAreaMm2: Double get() = PI / 4.0 * idMm * idMm
    val kgPerMetre: Double get() = metalAreaMm2 * 0.00785  // 7850 kg/m³
    val litresPerMetre: Double get() = boreAreaMm2 / 1000.0
    val waterFilledKgPerMetre: Double get() = kgPerMetre + litresPerMetre
}

class PipeCatalog private constructor(val rows: List<PipeSpec>) {
    companion object {
        fun load(reader: Reader): PipeCatalog {
            val records = mutableListOf<PipeSpec>()
            val seen = mutableSetOf<String>()
            reader.buffered().useLines { lines ->
                lines.drop(1).forEachIndexed { lineNo, raw ->
                    if (raw.isBlank()) return@forEachIndexed
                    val parts = raw.trim().split(",")
                    require(parts.size == 6) { "Invalid pipe table row " + (lineNo + 2) }
                    val record = PipeSpec(
                        standard = parts[0].trim(),
                        nps = parts[1].trim(),
                        dn = parts[2].toInt(),
                        odMm = parts[3].toDouble(),
                        schedule = parts[4].trim(),
                        wallMm = parts[5].toDouble()
                    )
                    require(record.standard == "B36.10" || record.standard == "B36.19")
                    require(record.nps.isNotEmpty() && record.schedule.isNotEmpty())
                    val key = record.standard + "|" + record.nps + "|" + record.schedule
                    require(seen.add(key)) { "Duplicate nominal dimensions: " + key }
                    records.add(record)
                }
            }
            require(records.isNotEmpty()) { "Pipe schedule asset is empty" }
            return PipeCatalog(records)
        }
    }

    val standards: List<String> = listOf("B36.10", "B36.19")

    fun sizes(standard: String): List<String> = rows.asSequence()
        .filter { it.standard == standard }
        .map { it.nps }.distinct().toList()

    fun schedules(standard: String, nps: String): List<String> = rows.asSequence()
        .filter { it.standard == standard && it.nps == nps }
        .map { it.schedule }.distinct().toList()

    fun get(standard: String, nps: String, schedule: String): PipeSpec? =
        rows.firstOrNull { it.standard == standard && it.nps == nps && it.schedule == schedule }

    /** A candidate is shown only when both measured values are close to its nominal values. */
    fun findByMeasurement(od: Double, wall: Double, standard: String? = null): List<PipeSpec> {
        if (!od.isFinite() || !wall.isFinite() || od <= 0 || wall <= 0) return emptyList()
        return rows.asSequence()
            .filter { standard == null || it.standard == standard }
            .filter {
                abs(it.odMm - od) <= max(0.6, it.odMm * 0.002) &&
                abs(it.wallMm - wall) <= max(0.30, it.wallMm * 0.08)
            }
            .sortedBy {
                abs(it.odMm - od) / max(1.0, it.odMm) +
                abs(it.wallMm - wall) / max(1.0, it.wallMm)
            }
            .toList()
    }
}
