package ua.wied.domain.repository

import ua.wied.domain.models.network.NetworkResponse
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus

interface ReportRepository {
    suspend fun getReportsByInstruction(): NetworkResponse<List<Report>>
    suspend fun createReport(instructionId: Int, title: String, info: String): NetworkResponse<Unit>
    suspend fun updateReportStatus(reportId: Int, status: ReportStatus): NetworkResponse<Unit>
}