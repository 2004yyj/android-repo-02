package com.woowahan.repositorysearch.util

import android.content.Context

object Dp2Px {
    fun convert(context: Context, dp: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f)
    }
}