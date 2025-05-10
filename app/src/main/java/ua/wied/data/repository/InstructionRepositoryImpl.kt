package ua.wied.data.repository

import android.content.Context
import ua.wied.data.datasource.network.api.InstructionApi
import ua.wied.data.datasource.network.dto.instruction.CreateElementDto
import ua.wied.data.datasource.network.dto.instruction.CreateInstructionDto
import ua.wied.domain.models.FlowResult
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.repository.InstructionRepository
import javax.inject.Inject

class InstructionRepositoryImpl @Inject constructor(
    private val api: InstructionApi,
    private val context: Context
): BaseRepository(), InstructionRepository {

    override suspend fun saveInstruction(
        title: String, posterUrl: String?,
        orderNum: Int, folderId: Int
    ): UnitFlow =
        handlePOSTApiCall (
            apiCall = {
                val image = convertImgUrlToMultipart(context, posterUrl, "file")
                api.createInstruction(
                    dto = CreateInstructionDto(
                        title = title,
                        orderNum = orderNum,
                        folderId = folderId
                    ),
                    file = image
                )
            }
        )

    override suspend fun updateInstruction(
        instructionId: Int, title: String,
        posterUrl: String?, orderNum: Int,
        folderId: Int
    ): UnitFlow =
        handlePUTApiCall (
            apiCall = {
                val image = convertImgUrlToMultipart(context, posterUrl, "file")
                api.updateInstruction(
                    dto = CreateInstructionDto(
                        title = title,
                        orderNum = orderNum,
                        folderId = folderId
                    ),
                    instructionId = instructionId,
                    file = image
                )
            }
        )

    override suspend fun deleteInstruction(instructionId: Int): UnitFlow =
        handleDELETEApiCall (
            apiCall = { api.deleteInstruction(instructionId) }
        )

    override suspend fun getInstruction(instructionId: Int): FlowResult<Instruction> =
        handleGETApiCall (
            apiCall = {
                api.getInstruction(instructionId)
            },
            transform = { it.data.toDomain() }
        )

    override suspend fun saveElement(
        title: String, info: String,
        videoUrl: String?, orderNum: Int,
        instructionId: Int
    ): UnitFlow =
        handlePOSTApiCall (
            apiCall = {
                val video = convertVideoUrlToMultipart(context, videoUrl, "file")
                api.createElement(
                    dto = CreateElementDto(
                        title = title,
                        info = info,
                        orderNum = orderNum
                    ),
                    instructionId = instructionId,
                    file = video
                )
            }
        )

    override suspend fun updateElement(
        elementId: Int, title: String,
        info: String, videoUrl: String?,
        orderNum: Int, instructionId: Int
    ): UnitFlow =
        handlePUTApiCall (
            apiCall = {
                val video = convertVideoUrlToMultipart(context, videoUrl, "file")
                api.updateElement(
                    dto = CreateElementDto(
                        title = title,
                        info = info,
                        orderNum = orderNum
                    ),
                    elementId = elementId,
                    instructionId = instructionId,
                    file = video
                )
            }
        )

    override suspend fun deleteElement(
        elementId: Int, instructionId: Int
    ): UnitFlow =
        handleDELETEApiCall (
            apiCall = { api.deleteElement(elementId, instructionId) }
        )

}