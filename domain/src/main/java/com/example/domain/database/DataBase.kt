package com.example.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.database.entity.LikedMovies
import com.example.domain.database.entity.MyList
import com.example.domain.database.entity.MyListDAO

@Database(entities = [MyList::class, LikedMovies::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myListDao(): MyListDAO
}

