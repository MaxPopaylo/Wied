package ua.wied.presentation.screens.people.create.model

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.domain.models.user.Role
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class CreateEmployeeState (
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val createResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    val login: String = "",
    val password: String = "",
    val name: String = "",
    val phone: String = "",
    val role: Role = Role.EMPLOYEE,
    val info: String = "",

    val loginError: String? = null,
    val passwordError: String? = null,
    val nameError: String? = null,
    val phoneError: String? = null,
    val infoError: String? = null
): BaseNetworkResponseState