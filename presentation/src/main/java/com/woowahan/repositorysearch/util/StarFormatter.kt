package com.woowahan.repositorysearch.util

import kotlin.math.round

object StarFormatter {
    @JvmStatic
    fun convert(count: Int): String {
        return if (count > 999) {
            "${round(count / 100F) / 10}k"
        } else {
            count.toString()
        }
    }
}