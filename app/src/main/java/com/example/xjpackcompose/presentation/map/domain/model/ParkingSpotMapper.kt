package com.example.xjpackcompose.presentation.map.domain.model

import com.example.xjpackcompose.presentation.map.data.ParkingSpotEntity

fun ParkingSpotEntity.toParkingSpot(): ParkingSpot =
    ParkingSpot(
        lat = lat,
        lng = lng,
        id = id
    )

fun ParkingSpot.toParkingSpotEntity(): ParkingSpotEntity =
    ParkingSpotEntity(
        lat = lat,
        lng = lng,
        id = id
    )