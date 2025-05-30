package ua.wied.domain.models.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.wied.R
import ua.wied.domain.models.SelectableEnum

enum class Theme(
    @StringRes override val displayName: Int,
    @DrawableRes override val icon: Int
): SelectableEnum {
    DARK(displayName = R.string.dark_theme, icon = R.drawable.icon_moon),
    LIGHT(displayName = R.string.light_theme, icon = R.drawable.icon_sun),
}