package com.parizek.myapplication.pokemon_list_feature.data.repository

import com.parizek.myapplication.core.Resource
import com.parizek.myapplication.pokemon_list_feature.data.dto.toPokemonList
import com.parizek.myapplication.pokemon_list_feature.data.remote.PokemonListApi
import com.parizek.myapplication.pokemon_list_feature.domain.model.PokemonList
import com.parizek.myapplication.pokemon_list_feature.domain.repository.PokemonListRepository
import retrofit2.HttpException
import javax.inject.Inject

class PokemonListRepositoryImpl @Inject constructor(
    private val api: PokemonListApi
) : PokemonListRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        return try {
            Resource.Success(
                data = api.getPokemons(limit, offset).toPokemonList()
            )
        } catch (e: HttpException) {
            Resource.Error(message = "${e.code()}, ${e.message()}")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "An unknown error occured")
        }
    }
}