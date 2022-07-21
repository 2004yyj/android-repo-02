package com.woowahan.repositorysearch.extension

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@BindingAdapter("android:viewPager", "android:tabs")
fun TabLayout.setMediator(viewPager: ViewPager2, tabs: Array<String>) {
    TabLayoutMediator(this, viewPager) { tab, position ->
        tab.text = tabs[position]
    }.attach()
}