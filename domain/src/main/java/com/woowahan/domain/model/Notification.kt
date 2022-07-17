package com.woowahan.domain.model


data class Notification(
    val id: String,
    val title: String,
    val lastUpdate: String,
    val repository: String,
    var commentCnt: Int,
    val profileUrl: String,
    val organization: String,
    val subjectType: String,
    val subjectId: String,
    var htmlUrl: String,
    val threadId: String
)
