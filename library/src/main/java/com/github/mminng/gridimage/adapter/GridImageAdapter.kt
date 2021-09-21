package com.github.mminng.gridimage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.gridimage.R
import com.github.mminng.gridimage.widget.AspectRatioRoundImageView
import com.github.mminng.gridimage.widget.SquareRoundFrameLayout

/**
 * Created by zh on 2021/6/8.
 */
abstract class GridImageAdapter<DATA> : RecyclerView.Adapter<GridImageViewHolder>() {

    companion object {
        private const val SINGLE_TYPE = 1
    }

    private var _spanFixed: Boolean = false
    private var _radius: Int = 0
    private var _aspectRatio: Float = 0.0F
    internal var rawData: List<DATA> = arrayListOf()
    private val data: MutableList<DATA> = arrayListOf()

    private var _itemClick: ((data: List<DATA>, /*real position*/index: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridImageViewHolder {
        val viewHolder: GridImageViewHolder =
            if (viewType == SINGLE_TYPE && !_spanFixed)
                GridImageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_single_image_layout, parent, false)
                )
            else
                GridImageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_grid_image_layout, parent, false)
                )
        bindItemClick(viewHolder)
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GridImageViewHolder, position: Int) {
        if (rawData.size == 4 && position == 2 && !_spanFixed) return
        if (holder.itemViewType == SINGLE_TYPE && !_spanFixed) {
            val singleImage: AspectRatioRoundImageView = holder.findViewById(R.id.item_single_image)
            singleImage.setCornerRadius(_radius)
            onBindView(singleImage, data[position], position)
            singleImage.setAspectRatio(_aspectRatio)
        } else {
            val girdFrame: SquareRoundFrameLayout = holder.findViewById(R.id.item_grid_frame)
            val gridImage: ImageView = holder.findViewById(R.id.item_grid_image)
            val gridMore: TextView = holder.findViewById(R.id.item_grid_image_more)
            girdFrame.setCornerRadius(_radius)
            if (position == 8 && rawData.size > 9) {
                gridMore.visibility = View.VISIBLE
                gridMore.text = "+${(rawData.size - 9)}"
            } else {
                gridMore.visibility = View.INVISIBLE
            }
            onBindView(gridImage, data[position], position)
        }
    }

    override fun getItemViewType(position: Int) =
        if (data.size == 1 && !_spanFixed) SINGLE_TYPE else super.getItemViewType(position)

    override fun getItemCount() = data.size

    internal fun setSpanFixed(spanFixed: Boolean) {
        this._spanFixed = spanFixed
    }

    internal fun setCornerRadius(radius: Int) {
        this._radius = radius
    }

    internal fun setNewData(newData: List<DATA>) {
        if (newData.isEmpty()) return
        this.data.clear()
        if (newData.size == 4 && !_spanFixed) {
            this.data.addAll(newData)
            this.data.add(2, newData[2])
        } else {
            if (newData.size > 9) {
                this.data.addAll(newData.subList(0, 9))
            } else {
                this.data.addAll(newData)
            }
        }
    }

    fun setData(data: List<DATA>) {
        this.rawData = data
    }

    fun setAspectRatio(aspectRatio: Float) {
        this._aspectRatio = aspectRatio
    }

    fun setOnItemClickListener(listener: (data: List<DATA>, /*real position*/index: Int) -> Unit) {
        this._itemClick = listener
    }

    protected abstract fun onBindView(imageView: ImageView, item: DATA, position: Int)

    private fun bindItemClick(viewHolder: GridImageViewHolder) {
        viewHolder.itemView.setOnClickListener {
            _itemClick?.let {
                var position: Int = viewHolder.adapterPosition
                if (position == RecyclerView.NO_POSITION ||
                    (rawData.size == 4 && !_spanFixed && position == 2)
                ) {
                    return@setOnClickListener
                }
                if (rawData.size == 4 && !_spanFixed && position > 2) {
                    position--
                }
                it.invoke(rawData, position)
            }
        }
    }

}