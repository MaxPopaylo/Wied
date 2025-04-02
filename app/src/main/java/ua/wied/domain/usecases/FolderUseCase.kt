package ua.wied.domain.usecases

import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.domain.repository.FolderRepository
import javax.inject.Inject

class GetInstructionFoldersUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(): FlowResultList<Folder<Instruction>> =
        folderRepository.getInstructionFolders()
}

class GetInstructionWithReportCountFoldersUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(): FlowResultList<Folder<InstructionWithReportCount>> =
        folderRepository.getInstructionWithReportCountFolders()
}