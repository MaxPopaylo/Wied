package ua.wied.presentation.screens.accesses.create.model

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class CreateFolderState(
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val createResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    val title: String = ""
): BaseNetworkResponseState