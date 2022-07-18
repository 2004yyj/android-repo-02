package com.woowahan.data.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woowahan.data.user.UserDataSourceImpl
import com.woowahan.domain.model.Repository

class SearchPagingSource(
    private val searchDataSourceImpl: SearchDataSourceImpl,
    private val userDataSourceImpl: UserDataSourceImpl,
    private val query: String,
    private val size: Int
) :
    PagingSource<Int, Repository>() {
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        return try {
            val page = params.key ?: 1

            val test = searchDataSourceImpl.getSearchResult(query, size, page)
            val repositories = test.items.map {
                it.starred = userDataSourceImpl.getRepoStarred(it.login, it.name)
                it
            }

            LoadResult.Page(
                data = repositories,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (repositories.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}