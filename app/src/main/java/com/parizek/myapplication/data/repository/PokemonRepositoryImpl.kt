package com.parizek.myapplication.data.repository

import com.parizek.myapplication.core.Resource
import com.parizek.myapplication.data.dto.pokemon_detail.toPokemon
import com.parizek.myapplication.data.dto.pokemon_list.PokemonResult
import com.parizek.myapplication.data.remote.PokemonApi
import com.parizek.myapplication.domain.model.Pokemon
import com.parizek.myapplication.domain.model.PokemonListData
import com.parizek.myapplication.domain.repository.PokemonRepository
import retrofit2.HttpException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {

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

    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<List<PokemonListData>> {
        return try {
            val result = api.getPokemons(limit, offset)
            val data: List<PokemonListData> = result.results.map {
                PokemonListData(
                    nameForList = it.name,
                    spriteForList = api.getPokemonDetail(it.name).sprites?.other?.officialArtwork?.frontDefault
                        ?: ""
                )
            }
            Resource.Success(
                data = data
            )
        } catch (e: HttpException) {
            Resource.Error(message = "${e.code()}, ${e.message()}")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "An unknown error occured")
        }
    }
}