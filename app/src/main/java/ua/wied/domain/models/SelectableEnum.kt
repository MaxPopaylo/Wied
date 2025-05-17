package ua.wied.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface SelectableEnum {
    @get:StringRes
    val displayName: Int

    @get:DrawableRes
    val icon: Int
}