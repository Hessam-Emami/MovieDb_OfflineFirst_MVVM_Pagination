package com.emami.moviedb.common.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeUtil {

    @SuppressLint("SimpleDateFormat")
    private val yearMonthDayFormatter = SimpleDateFormat("yyyy-MM-dd")
    @SuppressLint("SimpleDateFormat")
    private val yearMonthFormatter = SimpleDateFormat("MMMM yyyy")

    @SuppressLint("SimpleDateFormat")
    fun formatDateToString(date: Date = Date()): String =
        yearMonthDayFormatter.format(date)

    fun getYearMonthFromDateString(dateString: String): String {
        val date = yearMonthDayFormatter.parse(dateString)!!
        return yearMonthFormatter.format(date)
    }
}