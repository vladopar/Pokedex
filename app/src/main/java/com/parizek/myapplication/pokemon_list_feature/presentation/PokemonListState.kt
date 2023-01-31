package com.parizek.myapplication.pokemon_list_feature.presentation

data class PokemonListState(
    val pokemonList: List<Pair<String, String>> = emptyList()
)
