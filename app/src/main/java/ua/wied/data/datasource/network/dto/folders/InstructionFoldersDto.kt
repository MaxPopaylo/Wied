package ua.wied.data.datasource.network.dto.folders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ua.wied.data.datasource.network.dto.instruction.InstructionDto
import ua.wied.domain.models.folder.Access
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction

@JsonClass(generateAdapter = true)
data class InstructionFoldersDto(
    val id: Int,
    val title: String,
    @Json(name = "order_num")
    val orderNum: Int,
    val accesses: List<AccessDto>,
    val instructions: List<InstructionDto>
) {
    fun toDomain(): Folder<Instruction> =
        Folder(
            id = id,
            title = title,
            items = instructions.map { it.toDomain() },
            accesses = accesses.map { it.toDomain() },
            orderNum = orderNum
        )
}

@JsonClass(generateAdapter = true)
data class AccessDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
) {
    fun toDomain(): Access =
        Access(
            id = id,
            name = name
        )
}