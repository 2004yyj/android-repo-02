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
    val token = this.subject.url.split("/")

    return Notification(
        id = this.id,
        title = this.subject.title,
        lastUpdate = this.updatedAt,
        repository = this.repository.name,
        commentCnt = 5,
        profileUrl = this.repository.owner.avatarUrl,
        organization = this.repository.owner.name,
        subjectType = token[token.lastIndex - 1],
        subjectId = token.last(),
        htmlUrl = ""
    )
}