package com.muri.data.di

import com.muri.data.service.CharacterServiceImpl
import com.muri.data.service.api.MarvelApi
import com.muri.domain.service.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideCharacterService(marvelApi: MarvelApi): CharacterService = CharacterServiceImpl(marvelApi)
}
