package ua.wied.domain.usecases

import ua.wied.domain.repository.FolderRepository
import ua.wied.domain.repository.InstructionRepository
import javax.inject.Inject

class CreateInstructionUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(
        title: String,
        posterUrl: String?,
        orderNum: Int,
        folderId: Int
    ) = instructionRepository.saveInstruction(
        title = title,
        posterUrl = posterUrl,
        orderNum = orderNum,
        folderId = folderId
    )
}

class UpdateInstructionUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(
        instructionId: Int,
        title: String,
        posterUrl: String?,
        orderNum: Int,
        folderId: Int
    ) = instructionRepository.updateInstruction(
        instructionId = instructionId,
        title = title,
        posterUrl = posterUrl,
        orderNum = orderNum,
        folderId = folderId
    )
}

class ToggleInstructionAccessUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(
        instructionId: Int, userId: Int
    ) = instructionRepository.toggleAccess(
        instructionId = instructionId,
        userId = userId
    )
}

class DeleteInstructionUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(instructionId: Int) =
        instructionRepository.deleteInstruction(instructionId = instructionId)
}

class ReorderInstructionUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(
        instructionId: Int,
        folderId: Int,
        newOrder: Int
    ) = instructionRepository.reorderInstruction(
        instructionId = instructionId,
        folderId = folderId,
        newOrder = newOrder
    )
}

class GetInstructionUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(instructionId: Int) =
        instructionRepository.getInstruction(instructionId = instructionId)
}

class CreateElementUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(
        title: String,
        info: String,
        videoUrl: String?,
        orderNum: Int,
        instructionId: Int
    ) = instructionRepository.saveElement(
        title = title,
        info = info,
        videoUrl = videoUrl,
        orderNum = orderNum,
        instructionId = instructionId
    )
}

class UpdateElementUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(
        elementId: Int,
        title: String,
        info: String?,
        videoUrl: String?,
        orderNum: Int,
        instructionId: Int
    ) = instructionRepository.updateElement(
        elementId = elementId,
        title = title,
        info = info,
        videoUrl = videoUrl,
        orderNum = orderNum,
        instructionId = instructionId
    )
}

class DeleteElementUseCase @Inject constructor(
    private val instructionRepository: InstructionRepository
) {
    suspend operator fun invoke(
        elementId: Int,
        instructionId: Int
    ) = instructionRepository.deleteElement(
        elementId = elementId,
        instructionId = instructionId
    )
}
