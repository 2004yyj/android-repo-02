package com.woowahan.data.auth

import com.woowahan.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun getAccessToken(clientId: String, clientSecret: String, code: String) {
        TODO("Not yet implemented")
    }
}