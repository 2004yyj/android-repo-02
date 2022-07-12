package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woowahan.repositorysearch.databinding.FragmentNotificationBinding
import com.woowahan.repositorysearch.ui.adapter.NotificationAdapter

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val notificationAdapter = NotificationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notificationRecyclerView.adapter = notificationAdapter
    }
}