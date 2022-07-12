package com.woowahan.repositorysearch.ui.result.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentProfileBinding

class ProfileFragment(bundle: Bundle) : Fragment() {
    init { arguments = bundle }
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        val TAG = "ProfileFragment"
    }
}