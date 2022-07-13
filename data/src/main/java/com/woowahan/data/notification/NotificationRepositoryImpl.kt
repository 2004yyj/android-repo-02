package com.woowahan.data.notification

import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.domain.model.Notification
import com.woowahan.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository {
    override suspend fun getNotifications(): Notification {
        val notificationData = notificationDataSourceImpl.getNotifications()
        val commentCnt = notificationData.commentsUrl.split("/").last().replace("comments", "")
        return Notification(
            notificationData.id.toInt(),
            notificationData.reason,
            notificationData.updatedAt,
            notificationData.repository.fullName,
            commentCnt.toInt(),
            notificationData.repository.owner.avatarUrl
        )
    }
}