package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName
import com.woowahan.domain.model.User

data class UserData(
    val login: String,
    @SerializedName("avatar_url")
    val avatar: String,
    val company: String,
    val location: String,
    val blog: String,
    val email: String,
    val followers: Int,
    val following: Int,
    @SerializedName("public_repos")
    val repositories: String,
    @SerializedName("starred_url")
    val starredUrl: String
)

fun UserData.toModel() = User(
    login,
    avatar,
    company,
    location,
    blog,
    email,
    followers,
    following,
    repositories,
    starredUrl
)