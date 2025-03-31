package ua.wied.domain.models.instruction

import ua.wied.domain.models.HasId

data class InstructionWithReportCount(
    val instruction: Instruction,
    val reportCount: Int
) : HasId {
    override val id: Int
        get() = instruction.id
}
