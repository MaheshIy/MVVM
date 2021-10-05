package com.mvvm.kotlin.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MatchesEntity(
    @ColumnInfo(name = "status") var status: Int?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "city") var city: String?,
    @ColumnInfo(name = "age") var age: Int?,
    @ColumnInfo(name = "picture") var picture: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}