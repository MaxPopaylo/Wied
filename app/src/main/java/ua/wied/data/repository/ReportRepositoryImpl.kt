package ua.wied.data.repository

import android.content.Context
import ua.wied.data.datasource.network.api.ReportApi
import ua.wied.data.datasource.network.dto.report.CreateReportDto
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val api: ReportApi,
    private val context: Context
): BaseRepository(), ReportRepository {
    override suspend fun getReportsByInstruction(instructionId: Int): FlowResultList<Report> =
        handleGETApiCall(
            apiCall = { api.getAllByInstruction(instructionId) },
            transform = { response ->
                response.data.toReportsDomain()
            }
        )

    override suspend fun createReport(instructionId: Int, title: String, info: String, imageUris: List<String?>) =
        handlePOSTApiCall(
            apiCall = {
                val images = convertImagesToMultipartList(context, imageUris)
                api.createReport(
                    instructionId = instructionId,
                    files = images,
                    dto = CreateReportDto(
                        title = title,
                        info = info
                    )
                )
            }
        )

    override suspend fun updateReportStatus(reportId: Int, status: ReportStatus) {
        TODO("Not yet implemented")
    }
}