package ua.wied.presentation.screens.accesses.create.model

sealed class CreateFolderEvent {
    data class TitleChanged(val value: String): CreateFolderEvent()
    data object Created: CreateFolderEvent()
    data class Create(val orderNum: Int): CreateFolderEvent()
}