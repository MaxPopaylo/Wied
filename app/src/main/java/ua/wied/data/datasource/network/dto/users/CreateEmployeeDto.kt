package ua.wied.data.datasource.network.dto.users

import ua.wied.domain.models.user.Role

data class CreateEmployeeDto(
    val login: String,
    val password: String,
    val name: String,
    val email: String?,
    val phone: String,
    val role: Role,
    val info: String?
)