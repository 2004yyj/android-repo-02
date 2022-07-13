package com.woowahan.data.entity

import java.util.*

data class NotificationData(
    val id: String,
    val reason: String,
    val unread: Boolean,
    val updatedAt: String,
    val commentsUrl: String,
    val repository: RepositoryData
)
