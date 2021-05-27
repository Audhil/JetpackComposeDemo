package com.example.xjpackcompose.ui.screens.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.xjpackcompose.data.remote.responses.Pokemon
import com.example.xjpackcompose.repository.PokemonRepository
import com.example.xjpackcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel
@Inject
constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> =
        pokemonRepository.getPokemonInfo(pokemonName = pokemonName)
}