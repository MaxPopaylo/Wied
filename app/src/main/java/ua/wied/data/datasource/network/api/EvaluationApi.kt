package ua.wied.data.datasource.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ua.wied.data.datasource.network.dto.WrappedResponse
import ua.wied.data.datasource.network.dto.evaluation.CreateEvaluationDto
import ua.wied.data.datasource.network.dto.evaluation.EmployeeEvaluationsDto
import ua.wied.data.datasource.network.dto.evaluation.InstructionEvaluationsDto

interface EvaluationApi {
    @GET("api/evaluations/instruction/{instruction_id}")
    suspend fun getEvaluationByInstructionId(
        @Path("instruction_id") instructionId: Int,
    ): WrappedResponse<InstructionEvaluationsDto>

    @GET("api/evaluations/employee/{employee_id}")
    suspend fun getEvaluationByEmployeeId(
        @Path("employee_id") employeeId: Int,
    ): WrappedResponse<EmployeeEvaluationsDto>

    @POST("api/evaluations/create/{instruction_id}")
    suspend fun createEvaluation(
        @Path("instruction_id") instructionId: Int,
        @Body dto: CreateEvaluationDto
    ): Response<Any>
}