package com.woowahan.data.user

import com.woowahan.data.entity.UserData
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("/user")
    suspend fun getUser(): Response<UserData>
}