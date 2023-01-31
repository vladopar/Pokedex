package com.parizek.myapplication.pokemon_detail_feature.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parizek.myapplication.core.Resource
import com.parizek.myapplication.pokemon_detail_feature.domain.repository.PokemonDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repo: PokemonDetailRepository
) : ViewModel() {

    var state by mutableStateOf(PokemonDetailState())
        private set

    init {
        viewModelScope.launch {
            getPokemonDetail((1..151).random().toString())
        }
    }

    suspend fun getPokemonDetail(name: String) {
            when (val result = repo.getPokemonDetail(name)) {
                is Resource.Success -> {
                    result.data.let {pokemon ->
                        state = state.copy(
                            pokemon = pokemon,
                            currentId = pokemon?.id ?: 0,
                        )
                    }
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
    }

    fun updateCurrentId(i: Int) {
        state = state.copy(currentId = state.currentId + i)
    }
}