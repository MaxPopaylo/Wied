package ua.wied.domain.models.instruction

import kotlinx.serialization.Serializable
import ua.wied.domain.models.HasId

@Serializable
data class Instruction(
    override val id: Int,
    val title: String,
    val posterUrl: String,
    val elements: List<Element>
): HasId

@Serializable
data class Element(
    val title: String
)
