package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentIssueBinding
import com.woowahan.repositorysearch.ui.adapter.FilterAdapter
import com.woowahan.repositorysearch.ui.adapter.IssueAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssueFragment : Fragment() {

    private lateinit var binding: FragmentIssueBinding
    private val viewModel: IssueViewModel by viewModels()
    private val issueAdapter by lazy { IssueAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIssueBinding.inflate(inflater)
        binding.vm = viewModel
        binding.recyclerAdapter = issueAdapter
        binding.spinnerAdapter = FilterAdapter(requireContext(), R.array.filter)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding) {
        issueAdapter.addLoadStateListener {
            rvIssue.isVisible = it.refresh is LoadState.NotLoading
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
}