package com.woowahan.repositorysearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.woowahan.domain.model.Repository
import com.woowahan.repositorysearch.databinding.ItemSearchBinding

class SearchResultAdapter :
    PagingDataAdapter<Repository, SearchResultAdapter.ItemSearchViewHolder>(diffUtil) {
    inner class ItemSearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repository) = with(binding) {
            layoutUserIcon.ivUserIcon.load(item.avatarUrl)
            tvUsername.text = item.login
            tvRepoTitle.text = item.name
            if (item.description == null) {
                tvDescription.visibility = View.GONE
            } else {
                tvDescription.visibility = View.VISIBLE
                tvDescription.text = item.description
            }
            tvLanguage.text = item.language
            tvStarred.text = item.starred.toString()
        }
    }

    override fun onBindViewHolder(holder: ItemSearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)
        return ItemSearchViewHolder(binding)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}