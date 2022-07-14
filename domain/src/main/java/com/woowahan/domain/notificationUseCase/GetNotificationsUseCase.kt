package com.woowahan.domain.notificationUseCase

import com.woowahan.domain.repository.NotificationRepository

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    suspend fun execute(page: Int) = repository.getNotifications(page)
}