package com.woowahan.data.notification

import com.woowahan.data.entity.MessageData
import com.woowahan.data.entity.NotificationData
import retrofit2.Response
import retrofit2.http.*

interface NotificationService {
    @GET("/notifications")
    suspend fun getNotifications(
        @Query("page") page: Int
    ): Response<List<NotificationData>>

    @FormUrlEncoded
    @PUT("/notifications")
    suspend fun markNotificationAsRead(
        @Field("last_read_at") currentTime: String,
        @Field("read") isRead: Boolean = true
    ): Response<MessageData>
}