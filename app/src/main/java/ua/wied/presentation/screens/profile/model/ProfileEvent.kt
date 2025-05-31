package ua.wied.presentation.screens.profile.model

import android.content.Context
import ua.wied.domain.models.settings.Language
import ua.wied.domain.models.settings.Theme

sealed class ProfileEvent {
    data class ChangeTheme(val theme: Theme): ProfileEvent()
    data class ChangeLanguage(val language: Language): ProfileEvent()
    data class Logout(val context: Context): ProfileEvent()
}