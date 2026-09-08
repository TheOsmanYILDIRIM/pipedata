package com.avenox.pipedata.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicListScreen(
    categories: List<TopicCategory>,
    currentTheme: CadTheme,
    onThemeChange: (CadTheme) -> Unit,
    currentUnit: UnitSystem,
    onUnitToggle: () -> Unit,
    fontScale: Float,
    currentLang: AppLanguage,
    onOpenSettings: () -> Unit,
    onSelectCategory: (TopicCategory) -> Unit
) {
    val colors = LocalCadColors.current
    val isTr = currentLang == AppLanguage.TR
    var searchQuery by remember { mutableStateOf("") }
    var searchVisible by remember { mutableStateOf(false) }

    val filteredCategories = remember(categories, searchQuery, currentLang) {
        if (searchQuery.isBlank()) categories
        else categories.filter {
            val title = if (isTr) it.titleTr else it.titleEn
            val desc = if (isTr) it.descTr else it.descEn
            title.contains(searchQuery, ignoreCase = true) ||
                    desc.contains(searchQuery, ignoreCase = true) ||
                    it.std.contains(searchQuery, ignoreCase = true)
        }
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
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pipedata",
                        color = colors.textPrimary,
                        fontSize = (20 * fontScale).sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.5).sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Unit badge button
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

                        // Search toggle
                        IconButton(
                            onClick = { searchVisible = !searchVisible },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = colors.textSecondary
                            )
                        }

                        // Settings button
                        IconButton(
                            onClick = onOpenSettings,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = colors.textSecondary
                            )
                        }
                    }
                }

                if (searchVisible) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                if (isTr) "Bileşen filtrele..." else "Filter components...",
                                color = colors.textMuted,
                                fontSize = (13 * fontScale).sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border,
                            focusedContainerColor = colors.background,
                            unfocusedContainerColor = colors.background,
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                // Theme quick bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    CadTheme.values().forEach { theme ->
                        val selected = currentTheme == theme
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (selected) colors.accent else colors.chipBg)
                                .border(1.dp, if (selected) colors.accent else colors.border, RoundedCornerShape(6.dp))
                                .clickable { onThemeChange(theme) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = theme.title,
                                color = if (selected) colors.accentText else colors.textSecondary,
                                fontSize = (10.5 * fontScale).sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                Divider(color = colors.border, thickness = 1.dp)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredCategories, key = { it.id }) { cat ->
                val title = if (isTr) cat.titleTr else cat.titleEn
                val desc = if (isTr) cat.descTr else cat.descEn
                val context = LocalContext.current

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onSelectCategory(cat) },
                    colors = CardDefaults.cardColors(containerColor = colors.surface),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(colors.border)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Technical Drawing Box
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(colors.cadBg)
                                .border(1.dp, colors.border, RoundedCornerShape(10.dp))
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data("file:///android_asset/svg/${cat.svgAsset}")
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build(),
                                contentDescription = title,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        // Info Column
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = title,
                                    color = colors.textPrimary,
                                    fontSize = (15 * fontScale).sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Box(
                                    modifier = Modifier
                                        .background(colors.chipBg, RoundedCornerShape(4.dp))
                                        .border(1.dp, colors.border, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = cat.std,
                                        color = colors.accent,
                                        fontSize = (10 * fontScale).sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = desc,
                                color = colors.textMuted,
                                fontSize = (12 * fontScale).sp,
                                maxLines = 2,
                                lineHeight = (16 * fontScale).sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = colors.textMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
