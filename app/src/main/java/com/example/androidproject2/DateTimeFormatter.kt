package com.example.myweather

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM d"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}