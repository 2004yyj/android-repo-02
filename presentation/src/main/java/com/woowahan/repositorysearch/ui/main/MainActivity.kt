package com.woowahan.repositorysearch.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val tabText = arrayOf("Issue", "Notification")

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var ivUserIcon: ImageView
    private lateinit var userIconActionView: View
    private lateinit var searchMenu: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        initListener()
    }

    private fun init(): Unit = with(binding) {
        userIconActionView = toolbar.menu.findItem(R.id.user_icon).actionView
        searchMenu = toolbar.menu.findItem(R.id.search)
        ivUserIcon = userIconActionView.findViewById(R.id.iv_user_icon)

        //Todo: 추후 서버 연동 시 LiveData 에서 이미지 데이터 가져오기
        ivUserIcon.load("https://avatars.githubusercontent.com/u/18213322?v=4")
    }


    private fun initListener() {
        userIconActionView.setOnClickListener {
            // 사용자 아이콘 클릭 시
        }

        searchMenu.setOnMenuItemClickListener {
            // 검색 버튼 클릭 시
            return@setOnMenuItemClickListener true
        }
    }
}