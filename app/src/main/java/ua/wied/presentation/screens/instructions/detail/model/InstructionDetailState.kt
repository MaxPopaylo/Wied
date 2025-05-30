package ua.wied.presentation.screens.instructions.detail.model

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class InstructionDetailState (
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val updateResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    val instruction: Instruction? = null,
    val lastItemOrderNum: Int? = null
): BaseNetworkResponseState