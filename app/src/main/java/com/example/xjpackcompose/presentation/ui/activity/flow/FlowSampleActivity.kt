package com.example.xjpackcompose.presentation.ui.activity.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/*
* https://www.youtube.com/watch?v=ZX8VsqNO_Ss&list=PLQkwcJG4YTCQHCppNAQmLsj_jW38rU9sC
* https://www.youtube.com/watch?v=sk3svS_fzZM&list=PLQkwcJG4YTCQHCppNAQmLsj_jW38rU9sC&index=2
* */
class FlowSampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //  sample - 1 - countDownTimer
            CountDownTimer()
        }
    }

    @Composable
    fun CountDownTimer() {
        val viewModel = viewModel<FlowViewModel>()
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = viewModel.countDownFlow.collectAsState(10).value.toString(),
                fontSize = 30.sp,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}