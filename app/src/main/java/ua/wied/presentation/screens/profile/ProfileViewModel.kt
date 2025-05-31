package ua.wied.presentation.screens.profile

import android.content.Context
import android.content.res.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.models.settings.Language
import ua.wied.domain.models.settings.Theme
import ua.wied.domain.usecases.GetUserUseCase
import ua.wied.domain.usecases.ObserveSettingsUseCase
import ua.wied.domain.usecases.SetDarkThemeEnabledUseCase
import ua.wied.domain.usecases.SetLanguageUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.profile.model.ProfileEvent
import ua.wied.presentation.screens.profile.model.ProfileState
import javax.inject.Inject
import ua.wied.domain.models.settings.Settings
import ua.wied.domain.usecases.LogoutUseCase

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val observeSettings: ObserveSettingsUseCase,
    private val setLanguage: SetLanguageUseCase,
    private val setDarkTheme: SetDarkThemeEnabledUseCase,
    private val logoutUseCase: LogoutUseCase
): BaseViewModel<ProfileState, ProfileEvent>(ProfileState()) {


    override fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.ChangeTheme -> changeTheme(event.theme)
            is ProfileEvent.ChangeLanguage -> changeLanguage(event.language)
            is ProfileEvent.Logout -> logout(event.context)
        }
    }

    init {
        loadSettings()
        loadUser()
    }

    private fun logout(context: Context) {
        collectLocalRequest(
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            call = {
                logoutUseCase(context)
            },
            callback = {
                updateState { it.copy(user = null) }
            }
        )
    }

    private fun changeTheme(theme: Theme) {
        collectLocalRequest(
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            call = {
                setDarkTheme(theme == Theme.DARK)
            },
            callback = {
                loadSettings()
            }
        )
    }

    private fun changeLanguage(language: Language) {
        collectLocalRequest(
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            call = { setLanguage(language) },
            callback = {
                loadSettings()
            }
        )
    }

    private fun loadSettings() {
        collectLocalRequest(
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            call = {
                observeSettings.invoke()
            },
            callback = { flow ->
                flow.collect { settings ->
                    updateState { it.copy(
                        settings = Settings(
                            language = configureLanguage(settings.language),
                            darkTheme = settings.darkTheme
                        )
                    ) }
                }
            }
        )
    }

    private fun configureLanguage(language: Language): Language {
        val currentLocale = Resources.getSystem().configuration.locales[0]
        return when (currentLocale.language) {
            "uk" -> Language.UKRAINIAN
            "ru" -> Language.RUSSIAN
            else -> language
        }
    }

    private fun loadUser() {
        collectLocalRequest(
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            call = {
                getUser()
            },
            callback = { user ->
                updateState { it.copy(
                    user = user,
                    isNotInternetConnection = (user == null)
                ) }
            }
        )
    }

}