package com.woowahan.domain.model

data class User(
    val login: String,
    val name: String,
    val avatar: String,
    val company: String,
    val location: String,
    val blog: String,
    val mail: String,
    val followers: Int,
    val following: Int,
    val repositories: Int,
    val starredUrl: String
)