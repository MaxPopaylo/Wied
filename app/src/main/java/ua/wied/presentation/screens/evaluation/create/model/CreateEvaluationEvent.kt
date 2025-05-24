package ua.wied.presentation.screens.evaluation.create.model

import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.user.User
import java.time.LocalDateTime

sealed class CreateEvaluationEvent {
    data class OnEmployeeChanged(val value: User): CreateEvaluationEvent()
    data class OnInstructionChanged(val value: Instruction): CreateEvaluationEvent()
    data class OnDateChanged(val value: LocalDateTime): CreateEvaluationEvent()
    data class OnItemEvaluationChanged(val elementId: Int, val evaluation: Float) : CreateEvaluationEvent()
    data class OnInfoChanged(val value: String): CreateEvaluationEvent()
    data object LoadEmployees: CreateEvaluationEvent()
    data object LoadInstructions: CreateEvaluationEvent()
    data object Create: CreateEvaluationEvent()
}