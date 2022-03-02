package com.example.xjpackcompose.presentation.map.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ParkingSpotEntity::class],
    version = 1
)
abstract class ParkingSpotDatabase : RoomDatabase() {
    abstract val dao: ParkingSpotDAO

    companion object {
        const val DB_NAME = "ParkingSpotDatabase.db"
    }
}