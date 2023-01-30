package com.parizek.myapplication.pokemon_detail_feature.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parizek.myapplication.pokemon_detail_feature.core.Resource
import com.parizek.myapplication.pokemon_detail_feature.domain.model.Pokemon
import com.parizek.myapplication.pokemon_detail_feature.domain.repository.PokemonDetailRepository
import com.parizek.myapplication.ui.theme.PokedexTheme
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
        getPokemonDetail((1..151).random().toString())
    }

    fun getPokemonDetail(name: String) {
        viewModelScope.launch {
            when (val result = repo.getPokemonDetail(name)) {
                is Resource.Success -> {
                    state = state.copy(
                        pokemon = result.data
                    )
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        }
    }
}