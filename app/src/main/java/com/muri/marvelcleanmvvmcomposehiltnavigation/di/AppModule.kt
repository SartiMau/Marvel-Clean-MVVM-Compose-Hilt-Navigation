package com.muri.marvelcleanmvvmcomposehiltnavigation.di

import com.muri.data.di.DataModule
import com.muri.domain.di.DomainModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DataModule::class, DomainModule::class])
@InstallIn(SingletonComponent::class)
class AppModule
