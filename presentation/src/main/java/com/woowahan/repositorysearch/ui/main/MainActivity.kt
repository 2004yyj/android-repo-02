package com.woowahan.repositorysearch.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.ActivityMainBinding
import com.woowahan.repositorysearch.ui.adapter.ViewPagerAdapter
import com.woowahan.repositorysearch.ui.main.fragment.IssueFragment
import com.woowahan.repositorysearch.ui.main.fragment.NotificationFragment
import dagger.hilt.android.AndroidEntryPoint
import com.woowahan.repositorysearch.ui.result.ResultActivity
import com.woowahan.repositorysearch.util.dataStore
import com.woowahan.repositorysearch.util.set
import com.woowahan.repositorysearch.util.tokenPrefsKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ivUserIcon: ImageView
    private lateinit var userIconActionView: View
    private lateinit var searchMenu: MenuItem

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
        initFlow()
        initListener()
    }

    private fun init(): Unit = with(binding) {
        userIconActionView = toolbar.menu.findItem(R.id.user_icon).actionView
        searchMenu = toolbar.menu.findItem(R.id.search)
        ivUserIcon = userIconActionView.findViewById(R.id.iv_user_icon)
        viewPager.adapter = ViewPagerAdapter(
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

            lifecycleScope.launchWhenStarted {
                isFailure.collect { throwable ->
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to get profile: Caused By ${throwable.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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

    override fun onDestroy() {
        lifecycleScope.launch(Dispatchers.IO) {
            dataStore.set(tokenPrefsKey, "")
        }
        super.onDestroy()
    }
}