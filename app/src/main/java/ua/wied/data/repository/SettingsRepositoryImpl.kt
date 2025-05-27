package ua.wied.data.repository

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import ua.wied.data.UserPreferencesConstants.LANGUAGE_KEY
import ua.wied.domain.models.settings.Language
import ua.wied.domain.repository.SettingsRepository
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ua.wied.data.UserPreferencesConstants.DARK_THEME_KEY
import ua.wied.domain.models.settings.Settings
import javax.inject.Inject

import ua.wied.data.UserPreferencesConstants.SHOW_AI_SUMMARY_KEY

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
) : SettingsRepository {

    override suspend fun getLanguage(): Language {
        val preferences = dataStore.data.first()
        val languageName = preferences[LANGUAGE_KEY] ?: Language.ENGLISH.name
        return Language.valueOf(languageName)
    }

    override suspend fun setLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.name
        }
        changeLanguage(context, language)
    }

    override suspend fun isDarkThemeEnabled(): Boolean? {
        val preferences = dataStore.data.first()
        return preferences[DARK_THEME_KEY]
    }

    override suspend fun setDarkThemeEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isEnabled
        }
    }

    override suspend fun getSettings(): Settings {
        val preferences = dataStore.data.first()
        val language = Language.valueOf(
            preferences[LANGUAGE_KEY] ?: Language.ENGLISH.name
        )
        val darkTheme = preferences[DARK_THEME_KEY]
        return Settings(
            language = language,
            darkTheme = darkTheme
        )
    }

    override suspend fun getSettingsFlow(): Flow<Settings> {
        return dataStore.data.map { preferences ->
            val language = Language.valueOf(
                preferences[LANGUAGE_KEY] ?: Language.ENGLISH.name
            )
            val darkTheme = preferences[DARK_THEME_KEY]
            Settings(
                language = language,
                darkTheme = darkTheme
            )
        }
    }

    private fun changeLanguage(context: Context, language: Language) {
        val languageCode = language.value

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeList = LocaleList.forLanguageTags(languageCode)
            context.getSystemService(LocaleManager::class.java).applicationLocales = localeList
        } else {
            val localeListCompat = LocaleListCompat.forLanguageTags(languageCode)
            AppCompatDelegate.setApplicationLocales(localeListCompat)
        }
    }
}
