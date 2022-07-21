package com.woowahan.repositorysearch.ui.result.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.woowahan.domain.model.Repository
import com.woowahan.domain.searchUseCase.GetSearchResultUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @RetrofitModule.typeApi
    private val getSearchResultUseCase: GetSearchResultUseCase<PagingData<Repository>>
) : ViewModel() {
    private val _repositories = MutableSharedFlow<PagingData<Repository>>()
    val repositories = _repositories.asSharedFlow()

    private var searchJob: Job? = null

    fun getSearchResult(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotEmpty()) {
                delay(700)
                getSearchResultUseCase.execute(query, 10).collect {
                    _repositories.emit(it)
                }
            } else {
                _repositories.emit(PagingData.empty())
            }
        }
    }
}