package ua.wied.domain.models.report

import com.squareup.moshi.Json
import ua.wied.domain.models.HasId
import java.time.LocalDateTime

data class Report (
    override val id: Int,
    val instructionId: Int,
    val info: String,
    val imageUrls: List<String>,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val status: OrderStatus
): HasId


enum class OrderStatus{
    @Json(name = "todo")
    TODO,
    @Json(name = "in_progress")
    IN_PROGRESS,
    @Json(name = "done")
    DONE
}