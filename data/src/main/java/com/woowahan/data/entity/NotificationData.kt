package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName
import java.util.*

data class NotificationData(
    val id: String,
    val reason: String,
    val unread: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
    val repository: RepositoryData
)
