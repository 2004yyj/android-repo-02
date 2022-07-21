package com.woowahan.repositorysearch.ui.result.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.woowahan.domain.model.Repository
import com.woowahan.domain.searchUseCase.GetSearchResultUseCase
import com.woowahan.repositorysearch.di.module.RetrofitModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @RetrofitModule.typeApi
    private val getSearchResultUseCase: GetSearchResultUseCase<PagingData<Repository>>
) : ViewModel() {
    private val _repositories = MutableStateFlow<PagingData<Repository>>(PagingData.empty())
    val repositories = _repositories.asStateFlow()

    private var searchJob: Job? = null

    fun getSearchResult(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotEmpty()) {
                delay(700)
                getSearchResultUseCase.execute(query, 10).cachedIn(viewModelScope).collect {
                    _repositories.emit(it)
                }
            } else {
                _repositories.emit(PagingData.empty())
            }
        }
    }
}