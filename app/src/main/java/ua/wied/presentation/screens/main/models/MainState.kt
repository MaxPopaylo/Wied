package ua.wied.presentation.screens.main.models

import ua.wied.presentation.common.base.BaseState

data class MainState (
    override val isLoading: Boolean = false,
    val isInstructionEditing: Boolean? = null,
    val isElementEditing: Boolean? = null,
    val isElementDeleting: Boolean? = null,
    val isFolderEditing: Boolean? = null,
    val isFabVisible: Boolean = false,
    val fabClick: () -> Unit = {}
): BaseState