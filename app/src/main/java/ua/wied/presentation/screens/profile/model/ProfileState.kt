package ua.wied.presentation.screens.profile.model

import ua.wied.domain.models.settings.Settings
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class ProfileState(
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val user: User? = null,
    val settings: Settings = Settings.getDefaultSettings()
): BaseNetworkResponseState