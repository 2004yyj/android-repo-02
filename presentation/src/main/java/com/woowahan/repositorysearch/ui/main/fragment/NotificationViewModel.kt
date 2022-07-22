package com.woowahan.repositorysearch.ui.main.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Result
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.domain.notificationUseCase.GetSubjectUseCase
import com.woowahan.domain.notificationUseCase.MarkNotificationAsReadUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.thread

@HiltViewModel
class NotificationViewModel @Inject constructor(
    @RetrofitModule.typeApi private val getNotificationsUseCase: GetNotificationsUseCase<PagingData<Notification>>,
    @RetrofitModule.typeApi private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase<PagingData<Notification>>
) : ViewModel() {
    private val _notification = MutableStateFlow<PagingData<Notification>>(PagingData.empty())
    val notification = _notification.asStateFlow()

    private val _isMarkFailed = MutableSharedFlow<Pair<Int, Throwable>>()
    val isMarkFailed = _isMarkFailed.asSharedFlow()

    fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            getNotificationsUseCase.execute(10).cachedIn(viewModelScope).collect {
                _notification.emit(it)
            }
        }
    }

    fun markNotificationAsRead(adapterPosition: Int, threadId: String) {
        markNotificationAsReadUseCase.execute(threadId)
            .onEach { result ->
                when (result) {
                    is Result.Success -> {}
                    is Result.Failure -> {
                        _isMarkFailed.emit(Pair(adapterPosition, result.throwable))
                    }
                    is Result.Loading -> {}
                }
            }.launchIn(viewModelScope)
    }
}