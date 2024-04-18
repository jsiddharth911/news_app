package com.example.news.data.repository

import android.os.Build
import androidx.paging.Pager
import com.example.news.data.database.NewsDatabase
import com.example.news.data.database.entity.ArticleEntity
import com.example.news.data.database.entity.WorkManagerEntity
import com.example.news.utils.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import java.time.LocalDateTime
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsPager: Pager<Int, ArticleEntity>,
    private val newsDatabase: NewsDatabase
) {
    fun getNews(): Flow<Results> {
        return newsPager.flow
            .map { pagingData ->
                    Results.Success(pagingData) as Results
            }
            .catch {
                emit(Results.Error("Something went wrong"))
            }
    }

    suspend fun workManagerRequest() : Flow<Results> {
        val currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            0
        }
        newsDatabase.workerDao.insertAll(WorkManagerEntity(request = "worker request",
            time = currentTime.toString()))
        return newsPager.flow
            .take(1)
            .map { pagingData ->
                Results.Success(pagingData) as Results
            }
            .catch {
                emit(Results.Error("Something went wrong"))
            }
    }
}