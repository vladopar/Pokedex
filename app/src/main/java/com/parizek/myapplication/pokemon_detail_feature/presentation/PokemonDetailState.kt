package com.parizek.myapplication.pokemon_detail_feature.presentation

import com.parizek.myapplication.pokemon_detail_feature.domain.model.Pokemon

data class PokemonDetailState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = false
)
