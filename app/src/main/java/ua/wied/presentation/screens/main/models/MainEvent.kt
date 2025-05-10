package ua.wied.presentation.screens.main.models

sealed class MainEvent {
    data class InstructionEditingChanged(val value: Boolean?): MainEvent()
    data class ElementEditingChanged(val value: Boolean?): MainEvent()
    data class FabVisibilityChanged(val value: Boolean): MainEvent()
    data class FabClickChanged(val value: () -> Unit): MainEvent()
}