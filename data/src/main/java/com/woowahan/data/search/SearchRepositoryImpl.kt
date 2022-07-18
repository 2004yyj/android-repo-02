package com.woowahan.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.woowahan.data.user.UserDataSourceImpl
import com.woowahan.domain.model.Repository
import com.woowahan.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(
    private val searchDataSourceImpl: SearchDataSourceImpl,
    private val userDataSourceImpl: UserDataSourceImpl
) : SearchRepository<PagingData<Repository>> {
    override suspend fun getSearchResult(
        query: String,
        size: Int
    ): Flow<PagingData<Repository>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                enablePlaceholders = false
            ), pagingSourceFactory = { SearchPagingSource(searchDataSourceImpl, userDataSourceImpl, query, size) }
        ).flow
    }
}