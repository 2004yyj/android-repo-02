package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentNotificationBinding
import com.woowahan.repositorysearch.ui.adapter.NotificationAdapter
import com.woowahan.repositorysearch.ui.main.NotificationDivider
import com.woowahan.repositorysearch.ui.main.DividerItemDecoration
import com.woowahan.repositorysearch.ui.main.fragment.NotificationViewModel
import com.woowahan.repositorysearch.util.Dp2Px
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val notificationAdapter = NotificationAdapter()
    
    private val viewModel by viewModels<NotificationViewModel>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvNotification.adapter = notificationAdapter

        val customDecoration =
            DividerItemDecoration(
                Dp2Px.convert(requireContext(), 1F),
                Dp2Px.convert(requireContext(), 24F),
                ContextCompat.getColor(requireContext(), R.color.navy)
            )
        binding.rvNotification.addItemDecoration(customDecoration)
    }
}