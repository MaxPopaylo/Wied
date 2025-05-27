package ua.wied.presentation.screens.ai_instruction.create.model

sealed class CreateAiRequestEvent {
    data class OnBusinessTypeChanged(val value: String): CreateAiRequestEvent()
    data class OnJobPositionChanged(val value: String): CreateAiRequestEvent()
    data class OnWorkTaskChanged(val value: String): CreateAiRequestEvent()
    data class OnAdditionalInfoChanged(val value: String): CreateAiRequestEvent()
    data object Create: CreateAiRequestEvent()
}