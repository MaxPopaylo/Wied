package ua.wied.presentation.screens.main

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.main.models.MainState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
): BaseViewModel<MainState, MainEvent>(MainState()) {
    override fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.InstructionEditingChanged -> updateState { it.copy(isInstructionEditing = event.value) }
            is MainEvent.ElementEditingChanged -> updateState { it.copy(isElementEditing = event.value) }
            is MainEvent.FabVisibilityChanged -> updateState { it.copy(isFabVisible = event.value) }
            is MainEvent.FabClickChanged -> updateState { it.copy(fabClick = event.value) }
        }
    }
}