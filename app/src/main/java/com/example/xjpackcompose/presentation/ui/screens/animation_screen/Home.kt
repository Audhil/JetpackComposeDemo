package com.example.xjpackcompose.presentation.ui.screens.animation_screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    var visible by remember {
        mutableStateOf(false)
    }

    Column {
//        if (visible)  //  no animation
//            Text(text = "Hello World!")

        //  with animation - default slide down & slide up anims
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 1000,
                    delayMillis = 50
                )
            ) + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Text(text = "Hello World!")
        }

        Button(
            onClick = { visible = !visible },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Animate")
        }
    }
}

//@ExperimentalAnimationApi
@Composable
fun MainScreen2() {
    var boxState by remember {
        mutableStateOf(BoxState.Start)
    }

    val offset: Dp by animateDpAsState(
        targetValue = if (boxState == BoxState.Start) 5.dp else 300.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioHighBouncy,
//            stiffness = Spring.StiffnessHigh
//        )
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 50,
//            easing = LinearOutSlowInEasing
            easing = CubicBezierEasing(.95f, 0f, .5f, .2f)
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        MyObject(offset = offset)
        Button(
            onClick = {
                boxState = when (boxState) {
                    BoxState.Start -> BoxState.End
                    BoxState.End -> BoxState.Start
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Animate")
        }
    }
}

@Composable
fun MainScreen3() {
    var boxState by remember {
        mutableStateOf(BoxState.Start)
    }

    val scale: Float by animateFloatAsState(
        targetValue = if (boxState == BoxState.Start) 1f else 10f,
        animationSpec = keyframes {
            durationMillis = 1000
            5f.at(400).with(FastOutSlowInEasing)
            delayMillis = 50
        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        MyObject(scale = scale)
        Button(
            onClick = {
                boxState = when (boxState) {
                    BoxState.Start -> BoxState.End
                    BoxState.End -> BoxState.Start
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Animate")
        }
    }
}

enum class BoxState {
    Start,
    End
}

@Composable
fun MyObject(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
    offset: Dp = 0.dp,
    scale: Float = 0f
) {
    Surface(
        modifier = modifier
            .size(50.dp)
            .padding(start = 16.dp)
//            .absoluteOffset(y = offset),
            .absoluteOffset(x = offset)
            .scale(scale = scale),
        color = color,
        content = {}
    )
}