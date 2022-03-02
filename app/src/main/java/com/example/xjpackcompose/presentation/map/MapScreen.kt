package com.example.xjpackcompose.presentation.map

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable

@Composable
fun MapScreen(
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = if (viewModel.state.isFalloutMap) Icons.Default.ThumbUp else Icons.Default.ExitToApp,
                    contentDescription = "Toggle Fallout map"
                )
            }
        }
    ) {

    }
}