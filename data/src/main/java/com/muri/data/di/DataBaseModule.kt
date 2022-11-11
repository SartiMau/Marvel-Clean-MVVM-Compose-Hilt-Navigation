package com.muri.data.di

import android.content.Context
import androidx.room.Room
import com.muri.data.database.MarvelDB
import com.muri.data.database.MarvelDao
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
    fun provideAppDatabase(@ApplicationContext context: Context,): MarvelDB {
        return Room
            .databaseBuilder(context, MarvelDB::class.java, DB)
            .build()
    }

    @Provides
    @Singleton
    fun providePosterDao(marvelDB: MarvelDB): MarvelDao = marvelDB.marvelDao()
}
