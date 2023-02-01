package com.parizek.myapplication.domain.model

import androidx.compose.ui.graphics.Color

data class PokemonListData(
    val id: Int,
    val idForList: String,
    val nameForList: String = "",
    val spriteForList: String = "",
    val colorsForList: List<Color>
)
