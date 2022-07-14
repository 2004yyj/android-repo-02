package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName
import com.woowahan.domain.model.Notification
import java.util.*

data class NotificationData(
    val id: String,
    val reason: String,
    val unread: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String,
    val repository: RepositoryData,
    val subject: SubjectData
)

fun NotificationData.toModel(): Notification {
    return Notification(
        id = this.id,
        title = this.subject.title,
        lastUpdate = this.updatedAt,
        repository = this.repository.name,
        commentCnt = 5,
        profileUrl = this.repository.owner.avatarUrl
    )
}