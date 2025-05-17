package ua.wied.presentation.screens.accesses.create

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.wied.domain.usecases.CreateFolderUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.accesses.create.model.CreateFolderEvent
import ua.wied.presentation.screens.accesses.create.model.CreateFolderState
import javax.inject.Inject

@HiltViewModel
class CreateFolderViewModel @Inject constructor(
    private val createFolderUseCase: CreateFolderUseCase
): BaseViewModel<CreateFolderState, CreateFolderEvent>(CreateFolderState())  {

    override fun onEvent(event: CreateFolderEvent) {
        when(event) {
            is CreateFolderEvent.TitleChanged -> updateState { it.copy(title = event.value) }
            is CreateFolderEvent.Created -> onCreated()
            is CreateFolderEvent.Create -> createFolder(event.orderNum)
        }
    }

    private fun createFolder(orderNum: Int) {
        val state = uiState.value
        if (state.title.isNotEmpty()) {
            collectNetworkRequest(
                apiCall = {
                    createFolderUseCase(
                        title = state.title,
                        orderNum = orderNum
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

    private fun onCreated() {
        viewModelScope.launch {
            val state = uiState.value
            updateState { it.copy(title = "") }
            state.createResult.update { null }
        }
    }

}