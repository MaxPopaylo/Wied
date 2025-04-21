package ua.wied.presentation.screens.instructions.detail.model

import ua.wied.domain.models.instruction.Instruction

sealed class InstructionDetailEvent {
    data class LoadData(val instruction: Instruction): InstructionDetailEvent()
    data class TitleChanged(val value: String): InstructionDetailEvent()
    data class PosterChanged(val value: String?): InstructionDetailEvent()
    data object ChangeData: InstructionDetailEvent()
}