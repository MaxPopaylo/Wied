package ua.wied.domain.models.folder

import kotlinx.serialization.Serializable
import ua.wied.domain.models.DragAndDropItem
import ua.wied.domain.models.HasId

@Serializable
data class Folder<T : HasId> (
    override val id: Int,
    val title: String,
    val items: List<T>,
    override val orderNum: Int
): HasId, DragAndDropItem