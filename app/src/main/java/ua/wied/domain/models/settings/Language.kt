package ua.wied.domain.models.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.wied.R
import ua.wied.domain.SelectableEnum

enum class Language(
    val value: String,
    @StringRes override val displayName: Int,
    @DrawableRes override val icon: Int
): SelectableEnum {
    ENGLISH(value = "en", displayName = R.string.english, icon = R.drawable.icon_flag_en),
    UKRAINIAN(value = "uk", displayName = R.string.ukrainian, icon = R.drawable.icon_flag_ua),
    RUSSIAN(value = "ru", displayName = R.string.russian, icon = R.drawable.icon_flag_ru)
}