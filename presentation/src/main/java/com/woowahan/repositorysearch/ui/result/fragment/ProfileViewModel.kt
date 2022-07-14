package com.woowahan.repositorysearch.ui.result.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.Result
import com.woowahan.domain.model.User
import com.woowahan.domain.userUseCase.GetUserUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @RetrofitModule.typeApi
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _user = MutableSharedFlow<User>()
    val user = _user.asSharedFlow()

    private val _isFailure = MutableSharedFlow<Throwable>()
    val isFailure = _isFailure.asSharedFlow()

    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading = _isLoading.asSharedFlow()

    fun getUser() {
        val user = getUserUseCase.execute()
        user.onEach { result ->
            _isLoading.emit(false)
            when(result) {
                is Result.Success -> {
                    _user.emit(result.data)
                }
                is Result.Failure -> {
                    _isFailure.emit(result.throwable)
                }
                is Result.Loading -> {
                    _isLoading.emit(true)
                }
            }
        }.launchIn(viewModelScope)
    }
}