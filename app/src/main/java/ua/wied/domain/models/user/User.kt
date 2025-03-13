package ua.wied.domain.models.user

import ua.wied.domain.models.HasId

data class User (
    override val id: Int,
    val login: String,
    val name: String,
    val phone: String,
    val email: String,
    val company: String,
    val info: String,
    val role: Role
): HasId