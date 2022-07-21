package com.woowahan.repositorysearch.ui.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.woowahan.repositorysearch.BuildConfig
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.ActivityLoginBinding
import com.woowahan.repositorysearch.ui.main.MainActivity
import com.woowahan.repositorysearch.util.dataStore
import com.woowahan.repositorysearch.util.set
import com.woowahan.repositorysearch.util.tokenPrefsKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initFlow()
    }

    private fun initFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect { loginUri ->
                CustomTabsIntent.Builder().build().also {
                    it.launchUrl(this@LoginActivity, loginUri)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isFailure.collect { throwable ->
                Toast.makeText(
                    this@LoginActivity,
                    "Failed To Login: Caused By ${throwable.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.token.collect { result ->
                dataStore.set(tokenPrefsKey, result.token)
                val intent = MainActivity.getIntent(this@LoginActivity)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter("code")?.let { code ->
            viewModel.getAccessToken(code)
        }
    }
}