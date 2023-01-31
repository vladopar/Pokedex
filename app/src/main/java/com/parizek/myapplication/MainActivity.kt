package com.parizek.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.parizek.myapplication.pokemon_detail_feature.presentation.PokemonDetailScreen
import com.parizek.myapplication.pokemon_detail_feature.presentation.PokemonDetailViewModel
import com.parizek.myapplication.pokemon_list_feature.presentation.PokemonListScreen
import com.parizek.myapplication.pokemon_list_feature.presentation.PokemonListViewModel
import com.parizek.myapplication.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            PokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "detail"
                    ) {
                        composable("list") {
                            val viewModel: PokemonListViewModel by viewModels()
                            PokemonListScreen(state = viewModel.state)
                        }
                        composable("detail") {
                            val viewModel: PokemonDetailViewModel by viewModels()
                            PokemonDetailScreen(
                                viewModel = viewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}
