package ua.wied.data.datasource.network.dto.ai

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateAiInstructionDto (
    @Json(name = "business_type")
    val businessType: String,
    @Json(name = "job_position")
    val jobPosition: String,
    @Json(name = "work_task")
    val workTask: String,
    @Json(name = "additional_info")
    val additionalInfo: String
)
