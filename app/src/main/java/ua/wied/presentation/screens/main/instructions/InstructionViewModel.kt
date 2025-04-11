package ua.wied.presentation.screens.main.instructions

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.presentation.common.base.BaseViewModelWithEvent
import ua.wied.presentation.screens.main.instructions.model.InstructionsEvent
import ua.wied.presentation.screens.main.instructions.model.InstructionsState
import javax.inject.Inject

@HiltViewModel
class InstructionViewModel @Inject constructor(
    private val getInstructionFoldersUseCase: GetInstructionFoldersUseCase
) : BaseViewModelWithEvent<InstructionsState, InstructionsEvent>(InstructionsState()) {

    init {
        initialize()
    }

    override fun onEvent(event: InstructionsEvent) {
        when (event) {
            is InstructionsEvent.SearchChanged -> { updateState { it.copy(search = event.value) } }
            is InstructionsEvent.DeletePressed -> {  }
        }
    }

    private fun initialize() {
        collectNetworkRequest(
            apiCall = { getInstructionFoldersUseCase() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            updateFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { folders -> updateState { it.copy(folders = folders) } }
        )
    }


}
