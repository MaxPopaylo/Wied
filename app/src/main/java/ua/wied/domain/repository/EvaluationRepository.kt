package ua.wied.domain.repository

import ua.wied.data.datasource.network.dto.evaluation.CreateEvaluationDto
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.evaluation.Evaluation

interface EvaluationRepository {
    suspend fun getEvaluationByInstructionId(instructionId: Int): FlowResultList<Evaluation>

    suspend fun getEvaluationByEmployeeId(employeeId: Int): FlowResultList<Evaluation>

    suspend fun createEvaluation(
        instructionId: Int,
        dto: CreateEvaluationDto
    ): UnitFlow
}