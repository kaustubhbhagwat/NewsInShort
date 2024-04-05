package com.example.newsinshort.ui.screens.news_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newsinshort.ui.repository.NewsRepository
import kotlinx.coroutines.Job
import javax.inject.Inject

class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel(){

    var state by mutableStateOf(NewsState())

    private var search: Job? = null

    fun onEvent(newsScreenEvent: NewsScreenEvent){
        when(newsScreenEvent){
            is NewsScreenEvent.onCategoryChanged -> {
                state = state.copy(category = newsScreenEvent.category)
            }

            is NewsScreenEvent.onNewsCardClicked -> {
                state = state.copy(selectedArticle = newsScreenEvent.article)
            }

            is NewsScreenEvent.onSearchQueryChanged -> {
                state = state.copy(searchQuery = newsScreenEvent.searchQuery)
            }

            NewsScreenEvent.onSearchIconClicked -> {
                state = state.copy(
                    isSearchBarVisible = true ,
                    articles = emptyList()
                )
            }

            NewsScreenEvent.onCloseIconClick -> {
                state = state.copy(isSearchBarVisible = false)
//                getNewsArticles(category = state.category)
            }
        }
    }




}