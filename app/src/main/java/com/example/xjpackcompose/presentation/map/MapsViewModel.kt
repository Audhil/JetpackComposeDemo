package com.example.xjpackcompose.presentation.map

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.MapStyleOptions

class MapsViewModel : ViewModel() {

    var state by mutableStateOf(MapState())

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.ToggleFalloutMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if (state.isFalloutMap)
                            null
                        else
                            MapStyleOptions(MapStyle.json)
                    ),
                    isFalloutMap = !state.isFalloutMap
                )
            }

            else -> Unit
        }
    }
}