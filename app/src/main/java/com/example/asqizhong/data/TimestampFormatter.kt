package com.example.asqizhong.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object TimestampFormatter {
    private const val PATTERN = "yyyy-MM-dd HH:mm"

    fun format(
        millis: Long,
        locale: Locale = Locale.getDefault(),
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val formatter = SimpleDateFormat(PATTERN, locale)
        formatter.timeZone = timeZone
        return formatter.format(Date(millis))
    }
}
