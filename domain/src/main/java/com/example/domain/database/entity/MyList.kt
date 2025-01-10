package com.example.domain.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_list")
data class MyList(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "movie_poster") val moviePoster: String,
    @ColumnInfo(name = "movie_title") val movieTitle: String
)
