package ua.wied.domain.models.instruction

import kotlinx.serialization.Serializable
import ua.wied.domain.models.DragAndDropItem
import ua.wied.domain.serializer.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Instruction(
    override val id: Int,
    val title: String,
    val folderId: Int,
    val posterUrl: String?,
    val elements: List<Element>,
    @Serializable(with = LocalDateTimeSerializer::class)  val createTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)  val updateTime: LocalDateTime,
    override val orderNum: Int
): DragAndDropItem

@Serializable
data class Element(
    val id: Int,
    val title: String,
    val info: String?,
    val videoUrl: String?,
    val instructionId: Int,
    val orderNum: Int
)