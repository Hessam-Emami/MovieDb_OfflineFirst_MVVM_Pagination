package com.emami.moviedb.common.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeUtil {
    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(date: Date = Date()): String =
        SimpleDateFormat("yyyy-MM-dd").format(date)
}