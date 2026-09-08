package com.avenox.pipedata.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenox.pipedata.ui.theme.CadColorPalette
import kotlinx.coroutines.delay
import kotlin.math.*

/**
 * High-Precision Animated Orbital Splash Screen
 * Features a glowing CAD pipe core with concentric rotating orbital rings,
 * orbiting satellite nodes, dashed radar graduation ticks, and sleek engineering typography.
 */
@Composable
fun OrbitalSplashScreen(
    colors: CadColorPalette,
    fontScale: Float,
    isTr: Boolean,
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Auto-advance after 1.8 seconds or tap anywhere to skip
    LaunchedEffect(Unit) {
        delay(1800)
        onSplashFinished()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "OrbitalLoop")

    // Continuous orbital rotations at different velocities
    val rotationInner by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "InnerOrbit"
    )

    val rotationMiddle by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "MiddleOrbit"
    )

    val rotationOuter by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "OuterOrbit"
    )

    // Pulsing core breathing effect
    val corePulse by infiniteTransition.animateFloat(
        initialValue = 0.90f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "CorePulse"
    )

    // Progress bar animation for high-tech telemetry feel
    val progressAnim by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1700, easing = LinearOutSlowInEasing),
        label = "SplashProgress"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .clickable { onSplashFinished() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // ================================================================
            // ORBITAL CANVAS ENGINE
            // ================================================================
            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val accent = colors.accent
                    val dimColor = colors.border

                    // 1. Background subtle ambient glow around orbital field
                    drawCircle(
                        color = accent.copy(alpha = 0.05f * corePulse),
                        radius = 115.dp.toPx(),
                        center = center
                    )

                    // 2. Outer Orbit Ring (with CAD degree tick marks)
                    val rOuter = 100.dp.toPx()
                    drawCircle(
                        color = dimColor.copy(alpha = 0.5f),
                        radius = rOuter,
                        center = center,
                        style = Stroke(width = 1.2.dp.toPx())
                    )

                    // Degree ticks around outer orbit
                    val totalTicks = 36
                    for (i in 0 until totalTicks) {
                        val angleDeg = i * (360f / totalTicks)
                        val angleRad = Math.toRadians(angleDeg.toDouble())
                        val isMajor = i % 9 == 0
                        val tickLen = if (isMajor) 7.dp.toPx() else 3.5.dp.toPx()
                        val tickStart = Offset(
                            x = (center.x + (rOuter - tickLen / 2) * cos(angleRad)).toFloat(),
                            y = (center.y + (rOuter - tickLen / 2) * sin(angleRad)).toFloat()
                        )
                        val tickEnd = Offset(
                            x = (center.x + (rOuter + tickLen / 2) * cos(angleRad)).toFloat(),
                            y = (center.y + (rOuter + tickLen / 2) * sin(angleRad)).toFloat()
                        )
                        drawLine(
                            color = if (isMajor) accent.copy(alpha = 0.8f) else dimColor.copy(alpha = 0.6f),
                            start = tickStart,
                            end = tickEnd,
                            strokeWidth = if (isMajor) 1.5.dp.toPx() else 1.dp.toPx()
                        )
                    }

                    // Outer Orbit Satellite Node with trail
                    rotate(rotationOuter, pivot = center) {
                        drawArc(
                            color = accent.copy(alpha = 0.35f),
                            startAngle = 0f,
                            sweepAngle = -40f,
                            useCenter = false,
                            topLeft = Offset(center.x - rOuter, center.y - rOuter),
                            size = Size(rOuter * 2, rOuter * 2),
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawCircle(
                            color = accent,
                            radius = 3.5.dp.toPx(),
                            center = Offset(center.x + rOuter, center.y)
                        )
                    }

                    // 3. Middle Orbit Ring (Tilted elliptical look / dashed CAD arc)
                    val rMiddle = 72.dp.toPx()
                    drawCircle(
                        color = accent.copy(alpha = 0.25f),
                        radius = rMiddle,
                        center = center,
                        style = Stroke(
                            width = 1.2.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 8f))
                        )
                    )

                    // Middle Orbit Satellite Nodes (2 opposing nodes)
                    rotate(rotationMiddle, pivot = center) {
                        val node1 = Offset(center.x + rMiddle, center.y)
                        val node2 = Offset(center.x - rMiddle, center.y)

                        drawCircle(color = accent, radius = 4.dp.toPx(), center = node1)
                        drawCircle(color = colors.textPrimary, radius = 2.dp.toPx(), center = node1)

                        drawCircle(color = accent.copy(alpha = 0.7f), radius = 3.dp.toPx(), center = node2)
                    }

                    // 4. Inner Orbit Ring (Fast reverse orbit)
                    val rInner = 46.dp.toPx()
                    drawCircle(
                        color = dimColor.copy(alpha = 0.4f),
                        radius = rInner,
                        center = center,
                        style = Stroke(
                            width = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
                        )
                    )

                    rotate(rotationInner, pivot = center) {
                        drawCircle(
                            color = accent,
                            radius = 3.dp.toPx(),
                            center = Offset(center.x, center.y - rInner)
                        )
                    }

                    // 5. Central Glowing CAD Core (Pipe Hub & Crosshair)
                    val rCore = 22.dp.toPx() * corePulse
                    drawCircle(
                        color = colors.surface,
                        radius = rCore,
                        center = center
                    )
                    drawCircle(
                        color = accent,
                        radius = rCore,
                        center = center,
                        style = Stroke(width = 2.5.dp.toPx())
                    )
                    drawCircle(
                        color = accent.copy(alpha = 0.2f),
                        radius = rCore + 5.dp.toPx(),
                        center = center,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Center Crosshair lines (CAD alignment axis)
                    val crosshairLen = 12.dp.toPx()
                    drawLine(
                        color = accent.copy(alpha = 0.75f),
                        start = Offset(center.x - crosshairLen, center.y),
                        end = Offset(center.x + crosshairLen, center.y),
                        strokeWidth = 1.5.dp.toPx()
                    )
                    drawLine(
                        color = accent.copy(alpha = 0.75f),
                        start = Offset(center.x, center.y - crosshairLen),
                        end = Offset(center.x, center.y + crosshairLen),
                        strokeWidth = 1.5.dp.toPx()
                    )

                    // Center Core center dot
                    drawCircle(
                        color = accent,
                        radius = 2.5.dp.toPx(),
                        center = center
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ================================================================
            // BRANDING TYPOGRAPHY & TELEMETRY
            // ================================================================
            Text(
                text = "AVENOX PIPECAD",
                color = colors.textPrimary,
                fontSize = (22 * fontScale).sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.5.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (isTr) "Hassas Boru & Flanş CAD Motoru" else "Precision Piping CAD Engine",
                color = colors.accent,
                fontSize = (12 * fontScale).sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // High-tech telemetry loading bar
            Column(
                modifier = Modifier.width(180.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(1.5.dp))
                        .background(colors.border.copy(alpha = 0.4f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressAnim)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(1.5.dp))
                            .background(colors.accent)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isTr) "122 VEKTÖR ÇİZİMİ HAZIRLANIYOR..." else "INITIALIZING 122 CAD ASSETS...",
                    color = colors.textSecondary.copy(alpha = 0.6f),
                    fontSize = (9.5 * fontScale).sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}
