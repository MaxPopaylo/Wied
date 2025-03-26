package ua.wied.domain.models.report

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable
import ua.wied.domain.models.HasId
import ua.wied.domain.models.user.User
import ua.wied.domain.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Report (
    override val id: Int,
    val instructionId: Int,
    val title: String,
    val info: String,
    val imageUrls: List<String>,
    @Serializable(with = LocalDateTimeSerializer::class) val createTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class) val updateTime: LocalDateTime,
    val creator: User,
    val status: ReportStatus
): HasId


enum class ReportStatus{
    @Json(name = "todo")
    TODO,
    @Json(name = "in_progress")
    IN_PROGRESS,
    @Json(name = "done")
    DONE
}