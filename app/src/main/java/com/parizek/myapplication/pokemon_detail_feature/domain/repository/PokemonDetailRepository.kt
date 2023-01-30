package com.parizek.myapplication.pokemon_detail_feature.domain.repository

import com.parizek.myapplication.pokemon_detail_feature.core.Resource
import com.parizek.myapplication.pokemon_detail_feature.domain.model.Pokemon

interface PokemonDetailRepository {

    suspend fun getPokemonDetail(name: String): Resource<Pokemon>
}