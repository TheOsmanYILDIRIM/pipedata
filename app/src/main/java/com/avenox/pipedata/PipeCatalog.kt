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
    val odInches: Double,
    val schedule: String,
    val wallInches: Double
) {
    init {
        require(odInches.isFinite() && wallInches.isFinite() && odInches > 0.0 && wallInches > 0.0 && wallInches < odInches / 2.0)
    }

    // Canonical source measurements are in inches. No published SI rounding is stored.
    val odMm: Double get() = odInches * 25.4
    val wallMm: Double get() = wallInches * 25.4
    val idInches: Double get() = odInches - 2.0 * wallInches
    val idMm: Double get() = idInches * 25.4
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
                // A BufferedReader line sequence is one-shot; read header and data together.
                lines.forEachIndexed { lineNo, raw ->
                    if (lineNo == 0) {
                        require(raw.trim() == "standard,nps,dn,outer_diameter_in,schedule,wall_thickness_in") {
                            "Pipe catalogue must use original inch dimensions"
                        }
                        return@forEachIndexed
                    }
                    if (raw.isBlank()) return@forEachIndexed
                    val parts = raw.trim().split(",")
                    require(parts.size == 6) { "Invalid pipe table row " + (lineNo + 1) }
                    val record = PipeSpec(
                        standard = parts[0].trim(),
                        nps = parts[1].trim(),
                        dn = parts[2].toInt(),
                        odInches = parts[3].toDouble(),
                        schedule = parts[4].trim(),
                        wallInches = parts[5].toDouble()
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
