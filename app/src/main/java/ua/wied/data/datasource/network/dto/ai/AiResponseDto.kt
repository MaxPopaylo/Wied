package ua.wied.data.datasource.network.dto.ai

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import ua.wied.domain.models.HasId
import ua.wied.domain.models.ai.AiResponse

@JsonClass(generateAdapter = true)
data class AiResponseDto(
    override val id: Int,
    @Json(name = "business_type")
    val businessType: String,
    @Json(name = "job_position")
    val jobPosition: String,
    @Json(name = "work_task")
    val workTask: String,
    @Json(name = "additional_info")
    val additionalInfo: String,
    @Json(name = "ai_response")
    val aiResponse: String,
    @Json(name = "create_time")
    val createdAt: String
): HasId