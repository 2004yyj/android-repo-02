package com.woowahan.domain.repository

import com.woowahan.domain.model.Notification

interface NotificationRepository {
    suspend fun getNotifications(page: Int): List<Notification>
}