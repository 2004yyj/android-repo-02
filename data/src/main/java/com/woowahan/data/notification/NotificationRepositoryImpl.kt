package com.woowahan.data.notification

import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.entity.NotificationData
import com.woowahan.domain.model.Notification
import com.woowahan.domain.repository.NotificationRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository {
    override suspend fun getNotifications(page: Int): List<Notification> {
        val notificationDatas = notificationDataSourceImpl.getNotifications(page)
        val result = ArrayList<Notification>()

        for (notificationData in notificationDatas) {
            result.add(
                Notification(
                    id = notificationData.id,
                    title = notificationData.subject.title,
                    lastUpdate = notificationData.updatedAt,
                    repository = notificationData.repository.name,
                    commentCnt = 5,
                    profileUrl = notificationData.repository.owner.avatarUrl
                )
            )
        }

        return result
    }
}