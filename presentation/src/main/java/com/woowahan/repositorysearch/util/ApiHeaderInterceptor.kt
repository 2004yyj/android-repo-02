package com.woowahan.repositorysearch.util

import android.content.Context
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ApiHeaderInterceptor constructor(
    private val appContext: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
        val build = runBlocking {
            val token = appContext.dataStore.get(tokenPrefsKey, "")
            newRequest.addHeader("Accept", "application/vnd.github+json")
            newRequest.addHeader("Authorization", token)
            newRequest.build()
        }

        return proceed(build)
    }
}