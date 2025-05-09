package ua.wied.presentation.common.base

interface BaseNetworkResponseState : BaseState {
    override val isLoading: Boolean
    val isRefreshing: Boolean
    val isEmpty: Boolean
    val isNotInternetConnection: Boolean
    val requestBeforeNetworkError: () -> Unit
}