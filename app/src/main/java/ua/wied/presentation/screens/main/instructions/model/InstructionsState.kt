package ua.wied.presentation.screens.main.instructions.model

import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.base.BaseNetworkResponseState
import ua.wied.presentation.common.composable.SwipeableItemState

data class InstructionsState(
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    val search: String = "",
    val folders: List<Folder<SwipeableItemState<Instruction>>> = emptyList()
): BaseNetworkResponseState