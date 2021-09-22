package com.example.xjpackcompose.data.repository

import com.example.xjpackcompose.data.remote.PokeApi
import com.example.xjpackcompose.data.remote.rabbit.Rabbit
import com.example.xjpackcompose.domain.repository.RabbitRepository
import javax.inject.Inject

class RabbitRepositoryImpl
@Inject
constructor(
    private val api: PokeApi
) : RabbitRepository {

    override suspend fun getRabbit(url: String): Rabbit = api.getRandomRabbit(url)
}