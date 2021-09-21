package com.github.mminng.gridimage

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.github.mminng.gridimage.adapter.GridImageAdapter
import com.github.mminng.gridimage.decoration.GridImageDecoration
import com.github.mminng.gridimage.layoutmanager.GridImageLayoutManager

/**
 * Created by zh on 2021/6/7.
 */
class GridImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val spanCount: Int = 3
    private var _spanFixed: Boolean = false
    private var _spacing: Int = resources.getDimensionPixelSize(R.dimen.default_grid_spacing)
    private var _radius: Int = 0
    private val gridLayoutManager = GridImageLayoutManager(context, spanCount)

    init {

        context.theme.obtainStyledAttributes(attrs, R.styleable.GridImageView, 0, 0).apply {
            try {
                _spacing =
                    getDimensionPixelSize(R.styleable.GridImageView_gridImageSpacing, _spacing)
                _spacing = if (_spacing < 0) 0 else _spacing
                _radius = getDimensionPixelSize(R.styleable.GridImageView_gridImageRadius, _radius)
                _spanFixed = getBoolean(R.styleable.GridImageView_gridImageFixed, _spanFixed)
            } finally {
                recycle()
            }
        }

        isFocusableInTouchMode = false
        isNestedScrollingEnabled = false
        setHasFixedSize(true)
        layoutManager = gridLayoutManager
        addItemDecoration(GridImageDecoration(spanCount, _spacing))

    }

    fun <DATA> setAdapter(adapter: GridImageAdapter<DATA>) {
        if (!_spanFixed) {
            if (adapter.rawData.size == 1) {
                gridLayoutManager.spanCount = 2
            } else {
                gridLayoutManager.spanCount = 3
            }
        }
        adapter.setCornerRadius(_radius)
        adapter.setSpanFixed(_spanFixed)
        adapter.setNewData(adapter.rawData)
        this.adapter = adapter
    }

}