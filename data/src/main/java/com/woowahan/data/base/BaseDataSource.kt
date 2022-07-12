package com.woowahan.data.base

import retrofit2.Response

abstract class BaseDataSource<SV> {
    abstract val service: SV

    fun <T> getData(response: Response<T>): T {
        return getError(response).body()!!
    }

    fun <T> getMessage(response: Response<T>): String {
        return getError(response).message()
    }

    private fun <T> getError(response: Response<T>): Response<T> {
        return if (response.isSuccessful) {
            response
        } else {
            throw Throwable(response.message())
        }
    }


}