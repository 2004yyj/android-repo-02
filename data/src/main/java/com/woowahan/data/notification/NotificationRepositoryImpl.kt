package com.woowahan.data.notification

import com.woowahan.data.auth.AuthRepositoryImpl
import com.woowahan.data.entity.NotificationData
import com.woowahan.data.entity.toModel
import com.woowahan.domain.model.Notification
import com.woowahan.domain.repository.NotificationRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository {
    override suspend fun getNotifications(page: Int): List<Notification> {
        val notificationDatas = notificationDataSourceImpl.getNotifications(page)
        val result = ArrayList<Notification>()

        for (notificationData in notificationDatas) {
            result.add(notificationData.toModel())
        }

        return result
    }
}