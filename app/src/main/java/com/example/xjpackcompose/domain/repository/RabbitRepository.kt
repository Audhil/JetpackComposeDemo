package com.example.xjpackcompose.domain.repository

import com.example.xjpackcompose.data.remote.rabbit.Rabbit

interface RabbitRepository {
    suspend fun getRabbit(url: String): Rabbit
}