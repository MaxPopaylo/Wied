package ua.wied.presentation.common.utils.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatToShortDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd")
    return this.format(formatter)
}