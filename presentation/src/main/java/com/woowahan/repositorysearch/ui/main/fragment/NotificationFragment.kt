package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.woowahan.domain.model.Notification
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentNotificationBinding
import com.woowahan.repositorysearch.ui.adapter.NotificationAdapter
import com.woowahan.repositorysearch.ui.main.NotificationDivider
import com.woowahan.repositorysearch.util.Dp2Px

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
        notificationAdapter.submitList(getDummy())

        val customDecoration =
            NotificationDivider(
                Dp2Px.convert(requireContext(), 1F),
                Dp2Px.convert(requireContext(), 24F),
                ContextCompat.getColor(requireContext(), R.color.navy)
            )
        binding.notificationRecyclerView.addItemDecoration(customDecoration)
    }

    private fun getDummy(): List<Notification> {
        val list = ArrayList<Notification>()

        for (i in 1..10) {
            val item = Notification(0, "test$i", "${i}일 전", "repository #$i", i, "")
            list.add(item)
        }
        return list
    }
}