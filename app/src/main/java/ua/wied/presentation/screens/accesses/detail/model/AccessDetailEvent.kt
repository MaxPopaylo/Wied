package ua.wied.presentation.screens.accesses.detail.model

import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction

sealed class AccessDetailEvent {
    data class LoadData(val folder: Folder<Instruction>): AccessDetailEvent()
    data class TitleChanged(val value: String): AccessDetailEvent()
    data object ChangeData: AccessDetailEvent()
    data object Refresh: AccessDetailEvent()
}