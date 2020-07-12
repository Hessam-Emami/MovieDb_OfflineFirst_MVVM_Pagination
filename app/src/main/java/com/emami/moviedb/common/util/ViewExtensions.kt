package com.emami.moviedb.common.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.emami.moviedb.R


fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun ImageView.loadImage(string: String?) {
    Glide.with(context).load(string ?: R.drawable.ic_not_found).into(this)
}