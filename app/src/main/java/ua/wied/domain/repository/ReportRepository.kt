package ua.wied.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus

interface ReportRepository {
    suspend fun getReportsByInstruction(): Flow<List<Report>>
    suspend fun createReport(instructionId: Int, title: String, info: String)
    suspend fun updateReportStatus(reportId: Int, status: ReportStatus)
}