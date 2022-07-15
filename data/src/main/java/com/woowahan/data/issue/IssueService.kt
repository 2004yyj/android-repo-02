package com.woowahan.data.issue

import com.woowahan.data.entity.IssueData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IssueService {
    @GET("/issue")
    fun getIssues(
        @Query("state") state: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String = "updated"
    ): Response<List<IssueData>>
}