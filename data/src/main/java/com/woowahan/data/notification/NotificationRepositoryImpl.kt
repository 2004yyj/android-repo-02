package com.woowahan.data.notification

import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.entity.NotificationData
import com.woowahan.domain.model.Notification
import com.woowahan.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository {
    override suspend fun getNotifications(page: Int): List<Notification> {
        val notificationDatas = notificationDataSourceImpl.getNotifications(page)
        val result = ArrayList<Notification>()

        for (notificationData in notificationDatas) {
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