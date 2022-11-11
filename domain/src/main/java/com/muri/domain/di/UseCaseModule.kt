package com.muri.domain.di

import com.muri.domain.database.MarvelRepository
import com.muri.domain.service.CharacterService
import com.muri.domain.usecase.GetCharacterUseCase
import com.muri.domain.usecase.GetCharacterUseCaseImpl
import com.muri.domain.usecase.GetCharactersUseCase
import com.muri.domain.usecase.GetCharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCharactersUseCase(characterService: CharacterService, marvelRepository: MarvelRepository): GetCharactersUseCase =
        GetCharactersUseCaseImpl(characterService, marvelRepository)

    @Provides
    fun provideGetCharacterUseCase(characterService: CharacterService, marvelRepository: MarvelRepository): GetCharacterUseCase =
        GetCharacterUseCaseImpl(characterService, marvelRepository)
}
