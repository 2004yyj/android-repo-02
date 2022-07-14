package com.woowahan.data.auth

import com.woowahan.data.entity.toModel
import com.woowahan.domain.model.GitHubToken
import com.woowahan.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSourceImpl: AuthDataSourceImpl
) : AuthRepository {
    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): GitHubToken {
        return authDataSourceImpl.getAccessToken(clientId, clientSecret, code)
    }
}