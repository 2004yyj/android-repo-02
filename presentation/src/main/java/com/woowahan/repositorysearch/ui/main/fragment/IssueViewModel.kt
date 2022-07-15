package com.woowahan.repositorysearch.ui.main.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.woowahan.domain.issueUseCase.GetIssueUseCase
import com.woowahan.domain.model.Issue
import com.woowahan.domain.model.Result
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    @RetrofitModule.typeApi private val getIssueUseCase: GetIssueUseCase<PagingData<Issue>>
): ViewModel() {

    private val _issue = MutableSharedFlow<PagingData<Issue>>()
    val issue = _issue.asSharedFlow()

    fun getIssues(state: String) {
        val pagingData = getIssueUseCase.execute(50, state)
        viewModelScope.launch {
            _issue.emitAll(pagingData)
        }
    }
}