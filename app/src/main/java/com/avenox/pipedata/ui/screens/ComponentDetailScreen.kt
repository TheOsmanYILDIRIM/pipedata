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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.avenox.pipedata.data.model.*
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
    onOpenHelp: (String) -> Unit,
    onBack: () -> Unit
) {
    val colors = LocalCadColors.current
    val context = LocalContext.current
    val isTr = currentLang == AppLanguage.TR

    val currentSubtype = data.currentSubtype
    val subtypeTitle = currentSubtype.title

    // CAD Canvas Interactive Zoom/Pan State
    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    fun resetZoom() {
        scale = 1f
        offsetX = 0f
        offsetY = 0f
    }

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
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = colors.textPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentSubtype.abbr.isNotBlank()) "$subtypeTitle (${currentSubtype.abbr})" else subtypeTitle,
                            color = colors.textPrimary,
                            fontSize = (16.5 * fontScale).sp,
                            fontWeight = FontWeight.Bold
                        )
                        HelpQuestionButton(
                            onClick = { onOpenHelp(data.categoryId) },
                            fontScale = fontScale
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(colors.chipBg, RoundedCornerShape(6.dp))
                            .border(1.dp, colors.border, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentSubtype.standard,
                            color = colors.accent,
                            fontSize = (10.5 * fontScale).sp,
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
            // MAXIMIZED EXPANSIVE CAD DRAWING STAGE
            // ================================================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
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
                        .padding(14.dp)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offsetX,
                            translationY = offsetY
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("file:///android_asset/svg/${currentSubtype.svgAsset}")
                            .decoderFactory(SvgDecoder.Factory())
                            .crossfade(false)
                            .build(),
                        contentDescription = subtypeTitle,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Mini Left Arrow
                if (data.allSubtypes.size > 1) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp)
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(colors.surface.copy(alpha = 0.8f))
                            .border(1.dp, colors.border, CircleShape)
                            .clickable {
                                val newIdx = if (currentSubtypeIndex > 0) currentSubtypeIndex - 1 else data.allSubtypes.lastIndex
                                onSubtypeChange(newIdx)
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
                                val newIdx = if (currentSubtypeIndex < data.allSubtypes.lastIndex) currentSubtypeIndex + 1 else 0
                                onSubtypeChange(newIdx)
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

                // Zoom Level Indicator
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                        .background(colors.surface.copy(alpha = 0.85f), RoundedCornerShape(6.dp))
                        .border(1.dp, colors.border, RoundedCornerShape(6.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "${String.format("%.1f", scale)}x • Zoom",
                        color = colors.textSecondary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Reset Button if zoomed
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
                                .clickable {
                                    onSubtypeChange(idx)
                                    resetZoom()
                                }
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
            // SELECTORS CONTAINER
            // ================================================================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
            ) {
                // 1. NPS Row Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isTr) "Anma Çapı (NPS / DN)" else "Size (NPS / DN)",
                            color = colors.textSecondary,
                            fontSize = (12.5 * fontScale).sp,
                            fontWeight = FontWeight.Bold
                        )
                        HelpQuestionButton(
                            onClick = { onOpenHelp("nps") },
                            fontScale = fontScale
                        )
                    }
                    Text(
                        text = "${data.selectedNps} (DN ${data.dn})",
                        color = colors.textPrimary,
                        fontSize = (13 * fontScale).sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // NPS Chips Strip
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(data.availableNps) { nps ->
                        val isSelected = data.selectedNps == nps
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) colors.accent else colors.chipBg)
                                .border(1.dp, if (isSelected) colors.accent else colors.border, RoundedCornerShape(8.dp))
                                .clickable { onNpsChange(nps) }
                                .padding(horizontal = 11.dp, vertical = 7.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = nps,
                                color = if (isSelected) colors.accentText else colors.textPrimary,
                                fontSize = (12 * fontScale).sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Primary Selector (Rating/Schedule) and Secondary Selector (Facing)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Primary Selector
                    Column(modifier = Modifier.weight(1f)) {
                        val selectorLabel = if (isTr) data.primarySelectorLabelTr else data.primarySelectorLabelEn
                        val helpTopic = if (data.categoryId == "pipes" || data.categoryId == "fittings_bw") "pipes" else "rating"

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 5.dp)
                        ) {
                            Text(
                                text = selectorLabel,
                                color = colors.textSecondary,
                                fontSize = (12 * fontScale).sp,
                                fontWeight = FontWeight.Bold
                            )
                            HelpQuestionButton(
                                onClick = { onOpenHelp(helpTopic) },
                                fontScale = fontScale
                            )
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(data.primaryOptions) { opt ->
                                val sel = data.selectedPrimary == opt
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (sel) colors.accent else colors.chipBg)
                                        .border(0.8.dp, if (sel) colors.accent else colors.border, RoundedCornerShape(6.dp))
                                        .clickable { onPrimaryChange(opt) }
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = opt,
                                        color = if (sel) colors.accentText else colors.textPrimary,
                                        fontSize = (11 * fontScale).sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    // Secondary Selector (e.g. Facing)
                    if (data.secondaryOptions.isNotEmpty() && onSecondaryChange != null && data.selectedSecondary != null) {
                        Column(modifier = Modifier.width(130.dp)) {
                            val secLabel = (if (isTr) data.secondarySelectorLabelTr else data.secondarySelectorLabelEn) ?: (if (isTr) "Yüzey" else "Facing")
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 5.dp)
                            ) {
                                Text(
                                    text = secLabel,
                                    color = colors.textSecondary,
                                    fontSize = (12 * fontScale).sp,
                                    fontWeight = FontWeight.Bold
                                )
                                HelpQuestionButton(
                                    onClick = { onOpenHelp("facing") },
                                    fontScale = fontScale
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(colors.chipBg, RoundedCornerShape(6.dp))
                                    .border(0.8.dp, colors.border, RoundedCornerShape(6.dp))
                                    .padding(2.dp)
                            ) {
                                data.secondaryOptions.forEach { f ->
                                    val sel = data.selectedSecondary == f
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(if (sel) colors.accent else colors.chipBg)
                                            .clickable { onSecondaryChange(f) }
                                            .padding(vertical = 5.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = f,
                                            color = if (sel) colors.accentText else colors.textPrimary,
                                            fontSize = (11 * fontScale).sp,
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                }
                            }
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
