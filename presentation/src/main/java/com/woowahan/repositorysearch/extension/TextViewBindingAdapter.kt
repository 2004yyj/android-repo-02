package com.woowahan.repositorysearch.extension

import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:underlineText")
fun TextView.setUnderlineText(text: CharSequence) {
    val spn = SpannableString(text)
    spn.setSpan(UnderlineSpan(), 0, spn.length, 0)
    setText(spn)
}