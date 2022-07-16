package com.woowahan.repositorysearch.ui.main.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Result
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.domain.notificationUseCase.GetSubjectUseCase
import com.woowahan.domain.notificationUseCase.MarkNotificationAsReadUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    @RetrofitModule.typeApi private val getNotificationsUseCase: GetNotificationsUseCase,
    @RetrofitModule.typeGitHub private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    @RetrofitModule.typeApi private val getSubjectUseCase: GetSubjectUseCase
) : ViewModel() {
    private val _notifications = MutableSharedFlow<List<Notification>>()
    val notifications = _notifications.asSharedFlow()

    private val _isFailure = MutableSharedFlow<Throwable>()
    val isFailure = _isFailure.asSharedFlow()

    private var _markFailed = MutableLiveData<List<Any>>()
    val markFailed: LiveData<List<Any>>
        get() = _markFailed

    private lateinit var notificationList: MutableList<Notification>
    private var commentUpdateCnt = 0

    fun getNotifications() {
        val noti = getNotificationsUseCase.execute(1)

        noti.onEach { result ->
            when (result) {
                is Result.Success -> {
                    notificationList = result.data.toMutableList()

                    result.data.map {
                        getSubject(notificationList.indexOf(it))
                    }
                }
                is Result.Failure -> {
                    _isFailure.emit(result.throwable)
                }
                is Result.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun markNotificationAsRead(adapterPosition: Int, noti: Notification) {
        markNotificationAsReadUseCase.execute(noti.htmlUrl).onEach { result ->
            when (result) {
                is Result.Success -> {}
                is Result.Failure -> {
                    _markFailed.postValue(listOf(adapterPosition, noti, result.throwable))
                }
                is Result.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun getSubject(position: Int) {
        getSubjectUseCase.execute(notificationList[position]).onEach { result ->
            when (result) {
                is Result.Success -> {
                    notificationList[position].commentCnt = result.data.comments
                    notificationList[position].htmlUrl = result.data.htmlUrl
                    addUpdateCnt()
                }
                is Result.Failure -> {
                    _isFailure.emit(result.throwable)
                    addUpdateCnt()
                }
                is Result.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun addUpdateCnt() {
        commentUpdateCnt += 1
        if (commentUpdateCnt == notificationList.size) {
            _notifications.emit(notificationList)
        }
    }
}