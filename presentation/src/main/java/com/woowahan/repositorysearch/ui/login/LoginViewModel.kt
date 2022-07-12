package com.woowahan.repositorysearch.ui.login

import androidx.lifecycle.ViewModel
import com.woowahan.domain.authUseCase.GetGitHubAccessTokenUseCase

class LoginViewModel(
    private val getGitHubAccessTokenUseCase: GetGitHubAccessTokenUseCase
) : ViewModel() {
}