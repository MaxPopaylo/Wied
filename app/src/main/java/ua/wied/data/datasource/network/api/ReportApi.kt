package ua.wied.data.datasource.network.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ua.wied.data.datasource.network.dto.WrappedResponse
import ua.wied.data.datasource.network.dto.instruction.InstructionWithReportsDto
import ua.wied.data.datasource.network.dto.report.CreateReportDto

interface ReportApi {

    @GET("api/instructions/{instruction_id}/reports")
    suspend fun getAllByInstruction(
        @Path("instruction_id") instructionId: Int
    ): WrappedResponse<InstructionWithReportsDto>

    @Multipart
    @POST("api/reports/create/{instruction_id}")
    suspend fun createReport(
        @Path("instruction_id") instructionId: Int,
        @Part("report") dto: CreateReportDto,
        @Part files: List<MultipartBody.Part>
    ): Response<Any>

}