package com.parizek.myapplication.di

import com.parizek.myapplication.pokemon_detail_feature.data.repository.PokemonDetailRepositoryImpl
import com.parizek.myapplication.pokemon_detail_feature.domain.repository.PokemonDetailRepository
import com.parizek.myapplication.pokemon_list_feature.data.repository.PokemonListRepositoryImpl
import com.parizek.myapplication.pokemon_list_feature.domain.repository.PokemonListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonDetailRepository(
        repository: PokemonDetailRepositoryImpl
    ): PokemonDetailRepository

    @Binds
    @Singleton
    abstract fun bindPokemonListRepository(
        repository: PokemonListRepositoryImpl
    ): PokemonListRepository
}