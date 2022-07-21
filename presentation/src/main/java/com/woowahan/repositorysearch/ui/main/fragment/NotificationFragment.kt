package com.woowahan.repositorysearch.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.FragmentNotificationBinding
import com.woowahan.repositorysearch.ui.adapter.NotificationAdapter
import com.woowahan.repositorysearch.ui.recyclerview.SwipeTouchHelper
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
        binding.vm = viewModel
        binding.recyclerAdapter = notificationAdapter
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotifications()

        initFlow()
        initRecyclerView()
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.isMarkFailed.collect {
                val position = it.first
                val throwable = it.second
                notificationAdapter.restoreNotification(position)
                Toast.makeText(requireContext(), throwable.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() = with(binding) {

        ItemTouchHelper(
            SwipeTouchHelper { position ->
                val threadId = notificationAdapter.markNotificationAsRead(position)
                threadId?.let { viewModel.markNotificationAsRead(position, it) }
            }
        ).attachToRecyclerView(binding.rvNotification)

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
    }
}
