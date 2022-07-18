package com.woowahan.repositorysearch.ui.result.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentSearchBinding
import com.woowahan.repositorysearch.ui.result.ResultActivity
import com.woowahan.repositorysearch.ui.result.ResultViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val sharedViewModel: ResultViewModel by activityViewModels()

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
    }

    private fun init() = with(binding) {
        ibtClear.setOnClickListener {
            edtSearch.setText("")
        }
    }

    private fun initEditText() = with(binding) {
        edtSearch.doAfterTextChanged {
            it?.let {
                val count = it.length
                if (count > 0) {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    ibtClear.visibility = View.VISIBLE
                } else {
                    edtSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0)
                    ibtClear.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        val TAG = "SearchFragment"
    }
}