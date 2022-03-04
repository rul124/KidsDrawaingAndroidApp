package com.example.kidsdrawaingandroidapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private var drawingView: DrawingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set the brush size
        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())
        
        val ibBrush : ImageButton = findViewById(R.id.ib_brush)
        ibBrush.setOnClickListener{
            showBrushSizeChooserDialog()
        }
    }
    
    private fun showBrushSizeChooserDialog() {
        // a pop-up
        val brushDialog = Dialog(this)
        // the dialog should look like this
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size: ")

        val smallBtn = brushDialog.findViewById<ImageButton>(R.id.ib_small_brush)
        smallBtn.setOnClickListener {
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }

        val mediumBtn = brushDialog.findViewById<ImageButton>(R.id.ib_medium_brush)
        mediumBtn.setOnClickListener {
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }

        val largeBtn = brushDialog.findViewById<ImageButton>(R.id.ib_large_brush)
        largeBtn.setOnClickListener {
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }

        brushDialog.show()
    }
}