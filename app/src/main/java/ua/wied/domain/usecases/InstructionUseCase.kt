package ua.wied.domain.usecases

import kotlinx.coroutines.flow.Flow
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.domain.repository.InstructionRepository
import javax.inject.Inject

class GetInstructionFoldersUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(): Flow<List<Folder<Instruction>>> =
        instructionRepository.getInstructionFolders()
}

class GetInstructionWithReportCountFoldersUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(): Flow<List<Folder<InstructionWithReportCount>>> =
        instructionRepository.getInstructionWithReportCountFolders()
}