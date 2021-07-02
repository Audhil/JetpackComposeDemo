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
    const val MUSIC_KNOB_SCREEN = "music_knob_screen"
    const val TIMER_SCREEN = "timer_screen"
    const val DROP_DOWN_SCREEN = "drop_down_screen"
    const val SUPPORT_ALL_SCREEN_SIZES = "support_all_screen_sizes"
}