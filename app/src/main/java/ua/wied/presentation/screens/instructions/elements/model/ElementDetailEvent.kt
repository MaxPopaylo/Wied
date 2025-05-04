package ua.wied.presentation.screens.instructions.elements.model

import ua.wied.domain.models.instruction.Element
import ua.wied.presentation.screens.instructions.video.model.VideoEvent

sealed class ElementDetailEvent {
    data class LoadDate(val element: Element): ElementDetailEvent()
    data class TitleChanged(val value: String): ElementDetailEvent()
    data class InfoChanged(val value: String): ElementDetailEvent()
    data class VideoChanged(val value: String?): ElementDetailEvent()
    data class ChangeFullScreenVideoState(val url: String?, val showDialog: Boolean) : ElementDetailEvent()
    data object ChangeData: ElementDetailEvent()
}