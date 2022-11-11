package com.muri.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [ApiModule::class, DataBaseModule::class, ServiceModule::class])
@InstallIn(SingletonComponent::class)
object DataModule
