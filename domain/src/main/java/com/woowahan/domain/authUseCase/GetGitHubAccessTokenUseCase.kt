package com.woowahan.domain.authUseCase

import com.woowahan.domain.model.Result
import com.woowahan.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetGitHubAccessTokenUseCase(private val repository: AuthRepository) {
    fun execute(
        clientId: String,
        clientSecret: String,
        code: String
    ) = flow {
        emit(Result.Loading)
        try {
            val gitHubToken = repository.getAccessToken(clientId, clientSecret, code)
            emit(Result.Success(gitHubToken))
        } catch (e: Throwable) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}