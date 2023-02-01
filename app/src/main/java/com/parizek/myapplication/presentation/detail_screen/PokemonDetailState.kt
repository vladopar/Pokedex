package com.parizek.myapplication.presentation.detail_screen

import androidx.compose.ui.graphics.Color
import com.parizek.myapplication.domain.model.Pokemon

data class PokemonDetailState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = false,
    val currentId: Int = 0,
)
