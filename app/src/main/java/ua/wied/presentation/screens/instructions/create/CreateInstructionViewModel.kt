package ua.wied.presentation.screens.instructions.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.usecases.CreateInstructionUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.create.models.CreateInstructionEvent
import ua.wied.presentation.screens.instructions.create.models.CreateInstructionState
import javax.inject.Inject

@HiltViewModel
class CreateInstructionViewModel @Inject constructor(
    private val createInstruction: CreateInstructionUseCase
): BaseViewModel<CreateInstructionState, CreateInstructionEvent>(CreateInstructionState()) {

    override fun onEvent(event: CreateInstructionEvent) {
        when(event) {
            is CreateInstructionEvent.OnTitleChanged -> updateState { it.copy(title = event.value) }
            is CreateInstructionEvent.OnPosterUrlChanged -> updateState { it.copy(posterUrl = event.value) }
            is CreateInstructionEvent.Create -> { createInstruction(event.orderNum, event.folderId) }
        }
    }

    private fun createInstruction(orderNum: Int, folderId: Int) {
        val state = uiState.value
        collectNetworkRequest(
            apiCall = {
                createInstruction(
                    title = state.title,
                    posterUrl = state.posterUrl,
                    orderNum = orderNum,
                    folderId = folderId
                )
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = { state.createResult.emit(Result.failure(it)) },
            onSuccess = {
                state.createResult.emit(Result.success(Unit))
            }
        )
    }

}