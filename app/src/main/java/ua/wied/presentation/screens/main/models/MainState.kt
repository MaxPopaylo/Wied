package ua.wied.presentation.screens.main.models

import ua.wied.presentation.common.base.BaseState

data class MainState (
    override val isLoading: Boolean = false,
    val isInstructionEditing: Boolean = false,
    val isElementEditing: Boolean = false
): BaseState