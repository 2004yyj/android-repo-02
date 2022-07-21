package com.woowahan.repositorysearch.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.woowahan.repositorysearch.R

@BindingAdapter("android:loadImage")
fun ImageView.setImage(data: String?) {
    load(data)
}

@BindingAdapter("android:issueCheck")
fun ImageView.issueCheck(state: String) {
    if (state == "open")
        load(R.drawable.ic_issue_open)
    else
        load(R.drawable.ic_issue_closed)
}