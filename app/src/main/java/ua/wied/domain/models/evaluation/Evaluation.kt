package ua.wied.domain.models.evaluation

import kotlinx.serialization.Serializable
import ua.wied.domain.models.HasId
import ua.wied.domain.models.report.UserSummary
import ua.wied.domain.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Evaluation (
    override val id: Int,
    val employee: UserSummary,
    val manager: UserSummary,
    val evaluation: Double,
    val itemsEvaluation: List<ItemEvaluation>,
    @Serializable(with = LocalDateTimeSerializer::class) val createTime: LocalDateTime,
): HasId

@Serializable
data class ItemEvaluation (
    val title: String,
    val evaluation: Double
)
