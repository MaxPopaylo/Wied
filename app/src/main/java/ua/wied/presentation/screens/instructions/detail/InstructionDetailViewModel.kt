package ua.wied.presentation.screens.instructions.detail

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.usecases.GetInstructionUseCase
import ua.wied.domain.usecases.UpdateInstructionUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.detail.model.InstructionDetailEvent
import ua.wied.presentation.screens.instructions.detail.model.InstructionDetailState
import javax.inject.Inject

@HiltViewModel
class InstructionDetailViewModel @Inject constructor(
    private val updateInstruction: UpdateInstructionUseCase,
    private val getInstruction: GetInstructionUseCase
): BaseViewModel<InstructionDetailState, InstructionDetailEvent>(InstructionDetailState()) {

    override fun onEvent(event: InstructionDetailEvent) {
        when(event) {
            is InstructionDetailEvent.LoadData -> loadInstruction(event.instructionId)
            is InstructionDetailEvent.TitleChanged -> updateState { it.copy(instruction = it.instruction?.copy(title = event.value)) }
            is InstructionDetailEvent.PosterChanged -> updateState { it.copy(instruction = it.instruction?.copy(posterUrl = event.value)) }
            is InstructionDetailEvent.ChangeData -> { changeInstruction() }
            is InstructionDetailEvent.Refresh -> { }
        }
    }

    private fun loadInstruction(instructionId: Int, isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = {
                getInstruction(instructionId = instructionId)
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = { updateState { it.copy(isNotInternetConnection = true) } },
            onSuccess = { instruction ->
                updateState { it.copy(
                    instruction = instruction,
                    lastItemOrderNum = instruction.getLastItemOrderNum() + 1
                ) }
                updateState { it.copy(isNotInternetConnection = false) }
            },
            onRefresh = { value ->
                if (isRefresh) {
                    if (!value) delay(100)
                    updateState { it.copy(isRefreshing = value ) }
                }
            }
        )
    }


    private fun changeInstruction() {
        val state = uiState.value
        if (state.instruction != null) {
            collectNetworkRequest(
                apiCall = {
                    updateInstruction(
                        instructionId = state.instruction.id,
                        title = state.instruction.title,
                        posterUrl = state.instruction.posterUrl,
                        orderNum = state.instruction.orderNum,
                        folderId = state.instruction.folderId
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

    private fun Instruction.getLastItemOrderNum() =
        if (this.elements.isEmpty()) 0
        else this.elements.last().orderNum
}