package ua.wied.data.repository

import ua.wied.data.datasource.network.api.FolderApi
import ua.wied.data.datasource.network.dto.folders.UpdateFolderDto
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.domain.repository.FolderRepository
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val api: FolderApi
) : BaseRepository(), FolderRepository {

    override suspend fun getInstructionFolders(): FlowResultList<Folder<Instruction>> =
        handleGETApiCall(
            apiCall = { api.getAll() },
            transform = { response ->
                response.data.map { it.toDomain() }
            }
        )

    override suspend fun getInstructionWithReportCountFolders(): FlowResultList<Folder<InstructionWithReportCount>> =
        handleGETApiCall(
            apiCall = { api.getAllWithReportsCount() },
            transform = { response ->
                response.data.map { it.toDomain() }
            }
        )

    override suspend fun updateFolder(folderId: Int, orderNum: Int, title: String): UnitFlow =
        handlePUTApiCall (
            apiCall = {
                api.updateFolder(
                    dto = UpdateFolderDto(
                        title = title,
                        orderNum = orderNum
                    ),
                    folderId = folderId
                )
            }
        )

    override suspend fun reorderFolder(folderId: Int, newOrder: Int): UnitFlow =
        handlePUTApiCall (
            apiCall = {
                api.reorderFolder(
                    folderId = folderId,
                    newOrder = newOrder
                )
            }
        )

    override suspend fun deleteFolder(folderId: Int): UnitFlow =
        handleDELETEApiCall (
            apiCall = {
                api.deleteFolder(folderId)
            }
        )

}