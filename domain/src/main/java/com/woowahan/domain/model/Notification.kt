package com.woowahan.domain.model


data class Notification(
    val id: String,
    val title: String,
    val lastUpdate: String,
    val repository: String,
    val commentCnt: Int,
    val profileUrl: String
)
