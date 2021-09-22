package com.example.xjpackcompose.data.remote

import com.example.xjpackcompose.data.remote.rabbit.Rabbit
import com.example.xjpackcompose.data.remote.responses.Pokemon
import com.example.xjpackcompose.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon

    @GET
    suspend fun getRandomRabbit(
        @Url
        url: String
    ): Rabbit
}