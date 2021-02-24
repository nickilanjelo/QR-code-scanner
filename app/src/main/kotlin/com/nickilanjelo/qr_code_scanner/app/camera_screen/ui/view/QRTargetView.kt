package com.nickilanjelo.qr_code_scanner.app.camera_screen.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.nickilanjelo.qrcodescanner.R

class QRTargetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paintWidth = 20f
    private val transparentGrayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GRAY
        alpha = 200
    }
    private val transparentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }
    private val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = paintWidth
        color = ContextCompat.getColor(context, R.color.purple_500)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val metrics = context.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val metrics = context.resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels

            val minSize = if (width < height) width else height
            val viewSize = (minSize * 0.6).toInt()

            val x = (width - viewSize) / 2
            val y = (height - viewSize) / 2

            //Drawing big rect with transparent gray color
            val bigRect = Rect(0, 0, width, height)
            canvas.drawRect(bigRect, transparentGrayPaint)

            val rect = Rect(x, y, x + viewSize, y + viewSize)
            canvas.drawRect(rect, transparentPaint)

            val cornersPath = Path().also {
                it.moveTo(
                    x.toFloat(),
                    (y + viewSize / 3).toFloat()
                )
                it.lineTo(
                    x.toFloat(),
                    y.toFloat()
                )
                it.lineTo(
                    x.toFloat() + viewSize / 3,
                    y.toFloat()
                )

                it.moveTo(
                    x.toFloat(),
                    y + ((viewSize * 2) / 3).toFloat()
                )
                it.lineTo(
                    x.toFloat(),
                    (y + viewSize).toFloat()
                )
                it.lineTo(
                    (x + (viewSize / 3)).toFloat(),
                    (y + viewSize).toFloat()
                )

                it.moveTo(
                    (x + ((viewSize * 2) / 3)).toFloat(),
                    y.toFloat()
                )
                it.lineTo(
                    (x + viewSize).toFloat(),
                    y.toFloat()
                )
                it.lineTo(
                    (x+ viewSize).toFloat(),
                    (y + (viewSize / 3)).toFloat()
                )

                it.moveTo(
                    (x + viewSize).toFloat(),
                    (y + ((viewSize * 2) / 3)).toFloat()
                )
                it.lineTo(
                    (x + viewSize).toFloat(),
                    (y + viewSize).toFloat()
                )
                it.lineTo(
                    (x + ((viewSize * 2) / 3)).toFloat(),
                    (y + viewSize).toFloat()
                )
            }

            canvas.drawPath(cornersPath, greenPaint)

            invalidate()
        }
    }
}