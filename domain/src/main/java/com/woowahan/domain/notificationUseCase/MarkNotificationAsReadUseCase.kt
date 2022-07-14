package com.woowahan.domain.notificationUseCase

import com.woowahan.domain.model.Result
import com.woowahan.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MarkNotificationAsReadUseCase(private val repository: NotificationRepository) {
    fun execute(currentTime: String) = flow {
        emit(Result.Loading)
        try {
            val message = repository.markNotificationAsRead(currentTime)
            emit(Result.Success(message))
        } catch (e: Throwable) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}