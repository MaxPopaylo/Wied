package ua.wied.data.datasource.network.dto.evaluation

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.report.UserSummary

@JsonClass(generateAdapter = true)
data class InstructionEvaluationsDto(
    val id: Int,
    val title: String,
    val evaluations: List<InstructionEvaluationDto>
)

@JsonClass(generateAdapter = true)
data class InstructionEvaluationDto(
    val id: Int,
    val evaluation: Double,
    @Json(name = "manager")
    val manager: ManagerDto,
    @Json(name = "employee")
    val employee: EmployeeDto,
    val info: String,
    @Json(name = "create_time")
    val createTime: String,
    @Json(name = "evaluation_items")
    val evaluationItems: List<ItemEvaluationDto>
) {
    fun toDomain(
        instructionId: Int,
        instructionTitle: String,
    ): Evaluation {
        return Evaluation(
            id = id,
            instructionId = instructionId,
            instructionTitle = instructionTitle,
            employee = UserSummary(employee.id, employee.name),
            manager = UserSummary(manager.id, manager.name),
            evaluation = evaluation.toDouble(),
            itemsEvaluation = evaluationItems.map { it.toDomain() },
            createTime = LocalDateTime.parse(createTime, DateTimeFormatter.ISO_DATE_TIME)
        )
    }
}

@JsonClass(generateAdapter = true)
data class ManagerDto(
    val id: Int,
    val name: String
)

@JsonClass(generateAdapter = true)
data class EmployeeDto(
    val id: Int,
    val name: String
)