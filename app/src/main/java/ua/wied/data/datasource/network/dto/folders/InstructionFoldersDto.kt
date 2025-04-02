package ua.wied.data.datasource.network.dto.folders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ua.wied.data.datasource.network.dto.instruction.InstructionDto
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction

@JsonClass(generateAdapter = true)
data class InstructionFoldersDto(
    @Json(name = "title")
    val title: String,
    @Json(name = "order_num")
    val orderNum: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "accesses")
    val accesses: List<AccessDto>,
    @Json(name = "instructions")
    val instructions: List<InstructionDto>
) {
    fun toDomain(): Folder<Instruction> =
        Folder(
            id = id,
            title = title,
            items = instructions.map { it.toDomain() },
            orderNum = orderNum
        )
}

@JsonClass(generateAdapter = true)
data class AccessDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)