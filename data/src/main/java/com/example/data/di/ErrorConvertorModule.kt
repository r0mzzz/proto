package com.example.data.di

import com.example.domain.exceptions.ErrorConvertor
import com.example.domain.exceptions.ErrorConvertorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ErrorConvertorModule {
    @Binds
    fun bindErrorConvertor(errorConvertorImpl: ErrorConvertorImpl): ErrorConvertor
}