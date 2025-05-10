package ua.wied.domain.models.user

import com.squareup.moshi.Json

enum class Role(val displayName: String) {
    @Json(name = "owner")
    OWNER("Owner"),
    @Json(name = "admin")
    ADMIN("Admin"),
    @Json(name = "manager")
    MANAGER("Manager"),
    @Json(name = "employee")
    EMPLOYEE("Employee")
}
