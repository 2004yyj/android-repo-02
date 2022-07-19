package com.woowahan.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchRepository<T> {
    suspend fun getSearchResult(query: String, size: Int): Flow<T>
}