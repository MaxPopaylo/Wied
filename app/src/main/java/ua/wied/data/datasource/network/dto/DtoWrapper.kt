package ua.wied.data.datasource.network.dto

import retrofit2.Response

data class DtoWrapper<T> (
    val success: Boolean,
    val data: T
)

typealias WrappedResponse<T> = Response<DtoWrapper<T>>