package com.woowahan.repositorysearch.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.repositorysearch.databinding.ItemFooterBinding

class RecyclerViewStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<RecyclerViewStateAdapter.ItemFooterLoadStateViewHolder>() {

    inner class ItemFooterLoadStateViewHolder(
        private val binding: ItemFooterBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) = with(binding) {
            pbFooter.isVisible = loadState is LoadState.Loading
            tvErrorCause.isVisible = loadState is LoadState.Error
            btnErrorRetry.isVisible = loadState is LoadState.Error
            btnErrorRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ItemFooterLoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemFooterLoadStateViewHolder(ItemFooterBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemFooterLoadStateViewHolder, loadState: LoadState) {
        Log.d("Footer", "bind: $loadState")
        holder.bind(loadState)
    }
}