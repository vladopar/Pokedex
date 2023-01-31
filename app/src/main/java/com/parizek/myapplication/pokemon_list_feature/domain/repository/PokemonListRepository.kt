package com.parizek.myapplication.pokemon_list_feature.domain.repository

import com.parizek.myapplication.core.Resource
import com.parizek.myapplication.pokemon_list_feature.domain.model.PokemonList

interface PokemonListRepository {

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>
}