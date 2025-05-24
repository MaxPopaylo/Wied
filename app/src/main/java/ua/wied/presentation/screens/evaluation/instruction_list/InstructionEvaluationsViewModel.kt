package ua.wied.presentation.screens.evaluation.instruction_list

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ua.wied.domain.models.evaluation.DateRange
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.usecases.GetInstructionEvaluations
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationsEvent
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationsState
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class InstructionEvaluationsViewModel @Inject constructor(
    private val getInstructionEvaluations: GetInstructionEvaluations
) : BaseViewModel<InstructionEvaluationsState, InstructionEvaluationsEvent>(InstructionEvaluationsState()) {
    val evaluationsFlow = MutableStateFlow<List<Evaluation>>(emptyList())

    override fun onEvent(event: InstructionEvaluationsEvent) {
        when (event) {
            is InstructionEvaluationsEvent.LoadData -> {
                updateState { it.copy(instruction = event.instruction) }
                initialize(event.instruction.id)
            }
            is InstructionEvaluationsEvent.SearchChanged -> {
                val filteredEvaluations = filterEvaluations(event.value)
                updateState {
                    it.copy(
                        search = event.value,
                        isEmpty = filteredEvaluations.isEmpty(),
                        evaluations = filteredEvaluations
                    )
                }
            }
            is InstructionEvaluationsEvent.DateRangeChanged -> {
                val filteredEvaluations = filterEvaluations(event.value)
                updateState {
                    it.copy(
                        dateRange = event.value,
                        evaluations = filteredEvaluations,
                        isEmpty = filteredEvaluations.isEmpty()
                    )
                }
            }
            is InstructionEvaluationsEvent.Refresh -> { uiState.value.instruction?.id?.let { initialize(it, true) } }
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



    private fun initialize(instructionId: Int, isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getInstructionEvaluations(instructionId) },
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

}
