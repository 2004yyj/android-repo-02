package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.woowahan.repositorysearch.databinding.FragmentIssueBinding
import com.woowahan.repositorysearch.ui.adapter.FilterAdapter

class IssueFragment : Fragment() {

    private lateinit var binding: FragmentIssueBinding
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

        initSpinner()
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
                val query = filterItem[position]
                // 이슈 검색하기
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}