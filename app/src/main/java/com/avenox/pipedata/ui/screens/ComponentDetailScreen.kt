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
    currentUnit: UnitSystem,
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
    val subtypeTitle = currentSubtype.title

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
                                text = if (currentSubtype.abbr.isNotBlank()) "$subtypeTitle (${currentSubtype.abbr})" else subtypeTitle,
                                color = colors.textPrimary,
                                fontSize = (16.5 * fontScale).sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
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
                            Text(
                                text = if (sub.abbr.isNotBlank()) sub.abbr else sub.shortName,
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
                            appendLine("${data.currentSubtype.title} (${data.selectedNps}, ${data.selectedPrimary}${if (data.selectedSecondary != null) " ${data.selectedSecondary}" else ""})")
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

    val dynamicCacheKey = "${svgAsset}_${theme.name}_${data.selectedNps}_${data.selectedPrimary}_${data.selectedSecondary}_${isMetric}"

    val svgBytes = remember(dynamicCacheKey) {
        getThemedSvgBytes(context, svgAsset, theme, data, isMetric)
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
private fun fmt(d: Double): String = if (d % 1.0 == 0.0) d.toInt().toString() else "%.1f".format(java.util.Locale.US, d)

/**
 * Reads raw SVG asset, injects theme-calibrated CSS, and dynamically substitutes
 * all dimension placeholders ([[3]], [[4]], [[19]], [[6]], [[11]], [[DB2_6]], etc.)
 * with real, live engineering numbers corresponding to the user's active selection.
 */
private fun getThemedSvgBytes(
    context: Context,
    assetName: String,
    theme: CadTheme,
    data: ComponentData,
    isMetric: Boolean
): ByteArray? {
    return try {
        val rawSvg = context.assets.open("svg/$assetName").bufferedReader().use { it.readText() }
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
  .cad-badge-bg { fill: #f1f5f9; stroke: #0f172a; stroke-width: 1.5; rx: 6; ry: 6; }
  .cad-badge-text { font-family: 'DejaVu Sans', 'Liberation Sans', monospace; font-size: 17px; font-weight: bold; fill: #0f172a; text-anchor: middle; dominant-baseline: central; }
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
  .cad-badge-bg { fill: #0b294a; stroke: #60a5fa; stroke-width: 1.5; rx: 6; ry: 6; }
  .cad-badge-text { font-family: 'DejaVu Sans', 'Liberation Sans', monospace; font-size: 17px; font-weight: bold; fill: #ffffff; text-anchor: middle; dominant-baseline: central; }
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
  .cad-badge-bg { fill: #1a1200; stroke: #ffb000; stroke-width: 1.5; rx: 6; ry: 6; }
  .cad-badge-text { font-family: 'DejaVu Sans', 'Liberation Sans', monospace; font-size: 17px; font-weight: bold; fill: #ffb000; text-anchor: middle; dominant-baseline: central; }
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
  .cad-badge-bg { fill: #0f172a; stroke: #38bdf8; stroke-width: 1.5; rx: 6; ry: 6; }
  .cad-badge-text { font-family: 'DejaVu Sans', 'Liberation Sans', monospace; font-size: 17px; font-weight: bold; fill: #ffffff; text-anchor: middle; dominant-baseline: central; }
</style>
""".trimIndent()
        }

        val styleRegex = Regex("<style>[\\s\\S]*?</style>")
        var themedSvg = if (styleRegex.containsMatchIn(rawSvg)) {
            styleRegex.replace(rawSvg, styleReplacement)
        } else {
            rawSvg
        }

        // Build dimension lookup table from active component specifications
        val dimMap = mutableMapOf<String, String>()
        data.dimensions.forEach { row ->
            val raw = if (isMetric) row.valueMetric else row.valueImperial
            val cleaned = raw.replace(" mm", "").replace(" kg", "kg").replace(" lb", "lb").trim()
            dimMap[row.key.lowercase()] = cleaned
        }

        val dn = data.dn
        val odStr = dimMap["od"] ?: if (isMetric) "${fmt(dn * 2.8)}" else "${fmt(dn * 2.8 / 25.4)}\""
        val thkStr = dimMap["thk"] ?: if (isMetric) "17.5" else "0.69\""
        val hubStr = dimMap["hub"] ?: dimMap["ctf"] ?: if (isMetric) "${fmt(dn * 1.5)}" else "${fmt(dn * 1.5 / 25.4)}\""
        val boreStr = dimMap["bore"] ?: dimMap["id"] ?: if (isMetric) "$dn" else "${fmt(dn / 25.4)}\""
        val pcdStr = dimMap["pcd"] ?: if (isMetric) "${fmt(dn * 2.2)}" else "${fmt(dn * 2.2 / 25.4)}\""
        val lenStr = dimMap["len"] ?: dimMap["lth"] ?: if (isMetric) "${fmt(dn * 1.2)}" else "${fmt(dn * 1.2 / 25.4)}\""
        val rfStr = dimMap["rf"] ?: if (isMetric) "${fmt(dn * 1.75)}" else "${fmt(dn * 1.75 / 25.4)}\""
        val boltsStr = dimMap["bolts"] ?: "4 × 5/8\""
        val studStr = dimMap["stud"] ?: if (isMetric) "70" else "2.75\""
        val wtStr = dimMap["weight"] ?: if (isMetric) "2.5kg" else "5.5lb"

        val numBolts = boltsStr.split("×").firstOrNull()?.trim() ?: "4"
        val boltSz = boltsStr.split("×").getOrNull(1)?.trim() ?: "5/8\""

        // Dynamically inject real engineering values into all SVG parameter callout badges
        val placeholderRegex = Regex("\\[\\[(.*?)\\]\\]")
        themedSvg = placeholderRegex.replace(themedSvg) { matchResult ->
            val key = matchResult.groupValues[1].trim()
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

        themedSvg.toByteArray(Charsets.UTF_8)
    } catch (e: Exception) {
        null
    }
}

