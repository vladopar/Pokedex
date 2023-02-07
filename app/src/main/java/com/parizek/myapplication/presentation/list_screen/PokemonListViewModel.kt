package com.parizek.myapplication.presentation.list_screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.parizek.myapplication.core.Resource
import com.parizek.myapplication.data.paging.PokemonListPagingSource
import com.parizek.myapplication.domain.model.PokemonListData
import com.parizek.myapplication.domain.repository.PokemonRepository
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repo: PokemonRepository
) : ViewModel() {

    val listState = LazyGridState()

    fun getPokemonListPaginated(): Flow<PagingData<PokemonListData>> = repo.getPokemonList().cachedIn(viewModelScope)
}