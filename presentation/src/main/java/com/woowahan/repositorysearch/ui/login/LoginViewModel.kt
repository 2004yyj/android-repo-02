package com.woowahan.repositorysearch.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.authUseCase.GetGitHubAccessTokenUseCase
import com.woowahan.domain.model.GitToken
import com.woowahan.repositorysearch.BuildConfig
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @RetrofitModule.typeAuth private val getGitHubAccessTokenUseCase: GetGitHubAccessTokenUseCase
) : ViewModel() {
    val SUCCESS = 1
    val FAIL = 2

    private val _isSuccess = MutableLiveData<Int>()
    val isSuccess: LiveData<Int>
        get() = _isSuccess

    fun getAccessToken(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = getGitHubAccessTokenUseCase.execute(
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET,
                    code = code
                )

                GitToken.token = "$${token.type} ${token.token}"
                GitToken.scope = token.scope
                _isSuccess.postValue(SUCCESS)
            } catch (e: Exception) {
                _isSuccess.postValue(FAIL)
            }
        }
    }
}