package com.woowahan.data.auth

import com.woowahan.data.entity.GitHubToken
import retrofit2.Response

class AuthDataSourceImpl(private val authService: AuthService) : AuthDataSource {
    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Response<GitHubToken> = authService.getAccessToken(clientId, clientSecret, code)
}