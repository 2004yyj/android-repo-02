package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName

data class RepositoryData(
    val id: Int,
    val name: String,
    val owner: OwnerData,
    @SerializedName("comments_url")
    val commentsUrl: String,
)