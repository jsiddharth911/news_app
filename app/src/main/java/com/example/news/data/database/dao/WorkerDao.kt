package com.example.news.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.news.data.database.entity.WorkManagerEntity

@Dao
interface WorkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: WorkManagerEntity)
}