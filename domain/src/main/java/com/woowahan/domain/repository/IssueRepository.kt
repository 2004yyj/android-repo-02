package com.woowahan.domain.repository

import com.woowahan.domain.model.Issue
import kotlinx.coroutines.flow.Flow

interface IssueRepository<T> {
    suspend fun getIssues(
        size: Int,
        state: String
    ): Flow<T>
}