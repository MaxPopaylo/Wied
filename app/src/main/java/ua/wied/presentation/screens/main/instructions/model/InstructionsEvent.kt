package ua.wied.presentation.screens.main.instructions.model

import ua.wied.domain.models.instruction.Instruction

sealed class InstructionsEvent {
    data class SearchChanged(val value: String): InstructionsEvent()
    data class DeletePressed(val value: Instruction): InstructionsEvent()
}