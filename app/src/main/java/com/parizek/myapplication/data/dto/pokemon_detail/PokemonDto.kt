package com.parizek.myapplication.data.dto.pokemon_detail

import androidx.compose.ui.graphics.Color
import com.parizek.myapplication.domain.model.Pokemon
import com.parizek.myapplication.ui.theme.bugDark
import com.parizek.myapplication.ui.theme.bugLight
import com.parizek.myapplication.ui.theme.dragonDark
import com.parizek.myapplication.ui.theme.dragonLight
import com.parizek.myapplication.ui.theme.electricDark
import com.parizek.myapplication.ui.theme.electricLight
import com.parizek.myapplication.ui.theme.fairyDark
import com.parizek.myapplication.ui.theme.fairyLight
import com.parizek.myapplication.ui.theme.fightingDark
import com.parizek.myapplication.ui.theme.fightingLight
import com.parizek.myapplication.ui.theme.fireDark
import com.parizek.myapplication.ui.theme.fireLight
import com.parizek.myapplication.ui.theme.ghostDark
import com.parizek.myapplication.ui.theme.ghostLight
import com.parizek.myapplication.ui.theme.grassDark
import com.parizek.myapplication.ui.theme.grassLight
import com.parizek.myapplication.ui.theme.groundDark
import com.parizek.myapplication.ui.theme.groundLight
import com.parizek.myapplication.ui.theme.iceDark
import com.parizek.myapplication.ui.theme.iceLight
import com.parizek.myapplication.ui.theme.normalDark
import com.parizek.myapplication.ui.theme.normalLight
import com.parizek.myapplication.ui.theme.poisonDark
import com.parizek.myapplication.ui.theme.poisonLight
import com.parizek.myapplication.ui.theme.psychicDark
import com.parizek.myapplication.ui.theme.psychicLight
import com.parizek.myapplication.ui.theme.rockDark
import com.parizek.myapplication.ui.theme.rockLight
import com.parizek.myapplication.ui.theme.waterDark
import com.parizek.myapplication.ui.theme.waterLight

data class PokemonDto(
    val height: Int?,
    val id: Int?,
    val name: String?,
    val sprites: Sprites?,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int?
)

fun PokemonDto.toPokemon(): Pokemon = Pokemon(
    id = id ?: 0,
    idString = String.format("%03d", id),
    name = name?.replaceFirstChar { it.uppercase() } ?: "",
    height = height ?: 0,
    weight = weight ?: 0,
    sprite = sprites?.other?.officialArtwork?.frontDefault ?: "",
    stats = stats.map { stat -> stat.baseStat to mapStatType(stat.stat.name) },
    types = types.map { type -> type.slot to type.type.name.replaceFirstChar { it.uppercase() } },
    colors = mapTypeToColor(types.first().type.name)
)

fun mapStatType(stat: String): String {
    return when (stat) {
        "attack" -> "Attack"
        "defense" -> "Defense"
        "speed" -> "Speed"
        "special-attack" -> "Sp. Atk"
        "special-defense" -> "Sp. Def"
        else -> "HP"
    }
}

fun mapTypeToColor(type: String): List<Color> {
    return when (type) {
        "grass" -> listOf(grassLight, grassDark)
        "fire" -> listOf(fireLight, fireDark)
        "water" -> listOf(waterLight, waterDark)
        "bug" -> listOf(bugLight, bugDark)
        "normal" -> listOf(normalLight, normalDark)
        "poison" -> listOf(poisonLight, poisonDark)
        "electric" -> listOf(electricLight, electricDark)
        "ground" -> listOf(groundLight, groundDark)
        "fairy" -> listOf(fairyLight, fairyDark)
        "fighting" -> listOf(fightingLight, fightingDark)
        "psychic" -> listOf(psychicLight, psychicDark)
        "rock" -> listOf(rockLight, rockDark)
        "ghost" -> listOf(ghostLight, ghostDark)
        "ice" -> listOf(iceLight, iceDark)
        "dragon" -> listOf(dragonLight, dragonDark)
        else -> listOf(Color.LightGray, Color.DarkGray)
    }
}