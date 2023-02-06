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

    var pokemonList by mutableStateOf<List<PokemonListData>>(listOf())
    var endReached by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    val listState = LazyGridState()

    var currentPage = 0
    val pageSize = 20


    init {
//            getPokemonList(pageSize, currentPage * pageSize)
    }

    fun getPokemonListPaginated(): Flow<PagingData<PokemonListData>> = repo.getPokemonList().cachedIn(viewModelScope)


//    fun getPokemonList(limit: Int = pageSize, offset: Int = currentPage * pageSize) {
//        viewModelScope.launch {
//            isLoading = true
//            when (val result = repo.getPokemonList(limit, offset)) {
//                is Resource.Success -> {
//                    endReached = currentPage * pageSize >= 131
//                    result.data?.let { list ->
//                        pokemonList += list
//                    }
//                    currentPage++
//                    isLoading = false
//                }
//
//                is Resource.Loading -> {
//
//                }
//
//                is Resource.Error -> {
//
//                }
//            }
//        }
//    }

}