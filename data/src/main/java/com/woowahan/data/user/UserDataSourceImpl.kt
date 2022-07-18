package com.woowahan.data.user

import com.woowahan.data.base.BaseDataSource
import com.woowahan.data.entity.toModel
import retrofit2.http.Path
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    override val service: UserService
): BaseDataSource<UserService>() {
    suspend fun getUser() = getData(service.getUser()).toModel()
    suspend fun getUserStarredSize(username: String) =
        getData(service.getUserStarred(username)).size
}