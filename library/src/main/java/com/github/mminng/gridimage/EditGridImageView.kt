package com.github.mminng.gridimage

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.gridimage.adapter.EditGridImageAdapter
import com.github.mminng.gridimage.decoration.GridImageDecoration
import java.util.*

/**
 * Created by zh on 2021/7/10.
 */
class EditGridImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val spanCount: Int = 3
    private var _spacing: Int = resources.getDimensionPixelSize(R.dimen.default_grid_spacing)
    private var _radius: Int = 0
    private var _maxCount: Int = 9
    private var _removeIconDrawable: Int = R.drawable.ic_grid_remove
    private var _addIconDrawable: Int = R.drawable.ic_grid_add
    private var _addIconBackgroundColor: Int = Color.parseColor("#F1F1F1")

    private var _bindViewListener: ((data: String, view: ImageView) -> Unit)? = null
    private var _itemClickListener: ((data: List<String>, index: Int) -> Unit)? = null
    private var _addClickListener: (() -> Unit)? = null

    private val gridAdapter: EditGridImageAdapter = object : EditGridImageAdapter() {
        override fun onBindView(data: String, view: ImageView, position: Int) {
            _bindViewListener?.invoke(data, view)
        }

        override fun onItemClick(data: List<String>, position: Int) {
            _itemClickListener?.invoke(data, position)
        }

        override fun onAddClick() {
            _addClickListener?.invoke()
        }
    }

    private val dragSortHelper: ItemTouchHelper =
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
                var dragFlags: Int =
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
                if (gridAdapter.isAddType(viewHolder.adapterPosition)) {
                    dragFlags = ItemTouchHelper.ACTION_STATE_IDLE
                }
                return makeMovementFlags(dragFlags, ItemTouchHelper.ACTION_STATE_IDLE)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                if (viewHolder.itemViewType == target.itemViewType) {
                    val data: MutableList<String> = gridAdapter.getDataContainAddType()
                    data.add(
                        target.adapterPosition,
                        data.removeAt(viewHolder.adapterPosition)
                    )
                    gridAdapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                    return true
                }
                return false
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                //NO OP
            }
        })

    init {

        context.theme.obtainStyledAttributes(attrs, R.styleable.EditGridImageView, 0, 0).apply {
            try {
                _spacing =
                    getDimensionPixelSize(R.styleable.EditGridImageView_gridImageSpacing, _spacing)
                _radius =
                    getDimensionPixelSize(R.styleable.EditGridImageView_gridImageRadius, _radius)
                _maxCount = getInteger(R.styleable.EditGridImageView_gridImageMaxCount, _maxCount)
                _removeIconDrawable = getResourceId(
                    R.styleable.EditGridImageView_gridImageRemoveIconDrawable,
                    _removeIconDrawable
                )
                _addIconDrawable = getResourceId(
                    R.styleable.EditGridImageView_gridImageAddIconDrawable,
                    _addIconDrawable
                )
                _addIconBackgroundColor = getColor(
                    R.styleable.EditGridImageView_gridImageAddIconBackgroundColor,
                    _addIconBackgroundColor
                )
            } finally {
                recycle()
            }
        }

        isFocusableInTouchMode = false
        isNestedScrollingEnabled = false
        setHasFixedSize(true)
        layoutManager = GridLayoutManager(context, spanCount)
        addItemDecoration(GridImageDecoration(spanCount, _spacing))
        gridAdapter.setCornerRadius(_radius)
        gridAdapter.setMaxCount(_maxCount)
        gridAdapter.setRemoveIconDrawable(_removeIconDrawable)
        gridAdapter.setAddIconDrawable(_addIconDrawable)
        gridAdapter.setAddIconBackgroundColor(_addIconBackgroundColor)
        adapter = gridAdapter
        dragSortHelper.attachToRecyclerView(this)
    }

    fun add(data: List<String>) {
        gridAdapter.add(data)
        smoothScrollToPosition(0)
    }

    fun add(data: String) {
        gridAdapter.add(data)
        smoothScrollToPosition(0)
    }

    fun getData(): List<String> {
        return gridAdapter.getData()
    }

    fun setOnBindViewListener(listener: (data: String, view: ImageView) -> Unit) {
        _bindViewListener = listener
    }

    fun setOnItemClickListener(listener: (data: List<String>, index: Int) -> Unit) {
        _itemClickListener = listener
    }

    fun setOnAddClickListener(listener: () -> Unit) {
        _addClickListener = listener
    }

}