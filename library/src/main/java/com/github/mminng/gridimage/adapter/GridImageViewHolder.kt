package com.github.mminng.gridimage.adapter

import android.content.Context
import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by zh on 2021/6/8.
 */
class GridImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val views: SparseArray<View> = SparseArray()

    internal val context: Context = itemView.context

    internal fun <V : View> findViewById(@IdRes viewId: Int): V {
        val view = getView<V>(viewId)
        checkNotNull(view) { "GridImageHolder:findViewById() must not be null" }
        return view
    }

    @Suppress("UNCHECKED_CAST")
    private fun <V : View> getView(viewId: Int): V? {
        val view = views.get(viewId)
        if (view == null) {
            itemView.findViewById<V>(viewId)?.let {
                views.put(viewId, it)
                return it
            }
        }
        return view as? V
    }

}