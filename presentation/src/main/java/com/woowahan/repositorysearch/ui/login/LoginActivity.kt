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
    private var lastClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.lifecycleOwner = this

        binding.btnLogin.setOnClickListener {
            if (SystemClock.elapsedRealtime() - lastClickTime > 300) {
                val loginUrl = Uri.Builder().scheme("https").authority("github.com")
                    .appendPath("login")
                    .appendPath("oauth")
                    .appendPath("authorize")
                    .appendQueryParameter("client_id", BuildConfig.CLIENT_ID)
                    .appendQueryParameter("scope", "repo,notifications,user")
                    .build()

                showLoading(true)
                CustomTabsIntent.Builder().build().also {
                    it.launchUrl(this, loginUrl)
                }
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isFailure.collect { throwable ->
                showLoading(false)
                Toast.makeText(
                    this@LoginActivity,
                    "Failed To Login: Caused By ${throwable.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.token.collect { result ->
                showLoading(false)
                dataStore.set(tokenPrefsKey, result.token)
                val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        with(binding.layoutLoadErrorChecker) {
            root.isVisible = isShow
            pbReload.isVisible = isShow
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.getQueryParameter("code")?.let { code ->
            viewModel.getAccessToken(code)
        }
    }
}