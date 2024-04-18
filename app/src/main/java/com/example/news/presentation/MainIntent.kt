package com.example.news.presentation

sealed class MainIntent {
    object getNews: MainIntent()
}