package com.example.data.di

import com.example.domain.services.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Singleton scope
class ApiModule {

    @Singleton
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}

