package com.github.mminng.gridimage.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.gridimage.R
import com.github.mminng.gridimage.widget.SquareRoundFrameLayout

/**
 * Created by zh on 2021/7/10.
 */
abstract class EditGridImageAdapter : RecyclerView.Adapter<GridImageViewHolder>() {

    private companion object {
        private const val ADD_TYPE: Int = 1
    }

    private var _radius: Int = 0
    private var _maxCount: Int = 9
    private var _removeIconDrawable: Int = R.drawable.ic_grid_remove
    private var _addIconDrawable: Int = R.drawable.ic_grid_add
    private var _addIconBackgroundColor: Int = Color.parseColor("#F1F1F1")
    private var _hasAddType: Boolean = true

    private val data: MutableList<String> = arrayListOf()

    init {
        data.add("")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridImageViewHolder {
        val viewHolder: GridImageViewHolder
        if (viewType == ADD_TYPE) {
            viewHolder = GridImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_edit_grid_add_layout, parent, false)
            )
            val addFrame: SquareRoundFrameLayout =
                viewHolder.findViewById(R.id.item_edit_grid_add_frame)
            addFrame.setCornerRadius(_radius)
            val backgroundView: View = viewHolder.findViewById(R.id.item_edit_grid_add_background)
            backgroundView.setBackgroundColor(_addIconBackgroundColor)
            val addImage: ImageView = viewHolder.findViewById(R.id.item_edit_grid_add_image)
            addImage.setImageResource(_addIconDrawable)
            addClickListener(viewHolder)
        } else {
            viewHolder = GridImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_edit_grid_image_layout, parent, false)
            )
            val imageFrame: SquareRoundFrameLayout =
                viewHolder.findViewById(R.id.item_edit_grid_frame)
            imageFrame.setCornerRadius(_radius)
            val removeImage: ImageView = viewHolder.findViewById(R.id.item_edit_grid_remove_image)
            removeImage.setImageResource(_removeIconDrawable)
            itemClickAndRemoveClickListener(viewHolder)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: GridImageViewHolder, position: Int) {
        if (_hasAddType && position == itemCount - 1) {
            return
        }
        val imageview: ImageView = holder.findViewById(R.id.item_edit_grid_image)
        onBindView(data[position], imageview, position)
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        if (_hasAddType && position == itemCount - 1) {
            return ADD_TYPE
        }
        return super.getItemViewType(position)
    }

    internal fun add(source: List<String>) {
        data.addAll(0, source)
        if (data.size > _maxCount) {
            data.removeAt(data.lastIndex)
            notifyItemRemoved(data.lastIndex)
            _hasAddType = false
        }
        notifyItemRangeInserted(0, source.size)
    }

    internal fun add(source: String) {
        data.add(0, source)
        if (data.size > _maxCount) {
            data.removeAt(data.lastIndex)
            notifyItemRemoved(data.lastIndex)
            _hasAddType = false
        }
        notifyItemInserted(0)
    }

    private fun remove(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        if (!_hasAddType) {
            data.add("")
            _hasAddType = true
        }
    }

    internal fun getData(): List<String> {
        return data.filter { it.isNotEmpty() }
    }

    internal fun getDataContainAddType(): MutableList<String> {
        return data
    }

    internal fun isAddType(position: Int): Boolean {
        return getItemViewType(position) == ADD_TYPE
    }

    internal fun setCornerRadius(radius: Int) {
        this._radius = radius
    }

    internal fun setMaxCount(maxCount: Int) {
        this._maxCount = maxCount
    }

    internal fun setRemoveIconDrawable(removeIconDrawable: Int) {
        this._removeIconDrawable = removeIconDrawable
    }

    internal fun setAddIconDrawable(addIconDrawable: Int) {
        this._addIconDrawable = addIconDrawable
    }

    internal fun setAddIconBackgroundColor(addIconBackgroundColor: Int) {
        this._addIconBackgroundColor = addIconBackgroundColor
    }

    protected abstract fun onBindView(data: String, view: ImageView, position: Int)

    protected abstract fun onItemClick(data: List<String>, position: Int)

    protected abstract fun onAddClick()

    private fun itemClickAndRemoveClickListener(viewHolder: GridImageViewHolder) {
        viewHolder.itemView.setOnClickListener {
            val position: Int = viewHolder.adapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }
            onItemClick(data.filter { it.isNotEmpty() }, position)
        }
        viewHolder.findViewById<ImageView>(R.id.item_edit_grid_remove_image).setOnClickListener {
            val position: Int = viewHolder.adapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }
            remove(position)
        }
    }

    private fun addClickListener(viewHolder: GridImageViewHolder) {
        viewHolder.findViewById<SquareRoundFrameLayout>(R.id.item_edit_grid_add_frame)
            .setOnClickListener {
                val position: Int = viewHolder.adapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                onAddClick()
            }
    }

}