package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentIssueBinding
import com.woowahan.repositorysearch.ui.adapter.FilterAdapter
import com.woowahan.repositorysearch.ui.adapter.IssueAdapter
import com.woowahan.repositorysearch.ui.main.DividerItemDecoration
import com.woowahan.repositorysearch.util.Dp2Px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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

    private fun initRecyclerView() {
        binding.rvIssue.adapter = issueAdapter
        val customDecoration =
            DividerItemDecoration(
                Dp2Px.convert(requireContext(), 1F),
                Dp2Px.convert(requireContext(), 24F),
                ContextCompat.getColor(requireContext(), R.color.navy)
            )
        binding.rvIssue.addItemDecoration(customDecoration)

        lifecycleScope.launchWhenStarted {
            viewModel.issue.collect {
                issueAdapter.submitData(lifecycle, it)
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