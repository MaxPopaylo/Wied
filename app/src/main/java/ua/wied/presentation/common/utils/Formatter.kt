package ua.wied.presentation.common.utils

import ua.wied.domain.models.evaluation.DateRange
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import kotlin.text.format

object Formatter {

    fun LocalDateTime.formatToShortDate(): String {
        val formatter = DateTimeFormatter.ofPattern("yy.MM.dd")
        return this.format(formatter)
    }


    fun formatDate(timeInMillis: Long, pattern: String, locale: Locale = Locale.US): String {
        val calender = Calendar.getInstance()
        calender.timeInMillis = timeInMillis
        val dateFormat = SimpleDateFormat(pattern, locale)
        return dateFormat.format(calender.timeInMillis)
    }

    fun formatDate(dateTime: LocalDateTime, pattern: String, locale: Locale = Locale.US): String {
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)
        return dateTime.format(formatter)
    }

    fun formatDateRange(
        dateRange: DateRange,
        startPattern: String,
        endPattern: String,
        locale: Locale = Locale.US
    ): String {
        val start = formatDate(dateRange.startDate!!, startPattern, locale)
        val end = formatDate(dateRange.endDate!!, endPattern, locale)
        return "$start - $end"
    }
}

object DateFormats {
    const val FULL_DATE_TIME = "MMMM dd, h:mm a"
    const val FULL_DATE = "MM.dd.yyyy"
    const val YEAR = "yyyy"
    const val MONTH = "MMM"
    const val MONTH_YEAR = "MMM yyyy"
    const val MONTH_DAY = "MMMM, dd"
    const val WEEK_DAY ="EEE"
    const val DAY ="d"
    const val TIME = "h:mm a"
    const val TIME_HOURS = "h a"
}