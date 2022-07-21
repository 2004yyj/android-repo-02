package com.woowahan.repositorysearch.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("android:loadImage")
fun ImageView.setImage(data: String?) {
    load(data)
}