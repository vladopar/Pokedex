package com.parizek.myapplication.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.parizek.myapplication.data.dto.pokemon_detail.mapTypeToColor
import com.parizek.myapplication.data.remote.PokemonApi
import com.parizek.myapplication.domain.model.PokemonListData

class PokemonListPagingSource(
    private val pokemonApi: PokemonApi
) : PagingSource<Int, PokemonListData>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonListData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListData> {
        return try {
            val page = params.key ?: 0
            val response = pokemonApi.getPokemons(offset = page * 20)
            val data: List<PokemonListData> = response.results.map{
                val pokemon = pokemonApi.getPokemonDetail(it.name)
                PokemonListData(
                    id = pokemon.id ?: 0,
                    idForList = String.format("%03d", pokemon.id),
                    nameForList = it.name,
                    spriteForList = pokemon.sprites?.other?.officialArtwork?.frontDefault ?: "",
                    colorsForList = mapTypeToColor(pokemon.types.first().type.name)
                )
            }

            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}