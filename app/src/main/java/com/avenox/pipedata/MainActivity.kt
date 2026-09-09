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
import com.avenox.pipedata.data.db.CsvDataStore
import com.avenox.pipedata.data.model.*
import com.avenox.pipedata.data.prefs.PreferencesManager
import com.avenox.pipedata.ui.components.HelpBottomSheet
import com.avenox.pipedata.ui.components.SettingsBottomSheet
import com.avenox.pipedata.ui.screens.ComponentDetailScreen
import com.avenox.pipedata.ui.screens.TopicListScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.activity.compose.BackHandler
import com.avenox.pipedata.ui.components.OrbitalSplashScreen
import com.avenox.pipedata.ui.theme.LocalCadColors
import com.avenox.pipedata.ui.theme.PipedataTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dbHelper = CsvDataStore(this)
        val categories = dbHelper.getFlangeCategories()
        val helpGlossary = dbHelper.getHelpGlossary()

        // Asynchronous database copy to prevent any UI freeze or Choreographer lag
        lifecycleScope.launch(Dispatchers.IO) {
            dbHelper.ensureDatabaseAsync()
        }

        setContent {
            val prefsManager = remember { PreferencesManager(this@MainActivity) }
            var currentTheme by remember { mutableStateOf(prefsManager.cadTheme) }
            var currentUnit by remember { mutableStateOf(prefsManager.unitSystem) }
            var currentLang by remember { mutableStateOf(prefsManager.appLanguage) }
            var fontScale by remember { mutableFloatStateOf(prefsManager.fontScale) }
            var highContrast by remember { mutableStateOf(prefsManager.highContrast) }

            val onUnitToggle = {
                val newUnit = if (currentUnit == UnitSystem.METRIC) UnitSystem.IMPERIAL else UnitSystem.METRIC
                currentUnit = newUnit
                prefsManager.unitSystem = newUnit
            }

            // Navigation state
            var currentScreen by remember { mutableStateOf("catalog") }
            val initialCategory = remember {
                val lastId = prefsManager.lastCategoryId
                categories.find { it.id == lastId } ?: categories.first()
            }
            var selectedCategory by remember { mutableStateOf(initialCategory) }

            val initialSelection = remember(selectedCategory.id) {
                prefsManager.getCategorySelection(
                    categoryId = selectedCategory.id,
                    defaultSubtype = 0,
                    defaultNps = "2\"",
                    defaultPrimary = if (selectedCategory.id == "pipes") "40" else if (selectedCategory.id == "fittings_bw") "STD" else if (selectedCategory.id == "fittings_sw") "3000#" else "150#",
                    defaultSecondary = if (selectedCategory.id == "flanges") "RF" else null
                )
            }

            // Component selection state
            var currentSubtypeIndex by remember { mutableIntStateOf(initialSelection.subtypeIndex) }
            var selectedNps by remember { mutableStateOf(initialSelection.nps) }
            var selectedPrimary by remember { mutableStateOf(initialSelection.primary) }
            var selectedSecondary by remember { mutableStateOf<String?>(initialSelection.secondary) }

            // Modals & Splash
            var showSplash by remember { mutableStateOf(true) }
            var activeHelpTopicId by remember { mutableStateOf<String?>(null) }
            var isSettingsOpen by remember { mutableStateOf(false) }

            // Handle Android hardware/gesture Back Button
            BackHandler(enabled = showSplash || currentScreen == "detail" || isSettingsOpen || activeHelpTopicId != null) {
                if (showSplash) {
                    showSplash = false
                } else if (activeHelpTopicId != null) {
                    activeHelpTopicId = null
                } else if (isSettingsOpen) {
                    isSettingsOpen = false
                } else if (currentScreen == "detail") {
                    currentScreen = "catalog"
                }
            }

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

            LaunchedEffect(currentData) {
                if (selectedPrimary != currentData.selectedPrimary) {
                    selectedPrimary = currentData.selectedPrimary
                }
                if (selectedSecondary != currentData.selectedSecondary) {
                    selectedSecondary = currentData.selectedSecondary
                }
                if (selectedNps != currentData.selectedNps) {
                    selectedNps = currentData.selectedNps
                }
                prefsManager.saveCategorySelection(
                    categoryId = selectedCategory.id,
                    subtypeIndex = currentSubtypeIndex,
                    nps = currentData.selectedNps,
                    primary = currentData.selectedPrimary,
                    secondary = currentData.selectedSecondary
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
                    if (showSplash) {
                        OrbitalSplashScreen(
                            colors = colors,
                            fontScale = fontScale,
                            isTr = currentLang == AppLanguage.TR,
                            onSplashFinished = { showSplash = false }
                        )
                    } else {
                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = {
                                if (targetState == "detail") {
                                    (slideInHorizontally(
                                        initialOffsetX = { fullWidth -> (fullWidth * 0.18f).toInt() },
                                        animationSpec = tween(durationMillis = 180, easing = FastOutSlowInEasing)
                                    ) + fadeIn(animationSpec = tween(150)))
                                    .togetherWith(
                                        slideOutHorizontally(
                                            targetOffsetX = { fullWidth -> (-fullWidth * 0.12f).toInt() },
                                            animationSpec = tween(durationMillis = 180, easing = FastOutSlowInEasing)
                                        ) + fadeOut(animationSpec = tween(120))
                                    )
                                } else {
                                    (slideInHorizontally(
                                        initialOffsetX = { fullWidth -> (-fullWidth * 0.18f).toInt() },
                                        animationSpec = tween(durationMillis = 180, easing = FastOutSlowInEasing)
                                    ) + fadeIn(animationSpec = tween(150)))
                                    .togetherWith(
                                        slideOutHorizontally(
                                            targetOffsetX = { fullWidth -> (fullWidth * 0.12f).toInt() },
                                            animationSpec = tween(durationMillis = 180, easing = FastOutSlowInEasing)
                                        ) + fadeOut(animationSpec = tween(120))
                                    )
                                }
                            },
                            label = "ScreenTransition"
                        ) { screen ->
                            when (screen) {
                                "catalog" -> {
                                    TopicListScreen(
                                        categories = categories,
                                        currentUnit = currentUnit,
                                        onUnitToggle = onUnitToggle,
                                        fontScale = fontScale,
                                        currentLang = currentLang,
                                        onOpenSettings = { isSettingsOpen = true },
                                        onSelectCategory = { cat ->
                                            val defaultSub = 0
                                            val defaultN = "2\""
                                            val defaultP = when (cat.id) {
                                                "flanges" -> "150#"
                                                "pipes" -> "40"
                                                "fittings_bw" -> "STD"
                                                "fittings_sw" -> "3000#"
                                                "valves" -> "150#"
                                                "gaskets" -> "150#"
                                                "blanks" -> "150#"
                                                else -> "150#"
                                            }
                                            val defaultS = if (cat.id == "flanges") "RF" else null

                                            val saved = prefsManager.getCategorySelection(
                                                categoryId = cat.id,
                                                defaultSubtype = defaultSub,
                                                defaultNps = defaultN,
                                                defaultPrimary = defaultP,
                                                defaultSecondary = defaultS
                                            )

                                            selectedCategory = cat
                                            currentSubtypeIndex = saved.subtypeIndex
                                            selectedNps = saved.nps
                                            selectedPrimary = saved.primary
                                            selectedSecondary = saved.secondary
                                            prefsManager.lastCategoryId = cat.id
                                            currentScreen = "detail"
                                        }
                                    )
                                }
                                "detail" -> {
                                    ComponentDetailScreen(
                                        data = currentData,
                                        currentSubtypeIndex = currentSubtypeIndex,
                                        onSubtypeChange = {
                                            currentSubtypeIndex = it
                                            prefsManager.saveCategorySelection(
                                                categoryId = selectedCategory.id,
                                                subtypeIndex = it,
                                                nps = selectedNps,
                                                primary = selectedPrimary,
                                                secondary = selectedSecondary
                                            )
                                        },
                                        onNpsChange = {
                                            selectedNps = it
                                            prefsManager.saveCategorySelection(
                                                categoryId = selectedCategory.id,
                                                subtypeIndex = currentSubtypeIndex,
                                                nps = it,
                                                primary = selectedPrimary,
                                                secondary = selectedSecondary
                                            )
                                        },
                                        onPrimaryChange = {
                                            selectedPrimary = it
                                            prefsManager.saveCategorySelection(
                                                categoryId = selectedCategory.id,
                                                subtypeIndex = currentSubtypeIndex,
                                                nps = selectedNps,
                                                primary = it,
                                                secondary = selectedSecondary
                                            )
                                        },
                                        onSecondaryChange = if (selectedSecondary != null) {
                                            {
                                                selectedSecondary = it
                                                prefsManager.saveCategorySelection(
                                                    categoryId = selectedCategory.id,
                                                    subtypeIndex = currentSubtypeIndex,
                                                    nps = selectedNps,
                                                    primary = selectedPrimary,
                                                    secondary = it
                                                )
                                            }
                                        } else null,
                                        currentUnit = currentUnit,
                                        onUnitToggle = onUnitToggle,
                                        fontScale = fontScale,
                                        currentLang = currentLang,
                                        currentTheme = currentTheme,
                                        onOpenHelp = { topicId -> activeHelpTopicId = topicId },
                                        onBack = { currentScreen = "catalog" }
                                    )
                                }
                            }
                        }
                    }

                    // Settings Bottom Sheet (Sole place for changing themes)
                    if (isSettingsOpen) {
                        SettingsBottomSheet(
                            currentTheme = currentTheme,
                            onThemeChange = {
                                currentTheme = it
                                prefsManager.cadTheme = it
                            },
                            currentUnit = currentUnit,
                            onUnitChange = {
                                currentUnit = it
                                prefsManager.unitSystem = it
                            },
                            fontScale = fontScale,
                            onFontScaleChange = {
                                fontScale = it
                                prefsManager.fontScale = it
                            },
                            highContrast = highContrast,
                            onHighContrastChange = {
                                highContrast = it
                                prefsManager.highContrast = it
                            },
                            currentLang = currentLang,
                            onLangChange = {
                                currentLang = it
                                prefsManager.appLanguage = it
                            },
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
