package com.example.data.di

import com.example.data.errors.RemoteErrorMapper
import com.example.domain.exceptions.ErrorMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface ErrorMapperModule {
    @Binds
    @IntoSet
    fun bindErrorMapper(errorMapper: RemoteErrorMapper): ErrorMapper
}