package com.example.kidsdrawaingandroidapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mDrawPath : CustomPath? = null
    private var mCanvasBitmap : Bitmap? = null

    // Paint class holds the style and color information about how to draw geometries
    // text and bitmaps
    private var mDrawPaint : Paint? = null
    private var mCanvasPaint : Paint? =null
    private var mBrushSize : Float = 0.toFloat()
    private var color = Color.BLACK
    private var canvas : Canvas? = null
    private val mPaths = ArrayList<CustomPath>() // Store those path in an arraylist

    init{
        setUpDrawing()
    }

    private fun setUpDrawing() {
        // this function will set up all the objects ready
        mDrawPaint = Paint()
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE  // FILL / STROKE / ...
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND

        mDrawPath = CustomPath(color, mBrushSize)

        // set up canvas paint
        // DITHER_FLAG : paint flag that enables dithering when blitting
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        mBrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }

    // Change Canvas to Canvas? if fails
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)

        // save all those paths
        for(path in mPaths) {
            mDrawPaint!!.strokeWidth = path.brushThickness
            mDrawPaint!!.color = path.color
            canvas.drawPath(path, mDrawPaint!!)
        }

        if(!mDrawPath!!.isEmpty) {
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // Get event coordinates
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize

                mDrawPath!!.reset() // clear previous events
                if(touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.moveTo(touchX, touchY)
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if(touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.lineTo(touchX, touchY)
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                // save the path
                mPaths.add(mDrawPath!!)
                mDrawPath = CustomPath(color, mBrushSize)
            }

            else -> return false // don't do anything
        }

        invalidate() // invalidate the view if it's true
        return true
    }


    internal inner class CustomPath(var color: Int,
                                    var brushThickness: Float) : Path() {

    }
}