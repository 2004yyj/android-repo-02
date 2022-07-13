package com.woowahan.data.entity

data class RepositoryData(
    val id: Int,
    val fullName: String,
    val owner: OwnerData,
    val commentsUrl: String,
)