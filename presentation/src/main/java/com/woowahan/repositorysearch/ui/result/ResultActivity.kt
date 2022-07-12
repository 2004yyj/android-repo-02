package com.woowahan.repositorysearch.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}