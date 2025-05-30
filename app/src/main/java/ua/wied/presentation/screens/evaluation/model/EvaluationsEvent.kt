package ua.wied.presentation.screens.evaluation.model

import ua.wied.presentation.screens.instructions.model.InstructionsEvent

sealed class EvaluationsEvent {
    data class SearchChanged(val value: String): EvaluationsEvent()
    data object Refresh : EvaluationsEvent()
}