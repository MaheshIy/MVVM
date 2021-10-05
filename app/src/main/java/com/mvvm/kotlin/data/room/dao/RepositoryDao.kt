package com.mvvm.kotlin.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mvvm.kotlin.data.room.entity.MatchesEntity

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM matchesentity")
    suspend fun getAll(): List<MatchesEntity>

    @Query("UPDATE MatchesEntity SET status =:status WHERE id =:id")
    suspend fun updateOnClickedRow(id : Int, status : Int)

    @Insert
    suspend fun insertAll(repositories: List<MatchesEntity>)

    @Delete
    suspend fun delete(response: List<MatchesEntity>)
}