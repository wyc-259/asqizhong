package com.example.asqizhong

import com.example.asqizhong.data.TimestampFormatter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale
import java.util.TimeZone

class TimestampFormatterTest {

    @Test
    fun format_epochInUtc() {
        val result = TimestampFormatter.format(0L, Locale.US, TimeZone.getTimeZone("UTC"))
        assertEquals("1970-01-01 00:00", result)
    }

    @Test
    fun format_knownTimestamp() {
        // 2020-01-02 03:04:05 UTC
        val millis = 1577934245000L
        val result = TimestampFormatter.format(millis, Locale.US, TimeZone.getTimeZone("UTC"))
        assertEquals("2020-01-02 03:04", result)
    }
}
