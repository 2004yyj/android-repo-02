package com.woowahan.domain.model

data class GitHubToken(
    val token: String,
    val type: String,
    val scope: String
)