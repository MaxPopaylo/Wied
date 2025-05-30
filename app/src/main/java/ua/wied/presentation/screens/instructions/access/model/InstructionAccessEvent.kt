package ua.wied.presentation.screens.instructions.access.model

import ua.wied.presentation.screens.instructions.model.InstructionsEvent

sealed class InstructionAccessEvent {
    data class LoadData(val instructionId: Int): InstructionAccessEvent()
    data class AccessToggled(val userId: Int, val userName: String): InstructionAccessEvent()
    data object LoadEmployees: InstructionAccessEvent()
    data object Refresh: InstructionAccessEvent()
}