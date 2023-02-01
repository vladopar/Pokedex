package com.parizek.myapplication.data.remote

import com.parizek.myapplication.data.dto.pokemon_detail.PokemonDto
import com.parizek.myapplication.data.dto.pokemon_list.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val END_POINT = "pokemon"

interface PokemonApi {

    @GET("$END_POINT/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDto

    @GET(END_POINT)
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListDto

}