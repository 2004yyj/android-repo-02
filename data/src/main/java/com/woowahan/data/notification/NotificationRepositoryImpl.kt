package com.woowahan.data.notification

import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.entity.NotificationData
import com.woowahan.domain.model.Notification
import com.woowahan.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository {
    override suspend fun getNotifications(): List<Notification> {
        val notificationDatas = notificationDataSourceImpl.getNotifications()
        val result = ArrayList<Notification>()

        for (notificationData in notificationDatas) {
            val commentCnt =
                notificationData.repository.commentsUrl.split("/").last().replace("comments", "")
            result.add(
                Notification(
                    notificationData.id,
                    notificationData.reason,
                    notificationData.updatedAt,
                    notificationData.repository.fullName,
                    5,
                    notificationData.repository.owner.avatarUrl
                )
            )
        }

        return result
    }
}