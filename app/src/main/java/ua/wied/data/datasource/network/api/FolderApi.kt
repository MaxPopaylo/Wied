package ua.wied.data.datasource.network.api

import retrofit2.http.GET
import ua.wied.data.datasource.network.dto.Response
import ua.wied.data.datasource.network.dto.folders.InstructionFoldersDto
import ua.wied.data.datasource.network.dto.folders.InstructionFoldersWithReportsCountDto

interface FolderApi {

    @GET("api/folders/all")
    suspend fun getAll(): Response<List<InstructionFoldersDto>>

    @GET("api/folders/all_with_reports_count")
    suspend fun getAllWithReportsCount(): Response<List<InstructionFoldersWithReportsCountDto>>

}