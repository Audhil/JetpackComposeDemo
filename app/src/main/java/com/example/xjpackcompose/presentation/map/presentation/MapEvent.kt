package com.example.xjpackcompose.presentation.map.presentation

import com.example.xjpackcompose.presentation.map.domain.model.ParkingSpot
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    object ToggleFalloutMap : MapEvent()
    data class OnMapLongClick(val latLng: LatLng) : MapEvent()
    data class OnInfoWindowLongClick(val parkingSpot: ParkingSpot) : MapEvent()
}