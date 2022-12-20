package com.muri.marvelcleanmvvmcomposehiltnavigation.di

import com.muri.di.ApiModule
import com.muri.di.DataBaseModule
import com.muri.di.ServiceModule
import com.muri.di.UseCaseModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        ApiModule::class,
        DataBaseModule::class,
        ServiceModule::class,
        UseCaseModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class AppModule
