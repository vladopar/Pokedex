package com.parizek.myapplication.pokemon_detail_feature.presentation

import androidx.compose.ui.graphics.Color
import com.parizek.myapplication.pokemon_detail_feature.domain.model.Pokemon

data class PokemonDetailState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = false,
    val currentId: Int = 0,
)
