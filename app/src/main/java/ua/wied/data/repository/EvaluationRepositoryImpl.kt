package ua.wied.data.repository

import java.time.LocalDateTime
import javax.inject.Inject
import ua.wied.data.datasource.network.api.EvaluationApi
import ua.wied.data.datasource.network.dto.evaluation.CreateEvaluationDto
import ua.wied.data.datasource.network.dto.evaluation.CreateItemEvaluationDto
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.evaluation.ItemEvaluation
import ua.wied.domain.repository.EvaluationRepository

class EvaluationRepositoryImpl @Inject constructor(
    private val api: EvaluationApi
): EvaluationRepository, BaseRepository() {

    override suspend fun getEvaluationByInstructionId(instructionId: Int): FlowResultList<Evaluation> =
        handleGETApiCall (
            apiCall = { api.getEvaluationByInstructionId(instructionId) },
            transform = { response ->
                response.data.evaluations.map { it.toDomain(
                    instructionId = response.data.id,
                    instructionTitle = response.data.title
                ) }
            }
        )

    override suspend fun getEvaluationByEmployeeId(employeeId: Int): FlowResultList<Evaluation> =
        handleGETApiCall (
            apiCall = { api.getEvaluationByEmployeeId(employeeId) },
            transform = { response ->
                response.data.employeeEvaluations.map { it.toDomain(
                    employeeId = response.data.id,
                    employeeName = response.data.name
                ) }
            }
        )

    override suspend fun createEvaluation(
        instructionId: Int,
        employeeId: Int,
        info: String,
        itemsEvaluation: List<ItemEvaluation>,
        createTime: LocalDateTime
    ): UnitFlow =
        handlePOSTApiCall (
            apiCall = {
                api.createEvaluation(
                    instructionId = instructionId,
                    dto = CreateEvaluationDto(
                        employeeId = employeeId,
                        evaluationItems = itemsEvaluation.map { CreateItemEvaluationDto(it.elementId, evaluation = it.evaluation) },
                        createdAt = createTime.toString(),
                        info = info
                    )
                )
            }
        )

}