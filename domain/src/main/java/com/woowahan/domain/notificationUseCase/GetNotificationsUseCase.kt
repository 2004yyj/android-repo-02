package com.woowahan.domain.notificationUseCase

import com.woowahan.domain.model.Result
import com.woowahan.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    fun execute(page: Int) = flow {
        emit(Result.Loading)
        try {
            val notifications = repository.getNotifications(page)
            emit(Result.Success(notifications))
        } catch (e: Throwable) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}