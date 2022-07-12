package com.woowahan.repositorysearch.ui.login

import androidx.lifecycle.ViewModel
import com.woowahan.domain.authUseCase.GetGitHubAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getGitHubAccessTokenUseCase: GetGitHubAccessTokenUseCase
) : ViewModel() {
    suspend fun getAccessToken(code: String) {
        getGitHubAccessTokenUseCase.execute(
            clientId = "",
            clientSecret = "",
            code = code
        )
    }
}