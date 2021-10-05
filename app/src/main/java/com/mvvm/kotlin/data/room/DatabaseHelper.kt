package com.mvvm.kotlin.data.room

import com.mvvm.kotlin.data.room.entity.MatchesEntity

interface DatabaseHelper {

    suspend fun getRepository() : List<MatchesEntity>

    suspend fun insertAll(matchesEntity: List<MatchesEntity>)

    suspend fun updateOnClickedRow(id: Int, status : Int)
}