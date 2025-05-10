package ua.wied.presentation.screens.profile

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

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val observeSettings: ObserveSettingsUseCase,
    private val setLanguage: SetLanguageUseCase,
    private val setDarkTheme: SetDarkThemeEnabledUseCase
): BaseViewModel<ProfileState, ProfileEvent>(ProfileState()) {


    override fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.ChangeTheme -> changeTheme(event.theme)
            is ProfileEvent.ChangeLanguage -> changeLanguage(event.language)
        }
    }

    init {
        loadSettings()
        loadUser()
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
                        settings = settings
                    ) }
                }
            }
        )
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