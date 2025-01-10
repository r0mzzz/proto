package com.example.domain.database.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyListDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToMyList(movie: MyList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToMyLikedList(movie: LikedMovies)

    @Query("SELECT * FROM my_list")
    suspend fun getAllList(): List<MyList>

    @Query("SELECT * FROM my_liked_list")
    suspend fun getAllLikedList(): List<LikedMovies>

    @Query("SELECT * FROM my_list WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id: Int): MyList?

    @Query("DELETE FROM my_list WHERE id = :id")
    fun deleteMovieById(id: Long)

    @Query("DELETE FROM my_list")
    fun deleteAll()
}