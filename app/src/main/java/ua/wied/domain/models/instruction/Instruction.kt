package ua.wied.domain.models.instruction

import ua.wied.domain.models.HasId

data class Instruction(
    override val id: Int,
    val title: String,
    val posterUrl: String,
    val elements: List<Element>
): HasId

data class Element(
    val title: String
)
