package com.example.news.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.news.data.database.NewsDatabase
import com.example.news.data.database.entity.ArticleEntity
import com.example.news.data.network.NewsService
import com.example.news.data.repository.NewsRepository
import com.example.news.data.repository.mediator.NewsAndDatabaseMediator
import com.example.news.domain.ServiceLocator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PagerModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsPager: Pager<Int, ArticleEntity>,
        newsDatabase: NewsDatabase
    ): NewsRepository {
        val newsRepository = NewsRepository(newsPager, newsDatabase)
        ServiceLocator.newsRepository = newsRepository
        return newsRepository
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePokemonPager(
        newsDatabase: NewsDatabase,
        newsApi: NewsService,
    ): Pager<Int, ArticleEntity> {
        return Pager(
            config = PagingConfig(pageSize = 5, maxSize = 20),
            remoteMediator = NewsAndDatabaseMediator(
                newsDatabase,
                newsApi = newsApi
            ),
            pagingSourceFactory = {
                newsDatabase.articleDao.pagingSource()
            },
        )
    }
}