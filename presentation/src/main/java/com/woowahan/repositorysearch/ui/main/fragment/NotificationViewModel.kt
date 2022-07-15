package com.woowahan.repositorysearch.ui.main.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.*
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.domain.notificationUseCase.GetSubjectUseCase
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
    @RetrofitModule.typeGitHub private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    @RetrofitModule.typeApi private val getSubjectUseCase: GetSubjectUseCase
) : ViewModel() {
    private val _notifications = MutableSharedFlow<Notification>()
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
                    result.data.map {
                        getSubject(it)
                    }
                }
                is Result.Failure -> {
                    _isFailure.emit(result.throwable)
                }
                is Result.Loading -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun markNotificationAsRead() {
        markNotificationAsReadUseCase.execute(getCurrentTimeString()).onEach { result ->
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

    fun getSubject(noti: Notification) {
        getSubjectUseCase.execute(noti).onEach { result ->
            when (result) {
                is Result.Success -> {
                    noti.commentCnt = result.data.comments
                    _notifications.emit(noti)
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