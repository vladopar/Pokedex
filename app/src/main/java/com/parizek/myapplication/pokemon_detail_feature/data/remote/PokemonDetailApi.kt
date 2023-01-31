package com.parizek.myapplication.pokemon_detail_feature.data.remote

import com.parizek.myapplication.pokemon_detail_feature.data.dto.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Path

private const val END_POINT = "pokemon/{name}"

interface PokemonDetailApi {

    @GET(END_POINT)
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDto

}