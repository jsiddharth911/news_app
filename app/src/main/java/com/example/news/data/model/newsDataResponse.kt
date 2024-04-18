package com.example.news.data.model

data class newsDataResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)