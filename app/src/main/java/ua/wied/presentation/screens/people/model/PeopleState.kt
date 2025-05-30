package ua.wied.presentation.screens.people.model

import ua.wied.domain.models.user.User
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class PeopleState(
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val search: String = "",
    val employees: List<User> = emptyList()
): BaseNetworkResponseState