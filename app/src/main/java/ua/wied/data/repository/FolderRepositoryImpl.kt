package ua.wied.data.repository

import ua.wied.data.datasource.network.api.FolderApi
import ua.wied.domain.models.FlowResultList
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

}