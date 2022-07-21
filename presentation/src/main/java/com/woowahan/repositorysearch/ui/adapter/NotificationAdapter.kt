package com.woowahan.repositorysearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.woowahan.domain.model.Notification
import com.woowahan.repositorysearch.databinding.ItemNotificationBinding
import com.woowahan.repositorysearch.util.TimeFormatter

class NotificationAdapter :
    PagingDataAdapter<Notification, NotificationAdapter.NotificationViewHolder>(diffUtil) {

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: Notification) {
            binding.notification = notification

            binding.root.layoutParams = if (!notification.read) {
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                ViewGroup.LayoutParams(0, 0)
            }
        }
    }


    fun markNotificationAsRead(position: Int): String? {
        snapshot()[position]?.read = true
        notifyItemChanged(position)
        return snapshot()[position]?.id
    }

    fun restoreNotification(position: Int) {
        snapshot()[position]?.read = false
        notifyItemChanged(position)
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotificationBinding.inflate(inflater, parent, false)
        return NotificationViewHolder(binding)
    }
}