package com.mvvm.kotlin.data

import androidx.room.Database
import com.mvvm.kotlin.data.room.AppDatabase
import com.mvvm.kotlin.data.room.DatabaseHelper
import com.mvvm.kotlin.data.room.entity.MatchesEntity

class DatabaseHelperImpl(private val database: AppDatabase) : DatabaseHelper{

    override suspend fun getRepository(): List<MatchesEntity> = database.repositoryDao().getAll()

    override suspend fun updateOnClickedRow(id : Int, status : Int) = database.repositoryDao().updateOnClickedRow(id, status)

    override suspend fun insertAll(matchesEntity: List<MatchesEntity>) =
        database.repositoryDao().insertAll(matchesEntity)
}