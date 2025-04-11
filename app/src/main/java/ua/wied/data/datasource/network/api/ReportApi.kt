package ua.wied.data.datasource.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import ua.wied.data.datasource.network.dto.Response
import ua.wied.data.datasource.network.dto.instruction.InstructionWithReportsDto

interface ReportApi {

    @GET("api/instructions/{instruction_id}/reports")
    suspend fun getAllByInstruction(
        @Path("instruction_id") instructionId: Int
    ): Response<InstructionWithReportsDto>

}