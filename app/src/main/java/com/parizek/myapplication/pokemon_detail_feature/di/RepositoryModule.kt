package com.parizek.myapplication.pokemon_detail_feature.di

import com.parizek.myapplication.pokemon_detail_feature.data.repository.PokemonDetailRepositoryImpl
import com.parizek.myapplication.pokemon_detail_feature.domain.repository.PokemonDetailRepository
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

}