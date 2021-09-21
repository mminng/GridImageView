package com.github.mminng.gridimage.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by zh on 2021/6/7.
 */
internal class GridImageDecoration(
    private var _spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position: Int = parent.getChildAdapterPosition(view)
        val decoration: Int = position % _spanCount
        outRect.left = decoration * spacing / _spanCount
        outRect.right = spacing - (decoration + 1) * spacing / _spanCount
        if (position >= _spanCount) {
            outRect.top = spacing
        }
    }

}