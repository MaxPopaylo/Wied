package ua.wied.presentation.screens.ai_instruction.create.model

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class CreateAiRequestState (
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val createResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    val businessType: String = "",
    val jobPosition: String = "",
    val workTask: String = "",
    val additionalInfo: String = ""
): BaseNetworkResponseState