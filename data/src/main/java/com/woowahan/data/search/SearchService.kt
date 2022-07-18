package com.woowahan.data.search

import com.woowahan.data.entity.SearchData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/search/repositories")
    suspend fun getSearchResult(
        @Query("q") query: String,
        @Query("per_page") size: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String = "updated"
    ): Response<SearchData>
}