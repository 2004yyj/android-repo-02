package com.woowahan.data.entity

import com.google.gson.annotations.SerializedName
import com.woowahan.domain.model.Search

data class SearchData(
    @SerializedName("total_count")
    val totalCount: Int,
    val items: List<RepositoryData>
)

fun SearchData.toModel(): Search {
    return Search(
        totalCount = totalCount,
        items = items.map(RepositoryData::toModel)
    )
}