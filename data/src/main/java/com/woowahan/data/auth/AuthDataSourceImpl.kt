package com.woowahan.data.auth

import com.woowahan.data.base.BaseDataSource
import com.woowahan.data.entity.GitHubTokenData
import retrofit2.Response

class AuthDataSourceImpl(override val service: AuthService) : BaseDataSource<AuthService>() {
    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): GitHubTokenData {
        return getData(service.getAccessToken(clientId, clientSecret, code))
    }
}