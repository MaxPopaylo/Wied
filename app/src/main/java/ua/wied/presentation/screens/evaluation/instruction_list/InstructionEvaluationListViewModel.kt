package ua.wied.presentation.screens.evaluation.instruction_list

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.domain.models.evaluation.DateRange
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationListEvent
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationListState
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class InstructionEvaluationListViewModel @Inject constructor(

) : BaseViewModel<InstructionEvaluationListState, InstructionEvaluationListEvent>(InstructionEvaluationListState()) {
    val evaluationsFlow = MutableStateFlow<List<Evaluation>>(emptyList())

    init {
        initialize()
    }

    override fun onEvent(event: InstructionEvaluationListEvent) {
        when (event) {
            is InstructionEvaluationListEvent.SearchChanged -> {
                updateState {
                    it.copy(
                        search = event.value,
                        evaluations = filterEvaluations(event.value)
                    )
                }
            }
            is InstructionEvaluationListEvent.DateRangeChanged -> {
                updateState {
                    it.copy(
                        dateRange = event.value,
                        evaluations = filterEvaluations(event.value)
                    )
                }
            }
            is InstructionEvaluationListEvent.Refresh -> { initialize(true) }
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



    private fun initialize(isRefresh: Boolean = false) {
//        collectNetworkRequest(
//            apiCall = { getInstructionFoldersUseCase() },
//            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
//            onFailure = {
//                updateState { it.copy(isNotInternetConnection = true) }
//            },
//            onSuccess = { folders ->
//                foldersFlow.update { folders }
//                updateState {
//                    it.copy(
//                        folders = foldersFlow.value,
//                        isEmpty = folders.isFoldersEmpty(),
//                        isNotInternetConnection = false
//                    )
//                }
//            },
//            onRefresh = { value ->
//                if (isRefresh) {
//                    if (!value) delay(100)
//                    updateState { it.copy(isRefreshing = value ) }
//                }
//            }
//        )
    }

    private fun List<Folder<Instruction>>.isFoldersEmpty() =
        this.size == 1 && this.first().items.isEmpty()

}
