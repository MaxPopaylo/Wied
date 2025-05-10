package ua.wied.presentation.screens.instructions.create.models

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class CreateInstructionState (
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val createResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    val title: String = "",
    val posterUrl: String? = null
): BaseNetworkResponseState