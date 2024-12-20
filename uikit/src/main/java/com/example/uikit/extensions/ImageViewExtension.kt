package com.example.uikit.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener


fun ImageView.loadImageFromGlide(url: String, listener: RequestListener<Drawable>? = null) {
    Glide.with(this.context)
        .load(url)
        .listener(listener)
        .into(this)
}

fun ImageView.loadImageFromGLideRounded(
    imagePath: String,
    roundValue: Int,
    listener: RequestListener<Drawable>? = null
) {
    Glide.with(this)
        .load(imagePath)
        .listener(listener)
        .transform(RoundedCorners(roundValue))
        .into(this)
}