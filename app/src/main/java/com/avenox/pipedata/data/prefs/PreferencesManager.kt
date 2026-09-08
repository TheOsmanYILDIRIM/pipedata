package com.avenox.pipedata.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.avenox.pipedata.data.model.AppLanguage
import com.avenox.pipedata.data.model.CadTheme
import com.avenox.pipedata.data.model.UnitSystem

data class CategorySelection(
    val subtypeIndex: Int,
    val nps: String,
    val primary: String,
    val secondary: String?
)

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("pipedata_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_UNIT_SYSTEM = "key_unit_system"
        private const val KEY_CAD_THEME = "key_cad_theme"
        private const val KEY_APP_LANGUAGE = "key_app_language"
        private const val KEY_FONT_SCALE = "key_font_scale"
        private const val KEY_HIGH_CONTRAST = "key_high_contrast"
        private const val KEY_LAST_CATEGORY = "key_last_category"
    }

    // Default is IMPERIAL as requested: "Varsayılan inch olacak"
    var unitSystem: UnitSystem
        get() {
            val name = prefs.getString(KEY_UNIT_SYSTEM, UnitSystem.IMPERIAL.name)
            return try {
                UnitSystem.valueOf(name ?: UnitSystem.IMPERIAL.name)
            } catch (e: Exception) {
                UnitSystem.IMPERIAL
            }
        }
        set(value) {
            prefs.edit().putString(KEY_UNIT_SYSTEM, value.name).apply()
        }

    var cadTheme: CadTheme
        get() {
            val name = prefs.getString(KEY_CAD_THEME, CadTheme.CAD_DARK.name)
            return try {
                CadTheme.valueOf(name ?: CadTheme.CAD_DARK.name)
            } catch (e: Exception) {
                CadTheme.CAD_DARK
            }
        }
        set(value) {
            prefs.edit().putString(KEY_CAD_THEME, value.name).apply()
        }

    var appLanguage: AppLanguage
        get() {
            val name = prefs.getString(KEY_APP_LANGUAGE, AppLanguage.EN.name)
            return try {
                AppLanguage.valueOf(name ?: AppLanguage.EN.name)
            } catch (e: Exception) {
                AppLanguage.EN
            }
        }
        set(value) {
            prefs.edit().putString(KEY_APP_LANGUAGE, value.name).apply()
        }

    var fontScale: Float
        get() = prefs.getFloat(KEY_FONT_SCALE, 1.0f)
        set(value) {
            prefs.edit().putFloat(KEY_FONT_SCALE, value).apply()
        }

    var highContrast: Boolean
        get() = prefs.getBoolean(KEY_HIGH_CONTRAST, false)
        set(value) {
            prefs.edit().putBoolean(KEY_HIGH_CONTRAST, value).apply()
        }

    var lastCategoryId: String
        get() = prefs.getString(KEY_LAST_CATEGORY, "flanges") ?: "flanges"
        set(value) {
            prefs.edit().putString(KEY_LAST_CATEGORY, value).apply()
        }

    fun getCategorySelection(
        categoryId: String,
        defaultSubtype: Int,
        defaultNps: String,
        defaultPrimary: String,
        defaultSecondary: String?
    ): CategorySelection {
        val subtype = prefs.getInt("${categoryId}_subtype", defaultSubtype)
        val nps = prefs.getString("${categoryId}_nps", defaultNps) ?: defaultNps
        val primary = prefs.getString("${categoryId}_primary", defaultPrimary) ?: defaultPrimary
        val hasSecondaryKey = "${categoryId}_has_secondary"
        val secondary = if (prefs.contains(hasSecondaryKey)) {
            if (prefs.getBoolean(hasSecondaryKey, false)) {
                prefs.getString("${categoryId}_secondary", defaultSecondary)
            } else {
                null
            }
        } else {
            defaultSecondary
        }
        return CategorySelection(subtype, nps, primary, secondary)
    }

    fun saveCategorySelection(
        categoryId: String,
        subtypeIndex: Int,
        nps: String,
        primary: String,
        secondary: String?
    ) {
        prefs.edit().apply {
            putInt("${categoryId}_subtype", subtypeIndex)
            putString("${categoryId}_nps", nps)
            putString("${categoryId}_primary", primary)
            if (secondary != null) {
                putBoolean("${categoryId}_has_secondary", true)
                putString("${categoryId}_secondary", secondary)
            } else {
                putBoolean("${categoryId}_has_secondary", false)
                remove("${categoryId}_secondary")
            }
            apply()
        }
    }
}
