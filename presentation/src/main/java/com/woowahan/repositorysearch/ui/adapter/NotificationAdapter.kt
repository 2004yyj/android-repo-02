package com.woowahan.repositorysearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.woowahan.domain.model.Notification
import com.woowahan.repositorysearch.databinding.NotificationItemBinding

class NotificationAdapter :
    ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(diffUtil) {
    private val currentList = ArrayList<Notification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        //미리 만들어진 뷰홀더가 없는 경우 새로 생성하는 함수(레이아웃 생성)
        return NotificationViewHolder(
            NotificationItemBinding.inflate(
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

    inner class NotificationViewHolder(private val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: Notification) {
            binding.notificationTitle.text = notification.title
            binding.commentCntTextView.text = notification.commentCnt.toString()
            binding.recentUpdateTextView.text = notification.lastUpdate
            binding.repositoryNameTextView.text = notification.repository

            //Todo: 추후 서버 연동 시 LiveData 에서 이미지 데이터 가져오기
            //binding.userIconLayout.ivUserIcon.load(notification.profileUrl)
            binding.userIconLayout.ivUserIcon.load("https://avatars.githubusercontent.com/u/18213322?v=4")
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