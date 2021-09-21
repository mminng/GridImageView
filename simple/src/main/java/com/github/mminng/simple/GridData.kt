package com.github.mminng.simple

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by zh on 2021/6/25.
 */
@Parcelize
data class GridData(val url: String, val w: Float, val h: Float) : Parcelable