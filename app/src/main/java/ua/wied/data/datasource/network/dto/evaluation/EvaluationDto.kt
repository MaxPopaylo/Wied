package ua.wied.data.datasource.network.dto.evaluation

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import ua.wied.domain.models.evaluation.ItemEvaluation

@JsonClass(generateAdapter = true)
data class CreateEvaluationDto (
    @Json(name = "employee_id")
    val employeeId: Int,
    @Json(name = "evaluation_items")
    val evaluationItems: List<CreateItemEvaluationDto>,
    @Json(name = "create_time")
    val createdAt: String,
    val info: String
)

@JsonClass(generateAdapter = true)
data class ItemEvaluationDto (
    @Json(name = "instruction_item_id")
    val elementId: Int,
    @Json(name = "item_title")
    val title: String,
    val evaluation: Double
) {
    fun toDomain(): ItemEvaluation {
        return ItemEvaluation(
            elementId = elementId,
            title = title,
            evaluation = evaluation
        )
    }
}

@JsonClass(generateAdapter = true)
data class CreateItemEvaluationDto (
    @Json(name = "instruction_item_id")
    val elementId: Int,
    val evaluation: Double,
    val info: String = ""
)