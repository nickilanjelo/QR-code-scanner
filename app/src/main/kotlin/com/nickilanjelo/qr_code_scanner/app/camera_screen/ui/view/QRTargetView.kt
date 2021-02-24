package com.nickilanjelo.qr_code_scanner.app.camera_screen.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class QRTargetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val metrics = context.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val paintWidth = 20f
            val transparentGrayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = Color.GRAY
                alpha = 127
            }
            val transparentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = Color.TRANSPARENT
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            }
            val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = paintWidth
                color = Color.GREEN
                xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
            }
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

            val lines = floatArrayOf(
                //Top left
                x.toFloat(),
                (y + viewSize / 3).toFloat(),
                x.toFloat(),
                y - paintWidth / 2,

                x - paintWidth / 2,
                y.toFloat(),
                x.toFloat() + viewSize / 3,
                y.toFloat(),

                //Bottom left
                x.toFloat(),
                y + ((viewSize * 2) / 3).toFloat(),
                x.toFloat(),
                y + viewSize + paintWidth / 2,

                x.toFloat(),
                (y + viewSize).toFloat(),
                x + viewSize / 3 - paintWidth / 2,
                (y + viewSize).toFloat(),

                //Top right
                (x + ((viewSize * 2) / 3)).toFloat(),
                y.toFloat(),
                (x + viewSize).toFloat(),
                y.toFloat(),

                (x + viewSize).toFloat(),
                y - paintWidth / 2,
                (x + viewSize).toFloat(),
                (y + viewSize / 3).toFloat(),

                //Bottom right
                x + ((viewSize * 2) / 3).toFloat(),
                (y + viewSize).toFloat(),
                x + viewSize + paintWidth / 2,
                (y + viewSize).toFloat(),

                (x + viewSize).toFloat(),
                y + ((viewSize * 2) / 3).toFloat(),
                (x + viewSize).toFloat(),
                y + viewSize + paintWidth / 2
            )

            canvas.drawLines(
                lines,
                greenPaint
            )

            invalidate()
        }
    }
}