package com.example.xjpackcompose.presentation.rabbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.xjpackcompose.presentation.rabbit.components.RabbitScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RabbitsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RabbitScreen()
        }
    }
}