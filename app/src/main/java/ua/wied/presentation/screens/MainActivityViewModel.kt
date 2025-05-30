package ua.wied.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.wied.domain.models.settings.Settings
import ua.wied.domain.usecases.ObserveSettingsUseCase
import javax.inject.Inject
import ua.wied.domain.usecases.SetDarkThemeEnabledUseCase

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val observeSettings: ObserveSettingsUseCase,
    private val setDarkThemeEnabledUseCase: SetDarkThemeEnabledUseCase
) : ViewModel() {

    private val _settings = MutableStateFlow(Settings.getDefaultSettings())
    val settings: StateFlow<Settings> = _settings.asStateFlow()

    init {
        loadSettings()
    }

    fun setDarkTheme(value: Boolean) {
        viewModelScope.launch {
            setDarkThemeEnabledUseCase(value)
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            observeSettings()
                .collect { newSettings ->
                    _settings.update { newSettings }
                }
        }
    }
}