package com.woowahan.data.search

import com.woowahan.data.base.BaseDataSource
import com.woowahan.data.entity.toModel

class SearchDataSourceImpl(override val service: SearchService,
) : BaseDataSource<SearchService>() {
    suspend fun getSearchResult(query: String, size: Int, page: Int) =
        getData(service.getSearchResult(query, size, page)).toModel()
}