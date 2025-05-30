package ua.wied.domain.models.user

import kotlinx.serialization.Serializable
import ua.wied.domain.models.HasId

@Serializable
data class User (
    override val id: Int,
    val login: String,
    val name: String,
    val phone: String,
    val email: String?,
    val company: String,
    val info: String?,
    val role: Role
): HasId