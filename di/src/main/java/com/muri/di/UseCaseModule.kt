package com.muri.di

import com.muri.domain.database.MarvelRepository
import com.muri.domain.service.CharacterService
import com.muri.domain.usecase.GetCharacterListUseCase
import com.muri.domain.usecase.GetCharacterListUseCaseImpl
import com.muri.domain.usecase.GetCharacterUseCase
import com.muri.domain.usecase.GetCharacterUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCharacterListUseCase(characterService: CharacterService, marvelRepository: MarvelRepository): GetCharacterListUseCase =
        GetCharacterListUseCaseImpl(characterService, marvelRepository)

    @Provides
    fun provideGetCharacterUseCase(characterService: CharacterService, marvelRepository: MarvelRepository): GetCharacterUseCase =
        GetCharacterUseCaseImpl(characterService, marvelRepository)
}
