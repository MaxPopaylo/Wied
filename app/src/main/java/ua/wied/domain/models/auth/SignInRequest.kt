package ua.wied.domain.models.auth

data class SignInRequest (
    val phone: String,
    val password: String
)