package com.parizek.myapplication.pokemon_list_feature.data.dto

import com.parizek.myapplication.pokemon_list_feature.domain.model.PokemonList

data class PokemonListDto(
    val count: Int?,
    val next: String?,
    val previous: Any?,
    val results: List<PokemonResult>
)

fun PokemonListDto.toPokemonList(): PokemonList =
    PokemonList(results.map { it.name to it.url })


