package com.parizek.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.parizek.myapplication.presentation.detail_screen.PokemonDetailScreen
import com.parizek.myapplication.presentation.detail_screen.PokemonDetailViewModel
import com.parizek.myapplication.presentation.list_screen.PokemonListScreen
import com.parizek.myapplication.presentation.list_screen.PokemonListViewModel
import com.parizek.myapplication.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val pokemonListViewModel: PokemonListViewModel by viewModels()

            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "list"
                    ) {
                        composable("list") {
                            PokemonListScreen(
                                viewModel = pokemonListViewModel,
                                onClick = {
                                    navController.navigate("detail/$it")
                                }
                            )
                        }
                        composable(
                            "detail/{id}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val viewModel: PokemonDetailViewModel by viewModels()
                            PokemonDetailScreen(
                                id = backStackEntry.arguments?.getInt("id")!!,
                                state = viewModel.state,
                                updateCurrentId = {viewModel.updateCurrentId(it)},
                                getPokemonDetail = {viewModel.getPokemonDetail(it)}
                            )
                        }
                    }
                }
            }
        }
    }
}
