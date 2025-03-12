package ua.wied.domain.models.user

import com.squareup.moshi.Json

enum class Role {
    @Json(name = "owner")
    OWNER,
    @Json(name = "admin")
    ADMIN,
    @Json(name = "manager")
    MANAGER,
    @Json(name = "employee")
    EMPLOYEE
}
