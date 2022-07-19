package com.woowahan.data.issue

import com.woowahan.data.base.BaseDataSource
import com.woowahan.data.entity.toModel
import javax.inject.Inject

class IssueDataSourceImpl @Inject constructor(
    override val service: IssueService
): BaseDataSource<IssueService>() {
    suspend fun getIssues(
        state: String,
        perPage: Int,
        page: Int
    ) = getData(service.getIssues(state, perPage, page)).map {
        it.toModel()
    }
}