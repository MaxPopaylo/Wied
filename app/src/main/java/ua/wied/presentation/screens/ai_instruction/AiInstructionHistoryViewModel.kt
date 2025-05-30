package ua.wied.presentation.screens.ai_instruction

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import ua.wied.domain.usecases.GetAIInstructionHistoryUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.ai_instruction.model.AiInstructionHistoryEvent
import ua.wied.presentation.screens.ai_instruction.model.AiInstructionHistoryState

@HiltViewModel
class AiInstructionHistoryViewModel @Inject constructor(
    private val getAIInstructionHistoryUseCase: GetAIInstructionHistoryUseCase,
): BaseViewModel<AiInstructionHistoryState, AiInstructionHistoryEvent>(AiInstructionHistoryState()) {

    init {
        loadHistory()
    }

    override fun onEvent(event: AiInstructionHistoryEvent) {
        when(event) {
            is AiInstructionHistoryEvent.Refresh -> loadHistory(true)
        }
    }

    private fun loadHistory(isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getAIInstructionHistoryUseCase() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { history ->
                updateState {
                    it.copy(
                        history = history.reversed(),
                        isEmpty = history.isEmpty(),
                        isNotInternetConnection = false
                    )
                }
            },
            onRefresh = { value ->
                if (isRefresh) {
                    if (!value) delay(100)
                    updateState { it.copy(isRefreshing = value ) }
                }
            }
        )
    }

}