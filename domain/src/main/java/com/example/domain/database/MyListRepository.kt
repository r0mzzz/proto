package com.example.domain.database

import com.example.domain.database.entity.MyList

interface MyListRepository {
    suspend fun insertNewData(myListDbEntity: MyList)
    suspend fun getAllList(): List<MyList>
    suspend fun removeStatisticDataById(id: Long)
    suspend fun deleteAll()
}