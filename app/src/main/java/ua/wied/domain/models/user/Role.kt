package ua.wied.domain.models.user

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.squareup.moshi.Json
import ua.wied.R
import ua.wied.domain.SelectableEnum

enum class Role(
    @StringRes override val displayName: Int,
    @DrawableRes override val icon: Int
): SelectableEnum {
    @Json(name = "owner")
    OWNER(displayName = R.string.role_owner, icon = R.drawable.icon_owner),
    @Json(name = "admin")
    ADMIN(displayName = R.string.role_admin, icon = R.drawable.icon_admin),
    @Json(name = "manager")
    MANAGER(displayName = R.string.role_manager, icon = R.drawable.icon_manager),
    @Json(name = "employee")
    EMPLOYEE(displayName = R.string.role_employee, icon = R.drawable.icon_employee)
}
