package com.woowahan.repositorysearch.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.domain.model.Repository
import com.woowahan.repositorysearch.databinding.ItemSearchBinding
import com.woowahan.repositorysearch.util.ColorCreator

class SearchResultAdapter :
    PagingDataAdapter<Repository, SearchResultAdapter.ItemSearchViewHolder>(diffUtil) {

    private val colorMap = hashMapOf<String, Int>()

    inner class ItemSearchViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) = with(binding) {
            binding.repository = repository
            if (repository.language != null) {
                if (!colorMap.containsKey(repository.language))
                    colorMap[repository.language!!] = ColorCreator.create()
                cvLanguage.setCardBackgroundColor(colorMap[repository.language!!]?.let {
                    ColorStateList.valueOf(it)
                })
            }
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