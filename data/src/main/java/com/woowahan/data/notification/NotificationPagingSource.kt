package com.woowahan.data.notification

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woowahan.domain.model.Notification

class NotificationPagingSource(
    private val notificationDataSourceImpl: NotificationDataSourceImpl,
) : PagingSource<Int, Notification>() {
    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        return try {
            val page = params.key ?: 1

            try {
                val notifications = notificationDataSourceImpl.getNotifications(page).map {
                    val subject = notificationDataSourceImpl.getSubject(
                        it.organization,
                        it.repository,
                        it.subjectType,
                        it.subjectId
                    )
                    it.commentCnt = subject.comments
                    it
                }
                LoadResult.Page(
                    data = notifications,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (notifications.isEmpty()) null else page + 1
                )
            } catch (e: Exception) {
                print(e)
                LoadResult.Error(e)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}