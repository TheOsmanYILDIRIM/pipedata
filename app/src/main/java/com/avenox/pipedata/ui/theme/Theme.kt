package com.avenox.pipedata.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.avenox.pipedata.data.model.CadTheme

data class CadColorPalette(
    val background: Color,
    val surface: Color,
    val border: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val chipBg: Color,
    val accent: Color,
    val accentText: Color,
    val cadBg: Color,
    val cadPart: Color,
    val cadDim: Color,
    val cadCenter: Color
)

val CadDarkPalette = CadColorPalette(
    background = Color(0xFF0B111E),
    surface = Color(0xFF131E2E),
    border = Color(0xFF23354E),
    textPrimary = Color(0xFFF8FAFC),
    textSecondary = Color(0xFF94A3B8),
    textMuted = Color(0xFF64748B),
    chipBg = Color(0xFF1B293E),
    accent = Color(0xFF38BDF8),
    accentText = Color(0xFF0B111E),
    cadBg = Color(0xFF0B111E),
    cadPart = Color(0xFF38BDF8),
    cadDim = Color(0xFF94A3B8),
    cadCenter = Color(0xFFFBBF24)
)

val ClassicBlueprintPalette = CadColorPalette(
    background = Color(0xFF071C33),
    surface = Color(0xFF0B294A),
    border = Color(0xFF184A80),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFF93C5FD),
    textMuted = Color(0xFF60A5FA),
    chipBg = Color(0xFF0E3560),
    accent = Color(0xFF60A5FA),
    accentText = Color(0xFF071C33),
    cadBg = Color(0xFF071C33),
    cadPart = Color(0xFFFFFFFF),
    cadDim = Color(0xFF93C5FD),
    cadCenter = Color(0xFF60A5FA)
)

val EngineeringWhitePalette = CadColorPalette(
    background = Color(0xFFF8FAFC),
    surface = Color(0xFFFFFFFF),
    border = Color(0xFFE2E8F0),
    textPrimary = Color(0xFF0F172A),
    textSecondary = Color(0xFF64748B),
    textMuted = Color(0xFF94A3B8),
    chipBg = Color(0xFFF1F5F9),
    accent = Color(0xFF0F172A),
    accentText = Color(0xFFFFFFFF),
    cadBg = Color(0xFFFFFFFF),
    cadPart = Color(0xFF0F172A),
    cadDim = Color(0xFF2563EB),
    cadCenter = Color(0xFFDC2626)
)

val OledAmberPalette = CadColorPalette(
    background = Color(0xFF000000),
    surface = Color(0xFF0A0800),
    border = Color(0xFF332000),
    textPrimary = Color(0xFFFFB000),
    textSecondary = Color(0xFFD97706),
    textMuted = Color(0xFF92400E),
    chipBg = Color(0xFF1A1200),
    accent = Color(0xFFFFB000),
    accentText = Color(0xFF000000),
    cadBg = Color(0xFF000000),
    cadPart = Color(0xFFFFB000),
    cadDim = Color(0xFFFBBF24),
    cadCenter = Color(0xFFEF4444)
)

val LocalCadColors = staticCompositionLocalOf { CadDarkPalette }

@Composable
fun PipedataTheme(
    cadTheme: CadTheme = CadTheme.CAD_DARK,
    highContrast: Boolean = false,
    content: @Composable () -> Unit
) {
    var palette = when (cadTheme) {
        CadTheme.CAD_DARK -> CadDarkPalette
        CadTheme.CLASSIC_BLUEPRINT -> ClassicBlueprintPalette
        CadTheme.ENGINEERING_WHITE -> EngineeringWhitePalette
        CadTheme.OLED_AMBER -> OledAmberPalette
    }

    if (highContrast) {
        palette = palette.copy(
            textSecondary = palette.textPrimary,
            textMuted = palette.textSecondary
        )
    }

    val colorScheme = if (cadTheme == CadTheme.ENGINEERING_WHITE) {
        lightColorScheme(
            background = palette.background,
            surface = palette.surface,
            primary = palette.accent,
            onPrimary = palette.accentText,
            onBackground = palette.textPrimary,
            onSurface = palette.textPrimary
        )
    } else {
        darkColorScheme(
            background = palette.background,
            surface = palette.surface,
            primary = palette.accent,
            onPrimary = palette.accentText,
            onBackground = palette.textPrimary,
            onSurface = palette.textPrimary
        )
    }

    CompositionLocalProvider(LocalCadColors provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
