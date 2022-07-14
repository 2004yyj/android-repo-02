package com.woowahan.repositorysearch.ui.result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainerView
import com.woowahan.repositorysearch.databinding.ActivityResultBinding
import com.woowahan.repositorysearch.ui.result.fragment.ProfileFragment
import com.woowahan.repositorysearch.ui.result.fragment.SearchFragment
import com.woowahan.repositorysearch.util.add
import com.woowahan.repositorysearch.util.onBackPressed
import com.woowahan.repositorysearch.util.replace
import java.io.Serializable

class ResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }
    private val viewModel: ResultViewModel by viewModels()
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        fragmentContainerView = binding.fragmentContainerView
        toolbar = binding.toolbar

        val fragmentManager = supportFragmentManager

        when(intent.getSerializableExtra(START_FRAGMENT_NAME) as PageName) {
            is PageName.Profile -> {
                val login = intent.getStringExtra(LOGIN_VALUE)
                fragmentManager.replace(
                    ProfileFragment::class.java,
                    fragmentContainerView.id,
                    ProfileFragment.TAG,
                    bundleOf(LOGIN_VALUE to login)
                )
            }
            is PageName.Search -> {
                fragmentManager.replace(
                    SearchFragment::class.java,
                    fragmentContainerView.id,
                    SearchFragment.TAG
                )
            }
        }

        toolbar.setNavigationOnClickListener {
            fragmentManager.onBackPressed {
                finish()
            }
        }
    }

    companion object {
        const val LOGIN_VALUE = "LOGIN"
        private const val START_FRAGMENT_NAME = "START_FRAGMENT"

        // 검색 첫 화면
        fun getIntent(context: Context, pageName: PageName.Search): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(START_FRAGMENT_NAME, pageName)
            }
        }

        // 프로필 첫 화면
        fun getIntent(context: Context, pageName: PageName.Profile, login: String): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(START_FRAGMENT_NAME, pageName)
                putExtra(LOGIN_VALUE, login)
            }
        }
    }

    sealed class PageName: Serializable {
        object Profile : PageName()
        object Search: PageName()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
            with(fragmentManager) {
                onBackPressed {
                    finish()
                }
            }
    }
}