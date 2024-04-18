package com.example.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.news.data.database.entity.ArticleEntity
import com.example.news.data.repository.NewsRepository
import com.example.news.domain.NewsWorker
import com.example.news.utils.Results
import com.example.news.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Idle)
    val viewState = _viewState
    private val _newsDataFlow = MutableStateFlow<PagingData<ArticleEntity>>(PagingData.empty())
    val newsDataFlow: StateFlow<PagingData<ArticleEntity>> = _newsDataFlow

    init {
        _viewState.value = ViewState.Loading
        handleIntent(MainIntent.getNews)
    }

    private fun getNews() {
        viewModelScope.launch {
            try {
                newsRepository.getNews()
                    .map { result ->
                        when (result) {
                            is Results.Success -> result.data
                            is Results.Error -> throw Exception("Data not found")
                        }
                    }
                    .catch { e ->
                        _viewState.value = ViewState.Error(e.message ?: "Unknown error")
                    }
                    .cachedIn(viewModelScope)
                    .flowOn(Dispatchers.IO)
                    .collect { data ->
                        _viewState.value = ViewState.Success
                        _newsDataFlow.value = data
                    }
            } catch (e: Exception) {
                _viewState.value = ViewState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun handleIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.getNews -> getNews()
        }
    }
}


