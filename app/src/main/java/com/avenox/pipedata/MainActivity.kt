package com.avenox.pipedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.avenox.pipedata.data.db.PipedataDbHelper
import com.avenox.pipedata.data.model.*
import com.avenox.pipedata.ui.components.HelpBottomSheet
import com.avenox.pipedata.ui.components.SettingsBottomSheet
import com.avenox.pipedata.ui.screens.ComponentDetailScreen
import com.avenox.pipedata.ui.screens.TopicListScreen
import com.avenox.pipedata.ui.theme.LocalCadColors
import com.avenox.pipedata.ui.theme.PipedataTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dbHelper = PipedataDbHelper(this)
        val categories = dbHelper.getFlangeCategories()
        val helpGlossary = dbHelper.getHelpGlossary()

        // Asynchronous database copy to prevent any UI freeze or Choreographer lag
        lifecycleScope.launch(Dispatchers.IO) {
            dbHelper.ensureDatabaseAsync()
        }

        setContent {
            var currentTheme by remember { mutableStateOf(CadTheme.CAD_DARK) }
            var currentUnit by remember { mutableStateOf(UnitSystem.METRIC) }
            var currentLang by remember { mutableStateOf(AppLanguage.EN) }
            var fontScale by remember { mutableFloatStateOf(1.0f) }
            var highContrast by remember { mutableStateOf(false) }

            // Navigation state
            var currentScreen by remember { mutableStateOf("catalog") }
            var selectedCategory by remember { mutableStateOf(categories.first()) }

            // Component selection state
            var currentSubtypeIndex by remember { mutableIntStateOf(0) }
            var selectedNps by remember { mutableStateOf("2\"") }
            var selectedPrimary by remember { mutableStateOf("150#") }
            var selectedSecondary by remember { mutableStateOf<String?>("RF") }

            // Modals
            var activeHelpTopicId by remember { mutableStateOf<String?>(null) }
            var isSettingsOpen by remember { mutableStateOf(false) }

            val currentData = remember(
                selectedCategory.id,
                currentSubtypeIndex,
                selectedNps,
                selectedPrimary,
                selectedSecondary
            ) {
                dbHelper.getComponentData(
                    categoryId = selectedCategory.id,
                    subtypeIndex = currentSubtypeIndex,
                    nps = selectedNps,
                    primarySel = selectedPrimary,
                    secondarySel = selectedSecondary
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
                                    selectedNps = "2\""
                                    when (cat.id) {
                                        "flanges" -> {
                                            selectedPrimary = "150#"
                                            selectedSecondary = "RF"
                                        }
                                        "pipes" -> {
                                            selectedPrimary = "40"
                                            selectedSecondary = null
                                        }
                                        "fittings_bw" -> {
                                            selectedPrimary = "STD"
                                            selectedSecondary = null
                                        }
                                        "fittings_sw" -> {
                                            selectedPrimary = "3000#"
                                            selectedSecondary = null
                                        }
                                        "valves" -> {
                                            selectedPrimary = "150#"
                                            selectedSecondary = null
                                        }
                                        "gaskets" -> {
                                            selectedPrimary = "150#"
                                            selectedSecondary = null
                                        }
                                        "blanks" -> {
                                            selectedPrimary = "150#"
                                            selectedSecondary = null
                                        }
                                        else -> {
                                            selectedPrimary = "150#"
                                            selectedSecondary = null
                                        }
                                    }
                                    currentScreen = "detail"
                                }
                            )
                        }
                        "detail" -> {
                            ComponentDetailScreen(
                                data = currentData,
                                currentSubtypeIndex = currentSubtypeIndex,
                                onSubtypeChange = { currentSubtypeIndex = it },
                                onNpsChange = { selectedNps = it },
                                onPrimaryChange = { selectedPrimary = it },
                                onSecondaryChange = if (selectedSecondary != null) { { selectedSecondary = it } } else null,
                                currentUnit = currentUnit,
                                fontScale = fontScale,
                                currentLang = currentLang,
                                onOpenHelp = { topicId -> activeHelpTopicId = topicId },
                                onBack = { currentScreen = "catalog" }
                            )
                        }
                    }

                    // Settings Bottom Sheet (Sole place for changing themes)
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

                    // Technical Glossary (?) Bottom Sheet
                    activeHelpTopicId?.let { topicId ->
                        val topic = helpGlossary[topicId] ?: helpGlossary["nps"]
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
