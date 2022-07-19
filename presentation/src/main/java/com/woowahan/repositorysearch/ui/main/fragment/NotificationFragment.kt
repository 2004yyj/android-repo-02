package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentNotificationBinding
import com.woowahan.repositorysearch.ui.adapter.NotificationAdapter
import com.woowahan.repositorysearch.ui.adapter.RecyclerViewStateAdapter
import com.woowahan.repositorysearch.ui.recyclerview.DividerItemDecoration
import com.woowahan.repositorysearch.ui.recyclerview.SwipeTouchHelper
import com.woowahan.repositorysearch.util.Dp2Px
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val notificationAdapter: NotificationAdapter by lazy { NotificationAdapter() }

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

        viewModel.markFailed.observe(viewLifecycleOwner) {
            val position = it[0] as Int
            val throwable = it[1] as Throwable

            notificationAdapter.restoreNotification(position)

            Toast.makeText(requireContext(), throwable.message.toString(), Toast.LENGTH_SHORT)
                .show()
        }

        ItemTouchHelper(SwipeTouchHelper {
            val threadId = notificationAdapter.markNotificationAsRead(it)
            threadId?.let { it1 -> viewModel.markNotificationAsRead(it, it1) }
        }).attachToRecyclerView(binding.rvNotification)

        init()
        initFlow()
    }

    private fun initFlow() = with(binding) {
        lifecycleScope.launchWhenStarted {
            viewModel.notifications.collect {
                notificationAdapter.submitData(lifecycle, it)
                rvNotification.scrollToPosition(0)
            }
        }
    }

    private fun init() = with(binding) {
        val customDecoration =
            DividerItemDecoration(
                Dp2Px.convert(requireContext(), 1F),
                Dp2Px.convert(requireContext(), 24F),
                ContextCompat.getColor(requireContext(), R.color.navy)
            )
        rvNotification.addItemDecoration(customDecoration)

        rvNotification.adapter = notificationAdapter.withLoadStateFooter(
            RecyclerViewStateAdapter {
                notificationAdapter.retry()
            }
        )


        layoutLoadErrorChecker.root.visibility = View.VISIBLE
        layoutLoadErrorChecker.btnErrorRetry.setOnClickListener {
            notificationAdapter.retry()
        }
        notificationAdapter.addLoadStateListener {
            with(layoutLoadErrorChecker) {
                pbReload.isVisible = it.refresh is LoadState.Loading
                btnErrorRetry.isVisible = it.refresh is LoadState.Error
                tvErrorCause.isVisible = it.refresh is LoadState.Error
                if (it.refresh is LoadState.Error) {
                    tvErrorCause.text =
                        context?.getString(
                            R.string.error,
                            (it.refresh as LoadState.Error).error.message
                        )
                }
            }
        }

        viewModel.getNotifications()
    }
}
