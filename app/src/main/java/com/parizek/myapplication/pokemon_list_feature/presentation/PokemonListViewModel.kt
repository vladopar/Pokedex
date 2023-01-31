package com.parizek.myapplication.pokemon_list_feature.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parizek.myapplication.core.Resource
import com.parizek.myapplication.pokemon_list_feature.domain.repository.PokemonListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repo: PokemonListRepository
) : ViewModel() {

    var state by mutableStateOf(PokemonListState())

    init {
        viewModelScope.launch {
            getPokemonList(10, 0)
        }
    }

    suspend fun getPokemonList(limit: Int, offset: Int) {
        when (val result = repo.getPokemonList(limit, offset)) {
            is Resource.Success -> {
                result.data.let { list ->
                    state = state.copy(
                        pokemonList = list?.pokemons ?: emptyList()
                    )
                }
            }

            is Resource.Loading -> {

            }

            is Resource.Error -> {

            }
        }
    }

}