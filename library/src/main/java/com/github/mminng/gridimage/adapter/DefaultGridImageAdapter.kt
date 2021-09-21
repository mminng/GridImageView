package com.github.mminng.gridimage.adapter

import android.widget.ImageView

/**
 * Created by zh on 2021/6/24.
 */
abstract class DefaultGridImageAdapter : GridImageAdapter<String>() {

    override fun onBindView(imageView: ImageView, item: String, position: Int) {
        onBind(item, imageView)
    }

    protected abstract fun onBind(data: String, view: ImageView)

}