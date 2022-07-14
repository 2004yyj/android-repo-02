package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName

data class RepositoryData(
    val id: Int,
    @SerializedName("full_name")
    val fullName: String,
    val owner: OwnerData,
    @SerializedName("comments_url")
    val commentsUrl: String,
)