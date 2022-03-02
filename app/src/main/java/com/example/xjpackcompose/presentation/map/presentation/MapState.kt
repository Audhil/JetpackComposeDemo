package com.example.xjpackcompose.presentation.map.presentation

import com.example.xjpackcompose.presentation.map.domain.model.ParkingSpot
import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(),
    val isFalloutMap: Boolean = false,
    val parkingSpots: List<ParkingSpot> = emptyList()
)