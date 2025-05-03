package ua.wied.presentation.screens.instructions.model

import ua.wied.domain.models.instruction.Instruction

sealed class InstructionsEvent {
    data class SearchChanged(val value: String): InstructionsEvent()
    data class DeletePressed(val value: Int): InstructionsEvent()
    data class ChangeOrderNum(val instruction: Instruction, val folderId: Int, val orderNum: Int): InstructionsEvent()
    data object Refresh : InstructionsEvent()
}