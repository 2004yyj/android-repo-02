package com.woowahan.repositorysearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.woowahan.domain.model.GitToken
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.ActivityMainBinding
import com.woowahan.repositorysearch.ui.adapter.ViewPagerAdapter
import com.woowahan.repositorysearch.ui.main.fragment.IssueFragment
import com.woowahan.repositorysearch.ui.main.fragment.NotificationFragment
import dagger.hilt.android.AndroidEntryPoint
import com.woowahan.repositorysearch.ui.result.ResultActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val tabText = arrayOf("Issue", "Notification")

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var ivUserIcon: ImageView
    private lateinit var userIconActionView: View
    private lateinit var searchMenu: MenuItem
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        initFlow()
        initTabLayout()
        initListener()
    }

    private fun init(): Unit = with(binding) {
        userIconActionView = toolbar.menu.findItem(R.id.user_icon).actionView
        searchMenu = toolbar.menu.findItem(R.id.search)
        ivUserIcon = userIconActionView.findViewById(R.id.iv_user_icon)
        viewPagerAdapter = ViewPagerAdapter(
            this@MainActivity,
            listOf(IssueFragment(), NotificationFragment())
        )

        viewModel.getUser()
    }

    private fun initFlow() {
        viewModel.run {
            lifecycleScope.launchWhenStarted {
                user.collect {
                    ivUserIcon.load(it.avatar)
                }
            }
        }
    }

    private fun initTabLayout() = with(binding) {
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabText[position]
        }.attach()
    }

    private fun initListener() {
        userIconActionView.setOnClickListener {
            val intent = ResultActivity.getIntent(this, ResultActivity.PageName.Profile)
            startActivity(intent)
        }

        searchMenu.setOnMenuItemClickListener {
            val intent = ResultActivity.getIntent(this, ResultActivity.PageName.Search)
            startActivity(intent)
            return@setOnMenuItemClickListener true
        }
    }
}