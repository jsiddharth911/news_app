package com.example.news.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.news.data.database.NewsDatabase
import com.example.news.data.database.entity.ArticleEntity
import com.example.news.data.database.entity.KeyEntity
import com.example.news.data.network.NewsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsAndDatabaseMediator @Inject constructor(
    private val newsDatabase: NewsDatabase,
    private val newsApi: NewsService,
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosetToCurrent(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // RETRIEVE NEXT OFFSET FROM DATABASE
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextPage
                }
            }

            // MAKE API CALL
            val apiResponse = newsApi.getNews(
                page = currentPage,
                pageSize = state.config.pageSize
            )

            val articles = apiResponse.body()?.articles
            val totalPages = kotlin.math.ceil(
                apiResponse.body()?.totalResults?.toDouble()
                    ?: (0.0 / state.config.pageSize.toDouble())
            ).toInt()

            if (articles.isNullOrEmpty()) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // IF REFRESHING, CLEAR DATABASE FIRST
                    newsDatabase.articleDao.clearAll()
                    newsDatabase.articleDao.deletePrimaryKeyIndex()
                    newsDatabase.keyDao.clearAll()
                    newsDatabase.keyDao.deletePrimaryKeyIndex()
                }
                val articleData = articles.map {
                    ArticleEntity(
                        author = it.author,
                        content = it.content,
                        description = it.description,
                        publishedAt = it.publishedAt,
                        sourceName = it.source.name,
                        title = it.title,
                        url = it.url,
                        urlToImage = it.urlToImage
                    )
                }
                newsDatabase.articleDao.insertAll(articleData)

                val keys = List(articles.size) { _ ->
                    val nextPage = if (currentPage == totalPages) null else currentPage + 1
                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    KeyEntity(nextPage = nextPage, prevPage = prevPage)
                }
                newsDatabase.keyDao.insertAll(keys)
            }

            // CHECK IF END OF PAGINATION REACHED
            MediatorResult.Success(endOfPaginationReached = currentPage == totalPages)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosetToCurrent(state: PagingState<Int, ArticleEntity>): KeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                newsDatabase.keyDao.getById(id = it)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): KeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            newsDatabase.keyDao.getById(id = it.id)
        }
    }
}