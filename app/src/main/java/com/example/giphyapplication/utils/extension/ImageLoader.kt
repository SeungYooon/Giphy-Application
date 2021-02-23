package com.example.giphyapplication.utils.extension

import android.widget.ImageView
import com.example.giphyapplication.utils.GlideApp

fun bindImage(view: ImageView, url: String?) {
    GlideApp.with(view.context)
        .asGif()
        .override(1024)
        .load(url)
        .into(view)
}