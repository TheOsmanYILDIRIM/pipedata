package com.avenox.pipedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.avenox.pipedata.data.db.PipedataDbHelper
import com.avenox.pipedata.data.model.AppLanguage
import com.avenox.pipedata.data.model.CadTheme
import com.avenox.pipedata.data.model.UnitSystem
import com.avenox.pipedata.ui.components.HelpBottomSheet
import com.avenox.pipedata.ui.components.SettingsBottomSheet
import com.avenox.pipedata.ui.screens.FlangeDetailScreen
import com.avenox.pipedata.ui.screens.TopicListScreen
import com.avenox.pipedata.ui.theme.LocalCadColors
import com.avenox.pipedata.ui.theme.PipedataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dbHelper = PipedataDbHelper(this)
        val categories = dbHelper.getFlangeCategories()
        val flangeTypes = dbHelper.getFlangeTypes()
        val helpGlossary = dbHelper.getHelpGlossary()

        setContent {
            var currentTheme by remember { mutableStateOf(CadTheme.CAD_DARK) }
            var currentUnit by remember { mutableStateOf(UnitSystem.METRIC) }
            var currentLang by remember { mutableStateOf(AppLanguage.EN) }
            var fontScale by remember { mutableFloatStateOf(1.0f) }
            var highContrast by remember { mutableStateOf(false) }

            // Navigation state
            var currentScreen by remember { mutableStateOf("catalog") }

            // Flange state
            var currentTypeIndex by remember { mutableIntStateOf(0) }
            var selectedNps by remember { mutableStateOf("2\"") }
            var selectedRating by remember { mutableIntStateOf(150) }
            var selectedFacing by remember { mutableStateOf("RF") }

            // Modals
            var activeHelpTopicId by remember { mutableStateOf<String?>(null) }
            var isSettingsOpen by remember { mutableStateOf(false) }

            val currentSpec = remember(selectedNps, selectedRating, selectedFacing) {
                dbHelper.getFlangeSpec(selectedNps, selectedRating, selectedFacing)
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
                                currentTheme = currentTheme,
                                onThemeChange = { currentTheme = it },
                                currentUnit = currentUnit,
                                onUnitToggle = {
                                    currentUnit = if (currentUnit == UnitSystem.METRIC) UnitSystem.IMPERIAL else UnitSystem.METRIC
                                },
                                fontScale = fontScale,
                                currentLang = currentLang,
                                onOpenSettings = { isSettingsOpen = true },
                                onSelectCategory = { cat ->
                                    if (cat.id == "flanges") {
                                        currentScreen = "detail"
                                    }
                                }
                            )
                        }
                        "detail" -> {
                            FlangeDetailScreen(
                                flangeTypes = flangeTypes,
                                currentTypeIndex = currentTypeIndex,
                                onTypeChange = { currentTypeIndex = it },
                                selectedNps = selectedNps,
                                onNpsChange = { selectedNps = it },
                                selectedRating = selectedRating,
                                onRatingChange = { selectedRating = it },
                                selectedFacing = selectedFacing,
                                onFacingChange = { selectedFacing = it },
                                spec = currentSpec,
                                currentUnit = currentUnit,
                                fontScale = fontScale,
                                currentLang = currentLang,
                                onOpenHelp = { topicId -> activeHelpTopicId = topicId },
                                onBack = { currentScreen = "catalog" }
                            )
                        }
                    }

                    // Settings Bottom Sheet
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
