package com.emami.moviedb.common.util

import android.view.View


fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

private fun checkIfRangeIsValid(startIndex: Int, endIndex: Int) =
    (startIndex >= 0) && (endIndex >= 0) && (endIndex > startIndex)

