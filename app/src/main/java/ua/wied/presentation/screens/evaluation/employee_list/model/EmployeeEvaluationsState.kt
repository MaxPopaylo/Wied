package ua.wied.presentation.screens.evaluation.employee_list.model

import ua.wied.domain.models.evaluation.DateRange
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class EmployeeEvaluationsState(
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val employee: User? = null,
    val search: String = "",
    val dateRange: DateRange = DateRange(),
    val evaluations: List<Evaluation> = emptyList(),
): BaseNetworkResponseState