package ua.wied.presentation.screens.instructions.model

sealed class InstructionsEvent {
    data class SearchChanged(val value: String): InstructionsEvent()
    data class DeletePressed(val value: Int): InstructionsEvent()
    data class ChangeOrderNum(val instructionId: Int, val folderId: Int, val orderNum: Int): InstructionsEvent()
    data object Refresh : InstructionsEvent()
}