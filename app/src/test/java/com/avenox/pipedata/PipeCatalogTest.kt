package com.avenox.pipedata

import java.io.File
import java.io.StringReader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PipeCatalogTest {
    private fun bundledCsv(): File {
        val appRelative = File("src/main/assets/pipe_schedules.csv")
        val rootRelative = File("app/src/main/assets/pipe_schedules.csv")
        return when {
            appRelative.isFile -> appRelative
            rootRelative.isFile -> rootRelative
            else -> error("Bundled pipe_schedules.csv not found for JVM test")
        }
    }

    @Test fun loadsEveryBundledInchRecord() {
        val catalog = bundledCsv().bufferedReader().use { PipeCatalog.load(it) }
        assertEquals(402, catalog.rows.size)
        assertEquals(36, catalog.rows.map { it.nps }.distinct().size)
        val sixIn = catalog.get("B36.10", "6", "40")!!
        assertEquals(6.625, sixIn.odInches, 0.0)
        assertEquals(0.280, sixIn.wallInches, 0.0)
        assertEquals(168.275, sixIn.odMm, 0.000000001)
        assertEquals(154.051, sixIn.idMm, 0.000000001)
        assertTrue(catalog.rows.all { it.idInches > 0.0 })
    }

    @Test fun acceptsSinglePassReaderWithoutSkippingFirstRecord() {
        val csv = "standard,nps,dn,outer_diameter_in,schedule,wall_thickness_in\n" +
            "B36.19,1/8,6,0.405,10S,0.049\n"
        val catalog = PipeCatalog.load(StringReader(csv))
        assertEquals(1, catalog.rows.size)
        assertEquals("10S", catalog.rows.single().schedule)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsMetricOrCorruptHeader() {
        PipeCatalog.load(StringReader(
            "standard,nps,dn,outer_diameter_mm,schedule,wall_thickness_mm\n" +
            "B36.10,6,150,168.3,40,7.11\n"
        ))
    }
}
