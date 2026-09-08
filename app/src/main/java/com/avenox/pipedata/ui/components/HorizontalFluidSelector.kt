package com.avenox.pipedata.ui.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenox.pipedata.ui.screens.HelpQuestionButton
import com.avenox.pipedata.ui.theme.CadColorPalette
import kotlinx.coroutines.launch
import kotlin.math.*

/**
 * High-Precision Horizontal Fluid Animated Slider & Engineering Selector
 * Inspired by fluid interactive slider controls (Dribbble fluid UI/UX interactions).
 * Replaces bulky circular dials with a sleek, space-saving horizontal tape ruler,
 * fluid Gaussian wave ticks, glowing vernier pointer needle, and unified segmented controls.
 */
@Composable
fun HorizontalFluidSelector(
    availableNps: List<String>,
    selectedNps: String,
    onNpsChange: (String) -> Unit,
    dn: Int,
    primaryLabel: String,
    primaryOptions: List<String>,
    selectedPrimary: String,
    onPrimaryChange: (String) -> Unit,
    secondaryLabel: String?,
    secondaryOptions: List<String>,
    selectedSecondary: String?,
    onSecondaryChange: ((String) -> Unit)?,
    colors: CadColorPalette,
    fontScale: Float,
    isTr: Boolean,
    onOpenHelp: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = availableNps.indexOf(selectedNps).coerceAtLeast(0)
    val totalNps = availableNps.size.coerceAtLeast(1)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surface)
            .border(1.dp, colors.border, RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ====================================================================
        // 1. HEADER ROW: Title, Info Button, and Active Readout Badge
        // ====================================================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isTr) "Hassas Ölçü Kaydırıcı" else "Fluid Precision Slider",
                    color = colors.textSecondary,
                    fontSize = (12.5 * fontScale).sp,
                    fontWeight = FontWeight.Bold
                )
                HelpQuestionButton(
                    onClick = { onOpenHelp("nps") },
                    fontScale = fontScale
                )
            }

            // Compact Active Readout Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(colors.cardBackground)
                    .border(1.dp, colors.border, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = buildString {
                        append(selectedNps)
                        append(" • DN ")
                        append(dn)
                        if (selectedPrimary.isNotEmpty()) {
                            append(" • ")
                            append(selectedPrimary)
                        }
                        if (!selectedSecondary.isNullOrEmpty()) {
                            append(" ")
                            append(selectedSecondary)
                        }
                    },
                    color = colors.accent,
                    fontSize = (11.5 * fontScale).sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ====================================================================
        // 2. HORIZONTAL FLUID RULER / SLIDER TRACK
        // ====================================================================
        FluidRulerTrack(
            availableNps = availableNps,
            selectedIndex = selectedIndex,
            onIndexSelected = { idx ->
                if (idx in availableNps.indices) {
                    onNpsChange(availableNps[idx])
                }
            },
            colors = colors,
            fontScale = fontScale
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ====================================================================
        // 3. SCHEDULE / PRESSURE RATING SEGMENTED STRIP
        // ====================================================================
        if (primaryOptions.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = primaryLabel.uppercase(),
                    color = colors.textSecondary,
                    fontSize = (10.5 * fontScale).sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace
                )
                HelpQuestionButton(
                    onClick = { onOpenHelp(if (primaryLabel.contains("Sch", ignoreCase = true)) "sch" else "class") },
                    fontScale = fontScale
                )
            }

            FluidSegmentedStrip(
                options = primaryOptions,
                selectedOption = selectedPrimary,
                onOptionChange = onPrimaryChange,
                colors = colors,
                fontScale = fontScale
            )
        }

        // ====================================================================
        // 4. FACING SEGMENTED STRIP (IF APPLICABLE, E.G. FLANGES RF / RTJ / FF)
        // ====================================================================
        if (!secondaryLabel.isNullOrEmpty() && secondaryOptions.isNotEmpty() && selectedSecondary != null && onSecondaryChange != null) {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = secondaryLabel.uppercase(),
                    color = colors.textSecondary,
                    fontSize = (10.5 * fontScale).sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace
                )
                HelpQuestionButton(
                    onClick = { onOpenHelp("facing") },
                    fontScale = fontScale
                )
            }

            FluidSegmentedStrip(
                options = secondaryOptions,
                selectedOption = selectedSecondary,
                onOptionChange = onSecondaryChange,
                colors = colors,
                fontScale = fontScale
            )
        }
    }
}

