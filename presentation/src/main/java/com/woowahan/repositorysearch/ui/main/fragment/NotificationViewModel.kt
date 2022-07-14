package com.woowahan.repositorysearch.ui.main.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.GitToken
import com.woowahan.domain.model.Notification
import com.woowahan.domain.model.Result
import com.woowahan.domain.model.User
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.repositorysearch.BuildConfig
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
    @RetrofitModule.typeApi private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {
    private val _notifications = MutableSharedFlow<List<Notification>>()
    val notifications = _notifications.asSharedFlow()

    private val _isFailure = MutableSharedFlow<Throwable>()
    val isFailure = _isFailure.asSharedFlow()


    fun getNotifications() {
        val noti = getNotificationsUseCase.execute(1)

        noti.onEach { result ->
            when (result) {
                is Result.Success -> {
                    _notifications.emit(result.data)
                }
                is Result.Failure -> {
                    _isFailure.emit(result.throwable)
                }
                is Result.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }
}