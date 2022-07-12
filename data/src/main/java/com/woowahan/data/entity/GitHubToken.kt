package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName

data class GitHubToken(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val type: String
)