/**
 * Backward compatibility alias for any caller expecting EngineeringDialSelector
 */
@Composable
fun EngineeringDialSelector(
    availableNps: List<String>,
    selectedNps: String,
    onNpsChange: (String) -> Unit,
    dn: Int,
    primaryLabel: String,
    primaryOptions: List<String>,
    selectedPrimary: String,
    onPrimaryChange: (String) -> Unit,
    secondaryLabel: String?,
    secondaryOptions: List<String>,
    selectedSecondary: String?,
    onSecondaryChange: ((String) -> Unit)?,
    colors: CadColorPalette,
    fontScale: Float,
    isTr: Boolean,
    onOpenHelp: (String) -> Unit,
    modifier: Modifier = Modifier
) = HorizontalFluidSelector(
    availableNps = availableNps,
    selectedNps = selectedNps,
    onNpsChange = onNpsChange,
    dn = dn,
    primaryLabel = primaryLabel,
    primaryOptions = primaryOptions,
    selectedPrimary = selectedPrimary,
    onPrimaryChange = onPrimaryChange,
    secondaryLabel = secondaryLabel,
    secondaryOptions = secondaryOptions,
    selectedSecondary = selectedSecondary,
    onSecondaryChange = onSecondaryChange,
    colors = colors,
    fontScale = fontScale,
    isTr = isTr,
    onOpenHelp = onOpenHelp,
    modifier = modifier
)

