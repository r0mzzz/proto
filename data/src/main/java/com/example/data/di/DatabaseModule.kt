package com.example.data.di

import com.example.domain.database.AppDatabase
import android.content.Context
import androidx.room.Room
import com.example.domain.database.MyListImpl
import com.example.domain.database.MyListRepository
import com.example.domain.database.entity.MyListDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMyListDao(appDatabase: AppDatabase): MyListDAO {
        return appDatabase.myListDao()
    }

    @Provides
    @Singleton
    fun provideMyListRepository(myListDao: MyListDAO): MyListRepository {
        return MyListImpl(myListDao)
    }
}
