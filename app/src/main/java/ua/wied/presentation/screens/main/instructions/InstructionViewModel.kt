package ua.wied.presentation.screens.main.instructions

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.main.instructions.model.InstructionsEvent
import ua.wied.presentation.screens.main.instructions.model.InstructionsState
import javax.inject.Inject

@HiltViewModel
class InstructionViewModel @Inject constructor(
    private val getInstructionFoldersUseCase: GetInstructionFoldersUseCase
) : BaseViewModel<InstructionsState, InstructionsEvent>(InstructionsState()) {

    init {
        initialize()
    }

    override fun onEvent(event: InstructionsEvent) {
        when (event) {
            is InstructionsEvent.SearchChanged -> { updateState { it.copy(search = event.value) } }
            is InstructionsEvent.DeletePressed -> {  }
            is InstructionsEvent.Refresh -> { initialize(true) }
        }
    }

    private fun initialize(isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getInstructionFoldersUseCase() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { folders ->
                updateState {
                    it.copy(
                        folders = folders,
                        isEmpty = folders.isEmpty()
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
