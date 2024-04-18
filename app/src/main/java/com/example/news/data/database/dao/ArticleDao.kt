package com.example.news.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.data.database.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ArticleEntity>)

    @Query("SELECT * FROM articleTable")
    fun pagingSource(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articleTable")
    suspend fun clearAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'articleTable'")
    suspend fun deletePrimaryKeyIndex()
}