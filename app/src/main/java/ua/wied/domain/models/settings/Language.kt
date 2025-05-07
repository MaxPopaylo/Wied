package ua.wied.domain.models.settings

import androidx.annotation.StringRes
import ua.wied.R

enum class Language(
    val value: String,
    @StringRes val displayName: Int
) {
    ENGLISH(value = "en", displayName = R.string.english),
    UKRAINIAN(value = "uk", displayName = R.string.ukrainian),
    RUSSIAN(value = "ru", displayName = R.string.russian)
}