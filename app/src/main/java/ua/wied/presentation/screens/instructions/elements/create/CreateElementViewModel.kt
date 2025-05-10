package ua.wied.presentation.screens.instructions.elements.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.usecases.CreateElementUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementEvent
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementState
import javax.inject.Inject

@HiltViewModel
class CreateElementViewModel @Inject constructor(
    private val createElement: CreateElementUseCase
) : BaseViewModel<CreateElementState, CreateElementEvent>(CreateElementState()) {

    override fun onEvent(event: CreateElementEvent) {
        when(event) {
            is CreateElementEvent.OnTitleChanged -> updateState { it.copy(title = event.value) }
            is CreateElementEvent.OnInfoChanged -> updateState { it.copy(info = event.value) }
            is CreateElementEvent.OnVideoUrlChanged -> updateState { it.copy(videoUrl = event.value) }
            is CreateElementEvent.Create -> { createElement(event.orderNum, event.instructionId) }
        }
    }

    private fun createElement(orderNum: Int, instructionId: Int) {
        val state = uiState.value
        collectNetworkRequest(
            apiCall = {
                createElement(
                    title = state.title,
                    info = state.info,
                    videoUrl = state.videoUrl,
                    orderNum = orderNum,
                    instructionId = instructionId
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