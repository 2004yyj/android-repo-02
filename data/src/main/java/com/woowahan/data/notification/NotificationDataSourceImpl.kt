package com.woowahan.data.notification

import com.woowahan.data.base.BaseDataSource
import com.woowahan.data.entity.NotificationData

class NotificationDataSourceImpl(override val service: NotificationService) :
    BaseDataSource<NotificationService>() {
    suspend fun getNotifications(page: Int): List<NotificationData> =
        getData(service.getNotifications(page))
}