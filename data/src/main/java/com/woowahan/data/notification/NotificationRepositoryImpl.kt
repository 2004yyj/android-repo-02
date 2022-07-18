package com.woowahan.data.notification

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.woowahan.data.search.SearchPagingSource
import com.woowahan.domain.model.Message
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Subject
import com.woowahan.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSourceImpl: NotificationDataSourceImpl
) : NotificationRepository<PagingData<Notification>> {
    override suspend fun getNotifications(): Flow<PagingData<Notification>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                initialLoadSize = 30,
                enablePlaceholders = false
            ), pagingSourceFactory = { NotificationPagingSource(notificationDataSourceImpl) }
        ).flow
    }

    override suspend fun markNotificationAsRead(threadId: String): String {
        return notificationDataSourceImpl.markNotificationAsRead(threadId)
    }

    override suspend fun getSubject(
        organization: String,
        repository: String,
        type: String,
        id: String
    ): Subject {
        return notificationDataSourceImpl.getSubject(organization, repository, type, id)
    }
}