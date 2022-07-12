package com.woowahan.repositorysearch.util

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request
            .newBuilder()
            .addHeader("client_id", "21e566da1e2ebde399b4")
            .build()
        return chain.proceed(request)
    }
}