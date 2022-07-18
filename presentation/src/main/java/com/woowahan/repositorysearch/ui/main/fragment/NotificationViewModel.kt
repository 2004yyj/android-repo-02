package com.woowahan.repositorysearch.ui.main.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Result
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.domain.notificationUseCase.GetSubjectUseCase
import com.woowahan.domain.notificationUseCase.MarkNotificationAsReadUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    @RetrofitModule.typeApi private val getNotificationsUseCase: GetNotificationsUseCase<PagingData<Notification>>,
    @RetrofitModule.typeApi private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase<PagingData<Notification>>
) : ViewModel() {
    private val _notifications = MutableSharedFlow<PagingData<Notification>>()
    val notifications = _notifications.asSharedFlow()

    private var _markFailed = MutableLiveData<List<Any>>()
    val markFailed: LiveData<List<Any>>
        get() = _markFailed

    fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            getNotificationsUseCase.execute().collect { it ->
                _notifications.emit(it)
            }
        }
    }

    fun markNotificationAsRead(adapterPosition: Int, noti: Notification) {
        markNotificationAsReadUseCase.execute(noti.threadId).onEach { result ->
            when (result) {
                is Result.Success -> {}
                is Result.Failure -> {
                    _markFailed.postValue(listOf(adapterPosition, noti, result.throwable))
                }
                is Result.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }
}