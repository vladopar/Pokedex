package com.parizek.myapplication.pokemon_detail_feature.domain.model

import androidx.compose.ui.graphics.Color
import com.parizek.myapplication.ui.theme.grassDark
import com.parizek.myapplication.ui.theme.grassLight

data class Pokemon(
    val id: Int,
    val idString: String,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprite: String,
    val stats: List<Pair<Int, String>>,
    val types: List<Pair<Int, String>>,
    val colors: List<Color>
)
