package ua.wied.data.datasource.network.dto.folders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.instruction.InstructionWithReportCount
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class InstructionWithReportsCountDto(
    val title: String,
    @Json(name = "order_num")
    val orderNum: Int,
    @Json(name = "folder_id")
    val folderId: Int,
    val id: Int,
    @Json(name = "poster_url")
    val posterUrl: String?,
    @Json(name = "report_count")
    val reportCount: Int
) {
    fun toDomain(): InstructionWithReportCount {
        val instruction = Instruction(
            id = id,
            title = title,
            folderId = folderId,
            posterUrl = posterUrl,
            elements = emptyList(),
            createTime = LocalDateTime.now(),
            updateTime = LocalDateTime.now(),
            orderNum = orderNum
        )
        return InstructionWithReportCount(
            instruction = instruction,
            reportCount = reportCount
        )
    }
}

@JsonClass(generateAdapter = true)
data class InstructionFoldersWithReportsCountDto(
    val title: String,
    @Json(name = "order_num")
    val orderNum: Int,
    val id: Int,
    val instructions: List<InstructionWithReportsCountDto>
) {
   fun toDomain(): Folder<InstructionWithReportCount> =
       Folder(
           id = id,
           title = title,
           items = instructions.map { it.toDomain() },
           orderNum = orderNum
       )
}