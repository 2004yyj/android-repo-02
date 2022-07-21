package com.woowahan.repositorysearch.extension

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.ui.adapter.RecyclerViewStateAdapter
import com.woowahan.repositorysearch.ui.recyclerview.DividerItemDecoration
import com.woowahan.repositorysearch.util.Dp2Px

@BindingAdapter("android:adapter")
fun RecyclerView.setRecyclerViewAdapter(newAdapter: PagingDataAdapter<*, *>) {
    if (adapter == null) {
        adapter = newAdapter.withLoadStateFooter(
            RecyclerViewStateAdapter {
                newAdapter.retry()
            }
        )
        addItemDecoration(
            DividerItemDecoration(
                Dp2Px.convert(context, 1F),
                Dp2Px.convert(context, 24F),
                ContextCompat.getColor(context, R.color.navy)
            )
        )
    }
}

@BindingAdapter("android:listItem")
fun <T: Any> RecyclerView.setListItem(listItem: PagingData<T>) {
    val lifecycleOwner = findViewTreeLifecycleOwner()
    lifecycleOwner?.lifecycleScope?.launchWhenStarted {
        ((adapter as ConcatAdapter).adapters[0] as PagingDataAdapter<T, *>)
            .submitData(lifecycleOwner.lifecycle, listItem)
        scrollToPosition(0)
    }
}

@BindingAdapter("android:listItem")
fun <T: Any> RecyclerView.setListItem(listItem: List<T>) {
    (adapter as ListAdapter<T, *>).submitList(listItem)
    scrollToPosition(0)
}