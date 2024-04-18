package com.example.news.utils

sealed class ViewState {
    object Idle : ViewState()
    object Loading : ViewState()
    data class Error(val message: String) : ViewState()
    object Success : ViewState()
}