package ua.wied.domain.models.auth

import ua.wied.domain.models.user.Role

data class SignUpRequest(
    val login: String,
    val name: String,
    val phone: String,
    val email: String,
    val company: String,
    val password: String,
    val info: String,
    val role: Role
)