package com.woowahan.repositorysearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.Notification
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @RetrofitModule.typeApi private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {
    private val _liveNotifications = MutableLiveData<List<Notification>>()
    val liveNotifications: LiveData<List<Notification>>
        get() = _liveNotifications

    fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _liveNotifications.postValue(getNotificationsUseCase.execute(1))
            } catch (e: Exception) {

            }
        }
    }
}