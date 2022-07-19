package com.woowahan.data.user

import com.woowahan.data.entity.RepositoryData
import com.woowahan.data.entity.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/user")
    suspend fun getUser(): Response<UserData>

    @GET("/users/{username}/starred")
    suspend fun getUserStarred(
        @Path("username") username: String
    ): Response<List<RepositoryData>>

    @GET("/repos/{owners}/{repo}/stargazers")
    suspend fun getRepoStarred(
        @Path("owners") owners: String,
        @Path("repo") repo: String
    ): Response<List<RepositoryData>>
}