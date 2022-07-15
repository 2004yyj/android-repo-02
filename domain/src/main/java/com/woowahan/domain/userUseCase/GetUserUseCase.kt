package com.woowahan.domain.userUseCase

import com.woowahan.domain.repository.UserRepository
import com.woowahan.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserUseCase(private val repository: UserRepository) {
    fun execute() = flow {
        emit(Result.Loading)
        try {
            val user = repository.getUser()
            val starred = repository.getUserStarredSize(user.login)
            user.starredSize = starred

            emit(Result.Success(user))
        } catch (e: Throwable) {
            emit(Result.Failure(e))
        }
    }.flowOn(Dispatchers.IO)
}