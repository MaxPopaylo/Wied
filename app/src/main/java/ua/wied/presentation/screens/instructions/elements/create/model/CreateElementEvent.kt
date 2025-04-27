package ua.wied.presentation.screens.instructions.elements.create.model

sealed class CreateElementEvent {
    data class OnTitleChanged(val value: String): CreateElementEvent()
    data class OnInfoChanged(val value: String): CreateElementEvent()
    data class OnVideoUrlChanged(val value: String): CreateElementEvent()
    data class Create(val orderNum: Int): CreateElementEvent()
}