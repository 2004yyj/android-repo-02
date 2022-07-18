package com.woowahan.data.issue

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.woowahan.domain.model.Issue
import com.woowahan.domain.repository.IssueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(
    private val issueDataSourceImpl: IssueDataSourceImpl
): IssueRepository<PagingData<Issue>> {
    override suspend fun getIssues(
        size: Int,
        state: String
    ): Flow<PagingData<Issue>> {
        return Pager(
            config = PagingConfig(
                pageSize = size,
                initialLoadSize = size,
                enablePlaceholders = false
            ), pagingSourceFactory = { IssuePagingSource(issueDataSourceImpl, state) }
        ).flow
    }
}