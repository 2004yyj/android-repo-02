package com.woowahan.repositorysearch.ui.result.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val sharedViewModel: ResultViewModel by activityViewModels()
    private val viewModel: SearchViewModel by viewModels()
    private val searchAdapter: SearchResultAdapter by lazy {
        SearchResultAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.setPageName(ResultActivity.PageName.Search.javaClass.simpleName)
        initEditText()
        init()
        initFlow()
    }

    private fun initFlow() = with(binding) {
        lifecycleScope.launchWhenStarted {
            viewModel.repositories.collect {
                searchAdapter.submitData(lifecycle, it)
                rvSearch.scrollToPosition(0)
            }
        }
    }

    private fun init() = with(binding) {
        ibtClear.setOnClickListener {
            edtSearch.setText("")
        }
        rvSearch.addItemDecoration(
            DividerItemDecoration(
            Dp2Px.convert(requireContext(), 1F),
            Dp2Px.convert(requireContext(), 24F),
            ContextCompat.getColor(requireContext(), R.color.navy)
        )
        )
        rvSearch.adapter = searchAdapter.withLoadStateFooter(
            RecyclerViewStateAdapter {
                searchAdapter.retry()
            }
        )
        layoutLoadErrorChecker.btnErrorRetry.setOnClickListener {
            searchAdapter.retry()
        }

        searchAdapter.addLoadStateListener {
            rvSearch.isVisible = it.refresh is LoadState.NotLoading
            with(layoutLoadErrorChecker) {
                root.isVisible = edtSearch.text.isNotEmpty()
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
                ibtClear.isVisible = count > 0
                linearRvEmpty.isVisible = count == 0
                layoutLoadErrorChecker.root.isVisible = count > 0
                if (count > 0) {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    viewModel.getSearchResult(it.toString())
                } else {
                    rvSearch.isVisible = false
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0)
                }
            }
        }
    }

    companion object {
        val TAG = "SearchFragment"
    }
}