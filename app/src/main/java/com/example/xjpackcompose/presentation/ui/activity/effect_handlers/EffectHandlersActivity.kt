package com.example.xjpackcompose.presentation.ui.activity.effect_handlers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch


class EffectHandlersActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheWrong()
            //  samples - LaunchedEffect
            LaunchedEffectSample()
            LaunchedEffectSample2()
            //  sample - rememberCoroutineScope
            RememberCoroutineScopeSample()
            //  sample - rememberUpdatedState
            RememberUpdatedStateWrongSample({})
            RememberUpdatedStateSample({})
            //  sample - disposableEffect - to avoid any leak
            DisposableEffectSample()
            //  sample - SideEffect
            SideEffectSample()
            //  samples - produceState
            produceStateSample()
            produceStateEquivalentFlowSample()
            //  samples - DerivedStateOf
            WithOutDerivedStateOfSample()
            WithDerivedStateOfSample()
            //  samples - snapshotFlow - converts composable state to flow(opposite to collectAsState)
            SnapshotFlowSample()
        }
    }

    @Composable
    private fun SnapshotFlowSample() {
        val scaffoldState = rememberScaffoldState()
        LaunchedEffect(key1 = scaffoldState) {
            snapshotFlow { scaffoldState.snackbarHostState }
                .mapNotNull { it.currentSnackbarData?.message }
                .distinctUntilChanged()
                .collect { message ->
                    println("A snackbar with message: $message was shown!")
                }
        }
    }

    @Composable
    fun WithDerivedStateOfSample() {
        var counter by remember {
            mutableStateOf(0)
        }
        val textIs by derivedStateOf {  //  this blocks computes once and caches the result,
            // whenever any state inside this block updates,
            // it'll recompute and store new cache,
            // and also informs other listeners too
            "The counter is : $counter"
        }
        Button(onClick = { counter++ }) {
            Text(text = textIs)
        }
    }

    @Composable
    fun WithOutDerivedStateOfSample() {
        var counter by remember {
            mutableStateOf(0)
        }
        val textIs = "The counter is : $counter"
        Button(onClick = { counter++ }) {   //  whenever the counter changes, "textIs" is recomputed again
            Text(text = textIs)
        }
    }

    @Composable
    fun produceStateEquivalentFlowSample(countUpto: Int = 10): State<Int> {
        return flow {
            var value = 0
            while (value < countUpto) {
                delay(1000L)
                value++
                emit(value)
            }
        }.collectAsState(initial = 0)   //  converts flow to composable state
    }

    @Composable
    fun produceStateSample(countUpto: Int = 10): State<Int> {
        return produceState(initialValue = 0) {
            while (value < countUpto) {
                delay(1000L)
                value++
            }
        }
    }

    @Composable
    fun SideEffectSample(nonComposeCounter: Int = 0) {
        SideEffect {
            println("Called after every successful recomposition")  //  for eg., set firebase userId for every recomposition, we can do it here
        }
    }

    @Composable
    fun DisposableEffectSample() {
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(key1 = lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_PAUSE)
                    println("yup it is onPause()ed")
            }
            lifecycleOwner.lifecycle.addObserver(observer)  //  add
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)   //  remove
            }
        }
    }

    @Composable
    fun RememberUpdatedStateSample(
        onTimeOut: () -> Unit
    ) {
        val updatedTimeout by rememberUpdatedState(newValue = onTimeOut)
        LaunchedEffect(key1 = true) {
            delay(3000L)
            updatedTimeout() //  it'll launch the latest onTimeOut which has been set
        }
    }

    @Composable
    fun RememberUpdatedStateWrongSample(
        onTimeOut: () -> Unit
    ) {
        LaunchedEffect(key1 = true) {
            delay(3000L)
            onTimeOut() //  it'll launch the old onTimeOut(), won't consider newer onTimeOut if set
        }
    }

    @Composable
    fun RememberCoroutineScopeSample() {
        val scope = rememberCoroutineScope()
//        scope.launch {  }   //  this is side effect, it should be launched in LaunchedEffect or any callback as shown below
        Button(onClick = {
            scope.launch {
                delay(1000L)
                println("this is response from network!")
            }
        }) {

        }
    }

    @Composable
    private fun LaunchedEffectSample(
        viewModel: EffectHandlerViewModel = hiltViewModel()
    ) =
        LaunchedEffect(key1 = true) {    //  true - represents this coroutine/block is launched for first time when compose is drawn
            viewModel.sharedFlow.collect { event ->
                when (event) {
                    is EffectHandlerViewModel.ScreenEvents.ShowSnackBar -> println("yup: showing snack bar")
                    is EffectHandlerViewModel.ScreenEvents.Navigate -> println("yup: Navigate to next screen")
                }
            }
        }

    @Composable
    private fun LaunchedEffectSample2(
        count: Int = 0
    ) {
        val animatable = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = count) {  //  whenever count changes, this block coroutine is cancelled and executed again
            animatable.animateTo(count.toFloat())
        }
    }

    //  wrong sample of side effects in composable
    private var i = 0

    @Composable
    fun TheWrong() {
        var text by remember {
            mutableStateOf("")
        }
        Button(onClick = { text += "#" }) {
            i++ //  this is side effect, whenever this composable recomposes for UI, it'll increment the variable
            Text(text = text)
        }
    }
}