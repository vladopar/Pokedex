package com.parizek.myapplication.domain.repository

import androidx.paging.PagingData
import com.parizek.myapplication.core.Resource
import com.parizek.myapplication.data.dto.pokemon_list.PokemonResult
import com.parizek.myapplication.domain.model.Pokemon
import com.parizek.myapplication.domain.model.PokemonListData
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonDetail(name: String): Resource<Pokemon>

    fun getPokemonList(): Flow<PagingData<PokemonListData>>
}