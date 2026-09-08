package com.avenox.pipedata.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.avenox.pipedata.data.model.*
import com.avenox.pipedata.ui.theme.LocalCadColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicListScreen(
    categories: List<TopicCategory>,
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
                // Sleek Top App Bar: Brand on left, actions on right (NO THEMES HERE - ONLY IN SETTINGS)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PipeMatrix Pro",
                        color = colors.textPrimary,
                        fontSize = (18 * fontScale.coerceIn(0.85f, 1.15f)).sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 0.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        modifier = Modifier.wrapContentWidth(align = Alignment.End),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Unit badge button (mm / in)
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
                                fontSize = (12 * fontScale.coerceIn(0.85f, 1.2f)).sp,
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
                            .padding(horizontal = 16.dp, vertical = 6.dp),
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

                Divider(color = colors.border, thickness = 1.dp)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            items(filteredCategories, key = { it.id }) { cat ->
                val title = if (isTr) cat.titleTr else cat.titleEn
                val desc = if (isTr) cat.descTr else cat.descEn
                val context = LocalContext.current

                // Compact standard label (e.g. B16.5, B36.10M, B16.9)
                val cleanStd = cat.std.replace("ASME ", "").trim()

                // Cached ImageRequest to avoid re-decoding SVG on every scroll frame
                val imageRequest = remember(cat.svgAsset) {
                    ImageRequest.Builder(context)
                        .data("file:///android_asset/svg/${cat.svgAsset}")
                        .decoderFactory(SvgDecoder.Factory())
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .crossfade(false)
                        .build()
                }

                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val itemScale by animateFloatAsState(
                    targetValue = if (isPressed) 0.975f else 1.0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "ItemScale"
                )

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                scaleX = itemScale
                                scaleY = itemScale
                            }
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                interactionSource = interactionSource,
                                indication = ripple(color = colors.accent.copy(alpha = 0.2f)),
                                onClick = { onSelectCategory(cat) }
                            )
                            .padding(vertical = 10.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Compact 42x42dp Technical Icon Box
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(colors.surface)
                                .border(1.dp, colors.border, RoundedCornerShape(8.dp))
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = imageRequest,
                                contentDescription = title,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Text column: Title + Standard pill in 1 line; subtitle below
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = title,
                                    color = colors.textPrimary,
                                    fontSize = (15 * fontScale).sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                // Single-line standard badge (no wrapping, compact & elegant)
                                Box(
                                    modifier = Modifier
                                        .background(colors.chipBg, RoundedCornerShape(4.dp))
                                        .border(1.dp, colors.border, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = cleanStd,
                                        color = colors.accent,
                                        fontSize = (10.5 * fontScale).sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        softWrap = false
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = desc,
                                color = colors.textMuted,
                                fontSize = (12 * fontScale).sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = colors.textMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Clean thin separator line between items
                    Divider(
                        color = colors.border.copy(alpha = 0.5f),
                        thickness = 0.7.dp
                    )
                }
            }
        }
    }
}
