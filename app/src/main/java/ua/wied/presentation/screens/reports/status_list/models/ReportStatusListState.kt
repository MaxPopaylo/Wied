package ua.wied.presentation.screens.reports.status_list.models

import ua.wied.domain.models.report.Report
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class ReportStatusListState(
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val reports: List<Report> = emptyList(),
    val todoReports: List<Report> = emptyList(),
    val inProgressReports: List<Report> = emptyList(),
    val doneReports: List<Report> = emptyList()
): BaseNetworkResponseState