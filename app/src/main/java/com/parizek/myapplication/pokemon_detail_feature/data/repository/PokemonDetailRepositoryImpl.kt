package com.parizek.myapplication.pokemon_detail_feature.data.repository

import com.parizek.myapplication.pokemon_detail_feature.core.Resource
import com.parizek.myapplication.pokemon_detail_feature.data.model.toPokemon
import com.parizek.myapplication.pokemon_detail_feature.data.remote.PokemonDetailApi
import com.parizek.myapplication.pokemon_detail_feature.domain.model.Pokemon
import com.parizek.myapplication.pokemon_detail_feature.domain.repository.PokemonDetailRepository
import retrofit2.HttpException
import javax.inject.Inject

class PokemonDetailRepositoryImpl @Inject constructor(
    private val api: PokemonDetailApi
) : PokemonDetailRepository {

    override suspend fun getPokemonDetail(name: String): Resource<Pokemon> {
        return try {
            Resource.Success(
                data = api.getPokemonDetail(name).toPokemon()
            )
        } catch (e: HttpException) {
            Resource.Error(message = "${e.code()}, ${e.message()}")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "An unknown error occured")
        }
    }
}