package ua.wied.presentation.screens.instructions.access

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ua.wied.domain.models.folder.Access
import ua.wied.domain.usecases.GetEmployeesUseCase
import ua.wied.domain.usecases.GetInstructionUseCase
import ua.wied.domain.usecases.ToggleInstructionAccessUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.access.model.InstructionAccessEvent
import ua.wied.presentation.screens.instructions.access.model.InstructionAccessState
import javax.inject.Inject
import kotlin.collections.none
import kotlin.collections.orEmpty

@HiltViewModel
class InstructionAccessViewModel @Inject constructor(
    private val toggleInstructionAccessUseCase: ToggleInstructionAccessUseCase,
    private val getEmployeesUseCase: GetEmployeesUseCase,
    private val getInstruction: GetInstructionUseCase
): BaseViewModel<InstructionAccessState, InstructionAccessEvent>(InstructionAccessState()) {

    override fun onEvent(event: InstructionAccessEvent) {
        when(event) {
            is InstructionAccessEvent.LoadData -> loadInstruction(event.instructionId)
            is InstructionAccessEvent.AccessToggled -> toggleInstructionAccess(event.userId, event.userName)
            is InstructionAccessEvent.LoadEmployees -> getEmployees()
            is InstructionAccessEvent.Refresh -> uiState.value.instruction?.id?.let { loadInstruction(it) }
        }
    }

    private fun loadInstruction(instructionId: Int, isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = {
                getInstruction(instructionId = instructionId)
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = { updateState { it.copy(isNotInternetConnection = true) } },
            onSuccess = { instruction ->
                updateState { it.copy(
                    instruction = instruction
                ) }
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

    private fun toggleInstructionAccess(userId: Int, userName: String) {
        val state = uiState.value
        if (state.instruction != null) {
            collectNetworkRequest(
                apiCall = {
                    toggleInstructionAccessUseCase(
                        instructionId = state.instruction.id,
                        userId = userId
                    )
                },
                updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
                onFailure = {  },
                onSuccess = {
                    toggleUiStateInstructionAccess(userId, userName)
                }
            )
        }
    }

    private fun toggleUiStateInstructionAccess(userId: Int, userName: String) {
        val state = uiState.value
        val folder = state.instruction

        if (folder != null) {
            val currentAccesses = folder.accesses.toMutableList()
            val access = currentAccesses.find { it.id == userId }

            val updatedAccesses = if (access != null) {
                currentAccesses - access
            } else {
                currentAccesses + Access(userId, userName)
            }

            val updatedInstruction = folder.copy(accesses = updatedAccesses)

            updateState {
                it.copy(instruction = updatedInstruction)
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
                val accesses = uiState.value.instruction?.accesses.orEmpty()
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