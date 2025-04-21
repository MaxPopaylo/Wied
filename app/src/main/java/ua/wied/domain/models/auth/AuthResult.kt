package ua.wied.domain.models.auth

import androidx.annotation.StringRes

sealed class AuthResult {
    data class Success(val isManager: Boolean): AuthResult()
    data class Error(@StringRes val errorMessage: Int) : AuthResult()
}