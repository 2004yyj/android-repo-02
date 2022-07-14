package com.woowahan.repositorysearch.ui.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        //Todo: 추후 서버 연동 시 LiveData 에서 이미지 데이터 가져오기
        ivUserIcon.load("https://avatars.githubusercontent.com/u/18213322?v=4")
    }

    private fun initTabLayout() = with(binding) {
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabText[position]
        }.attach()
    }

    private fun initListener() {
        userIconActionView.setOnClickListener {
            // 사용자 아이콘 클릭 시
            val intent = ResultActivity.getIntent(this, ResultActivity.PageName.Profile, "2004yyj")
            startActivity(intent)
        }

        searchMenu.setOnMenuItemClickListener {
            // 검색 버튼 클릭 시
            return@setOnMenuItemClickListener true
        }
    }
}