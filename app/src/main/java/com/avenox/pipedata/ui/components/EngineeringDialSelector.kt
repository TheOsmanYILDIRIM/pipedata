package com.avenox.pipedata.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenox.pipedata.ui.screens.HelpQuestionButton
import com.avenox.pipedata.ui.theme.CadColorPalette
import kotlin.math.*

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
) {
    val selectedIndex = availableNps.indexOf(selectedNps).coerceAtLeast(0)
    val totalNps = availableNps.size.coerceAtLeast(1)
    val density = LocalDensity.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surface)
            .border(1.dp, colors.border, RoundedCornerShape(16.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dial Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isTr) "Kadranlı Seçici (Size & Schedule)" else "Dial Gauge (Size & Schedule)",
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
                text = "$selectedNps • DN $dn",
                color = colors.accent,
                fontSize = (12.5 * fontScale).sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ====================================================================
        // INTERACTIVE CLOCK DIAL (SAAT KADRANI)
        // ====================================================================
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Left Quick Step Button [‹]
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(colors.chipBg)
                    .border(1.dp, colors.border, CircleShape)
                    .clickable {
                        if (selectedIndex > 0) {
                            onNpsChange(availableNps[selectedIndex - 1])
                        } else {
                            onNpsChange(availableNps.last())
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Previous Size",
                    tint = colors.textPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Right Quick Step Button [›]
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(colors.chipBg)
                    .border(1.dp, colors.border, CircleShape)
                    .clickable {
                        if (selectedIndex < availableNps.lastIndex) {
                            onNpsChange(availableNps[selectedIndex + 1])
                        } else {
                            onNpsChange(availableNps.first())
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Next Size",
                    tint = colors.textPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Central Clock Dial
            val dialSizeDp = 226.dp
            val dialSizePx = with(density) { dialSizeDp.toPx() }
            val centerPx = dialSizePx / 2f

            Box(
                modifier = Modifier
                    .size(dialSizeDp)
                    .pointerInput(totalNps) {
                        detectTapGestures { offset ->
                            val dx = offset.x - centerPx
                            val dy = offset.y - centerPx
                            val dist = sqrt(dx * dx + dy * dy)
                            if (dist > with(density) { 45.dp.toPx() }) {
                                var angle = (atan2(dy, dx) * 180f / Math.PI.toFloat()) + 90f
                                if (angle < 0f) angle += 360f
                                val idx = ((angle / 360f) * totalNps).roundToInt() % totalNps
                                if (idx in availableNps.indices) {
                                    onNpsChange(availableNps[idx])
                                }
                            }
                        }
                    }
                    .pointerInput(totalNps) {
                        detectDragGestures { change, _ ->
                            val offset = change.position
                            val dx = offset.x - centerPx
                            val dy = offset.y - centerPx
                            val dist = sqrt(dx * dx + dy * dy)
                            if (dist > with(density) { 40.dp.toPx() }) {
                                var angle = (atan2(dy, dx) * 180f / Math.PI.toFloat()) + 90f
                                if (angle < 0f) angle += 360f
                                val idx = ((angle / 360f) * totalNps).roundToInt() % totalNps
                                if (idx in availableNps.indices && idx != selectedIndex) {
                                    onNpsChange(availableNps[idx])
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // Dial Surface Canvas (Bezel, Ticks, Indicator Needle)
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.minDimension / 2f
                    val outerTickR = radius - with(density) { 4.dp.toPx() }
                    val innerTickR = radius - with(density) { 13.dp.toPx() }
                    val hubR = with(density) { 50.dp.toPx() }

                    // Outer Bezel Ring
                    drawCircle(
                        color = colors.border,
                        radius = radius - with(density) { 2.dp.toPx() },
                        style = Stroke(width = with(density) { 1.5.dp.toPx() })
                    )

                    // Dial Background Surface
                    drawCircle(
                        color = colors.chipBg.copy(alpha = 0.5f),
                        radius = radius - with(density) { 3.dp.toPx() }
                    )

                    // Radial Clock Ticks
                    for (i in 0 until totalNps) {
                        val angleDeg = (i.toFloat() / totalNps.toFloat()) * 360f - 90f
                        val angleRad = Math.toRadians(angleDeg.toDouble())
                        val cosA = cos(angleRad).toFloat()
                        val sinA = sin(angleRad).toFloat()

                        val isSel = i == selectedIndex
                        val p1 = Offset(centerPx + outerTickR * cosA, centerPx + outerTickR * sinA)
                        val p2 = Offset(centerPx + innerTickR * cosA, centerPx + innerTickR * sinA)

                        if (isSel) {
                            // Active glowing notch / needle indicator
                            drawLine(
                                color = colors.accent,
                                start = Offset(centerPx + (innerTickR - with(density) { 6.dp.toPx() }) * cosA, centerPx + (innerTickR - with(density) { 6.dp.toPx() }) * sinA),
                                end = p1,
                                strokeWidth = with(density) { 3.5.dp.toPx() },
                                cap = StrokeCap.Round
                            )
                            // Outer indicator dot
                            drawCircle(
                                color = colors.accent,
                                radius = with(density) { 3.5.dp.toPx() },
                                center = p1
                            )
                        } else {
                            drawLine(
                                color = colors.border,
                                start = p2,
                                end = p1,
                                strokeWidth = with(density) { 1.2.dp.toPx() },
                                cap = StrokeCap.Round
                            )
                        }
                    }

                    // Center Hub Background
                    drawCircle(
                        color = colors.surface,
                        radius = hubR
                    )
                    drawCircle(
                        color = colors.border,
                        radius = hubR,
                        style = Stroke(width = with(density) { 1.5.dp.toPx() })
                    )
                }

                // Dial Labels Placed Radially Around the Clock Face
                val labelRadiusDp = 78.dp
                for ((idx, nps) in availableNps.withIndex()) {
                    val angleDeg = (idx.toFloat() / totalNps.toFloat()) * 360f - 90f
                    val angleRad = Math.toRadians(angleDeg.toDouble())
                    val xOffset = labelRadiusDp * cos(angleRad).toFloat()
                    val yOffset = labelRadiusDp * sin(angleRad).toFloat()

                    val isSel = idx == selectedIndex
                    val shouldDisplay = totalNps <= 12 || isSel || (idx % 2 == 0)

                    if (shouldDisplay) {
                        Box(
                            modifier = Modifier
                                .offset(x = xOffset, y = yOffset)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSel) colors.accent.copy(alpha = 0.2f) else Color.Transparent)
                                .padding(horizontal = 3.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = nps.replace("\"", ""),
                                color = if (isSel) colors.accent else colors.textMuted,
                                fontSize = (if (isSel) 12 * fontScale else 10 * fontScale).sp,
                                fontWeight = if (isSel) FontWeight.Black else FontWeight.Medium,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // ============================================================
                // KADRAN GÖBEĞİ (CENTER HUB - READOUT)
                // ============================================================
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            if (primaryOptions.isNotEmpty()) {
                                val curIdx = primaryOptions.indexOf(selectedPrimary)
                                val nextIdx = if (curIdx < primaryOptions.lastIndex) curIdx + 1 else 0
                                onPrimaryChange(primaryOptions[nextIdx])
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = selectedNps,
                        color = colors.accent,
                        fontSize = (18 * fontScale).sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = (18 * fontScale).sp
                    )
                    Text(
                        text = "DN $dn",
                        color = colors.textSecondary,
                        fontSize = (10 * fontScale).sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = (11 * fontScale).sp
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .width(42.dp)
                            .padding(vertical = 3.dp),
                        color = colors.border,
                        thickness = 1.dp
                    )

                    Text(
                        text = if (selectedSecondary != null) "$selectedPrimary $selectedSecondary" else selectedPrimary,
                        color = colors.textPrimary,
                        fontSize = (11 * fontScale).sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = (12 * fontScale).sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ====================================================================
        // CONNECTED PRECISION SEGMENTED CONTROL: SCHEDULE / RATING
        // ====================================================================
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = primaryLabel,
                        color = colors.textSecondary,
                        fontSize = (11.5 * fontScale).sp,
                        fontWeight = FontWeight.Bold
                    )
                    val helpTopic = if (primaryLabel.contains("Schedule", ignoreCase = true) || primaryLabel.contains("Et", ignoreCase = true)) "pipes" else "rating"
                    HelpQuestionButton(
                        onClick = { onOpenHelp(helpTopic) },
                        fontScale = fontScale
                    )
                }

                Text(
                    text = selectedPrimary,
                    color = colors.accent,
                    fontSize = (12 * fontScale).sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            if (primaryOptions.size <= 7) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.chipBg)
                        .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                ) {
                    primaryOptions.forEachIndexed { idx, opt ->
                        val isSel = selectedPrimary == opt
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onPrimaryChange(opt) }
                                .background(if (isSel) colors.accent else Color.Transparent)
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = opt,
                                color = if (isSel) colors.accentText else colors.textPrimary,
                                fontSize = (11.5 * fontScale).sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                        if (idx < primaryOptions.lastIndex && !isSel && selectedPrimary != primaryOptions[idx + 1]) {
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp)
                                    .align(Alignment.CenterVertically)
                                    .background(colors.border)
                            )
                        }
                    }
                }
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.chipBg)
                        .border(1.dp, colors.border, RoundedCornerShape(8.dp)),
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    items(primaryOptions) { opt ->
                        val isSel = selectedPrimary == opt
                        Box(
                            modifier = Modifier
                                .clickable { onPrimaryChange(opt) }
                                .background(if (isSel) colors.accent else Color.Transparent)
                                .padding(horizontal = 13.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = opt,
                                color = if (isSel) colors.accentText else colors.textPrimary,
                                fontSize = (11.5 * fontScale).sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // ====================================================================
        // CONNECTED PRECISION SEGMENTED CONTROL: SECONDARY (FACING: RF / RTJ)
        // ====================================================================
        if (secondaryOptions.isNotEmpty() && onSecondaryChange != null && selectedSecondary != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = secondaryLabel ?: if (isTr) "Yüzey Tipi (Facing)" else "Facing Type",
                        color = colors.textSecondary,
                        fontSize = (11.5 * fontScale).sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = selectedSecondary,
                        color = colors.accent,
                        fontSize = (12 * fontScale).sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.chipBg)
                        .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                ) {
                    secondaryOptions.forEachIndexed { idx, opt ->
                        val isSel = selectedSecondary == opt
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onSecondaryChange(opt) }
                                .background(if (isSel) colors.accent else Color.Transparent)
                                .padding(vertical = 7.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = opt,
                                color = if (isSel) colors.accentText else colors.textPrimary,
                                fontSize = (11.5 * fontScale).sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                        if (idx < secondaryOptions.lastIndex && !isSel && selectedSecondary != secondaryOptions[idx + 1]) {
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(20.dp)
                                    .align(Alignment.CenterVertically)
                                    .background(colors.border)
                            )
                        }
                    }
                }
            }
        }
    }
}