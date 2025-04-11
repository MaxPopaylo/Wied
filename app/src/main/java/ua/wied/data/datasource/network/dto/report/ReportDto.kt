package ua.wied.data.datasource.network.dto.report

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ua.wied.domain.models.report.Author
import ua.wied.domain.models.report.ImageUrl
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JsonClass(generateAdapter = true)
data class ImageUrlDto(
    val id: Int,
    @Json(name = "image_url")
    val imageUrl: String
) {
    fun toDomain(): ImageUrl {
        return ImageUrl(
            id = id,
            imageUrl = imageUrl
        )
    }
}

@JsonClass(generateAdapter = true)
data class ReportDto(
    val id: Int,
    @Json(name = "instruction_id")
    val instructionId: Int,
    val author: Author,
    val title: String,
    val info: String,
    @Json(name = "image_urls")
    val imageUrls: List<ImageUrlDto>,
    val status: ReportStatus,
    @Json(name = "create_time")
    val createTime: String,
    @Json(name = "update_time")
    val updateTime: String
) {
    fun toDomain(): Report {
        return Report(
            id = id,
            instructionId = instructionId,
            author = author,
            title = title,
            info = info,
            imageUrls = imageUrls.map { it.toDomain() },
            status = status,
            createTime = LocalDateTime.parse(createTime, DateTimeFormatter.ISO_DATE_TIME),
            updateTime = LocalDateTime.parse(updateTime, DateTimeFormatter.ISO_DATE_TIME)
        )
    }
}