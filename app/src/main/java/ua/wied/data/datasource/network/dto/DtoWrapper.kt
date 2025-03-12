package ua.wied.data.datasource.network.dto

data class DtoWrapper<T> (
    val success: Boolean,
    val data: T
)