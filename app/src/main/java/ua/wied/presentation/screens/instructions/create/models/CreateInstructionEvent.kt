package ua.wied.presentation.screens.instructions.create.models

sealed class CreateInstructionEvent {
    data class OnTitleChanged(val value: String): CreateInstructionEvent()
    data class OnPosterUrlChanged(val value: String?): CreateInstructionEvent()
    data class Create(val orderNum: Int, val folderId: Int,): CreateInstructionEvent()
}