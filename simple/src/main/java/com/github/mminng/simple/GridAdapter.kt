package com.github.mminng.simple

import android.graphics.BitmapFactory
import android.graphics.Outline
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.mminng.gridimage.adapter.GridImageAdapter

/**
 * Created by zh on 2021/6/25.
 */
class GridAdapter : GridImageAdapter<GridData>() {

    override fun onBindView(imageView: ImageView, item: GridData, position: Int) {
//        setAspectRatio(item.w / item.h)
        val options: RequestOptions = RequestOptions()
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.color.white_200)
//            .optionalTransform(RoundedCorners(imageView.context.resources.getDimensionPixelSize(R.dimen.default_radius)))
        Glide
            .with(imageView.context)
            .load(item.url)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

}