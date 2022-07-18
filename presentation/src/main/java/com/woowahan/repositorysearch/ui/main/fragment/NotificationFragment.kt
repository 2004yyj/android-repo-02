package com.woowahan.repositorysearch.ui.main.fragment

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.domain.model.Notification
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentNotificationBinding
import com.woowahan.repositorysearch.ui.adapter.NotificationAdapter
import com.woowahan.repositorysearch.ui.main.DividerItemDecoration
import com.woowahan.repositorysearch.ui.recyclerview.SwipeTouchHelper
import com.woowahan.repositorysearch.util.Dp2Px
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap


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

        viewModel.getNotifications()

        viewModel.run {
            lifecycleScope.launchWhenStarted {
                notifications.collect { notificationList ->
                    notificationAdapter.submitList(notificationList)
                }
            }

            lifecycleScope.launchWhenStarted {
                isFailure.collect { throwable ->
                    Toast.makeText(
                        requireContext(),
                        "Failed to get notifications: Caused By ${throwable.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.markFailed.observe(viewLifecycleOwner) {
            val position = it[0] as Int
            val notification = it[1] as Notification
            val throwable = it[2] as Throwable

            notificationAdapter.restoreItem(notification, position)
            Toast.makeText(requireContext(), throwable.message.toString(), Toast.LENGTH_SHORT)
                .show()
        }

        ItemTouchHelper(SwipeTouchHelper {
            viewModel.markNotificationAsRead(
                it,
                notificationAdapter.currentList[it]
            )
            notificationAdapter.removeItem(it)
        }).attachToRecyclerView(binding.rvNotification)
    }


}
