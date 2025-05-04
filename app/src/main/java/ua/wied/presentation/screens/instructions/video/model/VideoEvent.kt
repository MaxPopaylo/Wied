package ua.wied.presentation.screens.instructions.video.model

import ua.wied.domain.models.instruction.Instruction

sealed class VideoEvent {
    data class LoadData(val value: Instruction) : VideoEvent()
    data class ChangeFullScreenVideoState(val url: String?, val showDialog: Boolean) : VideoEvent()
    data object Refresh : VideoEvent()
}