package com.woowahan.repositorysearch.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.databinding.SpinnerFilterDropdownBinding
import com.woowahan.repositorysearch.databinding.SpinnerFilterTopBinding

class FilterAdapter(
    context: Context,
    @ArrayRes
    filterListResources: Int
): ArrayAdapter<String>(context, 0, context.resources.getStringArray(filterListResources)) {

    private var checkedItem = 0

    private lateinit var topBinding: SpinnerFilterTopBinding
    private lateinit var dropdownBinding: SpinnerFilterDropdownBinding

    private val startAnim = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_start)
    private val endAnim = AnimationUtils.loadAnimation(context, R.anim.anim_rotate_end)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            topBinding = SpinnerFilterTopBinding.inflate(inflater, parent, false)
        }
        val view: View = convertView ?: topBinding.root
        bindTopView(position)

        return view
    }

    private fun bindTopView(position: Int) = with(topBinding) {
        checkedItem = position
        root.viewTreeObserver.addOnWindowFocusChangeListener { hasFocus ->
            if (hasFocus) topBinding.ivArrow.startAnimation(endAnim)
            else topBinding.ivArrow.startAnimation(startAnim)
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            dropdownBinding = SpinnerFilterDropdownBinding.inflate(inflater, parent, false)
        }
        val view: View = convertView ?: dropdownBinding.root
        bindDropDownView(position)
        return view
    }

    private fun bindDropDownView(position: Int) = with(dropdownBinding) {
        tvTitle.text = getItem(position)
        if (position == checkedItem) {
            tvTitle.setTextColor(context.getColor(R.color.white))
            ivChecked.visibility = View.VISIBLE
        } else {
            tvTitle.setTextColor(context.getColor(R.color.grey))
            ivChecked.visibility = View.GONE
        }
    }
}