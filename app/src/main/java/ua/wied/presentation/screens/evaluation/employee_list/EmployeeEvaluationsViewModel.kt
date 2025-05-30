package ua.wied.presentation.screens.evaluation.employee_list

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ua.wied.domain.models.evaluation.DateRange
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.usecases.GetEmployeeEvaluations
import ua.wied.domain.usecases.GetInstructionEvaluations
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.evaluation.employee_list.model.EmployeeEvaluationsEvent
import ua.wied.presentation.screens.evaluation.employee_list.model.EmployeeEvaluationsState
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationsEvent
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationsState
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class EmployeeEvaluationsViewModel @Inject constructor(
    private val getEmployeeEvaluations: GetEmployeeEvaluations
) : BaseViewModel<EmployeeEvaluationsState, EmployeeEvaluationsEvent>(EmployeeEvaluationsState()) {
    val evaluationsFlow = MutableStateFlow<List<Evaluation>>(emptyList())

    override fun onEvent(event: EmployeeEvaluationsEvent) {
        when (event) {
            is EmployeeEvaluationsEvent.LoadData -> {
                updateState { it.copy(employee = event.employee) }
                initialize(event.employee.id)
            }
            is EmployeeEvaluationsEvent.SearchChanged -> {
                val filteredEvaluations = filterEvaluations(event.value)
                updateState {
                    it.copy(
                        search = event.value,
                        isEmpty = filteredEvaluations.isEmpty(),
                        evaluations = filteredEvaluations
                    )
                }
            }
            is EmployeeEvaluationsEvent.DateRangeChanged -> {
                val filteredEvaluations = filterEvaluations(event.value)
                updateState {
                    it.copy(
                        dateRange = event.value,
                        evaluations = filteredEvaluations,
                        isEmpty = filteredEvaluations.isEmpty()
                    )
                }
            }
            is EmployeeEvaluationsEvent.Refresh -> { uiState.value.employee?.let { initialize(it.id, true) } }
        }
    }

    private fun filterEvaluations(query: String): List<Evaluation> {
        if (query.isEmpty()) return evaluationsFlow.value

        val lowerQuery = query.trim().lowercase()
        return evaluationsFlow.value.filter { evaluation ->
            evaluation.employee.name.lowercase().contains(lowerQuery)
        }
    }

    private fun filterEvaluations(dateRange: DateRange): List<Evaluation> {
        val start = dateRange.startDate
            ?.takeIf { it != 0L }
            ?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() }

        val end = dateRange.endDate
            ?.takeIf { it != 0L }
            ?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() }

        if (start == null && end == null) return evaluationsFlow.value

        return evaluationsFlow.value.filter { evaluation ->
            val time = evaluation.createTime
            when {
                start != null && end != null -> time in start..end
                start != null -> time >= start
                end != null -> time <= end
                else -> true
            }
        }
    }



    private fun initialize(employeeId: Int, isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getEmployeeEvaluations(employeeId) },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { evaluations ->
                evaluationsFlow.update { evaluations }
                updateState {
                    it.copy(
                        evaluations = evaluationsFlow.value,
                        isEmpty = evaluations.isEmpty(),
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
