package ua.wied.presentation.screens.ai_instruction.model

sealed class AiInstructionHistoryEvent {
    data object Refresh: AiInstructionHistoryEvent()
}