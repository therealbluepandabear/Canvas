package com.realtomjoney.canvas

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet

class MyCanvasView(context: Context) : View(context) {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private val spanCount = 10.0

    private val rectangles = mutableListOf<RectF>()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()

        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)

        val scale = (w / spanCount)

        for (i in 0 until spanCount.toInt()) {
            for (i_2 in 0 until spanCount.toInt()) {
                val left = (i * scale).toFloat()
                val top = (i_2 * scale).toFloat()
                val rect = RectF(
                    left,
                    top,
                    left + scale.toFloat(),
                    top + scale.toFloat()
                )
                Log.d("MY_LOG", "LEFT: ${((i * scale).toFloat())} TOP: ${((i_2 * scale).toFloat())} ")

                rectangles.add(rect)
                extraCanvas.drawRect(
                    rect,
                    Paint().apply {
                        style = Paint.Style.FILL
                        color = Color.WHITE
                    })
            }
        }

        for (i in 0..spanCount.toInt()) {
            for (i_2 in 0..spanCount.toInt()) {
                extraCanvas.drawRect(
                    (i * scale).toFloat(),
                    (i_2 * scale).toFloat(),
                    scale.toFloat(),
                    scale.toFloat(),
                    Paint().apply {
                        color = Color.GRAY
                        style = Paint.Style.STROKE
                    })
            }
        }

    }

    fun validate(x: Float, y: Float) {
        for (rect in rectangles) {
            if (rect.contains(x, y)) {
                extraCanvas.drawRect(
                    rect,
                    Paint().apply {
                        style = Paint.Style.FILL
                        color = Color.BLACK
                    })
                invalidate()
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.rawX
        val y = event.rawY

        when (event.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                validate(x, y)
            }
            MotionEvent.ACTION_DOWN -> {
                validate(x, y)
            }
        }

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }
}