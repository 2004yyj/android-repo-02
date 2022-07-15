package com.woowahan.domain.repository

import com.woowahan.domain.model.Message
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Subject

interface NotificationRepository {
    suspend fun getNotifications(page: Int): List<Notification>
    suspend fun markNotificationAsRead(currentTime: String): Message
    suspend fun getSubject(
        organization: String,
        repository: String,
        type: String,
        id: String
    ): Subject
}