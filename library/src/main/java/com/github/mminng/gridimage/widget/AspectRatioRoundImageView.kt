package com.github.mminng.gridimage.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Created by zh on 2021/6/19.
 */
internal class AspectRatioRoundImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val MAX_RATIO: Float = 5.0F / 3.0F
        private const val MIN_RATIO: Float = 5.0F / 8.0F
    }

    private var _radius: Float = 0.0F
    private var _aspectRatio: Float = 3.0F / 4.0F

    private val rect: RectF = RectF()
    private val path: Path = Path()
    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(
                (MeasureSpec.getSize(widthMeasureSpec) / _aspectRatio).roundToInt(),
                MeasureSpec.EXACTLY
            )
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val min: Float = min(width, height) / 2.0F
        _radius = if (_radius > min) min else _radius
    }

    override fun onDraw(canvas: Canvas?) {
        if (_radius > 0) {
            canvas?.let {
                rect.set(0.0F, 0.0F, width.toFloat(), height.toFloat())
                val saved: Int = canvas.saveLayer(rect, null)
                super.onDraw(canvas)
                drawTopLeft(canvas)
                drawTopRight(canvas)
                drawBottomLeft(canvas)
                drawBottomRight(canvas)
                canvas.restoreToCount(saved)
            }
        } else {
            super.onDraw(canvas)
        }
    }

    fun setAspectRatio(aspectRatio: Float) {
        if (aspectRatio == 0F) return
        when {
            aspectRatio > MAX_RATIO -> {
                this._aspectRatio = MAX_RATIO
            }
            aspectRatio < MIN_RATIO -> {
                this._aspectRatio = MIN_RATIO
            }
            else -> {
                this._aspectRatio = aspectRatio
            }
        }
    }

    fun setCornerRadius(radius: Int) {
        this._radius = radius.toFloat()
    }

    private fun drawTopLeft(canvas: Canvas) {
        rect.set(
            0.0F,
            0.0F,
            _radius * 2.0F,
            _radius * 2.0F
        )
        path.moveTo(0.0F, _radius)
        path.lineTo(0.0F, 0.0F)
        path.lineTo(_radius, 0.0F)
        path.arcTo(rect, -90.0F, -90.0F)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawTopRight(canvas: Canvas) {
        rect.set(
            width.toFloat() - 2.0F * _radius,
            0.0F,
            width.toFloat(),
            _radius * 2.0F
        )
        path.moveTo(width - _radius, 0.0F)
        path.lineTo(width.toFloat(), 0.0F)
        path.lineTo(width.toFloat(), _radius)
        path.arcTo(rect, 0.0F, -90.0F)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawBottomLeft(canvas: Canvas) {
        rect.set(
            0.0F,
            height.toFloat() - 2.0F * _radius,
            _radius * 2.0F,
            height.toFloat()
        )
        path.moveTo(0.0F, height.toFloat() - _radius)
        path.lineTo(0.0F, height.toFloat())
        path.lineTo(_radius, height.toFloat())
        path.arcTo(rect, 90.0F, 90.0F)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawBottomRight(canvas: Canvas) {
        rect.set(
            width.toFloat() - 2.0F * _radius,
            height.toFloat() - 2.0F * _radius,
            width.toFloat(),
            height.toFloat()
        )
        path.moveTo(width.toFloat() - _radius, height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat() - _radius)
        path.arcTo(rect, 0.0F, 90.0F)
        path.close()
        canvas.drawPath(path, paint)
    }

}