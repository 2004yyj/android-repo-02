package com.woowahan.repositorysearch.ui.recyclerview

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.woowahan.repositorysearch.R
import com.woowahan.repositorysearch.util.Dp2Px

class SwipeTouchHelper(
    private val onSwiped: (Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.absoluteAdapterPosition
        onSwiped(position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isActiveNow: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isActiveNow)

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val icon =
                ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_check) ?: return

            val view = viewHolder.itemView
            val paint = Paint()
            paint.color = ContextCompat.getColor(recyclerView.context, R.color.primary)

            val icPadding = Dp2Px.convert(recyclerView.context, 48F)
            val icLeft = (view.right - icPadding - icon.intrinsicWidth).toInt()
            val icTop = view.top + ((view.bottom - view.top - icon.intrinsicHeight) / 2)
            val icRight = (view.right - icPadding).toInt()
            val icBottom = icTop + icon.intrinsicHeight

            c.drawRect(
                view.right.toFloat() + dX,
                view.top.toFloat(),
                view.right.toFloat(),
                view.bottom.toFloat(),
                paint
            )

            if (-dX < icon.intrinsicWidth + icPadding) {
                icon.bounds = Rect(0, 0, 0, 0)
            } else {
                icon.bounds = Rect(icLeft, icTop, icRight, icBottom)
                icon.draw(c)
            }
        }
    }
}