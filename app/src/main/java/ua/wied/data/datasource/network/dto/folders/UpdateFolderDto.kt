package ua.wied.data.datasource.network.dto.folders

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateFolderDto (
    val title: String,
    @Json(name = "order_num")
    val orderNum: Int
)