package com.woowahan.repositorysearch.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.User
import com.woowahan.domain.model.Result
import com.woowahan.domain.userUseCase.GetUserUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @RetrofitModule.typeApi
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    private val _isFailure = MutableSharedFlow<Throwable>()
    val isFailure = _isFailure.asSharedFlow()

    fun getUser() {
        val user = getUserUseCase.execute()
        Log.d("MainViewModel", "getUser: ")
        user.onEach { result ->
            when(result) {
                is Result.Success -> {
                    Log.d("MainViewModel", "getUser: success")
                    _user.emit(result.data)
                }
                is Result.Failure -> {
                    Log.d("MainViewModel", "getUser: failure")
                    _isFailure.emit(result.throwable)
                    Log.d("MainViewModel", "getUser: ${result.throwable.message}")
                }
                is Result.Loading -> {
                    Log.d("MainViewModel", "getUser: loading")
                }
            }
        }.launchIn(viewModelScope)
    }
}