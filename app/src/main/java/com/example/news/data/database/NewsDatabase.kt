package com.example.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.data.database.dao.ArticleDao
import com.example.news.data.database.dao.KeyDao
import com.example.news.data.database.dao.WorkerDao
import com.example.news.data.database.entity.ArticleEntity
import com.example.news.data.database.entity.KeyEntity
import com.example.news.data.database.entity.WorkManagerEntity


@Database(
    entities = [ArticleEntity::class, KeyEntity::class, WorkManagerEntity::class],
    version = 1,
)
abstract class NewsDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao
    abstract val keyDao: KeyDao
    abstract val workerDao: WorkerDao
}