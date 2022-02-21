package com.example.xjpackcompose.presentation.ui.activity.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/*
* https://www.youtube.com/watch?v=ZX8VsqNO_Ss&list=PLQkwcJG4YTCQHCppNAQmLsj_jW38rU9sC
* https://www.youtube.com/watch?v=sk3svS_fzZM&list=PLQkwcJG4YTCQHCppNAQmLsj_jW38rU9sC&index=2
* https://www.youtube.com/watch?v=za-EEkqJLCQ&list=PLQkwcJG4YTCQHCppNAQmLsj_jW38rU9sC&index=5
* https://www.youtube.com/watch?v=rk6aKkWqqcI&list=PLQkwcJG4YTCQHCppNAQmLsj_jW38rU9sC&index=4
* */
@AndroidEntryPoint
class FlowSampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //  sample - 1 - countDownTimer
//            CountDownTimer()
            //  stateFlow
//            StateFlowSample()
            //  sharedFlow
            SharedFlowSample()
        }
//        forXml()
    }

    @Composable
    private fun SharedFlowSample(viewModel: FlowViewModel = hiltViewModel()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { viewModel.squareNumber(8) },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = viewModel.sharedFlow.collectAsState(0).value.toString())
            }
        }
    }

    //    for xml
//    private val flowViewModel: FlowViewModel by viewModels()
//    private fun forXml() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                flowViewModel.stateFlow.collectLatest { number ->
//                    binding.tvCounter.text = number.toString()
//                }
//            }
//        }
//    }

    //    helper extension function for xml project
    fun <T> ComponentActivity.collectLatestLifecycleFlow(
        flow: Flow<T>,
        collect: suspend (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collect)
            }
        }
    }

    //  helper extension function for shared flow in xml project
//    fun <T> ComponentActivity.collectLifecycleFlow(
//        flow: Flow<T>,
//        collect: suspend (T) -> Unit
//    ) {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                flow.collect(collect)
//            }
//        }
//    }

    @Composable
    private fun StateFlowSample(
        viewModel: FlowViewModel = hiltViewModel()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { viewModel.incrementCounter() },
                modifier = Modifier.align(Alignment.Center)
            ) {
//                Text(text = "click me!")
                Text(text = viewModel.stateFlow.collectAsState().value.toString())
            }
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