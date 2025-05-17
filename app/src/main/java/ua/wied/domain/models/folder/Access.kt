package ua.wied.domain.models.folder

import kotlinx.serialization.Serializable
import ua.wied.domain.models.HasId

@Serializable
data class Access (
    override val id: Int,
    val name: String
): HasId