package ua.wied.domain.repository

import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount

interface FolderRepository {
    suspend fun getInstructionFolders(): FlowResultList<Folder<Instruction>>
    suspend fun getInstructionWithReportCountFolders():  FlowResultList<Folder<InstructionWithReportCount>>
    suspend fun updateFolder(folderId: Int, orderNum: Int, title: String): UnitFlow
    suspend fun reorderFolder(folderId: Int, newOrder: Int): UnitFlow
    suspend fun deleteFolder(folderId: Int): UnitFlow
}