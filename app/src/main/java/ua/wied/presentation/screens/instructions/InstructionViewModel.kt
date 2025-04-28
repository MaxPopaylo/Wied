package ua.wied.presentation.screens.instructions

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.model.InstructionsEvent
import ua.wied.presentation.screens.instructions.model.InstructionsState
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
                        isEmpty = folders.isFoldersEmpty(),
                        lastItemOrderNum = folders.getLastItemOrderNum() + 1,
                        firstFolderId = folders.getFirstFolderId()
                    )
                }
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

    private fun List<Folder<Instruction>>.getFirstFolderId() =
        this.first().id

    private fun List<Folder<Instruction>>.getLastItemOrderNum() =
        if (this.first().items.isEmpty()) 0
        else this.first().items.last().orderNum


    private fun List<Folder<Instruction>>.isFoldersEmpty() =
        this.size == 1 && this.first().items.isEmpty()

}
