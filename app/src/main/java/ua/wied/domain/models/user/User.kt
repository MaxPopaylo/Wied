package ua.wied.domain.models.user

data class User (
    val id: Int,
    val login: String,
    val name: String,
    val phone: String,
    val email: String,
    val company: String,
    val info: String,
    val role: Role
)