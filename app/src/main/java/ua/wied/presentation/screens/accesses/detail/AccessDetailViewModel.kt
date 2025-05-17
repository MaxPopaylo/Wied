package ua.wied.presentation.screens.accesses.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ua.wied.domain.models.folder.Access
import ua.wied.domain.usecases.GetEmployeesUseCase
import ua.wied.domain.usecases.GetFolderUseCase
import ua.wied.domain.usecases.ToggleFolderAccessUseCase
import ua.wied.domain.usecases.UpdateFolderUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailEvent
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailState
import javax.inject.Inject

@HiltViewModel
class AccessDetailViewModel @Inject constructor(
    private val updateFolder: UpdateFolderUseCase,
    private val getEmployeesUseCase: GetEmployeesUseCase,
    private val toggleFolderAccess: ToggleFolderAccessUseCase,
    private val getFolder: GetFolderUseCase
): BaseViewModel<AccessDetailState, AccessDetailEvent>(AccessDetailState()) {

    override fun onEvent(event: AccessDetailEvent) {
        when(event) {
            is AccessDetailEvent.LoadData -> loadFolder(event.folderId)
            is AccessDetailEvent.TitleChanged -> updateState { it.copy(folder = it.folder?.copy(title = event.value)) }
            is AccessDetailEvent.AccessToggled -> toggleFolderAccess(event.userId, event.userName)
            is AccessDetailEvent.LoadEmployees -> getEmployees()
            is AccessDetailEvent.ChangeData -> { changeFolder() }
        }
    }

    private fun loadFolder(folderId: Int) {
        collectNetworkRequest(
            apiCall = {
                getFolder(folderId)
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = { updateState { it.copy(isNotInternetConnection = true) } },
            onSuccess = { folder ->
                updateState { it.copy(
                    folder = folder
                ) }
                updateState { it.copy(isNotInternetConnection = false) }
            },
            onRefresh = { value ->
                if (!value) delay(100)
                updateState { it.copy(isRefreshing = value ) }
            }
        )
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

    private fun toggleFolderAccess(userId: Int, userName: String) {
        val state = uiState.value
        if (state.folder != null) {
            collectNetworkRequest(
                apiCall = {
                    toggleFolderAccess(
                        folderId = state.folder.id,
                        userId = userId
                    )
                },
                updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
                onFailure = { state.updateResult.emit(Result.failure(it)) },
                onSuccess = {
                    toggleUiStateFolderAccess(userId, userName)
                }
            )
        }
    }

    private fun toggleUiStateFolderAccess(userId: Int, userName: String) {
        val state = uiState.value
        val folder = state.folder

        if (folder != null) {
            val currentAccesses = folder.accesses.toMutableList()
            val access = currentAccesses.find { it.id == userId }

            val updatedAccesses = if (access != null) {
                currentAccesses - access
            } else {
                currentAccesses + Access(userId, userName)
            }

            val updatedFolder = folder.copy(accesses = updatedAccesses)

            updateState {
                it.copy(folder = updatedFolder)
            }
        }
    }

    private fun getEmployees() {
        collectNetworkRequest(
            apiCall = { getEmployeesUseCase() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { employees ->
                val accesses = uiState.value.folder?.accesses.orEmpty()
                val updatedEmployees = employees.filter { employee ->
                    accesses.none { access -> access.id == employee.id }

                }

                updateState {
                    it.copy(
                        employees = updatedEmployees,
                        isEmpty = employees.isEmpty(),
                        isNotInternetConnection = false
                    )
                }
            }
        )
    }

}