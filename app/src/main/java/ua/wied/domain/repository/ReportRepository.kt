package ua.wied.domain.repository

import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus

interface ReportRepository {
    suspend fun getReportsByInstruction(): FlowResultList<Report>
    suspend fun createReport(instructionId: Int, title: String, info: String)
    suspend fun updateReportStatus(reportId: Int, status: ReportStatus)
}