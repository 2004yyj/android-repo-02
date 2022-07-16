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
        }

        viewModel.markFailed.observe(viewLifecycleOwner) {
            val position = it[0] as Int
            val notification = it[1] as Notification
            val throwable = it[2] as Throwable

            notificationAdapter.restoreItem(notification, position)
            Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
        }

        ItemTouchHelper(recyclerViewTouchCallback).attachToRecyclerView(binding.rvNotification)
    }

    private val recyclerViewTouchCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.markNotificationAsRead(
                    position,
                    notificationAdapter.currentList[position]
                )
                notificationAdapter.removeItem(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isActiveNow: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isActiveNow)

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_check) ?: return

                    val view = viewHolder.itemView
                    val paint = Paint()
                    paint.color = ContextCompat.getColor(requireContext(), R.color.primary)

                    val icPadding = Dp2Px.convert(requireContext(), 48F)
                    val icLeft = (view.right - icPadding - icon.intrinsicWidth).toInt()
                    val icTop = view.top + ((view.bottom - view.top - icon.intrinsicHeight) / 2)
                    val icRight = (view.right - icPadding).toInt()
                    val icBottom = icTop + icon.intrinsicHeight

                    c.drawRect(
                        view.right.toFloat() + dX,
                        view.top.toFloat(),
                        view.right.toFloat(),
                        view.bottom.toFloat(),
                        paint
                    )

                    if (-dX < icon.intrinsicWidth + icPadding) {
                        icon.bounds = Rect(0, 0, 0, 0)
                    } else {
                        icon.bounds = Rect(icLeft, icTop, icRight, icBottom)
                        icon.draw(c)
                    }
                }
            }
        }
}
