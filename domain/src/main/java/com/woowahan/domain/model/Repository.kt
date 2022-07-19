package com.woowahan.domain.model

data class Repository(
    val id: Int,
    val name: String,
    val login: String,
    val avatarUrl: String,
    val description: String?,
    val language: String?,
    var starred: Int
)