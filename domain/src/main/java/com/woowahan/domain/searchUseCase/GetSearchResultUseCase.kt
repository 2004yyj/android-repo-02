package com.woowahan.domain.searchUseCase

import com.woowahan.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchResultUseCase<T>(private val searchRepository: SearchRepository<T>) {
    suspend fun execute(query: String, size: Int): Flow<T> {
        return searchRepository.getSearchResult(query, size)
    }
}