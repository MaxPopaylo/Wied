package ua.wied.presentation.screens.accesses.model

sealed class AccessesEvent {
    data class SearchChanged(val value: String): AccessesEvent()
    data class DeletePressed(val value: Int): AccessesEvent()
    data class ChangeOrderNum(val folderId: Int, val orderNum: Int): AccessesEvent()
    data object Refresh : AccessesEvent()
}