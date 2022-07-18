package com.woowahan.domain.model

data class Issue(
    val id: Int,
    val title: String,
    val number: Int,
    val state: String,
    val updatedAt: String,
    val repository: String
)