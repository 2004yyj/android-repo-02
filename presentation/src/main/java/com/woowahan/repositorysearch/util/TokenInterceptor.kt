package com.woowahan.repositorysearch.util

import com.woowahan.domain.model.GitToken
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response  = with(chain){
        val newRequest = request().newBuilder()
        if (GitToken.token.isNotEmpty()) {
            newRequest.addHeader("X-OAuth-Scopes", "notification")
            newRequest.addHeader("Authorization", GitToken.token)
        }
        val build = newRequest.build()

        return proceed(build)
    }
}