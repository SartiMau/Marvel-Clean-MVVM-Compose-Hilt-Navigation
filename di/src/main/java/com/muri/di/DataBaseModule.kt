package com.muri.di

import android.content.Context
import androidx.room.Room
import com.muri.data.database.MarvelDB
import com.muri.data.database.MarvelDao
import com.muri.data.database.MarvelRepositoryImpl
import com.muri.domain.database.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private const val DB = "MarvelRepository"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MarvelDB {
        return Room
            .databaseBuilder(context, MarvelDB::class.java, DB)
            .build()
    }

    @Provides
    @Singleton
    fun provideMarvelDao(marvelDB: MarvelDB): MarvelDao = marvelDB.marvelDao()

    @Provides
    fun provideMarvelRepository(marvelDao: MarvelDao): MarvelRepository = MarvelRepositoryImpl(marvelDao)
}
