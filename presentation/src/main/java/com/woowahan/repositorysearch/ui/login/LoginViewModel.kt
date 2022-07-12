package com.woowahan.repositorysearch.ui.login

import androidx.lifecycle.ViewModel
import com.woowahan.domain.authUseCase.GetGitHubAccessTokenUseCase
import com.woowahan.domain.model.GitHubToken
import com.woowahan.repositorysearch.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getGitHubAccessTokenUseCase: GetGitHubAccessTokenUseCase
) : ViewModel() {
    suspend fun getAccessToken(code: String): GitHubToken {
        return getGitHubAccessTokenUseCase.execute(
            clientId = BuildConfig.CLIENT_ID,
            clientSecret = BuildConfig.CLIENT_SECRET,
            code = code
        )

    }
}