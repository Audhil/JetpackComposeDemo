package com.example.xjpackcompose.presentation.map

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class MapsViewModel : ViewModel() {

    var state by mutableStateOf(MapState())
}