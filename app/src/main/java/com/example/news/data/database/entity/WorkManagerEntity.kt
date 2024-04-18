package com.example.news.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workerTable")
data class WorkManagerEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "workerRequest") val request: String = "workerRequest",
    @ColumnInfo(name = "time") val time: String = "not available"
)
