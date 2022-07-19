package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentIssueBinding
import com.woowahan.repositorysearch.ui.adapter.FilterAdapter
import com.woowahan.repositorysearch.ui.adapter.IssueAdapter
import com.woowahan.repositorysearch.ui.recyclerview.DividerItemDecoration
import com.woowahan.repositorysearch.ui.adapter.RecyclerViewStateAdapter
import com.woowahan.repositorysearch.util.Dp2Px
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssueFragment : Fragment() {

    private lateinit var binding: FragmentIssueBinding
    private val viewModel: IssueViewModel by viewModels()
    private val issueAdapter by lazy { IssueAdapter() }
    private val filterItem = listOf("Opened", "Closed", "All")
    private val filterAdapter: FilterAdapter by lazy {
        FilterAdapter(requireContext(), filterItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIssueBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initSpinner()
    }

    private fun initRecyclerView() = with(binding) {
        rvIssue.adapter = issueAdapter.withLoadStateFooter(
            RecyclerViewStateAdapter {
                issueAdapter.retry()
            }
        )
        rvIssue.addItemDecoration(DividerItemDecoration(
            Dp2Px.convert(requireContext(), 1F),
            Dp2Px.convert(requireContext(), 24F),
            ContextCompat.getColor(requireContext(), R.color.navy)
        ))


        layoutLoadErrorChecker.btnErrorRetry.setOnClickListener {
            issueAdapter.retry()
        }

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

        lifecycleScope.launchWhenStarted {
            viewModel.issue.collect {
                issueAdapter.submitData(lifecycle, it)
                rvIssue.scrollToPosition(0)
            }
        }
    }

    private fun initSpinner() {
        binding.spiFilter.adapter = filterAdapter
        binding.spiFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getIssues(
                    when(filterItem[position]) {
                        "Opened" -> "open"
                        "Closed" -> "closed"
                        "All" -> "all"
                        else -> "all"
                    }
                )
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}