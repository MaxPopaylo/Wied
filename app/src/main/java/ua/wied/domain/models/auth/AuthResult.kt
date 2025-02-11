package ua.wied.domain.models.auth

sealed class AuthResult {
    data object Success: AuthResult()
    data class Error(val errorMessage: String) : AuthResult()
}