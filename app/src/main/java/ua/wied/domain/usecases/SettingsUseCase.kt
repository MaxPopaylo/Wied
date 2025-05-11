package ua.wied.domain.usecases

import kotlinx.coroutines.flow.Flow
import ua.wied.domain.models.settings.Language
import ua.wied.domain.models.settings.Settings
import ua.wied.domain.repository.SettingsRepository
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(): Language =
        repo.getLanguage()
}

class SetLanguageUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(language: Language) {
        repo.setLanguage(language)
    }
}

class IsDarkThemeEnabledUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(): Boolean? =
        repo.isDarkThemeEnabled()
}

class SetDarkThemeEnabledUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(isEnabled: Boolean) {
        repo.setDarkThemeEnabled(isEnabled)
    }
}

class GetSettingsUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(): Settings =
        repo.getSettings()
}

class ObserveSettingsUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(): Flow<Settings> =
        repo.getSettingsFlow()
}