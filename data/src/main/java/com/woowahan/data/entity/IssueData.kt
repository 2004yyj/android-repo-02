package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName
import com.woowahan.domain.model.Issue

data class IssueData(
    val id: Int,
    val title: String,
    val number: Int,
    val state: String,
    val repository: RepositoryData,
    @SerializedName("created_at")
    val createdAt: String,
)

fun IssueData.toModel(): Issue {
    return Issue(
        id,
        title,
        number,
        state,
        createdAt,
        repository.fullName
    )
}