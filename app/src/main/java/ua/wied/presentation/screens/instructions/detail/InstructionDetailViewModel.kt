package ua.wied.presentation.screens.instructions.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.detail.model.InstructionDetailEvent
import ua.wied.presentation.screens.instructions.detail.model.InstructionDetailState
import javax.inject.Inject

@HiltViewModel
class InstructionDetailViewModel @Inject constructor(
): BaseViewModel<InstructionDetailState, InstructionDetailEvent>(InstructionDetailState()) {

    override fun onEvent(event: InstructionDetailEvent) {
        when(event) {
            is InstructionDetailEvent.LoadData -> updateState { it.copy(instruction = event.instruction) }
            is InstructionDetailEvent.TitleChanged -> updateState { it.copy(instruction = it.instruction?.copy(title = event.value)) }
            is InstructionDetailEvent.PosterChanged -> updateState { it.copy(instruction = it.instruction?.copy(posterUrl = event.value)) }
            is InstructionDetailEvent.ChangeData -> { changeInstruction() }
        }
    }

    private fun changeInstruction() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            delay(500)
            updateState { it.copy(isLoading = false) }
        }
    }
}