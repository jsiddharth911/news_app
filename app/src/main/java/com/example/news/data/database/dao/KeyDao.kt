package com.example.news.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.data.database.entity.KeyEntity


@Dao
interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<KeyEntity?>?)

    @Query("SELECT * FROM keyTable WHERE id = :id")
    suspend fun getById(id: Int): KeyEntity?

    @Query("DELETE FROM keyTable")
    suspend fun clearAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'keyTable'")
    suspend fun deletePrimaryKeyIndex()
}