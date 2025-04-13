package ua.wied.presentation.screens.main.reports.detail.models

import ua.wied.domain.models.report.Report
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.base.BaseState

data class ReportDetailState (
    override val isLoading: Boolean = false,
    val localUser: User? = null,
    val report: Report? = null,
): BaseState