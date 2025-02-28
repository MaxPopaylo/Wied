package ua.wied.domain.models.auth

data class SignInRequest (
    val login: String,
    val password: String
)