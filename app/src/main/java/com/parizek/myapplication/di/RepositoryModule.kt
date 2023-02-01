package com.parizek.myapplication.di

import com.parizek.myapplication.data.repository.PokemonRepositoryImpl
import com.parizek.myapplication.domain.repository.PokemonRepository
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
        repository: PokemonRepositoryImpl
    ): PokemonRepository
}