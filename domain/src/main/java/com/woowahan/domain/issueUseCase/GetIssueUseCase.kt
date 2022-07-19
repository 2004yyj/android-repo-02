package com.woowahan.domain.issueUseCase

import com.woowahan.domain.model.Result
import com.woowahan.domain.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class GetIssueUseCase<T>(private val repository: IssueRepository<T>) {
    suspend fun execute(
        size: Int,
        state: String
    ) = repository.getIssues(size, state)
}