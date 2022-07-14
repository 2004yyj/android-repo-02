package com.woowahan.domain.repository

import com.woowahan.domain.model.User

interface UserRepository {
    suspend fun getUser(): User
}