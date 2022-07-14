package com.woowahan.domain.model

sealed class Result<out Response> {
    data class Success<T>(val data: T): Result<T>()
    data class Failure(val throwable: Throwable): Result<Nothing>()
    object Loading: Result<Nothing>()
}