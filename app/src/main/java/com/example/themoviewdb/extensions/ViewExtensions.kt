package com.example.themoviewdb.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.themoviewdb.common.BASE_URL_TO_LOAD_POSTER

fun ImageView.load(url: String) {
    Glide
        .with(context)
        .load("$BASE_URL_TO_LOAD_POSTER$url")
        .into(this)
}