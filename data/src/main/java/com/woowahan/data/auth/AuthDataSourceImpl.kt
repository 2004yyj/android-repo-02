package com.woowahan.data.auth

import com.woowahan.data.base.BaseDataSource
import com.woowahan.data.entity.GitHubTokenData
import com.woowahan.data.entity.toModel
import retrofit2.Response

class AuthDataSourceImpl(override val service: AuthService) : BaseDataSource<AuthService>() {
    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ) = getData(service.getAccessToken(clientId, clientSecret, code)).toModel()
}