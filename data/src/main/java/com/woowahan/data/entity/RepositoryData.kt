package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName
import com.woowahan.domain.model.Repository

data class RepositoryData(
    val id: Int,
    val name: String,
    val owner: OwnerData,
    val description: String?,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("full_name")
    val fullName: String,
    val language: String,
    var starred: Int
)

fun RepositoryData.toModel(): Repository {
    return Repository(
        id = id,
        name = name,
        login = owner.name,
        avatarUrl = owner.avatarUrl,
        description = description,
        language = language,
        starred = starred
    )
}