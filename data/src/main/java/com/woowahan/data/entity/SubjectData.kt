package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName
import com.woowahan.domain.model.Subject

data class SubjectData(
    val title: String,
    val url: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    val comments: Int,
    val type: String,
    val id: String
)

fun SubjectData.toModel(): Subject {
    return Subject(
        this.id,
        this.htmlUrl,
        this.comments
    )
}
