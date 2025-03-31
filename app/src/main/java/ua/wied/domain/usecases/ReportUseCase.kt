package ua.wied.domain.usecases

import ua.wied.domain.models.network.NetworkResponse
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.repository.ReportRepository
import javax.inject.Inject

class GetReportsByInstructionUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(): NetworkResponse<List<Report>> =
        reportRepository.getReportsByInstruction()
}

class CreateReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(instructionId: Int, title: String, info: String) {
        reportRepository.createReport(instructionId, title, info)
    }
}

class UpdateReportStatusUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: Int, status: ReportStatus) {
        reportRepository.updateReportStatus(reportId, status)
    }
}
