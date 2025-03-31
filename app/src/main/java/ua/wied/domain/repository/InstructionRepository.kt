package ua.wied.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount

interface InstructionRepository {
    suspend fun getInstructionFolders(): Flow<List<Folder<Instruction>>>
    suspend fun getInstructionWithReportCountFolders(): Flow<List<Folder<InstructionWithReportCount>>>
}