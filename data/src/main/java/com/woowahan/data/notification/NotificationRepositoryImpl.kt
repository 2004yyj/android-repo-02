package com.woowahan.data.notification

import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository {
}