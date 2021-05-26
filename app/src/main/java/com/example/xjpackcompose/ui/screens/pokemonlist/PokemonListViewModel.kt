package com.example.xjpackcompose.ui.screens.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.xjpackcompose.data.model.PokemonListEntry
import com.example.xjpackcompose.repository.PokemonRepository
import com.example.xjpackcompose.util.Constants
import com.example.xjpackcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel
@Inject
constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    private var curPage = 0
    var pokemonList = mutableStateOf<List<PokemonListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            val result = pokemonRepository.getPokemonList(
                limit = Constants.PAGE_SIZE,
                offset = curPage * Constants.PAGE_SIZE
            )
            when (result) {
                is Resource.Success -> {
                    endReached.value = curPage * Constants.PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/"))
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        else
                            entry.url.takeLastWhile { it.isDigit() }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokemonListEntry(
                            pokemonName = entry.name.capitalize(Locale.ROOT),
                            imgUrl = url,
                            number = number.toInt()
                        )
                    }
                    curPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else ->
                    Unit
            }
        }
    }

    fun calcDominantColor(
        drawable: Drawable,
        onFinish: (Color) -> Unit
    ) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue -> onFinish(Color(colorValue)) }
        }
    }
}