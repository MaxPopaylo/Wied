package ua.wied.presentation.screens.accesses

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.usecases.DeleteFolderUseCase
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.domain.usecases.ReorderFolderUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.accesses.model.AccessesEvent
import ua.wied.presentation.screens.accesses.model.AccessesState
import javax.inject.Inject

@HiltViewModel
class AccessesViewModel @Inject constructor(
    private val getInstructionFolders: GetInstructionFoldersUseCase,
    private val reorderFolder: ReorderFolderUseCase,
    private val deleteFolder: DeleteFolderUseCase
): BaseViewModel<AccessesState, AccessesEvent>(AccessesState()) {
    val foldersFlow = MutableStateFlow<List<Folder<Instruction>>>(emptyList())

    init {
        initialize()
    }

    override fun onEvent(event: AccessesEvent) {
        when (event) {
            is AccessesEvent.SearchChanged -> {
                updateState {
                    it.copy(
                        search = event.value,
                        folders = filterFolders(event.value)
                    )
                }
            }
            is AccessesEvent.DeletePressed -> deleteInstruction(event.value)
            is AccessesEvent.ChangeOrderNum -> changeFolderOrderNum(event.folderId, event.orderNum)
            is AccessesEvent.Refresh -> { initialize(true) }
        }
    }

    private fun filterFolders(query: String): List<Folder<Instruction>> {
        if (query.isEmpty()) return foldersFlow.value

        val lowerQuery = query.trim().lowercase()
        return foldersFlow.value.filter { folder ->
            folder.title.lowercase().contains(lowerQuery)
        }
    }

    private fun initialize(isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getInstructionFolders() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { folders ->
                foldersFlow.update { folders }
                updateState {
                    it.copy(
                        folders = foldersFlow.value,
                        isEmpty = folders.isEmpty(),
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

    private fun changeFolderOrderNum(folderId: Int, orderNum: Int) {
        collectNetworkRequest(
            apiCall = {
                reorderFolder(
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

    private fun deleteInstruction(folderId: Int) {
        collectNetworkRequest(
            apiCall = {
                deleteFolder(folderId)
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {  },
            onSuccess = {
                initialize(true)
            }
        )
    }
}