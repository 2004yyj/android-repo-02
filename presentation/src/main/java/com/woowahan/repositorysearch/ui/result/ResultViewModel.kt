package com.woowahan.repositorysearch.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel: ViewModel() {
    private val _pageName = MutableLiveData<String>()
    val pageName: LiveData<String> = _pageName

    fun setPageName(pageName: String) {
        _pageName.value = pageName
    }
}