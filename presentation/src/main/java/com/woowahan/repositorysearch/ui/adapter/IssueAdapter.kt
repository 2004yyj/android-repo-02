package com.woowahan.repositorysearch.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.woowahan.domain.model.Issue
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.ItemIssueBinding
import com.woowahan.repositorysearch.util.TimeFormatter

class IssueAdapter: PagingDataAdapter<Issue, IssueAdapter.IssueViewHolder>(diffUtil) {
    inner class IssueViewHolder(
        private val binding: ItemIssueBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: Issue) {
            binding.issue = issue
        }
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return IssueViewHolder(ItemIssueBinding.inflate(inflater, parent, false))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Issue>() {
            override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}