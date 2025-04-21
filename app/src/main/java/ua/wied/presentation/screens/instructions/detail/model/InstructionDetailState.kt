package ua.wied.presentation.screens.instructions.detail.model

import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.base.BaseState

data class InstructionDetailState (
    override val isLoading: Boolean = false,
    val instruction: Instruction? = null

): BaseState