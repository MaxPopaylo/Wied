package ua.wied.presentation.screens.ai_instruction.model

import ua.wied.domain.models.ai.AiResponse
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class AiInstructionHistoryState (
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val history: List<AiResponse> = emptyList(),
    val shouldShowSummary: Boolean = false
): BaseNetworkResponseState