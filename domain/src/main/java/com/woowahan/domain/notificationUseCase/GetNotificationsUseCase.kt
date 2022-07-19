package com.woowahan.domain.notificationUseCase

import com.woowahan.domain.model.Result
import com.woowahan.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetNotificationsUseCase<T>(private val repository: NotificationRepository<T>) {
    suspend fun execute(size: Int): Flow<T> {
        return repository.getNotifications(size)
    }
}