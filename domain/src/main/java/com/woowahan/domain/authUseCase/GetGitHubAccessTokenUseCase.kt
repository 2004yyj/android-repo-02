package com.woowahan.domain.authUseCase

import com.woowahan.domain.repository.AuthRepository

class GetGitHubAccessTokenUseCase(private val repository: AuthRepository) {
    suspend fun execute(
        clientId: String,
        clientSecret: String,
        code: String
    ) = repository.getAccessToken(clientId, clientSecret, code)
}