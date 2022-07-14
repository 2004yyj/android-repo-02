package com.woowahan.repositorysearch.util

import com.woowahan.domain.model.GitToken
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.Headers

class ApiHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
        if (GitToken.token.isNotEmpty()) {
            newRequest.addHeader("Accept", "application/vnd.github+json")
            newRequest.addHeader("Authorization", GitToken.token)
        }
        val build = newRequest.build()

        return proceed(build)
    }
}