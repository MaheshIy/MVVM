package com.mvvm.kotlin.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mvvm.kotlin.data.room.dao.RepositoryDao
import com.mvvm.kotlin.data.room.entity.MatchesEntity

@Database(entities = [MatchesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repositoryDao() : RepositoryDao
}