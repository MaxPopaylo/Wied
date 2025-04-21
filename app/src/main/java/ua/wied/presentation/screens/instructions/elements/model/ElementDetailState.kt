package ua.wied.presentation.screens.instructions.elements.model

import ua.wied.domain.models.instruction.Element
import ua.wied.presentation.common.base.BaseState

data class ElementDetailState (
    override val isLoading: Boolean = false,
    val element: Element? = null
): BaseState