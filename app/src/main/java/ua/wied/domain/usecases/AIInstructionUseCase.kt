package ua.wied.domain.usecases

import javax.inject.Inject
import ua.wied.domain.models.FlowResult
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.ai.AiResponse
import ua.wied.domain.repository.AiInstructionRepository

class CreateAIInstructionUseCase @Inject constructor(
    private val aiInstructionRepository: AiInstructionRepository
) {
    suspend operator fun invoke(
        businessType: String,
        jobPosition: String,
        workTask: String,
        additionalInfo: String
    ): FlowResult<AiResponse> =
        aiInstructionRepository.createAiInstruction(
            businessType = businessType,
            jobPosition = jobPosition,
            workTask = workTask,
            additionalInfo = additionalInfo
        )
}

class GetAIInstructionHistoryUseCase @Inject constructor(
    private val aiInstructionRepository: AiInstructionRepository
) {
    suspend operator fun invoke(): FlowResultList<AiResponse> =
        aiInstructionRepository.getAiInstructionHistory()
}