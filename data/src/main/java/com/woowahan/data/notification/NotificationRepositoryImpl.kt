package com.woowahan.data.notification

import com.woowahan.domain.model.Message
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Subject
import com.woowahan.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository {
    override suspend fun getNotifications(page: Int): List<Notification> {
        return notificationDataSourceImpl.getNotifications(page)
    }

    override suspend fun markNotificationAsRead(threadId: String): String {
        return notificationDataSourceImpl.markNotificationAsRead(threadId)
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