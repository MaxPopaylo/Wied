package ua.wied.data.datasource.network.dto.evaluation

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.report.UserSummary


@JsonClass(generateAdapter = true)
data class EmployeeEvaluationsDto(
    val id: Int,
    val name: String,
    @Json(name = "employee_evaluations")
    val employeeEvaluations: List<EmployeeEvaluationDto>
)

@JsonClass(generateAdapter = true)
data class EmployeeEvaluationDto(
    val id: Int,
    val evaluation: Double,
    @Json(name = "instruction_title")
    val instructionTitle: String,
    @Json(name = "manager")
    val manager: ManagerDto,
    val info: String,
    @Json(name = "create_time")
    val createTime: String,
    @Json(name = "evaluation_items")
    val evaluationItems: List<ItemEvaluationDto>
) {
    fun toDomain(
        employeeId: Int,
        employeeName: String
    ): Evaluation {
        return Evaluation(
            id = id,
            instructionId = 0,
            instructionTitle = instructionTitle,
            employee = UserSummary(employeeId, employeeName),
            manager = UserSummary(manager.id, manager.name),
            evaluation = evaluation.toDouble(),
            itemsEvaluation = evaluationItems.map { it.toDomain() },
            createTime = LocalDateTime.parse(createTime, DateTimeFormatter.ISO_DATE_TIME)
        )
    }
}