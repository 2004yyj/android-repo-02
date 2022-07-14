package com.woowahan.data.notification

import com.woowahan.data.entity.NotificationData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NotificationService {
    @GET("/notifications")
    suspend fun getNotifications(
        @Query("page") page: Int
    ): Response<List<NotificationData>>
}