package ua.wied.presentation.screens.reports.detail.models

import ua.wied.domain.models.report.Report
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class ReportDetailState (
    override val isRefreshing: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isLoading: Boolean = false,
    val localUser: User? = null,
    val report: Report? = null
): BaseNetworkResponseState