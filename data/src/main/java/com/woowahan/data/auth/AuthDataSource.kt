package com.woowahan.data.auth

import com.woowahan.data.entity.GitHubToken

interface AuthDataSource {
    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): retrofit2.Response<GitHubToken>
}