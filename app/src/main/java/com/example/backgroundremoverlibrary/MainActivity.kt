package com.example.backgroundremoverlibrary

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.backgroundremoverlibrary.databinding.ActivityMainBinding
import com.slowmac.autobackgroundremover.BackgroundRemover
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener
import java.lang.Exception
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val displayWidth = Resources.getSystem().displayMetrics.widthPixels


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bitmap = getBitmapFromRes(resources, R.drawable.woman, displayWidth)
        BackgroundRemover.bitmapForProcessing(bitmap, object: OnBackgroundChangeListener{
            override fun onSuccess(bitmap: Bitmap) {
                binding.img.setImageBitmap(bitmap)
            }

            override fun onFailed(exception: Exception) {
                Toast.makeText(this@MainActivity, "Error Occur", Toast.LENGTH_SHORT).show()
            }

        })

    }


}