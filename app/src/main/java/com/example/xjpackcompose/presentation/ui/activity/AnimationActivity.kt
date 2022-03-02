package com.example.xjpackcompose.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.xjpackcompose.presentation.ui.screens.animation_screen.MainScreen3

//  ref: https://www.youtube.com/watch?v=Z5GYz8L6Ubc&t=4s
class AnimationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
//                MainScreen()
//                MainScreen2()
                MainScreen3()
            }
        }
    }
}