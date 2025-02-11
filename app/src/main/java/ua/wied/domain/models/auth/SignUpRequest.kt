package ua.wied.domain.models.auth

data class SignUpRequest(
    val name: String,
    val phone: String,
    val password: String,
    val company: String
)
