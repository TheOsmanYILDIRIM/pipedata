package com.avenox.pipedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.memory.MemoryCache
import com.avenox.pipedata.data.db.PipedataDbHelper
import com.avenox.pipedata.data.model.AppLanguage
import com.avenox.pipedata.data.model.CadTheme
import com.avenox.pipedata.data.model.TopicCategory
import com.avenox.pipedata.data.model.UnitSystem
import com.avenox.pipedata.ui.components.HelpBottomSheet
import com.avenox.pipedata.ui.components.SettingsBottomSheet
import com.avenox.pipedata.ui.screens.ComponentDetailScreen
import com.avenox.pipedata.ui.screens.TopicListScreen
import com.avenox.pipedata.ui.theme.LocalCadColors
import com.avenox.pipedata.ui.theme.PipedataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configure high-performance memory-cached ImageLoader for SVGs
        val imageLoader = ImageLoader.Builder(this)
            .components { add(SvgDecoder.Factory()) }
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .crossfade(false)
            .build()
        Coil.setImageLoader(imageLoader)

        val dbHelper = PipedataDbHelper(applicationContext)
        val categories = dbHelper.getCategoryList()
        val helpGlossary = dbHelper.getHelpGlossary()

        setContent {
            var currentTheme by remember { mutableStateOf(CadTheme.CAD_DARK) }
            var currentUnit by remember { mutableStateOf(UnitSystem.METRIC) }
            var currentLang by remember { mutableStateOf(AppLanguage.EN) }
            var fontScale by remember { mutableFloatStateOf(1.0f) }
            var highContrast by remember { mutableStateOf(false) }

            // Navigation state
            var currentScreen by remember { mutableStateOf("catalog") }
            var selectedCategory by remember { mutableStateOf(categories.first()) }

            // Component state
            var currentSubtypeIndex by remember { mutableIntStateOf(0) }
            var selectedNps by remember { mutableStateOf("2\"") }
            var selectedRatingOrSch by remember { mutableStateOf("150#") }
            var selectedFacing by remember { mutableStateOf("RF") }

            // Modals
            var activeHelpTopicId by remember { mutableStateOf<String?>(null) }
            var isSettingsOpen by remember { mutableStateOf(false) }

            val availableNps = remember(selectedCategory) {
                dbHelper.getAvailableNps(selectedCategory.id)
            }
            val availableRatingsOrSch = remember(selectedCategory) {
                dbHelper.getAvailableRatingsOrSch(selectedCategory.id)
            }

            val currentSpecs = remember(selectedCategory, currentSubtypeIndex, selectedNps, selectedRatingOrSch, selectedFacing) {
                val subtype = selectedCategory.defaultSubtypes.getOrElse(currentSubtypeIndex) {
                    selectedCategory.defaultSubtypes.first()
                }
                dbHelper.getCategorySpecs(
                    categoryId = selectedCategory.id,
                    subtypeId = subtype.id,
                    nps = selectedNps,
                    ratingOrSch = selectedRatingOrSch,
                    facing = selectedFacing
                )
            }

            PipedataTheme(
                cadTheme = currentTheme,
                highContrast = highContrast
            ) {
                val colors = LocalCadColors.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    when (currentScreen) {
                        "catalog" -> {
                            TopicListScreen(
                                categories = categories,
                                currentUnit = currentUnit,
                                onUnitToggle = {
                                    currentUnit = if (currentUnit == UnitSystem.METRIC) UnitSystem.IMPERIAL else UnitSystem.METRIC
                                },
                                fontScale = fontScale,
                                currentLang = currentLang,
                                onOpenSettings = { isSettingsOpen = true },
                                onSelectCategory = { cat ->
                                    selectedCategory = cat
                                    currentSubtypeIndex = 0
                                    val ratings = dbHelper.getAvailableRatingsOrSch(cat.id)
                                    selectedRatingOrSch = ratings.firstOrNull() ?: "150#"
                                    currentScreen = "detail"
                                }
                            )
                        }
                        "detail" -> {
                            ComponentDetailScreen(
                                category = selectedCategory,
                                currentSubtypeIndex = currentSubtypeIndex,
                                onSubtypeChange = { currentSubtypeIndex = it },
                                availableNps = availableNps,
                                selectedNps = selectedNps,
                                onNpsChange = { selectedNps = it },
                                availableRatingsOrSch = availableRatingsOrSch,
                                selectedRatingOrSch = selectedRatingOrSch,
                                onRatingOrSchChange = { selectedRatingOrSch = it },
                                selectedFacing = selectedFacing,
                                onFacingChange = { selectedFacing = it },
                                specs = currentSpecs,
                                currentUnit = currentUnit,
                                fontScale = fontScale,
                                currentLang = currentLang,
                                onOpenHelp = { topicId -> activeHelpTopicId = topicId },
                                onBack = { currentScreen = "catalog" }
                            )
                        }
                    }

                    // Settings Bottom Sheet (Theme ONLY changes here!)
                    if (isSettingsOpen) {
                        SettingsBottomSheet(
                            currentTheme = currentTheme,
                            onThemeChange = { currentTheme = it },
                            currentUnit = currentUnit,
                            onUnitChange = { currentUnit = it },
                            fontScale = fontScale,
                            onFontScaleChange = { fontScale = it },
                            highContrast = highContrast,
                            onHighContrastChange = { highContrast = it },
                            currentLang = currentLang,
                            onLangChange = { currentLang = it },
                            onDismiss = { isSettingsOpen = false }
                        )
                    }

                    // Technical Help Glossary Bottom Sheet
                    activeHelpTopicId?.let { topicId ->
                        val topic = helpGlossary[topicId]
                        if (topic != null) {
                            HelpBottomSheet(
                                topic = topic,
                                language = currentLang,
                                fontScale = fontScale,
                                onDismiss = { activeHelpTopicId = null }
                            )
                        }
                    }
                }
            }
        }
    }
}
