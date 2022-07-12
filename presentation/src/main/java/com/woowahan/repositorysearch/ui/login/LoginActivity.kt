package com.woowahan.repositorysearch.ui.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import com.woowahan.repositorysearch.BuildConfig
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginButton.setOnClickListener {
            val loginUrl = Uri.Builder().scheme("https").authority("github.com")
                .appendPath("login")
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", "21e566da1e2ebde399b4")
                .build()

            CustomTabsIntent.Builder().build().also {
                it.launchUrl(this, loginUrl)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // ViewModel에서 로그인한거 받아서 처리
        intent?.data?.getQueryParameter("code")?.let {
            print(it)
        }
    }
}