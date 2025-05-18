package ua.wied.presentation.screens.evaluation.instruction_list.model

import ua.wied.domain.models.evaluation.DateRange

sealed class InstructionEvaluationListEvent {
    data class SearchChanged(val value: String): InstructionEvaluationListEvent()
    data class DateRangeChanged(val value: DateRange): InstructionEvaluationListEvent()
    data object Refresh : InstructionEvaluationListEvent()
}