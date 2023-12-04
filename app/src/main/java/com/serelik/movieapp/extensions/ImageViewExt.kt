package com.example.movieappst.ui.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.load(url: String?, placeHolder: Int? = null, isCenterCrop: Boolean = false) {
    var builder = Glide
        .with(this)
        .load(url)
    if (isCenterCrop)
        builder = builder.centerCrop()

    if (placeHolder != null) {
        builder = builder.placeholder(placeHolder)
    }
    builder
        .into(this)
}