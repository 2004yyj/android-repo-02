package com.woowahan.data.notification

import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.entity.NotificationData
import com.woowahan.data.entity.toModel
import com.woowahan.domain.model.Message
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Subject
import com.woowahan.domain.repository.NotificationRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository {
    override suspend fun getNotifications(page: Int): List<Notification> {
        return notificationDataSourceImpl.getNotifications(page)
    }

    override suspend fun markNotificationAsRead(currentTime: String): Message {
        return notificationDataSourceImpl.markNotificationAsRead(currentTime)
    }

    override suspend fun getSubject(
        organization: String,
        repository: String,
        type: String,
        id: String
    ): Subject {
        return notificationDataSourceImpl.getSubject(organization, repository, type, id)
    }
}