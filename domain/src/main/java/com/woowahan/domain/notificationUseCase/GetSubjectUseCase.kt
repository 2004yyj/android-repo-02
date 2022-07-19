package com.woowahan.domain.notificationUseCase

import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Result
import com.woowahan.domain.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSubjectUseCase(private val repository: NotificationRepository<Any?>) {
    fun execute(notification: Notification) = flow {
        emit(Result.Loading)
        try {
            val notifications = repository.getSubject(
                notification.organization,
                notification.repository,
                notification.subjectType,
                notification.subjectId
            )
            emit(Result.Success(notifications))
        } catch (e: Throwable) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}