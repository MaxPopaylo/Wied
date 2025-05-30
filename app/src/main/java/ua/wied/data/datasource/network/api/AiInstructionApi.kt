package ua.wied.data.datasource.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ua.wied.data.datasource.network.dto.WrappedResponse
import ua.wied.data.datasource.network.dto.ai.AiResponseDto
import ua.wied.data.datasource.network.dto.ai.CreateAiInstructionDto

interface AiInstructionApi {

    @GET("api/ai/history")
    suspend fun getAiHistory(
    ): WrappedResponse<List<AiResponseDto>>

    @POST("api/ai/instruction_example")
    suspend fun createAiInstruction(
        @Body dto: CreateAiInstructionDto
    ): WrappedResponse<AiResponseDto>
}