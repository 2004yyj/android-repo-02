package com.woowahan.repositorysearch.ui.result.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.woowahan.repositorysearch.ui.recyclerview.DividerItemDecoration
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentSearchBinding
import com.woowahan.repositorysearch.ui.adapter.RecyclerViewStateAdapter
import com.woowahan.repositorysearch.ui.adapter.SearchResultAdapter
import com.woowahan.repositorysearch.ui.result.ResultActivity
import com.woowahan.repositorysearch.ui.result.ResultViewModel
import com.woowahan.repositorysearch.util.Dp2Px
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val sharedViewModel: ResultViewModel by activityViewModels()
    private val viewModel: SearchViewModel by viewModels()
    private val searchAdapter: SearchResultAdapter by lazy {
        SearchResultAdapter()
    }

    private val keyboard: InputMethodManager by lazy {
        requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.recyclerAdapter = searchAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.setPageName(ResultActivity.PageName.Search.javaClass.simpleName)
        init()
        initEditText()
    }

    private fun init() = with(binding) {
        edtSearch.requestFocus()
        keyboard.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT)

        searchAdapter.addLoadStateListener {
            rvSearch.isVisible = it.refresh is LoadState.NotLoading && edtSearch.text.isNotEmpty()
            with(layoutLoadErrorChecker) {
                pbReload.isVisible = it.refresh is LoadState.Loading
                btnErrorRetry.isVisible = it.refresh is LoadState.Error
                tvErrorCause.isVisible = it.refresh is LoadState.Error
                if (it.refresh is LoadState.Error) {
                    tvErrorCause.text =
                        context?.getString(
                            R.string.error,
                            (it.refresh as LoadState.Error).error.message
                        )
                }
            }
        }
    }

    private fun initEditText() = with(binding) {
        edtSearch.doAfterTextChanged {
            it?.let {
                val count = it.length
                rvSearch.isVisible = false
                ibtClear.isVisible = count > 0
                linearRvEmpty.isVisible = count == 0
                layoutLoadErrorChecker.pbReload.isVisible = count > 0
                viewModel.getSearchResult(it.toString())
                edtSearch.setCompoundDrawablesWithIntrinsicBounds(
                    if (count > 0) 0 else R.drawable.ic_search,
                    0,
                    0,
                    0
                )
            }
        }
    }

    companion object {
        val TAG = "SearchFragment"
    }
}