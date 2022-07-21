package com.woowahan.repositorysearch.util

import java.text.SimpleDateFormat
import java.util.*

object TimeFormatter {
    @JvmStatic
    fun toRelativeTime(src: String): String {
        val srcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val datetime = srcFormat.parse(src)
        val current = Calendar.getInstance().time.time

        val minDiff = (current - datetime!!.time) / (60 * 1000)

        return if (minDiff < 60) {
            "${minDiff}분 전"
        } else {
            val hourDiff = minDiff / 60

            if (hourDiff < 24) {
                "${hourDiff}시간 전"
            } else {
                val dayDiff = hourDiff / 24
                "${dayDiff}일 전"
            }
        }
    }
}