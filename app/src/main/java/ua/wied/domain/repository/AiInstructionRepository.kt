package ua.wied.domain.repository

import ua.wied.domain.models.FlowResult
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.ai.AiResponse

interface AiInstructionRepository {
    suspend fun getAiInstructionHistory(): FlowResultList<AiResponse>

    suspend fun createAiInstruction(
        businessType: String,
        jobPosition: String,
        workTask: String,
        additionalInfo: String
    ): FlowResult<AiResponse>
}