package ua.wied.domain.models.folder

import ua.wied.domain.models.HasId

data class Folder<T : HasId> (
    override val id: Int,
    val title: String,
    val items: List<T>
): HasId