package ua.wied.domain.repository

import java.time.LocalDateTime
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.evaluation.ItemEvaluation

interface EvaluationRepository {
    suspend fun getEvaluationByInstructionId(instructionId: Int): FlowResultList<Evaluation>

    suspend fun getEvaluationByEmployeeId(employeeId: Int): FlowResultList<Evaluation>

    suspend fun createEvaluation(
        instructionId: Int,
        employeeId: Int,
        info: String,
        itemsEvaluation: List<ItemEvaluation>,
        createTime: LocalDateTime
    ): UnitFlow
}