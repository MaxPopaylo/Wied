package ua.wied.domain.usecases

import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.domain.repository.FolderRepository
import ua.wied.domain.repository.InstructionRepository
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

class UpdateFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(
        folderId: Int, orderNum: Int,
        title: String
    ) = folderRepository.updateFolder(
        folderId = folderId,
        orderNum = orderNum,
        title = title
    )
}

class ReorderFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(
        folderId: Int,
        newOrder: Int
    ) = folderRepository.reorderFolder(
        folderId = folderId,
        newOrder = newOrder
    )
}

class DeleteFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(folderId: Int) = folderRepository.deleteFolder(folderId = folderId)
}