package com.woowahan.repositorysearch.ui.main.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.*
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.domain.notificationUseCase.MarkNotificationAsReadUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    @RetrofitModule.typeApi private val getNotificationsUseCase: GetNotificationsUseCase,
    @RetrofitModule.typeApi private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase
) : ViewModel() {
    private val _notifications = MutableSharedFlow<List<Notification>>()
    val notifications = _notifications.asSharedFlow()

    private val _isFailure = MutableSharedFlow<Throwable>()
    val isFailure = _isFailure.asSharedFlow()


    private val _isMarkedSuccess = MutableSharedFlow<Message>()
    val isMarkedSuccess = _isMarkedSuccess.asSharedFlow()

    private val _isMarkedFail = MutableSharedFlow<Throwable>()
    val isMarkedFail = _isMarkedFail.asSharedFlow()

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

    fun markNotificationAsRead(threadId: String) {
        markNotificationAsReadUseCase.execute(getCurrentTimeString(), threadId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _isMarkedSuccess.emit(result.data)
                }
                is Result.Failure -> {
                    _isMarkedFail.emit(result.throwable)
                }
                is Result.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun getCurrentTimeString(): String {
        val tz = TimeZone.getTimeZone("UTC")
        val df: DateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")

        df.timeZone = tz
        return df.format(Date())
    }
}