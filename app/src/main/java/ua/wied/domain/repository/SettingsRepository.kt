package ua.wied.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.wied.domain.models.settings.Language
import ua.wied.domain.models.settings.Settings

interface SettingsRepository {
    suspend fun getLanguage(): Language
    suspend fun setLanguage(language: Language)

    suspend fun isDarkThemeEnabled(): Boolean?
    suspend fun setDarkThemeEnabled(isEnabled: Boolean)

    suspend fun getSettings(): Settings
    suspend fun getSettingsFlow(): Flow<Settings>
}