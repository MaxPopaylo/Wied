package ua.wied.domain.models.network

import androidx.annotation.StringRes

sealed class NetworkResponse <T : Any> {
    data class Success<T : Any>(val data: T) : NetworkResponse<T>()
    data class Error(@StringRes val errorMessage: Int) : NetworkResponse<Unit>()
    data object Timeout : NetworkResponse<Unit>()
}