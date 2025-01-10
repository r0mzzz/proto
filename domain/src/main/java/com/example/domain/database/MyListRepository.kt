package com.example.domain.database

import com.example.domain.database.entity.LikedMovies
import com.example.domain.database.entity.MyList

interface MyListRepository {
    suspend fun insertNewData(myListDbEntity: MyList)
    suspend fun insertDataToLiked(myListDbEntity: LikedMovies)
    suspend fun getAllList(): List<MyList>
    suspend fun getAllLiked(): List<LikedMovies>
    suspend fun removeStatisticDataById(id: Long)
    suspend fun deleteAll()
}