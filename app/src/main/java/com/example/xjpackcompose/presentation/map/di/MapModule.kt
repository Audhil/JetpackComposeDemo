package com.example.xjpackcompose.presentation.map.di

import android.content.Context
import androidx.room.Room
import com.example.xjpackcompose.presentation.map.data.ParkingSpotDatabase
import com.example.xjpackcompose.presentation.map.data.ParkingSpotRepositoryImpl
import com.example.xjpackcompose.presentation.map.domain.repository.ParkingSpotRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapModule {

    @Singleton
    @Provides
    fun provideParkingSpotDatabase(
        @ApplicationContext context: Context
    ): ParkingSpotDatabase =
        Room.databaseBuilder(
            context,
            ParkingSpotDatabase::class.java,
            ParkingSpotDatabase.DB_NAME
        ).build()

    @Singleton
    @Provides
    fun provideParkingSpotRepository(db: ParkingSpotDatabase): ParkingSpotRepository =
        ParkingSpotRepositoryImpl(db.dao)
}