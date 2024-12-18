package com.example.uikit.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.loadImageFromGLide(imagePath: String) {
    Glide.with(this).load(imagePath).into(this)
}

fun ImageView.loadImageFromGLideRounded(imagePath: String, roundValue: Int) {
    Glide.with(this)
        .load(imagePath)
        .transform(RoundedCorners(roundValue)) // The value here represents the radius of the corners
        .into(this)
}