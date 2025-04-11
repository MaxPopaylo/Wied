package ua.wied.data.datasource.network.dto.instruction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Instruction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JsonClass(generateAdapter = true)
data class InstructionItemDto(
    val title: String,
    @Json(name = "order_num")
    val orderNum: Int,
    @Json(name = "video_url")
    val videoUrl: String?,
    @Json(name = "instruction_id")
    val instructionId: Int,
    val id: Int
) {
    fun toDomain(): Element {
        return Element(
            id = id,
            title = title,
            videoUrl = videoUrl,
            instructionId = instructionId,
            orderNum = orderNum
        )
    }
}

@JsonClass(generateAdapter = true)
data class InstructionDto(
    @Json(name = "create_time")
    val createTime: String,
    @Json(name = "update_time")
    val updateTime: String,
    val title: String,
    @Json(name = "order_num")
    val orderNum: Int,
    @Json(name = "folder_id")
    val folderId: Int,
    val id: Int,
    @Json(name = "poster_url")
    val posterUrl: String?,
    val items: List<InstructionItemDto>
) {
    fun toDomain(): Instruction {
        return Instruction(
            id = id,
            title = title,
            folderId = folderId,
            posterUrl = posterUrl,
            elements = items.map { it.toDomain() },
            createTime = LocalDateTime.parse(createTime, DateTimeFormatter.ISO_DATE_TIME),
            updateTime = LocalDateTime.parse(updateTime, DateTimeFormatter.ISO_DATE_TIME),
            orderNum = orderNum
        )
    }
}