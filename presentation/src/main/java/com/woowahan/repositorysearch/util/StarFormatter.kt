package com.woowahan.repositorysearch.util

import kotlin.math.round

object StarFormatter {
    fun convert(count: Int): String {
        return if (count > 999) {
            "${round(count / 100F) / 10}k"
        } else {
            count.toString()
        }
    }
}