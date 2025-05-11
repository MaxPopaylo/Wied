package ua.wied.data.datasource.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import ua.wied.data.datasource.network.dto.WrappedResponse
import ua.wied.data.datasource.network.dto.folders.InstructionFoldersDto
import ua.wied.data.datasource.network.dto.folders.InstructionFoldersWithReportsCountDto
import ua.wied.data.datasource.network.dto.folders.UpdateFolderDto

interface FolderApi {

    @GET("api/folders/all")
    suspend fun getAll(): WrappedResponse<List<InstructionFoldersDto>>

    @GET("api/folders/all_with_reports_count")
    suspend fun getAllWithReportsCount(): WrappedResponse<List<InstructionFoldersWithReportsCountDto>>

    @PATCH("api/folders/{folder_id}")
    suspend fun updateFolder(
        @Path("folder_id") folderId: Int,
        @Body dto: UpdateFolderDto
    ): Response<Any>

    @PATCH("api/folders/reorder/{folder_id}")
    suspend fun reorderFolder(
        @Path("folder_id") folderId: Int,
        @Query("new_order") newOrder: Int
    ): Response<Any>

    @DELETE("api/folders/{folder_id}")
    suspend fun deleteFolder(
        @Path("folder_id") folderId: Int,
    ): Response<Any>
}