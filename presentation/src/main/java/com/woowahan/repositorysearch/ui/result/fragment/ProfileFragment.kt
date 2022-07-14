package com.woowahan.repositorysearch.ui.result.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.woowahan.repositorysearch.databinding.FragmentProfileBinding
import com.woowahan.repositorysearch.ui.result.ResultActivity
import com.woowahan.repositorysearch.ui.result.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val sharedViewModel: ResultViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.setPageName(ResultActivity.PageName.Profile.javaClass.simpleName)
    }

    companion object {
        val TAG = "ProfileFragment"
    }
}