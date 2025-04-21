package ua.wied.data.datasource.network.dto.instruction

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ua.wied.data.datasource.network.dto.report.ReportDto
import ua.wied.domain.models.report.Report

@JsonClass(generateAdapter = true)
data class InstructionWithReportsDto(
    val id: Int,
    @Json(name = "folder_id")
    val folderId: Int,
    val title: String,
    @Json(name = "poster_url")
    val posterUrl: String?,
    @Json(name = "order_num")
    val orderNum: Int,
    @Json(name = "create_time")
    val createTime: String,
    @Json(name = "update_time")
    val updateTime: String,
    val reports: List<ReportDto>
) {
    fun toReportsDomain(): List<Report> {
        return reports.map { it.toDomain() }
    }
}