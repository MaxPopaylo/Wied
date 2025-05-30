package ua.wied.data.datasource.network.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ua.wied.data.datasource.network.dto.DtoWrapper
import ua.wied.data.datasource.network.dto.instruction.CreateElementDto
import ua.wied.data.datasource.network.dto.instruction.CreateInstructionDto
import ua.wied.data.datasource.network.dto.instruction.InstructionDto

interface InstructionApi {

    @Multipart
    @POST("api/instructions")
    suspend fun createInstruction(
        @Part("instruction") dto: CreateInstructionDto,
        @Part file: MultipartBody.Part?
    ): Response<Any>

    @Multipart
    @PUT("api/instructions/{instruction_id}")
    suspend fun updateInstruction(
        @Path("instruction_id") instructionId: Int,
        @Part("instruction") dto: CreateInstructionDto,
        @Part file: MultipartBody.Part?
    ): Response<Any>

    @DELETE("api/instructions/{instruction_id}")
    suspend fun deleteInstruction(
        @Path("instruction_id") instructionId: Int,
    ): Response<Any>

    @GET("api/instructions/{instruction_id}")
    suspend fun getInstruction(
        @Path("instruction_id") instructionId: Int,
    ): Response<DtoWrapper<InstructionDto>>

    @PUT("api/instructions/toggle_access/{instruction_id}/{user_id}")
    suspend fun toggleInstructionAccess(
        @Path("instruction_id") instructionId: Int,
        @Path("user_id") userId: Int
    ): Response<Any>

    @PATCH("api/instructions/reorder/{instruction_id}")
    suspend fun reorderInstruction(
        @Path("instruction_id") instructionId: Int,
        @Query("new_order") newOrder: Int,
        @Query("folder_id") folderId: Int
    ): Response<Any>

    @Multipart
    @POST("api/instructions/{instruction_id}/items")
    suspend fun createElement(
        @Path("instruction_id") instructionId: Int,
        @Part("item") dto: CreateElementDto,
        @Part file: MultipartBody.Part?
    ): Response<Any>

    @Multipart
    @PUT("api/instructions/{instruction_id}/items/{item_id}")
    suspend fun updateElement(
        @Path("item_id") elementId: Int,
        @Path("instruction_id") instructionId: Int,
        @Part("item") dto: CreateElementDto,
        @Part file: MultipartBody.Part?
    ): Response<Any>

    @DELETE("api/instructions/{instruction_id}/items/{item_id}")
    suspend fun deleteElement(
        @Path("item_id") elementId: Int,
        @Path("instruction_id") instructionId: Int,
    ): Response<Any>

}