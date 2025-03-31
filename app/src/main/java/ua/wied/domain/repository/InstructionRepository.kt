package ua.wied.domain.repository

import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.domain.models.network.NetworkResponse

interface InstructionRepository {
    suspend fun getInstructionFolders(): NetworkResponse<List<Folder<Instruction>>>
    suspend fun getInstructionWithReportCountFolders(): NetworkResponse<List<Folder<InstructionWithReportCount>>>
}