package ua.wied.data.datasource.network.dto.evaluation

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class CreateEvaluationDto (
    val employeeId: Int,
    @Json(name = "evaluation_items")
    val evaluationItems: List<CreateItemEvaluationDto>,
    val createdAt: LocalDateTime,
    val info: String
)

@JsonClass(generateAdapter = true)
data class ItemEvaluationDto (
    @Json(name = "instruction_item_id")
    val elementId: Int,
    @Json(name = "item_title")
    val title: String,
    val evaluation: Double
)

@JsonClass(generateAdapter = true)
data class CreateItemEvaluationDto (
    @Json(name = "instruction_item_id")
    val elementId: Int,
    val evaluation: Double,
    val info: String = ""
)