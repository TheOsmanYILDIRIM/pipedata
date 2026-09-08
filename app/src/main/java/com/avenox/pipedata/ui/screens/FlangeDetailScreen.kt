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
fun FlangeDetailScreen(
    flangeTypes: List<FlangeTypeItem>,
    currentTypeIndex: Int,
    onTypeChange: (Int) -> Unit,
    selectedNps: String,
    onNpsChange: (String) -> Unit,
    selectedRating: Int,
    onRatingChange: (Int) -> Unit,
    selectedFacing: String,
    onFacingChange: (String) -> Unit,
    spec: FlangeSpec,
    currentUnit: UnitSystem,
    fontScale: Float,
    currentLang: AppLanguage,
    onOpenHelp: (String) -> Unit,
    onBack: () -> Unit
) {
    val colors = LocalCadColors.current
    val context = LocalContext.current
    val isTr = currentLang == AppLanguage.TR
    val currentType = flangeTypes.getOrElse(currentTypeIndex) { flangeTypes.first() }

    val npsList = listOf("1/2\"", "3/4\"", "1\"", "1 1/4\"", "1 1/2\"", "2\"", "2 1/2\"", "3\"", "4\"", "6\"", "8\"", "10\"", "12\"", "14\"", "16\"", "18\"", "20\"", "24\"")
    val ratingList = listOf(150, 300, 600)
    val facingList = listOf("RF", "FF", "RTJ")

    // CAD Canvas Interactive Zoom/Pan State
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

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
                            text = "${currentType.shortName} (${currentType.abbr})",
                            color = colors.textPrimary,
                            fontSize = (17 * fontScale).sp,
                            fontWeight = FontWeight.Bold
                        )
                        // Help question mark button for Flange Type
                        HelpQuestionButton(
                            onClick = { onOpenHelp("flange_types") },
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
                            text = currentType.standard,
                            color = colors.accent,
                            fontSize = (11 * fontScale).sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Divider(color = colors.border, thickness = 1.dp)
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
                    .height(290.dp)
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
                                val maxOffset = (scale - 1f) * 400f
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
                        .padding(16.dp)
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
                            .data("file:///android_asset/svg/${currentType.svgAsset}")
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = currentType.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Mini Left Arrow
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(colors.surface.copy(alpha = 0.75f))
                        .border(1.dp, colors.border, CircleShape)
                        .clickable {
                            val newIdx = if (currentTypeIndex > 0) currentTypeIndex - 1 else flangeTypes.lastIndex
                            onTypeChange(newIdx)
                            resetZoom()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Previous",
                        tint = colors.textPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Mini Right Arrow
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(colors.surface.copy(alpha = 0.75f))
                        .border(1.dp, colors.border, CircleShape)
                        .clickable {
                            val newIdx = if (currentTypeIndex < flangeTypes.lastIndex) currentTypeIndex + 1 else 0
                            onTypeChange(newIdx)
                            resetZoom()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Next",
                        tint = colors.textPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Zoom Level Indicator
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .background(colors.surface.copy(alpha = 0.85f), RoundedCornerShape(6.dp))
                        .border(1.dp, colors.border, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${String.format("%.1f", scale)}x • Pinch to Zoom",
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
                            .padding(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(colors.chipBg)
                            .border(1.dp, colors.accent, RoundedCornerShape(6.dp))
                            .clickable { resetZoom() }
                            .padding(horizontal = 10.dp, vertical = 4.dp)
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

            Spacer(modifier = Modifier.height(14.dp))

            // ================================================================
            // SELECTORS CONTAINER
            // ================================================================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // 1. NPS Row Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isTr) "Anma Çapı (NPS)" else "Size (NPS)",
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
                        text = "$selectedNps (DN ${spec.dn})",
                        color = colors.textPrimary,
                        fontSize = (13 * fontScale).sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // NPS Chips Strip
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(npsList) { nps ->
                        val isSelected = selectedNps == nps
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) colors.accent else colors.chipBg)
                                .border(1.dp, if (isSelected) colors.accent else colors.border, RoundedCornerShape(8.dp))
                                .clickable { onNpsChange(nps) }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
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

                Spacer(modifier = Modifier.height(14.dp))

                // Dual Selectors: Class and Facing
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Class Selector
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Text(
                                text = if (isTr) "Sınıf" else "Class",
                                color = colors.textSecondary,
                                fontSize = (12.5 * fontScale).sp,
                                fontWeight = FontWeight.Bold
                            )
                            HelpQuestionButton(
                                onClick = { onOpenHelp("rating") },
                                fontScale = fontScale
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(colors.chipBg, RoundedCornerShape(8.dp))
                                .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                                .padding(2.dp)
                        ) {
                            ratingList.forEach { r ->
                                val sel = selectedRating == r
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (sel) colors.accent else colors.chipBg)
                                        .clickable { onRatingChange(r) }
                                        .padding(vertical = 7.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${r}#",
                                        color = if (sel) colors.accentText else colors.textPrimary,
                                        fontSize = (11.5 * fontScale).sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    // Facing Selector
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 6.dp)
                        ) {
                            Text(
                                text = if (isTr) "Yüzey" else "Facing",
                                color = colors.textSecondary,
                                fontSize = (12.5 * fontScale).sp,
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
                                .background(colors.chipBg, RoundedCornerShape(8.dp))
                                .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                                .padding(2.dp)
                        ) {
                            facingList.forEach { f ->
                                val sel = selectedFacing == f
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (sel) colors.accent else colors.chipBg)
                                        .clickable { onFacingChange(f) }
                                        .padding(vertical = 7.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = f,
                                        color = if (sel) colors.accentText else colors.textPrimary,
                                        fontSize = (11.5 * fontScale).sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ================================================================
            // SPECIFICATION TABLE CARD
            // ================================================================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isTr) "Ölçüler ve PCD" else "Dimensions & PCD",
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
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(colors.border)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        val isMetric = currentUnit == UnitSystem.METRIC

                        SpecTableRow(
                            name = if (isTr) "Dış Çap (O)" else "Outside Diameter (O)",
                            value = if (isMetric) "${spec.odMm} mm" else "${String.format("%.2f", spec.odIn)}\"",
                            fontScale = fontScale
                        )
                        SpecTableRow(
                            name = if (isTr) "Min. Kalınlık (T)" else "Min Thickness (T)",
                            value = if (isMetric) "${spec.thkMm} mm" else "${String.format("%.2f", spec.thkIn)}\"",
                            fontScale = fontScale
                        )
                        SpecTableRow(
                            name = if (isTr) "Boyun Tabanı (X)" else "Base Hub (X)",
                            value = if (isMetric) "${spec.hubXMm} mm" else "${String.format("%.2f", spec.hubXIn)}\"",
                            fontScale = fontScale
                        )
                        SpecTableRow(
                            name = if (isTr) "İç Delik (B)" else "Inside Bore (B)",
                            value = if (isMetric) "${spec.boreBMm} mm" else "${String.format("%.2f", spec.boreBIn)}\"",
                            fontScale = fontScale
                        )
                        SpecTableRow(
                            name = if (isTr) "Cıvata Dairesi (PCD)" else "Bolt Circle (PCD)",
                            value = if (isMetric) "${spec.pcdMm} mm" else "${String.format("%.2f", spec.pcdIn)}\"",
                            fontScale = fontScale
                        )
                        SpecTableRow(
                            name = if (isTr) "Cıvata Adedi & Çap" else "Bolting",
                            value = "${spec.boltsCount} × ${spec.boltSizeIn}",
                            fontScale = fontScale
                        )
                        SpecTableRow(
                            name = if (isTr) "Saplama Boyu" else "Stud Length",
                            value = if (isMetric) "${spec.studLengthMm} mm" else "${String.format("%.2f", spec.studLengthIn)}\"",
                            fontScale = fontScale
                        )
                        SpecTableRow(
                            name = if (isTr) "Tahmini Ağırlık" else "Approx. Weight",
                            value = if (isMetric) "${spec.weightKg} kg" else "${String.format("%.2f", spec.weightLb)} lb",
                            fontScale = fontScale,
                            isLast = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Copy Specification Button
                Button(
                    onClick = {
                        val textToCopy = buildString {
                            appendLine("${currentType.title} - $selectedNps ${selectedRating}# $selectedFacing")
                            appendLine("OD: ${spec.odMm} mm | T: ${spec.thkMm} mm | Hub: ${spec.hubXMm} mm | Bore: ${spec.boreBMm} mm")
                            appendLine("PCD: ${spec.pcdMm} mm | Bolts: ${spec.boltsCount}x${spec.boltSizeIn} | Stud: ${spec.studLengthMm} mm | Weight: ${spec.weightKg} kg")
                        }
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Flange Spec", textToCopy)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, if (isTr) "Ölçüler Panoya Kopyalandı! ✓" else "Specification Copied! ✓", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.accent,
                        contentColor = colors.accentText
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isTr) "Ölçüleri Kopyala" else "Copy Specification",
                        fontSize = (14 * fontScale).sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SpecTableRow(
    name: String,
    value: String,
    fontScale: Float,
    isLast: Boolean = false
) {
    val colors = LocalCadColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = colors.textSecondary,
            fontSize = (13 * fontScale).sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = colors.textPrimary,
            fontSize = (13.5 * fontScale).sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
    if (!isLast) {
        Divider(color = colors.border.copy(alpha = 0.5f), thickness = 0.7.dp)
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
