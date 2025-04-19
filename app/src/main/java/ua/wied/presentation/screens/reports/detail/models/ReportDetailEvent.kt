package ua.wied.presentation.screens.reports.detail.models

import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus

sealed class ReportDetailEvent {
    data class LoadData(val report: Report): ReportDetailEvent()
    data class ChangeStatus(val status: ReportStatus): ReportDetailEvent()
}