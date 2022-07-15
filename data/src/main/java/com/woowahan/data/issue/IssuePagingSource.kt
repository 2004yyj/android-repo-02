package com.woowahan.data.issue

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woowahan.domain.model.Issue
import javax.inject.Inject

class IssuePagingSource @Inject constructor(
    private val issueDataSourceImpl: IssueDataSourceImpl,
    private val state: String
): PagingSource<Int, Issue>() {
    override fun getRefreshKey(state: PagingState<Int, Issue>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Issue> {
        return try {
            val page = params.key ?: 1
            val issues = issueDataSourceImpl.getIssues(
                state,
                params.loadSize,
                page
            )
            LoadResult.Page(
                data = issues,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (issues.isEmpty()) null else page + 1

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}