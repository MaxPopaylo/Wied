package ua.wied.domain.usecases

import ua.wied.data.datasource.network.dto.evaluation.CreateEvaluationDto
import ua.wied.data.datasource.network.dto.evaluation.CreateItemEvaluationDto
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.evaluation.ItemEvaluation
import ua.wied.domain.repository.EvaluationRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetInstructionEvaluations @Inject constructor(
    private val evaluationRepository: EvaluationRepository
) {
    suspend operator fun invoke(instructionId: Int): FlowResultList<Evaluation> =
        evaluationRepository.getEvaluationByInstructionId(instructionId)
}

class GetEmployeeEvaluations @Inject constructor(
    private val evaluationRepository: EvaluationRepository
) {
    suspend operator fun invoke(employeeId: Int): FlowResultList<Evaluation> =
        evaluationRepository.getEvaluationByEmployeeId(employeeId)
}

class CreateEvaluationUseCase @Inject constructor(
    private val evaluationRepository: EvaluationRepository
) {
    suspend operator fun invoke(
        instructionId: Int,
        employeeId: Int,
        info: String,
        itemsEvaluation: List<ItemEvaluation>,
        createTime: LocalDateTime
    ): UnitFlow = evaluationRepository.createEvaluation(
        instructionId = instructionId,
        dto = CreateEvaluationDto(
            employeeId = employeeId,
            evaluationItems = itemsEvaluation.map { CreateItemEvaluationDto(it.elementId, evaluation = it.evaluation) },
            createdAt = createTime,
            info = info
        )
    )
}