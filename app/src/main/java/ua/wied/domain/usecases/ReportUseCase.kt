package ua.wied.domain.usecases

import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.repository.ReportRepository
import javax.inject.Inject

class GetReportsByInstructionUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(instructionId: Int):FlowResultList<Report> =
        reportRepository.getReportsByInstruction(instructionId)
}

class CreateReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(instructionId: Int, title: String, info: String, imageUris: List<String?>) =
        reportRepository.createReport(instructionId, title, info, imageUris)
}

class UpdateReportStatusUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(reportId: Int, status: ReportStatus) =
        reportRepository.updateReportStatus(reportId, status)
}
