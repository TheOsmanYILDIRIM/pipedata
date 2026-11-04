package com.avenox.pipedata.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.avenox.pipedata.data.model.*
import com.avenox.pipedata.ui.components.HorizontalFluidSelector
import com.avenox.pipedata.ui.theme.LocalCadColors

@Composable
fun ComponentDetailScreen(
    data: ComponentData,
    currentSubtypeIndex: Int,
    onSubtypeChange: (Int) -> Unit,
    onNpsChange: (String) -> Unit,
    onPrimaryChange: (String) -> Unit,
    onSecondaryChange: ((String) -> Unit)? = null,
    onFlangeScheduleChange: (String) -> Unit,
    currentUnit: UnitSystem,
    onUnitToggle: () -> Unit,
    fontScale: Float,
    currentLang: AppLanguage,
    currentTheme: CadTheme = CadTheme.CAD_DARK,
    onOpenHelp: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val colors = LocalCadColors.current
    val isTr = currentLang == AppLanguage.TR

    val currentSubtype = data.currentSubtype
    val subtypeTitle = if (isTr && currentSubtype.titleTr.isNotBlank()) currentSubtype.titleTr else currentSubtype.title

    Scaffold(
        containerColor = colors.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.surface)
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.textPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = subtypeTitle,
                                color = colors.textPrimary,
                                fontSize = (16.5 * fontScale).sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )

                            HelpQuestionButton(
                                onClick = { onOpenHelp(data.categoryId) },
                                fontScale = fontScale
                            )
                        }
                        if (currentSubtype.standard.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = currentSubtype.standard,
                                color = colors.accent,
                                fontSize = (11.5 * fontScale).sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Inch / mm Unit Toggle Badge Button on Top Bar
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(colors.chipBg)
                            .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                            .clickable { onUnitToggle() }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = currentUnit.symbol,
                            color = colors.accent,
                            fontSize = (12 * fontScale).sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Divider(color = colors.border, thickness = 0.8.dp)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            // ================================================================
            // ISOLATED CAD DRAWING STAGE (Skips recomposition on dial clicks!)
            // ================================================================
            CadDrawingStage(
                svgAsset = currentSubtype.svgAsset,
                theme = currentTheme,
                title = subtypeTitle,
                colors = colors,
                data = data,
                isMetric = currentUnit == UnitSystem.METRIC,
                fontScale = fontScale,
                hasMultipleSubtypes = data.allSubtypes.size > 1,
                onPrevSubtype = {
                    val newIdx = if (currentSubtypeIndex > 0) currentSubtypeIndex - 1 else data.allSubtypes.lastIndex
                    onSubtypeChange(newIdx)
                },
                onNextSubtype = {
                    val newIdx = if (currentSubtypeIndex < data.allSubtypes.lastIndex) currentSubtypeIndex + 1 else 0
                    onSubtypeChange(newIdx)
                }
            )

            // Subtypes Chips Strip (if multiple subtypes)
            if (data.allSubtypes.size > 1) {
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp)
                ) {
                    itemsIndexed(data.allSubtypes) { idx, sub ->
                        val isSelected = idx == currentSubtypeIndex
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) colors.accent else colors.chipBg)
                                .border(1.dp, if (isSelected) colors.accent else colors.border, RoundedCornerShape(8.dp))
                                .clickable { onSubtypeChange(idx) }
                                .padding(horizontal = 11.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val chipText = if (sub.abbr.isNotBlank()) sub.abbr else if (isTr && sub.shortNameTr.isNotBlank()) sub.shortNameTr else sub.shortName
                            Text(
                                text = chipText,
                                color = if (isSelected) colors.accentText else colors.textPrimary,
                                fontSize = (11.5 * fontScale).sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ================================================================
            // HORIZONTAL FLUID ANIMATED SLIDER SELECTOR
            // ================================================================
            HorizontalFluidSelector(
                availableNps = data.availableNps,
                selectedNps = data.selectedNps,
                onNpsChange = onNpsChange,
                dn = data.dn,
                primaryLabel = if (isTr) data.primarySelectorLabelTr else data.primarySelectorLabelEn,
                primaryOptions = data.primaryOptions,
                selectedPrimary = data.selectedPrimary,
                onPrimaryChange = onPrimaryChange,
                secondaryLabel = if (isTr) data.secondarySelectorLabelTr else data.secondarySelectorLabelEn,
                secondaryOptions = data.secondaryOptions,
                selectedSecondary = data.selectedSecondary,
                onSecondaryChange = onSecondaryChange,
                colors = colors,
                fontScale = fontScale,
                isTr = isTr,
                onOpenHelp = onOpenHelp,
                modifier = Modifier.padding(horizontal = 14.dp)
            )

            if (data.flangeScheduleOptions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isTr) "BORU ET KALINLIĞI (SCH)" else "PIPE SCHEDULE (SCH)",
                    color = colors.textSecondary,
                    fontSize = (11 * fontScale).sp,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp)
                ) {
                    items(data.flangeScheduleOptions) { schedule ->
                        val active = schedule == data.selectedFlangeSchedule
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(7.dp))
                                .background(if (active) colors.accent else colors.chipBg)
                                .border(1.dp, if (active) colors.accent else colors.border, RoundedCornerShape(7.dp))
                                .clickable { onFlangeScheduleChange(schedule) }
                                .padding(horizontal = 13.dp, vertical = 9.dp)
                        ) {
                            Text(
                                text = schedule,
                                color = if (active) colors.accentText else colors.textPrimary,
                                fontSize = (12 * fontScale).sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ================================================================
            // SPECIFICATION TABLE CARD
            // ================================================================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isTr) "Mühendislik Ölçüleri" else "Engineering Specifications",
                            color = colors.textSecondary,
                            fontSize = (12.5 * fontScale).sp,
                            fontWeight = FontWeight.Bold
                        )
                        HelpQuestionButton(
                            onClick = { onOpenHelp("dimensions") },
                            fontScale = fontScale
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = colors.surface),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(colors.border)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        val isMetric = currentUnit == UnitSystem.METRIC

                        data.dimensions.forEachIndexed { idx, row ->
                            val label = if (isTr) row.nameTr else row.nameEn
                            val value = if (isMetric) row.valueMetric else row.valueImperial

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 7.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f, fill = false)
                                ) {
                                    Text(
                                        text = label,
                                        color = colors.textSecondary,
                                        fontSize = (13 * fontScale).sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    if (!row.helpKey.isNullOrEmpty()) {
                                        HelpQuestionButton(
                                            onClick = { onOpenHelp(row.helpKey) },
                                            fontScale = fontScale
                                        )
                                    }
                                }
                                Text(
                                    text = value,
                                    color = colors.textPrimary,
                                    fontSize = (13.5 * fontScale).sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            if (idx < data.dimensions.lastIndex) {
                                Divider(color = colors.border.copy(alpha = 0.5f), thickness = 0.7.dp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Copy Specification Button
                Button(
                    onClick = {
                        val textToCopy = buildString {
                            appendLine("$subtypeTitle (${data.selectedNps}, ${data.selectedPrimary}${if (data.selectedSecondary != null) " ${data.selectedSecondary}" else ""})")
                            data.dimensions.forEach { d ->
                                val v = if (currentUnit == UnitSystem.METRIC) d.valueMetric else d.valueImperial
                                val l = if (isTr) d.nameTr else d.nameEn
                                appendLine("$l: $v")
                            }
                        }
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Pipedata Spec", textToCopy)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(
                            context,
                            if (isTr) "Ölçüler Panoya Kopyalandı! ✓" else "Specifications Copied! ✓",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.accent,
                        contentColor = colors.accentText
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isTr) "Ölçüleri Kopyala" else "Copy Specification",
                        fontSize = (13.5 * fontScale).sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CadDrawingStage(
    svgAsset: String,
    theme: CadTheme,
    title: String,
    colors: com.avenox.pipedata.ui.theme.CadColorPalette,
    data: ComponentData,
    isMetric: Boolean,
    fontScale: Float,
    hasMultipleSubtypes: Boolean,
    onPrevSubtype: () -> Unit,
    onNextSubtype: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    fun resetZoom() {
        scale = 1f
        offsetX = 0f
        offsetY = 0f
    }

    val normScale = (Math.round(fontScale * 100.0) / 100.0).toFloat()
    val dynamicCacheKey = "${svgAsset}_${theme.name}_${data.selectedNps}_${data.selectedPrimary}_${data.selectedSecondary}_${data.selectedFlangeSchedule}_${isMetric}_$normScale"

    val svgBytes = remember(dynamicCacheKey) {
        getThemedSvgBytes(context, svgAsset, theme, data, isMetric, normScale)
    }

    val imageRequest = remember(dynamicCacheKey, svgBytes) {
        ImageRequest.Builder(context)
            .data(svgBytes ?: "file:///android_asset/svg/$svgAsset")
            .memoryCacheKey(dynamicCacheKey)
            .decoderFactory(SvgDecoder.Factory())
            .crossfade(false)
            .build()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(290.dp)
            .clipToBounds()
            .background(colors.cadBg)
            .border(1.dp, colors.border)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (scale > 1.2f) resetZoom() else scale = 2.2f
                    }
                )
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1.0f, 4.0f)
                    if (scale > 1.0f) {
                        val maxOffset = (scale - 1f) * 350f
                        offsetX = (offsetX + pan.x).coerceIn(-maxOffset, maxOffset)
                        offsetY = (offsetY + pan.y).coerceIn(-maxOffset, maxOffset)
                    } else {
                        offsetX = 0f
                        offsetY = 0f
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Technical Drawing SVG Viewport
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .padding(4.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offsetX,
                    translationY = offsetY
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Mini Left Arrow
        if (hasMultipleSubtypes) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(colors.surface.copy(alpha = 0.8f))
                    .border(1.dp, colors.border, CircleShape)
                    .clickable {
                        onPrevSubtype()
                        resetZoom()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Previous",
                    tint = colors.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Mini Right Arrow
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(colors.surface.copy(alpha = 0.8f))
                    .border(1.dp, colors.border, CircleShape)
                    .clickable {
                        onNextSubtype()
                        resetZoom()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Next",
                    tint = colors.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Reset Button if zoomed (User: "Zoom miktarı yazmasın resmin yanında")
        if (scale > 1.05f) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(colors.chipBg)
                    .border(1.dp, colors.accent, RoundedCornerShape(6.dp))
                    .clickable { resetZoom() }
                    .padding(horizontal = 9.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "⟲ Reset",
                    color = colors.accent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HelpQuestionButton(
    onClick: () -> Unit,
    fontScale: Float
) {
    val colors = LocalCadColors.current
    Box(
        modifier = Modifier
            .padding(start = 6.dp)
            .size(18.dp)
            .clip(CircleShape)
            .background(colors.chipBg)
            .border(1.dp, colors.border, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "?",
            color = colors.textSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 11.sp
        )
    }
}

/**
 * Reads the raw SVG asset and dynamically injects theme-calibrated CSS rules into its <style> block.
 * Adapts seamlessly to Engineering Paper White (crisp slate/blue/red), Classic Blueprint, OLED Amber, and CAD Dark.
 */
private val dfPrecision = java.text.DecimalFormat("0.####", java.text.DecimalFormatSymbols(java.util.Locale.US))
private fun fmt(d: Double): String {
    if (d.isNaN() || d.isInfinite()) return "—"
    val rounded = Math.round(d * 10000.0) / 10000.0
    return dfPrecision.format(rounded)
}


private val rawSvgCache = java.util.concurrent.ConcurrentHashMap<String, String>()
private val renderedSvgBytesCache = java.util.concurrent.ConcurrentHashMap<String, ByteArray>()

/**
 * Reads raw SVG asset, injects theme-calibrated CSS, and dynamically substitutes
 * all dimension placeholders ([[3]], [[4]], [[19]], [[6]], [[11]], [[DB2_6]], etc.)
 * with real, live engineering numbers corresponding to the user's active selection.
 * Number size dynamically adapts to fontScale while preserving badge boundaries and CAD clarity.
 */
private fun getThemedSvgBytes(
    context: Context,
    assetName: String,
    theme: CadTheme,
    data: ComponentData,
    isMetric: Boolean,
    fontScale: Float = 1.0f
): ByteArray? {
    val normScale = (Math.round(fontScale * 100.0) / 100.0).toFloat()
    val cacheKey = "${assetName}_${theme.name}_${data.selectedNps}_${data.selectedPrimary}_${data.selectedSecondary}_${data.selectedFlangeSchedule}_${isMetric}_$normScale"
    renderedSvgBytesCache[cacheKey]?.let { return it }

    return try {
        val rawSvg = rawSvgCache.getOrPut(assetName) {
            context.assets.open("svg/$assetName").bufferedReader().use { it.readText() }
        }

        // Calculate massive font size based on user fontScale preference (5x previous growth for elderly accessibility)
        val growth = (normScale - 1.0f) * 65.0f
        val badgeFontSize = (26.0f + growth).coerceIn(22.0f, 54.0f)
        val fs = String.format(java.util.Locale.US, "%.1f", badgeFontSize)

        val styleReplacement = when (theme) {
            CadTheme.ENGINEERING_WHITE -> """
<style>
  .cad-bg { fill: #ffffff; }
  .cad-part { stroke: #0f172a; stroke-width: 3.0; stroke-linecap: round; stroke-linejoin: round; fill: none; }
  .cad-bevel { stroke: #0f172a; stroke-width: 2.6; stroke-linecap: round; stroke-linejoin: round; fill: none; }
  .cad-dim { stroke: #2563eb; stroke-width: 1.5; stroke-linecap: square; fill: none; }
  .cad-center { stroke: #dc2626; stroke-width: 1.3; stroke-dasharray: 14, 4, 3, 4; fill: none; }
  .cad-hatch { stroke: #475569; stroke-width: 1.2; stroke-opacity: 0.65; fill: none; }
  .cad-arrow { fill: #2563eb; stroke: #2563eb; stroke-width: 0.5; }
  .cad-badge-bg { display: none !important; fill: none !important; stroke: none !important; opacity: 0 !important; }
  .cad-badge-text { font-family: 'DejaVu Sans', 'Liberation Sans', monospace; font-size: ${fs}px !important; font-weight: bold !important; fill: #0f172a !important; stroke: none !important; text-anchor: middle; dominant-baseline: central; }
</style>
""".trimIndent()

            CadTheme.CLASSIC_BLUEPRINT -> """
<style>
  .cad-bg { fill: #071c33; }
  .cad-part { stroke: #ffffff; stroke-width: 3.0; stroke-linecap: round; stroke-linejoin: round; fill: none; }
  .cad-bevel { stroke: #ffffff; stroke-width: 2.6; stroke-linecap: round; stroke-linejoin: round; fill: none; }
  .cad-dim { stroke: #93c5fd; stroke-width: 1.4; stroke-linecap: square; fill: none; }
  .cad-center { stroke: #60a5fa; stroke-width: 1.2; stroke-dasharray: 14, 4, 3, 4; fill: none; }
  .cad-hatch { stroke: #ffffff; stroke-width: 1.2; stroke-opacity: 0.65; fill: none; }
  .cad-arrow { fill: #93c5fd; stroke: #93c5fd; stroke-width: 0.5; }
  .cad-badge-bg { display: none !important; fill: none !important; stroke: none !important; opacity: 0 !important; }
  .cad-badge-text { font-family: 'DejaVu Sans', 'Liberation Sans', monospace; font-size: ${fs}px !important; font-weight: bold !important; fill: #ffffff !important; stroke: none !important; text-anchor: middle; dominant-baseline: central; }
</style>
""".trimIndent()

            CadTheme.OLED_AMBER -> """
<style>
  .cad-bg { fill: #000000; }
  .cad-part { stroke: #ffb000; stroke-width: 3.0; stroke-linecap: round; stroke-linejoin: round; fill: none; }
  .cad-bevel { stroke: #ffb000; stroke-width: 2.6; stroke-linecap: round; stroke-linejoin: round; fill: none; }
  .cad-dim { stroke: #f59e0b; stroke-width: 1.4; stroke-linecap: square; fill: none; }
  .cad-center { stroke: #ef4444; stroke-width: 1.2; stroke-dasharray: 14, 4, 3, 4; fill: none; }
  .cad-hatch { stroke: #ffb000; stroke-width: 1.2; stroke-opacity: 0.65; fill: none; }
  .cad-arrow { fill: #f59e0b; stroke: #f59e0b; stroke-width: 0.5; }
  .cad-badge-bg { display: none !important; fill: none !important; stroke: none !important; opacity: 0 !important; }
  .cad-badge-text { font-family: 'DejaVu Sans', 'Liberation Sans', monospace; font-size: ${fs}px !important; font-weight: bold !important; fill: #ffb000 !important; stroke: none !important; text-anchor: middle; dominant-baseline: central; }
</style>
""".trimIndent()

            CadTheme.CAD_DARK -> """
<style>
  .cad-bg { fill: #0b111e; }
  .cad-part { stroke: #38bdf8; stroke-width: 3.0; stroke-linecap: round; stroke-linejoin: round; fill: none; }
  .cad-bevel { stroke: #38bdf8; stroke-width: 2.6; stroke-linecap: round; stroke-linejoin: round; fill: none; }
  .cad-dim { stroke: #94a3b8; stroke-width: 1.4; stroke-linecap: square; fill: none; }
  .cad-center { stroke: #fbbf24; stroke-width: 1.2; stroke-dasharray: 14, 4, 3, 4; fill: none; }
  .cad-hatch { stroke: #38bdf8; stroke-width: 1.2; stroke-opacity: 0.65; fill: none; }
  .cad-arrow { fill: #38bdf8; stroke: #38bdf8; stroke-width: 0.5; }
  .cad-badge-bg { display: none !important; fill: none !important; stroke: none !important; opacity: 0 !important; }
  .cad-badge-text { font-family: 'DejaVu Sans', 'Liberation Sans', monospace; font-size: ${fs}px !important; font-weight: bold !important; fill: #ffffff !important; stroke: none !important; text-anchor: middle; dominant-baseline: central; }
</style>
""".trimIndent()
        }

        val styleRegex = Regex("<style>[\\s\\S]*?</style>")
        var themedSvg = if (styleRegex.containsMatchIn(rawSvg)) {
            styleRegex.replace(rawSvg, styleReplacement)
        } else {
            rawSvg
        }

        // 1. Remove all rectangular background boxes from SVG
        themedSvg = themedSvg.replace(Regex("""<rect[^>]*class="cad-badge-bg"[^>]*/>\s*"""), "")

        // 2. Dynamic Spreading and Dimension Shifting when fontScale increases
        if (normScale > 1.02f) {
            val scaleRatio = normScale - 1.0f

            // Shift and stagger text labels
            val translateRegex = Regex("""<g\s+transform="translate\((\d+(?:\.\d+)?),\s*(\d+(?:\.\d+)?)\)">""")
            var tagCounter = 0
            themedSvg = translateRegex.replace(themedSvg) { matchResult ->
                var x = matchResult.groupValues[1].toFloatOrNull() ?: 800f
                var y = matchResult.groupValues[2].toFloatOrNull() ?: 550f

                // Vertical Spreading: Alt ölçüleri aşağıya, üst ölçüleri yukarıya it
                if (y > 600f) {
                    val dist = y - 550f
                    y += scaleRatio * (dist * 0.40f)
                } else if (y < 500f) {
                    val dist = 550f - y
                    y -= scaleRatio * (dist * 0.40f)
                }

                // Horizontal Staggering: Aynı dikey eksendeki sayıları sağa sola basamakla
                if (Math.abs(x - 800f) < 45f) {
                    tagCounter++
                    val offset = if (tagCounter % 3 == 1) -75f else if (tagCounter % 3 == 2) 75f else 0f
                    x += scaleRatio * offset
                }

                val newXStr = String.format(java.util.Locale.US, "%.1f", x)
                val newYStr = String.format(java.util.Locale.US, "%.1f", y)
                """<g transform="translate($newXStr, $newYStr)">"""
            }

            // Shift dimension lines (cad-dim) to follow the expanded labels
            val dimLineRegex = Regex("""<line\s+x1="([^"]+)"\s+y1="([^"]+)"\s+x2="([^"]+)"\s+y2="([^"]+)"\s+class="cad-dim"\s*/>""")
            themedSvg = dimLineRegex.replace(themedSvg) { m ->
                val x1 = m.groupValues[1].toFloatOrNull() ?: 0f
                var y1 = m.groupValues[2].toFloatOrNull() ?: 0f
                val x2 = m.groupValues[3].toFloatOrNull() ?: 0f
                var y2 = m.groupValues[4].toFloatOrNull() ?: 0f

                if (Math.abs(y1 - y2) < 2f) {
                    if (y1 > 600f) {
                        val dist = y1 - 550f
                        val delta = scaleRatio * (dist * 0.40f)
                        y1 += delta
                        y2 += delta
                    } else if (y1 < 500f) {
                        val dist = 550f - y1
                        val delta = scaleRatio * (dist * 0.40f)
                        y1 -= delta
                        y2 -= delta
                    }
                } else if (Math.abs(x1 - x2) < 2f) {
                    if (Math.max(y1, y2) > 600f) {
                        if (y2 > y1) {
                            val dist = y2 - 550f
                            y2 += scaleRatio * (dist * 0.40f)
                        } else {
                            val dist = y1 - 550f
                            y1 += scaleRatio * (dist * 0.40f)
                        }
                    } else if (Math.min(y1, y2) < 500f) {
                        if (y2 < y1) {
                            val dist = 550f - y2
                            y2 -= scaleRatio * (dist * 0.40f)
                        } else {
                            val dist = 550f - y1
                            y1 -= scaleRatio * (dist * 0.40f)
                        }
                    }
                }

                val nx1 = String.format(java.util.Locale.US, "%.1f", x1)
                val ny1 = String.format(java.util.Locale.US, "%.1f", y1)
                val nx2 = String.format(java.util.Locale.US, "%.1f", x2)
                val ny2 = String.format(java.util.Locale.US, "%.1f", y2)
                """<line x1="$nx1" y1="$ny1" x2="$nx2" y2="$ny2" class="cad-dim" />"""
            }

            // Expand SVG viewBox so shifted dimensions and enlarged numbers never clip
            val viewBoxRegex = Regex("""viewBox="(-?\d+(?:\.\d+)?)\s+(-?\d+(?:\.\d+)?)\s+(\d+(?:\.\d+)?)\s+(\d+(?:\.\d+)?)"""")
            themedSvg = viewBoxRegex.find(themedSvg)?.let { match ->
                val vx = match.groupValues[1].toFloatOrNull() ?: 0f
                val vy = match.groupValues[2].toFloatOrNull() ?: 0f
                val vw = match.groupValues[3].toFloatOrNull() ?: 1000f
                val vh = match.groupValues[4].toFloatOrNull() ?: 800f

                val padX = scaleRatio * 100f
                val padY = scaleRatio * 180f
                val nvx = String.format(java.util.Locale.US, "%.1f", vx - padX / 2f)
                val nvy = String.format(java.util.Locale.US, "%.1f", vy - padY / 2f)
                val nvw = String.format(java.util.Locale.US, "%.1f", vw + padX)
                val nvh = String.format(java.util.Locale.US, "%.1f", vh + padY)
                val newViewBox = """viewBox="$nvx $nvy $nvw $nvh""""
                themedSvg.replaceFirst(match.value, newViewBox)
            } ?: themedSvg
        }

        // Direct inline font-size replacement to guarantee AndroidSVG renderer scales numbers visibly with clean solid fill
        themedSvg = themedSvg.replace("<text class=\"cad-badge-text\">", "<text class=\"cad-badge-text\" font-size=\"${fs}px\" stroke=\"none\">")

        // Build dimension lookup table from active component specifications
        val dimMap = mutableMapOf<String, String>()
        data.dimensions.forEach { row ->
            val raw = if (isMetric) row.valueMetric else row.valueImperial
            // No guessed engineering numbers and no meaningless N/A" badge.
            if (raw.startsWith("N/A")) return@forEach
            val cleaned = raw.replace(" mm", "").replace(" kg", "kg").replace(" lb", "lb").trim()
            dimMap[row.key.lowercase()] = cleaned
        }

        val dn = data.dn
        val odStr = dimMap["od"] ?: dimMap["flange_od"] ?: "—"
        val thkStr = dimMap["thk"] ?: "—"
        val hubStr = dimMap["hub"] ?: dimMap["ctf"] ?: "—"
        val boreStr = dimMap["bore"] ?: dimMap["id"] ?: "—"
        val pcdStr = dimMap["pcd"] ?: "—"
        val lenStr = dimMap["len"] ?: dimMap["lth"] ?: "—"
        val rfStr = dimMap["rf"] ?: "—"
        val boltsStr = dimMap["bolts"] ?: "—"
        val studStr = dimMap["stud"] ?: "—"
        val wtStr = dimMap["weight"] ?: "—"

        val numBolts = boltsStr.split("×").firstOrNull()?.trim() ?: "—"
        val boltSz = boltsStr.split("×").getOrNull(1)?.trim() ?: "—"

        // Dynamically inject real engineering values into all SVG parameter callout badges
        val placeholderRegex = Regex("\\[\\[(.*?)\\]\\]")
        themedSvg = placeholderRegex.replace(themedSvg) { matchResult ->
            val key = matchResult.groupValues[1].trim()
            if (data.categoryId == "fittings_sw") {
                val swSockDia = dimMap["sock_dia"] ?: if (isMetric) "22.2" else "0.88\""
                val swSockDepth = dimMap["sock_depth"] ?: if (isMetric) "9.5" else "0.38\""
                val swCenterEnd = dimMap["center_end"] ?: if (isMetric) "15.5" else "0.62\""
                val swSockThk = dimMap["sock_thk"] ?: if (isMetric) "4.7" else "0.18\""
                val swBore = dimMap["bore"] ?: if (isMetric) "16.6" else "0.65\""
                val swWallThk = dimMap["wall_thk"] ?: if (isMetric) "3.7" else "0.15\""
                val swLen = dimMap["length"] ?: if (isMetric) "22.5" else "0.88\""
                val swCapThk = dimMap["cap_thk"] ?: if (isMetric) "6.4" else "0.25\""
                when (key) {
                    "3" -> swCenterEnd
                    "4" -> swSockDia
                    "5" -> swSockThk
                    "6" -> swBore
                    "7" -> swSockDepth
                    "8" -> swWallThk
                    "9" -> swCenterEnd
                    "10" -> swLen
                    "11" -> swLen
                    "12" -> swCapThk
                    "13", "14", "15", "16" -> wtStr
                    else -> dimMap[key.lowercase()] ?: data.selectedPrimary
                }
            } else if (data.categoryId == "fittings_bw") {
                val bwOd = dimMap["od"] ?: if (isMetric) "60.3" else "2.375\""
                val bwCenterEnd = dimMap["center_end"] ?: dimMap["length"] ?: if (isMetric) "76.2" else "3.00\""
                val bwCenterCenter = dimMap["center_center"] ?: if (isMetric) "${fmt(dn * 3.0)}" else "${fmt(dn * 3.0 / 25.4)}\""
                val bwBackFace = dimMap["back_to_face"] ?: if (isMetric) "${fmt(dn * 2.1)}" else "${fmt(dn * 2.1 / 25.4)}\""
                val bwLen = dimMap["length"] ?: dimMap["center_end"] ?: if (isMetric) "63.5" else "2.50\""
                val bwSmallOd = dimMap["small_od"] ?: dimMap["branch_center_end"] ?: if (isMetric) "${fmt(dn * 0.75)}" else "${fmt(dn * 0.75 / 25.4)}\""
                val bwLapDia = dimMap["lap_dia"] ?: if (isMetric) "${fmt(dn * 1.5)}" else "${fmt(dn * 1.5 / 25.4)}\""
                val bwRadius = dimMap["radius"] ?: if (isMetric) "8.0" else "5/16\""
                when (key) {
                    "3" -> bwOd
                    "4" -> if (dimMap.containsKey("center_center")) bwCenterCenter else bwCenterEnd
                    "5" -> if (dimMap.containsKey("back_to_face")) bwBackFace else bwCenterEnd
                    "6" -> if (dimMap.containsKey("lap_dia")) bwLapDia else bwCenterEnd
                    "7" -> bwRadius
                    "10" -> data.selectedPrimary
                    "13", "14", "15" -> wtStr
                    "39" -> bwSmallOd
                    "DB2_4" -> if (isMetric) "3.9" else "0.15\""
                    else -> dimMap[key.lowercase()] ?: data.selectedPrimary
                }
            } else {
                when (key) {
                    "3" -> odStr
                    "4" -> thkStr
                    "5" -> hubStr
                    "6" -> boreStr
                    "7" -> dimMap["hgt"] ?: if (isMetric) "125" else "4.92\""
                    "8" -> dimMap["rad"] ?: if (isMetric) "3.0" else "1/8\""
                    "9" -> lenStr
                    "10" -> dimMap["socket"] ?: if (isMetric) "16.0" else "5/8\""
                    "11" -> rfStr
                    "12" -> if (isMetric) "2.0" else "1/16\""
                    "13" -> dimMap["rtj_dia"] ?: if (isMetric) "${fmt(dn * 1.6)}" else "${fmt(dn * 1.6 / 25.4)}\""
                    "14" -> dimMap["ring"] ?: "R23"
                    "15" -> if (isMetric) "6.4" else "1/4\""
                    "16" -> if (isMetric) "8.7" else "11/32\""
                    "17" -> if (isMetric) "4.0" else "5/32\""
                    "18" -> boreStr
                    "19" -> pcdStr
                    "20" -> numBolts
                    "21" -> boltSz
                    "22" -> if (isMetric) "60" else "2.36\""
                    "23" -> studStr
                    "24" -> studStr
                    "25" -> if (isMetric) "19.0" else "3/4\""
                    "27", "28", "29" -> wtStr
                    "32" -> boreStr
                    "36" -> pcdStr
                    "37" -> if (isMetric) "6.4" else "1/4\""
                    "38" -> if (isMetric) "8.7" else "11/32\""
                    "43" -> if (isMetric) "${fmt(dn * 1.2)}" else "${fmt(dn * 1.2 / 25.4)}\""
                    "44" -> if (isMetric) "3.9" else "0.154\""
                    "46" -> "$numBolts × $boltSz"
                    "48" -> "37.5°"
                    "49" -> "10°"
                    "50" -> if (isMetric) "1.6" else "1/16\""
                    "51" -> if (isMetric) "3.0" else "1/8\""
                    "52" -> if (isMetric) "1.6" else "1/16\""
                    "DB2_6" -> data.selectedPrimary
                    "DB2_4" -> thkStr
                    else -> dimMap[key.lowercase()] ?: data.selectedPrimary
                }
            }
        }

        val bytes = themedSvg.toByteArray(Charsets.UTF_8)
        renderedSvgBytesCache[cacheKey] = bytes
        bytes
    } catch (e: Exception) {
        null
    }
}

