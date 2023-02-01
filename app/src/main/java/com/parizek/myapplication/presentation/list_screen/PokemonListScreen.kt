package com.parizek.myapplication.presentation.list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onClick: (String) -> Unit
) {

    val pokemonList = viewModel.pokemonList
    val endReached = viewModel.endReached
    val isLoading = viewModel.isLoading

    val systemUiController = rememberSystemUiController()


    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (pokemonList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
            ) {
                itemsIndexed(pokemonList) { index, pokemon ->
                    if (index >= pokemonList.size - 1 && !endReached && !isLoading) {
                        viewModel.getPokemonList()
                    }
                    Column(
                        modifier = Modifier
                            .clickable {
                                onClick(pokemon.nameForList)
                            }
                    ) {
                        Text(text = pokemon.nameForList.replaceFirstChar { it.uppercase() })
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(pokemon.spriteForList)
                                .scale(Scale.FIT)
                                .build(),
                            contentDescription = null,
                            loading = {
                                CircularProgressIndicator()
                            },
                            modifier = Modifier
                                .size(150.dp)
                        )
                    }
                }
            }
        }
    }
}