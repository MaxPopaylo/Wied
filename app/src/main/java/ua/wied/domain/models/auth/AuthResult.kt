package ua.wied.domain.models.auth

import androidx.annotation.StringRes

sealed class AuthResult {
    data object Success: AuthResult()
    data class Error(@StringRes val errorMessage: Int) : AuthResult()
}