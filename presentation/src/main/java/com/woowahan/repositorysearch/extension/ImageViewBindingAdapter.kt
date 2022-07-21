package com.woowahan.repositorysearch.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("android:loadImage")
fun ImageView.setImage(data: String?) {
    load(data)
}

@BindingAdapter("android:loadImage")
fun ImageView.setImage(data: Drawable) {
    load(data)
}