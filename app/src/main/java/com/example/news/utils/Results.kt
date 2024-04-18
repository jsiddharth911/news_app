package com.example.news.utils

import androidx.paging.PagingData
import com.example.news.data.database.entity.ArticleEntity

sealed class Results {
    data class Success(val data: PagingData<ArticleEntity>) : Results()
    data class Error(val errorMessage: String? = null) : Results()
}
