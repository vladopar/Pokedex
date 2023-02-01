package com.parizek.myapplication.domain.repository

import com.parizek.myapplication.core.Resource
import com.parizek.myapplication.domain.model.Pokemon
import com.parizek.myapplication.domain.model.PokemonListData

interface PokemonRepository {

    suspend fun getPokemonDetail(name: String): Resource<Pokemon>

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<List<PokemonListData>>
}