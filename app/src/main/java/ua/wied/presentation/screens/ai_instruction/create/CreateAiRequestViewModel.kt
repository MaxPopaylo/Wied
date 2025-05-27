package ua.wied.presentation.screens.ai_instruction.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.usecases.CreateAIInstructionUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.ai_instruction.create.model.CreateAiRequestEvent
import ua.wied.presentation.screens.ai_instruction.create.model.CreateAiRequestState
import javax.inject.Inject

@HiltViewModel
class CreateAiRequestViewModel @Inject constructor(
    private val createAIInstructionUseCase: CreateAIInstructionUseCase
): BaseViewModel<CreateAiRequestState, CreateAiRequestEvent>(CreateAiRequestState()) {

    override fun onEvent(event: CreateAiRequestEvent) {
        when(event) {
            is CreateAiRequestEvent.OnBusinessTypeChanged -> updateState { it.copy(businessType = event.value) }
            is CreateAiRequestEvent.OnWorkTaskChanged -> updateState { it.copy(workTask = event.value) }
            is CreateAiRequestEvent.OnJobPositionChanged -> updateState { it.copy(jobPosition = event.value) }
            is CreateAiRequestEvent.OnAdditionalInfoChanged -> updateState { it.copy(additionalInfo = event.value) }
            is CreateAiRequestEvent.Create -> createAiInstruction()
        }
    }

    private fun createAiInstruction() {
        val state = uiState.value
        collectNetworkRequest(
            apiCall = {
                createAIInstructionUseCase(
                    businessType = state.businessType,
                    workTask = state.workTask,
                    jobPosition = state.jobPosition,
                    additionalInfo = state.additionalInfo,
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