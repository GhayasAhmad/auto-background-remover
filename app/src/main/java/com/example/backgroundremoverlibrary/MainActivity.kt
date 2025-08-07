package com.example.backgroundremoverlibrary

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import com.example.backgroundremoverlibrary.databinding.ActivityMainBinding
import com.slowmac.autobackgroundremover.removeBackground
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val imageResult =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { uri ->
                binding.img.setImageURI(uri)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageResult.launch("image/*")

        binding.removeBgBtn.setOnClickListener {
            removeBg()
        }
    }


    private fun removeBg() {
        binding.img.invalidate()
        val bitmap = binding.img.drawable.toBitmap()
        lifecycleScope.launch {
            binding.img.setImageBitmap(
                bitmap.removeBackground(applicationContext)
            )
        }
    }


}