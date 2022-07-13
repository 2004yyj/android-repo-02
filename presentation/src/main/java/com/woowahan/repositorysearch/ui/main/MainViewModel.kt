package com.woowahan.repositorysearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.GitToken
import com.woowahan.domain.notificationUseCase.GetNotificationsUseCase
import com.woowahan.repositorysearch.BuildConfig
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @RetrofitModule.typeApi private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {

    fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            val test = getNotificationsUseCase.execute()
            print(test)
        }
    }
}