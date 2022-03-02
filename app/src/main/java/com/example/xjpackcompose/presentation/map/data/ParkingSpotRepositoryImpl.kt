package com.example.xjpackcompose.presentation.map.data

import com.example.xjpackcompose.presentation.map.domain.model.ParkingSpot
import com.example.xjpackcompose.presentation.map.domain.model.toParkingSpot
import com.example.xjpackcompose.presentation.map.domain.model.toParkingSpotEntity
import com.example.xjpackcompose.presentation.map.domain.repository.ParkingSpotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ParkingSpotRepositoryImpl(
    private val dao: ParkingSpotDAO
) : ParkingSpotRepository {

    override suspend fun insertParkingSpot(spot: ParkingSpot) =
        dao.insertParkingSpot(spot = spot.toParkingSpotEntity())

    override suspend fun deleteParkingSpot(spot: ParkingSpot) =
        dao.deleteParkingSpot(spot = spot.toParkingSpotEntity())

    override fun getParkingSpots(): Flow<List<ParkingSpot>> =
        dao.getParkingSpots().map { spots ->
            spots.map { it.toParkingSpot() }
        }
}