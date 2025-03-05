package ua.wied.domain.models.auth

data class SignUpRequest(
    val login: String,
    val name: String,
    val phone: String,
    val email: String,
    val company: String,
    val password: String
)
