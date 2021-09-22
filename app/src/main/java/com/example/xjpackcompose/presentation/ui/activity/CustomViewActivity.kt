package com.example.xjpackcompose.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.xjpackcompose.databinding.ActivityCustomViewBinding

class CustomViewActivity : ComponentActivity() {

    private val binding by lazy {
        ActivityCustomViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() = binding.run {
        btn.setOnClickListener {
            counter.increment()
        }
    }
}