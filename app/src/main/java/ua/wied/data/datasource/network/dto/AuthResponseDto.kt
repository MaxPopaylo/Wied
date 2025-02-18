package ua.wied.data.datasource.network.dto

import ua.wied.domain.models.user.User

data class AuthResponseDto (
    val user: User,
    val token: String
)