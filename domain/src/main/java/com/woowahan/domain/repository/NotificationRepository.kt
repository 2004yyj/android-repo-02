package com.woowahan.domain.repository

import com.woowahan.domain.model.Message
import com.woowahan.domain.model.Notification

interface NotificationRepository {
    suspend fun getNotifications(page: Int): List<Notification>
    suspend fun markNotificationAsRead(currentTime: String): Message
}