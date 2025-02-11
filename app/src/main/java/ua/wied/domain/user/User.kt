package ua.wied.domain.user

import java.util.UUID

data class User (
    val id: UUID,
    val name: String,
    val phone: String,
    val company: String
)