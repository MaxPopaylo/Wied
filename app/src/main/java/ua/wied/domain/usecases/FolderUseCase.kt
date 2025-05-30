package ua.wied.domain.usecases

import ua.wied.domain.models.FlowResult
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.domain.repository.FolderRepository
import ua.wied.domain.repository.InstructionRepository
import javax.inject.Inject

class GetFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(folderId: Int): FlowResult<Folder<Instruction>> =
        folderRepository.getFolder(folderId)
}

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

class CreateFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(title: String, orderNum: Int) =
        folderRepository.createFolder(
            title = title,
            orderNum = orderNum
        )
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

class ToggleFolderAccessUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(
        folderId: Int, userId: Int
    ) = folderRepository.toggleAccess(
        folderId = folderId,
        userId = userId
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