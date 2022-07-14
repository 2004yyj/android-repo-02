package com.woowahan.data.notification

import com.woowahan.data.base.BaseDataSource
import com.woowahan.data.entity.NotificationData
import com.woowahan.data.entity.toModel
import com.woowahan.domain.model.Message
import com.woowahan.domain.model.Notification

class NotificationDataSourceImpl(override val service: NotificationService) :
    BaseDataSource<NotificationService>() {
    suspend fun getNotifications(page: Int): List<Notification> =
        getData(service.getNotifications(page)).map {
            it.toModel()
        }

    suspend fun markNotificationAsRead(currentTime: String): Message =
        getData(service.markNotificationAsRead(currentTime)).toModel()
}