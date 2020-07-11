package com.emami.moviedb.common.util

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochSecond(this),
    ZoneId.systemDefault()
)

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun TextView.setColoredTextWithGivenRange(
    startIndex: Int,
    endIndex: Int,
    content: String,
    color: Int
) {
    if (checkIfRangeIsValid(startIndex, endIndex)) {
        if (content.length >= endIndex) {
            SpannableString(content).also { spannedText ->
                spannedText.setSpan(
                    ForegroundColorSpan(color),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                this.text = spannedText
            }
        } else text = content
    }
}

private fun checkIfRangeIsValid(startIndex: Int, endIndex: Int) =
    (startIndex >= 0) && (endIndex >= 0) && (endIndex > startIndex)

