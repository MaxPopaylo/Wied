package ua.wied.presentation.screens.instructions

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.usecases.DeleteInstructionUseCase
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.domain.usecases.ReorderInstructionUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.model.InstructionsEvent
import ua.wied.presentation.screens.instructions.model.InstructionsState
import javax.inject.Inject

@HiltViewModel
class InstructionViewModel @Inject constructor(
    private val getInstructionFoldersUseCase: GetInstructionFoldersUseCase,
    private val reorderInstruction: ReorderInstructionUseCase,
    private val deleteInstructionUseCase: DeleteInstructionUseCase
) : BaseViewModel<InstructionsState, InstructionsEvent>(InstructionsState()) {
    val foldersFlow = MutableStateFlow<List<Folder<Instruction>>>(emptyList())

    init {
        initialize()
    }

    override fun onEvent(event: InstructionsEvent) {
        when (event) {
            is InstructionsEvent.SearchChanged -> {
                val filteredFolders = filterFolders(event.value)
                updateState {
                    it.copy(
                        search = event.value,
                        isEmpty = filteredFolders.isEmpty(),
                        folders = filteredFolders
                    )
                }
            }
            is InstructionsEvent.DeletePressed -> deleteInstruction(event.value)
            is InstructionsEvent.ChangeOrderNum -> changeInstructionOrderNum(event.instructionId, event.folderId, event.orderNum)
            is InstructionsEvent.Refresh -> { initialize(true) }
        }
    }

    private fun filterFolders(query: String): List<Folder<Instruction>> {
        if (query.isEmpty()) return foldersFlow.value

        val lowerQuery = query.trim().lowercase()
        return foldersFlow.value.mapNotNull { folder ->
            val matchingItems = folder.items.filter { instruction ->
                instruction.title.lowercase().contains(lowerQuery)
            }

            if (matchingItems.isNotEmpty()) {
                folder.copy(items = matchingItems)
            } else {
                null
            }
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
                foldersFlow.update { folders }
                updateState {
                    it.copy(
                        folders = foldersFlow.value,
                        isEmpty = folders.isFoldersEmpty(),
                        lastItemOrderNum = folders.getLastItemOrderNum() + 1,
                        firstFolderId = folders.getFirstFolderId(),
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

    private fun changeInstructionOrderNum(instructionId: Int, folderId: Int, orderNum: Int) {
        collectNetworkRequest(
            apiCall = {
                reorderInstruction(
                    instructionId = instructionId,
                    folderId = folderId,
                    newOrder = orderNum + 1
                )
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {  },
            onSuccess = {
                initialize(true)
            }
        )
    }

    private fun deleteInstruction(instructionId: Int) {
        collectNetworkRequest(
            apiCall = {
                deleteInstructionUseCase(instructionId)
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {  },
            onSuccess = {
                initialize(true)
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
