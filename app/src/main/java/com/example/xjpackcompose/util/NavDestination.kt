package com.example.xjpackcompose.util

object NavDestination {
    object Constant {
        const val dominantColor = "dominantColor"
        const val pokemonName = "pokemonName"
    }

    const val POKEMON_LIST_SCREEN = "pokemon_list_screen"
    const val POKEMON_DETAIL_SCREEN =
        "pokemon_detail_screen/{${Constant.dominantColor}}/{${Constant.pokemonName}}"

    const val ANIMATING_CIRCLE_SCREEN = "animating_circle"
}