package ua.wied.presentation.screens.instructions.detail.model

sealed class InstructionDetailEvent {
    data class LoadData(val instructionId: Int): InstructionDetailEvent()
    data class TitleChanged(val value: String): InstructionDetailEvent()
    data class PosterChanged(val value: String?): InstructionDetailEvent()
    data object ChangeData: InstructionDetailEvent()
    data object Refresh: InstructionDetailEvent()
}