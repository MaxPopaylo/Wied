package ua.wied.presentation.screens.evaluation.model

import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class EvaluationsState(
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val search: String = "",
    val folders: List<Folder<Instruction>> = emptyList(),
): BaseNetworkResponseState