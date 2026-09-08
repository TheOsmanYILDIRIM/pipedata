package com.avenox.pipedata.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avenox.pipedata.data.model.AppLanguage
import com.avenox.pipedata.data.model.CadTheme
import com.avenox.pipedata.data.model.UnitSystem
import com.avenox.pipedata.ui.theme.LocalCadColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    currentTheme: CadTheme,
    onThemeChange: (CadTheme) -> Unit,
    currentUnit: UnitSystem,
    onUnitChange: (UnitSystem) -> Unit,
    fontScale: Float,
    onFontScaleChange: (Float) -> Unit,
    highContrast: Boolean,
    onHighContrastChange: (Boolean) -> Unit,
    currentLang: AppLanguage,
    onLangChange: (AppLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    val colors = LocalCadColors.current
    val isTr = currentLang == AppLanguage.TR

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = colors.surface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .background(colors.border, RoundedCornerShape(2.dp))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isTr) "Ayarlar" else "Settings",
                    color = colors.textPrimary,
                    fontSize = (18 * fontScale).sp,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = colors.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1. Theme Selection
            Text(
                text = if (isTr) "Görünüm Teması" else "Display Theme",
                color = colors.textSecondary,
                fontSize = (12 * fontScale).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CadTheme.values().forEach { theme ->
                    val selected = currentTheme == theme
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selected) colors.accent else colors.chipBg)
                            .border(1.dp, if (selected) colors.accent else colors.border, RoundedCornerShape(8.dp))
                            .clickable { onThemeChange(theme) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = theme.title,
                            color = if (selected) colors.accentText else colors.textPrimary,
                            fontSize = (11 * fontScale).sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2. Unit System
            Text(
                text = if (isTr) "Birim Sistemi" else "Unit System",
                color = colors.textSecondary,
                fontSize = (12 * fontScale).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                UnitSystem.values().forEach { unit ->
                    val selected = currentUnit == unit
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selected) colors.accent else colors.chipBg)
                            .border(1.dp, if (selected) colors.accent else colors.border, RoundedCornerShape(8.dp))
                            .clickable { onUnitChange(unit) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = unit.label,
                            color = if (selected) colors.accentText else colors.textPrimary,
                            fontSize = (12 * fontScale).sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3. Text Size Stepper (- / +)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isTr) "Yazı Boyutu" else "Text Size (Font Scale)",
                        color = colors.textSecondary,
                        fontSize = (12 * fontScale).sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${(fontScale * 100).toInt()}%",
                        color = colors.accent,
                        fontSize = (14 * fontScale).sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Decrease button
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(colors.chipBg)
                            .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                            .clickable {
                                if (fontScale > 0.85f) onFontScaleChange(fontScale - 0.1f)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "−",
                            color = colors.textPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Increase button
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(colors.chipBg)
                            .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                            .clickable {
                                if (fontScale < 1.4f) onFontScaleChange(fontScale + 0.1f)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+",
                            color = colors.textPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 4. Language Selection
            Text(
                text = if (isTr) "Dil / Language" else "Language / Dil",
                color = colors.textSecondary,
                fontSize = (12 * fontScale).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppLanguage.values().forEach { lang ->
                    val selected = currentLang == lang
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selected) colors.accent else colors.chipBg)
                            .border(1.dp, if (selected) colors.accent else colors.border, RoundedCornerShape(8.dp))
                            .clickable { onLangChange(lang) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = lang.label,
                            color = if (selected) colors.accentText else colors.textPrimary,
                            fontSize = (12 * fontScale).sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. High Contrast Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isTr) "Yüksek Kontrast Metin" else "High Contrast Text",
                    color = colors.textPrimary,
                    fontSize = (13 * fontScale).sp,
                    fontWeight = FontWeight.SemiBold
                )
                Switch(
                    checked = highContrast,
                    onCheckedChange = onHighContrastChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.accentText,
                        checkedTrackColor = colors.accent,
                        uncheckedThumbColor = colors.textSecondary,
                        uncheckedTrackColor = colors.chipBg
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
