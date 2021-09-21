package com.github.mminng.gridimage.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

/**
 * Created by zh on 2021/6/11.
 */
internal class GridImageLayoutManager(context: Context, spanCount: Int) :
    GridLayoutManager(context, spanCount) {

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }

}