package com.muri.domain.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [UseCaseModule::class])
@InstallIn(SingletonComponent::class)
object DomainModule
