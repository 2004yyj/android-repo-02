package com.woowahan.data.notification

import com.woowahan.data.entity.MessageData
import com.woowahan.data.entity.NotificationData
import com.woowahan.data.entity.SubjectData
import retrofit2.Response
import retrofit2.http.*
import javax.security.auth.Subject

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

    @GET("/repos/{organization}/{repo}/{type}/{id}")
    suspend fun getSubject(
        @Path("organization") organization: String,
        @Path("repo") repository: String,
        @Path("type") type: String,
        @Path("id") id: String
    ): Response<SubjectData>
}