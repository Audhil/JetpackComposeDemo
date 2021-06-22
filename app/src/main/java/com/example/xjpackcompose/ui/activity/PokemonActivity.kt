package com.example.xjpackcompose.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.xjpackcompose.ui.screens.animatecircle.AnimationCircularProgress
import com.example.xjpackcompose.ui.screens.musicknob.MusicScreen
import com.example.xjpackcompose.ui.screens.pokemondetail.PokemonDetailScreen
import com.example.xjpackcompose.ui.screens.pokemonlist.PokemonListScreen
import com.example.xjpackcompose.ui.screens.timer.TimerScreen
import com.example.xjpackcompose.ui.theme.XJpackComposeTheme
import com.example.xjpackcompose.util.NavDestination
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PokemonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XJpackComposeTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
//                        TopAppBar(title = { Text("Title") })
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                showDialogg(navController)
                            },
                            shape = RectangleShape
                        ) {
                            Icon(Icons.Filled.Add, "Add")
                        }
                    },
                    content = {
                        ScreenContent(navController)
                    }
                )
            }
        }
    }

    @Composable
    private fun ScreenContent(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = NavDestination.POKEMON_LIST_SCREEN
        ) {

            //  #1
            composable(NavDestination.POKEMON_LIST_SCREEN) {
                PokemonListScreen(navController = navController)
            }

            //  #2
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

                PokemonDetailScreen(
                    dominantColor = dominantColor,
                    pokemonName = pokemonName?.toLowerCase(Locale.ROOT) ?: "",
                    navController = navController
                )
            }

            //  #3
            composable(NavDestination.ANIMATING_CIRCLE_SCREEN) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AnimationCircularProgress(
                        percentage = .9f,
                        number = 88
                    )
                }
            }

            //  #4
            composable(NavDestination.MUSIC_KNOB_SCREEN) {
                MusicScreen()
            }

            //  #5
            composable(NavDestination.TIMER_SCREEN) {
                Surface(
                    color = Color(0xFF101010),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        TimerScreen(
                            totalTime = 100L * 1000L,
                            handleColor = Color.Green,
                            inactiveBarColor = Color.DarkGray,
                            activeBarColor = Color(0xFF37B900),
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }
            }
        }
    }

    private fun showDialogg(navController: NavHostController) {
        val options = arrayOf("Animating circle", "Music knob", "Timer")
        val builder = AlertDialog.Builder(this)
        builder.run {
            setTitle("Choose following...")
            setItems(options) { _, which ->
                when (which) {
                    0 ->
                        navController.navigate(NavDestination.ANIMATING_CIRCLE_SCREEN)

                    1 ->
                        navController.navigate(NavDestination.MUSIC_KNOB_SCREEN)

                    2 ->
                        navController.navigate(NavDestination.TIMER_SCREEN)
                }
            }
        }.create().show()
    }
}