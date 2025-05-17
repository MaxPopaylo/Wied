package ua.wied.presentation.screens.accesses.detail.model

import ua.wied.domain.models.folder.Access
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction

sealed class AccessDetailEvent {
    data class LoadData(val folderId: Int): AccessDetailEvent()
    data class TitleChanged(val value: String): AccessDetailEvent()
    data class AccessToggled(val userId: Int, val userName: String): AccessDetailEvent()
    data object LoadEmployees: AccessDetailEvent()
    data object ChangeData: AccessDetailEvent()
    data object Refresh: AccessDetailEvent()
}