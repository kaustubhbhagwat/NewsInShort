package com.example.newsinshort.ui.screens.news_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsinshort.ui.repository.NewsRepository2
import com.example.newsinshort.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository2
) : ViewModel() {

    var state by mutableStateOf(NewsState())

    private var searchJob: Job? = null

    fun onEvent(newsScreenEvent: NewsScreenEvent) {
        when (newsScreenEvent) {
            is NewsScreenEvent.onCategoryChanged -> {
                state = state.copy(category = newsScreenEvent.category)
                getNewsArticles(category = state.category)
            }

            is NewsScreenEvent.onNewsCardClicked -> {
                state = state.copy(selectedArticle = newsScreenEvent.article)
            }

            is NewsScreenEvent.onSearchQueryChanged -> {
                state = state.copy(searchQuery = newsScreenEvent.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.async {
                    searchForNews(query = state.searchQuery)
                }


            }

            NewsScreenEvent.onSearchIconClicked -> {
                state = state.copy(
                    isSearchBarVisible = true,
                    articles = emptyList()
                )
            }

            NewsScreenEvent.onCloseIconClick -> {
                state = state.copy(isSearchBarVisible = false)
                getNewsArticles(category = state.category)
            }
        }
    }

    private fun getNewsArticles(category: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = newsRepository.getTopHeadlines(category = category)
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        articles = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        articles = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }

        }
    }

    private fun searchForNews(query: String) {
        if (query.isEmpty()) {
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = newsRepository.searchForNews(query = query)
            when (result) {
                is Resource.Success -> {
                    state = state.copy(
                        articles = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        articles = emptyList(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }

    }
}