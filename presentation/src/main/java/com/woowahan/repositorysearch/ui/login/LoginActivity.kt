package com.woowahan.repositorysearch.ui.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.woowahan.domain.model.GitToken
import com.woowahan.repositorysearch.BuildConfig
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.ActivityLoginBinding
import com.woowahan.repositorysearch.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.lifecycleOwner = this

        binding.btnLogin.setOnClickListener {
            val loginUrl = Uri.Builder().scheme("https").authority("github.com")
                .appendPath("login")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", BuildConfig.CLIENT_ID)
                .appendQueryParameter("scope", "repo,notifications,user")
                .build()

            CustomTabsIntent.Builder().build().also {
                it.launchUrl(this, loginUrl)
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
                GitToken.token = result.token
                GitToken.scope = result.scope

                val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(mainIntent)
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