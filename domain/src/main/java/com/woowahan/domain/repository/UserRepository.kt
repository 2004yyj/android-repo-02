package com.woowahan.domain.repository

import com.woowahan.domain.model.User

interface UserRepository {
    suspend fun getUser(): User
    suspend fun getUserStarredSize(username: String): Int
    suspend fun getRepoStarred(owners: String, repo: String): Int
}