// ============================================================================
// FLUID RULER TRACK IMPLEMENTATION
// ============================================================================
@Composable
private fun FluidRulerTrack(
    availableNps: List<String>,
    selectedIndex: Int,
    onIndexSelected: (Int) -> Unit,
    colors: CadColorPalette,
    fontScale: Float
) {
    val totalNps = availableNps.size.coerceAtLeast(1)
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val stepDp = 58.dp
    val stepPx = with(density) { stepDp.toPx() }

    val animatableOffset = remember { Animatable(selectedIndex.toFloat()) }
    var isDragging by remember { mutableStateOf(false) }

    // Synchronize animatable position when selectedIndex changes from outside
    LaunchedEffect(selectedIndex, totalNps) {
        if (!isDragging && abs(animatableOffset.value - selectedIndex) > 0.01f) {
            animatableOffset.animateTo(
                targetValue = selectedIndex.toFloat(),
                animationSpec = spring(dampingRatio = 0.72f, stiffness = 380f)
            )
        }
    }

    // Android native paints for high-speed sub-millisecond Canvas rendering
    val activePaint = remember(colors.accent, fontScale) {
        Paint().apply {
            color = colors.accent.toArgb()
            textSize = with(density) { (13.5f * fontScale).sp.toPx() }
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
    }

    val inactivePaint = remember(colors.textSecondary, fontScale) {
        Paint().apply {
            color = colors.textSecondary.toArgb()
            textSize = with(density) { (11f * fontScale).sp.toPx() }
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.cardBackground)
            .border(1.dp, colors.border, RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Quick Left Stepper Button
        Box(
            modifier = Modifier
                .width(36.dp)
                .fillMaxHeight()
                .clickable(enabled = selectedIndex > 0) {
                    val prev = (selectedIndex - 1).coerceAtLeast(0)
                    onIndexSelected(prev)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Previous NPS",
                tint = if (selectedIndex > 0) colors.accent else colors.border,
                modifier = Modifier.size(20.dp)
            )
        }

        // The Main Canvas Tape Ruler Viewport
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .pointerInput(totalNps, stepPx) {
                    detectTapGestures { tapOffset ->
                        val centerPx = size.width / 2f
                        val deltaPx = tapOffset.x - centerPx
                        val target = (animatableOffset.value + (deltaPx / stepPx)).roundToInt().coerceIn(0, totalNps - 1)
                        coroutineScope.launch {
                            animatableOffset.animateTo(
                                targetValue = target.toFloat(),
                                animationSpec = spring(dampingRatio = 0.72f, stiffness = 420f)
                            )
                            onIndexSelected(target)
                        }
                    }
                }
                .pointerInput(totalNps, stepPx) {
                    detectDragGestures(
                        onDragStart = { isDragging = true },
                        onDragEnd = {
                            isDragging = false
                            val snap = animatableOffset.value.roundToInt().coerceIn(0, totalNps - 1)
                            coroutineScope.launch {
                                animatableOffset.animateTo(
                                    targetValue = snap.toFloat(),
                                    animationSpec = spring(dampingRatio = 0.72f, stiffness = 420f)
                                )
                                onIndexSelected(snap)
                            }
                        },
                        onDragCancel = {
                            isDragging = false
                            val snap = animatableOffset.value.roundToInt().coerceIn(0, totalNps - 1)
                            coroutineScope.launch {
                                animatableOffset.animateTo(
                                    targetValue = snap.toFloat(),
                                    animationSpec = spring(dampingRatio = 0.72f, stiffness = 420f)
                                )
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val delta = -dragAmount.x / stepPx
                            coroutineScope.launch {
                                val newVal = (animatableOffset.value + delta).coerceIn(-0.35f, (totalNps - 1) + 0.35f)
                                animatableOffset.snapTo(newVal)
                                val currentInt = newVal.roundToInt().coerceIn(0, totalNps - 1)
                                if (currentInt != selectedIndex) {
                                    onIndexSelected(currentInt)
                                }
                            }
                        }
                    )
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val centerPx = width / 2f
                val currentOffset = animatableOffset.value

                val baselineY = height - 14.dp.toPx()
                val textY = 24.dp.toPx()

                // Baseline ruler guideline
                drawLine(
                    color = colors.border.copy(alpha = 0.45f),
                    start = Offset(0f, baselineY),
                    end = Offset(width, baselineY),
                    strokeWidth = 1.dp.toPx()
                )

                // Fluid Lens Glow at Center
                drawRoundRect(
                    color = colors.accent.copy(alpha = 0.12f),
                    topLeft = Offset(centerPx - 26.dp.toPx(), 6.dp.toPx()),
                    size = Size(52.dp.toPx(), height - 12.dp.toPx()),
                    cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                )

                val sigma = stepPx * 0.9f

                // Draw Ticks & Text
                for (i in 0 until totalNps) {
                    val itemX = centerPx + (i - currentOffset) * stepPx

                    // Culling: only draw items visible within horizontal viewport
                    if (itemX >= -stepPx && itemX <= width + stepPx) {
                        val distFromCenter = abs(itemX - centerPx)
                        // Gaussian Wave factor for fluid bulge (0.0 to 1.0)
                        val waveFactor = exp(- (distFromCenter * distFromCenter) / (2f * sigma * sigma))

                        // Major Tick Line
                        val majorTickBaseHeight = 16.dp.toPx()
                        val majorTickHeight = majorTickBaseHeight + waveFactor * (16.dp.toPx())
                        val tickStroke = 1.5.dp.toPx() + waveFactor * (1.5.dp.toPx())

                        val tickColor = if (waveFactor > 0.5f) {
                            colors.accent
                        } else {
                            colors.border
                        }

                        drawLine(
                            color = tickColor,
                            start = Offset(itemX, baselineY),
                            end = Offset(itemX, baselineY - majorTickHeight),
                            strokeWidth = tickStroke,
                            cap = StrokeCap.Round
                        )

                        // NPS Text Label
                        val label = availableNps[i]
                        val isCenterItem = waveFactor > 0.65f

                        drawContext.canvas.nativeCanvas.drawText(
                            label,
                            itemX,
                            textY,
                            if (isCenterItem) activePaint else inactivePaint
                        )

                        // Draw Minor Sub-Ticks between items (3 sub-ticks per interval)
                        if (i < totalNps - 1) {
                            for (sub in 1..3) {
                                val subX = itemX + (sub / 4f) * stepPx
                                if (subX >= 0 && subX <= width) {
                                    val subDist = abs(subX - centerPx)
                                    val subWave = exp(- (subDist * subDist) / (2f * sigma * sigma))
                                    val subBase = if (sub == 2) 9.dp.toPx() else 6.dp.toPx()
                                    val subHeight = subBase + subWave * 6.dp.toPx()

                                    drawLine(
                                        color = colors.border.copy(alpha = 0.5f + (subWave * 0.5f)),
                                        start = Offset(subX, baselineY),
                                        end = Offset(subX, baselineY - subHeight),
                                        strokeWidth = 1.dp.toPx(),
                                        cap = StrokeCap.Round
                                    )
                                }
                            }
                        }
                    }
                }

                // Precision Vernier Needle (Center Pointer)
                val needleTopY = 8.dp.toPx()
                val needleBottomY = baselineY + 4.dp.toPx()

                // Center glowing indicator needle
                drawLine(
                    color = colors.accent,
                    start = Offset(centerPx, needleTopY),
                    end = Offset(centerPx, needleBottomY),
                    strokeWidth = 2.5.dp.toPx(),
                    cap = StrokeCap.Round
                )

                // Top downward fluid triangle pointer
                val pointerPath = Path().apply {
                    moveTo(centerPx - 5.dp.toPx(), needleTopY)
                    lineTo(centerPx + 5.dp.toPx(), needleTopY)
                    lineTo(centerPx, needleTopY + 6.dp.toPx())
                    close()
                }
                drawPath(pointerPath, color = colors.accent)

                // Bottom precision dot
                drawCircle(
                    color = colors.accent,
                    radius = 2.5.dp.toPx(),
                    center = Offset(centerPx, needleBottomY)
                )
            }
        }

        // Quick Right Stepper Button
        Box(
            modifier = Modifier
                .width(36.dp)
                .fillMaxHeight()
                .clickable(enabled = selectedIndex < totalNps - 1) {
                    val next = (selectedIndex + 1).coerceAtMost(totalNps - 1)
                    onIndexSelected(next)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Next NPS",
                tint = if (selectedIndex < totalNps - 1) colors.accent else colors.border,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ============================================================================
// COHESIVE FLUID SEGMENTED CONTROL
// ============================================================================
@Composable
private fun FluidSegmentedStrip(
    options: List<String>,
    selectedOption: String,
    onOptionChange: (String) -> Unit,
    colors: CadColorPalette,
    fontScale: Float,
    modifier: Modifier = Modifier
) {
    if (options.size <= 6) {
        // Equal-width connected segmented bar for small sets (Ratings, Facings, short schedules)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colors.cardBackground)
                .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEach { opt ->
                val isSelected = opt == selectedOption
                val animBg by animateColorAsState(
                    targetValue = if (isSelected) colors.surface else Color.Transparent,
                    animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(6.dp))
                        .background(animBg)
                        .then(
                            if (isSelected) Modifier.border(1.dp, colors.accent.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            else Modifier
                        )
                        .clickable { onOptionChange(opt) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = opt,
                        color = if (isSelected) colors.accent else colors.textSecondary,
                        fontSize = (11.5 * fontScale).sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                    )
                }
            }
        }
    } else {
        // Horizontally scrolling segmented bar for long lists (e.g. 10+ pipe schedules)
        val selectedIdx = options.indexOf(selectedOption).coerceAtLeast(0)
        val listState = rememberLazyListState()

        LaunchedEffect(selectedIdx) {
            listState.animateScrollToItem((selectedIdx - 2).coerceAtLeast(0))
        }

        LazyRow(
            state = listState,
            modifier = modifier
                .fillMaxWidth()
                .height(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colors.cardBackground)
                .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                .padding(2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(options) { idx, opt ->
                val isSelected = opt == selectedOption
                val animBg by animateColorAsState(
                    targetValue = if (isSelected) colors.surface else Color.Transparent,
                    animationSpec = spring(dampingRatio = 0.8f, stiffness = 400f)
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(6.dp))
                        .background(animBg)
                        .then(
                            if (isSelected) Modifier.border(1.dp, colors.accent.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            else Modifier
                        )
                        .clickable { onOptionChange(opt) }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = opt,
                        color = if (isSelected) colors.accent else colors.textSecondary,
                        fontSize = (11.5 * fontScale).sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                    )
                }
            }
        }
    }
}
