package com.example.xjpackcompose.presentation.map.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ParkingSpotDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParkingSpot(spot: ParkingSpotEntity)

    @Delete
    suspend fun deleteParkingSpot(spot: ParkingSpotEntity)

    @Query("""select * from ParkingSpotEntity""")
    fun getParkingSpots(): Flow<List<ParkingSpotEntity>>
}