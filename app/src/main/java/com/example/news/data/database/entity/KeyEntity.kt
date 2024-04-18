package com.example.news.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keyTable")
data class KeyEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "nextPage") val nextPage: Int?,
    @ColumnInfo(name = "prevPage") val prevPage: Int?
)
