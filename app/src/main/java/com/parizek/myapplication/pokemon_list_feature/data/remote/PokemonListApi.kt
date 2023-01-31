package com.parizek.myapplication.pokemon_list_feature.data.remote

import com.parizek.myapplication.pokemon_list_feature.data.dto.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Query

private const val END_POINT = "pokemon"

interface PokemonListApi {

    @GET(END_POINT)
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListDto
}