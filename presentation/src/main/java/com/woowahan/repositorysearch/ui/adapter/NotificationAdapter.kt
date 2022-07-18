package com.woowahan.repositorysearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.woowahan.domain.model.Notification
import com.woowahan.repositorysearch.databinding.ItemNotificationBinding
import com.woowahan.repositorysearch.util.TimeFormatter

class NotificationAdapter :
    ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(diffUtil) {
    fun removeItem(position: Int) {
        val currentList = this.currentList.toMutableList()
        currentList.removeAt(position)
        this.submitList(currentList)
    }

    fun restoreItem(notification: Notification, position: Int) {
        val currentList = this.currentList.toMutableList()
        currentList.add(position, notification)
        this.submitList(currentList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        //미리 만들어진 뷰홀더가 없는 경우 새로 생성하는 함수(레이아웃 생성)
        return NotificationViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        //실제로 뷰홀더가 뷰에 그려졌을때 데이터를 뿌려주는 바인드해주는 함수(뷰홀더가 재활용될때 실행)
        holder.bind(currentList[position])
    }

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: Notification) {
            binding.tvNotificationTitle.text = notification.title
            binding.tvCommentCnt.text = notification.commentCnt.toString()
            binding.tvRecentUpdate.text = TimeFormatter.toRelativeTime(notification.lastUpdate)
            binding.tvRepositoryName.text = notification.repository
            binding.layoutUserIcon.ivUserIcon.load(notification.profileUrl)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem == newItem
            }
        }
    }
}