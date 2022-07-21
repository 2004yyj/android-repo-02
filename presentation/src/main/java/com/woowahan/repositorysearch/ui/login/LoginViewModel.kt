package com.woowahan.repositorysearch.ui.login

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.authUseCase.GetGitHubAccessTokenUseCase
import com.woowahan.domain.model.GitHubToken
import com.woowahan.repositorysearch.BuildConfig
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.woowahan.domain.model.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @RetrofitModule.typeGitHub private val getGitHubAccessTokenUseCase: GetGitHubAccessTokenUseCase
) : ViewModel() {
    private val _login = MutableSharedFlow<Uri>()
    val login = _login.asSharedFlow()

    private val _token = MutableSharedFlow<GitHubToken>()
    val token = _token.asSharedFlow()

    private val _isFailure = MutableSharedFlow<Throwable>()
    val isFailure = _isFailure.asSharedFlow()

    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading = _isLoading.asSharedFlow()

    fun onClickLogin() {
        viewModelScope.launch {
            _login.emit(
                Uri.Builder().scheme("https").authority("github.com")
                    .appendPath("login")
                    .appendPath("oauth")
                    .appendPath("authorize")
                    .appendQueryParameter("client_id", BuildConfig.CLIENT_ID)
                    .appendQueryParameter("scope", "repo,notifications,user")
                    .build()
            )
        }
    }

    fun getAccessToken(code: String) {
        val token = getGitHubAccessTokenUseCase.execute(
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET,
            code = code
        )

        token.onEach { result ->
            _isLoading.emit(result is Result.Loading)
            when(result) {
                is Result.Success -> {
                    _token.emit(result.data)
                }
                is Result.Failure -> {
                    _isFailure.emit(result.throwable)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}