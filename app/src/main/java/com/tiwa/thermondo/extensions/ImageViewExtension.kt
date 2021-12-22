package com.tiwa.thermondo.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Displays an image from a URL in an ImageView.
 */

fun ImageView.loadImage(url: String?) {
    Glide
        .with(this.rootView.context)
        .load(url)
        .centerCrop()
        .dontAnimate()
        .into(this)
}