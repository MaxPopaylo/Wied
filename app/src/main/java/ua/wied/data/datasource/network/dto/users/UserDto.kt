package ua.wied.data.datasource.network.dto.users

import ua.wied.domain.models.user.Role

data class UserDto (
    val id: Int,
    val login: String,
    val name: String,
    val phone: String,
    val email: String?,
    val company: String,
    val info: String?,
    val role: Role,
    val token: String
)