package com.woowahan.domain.repository

interface AuthRepository {
    suspend fun getAccessToken(clientId: String, clientSecret: String, code: String)
}