package com.parizek.myapplication.pokemon_list_feature.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PokemonListScreen(
    state: PokemonListState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(state.pokemonList) { pokemon ->
                Text(text = pokemon.first)
            }
        }
    }
}