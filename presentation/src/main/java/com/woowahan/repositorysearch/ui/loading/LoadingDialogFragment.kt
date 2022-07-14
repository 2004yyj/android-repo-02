package com.woowahan.repositorysearch.ui.loading

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.woowahan.repositorysearch.databinding.DialogLoadingBinding

class LoadingDialogFragment: DialogFragment() {

    private lateinit var binding: DialogLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLoadingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().run {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
        }
    }



    override fun dismiss() {
        if (isVisible) {
            super.dismiss()
        }
    }
}