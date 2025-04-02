package ua.wied.domain.repository

import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount

interface FolderRepository {
    suspend fun getInstructionFolders(): FlowResultList<Folder<Instruction>>
    suspend fun getInstructionWithReportCountFolders():  FlowResultList<Folder<InstructionWithReportCount>>
}