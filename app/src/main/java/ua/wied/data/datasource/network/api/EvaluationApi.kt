package ua.wied.data.datasource.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ua.wied.data.datasource.network.dto.WrappedResponse
import ua.wied.data.datasource.network.dto.evaluation.CreateEvaluationDto
import ua.wied.data.datasource.network.dto.folders.UpdateFolderDto
import ua.wied.domain.models.evaluation.Evaluation

interface EvaluationApi {
    @GET("api/evaluations/{instruction_id}")
    suspend fun getEvaluationByInstructionId(
        @Path("instruction_id") instructionId: Int,
    ): WrappedResponse<Evaluation>

    @GET("api/evaluations/{employee_id}")
    suspend fun getEvaluationByEmployeeId(
        @Path("employee_id") employeeId: Int,
    ): WrappedResponse<Evaluation>

    @POST("api/evaluations/{instruction_id}")
    suspend fun createFolder(
        @Path("instruction_id") instructionId: Int,
        @Body dto: CreateEvaluationDto
    ): Response<Any>
}