package ua.wied.presentation.screens.evaluation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.evaluation.model.EvaluationsEvent
import ua.wied.presentation.screens.evaluation.model.EvaluationsState
import javax.inject.Inject

@HiltViewModel
class EvaluationsViewModel @Inject constructor(
    private val getInstructionFoldersUseCase: GetInstructionFoldersUseCase,
) : BaseViewModel<EvaluationsState, EvaluationsEvent>(EvaluationsState()) {
    val foldersFlow = MutableStateFlow<List<Folder<Instruction>>>(emptyList())

    init {
        initialize()
    }

    override fun onEvent(event: EvaluationsEvent) {
        when (event) {
            is EvaluationsEvent.SearchChanged -> {
                val filteredFolders = filterFolders(event.value)
                updateState {
                    it.copy(
                        search = event.value,
                        isEmpty = filteredFolders.isEmpty(),
                        folders = filteredFolders
                    )
                }
            }
            is EvaluationsEvent.Refresh -> { initialize(true) }
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

    private fun List<Folder<Instruction>>.isFoldersEmpty() =
        this.size == 1 && this.first().items.isEmpty()

}
