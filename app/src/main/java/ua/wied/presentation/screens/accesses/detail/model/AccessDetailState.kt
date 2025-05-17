package ua.wied.presentation.screens.accesses.detail.model

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class AccessDetailState (
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val employees: List<User> = emptyList(),
    val updateResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    val folder: Folder<Instruction>? = null
): BaseNetworkResponseState