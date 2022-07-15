package com.woowahan.data.user

import com.woowahan.domain.model.User
import com.woowahan.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSourceImpl: UserDataSourceImpl
): UserRepository {
    override suspend fun getUser() = userDataSourceImpl.getUser()
    override suspend fun getUserStarredSize(username: String) =
        userDataSourceImpl.getUserStarredSize(username)
}