package ua.wied.domain.models.ai

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime
import ua.wied.domain.models.HasId

@JsonClass(generateAdapter = true)
data class AiResponse(
    override val id: Int,
    val businessType: String,
    val jobPosition: String,
    val workTask: String,
    val additionalInfo: String,
    val aiResponse: String,
    val createdAt: LocalDateTime
): HasId