package com.woowahan.domain.repository

import com.woowahan.domain.model.GitHubToken

interface AuthRepository {
    suspend fun getAccessToken(clientId: String, clientSecret: String, code: String): GitHubToken
}