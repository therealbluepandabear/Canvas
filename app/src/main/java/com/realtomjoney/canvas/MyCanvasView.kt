package com.realtomjoney.canvas

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

class MyCanvasView(context: Context) : View(context) {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
}