package ua.wied.domain.usecases

import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.domain.models.network.NetworkResponse
import ua.wied.domain.repository.InstructionRepository
import javax.inject.Inject

class GetInstructionFoldersUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(): NetworkResponse<List<Folder<Instruction>>> =
        instructionRepository.getInstructionFolders()
}

class GetInstructionWithReportCountFoldersUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(): NetworkResponse<List<Folder<InstructionWithReportCount>>> =
        instructionRepository.getInstructionWithReportCountFolders()
}