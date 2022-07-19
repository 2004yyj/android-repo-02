package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName
import com.woowahan.domain.model.GitHubToken

data class GitHubTokenData(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val type: String
)

fun GitHubTokenData.toModel(): GitHubToken {
    return GitHubToken(
        token = "token ${this.token}",
        type = this.type,
        scope = this.scope
    )
}
