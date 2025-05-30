package ua.wied.presentation.screens.evaluation.instruction_list.model

import ua.wied.domain.models.evaluation.DateRange
import ua.wied.domain.models.instruction.Instruction

sealed class InstructionEvaluationsEvent {
    data class LoadData(val instruction: Instruction): InstructionEvaluationsEvent()
    data class SearchChanged(val value: String): InstructionEvaluationsEvent()
    data class DateRangeChanged(val value: DateRange): InstructionEvaluationsEvent()
    data object Refresh : InstructionEvaluationsEvent()
}