package com.example.xjpackcompose.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.xjpackcompose.ui.screens.pokemonlist.PokemonListScreen
import com.example.xjpackcompose.ui.theme.XJpackComposeTheme
import com.example.xjpackcompose.util.NavDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XJpackComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavDestination.POKEMON_LIST_SCREEN
                ) {
                    composable(NavDestination.POKEMON_LIST_SCREEN) {
                        PokemonListScreen(navController = navController)
                    }

                    composable(
                        NavDestination.POKEMON_DETAIL_SCREEN,
                        arguments = listOf(
                            navArgument(NavDestination.Constant.dominantColor) {
                                type = NavType.IntType
                            },
                            navArgument(NavDestination.Constant.pokemonName) {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt(NavDestination.Constant.dominantColor)
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString(NavDestination.Constant.pokemonName)
                        }
                    }
                }
            }
        }
    }
}