package com.example.domain.database

import com.example.domain.database.entity.MyList
import com.example.domain.database.entity.MyListDAO
import javax.inject.Inject

class MyListImpl @Inject constructor(
    private val myListDao: MyListDAO
) : MyListRepository {

    override suspend fun insertNewData(myListDbEntity: MyList) {
        myListDao.insertToMyList(myListDbEntity)
    }

    override suspend fun getAllList(): List<MyList> {
        return myListDao.getAllList()
    }

    override suspend fun removeStatisticDataById(id: Long) {
        return myListDao.deleteMovieById(id)
    }

    override suspend fun deleteAll() {
        return myListDao.deleteAll()

    }

}