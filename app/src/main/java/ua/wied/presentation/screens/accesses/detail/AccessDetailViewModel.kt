package ua.wied.presentation.screens.accesses.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.usecases.UpdateFolderUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailEvent
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailState
import javax.inject.Inject

@HiltViewModel
class AccessDetailViewModel @Inject constructor(
    private val updateFolder: UpdateFolderUseCase
): BaseViewModel<AccessDetailState, AccessDetailEvent>(AccessDetailState()) {

    override fun onEvent(event: AccessDetailEvent) {
        when(event) {
            is AccessDetailEvent.LoadData -> updateState { it.copy(folder = event.folder) }
            is AccessDetailEvent.TitleChanged -> updateState { it.copy(folder = it.folder?.copy(title = event.value)) }
            is AccessDetailEvent.ChangeData -> { changeFolder() }
            is AccessDetailEvent.Refresh -> { }
        }
    }

    private fun changeFolder() {
        val state = uiState.value
        if (state.folder != null) {
            collectNetworkRequest(
                apiCall = {
                    updateFolder(
                        title = state.folder.title,
                        orderNum = state.folder.orderNum,
                        folderId = state.folder.id
                    )
                },
                updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
                onFailure = { state.updateResult.emit(Result.failure(it)) },
                onSuccess = {
                    state.updateResult.emit(Result.success(Unit))
                }
            )
        }
    }

}