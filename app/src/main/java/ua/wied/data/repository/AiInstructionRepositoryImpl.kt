package ua.wied.data.repository

import java.time.LocalDateTime
import javax.inject.Inject
import ua.wied.data.datasource.network.api.AiInstructionApi
import ua.wied.data.datasource.network.dto.ai.CreateAiInstructionDto
import ua.wied.domain.models.FlowResult
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.ai.AiResponse
import ua.wied.domain.repository.AiInstructionRepository

class AiInstructionRepositoryImpl @Inject constructor(
    private val api: AiInstructionApi
): AiInstructionRepository, BaseRepository() {
    override suspend fun getAiInstructionHistory(): FlowResultList<AiResponse> =
        handleGETApiCall(
            apiCall = { api.getAiHistory() },
            transform = {
                it.data.map {
                    AiResponse(
                        id = it.id,
                        businessType = it.businessType,
                        jobPosition = it.jobPosition,
                        workTask = it.workTask,
                        additionalInfo = it.additionalInfo,
                        aiResponse = it.aiResponse,
                        createdAt = LocalDateTime.parse(it.createdAt)
                    )
                }
            }
        )

    override suspend fun createAiInstruction(
        businessType: String,
        jobPosition: String,
        workTask: String,
        additionalInfo: String
    ): FlowResult<AiResponse> =
        handlePOSTApiCall(
            apiCall = {
                api.createAiInstruction(
                    dto = CreateAiInstructionDto(
                        businessType = businessType,
                        jobPosition = jobPosition,
                        workTask = workTask,
                        additionalInfo = additionalInfo
                    )
                )
            },
            transform = {
                AiResponse(
                    id = it.data.id,
                    businessType = it.data.businessType,
                    jobPosition = it.data.jobPosition,
                    workTask = it.data.workTask,
                    additionalInfo = it.data.additionalInfo,
                    aiResponse = it.data.aiResponse,
                    createdAt = LocalDateTime.parse(it.data.createdAt)
                )
            }
        )
}